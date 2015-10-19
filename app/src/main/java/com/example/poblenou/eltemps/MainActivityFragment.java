package com.example.poblenou.eltemps;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    ListView tablaDias;
    ArrayList items;
    ArrayAdapter<String>adaptador;

    public MainActivityFragment() {


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View fragmento1 = inflater.inflate(R.layout.fragment_main, container, false);

        tablaDias = (ListView)fragmento1.findViewById(R.id.listaVer);

        String fechas []={
                "Lun 26/10/15 - Soleado",
                "Mar 27/10/15 - Nublado",
                "Mier 28/10/15 - Chubascos",
                "Juev 29/10/15 - Soleado",
                "Vier 30/10/15 - Soleado",
                "Sab 31/10/15 - Lluvia debil",
                "Dom 01/11/15 - Nublado",

        };

        items = new ArrayList<>(Arrays.asList(fechas));
        adaptador = new ArrayAdapter<>(getContext(),
                R.layout.filasdias,R.id.textView,items);
        tablaDias.setAdapter(adaptador);


        return fragmento1;
    }
}
