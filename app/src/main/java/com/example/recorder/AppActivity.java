package com.example.recorder;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.Nullable;

public class AppActivity extends Activity {
    final int BUTTON_STATUS_RECORD = 0;
    final int BUTTON_STATUS_STOP = 1;

    int buttonStatus;
    Button recordButton;
    BluetoothService btService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app);
        btService = BluetoothService.getInstance();
        recordButton = findViewById(R.id.record_stop_button);
        recordButton.setOnClickListener((v) -> {
            onClickRecordStop();
        });
        buttonStatus = BUTTON_STATUS_RECORD;
        updateButtonStyle(buttonStatus);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        btService.disable();
    }

    protected void updateButtonStyle(int buttonStatus) {
        if (recordButton == null) {
            return;
        }

        switch (buttonStatus) {
            case BUTTON_STATUS_RECORD: {
                recordButton.setText("RECORD");
                recordButton.setBackgroundColor(Color.rgb(0, 210, 0));
            } break;
            case BUTTON_STATUS_STOP: {
                recordButton.setText("STOP");
                recordButton.setBackgroundColor(Color.rgb(210, 0, 0));
            } break;
        }
    }

    protected void onClickRecordStop() {
        switch (buttonStatus) {
            case BUTTON_STATUS_RECORD: {
                btService.sendMessage("attribute:recording;value:true");
                Log.d("debug", "Sent 'attribute:recording;value:true' for server");

                buttonStatus = BUTTON_STATUS_STOP;
            } break;
            case BUTTON_STATUS_STOP: {
                btService.sendMessage("attribute:recording;value:false");
                Log.d("debug", "Sent 'attribute:recording;value:false' for server");

                buttonStatus = BUTTON_STATUS_RECORD;
            } break;
            default: {
                Log.e("error", "[AppActivity::OnClickRecordStop] Invalid button status.");
            }
        }

        updateButtonStyle(buttonStatus);
    }
}
