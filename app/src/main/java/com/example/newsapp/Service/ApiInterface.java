package com.example.newsapp.Service;

import com.example.newsapp.Model.HeadLine;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {



    @GET("top-headlines")
    Call<HeadLine> getHeadlines(@Query("country") String country,@Query("apiKey") String apiKey);


}
