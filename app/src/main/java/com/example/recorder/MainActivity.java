package com.example.recorder;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    Button recordButton;
    enum ButtonMode {
        RECORD_BUTTON_START,
        RECORD_BUTTON_STOP
    };
    ButtonMode recordButtonMode = ButtonMode.RECORD_BUTTON_START;

    protected boolean sendMessage(String msg) {
        // TODO: Implement bluetooth msg
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recordButton = (Button) findViewById(R.id.recordButton);

        recordButton.setOnClickListener(new View.OnClickListener() {
            public void SetButtonStart() {
                recordButton.setText("Record");
                recordButton.setBackgroundColor(Color.rgb(0, 210, 0));
                recordButtonMode = ButtonMode.RECORD_BUTTON_START;
                sendMessage("record-status;1");
            }

            public void SetButtonStop() {
                recordButton.setText("Stop");
                recordButton.setBackgroundColor(Color.rgb(210, 0, 0));
                recordButtonMode = ButtonMode.RECORD_BUTTON_STOP;
                sendMessage("record-status;0");
            }

            @Override
            public void onClick(View v) {
                switch (recordButtonMode) {
                    case RECORD_BUTTON_START: {
                        SetButtonStop();
                    } break;
                    case RECORD_BUTTON_STOP: {
                        SetButtonStart();
                    } break;
                }
            }
        });
    }
}
