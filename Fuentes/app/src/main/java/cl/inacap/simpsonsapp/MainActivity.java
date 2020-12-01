package cl.inacap.simpsonsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cl.inacap.simpsonsapp.adapters.FrasesListAdapter;
import cl.inacap.simpsonsapp.dto.Frase;

//Activity que contiene los elementos para el consumo de la api

public class MainActivity extends AppCompatActivity {

    private Spinner frasesSpinner;
    private Button ejecutarBtn;
    private ListView frasesLv;
    private RequestQueue requestQueue;
    private List<Frase> frases = new ArrayList<>();
    private FrasesListAdapter flAdapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.frasesSpinner = findViewById(R.id.cantidad_spinner);
        this.ejecutarBtn = findViewById(R.id.ejecutar_btn);
        this.frasesLv = findViewById(R.id.frases_lv);

        this.flAdapter = new FrasesListAdapter(this, R.layout.frases_list, this.frases);
        this.frasesLv.setAdapter(flAdapter);

        ArrayList<String> cantidadFrases = new ArrayList<>();
        for(int i=0;i<11;i++){
            if(i==0){
                cantidadFrases.add("------Seleccione la cantidad de consejos------");
            }else if(i==1){
                cantidadFrases.add("1 Consejo");
            }else{
                cantidadFrases.add(i + " Consejos");
            }
        }

        ArrayAdapter spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,cantidadFrases);
        this.frasesSpinner.setAdapter(spinnerAdapter);

        this.frasesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        this.ejecutarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = frasesSpinner.getSelectedItemPosition();
                if(num==0){
                    Toast.makeText(MainActivity.this,"Debe seleccionar cantidad de consejos",Toast.LENGTH_LONG).show();
                }else{
                    String url = ("https://thesimpsonsquoteapi.glitch.me/quotes?count="+ num);
                    requestQueue = Volley.newRequestQueue(MainActivity.this);
                    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                            new Response.Listener<JSONArray>() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    try{
                                        frases.clear();
                                        Frase[] fraseObt =new Gson().fromJson(response.toString(),Frase[].class);
                                        frases.addAll(Arrays.asList(fraseObt));
                                    }catch(Exception ex){
                                        frases=null;
                                    }finally {
                                        flAdapter.notifyDataSetChanged();
                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            frases=null;
                            flAdapter.notifyDataSetChanged();
                        }
                    });
                    requestQueue.add(jsonArrayRequest);
                }
            }
        });

    }



}
