package com.example.android.pictureinpicture;

public interface IPip {
    void onRestart();

    void onUserLeaveHint();

    void onWindowFocusChanged(boolean hasFocus);
}
