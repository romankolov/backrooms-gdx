# Emulator smoke test diagnostics

Commit: 893bc8011737137cbf806b714775e8dfdc91dc7d
Outcome: success

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 28 08:35 /dev/kvm
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
INFO         | The active JSON Web Key Sets can be found here: /run/user/1001/avd/running/2743/jwks/5e4f55c7-ed66-4844-a843-e97018e4675b/active.jwk
INFO         | Scanning /run/user/1001/avd/running/2743/jwks/5e4f55c7-ed66-4844-a843-e97018e4675b for jwk keys.
INFO         | Started GRPC server at 127.0.0.1:8554, security: Local, auth: +token
INFO         | Advertising in: /run/user/1001/avd/running/pid_2743.ini
INFO         | Setting display: 0 configuration to: 1080x1920, dpi: 420x420 
INFO         | setDisplayActiveConfig: id:0, 1080x1920
INFO         | emulatorSetupEnvironment: Environment scene is not required
USER_INFO    | Emulator is performing a full startup. This may take upto two minutes, or more.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
INFO         | Boot completed in 20008 ms
INFO         | Increasing screen off timeout, logcat buffer size to 2M.
```
## Logcat (filtered)
```
10-28 08:37:42.483   504  1707 W ActivityManager: Slow operation: 75ms so far, now at attachApplicationLocked: after updateLruProcessLocked
10-28 08:37:42.484   504  1707 W ActivityManager: Slow operation: 76ms so far, now at attachApplicationLocked: after mServices.attachApplicationLocked
10-28 08:37:57.307   504   546 I ActivityManager: Start proc 2334:com.android.cellbroadcastreceiver.module/u0a129 for broadcast {com.android.cellbroadcastreceiver.module/com.android.cellbroadcastreceiver.CellBroadcastReceiver}
10-28 08:37:57.367   504  1094 I ActivityManager: Killing 1940:com.android.camera2/u0a109 (adj 985): empty #17
10-28 08:37:57.466   504   533 I ActivityManager: Killing 1967:com.android.contacts/u0a98 (adj 975): empty #17
10-28 08:38:04.171  2360  2360 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:38:04.172  2360  2360 I AndroidRuntime: Using default boot image
10-28 08:38:04.172  2360  2360 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:38:04.236  2360  2360 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:38:04.241  2360  2360 D AndroidRuntime: Shutting down VM
10-28 08:38:04.246  2360  2371 I AndroidRuntime: NOTE: attach of thread 'Binder:2360_1' failed
10-28 08:38:04.768  2255  2287 I BackroomsGL: world shader compiled
10-28 08:38:04.818  2255  2287 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 08:38:04.818  2255  2287 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 08:38:06.817  2255  2287 I Perf    : fps=6.6 worst=32 drawn=13/21 queue=2 build=63ms verts=33k cam=3.5,1.6,-2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=7
10-28 08:38:08.918  2255  2287 I Perf    : fps=8.1 worst=9 drawn=17/37 queue=0 build=12ms verts=44k cam=3.5,1.6,-2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-28 08:38:11.031  2255  2287 I Perf    : fps=8.5 worst=9 drawn=17/37 queue=0 build=12ms verts=44k cam=3.5,1.6,-2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-28 08:38:13.133  2255  2287 I Perf    : fps=8.1 worst=9 drawn=17/37 queue=0 build=12ms verts=44k cam=3.5,1.6,-2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-28 08:38:15.156  2255  2287 I Perf    : fps=8.4 worst=10 drawn=17/37 queue=0 build=12ms verts=44k cam=3.5,1.6,-2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-28 08:38:17.178  2255  2287 I Perf    : fps=8.4 worst=9 drawn=17/37 queue=0 build=12ms verts=44k cam=3.5,1.6,-2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-28 08:38:19.188  2255  2287 I Perf    : fps=8.5 worst=9 drawn=17/37 queue=0 build=12ms verts=44k cam=3.5,1.6,-2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-28 08:38:21.197  2255  2287 I Perf    : fps=8.5 worst=10 drawn=17/37 queue=0 build=12ms verts=44k cam=3.5,1.6,-2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-28 08:38:23.299  2255  2287 I Perf    : fps=8.6 worst=9 drawn=17/37 queue=0 build=12ms verts=44k cam=3.5,1.6,-2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-28 08:38:25.395  2255  2287 I Perf    : fps=8.1 worst=10 drawn=17/37 queue=0 build=12ms verts=44k cam=3.5,1.6,-2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-28 08:38:27.443  2255  2287 I Perf    : fps=8.3 worst=10 drawn=17/37 queue=0 build=12ms verts=44k cam=3.5,1.6,-2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-28 08:38:29.480  2255  2287 I Perf    : fps=8.3 worst=9 drawn=17/37 queue=0 build=12ms verts=44k cam=3.5,1.6,-2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-28 08:38:31.588  2255  2287 I Perf    : fps=8.1 worst=9 drawn=17/37 queue=0 build=12ms verts=44k cam=3.5,1.6,-2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-28 08:38:33.673  2255  2287 I Perf    : fps=8.2 worst=10 drawn=17/37 queue=0 build=12ms verts=44k cam=3.5,1.6,-2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-28 08:38:34.355  2400  2400 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:38:34.369  2400  2400 I AndroidRuntime: Using default boot image
10-28 08:38:34.369  2400  2400 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:38:34.428  2400  2400 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:38:35.065  2400  2400 D AndroidRuntime: Shutting down VM
10-28 08:38:35.727  2255  2287 I Perf    : fps=7.3 worst=9 drawn=14/37 queue=0 build=12ms verts=37k cam=3.5,1.6,-2.5 yaw=2.52 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:38:37.846  2255  2287 I Perf    : fps=7.1 worst=9 drawn=14/37 queue=0 build=12ms verts=37k cam=3.5,1.6,-2.5 yaw=2.52 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:38:39.819  2418  2418 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:38:39.830  2418  2418 I AndroidRuntime: Using default boot image
10-28 08:38:39.830  2418  2418 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:38:39.891  2418  2418 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:38:39.898  2418  2418 D AndroidRuntime: Shutting down VM
10-28 08:38:39.902  2418  2429 I AndroidRuntime: NOTE: attach of thread 'Binder:2418_1' failed
10-28 08:38:39.938  2255  2287 I Perf    : fps=7.2 worst=9 drawn=14/37 queue=0 build=12ms verts=37k cam=3.5,1.6,-2.5 yaw=2.52 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-28 08:38:40.720   504  2355 I ActivityManager: Killing 1211:com.android.deskclock/u0a108 (adj 985): empty #17
10-28 08:38:40.720   504  2355 I ActivityManager: Killing 1565:android.process.acore/u0a63 (adj 985): empty #18
10-28 08:38:40.720   504  2355 I ActivityManager: Killing 1269:com.android.cellbroadcastreceiver/u0a66 (adj 995): empty #19
10-28 08:38:41.970  2255  2287 I Perf    : fps=7.4 worst=8 drawn=14/37 queue=0 build=12ms verts=37k cam=3.5,1.6,-2.5 yaw=2.52 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-28 08:38:44.051  2255  2287 I Perf    : fps=7.2 worst=7 drawn=14/37 queue=0 build=12ms verts=37k cam=3.5,1.6,-2.5 yaw=2.52 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-28 08:38:44.900  2453  2453 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:38:44.901  2453  2453 I AndroidRuntime: Using default boot image
10-28 08:38:44.901  2453  2453 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:38:45.048  2453  2453 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:38:45.771   504   546 I ActivityManager: Start proc 2466:android.process.acore/u0a63 for content provider {com.android.providers.contacts/com.android.providers.contacts.VoicemailContentProvider}
10-28 08:38:45.807  2466  2466 I ContactsPerf: VoicemailContentProvider.onCreate start
10-28 08:38:45.811  2466  2466 I ContactsPerf: VoicemailContentProvider.onCreate finish
10-28 08:38:46.062  2255  2287 I Perf    : fps=7.5 worst=8 drawn=14/37 queue=0 build=12ms verts=37k cam=3.3,1.7,-2.1 yaw=2.52 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-28 08:38:46.664  2453  2453 D AndroidRuntime: Shutting down VM
10-28 08:38:48.126  2255  2287 I Perf    : fps=8.2 worst=9 drawn=14/37 queue=0 build=12ms verts=37k cam=2.9,-0.9,-0.7 yaw=2.52 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-28 08:38:50.160  2255  2287 I Perf    : fps=8.9 worst=52 drawn=14/27 queue=2 build=121ms verts=35k cam=-269.5,1.6,-145.5 yaw=4.96 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=12
10-28 08:38:52.238  2255  2287 I Perf    : fps=7.2 worst=8 drawn=18/37 queue=0 build=7ms verts=45k cam=-269.5,1.6,-145.5 yaw=4.96 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 08:38:54.342  2255  2287 I Perf    : fps=7.1 worst=8 drawn=18/37 queue=0 build=7ms verts=45k cam=-269.5,1.6,-145.5 yaw=4.96 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
```
## Step log
```
6.817  2255  2287 I Perf    : fps=6.6 worst=32 drawn=13/21 queue=2 build=63ms verts=33k cam=3.5,1.6,-2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=7
10-28 08:38:08.918  2255  2287 I Perf    : fps=8.1 worst=9 drawn=17/37 queue=0 build=12ms verts=44k cam=3.5,1.6,-2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-28 08:38:11.031  2255  2287 I Perf    : fps=8.5 worst=9 drawn=17/37 queue=0 build=12ms verts=44k cam=3.5,1.6,-2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-28 08:38:13.133  2255  2287 I Perf    : fps=8.1 worst=9 drawn=17/37 queue=0 build=12ms verts=44k cam=3.5,1.6,-2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-28 08:38:15.156  2255  2287 I Perf    : fps=8.4 worst=10 drawn=17/37 queue=0 build=12ms verts=44k cam=3.5,1.6,-2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-28 08:38:17.178  2255  2287 I Perf    : fps=8.4 worst=9 drawn=17/37 queue=0 build=12ms verts=44k cam=3.5,1.6,-2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-28 08:38:19.188  2255  2287 I Perf    : fps=8.5 worst=9 drawn=17/37 queue=0 build=12ms verts=44k cam=3.5,1.6,-2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-28 08:38:21.197  2255  2287 I Perf    : fps=8.5 worst=10 drawn=17/37 queue=0 build=12ms verts=44k cam=3.5,1.6,-2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-28 08:38:23.299  2255  2287 I Perf    : fps=8.6 worst=9 drawn=17/37 queue=0 build=12ms verts=44k cam=3.5,1.6,-2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-28 08:38:25.395  2255  2287 I Perf    : fps=8.1 worst=10 drawn=17/37 queue=0 build=12ms verts=44k cam=3.5,1.6,-2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-28 08:38:27.443  2255  2287 I Perf    : fps=8.3 worst=10 drawn=17/37 queue=0 build=12ms verts=44k cam=3.5,1.6,-2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-28 08:38:29.480  2255  2287 I Perf    : fps=8.3 worst=9 drawn=17/37 queue=0 build=12ms verts=44k cam=3.5,1.6,-2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-28 08:38:30.085  2394  2394 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 08:38:31.588  2255  2287 I Perf    : fps=8.1 worst=9 drawn=17/37 queue=0 build=12ms verts=44k cam=3.5,1.6,-2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-28 08:38:33.673  2255  2287 I Perf    : fps=8.2 worst=10 drawn=17/37 queue=0 build=12ms verts=44k cam=3.5,1.6,-2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-28 08:38:34.355  2400  2400 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:38:34.369  2400  2400 I AndroidRuntime: Using default boot image
10-28 08:38:34.369  2400  2400 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:38:34.428  2400  2400 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:38:35.065  2400  2400 D AndroidRuntime: Shutting down VM
10-28 08:38:35.727  2255  2287 I Perf    : fps=7.3 worst=9 drawn=14/37 queue=0 build=12ms verts=37k cam=3.5,1.6,-2.5 yaw=2.52 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:38:37.846  2255  2287 I Perf    : fps=7.1 worst=9 drawn=14/37 queue=0 build=12ms verts=37k cam=3.5,1.6,-2.5 yaw=2.52 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:38:39.819  2418  2418 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:38:39.830  2418  2418 I AndroidRuntime: Using default boot image
10-28 08:38:39.830  2418  2418 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:38:39.891  2418  2418 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:38:39.898  2418  2418 D AndroidRuntime: Shutting down VM
10-28 08:38:39.902  2418  2429 I AndroidRuntime: NOTE: attach of thread 'Binder:2418_1' failed
10-28 08:38:39.938  2255  2287 I Perf    : fps=7.2 worst=9 drawn=14/37 queue=0 build=12ms verts=37k cam=3.5,1.6,-2.5 yaw=2.52 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-28 08:38:41.970  2255  2287 I Perf    : fps=7.4 worst=8 drawn=14/37 queue=0 build=12ms verts=37k cam=3.5,1.6,-2.5 yaw=2.52 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-28 08:38:44.051  2255  2287 I Perf    : fps=7.2 worst=7 drawn=14/37 queue=0 build=12ms verts=37k cam=3.5,1.6,-2.5 yaw=2.52 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-28 08:38:44.900  2453  2453 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:38:44.901  2453  2453 I AndroidRuntime: Using default boot image
10-28 08:38:44.901  2453  2453 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:38:45.048  2453  2453 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:38:45.807  2466  2466 I ContactsPerf: VoicemailContentProvider.onCreate start
10-28 08:38:45.811  2466  2466 I ContactsPerf: VoicemailContentProvider.onCreate finish
10-28 08:38:46.062  2255  2287 I Perf    : fps=7.5 worst=8 drawn=14/37 queue=0 build=12ms verts=37k cam=3.3,1.7,-2.1 yaw=2.52 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-28 08:38:46.664  2453  2453 D AndroidRuntime: Shutting down VM
10-28 08:38:48.126  2255  2287 I Perf    : fps=8.2 worst=9 drawn=14/37 queue=0 build=12ms verts=37k cam=2.9,-0.9,-0.7 yaw=2.52 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-28 08:38:50.160  2255  2287 I Perf    : fps=8.9 worst=52 drawn=14/27 queue=2 build=121ms verts=35k cam=-269.5,1.6,-145.5 yaw=4.96 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=12
10-28 08:38:51.790  2505  2505 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 08:38:52.238  2255  2287 I Perf    : fps=7.2 worst=8 drawn=18/37 queue=0 build=7ms verts=45k cam=-269.5,1.6,-145.5 yaw=4.96 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 08:38:54.342  2255  2287 I Perf    : fps=7.1 worst=8 drawn=18/37 queue=0 build=7ms verts=45k cam=-269.5,1.6,-145.5 yaw=4.96 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
+ '[' -z 2255 ']'
+ grep -qa 'FATAL EXCEPTION' emulator-shots/logcat.txt
+ '[' 0 '!=' 0 ']'
+ echo 'OK: the app is alive, rendering, and the touch controls work'
OK: the app is alive, rendering, and the touch controls work
```
