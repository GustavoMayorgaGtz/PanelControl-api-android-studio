package com.bluetooth.hidrosensorbluetooth;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.HashMap;
import java.util.Map;

public class Runneables {
    public static Api api;
    public static Context context;
    public static AlertDialog.Builder alert;


    Runneables(Api api) {
        this.api = api;
    }

    /********************REQUEST1**********************/
    public static String response1;
    public static int iterator = 0;
    public static Handler trigger1 = new Handler();
    public static Runnable notificacion = new Runnable() {
        @Override
        public void run() {
            if (iterator > 0) {
                Toast.makeText(context, "Espera " + iterator + " segundos.", Toast.LENGTH_SHORT).show();
                iterator--;
                trigger1.postDelayed(this, 3000);

                if (iterator == 1) {
                    System.out.println("Iterator: " + iterator);
                    System.out.println("Lanzando peticion GET DEFAULT");
                    Api api = new Api(context);
                    api.get("http://192.168.4.1", 1);
                    trigger1.postDelayed(getData, 1000);
                }
            }
        }
    };
    public static Runnable getData = new Runnable() {
        @Override
        public void run() {
            response1 = api.RESPONSE;
            System.out.println("/******RESPUESTA*****/");
            System.out.println(response1);
            if (response1 != null) {
                /***************************/
                if (response1.equals("ok")) {
                    iterator = 10;
                    trigger1.post(notificacion);
                }
                /*************************/
                if (response1.contains("http")) {
                    SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                    SharedPreferences.Editor myEditor = myPreferences.edit();
                    myEditor.putString("Dic", response1);
                    myEditor.commit();

                    String preference = myPreferences.getString("Dic", "DEFAULT");
                    System.out.println(preference);

                    trigger1.postDelayed(request2, 5000);
                }
                /*************************/
                if (response1.contains("finish")) {
                    alert = new AlertDialog.Builder(context);
                    alert.setTitle("Registro Completado");
                    SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                     Intent i = new Intent(context, Finish.class);
                     context.startActivity(i);
                }
            }else
            {
                trigger1.postDelayed(this, 1000);
            }

        }
    };

    public void request1(Context context) {
        this.context = context;
        trigger1.postDelayed(getData, 1000);
    }

    public static Runnable request2 = new Runnable() {
        @Override
        public void run() {
            Api api = new Api(context);
            Map<String, String> params = new HashMap<String, String>();
            params.put("username","");
            System.out.println("Entrando a reset");
            api.post("http://192.168.4.1/reset", params, 2);
            trigger1.postDelayed(getData, 1000);
        }
    };

    /*************************REQUEST1(CLOSE)*****************************/

}
