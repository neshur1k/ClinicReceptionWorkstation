package com.example.clinicreceptionworkstation.adapters;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.clinicreceptionworkstation.R;
import com.example.clinicreceptionworkstation.fragments.AppointmentInfoFragment;
import com.example.clinicreceptionworkstation.models.Appointment;
import com.example.clinicreceptionworkstation.models.Doctor;
import com.example.clinicreceptionworkstation.models.Patient;

import java.util.List;
import java.util.Map;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {
    private final List<Appointment> appointments;
    private final Map<Integer, Patient> patientMap;
    private final Map<Integer, Doctor> doctorMap;
    private final FragmentActivity activity;

    public AppointmentAdapter(FragmentActivity activity, List<Appointment> appointments,
                              Map<Integer, Patient> patientMap, Map<Integer, Doctor> doctorMap) {
        this.activity = activity;
        this.appointments = appointments;
        this.patientMap = patientMap;
        this.doctorMap = doctorMap;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView patientSpecializationTextView;
        private final TextView dateTimeTextView;

        public ViewHolder(View view) {
            super(view);
            patientSpecializationTextView = view.findViewById(R.id.patient_specialization);
            dateTimeTextView = view.findViewById(R.id.date_time);
        }

        public void bind(Appointment appointment) {
            Patient patient = patientMap.get(appointment.getPatientId());
            Doctor doctor = doctorMap.get(appointment.getDoctorId());

            if (patient != null && doctor != null) {
                patientSpecializationTextView.setText(doctor.getSpecialization() + ", пациент " +
                        patient.getSurname() + " " + patient.getName());
                dateTimeTextView.setText(appointment.getDate() + " " + appointment.getTime());

                itemView.setOnClickListener(v -> {
                    Bundle bundle = new Bundle();
                    bundle.putInt("appointment_id", appointment.getId());

                    AppointmentInfoFragment fragment = new AppointmentInfoFragment();
                    fragment.setArguments(bundle);

                    activity.getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .addToBackStack(null)
                            .commit();
                });
            }
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_appointment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(appointments.get(position));
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }
}