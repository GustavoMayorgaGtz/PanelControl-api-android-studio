package com.bluetooth.hidrosensorbluetooth;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TextView;

public class Finish extends AppCompatActivity {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
        SharedPreferences myPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String resultado = myPreferences.getString("Dic", "DEFAULT");
        tv = (TextView) findViewById(R.id.result);
        tv.setText(resultado);
    }
}