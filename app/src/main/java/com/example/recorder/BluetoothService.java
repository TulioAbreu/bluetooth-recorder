package com.example.recorder;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

public class BluetoothService {
    BluetoothAdapter adapter;
    BluetoothDevice pairedDevice;
    BluetoothSocket socket;

    InputStream input = null;
    OutputStream output = null;

    String connectionStr;

    private static final UUID MY_UUID = UUID.fromString("fa87c0d0-afac-11de-8a39-0800200c9a66");

    static BluetoothService instance = null;
    public static BluetoothService getInstance() {
        if (instance == null) {
           instance = new BluetoothService();
        }
        return instance;
    }

    private BluetoothService() {
        adapter = BluetoothAdapter.getDefaultAdapter();
    }

    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1)
    public void setRemoteDevice(String connectionStr) {
        this.connectionStr = connectionStr;
    }

    public void sendMessage(String msg) {
        pairedDevice = adapter.getRemoteDevice(connectionStr);
        if (pairedDevice == null) {
            Log.e("error", "Paired device failed.");
            return;
        }

        try {
            socket = pairedDevice.createRfcommSocketToServiceRecord(MY_UUID);
            socket.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (socket != null) {
                input = socket.getInputStream();
                output = socket.getOutputStream();

                if (output != null) {
                    try {
                        output.write(msg.getBytes());
                    }
                    catch (IOException e) {
                        Log.e("error", "Could not send bluetooth message");
                    }
                }

            }
        }
        catch (IOException e) {
            Log.e("error", "could not get input or output stream");
        }
    }

    public void disable() {
        try {
            socket.close();
            adapter.disable();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
