# Emulator smoke test diagnostics

Commit: 1c6b53ad5ae662a7c9bdc535d2f56910de79b1bd
Outcome: success

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 28 20:22 /dev/kvm
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
INFO         | The active JSON Web Key Sets can be found here: /run/user/1001/avd/running/2740/jwks/1b346a4c-5f11-4e55-b156-28baf865479c/active.jwk
INFO         | Scanning /run/user/1001/avd/running/2740/jwks/1b346a4c-5f11-4e55-b156-28baf865479c for jwk keys.
INFO         | Started GRPC server at 127.0.0.1:8554, security: Local, auth: +token
INFO         | Advertising in: /run/user/1001/avd/running/pid_2740.ini
INFO         | Setting display: 0 configuration to: 1080x1920, dpi: 420x420 
INFO         | setDisplayActiveConfig: id:0, 1080x1920
INFO         | emulatorSetupEnvironment: Environment scene is not required
USER_INFO    | Emulator is performing a full startup. This may take upto two minutes, or more.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
INFO         | Boot completed in 26205 ms
INFO         | Increasing screen off timeout, logcat buffer size to 2M.
```
## Logcat (filtered)
```
10-28 20:25:27.981   514   555 W ActivityManager: Slow operation: 74ms so far, now at startProcess: starting to update pids map
10-28 20:25:27.987   514   555 W ActivityManager: Slow operation: 81ms so far, now at startProcess: done updating pids map
10-28 20:25:28.118   514   581 I ActivityManager: Killing 1692:com.android.externalstorage/u0a67 (adj 985): empty #17
10-28 20:25:28.193   514   555 I ActivityManager: Start proc 2320:com.android.traceur/u0a94 for broadcast {com.android.traceur/com.android.traceur.Receiver}
10-28 20:25:28.375   514   581 I ActivityManager: Finished processing BOOT_COMPLETED for u0
10-28 20:25:28.377   514   581 I ActivityManager: Killing 1814:com.android.provision/u0a116 (adj 985): empty #17
10-28 20:25:28.624   514  1723 I ActivityManager: Killing 1938:com.android.quicksearchbox/u0a103 (adj 975): empty #17
10-28 20:25:42.613   514   553 I ActivityManager: Killing 1297:com.android.cellbroadcastreceiver/u0a63 (adj 985): empty #17
10-28 20:25:48.452   514   545 I ActivityManager: Killing 1993:com.android.calendar/u0a109 (adj 985): empty #17
10-28 20:25:48.452   514   545 I ActivityManager: Killing 1970:com.android.localtransport/1000 (adj 985): empty #18
10-28 20:25:48.466   514  1723 I ActivityManager: Killing 2030:com.android.camera2/u0a112 (adj 975): empty #17
10-28 20:25:48.616  2396  2396 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 20:25:48.625  2396  2396 I AndroidRuntime: Using default boot image
10-28 20:25:48.626  2396  2396 I AndroidRuntime: Leaving lock profiling enabled
10-28 20:25:48.847  2396  2396 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 20:25:48.851  2396  2396 D AndroidRuntime: Shutting down VM
10-28 20:25:48.853  2396  2408 I AndroidRuntime: NOTE: attach of thread 'Binder:2396_2' failed
10-28 20:25:49.390  2117  2183 I BackroomsGL: world shader compiled
10-28 20:25:49.853  2117  2183 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 20:25:49.854  2117  2183 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 20:25:51.338  2117  2183 I Perf    : fps=2.5 worst=5 drawn=5/8 queue=2 build=18ms verts=13k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=5 noMesh=0 culled=1
10-28 20:25:53.462  2117  2183 I Perf    : fps=5.2 worst=5 drawn=11/27 queue=2 build=24ms verts=27k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=11 noMesh=0 culled=14
10-28 20:25:55.570  2117  2183 I Perf    : fps=5.2 worst=5 drawn=14/37 queue=0 build=31ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:25:57.700  2117  2183 I Perf    : fps=5.2 worst=5 drawn=14/37 queue=0 build=31ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:25:59.882  2117  2183 I Perf    : fps=5.0 worst=5 drawn=14/37 queue=0 build=31ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:26:02.010  2117  2183 I Perf    : fps=5.2 worst=5 drawn=14/37 queue=0 build=31ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:26:04.151  2117  2183 I Perf    : fps=5.1 worst=5 drawn=14/37 queue=0 build=31ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:26:06.270  2117  2183 I Perf    : fps=5.2 worst=5 drawn=14/37 queue=0 build=31ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:26:08.446  2117  2183 I Perf    : fps=5.1 worst=5 drawn=14/37 queue=0 build=31ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:26:10.552  2117  2183 I Perf    : fps=5.2 worst=5 drawn=14/37 queue=0 build=31ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:26:12.647  2117  2183 I Perf    : fps=5.3 worst=5 drawn=14/37 queue=0 build=31ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:26:14.796  2117  2183 I Perf    : fps=5.1 worst=5 drawn=14/37 queue=0 build=31ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:26:16.948  2117  2183 I Perf    : fps=5.1 worst=5 drawn=14/37 queue=0 build=31ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:26:19.037  2117  2183 I Perf    : fps=5.3 worst=5 drawn=14/37 queue=0 build=31ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:26:20.613  2434  2434 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 20:26:20.618  2434  2434 I AndroidRuntime: Using default boot image
10-28 20:26:20.618  2434  2434 I AndroidRuntime: Leaving lock profiling enabled
10-28 20:26:20.721  2434  2434 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 20:26:21.038  2117  2183 I Perf    : fps=5.0 worst=5 drawn=12/37 queue=0 build=31ms verts=30k cam=0.5,1.6,0.5 yaw=1.39 pitch=0.00 torch=1 ready=true drawn=12 noMesh=0 culled=25
10-28 20:26:21.329  2434  2434 D AndroidRuntime: Shutting down VM
10-28 20:26:23.092  2117  2183 I Perf    : fps=4.9 worst=5 drawn=17/37 queue=0 build=31ms verts=43k cam=0.5,1.6,0.5 yaw=2.80 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-28 20:26:25.254  2117  2183 I Perf    : fps=5.1 worst=6 drawn=17/37 queue=0 build=31ms verts=43k cam=0.5,1.6,0.5 yaw=2.80 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-28 20:26:26.225  2461  2461 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 20:26:26.230  2461  2461 I AndroidRuntime: Using default boot image
10-28 20:26:26.230  2461  2461 I AndroidRuntime: Leaving lock profiling enabled
10-28 20:26:26.377  2461  2461 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 20:26:26.381  2461  2461 D AndroidRuntime: Shutting down VM
10-28 20:26:27.352  2117  2183 I Perf    : fps=4.8 worst=5 drawn=17/37 queue=0 build=31ms verts=43k cam=0.5,1.6,0.5 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 20:26:29.490  2117  2183 I Perf    : fps=4.7 worst=5 drawn=17/37 queue=0 build=31ms verts=43k cam=0.5,1.6,0.5 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 20:26:31.212  2478  2478 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 20:26:31.213  2478  2478 I AndroidRuntime: Using default boot image
10-28 20:26:31.213  2478  2478 I AndroidRuntime: Leaving lock profiling enabled
10-28 20:26:31.282  2478  2478 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 20:26:31.543  2117  2183 I Perf    : fps=4.9 worst=5 drawn=17/37 queue=0 build=31ms verts=43k cam=0.5,1.6,0.5 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 20:26:32.826  2478  2478 D AndroidRuntime: Shutting down VM
10-28 20:26:33.554  2117  2183 I Perf    : fps=5.0 worst=5 drawn=17/41 queue=2 build=25ms verts=43k cam=-0.0,1.6,1.6 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=22
10-28 20:26:35.673  2117  2183 I Perf    : fps=4.7 worst=4 drawn=19/44 queue=0 build=99ms verts=48k cam=-0.0,1.6,1.6 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=19 noMesh=0 culled=25
10-28 20:26:37.745  2117  2183 I Perf    : fps=4.8 worst=5 drawn=19/44 queue=0 build=99ms verts=48k cam=-0.0,1.6,1.6 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=19 noMesh=0 culled=25
10-28 20:26:39.882  2117  2183 I Perf    : fps=4.7 worst=5 drawn=19/44 queue=0 build=99ms verts=48k cam=-0.0,1.6,1.6 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=19 noMesh=0 culled=25
10-28 20:26:41.966  2117  2183 I Perf    : fps=4.8 worst=5 drawn=19/44 queue=0 build=99ms verts=48k cam=-0.0,1.6,1.6 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=19 noMesh=0 culled=25
```
## Step log
```
17  2183 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 20:25:51.338  2117  2183 I Perf    : fps=2.5 worst=5 drawn=5/8 queue=2 build=18ms verts=13k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=5 noMesh=0 culled=1
10-28 20:25:53.462  2117  2183 I Perf    : fps=5.2 worst=5 drawn=11/27 queue=2 build=24ms verts=27k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=11 noMesh=0 culled=14
10-28 20:25:55.570  2117  2183 I Perf    : fps=5.2 worst=5 drawn=14/37 queue=0 build=31ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:25:57.700  2117  2183 I Perf    : fps=5.2 worst=5 drawn=14/37 queue=0 build=31ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:25:59.882  2117  2183 I Perf    : fps=5.0 worst=5 drawn=14/37 queue=0 build=31ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:26:02.010  2117  2183 I Perf    : fps=5.2 worst=5 drawn=14/37 queue=0 build=31ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:26:04.151  2117  2183 I Perf    : fps=5.1 worst=5 drawn=14/37 queue=0 build=31ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:26:06.270  2117  2183 I Perf    : fps=5.2 worst=5 drawn=14/37 queue=0 build=31ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:26:08.446  2117  2183 I Perf    : fps=5.1 worst=5 drawn=14/37 queue=0 build=31ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:26:10.552  2117  2183 I Perf    : fps=5.2 worst=5 drawn=14/37 queue=0 build=31ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:26:12.647  2117  2183 I Perf    : fps=5.3 worst=5 drawn=14/37 queue=0 build=31ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:26:14.779  2421  2421 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 20:26:14.796  2117  2183 I Perf    : fps=5.1 worst=5 drawn=14/37 queue=0 build=31ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:26:16.948  2117  2183 I Perf    : fps=5.1 worst=5 drawn=14/37 queue=0 build=31ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:26:19.037  2117  2183 I Perf    : fps=5.3 worst=5 drawn=14/37 queue=0 build=31ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:26:20.613  2434  2434 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 20:26:20.618  2434  2434 I AndroidRuntime: Using default boot image
10-28 20:26:20.618  2434  2434 I AndroidRuntime: Leaving lock profiling enabled
10-28 20:26:20.721  2434  2434 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 20:26:21.038  2117  2183 I Perf    : fps=5.0 worst=5 drawn=12/37 queue=0 build=31ms verts=30k cam=0.5,1.6,0.5 yaw=1.39 pitch=0.00 torch=1 ready=true drawn=12 noMesh=0 culled=25
10-28 20:26:21.329  2434  2434 D AndroidRuntime: Shutting down VM
10-28 20:26:23.092  2117  2183 I Perf    : fps=4.9 worst=5 drawn=17/37 queue=0 build=31ms verts=43k cam=0.5,1.6,0.5 yaw=2.80 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-28 20:26:25.254  2117  2183 I Perf    : fps=5.1 worst=6 drawn=17/37 queue=0 build=31ms verts=43k cam=0.5,1.6,0.5 yaw=2.80 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-28 20:26:26.225  2461  2461 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 20:26:26.230  2461  2461 I AndroidRuntime: Using default boot image
10-28 20:26:26.230  2461  2461 I AndroidRuntime: Leaving lock profiling enabled
10-28 20:26:26.377  2461  2461 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 20:26:26.381  2461  2461 D AndroidRuntime: Shutting down VM
10-28 20:26:27.352  2117  2183 I Perf    : fps=4.8 worst=5 drawn=17/37 queue=0 build=31ms verts=43k cam=0.5,1.6,0.5 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 20:26:29.490  2117  2183 I Perf    : fps=4.7 worst=5 drawn=17/37 queue=0 build=31ms verts=43k cam=0.5,1.6,0.5 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 20:26:31.212  2478  2478 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 20:26:31.213  2478  2478 I AndroidRuntime: Using default boot image
10-28 20:26:31.213  2478  2478 I AndroidRuntime: Leaving lock profiling enabled
10-28 20:26:31.282  2478  2478 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 20:26:31.543  2117  2183 I Perf    : fps=4.9 worst=5 drawn=17/37 queue=0 build=31ms verts=43k cam=0.5,1.6,0.5 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 20:26:32.826  2478  2478 D AndroidRuntime: Shutting down VM
10-28 20:26:33.554  2117  2183 I Perf    : fps=5.0 worst=5 drawn=17/41 queue=2 build=25ms verts=43k cam=-0.0,1.6,1.6 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=22
10-28 20:26:35.673  2117  2183 I Perf    : fps=4.7 worst=4 drawn=19/44 queue=0 build=99ms verts=48k cam=-0.0,1.6,1.6 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=19 noMesh=0 culled=25
10-28 20:26:37.745  2117  2183 I Perf    : fps=4.8 worst=5 drawn=19/44 queue=0 build=99ms verts=48k cam=-0.0,1.6,1.6 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=19 noMesh=0 culled=25
10-28 20:26:38.188  2496  2496 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 20:26:39.882  2117  2183 I Perf    : fps=4.7 worst=5 drawn=19/44 queue=0 build=99ms verts=48k cam=-0.0,1.6,1.6 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=19 noMesh=0 culled=25
10-28 20:26:41.966  2117  2183 I Perf    : fps=4.8 worst=5 drawn=19/44 queue=0 build=99ms verts=48k cam=-0.0,1.6,1.6 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=19 noMesh=0 culled=25
+ '[' -z 2117 ']'
+ grep -qa 'FATAL EXCEPTION' emulator-shots/logcat.txt
+ '[' 0 '!=' 0 ']'
+ echo 'OK: the app is alive, rendering, and the touch controls work'
OK: the app is alive, rendering, and the touch controls work
```
