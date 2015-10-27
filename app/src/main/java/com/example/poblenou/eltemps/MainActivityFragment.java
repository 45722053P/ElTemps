package com.example.poblenou.eltemps;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.poblenou.eltemps.json.Forecast;
import com.example.poblenou.eltemps.json.List;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.GET;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    //Inicializamos las variables.
    private static final String APPID = "6ccb7be7c112de46b04c27a3f1dafda9";
    ListView tablaDias;
    ArrayList items;
    ArrayAdapter<String>adaptador;



    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_fragment,menu);
    }


    public MainActivityFragment() {


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        setHasOptionsMenu(true);
        View fragmento1 = inflater.inflate(R.layout.fragment_main, container, false);

        tablaDias = (ListView)fragmento1.findViewById(R.id.listaVer); // Relacionamos con el id la variable con el elemento del layout.

        //Aqui añadimos los dias en una array de Strings llamada fecha.
        String fechas []={
                "Lun 26/10/15 - Soleado",
                "Mar 27/10/15 - Nublado",
                "Mier 28/10/15 - Chubascos",
                "Juev 29/10/15 - Soleado",
                "Vier 30/10/15 - Soleado",
                "Sab 31/10/15 - Lluvia debil",
                "Dom 01/11/15 - Nublado",

        };

        items = new ArrayList<>(Arrays.asList(fechas)); //A items le decimos que es un arrayList con el contenido del Array de Strings fechas.

        adaptador = new ArrayAdapter<>(getContext(),R.layout.filasdias,R.id.textView,items); // En el adaptador con el Array adapter para el contexto ponemos que sera el layout hecho para este fragmento.

        tablaDias.setAdapter(adaptador); // A nuestro ListView le pasamos para que muestre con el adapter lo del layout creado.


        return fragmento1; // Aqui retornamos el fragmento.
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if(id==R.id.action_refresh){
            botonRefresh();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //En esta interficie lo que hacemos es la llamada a la api para poder cojer  los datos que nos interesen.
    public interface OwnService {
        @GET("daily?q=Barcelona&mode=json&units=metric&cnt=14&appid=bd82977b86bf27fb59a04b61b657fb6f")
        Call<Forecast> dailyForecast();
    }

    //En este boton de refresh la funcion que nos proporciona es refrescar la pantalla con la clase OwpApiClient.
    public void botonRefresh(){

        OwpApiClient apiClient= new OwpApiClient();
        apiClient.updateForecasts(adaptador);


    }


}
