package org.tmcrafz.flipiconchecker;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class FlipIconChecker extends FrameLayout implements View.OnClickListener {

    public interface OnFlipIconCheckerClickedListener {
        void onFlipIconCheckerClicked();
    }

    private static final String TAG = FlipIconChecker.class.getSimpleName();

    private OnFlipIconCheckerClickedListener mOnFlipIconCheckerClickedListener;

    private ImageView mImageView;
    // The picture which is scale in when the view is flipped after checking
    private ImageView mImageViewCheck;

    // The duration of the whole animation
    private long mDuration;
    // The drawable resource which is shown when the flipper is unchecked
    private int mFrontDrawableResource;
    // The drawable resource which is shown when the flipper is checked
    private int mBackDrawableResource;

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


        setOnClickListener(this);
        init(context, attr);
    }

    private void init(Context context, AttributeSet attr) {

        TypedArray a = context.getTheme().obtainStyledAttributes(attr, R.styleable.FlipIconChecker, 0, 0);
        try {
            mFrontDrawableResource = a.getResourceId(R. styleable.FlipIconChecker_frontSrc, R.drawable.circle_unchecked);
            mBackDrawableResource = a.getResourceId(R.styleable.FlipIconChecker_backSrc, R.drawable.circle_checked);
            mCheckDrawableResource = a.getResourceId(R.styleable.FlipIconChecker_checkSrc, R.drawable.ic_check);
            mDuration = a.getInteger(R.styleable.FlipIconChecker_duration, 200);
            //mClockStyle = ClockStyle.fromInteger(a.getInteger(R.styleable.Clock_clockStyle, 0));
            //mClockNumberRange = ClockNumberRange.fromInteger(a.getInteger(R.styleable.Clock_clockNumberRange, 0));
        } finally {
            a.recycle();
        }

        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.flip_icon_checker, this, true);

        mImageView = (ImageView) findViewById(R.id.imageView);
        mImageView.setImageResource(mFrontDrawableResource);
        mImageViewCheck = (ImageView) findViewById(R.id.imageViewCheck);
        mImageViewCheck.setImageResource(mCheckDrawableResource);

        setOnClickListener(this);
        mIsChecked = false;
        mIsInAnim = false;
        // Check animation
        mAnimatorCheckPh1 = ObjectAnimator.ofFloat(this, "rotationY", 0.f, 90.f);
        mAnimatorCheckPh1.setDuration(mDuration / 2);
        mAnimatorCheckPh1.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                // Optional
                mImageView.setImageResource(mFrontDrawableResource);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mImageView.setImageResource(mBackDrawableResource);
                mAnimatorCheckPh2.start();
                mImageViewCheck.setVisibility(VISIBLE);
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
                mImageView.setImageResource(mBackDrawableResource);
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mImageView.setImageResource(mFrontDrawableResource);
                mAnimatorUnCheckPh2.start();
                mImageViewCheck.setVisibility(GONE);
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
                objectAnimator.ofFloat(mImageViewCheck, "scaleX", 0.0f, 0.8f),
                objectAnimator.ofFloat(mImageViewCheck, "scaleY", 0.0f, 0.8f)
        );
        mAnimatorScaleIn.setDuration(mDuration);
    }

    public void setOnFlipIconCheckerClickedListener(OnFlipIconCheckerClickedListener listener) { mOnFlipIconCheckerClickedListener = listener; }

    public void setFrontDrawableResource(int resource) {
        mFrontDrawableResource = resource;
        if (!mIsChecked)
            mImageView.setImageResource(mFrontDrawableResource);
    }

    public void setBackDrawableResource(int resource) {
        mBackDrawableResource = resource;
        if (mIsChecked)
            mImageView.setImageResource(mBackDrawableResource);
    }

    public void setCheckDrawableResource(int resource) {
        mCheckDrawableResource = resource;
        mImageViewCheck.setImageResource(mCheckDrawableResource);
    }

    public boolean isChecked() { return mIsChecked; }

    public int getFrontDrawableResource() { return mFrontDrawableResource; }

    public int getBackDrawableResource() { return mFrontDrawableResource; }

    public int getCheckDrawableResource() { return mFrontDrawableResource; }

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
            mImageView.setImageResource(mBackDrawableResource);
            mImageViewCheck.setVisibility(VISIBLE);
        }
        else {
            mImageView.setImageResource(mFrontDrawableResource);
            mImageViewCheck.setVisibility(GONE);
        }
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
