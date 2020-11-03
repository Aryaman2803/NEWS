package com.example.news;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.news.Model.Articles;
import com.example.news.Model.Headline;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Search extends AppCompatActivity {
//    final String API_KEY = "579d24af38bb4044b9203297313dc669";
final String API_KEY = "0eb52f4866d045a48400fa5c03e5f840";
    RecyclerView recyclerView;
    SearchAdapter adapter;
    List<Articles> articles;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();
        String query = intent.getStringExtra("query");

        swipeRefreshLayout = findViewById(R.id.swipeRefresh);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        articles = new ArrayList<>();
        int pageSize = 100;


        retrieveJson(query, pageSize, API_KEY);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrieveJson(query, pageSize, API_KEY);
            }
        });


    }

    public void retrieveJson(String query, int pageSize, String apiKey) {
        swipeRefreshLayout.setRefreshing(true);
        Call<Headline> call;
        call = ApiClient.getInstance().getApi().getSpecificData(query, pageSize, apiKey);
        call.enqueue(new Callback<Headline>() {
            @Override
            public void onResponse(Call<Headline> call, Response<Headline> response) {
                if (response.isSuccessful() && response.body().getArticles() != null) {
                    swipeRefreshLayout.setRefreshing(false);
                    articles.clear();
                    articles = response.body().getArticles();
                    adapter = new SearchAdapter(getApplicationContext(), articles);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Headline> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(Search.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}