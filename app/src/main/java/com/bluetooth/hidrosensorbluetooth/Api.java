package com.bluetooth.hidrosensorbluetooth;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

import java.util.Map;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Api {
    public static Context context;
    public static String RESPONSE;
    public static AlertDialog.Builder alert;
/***************************************************************************************/
    Api(Context context) {
        this.context = context;
        alert = new AlertDialog.Builder(context);
    }
   /*************************************************************************************/
    public void post(String url, Map<String, String> body, int id) {
        //id es un identidicador de consulta para que cuando nos de error tenga el mensaje correspondiente para el usuario
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        /**RESPUESTA**/
                        RESPONSE = response;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                /**RESPUESTA**/
                RESPONSE = error.toString();
                alert.setTitle("Error");
                String status = error.getMessage();
                alert.setMessage(status);
                alert.create();
                alert.show();

                switch (id)
                {
                    case 1:{
                        alert.setTitle("Vuelve a intentarlo");
                        alert.setMessage("Asegurate de estar conectado a Hidrosensor y vuelve a intentarlo");
                        alert.setPositiveButton("Aceptar", null);
                        alert.create();
                        alert.show();
                        break;
                    }
                    case 2:{
                        alert.setTitle("Algo salio mal :(");
                        alert.setMessage("Asegurate de estar conectado a Hidrosensor y vuelve a intentarlo. La peticion no fue exitosa");
                        alert.setPositiveButton("Aceptar", null);
                        alert.create();
                        alert.show();
                        break;
                    }
                }
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return body;
            }
        };
        queue.add(stringRequest);
    }
    /*************************************************************************************/
    public void get(String url, int id) {
        //id es un identidicador de consulta para que cuando nos de error tenga el mensaje correspondiente para el usuario
        RequestQueue queue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        /**RESPUESTA**/
                        RESPONSE = response;


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                /**RESPUESTA**/
                RESPONSE = error.toString();
                RESPONSE = error.toString();
                alert.setTitle("Error");
                String status = error.getMessage();
                alert.setMessage(status);
                alert.create();
                alert.show();

                switch(id)
                {
                    case 1:
                    {
                        alert.setTitle("Algo salio mal :(");
                        alert.setMessage("Asegurate de estar conectado a Hidrosensor y vuelve a intentarlo");
                        alert.setPositiveButton("Aceptar", null);
                        alert.create();
                        alert.show();
                        break;
                    }
                }
            }
        });
        queue.add(stringRequest);
    }
    /*************************************************************************************/
}
