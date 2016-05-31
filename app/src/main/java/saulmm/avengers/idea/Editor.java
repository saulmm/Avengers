package saulmm.avengers.idea;

import android.app.Activity;
import android.support.design.widget.AppBarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.Toolbar;
import android.transition.Transition;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import saulmm.avengers.R;
import saulmm.avengers.entities.Character;
import saulmm.avengers.views.activities.CharacterDetailActivity;


@SuppressWarnings("unused")
public class Editor extends Activity {


    public static final String BEST_CHARACTER_EVER = "Iron Man";

    // Complete current statement (shift + cmd + enter)
    public boolean canIClaimTheThrone(IronThrone throne) {
        if (throne.isEmpty()) {
            return true;
        }

        return false;
    }




















    public void replaceVsAppend(Character character) {
        Toolbar b = (Toolbar) findViewById(R.id.character_appbar);

        // Replace vs append
        if (character.getName().equals(BEST_CHARACTER_EVER)) {
            CharacterDetailActivity.start(this, character.getName(), character.getId());
        }
    }











    // Join lines shift + ctrl + j
    public String theDoor() {
        final String hold = "Hold" +
            " the" +
            " door";

        return hold + " = HODOR";
    }















    public void moveAllTheThings() {
        String one = "one";
        String two = "two";
        String three = "three";
        String four = "four";
        String five = "five";
        String six = "six";


        // xml
    }














    // Expand / Collapse selection
    public static void excludeTransitionIds(Transition transition, Integer[] exlcudeIds) {
        transition.excludeTarget(android.R.id.statusBarBackground, true);
        transition.excludeTarget(android.R.id.navigationBarBackground, true);

        for (Integer exlcudeId : exlcudeIds) {
            transition.excludeTarget(exlcudeId, true);
        }
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













    // Surround with: cmd + alt + T
    public void getANicePositionOnWesteros() {
        RedWoman redWoman = new RedWoman();

        redWoman.helpStannisTheTrueKing();
    }















    // Playing with booleans
    public boolean askTheGoods(boolean lightGod, boolean oldGod, boolean newGod) {
        if (!(newGod && lightGod || !(!lightGod || !oldGod))) {
            return false;
        }
        return true;
    }






    public Object refactoringWithMenu() {
        float farAway = .3f;
        long ago = 10000L;
        int string = 1337;
        long member = 10000L;
        Object wat = null;


        // option + t
        farAway *= 3;
        string /= 2 % 3;
        wat.hashCode();

        return wat;
    }









    // cmd + opt + v -> Variable
    // cmd + opt + f -> field
    // cmd + opt + c -> constant
    public void refactorIsNotRefactorWithoutTests(String toValidate, List<String> aList) {
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
        //JSON
        String amazingJson = "";


        //HTML div>(header>ul>li*2>a)+footer>p
        String html = "";

        //regXp .+@.+\..+
        String regExp = "";

        return html;
    }







    // Live templates
    // logt

    public boolean longLiveToTemplates(String param1, int param2, String param3) {
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





    public void structuralReplaceHere() {
        // Replace structuraelly
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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



    class DaenerisDragon {
        boolean isMomLookingForBurnSomeone;

        public void waitForMom() {
            if (!isMomLookingForBurnSomeone) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    class RedWoman {
        public void helpStannisTheTrueKing() {};
    }

    class RansayExpception extends Exception {

    }
}
