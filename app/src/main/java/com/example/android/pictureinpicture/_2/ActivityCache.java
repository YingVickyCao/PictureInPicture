package com.example.android.pictureinpicture._2;

import android.app.Activity;
import android.util.Log;

import java.util.Stack;

public class ActivityCache {
    private static final String TAG = ActivityCache.class.getSimpleName();

    private Stack<Activity> mStack = new Stack<>();
    private static ActivityCache mInstance = null;

    public static ActivityCache getInstance() {
        if (null == mInstance) {
            mInstance = new ActivityCache();
        }
        return mInstance;
    }

    public void addActivity(Activity activity) {
        if (null == activity) {
            return;
        }
        mStack.push(activity);
        Log.d(TAG, "addActivity: activity=" + activity.getClass().getSimpleName());
    }

    public Activity getCurrentActivity() {
        if (isEmpty()) {
            return null;
        }
        Activity activity = mStack.peek();
        Log.d(TAG, "getCurrentActivity: activity=" + activity.getClass().getSimpleName());
        return activity;
    }

    public Activity getCurrentActivityNotPIP() {
        if (isEmpty()) {
            return null;
        }

        Activity tmp = null;
        for (int i = mStack.size() - 1; i >= 0; i--) {
            tmp = mStack.get(i);
            if (null != tmp && !tmp.isInPictureInPictureMode()) {
                return tmp;
            }
        }
        return null;
    }

    public void removeCurrentActivity() {
        if (isEmpty()) {
            return;
        }
        Activity activity = mStack.pop();
        Log.d(TAG, "removeCurrentActivity: activity=" + activity.getClass().getSimpleName());
    }

    public void removeActivity(Activity activity) {
        if (isEmpty() || null == activity) {
            return;
        }
        Log.d(TAG, "removeActivity: activity=" + activity.getClass().getSimpleName());
        mStack.remove(activity);
    }

    public Activity findActivity(Class activity) {
        if (isEmpty()) {
            return null;
        }

        Activity tmp = null;
        for (int i = 0; i < mStack.size(); i++) {
            tmp = mStack.get(i);
            if (null != tmp) {
                if (tmp.getClass() == activity) {
                    return tmp;
                }
            }
        }
        return null;
    }

    public boolean isEmpty() {
        return null == mStack || mStack.isEmpty();
    }
}