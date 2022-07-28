package com.bluetooth.hidrosensorbluetooth;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
            System.out.println("Monitoreando la conexion");
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            ssid = wifiInfo.getSSID();
            if (ssid.equals("\"HidroSensorConfig\"")) {
                  status = "connected";
            } else {
                trigger.post(run);
            }
        }
    };
    public  Runnable run = new Runnable() {
        @Override
        public void run() {
            wifiManager = (WifiManager) context.getSystemService(context.WIFI_SERVICE);

            if ((wifiManager.isWifiEnabled() == false)) {
                Toast.makeText(context, "Conectando a WIFI...", Toast.LENGTH_LONG).show();
                wifiManager.setWifiEnabled(true);
            }
            wifiManager.addNetwork(conf);
            @SuppressLint("MissingPermission") List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
            for (WifiConfiguration i : list) {
                if (i.SSID != null && i.SSID.equals("\"" + networkSSID + "\"")) {
                    wifiManager.disconnect();
                    wifiManager.enableNetwork(i.networkId, true);
                    wifiManager.reconnect();

                    break;
                }
            }
            trigger.postDelayed(checkConnection, 5000);
        }
    };

    Conexion(Context context)
    {
        this.context = context;
        conf.SSID = "\"" + networkSSID + "\"";
        conf.preSharedKey = "\"" + networkPass + "\"";
        trigger.post(run);
    }
}
