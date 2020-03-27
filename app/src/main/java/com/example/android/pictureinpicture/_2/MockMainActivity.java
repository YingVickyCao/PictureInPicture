package com.example.android.pictureinpicture._2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.android.pictureinpicture.R;

public class MockMainActivity extends Activity {
    private static final String TAG = MockMainActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityCache.getInstance().addActivity(this);

        setContentView(R.layout.activity_first);

        findViewById(R.id.showMoviePage).setOnClickListener(v -> showMoviePage());
        findViewById(R.id.alertDialog).setOnClickListener(v -> alertDialog());
        findViewById(R.id.toast).setOnClickListener(v -> toast());
    }

    private void showMoviePage() {
        startActivity(new Intent(this, MovieAActivity.class));
    }

    // 若 current activity is PIP,则Dialog 出现在 PIP 窗口中。
    private void alertDialog() {
//        Activity activity = ActivityCache.getInstance().getCurrentActivity();
        Activity activity = ActivityCache.getInstance().getCurrentActivityNotPIP();
        Test.showADialog(activity);
    }

    // Toast 位置不受影响. 若 current activity is PIP,则Toast 默认出现在屏幕的正下方。
    private void toast() {
        Activity activity = ActivityCache.getInstance().getCurrentActivity();
        Test.showToast(activity);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");

        ActivityCache.getInstance().removeActivity(this);

        // TODO: 2020/3/26 send Event
    }
}
