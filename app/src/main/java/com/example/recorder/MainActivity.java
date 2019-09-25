package com.example.recorder;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    ToggleButtonController toggleButtonController;

    protected boolean sendMessage(String msg) {
        // TODO: Implement bluetooth msg
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toggleButtonController = new ToggleButtonController(
                (Button) findViewById(R.id.recordButton),
                "Record",
                Color.rgb(0, 210, 0),
                "Stop",
                Color.rgb(210, 0, 0)
        );


        toggleButtonController.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int buttonStatus = toggleButtonController.executeOnClick();
                if (buttonStatus == 1) {
                    sendMessage("record-status;1");
                }
                else if (buttonStatus == 2) {
                    sendMessage("record-status;0");
                }
                else {
                    Log.e("error", "Invalid toggle button state.");
                }
            }
        });
    }
}
