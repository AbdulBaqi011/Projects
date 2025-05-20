package edu.gatech.seclass.jobcompare6300;

import android.content.Context;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
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


public class StartupFunctionTest {
    /*
    These tests cover the 'Startup' behaviors
     */

    @Test
    public void user_can_see_job_details_form() {
        /*
         * Given I am a user
         * When I launch the app
         * Then I see the 'Main Menu' and all options are selectable
         */
        onView(withId(R.id.MainMenuJobOfferID)).check(matches(isDisplayed()));
    }
}