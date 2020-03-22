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

# 12 Activity lifecycle with PIP

## Case 1 : Main -> A(PIP Mode) -> B （Support PIP), Then : Press back

- A 和 B 是同一个 PIP 小窗口：A and B 成为僵尸 PIP 画面（no system controll bar）  
  Main 在 Recent 中是一个单独的窗口。  
  从 Recent 中只能杀死 Main。
- Recent 只有 Main 的僵尸画面。单击 Main 的僵尸画面，进入 Main。
- 杀死 Main，重新 Click app，进入 AB，没有进入 Main。 AB 的 PIP 画面再次出现 system controll bar。Click X，退出 PIP Mode。
- Back 顺序异常 ： Main is destroyed, but A and B not any lifecycle function is invorked.

## Case 2: Main -> A(PIP Mode) -> B （Not Support PIP）, Then : Press back

- B: onResume:isInPictureInPictureMode=true => A 是 PIP mode，尽管 B 配置不支持 PIP。但是因为是由 A 启动，B 也被动成了 PIP mode。 并且无论是不是平板，PIPmode 时，values 和 layout 是都是 normal 类型。
- 其他 同 Case 1 的结论

## Case 3: Main -> A(Exit from PIP Mode) -> B （Not Support PIP）, Then : Press back

- B : onResume:isInPictureInPictureMode=false
- A 和 B 在 Recents 在同一个单独的窗口,Main 是一个单独的窗口 => 只要曾经 PIP，就单独占据一个 task stack。  
  从 Recent 删除 Main，main 被删除。  
  从 Recent 删除 AB 的 PIP task ，AB 被删除。  
  从 Recents clear all 后，整个 app = main + AB 的 PIP ，全杀死。
- Back 顺序正常：B -> A -> Main

## Case 4: Main -> A(Exit from PIP Mode) -> B （Support PIP）, Then : Press back

同 Case 3

## Case 5: Main -> A(Support PIP，but PIP mode is not even once) -> B （Support PIP）

- B : onResume:isInPictureInPictureMode=false
- main, A and B ， 在 Rencents 是一个窗口。 从 Recents clear 后，整个 app 都杀死。
- Back 顺序正常：B -> A -> Main

## Case 6: Main -> A(PIP mode)

- main, 在 Rencents 是一个窗口。 A 是 PIP 窗口。  
  从 Recents clear 后，杀死 Main， A PIP 还活着（sysem bar 可操作）
- Back 顺序异常：杀死 Main。A PIP 还活着（sysem bar 可操作）

# 13 Q: When A is PIP mode,user click video item from Video list page. How to handle?

A Activity is main vieo page supporting auto next.  
B Activity supports Only one video).User can pickup ref video of A, then A -> B.

**A :**  
When user click video, this is to a signal to begin new video reading session.  
Step 1 : send event to notify A and B to close themselves.  
Step 2 : open the new video in A.  
Only when A is not in PIP mode, user can pickup up ref video, A -> B. The ref video will be opened in B.

# 14 Q: How to listener PIP X click action to exit activity?

A :  
No way to listen X click action.  
Use bool flag to check if user had clicked PIP X.

```java
@Override
    public void onStop() {
        mIsOnStopCalled = true;
        super.onStop();
    }

@Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode);
        if (!isInPictureInPictureMode) {
            if (mIsOnStopCalled) {
                if (null != getActivity()) {
                    getActivity().finish();
                    return;
                }
            }
    }
```
