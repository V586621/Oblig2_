package no.hvl.dat153.oblig2;


import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSubstring;
import static org.junit.Assert.assertNotNull;

import no.hvl.dat153.oblig2.main.activities.QuizActivity;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class QuizActivityTest {

    @Rule
    public ActivityScenarioRule<QuizActivity> mActivityRule = new ActivityScenarioRule<QuizActivity>(
            QuizActivity.class);

    QuizActivity activity;

    @Before
    public void setup(){
        mActivityRule.getScenario().onActivity(o ->{activity=o;});
    }

    @Test
    public void startCorrectTest() throws InterruptedException {
        assertNotNull(activity);

        Thread.sleep(3000); //waiting so that the page loads
        String n = activity.getFirstPerson();
        int siz = activity.getDatabaseSize();
        //String n = QuizActivity.pList.get(0).getName();

        onView(withId(R.id.textViewScore)).check(matches(withSubstring("Score: " + 0 + "/" + siz)));
        onView(withId(R.id.guessText)).perform(typeText(n));
        onView(withId(R.id.guessButton)).perform(click());
        onView(withId(R.id.textViewScore)).check(matches(withSubstring("Score: " + 1 + "/" + siz)));
    }

    @Test
    public void startWrongTest() throws InterruptedException {
        assertNotNull(activity);
        Thread.sleep(3000); //waiting so that the page loads
        //String n = QuizActivity.pList.get(0).getName();
        String n = activity.getFirstPerson();
        int siz = activity.getDatabaseSize();
        onView(withId(R.id.textViewScore)).check(matches(withSubstring("Score: " + 0 + "/" + siz)));
        onView(withId(R.id.guessText)).perform(typeText("WRONG NAME"));
        onView(withId(R.id.guessButton)).perform(click());
        onView(withId(R.id.textViewScore)).check(matches(withSubstring("Score: " + 0 + "/" + siz)));
    }



}