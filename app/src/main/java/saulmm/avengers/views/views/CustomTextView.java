package saulmm.avengers.views.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;
import saulmm.avengers.R;

public class CustomTextView extends TextView {
	public CustomTextView(Context context) {
		super(context);
	}

	public CustomTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public CustomTextView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
			R.styleable.CustomTextView, 0, 0);

		String typefaceName = a.getString(R.styleable.CustomTextView_typeface);

		if (typefaceName != null && !typefaceName.equals("")) {
			setTypeface(Typeface.createFromAsset(context.getAssets(), typefaceName));
		}

		a.recycle();
	}
}
