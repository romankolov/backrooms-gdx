# Emulator smoke test diagnostics

Commit: ab84bd339c38171c499f17c98c8d609330b1f97d
Outcome: success

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 28 08:05 /dev/kvm
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
INFO         | The active JSON Web Key Sets can be found here: /run/user/1001/avd/running/2736/jwks/0407f1e2-58f6-4729-8552-d90f9c75e0ed/active.jwk
INFO         | Scanning /run/user/1001/avd/running/2736/jwks/0407f1e2-58f6-4729-8552-d90f9c75e0ed for jwk keys.
INFO         | Started GRPC server at 127.0.0.1:8554, security: Local, auth: +token
INFO         | Advertising in: /run/user/1001/avd/running/pid_2736.ini
INFO         | Setting display: 0 configuration to: 1080x1920, dpi: 420x420 
INFO         | setDisplayActiveConfig: id:0, 1080x1920
INFO         | emulatorSetupEnvironment: Environment scene is not required
USER_INFO    | Emulator is performing a full startup. This may take upto two minutes, or more.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
INFO         | Boot completed in 20133 ms
INFO         | Increasing screen off timeout, logcat buffer size to 2M.
```
## Logcat (filtered)
```
10-28 08:09:13.175   507   547 W ActivityManager: Slow operation: 60ms so far, now at startProcess: returned from zygote!
10-28 08:09:13.176   507   547 W ActivityManager: Slow operation: 61ms so far, now at startProcess: done updating battery stats
10-28 08:09:13.176   507   547 W ActivityManager: Slow operation: 61ms so far, now at startProcess: building log message
10-28 08:09:13.176   507   547 I ActivityManager: Start proc 2290:com.android.traceur/u0a90 for broadcast {com.android.traceur/com.android.traceur.Receiver}
10-28 08:09:13.176   507   547 W ActivityManager: Slow operation: 61ms so far, now at startProcess: starting to update pids map
10-28 08:09:13.176   507   547 W ActivityManager: Slow operation: 61ms so far, now at startProcess: done updating pids map
10-28 08:09:13.574   507   534 I ActivityManager: Finished processing BOOT_COMPLETED for u0
10-28 08:09:13.575   507   534 I ActivityManager: Killing 1718:com.android.externalstorage/u0a68 (adj 985): empty #17
10-28 08:09:14.881   430  2012 I iorapd  : Perfetto TraceBuffer saved to file: /data/misc/iorapd/com.backrooms.gdx.debug/1/com.backrooms.gdx.android.AndroidLauncher/raw_traces/1793174954881583014.perfetto_trace.pb
10-28 08:09:28.690   507   537 I ActivityManager: Killing 1322:com.android.cellbroadcastreceiver/u0a66 (adj 985): empty #17
10-28 08:09:31.935  2340  2340 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:09:31.937  2340  2340 I AndroidRuntime: Using default boot image
10-28 08:09:31.937  2340  2340 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:09:32.013  2340  2340 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:09:32.031  2340  2340 D AndroidRuntime: Shutting down VM
10-28 08:09:32.550  2006  2062 I BackroomsGL: world shader compiled
10-28 08:09:32.602  2006  2062 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 08:09:32.602  2006  2062 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 08:09:33.405   507   537 I ActivityManager: Killing 2073:com.android.calendar/u0a107 (adj 985): empty #17
10-28 08:09:33.405   507   537 I ActivityManager: Killing 1938:com.android.quicksearchbox/u0a104 (adj 985): empty #18
10-28 08:09:33.480   507  2311 I ActivityManager: Killing 2107:com.android.camera2/u0a111 (adj 975): empty #17
10-28 08:09:34.540  2006  2062 I Perf    : fps=6.4 worst=46 drawn=10/16 queue=2 build=12ms verts=26k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=10 noMesh=0 culled=4
10-28 08:09:36.688  2006  2062 I Perf    : fps=6.0 worst=6 drawn=14/37 queue=0 build=181ms verts=37k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=22
10-28 08:09:38.700  2006  2062 I Perf    : fps=6.5 worst=7 drawn=14/37 queue=0 build=181ms verts=37k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:09:40.767  2006  2062 I Perf    : fps=6.3 worst=6 drawn=14/37 queue=0 build=181ms verts=37k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:09:42.840  2006  2062 I Perf    : fps=6.3 worst=7 drawn=14/37 queue=0 build=181ms verts=37k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:09:44.975  2006  2062 I Perf    : fps=6.1 worst=7 drawn=14/37 queue=0 build=181ms verts=37k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:09:46.992  2006  2062 I Perf    : fps=6.4 worst=7 drawn=14/37 queue=0 build=181ms verts=37k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:09:49.053  2006  2062 I Perf    : fps=6.3 worst=7 drawn=14/37 queue=0 build=181ms verts=37k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:09:51.058  2006  2062 I Perf    : fps=6.5 worst=7 drawn=14/37 queue=0 build=181ms verts=37k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:09:53.066  2006  2062 I Perf    : fps=6.5 worst=7 drawn=14/37 queue=0 build=181ms verts=37k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:09:55.075  2006  2062 I Perf    : fps=6.5 worst=7 drawn=14/37 queue=0 build=181ms verts=37k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:09:57.174  2006  2062 I Perf    : fps=6.2 worst=7 drawn=14/37 queue=0 build=181ms verts=37k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:09:59.229  2006  2062 I Perf    : fps=6.4 worst=7 drawn=14/37 queue=0 build=181ms verts=37k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:10:01.376  2006  2062 I Perf    : fps=6.0 worst=6 drawn=14/37 queue=0 build=181ms verts=37k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:10:01.530  2384  2384 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:10:01.531  2384  2384 I AndroidRuntime: Using default boot image
10-28 08:10:01.531  2384  2384 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:10:01.809  2384  2384 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:10:02.500  2384  2384 D AndroidRuntime: Shutting down VM
10-28 08:10:03.428  2006  2062 I Perf    : fps=6.8 worst=8 drawn=21/37 queue=0 build=181ms verts=55k cam=-0.5,1.6,1.5 yaw=2.36 pitch=0.00 torch=1 ready=true drawn=21 noMesh=0 culled=16
10-28 08:10:05.465  2006  2062 I Perf    : fps=7.4 worst=8 drawn=21/37 queue=0 build=181ms verts=55k cam=-0.5,1.6,1.5 yaw=2.36 pitch=0.00 torch=1 ready=true drawn=21 noMesh=0 culled=16
10-28 08:10:07.127  2400  2400 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:10:07.141  2400  2400 I AndroidRuntime: Using default boot image
10-28 08:10:07.141  2400  2400 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:10:07.281  2400  2400 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:10:07.290  2400  2400 D AndroidRuntime: Shutting down VM
10-28 08:10:07.571  2006  2062 I Perf    : fps=7.1 worst=8 drawn=21/37 queue=0 build=181ms verts=55k cam=-0.5,1.6,1.5 yaw=2.36 pitch=0.00 torch=0 ready=true drawn=21 noMesh=0 culled=16
10-28 08:10:09.645  2006  2062 I Perf    : fps=7.2 worst=8 drawn=21/37 queue=0 build=181ms verts=55k cam=-0.5,1.6,1.5 yaw=2.36 pitch=0.00 torch=0 ready=true drawn=21 noMesh=0 culled=16
10-28 08:10:11.733  2006  2062 I Perf    : fps=7.2 worst=8 drawn=21/37 queue=0 build=181ms verts=55k cam=-0.5,1.6,1.5 yaw=2.36 pitch=0.00 torch=0 ready=true drawn=21 noMesh=0 culled=16
10-28 08:10:12.116  2435  2435 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:10:12.118  2435  2435 I AndroidRuntime: Using default boot image
10-28 08:10:12.118  2435  2435 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:10:12.204  2435  2435 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:10:13.776  2435  2435 D AndroidRuntime: Shutting down VM
10-28 08:10:13.811  2006  2062 I Perf    : fps=7.2 worst=8 drawn=20/37 queue=0 build=181ms verts=52k cam=-1.7,1.6,2.7 yaw=2.36 pitch=0.00 torch=0 ready=true drawn=20 noMesh=0 culled=17
10-28 08:10:15.867  2006  2062 I Perf    : fps=7.3 worst=8 drawn=20/37 queue=0 build=181ms verts=52k cam=-1.8,1.6,2.8 yaw=2.36 pitch=0.00 torch=0 ready=true drawn=20 noMesh=0 culled=17
10-28 08:10:17.952  2006  2062 I Perf    : fps=7.2 worst=8 drawn=20/37 queue=0 build=181ms verts=52k cam=-1.8,1.6,2.8 yaw=2.36 pitch=0.00 torch=0 ready=true drawn=20 noMesh=0 culled=17
10-28 08:10:19.971  2006  2062 I Perf    : fps=6.9 worst=7 drawn=20/37 queue=0 build=181ms verts=52k cam=-1.8,1.6,2.8 yaw=2.36 pitch=0.00 torch=0 ready=true drawn=20 noMesh=0 culled=17
10-28 08:10:22.095  2006  2062 I Perf    : fps=7.1 worst=8 drawn=20/37 queue=0 build=181ms verts=52k cam=-1.8,1.6,2.8 yaw=2.36 pitch=0.00 torch=0 ready=true drawn=20 noMesh=0 culled=17
```
## Step log
```
 2062 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 08:09:32.602  2006  2062 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 08:09:34.540  2006  2062 I Perf    : fps=6.4 worst=46 drawn=10/16 queue=2 build=12ms verts=26k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=10 noMesh=0 culled=4
