package edu.gatech.seclass.jobcompare6300;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JobOfferAddActivity extends AppCompatActivity {

    private EditText titleEditText, companyEditText, stateEditText, cityEditText, costOfLivingEditText,
            yearlySalaryEditText, yearlyBonusEditText, tuitionReimbursementEditText,
            healthInsuranceEditText, employeeDiscountEditText, childAdoptionEditText;
    private FileStorage storage;
    private Button cancelButton, saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_offer_add);
        storage = new FileStorage(this);

        titleEditText = findViewById(R.id.titleUnitID);
        companyEditText = findViewById(R.id.companyUnitID);
        stateEditText = findViewById(R.id.StateUnitID);
        cityEditText = findViewById(R.id.CityUnitID);
        costOfLivingEditText = findViewById(R.id.costoflivingUnitID);
        yearlySalaryEditText = findViewById(R.id.yearlysalaryUnitID);
        yearlyBonusEditText = findViewById(R.id.yearlybonusUnitID);
        tuitionReimbursementEditText = findViewById(R.id.tuitionreimbursementUnitID);
        healthInsuranceEditText = findViewById(R.id.healthinsuranceUnitID);
        employeeDiscountEditText = findViewById(R.id.employeediscountUnitID);
        childAdoptionEditText = findViewById(R.id.childadoptionUnitID);

        cancelButton = findViewById(R.id.cancelButtonUnitID);
        saveButton = findViewById(R.id.saveButtonUnitID);

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(JobOfferAddActivity.this, "Any edits made were not saved.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateInputs()) {

                    saveJobOffer();
                    finish(); // Close this activity and return to the previous one
                } else {
                    Toast.makeText(JobOfferAddActivity.this, "Fix the errors before saving.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateInputs() {
        int error_seen = 0;

        // Clear previous errors
        titleEditText.setError(null);
        companyEditText.setError(null);
        stateEditText.setError(null);
        cityEditText.setError(null);
        costOfLivingEditText.setError(null);
        yearlySalaryEditText.setError(null);
        yearlyBonusEditText.setError(null);
        tuitionReimbursementEditText.setError(null);
        healthInsuranceEditText.setError(null);
        employeeDiscountEditText.setError(null);
        childAdoptionEditText.setError(null);

        // Required field checks
        if (titleEditText.getText().toString().isEmpty()) {
            titleEditText.setError("This field is required");
            error_seen = 1;
        }
        if (companyEditText.getText().toString().isEmpty()) {
            companyEditText.setError("This field is required");
            error_seen = 1;
        }
        if (stateEditText.getText().toString().isEmpty()) {
            stateEditText.setError("This field is required");
            error_seen = 1;
        }
        if (cityEditText.getText().toString().isEmpty()) {
            cityEditText.setError("This field is required");
            error_seen = 1;
        }
        if (costOfLivingEditText.getText().toString().isEmpty()) {
            costOfLivingEditText.setError("This field is required");
            error_seen = 1;
        }
        if (yearlySalaryEditText.getText().toString().isEmpty()) {
            yearlySalaryEditText.setError("This field is required");
            error_seen = 1;
        }
        if (yearlyBonusEditText.getText().toString().isEmpty()) {
            yearlyBonusEditText.setError("This field is required");
            error_seen = 1;
        }

        // Tuition Reimbursement validation
        if (tuitionReimbursementEditText.getText().toString().isEmpty()) {
            tuitionReimbursementEditText.setError("This field is required");
            error_seen = 1;
        } else if (Float.parseFloat(tuitionReimbursementEditText.getText().toString()) < 0 || Float.parseFloat(tuitionReimbursementEditText.getText().toString()) > 15000) {
            tuitionReimbursementEditText.setError("Please enter a value between 0 and 15000");
            error_seen = 1;
        }

        // Health Insurance validation
        if (healthInsuranceEditText.getText().toString().isEmpty()) {
            healthInsuranceEditText.setError("This field is required");
            error_seen = 1;
        } else if (Float.parseFloat(healthInsuranceEditText.getText().toString()) < 0 || Float.parseFloat(healthInsuranceEditText.getText().toString()) > 1000) {
            healthInsuranceEditText.setError("Please enter a value between 0 and 1000");
            error_seen = 1;
        }

        // Employee Discount validation
        if (employeeDiscountEditText.getText().toString().isEmpty()) {
            employeeDiscountEditText.setError("This field is required");
            error_seen = 1;
        } else if (Float.parseFloat(employeeDiscountEditText.getText().toString()) > (Float.parseFloat(yearlySalaryEditText.getText().toString()) * 0.18)) {
            employeeDiscountEditText.setError("Please enter a value less than 18% of your yearly salary");
            error_seen = 1;
        }

        // Child Adoption validation
        if (childAdoptionEditText.getText().toString().isEmpty()) {
            childAdoptionEditText.setError("This field is required");
            error_seen = 1;
        } else if (Float.parseFloat(childAdoptionEditText.getText().toString()) < 0 || Float.parseFloat(childAdoptionEditText.getText().toString()) > 30000) {
            childAdoptionEditText.setError("Please enter a value between 0 and 30000");
            error_seen = 1;
        }

        return error_seen == 0;
    }

    private void saveJobOffer() {
        String city = cityEditText.getText().toString().trim();
        String state = stateEditText.getText().toString().trim();
        float costOfLiving = Float.parseFloat(costOfLivingEditText.getText().toString());

        Location location = new Location(city, state, costOfLiving);
        storage.saveLocationData(location);

        String jobId = UUID.randomUUID().toString(); // generates a unique job id
        Job job = new Job(
                jobId,  // Pass jobId here, no need to call setJobId() later
                titleEditText.getText().toString().trim(),
                companyEditText.getText().toString().trim(),
                location.getLocationKey(),
                Float.parseFloat(yearlySalaryEditText.getText().toString()),
                Float.parseFloat(yearlyBonusEditText.getText().toString()),
                Float.parseFloat(tuitionReimbursementEditText.getText().toString()),
                Float.parseFloat(healthInsuranceEditText.getText().toString()),
                Float.parseFloat(employeeDiscountEditText.getText().toString()),
                Float.parseFloat(childAdoptionEditText.getText().toString())
        );
        job.setAdjustedHealthInsurance();
        job.setJobId(jobId);

        Map<String, Job> jobOffers = storage.getJobOffersData();
        if (jobOffers == null) {
            jobOffers = new HashMap<>();
        }
        jobOffers.put(jobId, job);  // Use the jobId as the key

        storage.saveNewJobOffers(jobOffers);
        Toast.makeText(this, "Job offer saved.", Toast.LENGTH_SHORT).show();
    }
}
