/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.pictureinpicture._2;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.android.pictureinpicture.R;

public class MovieBActivity extends AppCompatActivity {
    private static final String TAG = MovieBActivity.class.getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.activity_movie);
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_main, new MovieFragment(), MovieFragment.TAG).commit();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.activity_main);
        if (fragment instanceof IPip) {
            ((IPip) fragment).onRestart();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: ");
    }

    @Override
    protected void onStop() {
        // On entering Picture-in-Picture mode, onPause is called, but not onStop.
        // For this reason, this is the place where we should pause the video playback.
        Log.e(TAG, "onStop: ");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
    }

    @Override
    protected void onUserLeaveHint() {
        super.onUserLeaveHint();

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.activity_main);
        if (fragment instanceof IPip) {
            ((IPip) fragment).onUserLeaveHint();
        }
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
//        Log.d(TAG, "onConfigurationChanged: newConfig=" + newConfig.orientation);
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, Configuration newConfig) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig);
        Log.d(TAG, "onPictureInPictureModeChanged:isInPictureInPictureMode=" + isInPictureInPictureMode + ",newConfig=" + newConfig.orientation + ",screen=" + getString(R.string.screen));
    }

//    @Override
//    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
//        super.onPictureInPictureModeChanged(isInPictureInPictureMode);
//        Log.d(TAG, "onPictureInPictureModeChanged:isInPictureInPictureMode=" + isInPictureInPictureMode);
//    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        Log.d(TAG, "onWindowFocusChanged: hasFocus=" + hasFocus);
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.activity_main);
        if (fragment instanceof IPip) {
            ((IPip) fragment).onWindowFocusChanged(hasFocus);
        }
    }
}