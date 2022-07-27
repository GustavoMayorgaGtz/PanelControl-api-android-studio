package com.bluetooth.hidrosensorbluetooth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import android.os.Handler;

public class CargarRed extends AppCompatActivity {

    String networkSSID = "HidroSensorConfig";
    String networkPass = "";
    WifiConfiguration conf = new WifiConfiguration();
    String ssid;
    WifiManager wifiManager;
    Handler trigger = new Handler();
    Context context;
    Runnable checkConnection = new Runnable() {
        @Override
        public void run() {
            System.out.println(wifiManager.EXTRA_BSSID);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            ssid = wifiInfo.getSSID();
            System.out.println("nombre de la red definida");
            System.out.println(ssid);
            if (ssid.equals("\"HidroSensorConfig\"")) {
                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            } else {

                System.out.println("Reconectando");
                System.out.println(ssid);
                trigger.post(run);
            }
        }
    };
    public  Runnable run = new Runnable() {
        @Override
        public void run() {
            wifiManager = (WifiManager) getApplicationContext().getSystemService(context.WIFI_SERVICE);

            if ((wifiManager.isWifiEnabled() == false)) {
                Toast.makeText(CargarRed.this, "Conectando a WIFI...", Toast.LENGTH_LONG).show();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargar_red);
        context = this;
        //trigger.postDelayed(run,100);
        conf.SSID = "\"" + networkSSID + "\"";
        conf.preSharedKey = "\"" + networkPass + "\"";
        trigger.post(run);
    }
}