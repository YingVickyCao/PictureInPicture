# # Picture In Picture

Picture-in-picture Support  
https://developer.android.google.cn/guide/topics/ui/picture-in-picture?hl=en

Picture-in-picture Example  
https://github.com/android/media-samples/tree/master/PictureInPicture/

How to 画中画：  
Android >=8.0（26）（Current）  
Android <8.0: 低版本实现画中画还是需要利用 windowManger 通过 addview 去做

画中画

- 内置操作：手势移动、关闭画中画、画中画切换回原页面
- PIP is single instance in phone.  
  If 其他 app 使用 PIP， 覆盖。

# 1 Source

## AndroidManifest.xml

```
android:configChanges="screenSize|smallestScreenSize|screenLayout|orientation"
android:supportsPictureInPicture="true"
```

# 2 Log

Phone = Android 10 Google Pixel 3  
Tablet = Androd 9 SamSung SM-T830

## Phone is portrait

```
MovieFragment.java, Phone is porttrait

onCreateView: isInPictureInPictureMode=false
onStart: isInPictureInPictureMode=false
onResume: isInPictureInPictureMode=false
onWindowFocusChanged: hasFocus=true,orientation=PORTRAIT

// Enter PIP (Press Home/Recent)
onReceive: action=android.intent.action.CLOSE_SYSTEM_DIALOGS,reason=homekey
minimize
onUserLeaveHint
onPause: isInPictureInPictureMode=true
onPictureInPictureModeChanged: isInPictureInPictureMode=true,screen=normal
onConfigurationChanged:isInPictureInPictureMode=true,orientation=LANDSCAPE

// Out PIP
onConfigurationChanged:isInPictureInPictureMode=true,orientation=LANDSCAPE  // Touch PIP
onPictureInPictureModeChanged: isInPictureInPictureMode=false,screen=normal
onConfigurationChanged:isInPictureInPictureMode=false,orientation=PORTRAIT
onWindowFocusChanged: hasFocus=true,orientation=PORTRAIT
onResume: isInPictureInPictureMode=false


// Enter PIP (Click button)
minimize:
onPause: isInPictureInPictureMode=true
onPictureInPictureModeChanged: isInPictureInPictureMode=true,screen=normal
onConfigurationChanged:isInPictureInPictureMode=true,orientation=LANDSCAPE


// In PIP, click X
onConfigurationChanged:isInPictureInPictureMode=true,orientation=LANDSCAPE
onStop: isInPictureInPictureMode=false
onPictureInPictureModeChanged: isInPictureInPictureMode=false,screen=normal

// Pickup activity from Recents
onConfigurationChanged:isInPictureInPictureMode=false,orientation=PORTRAIT
onRestart: isInPictureInPictureMode=false
onStart: isInPictureInPictureMode=false
onResume: isInPictureInPictureMode=false
onWindowFocusChanged: hasFocus=true,orientation=PORTRAIT

// Close MovieFragment and it's activity
onPause: isInPictureInPictureMode=false
onStop: isInPictureInPictureMode=false
onDestroyView: isInPictureInPictureMode=false
onDestroy: isInPictureInPictureMode=false
```

## Phone is landscape

