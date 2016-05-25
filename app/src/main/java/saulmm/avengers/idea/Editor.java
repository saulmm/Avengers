package saulmm.avengers.idea;

import android.app.Activity;
import android.util.Log;

import java.util.List;


public class Editor {

    // Complete current statement (shift + cmd + enter)
    public boolean canIClainTheThrone(IronThrone throne) {
        if (throne.isEmpty( )) {
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



    // Surround with: cmd + control + T
    public void getANicePositionOnWesteros() {
        RedWoman redWoman = new RedWoman();

        redWoman.helpStannisTheTrueKing();
    }


    // Multiple cursors : CTRL + G
    public void bindButtons (Activity a) {
//        FloatingActionButton fab    = (FloatingActionButton) a.findViewById(R.id.fab_edit_task);
//        Button button               = (Button) a.findViewById(R.id.fab_edit_task);
//        TextView textView           = (TextView) a.findViewById(R.id.fab_edit_task);
//        EditText editText           = (EditText) a.findViewById(R.id.fab_edit_task);
//        FrameLayout frameLayout     = (FrameLayout) a.findViewById(R.id.fab_edit_task);
    }



    // Playing with booleans
    public boolean askTheGoods(boolean lightGod, boolean oldGod, boolean newGod) {
        if (!(newGod && lightGod || !(!lightGod || !oldGod))) {
            return false;
        }
        return true;
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

    // Live templates menu: CMD + J
    public void anAmazingLongMethod2() {
        // TODO: 25/05/16

        // TODO: 25/05/16
    }

    // Inject code
    public String injectingCode(String a) {
        String html = "<html>\n" +
            "    <body>\n" +
            "        <comment id=\"nunca sere una app\"/>\n" +
            "    </body>\n" +
            "</html>";

        return "{\"clave2\":{\"df\":\"\"}, \"clave1\":\"valor1\"}";
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
