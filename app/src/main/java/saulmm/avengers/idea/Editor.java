package saulmm.avengers.idea;

import android.app.Activity;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import saulmm.avengers.R;


public class Editor extends Activity {














    // Complete current statement (shift + cmd + enter)
    public boolean canIClaimTheThrone(IronThrone throne) {
        if (throne.isEmpty()) {
            return true;
        }

        return false;
    }












    // Join lines shift + ctrl + j
    public String theDoor() {
        final String hold = "Hold" +
            " the" +
            " door";

        return hold + " = HODOR";
    }














    // Surround with: cmd + alt + T
    public void getANicePositionOnWesteros() {
        RedWoman redWoman = new RedWoman();

        redWoman.helpStannisTheTrueKing();
    }













    // Multiple cursors : CTRL + G
    public void bindButtons (Activity a) {
        Toolbar chToolbar               = (Toolbar) findViewById(R.id.character_toolbar);
        TextView chBiographyTextView    = (TextView) findViewById(R.id.character_biography);
        TextView chLabelTextView        = (TextView) findViewById(R.id.character_label_info);
        ProgressBar chProgress          = (ProgressBar) findViewById(R.id.collection_loading);
        ViewGroup chInfoContainer       = (ViewGroup) findViewById(R.id.character_info_container);
        ImageView chImage               = (ImageView) findViewById(R.id.character_image);
        AppBarLayout chAppBar           = (AppBarLayout) findViewById(R.id.character_appbar);
    }















    // Playing with booleans
    public boolean askTheGoods(boolean lightGod, boolean oldGod, boolean newGod) {
        if (!(newGod && lightGod || !(!lightGod || !oldGod))) {
            return false;
        }
        return true;
    }












    // cmd + opt + v -> Variable
    // cmd + opt + f -> field
    // cmd + opt + c -> constant
    public void lookForSwag(String toValidate, List<String> aList) {
        boolean isEmpty = aList.isEmpty();

        if (isEmpty) {
            for (String yupi : aList) {
                if (toValidate.equals("CONSTANT")) {
                    System.out.println(yupi);
                }
            }
        }
    }















    // extract
    // cmd + opt - m -> method
    public void lookForSwag2(String toValidate, List<String> aList) {
        boolean isEmpty = aList.isEmpty();

        if (isEmpty) {
            for (String yupi : aList) {
                if (toValidate.equals("CONSTANT")) {
                    System.out.println(yupi);
                }
            }
        }
    }





    // Inject code
    public String injectingCode(String a) {
        String html = "";

        return html;
    }






    // Live templates
    // logt

    public boolean doHardComputation(String param1, int param2, String param3) {
        // logm

        boolean computationResult = 1 + 1 == 2;
        // live_template.xml

        // cmd + j

        // logr
        return computationResult;
    }











    // Postfix
    public void lookForStuff(List<Person> personList) {
        for (Person person : personList) {
            if (person != null) {
                System.out.println(person);
            }
        }
    }


    private static final String TAG = "Editor";

    // Live templates
    public String anAmazingLongMethod(String param1, int param2, Person param3) {
        Log.d(TAG, "anAmazingLongMethod() called with: " + "param1 = [" + param1 + "], param2 = [" + param2 + "], param3 = [" + param3 + "]");

        Log.d(TAG, "anAmazingLongMethod() returned: " + param1);
        return param1;

    }









    // CMD + A -> scrach file
    public void scachFiles() {

    }


    // Extend / Shrink selection: alt up/down
    class IronThrone {
        private King king;

        public IronThrone(King king) {
            this.king = king;
        }

        public King getKing() {
            return king;
        }

        public boolean isEmpty() { return true; }
    }

    class King {
        private String name;
    }

    class Person {
        String name;
    }

    class RedWoman {
        public void helpStannisTheTrueKing() {};
    }

    class RansayExpception extends Exception {

    }
}
