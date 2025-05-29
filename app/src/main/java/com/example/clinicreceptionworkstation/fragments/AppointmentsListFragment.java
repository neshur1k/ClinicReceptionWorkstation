package com.example.clinicreceptionworkstation.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.clinicreceptionworkstation.R;
import com.example.clinicreceptionworkstation.adapters.AppointmentAdapter;
import com.example.clinicreceptionworkstation.adapters.DoctorAdapter;
import com.example.clinicreceptionworkstation.db.DatabaseHelper;
import com.example.clinicreceptionworkstation.models.Appointment;
import com.example.clinicreceptionworkstation.models.Doctor;
import com.example.clinicreceptionworkstation.models.Patient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AppointmentsListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AppointmentsListFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AppointmentsListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AppointmentsListFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AppointmentsListFragment newInstance(String param1, String param2) {
        AppointmentsListFragment fragment = new AppointmentsListFragment();
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
        return inflater.inflate(R.layout.fragment_appointments_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DatabaseHelper dbHelper = new DatabaseHelper(requireContext());

        List<Doctor> doctorList = dbHelper.getAllDoctors();
        Map<Integer, Doctor> doctorMap = new HashMap<>();
        for (Doctor doctor : doctorList) {
            doctorMap.put(doctor.getId(), doctor);
        }

        List<Patient> patientList = dbHelper.getAllPatients();
        Map<Integer, Patient> patientMap = new HashMap<>();
        for (Patient patient : patientList) {
            patientMap.put(patient.getId(), patient);
        }

        RecyclerView appointmentsList = view.findViewById(R.id.appointments_list);
        List<Appointment> appointments = dbHelper.getAllAppointments();
        AppointmentAdapter adapter = new AppointmentAdapter(
                requireActivity(), appointments, patientMap, doctorMap);
        appointmentsList.setLayoutManager(new LinearLayoutManager(requireContext()));
        appointmentsList.setAdapter(adapter);
    }
}