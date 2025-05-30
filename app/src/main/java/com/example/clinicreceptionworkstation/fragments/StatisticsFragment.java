package com.example.clinicreceptionworkstation.fragments;

import android.animation.ObjectAnimator;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.clinicreceptionworkstation.R;
import com.example.clinicreceptionworkstation.db.DatabaseHelper;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatisticsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatisticsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StatisticsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StatisticsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatisticsFragment newInstance(String param1, String param2) {
        StatisticsFragment fragment = new StatisticsFragment();
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
        return inflater.inflate(R.layout.fragment_statistics, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        DatabaseHelper dbHelper = new DatabaseHelper(requireContext());

        TextView patientCountTextView = view.findViewById(R.id.patientCountTextView);
        TextView doctorCountTextView = view.findViewById(R.id.doctorCountTextView);
        TextView appointmentCountTextView = view.findViewById(R.id.appointmentCountTextView);
        TextView actionCountTextView = view.findViewById(R.id.actionCountTextView);
        TextView sectionTextView = requireActivity().findViewById(R.id.sectionTextView);
        sectionTextView.setText("Статистика");

        patientCountTextView.setText("Всего пациентов: " + dbHelper.getPatientCount());
        doctorCountTextView.setText("Всего врачей: " + dbHelper.getDoctorCount());
        appointmentCountTextView.setText("Всего записей: " + dbHelper.getAppointmentCount());
        actionCountTextView.setText("Всего действий: " + dbHelper.getActionCount());

        patientCountTextView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 ObjectAnimator scaleX =
                         ObjectAnimator.ofFloat(patientCountTextView, "scaleX", 1f, 2f);
                 ObjectAnimator scaleY =
                         ObjectAnimator.ofFloat(patientCountTextView, "scaleY", 1f, 2f);
                 scaleX.setDuration(1000);
                 scaleY.setDuration(1000);
                 scaleX.start();
                 scaleY.start();
             }
        });

        doctorCountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator scaleX =
                        ObjectAnimator.ofFloat(doctorCountTextView, "scaleX", 1f, 2f);
                ObjectAnimator scaleY =
                        ObjectAnimator.ofFloat(doctorCountTextView, "scaleY", 1f, 2f);
                scaleX.setDuration(1000);
                scaleY.setDuration(1000);
                scaleX.start();
                scaleY.start();
            }
        });

        appointmentCountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator scaleX =
                        ObjectAnimator.ofFloat(appointmentCountTextView, "scaleX", 1f, 2f);
                ObjectAnimator scaleY =
                        ObjectAnimator.ofFloat(appointmentCountTextView, "scaleY", 1f, 2f);
                scaleX.setDuration(1000);
                scaleY.setDuration(1000);
                scaleX.start();
                scaleY.start();
            }
        });

        actionCountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator scaleX =
                        ObjectAnimator.ofFloat(actionCountTextView, "scaleX", 1f, 2f);
                ObjectAnimator scaleY =
                        ObjectAnimator.ofFloat(actionCountTextView, "scaleY", 1f, 2f);
                scaleX.setDuration(1000);
                scaleY.setDuration(1000);
                scaleX.start();
                scaleY.start();
            }
        });
    }
}