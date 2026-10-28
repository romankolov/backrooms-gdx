# Emulator smoke test diagnostics

Commit: 4b0757197815044539bef403e7e38f323270370e
Outcome: success

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 28 21:54 /dev/kvm
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
INFO         | The active JSON Web Key Sets can be found here: /run/user/1001/avd/running/3265/jwks/351404d9-28e6-4359-88ab-0634cc65504a/active.jwk
INFO         | Scanning /run/user/1001/avd/running/3265/jwks/351404d9-28e6-4359-88ab-0634cc65504a for jwk keys.
INFO         | Started GRPC server at 127.0.0.1:8554, security: Local, auth: +token
INFO         | Advertising in: /run/user/1001/avd/running/pid_3265.ini
INFO         | Setting display: 0 configuration to: 1080x1920, dpi: 420x420 
INFO         | setDisplayActiveConfig: id:0, 1080x1920
INFO         | emulatorSetupEnvironment: Environment scene is not required
USER_INFO    | Emulator is performing a full startup. This may take upto two minutes, or more.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
INFO         | Boot completed in 26744 ms
INFO         | Increasing screen off timeout, logcat buffer size to 2M.
```
## Logcat (filtered)
```
10-28 21:57:43.577   508   642 I ActivityManager: Killing 1861:com.android.printspooler/u0a86 (adj 985): empty #17
10-28 21:57:43.578   508   548 I ActivityManager: Start proc 2362:com.android.shell/2000 for broadcast {com.android.shell/com.android.shell.HeapDumpReceiver}
10-28 21:57:43.786   508   642 I ActivityManager: Killing 1952:com.android.localtransport/1000 (adj 985): empty #17
10-28 21:57:43.895   508   548 I ActivityManager: Start proc 2383:com.android.traceur/u0a88 for broadcast {com.android.traceur/com.android.traceur.Receiver}
10-28 21:57:44.115   508  2175 I ActivityManager: Finished processing BOOT_COMPLETED for u0
10-28 21:57:44.116   508  2175 I ActivityManager: Killing 1800:com.android.provision/u0a116 (adj 985): empty #17
10-28 21:57:58.580   508   546 I ActivityManager: Killing 1285:com.android.cellbroadcastreceiver/u0a65 (adj 985): empty #17
10-28 21:58:01.873   508   538 I ActivityManager: Killing 2107:com.android.calendar/u0a107 (adj 985): empty #17
10-28 21:58:01.874   508   538 I ActivityManager: Killing 1989:com.android.quicksearchbox/u0a101 (adj 985): empty #18
10-28 21:58:02.775  2446  2446 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 21:58:02.807  2446  2446 I AndroidRuntime: Using default boot image
10-28 21:58:02.807  2446  2446 I AndroidRuntime: Leaving lock profiling enabled
10-28 21:58:03.123  2446  2446 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 21:58:03.204  2446  2446 D AndroidRuntime: Shutting down VM
10-28 21:58:04.000  2037  2097 I BackroomsGL: world shader compiled
10-28 21:58:04.498  2037  2097 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 21:58:04.498  2037  2097 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 21:58:05.932  2037  2097 I Perf    : fps=1.3 worst=2 drawn=2/4 queue=2 build=15ms verts=4k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=2 noMesh=0 culled=0
10-28 21:58:06.973   508  1250 I ActivityManager: Killing 2134:com.android.camera2/u0a112 (adj 975): empty #17
10-28 21:58:08.117  2037  2097 I Perf    : fps=3.2 worst=3 drawn=10/16 queue=2 build=79ms verts=24k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=10 noMesh=0 culled=4
10-28 21:58:10.382  2037  2097 I Perf    : fps=3.1 worst=3 drawn=13/29 queue=2 build=33ms verts=32k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=14
10-28 21:58:12.612  2037  2097 I Perf    : fps=3.1 worst=3 drawn=14/37 queue=0 build=50ms verts=34k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:58:14.877  2037  2097 I Perf    : fps=3.1 worst=3 drawn=14/37 queue=0 build=50ms verts=34k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:58:17.010  2037  2097 I Perf    : fps=3.3 worst=3 drawn=14/37 queue=0 build=50ms verts=34k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:58:19.199  2037  2097 I Perf    : fps=3.2 worst=3 drawn=14/37 queue=0 build=50ms verts=34k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:58:21.410  2037  2097 I Perf    : fps=3.2 worst=3 drawn=14/37 queue=0 build=50ms verts=34k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:58:23.585  2037  2097 I Perf    : fps=3.2 worst=3 drawn=14/37 queue=0 build=50ms verts=34k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:58:25.823  2037  2097 I Perf    : fps=3.1 worst=3 drawn=14/37 queue=0 build=50ms verts=34k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:58:27.946  2037  2097 I Perf    : fps=3.3 worst=3 drawn=14/37 queue=0 build=50ms verts=34k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:58:29.963  2037  2097 I Perf    : fps=3.0 worst=3 drawn=14/37 queue=0 build=50ms verts=34k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:58:32.239  2037  2097 I Perf    : fps=3.1 worst=3 drawn=14/37 queue=0 build=50ms verts=34k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:58:34.530  2037  2097 I Perf    : fps=3.1 worst=3 drawn=14/37 queue=0 build=50ms verts=34k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:58:35.896  2499  2499 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 21:58:35.897  2499  2499 I AndroidRuntime: Using default boot image
10-28 21:58:35.898  2499  2499 I AndroidRuntime: Leaving lock profiling enabled
10-28 21:58:35.992  2499  2499 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 21:58:36.569  2037  2097 I Perf    : fps=2.9 worst=3 drawn=13/37 queue=0 build=50ms verts=33k cam=1.5,1.6,1.5 yaw=1.37 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 21:58:36.593  2499  2499 D AndroidRuntime: Shutting down VM
10-28 21:58:38.737  2037  2097 I Perf    : fps=4.6 worst=5 drawn=16/37 queue=0 build=50ms verts=41k cam=1.5,1.6,1.5 yaw=2.75 pitch=0.00 torch=1 ready=true drawn=16 noMesh=0 culled=21
10-28 21:58:40.847  2037  2097 I Perf    : fps=4.7 worst=5 drawn=16/37 queue=0 build=50ms verts=41k cam=1.5,1.6,1.5 yaw=2.75 pitch=0.00 torch=1 ready=true drawn=16 noMesh=0 culled=21
10-28 21:58:41.548  2515  2515 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 21:58:41.567  2515  2515 I AndroidRuntime: Using default boot image
10-28 21:58:41.567  2515  2515 I AndroidRuntime: Leaving lock profiling enabled
10-28 21:58:41.704  2515  2515 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 21:58:41.711  2515  2515 D AndroidRuntime: Shutting down VM
10-28 21:58:41.719  2515  2526 I AndroidRuntime: NOTE: attach of thread 'Binder:2515_1' failed
10-28 21:58:42.877  2037  2097 I Perf    : fps=4.4 worst=4 drawn=16/37 queue=0 build=50ms verts=41k cam=1.5,1.6,1.5 yaw=2.75 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 21:58:44.986  2037  2097 I Perf    : fps=4.7 worst=5 drawn=16/37 queue=0 build=50ms verts=41k cam=1.5,1.6,1.5 yaw=2.75 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 21:58:46.602  2531  2531 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 21:58:46.617  2531  2531 I AndroidRuntime: Using default boot image
10-28 21:58:46.617  2531  2531 I AndroidRuntime: Leaving lock profiling enabled
10-28 21:58:46.773  2531  2531 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 21:58:46.988  2037  2097 I Perf    : fps=4.5 worst=4 drawn=16/37 queue=0 build=50ms verts=41k cam=1.5,1.6,1.5 yaw=2.75 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 21:58:48.365  2531  2531 D AndroidRuntime: Shutting down VM
10-28 21:58:49.167  2037  2097 I Perf    : fps=4.6 worst=5 drawn=16/37 queue=0 build=50ms verts=41k cam=0.9,1.6,2.9 yaw=2.75 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 21:58:51.232  2037  2097 I Perf    : fps=4.4 worst=4 drawn=16/37 queue=0 build=50ms verts=41k cam=0.9,1.6,2.9 yaw=2.75 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 21:58:53.476  2037  2097 I Perf    : fps=4.5 worst=5 drawn=16/37 queue=0 build=50ms verts=41k cam=0.9,1.6,2.9 yaw=2.75 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 21:58:55.515  2037  2097 I Perf    : fps=4.4 worst=4 drawn=16/37 queue=0 build=50ms verts=41k cam=0.9,1.6,2.9 yaw=2.75 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 21:58:57.581  2037  2097 I Perf    : fps=4.4 worst=4 drawn=16/37 queue=0 build=50ms verts=41k cam=0.9,1.6,2.9 yaw=2.75 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 21:58:59.590  2037  2097 I Perf    : fps=4.5 worst=4 drawn=16/37 queue=0 build=50ms verts=41k cam=0.9,1.6,2.9 yaw=2.75 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
```
## Step log
```
rawn=2 noMesh=0 culled=0
10-28 21:58:08.117  2037  2097 I Perf    : fps=3.2 worst=3 drawn=10/16 queue=2 build=79ms verts=24k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=10 noMesh=0 culled=4
10-28 21:58:10.382  2037  2097 I Perf    : fps=3.1 worst=3 drawn=13/29 queue=2 build=33ms verts=32k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=14
10-28 21:58:12.612  2037  2097 I Perf    : fps=3.1 worst=3 drawn=14/37 queue=0 build=50ms verts=34k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:58:14.877  2037  2097 I Perf    : fps=3.1 worst=3 drawn=14/37 queue=0 build=50ms verts=34k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:58:17.010  2037  2097 I Perf    : fps=3.3 worst=3 drawn=14/37 queue=0 build=50ms verts=34k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:58:19.199  2037  2097 I Perf    : fps=3.2 worst=3 drawn=14/37 queue=0 build=50ms verts=34k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:58:21.410  2037  2097 I Perf    : fps=3.2 worst=3 drawn=14/37 queue=0 build=50ms verts=34k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:58:23.585  2037  2097 I Perf    : fps=3.2 worst=3 drawn=14/37 queue=0 build=50ms verts=34k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:58:25.823  2037  2097 I Perf    : fps=3.1 worst=3 drawn=14/37 queue=0 build=50ms verts=34k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:58:27.946  2037  2097 I Perf    : fps=3.3 worst=3 drawn=14/37 queue=0 build=50ms verts=34k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:58:29.227  2474  2474 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 21:58:29.963  2037  2097 I Perf    : fps=3.0 worst=3 drawn=14/37 queue=0 build=50ms verts=34k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:58:32.239  2037  2097 I Perf    : fps=3.1 worst=3 drawn=14/37 queue=0 build=50ms verts=34k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:58:34.530  2037  2097 I Perf    : fps=3.1 worst=3 drawn=14/37 queue=0 build=50ms verts=34k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:58:35.896  2499  2499 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 21:58:35.897  2499  2499 I AndroidRuntime: Using default boot image
10-28 21:58:35.898  2499  2499 I AndroidRuntime: Leaving lock profiling enabled
10-28 21:58:35.992  2499  2499 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 21:58:36.569  2037  2097 I Perf    : fps=2.9 worst=3 drawn=13/37 queue=0 build=50ms verts=33k cam=1.5,1.6,1.5 yaw=1.37 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 21:58:36.593  2499  2499 D AndroidRuntime: Shutting down VM
10-28 21:58:38.737  2037  2097 I Perf    : fps=4.6 worst=5 drawn=16/37 queue=0 build=50ms verts=41k cam=1.5,1.6,1.5 yaw=2.75 pitch=0.00 torch=1 ready=true drawn=16 noMesh=0 culled=21
10-28 21:58:40.847  2037  2097 I Perf    : fps=4.7 worst=5 drawn=16/37 queue=0 build=50ms verts=41k cam=1.5,1.6,1.5 yaw=2.75 pitch=0.00 torch=1 ready=true drawn=16 noMesh=0 culled=21
10-28 21:58:41.548  2515  2515 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 21:58:41.567  2515  2515 I AndroidRuntime: Using default boot image
10-28 21:58:41.567  2515  2515 I AndroidRuntime: Leaving lock profiling enabled
10-28 21:58:41.704  2515  2515 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 21:58:41.711  2515  2515 D AndroidRuntime: Shutting down VM
10-28 21:58:41.719  2515  2526 I AndroidRuntime: NOTE: attach of thread 'Binder:2515_1' failed
10-28 21:58:42.877  2037  2097 I Perf    : fps=4.4 worst=4 drawn=16/37 queue=0 build=50ms verts=41k cam=1.5,1.6,1.5 yaw=2.75 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 21:58:44.986  2037  2097 I Perf    : fps=4.7 worst=5 drawn=16/37 queue=0 build=50ms verts=41k cam=1.5,1.6,1.5 yaw=2.75 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 21:58:46.602  2531  2531 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 21:58:46.617  2531  2531 I AndroidRuntime: Using default boot image
10-28 21:58:46.617  2531  2531 I AndroidRuntime: Leaving lock profiling enabled
10-28 21:58:46.773  2531  2531 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 21:58:46.988  2037  2097 I Perf    : fps=4.5 worst=4 drawn=16/37 queue=0 build=50ms verts=41k cam=1.5,1.6,1.5 yaw=2.75 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 21:58:48.365  2531  2531 D AndroidRuntime: Shutting down VM
10-28 21:58:49.167  2037  2097 I Perf    : fps=4.6 worst=5 drawn=16/37 queue=0 build=50ms verts=41k cam=0.9,1.6,2.9 yaw=2.75 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 21:58:51.232  2037  2097 I Perf    : fps=4.4 worst=4 drawn=16/37 queue=0 build=50ms verts=41k cam=0.9,1.6,2.9 yaw=2.75 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 21:58:53.476  2037  2097 I Perf    : fps=4.5 worst=5 drawn=16/37 queue=0 build=50ms verts=41k cam=0.9,1.6,2.9 yaw=2.75 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 21:58:53.705  2549  2549 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 21:58:55.515  2037  2097 I Perf    : fps=4.4 worst=4 drawn=16/37 queue=0 build=50ms verts=41k cam=0.9,1.6,2.9 yaw=2.75 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 21:58:57.581  2037  2097 I Perf    : fps=4.4 worst=4 drawn=16/37 queue=0 build=50ms verts=41k cam=0.9,1.6,2.9 yaw=2.75 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 21:58:59.590  2037  2097 I Perf    : fps=4.5 worst=4 drawn=16/37 queue=0 build=50ms verts=41k cam=0.9,1.6,2.9 yaw=2.75 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
+ '[' -z 2037 ']'
+ grep -qa 'FATAL EXCEPTION' emulator-shots/logcat.txt
+ '[' 0 '!=' 0 ']'
+ echo 'OK: the app is alive, rendering, and the touch controls work'
OK: the app is alive, rendering, and the touch controls work
```
