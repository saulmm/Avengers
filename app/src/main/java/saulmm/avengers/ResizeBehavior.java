package saulmm.avengers;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

@SuppressWarnings("unused")
public class ResizeBehavior extends CoordinatorLayout.Behavior<ImageView> {
    private final static String TAG = "behavior";

    private float mFinalLeftAvatarPadding;
    private float mStartPosition;
    private float mStartToolbarPosition;
    private int mStartWidth;
    private int mFinalWidth;

    public ResizeBehavior(Context context, AttributeSet attrs) {
    }

    private int finalHeight;
    private int mStartHeight;

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, ImageView child, View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, ImageView child, View dependency) {
        shouldInitProperties(child, dependency);
        float expandedPercentageFactor = dependency.getY() / mStartHeight;

        // The child will remain always in the middle of its dependency
        child.setY(((dependency.getY() /2) + (dependency.getHeight() /2)) - child.getHeight()/2);
        float collapsedHeight = ((mStartHeight) * (1f - Math.abs(expandedPercentageFactor)));

        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        if (collapsedHeight > finalHeight) lp.height = (int) collapsedHeight;
        child.setLayoutParams(lp);
        return true;
    }

    private void shouldInitProperties(ImageView child, View dependency) {
        if (mStartHeight == 0)
            mStartHeight = child.getHeight();

        if (mStartWidth == 0)
            mStartWidth = child.getWidth();

        if (finalHeight == 0) finalHeight =
            child.getContext().getResources().getDimensionPixelOffset(R.dimen.image_final_height);

        if (mStartToolbarPosition == 0)
            mStartToolbarPosition = dependency.getY() + (dependency.getHeight()/2);
    }
}