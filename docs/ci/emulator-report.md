# Emulator smoke test diagnostics

Commit: 29752a9f60626fa4f0cc21becc0ab6471c4b1f10
Outcome: failure

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 27 23:18 /dev/kvm
--- sdk contents
build-tools
cmake
cmdline-tools
extras
licenses
ndk
packages-list.txt
platform-tools
platforms
repositories.cfg
```
## Emulator log
```
INFO         | Android emulator version 37.2.12.0 (build_id 16428233) (CL:N/A)
INFO         | Graphics backend: gfxstream
ERROR        | Unknown AVD name [bgdx], use -list-avds to see valid list.
ERROR        | HOME is defined but there is no file bgdx.ini in $HOME/.android/avd
ERROR        | (Note: Directories are searched in the order $ANDROID_AVD_HOME, $ANDROID_SDK_HOME/avd and $HOME/.android/avd)
```
## Logcat (filtered)
```
```
## Step log
```
/lib64/libtcm[==========                             ] 26% Unzipping... emulator/lib64/libima[==========                             ] 26% Unzipping... emulator/lib64/libc++[==========                             ] 26% Unzipping... emulator/lib64/liband[==========                             ] 26% Unzipping... emulator/lib64/libabs[==========                             ] 26% Unzipping... emulator/lib64/gles_a[==========                             ] 26% Unzipping... emulator/lib64/libsha[==========                             ] 26% Unzipping... emulator/lib64/libc++[==========                             ] 26% Unzipping... emulator/lib64/qt/res[==========                             ] 26% Unzipping... emulator/lib64/qt/lib[==========                             ] 26% Unzipping... emulator/lib64/qt/tra[==========                             ] 26% Unzipping... emulator/lib64/qt/plu[==========                             ] 26% Unzipping... emulator/lib64/qt/res[==========                             ] 27% Unzipping... emulator/lib64/qt/res[==========                             ] 27% Unzipping... emulator/lib64/qt/lib[===========                            ] 28% Unzipping... emulator/lib64/qt/lib[===========                            ] 29% Unzipping... emulator/lib64/qt/lib[===========                            ] 30% Unzipping... emulator/lib64/qt/lib[============                           ] 30% Unzipping... emulator/lib64/qt/lib[============                           ] 30% Unzipping... emulator/lib64/qt/tra[============                           ] 30% Unzipping... emulator/lib64/qt/plu[============                           ] 30% Unzipping... emulator/lib64/gles_l[============                           ] 31% Unzipping... emulator/lib64/gles_l[============                           ] 31% Unzipping... emulator/lib64/vulkan[============                           ] 32% Unzipping... emulator/lib64/vulkan[============                           ] 32% Unzipping... emulator/lib64/gles_s[============                           ] 32% Unzipping... emulator/lib64/gles_a[=============                          ] 33% Unzipping... emulator/lib64/gles_a[=====================                  ] 55% Unzipping... emulator/lib64/gles_a[=====================                  ] 55% Installing Intel x86_64 Atom Syste[=====================                  ] 55% Downloading x86_64-30_r11.zip... e[======================                 ] 55% Downloading x86_64-30_r11.zip... e[======================                 ] 56% Downloading x86_64-30_r11.zip... e[======================                 ] 57% Downloading x86_64-30_r11.zip... e[=======================                ] 58% Downloading x86_64-30_r11.zip... e[=======================                ] 59% Downloading x86_64-30_r11.zip... e[=======================                ] 60% Downloading x86_64-30_r11.zip... e[========================               ] 60% Downloading x86_64-30_r11.zip... e[========================               ] 61% Downloading x86_64-30_r11.zip... e[========================               ] 62% Downloading x86_64-30_r11.zip... e[=========================              ] 63% Downloading x86_64-30_r11.zip... e[=========================              ] 64% Downloading x86_64-30_r11.zip... e[=========================              ] 65% Downloading x86_64-30_r11.zip... e[==========================             ] 65% Downloading x86_64-30_r11.zip... e[==========================             ] 66% Downloading x86_64-30_r11.zip... e[==========================             ] 66% Unzipping... emulator/lib64/gles_a[==========================             ] 66% Unzipping... x86_64/VerifiedBootPa[==========================             ] 66% Unzipping... x86_64/advancedFeatur[==========================             ] 66% Unzipping... x86_64/build.prop    [==========================             ] 66% Unzipping... x86_64/encryptionkey.[==========================             ] 66% Unzipping... x86_64/kernel-ranchu [==========================             ] 67% Unzipping... x86_64/kernel-ranchu [==========================             ] 67% Unzipping... x86_64/ramdisk.img   [==========================             ] 67% Unzipping... x86_64/source.propert[==========================             ] 67% Unzipping... x86_64/system.img    [===========================            ] 68% Unzipping... x86_64/system.img    [===========================            ] 69% Unzipping... x86_64/system.img    [===========================            ] 70% Unzipping... x86_64/system.img    [============================           ] 70% Unzipping... x86_64/system.img    [============================           ] 71% Unzipping... x86_64/system.img    [============================           ] 72% Unzipping... x86_64/system.img    [=============================          ] 73% Unzipping... x86_64/system.img    [=============================          ] 74% Unzipping... x86_64/system.img    [=============================          ] 75% Unzipping... x86_64/system.img    [==============================         ] 75% Unzipping... x86_64/system.img    [==============================         ] 76% Unzipping... x86_64/system.img    [==============================         ] 77% Unzipping... x86_64/system.img    [==============================         ] 77% Unzipping... x86_64/userdata.img  [==============================         ] 77% Unzipping... x86_64/vendor.img    [==============================         ] 77% Unzipping... x86_64/data/local.pro[==============================         ] 77% Unzipping... x86_64/data/misc/apns[==============================         ] 77% Unzipping... x86_64/data/misc/emul[==============================         ] 77% Unzipping... x86_64/data/misc/gcec[==============================         ] 77% Unzipping... x86_64/data/misc/wifi[==============================         ] 77% Unzipping... x86_64/data/system/di[==============================         ] 78% Unzipping... x86_64/data/system/di[=======================================] 100% Unzipping... x86_64/data/system/d

