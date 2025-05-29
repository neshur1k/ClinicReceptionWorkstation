package com.example.clinicreceptionworkstation.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.clinicreceptionworkstation.R;
import com.example.clinicreceptionworkstation.models.Appointment;
import com.example.clinicreceptionworkstation.models.Doctor;
import com.example.clinicreceptionworkstation.models.Patient;

import java.util.List;
import java.util.Map;

public class AppointmentAdapter extends RecyclerView.Adapter<AppointmentAdapter.ViewHolder> {
    private final List<Appointment> appointments;
    private static Map<Integer, Patient> patientMap;
    private static Map<Integer, Doctor> doctorMap;

    public AppointmentAdapter(List<Appointment> appointments,
                              Map<Integer, Patient>patientMap, Map<Integer, Doctor>doctorMap) {
        this.appointments = appointments;
        this.patientMap = patientMap;
        this.doctorMap = doctorMap;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
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

            assert patient != null;
            assert doctor != null;
            patientSpecializationTextView.setText(doctor.getSpecialization() + ", пациент " +
                    patient.getSurname() + " " + patient.getName());
            dateTimeTextView.setText(appointment.getDate() + " " + appointment.getTime());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_appointment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.bind(appointments.get(position));
    }

    @Override
    public int getItemCount() {
        return appointments.size();
    }
}