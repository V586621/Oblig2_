package no.hvl.dat153.oblig2;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import no.hvl.dat153.oblig2.main.activities.DatabaseActivity;
import no.hvl.dat153.oblig2.main.activities.QuizActivity;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;

import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class DatabaseDeleteTest {

    @Rule
    public ActivityScenarioRule<DatabaseActivity> mActivityRule = new ActivityScenarioRule<DatabaseActivity>(
            DatabaseActivity.class);

    DatabaseActivity activity;

    @Before
    public void setup(){
        mActivityRule.getScenario().onActivity(o ->{activity=o;});
    }
    //Sletter første elementet i datbasen og sjekker om databasen har blitt midre etter slettingen.
    @Test
    public void startDeleteTest() throws InterruptedException {
        Thread.sleep(3000); //waiting so that the page loads
        int n = activity.getDatabaseSize();
        int id = activity.getFirstPersonId();
        if (n > 0) {
            onView(withId(R.id.edittext_delete)).perform(typeText(String.valueOf(id)));
            onView(withId(R.id.button_delete)).perform(click());
            assertEquals(activity.getDatabaseSize(), (n - 1));
        } else {
            System.out.println("INGEN DATA I DATABASE");
        }


    }


}
