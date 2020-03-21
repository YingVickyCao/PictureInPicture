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
        setContentView(R.layout.activity_first);

        findViewById(R.id.showMoviePage).setOnClickListener(v -> showMoviePage());
    }

    private void showMoviePage() {
        startActivity(new Intent(this, MovieAActivity.class));
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
    }
}
