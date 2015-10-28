package com.example.poblenou.eltemps;

import com.example.poblenou.eltemps.json.Forecast;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import com.example.poblenou.eltemps.json.List;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by 45722053p on 26/10/15.
 */
//Aqui en la interficie con la llamada para el forecast y les asignamos a cada etiqueta su tipo de variable.
interface OpenWeatherMapService {

    @GET("forecast/daily")
    Call<Forecast> dailyForecast(
            @Query("q") String city,
            @Query("mode") String format,
            @Query("units") String units,
            @Query("cnt") Integer num,
            @Query("appid") String appid);
}
public class OwpApiClient {
    //Aqui tenemos las constantes y les damos valor.

    private final OpenWeatherMapService service;
    private final String Forecast_URL = "http://api.openweathermap.org/data/2.5/";
    private final String Ciudad = "Barcelona";
    private final String APPID = "bd82977b86bf27fb59a04b61b657fb6f";


    public OwpApiClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Forecast_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(OpenWeatherMapService.class);
    }


    public void updateForecasts(final ArrayAdapter<String> adapter,Context context) {

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        String city = preferences.getString("City", "Barcelona");
        String valorUnits;
        if(preferences.getString("Unit","0").equals(0)){

            valorUnits="metric";
        }else{
            valorUnits="imperial";
        }


        Call<Forecast> forecastCall = service.dailyForecast(
                city, "json", valorUnits, 14, APPID //Al hacer la llamada le introducimos los parametros para la llama en este caso son:
                                                    // el nombre de la ciudad , Json , metric,que seran 14 dias la prevision del tiempo y la APPID.
        );
        forecastCall.enqueue(new Callback<Forecast>() {
            @Override
            public void onResponse(Response<Forecast> response, Retrofit retrofit) {
                Forecast forecast = response.body();

                ArrayList<String> forecastStrings = new ArrayList<>();
                for (List list : forecast.getList()) {
                    String forecastString = getForecastString(list);
                    forecastStrings.add(forecastString);
                }
                adapter.clear();
                adapter.addAll(forecastStrings);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("Update Forecasts", Arrays.toString(t.getStackTrace()));
            }
        });

    }

    //En este metodo lo que vamos a hacer es poner el formato que queremos la fecha.
    private String getForecastString(List list){

        Long data = list.getDt();
        java.util.Date date = new java.util.Date(data * 1000);
        SimpleDateFormat dateFormat = new SimpleDateFormat("E d/M");
        String dateString = dateFormat.format(date);

        String description = list.getWeather().get(0).getDescription();

        Long min = Math.round(list.getTemp().getMin());
        Long max = Math.round(list.getTemp().getMax());

        return String.format("%s - %s - %s/%s",
                dateString, description, min, max
        );
    }

}
