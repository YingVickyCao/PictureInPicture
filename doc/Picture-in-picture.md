# Picture In Picture

画中画：

- Android >=8.0（26）（本文讨论）  
  内置操作：手势移动、关闭画中画、画中画切换回原页面
- 低版本实现画中画还是需要利用 windowManger 通过 addview 去做

# 1 Source

## AndroidManifest.xml

```
android:configChanges="screenSize|smallestScreenSize|screenLayout|orientation"
android:supportsPictureInPicture="true"
```

# 2 Log

```
onCreate
onStart
onResume

// Click button ->  Enter Picture-in-Picture mode.
onPause
onPictureInPictureModeChanged

// Click button  -> Exist Picture-in-Picture mode. Back to page
onPictureInPictureModeChanged
onResume

// Click button ->  Enter Picture-in-Picture mode.
onPause
onPictureInPictureModeChanged

// In Picture-in-Picture mode, click Close button
onStop
onPictureInPictureModeChanged
onRestart
onStart
onResume
```

```
onCreate
onStart
onResume

// Press Home/Reccent -> Enter Picture-in-Picture mode.
onUserLeaveHint
onPause
onPictureInPictureModeChanged true

// Click button  -> Exist Picture-in-Picture mode. Back to page
onPictureInPictureModeChanged false
onResume

// Click button ->  Enter Picture-in-Picture mode.
onPause
onPictureInPictureModeChanged true

// In Picture-in-Picture mode, click Close button
onStop
onPictureInPictureModeChanged false
onRestart
onStart
onResume
```

# 3 QA：Tablet Landscape <=>PIP 模式时，onConfigurationChanged 调用两次，造成 PIP Mode 时，不需要的 view 被显示。

```
Tablet Landscape:

MovieFragment: onUserLeaveHint:
MovieFragment: minimize:
MovieFragment: onPause:
MovieFragment: onConfigurationChanged: isInPictureInPictureMode=true// first
MovieActivity: onConfigurationChanged: newConfig=2
MovieFragment: onPictureInPictureModeChanged: true
MovieActivity: onPictureInPictureModeChanged:isInPictureInPictureMode=true
MovieActivity: onPictureInPictureModeChanged:isInPictureInPictureMode=true,newConfig=2
MovieFragment: onConfigurationChanged:isInPictureInPictureMode=true // second
MovieActivity: onConfigurationChanged: newConfig=2
```

```
Tablet portrait:

MovieFragment: onUserLeaveHint:
MovieFragment: minimize:
MovieFragment: onPause:
MovieActivity: onWindowFocusChanged: hasFocus=false
MovieFragment: onPictureInPictureModeChanged: isInPictureInPictureMode=true
MovieActivity: onPictureInPictureModeChanged:isInPictureInPictureMode=true,newConfig=1
MovieFragment: adjustFullScreen: ORIENTATION_LANDSCAPE
MovieFragment: onConfigurationChanged: 2      // first
```

解决：使用 getActivity().isInPictureInPictureMode()) 判断

```java
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
    Log.d(TAG, "onConfigurationChanged:isInPictureInPictureMode=" + getActivity().isInPictureInPictureMode());

    if (null == getActivity() || !getActivity().isInPictureInPictureMode()){
      super.onConfigurationChanged(newConfig);
      return;
    }
      adjustFullScreen(newConfig);
    }
```

# 4 Q: Can support other color icon instead of white icon ?

A : Only support white.  
If set not white icon, displayed as white icon,too.

# 5 Q: Activity is still in Recent after invoked onDestroy()?

How to reproduce?  
First Activity -> MovieActivity.  
MovieActivity=> PIP Mode => MovieActivity, Click Close button to destroy MovieActivity.  
but MovieActivity is still in Recent after invoked onDestroy().

A : `android:autoRemoveFromRecents="true"`
