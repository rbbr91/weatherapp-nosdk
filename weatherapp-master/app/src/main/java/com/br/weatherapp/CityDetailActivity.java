package com.br.weatherapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;

import android.view.MenuItem;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CityDetailActivity extends AppCompatActivity {
    @BindView(R.id.cityName)
    TextView cityName;
    @BindView(R.id.cityDescription)
    TextView cityDescription;
    @BindView(R.id.cityMinTemperature)
    TextView cityMinTemperature;
    @BindView(R.id.cityMaxTemperature)
    TextView cityMaxTemperature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        Intent i = getIntent();
        final String name=i.getExtras().getString("Name");
        final String description=i.getExtras().getString("Description");
        final String minTemperature=i.getExtras().getString("MinTemperature");
        final String maxTemperature=i.getExtras().getString("MaxTemperature");

        cityName.setText(name);
        cityDescription.setText(description);
        cityMinTemperature.setText(minTemperature);
        cityMaxTemperature.setText(maxTemperature);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, MapsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
