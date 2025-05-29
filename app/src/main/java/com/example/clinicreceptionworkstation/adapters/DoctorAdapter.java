package com.example.clinicreceptionworkstation.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.clinicreceptionworkstation.R;
import com.example.clinicreceptionworkstation.models.Doctor;

import java.util.List;

public class DoctorAdapter extends RecyclerView.Adapter<DoctorAdapter.ViewHolder> {
    private final List<Doctor> doctors;

    public DoctorAdapter(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView fioTextView;
        private final TextView specializationTextView;

        public ViewHolder(View view) {
            super(view);
            fioTextView = view.findViewById(R.id.doctor_fio);
            specializationTextView = view.findViewById(R.id.doctor_specialization);
        }

        public void bind(Doctor doctor) {
            fioTextView.setText(doctor.getSurname() + " " + doctor.getName() + " " + doctor.getPatronymic());
            specializationTextView.setText(doctor.getSpecialization());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_doctor, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.bind(doctors.get(position));
    }

    @Override
    public int getItemCount() {
        return doctors.size();
    }
}