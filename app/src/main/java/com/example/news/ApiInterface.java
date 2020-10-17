package com.example.news;

import com.example.news.Model.Headline;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    /**
     * (100)Now we will create an Interface which will contain the API ENDPOINTS AND ITS PATH AND QUERIES
     **/

    @GET("top-headlines")
    Call<Headline> getHeadliine(
            @Query("country") String country,
            @Query("apiKey") String apiKey
    );
    /**
     * Here we are starting PATH AND QUERIES
     **/

}
