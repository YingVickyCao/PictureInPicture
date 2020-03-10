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

import android.app.PendingIntent;
import android.app.PictureInPictureParams;
import android.app.RemoteAction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Rational;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.android.pictureinpicture.R;
import com.example.android.pictureinpicture._origin.MediaSessionPlaybackActivity;
import com.example.android.pictureinpicture.widget.MovieView;

import java.util.ArrayList;

/**
 * Demonstrates usage of Picture-in-Picture mode on phones and tablets.
 */
public class MovieFragment extends Fragment implements IPip {
    public static final String TAG = "MovieFragment";

    /**
     * Intent action for media controls from Picture-in-Picture mode.
     */
    private static final String ACTION_MEDIA_CONTROL = "media_control";

    /**
     * Intent extra for media controls from Picture-in-Picture mode.
     */
    private static final String EXTRA_CONTROL_TYPE = "control_type";

    /**
     * The request code for play action PendingIntent.
     */
    private static final int REQUEST_PLAY = 1;

    /**
     * The request code for pause action PendingIntent.
     */
    private static final int REQUEST_PAUSE = 2;

    /**
     * The request code for info action PendingIntent.
     */
    private static final int REQUEST_INFO = 3;

    /**
     * The intent extra value for play action.
     */
    private static final int CONTROL_TYPE_PLAY = 1;

    /**
     * The intent extra value for pause action.
     */
    private static final int CONTROL_TYPE_PAUSE = 2;

    /**
     * The arguments to be used for Picture-in-Picture mode.
     */
    private PictureInPictureParams.Builder mPictureInPictureParamsBuilder;

    /**
     * This shows the video.
     */
    private MovieView mMovieView;

    /**
     * The bottom half of the screen; hidden on landscape
     */
    private ScrollView mScrollView;

    /**
     * A {@link BroadcastReceiver} to receive action item events from Picture-in-Picture mode.
     */
    private BroadcastReceiver mReceiver;

    private String mPlay;
    private String mPause;

    private View mCloseBtn;
    /**
     * Callbacks from the {@link MovieView} showing the video playback.
     */
    private MovieView.MovieListener mMovieListener = new MovieView.MovieListener() {

        @Override
        public void onMovieStarted() {
            // We are playing the video now. In PiP mode, we want to show an action item to
            // pause
            // the video.
            updatePictureInPictureActions(R.drawable.ic_pause_24dp, mPause, CONTROL_TYPE_PAUSE, REQUEST_PAUSE);
        }

        @Override
        public void onMovieStopped() {
            // The video stopped or reached its end. In PiP mode, we want to show an action
            // item to play the video.
            updatePictureInPictureActions(R.drawable.ic_play_arrow_24dp, mPlay, CONTROL_TYPE_PLAY, REQUEST_PLAY);
        }

        @Override
        public void onMovieMinimized() {
            // The MovieView wants us to minimize it. We enter Picture-in-Picture mode now.
            minimize();
        }
    };

