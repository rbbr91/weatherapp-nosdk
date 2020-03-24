package com.br.weatherapp.presenter;

import com.google.gson.JsonElement;

import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.Call;


public interface CityApiInterface {
    @GET("data/2.5/find")
    Call<JsonElement> getCities(@Query("lat") double latitude, @Query("lon") double longitude, @Query("APPID") String appId, @Query("cnt") int cnt);
}
