package com.br.weatherapp.presenter;

import java.util.ArrayList;
import com.br.weatherapp.view.ICityView;
import com.br.weatherapp.model.City;

public class CityPresenterImpl implements CityPresenter, CityInteractor.OnFinishedListener {

    private ICityView iCityView;
    private CityInteractor CityInteractor;

    public CityPresenterImpl(ICityView iCityView) {
        this.iCityView = iCityView;
        this.CityInteractor = new CityInteractorImpl();
    }

    @Override
    public void onSearchStarted(double latitude, double longitude) {
        CityInteractor.getCities(this, latitude, longitude);
    }

    @Override
    public void onCityReady(ArrayList<City> items) {
        if (iCityView != null) {
            iCityView.setItems(items);
        }
    }

    @Override
    public void onCityFailed(String errorMessage)
    {
    }
}
