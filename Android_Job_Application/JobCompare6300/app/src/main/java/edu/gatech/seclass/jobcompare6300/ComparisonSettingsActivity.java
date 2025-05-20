package edu.gatech.seclass.jobcompare6300;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.slider.Slider;

public class ComparisonSettingsActivity extends AppCompatActivity {
    private Slider yearlySalaryWeightID, yearlyBonusWeightID, tuitionReimbursementWeightID,
            healthInsuranceWeightID,employeeDiscountWeightID,childAdoptionWeightID;
    private FileStorage storage;
    private Button cancelButton, saveButton;
    private ComparisonSettings settings;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comparison_settings); //  layout file for comparison settings
        storage = new FileStorage(this);


        // here we are Initializing the various EditText fields that the user enters for their comparison settings details
        yearlySalaryWeightID = findViewById(R.id.yearlySalaryWeightID);
        yearlyBonusWeightID = findViewById(R.id.yearlyBonusWeightID);
        tuitionReimbursementWeightID = findViewById(R.id.tuitionReimbursementWeightID);
        healthInsuranceWeightID = findViewById(R.id.healthInsuranceWeightID);
        employeeDiscountWeightID = findViewById(R.id.employeeDiscountWeightID);
        childAdoptionWeightID = findViewById(R.id.childAdoptionWeightID);

        cancelButton = findViewById(R.id.cancelButtonComparisonID);
        saveButton = findViewById(R.id.saveButtonComparisonID);

        loadComparisonSettings(); // Load our comparison settings if they exist


        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                Toast.makeText(ComparisonSettingsActivity.this, "Any edits made were not saved.", Toast.LENGTH_SHORT).show();
                //childAdoptionEditText.setError("tester error");
                finish(); //this will close our current jobs activity and will not save the values to json
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick (View v) {
                //here we are saving the comparison settings using our filestorage class if there are no errros
                saveComparisonSettings();
                finish();
            }
        });

    }
    private void loadComparisonSettings(){
        ComparisonSettings settings = storage.getComparSettings();

        if (settings != null){
            yearlySalaryWeightID.setValue(settings.getYearlySalaryWeight());
            yearlyBonusWeightID.setValue(settings.getYearlyBonusWeight());
            tuitionReimbursementWeightID.setValue(settings.getTuitionReimbursementWeight());
            healthInsuranceWeightID.setValue(settings.getHealthInsuranceWeight());
            employeeDiscountWeightID.setValue(settings.getEmployeeDiscountWeight());
            childAdoptionWeightID.setValue(settings.getChildAdoptionWeight());
        }
        else {
            settings = new ComparisonSettings();
            Toast.makeText(this, "No comparison settings found. Please add comparison settings if available", Toast.LENGTH_SHORT).show();
        }


    }
    private void saveComparisonSettings(){
        if (settings == null){
            settings = new ComparisonSettings();
        }
        settings.setYearlySalaryWeight((int) yearlySalaryWeightID.getValue());
        settings.setYearlyBonusWeight((int) yearlyBonusWeightID.getValue());
        settings.setTuitionReimbursementWeight((int) tuitionReimbursementWeightID.getValue());
        settings.setHealthInsuranceWeight((int) healthInsuranceWeightID.getValue());
        settings.setEmployeeDiscountWeight((int) employeeDiscountWeightID.getValue());
        settings.setChildAdoptionWeight((int) childAdoptionWeightID.getValue());

        //save the comparison settings to json
        storage.saveComparSettings(settings);
        Toast.makeText(this, "Comparison settings saved.", Toast.LENGTH_SHORT).show();
    }



    }

