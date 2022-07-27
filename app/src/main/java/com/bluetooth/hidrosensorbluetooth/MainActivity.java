package com.bluetooth.hidrosensorbluetooth;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;



public class MainActivity extends AppCompatActivity {
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

        Peticiones();

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
    void Peticiones()
    {
        Map<String, String> params = new HashMap<String, String>();
        params.put("iduser", "Gustavo");
        api = new Api(this);
        api.post("http://hidrosensor.herokuapp.com", params);
        textView = (TextView) findViewById(R.id.textView6);
        Runneables run = new Runneables(api);
        run.request1(textView);
    }
    /*************************************************************************************/
}