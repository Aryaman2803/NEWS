package com.example.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news.Model.Articles;
import com.example.news.Model.Headline;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SportsFragment extends Fragment {
    final String API_KEY = "579d24af38bb4044b9203297313dc669";
    RecyclerView recyclerView;
    Adapter adapter;
    List<Articles> articles;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sports, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        articles = new ArrayList<>();

        String country = getCountry();
        String category = "sports";
        int pageSize = 100;
        retrieveJson(country, pageSize, category, API_KEY);
        return view;
    }

    public void retrieveJson(String country, int pageSize, String category, String apiKey) {
        Call<Headline> call = ApiClient.getInstance().getApi().getCategory(country, pageSize, category, apiKey);
        call.enqueue(new Callback<Headline>() {
            @Override
            public void onResponse(Call<Headline> call, Response<Headline> response) {
                if (response.isSuccessful() && response.body().getArticles() != null) {
                    articles.clear();
                    articles = response.body().getArticles();
                    adapter = new Adapter(getContext(), articles);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Headline> call, Throwable t) {
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    String getCountry() {
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }
}