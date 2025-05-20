package edu.gatech.seclass.jobcompare6300;

import android.content.Context;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

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


public class CurrentJobFunctionTest {
    /*
    These tests cover the 'Manage Current Job Details' behaviors.
     */

    @Test
    public void user_can_see_current_job_details() {
        /*
         * Given I am a user
         * When I click on 'Manage Current Job Details'
         * Then a form for Current Job details is displayed
         */
        onView(withId(R.id.currentjobbuttonID)).perform(click());

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
    public void user_can_save_current_job_details() {
        /*
        * Given I am a user on the 'Manage Current Job Details' view
        * When I enter all job details into the form and click 'save'
        * Then the job details are updated
         */
        onView(withId(R.id.currentjobbuttonID)).perform(click());

        onView(withId(R.id.saveButtonUnitID)).perform(replaceText("123"), closeSoftKeyboard());

        onView(withId(R.id.titleUnitID)).perform(replaceText("123"), closeSoftKeyboard());
        onView(withId(R.id.companyUnitID)).perform(replaceText("123"), closeSoftKeyboard());
        onView(withId(R.id.StateUnitID)).perform(replaceText("123"), closeSoftKeyboard());
        onView(withId(R.id.CityUnitID)).perform(replaceText("123"), closeSoftKeyboard());
        onView(withId(R.id.costoflivingUnitID)).perform(replaceText("123"), closeSoftKeyboard());
        onView(withId(R.id.yearlysalaryUnitID)).perform(replaceText("123"), closeSoftKeyboard());
        onView(withId(R.id.yearlybonusUnitID)).perform(replaceText("2"), closeSoftKeyboard());
        onView(withId(R.id.tuitionreimbursementUnitID)).perform(replaceText("2"), closeSoftKeyboard());
        onView(withId(R.id.healthinsuranceUnitID)).perform(replaceText("2"), closeSoftKeyboard());
        onView(withId(R.id.employeediscountUnitID)).perform(replaceText("2"), closeSoftKeyboard());
        onView(withId(R.id.childadoptionUnitID)).perform(replaceText("2"), closeSoftKeyboard());

        onView(withId(R.id.saveButtonUnitID)).perform(click());

        // todo: change below to reference a database instead
        onView(withId(R.id.yearlySalaryWeightID)).check(matches(withText("123")));
        onView(withId(R.id.titleUnitID)).check(matches(withText("123")));
        onView(withId(R.id.companyUnitID)).check(matches(withText("123")));
        onView(withId(R.id.StateUnitID)).check(matches(withText("123")));
        onView(withId(R.id.CityUnitID)).check(matches(withText("123")));
        onView(withId(R.id.costoflivingUnitID)).check(matches(withText("123")));
        onView(withId(R.id.yearlysalaryUnitID)).check(matches(withText("123")));
        onView(withId(R.id.yearlybonusUnitID)).check(matches(withText("2")));
        onView(withId(R.id.tuitionreimbursementUnitID)).check(matches(withText("2")));
        onView(withId(R.id.healthinsuranceUnitID)).check(matches(withText("2")));
        onView(withId(R.id.employeediscountUnitID)).check(matches(withText("2")));
        onView(withId(R.id.childadoptionUnitID)).check(matches(withText("2")));
    }

    @Test
    public void job_details_must_be_valid() {
        /*
        * Given I am a user on the 'Manage Current Job Details' view
        * When I enter all job details into the form and click 'save'
        * Then the job details are updated
         */
        onView(withId(R.id.currentjobbuttonID)).perform(click());

        onView(withId(R.id.saveButtonUnitID)).perform(replaceText("123"), closeSoftKeyboard());

        onView(withId(R.id.companyUnitID)).perform(replaceText("123"), closeSoftKeyboard());
        onView(withId(R.id.StateUnitID)).perform(replaceText("123"), closeSoftKeyboard());
        onView(withId(R.id.CityUnitID)).perform(replaceText("123"), closeSoftKeyboard());
        onView(withId(R.id.costoflivingUnitID)).perform(replaceText("123"), closeSoftKeyboard());
        onView(withId(R.id.yearlysalaryUnitID)).perform(replaceText("123"), closeSoftKeyboard());
        onView(withId(R.id.yearlybonusUnitID)).perform(replaceText("2"), closeSoftKeyboard());
        onView(withId(R.id.tuitionreimbursementUnitID)).perform(replaceText("2"), closeSoftKeyboard());
        onView(withId(R.id.healthinsuranceUnitID)).perform(replaceText("2"), closeSoftKeyboard());
        onView(withId(R.id.employeediscountUnitID)).perform(replaceText("2"), closeSoftKeyboard());
        onView(withId(R.id.childadoptionUnitID)).perform(replaceText("2"), closeSoftKeyboard());

        onView(withId(R.id.saveButtonUnitID)).perform(click());

        // todo: change below to reference a database instead
        onView(withId(R.id.yearlySalaryWeightID)).check(matches(withText("123")));
        onView(withId(R.id.titleUnitID)).check(matches(hasErrorText("This field is required")));
    }

    @Test
    public void user_can_cancel_updating_current_job_details() {
        /*
         * Given I am a user on the 'Manage Current Job Details' view
         * When I enter all job details into the form and click 'cancel'
         * Then the job details are not updated
         */
        onView(withId(R.id.currentjobbuttonID)).perform(click());

        onView(withId(R.id.saveButtonUnitID)).perform(replaceText("123"), closeSoftKeyboard());

        onView(withId(R.id.titleUnitID)).perform(replaceText("123"), closeSoftKeyboard());
        onView(withId(R.id.companyUnitID)).perform(replaceText("123"), closeSoftKeyboard());
        onView(withId(R.id.StateUnitID)).perform(replaceText("123"), closeSoftKeyboard());
        onView(withId(R.id.CityUnitID)).perform(replaceText("123"), closeSoftKeyboard());
        onView(withId(R.id.costoflivingUnitID)).perform(replaceText("123"), closeSoftKeyboard());
        onView(withId(R.id.yearlysalaryUnitID)).perform(replaceText("123"), closeSoftKeyboard());
        onView(withId(R.id.yearlybonusUnitID)).perform(replaceText("2"), closeSoftKeyboard());
        onView(withId(R.id.tuitionreimbursementUnitID)).perform(replaceText("2"), closeSoftKeyboard());
        onView(withId(R.id.healthinsuranceUnitID)).perform(replaceText("2"), closeSoftKeyboard());
        onView(withId(R.id.employeediscountUnitID)).perform(replaceText("2"), closeSoftKeyboard());
        onView(withId(R.id.childadoptionUnitID)).perform(replaceText("2"), closeSoftKeyboard());

        onView(withId(R.id.cancelButtonUnitID)).perform(click());

        onView(withId(R.id.yearlySalaryWeightID)).check(matches(withText("")));
        onView(withId(R.id.titleUnitID)).check(matches(withText("")));
        onView(withId(R.id.companyUnitID)).check(matches(withText("")));
        onView(withId(R.id.StateUnitID)).check(matches(withText("")));
        onView(withId(R.id.CityUnitID)).check(matches(withText("")));
        onView(withId(R.id.costoflivingUnitID)).check(matches(withText("")));
        onView(withId(R.id.yearlysalaryUnitID)).check(matches(withText("")));
        onView(withId(R.id.yearlybonusUnitID)).check(matches(withText("")));
        onView(withId(R.id.tuitionreimbursementUnitID)).check(matches(withText("")));
        onView(withId(R.id.healthinsuranceUnitID)).check(matches(withText("")));
        onView(withId(R.id.employeediscountUnitID)).check(matches(withText("")));
        onView(withId(R.id.childadoptionUnitID)).check(matches(withText("")));
    }
}