package ir.saharapps.restapi_retrofit_volley.retrofit;

import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.annotations.SerializedName;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.saharapps.restapi_retrofit_volley.MainActivity;
import ir.saharapps.restapi_retrofit_volley.R;
import ir.saharapps.restapi_retrofit_volley.model.WeatherModel;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetWeatherJSON_Retrofit {
    private static final String TAG = "GetWeatherJSON_Retrofit";

    Context mContext;
    String cityID="";
    String baseUrl = "https://www.metaweather.com/api/";

    public GetWeatherJSON_Retrofit(Context context) {
        mContext = context;
    }

    public interface ForecastByNameListener{
        void onError(String message);
        void onResponse(List<WeatherModel> weatherForecast);
    }
    public void getWeatherForecastByCityName(String cityName, ForecastByNameListener forecastByNameListener){
        if(cityName.trim().toLowerCase().equals("tehran")){
            cityName = "Tehrān";
        }
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder().baseUrl(baseUrl);
        Retrofit retrofit = retrofitBuilder.client(okHttpBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MetaWeather metaWeatherApi = retrofit.create(MetaWeather.class);
        Call<JsonArray> call = metaWeatherApi.getCityId(cityName);
        call.enqueue(new Callback<JsonArray>() {
            @Override
            public void onResponse(Call<JsonArray> call, Response<JsonArray> response) {
                if(response.isSuccessful()){
                    try {
                        JsonObject cityInfo = (JsonObject) response.body().get(0);
                        cityID = cityInfo.get("woeid").getAsString();
                        GetWeatherForecastById(cityID, new ForecastByIDListener() {
                            @Override
                            public void onError(String message) {
                                Log.d(TAG, "onError: An error occurred");
                            }

                            @Override
                            public void onResponse(List<WeatherModel> weatherForecast) {
                                forecastByNameListener.onResponse(weatherForecast);
                            }
                        });
                    }catch (Exception e){
                        AlertDialog dialog = new AlertDialog.Builder(mContext).create();
                        dialog.setTitle("Error");
                        dialog.setMessage(mContext.getString(R.string.dialogString));
                        dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        dialog.show();
                    }

                }
            }

            @Override
            public void onFailure(Call<JsonArray> call, Throwable t) {
                t.printStackTrace();
                AlertDialog dialog = new AlertDialog.Builder(mContext).create();
                dialog.setTitle("Error");
                dialog.setMessage(mContext.getString(R.string.dialogString));
                dialog.setButton(AlertDialog.BUTTON_POSITIVE, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                dialog.show();
            }
        });
    }

    public interface ForecastByIDListener {
        void onError(String message);
        void onResponse(List<WeatherModel> weatherForecast);
    }

    public void GetWeatherForecastById(String cityID, ForecastByIDListener forecastByIDListener){
        List<WeatherModel> report = new ArrayList<>();

        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        Retrofit.Builder retrofitBuilder = new Retrofit.Builder().baseUrl(baseUrl);
        Retrofit retrofit = retrofitBuilder.client(okHttpBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MetaWeather metaWeatherApi = retrofit.create(MetaWeather.class);
        Call<JsonObject> call = metaWeatherApi.getWeatherForecast(cityID);
        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if(response.isSuccessful()){
                    JsonArray weatherReportList = response.body().getAsJsonArray("consolidated_weather");
                    for(int i = 0; i<weatherReportList.size(); i++) {
                        JsonObject oneDay = (JsonObject) weatherReportList.get(i);
                        WeatherModel weatherObject = new WeatherModel();
                        weatherObject.setId(oneDay.get("id").getAsString());
                        weatherObject.setWeather_state_name(oneDay.get("weather_state_name").getAsString());
                        weatherObject.setApplicable_date(oneDay.get("applicable_date").getAsString());
                        try {
                            weatherObject.setThe_temp(oneDay.get("the_temp").getAsFloat());
                        }catch (Exception e){
                            weatherObject.setThe_temp(1000);
                        }
                        report.add(weatherObject);
                    }
                    forecastByIDListener.onResponse(report);
                }else{
                    Log.e(TAG, "error");
                    Log.e(TAG, "message   = " + response.message());
                }
            }
            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                Log.d(TAG, "onFailure: Failure");
            }
        });
    }
}
