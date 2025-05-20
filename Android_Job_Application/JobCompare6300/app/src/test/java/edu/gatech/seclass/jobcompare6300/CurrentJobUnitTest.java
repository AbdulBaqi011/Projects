package edu.gatech.seclass.jobcompare6300;

import org.junit.Test;

import static org.junit.Assert.*;


public class CurrentJobUnitTest {
    /*
    These tests cover the 'Manage Current Job Details' behaviors.
     */

    @Test
    public void user_can_save_current_job_details() {
        /*
        * Given I am a user on the 'Manage Current Job Details' view
        * When I enter all job details into the form and click 'save'
        * Then the health insurance is computed correctly
         */
        Location location = new Location("some_city", "some_state", 1.2f);
        Job subject = new Job(
                "some_job",
                "some_title",
                "some_company",
                location.getLocationKey(),
                100,
                2,
                3,
                4,
                5,
                6.6f
        );
        subject.setAdjustedHealthInsurance();
        float actual = subject.getAdjustedHealthInsurance();
        assert actual == 6.0f;
    }
}