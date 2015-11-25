package saulmm.avengers.utils;

import android.databinding.BindingAdapter;
import android.view.View;

public class DatabindingUtils {
	@BindingAdapter({"app:onClick"})
	public static void bindOnClick(View view, final Runnable runnable) {
		view.setOnClickListener(v -> runnable.run());
	}
}
