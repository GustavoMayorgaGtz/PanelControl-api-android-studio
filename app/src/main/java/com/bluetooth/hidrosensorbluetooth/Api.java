package com.bluetooth.hidrosensorbluetooth;

import android.content.Context;
import java.util.Map;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Api {
    Context context;
    public static String RESPONSE;
/***************************************************************************************/
    Api(Context context) {
        this.context = context;
    }
   /*************************************************************************************/
    public void post(String url, Map<String, String> body) {
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
    public void get(String url) {
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
            }
        });
        queue.add(stringRequest);
    }
    /*************************************************************************************/
}
