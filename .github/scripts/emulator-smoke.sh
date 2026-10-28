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

# Keep the AVD in a directory both avdmanager and the emulator agree on.
export ANDROID_USER_HOME="${ANDROID_USER_HOME:-$HOME/.android}"
export ANDROID_AVD_HOME="${ANDROID_AVD_HOME:-$HOME/.android/avd}"
mkdir -p "$ANDROID_AVD_HOME"
echo "ANDROID_USER_HOME=$ANDROID_USER_HOME ANDROID_AVD_HOME=$ANDROID_AVD_HOME"

echo "no" | "$AVDMANAGER" create avd -n bgdx -k "$SYSTEM_IMAGE" -d pixel_2 --force \
  > "$SHOTS/avdmanager.log" 2>&1 || echo "avd creation returned $?"
cat "$SHOTS/avdmanager.log" 2>/dev/null || true
echo "--- avd files"
ls -la "$ANDROID_AVD_HOME" 2>&1 | head -10
find "$HOME" -name "bgdx*" -maxdepth 6 2>/dev/null | head -10
"$AVDMANAGER" list avd 2>&1 | head -20

if [ ! -f "$ANDROID_AVD_HOME/bgdx.ini" ]; then
  echo "FATAL: the AVD was not created in $ANDROID_AVD_HOME"
  exit 1
fi

# The runner user is usually not in the kvm group; GitHub's images allow passwordless sudo.
if [ -e /dev/kvm ] && [ ! -w /dev/kvm ]; then
  sudo chmod 666 /dev/kvm 2>/dev/null || true
fi

ACCEL=auto
GPU=swiftshader_indirect
if [ -r /dev/kvm ] && [ -w /dev/kvm ]; then
  echo "KVM is usable"
else
  echo "KVM not usable: running unaccelerated (slow)"
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

# ---------------------------------------------------------------------------------------------
# Actually play the game. The look drag and the buttons used to be dead on device while the
# stick kept working, and nothing in CI noticed, so the controls are exercised here and the
# numbers the app logs are checked.
# ---------------------------------------------------------------------------------------------

FAILED=0

dump() {
  adb logcat -d -s BackroomsInput:I Perf:I 2>/dev/null | tr -d '\r' > "$SHOTS/input-log.txt" || true
}

field() {   # field NAME LINE
  sed -n "s/.*[ ]$1=\([^ ]*\).*/\1/p" <<<"$2" | tail -1
}

last_perf() { grep -a "Perf" "$SHOTS/input-log.txt" | tail -1; }

