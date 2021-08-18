package com.example.newsapp.Service;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit;
    private static final String BASE_URL="https://newsapi.org/v2/";
    private static ApiClient apiClient;


    public ApiClient() {
        retrofit =new Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build();
    }

    public static synchronized ApiClient getInstance(){
        if (apiClient==null){
            apiClient =new ApiClient();
        }
        return  apiClient;

    }

    public ApiInterface getApi(){
        return retrofit.create(ApiInterface.class);
    }

}
