package com.bluetooth.hidrosensorbluetooth;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.HandlerThread;
import android.widget.Toast;

import java.util.List;

import android.os.Handler;

public class CargarRed extends AppCompatActivity {
    Conexion conexion;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
              if(conexion.status == "connected")
              {
                  Intent i = new Intent(CargarRed.this, MainActivity.class);
                  startActivity(i);
              }else
              {
                  handler.postDelayed(this, 1000);
              }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cargar_red);
        this.conexion = new Conexion(this);
        handler.postDelayed(runnable,1000);
    }
}