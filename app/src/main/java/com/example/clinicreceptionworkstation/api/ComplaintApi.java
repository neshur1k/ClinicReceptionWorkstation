package com.example.clinicreceptionworkstation.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ComplaintApi {
    @GET("complaints")
    Call<List<Complaint>> getComplaints();
}