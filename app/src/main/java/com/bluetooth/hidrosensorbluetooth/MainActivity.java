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
import android.os.HandlerThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



public class MainActivity extends AppCompatActivity {
    /*******************************Objetos de Conexion*************************************/
    Conexion conexion;
    /*************************************************************************************/
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

        conexion = new Conexion(this);

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
        api.post("http://192.168.4.1", params, 1);
        Runneables runa = new Runneables(api);
        runa.request1(this);
    }
    /*************************************************************************************/
}