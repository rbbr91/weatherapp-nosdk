package com.br.weatherapp.presenter;

import com.br.weatherapp.model.City;

import java.util.ArrayList;

public interface CityInteractor
{

    void getCities(OnFinishedListener listener, double latitude, double longitude);

    interface OnFinishedListener
    {
        void onCityReady(ArrayList<City> items);
        void onCityFailed(String errorMessage);
    }
}
