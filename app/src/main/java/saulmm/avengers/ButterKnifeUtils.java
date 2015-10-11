package saulmm.avengers;

import android.widget.TextView;
import butterknife.ButterKnife;

public class ButterKnifeUtils {

	public static final ButterKnife.Setter<TextView, Integer> TEXTCOLOR = new ButterKnife.Setter<TextView, Integer>() {

		@Override
		public void set(TextView view, Integer value, int index) {
			view.setTextColor(value);
		}
	};
}
