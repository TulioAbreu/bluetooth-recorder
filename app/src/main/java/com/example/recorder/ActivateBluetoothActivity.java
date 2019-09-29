package com.example.recorder;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class ActivateBluetoothActivity extends Activity {
    final int REQUEST_ENABLE_BT = 1;
    final int REQUEST_SELECT_DEVICE = 2;

    BluetoothAdapter btAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        btAdapter = BluetoothAdapter.getDefaultAdapter();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (btAdapter == null) {
            Toast.makeText(
                    this,
                    "Your device does not have support for Bluetooth",
                    Toast.LENGTH_LONG);
            finish();
        }

        if (! btAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }

        Intent selectDeviceIntent = new Intent(this, SelectDeviceActivity.class);
        startActivityForResult(selectDeviceIntent, REQUEST_SELECT_DEVICE);
    }
}
