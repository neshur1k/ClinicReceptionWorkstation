package com.example.clinicreceptionworkstation.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clinicreceptionworkstation.R;
import com.example.clinicreceptionworkstation.db.DatabaseHelper;
import com.example.clinicreceptionworkstation.models.Appointment;
import com.example.clinicreceptionworkstation.models.Doctor;
import com.example.clinicreceptionworkstation.models.Patient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AppointmentInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppointmentInfoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Appointment appointment;

    public AppointmentInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AppointmentInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AppointmentInfoFragment newInstance(String param1, String param2) {
        AppointmentInfoFragment fragment = new AppointmentInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        assert getArguments() != null;
        int appointmentId = getArguments().getInt("appointment_id", -1);
        DatabaseHelper dbHelper = new DatabaseHelper(requireContext());
        appointment = dbHelper.findAppointment(appointmentId);
        return inflater.inflate(R.layout.fragment_appointment_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DatabaseHelper dbHelper = new DatabaseHelper(requireContext());
        List<Appointment> appointments = dbHelper.getAllAppointments();

        TextView patientFioTextView = view.findViewById(R.id.patientFioTextView);
        TextView recordTextView = view.findViewById(R.id.recordTextView);
        TextView insuranceTextView = view.findViewById(R.id.insuranceTextView);
        TextView patientPhoneTextView = view.findViewById(R.id.patientPhoneTextView);
        TextView doctorFioTextView = view.findViewById(R.id.doctorFioTextView);
        TextView specializationTextView = view.findViewById(R.id.specializationTextView);
        TextView doctorPhoneTextView = view.findViewById(R.id.doctorPhoneTextView);
        TextView dateTimeTextView = view.findViewById(R.id.dateTimeTextView);
        TextView officeTextView = view.findViewById(R.id.officeTextView);
        TextView schedulingDateTimeTextView = view.findViewById(R.id.schedulingDateTimeTextView);
        TextView notesTextView = view.findViewById(R.id.notesTextView);
        ImageButton backButton = view.findViewById(R.id.backButton);
        Button deleteButton = view.findViewById(R.id.deleteAppointmentButton);
        TextView sectionTextView = requireActivity().findViewById(R.id.sectionTextView);
        sectionTextView.setText("Запись");

        Patient patient = dbHelper.findPatient(appointment.getPatientId());
        Doctor doctor = dbHelper.findDoctor(appointment.getDoctorId());

        patientFioTextView.setText("Пациент: " + patient.getSurname() + " " + patient.getName() + " " + patient.getPatronymic());
        recordTextView.setText("Номер медкарты: " + patient.getRecord());
        insuranceTextView.setText("СНИЛС: " + patient.getInsurance());
        patientPhoneTextView.setText(patient.getPhone());
        doctorFioTextView.setText("Врач: " + doctor.getSurname() + " " + doctor.getName() + " " + doctor.getPatronymic());
        specializationTextView.setText(doctor.getSpecialization());
        doctorPhoneTextView.setText(doctor.getPhone());
        dateTimeTextView.setText(appointment.getDate() + " " + appointment.getTime());
        officeTextView.setText("Кабинет: " + doctor.getOffice());
        schedulingDateTimeTextView.setText("Запись создана " + appointment.getSchedulingDate() + " " + appointment.getSchedulingTime());
        notesTextView.setText(appointment.getNotes());

        backButton.setOnClickListener(v -> {
            OverviewFragment fragment = new OverviewFragment();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        deleteButton.setOnClickListener(v -> {
            int id = appointment.getId();

            Date currentDate = new Date();
            Locale russianLocale = new Locale("ru", "RU");
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", russianLocale);
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", russianLocale);
            String deletingDate = dateFormat.format(currentDate);
            String deletingTime = timeFormat.format(currentDate);

            if (dbHelper.deleteAppointment(id)) {
                int position = -1;
                for (int i = 0; i < appointments.size(); i++) {
                    if (appointments.get(i).getId() == id)
                    {
                        position = i;
                        appointments.remove(i);
                        break;
                    }
                }
                if (position != -1) {
                    Toast.makeText(requireContext(), "Запись удалена", Toast.LENGTH_SHORT).show();
                    OverviewFragment fragment = new OverviewFragment();
                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .addToBackStack(null)
                            .commit();
                    dbHelper.addAction("Запись удалена: пациент " + patient.getSurname() + " " +
                            patient.getName() + " " + patient.getPatronymic() + " (медкарта " +
                            patient.getRecord() + "), врач " + doctor.getSurname() + " " +
                            doctor.getName() + " " + doctor.getPatronymic() + " (" +
                            doctor.getSpecialization() + "), дата " + appointment.getDate() +
                            ", время " + appointment.getTime(), deletingDate, deletingTime);
                } else {
                    Toast.makeText(requireContext(), "Ошибка при удалении записи", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(requireContext(), "Ошибка при удалении записи", Toast.LENGTH_SHORT).show();
            }
        });
    }
}