```
MovieFragment.java, Phone is landscape

onCreateView: isInPictureInPictureMode=false
onStart: isInPictureInPictureMode=false
onResume: isInPictureInPictureMode=false
onWindowFocusChanged: hasFocus=true,orientation=LANDSCAPE

// Enter PIP (Press Home/Recent)
onReceive: action=android.intent.action.CLOSE_SYSTEM_DIALOGS,reason=homekey
minimize
onUserLeaveHint
onPause: isInPictureInPictureMode=true
onConfigurationChanged:isInPictureInPictureMode=true,orientation=LANDSCAPE
onPictureInPictureModeChanged: isInPictureInPictureMode=true,screen=normal

// Out PIP
onConfigurationChanged:isInPictureInPictureMode=true,orientation=LANDSCAPE
onPictureInPictureModeChanged: isInPictureInPictureMode=false,screen=normal
onConfigurationChanged:isInPictureInPictureMode=false,orientation=PORTRAIT
onWindowFocusChanged: hasFocus=true,orientation=PORTRAIT
onConfigurationChanged:isInPictureInPictureMode=false,orientation=LANDSCAPE
onResume: isInPictureInPictureMode=false

// Enter PIP (Click button)
minimize:
onPause: isInPictureInPictureMode=true
onConfigurationChanged:isInPictureInPictureMode=true,orientation=LANDSCAPE
onPictureInPictureModeChanged: isInPictureInPictureMode=true,screen=normal

// In PIP, click X
onConfigurationChanged:isInPictureInPictureMode=true,orientation=LANDSCAPE
onStop: isInPictureInPictureMode=false
onPictureInPictureModeChanged: isInPictureInPictureMode=false,screen=normal

// Pickup activity from Recents
onConfigurationChanged:isInPictureInPictureMode=false,orientation=LANDSCAPE
onRestart: isInPictureInPictureMode=false
onStart: isInPictureInPictureMode=false
onResume: isInPictureInPictureMode=false
onWindowFocusChanged: hasFocus=true,orientation=LANDSCAPE

// Close MovieFragment and it's activity
onPause: isInPictureInPictureMode=false
onStop: isInPictureInPictureMode=false
onDestroyView: isInPictureInPictureMode=false
onDestroy: isInPictureInPictureMode=false
```

## Tablet is portrait

```
MovieFragment.java, Tablet is portrait

onCreateView: isInPictureInPictureMode=false
onStart: isInPictureInPictureMode=false
onResume: isInPictureInPictureMode=false
onWindowFocusChanged: hasFocus=true,orientation=PORTRAIT

// Enter PIP (Press Home/Recent)
onReceive: action=android.intent.action.CLOSE_SYSTEM_DIALOGS,reason=homekey
minimize:
onUserLeaveHint:
onPause: isInPictureInPictureMode=true
onPictureInPictureModeChanged: isInPictureInPictureMode=true,screen=large
onConfigurationChanged:isInPictureInPictureMode=true,orientation=LANDSCAPE

// Out PIP
onPictureInPictureModeChanged: isInPictureInPictureMode=false,screen=normal
onConfigurationChanged:isInPictureInPictureMode=false,orientation=PORTRAIT
onWindowFocusChanged: hasFocus=true,orientation=PORTRAIT
onResume: isInPictureInPictureMode=false

// Enter PIP (Click button)
minimize:
onPause: isInPictureInPictureMode=true
onPictureInPictureModeChanged: isInPictureInPictureMode=true,screen=large
onConfigurationChanged:isInPictureInPictureMode=true,orientation=LANDSCAPE

// In PIP, click X
onConfigurationChanged:isInPictureInPictureMode=false,orientation=PORTRAIT
onStop: isInPictureInPictureMode=false
onPictureInPictureModeChanged: isInPictureInPictureMode=false,screen=large

// Pickup activity from Recents
onRestart: isInPictureInPictureMode=false
onStart: isInPictureInPictureMode=false
onResume: isInPictureInPictureMode=false
onWindowFocusChanged: hasFocus=true,orientation=PORTRAIT

// Close MovieFragment and it's activity
onPause: isInPictureInPictureMode=false
onStop: isInPictureInPictureMode=false
onDestroyView: isInPictureInPictureMode=false
onDestroy: isInPictureInPictureMode=false
```

## Tablet is landcape

