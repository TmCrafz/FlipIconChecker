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

    private OnFlipIconCheckerClickedListener m_onFlipIconCheckerClickedListener;

    // The duration of the whole animation
    private long m_duration;

    // The view which is shown when the icon is unchecked
    // the view have a custom child view
    private FrameLayout m_viewParentFront;
    // The view which is flipping to the front when icon is clicked
    // the view have a custom child view
    private FrameLayout m_viewParentBack;
    // The view which is scale in when the view is flipped after checking
    // the view have a custom child view
    private FrameLayout m_viewParentCheck;
    // The custom view which get added to the parent front view
    private View m_viewFront;
    // The cutsom view which get added to the parent back view
    private View m_viewBack;
    // The cutsom view which get added to the parent back view
    private View m_viewCheck;

    // The drawable resource ids are zero if the user dont specifies drawables
    // The drawable resource which is shown when the flipper is unchecked
    private int m_frontDrawableResource;
    // The drawable resource which is shown when the flipper is checked
    private int m_backDrawableResource;
    // The drawable resource which is "scaling" in when the flipper is checked
    private int m_checkDrawableResource;

    private boolean m_isChecked;
    private boolean m_isInAnim;

    // The animator which is played when the checker gets checked
    private Animator m_animatorCheckPh1;
    // The second phase of the check animation
    private Animator m_animatorCheckPh2;

    // The animator which is played when the checker gets unchecked
    private Animator m_animatorUnCheckPh1;
    // The second phase of the uncheck animation
    private Animator m_animatorUnCheckPh2;

    // The Animator which scale the check drawable
    private AnimatorSet m_animatorScaleIn;

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

        m_viewParentFront = (FrameLayout) view.findViewById(R.id.layout_parent_front);
        m_viewParentBack = (FrameLayout) view.findViewById(R.id.layout_parent_back);
        m_viewParentCheck = (FrameLayout) view.findViewById(R.id.layout_parent_check);

        try {
            // Zero when nothing is specifies so we can check later if user have specified a resource id
            m_frontDrawableResource = a.getResourceId(R.styleable.FlipIconChecker_frontSrc, 0);
            m_backDrawableResource = a.getResourceId(R.styleable.FlipIconChecker_backSrc, 0);
            m_checkDrawableResource = a.getResourceId(R.styleable.FlipIconChecker_checkSrc, 0);

            m_duration = a.getInteger(R.styleable.FlipIconChecker_duration, 200);

            // Create Front side
            // Only load custom layout when user dont specified a front drawable resource
            int frontViewId = R.layout.front_view_default;
            if (m_frontDrawableResource == 0) {
                frontViewId = a.getResourceId(R.styleable.FlipIconChecker_frontView, R.layout.front_view_default);
            }
            m_viewFront = inflater.inflate(frontViewId, this, false).getRootView();
            // Add the custom drawable if there is one
            if (m_frontDrawableResource != 0) {
                ImageView frontImageView = (ImageView) m_viewFront;
                frontImageView.setImageResource(m_frontDrawableResource);
            }
            m_viewParentFront.addView(m_viewFront);

            // Crate back side
            int backViewId = R.layout.back_view_default;
            if (m_backDrawableResource == 0) {
                backViewId = a.getResourceId(R.styleable.FlipIconChecker_backView, R.layout.back_view_default);
            }
            m_viewBack = inflater.inflate(backViewId, this, false).getRootView();
            if (m_backDrawableResource != 0) {
                ImageView backImageView = (ImageView) m_viewBack;
                backImageView.setImageResource(m_backDrawableResource);
            }
            m_viewParentBack.addView(m_viewBack);

            // Create check view
            int checkViewId = R.layout.check_view_default;
            if (m_backDrawableResource == 0) {
                checkViewId = a.getResourceId(R.styleable.FlipIconChecker_checkView, R.layout.check_view_default);
            }
            m_viewCheck = inflater.inflate(checkViewId, this, false).getRootView();
            if (m_backDrawableResource != 0) {
                ImageView checkImageView = (ImageView) m_viewCheck;
                checkImageView.setImageResource(m_checkDrawableResource);
            }
            m_viewParentCheck.addView(m_viewCheck);
            m_viewParentCheck.setScaleX(0.8f);
            m_viewParentCheck.setScaleY(0.8f);

        } finally {
            a.recycle();
        }

        setOnClickListener(this);
        if (!isInEditMode()) {
            initAnimation();
        }
    }

    private void initAnimation() {
        m_isChecked = false;
        m_isInAnim = false;
        // Check animation
        m_animatorCheckPh1 = ObjectAnimator.ofFloat(this, "rotationY", 0.f, 90.f);
        m_animatorCheckPh1.setDuration(m_duration / 2);
        m_animatorCheckPh1.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                // Optional
                showFrontLayout();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                showBackLayout();

                m_animatorCheckPh2.start();
                m_viewParentCheck.setVisibility(VISIBLE);
                m_animatorScaleIn.start();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        // Rotate from 270 to 360 instead of 90 to 180 so the drawable is shown correct and not mirrored
        m_animatorCheckPh2 = ObjectAnimator.ofFloat(this, "rotationY", 270.f, 360.f);
        m_animatorCheckPh2.setDuration(m_duration / 2);
        m_animatorCheckPh2.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                m_isInAnim = false;
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
        m_animatorUnCheckPh1 = ObjectAnimator.ofFloat(this, "rotationY", 360.f, 270.f);
        m_animatorUnCheckPh1.setDuration(m_duration / 2);
        m_animatorUnCheckPh1.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                // Optional
                showBackLayout();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                showFrontLayout();

                m_animatorUnCheckPh2.start();
                m_viewParentCheck.setVisibility(GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        m_animatorUnCheckPh2 = ObjectAnimator.ofFloat(this, "rotationY", 90.f, 0.f);
        m_animatorUnCheckPh2.setDuration(m_duration / 2);
        m_animatorUnCheckPh2.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                m_isInAnim = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        ObjectAnimator objectAnimator = new ObjectAnimator();
        m_animatorScaleIn = new AnimatorSet();
        m_animatorScaleIn.playTogether(
                objectAnimator.ofFloat(m_viewParentCheck, "scaleX", 0.0f, 0.8f),
                objectAnimator.ofFloat(m_viewParentCheck, "scaleY", 0.0f, 0.8f)
        );
        m_animatorScaleIn.setDuration(m_duration);
    }

    public void setOnFlipIconCheckerClickedListener(OnFlipIconCheckerClickedListener listener) { m_onFlipIconCheckerClickedListener = listener; }

    public void setFrontView(View frontView) {
        m_viewFront = frontView;
        // Remove old views before adding the new one, so the new view is shown
        m_viewParentFront.removeAllViews();
        m_viewParentFront.addView(m_viewFront);
    }

    public void setBackView(View backView) {
        m_viewBack = backView;
        m_viewParentBack.removeAllViews();
        m_viewParentBack.addView(m_viewBack);
    }

    public void setCheckView(View checkView) {
        m_viewCheck = checkView;
        m_viewParentCheck.removeAllViews();
        m_viewParentCheck.addView(m_viewCheck);
    }

    public boolean isChecked() { return m_isChecked; }

    public View getFrontView() { return m_viewFront; }

    public View getBackView() { return m_viewBack; }

    public View getCheckView() { return m_viewCheck; }


    public int getFrontDrawableResource() { return m_frontDrawableResource; }

    public int getBackDrawableResource() { return m_frontDrawableResource; }

    public int getCheckDrawableResource() { return m_frontDrawableResource; }



    // Change the state of the checker and start animations
    public void changeState() {
        if (!m_isInAnim) {
            if (!m_isChecked) {
                m_isInAnim = true;
                m_isChecked = true;
                m_animatorCheckPh1.start();
            }
            else if (m_isChecked) {
                m_isInAnim = true;
                m_isChecked = false;
                m_animatorUnCheckPh1.start();
            }
        }
    }

    public void setChecked(boolean checked) {
        m_isChecked = checked;
        if (checked) {
            showBackLayout();
            m_viewParentCheck.setVisibility(VISIBLE);
        }
        else {
            showFrontLayout();
            m_viewParentCheck.setVisibility(GONE);
        }
    }

    private void showFrontLayout() {
        m_viewParentBack.setVisibility(GONE);
        m_viewParentFront.setVisibility(VISIBLE);
    }

    private void showBackLayout() {
        m_viewParentFront.setVisibility(GONE);
        m_viewParentBack.setVisibility(VISIBLE);
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

        if (m_onFlipIconCheckerClickedListener != null) {
            m_onFlipIconCheckerClickedListener.onFlipIconCheckerClicked();
        }
    }
}