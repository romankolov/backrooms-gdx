# Emulator smoke test diagnostics

Commit: 118142b3388e73f833d5bd1f84788bd47e6053ee
Outcome: success

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 28 12:05 /dev/kvm
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
INFO         | The active JSON Web Key Sets can be found here: /run/user/1001/avd/running/2959/jwks/57da235d-6631-48e1-8748-07447aea5578/active.jwk
INFO         | Scanning /run/user/1001/avd/running/2959/jwks/57da235d-6631-48e1-8748-07447aea5578 for jwk keys.
INFO         | Started GRPC server at 127.0.0.1:8554, security: Local, auth: +token
INFO         | Advertising in: /run/user/1001/avd/running/pid_2959.ini
INFO         | Setting display: 0 configuration to: 1080x1920, dpi: 420x420 
INFO         | setDisplayActiveConfig: id:0, 1080x1920
INFO         | emulatorSetupEnvironment: Environment scene is not required
USER_INFO    | Emulator is performing a full startup. This may take upto two minutes, or more.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
INFO         | Boot completed in 34596 ms
INFO         | Increasing screen off timeout, logcat buffer size to 2M.
```
## Logcat (filtered)
```
10-28 12:20:07.118   512   552 W ActivityManager: Slow operation: 138ms so far, now at startProcess: building log message
10-28 12:20:07.118   512   552 I ActivityManager: Start proc 2341:com.android.traceur/u0a92 for broadcast {com.android.traceur/com.android.traceur.Receiver}
10-28 12:20:07.118   512   552 W ActivityManager: Slow operation: 138ms so far, now at startProcess: starting to update pids map
10-28 12:20:07.119   512   552 W ActivityManager: Slow operation: 139ms so far, now at startProcess: done updating pids map
10-28 12:20:07.407   512  2095 I ActivityManager: Finished processing BOOT_COMPLETED for u0
10-28 12:20:07.408   512  2095 I ActivityManager: Killing 1924:com.android.printspooler/u0a88 (adj 985): empty #17
10-28 12:20:21.849   512   550 I ActivityManager: Killing 1290:com.android.cellbroadcastreceiver/u0a64 (adj 985): empty #17
10-28 12:20:28.423  2407  2407 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 12:20:28.447  2407  2407 I AndroidRuntime: Using default boot image
10-28 12:20:28.447  2407  2407 I AndroidRuntime: Leaving lock profiling enabled
10-28 12:20:28.637  2407  2407 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 12:20:28.701  2407  2407 D AndroidRuntime: Shutting down VM
10-28 12:20:28.991   512   542 I ActivityManager: Killing 2035:com.android.calendar/u0a108 (adj 985): empty #17
10-28 12:20:28.992   512   542 I ActivityManager: Killing 1956:com.android.quicksearchbox/u0a101 (adj 985): empty #18
10-28 12:20:29.046   512   830 I ActivityManager: Killing 2057:com.android.camera2/u0a111 (adj 975): empty #17
10-28 12:20:29.300  2144  2227 I BackroomsGL: world shader compiled
10-28 12:20:29.383  2144  2227 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 12:20:29.383  2144  2227 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 12:20:31.379  2144  2227 I Perf    : fps=2.2 worst=9 drawn=4/6 queue=2 build=35ms verts=10k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=4 noMesh=0 culled=0
10-28 12:20:33.500  2144  2227 I Perf    : fps=3.3 worst=3 drawn=11/17 queue=2 build=29ms verts=28k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=11 noMesh=0 culled=4
10-28 12:20:35.523  2144  2227 I Perf    : fps=3.5 worst=3 drawn=14/31 queue=1 build=202ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=15
10-28 12:20:37.764  2144  2227 I Perf    : fps=3.5 worst=3 drawn=14/37 queue=0 build=18ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:20:39.766  2144  2227 I Perf    : fps=3.5 worst=4 drawn=14/37 queue=0 build=18ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:20:41.810  2144  2227 I Perf    : fps=3.4 worst=3 drawn=14/37 queue=0 build=18ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:20:43.977  2144  2227 I Perf    : fps=3.2 worst=3 drawn=14/37 queue=0 build=18ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:20:46.071  2144  2227 I Perf    : fps=3.3 worst=4 drawn=14/37 queue=0 build=18ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:20:48.394  2144  2227 I Perf    : fps=3.4 worst=3 drawn=14/37 queue=0 build=18ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:20:50.447  2144  2227 I Perf    : fps=3.4 worst=3 drawn=14/37 queue=0 build=18ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:20:52.502  2144  2227 I Perf    : fps=3.4 worst=3 drawn=14/37 queue=0 build=18ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:20:54.688  2144  2227 I Perf    : fps=3.2 worst=3 drawn=14/37 queue=0 build=18ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:20:56.757  2144  2227 I Perf    : fps=3.4 worst=3 drawn=14/37 queue=0 build=18ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:20:58.806  2144  2227 I Perf    : fps=3.4 worst=3 drawn=14/37 queue=0 build=18ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:21:00.857  2144  2227 I Perf    : fps=3.4 worst=3 drawn=14/37 queue=0 build=18ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:21:01.894  2461  2461 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 12:21:01.917  2461  2461 I AndroidRuntime: Using default boot image
10-28 12:21:01.917  2461  2461 I AndroidRuntime: Leaving lock profiling enabled
10-28 12:21:02.243  2461  2461 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 12:21:02.860  2461  2461 D AndroidRuntime: Shutting down VM
10-28 12:21:02.897  2144  2227 I Perf    : fps=3.4 worst=4 drawn=14/37 queue=0 build=18ms verts=36k cam=0.5,1.6,0.5 yaw=2.83 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:21:04.911  2144  2227 I Perf    : fps=3.5 worst=3 drawn=17/37 queue=0 build=18ms verts=43k cam=0.5,1.6,0.5 yaw=2.83 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-28 12:21:06.994  2144  2227 I Perf    : fps=3.4 worst=3 drawn=17/37 queue=0 build=18ms verts=43k cam=0.5,1.6,0.5 yaw=2.83 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-28 12:21:07.950  2477  2477 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 12:21:07.961  2477  2477 I AndroidRuntime: Using default boot image
10-28 12:21:07.961  2477  2477 I AndroidRuntime: Leaving lock profiling enabled
10-28 12:21:08.094  2477  2477 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 12:21:08.097  2477  2477 D AndroidRuntime: Shutting down VM
10-28 12:21:09.070  2144  2227 I Perf    : fps=3.4 worst=3 drawn=17/37 queue=0 build=18ms verts=43k cam=0.5,1.6,0.5 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 12:21:11.310  2144  2227 I Perf    : fps=3.6 worst=4 drawn=17/37 queue=0 build=18ms verts=43k cam=0.5,1.6,0.5 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 12:21:13.145  2494  2494 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 12:21:13.162  2494  2494 I AndroidRuntime: Using default boot image
10-28 12:21:13.162  2494  2494 I AndroidRuntime: Leaving lock profiling enabled
10-28 12:21:13.305  2494  2494 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 12:21:13.335  2144  2227 I Perf    : fps=3.5 worst=4 drawn=17/37 queue=0 build=18ms verts=43k cam=0.5,1.6,0.5 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 12:21:14.907  2494  2494 D AndroidRuntime: Shutting down VM
10-28 12:21:15.370  2144  2227 I Perf    : fps=3.4 worst=3 drawn=17/37 queue=0 build=18ms verts=43k cam=0.2,1.6,1.4 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 12:21:17.495  2144  2227 I Perf    : fps=3.3 worst=4 drawn=17/37 queue=0 build=18ms verts=43k cam=0.2,1.6,1.5 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 12:21:19.733  2144  2227 I Perf    : fps=3.1 worst=3 drawn=17/37 queue=0 build=18ms verts=43k cam=0.2,1.6,1.5 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 12:21:21.981  2144  2227 I Perf    : fps=3.1 worst=3 drawn=17/37 queue=0 build=18ms verts=43k cam=0.2,1.6,1.5 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 12:21:24.162  2144  2227 I Perf    : fps=3.2 worst=3 drawn=17/37 queue=0 build=18ms verts=43k cam=0.2,1.6,1.5 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 12:21:26.340  2144  2227 I Perf    : fps=3.2 worst=3 drawn=17/37 queue=0 build=18ms verts=43k cam=0.2,1.6,1.5 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
```
## Step log
```
=11/17 queue=2 build=29ms verts=28k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=11 noMesh=0 culled=4
10-28 12:20:35.523  2144  2227 I Perf    : fps=3.5 worst=3 drawn=14/31 queue=1 build=202ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=15
10-28 12:20:37.764  2144  2227 I Perf    : fps=3.5 worst=3 drawn=14/37 queue=0 build=18ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:20:39.766  2144  2227 I Perf    : fps=3.5 worst=4 drawn=14/37 queue=0 build=18ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:20:41.810  2144  2227 I Perf    : fps=3.4 worst=3 drawn=14/37 queue=0 build=18ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:20:43.977  2144  2227 I Perf    : fps=3.2 worst=3 drawn=14/37 queue=0 build=18ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:20:46.071  2144  2227 I Perf    : fps=3.3 worst=4 drawn=14/37 queue=0 build=18ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:20:48.394  2144  2227 I Perf    : fps=3.4 worst=3 drawn=14/37 queue=0 build=18ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:20:50.447  2144  2227 I Perf    : fps=3.4 worst=3 drawn=14/37 queue=0 build=18ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:20:52.502  2144  2227 I Perf    : fps=3.4 worst=3 drawn=14/37 queue=0 build=18ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:20:54.688  2144  2227 I Perf    : fps=3.2 worst=3 drawn=14/37 queue=0 build=18ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:20:54.877  2445  2445 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 12:20:56.757  2144  2227 I Perf    : fps=3.4 worst=3 drawn=14/37 queue=0 build=18ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:20:58.806  2144  2227 I Perf    : fps=3.4 worst=3 drawn=14/37 queue=0 build=18ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:21:00.857  2144  2227 I Perf    : fps=3.4 worst=3 drawn=14/37 queue=0 build=18ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:21:01.894  2461  2461 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 12:21:01.917  2461  2461 I AndroidRuntime: Using default boot image
10-28 12:21:01.917  2461  2461 I AndroidRuntime: Leaving lock profiling enabled
10-28 12:21:02.243  2461  2461 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 12:21:02.860  2461  2461 D AndroidRuntime: Shutting down VM
10-28 12:21:02.897  2144  2227 I Perf    : fps=3.4 worst=4 drawn=14/37 queue=0 build=18ms verts=36k cam=0.5,1.6,0.5 yaw=2.83 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:21:04.911  2144  2227 I Perf    : fps=3.5 worst=3 drawn=17/37 queue=0 build=18ms verts=43k cam=0.5,1.6,0.5 yaw=2.83 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-28 12:21:06.994  2144  2227 I Perf    : fps=3.4 worst=3 drawn=17/37 queue=0 build=18ms verts=43k cam=0.5,1.6,0.5 yaw=2.83 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-28 12:21:07.950  2477  2477 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 12:21:07.961  2477  2477 I AndroidRuntime: Using default boot image
10-28 12:21:07.961  2477  2477 I AndroidRuntime: Leaving lock profiling enabled
10-28 12:21:08.094  2477  2477 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 12:21:08.097  2477  2477 D AndroidRuntime: Shutting down VM
10-28 12:21:09.070  2144  2227 I Perf    : fps=3.4 worst=3 drawn=17/37 queue=0 build=18ms verts=43k cam=0.5,1.6,0.5 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 12:21:11.310  2144  2227 I Perf    : fps=3.6 worst=4 drawn=17/37 queue=0 build=18ms verts=43k cam=0.5,1.6,0.5 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 12:21:13.145  2494  2494 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 12:21:13.162  2494  2494 I AndroidRuntime: Using default boot image
10-28 12:21:13.162  2494  2494 I AndroidRuntime: Leaving lock profiling enabled
10-28 12:21:13.305  2494  2494 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 12:21:13.335  2144  2227 I Perf    : fps=3.5 worst=4 drawn=17/37 queue=0 build=18ms verts=43k cam=0.5,1.6,0.5 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 12:21:14.907  2494  2494 D AndroidRuntime: Shutting down VM
10-28 12:21:15.370  2144  2227 I Perf    : fps=3.4 worst=3 drawn=17/37 queue=0 build=18ms verts=43k cam=0.2,1.6,1.4 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 12:21:17.495  2144  2227 I Perf    : fps=3.3 worst=4 drawn=17/37 queue=0 build=18ms verts=43k cam=0.2,1.6,1.5 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 12:21:19.733  2144  2227 I Perf    : fps=3.1 worst=3 drawn=17/37 queue=0 build=18ms verts=43k cam=0.2,1.6,1.5 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 12:21:20.292  2513  2513 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 12:21:21.981  2144  2227 I Perf    : fps=3.1 worst=3 drawn=17/37 queue=0 build=18ms verts=43k cam=0.2,1.6,1.5 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 12:21:24.162  2144  2227 I Perf    : fps=3.2 worst=3 drawn=17/37 queue=0 build=18ms verts=43k cam=0.2,1.6,1.5 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 12:21:26.340  2144  2227 I Perf    : fps=3.2 worst=3 drawn=17/37 queue=0 build=18ms verts=43k cam=0.2,1.6,1.5 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
+ '[' -z 2144 ']'
+ grep -qa 'FATAL EXCEPTION' emulator-shots/logcat.txt
+ '[' 0 '!=' 0 ']'
+ echo 'OK: the app is alive, rendering, and the touch controls work'
OK: the app is alive, rendering, and the touch controls work
```
