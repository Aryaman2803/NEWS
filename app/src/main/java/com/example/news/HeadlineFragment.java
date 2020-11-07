package com.example.news;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.news.Model.Articles;

import java.util.ArrayList;
import java.util.List;


public class HeadlineFragment extends Fragment {
    RecyclerView recyclerView;
    Adapter adapter;
    List<Articles> articles;
    SwipeRefreshLayout swipeRefreshLayout;
    HeadlineFragment context;

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
        articles = new ArrayList<>();

        Utility utility = new Utility(getActivity(), recyclerView, swipeRefreshLayout);

        /**(2) Set LayoutManager**/
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new Adapter(getActivity(), articles);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        adapter.notifyDataSetChanged();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                utility.retrieveJson("general");
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
        utility.retrieveJson("general");
        return view;
    }
}