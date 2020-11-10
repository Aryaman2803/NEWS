package com.example.news;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.news.Model.Articles;
import com.example.news.Model.Headline;

import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Utility {
    final String API_KEY = "YOUR NEWS API KEY";
    SwipeRefreshLayout swipeRefreshLayout;
    List<Articles> articles;
    Adapter adapter;
    Context context;
    RecyclerView recyclerView;
    int pageSize = 100;
    String country = getCountry();

    public Utility(Context context, RecyclerView recyclerView, SwipeRefreshLayout swipeRefreshLayout) {
        this.context = context;
        this.recyclerView = recyclerView;
        this.swipeRefreshLayout = swipeRefreshLayout;

    }

    public void retrieveJson(String category) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            //fetch data
            Call<Headline> call;
            call = ApiClient.getInstance().getApi().getCategory(country, pageSize, category, API_KEY);
            call.enqueue(new Callback<Headline>() {
                @Override
                public void onResponse(Call<Headline> call, Response<Headline> response) {
                    if (response.isSuccessful() && response.body().getArticles() != null) {
                        swipeRefreshLayout.setRefreshing(false);
                        articles = response.body().getArticles();
                        adapter = new Adapter(context, articles);
                        recyclerView.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(Call<Headline> call, Throwable t) {
                    swipeRefreshLayout.setRefreshing(false);
                    Toast.makeText(context, "Server Down", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            swipeRefreshLayout.setRefreshing(false);
            Toast.makeText(context, "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    public String getCountry() {
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }
}
