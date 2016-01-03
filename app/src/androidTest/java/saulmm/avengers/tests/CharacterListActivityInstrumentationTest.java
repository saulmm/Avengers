package saulmm.avengers.tests;


import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import saulmm.avengers.R;
import saulmm.avengers.injector.components.DaggerAvengersComponent;
import saulmm.avengers.injector.modules.ActivityModule;
import saulmm.avengers.repository.CharacterRepository;
import saulmm.avengers.views.activities.CharacterDetailActivity;
import saulmm.avengers.views.activities.CharacterListActivity;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.times;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class CharacterListActivityInstrumentationTest {

    @Rule
    public IntentsTestRule<CharacterListActivity> mCharacterListIntentRule =
            new IntentsTestRule<>(CharacterListActivity.class);

    @Before
    public void setup() {
    }

    @Test
    public void testThatAClickOnTheAvengerOPensTheDetailActivity() {
        waitForRequest();

        onView(ViewMatchers.withId(R.id.activity_avengers_recycler))
            .perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        intended(hasComponent(CharacterDetailActivity.class.getCanonicalName()));
    }

    public void waitForRequest() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
