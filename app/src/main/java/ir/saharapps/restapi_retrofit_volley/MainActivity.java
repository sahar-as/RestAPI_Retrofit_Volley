package ir.saharapps.restapi_retrofit_volley;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.List;

import ir.saharapps.restapi_retrofit_volley.volley.GetWeatherJSON_Volley;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    ImageButton imgBtnSearch;
    EditText edtCityName;
    RadioButton rbBtnVolley, rbBtnRetrofit;
    RecyclerView rvWeatherStatus;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtCityName = findViewById(R.id.edtInput_MainActivity_cityName);
        imgBtnSearch = findViewById(R.id.btn_MainActivity_Search);
        rbBtnVolley = findViewById(R.id.rb_MainActivity_runWithVolley);
        rbBtnRetrofit = findViewById(R.id.rb_MainActivity_runWithRetrofit);
        rvWeatherStatus = findViewById(R.id.rv_MainActivity_WeatherList);

        rbBtnVolley.setChecked(true);

        imgBtnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(rbBtnVolley.isChecked()){
                    GetWeatherJSON_Volley weatherInfo = new GetWeatherJSON_Volley(MainActivity.this);
                    weatherInfo.getWeatherForecastByName(edtCityName.getText().toString(), new GetWeatherJSON_Volley.VolleyGetWeatherByNameListener() {
                        @Override
                        public void onError(String message) {
                            Toast.makeText(MainActivity.this, "An error occurred", Toast.LENGTH_SHORT);
                        }

                        @Override
                        public void onResponse(List<WeatherModel> weatherForecast) {
                            for(WeatherModel model : weatherForecast){
                                Log.d(TAG, "onResponse: TTTTTTTTTTTTTT " + model.toString());
                            }
                        }
                    });
                }
            }
        });
    }
}