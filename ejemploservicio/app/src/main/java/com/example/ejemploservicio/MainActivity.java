package com.example.ejemploservicio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.telephony.mbms.StreamingServiceInfo;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ejemploservicio.adaptadores.PersonajeAdaptador;
import com.example.ejemploservicio.clases.Personaje;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<Personaje> listaPersonaje = new ArrayList<>();
    RecyclerView rcv_personajes;
    Button btnGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rcv_personajes = findViewById(R.id.rcv_personajes);
        btnGuardar = findViewById(R.id.btnGuardar);
        cargarInformacion();
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarInformacion();
            }
        });
    }

    public void guardarInformacion(){
        HashMap<String, Object> json = new HashMap<>();
        json.put("id", 101); // json.put("name", edtName.getText().toString());
        json.put("title", "foo");
        json.put("body", "bar");
        json.put("userId", 1);
        JSONObject jsonObject = new JSONObject(json);
        String url = "https://jsonplaceholder.typicode.com/posts";
        JsonObjectRequest myRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                respuestaGuardar(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error en el servidor", Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
        rq.add(myRequest);
    }

    public void respuestaGuardar(JSONObject respuesta){
        try {
            if (respuesta.getInt("id")==101)
                Toast.makeText(getApplicationContext(), "Se guardó correctamente", Toast.LENGTH_LONG).show();
            else
                Toast.makeText(getApplicationContext(), "Error en el servidor", Toast.LENGTH_LONG).show();
        } catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error"+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    private void cargarInformacion() {
        String url="https://rickandmortyapi.com/api/character";
        StringRequest myRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    recibirRespuesta(new JSONObject(response));
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Error en el servidor", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error en el servidor", Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
        rq.add(myRequest);
    }

    private void recibirRespuesta(JSONObject respuesta) {
        try {
            for (int i =0; i<=respuesta.getJSONArray("results").length(); i++){
                String nombre = respuesta.getJSONArray("results").getJSONObject(i).getString("name");
                String estado = respuesta.getJSONArray("results").getJSONObject(i).getString("status");
                String especie = respuesta.getJSONArray("results").getJSONObject(i).getString("species");
                String imagen = respuesta.getJSONArray("results").getJSONObject(i).getString("image");

                Personaje p = new Personaje(nombre, especie, estado, imagen);
                listaPersonaje.add(p);
                rcv_personajes.setLayoutManager(new LinearLayoutManager(this));
                rcv_personajes.setAdapter(new PersonajeAdaptador(listaPersonaje));
            }
        } catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error"+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}