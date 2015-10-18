package saulmm.avengers;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

@SuppressWarnings("unused")
public class ResizeBehavior extends CoordinatorLayout.Behavior<ImageView> {

    private final static float MIN_AVATAR_PERCENTAGE_SIZE   = 0.3f;
    private final static int EXTRA_FINAL_AVATAR_PADDING     = 80;

    private final static String TAG = "behavior";
    private final Context mContext;
    private float mAvatarMaxSize;

    private float mFinalLeftAvatarPadding;
    private float mStartPosition;
    private float mStartToolbarPosition;
    private int mStartWidth;
    private int mFinalWidth;

    public ResizeBehavior(Context context, AttributeSet attrs) {
        mContext = context;
        init();

        mFinalLeftAvatarPadding = context.getResources().getDimension(
            R.dimen.spacing_normal);
    }

    private void init() {
        bindDimensions();
    }

    private void bindDimensions() {
        mAvatarMaxSize = mContext.getResources().getDimension(R.dimen.image_width);
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

        final int maxScrollDistance = (int) (mStartToolbarPosition - getStatusBarHeight());
        float expandedPercentageFactor = dependency.getY() / maxScrollDistance;

        // The child will remain always in the middle of its dependency
        child.setY(((dependency.getY() /2) + (dependency.getHeight() /2)) - child.getHeight()/2);

        float collapsedHeight = ((mStartHeight) * (1f - Math.abs(expandedPercentageFactor)));

            String log = String.format("%d * (1f - %.2f) = %d", (int) mStartHeight, Math.abs(expandedPercentageFactor), (int) collapsedHeight);
            System.out.println("[DEBUG]" + " ResizeBehavior onDependentViewChanged - " + log);
        //float widthToSubtract = ((mStartWidth - mFinalWidth) * (1f - expandedPercentageFactor));

        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
        //lp.width = (int) (mStartWidth - widthToSubtract);
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
            mContext.getResources().getDimensionPixelOffset(R.dimen.image_final_height);



        if (mStartToolbarPosition == 0)
            mStartToolbarPosition = dependency.getY() + (dependency.getHeight()/2);
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = mContext.getResources().getIdentifier("status_bar_height", "dimen", "android");

        if (resourceId > 0) {
            result = mContext.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}