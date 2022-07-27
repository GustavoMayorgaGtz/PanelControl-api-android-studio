package com.bluetooth.hidrosensorbluetooth;

public class Functions {

    Functions() {

    }

    public boolean CheckisEmpty(String item) {
        if (item.length() == 0 || item == "" || item == null) {
            return false;
        } else {
            return true;
        }
    }

}