+ '[' '!' -x /usr/local/lib/android/sdk/emulator/emulator ']'
+ echo no
+ /usr/local/lib/android/sdk/cmdline-tools/latest/bin/avdmanager create avd -n bgdx -k 'system-images;android-30;default;x86_64' -d pixel_2 --force
+ cat emulator-shots/avdmanager.log
Loading local repository...                                                     [=========                              ] 25% Loading local repository...       [=========                              ] 25% Fetch remote repository...        [=======================================] 100% Fetch remote repository...       
Auto-selecting single ABI x86_64
+ ACCEL=auto
+ GPU=swiftshader_indirect
+ '[' -r /dev/kvm ']'
+ echo 'KVM not usable: running unaccelerated'
KVM not usable: running unaccelerated
+ ACCEL=off
+ EMU_PID=2820
+ /usr/local/lib/android/sdk/emulator/emulator -avd bgdx -accel off -gpu swiftshader_indirect -no-window -no-snapshot -no-audio -no-boot-anim -memory 3072 -cores 2 -netdelay none -netspeed full
+ echo 'emulator pid 2820'
emulator pid 2820
+ adb start-server
+ adb devices
List of devices attached

+ BOOTED=0
++ seq 1 120
+ for i in $(seq 1 120)
++ adb shell getprop sys.boot_completed
++ tr -d '\r'
+ STATE=
+ '[' '' = 1 ']'
+ kill -0 2820
+ echo 'emulator process exited early'
emulator process exited early
+ break
+ '[' 0 '!=' 1 ']'
+ echo EMULATOR_DID_NOT_BOOT
EMULATOR_DID_NOT_BOOT
+ tail -60 emulator-shots/emulator.log
INFO         | Android emulator version 37.2.12.0 (build_id 16428233) (CL:N/A)
INFO         | Graphics backend: gfxstream
ERROR        | Unknown AVD name [bgdx], use -list-avds to see valid list.
ERROR        | HOME is defined but there is no file bgdx.ini in $HOME/.android/avd
ERROR        | (Note: Directories are searched in the order $ANDROID_AVD_HOME, $ANDROID_SDK_HOME/avd and $HOME/.android/avd)
+ exit 1
```
