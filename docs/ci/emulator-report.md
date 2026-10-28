# Emulator smoke test diagnostics

Commit: b4fb5c2513c03ae0d86ba86bdebccb5b2ad46443
Outcome: success

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 28 07:38 /dev/kvm
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
INFO         |   qemu.camera_protocol_ver=1
INFO         |   qemu.dalvik.vm.heapsize=576m
INFO         |   qemu.encrypt=1
INFO         |   qemu.gles=1
INFO         |   qemu.gltransport=pipe
INFO         |   qemu.gltransport.drawFlushInterval=800
INFO         |   qemu.hwcodec.avcdec=2
INFO         |   qemu.hwcodec.hevcdec=2
INFO         |   qemu.hwcodec.vpxdec=2
INFO         |   qemu.opengles.version=196608
INFO         |   qemu.settings.system.screen_off_timeout=2147483647
INFO         |   qemu.skin=1080x1920
INFO         |   qemu.uirenderer=skiagl
INFO         |   qemu.vsync=60
INFO         |   qemu.wifi=1
INFO         | Monitoring duration of emulator setup.
WARNING      | The emulator now requires a signed jwt token for gRPC access! Use the -grpc flag if you really want an open unprotected grpc port
INFO         | Using security allow list from: /usr/local/lib/android/sdk/emulator/lib/emulator_access.json
INFO         | The active JSON Web Key Sets can be found here: /run/user/1001/avd/running/2726/jwks/bc11ddf0-f5aa-452e-b033-3957bbaa3078/active.jwk
INFO         | Scanning /run/user/1001/avd/running/2726/jwks/bc11ddf0-f5aa-452e-b033-3957bbaa3078 for jwk keys.
INFO         | Started GRPC server at 127.0.0.1:8554, security: Local, auth: +token
INFO         | Advertising in: /run/user/1001/avd/running/pid_2726.ini
INFO         | Setting display: 0 configuration to: 1080x1920, dpi: 420x420 
INFO         | setDisplayActiveConfig: id:0, 1080x1920
INFO         | emulatorSetupEnvironment: Environment scene is not required
USER_INFO    | Emulator is performing a full startup. This may take upto two minutes, or more.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
INFO         | Boot completed in 25589 ms
INFO         | Increasing screen off timeout, logcat buffer size to 2M.
```
## Logcat (filtered)
```
10-28 07:41:06.210   511  1171 I ActivityManager: Killing 1760:com.android.provision/u0a116 (adj 985): empty #17
10-28 07:41:06.306   511   553 I ActivityManager: Start proc 2329:com.android.shell/2000 for broadcast {com.android.shell/com.android.shell.HeapDumpReceiver}
10-28 07:41:06.523   511   540 I ActivityManager: Killing 1607:com.android.printspooler/u0a89 (adj 985): empty #17
10-28 07:41:06.726   511   553 I ActivityManager: Start proc 2356:com.android.traceur/u0a93 for broadcast {com.android.traceur/com.android.traceur.Receiver}
10-28 07:41:06.817   511  2149 I ActivityManager: Finished processing BOOT_COMPLETED for u0
10-28 07:41:06.818   511  2149 I ActivityManager: Killing 1951:com.android.quicksearchbox/u0a104 (adj 985): empty #17
10-28 07:41:09.748   427  2168 I iorapd  : Perfetto TraceBuffer saved to file: /data/misc/iorapd/com.backrooms.gdx.debug/1/com.backrooms.gdx.android.AndroidLauncher/raw_traces/1793173269747694325.perfetto_trace.pb
10-28 07:41:26.896   511   543 I ActivityManager: Killing 2031:com.android.calendar/u0a108 (adj 985): empty #17
10-28 07:41:26.896   511   543 I ActivityManager: Killing 1983:com.android.localtransport/1000 (adj 985): empty #18
10-28 07:41:26.896   511   543 I ActivityManager: Killing 1297:com.android.cellbroadcastreceiver/u0a65 (adj 995): empty #19
10-28 07:41:26.922   511   540 I ActivityManager: Killing 2063:com.android.camera2/u0a109 (adj 975): empty #17
10-28 07:41:27.226  2417  2417 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 07:41:27.232  2417  2417 I AndroidRuntime: Using default boot image
10-28 07:41:27.232  2417  2417 I AndroidRuntime: Leaving lock profiling enabled
10-28 07:41:27.452  2417  2417 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 07:41:27.470  2417  2417 D AndroidRuntime: Shutting down VM
10-28 07:41:28.019  2172  2244 I BackroomsGL: world shader compiled
10-28 07:41:28.070  2172  2244 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 07:41:28.070  2172  2244 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 07:41:29.994  2172  2244 I Perf    : fps=6.4 worst=50 drawn=11/17 queue=1 build=18ms verts=28k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=11 noMesh=0 culled=4
10-28 07:41:32.124  2172  2244 I Perf    : fps=6.5 worst=7 drawn=14/37 queue=0 build=18ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:41:34.152  2172  2244 I Perf    : fps=6.4 worst=8 drawn=14/37 queue=0 build=18ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:41:36.283  2172  2244 I Perf    : fps=6.1 worst=7 drawn=14/37 queue=0 build=18ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:41:38.338  2172  2244 I Perf    : fps=6.3 worst=7 drawn=14/37 queue=0 build=18ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:41:40.477  2172  2244 I Perf    : fps=6.5 worst=7 drawn=14/37 queue=0 build=18ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:41:42.525  2172  2244 I Perf    : fps=6.3 worst=8 drawn=14/37 queue=0 build=18ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:41:44.620  2172  2244 I Perf    : fps=6.7 worst=7 drawn=14/37 queue=0 build=18ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:41:46.676  2172  2244 I Perf    : fps=6.3 worst=7 drawn=14/37 queue=0 build=18ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:41:48.775  2172  2244 I Perf    : fps=6.2 worst=7 drawn=14/37 queue=0 build=18ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:41:50.829  2172  2244 I Perf    : fps=6.3 worst=7 drawn=14/37 queue=0 build=18ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:41:52.883  2172  2244 I Perf    : fps=6.3 worst=7 drawn=14/37 queue=0 build=18ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:41:54.948  2172  2244 I Perf    : fps=6.3 worst=7 drawn=14/37 queue=0 build=18ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:41:56.977  2172  2244 I Perf    : fps=6.4 worst=8 drawn=14/37 queue=0 build=18ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:41:58.767  2463  2463 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 07:41:58.784  2463  2463 I AndroidRuntime: Using default boot image
10-28 07:41:58.784  2463  2463 I AndroidRuntime: Leaving lock profiling enabled
10-28 07:41:58.898  2463  2463 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 07:41:59.006  2172  2244 I Perf    : fps=6.4 worst=7 drawn=14/37 queue=0 build=18ms verts=35k cam=0.5,1.6,0.5 yaw=0.37 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:41:59.510  2463  2463 D AndroidRuntime: Shutting down VM
10-28 07:42:01.098  2172  2244 I Perf    : fps=5.3 worst=6 drawn=13/37 queue=0 build=18ms verts=32k cam=0.5,1.6,0.5 yaw=2.07 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 07:42:03.119  2172  2244 I Perf    : fps=5.4 worst=6 drawn=13/37 queue=0 build=18ms verts=32k cam=0.5,1.6,0.5 yaw=2.07 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 07:42:04.684  2479  2479 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 07:42:04.702  2479  2479 I AndroidRuntime: Using default boot image
10-28 07:42:04.702  2479  2479 I AndroidRuntime: Leaving lock profiling enabled
10-28 07:42:04.800  2479  2479 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 07:42:04.947  2479  2479 D AndroidRuntime: Shutting down VM
10-28 07:42:04.950  2479  2491 I AndroidRuntime: NOTE: attach of thread 'Binder:2479_2' failed
10-28 07:42:05.180  2172  2244 I Perf    : fps=5.3 worst=6 drawn=13/37 queue=0 build=18ms verts=32k cam=0.5,1.6,0.5 yaw=2.07 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 07:42:07.317  2172  2244 I Perf    : fps=5.2 worst=6 drawn=13/37 queue=0 build=18ms verts=32k cam=0.5,1.6,0.5 yaw=2.07 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 07:42:09.382  2172  2244 I Perf    : fps=5.3 worst=6 drawn=13/37 queue=0 build=18ms verts=32k cam=0.5,1.6,0.5 yaw=2.07 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 07:42:09.816  2496  2496 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 07:42:09.826  2496  2496 I AndroidRuntime: Using default boot image
10-28 07:42:09.826  2496  2496 I AndroidRuntime: Leaving lock profiling enabled
10-28 07:42:09.903  2496  2496 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 07:42:11.386  2172  2244 I Perf    : fps=5.5 worst=6 drawn=14/41 queue=2 build=17ms verts=35k cam=-0.5,1.6,1.0 yaw=2.07 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=25
10-28 07:42:11.420  2496  2496 D AndroidRuntime: Shutting down VM
10-28 07:42:13.448  2172  2244 I Perf    : fps=5.3 worst=6 drawn=18/44 queue=0 build=20ms verts=46k cam=-0.9,1.6,1.3 yaw=2.07 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-28 07:42:15.484  2172  2244 I Perf    : fps=4.9 worst=6 drawn=18/44 queue=0 build=20ms verts=46k cam=-0.9,1.6,1.3 yaw=2.07 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-28 07:42:17.564  2172  2244 I Perf    : fps=5.3 worst=6 drawn=18/44 queue=0 build=20ms verts=46k cam=-0.9,1.6,1.3 yaw=2.07 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-28 07:42:19.599  2172  2244 I Perf    : fps=5.4 worst=6 drawn=18/44 queue=0 build=20ms verts=46k cam=-0.9,1.6,1.3 yaw=2.07 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
```
## Step log
```
,937 stickZone=864
10-28 07:41:29.994  2172  2244 I Perf    : fps=6.4 worst=50 drawn=11/17 queue=1 build=18ms verts=28k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=11 noMesh=0 culled=4
10-28 07:41:32.124  2172  2244 I Perf    : fps=6.5 worst=7 drawn=14/37 queue=0 build=18ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:41:34.152  2172  2244 I Perf    : fps=6.4 worst=8 drawn=14/37 queue=0 build=18ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:41:36.283  2172  2244 I Perf    : fps=6.1 worst=7 drawn=14/37 queue=0 build=18ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:41:38.338  2172  2244 I Perf    : fps=6.3 worst=7 drawn=14/37 queue=0 build=18ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:41:40.477  2172  2244 I Perf    : fps=6.5 worst=7 drawn=14/37 queue=0 build=18ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:41:42.525  2172  2244 I Perf    : fps=6.3 worst=8 drawn=14/37 queue=0 build=18ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:41:44.620  2172  2244 I Perf    : fps=6.7 worst=7 drawn=14/37 queue=0 build=18ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:41:46.676  2172  2244 I Perf    : fps=6.3 worst=7 drawn=14/37 queue=0 build=18ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:41:48.775  2172  2244 I Perf    : fps=6.2 worst=7 drawn=14/37 queue=0 build=18ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:41:50.829  2172  2244 I Perf    : fps=6.3 worst=7 drawn=14/37 queue=0 build=18ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:41:52.883  2172  2244 I Perf    : fps=6.3 worst=7 drawn=14/37 queue=0 build=18ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:41:53.547  2439  2439 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 07:41:54.948  2172  2244 I Perf    : fps=6.3 worst=7 drawn=14/37 queue=0 build=18ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:41:56.977  2172  2244 I Perf    : fps=6.4 worst=8 drawn=14/37 queue=0 build=18ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:41:58.767  2463  2463 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 07:41:58.784  2463  2463 I AndroidRuntime: Using default boot image
10-28 07:41:58.784  2463  2463 I AndroidRuntime: Leaving lock profiling enabled
10-28 07:41:58.898  2463  2463 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 07:41:59.006  2172  2244 I Perf    : fps=6.4 worst=7 drawn=14/37 queue=0 build=18ms verts=35k cam=0.5,1.6,0.5 yaw=0.37 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:41:59.510  2463  2463 D AndroidRuntime: Shutting down VM
10-28 07:42:01.098  2172  2244 I Perf    : fps=5.3 worst=6 drawn=13/37 queue=0 build=18ms verts=32k cam=0.5,1.6,0.5 yaw=2.07 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 07:42:03.119  2172  2244 I Perf    : fps=5.4 worst=6 drawn=13/37 queue=0 build=18ms verts=32k cam=0.5,1.6,0.5 yaw=2.07 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 07:42:04.684  2479  2479 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 07:42:04.702  2479  2479 I AndroidRuntime: Using default boot image
10-28 07:42:04.702  2479  2479 I AndroidRuntime: Leaving lock profiling enabled
10-28 07:42:04.800  2479  2479 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 07:42:04.947  2479  2479 D AndroidRuntime: Shutting down VM
10-28 07:42:04.950  2479  2491 I AndroidRuntime: NOTE: attach of thread 'Binder:2479_2' failed
10-28 07:42:05.180  2172  2244 I Perf    : fps=5.3 worst=6 drawn=13/37 queue=0 build=18ms verts=32k cam=0.5,1.6,0.5 yaw=2.07 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 07:42:07.317  2172  2244 I Perf    : fps=5.2 worst=6 drawn=13/37 queue=0 build=18ms verts=32k cam=0.5,1.6,0.5 yaw=2.07 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 07:42:09.382  2172  2244 I Perf    : fps=5.3 worst=6 drawn=13/37 queue=0 build=18ms verts=32k cam=0.5,1.6,0.5 yaw=2.07 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 07:42:09.816  2496  2496 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 07:42:09.826  2496  2496 I AndroidRuntime: Using default boot image
10-28 07:42:09.826  2496  2496 I AndroidRuntime: Leaving lock profiling enabled
10-28 07:42:09.903  2496  2496 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 07:42:11.386  2172  2244 I Perf    : fps=5.5 worst=6 drawn=14/41 queue=2 build=17ms verts=35k cam=-0.5,1.6,1.0 yaw=2.07 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=25
10-28 07:42:11.420  2496  2496 D AndroidRuntime: Shutting down VM
10-28 07:42:13.448  2172  2244 I Perf    : fps=5.3 worst=6 drawn=18/44 queue=0 build=20ms verts=46k cam=-0.9,1.6,1.3 yaw=2.07 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-28 07:42:15.484  2172  2244 I Perf    : fps=4.9 worst=6 drawn=18/44 queue=0 build=20ms verts=46k cam=-0.9,1.6,1.3 yaw=2.07 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-28 07:42:16.777  2516  2516 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 07:42:17.564  2172  2244 I Perf    : fps=5.3 worst=6 drawn=18/44 queue=0 build=20ms verts=46k cam=-0.9,1.6,1.3 yaw=2.07 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-28 07:42:19.599  2172  2244 I Perf    : fps=5.4 worst=6 drawn=18/44 queue=0 build=20ms verts=46k cam=-0.9,1.6,1.3 yaw=2.07 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
+ '[' -z 2172 ']'
+ grep -qa 'FATAL EXCEPTION' emulator-shots/logcat.txt
+ '[' 0 '!=' 0 ']'
+ echo 'OK: the app is alive, rendering, and the touch controls work'
OK: the app is alive, rendering, and the touch controls work
```
