package ir.saharapps.restapi_retrofit_volley.retrofit;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherList {
    @SerializedName("consolidated_weather")
    public List<WeatherList> mWeatherLists;

}
