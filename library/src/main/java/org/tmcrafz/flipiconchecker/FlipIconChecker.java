package org.tmcrafz.flipiconchecker;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class FlipIconChecker extends FrameLayout implements View.OnClickListener {

    public interface OnFlipIconCheckerClickedListener {
        void onFlipIconCheckerClicked();
    }

    private static final String TAG = FlipIconChecker.class.getSimpleName();

    private OnFlipIconCheckerClickedListener mOnFlipIconCheckerClickedListener;

    // The duration of the whole animation
    private long mDuration;

    // The view which is shown when the icon is unchecked
    // the view have a custom child view
    private FrameLayout mView_parent_front;
    // The view which is flipping to the front when icon is clicked
    // the view have a custom child view
    private FrameLayout mView_parent_back;
    // The view which is scale in when the view is flipped after checking
    // the view have a custom child view
    private FrameLayout mView_parent_check;
    // The custom view which get added to the parent front view
    private View mView_front;
    // The cutsom view which get added to the parent back view
    private View mView_back;
    // The cutsom view which get added to the parent back view
    private View mView_check;

    // The drawable resource ids are zero if the user dont specifies drawables
    // The drawable resource which is shown when the flipper is unchecked
    private int mFrontDrawableResource;
    // The drawable resource which is shown when the flipper is checked
    private int mBackDrawableResource;
    // The drawable resource which is "scaling" in when the flipper is checked
    private int mCheckDrawableResource;

    private boolean mIsChecked;
    private boolean mIsInAnim;

    // The animator which is played when the checker gets checked
    private Animator mAnimatorCheckPh1;
    // The second phase of the check animation
    private Animator mAnimatorCheckPh2;

    // The animator which is played when the checker gets unchecked
    private Animator mAnimatorUnCheckPh1;
    // The second phase of the uncheck animation
    private Animator mAnimatorUnCheckPh2;

    // The Animator which scale the check drawable
    private AnimatorSet mAnimatorScaleIn;

    public FlipIconChecker(Context context) {
        super(context);

        init(context, null);
    }

    public FlipIconChecker(Context context, AttributeSet attr) {
        super(context, attr);

        init(context, attr);
    }

    private void init(Context context, AttributeSet attr) {
        TypedArray a = context.getTheme().obtainStyledAttributes(attr, R.styleable.FlipIconChecker, 0, 0);

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View view = inflater.inflate(R.layout.flip_icon_checker, this, true);

        mView_parent_front = (FrameLayout) view.findViewById(R.id.layout_parent_front);
        mView_parent_back = (FrameLayout) view.findViewById(R.id.layout_parent_back);
        mView_parent_check = (FrameLayout) view.findViewById(R.id.layout_parent_check);

        try {
            // Zero when nothing is specifies so we can check later if user have specified a resource id
            mFrontDrawableResource = a.getResourceId(R.styleable.FlipIconChecker_frontSrc, 0);
            mBackDrawableResource = a.getResourceId(R.styleable.FlipIconChecker_backSrc, 0);
            mCheckDrawableResource = a.getResourceId(R.styleable.FlipIconChecker_checkSrc, 0);

            mDuration = a.getInteger(R.styleable.FlipIconChecker_duration, 200);

            // Create Front side
            // Only load custom layout when user dont specified a front drawable resource
            int frontViewId = R.layout.front_view_default;
            if (mFrontDrawableResource == 0) {
                frontViewId = a.getResourceId(R.styleable.FlipIconChecker_frontView, R.layout.front_view_default);
            }
            mView_front = inflater.inflate(frontViewId, this, false).getRootView();
            // Add the custom drawable if there is one
            if (mFrontDrawableResource != 0) {
                ImageView frontImageView = (ImageView) mView_front;
                frontImageView.setImageResource(mFrontDrawableResource);
            }
            mView_parent_front.addView(mView_front);

            // Crate back side
            int backViewId = R.layout.back_view_default;
            if (mBackDrawableResource == 0) {
                backViewId = a.getResourceId(R.styleable.FlipIconChecker_backView, R.layout.back_view_default);
            }
            mView_back = inflater.inflate(backViewId, this, false).getRootView();
            if (mBackDrawableResource != 0) {
                ImageView backImageView = (ImageView) mView_back;
                backImageView.setImageResource(mBackDrawableResource);
            }
            mView_parent_back.addView(mView_back);

            // Create check view
            int checkViewId = R.layout.check_view_default;
            if (mBackDrawableResource == 0) {
                checkViewId = a.getResourceId(R.styleable.FlipIconChecker_checkView, R.layout.check_view_default);
            }
            mView_check = inflater.inflate(checkViewId, this, false).getRootView();
            if (mBackDrawableResource != 0) {
                ImageView checkImageView = (ImageView) mView_check;
                checkImageView.setImageResource(mCheckDrawableResource);
            }
            mView_parent_check.addView(mView_check);

        } finally {
            a.recycle();
        }

        setOnClickListener(this);
        if (!isInEditMode()) {
            initAnimation();
        }
    }

    private void initAnimation() {
        mIsChecked = false;
        mIsInAnim = false;
        // Check animation
        mAnimatorCheckPh1 = ObjectAnimator.ofFloat(this, "rotationY", 0.f, 90.f);
        mAnimatorCheckPh1.setDuration(mDuration / 2);
        mAnimatorCheckPh1.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                // Optional
                //mImageView.setImageResource(mFrontDrawableResource);
                showFrontLayout();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //mImageView.setImageResource(mBackDrawableResource);
                showBackLayout();

                mAnimatorCheckPh2.start();
                mView_parent_check.setVisibility(VISIBLE);
                mAnimatorScaleIn.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        // Rotate from 270 to 360 instead of 90 to 180 so the drawable is shown correct and not mirrored
        mAnimatorCheckPh2 = ObjectAnimator.ofFloat(this, "rotationY", 270.f, 360.f);
        mAnimatorCheckPh2.setDuration(mDuration / 2);
        mAnimatorCheckPh2.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mIsInAnim = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        // Uncheck animation
        // Rotate from 360 to 270 instead of 180 to 90 so the drawable is shown correct and not mirrored
        mAnimatorUnCheckPh1 = ObjectAnimator.ofFloat(this, "rotationY", 360.f, 270.f);
        mAnimatorUnCheckPh1.setDuration(mDuration / 2);
        mAnimatorUnCheckPh1.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                // Optional
                //mImageView.setImageResource(mBackDrawableResource);
                showBackLayout();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //mImageView.setImageResource(mFrontDrawableResource);
                showFrontLayout();

                mAnimatorUnCheckPh2.start();
                mView_parent_check.setVisibility(GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mAnimatorUnCheckPh2 = ObjectAnimator.ofFloat(this, "rotationY", 90.f, 0.f);
        mAnimatorUnCheckPh2.setDuration(mDuration / 2);
        mAnimatorUnCheckPh2.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mIsInAnim = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        ObjectAnimator objectAnimator = new ObjectAnimator();
        mAnimatorScaleIn = new AnimatorSet();
        mAnimatorScaleIn.playTogether(
                objectAnimator.ofFloat(mView_parent_check, "scaleX", 0.0f, 0.8f),
                objectAnimator.ofFloat(mView_parent_check, "scaleY", 0.0f, 0.8f)
        );
        mAnimatorScaleIn.setDuration(mDuration);
    }

    public void setOnFlipIconCheckerClickedListener(OnFlipIconCheckerClickedListener listener) { mOnFlipIconCheckerClickedListener = listener; }

    // Test if it works !!
    public void setFrontView(View frontView) {
        mView_front = frontView;
    }

    public void setBackView(View backView) {
        mView_back = backView;
    }

    public void setCheckView(View checkView) {
        mView_check = checkView;
    }

    /*
    public void setFrontDrawableResource(int resource) {
        mFrontDrawableResource = resource;
        if (!mIsChecked) {
            //mImageView.setImageResource(mFrontDrawableResource);
        }
    }

    public void setBackDrawableResource(int resource) {
        mBackDrawableResource = resource;
        if (mIsChecked) {
            //mImageView.setImageResource(mBackDrawableResource);
        }
    }


    public void setCheckDrawableResource(int resource) {
        mCheckDrawableResource = resource;
        mImageViewCheck.setImageResource(mCheckDrawableResource);
    }

    */

    public boolean isChecked() { return mIsChecked; }

    public View getFrontView() { return mView_front; }

    public View getBackView() { return mView_back; }

    public View getCheckView() { return mView_check; }

    /*
    public int getFrontDrawableResource() { return mFrontDrawableResource; }

    public int getBackDrawableResource() { return mFrontDrawableResource; }

    public int getCheckDrawableResource() { return mFrontDrawableResource; }
    */
    // Change the state of the checker and start animations
    public void changeState() {
        if (!mIsInAnim) {
            if (!mIsChecked) {
                mIsInAnim = true;
                mIsChecked = true;
                mAnimatorCheckPh1.start();
            }
            else if (mIsChecked) {
                mIsInAnim = true;
                mIsChecked = false;
                mAnimatorUnCheckPh1.start();
            }
        }
    }

    public void setChecked(boolean checked) {
        mIsChecked = checked;
        if (checked) {
            //mImageView.setImageResource(mBackDrawableResource);
            showBackLayout();
            mView_parent_check.setVisibility(VISIBLE);
        }
        else {
            //mImageView.setImageResource(mFrontDrawableResource);
            showFrontLayout();
            mView_parent_check.setVisibility(GONE);
        }
    }

    private void showFrontLayout() {
        mView_parent_back.setVisibility(GONE);
        mView_parent_front.setVisibility(VISIBLE);
    }

    private void showBackLayout() {
        mView_parent_front.setVisibility(GONE);
        mView_parent_back.setVisibility(VISIBLE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int width = getWidth();
        int height = getHeight();
    }

    @Override
    public void onClick(View v) {
        changeState();

        if (mOnFlipIconCheckerClickedListener != null) {
            mOnFlipIconCheckerClickedListener.onFlipIconCheckerClicked();
        }
    }
}
