package com.br.weatherapp.model;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.text.DecimalFormat;
import java.math.RoundingMode;

public class City {
    @SerializedName("name")
    private String name;
    @SerializedName("main")
    private Main main;
    @SerializedName("weather")
    private ArrayList<Weather> weather;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormattedMinTemperature() {
        return this.main.getFormattedMinTemperature();
    }

    public void setMinTemperature(double minTemperature) {
        this.main.setMinTemperature(minTemperature);
    }

    public String getFormattedMaxTemperature() {
        return this.main.getFormattedMaxTemperature();
    }

    public void setMaxTemperature(double maxTemperature) {
        this.main.setMaxTemperature(maxTemperature);
    }

    public String getWeatherDescription() {
        String text = this.weather.get(0).getWeatherDescription();
        return text.substring(0, 1).toUpperCase() + text.substring(1);
    }

    public void setWeatherDescription(String weatherDescription) {
        this.weather.get(0).setWeatherDescription(weatherDescription);
    }

    static class Main {
        private static final double KELVIN_C = 273.15;
        @SerializedName("temp_min")
        private double minTemperature;
        @SerializedName("temp_max")
        private double maxTemperature;

        public double getMinTemperature() {
            return minTemperature - KELVIN_C;
        }

        public void setMinTemperature(double minTemperature) {
            this.minTemperature = minTemperature;
        }

        public String getFormattedMinTemperature() {
            DecimalFormat df = new DecimalFormat("min  #.##");
            df.setRoundingMode(RoundingMode.CEILING);
            return df.format(this.getMinTemperature())  + (char) 0x00B0 + "C";
        }

        public double getMaxTemperature() {
            return maxTemperature - KELVIN_C;
        }

        public String getFormattedMaxTemperature() {
            DecimalFormat df = new DecimalFormat("max #.##");
            df.setRoundingMode(RoundingMode.CEILING);
            return df.format(this.getMaxTemperature())  + (char) 0x00B0 + "C";
        }

        public void setMaxTemperature(double maxTemperature) {
            this.maxTemperature = maxTemperature;
        }
    }

    static class Weather {
        @SerializedName("description")
        private String weatherDescription;

        public String getWeatherDescription() {
            return weatherDescription;
        }

        public void setWeatherDescription(String weatherDescription) {
            this.weatherDescription = weatherDescription;
        }
    }
}
