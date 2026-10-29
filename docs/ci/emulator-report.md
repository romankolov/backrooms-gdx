# Emulator smoke test diagnostics

Commit: 74b8bf883cdabfb335f06601babea01afad8fac8
Outcome: success

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 29 04:56 /dev/kvm
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
INFO         | The active JSON Web Key Sets can be found here: /run/user/1001/avd/running/3023/jwks/8ee93270-001b-4137-a907-b83028eec67e/active.jwk
INFO         | Scanning /run/user/1001/avd/running/3023/jwks/8ee93270-001b-4137-a907-b83028eec67e for jwk keys.
INFO         | Started GRPC server at 127.0.0.1:8554, security: Local, auth: +token
INFO         | Advertising in: /run/user/1001/avd/running/pid_3023.ini
INFO         | Setting display: 0 configuration to: 1080x1920, dpi: 420x420 
INFO         | setDisplayActiveConfig: id:0, 1080x1920
INFO         | emulatorSetupEnvironment: Environment scene is not required
USER_INFO    | Emulator is performing a full startup. This may take upto two minutes, or more.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
INFO         | Boot completed in 26655 ms
INFO         | Increasing screen off timeout, logcat buffer size to 2M.
```
## Logcat (filtered)
```
10-29 05:00:46.312   508   548 W ActivityManager: Slow operation: 120ms so far, now at startProcess: done updating battery stats
10-29 05:00:46.312   508   548 W ActivityManager: Slow operation: 120ms so far, now at startProcess: building log message
10-29 05:00:46.312   508   548 I ActivityManager: Start proc 2400:com.android.cellbroadcastreceiver.module/u0a128 for broadcast {com.android.cellbroadcastreceiver.module/com.android.cellbroadcastreceiver.CellBroadcastReceiver}
10-29 05:00:46.312   508   548 W ActivityManager: Slow operation: 120ms so far, now at startProcess: starting to update pids map
10-29 05:00:46.312   508   548 W ActivityManager: Slow operation: 121ms so far, now at startProcess: done updating pids map
10-29 05:00:46.452   508   535 I ActivityManager: Killing 1992:com.android.camera2/u0a110 (adj 985): empty #17
10-29 05:00:51.031   508   546 I ActivityManager: Killing 1285:com.android.cellbroadcastreceiver/u0a65 (adj 985): empty #17
10-29 05:00:53.052   508  1012 I ActivityManager: Killing 1972:com.android.localtransport/1000 (adj 985): empty #17
10-29 05:00:56.840  2428  2428 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 05:00:56.852  2428  2428 I AndroidRuntime: Using default boot image
10-29 05:00:56.852  2428  2428 I AndroidRuntime: Leaving lock profiling enabled
10-29 05:00:56.989  2428  2428 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 05:00:57.152  2428  2428 D AndroidRuntime: Shutting down VM
10-29 05:00:57.684  2097  2163 I BackroomsGL: world shader compiled
10-29 05:00:57.748  2097  2163 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-29 05:00:57.748  2097  2163 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-29 05:00:59.581  2097  2163 I Perf    : fps=2.0 worst=4 drawn=4/6 queue=2 build=130ms verts=11k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=4 noMesh=0 culled=0
10-29 05:00:59.643   508   538 I ActivityManager: Killing 2028:com.android.contacts/u0a98 (adj 985): empty #17
10-29 05:00:59.644   508   991 I ActivityManager: Killing 1784:com.android.provision/u0a115 (adj 975): empty #17
10-29 05:01:01.818  2097  2163 I Perf    : fps=4.0 worst=4 drawn=14/23 queue=2 build=20ms verts=41k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=7
10-29 05:01:04.025  2097  2163 I Perf    : fps=4.1 worst=4 drawn=18/37 queue=0 build=22ms verts=53k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 05:01:06.311  2097  2163 I Perf    : fps=3.9 worst=4 drawn=18/37 queue=0 build=22ms verts=53k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 05:01:08.358  2097  2163 I Perf    : fps=3.9 worst=4 drawn=18/37 queue=0 build=22ms verts=53k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 05:01:10.387  2097  2163 I Perf    : fps=3.9 worst=4 drawn=18/37 queue=0 build=22ms verts=53k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 05:01:12.398  2097  2163 I Perf    : fps=4.0 worst=4 drawn=18/37 queue=0 build=22ms verts=53k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 05:01:14.649  2097  2163 I Perf    : fps=4.0 worst=4 drawn=18/37 queue=0 build=22ms verts=53k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 05:01:16.896  2097  2163 I Perf    : fps=4.0 worst=4 drawn=18/37 queue=0 build=22ms verts=53k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 05:01:18.985  2097  2163 I Perf    : fps=3.8 worst=4 drawn=18/37 queue=0 build=22ms verts=53k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 05:01:21.209  2097  2163 I Perf    : fps=4.0 worst=4 drawn=18/37 queue=0 build=22ms verts=53k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 05:01:23.359  2097  2163 I Perf    : fps=3.7 worst=4 drawn=18/37 queue=0 build=22ms verts=53k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 05:01:25.446  2097  2163 I Perf    : fps=3.8 worst=4 drawn=18/37 queue=0 build=22ms verts=53k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 05:01:27.532  2097  2163 I Perf    : fps=3.8 worst=4 drawn=18/37 queue=0 build=22ms verts=53k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 05:01:29.609  2097  2163 I Perf    : fps=3.9 worst=4 drawn=18/37 queue=0 build=22ms verts=53k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 05:01:30.649  2475  2475 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 05:01:30.661  2475  2475 I AndroidRuntime: Using default boot image
10-29 05:01:30.661  2475  2475 I AndroidRuntime: Leaving lock profiling enabled
10-29 05:01:30.741  2475  2475 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 05:01:31.354  2475  2475 D AndroidRuntime: Shutting down VM
10-29 05:01:31.642  2097  2163 I Perf    : fps=3.9 worst=4 drawn=14/37 queue=0 build=22ms verts=39k cam=-0.5,1.6,-0.5 yaw=2.84 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 05:01:33.824  2097  2163 I Perf    : fps=4.6 worst=5 drawn=14/37 queue=0 build=22ms verts=39k cam=-0.5,1.6,-0.5 yaw=2.84 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 05:01:36.022  2097  2163 I Perf    : fps=4.5 worst=4 drawn=14/37 queue=0 build=22ms verts=39k cam=-0.5,1.6,-0.5 yaw=2.84 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 05:01:36.457  2491  2491 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 05:01:36.458  2491  2491 I AndroidRuntime: Using default boot image
10-29 05:01:36.459  2491  2491 I AndroidRuntime: Leaving lock profiling enabled
10-29 05:01:36.665  2491  2491 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 05:01:36.703  2491  2491 D AndroidRuntime: Shutting down VM
10-29 05:01:38.040  2097  2163 I Perf    : fps=4.5 worst=4 drawn=14/37 queue=0 build=22ms verts=39k cam=-0.5,1.6,-0.5 yaw=2.84 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-29 05:01:40.246  2097  2163 I Perf    : fps=4.5 worst=4 drawn=14/37 queue=0 build=22ms verts=39k cam=-0.5,1.6,-0.5 yaw=2.84 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-29 05:01:41.544  2508  2508 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 05:01:41.554  2508  2508 I AndroidRuntime: Using default boot image
10-29 05:01:41.554  2508  2508 I AndroidRuntime: Leaving lock profiling enabled
10-29 05:01:41.678  2508  2508 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 05:01:42.251  2097  2163 I Perf    : fps=4.5 worst=5 drawn=14/37 queue=0 build=22ms verts=39k cam=-0.5,1.6,-0.5 yaw=2.84 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-29 05:01:43.326  2508  2508 D AndroidRuntime: Shutting down VM
10-29 05:01:44.339  2097  2163 I Perf    : fps=4.3 worst=4 drawn=18/44 queue=0 build=9ms verts=51k cam=-0.9,1.6,0.8 yaw=2.84 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-29 05:01:46.387  2097  2163 I Perf    : fps=4.4 worst=4 drawn=18/44 queue=0 build=9ms verts=51k cam=-0.9,1.6,0.8 yaw=2.84 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-29 05:01:48.494  2097  2163 I Perf    : fps=4.3 worst=4 drawn=18/44 queue=0 build=9ms verts=51k cam=-0.9,1.6,0.8 yaw=2.84 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-29 05:01:50.635  2097  2163 I Perf    : fps=4.2 worst=4 drawn=18/44 queue=0 build=9ms verts=51k cam=-0.9,1.6,0.8 yaw=2.84 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-29 05:01:52.817  2097  2163 I Perf    : fps=4.1 worst=4 drawn=18/44 queue=0 build=9ms verts=51k cam=-0.9,1.6,0.8 yaw=2.84 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-29 05:01:54.943  2097  2163 I Perf    : fps=4.2 worst=4 drawn=18/44 queue=0 build=9ms verts=51k cam=-0.9,1.6,0.8 yaw=2.84 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
```
## Step log
```
=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=7
10-29 05:01:04.025  2097  2163 I Perf    : fps=4.1 worst=4 drawn=18/37 queue=0 build=22ms verts=53k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 05:01:06.311  2097  2163 I Perf    : fps=3.9 worst=4 drawn=18/37 queue=0 build=22ms verts=53k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 05:01:08.358  2097  2163 I Perf    : fps=3.9 worst=4 drawn=18/37 queue=0 build=22ms verts=53k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 05:01:10.387  2097  2163 I Perf    : fps=3.9 worst=4 drawn=18/37 queue=0 build=22ms verts=53k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 05:01:12.398  2097  2163 I Perf    : fps=4.0 worst=4 drawn=18/37 queue=0 build=22ms verts=53k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 05:01:14.649  2097  2163 I Perf    : fps=4.0 worst=4 drawn=18/37 queue=0 build=22ms verts=53k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 05:01:16.896  2097  2163 I Perf    : fps=4.0 worst=4 drawn=18/37 queue=0 build=22ms verts=53k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 05:01:18.985  2097  2163 I Perf    : fps=3.8 worst=4 drawn=18/37 queue=0 build=22ms verts=53k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 05:01:21.209  2097  2163 I Perf    : fps=4.0 worst=4 drawn=18/37 queue=0 build=22ms verts=53k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 05:01:23.350  2451  2451 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-29 05:01:23.359  2097  2163 I Perf    : fps=3.7 worst=4 drawn=18/37 queue=0 build=22ms verts=53k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 05:01:25.446  2097  2163 I Perf    : fps=3.8 worst=4 drawn=18/37 queue=0 build=22ms verts=53k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 05:01:27.532  2097  2163 I Perf    : fps=3.8 worst=4 drawn=18/37 queue=0 build=22ms verts=53k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 05:01:29.609  2097  2163 I Perf    : fps=3.9 worst=4 drawn=18/37 queue=0 build=22ms verts=53k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 05:01:30.649  2475  2475 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 05:01:30.661  2475  2475 I AndroidRuntime: Using default boot image
10-29 05:01:30.661  2475  2475 I AndroidRuntime: Leaving lock profiling enabled
10-29 05:01:30.741  2475  2475 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 05:01:31.354  2475  2475 D AndroidRuntime: Shutting down VM
10-29 05:01:31.642  2097  2163 I Perf    : fps=3.9 worst=4 drawn=14/37 queue=0 build=22ms verts=39k cam=-0.5,1.6,-0.5 yaw=2.84 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 05:01:33.824  2097  2163 I Perf    : fps=4.6 worst=5 drawn=14/37 queue=0 build=22ms verts=39k cam=-0.5,1.6,-0.5 yaw=2.84 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 05:01:36.022  2097  2163 I Perf    : fps=4.5 worst=4 drawn=14/37 queue=0 build=22ms verts=39k cam=-0.5,1.6,-0.5 yaw=2.84 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 05:01:36.457  2491  2491 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 05:01:36.458  2491  2491 I AndroidRuntime: Using default boot image
10-29 05:01:36.459  2491  2491 I AndroidRuntime: Leaving lock profiling enabled
10-29 05:01:36.665  2491  2491 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 05:01:36.703  2491  2491 D AndroidRuntime: Shutting down VM
10-29 05:01:38.040  2097  2163 I Perf    : fps=4.5 worst=4 drawn=14/37 queue=0 build=22ms verts=39k cam=-0.5,1.6,-0.5 yaw=2.84 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-29 05:01:40.246  2097  2163 I Perf    : fps=4.5 worst=4 drawn=14/37 queue=0 build=22ms verts=39k cam=-0.5,1.6,-0.5 yaw=2.84 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-29 05:01:41.544  2508  2508 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 05:01:41.554  2508  2508 I AndroidRuntime: Using default boot image
10-29 05:01:41.554  2508  2508 I AndroidRuntime: Leaving lock profiling enabled
10-29 05:01:41.678  2508  2508 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 05:01:42.251  2097  2163 I Perf    : fps=4.5 worst=5 drawn=14/37 queue=0 build=22ms verts=39k cam=-0.5,1.6,-0.5 yaw=2.84 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-29 05:01:43.326  2508  2508 D AndroidRuntime: Shutting down VM
10-29 05:01:44.339  2097  2163 I Perf    : fps=4.3 worst=4 drawn=18/44 queue=0 build=9ms verts=51k cam=-0.9,1.6,0.8 yaw=2.84 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-29 05:01:46.387  2097  2163 I Perf    : fps=4.4 worst=4 drawn=18/44 queue=0 build=9ms verts=51k cam=-0.9,1.6,0.8 yaw=2.84 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-29 05:01:48.479  2525  2525 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-29 05:01:48.494  2097  2163 I Perf    : fps=4.3 worst=4 drawn=18/44 queue=0 build=9ms verts=51k cam=-0.9,1.6,0.8 yaw=2.84 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-29 05:01:50.635  2097  2163 I Perf    : fps=4.2 worst=4 drawn=18/44 queue=0 build=9ms verts=51k cam=-0.9,1.6,0.8 yaw=2.84 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-29 05:01:52.817  2097  2163 I Perf    : fps=4.1 worst=4 drawn=18/44 queue=0 build=9ms verts=51k cam=-0.9,1.6,0.8 yaw=2.84 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-29 05:01:54.943  2097  2163 I Perf    : fps=4.2 worst=4 drawn=18/44 queue=0 build=9ms verts=51k cam=-0.9,1.6,0.8 yaw=2.84 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
+ '[' -z 2097 ']'
+ grep -qa 'FATAL EXCEPTION' emulator-shots/logcat.txt
+ '[' 0 '!=' 0 ']'
+ echo 'OK: the app is alive, rendering, and the touch controls work'
OK: the app is alive, rendering, and the touch controls work
```
