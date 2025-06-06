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
import com.example.clinicreceptionworkstation.models.Patient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PatientInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PatientInfoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Patient patient;

    public PatientInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PatientInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PatientInfoFragment newInstance(String param1, String param2) {
        PatientInfoFragment fragment = new PatientInfoFragment();
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
        int patientId = getArguments().getInt("patient_id", -1);
        DatabaseHelper dbHelper = new DatabaseHelper(requireContext());
        patient = dbHelper.findPatient(patientId);
        return inflater.inflate(R.layout.fragment_patient_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DatabaseHelper dbHelper = new DatabaseHelper(requireContext());
        List<Patient> patients = dbHelper.getAllPatients();

        TextView fioTextView = view.findViewById(R.id.fioTextView);
        TextView recordTextView = view.findViewById(R.id.recordTextView);
        TextView insuranceTextView = view.findViewById(R.id.insuranceTextView);
        TextView phoneTextView = view.findViewById(R.id.phoneTextView);
        TextView genderTextView = view.findViewById(R.id.genderTextView);
        TextView birthDateTextView = view.findViewById(R.id.birthDateTextView);
        TextView registrationDateTimeTextView = view.findViewById(R.id.registrationDateTimeTextView);
        ImageButton backButton = view.findViewById(R.id.backButton);
        Button deleteButton = view.findViewById(R.id.deletePatientButton);
        TextView sectionTextView = requireActivity().findViewById(R.id.sectionTextView);
        sectionTextView.setText("Пациент");

        fioTextView.setText(patient.getSurname() + " " + patient.getName() + " " + patient.getPatronymic());
        recordTextView.setText("Номер медкарты: " + patient.getRecord());
        insuranceTextView.setText("СНИЛС: " + patient.getInsurance());
        phoneTextView.setText(patient.getPhone());
        genderTextView.setText("Пол: " + patient.getGender());
        birthDateTextView.setText("Дата рождения: " + patient.getBirthDate());
        registrationDateTimeTextView.setText("Зарегистрирован " + patient.getRegistrationDate() + " " + patient.getRegistrationTime());

        backButton.setOnClickListener(v -> {
            OverviewFragment fragment = new OverviewFragment();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        });

        deleteButton.setOnClickListener(v -> {
            int id = patient.getId();

            Date currentDate = new Date();
            Locale russianLocale = new Locale("ru", "RU");
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", russianLocale);
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", russianLocale);
            String deletingDate = dateFormat.format(currentDate);
            String deletingTime = timeFormat.format(currentDate);

            if (dbHelper.deletePatient(id)) {
                int position = -1;
                for (int i = 0; i < patients.size(); i++) {
                    if (patients.get(i).getId() == id)
                    {
                        position = i;
                        patients.remove(i);
                        break;
                    }
                }
                if (position != -1) {
                    dbHelper.deleteAppointmentsByPatientId(id);
                    Toast.makeText(requireContext(), "Пациент удалён", Toast.LENGTH_SHORT).show();
                    OverviewFragment fragment = new OverviewFragment();
                    requireActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .addToBackStack(null)
                            .commit();
                    dbHelper.addAction("Пациент удалён: " + patient.getSurname() + " " +
                            patient.getName() + " " + patient.getPatronymic() + ", медкарта " +
                            patient.getRecord() + ", СНИЛС " + patient.getInsurance(),
                            deletingDate, deletingTime);
                } else {
                    Toast.makeText(requireContext(), "Ошибка при удалении пациента", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(requireContext(), "Ошибка при удалении пациента", Toast.LENGTH_SHORT).show();
            }
        });
    }
}