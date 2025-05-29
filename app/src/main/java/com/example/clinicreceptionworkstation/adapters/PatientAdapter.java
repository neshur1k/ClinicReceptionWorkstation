package com.example.clinicreceptionworkstation.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.clinicreceptionworkstation.R;
import com.example.clinicreceptionworkstation.models.Patient;

import java.util.List;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.ViewHolder> {
    private final List<Patient> patients;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView fioTextView;
        private final TextView recordTextView;
        private final TextView birthDateTextView;
        private final TextView genderTextView;
        private final TextView insuranceTextView;
        private final TextView phoneTextView;
        private final TextView registrationDateTimeTextView;

        public ViewHolder(View view) {
            super(view);
            fioTextView = view.findViewById(R.id.patient_fio);
            recordTextView = view.findViewById(R.id.patient_record);
            birthDateTextView = view.findViewById(R.id.patient_birth_date);
            genderTextView = view.findViewById(R.id.patient_gender);
            insuranceTextView = view.findViewById(R.id.patient_insurance);
            phoneTextView = view.findViewById(R.id.patient_phone);
            registrationDateTimeTextView = view.findViewById(R.id.patient_registration_date_time);
        }

        public void bind(Patient patient) {
            fioTextView.setText(patient.getSurname() + " " + patient.getName() + " " + patient.getPatronymic());
            recordTextView.setText(patient.getRecord());
            birthDateTextView.setText(patient.getBirthDate());
            genderTextView.setText(patient.getGender());
            insuranceTextView.setText(patient.getInsurance());
            phoneTextView.setText(patient.getPhone());
            registrationDateTimeTextView.setText(patient.getRegistrationDate() + " " + patient.getRegistrationTime());
        }
    }

    public PatientAdapter(List<Patient> patients) {
        this.patients = patients;
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