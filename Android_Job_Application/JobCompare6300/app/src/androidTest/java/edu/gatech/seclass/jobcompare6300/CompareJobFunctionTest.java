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

// todo: implement this test after the comparison table is imiplemented
public class CompareJobFunctionTest {
    /*
    These tests cover the 'Compare Job' behaviors.
     */

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void user_can_see_job_offers() {
        /*
         * Given I am a user
         * When I click on 'Compare Job'
         * Then a table is displayed with a list of Job Offers
         */
        onView(withId(R.id.compareJobsButtonID)).perform(click());

        onView(withId(R.id.titleUnitID)).check(matches(isDisplayed()));
        onView(withId(R.id.companyUnitID)).check(matches(isDisplayed()));
        onView(withId(R.id.StateUnitID)).check(matches(isDisplayed()));
        onView(withId(R.id.CityUnitID)).check(matches(isDisplayed()));
        onView(withId(R.id.costoflivingUnitID)).check(matches(isDisplayed()));
        onView(withId(R.id.yearlysalaryUnitID)).check(matches(isDisplayed()));
        onView(withId(R.id.yearlybonusUnitID)).check(matches(isDisplayed()));
        onView(withId(R.id.tuitionreimbursementUnitID)).check(matches(isDisplayed()));
        onView(withId(R.id.healthinsuranceUnitID)).check(matches(isDisplayed()));
        onView(withId(R.id.employeediscountUnitID)).check(matches(isDisplayed()));
        onView(withId(R.id.childadoptionUnitID)).check(matches(isDisplayed()));
    }

    @Test
    public void user_can_compare_two_job_details() {
        /*
        * Given I am a user on the 'Compare Job' view
        * When I select two jobs from the list
        * Then I see the two job details displayed in the comparison table
         */
    }

    @Test
    public void user_cannot_compare_unselected_jobs() {
        /*
        * Given I am a user on the 'Compare Job' view
        * When I do not select exactly two jobs from the list
        * Then the comparison button is disabled
         */
    }

}