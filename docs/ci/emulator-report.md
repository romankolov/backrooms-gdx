# Emulator smoke test diagnostics

Commit: a4c076e3c6c3be2aa448d7992069cfe1eebaa8ae
Outcome: success

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 28 07:55 /dev/kvm
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
INFO         | The active JSON Web Key Sets can be found here: /run/user/1001/avd/running/2976/jwks/69325443-006f-4682-b428-8126d5ebe624/active.jwk
INFO         | Scanning /run/user/1001/avd/running/2976/jwks/69325443-006f-4682-b428-8126d5ebe624 for jwk keys.
INFO         | Started GRPC server at 127.0.0.1:8554, security: Local, auth: +token
INFO         | Advertising in: /run/user/1001/avd/running/pid_2976.ini
INFO         | Setting display: 0 configuration to: 1080x1920, dpi: 420x420 
INFO         | setDisplayActiveConfig: id:0, 1080x1920
INFO         | emulatorSetupEnvironment: Environment scene is not required
USER_INFO    | Emulator is performing a full startup. This may take upto two minutes, or more.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
INFO         | Boot completed in 32604 ms
INFO         | Increasing screen off timeout, logcat buffer size to 2M.
```
## Logcat (filtered)
```
10-28 07:57:49.294   514   555 I ActivityManager: Start proc 2254:com.android.shell/2000 for broadcast {com.android.shell/com.android.shell.HeapDumpReceiver}
10-28 07:57:49.294   514   555 W ActivityManager: Slow operation: 161ms so far, now at startProcess: starting to update pids map
10-28 07:57:49.294   514   555 W ActivityManager: Slow operation: 161ms so far, now at startProcess: done updating pids map
10-28 07:57:49.608   514  2088 I ActivityManager: Killing 1872:com.android.localtransport/1000 (adj 985): empty #17
10-28 07:57:49.743   514   555 I ActivityManager: Start proc 2289:com.android.traceur/u0a95 for broadcast {com.android.traceur/com.android.traceur.Receiver}
10-28 07:57:49.835   514   636 I ActivityManager: Finished processing BOOT_COMPLETED for u0
10-28 07:57:49.836   514   636 I ActivityManager: Killing 1764:com.android.provision/u0a116 (adj 985): empty #17
10-28 07:58:04.165   514   553 I ActivityManager: Killing 1298:com.android.cellbroadcastreceiver/u0a65 (adj 985): empty #17
10-28 07:58:08.474   514   545 I ActivityManager: Killing 1944:com.android.calendar/u0a108 (adj 985): empty #17
10-28 07:58:08.476   514   545 I ActivityManager: Killing 1897:com.android.quicksearchbox/u0a102 (adj 985): empty #18
10-28 07:58:12.348  2365  2365 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 07:58:12.375  2365  2365 I AndroidRuntime: Using default boot image
10-28 07:58:12.375  2365  2365 I AndroidRuntime: Leaving lock profiling enabled
10-28 07:58:12.486  2365  2365 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 07:58:12.541  2365  2365 D AndroidRuntime: Shutting down VM
10-28 07:58:13.073  2248  2317 I BackroomsGL: world shader compiled
10-28 07:58:13.126  2248  2317 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 07:58:13.126  2248  2317 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 07:58:13.421   514  2088 I ActivityManager: Killing 1980:com.android.camera2/u0a111 (adj 975): empty #17
10-28 07:58:15.101  2248  2317 I Perf    : fps=4.7 worst=68 drawn=4/6 queue=2 build=25ms verts=10k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=4 noMesh=0 culled=0
10-28 07:58:17.231  2248  2317 I Perf    : fps=2.8 worst=3 drawn=11/16 queue=2 build=416ms verts=28k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=11 noMesh=0 culled=4
10-28 07:58:19.332  2248  2317 I Perf    : fps=2.9 worst=3 drawn=11/26 queue=2 build=240ms verts=28k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=11 noMesh=0 culled=13
10-28 07:58:21.409  2248  2317 I Perf    : fps=2.9 worst=3 drawn=14/36 queue=1 build=218ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=21
10-28 07:58:23.535  2248  2317 I Perf    : fps=2.8 worst=3 drawn=14/37 queue=0 build=228ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:58:25.631  2248  2317 I Perf    : fps=2.9 worst=2 drawn=14/37 queue=0 build=228ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:58:27.752  2248  2317 I Perf    : fps=2.8 worst=3 drawn=14/37 queue=0 build=228ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:58:29.974  2248  2317 I Perf    : fps=2.7 worst=2 drawn=14/37 queue=0 build=228ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:58:32.092  2248  2317 I Perf    : fps=2.8 worst=3 drawn=14/37 queue=0 build=228ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:58:34.181  2248  2317 I Perf    : fps=2.9 worst=3 drawn=14/37 queue=0 build=228ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:58:36.253  2248  2317 I Perf    : fps=2.9 worst=3 drawn=14/37 queue=0 build=228ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:58:38.389  2248  2317 I Perf    : fps=2.8 worst=3 drawn=14/37 queue=0 build=228ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:58:40.608  2248  2317 I Perf    : fps=2.7 worst=2 drawn=14/37 queue=0 build=228ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:58:42.816  2248  2317 I Perf    : fps=2.7 worst=3 drawn=14/37 queue=0 build=228ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:58:44.516  2416  2416 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 07:58:44.530  2416  2416 I AndroidRuntime: Using default boot image
10-28 07:58:44.530  2416  2416 I AndroidRuntime: Leaving lock profiling enabled
10-28 07:58:44.729  2416  2416 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 07:58:44.949  2248  2317 I Perf    : fps=2.8 worst=3 drawn=14/37 queue=0 build=228ms verts=36k cam=1.5,1.6,1.5 yaw=0.49 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:58:45.465  2416  2416 D AndroidRuntime: Shutting down VM
10-28 07:58:47.117  2248  2317 I Perf    : fps=3.7 worst=4 drawn=16/37 queue=0 build=228ms verts=40k cam=1.5,1.6,1.5 yaw=2.76 pitch=0.00 torch=1 ready=true drawn=16 noMesh=0 culled=21
10-28 07:58:49.257  2248  2317 I Perf    : fps=4.2 worst=4 drawn=16/37 queue=0 build=228ms verts=40k cam=1.5,1.6,1.5 yaw=2.76 pitch=0.00 torch=1 ready=true drawn=16 noMesh=0 culled=21
10-28 07:58:50.591  2433  2433 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 07:58:50.605  2433  2433 I AndroidRuntime: Using default boot image
10-28 07:58:50.606  2433  2433 I AndroidRuntime: Leaving lock profiling enabled
10-28 07:58:50.803  2433  2433 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 07:58:51.005  2433  2433 D AndroidRuntime: Shutting down VM
10-28 07:58:51.275  2248  2317 I Perf    : fps=4.0 worst=4 drawn=16/37 queue=0 build=228ms verts=40k cam=1.5,1.6,1.5 yaw=2.76 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 07:58:53.381  2248  2317 I Perf    : fps=4.3 worst=4 drawn=16/37 queue=0 build=228ms verts=40k cam=1.5,1.6,1.5 yaw=2.76 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 07:58:55.551  2248  2317 I Perf    : fps=4.2 worst=4 drawn=16/37 queue=0 build=228ms verts=40k cam=1.5,1.6,1.5 yaw=2.76 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 07:58:55.679  2450  2450 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 07:58:55.681  2450  2450 I AndroidRuntime: Using default boot image
10-28 07:58:55.681  2450  2450 I AndroidRuntime: Leaving lock profiling enabled
10-28 07:58:55.776  2450  2450 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 07:58:57.509  2450  2450 D AndroidRuntime: Shutting down VM
10-28 07:58:57.670  2248  2317 I Perf    : fps=4.2 worst=5 drawn=16/37 queue=0 build=228ms verts=40k cam=1.0,1.6,2.8 yaw=2.76 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 07:58:59.686  2248  2317 I Perf    : fps=4.0 worst=4 drawn=16/37 queue=0 build=228ms verts=40k cam=0.9,1.6,3.0 yaw=2.76 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 07:59:01.726  2248  2317 I Perf    : fps=3.9 worst=4 drawn=16/37 queue=0 build=228ms verts=40k cam=0.9,1.6,3.0 yaw=2.76 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 07:59:03.747  2248  2317 I Perf    : fps=4.0 worst=4 drawn=16/37 queue=0 build=228ms verts=40k cam=0.9,1.6,3.0 yaw=2.76 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 07:59:05.749  2248  2317 I Perf    : fps=4.0 worst=4 drawn=16/37 queue=0 build=228ms verts=40k cam=0.9,1.6,3.0 yaw=2.76 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 07:59:07.985  2248  2317 I Perf    : fps=4.0 worst=4 drawn=16/37 queue=0 build=228ms verts=40k cam=0.9,1.6,3.0 yaw=2.76 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
```
## Step log
```
5ms verts=10k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=4 noMesh=0 culled=0
10-28 07:58:17.231  2248  2317 I Perf    : fps=2.8 worst=3 drawn=11/16 queue=2 build=416ms verts=28k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=11 noMesh=0 culled=4
10-28 07:58:19.332  2248  2317 I Perf    : fps=2.9 worst=3 drawn=11/26 queue=2 build=240ms verts=28k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=11 noMesh=0 culled=13
10-28 07:58:21.409  2248  2317 I Perf    : fps=2.9 worst=3 drawn=14/36 queue=1 build=218ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=21
10-28 07:58:23.535  2248  2317 I Perf    : fps=2.8 worst=3 drawn=14/37 queue=0 build=228ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:58:25.631  2248  2317 I Perf    : fps=2.9 worst=2 drawn=14/37 queue=0 build=228ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:58:27.752  2248  2317 I Perf    : fps=2.8 worst=3 drawn=14/37 queue=0 build=228ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:58:29.974  2248  2317 I Perf    : fps=2.7 worst=2 drawn=14/37 queue=0 build=228ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:58:32.092  2248  2317 I Perf    : fps=2.8 worst=3 drawn=14/37 queue=0 build=228ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:58:34.181  2248  2317 I Perf    : fps=2.9 worst=3 drawn=14/37 queue=0 build=228ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:58:36.253  2248  2317 I Perf    : fps=2.9 worst=3 drawn=14/37 queue=0 build=228ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:58:38.389  2248  2317 I Perf    : fps=2.8 worst=3 drawn=14/37 queue=0 build=228ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:58:39.176  2397  2397 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 07:58:40.608  2248  2317 I Perf    : fps=2.7 worst=2 drawn=14/37 queue=0 build=228ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:58:42.816  2248  2317 I Perf    : fps=2.7 worst=3 drawn=14/37 queue=0 build=228ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:58:44.516  2416  2416 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 07:58:44.530  2416  2416 I AndroidRuntime: Using default boot image
10-28 07:58:44.530  2416  2416 I AndroidRuntime: Leaving lock profiling enabled
10-28 07:58:44.729  2416  2416 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 07:58:44.949  2248  2317 I Perf    : fps=2.8 worst=3 drawn=14/37 queue=0 build=228ms verts=36k cam=1.5,1.6,1.5 yaw=0.49 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:58:45.465  2416  2416 D AndroidRuntime: Shutting down VM
10-28 07:58:47.117  2248  2317 I Perf    : fps=3.7 worst=4 drawn=16/37 queue=0 build=228ms verts=40k cam=1.5,1.6,1.5 yaw=2.76 pitch=0.00 torch=1 ready=true drawn=16 noMesh=0 culled=21
10-28 07:58:49.257  2248  2317 I Perf    : fps=4.2 worst=4 drawn=16/37 queue=0 build=228ms verts=40k cam=1.5,1.6,1.5 yaw=2.76 pitch=0.00 torch=1 ready=true drawn=16 noMesh=0 culled=21
10-28 07:58:50.591  2433  2433 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 07:58:50.605  2433  2433 I AndroidRuntime: Using default boot image
10-28 07:58:50.606  2433  2433 I AndroidRuntime: Leaving lock profiling enabled
10-28 07:58:50.803  2433  2433 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 07:58:51.005  2433  2433 D AndroidRuntime: Shutting down VM
10-28 07:58:51.275  2248  2317 I Perf    : fps=4.0 worst=4 drawn=16/37 queue=0 build=228ms verts=40k cam=1.5,1.6,1.5 yaw=2.76 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 07:58:53.381  2248  2317 I Perf    : fps=4.3 worst=4 drawn=16/37 queue=0 build=228ms verts=40k cam=1.5,1.6,1.5 yaw=2.76 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 07:58:55.551  2248  2317 I Perf    : fps=4.2 worst=4 drawn=16/37 queue=0 build=228ms verts=40k cam=1.5,1.6,1.5 yaw=2.76 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 07:58:55.679  2450  2450 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 07:58:55.681  2450  2450 I AndroidRuntime: Using default boot image
10-28 07:58:55.681  2450  2450 I AndroidRuntime: Leaving lock profiling enabled
10-28 07:58:55.776  2450  2450 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 07:58:57.509  2450  2450 D AndroidRuntime: Shutting down VM
10-28 07:58:57.670  2248  2317 I Perf    : fps=4.2 worst=5 drawn=16/37 queue=0 build=228ms verts=40k cam=1.0,1.6,2.8 yaw=2.76 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 07:58:59.686  2248  2317 I Perf    : fps=4.0 worst=4 drawn=16/37 queue=0 build=228ms verts=40k cam=0.9,1.6,3.0 yaw=2.76 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 07:59:01.726  2248  2317 I Perf    : fps=3.9 worst=4 drawn=16/37 queue=0 build=228ms verts=40k cam=0.9,1.6,3.0 yaw=2.76 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 07:59:02.740  2470  2470 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 07:59:03.747  2248  2317 I Perf    : fps=4.0 worst=4 drawn=16/37 queue=0 build=228ms verts=40k cam=0.9,1.6,3.0 yaw=2.76 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 07:59:05.749  2248  2317 I Perf    : fps=4.0 worst=4 drawn=16/37 queue=0 build=228ms verts=40k cam=0.9,1.6,3.0 yaw=2.76 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 07:59:07.985  2248  2317 I Perf    : fps=4.0 worst=4 drawn=16/37 queue=0 build=228ms verts=40k cam=0.9,1.6,3.0 yaw=2.76 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
+ '[' -z 2248 ']'
+ grep -qa 'FATAL EXCEPTION' emulator-shots/logcat.txt
+ '[' 0 '!=' 0 ']'
+ echo 'OK: the app is alive, rendering, and the touch controls work'
OK: the app is alive, rendering, and the touch controls work
```
