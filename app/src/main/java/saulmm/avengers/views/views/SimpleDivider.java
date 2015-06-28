package saulmm.avengers.views.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import saulmm.avengers.R;

public class SimpleDivider extends RecyclerView.ItemDecoration {
 
    private final int mPadding;

    private Drawable mDivider;
 
    public SimpleDivider(Context context) {

        mDivider = context.getDrawable(R.drawable.divider);
        mPadding = context.getResources().getDimensionPixelOffset(R.dimen.spacing_normal);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {

        super.onDrawOver(c, parent, state);

        int left = mPadding;
        int right = parent.getWidth() - mPadding;

        int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {

            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                .getLayoutParams();

            int top = child.getBottom() + params.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();

            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
}