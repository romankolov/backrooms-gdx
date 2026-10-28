# APK sanity check

Commit: 532e33c118d18f63179197e6844a3b0ccb4f5728

```
ANDROID_HOME=/usr/local/lib/android/sdk ANDROID_SDK_ROOT=/usr/local/lib/android/sdk
build-tools present:
34.0.0
35.0.0
35.0.1
36.0.0
36.1.0
37.0.0
using aapt2: /usr/local/lib/android/sdk/build-tools/37.0.0/aapt2
== android/build/outputs/apk/debug/android-debug.apk
ok: lib/arm64-v8a/libgdx.so
ok: lib/armeabi-v7a/libgdx.so
ok: lib/x86_64/libgdx.so
package: name='com.backrooms.gdx.debug' versionCode='1' versionName='1.0.0-debug' platformBuildVersionName='15' platformBuildVersionCode='35' compileSdkVersion='35' compileSdkVersionCodename='15'
targetSdkVersion:'35'
launchable-activity: name='com.backrooms.gdx.android.AndroidLauncher'  label='' icon=''
native-code: 'arm64-v8a' 'armeabi-v7a' 'x86_64'
ok: launcher activity
ok: AndroidLauncher in classes.dex
-- shared libraries packaged: 3
== android/build/outputs/apk/release/android-release-unsigned.apk
ok: lib/arm64-v8a/libgdx.so
ok: lib/armeabi-v7a/libgdx.so
ok: lib/x86_64/libgdx.so
package: name='com.backrooms.gdx' versionCode='1' versionName='1.0.0' platformBuildVersionName='15' platformBuildVersionCode='35' compileSdkVersion='35' compileSdkVersionCodename='15'
targetSdkVersion:'35'
launchable-activity: name='com.backrooms.gdx.android.AndroidLauncher'  label='' icon=''
native-code: 'arm64-v8a' 'armeabi-v7a' 'x86_64'
ok: launcher activity
ok: AndroidLauncher in classes.dex
-- shared libraries packaged: 3
APK sanity checks passed
```
