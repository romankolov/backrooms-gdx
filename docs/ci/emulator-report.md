# Emulator smoke test diagnostics

Commit: 9a0da0a1e255f32d2da8774966f433f3cb0280ce
Outcome: success

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 28 21:11 /dev/kvm
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
INFO         | The active JSON Web Key Sets can be found here: /run/user/1001/avd/running/3246/jwks/e6f7a86e-1a42-4475-9a78-7270cfe3f3db/active.jwk
INFO         | Scanning /run/user/1001/avd/running/3246/jwks/e6f7a86e-1a42-4475-9a78-7270cfe3f3db for jwk keys.
INFO         | Started GRPC server at 127.0.0.1:8554, security: Local, auth: +token
INFO         | Advertising in: /run/user/1001/avd/running/pid_3246.ini
INFO         | Setting display: 0 configuration to: 1080x1920, dpi: 420x420 
INFO         | setDisplayActiveConfig: id:0, 1080x1920
INFO         | emulatorSetupEnvironment: Environment scene is not required
USER_INFO    | Emulator is performing a full startup. This may take upto two minutes, or more.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
INFO         | Boot completed in 26385 ms
INFO         | Increasing screen off timeout, logcat buffer size to 2M.
```
## Logcat (filtered)
```
10-28 21:15:35.631   505   545 W ActivityManager: Slow operation: 134ms so far, now at startProcess: starting to update pids map
10-28 21:15:35.631   505   545 W ActivityManager: Slow operation: 134ms so far, now at startProcess: done updating pids map
10-28 21:15:35.802   505  2083 I ActivityManager: Killing 2003:com.android.calendar/u0a108 (adj 985): empty #17
10-28 21:15:39.955   505   543 I ActivityManager: Killing 1284:com.android.cellbroadcastreceiver/u0a66 (adj 985): empty #17
10-28 21:15:45.325  2508  2508 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 21:15:45.355  2508  2508 I AndroidRuntime: Using default boot image
10-28 21:15:45.355  2508  2508 I AndroidRuntime: Leaving lock profiling enabled
10-28 21:15:45.711  2508  2508 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 21:15:45.717  2508  2508 D AndroidRuntime: Shutting down VM
10-28 21:15:45.724  2508  2519 I AndroidRuntime: NOTE: attach of thread 'Binder:2508_1' failed
10-28 21:15:45.796   505   535 I ActivityManager: Killing 2042:com.android.camera2/u0a109 (adj 985): empty #17
10-28 21:15:46.465  2174  2218 I BackroomsGL: world shader compiled
10-28 21:15:47.280  2174  2218 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 21:15:47.280  2174  2218 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 21:15:48.751  2174  2218 I Perf    : fps=1.2 worst=2 drawn=2/4 queue=2 build=14ms verts=5k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=2 noMesh=0 culled=0
10-28 21:15:50.783  2174  2218 I Perf    : fps=4.4 worst=5 drawn=14/19 queue=2 build=15ms verts=35k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=3
10-28 21:15:50.817   505   535 I ActivityManager: Killing 2065:com.android.contacts/u0a98 (adj 985): empty #17
10-28 21:15:50.818   505  1641 I ActivityManager: Killing 1622:android.process.acore/u0a63 (adj 975): empty #17
10-28 21:15:52.980  2174  2218 I Perf    : fps=4.1 worst=4 drawn=18/34 queue=2 build=129ms verts=45k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=14
10-28 21:15:55.095  2174  2218 I Perf    : fps=4.2 worst=4 drawn=18/37 queue=0 build=30ms verts=45k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 21:15:57.189  2174  2218 I Perf    : fps=4.3 worst=4 drawn=18/37 queue=0 build=30ms verts=45k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 21:15:59.288  2174  2218 I Perf    : fps=4.3 worst=4 drawn=18/37 queue=0 build=30ms verts=45k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 21:16:01.406  2174  2218 I Perf    : fps=4.2 worst=4 drawn=18/37 queue=0 build=30ms verts=45k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 21:16:03.505  2174  2218 I Perf    : fps=4.3 worst=4 drawn=18/37 queue=0 build=30ms verts=45k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 21:16:05.589  2174  2218 I Perf    : fps=4.3 worst=4 drawn=18/37 queue=0 build=30ms verts=45k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 21:16:07.714  2174  2218 I Perf    : fps=4.2 worst=4 drawn=18/37 queue=0 build=30ms verts=45k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 21:16:09.856  2174  2218 I Perf    : fps=4.2 worst=4 drawn=18/37 queue=0 build=30ms verts=45k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 21:16:12.008  2174  2218 I Perf    : fps=4.2 worst=4 drawn=18/37 queue=0 build=30ms verts=45k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 21:16:14.188  2174  2218 I Perf    : fps=4.1 worst=4 drawn=18/37 queue=0 build=30ms verts=45k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 21:16:16.396  2174  2218 I Perf    : fps=4.1 worst=4 drawn=18/37 queue=0 build=30ms verts=45k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 21:16:17.745  2550  2550 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 21:16:17.751  2550  2550 I AndroidRuntime: Using default boot image
10-28 21:16:17.751  2550  2550 I AndroidRuntime: Leaving lock profiling enabled
10-28 21:16:17.917  2550  2550 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 21:16:18.521  2550  2550 D AndroidRuntime: Shutting down VM
10-28 21:16:18.570  2174  2218 I Perf    : fps=4.1 worst=4 drawn=13/37 queue=0 build=30ms verts=33k cam=1.5,1.6,-0.5 yaw=2.79 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 21:16:20.621  2174  2218 I Perf    : fps=4.9 worst=5 drawn=13/37 queue=0 build=30ms verts=34k cam=1.5,1.6,-0.5 yaw=2.79 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 21:16:22.710  2174  2218 I Perf    : fps=4.8 worst=5 drawn=13/37 queue=0 build=30ms verts=34k cam=1.5,1.6,-0.5 yaw=2.79 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 21:16:23.793  2577  2577 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 21:16:23.806  2577  2577 I AndroidRuntime: Using default boot image
10-28 21:16:23.806  2577  2577 I AndroidRuntime: Leaving lock profiling enabled
10-28 21:16:23.886  2577  2577 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 21:16:23.901  2577  2577 D AndroidRuntime: Shutting down VM
10-28 21:16:24.874  2174  2218 I Perf    : fps=4.6 worst=5 drawn=13/37 queue=0 build=30ms verts=34k cam=1.5,1.6,-0.5 yaw=2.79 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 21:16:25.475   505   545 I ActivityManager: Start proc 2591:android.process.acore/u0a63 for content provider {com.android.providers.contacts/com.android.providers.contacts.VoicemailContentProvider}
10-28 21:16:25.514  2591  2591 I ContactsPerf: VoicemailContentProvider.onCreate start
10-28 21:16:25.516  2591  2591 I ContactsPerf: VoicemailContentProvider.onCreate finish
10-28 21:16:26.969  2174  2218 I Perf    : fps=4.8 worst=5 drawn=13/37 queue=0 build=30ms verts=34k cam=1.5,1.6,-0.5 yaw=2.79 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 21:16:28.975  2621  2621 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 21:16:28.977  2621  2621 I AndroidRuntime: Using default boot image
10-28 21:16:28.977  2621  2621 I AndroidRuntime: Leaving lock profiling enabled
10-28 21:16:29.065  2174  2218 I Perf    : fps=4.8 worst=5 drawn=13/37 queue=0 build=30ms verts=34k cam=1.5,1.6,-0.5 yaw=2.79 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 21:16:29.081  2621  2621 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 21:16:30.815  2621  2621 D AndroidRuntime: Shutting down VM
10-28 21:16:31.079  2174  2218 I Perf    : fps=4.5 worst=5 drawn=14/41 queue=2 build=20ms verts=36k cam=1.3,1.6,0.6 yaw=2.79 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=25
10-28 21:16:33.142  2174  2218 I Perf    : fps=4.4 worst=4 drawn=17/44 queue=0 build=229ms verts=44k cam=1.3,1.6,0.6 yaw=2.79 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=27
10-28 21:16:35.221  2174  2218 I Perf    : fps=4.3 worst=4 drawn=17/44 queue=0 build=229ms verts=44k cam=1.3,1.6,0.6 yaw=2.79 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=27
10-28 21:16:37.425  2174  2218 I Perf    : fps=4.1 worst=4 drawn=17/44 queue=0 build=229ms verts=44k cam=1.3,1.6,0.6 yaw=2.79 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=27
10-28 21:16:39.621  2174  2218 I Perf    : fps=4.1 worst=4 drawn=17/44 queue=0 build=229ms verts=44k cam=1.3,1.6,0.6 yaw=2.79 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=27
10-28 21:16:41.787  2174  2218 I Perf    : fps=4.2 worst=4 drawn=17/44 queue=0 build=229ms verts=44k cam=1.3,1.6,0.6 yaw=2.79 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=27
```
## Step log
```
uild=15ms verts=35k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=3
10-28 21:15:52.980  2174  2218 I Perf    : fps=4.1 worst=4 drawn=18/34 queue=2 build=129ms verts=45k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=14
10-28 21:15:55.095  2174  2218 I Perf    : fps=4.2 worst=4 drawn=18/37 queue=0 build=30ms verts=45k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 21:15:57.189  2174  2218 I Perf    : fps=4.3 worst=4 drawn=18/37 queue=0 build=30ms verts=45k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 21:15:59.288  2174  2218 I Perf    : fps=4.3 worst=4 drawn=18/37 queue=0 build=30ms verts=45k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 21:16:01.406  2174  2218 I Perf    : fps=4.2 worst=4 drawn=18/37 queue=0 build=30ms verts=45k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 21:16:03.505  2174  2218 I Perf    : fps=4.3 worst=4 drawn=18/37 queue=0 build=30ms verts=45k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 21:16:05.589  2174  2218 I Perf    : fps=4.3 worst=4 drawn=18/37 queue=0 build=30ms verts=45k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 21:16:07.714  2174  2218 I Perf    : fps=4.2 worst=4 drawn=18/37 queue=0 build=30ms verts=45k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 21:16:09.856  2174  2218 I Perf    : fps=4.2 worst=4 drawn=18/37 queue=0 build=30ms verts=45k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 21:16:12.006  2537  2537 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 21:16:12.008  2174  2218 I Perf    : fps=4.2 worst=4 drawn=18/37 queue=0 build=30ms verts=45k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 21:16:14.188  2174  2218 I Perf    : fps=4.1 worst=4 drawn=18/37 queue=0 build=30ms verts=45k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 21:16:16.396  2174  2218 I Perf    : fps=4.1 worst=4 drawn=18/37 queue=0 build=30ms verts=45k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 21:16:17.745  2550  2550 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 21:16:17.751  2550  2550 I AndroidRuntime: Using default boot image
10-28 21:16:17.751  2550  2550 I AndroidRuntime: Leaving lock profiling enabled
10-28 21:16:17.917  2550  2550 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 21:16:18.521  2550  2550 D AndroidRuntime: Shutting down VM
10-28 21:16:18.570  2174  2218 I Perf    : fps=4.1 worst=4 drawn=13/37 queue=0 build=30ms verts=33k cam=1.5,1.6,-0.5 yaw=2.79 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 21:16:20.621  2174  2218 I Perf    : fps=4.9 worst=5 drawn=13/37 queue=0 build=30ms verts=34k cam=1.5,1.6,-0.5 yaw=2.79 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 21:16:22.710  2174  2218 I Perf    : fps=4.8 worst=5 drawn=13/37 queue=0 build=30ms verts=34k cam=1.5,1.6,-0.5 yaw=2.79 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 21:16:23.793  2577  2577 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 21:16:23.806  2577  2577 I AndroidRuntime: Using default boot image
10-28 21:16:23.806  2577  2577 I AndroidRuntime: Leaving lock profiling enabled
10-28 21:16:23.886  2577  2577 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 21:16:23.901  2577  2577 D AndroidRuntime: Shutting down VM
10-28 21:16:24.874  2174  2218 I Perf    : fps=4.6 worst=5 drawn=13/37 queue=0 build=30ms verts=34k cam=1.5,1.6,-0.5 yaw=2.79 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 21:16:25.514  2591  2591 I ContactsPerf: VoicemailContentProvider.onCreate start
10-28 21:16:25.516  2591  2591 I ContactsPerf: VoicemailContentProvider.onCreate finish
10-28 21:16:26.969  2174  2218 I Perf    : fps=4.8 worst=5 drawn=13/37 queue=0 build=30ms verts=34k cam=1.5,1.6,-0.5 yaw=2.79 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 21:16:28.975  2621  2621 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 21:16:28.977  2621  2621 I AndroidRuntime: Using default boot image
10-28 21:16:28.977  2621  2621 I AndroidRuntime: Leaving lock profiling enabled
10-28 21:16:29.065  2174  2218 I Perf    : fps=4.8 worst=5 drawn=13/37 queue=0 build=30ms verts=34k cam=1.5,1.6,-0.5 yaw=2.79 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 21:16:29.081  2621  2621 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 21:16:30.815  2621  2621 D AndroidRuntime: Shutting down VM
10-28 21:16:31.079  2174  2218 I Perf    : fps=4.5 worst=5 drawn=14/41 queue=2 build=20ms verts=36k cam=1.3,1.6,0.6 yaw=2.79 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=25
10-28 21:16:33.142  2174  2218 I Perf    : fps=4.4 worst=4 drawn=17/44 queue=0 build=229ms verts=44k cam=1.3,1.6,0.6 yaw=2.79 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=27
10-28 21:16:35.221  2174  2218 I Perf    : fps=4.3 worst=4 drawn=17/44 queue=0 build=229ms verts=44k cam=1.3,1.6,0.6 yaw=2.79 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=27
10-28 21:16:35.944  2639  2639 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 21:16:37.425  2174  2218 I Perf    : fps=4.1 worst=4 drawn=17/44 queue=0 build=229ms verts=44k cam=1.3,1.6,0.6 yaw=2.79 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=27
10-28 21:16:39.621  2174  2218 I Perf    : fps=4.1 worst=4 drawn=17/44 queue=0 build=229ms verts=44k cam=1.3,1.6,0.6 yaw=2.79 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=27
10-28 21:16:41.787  2174  2218 I Perf    : fps=4.2 worst=4 drawn=17/44 queue=0 build=229ms verts=44k cam=1.3,1.6,0.6 yaw=2.79 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=27
+ '[' -z 2174 ']'
+ grep -qa 'FATAL EXCEPTION' emulator-shots/logcat.txt
+ '[' 0 '!=' 0 ']'
+ echo 'OK: the app is alive, rendering, and the touch controls work'
OK: the app is alive, rendering, and the touch controls work
```
