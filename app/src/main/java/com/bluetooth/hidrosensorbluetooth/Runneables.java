package com.bluetooth.hidrosensorbluetooth;

import android.os.Handler;
import android.widget.TextView;

public class Runneables {
   public static Api api;
   public static TextView tv;

    Runneables(Api api) {
        this.api = api;
    }

    /********************REQUEST1**********************/
    public static String response1;
    public static Handler trigger1 = new Handler();
    public static  Runnable getData = new Runnable() {
        @Override
        public void run() {
            response1 = api.RESPONSE;
            tv.setText(response1);
            if(response1 == null || response1 == "")
            {
                trigger1.postDelayed(this, 100);
            }
        }
    };

    public void request1(TextView tv)
    {
         this.tv = tv;
        trigger1.postDelayed(getData, 1000);
    }
    /*************************REQUEST1(CLOSE)*****************************/

}
