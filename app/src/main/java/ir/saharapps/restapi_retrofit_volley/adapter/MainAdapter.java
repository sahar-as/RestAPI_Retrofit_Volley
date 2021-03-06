package ir.saharapps.restapi_retrofit_volley.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ir.saharapps.restapi_retrofit_volley.R;
import ir.saharapps.restapi_retrofit_volley.model.WeatherModel;

//Design and developed by Sahar Asadian

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ItemHolder> {

    List<WeatherModel> weatherForecastList = new ArrayList<>();
    Context mContext;
    public static final String WEATHER_STATUS_PICTURE_LINK = "https://www.metaweather.com/static/img/weather/png/";

    Map<String, String> weatherStatusAbbreviation= new HashMap<String, String>(){{
        put("Snow","sn");
        put("Sleet","sl");
        put("Hail","h");
        put("Thunderstorm","t");
        put("Heavy Rain","hr");
        put("Light Rain","lr");
        put("Showers","s");
        put("Heavy Cloud","hc");
        put("Light Cloud","lc");
        put("Clear","c");
    }};

    public MainAdapter(List<WeatherModel> weatherForecastList, Context context) {
        this.weatherForecastList = weatherForecastList;
        mContext = context;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_forecast_item, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        WeatherModel weatherModel = weatherForecastList.get(position);
        holder.txtDate.setText(weatherModel.getApplicable_date());
        holder.txtStatus.setText(weatherModel.getWeather_state_name());
        float temp = weatherModel.getThe_temp();
        if(temp == 1000){
            holder.txtMinMax.setText("null");
        }else{
            holder.txtMinMax.setText(String.valueOf(temp));
        }

        String imageUrl = WEATHER_STATUS_PICTURE_LINK + weatherStatusAbbreviation.get(weatherModel.getWeather_state_name()) + ".png";
        Picasso.get().load(imageUrl).into(holder.imgWeatherImage);

    }

    @Override
    public int getItemCount() {
        return weatherForecastList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        TextView txtDate, txtStatus, txtMinMax;
        ImageView imgWeatherImage;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            txtDate = itemView.findViewById(R.id.txt_WeatherForcastItem_Date);
            txtStatus = itemView.findViewById(R.id.txt_WeatherForcastItem_Status);
            txtMinMax = itemView.findViewById(R.id.txt_WeatherForcastItem_MinMax);
            imgWeatherImage = itemView.findViewById(R.id.img_WeatherForcastItem_weatherImage);
        }
    }
}
