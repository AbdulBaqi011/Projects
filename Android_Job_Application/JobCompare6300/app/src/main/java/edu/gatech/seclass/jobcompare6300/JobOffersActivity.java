package edu.gatech.seclass.jobcompare6300;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JobOffersActivity extends AppCompatActivity implements JobAdapter.OnJobActionListener {

    private FileStorage storage;
    private Button mainMenuButton, addJobOfferButton;
    private RecyclerView recyclerView;
    private JobAdapter jobAdapter;
    private List<Job> jobList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.job_offer_details); // Layout file for job offers

        storage = new FileStorage(this);
        recyclerView = findViewById(R.id.jobOffersListID);
        mainMenuButton = findViewById(R.id.MainMenuJobOfferID);
        addJobOfferButton = findViewById(R.id.addJobOfferButtonID);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        loadJobOffers();

        mainMenuButton.setOnClickListener(v -> finish());

        addJobOfferButton.setOnClickListener(v -> {
            Intent intent = new Intent(JobOffersActivity.this, JobOfferAddActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadJobOffers();
    }

    private void loadJobOffers() {
        Map<String, Job> jobMap = storage.getJobOffersData(); // Retrieve saved job offers from storage
        List<Job> jobList = new ArrayList<>(jobMap.values()); // Convert Map values to List

        // Check if jobList is empty before setting the adapter
        if (jobList.isEmpty()) {
            Toast.makeText(JobOffersActivity.this, "No Job offers Found. Add a job offer to get started.", Toast.LENGTH_SHORT).show();
        }

        jobAdapter = new JobAdapter(jobList, this); // Pass the activity as the OnJobActionListener
        recyclerView.setAdapter(jobAdapter); // Set the adapter to RecyclerView
    }

    @Override
    public void onEditJob(Job job) {
        Intent intent = new Intent(this, JobEditActivity.class);
        intent.putExtra("jobId", job.getJobId()); // Pass job ID for editing
        Log.d("JobDelete", "Attempting to start edit job with ID: " + job + job.getJobId());

        startActivity(intent);
    }

    @Override
    public void onDeleteJob(Job job) {
        Map<String, Job> jobMap = storage.getJobOffersData();
        Log.d("JobDelete", "Attempting to delete job with ID: " + job + job.getJobId());

        jobMap.remove(job.getJobId()); // Remove the job by its ID

        // Save the updated job list back to storage
        storage.saveNewJobOffers(jobMap); // Save the updated map

        // Refresh the list after a brief delay or in the next event loop cycle
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                loadJobOffers(); // Refresh the list
            }
        });
    }

}

