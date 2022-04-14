package ir.saharapps.restapi_retrofit_volley.model;

//Design and developed by Sahar Asadian

public class WeatherModel {
    private String id;
    private String weather_state_name;
    private String applicable_date;
    private float min_temp;
    private float max_temp;
    private float the_temp;

    public WeatherModel() {
    }

    public WeatherModel(String id, String weather_state_name, String applicable_date, float min_temp, float max_temp, float the_temp) {
        this.id = id;
        this.weather_state_name = weather_state_name;
        this.applicable_date = applicable_date;
        this.min_temp = min_temp;
        this.max_temp = max_temp;
        this.the_temp = the_temp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getWeather_state_name() {
        return weather_state_name;
    }

    public void setWeather_state_name(String weather_state_name) {
        this.weather_state_name = weather_state_name;
    }

    public String getApplicable_date() {
        return applicable_date;
    }

    public void setApplicable_date(String applicable_date) {
        this.applicable_date = applicable_date;
    }

    public float getMin_temp() {
        return min_temp;
    }

    public void setMin_temp(float min_temp) {
        this.min_temp = min_temp;
    }

    public float getMax_temp() {
        return max_temp;
    }

    public void setMax_temp(float max_temp) {
        this.max_temp = max_temp;
    }

    public float getThe_temp() {
        return the_temp;
    }

    public void setThe_temp(float the_temp) {
        this.the_temp = the_temp;
    }

    @Override
    public String toString() {
        return "WeatherModel{" +
                "id='" + id + " " +
                ", weather_state_name='" + weather_state_name + " " +
                ", applicable_date='" + applicable_date + " " +
                ", min_temp=" + min_temp + " " +
                ", max_temp=" + max_temp + " " +
                ", the_temp=" + the_temp + " " +
                '}';
    }
}
