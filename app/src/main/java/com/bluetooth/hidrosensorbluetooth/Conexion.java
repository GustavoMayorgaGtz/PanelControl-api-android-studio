package com.bluetooth.hidrosensorbluetooth;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

public class Conexion {
    String networkSSID = "HidroSensorConfig";
    String networkPass = "";
    WifiConfiguration conf = new WifiConfiguration();
    WifiManager wifiManager;
    Handler trigger = new Handler();
    Context context;
    public static String status = "disconnected";

    Runnable checkConnection = new Runnable() {


        @Override
        public void run() {
            WifiInfo info = wifiManager.getConnectionInfo();
            System.out.println(info.getSSID());
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                //TODO: Hay conexion
                if ((networkInfo.getExtraInfo().equals("\"HidroSensorConfig\"") || info.getSSID().equals("\"HidroSensorConfig\"")) && networkInfo.getState() == NetworkInfo.State.CONNECTED && networkInfo.isConnected()) {
                    status = "connected";
                    System.out.println("/***********CONECTADO****************/");
                    trigger.postDelayed(this, 500);
                } else {
                    trigger.post(run);
                    status = "disconnected";
                }
            } else {
                //TODO: No hay conexion
                trigger.post(run);
                status = "disconnected";
            }

            System.out.println("Monitoreando la conexion:");
        }
    };
    public Runnable run = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.R)
        @Override
        public void run() {

            System.out.println("Tratando de conectar");
            if(context != null)
            wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo;
            wifiInfo = wifiManager.getConnectionInfo();
            if(wifiInfo.getSupplicantState() == SupplicantState.COMPLETED)
            {
                System.out.println(wifiInfo.getSSID());
            }
            if ((!wifiManager.isWifiEnabled())) {
                Toast.makeText(context, "Conectando a WIFI...", Toast.LENGTH_LONG).show();
                wifiManager.setWifiEnabled(true);
            }
            wifiManager.addNetwork(conf);
            trigger.postDelayed(checkConnection, 7000);
        }
    };

    Conexion(Context context) {
        this.context = context;
        conf.SSID = "\"" + networkSSID + "\"";
        conf.preSharedKey = "\"" + networkPass + "\"";
        trigger.post(run);
    }


}
