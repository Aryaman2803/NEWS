package com.example.news;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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


public class HeadlineFragment extends Fragment {
    final String API_KEY = "579d24af38bb4044b9203297313dc669";
    RecyclerView recyclerView;
    Adapter adapter;
    List<Articles> articles;
    EditText editQuery;
    Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_headline, container, false);
        editQuery = view.findViewById(R.id.editText);
        button = view.findViewById(R.id.button);

        /**(1) Get reference to the recylerView **/
        recyclerView = view.findViewById(R.id.recyclerView);
        /**(2) Set LayoutManager**/
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        articles = new ArrayList<>();

        String country = getCountry();
        int pageSize = 100;
        retrieveJson("", country, pageSize, API_KEY);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editQuery.getText().toString().equals("")) {
                    retrieveJson(editQuery.getText().toString(), country, pageSize, API_KEY);
                } else {
                    retrieveJson("", country, pageSize, API_KEY);
                }
            }
        });

        return view;
    }


    public void retrieveJson(String query, String country, int pageSize, String apiKey) {
        Call<Headline> call;
        if (!editQuery.getText().toString().equals("")) {
            call = ApiClient.getInstance().getApi().getSpecificData(query, pageSize, apiKey);
        } else {
            call = ApiClient.getInstance().getApi().getHeadline(country, pageSize, apiKey);
        }
        call.enqueue(new Callback<Headline>() {
            @Override
            public void onResponse(Call<Headline> call, Response<Headline> response) {
                if (response.isSuccessful() && response.body().getArticles() != null) {
                    Log.d("RESPONSEEE", response.body().toString());
                    articles.clear();
                    articles = response.body().getArticles();
                    adapter = new Adapter(getActivity(), articles);
                    //        recyclerView.setAdapter(new Adapter(getActivity(), articles));
                    recyclerView.setAdapter(adapter);
                    //                   recyclerView.setAdapter(new Adapter(getContext(),articles));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<Headline> call, Throwable t) {
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