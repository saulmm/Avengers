package saulmm.avengers.views.utils;

import android.animation.Animator;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewAnimationUtils;

public class AnimUtils {

	public static final int REVEAL_DURATION = 350;

	public static void showRevealEffect(final View v, int centerX, int centerY,
		@Nullable Animator.AnimatorListener lis) {

		v.setVisibility(View.VISIBLE);

		int height = v.getHeight();

		Animator anim = ViewAnimationUtils.createCircularReveal(
			v, centerX, centerY, 0, height);

		anim.setDuration(REVEAL_DURATION);

		if (lis != null)
			anim.addListener(lis);

		anim.start();
	}
}
