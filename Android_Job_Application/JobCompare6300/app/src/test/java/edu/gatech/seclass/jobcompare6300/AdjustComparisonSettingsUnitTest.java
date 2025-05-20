package edu.gatech.seclass.jobcompare6300;

import org.junit.Test;
import static org.junit.Assert.*;


public class AdjustComparisonSettingsUnitTest {
    /*
    These tests cover the `Adjust Comparison Settings` behaviors at the unit level
     */

    public AdjustComparisonSettingsUnitTest() {}

    @Test
    public void user_can_update_comparison_settings() {
        /*
        * Given I am a user on the `Comparison Settings` view
        * When I enter my comparison setting weights and click `save`
        * Then the weights are properly updated
        */
        ComparisonSettings subject = new ComparisonSettings();

        subject.setYearlySalaryWeight(134124);
        subject.setYearlyBonusWeight(2);
        subject.setTuitionReimbursementWeight(3);
        subject.setHealthInsuranceWeight(5);
        subject.setEmployeeDiscountWeight(9);
        subject.setChildAdoptionWeight(1);

        assertEquals(subject.getYearlySalaryWeight(), 134124);
        assertEquals(subject.getYearlyBonusWeight(), 2);
        assertEquals(subject.getTuitionReimbursementWeight(), 3);
        assertEquals(subject.getHealthInsuranceWeight(), 5);
        assertEquals(subject.getEmployeeDiscountWeight(), 9);
        assertEquals(subject.getChildAdoptionWeight(), 1);

    }

    @Test
    public void comparison_settings_have_default_values() {
        /*
        * Given I am a user that has clicked on 'Adjust Comparison Settings'
        * When I have not entered any values
        * Then all default values are set to 1
         */
        ComparisonSettings subject = new ComparisonSettings();
        assertEquals(subject.getYearlySalaryWeight(), 1);
        assertEquals(subject.getYearlyBonusWeight(), 1);
        assertEquals(subject.getTuitionReimbursementWeight(), 1);
        assertEquals(subject.getHealthInsuranceWeight(), 1);
        assertEquals(subject.getEmployeeDiscountWeight(), 1);
        assertEquals(subject.getChildAdoptionWeight(), 1);
    }
}