package edu.gatech.seclass.jobcompare6300;

import android.content.Context;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class EditJobDetailsFunctionTest {
    /*
    These tests cover the `Adjust Comparison Settings` behaviors at the functional level
     */

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void user_can_see_comparison_settings() {
        /*
         * Given I am a user on the main menu
         * When I click on 'Job Offers', I'm given a list of job offers
         * Then when I click on the 'edit' button, I can see job details for that job
         */

        onView(withId(R.id.jobOffersButtonID)).perform(click());
        onView(withId(R.id.editButton)).perform(click());
    }
}