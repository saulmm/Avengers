package saulmm.avengers;

import android.content.Context;
import android.graphics.Typeface;

public class Utils {

    public final static String CHARACTER_IMG_NAME           = "ch_thumb";
    public final static String BANGERS_TTF                  = "fonts/Bangers.ttf";

    public static final int DIALOG_ACCEPT = -1;
    public static final int DIALOG_CANCEL = -2;

    public static String getListTransitionName (int position) {
        return CHARACTER_IMG_NAME + position;
    }

    public static Typeface getBangersTypeface (Context context) {
        return Typeface.createFromAsset(context.getAssets(), BANGERS_TTF);
    }
}
