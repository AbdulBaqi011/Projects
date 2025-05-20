package edu.gatech.seclass.jobcompare6300;

import org.junit.Test;

import static org.junit.Assert.*;


public class ManageJobOffersUnitTest {
    /*
    These tests cover the 'Manage Job Offers' behaviors
     */

    @Test
    public void user_can_see_job_details_form() {
        /*
        * Given I am a user
        * When I click on 'Manage Job Offers'
        * Then I can see a 'Job Details' form
         */
    }

    @Test
    public void user_can_enter_job_details() {
        /*
        * Given I am a user viewing the 'Job Details' form
        * When I enter all Job details and click 'save'
        * Then all Job details are updated
         */
    }

    @Test
    public void user_cannot_save_incomplete_job_details() {
        /*
        * Given I am a user viewing the 'Job Details' form
        * When I enter incomplete job details
        * Then the 'save' button is disabled
         */
    }

    @Test
    public void user_can_cancel_job_offer_details_form() {
        /*
        * Given I am a user viewing the 'Job Details' form
        * When I enter Job details and click 'cancel'
        * Then the Job details remain unchanged
         */
    }
}