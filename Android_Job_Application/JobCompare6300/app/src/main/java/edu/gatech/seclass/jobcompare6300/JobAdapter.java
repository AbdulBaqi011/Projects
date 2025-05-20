package edu.gatech.seclass.jobcompare6300;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class JobAdapter extends RecyclerView.Adapter<JobAdapter.JobViewHolder> {
    private List<Job> jobList;
    private OnJobActionListener jobActionListener;

    public JobAdapter(List<Job> jobList, OnJobActionListener jobActionListener) {
        this.jobList = jobList;
        this.jobActionListener = jobActionListener;
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.job_list_item, parent, false);
        return new JobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        if (jobList.isEmpty()) {
            holder.titleTextView.setText("No job offers yet. Add one to get started.");
        } else {
            Job job = jobList.get(position);
            holder.titleTextView.setText(job.getTitle());
            holder.companyTextView.setText(job.getCompany());

            // Handle Edit button click
            holder.editButton.setOnClickListener(v -> {
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) {
                    jobActionListener.onEditJob(jobList.get(currentPosition));
                }
            });

            // Handle Delete button click
            holder.deleteButton.setOnClickListener(v -> {
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) {
                    Job currentJob = jobList.get(currentPosition);
                    jobActionListener.onDeleteJob(currentJob);  // Notify deletion
                    jobList.remove(currentPosition);  // Remove from the list
                    notifyItemRemoved(currentPosition);  // Notify adapter
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    public static class JobViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, companyTextView;
        Button editButton, deleteButton;

        public JobViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.jobTitle);
            companyTextView = itemView.findViewById(R.id.jobCompany);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }

    // Interface for handling button clicks
    public interface OnJobActionListener {
        void onEditJob(Job job);
        void onDeleteJob(Job job);
    }
}
