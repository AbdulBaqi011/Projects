package edu.gatech.seclass.jobcompare6300;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CurrentJobDetailsActivity extends AppCompatActivity {

    private EditText titleEditText, companyEditText, stateEditText, cityEditText, costOfLivingEditText,
            yearlySalaryEditText, yearlyBonusEditText, tuitionReimbursementEditText,
            healthInsuranceEditText, employeeDiscountEditText, childAdoptionEditText;
    private FileStorage storage;

    private Button cancelButton, saveButton;
    private String existingJobID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.currentjobdetails); //  layout file for current job details
        storage = new FileStorage(this);

        // here we are Initializing the various EditText fields that the user enters for their current job details

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

        loadCurrentJobInfo();

        existingJobID = getIntent().getStringExtra("JOB_ID"); // Passed from job selection
        if (existingJobID != null) {
            loadCurrentJobInfo();
        }

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                Toast.makeText(CurrentJobDetailsActivity.this, "Any edits made were not saved.", Toast.LENGTH_SHORT).show();
                //childAdoptionEditText.setError("tester error");
                finish(); //this will close our current jobs activity and will not save the values to json
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v){
            int error_seen = 0;

            titleEditText.setError(null);
            if (titleEditText.getText().toString().isEmpty() == true){
                titleEditText.setError("This field is required");
                error_seen = 1;
            }
            companyEditText.setError(null);
            if (companyEditText.getText().toString().isEmpty() == true){
                companyEditText.setError("This field is required");
                error_seen = 1;
            }
            stateEditText.setError(null);
            if (stateEditText.getText().toString().isEmpty() == true){
                stateEditText.setError("This field is required");
                error_seen = 1;
            }
            cityEditText.setError(null);
            if (cityEditText.getText().toString().isEmpty() == true){
                cityEditText.setError("This field is required");
                error_seen = 1;
            }
            costOfLivingEditText.setError(null);
            if (costOfLivingEditText.getText().toString().isEmpty() == true){
                costOfLivingEditText.setError("This field is required");
                error_seen = 1;
            }
            yearlySalaryEditText.setError(null);
            if (yearlySalaryEditText.getText().toString().isEmpty() == true){
                yearlySalaryEditText.setError("This field is required");
                error_seen = 1;
            }
            yearlyBonusEditText.setError(null);
            if (yearlyBonusEditText.getText().toString().isEmpty() == true){
                yearlyBonusEditText.setError("This field is required");
                error_seen = 1;
            }
            tuitionReimbursementEditText.setError(null);
            if (tuitionReimbursementEditText.getText().toString().isEmpty() == true){
                tuitionReimbursementEditText.setError("This field is required");
                error_seen = 1;
            }
            else if (Float.parseFloat(tuitionReimbursementEditText.getText().toString()) < 0 ||Float.parseFloat(tuitionReimbursementEditText.getText().toString())> 15000) {
                tuitionReimbursementEditText.setError("Please enter a value between 0 and 15000");
                error_seen = 1;
            }
                healthInsuranceEditText.setError(null);
            if (healthInsuranceEditText.getText().toString().isEmpty() == true){
                healthInsuranceEditText.setError("This field is required");
                error_seen = 1;
            }
            else if (Float.parseFloat(healthInsuranceEditText.getText().toString()) < 0 ||Float.parseFloat(healthInsuranceEditText.getText().toString())> 1000){
                healthInsuranceEditText.setError("Please enter a value between 0 and 1000");
                error_seen = 1;
            }
            employeeDiscountEditText.setError(null);
            if (employeeDiscountEditText.getText().toString().isEmpty() == true){
                employeeDiscountEditText.setError("This field is required");
                error_seen = 1;
            }
            else if (Float.parseFloat(employeeDiscountEditText.getText().toString()) > (Float.parseFloat(yearlySalaryEditText.getText().toString()) *0.18)){
                employeeDiscountEditText.setError("Please enter a value less than 18% of your yearly salary");
                error_seen = 1;
            }
            childAdoptionEditText.setError(null);
            if (childAdoptionEditText.getText().toString().isEmpty() == true){
                childAdoptionEditText.setError("This field is required");
                error_seen = 1;
            }
            else if (Float.parseFloat(childAdoptionEditText.getText().toString()) < 0 ||Float.parseFloat(childAdoptionEditText.getText().toString())> 30000){
                childAdoptionEditText.setError("Please enter a value between 0 and 30000");
                error_seen = 1;
                }

            if (error_seen == 0) {
                    saveCurrentJobInfo();
                    finish();
                }
            else{
                Toast.makeText(CurrentJobDetailsActivity.this, "Fix the errors before saving.", Toast.LENGTH_SHORT).show();

            }
            }
        });
    }

    private void loadCurrentJobInfo(){
        Job job = storage.getCurrJob();

        if (job!= null){
            String locationKey = job.getLocationKey();
            float costOfLivingForLocation = storage.getCostOfLivingForLocation(locationKey);
            String[] cityState = Location.getCityStateFromKey(locationKey);
            Location location = new Location(cityState[0], cityState[1], costOfLivingForLocation);

            titleEditText.setText(job.getTitle());
            companyEditText.setText(job.getCompany());
            stateEditText.setText(location.getState());
            cityEditText.setText(location.getCity());
            costOfLivingEditText.setText(String.valueOf(location.getCostOfLiving()));
            yearlySalaryEditText.setText(String.valueOf(job.getYearlySalary()));
            yearlyBonusEditText.setText(String.valueOf(job.getYearlyBonus()));
            tuitionReimbursementEditText.setText(String.valueOf(job.getTuitionReimbursement()));
            healthInsuranceEditText.setText(String.valueOf(job.getHealthInsurance()));
            employeeDiscountEditText.setText(String.valueOf(job.getEmployeeDiscount()));
            childAdoptionEditText.setText(String.valueOf(job.getChildAdoptionAssistance()));


        }
        else{
            Toast.makeText(this, "No current job found. Please add current job details if available", Toast.LENGTH_SHORT).show();
        }
    }
    private void saveCurrentJobInfo(){
        String city = cityEditText.getText().toString().trim();
        String state = stateEditText.getText().toString().trim();
        float costOfLiving = Float.parseFloat(costOfLivingEditText.getText().toString());
        Location location = new Location(city, state, costOfLiving);
        location.setCostOfLiving(costOfLiving);
        //Float adjustedHealthInsurance = Float.parseFloat(healthInsuranceEditText.getText().toString()) + (0.02f * Float.parseFloat(yearlySalaryEditText.getText().toString()));

        Job job = new Job(
                existingJobID,
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
        //here i am setting the adjusted health insurance value. this is the value that will be used for the calculations later
        //but will not be shown to the user as the user will only enter in the base value from 0-1000
        job.setAdjustedHealthInsurance();

        // here we are saving the job using our filestorage class
        storage.saveCurrJob(job);
        Toast.makeText(this, "Job details saved.", Toast.LENGTH_SHORT).show();
    }
}
