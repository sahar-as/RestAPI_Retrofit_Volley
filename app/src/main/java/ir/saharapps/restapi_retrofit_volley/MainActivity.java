package ir.saharapps.restapi_retrofit_volley;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ir.saharapps.restapi_retrofit_volley.adapter.VolleyAdapter;
import ir.saharapps.restapi_retrofit_volley.model.WeatherModel;
import ir.saharapps.restapi_retrofit_volley.retrofit.GetWeatherJSON_Retrofit;
import ir.saharapps.restapi_retrofit_volley.volley.GetWeatherJSON_Volley;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    ImageButton imgBtnSearch;
    EditText edtCityName;
    RadioButton rbBtnVolley, rbBtnRetrofit;
    RecyclerView rvWeatherStatus;
    List<WeatherModel> WeatherForecastList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtCityName = findViewById(R.id.edtInput_MainActivity_cityName);
        imgBtnSearch = findViewById(R.id.btn_MainActivity_Search);
        rbBtnVolley = findViewById(R.id.rb_MainActivity_runWithVolley);
        rbBtnVolley.setChecked(true);
        rbBtnRetrofit = findViewById(R.id.rb_MainActivity_runWithRetrofit);

        rvWeatherStatus = findViewById(R.id.rv_MainActivity_WeatherList);
        rvWeatherStatus.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        VolleyAdapter adapter = new VolleyAdapter(WeatherForecastList, getApplicationContext());
        rvWeatherStatus.setAdapter(adapter);


        imgBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rbBtnVolley.isChecked()){
                    GetWeatherJSON_Volley weatherInfo = new GetWeatherJSON_Volley(MainActivity.this);
                    weatherInfo.getWeatherForecastByName(edtCityName.getText().toString(), new GetWeatherJSON_Volley.VolleyGetWeatherByNameListener() {
                        @Override
                        public void onError(String message) {
                            Toast.makeText(MainActivity.this, "An Error occurred", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(List<WeatherModel> weatherForecast) {
                            WeatherForecastList.clear();
                            for(WeatherModel model : weatherForecast){
                                WeatherForecastList.add(model);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    });
                }else{
                    Log.d(TAG, "onClick: EEEEEEEEEEEEEEEE0");
                    GetWeatherJSON_Retrofit weatherInfo = new GetWeatherJSON_Retrofit(MainActivity.this);
                    weatherInfo.getWeatherForecastByCityName(edtCityName.getText().toString(), new GetWeatherJSON_Retrofit.ForecastByNameListener() {
                        @Override
                        public void onError(String message) {
                            Toast.makeText(MainActivity.this, "An Error occurred", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onResponse(List<WeatherModel> weatherForecast) {
                            WeatherForecastList.clear();
                            for(WeatherModel model : weatherForecast){
                                WeatherForecastList.add(model);
                            }
                            adapter.notifyDataSetChanged();
                        }
                    });

                }
            }
        });
    }
}