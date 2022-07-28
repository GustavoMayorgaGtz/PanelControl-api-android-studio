package com.bluetooth.hidrosensorbluetooth;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.widget.Toast;

import java.util.List;

public class Conexion {
    String networkSSID = "HidroSensorConfig";
    String networkPass = "";
    WifiConfiguration conf = new WifiConfiguration();
    String ssid;
    WifiManager wifiManager;
    Handler trigger = new Handler();
    Context context;
    public static String status = "disconnected";

    Runnable checkConnection = new Runnable() {
        @Override
        public void run() {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                // Si hay conexión a Internet en este momento
                System.out.println(networkInfo.getState());
                System.out.println(networkInfo.isConnected());
                System.out.println(networkInfo.getExtraInfo());
                if (networkInfo.getExtraInfo().equals("\"HidroSensorConfig\"") && networkInfo.getState()== NetworkInfo.State.CONNECTED && networkInfo.isConnected()) {
                    status = "connected";
                    System.out.println("/***********CONECTADO****************/");
                    trigger.postDelayed(this, 1000);
                } else {
                    trigger.post(run);
                    status = "disconnected";
                }
            } else {
                trigger.post(run);
                status = "disconnected";
                // No hay conexión a Internet en este momento
            }

            System.out.println("Monitoreando la conexion:");



        }
    };
    public Runnable run = new Runnable() {
        @Override
        public void run() {
            System.out.println("Tratando de conectar");
            wifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);
            if ((wifiManager.isWifiEnabled() == false)) {
                Toast.makeText(context, "Conectando a WIFI...", Toast.LENGTH_LONG).show();
                wifiManager.setWifiEnabled(true);
            }
            wifiManager.addNetwork(conf);
            @SuppressLint("MissingPermission") List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
            for (WifiConfiguration i : list) {
                if (i.SSID != null && i.SSID.equals("\"" + networkSSID + "\"")) {
                    wifiManager.enableNetwork(i.networkId, true);
                    wifiManager.reconnect();

                    break;
                }
            }
            trigger.postDelayed(checkConnection, 5000);
        }
    };

    Conexion(Context context) {
        this.context = context;
        conf.SSID = "\"" + networkSSID + "\"";
        conf.preSharedKey = "\"" + networkPass + "\"";
        trigger.post(run);
    }
}
