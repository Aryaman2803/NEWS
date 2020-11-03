package com.example.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.news.Model.Articles;
import com.example.news.Model.Headline;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HeadlineFragment extends Fragment {
//    final  String API_KEY = String.valueOf(R.string.API_KEY);

    //    final String API_KEY = "579d24af38bb4044b9203297313dc669";
    final String API_KEY = "0eb52f4866d045a48400fa5c03e5f840";
    RecyclerView recyclerView;
    Adapter adapter;
    List<Articles> articles;
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_headline, container, false);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefresh);

        /**(1) Get reference to the recylerView **/
        recyclerView = view.findViewById(R.id.recyclerView);
        /**(2) Set LayoutManager**/
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        articles = new ArrayList<>();

        adapter = new Adapter(getActivity(), articles);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        String country = getCountry();
        int pageSize = 100;

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                retrieveJson(country, pageSize, API_KEY);
            }
        });
        retrieveJson(country, pageSize, API_KEY);

        return view;
    }


    public void retrieveJson(String country, int pageSize, String apiKey) {
        swipeRefreshLayout.setRefreshing(true);
        Call<Headline> call;
        call = ApiClient.getInstance().getApi().getHeadline(country, pageSize, apiKey);

        call.enqueue(new Callback<Headline>() {
            @Override
            public void onResponse(Call<Headline> call, Response<Headline> response) {
                if (response.isSuccessful() && response.body().getArticles() != null) {
                    swipeRefreshLayout.setRefreshing(false);
                    articles.clear();
                    articles = response.body().getArticles();
                    adapter = new Adapter(getActivity(), articles);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                }

            }


            @Override
            public void onFailure(Call<Headline> call, Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getActivity(), t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String getCountry() {
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }


}