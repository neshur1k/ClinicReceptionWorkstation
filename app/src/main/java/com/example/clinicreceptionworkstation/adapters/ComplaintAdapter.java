package com.example.clinicreceptionworkstation.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.clinicreceptionworkstation.R;
import com.example.clinicreceptionworkstation.api.Complaint;
import com.example.clinicreceptionworkstation.models.Action;

import org.w3c.dom.Text;

import java.util.List;

public class ComplaintAdapter extends RecyclerView.Adapter<ComplaintAdapter.ViewHolder> {
    private List<Complaint> complaints;

    public ComplaintAdapter(List<Complaint> complaints) {
        this.complaints = complaints;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView symptomTextView;
        private final TextView specialistTextView;

        public ViewHolder(View view) {
            super(view);
            symptomTextView = itemView.findViewById(R.id.complaint_symptom);
            specialistTextView = itemView.findViewById(R.id.complaint_specialist);
        }

        public void bind(Complaint complaint) {
            symptomTextView.setText(complaint.getSymptom());
            specialistTextView.setText(complaint.getSpecialist());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_complaint, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(complaints.get(position));
    }

    @Override
    public int getItemCount() {
        return complaints.size();
    }
}