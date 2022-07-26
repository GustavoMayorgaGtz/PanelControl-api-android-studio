package com.bluetooth.hidrosensorbluetooth;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.os.Handler;
import java.util.HashMap;
import java.util.Map;



public class MainActivity extends AppCompatActivity {
    Api api;
    TextView textView;
    /*****/
    private String response1;

    /*****/
    Handler trigger1 = new Handler();
    Runnable getData = new Runnable() {
        @Override
        public void run() {
            response1 = api.RESPONSE;
            textView.setText("respusta: " + api.RESPONSE);
            if(response1 == null || response1 == "")
            {
                trigger1.postDelayed(this, 100);
            }
        }
    };
    /*************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Peticiones();
    }
    /*************************************************************************************/
    void Peticiones()
    {
        Map<String, String> params = new HashMap<String, String>();
        params.put("iduser", "Gustavo");
        api = new Api(this);
        api.post("http://hidrosensor.herokuapp.com", params);
        textView = (TextView) findViewById(R.id.textView6);
        trigger1.postDelayed(getData, 1000);
    }
    /*************************************************************************************/
}