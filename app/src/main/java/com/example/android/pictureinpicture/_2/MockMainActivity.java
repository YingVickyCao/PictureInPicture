package com.example.android.pictureinpicture._2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.android.pictureinpicture.R;

import org.greenrobot.eventbus.EventBus;

public class MockMainActivity extends Activity {
    private static final String TAG = MockMainActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityCache.getInstance().addActivity(this);

        setContentView(R.layout.activity_mock_main);

        findViewById(R.id.showMoviePage).setOnClickListener(v -> showMoviePage());

        findViewById(R.id.alertDialog_by_pip).setOnClickListener(v -> alertDialog_by_pip());
        findViewById(R.id.alertDialog_by_not_pip).setOnClickListener(v -> alertDialog_by_not_pip());

        findViewById(R.id.toast_by_pip).setOnClickListener(v -> toast_by_pip());
        findViewById(R.id.toast_by_not_pip).setOnClickListener(v -> toast_by_not_pip());

        findViewById(R.id.openBPage_by_pip).setOnClickListener(v -> openBPage_by_pip());
        findViewById(R.id.openBPage_by_not_pip).setOnClickListener(v -> openBPage_by_not_pip());
    }

    private void showMoviePage() {
        startActivity(new Intent(this, MovieAActivity.class));
    }

    // 若 current activity is PIP,则Dialog 出现在 PIP 窗口中。
    private void alertDialog_by_pip() {
        Activity activity = ActivityCache.getInstance().getCurrentActivity();
        Test.showADialog(activity);
    }

    private void alertDialog_by_not_pip() {
        Activity activity = ActivityCache.getInstance().getCurrentActivityNotPIP();
        Test.showADialog(activity);
    }


    // Toast 位置不受影响. 若 current activity is PIP,则Toast 默认出现在屏幕的正下方。
    private void toast_by_pip() {
        Activity activity = ActivityCache.getInstance().getCurrentActivity();
        Test.showToast(activity);
    }

    private void toast_by_not_pip() {
        Activity activity = ActivityCache.getInstance().getCurrentActivityNotPIP();
        Test.showToast(activity);
    }

    public void openBPage_by_pip() {
        // B is PIP
//        Activity activity = ActivityCache.getInstance().getCurrentActivity();
//        Log.d(TAG, "openBPage_by_pip: " + activity.getClass().getSimpleName());
//        Intent intent = new Intent(activity, BActivity.class); // activity = A
//        activity.startActivity(intent); // start from activity = A

        // B is  PIP
        Activity activity = ActivityCache.getInstance().getCurrentActivityNotPIP();
        Log.d(TAG, "openBPage_by_not_pip: " + activity.getClass().getSimpleName());
        Intent intent = new Intent(activity, BActivity.class); //activity = Main
        Activity a = ActivityCache.getInstance().getCurrentActivity();
        a.startActivity(intent); // start from activity = A
    }

    private void openBPage_by_not_pip() {
        // B is Not PIP
        Activity activity = ActivityCache.getInstance().getCurrentActivity();
        Log.d(TAG, "openBPage_by_pip: " + activity.getClass().getSimpleName());
        Intent intent = new Intent(activity, BActivity.class); // activity = A
        startActivity(intent); // start from activity = Main

        // B is not PIP
//        Activity activity = ActivityCache.getInstance().getCurrentActivityNotPIP();
//        Log.d(TAG, "openBPage_by_not_pip: " + activity.getClass().getSimpleName());
//        Intent intent = new Intent(activity, BActivity.class); //activity = Main
//        activity.startActivity(intent); // start from activity = Main
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
        EventBus.getDefault().post(new PrepareDestroyAppEvent());
    }
}
