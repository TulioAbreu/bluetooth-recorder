package com.example.recorder;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class SelectDeviceActivity extends Activity {
    ListView devicesList = null;
    Button okButton = null;
    BluetoothAdapter btAdapter;
    List list;

    String selectedAddress;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectdevices);
        devicesList = (ListView) findViewById(R.id.devList);
        okButton = (Button) findViewById(R.id.button);
        btAdapter = BluetoothAdapter.getDefaultAdapter();
        list = new LinkedList();
        selectedAddress = "";
    }

    @Override
    protected void onStart() {
        super.onStart();
        Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                list.add(device.getName() + '\n' + device.getAddress());
            }
        }
        final ArrayAdapter btArray = new ArrayAdapter(this,android.R.layout.simple_list_item_1, list);
        devicesList.setAdapter(btArray);

        devicesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View v, int position,
                                    long arg3) {
                String value = (String)adapter.getItemAtPosition(position);

                String[] valueChunks = value.split("\n");
                selectedAddress = valueChunks[valueChunks.length-1];
                Log.d("debug", "Selected address: " + selectedAddress);
            }
        });


        okButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
            @Override
            public void onClick(View v) {
                if (selectedAddress != "") {
                    BluetoothService btService = BluetoothService.getInstance();
                    Log.d("debug", "Setting remote address to: " + selectedAddress);
                    btService.setRemoteDevice(selectedAddress);

                    Intent appActivityIntent = new Intent(v.getContext(), AppActivity.class);
                    startActivity(appActivityIntent);
                }
            }
        });
    }
}
