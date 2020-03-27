package com.example.android.pictureinpicture._2;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android.pictureinpicture.R;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class BActivity extends AppCompatActivity {
    private static final String TAG = BActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        ActivityCache.getInstance().addActivity(this);

        setContentView(R.layout.activity_b);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        ActivityCache.getInstance().removeActivity(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPrepareDestroyAppEvent(PrepareDestroyAppEvent event) {
        Log.d(TAG, "onMessageEvent: ");
        finish();
    }
}
