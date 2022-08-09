package com.bluetooth.hidrosensorbluetooth;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.LinkProperties;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.TransportInfo;
import android.net.wifi.ScanResult;
import android.net.wifi.SupplicantState;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.os.HandlerThread;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import android.os.Handler;

import org.w3c.dom.Text;

public class CargarRed extends AppCompatActivity {
    Conexion conexion;
    TextView tv;
    Handler handler = new Handler();
    AlertDialog.Builder alert;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            if (conexion.status == "connected") {
                Intent i = new Intent(CargarRed.this, MainActivity.class);
                startActivity(i);
            } else {
                handler.postDelayed(this, 1000);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargar_red);
        this.conexion = new Conexion(this);
        tv = (TextView) findViewById(R.id.textView2);
        alert = new AlertDialog.Builder(this);
        handler.postDelayed(runnable, 1000);
        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String resultado = myPreferences.getString("Dic", "DEFAULT");
        Functions functions = new Functions();

        ActivityCompat.requestPermissions(this,
                new String[]{
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_WIFI_STATE,
                        },
                PackageManager.PERMISSION_GRANTED
        );


        if (!functions.CheckisEmpty(resultado)) {
            alert.setTitle("¿Deseas reiniciar el registro?");
            alert.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(CargarRed.this);
                    SharedPreferences.Editor editor = myPreferences.edit();
                    editor.clear();
                    editor.putString("Dic","");
                    editor.commit();
                    String resultado = myPreferences.getString("Dic", "DEFAULT");
                    if (!functions.CheckisEmpty(resultado)) {
                        Intent i2 = new Intent(CargarRed.this, CargarRed.class);
                        startActivity(i2);
                    }
                }
            });

            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent i3 = new Intent(CargarRed.this, Finish.class);
                    startActivity(i3);
                }
            });

            alert.create();
            alert.show();
        }
    }

}