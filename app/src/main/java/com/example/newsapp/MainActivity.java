package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.widget.Adapter;
import android.widget.Toast;

import com.example.newsapp.Model.Articles;
import com.example.newsapp.Model.HeadLine;
import com.example.newsapp.Service.ApiClient;
import com.example.newsapp.Service.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    final String API_KEY ="8ea48238e3434cde9aba1dc7ff46b595";
    final String COUNTRY="us";
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    MainAdapter mainAdapter;
    List<Articles> articles ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.new_recycler);
        swipeRefreshLayout =findViewById(R.id.swipe_refresh);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrieveJson(COUNTRY,API_KEY);
            }
        });

        retrieveJson(COUNTRY,API_KEY);

    }


    public void  retrieveJson(String country,String api_key) {

        swipeRefreshLayout.setRefreshing(true);
        Call<HeadLine> call =ApiClient.getInstance().getApi().getHeadlines(country,api_key);
        call.enqueue(new Callback<HeadLine>() {
            @Override
            public void onResponse(Call<HeadLine> call, Response<HeadLine> response) {
                swipeRefreshLayout.setRefreshing(false);
                if (response.isSuccessful()&& response.body().getArticles()!=null){
                   // articles.clear();
                    articles=response.body().getArticles();
                    mainAdapter =new MainAdapter(MainActivity.this,articles);
                    recyclerView.setAdapter(mainAdapter);


                }
            }

            @Override
            public void onFailure(Call<HeadLine> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);

            }
        });

    }
}