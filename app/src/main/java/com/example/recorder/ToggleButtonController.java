package com.example.recorder;

import android.graphics.Color;
import android.widget.Button;

public class ToggleButtonController {
    Button button;

    private enum ButtonMode {
        STATUS_1,
        STATUS_2
    };
    String statusStr1;
    int statusColor1;

    String statusStr2;
    int statusColor2;

    ButtonMode mode = ButtonMode.STATUS_1;

    public ToggleButtonController(Button btn, String txt1, int color1, String txt2, int color2) {
        button = btn;
        statusStr1 = txt1;
        statusColor1 = color1;
        statusStr2 = txt2;
        statusColor2 = color2;
    }

    public int executeOnClick() {
        switch (mode) {
            case STATUS_1: {
                button.setText(statusStr2);
                button.setBackgroundColor(statusColor2);
                mode = ButtonMode.STATUS_2;
                return 1;
            }
            case STATUS_2: {
                button.setText(statusStr1);
                button.setBackgroundColor(statusColor1);
                mode = ButtonMode.STATUS_1;
                return 2;
            }
        }
        return 0;
    }
}
