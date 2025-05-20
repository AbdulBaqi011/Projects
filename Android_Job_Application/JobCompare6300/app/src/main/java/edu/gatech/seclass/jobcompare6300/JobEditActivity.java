package edu.gatech.seclass.jobcompare6300;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class JobEditActivity extends AppCompatActivity {

    private EditText titleEditText, companyEditText, stateEditText, cityEditText, costOfLivingEditText,
            yearlySalaryEditText, yearlyBonusEditText, tuitionReimbursementEditText,
            healthInsuranceEditText, employeeDiscountEditText, childAdoptionEditText;
    private FileStorage storage;
    private Button cancelButton, saveButton;
    private String existingJobID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_edit);
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

        existingJobID = getIntent().getStringExtra("jobId"); // Passed from job selection
        if (existingJobID == null) {
            try {
                throw new Exception("JobID is null");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        this.loadExistingJob();

        cancelButton.setOnClickListener(v -> {
            Toast.makeText(JobEditActivity.this, "Any edits made were not saved.", Toast.LENGTH_SHORT).show();
            finish();
        });

        saveButton.setOnClickListener(v -> {
            if (validateInputs()) {
                saveNewJob();
                finish();
            } else {
                Toast.makeText(JobEditActivity.this, "Fix the errors before saving.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadExistingJob() {
        Job job = storage.getJobOffer(this.existingJobID);
        if (job == null) {
            try {
                throw new Exception("JobID is null");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        String[] cityStateFromKey = Location.getCityStateFromKey(job.getLocationKey());
        float costOfLiving = storage.getCostOfLivingForJob(job);
        titleEditText.setText(job.getTitle());
        companyEditText.setText(job.getCompany());
        cityEditText.setText(cityStateFromKey[0]);
        stateEditText.setText(cityStateFromKey[1]);
        costOfLivingEditText.setText(String.valueOf(costOfLiving));
        yearlySalaryEditText.setText(String.valueOf(job.getYearlySalary()));
        yearlyBonusEditText.setText(String.valueOf(job.getYearlyBonus()));
        tuitionReimbursementEditText.setText(String.valueOf(job.getTuitionReimbursement()));
        healthInsuranceEditText.setText(String.valueOf(job.getHealthInsurance()));
        employeeDiscountEditText.setText(String.valueOf(job.getEmployeeDiscount()));
        childAdoptionEditText.setText(String.valueOf(job.getChildAdoptionAssistance()));
    }

    private void saveNewJob() {
        Location location = new Location(
                cityEditText.getText().toString().trim(),
                stateEditText.getText().toString().trim(),
                Float.parseFloat(costOfLivingEditText.getText().toString())
        );

        Job job = new Job(
                existingJobID, // Set existing job ID if updating an existing job
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

        storage.saveJobOffer(existingJobID,job);
        Toast.makeText(this, "Job details saved.", Toast.LENGTH_SHORT).show();
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

        if (error_seen == 0) {
            return true;
        } else {
            return false;
        }
    }
}
