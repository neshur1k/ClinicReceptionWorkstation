package com.example.clinicreceptionworkstation.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.clinicreceptionworkstation.R;
import com.example.clinicreceptionworkstation.db.DatabaseHelper;
import com.example.clinicreceptionworkstation.models.Patient;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddPatientFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPatientFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AddPatientFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddPatientFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddPatientFragment newInstance(String param1, String param2) {
        AddPatientFragment fragment = new AddPatientFragment();
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
        return inflater.inflate(R.layout.fragment_add_patient, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText surnameEditText = view.findViewById(R.id.surnameEditText);
        EditText nameEditText = view.findViewById(R.id.nameEditText);
        EditText patronymicEditText = view.findViewById(R.id.patronymicEditText);
        EditText recordEditText = view.findViewById(R.id.recordEditText);
        EditText genderEditText = view.findViewById(R.id.genderEditText);
        EditText birthDateEditText = view.findViewById(R.id.birthDateEditText);
        EditText insuranceEditText = view.findViewById(R.id.patientInsuranceEditText);
        EditText phoneEditText = view.findViewById(R.id.phoneEditText);
        Button saveButton = view.findViewById(R.id.cancelButton);
        ImageButton backButton = view.findViewById(R.id.backButton);
        TextView sectionTextView = requireActivity().findViewById(R.id.sectionTextView);
        sectionTextView.setText("Зарегистрировать");

        DatabaseHelper dbHelper = new DatabaseHelper(requireContext());

        saveButton.setOnClickListener(v -> {
            String surname = surnameEditText.getText().toString();
            String name = nameEditText.getText().toString();
            String patronymic = patronymicEditText.getText().toString();
            String record = recordEditText.getText().toString();
            String gender = genderEditText.getText().toString();
            String birthDate = birthDateEditText.getText().toString();
            String insurance = insuranceEditText.getText().toString();
            String phone = phoneEditText.getText().toString();

            Date currentDate = new Date();
            Locale russianLocale = new Locale("ru", "RU");
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", russianLocale);
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", russianLocale);
            String registrationDate = dateFormat.format(currentDate);
            String registrationTime = timeFormat.format(currentDate);

            if (dbHelper.addPatient(new Patient(0, record, name, surname, patronymic, gender,
                    birthDate, insurance, phone, registrationDate, registrationTime)))
            {
                dbHelper.addAction("Пациент зарегистрирован: " + surname + " " + name + " " +
                        patronymic + ", медкарта " + record + ", СНИЛС " + insurance,
                        registrationDate, registrationTime);
                surnameEditText.setText("");
                nameEditText.setText("");
                patronymicEditText.setText("");
                recordEditText.setText("");
                genderEditText.setText("");
                birthDateEditText.setText("");
                insuranceEditText.setText("");
                phoneEditText.setText("");
                Toast.makeText(requireContext(), "Пациент зарегистирован", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), "Ошибка при регистрации пациента", Toast.LENGTH_SHORT).show();
            }
        });

        backButton.setOnClickListener(v -> {
            OverviewFragment fragment = new OverviewFragment();
            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit();
        });
    }
}