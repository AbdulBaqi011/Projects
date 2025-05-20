package edu.gatech.seclass.jobcompare6300;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;

public class JobComparisonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_comparison);

        Intent intent = getIntent();
        ArrayList<Job> selectedJobs = (ArrayList<Job>) intent.getSerializableExtra("selectedJobs");

        if (selectedJobs != null && selectedJobs.size() == 2) {
            Job job1 = selectedJobs.get(0);
            Job job2 = selectedJobs.get(1);

            populateTable(job1, job2);
        }

        Button compareAgainButton = findViewById(R.id.compareAgainButton);
        Button mainMenuButton = findViewById(R.id.mainMenuButton);

        compareAgainButton.setOnClickListener(v -> finish());
        mainMenuButton.setOnClickListener(v -> {
            Intent mainIntent = new Intent(JobComparisonActivity.this, MainActivity.class);
            startActivity(mainIntent);
        });
    }

    private void populateTable(Job job1, Job job2) {
        TextView job1TitleHeader = findViewById(R.id.job1TitleHeader);
        TextView job2TitleHeader = findViewById(R.id.job2TitleHeader);

        job1TitleHeader.setText(job1.getTitle());
        job2TitleHeader.setText(job2.getTitle());

        TextView job1Title = findViewById(R.id.job1Title);
        TextView job1Company = findViewById(R.id.job1Company);
        TextView job1Location = findViewById(R.id.job1Location);
        TextView job1AdjSalary = findViewById(R.id.job1AdjSalary);
        TextView job1AdjBonus = findViewById(R.id.job1AdjBonus);
        TextView job1TR = findViewById(R.id.job1TR);
        TextView job1HI = findViewById(R.id.job1HI);
        TextView job1EPSD = findViewById(R.id.job1EPSD);
        TextView job1CAA = findViewById(R.id.job1CAA);
        TextView job1JS = findViewById(R.id.job1JS);

        TextView job2Title = findViewById(R.id.job2Title);
        TextView job2Company = findViewById(R.id.job2Company);
        TextView job2Location = findViewById(R.id.job2Location);
        TextView job2AdjSalary = findViewById(R.id.job2AdjSalary);
        TextView job2AdjBonus = findViewById(R.id.job2AdjBonus);
        TextView job2TR = findViewById(R.id.job2TR);
        TextView job2HI = findViewById(R.id.job2HI);
        TextView job2EPSD = findViewById(R.id.job2EPSD);
        TextView job2CAA = findViewById(R.id.job2CAA);
        TextView job2JS = findViewById(R.id.job2JS);

        FileStorage storage = new FileStorage(this);
        ComparisonSettings settings = storage.getComparSettings();
        float costOfLiving1 = storage.getCostOfLivingForLocation(job1.getLocationKey());
        float costOfLiving2 = storage.getCostOfLivingForLocation(job2.getLocationKey());
        job1.calculateJobScore(settings, costOfLiving1);
        job2.calculateJobScore(settings, costOfLiving2);

        job1Title.setText(job1.getTitle());
        job1Company.setText(job1.getCompany());
        job1Location.setText(job1.getLocationKey());
        job1AdjSalary.setText(String.format("%.2f", job1.getYearlySalary() / costOfLiving1 * 100));
        job1AdjBonus.setText(String.format("%.2f", job1.getYearlyBonus() / costOfLiving1 * 100));
        job1TR.setText(String.valueOf(job1.getTuitionReimbursement()));
        job1HI.setText(String.valueOf(job1.getHealthInsurance()));
        job1EPSD.setText(String.valueOf(job1.getEmployeeDiscount()));
        job1CAA.setText(String.valueOf(job1.getChildAdoptionAssistance()));

        job1JS.setText(String.format("%.1f", job1.getJobScore()));

        job2Title.setText(job2.getTitle());
        job2Company.setText(job2.getCompany());
        job2Location.setText(job2.getLocationKey());
        job2AdjSalary.setText(String.format("%.2f", job2.getYearlySalary() / costOfLiving2 * 100));
        job2AdjBonus.setText(String.format("%.2f", job2.getYearlyBonus() / costOfLiving2 * 100));
        job2TR.setText(String.valueOf(job2.getTuitionReimbursement()));
        job2HI.setText(String.valueOf(job2.getHealthInsurance()));
        job2EPSD.setText(String.valueOf(job2.getEmployeeDiscount()));
        job2CAA.setText(String.valueOf(job2.getChildAdoptionAssistance()));
        job2JS.setText(String.format("%.1f", job2.getJobScore()));
    }
}