dump
TARGETS=$(grep -a "BackroomsInput" "$SHOTS/input-log.txt" | tail -1)
echo "hit targets: $TARGETS"
SCREEN=$(field screen "$TARGETS")
SW=${SCREEN%x*}
SH=${SCREEN#*x}
if ! [ "$SW" -gt 0 ] 2>/dev/null || ! [ "$SH" -gt 0 ] 2>/dev/null; then
  echo "FATAL: the app never logged its layout (screen=[$SCREEN])"
  exit 1
fi
echo "surface ${SW}x${SH}"

# adb shell input talks in display pixels; the app logs the GL surface, which is the display
# rotated into landscape. Only a scale factor is ever needed on top of that.
WM=$(adb shell wm size 2>/dev/null | tr -d '\r' | sed -n 's/.*: \([0-9]*\)x\([0-9]*\).*/\1 \2/p' | tail -1)
DW=$(echo "$WM" | cut -d' ' -f1)
DH=$(echo "$WM" | cut -d' ' -f2)
SX=1
SY=1
if [ -n "$DW" ] && [ "$DW" != "$SW" ] && [ "$DW" != "$SH" ]; then
  SX=$(awk "BEGIN{printf \"%.4f\", $DW/$SW}")
  SY=$(awk "BEGIN{printf \"%.4f\", $DH/$SH}")
fi
echo "display ${DW}x${DH} scale ${SX}x${SY}"

tap_at() {  # tap_at x y, in surface coordinates
  local x=$(awk "BEGIN{printf \"%d\", $1*$SX}")
  local y=$(awk "BEGIN{printf \"%d\", $2*$SY}")
  echo "tap $x,$y"
  adb shell input tap "$x" "$y" || true
}

# ---- 1. dragging on the right side must turn the camera --------------------------------------
dump
BEFORE=$(last_perf)
YAW_BEFORE=$(field yaw "$BEFORE")
DRAWN=$(field drawn "$BEFORE")
echo "before look: $BEFORE"
if [ -z "$YAW_BEFORE" ]; then
  echo "FATAL: no [Perf] line from the app, it is not running"
  exit 1
fi
DRAW_N=$(echo "$DRAWN" | cut -d/ -f1)
if ! [ "${DRAW_N:-0}" -gt 0 ] 2>/dev/null; then
  echo "FATAL: the renderer drew no chunks (drawn=$DRAWN)"
  FAILED=1
fi

LOOK_Y=$(( SH / 2 ))
adb shell input swipe $(( SW * 3 / 4 )) "$LOOK_Y" $(( SW * 2 / 5 )) "$LOOK_Y" 500 || true
sleep 4
dump
AFTER=$(last_perf)
YAW_AFTER=$(field yaw "$AFTER")
echo "after look: $AFTER"
if [ "$YAW_BEFORE" = "$YAW_AFTER" ]; then
  echo "FATAL: dragging on the right side did not turn the camera (yaw stayed $YAW_BEFORE)"
  FAILED=1
fi

# ---- 2. the torch button must toggle the torch ------------------------------------------------
TORCH_XY=$(field torch "$TARGETS")
TORCH_BEFORE=$(field torch "$AFTER")
echo "torch button at ${TORCH_XY}, torch=${TORCH_BEFORE}"
if [ -n "$TORCH_XY" ]; then
  TORCH_X=${TORCH_XY%,*}
  TORCH_Y=${TORCH_XY#*,}
  tap_at "$TORCH_X" "$TORCH_Y"
  sleep 4
  dump
  TORCH_AFTER=$(field torch "$(last_perf)")
  if [ "$TORCH_AFTER" = "$TORCH_BEFORE" ]; then
    echo "retrying the tap with raw surface coordinates"
    adb shell input tap "$TORCH_X" "$TORCH_Y" || true
    sleep 4
    dump
    TORCH_AFTER=$(field torch "$(last_perf)")
  fi
  echo "torch before=$TORCH_BEFORE after=$TORCH_AFTER"
  if [ "$TORCH_AFTER" = "$TORCH_BEFORE" ]; then
    echo "FATAL: the torch button did not react"
    FAILED=1
  fi
else
  echo "FATAL: the app did not report the torch button rectangle"
  FAILED=1
fi

# ---- 3. the stick must move the player ---------------------------------------------------------
STICK_Y=$(( SH * 3 / 4 ))
POS_BEFORE=$(field cam "$(last_perf)")
adb shell input swipe $(( SW / 5 )) "$STICK_Y" $(( SW / 5 )) $(( STICK_Y - SH / 4 )) 1200 || true
sleep 4
dump
POS_AFTER=$(field cam "$(last_perf)")
echo "position before=$POS_BEFORE after=$POS_AFTER"
if [ "$POS_BEFORE" = "$POS_AFTER" ]; then
  echo "FATAL: pushing the stick did not move the player"
  FAILED=1
fi

adb exec-out screencap -p > "$SHOTS/look.png" || true

PID=$(adb shell pidof "$PKG" | tr -d '\r')
echo "pid=[$PID]"
adb shell dumpsys activity activities 2>/dev/null | grep -E "mResumedActivity|topResumedActivity" | head -3 || true
adb logcat -d > "$SHOTS/logcat.txt" || true
# Keep the app's own diagnostics (GL shader compile log, perf counters) and any crash.
grep -aE "AndroidRuntime|FATAL|UnsatisfiedLink|GLSL|libGDX|Backrooms|BackroomsGL|WorldRenderer|Shader|Perf|GL_" \
  "$SHOTS/logcat.txt" | tail -80 || true

if [ -z "$PID" ]; then
  echo "FATAL: the app is not running any more"
  exit 1
fi
if grep -qa "FATAL EXCEPTION" "$SHOTS/logcat.txt"; then
  echo "FATAL: crash recorded in logcat"
  exit 1
fi
if [ "$FAILED" != "0" ]; then
  echo "FATAL: the controls did not behave (see the [Perf] lines above)"
  exit 1
fi
echo "OK: the app is alive, rendering, and the touch controls work"
