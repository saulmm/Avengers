package saulmm.avengers;

import saulmm.avengers.views.activities.AvengersListActivity;

public class Utils {
    public static final int DIALOG_ACCEPT = -1;
    public static final int DIALOG_CANCEL = -2;

    public static String getListTransitionName (int position) {
        return AvengersListActivity.CHARACTER_IMG_NAME + position;
    }
}
