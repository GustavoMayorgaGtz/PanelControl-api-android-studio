package com.bluetooth.hidrosensorbluetooth;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class MainActivity extends AppCompatActivity {
     String networkSSID = "HidroSensorConfig";
     String networkPass = "";
     WifiConfiguration conf = new WifiConfiguration();
     String ssidSaved;
     WifiManager wifiManager;
     Handler trigger = new Handler();
     Context context;
     Runnable checkConnection = new Runnable() {
        @Override
        public void run() {
            //System.out.println(wifiManager.EXTRA_BSSID);
            WifiInfo wifiInfo = wifiManager.getConnectionInfo();
            ssidSaved = wifiInfo.getSSID();
           // System.out.println("nombre de la red definida");
            //System.out.println(ssidSaved);
            if (ssidSaved.equals("\"HidroSensorConfig\"")) {
                trigger.postDelayed(this, 5000);
            } else {

               // System.out.println("Reconectando");
             //   System.out.println(ssidSaved);
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

                    wifiManager.enableNetwork(i.networkId, true);
                    wifiManager.reconnect();

                    break;
                }
            }
            trigger.postDelayed(checkConnection, 5000);
        }
    };
/*********************************************************************************/


    Api api;
    TextView textView;
    EditText username, tanqueid, ssid, password;
    Button enviar;
    AlertDialog.Builder alertDialog;

    /*************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        context = this;
        //trigger.postDelayed(run,100);
        conf.SSID = "\"" + networkSSID + "\"";
        conf.preSharedKey = "\"" + networkPass + "\"";
        username = (EditText) findViewById(R.id.username);
        tanqueid = (EditText) findViewById(R.id.tanqueID);
        ssid = (EditText) findViewById(R.id.ssid);
        password = (EditText) findViewById(R.id.password);
        enviar = (Button) findViewById(R.id.btn_Enviar);
        enviar.setOnClickListener(this::onClick);
        alertDialog = new AlertDialog.Builder(this);

    }
    /*************************************************************************************/

    public void onClick(View view)
    {
        System.out.println("Entro por aqui");
        switch(view.getId())
        {


            case R.id.btn_Enviar:{
                String usernameText = username.getText().toString();
                String tanqueidText = tanqueid.getText().toString();
                String ssidText = ssid.getText().toString();
                String passwordText = password.getText().toString();
                Functions functions = new Functions();
                if(functions.CheckisEmpty(usernameText) && functions.CheckisEmpty(tanqueidText) && functions.CheckisEmpty(ssidText) && functions.CheckisEmpty(passwordText))
                {
                     SendData(usernameText,tanqueidText, ssidText, passwordText);
                }else
                {
                    alertDialog.setTitle("Notificacion");
                    alertDialog.setMessage("Faltan datos por completar en el formulario \uD83D\uDE41");
                    alertDialog.create();
                    alertDialog.show();
                }
                break;
            }
        }
    }
    /*************************************************************************************/
    void SendData(String username, String idtanque, String ssid, String password)
    {
        Map<String, String> params = new HashMap<String, String>();
        params.put("username", username);
        params.put("idtanque", idtanque);
        params.put("ssid", ssid);
        params.put("passid", password);
        api = new Api(this);
        api.post("http://192.168.5.1", params);
        Runneables runa = new Runneables(api);
        runa.request1(this);
        trigger.post(run);
    }
    /*************************************************************************************/
}