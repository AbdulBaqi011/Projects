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
public class AdjustComparisonSettingsFunctionTest {
    /*
    These tests cover the `Adjust Comparison Settings` behaviors at the functional level
     */

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule =
            new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void user_can_see_comparison_settings() {
        /*
         * Given I am a user
         * When I click on 'Adjust Comparison Settings'
         * Then a form for 'Comparison Settings' details is displayed
         */
        onView(withId(R.id.comparisonSettingsButtonID)).perform(click());

        onView(withId(R.id.yearlySalaryWeightID)).check(matches(isDisplayed()));
        onView(withId(R.id.yearlyBonusWeightID)).check(matches(isDisplayed()));
        onView(withId(R.id.tuitionReimbursementWeightID)).check(matches(isDisplayed()));
        onView(withId(R.id.healthInsuranceWeightID)).check(matches(isDisplayed()));
        onView(withId(R.id.employeeDiscountWeightID)).check(matches(isDisplayed()));
        onView(withId(R.id.childAdoptionWeightID)).check(matches(isDisplayed()));
    }

    @Test
    public void user_can_cancel_comparison_settings_update() {
        /*
         * Given I am a user that has entered `Comparison Settings` values
         * When I click `cancel`
         * Then the comparison settings weights are not changed and the view resets
         */
        onView(withId(R.id.comparisonSettingsButtonID)).perform(click());

        onView(withId(R.id.yearlySalaryWeightID)).perform(replaceText("123"), closeSoftKeyboard());
        onView(withId(R.id.yearlySalaryWeightID)).check(matches(withText("123")));

        onView(withId(R.id.cancelButtonComparisonID)).perform(click());
        onView(withId(R.id.yearlySalaryWeightID)).check(matches(withText("")));
    }
}