package com.br.weatherapp.presenter;

import com.br.weatherapp.model.City;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CityInteractorImpl implements CityInteractor {

    public void getCities(final OnFinishedListener fListener, double latitude, double longitude) {

        String APP_ID = "ae2eaf5534348ee21b8fd9eb4ca94950";
        int cnt = 15;
        CityApiFactory.getCityClient().getCities(latitude, longitude, APP_ID, cnt)
                .enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        JsonElement result = response.body();
                        if (result != null) {
                           City[] cities = new Gson().fromJson(result.getAsJsonObject().get("list"), City[].class);
                            fListener.onCityReady(new ArrayList<>(Arrays.asList(cities)));
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonElement> call, Throwable t) {
                        try {
                            throw new InterruptedException("Error occured!");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {

                        }
                    }
                });
    }


}