10-28 08:09:36.688  2006  2062 I Perf    : fps=6.0 worst=6 drawn=14/37 queue=0 build=181ms verts=37k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=22
10-28 08:09:38.700  2006  2062 I Perf    : fps=6.5 worst=7 drawn=14/37 queue=0 build=181ms verts=37k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:09:40.767  2006  2062 I Perf    : fps=6.3 worst=6 drawn=14/37 queue=0 build=181ms verts=37k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:09:42.840  2006  2062 I Perf    : fps=6.3 worst=7 drawn=14/37 queue=0 build=181ms verts=37k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:09:44.975  2006  2062 I Perf    : fps=6.1 worst=7 drawn=14/37 queue=0 build=181ms verts=37k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:09:46.992  2006  2062 I Perf    : fps=6.4 worst=7 drawn=14/37 queue=0 build=181ms verts=37k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:09:49.053  2006  2062 I Perf    : fps=6.3 worst=7 drawn=14/37 queue=0 build=181ms verts=37k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:09:51.058  2006  2062 I Perf    : fps=6.5 worst=7 drawn=14/37 queue=0 build=181ms verts=37k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:09:53.066  2006  2062 I Perf    : fps=6.5 worst=7 drawn=14/37 queue=0 build=181ms verts=37k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:09:55.075  2006  2062 I Perf    : fps=6.5 worst=7 drawn=14/37 queue=0 build=181ms verts=37k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:09:57.174  2006  2062 I Perf    : fps=6.2 worst=7 drawn=14/37 queue=0 build=181ms verts=37k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:09:57.855  2378  2378 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 08:09:59.229  2006  2062 I Perf    : fps=6.4 worst=7 drawn=14/37 queue=0 build=181ms verts=37k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:10:01.376  2006  2062 I Perf    : fps=6.0 worst=6 drawn=14/37 queue=0 build=181ms verts=37k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:10:01.530  2384  2384 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:10:01.531  2384  2384 I AndroidRuntime: Using default boot image
10-28 08:10:01.531  2384  2384 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:10:01.809  2384  2384 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:10:02.500  2384  2384 D AndroidRuntime: Shutting down VM
10-28 08:10:03.428  2006  2062 I Perf    : fps=6.8 worst=8 drawn=21/37 queue=0 build=181ms verts=55k cam=-0.5,1.6,1.5 yaw=2.36 pitch=0.00 torch=1 ready=true drawn=21 noMesh=0 culled=16
10-28 08:10:05.465  2006  2062 I Perf    : fps=7.4 worst=8 drawn=21/37 queue=0 build=181ms verts=55k cam=-0.5,1.6,1.5 yaw=2.36 pitch=0.00 torch=1 ready=true drawn=21 noMesh=0 culled=16
10-28 08:10:07.127  2400  2400 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:10:07.141  2400  2400 I AndroidRuntime: Using default boot image
10-28 08:10:07.141  2400  2400 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:10:07.281  2400  2400 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:10:07.290  2400  2400 D AndroidRuntime: Shutting down VM
10-28 08:10:07.571  2006  2062 I Perf    : fps=7.1 worst=8 drawn=21/37 queue=0 build=181ms verts=55k cam=-0.5,1.6,1.5 yaw=2.36 pitch=0.00 torch=0 ready=true drawn=21 noMesh=0 culled=16
10-28 08:10:09.645  2006  2062 I Perf    : fps=7.2 worst=8 drawn=21/37 queue=0 build=181ms verts=55k cam=-0.5,1.6,1.5 yaw=2.36 pitch=0.00 torch=0 ready=true drawn=21 noMesh=0 culled=16
10-28 08:10:11.733  2006  2062 I Perf    : fps=7.2 worst=8 drawn=21/37 queue=0 build=181ms verts=55k cam=-0.5,1.6,1.5 yaw=2.36 pitch=0.00 torch=0 ready=true drawn=21 noMesh=0 culled=16
10-28 08:10:12.116  2435  2435 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:10:12.118  2435  2435 I AndroidRuntime: Using default boot image
10-28 08:10:12.118  2435  2435 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:10:12.204  2435  2435 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:10:13.776  2435  2435 D AndroidRuntime: Shutting down VM
10-28 08:10:13.811  2006  2062 I Perf    : fps=7.2 worst=8 drawn=20/37 queue=0 build=181ms verts=52k cam=-1.7,1.6,2.7 yaw=2.36 pitch=0.00 torch=0 ready=true drawn=20 noMesh=0 culled=17
10-28 08:10:15.867  2006  2062 I Perf    : fps=7.3 worst=8 drawn=20/37 queue=0 build=181ms verts=52k cam=-1.8,1.6,2.8 yaw=2.36 pitch=0.00 torch=0 ready=true drawn=20 noMesh=0 culled=17
10-28 08:10:17.952  2006  2062 I Perf    : fps=7.2 worst=8 drawn=20/37 queue=0 build=181ms verts=52k cam=-1.8,1.6,2.8 yaw=2.36 pitch=0.00 torch=0 ready=true drawn=20 noMesh=0 culled=17
10-28 08:10:18.976  2456  2456 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 08:10:19.971  2006  2062 I Perf    : fps=6.9 worst=7 drawn=20/37 queue=0 build=181ms verts=52k cam=-1.8,1.6,2.8 yaw=2.36 pitch=0.00 torch=0 ready=true drawn=20 noMesh=0 culled=17
10-28 08:10:22.095  2006  2062 I Perf    : fps=7.1 worst=8 drawn=20/37 queue=0 build=181ms verts=52k cam=-1.8,1.6,2.8 yaw=2.36 pitch=0.00 torch=0 ready=true drawn=20 noMesh=0 culled=17
+ '[' -z 2006 ']'
+ grep -qa 'FATAL EXCEPTION' emulator-shots/logcat.txt
+ '[' 0 '!=' 0 ']'
+ echo 'OK: the app is alive, rendering, and the touch controls work'
OK: the app is alive, rendering, and the touch controls work
```
