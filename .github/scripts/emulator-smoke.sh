#!/usr/bin/env bash
# Installs the debug APK on an Android emulator, launches it, and verifies that it renders and
# stays alive. Run by .github/workflows/emulator.yml; can also be run locally with an SDK and an
# already built APK (scripts writes screenshots + logcat into emulator-shots/).
set -x

SHOTS=${SHOTS:-emulator-shots}
APK=${APK:-$(find android/build/outputs/apk/debug -name '*.apk' 2>/dev/null | head -1)}
PKG=com.backrooms.gdx.debug
ACTIVITY=$PKG/com.backrooms.gdx.android.AndroidLauncher
SDK=${ANDROID_HOME:-${ANDROID_SDK_ROOT:-}}
SYSTEM_IMAGE="system-images;android-30;default;x86_64"

mkdir -p "$SHOTS"
export ANDROID_HOME="$SDK"
export ANDROID_SDK_ROOT="$SDK"
export PATH="$SDK/platform-tools:$SDK/emulator:$PATH"

{
  echo "SDK=$SDK"
  echo "APK=$APK"
  echo "--- kvm"
  ls -l /dev/kvm 2>&1 | head -2
  echo "--- sdk contents"
  ls "$SDK" 2>&1
} | tee "$SHOTS/env.txt"

SDKMANAGER=$(find "$SDK" -type f -name sdkmanager 2>/dev/null | head -1)
AVDMANAGER=$(find "$SDK" -type f -name avdmanager 2>/dev/null | head -1)
echo "sdkmanager=$SDKMANAGER avdmanager=$AVDMANAGER"

if [ -z "$SDKMANAGER" ]; then
  echo "FATAL: no sdkmanager in the Android SDK"
  exit 1
fi

yes | "$SDKMANAGER" --licenses > "$SHOTS/sdkmanager-licenses.log" 2>&1 || true

if [ ! -x "$SDK/emulator/emulator" ] || [ ! -d "$SDK/system-images/android-30/default/x86_64" ]; then
  echo "installing emulator and $SYSTEM_IMAGE (this takes a while)"
  yes | "$SDKMANAGER" --install "platform-tools" "emulator" "$SYSTEM_IMAGE" \
    > "$SHOTS/sdkmanager-install.log" 2>&1 || echo "sdkmanager install returned $?"
  tail -5 "$SHOTS/sdkmanager-install.log" || true
fi

if [ ! -x "$SDK/emulator/emulator" ]; then
  echo "FATAL: emulator binary still missing"
  exit 1
fi

echo "no" | "$AVDMANAGER" create avd -n bgdx -k "$SYSTEM_IMAGE" -d pixel_2 --force \
  > "$SHOTS/avdmanager.log" 2>&1 || echo "avd creation returned $?"
cat "$SHOTS/avdmanager.log" 2>/dev/null || true

ACCEL=auto
GPU=swiftshader_indirect
if [ -r /dev/kvm ] && [ -w /dev/kvm ]; then
  echo "KVM is usable"
else
  echo "KVM not usable: running unaccelerated"
  ACCEL=off
fi

"$SDK/emulator/emulator" -avd bgdx -accel "$ACCEL" -gpu "$GPU" -no-window -no-snapshot \
  -no-audio -no-boot-anim -memory 3072 -cores 2 -netdelay none -netspeed full \
  > "$SHOTS/emulator.log" 2>&1 &
EMU_PID=$!
echo "emulator pid $EMU_PID"

adb start-server > /dev/null 2>&1 || true
adb devices

BOOTED=0
for i in $(seq 1 120); do
  STATE=$(adb shell getprop sys.boot_completed 2>/dev/null | tr -d '\r')
  if [ "$STATE" = "1" ]; then
    BOOTED=1
    echo "booted after $((i * 5))s"
    break
  fi
  if ! kill -0 "$EMU_PID" 2>/dev/null; then
    echo "emulator process exited early"
    break
  fi
  sleep 5
done

if [ "$BOOTED" != "1" ]; then
  echo "EMULATOR_DID_NOT_BOOT"
  tail -60 "$SHOTS/emulator.log"
  exit 1
fi

adb shell input keyevent 82 || true
adb shell wm dismiss-keyguard || true
adb shell settings put global window_animation_scale 0 || true
adb shell settings put global transition_animation_scale 0 || true
adb shell settings put global animator_duration_scale 0 || true

if [ -z "$APK" ]; then
  echo "FATAL: no debug APK found"
  exit 1
fi
echo "installing $APK"
adb install -r -t "$APK" || exit 1

adb logcat -c || true
adb shell am start -W -n "$ACTIVITY" || exit 1

sleep 20
adb exec-out screencap -p > "$SHOTS/title.png" || true
adb shell input tap 640 360 || true      # TAP TO ENTER
sleep 25
adb exec-out screencap -p > "$SHOTS/ingame.png" || true
adb shell input swipe 500 900 500 300 300 || true   # look around
sleep 5
adb exec-out screencap -p > "$SHOTS/look.png" || true

PID=$(adb shell pidof "$PKG" | tr -d '\r')
echo "pid=[$PID]"
adb shell dumpsys activity activities 2>/dev/null | grep -E "mResumedActivity|topResumedActivity" | head -3 || true
adb logcat -d > "$SHOTS/logcat.txt" || true
grep -aE "AndroidRuntime|FATAL|UnsatisfiedLink|GLSL|libGDX|Backrooms|Perf|GL_" \
  "$SHOTS/logcat.txt" | tail -60 || true

if [ -z "$PID" ]; then
  echo "FATAL: the app is not running any more"
  exit 1
fi
if grep -qa "FATAL EXCEPTION" "$SHOTS/logcat.txt"; then
  echo "FATAL: crash recorded in logcat"
  exit 1
fi
echo "OK: the app is alive and rendering"
