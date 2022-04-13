package ir.saharapps.restapi_retrofit_volley.volley;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ir.saharapps.restapi_retrofit_volley.WeatherModel;

public class GetWeatherJSON_Volley {
    private static final String TAG = "GetWeatherJSON_Volley";
    String cityID = "";

    Context mContext;
    public GetWeatherJSON_Volley(Context context) {
        mContext = context;
    }



    //Listener for getting weather forecast by city Name
    public interface VolleyGetWeatherByNameListener{
        void onError(String message);
        void onResponse(List<WeatherModel> weatherForecast);
    }
    //Method of getting weather forecast by city name
    public void getWeatherForecastByName(String cityName, VolleyGetWeatherByNameListener volleyGetWeatherByNameListener){
        getCityId(cityName, new VolleyGetCityIdListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(mContext, "An error occurred", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String cityID) {
                getCityWeatherById(cityID, new VolleyGetWeatherByIdListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(mContext, "An error occurred", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(List<WeatherModel> weatherForecast) {
                        volleyGetWeatherByNameListener.onResponse(weatherForecast);
                    }
                });
            }
        });
    }

    //Listener for getting CityID
    public interface VolleyGetCityIdListener{
        void onError(String message);
        void onResponse(String cityID);
    }
    //Method of Getting city id by its name
    public void getCityId(String cityName, VolleyGetCityIdListener volleyGetCityIdListener){
        String url = "https://www.metaweather.com/api/location/search/?query=" + cityName;

        JsonArrayRequest cityIdRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject cityInfo = response.getJSONObject(0);
                    cityID = cityInfo.getString("woeid");
                    volleyGetCityIdListener.onResponse(cityID);

                } catch (JSONException e) {
                    e.printStackTrace();
                    volleyGetCityIdListener.onError("Something Wrong");
                    Toast.makeText(mContext, "Check spell of city", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getInstance(mContext).addToRequestQueue(cityIdRequest);
    }

    //listener for getting weather forecast by city Id
    public interface VolleyGetWeatherByIdListener{
        void onError(String message);
        void onResponse(List<WeatherModel> weatherForecast);
    }

    //Method of getting weather forecast by Id
    public void getCityWeatherById(String cityId, VolleyGetWeatherByIdListener volleyGetWeatherByIdListener){
        List<WeatherModel> report = new ArrayList<>();
        String url = "https://www.metaweather.com/api/location/" + cityId;

        JsonObjectRequest cityWeatherByIdRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray weatherReportList = response.getJSONArray("consolidated_weather");

                    for(int i = 0; i<weatherReportList.length(); i++) {
                        JSONObject oneDay = (JSONObject) weatherReportList.get(i);
                        WeatherModel weatherObject = new WeatherModel();
                        weatherObject.setId(oneDay.getString("id"));
                        weatherObject.setWeather_state_name(oneDay.getString("weather_state_name"));
                        weatherObject.setApplicable_date(oneDay.getString("applicable_date"));
                        weatherObject.setMin_temp(oneDay.getLong("min_temp"));
                        weatherObject.setMax_temp(oneDay.getLong("max_temp"));
                        weatherObject.setThe_temp(oneDay.getLong("the_temp"));
                        report.add(weatherObject);
                    }
                    volleyGetWeatherByIdListener.onResponse(report);
                } catch (JSONException e) {
                    e.printStackTrace();
                    volleyGetWeatherByIdListener.onError("An error occurred");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(mContext, "An error occurred", Toast.LENGTH_SHORT).show();
            }
        });
        MySingleton.getInstance(mContext).addToRequestQueue(cityWeatherByIdRequest);

    }
}
