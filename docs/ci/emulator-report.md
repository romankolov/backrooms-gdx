# Emulator smoke test diagnostics

Commit: e97a7827a65aab4b30023845f741618b9e9ea0c6
Outcome: success

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 28 12:27 /dev/kvm
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
INFO         | The active JSON Web Key Sets can be found here: /run/user/1001/avd/running/2746/jwks/ae2ff9f0-b5ac-49a2-b8a6-c0886e3f205c/active.jwk
INFO         | Scanning /run/user/1001/avd/running/2746/jwks/ae2ff9f0-b5ac-49a2-b8a6-c0886e3f205c for jwk keys.
INFO         | Started GRPC server at 127.0.0.1:8554, security: Local, auth: +token
INFO         | Advertising in: /run/user/1001/avd/running/pid_2746.ini
INFO         | Setting display: 0 configuration to: 1080x1920, dpi: 420x420 
INFO         | setDisplayActiveConfig: id:0, 1080x1920
INFO         | emulatorSetupEnvironment: Environment scene is not required
USER_INFO    | Emulator is performing a full startup. This may take upto two minutes, or more.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
INFO         | Boot completed in 26564 ms
INFO         | Increasing screen off timeout, logcat buffer size to 2M.
```
## Logcat (filtered)
```
10-28 12:31:28.207   508   538 I ActivityManager: Killing 2000:com.android.quicksearchbox/u0a104 (adj 985): empty #18
10-28 12:31:28.669  2400  2400 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 12:31:28.673  2400  2400 I AndroidRuntime: Using default boot image
10-28 12:31:28.673  2400  2400 I AndroidRuntime: Leaving lock profiling enabled
10-28 12:31:28.832  2400  2400 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 12:31:28.880  2400  2400 D AndroidRuntime: Shutting down VM
10-28 12:31:29.406  2091  2142 I BackroomsGL: world shader compiled
10-28 12:31:29.480  2091  2142 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 12:31:29.480  2091  2142 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 12:31:31.388  2091  2142 I Perf    : fps=2.4 worst=5 drawn=5/8 queue=2 build=18ms verts=13k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=5 noMesh=0 culled=1
10-28 12:31:33.342   508   810 I ActivityManager: Killing 2065:com.android.calendar/u0a108 (adj 975): empty #17
10-28 12:31:33.597  2091  2142 I Perf    : fps=4.1 worst=4 drawn=11/23 queue=2 build=36ms verts=28k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=11 noMesh=0 culled=10
10-28 12:31:35.758  2091  2142 I Perf    : fps=4.2 worst=4 drawn=14/37 queue=0 build=33ms verts=36k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=22
10-28 12:31:37.954  2091  2142 I Perf    : fps=4.1 worst=4 drawn=14/37 queue=0 build=33ms verts=36k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:31:40.061  2091  2142 I Perf    : fps=4.3 worst=4 drawn=14/37 queue=0 build=33ms verts=36k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:31:42.160  2091  2142 I Perf    : fps=4.3 worst=4 drawn=14/37 queue=0 build=33ms verts=36k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:31:44.367  2091  2142 I Perf    : fps=4.1 worst=4 drawn=14/37 queue=0 build=33ms verts=36k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:31:46.547  2091  2142 I Perf    : fps=4.1 worst=4 drawn=14/37 queue=0 build=33ms verts=36k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:31:48.711  2091  2142 I Perf    : fps=4.2 worst=4 drawn=14/37 queue=0 build=33ms verts=36k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:31:50.939  2091  2142 I Perf    : fps=4.0 worst=4 drawn=14/37 queue=0 build=33ms verts=36k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:31:53.126  2091  2142 I Perf    : fps=4.1 worst=4 drawn=14/37 queue=0 build=33ms verts=36k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:31:55.313  2091  2142 I Perf    : fps=4.1 worst=4 drawn=14/37 queue=0 build=33ms verts=36k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:31:57.402  2091  2142 I Perf    : fps=3.8 worst=4 drawn=14/37 queue=0 build=33ms verts=36k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:31:59.415  2091  2142 I Perf    : fps=4.0 worst=4 drawn=14/37 queue=0 build=33ms verts=36k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:32:01.496  2091  2142 I Perf    : fps=3.8 worst=4 drawn=14/37 queue=0 build=33ms verts=36k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:32:02.890  2448  2448 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 12:32:02.914  2448  2448 I AndroidRuntime: Using default boot image
10-28 12:32:02.914  2448  2448 I AndroidRuntime: Leaving lock profiling enabled
10-28 12:32:03.151  2448  2448 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 12:32:03.707  2091  2142 I Perf    : fps=4.1 worst=4 drawn=15/37 queue=0 build=33ms verts=38k cam=-0.5,1.6,1.5 yaw=1.64 pitch=0.00 torch=1 ready=true drawn=15 noMesh=0 culled=22
10-28 12:32:03.796  2448  2448 D AndroidRuntime: Shutting down VM
10-28 12:32:05.856  2091  2142 I Perf    : fps=4.2 worst=4 drawn=18/37 queue=0 build=33ms verts=48k cam=-0.5,1.6,1.5 yaw=2.57 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:32:07.976  2091  2142 I Perf    : fps=4.2 worst=4 drawn=18/37 queue=0 build=33ms verts=48k cam=-0.5,1.6,1.5 yaw=2.57 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:32:09.142  2464  2464 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 12:32:09.147  2464  2464 I AndroidRuntime: Using default boot image
10-28 12:32:09.147  2464  2464 I AndroidRuntime: Leaving lock profiling enabled
10-28 12:32:09.299  2464  2464 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 12:32:09.306  2464  2464 D AndroidRuntime: Shutting down VM
10-28 12:32:10.131  2091  2142 I Perf    : fps=4.2 worst=4 drawn=18/37 queue=0 build=33ms verts=48k cam=-0.5,1.6,1.5 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 12:32:10.212   508   548 W ActivityManager: Slow operation: 71ms so far, now at startProcess: returned from zygote!
10-28 12:32:10.213   508   548 W ActivityManager: Slow operation: 71ms so far, now at startProcess: done updating battery stats
10-28 12:32:10.213   508   548 W ActivityManager: Slow operation: 71ms so far, now at startProcess: building log message
10-28 12:32:10.213   508   548 I ActivityManager: Start proc 2477:android.process.acore/u0a64 for content provider {com.android.providers.contacts/com.android.providers.contacts.VoicemailContentProvider}
10-28 12:32:10.213   508   548 W ActivityManager: Slow operation: 71ms so far, now at startProcess: starting to update pids map
10-28 12:32:10.213   508   548 W ActivityManager: Slow operation: 72ms so far, now at startProcess: done updating pids map
10-28 12:32:10.404  2477  2477 I ContactsPerf: VoicemailContentProvider.onCreate start
10-28 12:32:10.405  2477  2477 I ContactsPerf: VoicemailContentProvider.onCreate finish
10-28 12:32:12.271  2091  2142 I Perf    : fps=4.2 worst=4 drawn=18/37 queue=0 build=33ms verts=48k cam=-0.5,1.6,1.5 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 12:32:14.096  2508  2508 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 12:32:14.098  2508  2508 I AndroidRuntime: Using default boot image
10-28 12:32:14.098  2508  2508 I AndroidRuntime: Leaving lock profiling enabled
10-28 12:32:14.240  2508  2508 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 12:32:14.454  2091  2142 I Perf    : fps=4.1 worst=4 drawn=18/37 queue=0 build=33ms verts=48k cam=-0.5,1.6,1.5 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 12:32:15.747  2508  2508 D AndroidRuntime: Shutting down VM
10-28 12:32:16.586  2091  2142 I Perf    : fps=4.2 worst=4 drawn=17/37 queue=0 build=33ms verts=45k cam=-1.1,1.6,2.4 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 12:32:18.586  2091  2142 I Perf    : fps=4.5 worst=4 drawn=17/37 queue=0 build=33ms verts=45k cam=-1.1,1.6,2.5 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 12:32:20.817  2091  2142 I Perf    : fps=4.5 worst=4 drawn=17/37 queue=0 build=33ms verts=45k cam=-1.1,1.6,2.5 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 12:32:22.960  2091  2142 I Perf    : fps=4.2 worst=4 drawn=17/37 queue=0 build=33ms verts=45k cam=-1.1,1.6,2.5 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 12:32:25.111  2091  2142 I Perf    : fps=4.2 worst=4 drawn=17/37 queue=0 build=33ms verts=45k cam=-1.1,1.6,2.5 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 12:32:27.248  2091  2142 I Perf    : fps=4.2 worst=4 drawn=17/37 queue=0 build=33ms verts=45k cam=-1.1,1.6,2.5 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
```
## Step log
```
ild=33ms verts=36k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=22
10-28 12:31:37.954  2091  2142 I Perf    : fps=4.1 worst=4 drawn=14/37 queue=0 build=33ms verts=36k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:31:40.061  2091  2142 I Perf    : fps=4.3 worst=4 drawn=14/37 queue=0 build=33ms verts=36k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:31:42.160  2091  2142 I Perf    : fps=4.3 worst=4 drawn=14/37 queue=0 build=33ms verts=36k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:31:44.367  2091  2142 I Perf    : fps=4.1 worst=4 drawn=14/37 queue=0 build=33ms verts=36k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:31:46.547  2091  2142 I Perf    : fps=4.1 worst=4 drawn=14/37 queue=0 build=33ms verts=36k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:31:48.711  2091  2142 I Perf    : fps=4.2 worst=4 drawn=14/37 queue=0 build=33ms verts=36k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:31:50.939  2091  2142 I Perf    : fps=4.0 worst=4 drawn=14/37 queue=0 build=33ms verts=36k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:31:53.126  2091  2142 I Perf    : fps=4.1 worst=4 drawn=14/37 queue=0 build=33ms verts=36k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:31:54.791  2424  2424 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 12:31:55.313  2091  2142 I Perf    : fps=4.1 worst=4 drawn=14/37 queue=0 build=33ms verts=36k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:31:57.402  2091  2142 I Perf    : fps=3.8 worst=4 drawn=14/37 queue=0 build=33ms verts=36k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:31:59.415  2091  2142 I Perf    : fps=4.0 worst=4 drawn=14/37 queue=0 build=33ms verts=36k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:32:01.496  2091  2142 I Perf    : fps=3.8 worst=4 drawn=14/37 queue=0 build=33ms verts=36k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:32:02.890  2448  2448 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 12:32:02.914  2448  2448 I AndroidRuntime: Using default boot image
10-28 12:32:02.914  2448  2448 I AndroidRuntime: Leaving lock profiling enabled
10-28 12:32:03.151  2448  2448 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 12:32:03.707  2091  2142 I Perf    : fps=4.1 worst=4 drawn=15/37 queue=0 build=33ms verts=38k cam=-0.5,1.6,1.5 yaw=1.64 pitch=0.00 torch=1 ready=true drawn=15 noMesh=0 culled=22
10-28 12:32:03.796  2448  2448 D AndroidRuntime: Shutting down VM
10-28 12:32:05.856  2091  2142 I Perf    : fps=4.2 worst=4 drawn=18/37 queue=0 build=33ms verts=48k cam=-0.5,1.6,1.5 yaw=2.57 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:32:07.976  2091  2142 I Perf    : fps=4.2 worst=4 drawn=18/37 queue=0 build=33ms verts=48k cam=-0.5,1.6,1.5 yaw=2.57 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:32:09.142  2464  2464 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 12:32:09.147  2464  2464 I AndroidRuntime: Using default boot image
10-28 12:32:09.147  2464  2464 I AndroidRuntime: Leaving lock profiling enabled
10-28 12:32:09.299  2464  2464 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 12:32:09.306  2464  2464 D AndroidRuntime: Shutting down VM
10-28 12:32:10.131  2091  2142 I Perf    : fps=4.2 worst=4 drawn=18/37 queue=0 build=33ms verts=48k cam=-0.5,1.6,1.5 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 12:32:10.404  2477  2477 I ContactsPerf: VoicemailContentProvider.onCreate start
10-28 12:32:10.405  2477  2477 I ContactsPerf: VoicemailContentProvider.onCreate finish
10-28 12:32:12.271  2091  2142 I Perf    : fps=4.2 worst=4 drawn=18/37 queue=0 build=33ms verts=48k cam=-0.5,1.6,1.5 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 12:32:14.096  2508  2508 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 12:32:14.098  2508  2508 I AndroidRuntime: Using default boot image
10-28 12:32:14.098  2508  2508 I AndroidRuntime: Leaving lock profiling enabled
10-28 12:32:14.240  2508  2508 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 12:32:14.454  2091  2142 I Perf    : fps=4.1 worst=4 drawn=18/37 queue=0 build=33ms verts=48k cam=-0.5,1.6,1.5 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 12:32:15.747  2508  2508 D AndroidRuntime: Shutting down VM
10-28 12:32:16.586  2091  2142 I Perf    : fps=4.2 worst=4 drawn=17/37 queue=0 build=33ms verts=45k cam=-1.1,1.6,2.4 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 12:32:18.586  2091  2142 I Perf    : fps=4.5 worst=4 drawn=17/37 queue=0 build=33ms verts=45k cam=-1.1,1.6,2.5 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 12:32:20.817  2091  2142 I Perf    : fps=4.5 worst=4 drawn=17/37 queue=0 build=33ms verts=45k cam=-1.1,1.6,2.5 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 12:32:21.301  2526  2526 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 12:32:22.960  2091  2142 I Perf    : fps=4.2 worst=4 drawn=17/37 queue=0 build=33ms verts=45k cam=-1.1,1.6,2.5 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 12:32:25.111  2091  2142 I Perf    : fps=4.2 worst=4 drawn=17/37 queue=0 build=33ms verts=45k cam=-1.1,1.6,2.5 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 12:32:27.248  2091  2142 I Perf    : fps=4.2 worst=4 drawn=17/37 queue=0 build=33ms verts=45k cam=-1.1,1.6,2.5 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
+ '[' -z 2091 ']'
+ grep -qa 'FATAL EXCEPTION' emulator-shots/logcat.txt
+ '[' 0 '!=' 0 ']'
+ echo 'OK: the app is alive, rendering, and the touch controls work'
OK: the app is alive, rendering, and the touch controls work
```
