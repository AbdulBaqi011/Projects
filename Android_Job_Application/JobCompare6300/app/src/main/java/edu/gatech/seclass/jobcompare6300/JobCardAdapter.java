package edu.gatech.seclass.jobcompare6300;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.card.MaterialCardView;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class JobCardAdapter extends RecyclerView.Adapter<JobCardAdapter.JobViewHolder> {

    private List<Job> jobList;
    private Set<Job> selectedJobs = new HashSet<>();
    private OnSelectionChangedListener listener;

    public interface OnSelectionChangedListener {
        void onSelectionChanged();
    }

    public JobCardAdapter(List<Job> jobList) {
        this.jobList = jobList;
    }

    public void setOnSelectionChangedListener(OnSelectionChangedListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.job_card_item, parent, false);
        return new JobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        Job job = jobList.get(position);
        holder.jobTitleTextView.setText(job.getTitle());
        holder.jobCompanyTextView.setText(job.getCompany());
        holder.jobScoreTextView.setText("Job Score: " + String.format("%.2f", job.getJobScore()));

        int selectionColor = Color.argb(255, 150, 100, 200);
        if (selectedJobs.contains(job)) {
            holder.cardView.setStrokeColor(selectionColor);
            holder.cardView.setStrokeWidth(4);
        } else {
            holder.cardView.setStrokeWidth(0);
        }

        holder.cardView.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                Job j = jobList.get(pos);
                if (selectedJobs.contains(j)) {
                    selectedJobs.remove(j);
                } else {
                    if (selectedJobs.size() < 2) {
                        selectedJobs.add(j);
                    } else {
                        // case >2 jobs are selected
                    }
                }
                notifyItemChanged(pos);
                if (listener != null) {
                    listener.onSelectionChanged();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    public Set<Job> getSelectedJobs() {
        return selectedJobs;
    }

    public static class JobViewHolder extends RecyclerView.ViewHolder {
        TextView jobTitleTextView;
        TextView jobCompanyTextView;
        TextView jobScoreTextView;
        MaterialCardView cardView;

        public JobViewHolder(@NonNull View itemView) {
            super(itemView);
            jobTitleTextView = itemView.findViewById(R.id.jobTitleTextView);
            jobCompanyTextView = itemView.findViewById(R.id.jobCompanyTextView);
            jobScoreTextView = itemView.findViewById(R.id.jobScoreTextView);
            cardView = itemView.findViewById(R.id.jobCardView);
        }
    }
}