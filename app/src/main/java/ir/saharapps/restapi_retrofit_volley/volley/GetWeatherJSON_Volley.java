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

public class GetWeatherJSON_Volley {
    private static final String TAG = "GetWeatherJSON_Volley";
    String cityID = "";

    Context mContext;
    public GetWeatherJSON_Volley(Context context) {
        mContext = context;
    }

    public String getCityId(String cityName){
        String url = "https://www.metaweather.com/api/location/search/?query=" + cityName;

        JsonArrayRequest cityIdRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    JSONObject cityInfo = response.getJSONObject(0);
                    cityID = cityInfo.getString("woeid");
                    Log.d(TAG, "onResponse: TTTTTTTTTTTTT " + cityID);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, "Check spell of city", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        MySingleton.getInstance(mContext).addToRequestQueue(cityIdRequest);
//        Toast.makeText(mContext, cityID, Toast.LENGTH_SHORT).show();
        return cityID;
    }
}