    /**
     * Update the state of pause/resume action item in Picture-in-Picture mode.
     *
     * @param iconId      The icon to be used.
     * @param title       The title text.
     * @param controlType The type of the action. either {@link #CONTROL_TYPE_PLAY} or {@link
     *                    #CONTROL_TYPE_PAUSE}.
     * @param requestCode The request code for the {@link PendingIntent}.
     */
    void updatePictureInPictureActions(@DrawableRes int iconId, String title, int controlType, int requestCode) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return;
        }
        final ArrayList<RemoteAction> actions = new ArrayList<>();

        // This is the PendingIntent that is invoked when a user clicks on the action item.
        // You need to use distinct request codes for play and pause, or the PendingIntent won't
        // be properly updated.
        Intent tIntent = new Intent(ACTION_MEDIA_CONTROL).putExtra(EXTRA_CONTROL_TYPE, controlType);
        final PendingIntent intent = PendingIntent.getBroadcast(MovieFragment.this.getActivity(), requestCode, tIntent, 0);
        final Icon icon = Icon.createWithResource(MovieFragment.this.getActivity(), iconId);
        actions.add(new RemoteAction(icon, title, title, intent));

        // Another action item. This is a fixed action.
        actions.add(new RemoteAction(Icon.createWithResource(MovieFragment.this.getActivity(), R.drawable.ic_info_24dp),
                getString(R.string.info),
                getString(R.string.info_description),
                PendingIntent.getActivity(MovieFragment.this.getActivity(), REQUEST_INFO, new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.info_uri))), 0)));

        mPictureInPictureParamsBuilder.setActions(actions);

        // This is how you can update action items (or aspect ratio) for Picture-in-Picture mode.
        // Note this call can happen even when the app is not in PiP mode. In that case, the
        // arguments will be used for at the next call of #enterPictureInPictureMode.
        getActivity().setPictureInPictureParams(mPictureInPictureParamsBuilder.build());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_layout2, container, false);
        Log.e(TAG, "onCreate:@" + hashCode());

        // Prepare string resources for Picture-in-Picture actions.
        mPlay = getString(R.string.play);
        mPause = getString(R.string.pause);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mPictureInPictureParamsBuilder = new PictureInPictureParams.Builder();
        }

        // View references
        mMovieView = view.findViewById(R.id.movie);
        mScrollView = view.findViewById(R.id.scroll);
        mCloseBtn = view.findViewById(R.id.close);
        mCloseBtn.setOnClickListener(v -> clickClose());

        Button switchExampleButton = view.findViewById(R.id.switch_example);
        switchExampleButton.setText(getString(R.string.switch_media_session));
        switchExampleButton.setOnClickListener(this::switchActivityOnClick);

        // Set up the video; it automatically starts.
        mMovieView.setMovieListener(mMovieListener);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            view.findViewById(R.id.pip).setVisibility(View.GONE);
        } else {
            view.findViewById(R.id.pip).setOnClickListener(v -> pip());
        }
        return view;
    }

    private void clickClose() {
        mMovieView.pause();
        getActivity().finish();
    }

    private void switchActivityOnClick(View view) {
        startActivity(new Intent(view.getContext(), MediaSessionPlaybackActivity.class));
        getActivity().finish();
    }

    @Override
    public void onRestart() {
        Log.e(TAG, "onRestart: ");
        if (!getActivity().isInPictureInPictureMode()) {
            // Show the video controls so the video can be easily resumed.
            mMovieView.showControls();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e(TAG, "onResume: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e(TAG, "onPause: ");
    }

    @Override
    public void onStop() {
        // On entering Picture-in-Picture mode, onPause is called, but not onStop.
        // For this reason, this is the place where we should pause the video playback.
        Log.e(TAG, "onStop: ");
        mMovieView.pause();
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (null == getActivity()) {
            return;
        }

//        Log.d(TAG, "onConfigurationChanged:isInPictureInPictureMode=" + getActivity().isInPictureInPictureMode());
//        Log.d(TAG, "onConfigurationChanged:" + newConfig.toString());
        if (getActivity().isInPictureInPictureMode()) {
            adjustFullScreen(newConfig);
            Log.d(TAG, "onConfigurationChanged: " + newConfig.orientation);
        } else {
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        if (hasFocus) {
            adjustFullScreen(getResources().getConfiguration());
        }
    }

    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode);
        Log.d(TAG, "onPictureInPictureModeChanged: isInPictureInPictureMode=" + isInPictureInPictureMode);

        if (isInPictureInPictureMode) {
            // Starts receiving events from action items in PiP mode.
            mReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {
                    if (intent == null || !ACTION_MEDIA_CONTROL.equals(intent.getAction())) {
                        return;
                    }

                    // This is where we are called back from Picture-in-Picture action
                    // items.
                    final int controlType = intent.getIntExtra(EXTRA_CONTROL_TYPE, 0);
                    switch (controlType) {
                        case CONTROL_TYPE_PLAY:
                            mMovieView.play();
                            break;
                        case CONTROL_TYPE_PAUSE:
                            mMovieView.pause();
                            break;
                    }
                }
            };
            getActivity().registerReceiver(mReceiver, new IntentFilter(ACTION_MEDIA_CONTROL));
        } else {
            if (null == mReceiver) {
                return;
            }
            // We are out of PiP mode. We can stop receiving events from it.
            getActivity().unregisterReceiver(mReceiver);
            mReceiver = null;
            // Show the video controls if the video is not playing
            if (mMovieView != null && !mMovieView.isPlaying()) {
                mMovieView.showControls();
            }
        }
    }

    /**
     * Press Picture-in-Picture  button -> Enters Picture-in-Picture mode.
     */
    private void pip() {
        minimize();
    }

    void minimize() {
        Log.d(TAG, "minimize: ");
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            return;
        }
        if (mMovieView == null) {
            return;
        }
        // Hide the controls in picture-in-picture mode.
        mMovieView.hideControls();
        mScrollView.setVisibility(View.GONE);
        mCloseBtn.setVisibility(View.GONE);
        // Calculate the aspect ratio of the PiP screen.
        Rational aspectRatio = new Rational(mMovieView.getWidth(), mMovieView.getHeight());
        mPictureInPictureParamsBuilder.setAspectRatio(aspectRatio).build();
        getActivity().enterPictureInPictureMode(mPictureInPictureParamsBuilder.build());
    }

    /**
     * Press Home or Recent -> Enters Picture-in-Picture mode.
     */
    @Override
    public void onUserLeaveHint() {
        Log.e(TAG, "onUserLeaveHint: ");
        if (iWantToBeInPipModeNow()) {
            minimize();
        }
    }

    private boolean iWantToBeInPipModeNow() {
        return true;
    }

    /**
     * Adjusts immersive full-screen flags depending on the screen orientation.
     *
     * @param config The current {@link Configuration}.
     */
    private void adjustFullScreen(Configuration config) {
        final View decorView = getActivity().getWindow().getDecorView();
        if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.d(TAG, "adjustFullScreen: ORIENTATION_LANDSCAPE");
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
            mScrollView.setVisibility(View.GONE);
            mCloseBtn.setVisibility(View.GONE);
            mMovieView.setAdjustViewBounds(false);
        } else {
            Log.d(TAG, "adjustFullScreen: ORIENTATION_PORTRAIT");
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            mScrollView.setVisibility(View.VISIBLE);
            mCloseBtn.setVisibility(View.VISIBLE);
            mMovieView.setAdjustViewBounds(true);
        }
    }
}