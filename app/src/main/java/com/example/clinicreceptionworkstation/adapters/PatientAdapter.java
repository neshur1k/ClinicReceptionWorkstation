package com.example.clinicreceptionworkstation.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.clinicreceptionworkstation.R;
import com.example.clinicreceptionworkstation.fragments.DoctorInfoFragment;
import com.example.clinicreceptionworkstation.fragments.PatientInfoFragment;
import com.example.clinicreceptionworkstation.models.Patient;

import java.util.List;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.ViewHolder> {
    private final List<Patient> patients;
    private static FragmentActivity activity = null;

    public PatientAdapter(FragmentActivity activity, List<Patient> patients) {
        PatientAdapter.activity = activity;
        this.patients = patients;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView fioTextView;
        private final TextView recordTextView;

        public ViewHolder(View view) {
            super(view);
            fioTextView = view.findViewById(R.id.patient_fio);
            recordTextView = view.findViewById(R.id.patient_record);
        }

        public void bind(Patient patient) {
            fioTextView.setText(patient.getSurname() + " " + patient.getName() + " " + patient.getPatronymic());
            recordTextView.setText("Номер медкарты: " + patient.getRecord());
            itemView.setOnClickListener(v -> {
                Bundle bundle = new Bundle();
                bundle.putInt("patient_id", patient.getId());

                PatientInfoFragment fragment = new PatientInfoFragment();
                fragment.setArguments(bundle);

                activity.getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, fragment)
                        .addToBackStack(null)
                        .commit();
            });
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_patient, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.bind(patients.get(position));
    }

    @Override
    public int getItemCount() {
        return patients.size();
    }
}