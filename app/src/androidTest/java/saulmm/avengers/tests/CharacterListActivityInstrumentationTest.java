package saulmm.avengers.tests;

import android.content.Intent;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.Intents;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.util.Log;
import android.widget.TextView;

import com.squareup.okhttp.mockwebserver.Dispatcher;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;
import com.squareup.okhttp.mockwebserver.RecordedRequest;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;

import saulmm.avengers.R;
import saulmm.avengers.TestData;
import saulmm.avengers.injector.components.DaggerAvengersComponent;
import saulmm.avengers.injector.modules.ActivityModule;
import saulmm.avengers.repository.CharacterRepository;
import saulmm.avengers.rest.RestDataSource;
import saulmm.avengers.views.activities.CharacterDetailActivity;
import saulmm.avengers.views.activities.CharacterListActivity;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.times;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.matcher.ViewMatchers.*;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class CharacterListActivityInstrumentationTest {
    private MockWebServer mMockWebServer;

    @Rule public ActivityTestRule<CharacterListActivity> mCharacterListIntentRule =
            new ActivityTestRule<>(CharacterListActivity.class, true, false);

    @Before public void setUp() throws Exception {
        mMockWebServer = new MockWebServer();
        mMockWebServer.start();

        RestDataSource.END_POINT = mMockWebServer.getUrl("/").toString();
    }

    @Test public void showCharacters() {
        mMockWebServer.enqueue(new MockResponse().setBody(TestData.TWENTY_CHARACTERS_JSON));

        mCharacterListIntentRule.launchActivity(null);

        onView(withId(R.id.activity_avengers_recycler)).check(matches(isDisplayed()));
    }

    @After public void tearDown() throws Exception {
        mMockWebServer.shutdown();
    }
}
