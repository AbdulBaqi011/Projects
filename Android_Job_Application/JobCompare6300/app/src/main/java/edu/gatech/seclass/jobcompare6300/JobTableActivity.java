package edu.gatech.seclass.jobcompare6300;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JobTableActivity extends AppCompatActivity implements JobCardAdapter.OnSelectionChangedListener {

    private JobCardAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_table);

        RecyclerView recyclerView = findViewById(R.id.job_list_recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FileStorage storage = new FileStorage(this);
        Map<String, Job> jobOffers = storage.getJobOffersData();
        Job currentJob = storage.getCurrJob();
        List<Job> jobList = new ArrayList<>();
        if(currentJob != null) {
            jobList.add(currentJob);
        }
        jobList.addAll(jobOffers.values());

        ComparisonSettings settings = storage.getComparSettings();
        for (Job job : jobList) {
            float costOfLiving = storage.getCostOfLivingForJob(job);
            job.calculateJobScore(settings, costOfLiving);
        }

        // sort jobs by jobScore (descending order)
        jobList.sort(new Comparator<Job>() {
            @Override
            public int compare(Job job1, Job job2) {
                return Float.compare(job2.getJobScore(), job1.getJobScore());
            }
        });

        adapter = new JobCardAdapter(jobList);
        adapter.setOnSelectionChangedListener(this);
        recyclerView.setAdapter(adapter);

        Button compareButton = findViewById(R.id.compareButton);
        compareButton.setOnClickListener(v -> {
            Set<Job> selectedJobs = adapter.getSelectedJobs();
            if (selectedJobs.size() == 2) {
                navigateToComparison(selectedJobs);
            }
        });
        updateCompareButtonState();
    }

    @Override
    public void onSelectionChanged() {
        updateCompareButtonState();
    }

    private void updateCompareButtonState() {
        Button compareButton = findViewById(R.id.compareButton);
        if(adapter != null){
            compareButton.setEnabled(adapter.getSelectedJobs().size() == 2);
        }
    }
    private void navigateToComparison(Set<Job> selectedJobs) {
        Intent intent = new Intent(JobTableActivity.this, JobComparisonActivity.class);
        ArrayList<Job> selectedJobsList = new ArrayList<>(selectedJobs);
        intent.putExtra("selectedJobs", selectedJobsList);
        startActivity(intent);
    }
}