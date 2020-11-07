package com.example.news;

import com.example.news.Model.Headline;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {


    /**
     * FOR SEARCHING NEWS
     **/
    @GET("everything")
    Call<Headline> getSpecificData(
            @Query("q") String query,
            @Query("pageSize") int pageSize,
            @Query("apiKey") String apiKey
    );

    /**
     * Here we are starting PATH AND QUERIES
     **/
    /**
     * Here we will display Business data in BusinessFragment
     **/

    @GET("top-headlines")
    Call<Headline> getCategory(
            @Query("country") String country,
            @Query("pageSize") int pageSize,
            @Query("category") String category,
            @Query("apiKey") String apiKey
    );

}
