package ir.saharapps.restapi_retrofit_volley.retrofit;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import ir.saharapps.restapi_retrofit_volley.model.WeatherModel;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MetaWeather {

    @GET("location/search/")
    Call<JsonArray> getCityId(@Query("query") String cityName);

    @GET("location/{cityId}/")
    Call<JsonObject> getWeatherForecast(@Path("cityId") String cityId);

}