```
MovieFragment.java, Tablet is landcape

onCreateView: isInPictureInPictureMode=false
onStart: isInPictureInPictureMode=false
onResume: isInPictureInPictureMode=false
onWindowFocusChanged: hasFocus=true,orientation=LANDSCAPE

// Enter PIP (Press Home/Recent)
onReceive: action=android.intent.action.CLOSE_SYSTEM_DIALOGS,reason=homekey
minimize:
onPause: isInPictureInPictureMode=true
onConfigurationChanged:isInPictureInPictureMode=true,orientation=LANDSCAPE
onPictureInPictureModeChanged: isInPictureInPictureMode=true,screen=large-land
onConfigurationChanged:isInPictureInPictureMode=true,orientation=LANDSCAPE

// Out PIP
onPictureInPictureModeChanged: isInPictureInPictureMode=false,screen=normal
onConfigurationChanged:isInPictureInPictureMode=false,orientation=LANDSCAPE
onConfigurationChanged:isInPictureInPictureMode=false,orientation=LANDSCAPE
onWindowFocusChanged: hasFocus=true,orientation=LANDSCAPE
onResume: isInPictureInPictureMode=false

// Enter PIP (Click button)
minimize:
onPause: isInPictureInPictureMode=true
onConfigurationChanged:isInPictureInPictureMode=true,orientation=LANDSCAPE
onPictureInPictureModeChanged: isInPictureInPictureMode=true,screen=large-land
onConfigurationChanged:isInPictureInPictureMode=true,orientation=LANDSCAPE

// In PIP, click X
onConfigurationChanged:isInPictureInPictureMode=false,orientation=LANDSCAPE
onStop: isInPictureInPictureMode=false
onPictureInPictureModeChanged: isInPictureInPictureMode=false,screen=large-land

// Pickup activity from Recents
onRestart: isInPictureInPictureMode=false
onStart: isInPictureInPictureMode=false
onResume: isInPictureInPictureMode=false
onWindowFocusChanged: hasFocus=true,orientation=LANDSCAPE

// Close MovieFragment and it's activity
onPause: isInPictureInPictureMode=false
onStop: isInPictureInPictureMode=false
onDestroyView: isInPictureInPictureMode=false
onDestroy: isInPictureInPictureMode=false
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

# 6 Q: Media Service is still playing music after app is killed by swiped from Recents?

A:

- Way 1:

```xml
<service
android:stopWithTask="true"/>
```

- Way 2:

```xml
<service
android:stopWithTask="false"/>
```

```java
// Service

// Only if android:stopWithTask="false", onTaskRemoved can be invoked.
@Override
public void onTaskRemoved(Intent rootIntent) {
  super.onTaskRemoved(rootIntent);
  // Do clear task
}
```

# 7 Q: PIP Mode showes many views?

A:  
In Activity / Fragment `onConfigurationChanged(@NonNull Configuration newConfig)`, hide/show views?

```java
// Fragment
@Override
public void onConfigurationChanged(Configuration newConfig) {
  super.onConfigurationChanged(newConfig);
  if (null == getActivity()) {
      return;
  }
  if (getActivity().isInPictureInPictureMode()) {
    // hide no used views in PIP Mode.
  } else {
  // Show views in Video Page
  }
}
```

# 8 Q: When press Home or Recents, go to PIP mode?

A:
Way 1 : Press Home or Recents

```java
@Override
public void onUserLeaveHint() {
    Log.e(TAG, "onUserLeaveHint: ");
    pip();
}
```

Way 2 : Only press Home

```
// Use BroadcastReceiver
// Press Recents
onReceive: action=android.intent.action.CLOSE_SYSTEM_DIALOGS
onReceive: reason=recentapps

// Press Home
onReceive: action=android.intent.action.CLOSE_SYSTEM_DIALOGS
onReceive: reason=homekey
```

# 9 Q:When In PIP, for tablet, value is from values

Q: In PIP of tablet , when trying to reuse fragment VideoPhoneFragment.java and VideoTabletFragment.java, use screen string to check if is Tablet, is always phone.

```
If normal, phone.
If large/larage_land/xlarage/xlarage_land, tablet.

When PIP,     screen = normal(values)
When Not PIP, screen = large(values-large) / larage_land(values-large-land)
```

A:  
How to find fragment tag?  
Fist check: find fagment by id. If not null, return tag.
Then check: find fragment by tag.

# 10 How can user disable Picture-in-Picture Mode?

Settings -> App -> Advances -> Picture-in-picture allowed.

# 11 Check if app support Picture-in-Picture

Step 1 : API >= 26 (Android 8)

Step 2 : check if use not alow Picture-in-Picture.

```java
// AppOpsManagerCompat
AppOpsManagerCompat.noteOpNoThrow(getActivity(), AppOpsManager.OPSTR_PICTURE_IN_PICTURE, android.os.Process.myUid(), getActivity().getPackageName())

// AppOpsManager (API 19)
((AppOpsManager) appOpsManager).unsafeCheckOp(AppOpsManager.OPSTR_PICTURE_IN_PICTURE, android.os.Process.myUid(), getActivity().getPackageName())
```

Step 3 : PIP might be disabled on devices that have low RAM.

```java
getPackageManager().hasSystemFeature(PackageManager.FEATURE_PICTURE_IN_PICTURE)
```
