package com.example.clinicreceptionworkstation.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
 * Use the {@link AddAppointmentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddAppointmentFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private int selectedPatientId;
    private int selectedDoctorId;

    public AddAppointmentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddAppointmentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddAppointmentFragment newInstance(String param1, String param2) {
        AddAppointmentFragment fragment = new AddAppointmentFragment();
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_appointment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText dateEditText = view.findViewById(R.id.dateEditText);
        EditText timeEditText = view.findViewById(R.id.timeEditText);
        EditText notesEditText = view.findViewById(R.id.notesEditText);
        Spinner patientSpinner = view.findViewById(R.id.patientSpinner);
        Spinner doctorSpinner = view.findViewById(R.id.doctorSpinner);
        Button saveButton = view.findViewById(R.id.cancelButton);

        DatabaseHelper dbHelper = new DatabaseHelper(requireContext());

        List<Patient> patientList = dbHelper.getAllPatients();
        ArrayAdapter<Patient> patientAdapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, patientList);
        patientAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        patientSpinner.setAdapter(patientAdapter);

        patientSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Patient selectedPatient = (Patient) parent.getItemAtPosition(position);
                if (selectedPatient.getId() != 0) {
                    selectedPatientId = selectedPatient.getId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        List<Doctor> doctorList = dbHelper.getAllDoctors();
        ArrayAdapter<Doctor> doctorAdapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_spinner_item, doctorList);
        doctorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        doctorSpinner.setAdapter(doctorAdapter);

        doctorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Doctor selectedDoctor = (Doctor) parent.getItemAtPosition(position);
                if (selectedDoctor.getId() != 0) {
                    selectedDoctorId = selectedDoctor.getId();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        saveButton.setOnClickListener(v -> {
            String date = dateEditText.getText().toString();
            String time = timeEditText.getText().toString();
            String notes = notesEditText.getText().toString();

            Date currentDate = new Date();
            Locale russianLocale = new Locale("ru", "RU");
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", russianLocale);
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", russianLocale);
            String schedulingDate = dateFormat.format(currentDate);
            String schedulingTime = timeFormat.format(currentDate);

            if (dbHelper.addAppointment(new Appointment(0, selectedPatientId, selectedDoctorId,
                    date, time, notes, schedulingDate, schedulingTime)))
            {
                Patient patient = dbHelper.findPatient(selectedPatientId);
                Doctor doctor = dbHelper.findDoctor(selectedDoctorId);
                dbHelper.addAction("Запись создана: пациент " + patient.getSurname() + " " +
                        patient.getName() + " " + patient.getPatronymic() + " (медкарта " +
                        patient.getRecord() + "), врач " + doctor.getSurname() + " " +
                        doctor.getName() + " " + doctor.getPatronymic() + " (" +
                        doctor.getSpecialization() + "), дата " + date + ", время " + time,
                        schedulingDate, schedulingTime);
                Toast.makeText(requireContext(), "Запись создана", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), "Ошибка при создании записи", Toast.LENGTH_SHORT).show();
            }
        });
    }
}