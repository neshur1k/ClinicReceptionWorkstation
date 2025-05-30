package com.example.clinicreceptionworkstation.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.clinicreceptionworkstation.R;
import com.example.clinicreceptionworkstation.adapters.ActionAdapter;
import com.example.clinicreceptionworkstation.adapters.ComplaintAdapter;
import com.example.clinicreceptionworkstation.api.Complaint;
import com.example.clinicreceptionworkstation.api.ComplaintApi;
import com.example.clinicreceptionworkstation.api.RetrofitClient;
import com.example.clinicreceptionworkstation.db.DatabaseHelper;
import com.example.clinicreceptionworkstation.models.Action;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GuideFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GuideFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public GuideFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GuideFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GuideFragment newInstance(String param1, String param2) {
        GuideFragment fragment = new GuideFragment();
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
        return inflater.inflate(R.layout.fragment_guide, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ComplaintApi api = RetrofitClient.getClient().create(ComplaintApi.class);
        Call<List<Complaint>> call = api.getComplaints();

        RecyclerView complaintsList = view.findViewById(R.id.complaints_list);
        complaintsList.setLayoutManager(new LinearLayoutManager(getContext()));

        call.enqueue(new Callback<List<Complaint>>() {
            @Override
            public void onResponse(Call<List<Complaint>> call, Response<List<Complaint>> response) {
                if (response.isSuccessful()) {
                    List<Complaint> complaints = response.body();
                    for (Complaint c : complaints) {
                        Log.d("API", c.getSymptom() + " — " + c.getSpecialist());
                    }
                    ComplaintAdapter adapter = new ComplaintAdapter(complaints);
                    complaintsList.setAdapter(adapter);
                } else {
                    Log.e("API", "Ошибка сервера: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<Complaint>> call, Throwable t) {
                Log.e("API", "Ошибка подключения: " + t.getMessage());
            }
        });
    }
}