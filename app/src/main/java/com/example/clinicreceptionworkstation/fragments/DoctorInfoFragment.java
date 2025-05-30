package com.example.clinicreceptionworkstation.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.clinicreceptionworkstation.R;
import com.example.clinicreceptionworkstation.db.DatabaseHelper;
import com.example.clinicreceptionworkstation.models.Doctor;
import com.example.clinicreceptionworkstation.models.Patient;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DoctorInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DoctorInfoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private Doctor doctor;

    public DoctorInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DoctorInfoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DoctorInfoFragment newInstance(String param1, String param2) {
        DoctorInfoFragment fragment = new DoctorInfoFragment();
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
        int doctorId = getArguments().getInt("doctor_id", -1);
        DatabaseHelper dbHelper = new DatabaseHelper(requireContext());
        doctor = dbHelper.findDoctor(doctorId);
        return inflater.inflate(R.layout.fragment_doctor_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView fioTextView = view.findViewById(R.id.fioTextView);
        TextView phoneTextView = view.findViewById(R.id.phoneTextView);
        TextView specializationTextView = view.findViewById(R.id.specializationTextView);
        TextView officeTextView = view.findViewById(R.id.officeTextView);
        TextView notesTextView = view.findViewById(R.id.notesTextView);
        ImageButton backButton = view.findViewById(R.id.backButton);

        fioTextView.setText(doctor.getSurname() + " " + doctor.getName() + " " + doctor.getPatronymic());
        phoneTextView.setText(doctor.getPhone());
        specializationTextView.setText(doctor.getSpecialization());
        officeTextView.setText("Кабинет: " + doctor.getOffice());
        notesTextView.setText(doctor.getNotes());

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