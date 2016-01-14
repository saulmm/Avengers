package saulmm.avengers;

import android.content.Context;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

public class ResizeBehaviorTest {


    @Test
    public void testLayoutDependsOn() throws Exception {
        CoordinatorLayout parent = mock(CoordinatorLayout.class);
        ImageView child = mock(ImageView.class);
        View view = mock(View.class);

        Context context = mock(Context.class);
        AttributeSet attrs = mock(AttributeSet.class);

        ResizeBehavior resizeBehavior = new ResizeBehavior(context, attrs);

        //when
        boolean result = resizeBehavior.layoutDependsOn(parent, child, view);

        //then
        assertFalse(result);

        //when
        AppBarLayout appBarLayout = mock(AppBarLayout.class);
        result = resizeBehavior.layoutDependsOn(parent, child, appBarLayout);

        //then
        assertTrue(result);
    }
}