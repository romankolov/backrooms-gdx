# Emulator smoke test diagnostics

Commit: a51c83a4ed305b3eba23cd38d8a4eefba8d2a7a9
Outcome: success

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 28 08:15 /dev/kvm
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
INFO         | The active JSON Web Key Sets can be found here: /run/user/1001/avd/running/2755/jwks/c91ed4fa-7ffb-409d-bf70-1eca1400880d/active.jwk
INFO         | Scanning /run/user/1001/avd/running/2755/jwks/c91ed4fa-7ffb-409d-bf70-1eca1400880d for jwk keys.
INFO         | Started GRPC server at 127.0.0.1:8554, security: Local, auth: +token
INFO         | Advertising in: /run/user/1001/avd/running/pid_2755.ini
INFO         | Setting display: 0 configuration to: 1080x1920, dpi: 420x420 
INFO         | setDisplayActiveConfig: id:0, 1080x1920
INFO         | emulatorSetupEnvironment: Environment scene is not required
USER_INFO    | Emulator is performing a full startup. This may take upto two minutes, or more.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
INFO         | Boot completed in 26180 ms
INFO         | Increasing screen off timeout, logcat buffer size to 2M.
```
## Logcat (filtered)
```
10-28 08:19:43.226   503   545 I ActivityManager: Start proc 2373:com.android.cellbroadcastreceiver.module/u0a127 for broadcast {com.android.cellbroadcastreceiver.module/com.android.cellbroadcastreceiver.CellBroadcastReceiver}
10-28 08:19:43.226   503   545 W ActivityManager: Slow operation: 56ms so far, now at startProcess: starting to update pids map
10-28 08:19:43.226   503   545 W ActivityManager: Slow operation: 56ms so far, now at startProcess: done updating pids map
10-28 08:19:43.277   503   532 I ActivityManager: Killing 1814:com.android.provision/u0a116 (adj 985): empty #17
10-28 08:19:46.599   503  2073 I ActivityManager: Killing 1336:com.android.cellbroadcastreceiver/u0a63 (adj 985): empty #17
10-28 08:19:51.802   503   535 I ActivityManager: Killing 1563:android.process.acore/u0a64 (adj 985): empty #17
10-28 08:19:51.807   503   864 I ActivityManager: Killing 1971:com.android.localtransport/1000 (adj 975): empty #17
10-28 08:19:52.680  2398  2398 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:19:52.686  2398  2398 I AndroidRuntime: Using default boot image
10-28 08:19:52.686  2398  2398 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:19:52.778  2398  2398 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:19:52.785  2398  2398 D AndroidRuntime: Shutting down VM
10-28 08:19:53.344  2177  2220 I BackroomsGL: world shader compiled
10-28 08:19:53.398  2177  2220 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 08:19:53.398  2177  2220 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 08:19:55.355  2177  2220 I Perf    : fps=5.8 worst=44 drawn=6/9 queue=2 build=334ms verts=15k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=6 noMesh=0 culled=2
10-28 08:19:57.460  2177  2220 I Perf    : fps=4.8 worst=5 drawn=14/24 queue=2 build=16ms verts=35k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=8
10-28 08:19:59.472  2177  2220 I Perf    : fps=4.9 worst=5 drawn=18/37 queue=0 build=16ms verts=45k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:20:01.583  2177  2220 I Perf    : fps=4.7 worst=5 drawn=18/37 queue=0 build=16ms verts=45k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:20:03.647  2177  2220 I Perf    : fps=4.8 worst=5 drawn=18/37 queue=0 build=16ms verts=45k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:20:05.817  2177  2220 I Perf    : fps=5.1 worst=6 drawn=18/37 queue=0 build=16ms verts=45k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:20:07.840  2177  2220 I Perf    : fps=4.9 worst=5 drawn=18/37 queue=0 build=16ms verts=45k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:20:10.028  2177  2220 I Perf    : fps=4.6 worst=5 drawn=18/37 queue=0 build=16ms verts=45k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:20:12.091  2177  2220 I Perf    : fps=4.8 worst=5 drawn=18/37 queue=0 build=16ms verts=45k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:20:14.145  2177  2220 I Perf    : fps=4.9 worst=5 drawn=18/37 queue=0 build=16ms verts=45k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:20:16.218  2177  2220 I Perf    : fps=4.8 worst=5 drawn=18/37 queue=0 build=16ms verts=45k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:20:18.362  2177  2220 I Perf    : fps=4.7 worst=5 drawn=18/37 queue=0 build=16ms verts=45k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:20:20.506  2177  2220 I Perf    : fps=4.7 worst=4 drawn=18/37 queue=0 build=16ms verts=45k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:20:22.638  2177  2220 I Perf    : fps=4.7 worst=5 drawn=18/37 queue=0 build=16ms verts=45k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:20:24.654  2177  2220 I Perf    : fps=5.0 worst=5 drawn=18/37 queue=0 build=16ms verts=45k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:20:25.824   503   503 I ActivityManager: Killing 1995:com.android.contacts/u0a98 (adj 985): empty #17
10-28 08:20:25.851  2448  2448 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:20:25.864  2448  2448 I AndroidRuntime: Using default boot image
10-28 08:20:25.864  2448  2448 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:20:26.076  2448  2448 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:20:26.660  2177  2220 I Perf    : fps=5.0 worst=6 drawn=16/37 queue=0 build=16ms verts=39k cam=-0.5,1.6,-0.5 yaw=2.48 pitch=0.00 torch=1 ready=true drawn=16 noMesh=0 culled=21
10-28 08:20:26.772  2448  2448 D AndroidRuntime: Shutting down VM
10-28 08:20:28.739  2177  2220 I Perf    : fps=5.8 worst=7 drawn=13/37 queue=0 build=16ms verts=31k cam=-0.5,1.6,-0.5 yaw=2.64 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 08:20:30.881  2177  2220 I Perf    : fps=6.0 worst=7 drawn=13/37 queue=0 build=16ms verts=31k cam=-0.5,1.6,-0.5 yaw=2.64 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 08:20:31.548  2465  2465 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:20:31.652  2465  2465 I AndroidRuntime: Using default boot image
10-28 08:20:31.652  2465  2465 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:20:31.765  2465  2465 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:20:31.784  2465  2465 D AndroidRuntime: Shutting down VM
10-28 08:20:32.567   503   545 I ActivityManager: Start proc 2479:android.process.acore/u0a64 for content provider {com.android.providers.contacts/com.android.providers.contacts.VoicemailContentProvider}
10-28 08:20:32.667  2479  2479 I ContactsPerf: VoicemailContentProvider.onCreate start
10-28 08:20:32.669  2479  2479 I ContactsPerf: VoicemailContentProvider.onCreate finish
10-28 08:20:32.928  2177  2220 I Perf    : fps=5.9 worst=7 drawn=13/37 queue=0 build=16ms verts=31k cam=-0.5,1.6,-0.5 yaw=2.64 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 08:20:34.937  2177  2220 I Perf    : fps=6.0 worst=7 drawn=13/37 queue=0 build=16ms verts=31k cam=-0.5,1.6,-0.5 yaw=2.64 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 08:20:36.562  2510  2510 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:20:36.577  2510  2510 I AndroidRuntime: Using default boot image
10-28 08:20:36.577  2510  2510 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:20:36.730  2510  2510 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:20:36.972  2177  2220 I Perf    : fps=5.9 worst=6 drawn=13/37 queue=0 build=16ms verts=31k cam=-0.5,1.6,-0.5 yaw=2.64 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 08:20:38.249  2510  2510 D AndroidRuntime: Shutting down VM
10-28 08:20:38.998  2177  2220 I Perf    : fps=6.0 worst=7 drawn=18/44 queue=0 build=35ms verts=44k cam=-1.2,1.6,0.8 yaw=2.64 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-28 08:20:41.101  2177  2220 I Perf    : fps=6.1 worst=6 drawn=18/44 queue=0 build=35ms verts=44k cam=-1.2,1.6,0.8 yaw=2.64 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-28 08:20:43.236  2177  2220 I Perf    : fps=6.1 worst=7 drawn=18/44 queue=0 build=35ms verts=44k cam=-1.2,1.6,0.8 yaw=2.64 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-28 08:20:45.238  2177  2220 I Perf    : fps=5.9 worst=6 drawn=18/44 queue=0 build=35ms verts=44k cam=-1.2,1.6,0.8 yaw=2.64 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-28 08:20:47.370  2177  2220 I Perf    : fps=6.1 worst=7 drawn=18/44 queue=0 build=35ms verts=44k cam=-1.2,1.6,0.8 yaw=2.64 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
```
## Step log
```
cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=8
10-28 08:19:59.472  2177  2220 I Perf    : fps=4.9 worst=5 drawn=18/37 queue=0 build=16ms verts=45k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:20:01.583  2177  2220 I Perf    : fps=4.7 worst=5 drawn=18/37 queue=0 build=16ms verts=45k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:20:03.647  2177  2220 I Perf    : fps=4.8 worst=5 drawn=18/37 queue=0 build=16ms verts=45k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:20:05.817  2177  2220 I Perf    : fps=5.1 worst=6 drawn=18/37 queue=0 build=16ms verts=45k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:20:07.840  2177  2220 I Perf    : fps=4.9 worst=5 drawn=18/37 queue=0 build=16ms verts=45k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:20:10.028  2177  2220 I Perf    : fps=4.6 worst=5 drawn=18/37 queue=0 build=16ms verts=45k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:20:12.091  2177  2220 I Perf    : fps=4.8 worst=5 drawn=18/37 queue=0 build=16ms verts=45k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:20:14.145  2177  2220 I Perf    : fps=4.9 worst=5 drawn=18/37 queue=0 build=16ms verts=45k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:20:16.218  2177  2220 I Perf    : fps=4.8 worst=5 drawn=18/37 queue=0 build=16ms verts=45k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:20:18.362  2177  2220 I Perf    : fps=4.7 worst=5 drawn=18/37 queue=0 build=16ms verts=45k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:20:19.254  2424  2424 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 08:20:20.506  2177  2220 I Perf    : fps=4.7 worst=4 drawn=18/37 queue=0 build=16ms verts=45k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:20:22.638  2177  2220 I Perf    : fps=4.7 worst=5 drawn=18/37 queue=0 build=16ms verts=45k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:20:24.654  2177  2220 I Perf    : fps=5.0 worst=5 drawn=18/37 queue=0 build=16ms verts=45k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:20:25.851  2448  2448 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:20:25.864  2448  2448 I AndroidRuntime: Using default boot image
10-28 08:20:25.864  2448  2448 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:20:26.076  2448  2448 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:20:26.660  2177  2220 I Perf    : fps=5.0 worst=6 drawn=16/37 queue=0 build=16ms verts=39k cam=-0.5,1.6,-0.5 yaw=2.48 pitch=0.00 torch=1 ready=true drawn=16 noMesh=0 culled=21
10-28 08:20:26.772  2448  2448 D AndroidRuntime: Shutting down VM
10-28 08:20:28.739  2177  2220 I Perf    : fps=5.8 worst=7 drawn=13/37 queue=0 build=16ms verts=31k cam=-0.5,1.6,-0.5 yaw=2.64 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 08:20:30.881  2177  2220 I Perf    : fps=6.0 worst=7 drawn=13/37 queue=0 build=16ms verts=31k cam=-0.5,1.6,-0.5 yaw=2.64 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 08:20:31.548  2465  2465 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:20:31.652  2465  2465 I AndroidRuntime: Using default boot image
10-28 08:20:31.652  2465  2465 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:20:31.765  2465  2465 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:20:31.784  2465  2465 D AndroidRuntime: Shutting down VM
10-28 08:20:32.667  2479  2479 I ContactsPerf: VoicemailContentProvider.onCreate start
10-28 08:20:32.669  2479  2479 I ContactsPerf: VoicemailContentProvider.onCreate finish
10-28 08:20:32.928  2177  2220 I Perf    : fps=5.9 worst=7 drawn=13/37 queue=0 build=16ms verts=31k cam=-0.5,1.6,-0.5 yaw=2.64 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 08:20:34.937  2177  2220 I Perf    : fps=6.0 worst=7 drawn=13/37 queue=0 build=16ms verts=31k cam=-0.5,1.6,-0.5 yaw=2.64 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 08:20:36.562  2510  2510 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:20:36.577  2510  2510 I AndroidRuntime: Using default boot image
10-28 08:20:36.577  2510  2510 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:20:36.730  2510  2510 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:20:36.972  2177  2220 I Perf    : fps=5.9 worst=6 drawn=13/37 queue=0 build=16ms verts=31k cam=-0.5,1.6,-0.5 yaw=2.64 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 08:20:38.249  2510  2510 D AndroidRuntime: Shutting down VM
10-28 08:20:38.998  2177  2220 I Perf    : fps=6.0 worst=7 drawn=18/44 queue=0 build=35ms verts=44k cam=-1.2,1.6,0.8 yaw=2.64 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-28 08:20:41.101  2177  2220 I Perf    : fps=6.1 worst=6 drawn=18/44 queue=0 build=35ms verts=44k cam=-1.2,1.6,0.8 yaw=2.64 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-28 08:20:43.036  2530  2530 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 08:20:43.236  2177  2220 I Perf    : fps=6.1 worst=7 drawn=18/44 queue=0 build=35ms verts=44k cam=-1.2,1.6,0.8 yaw=2.64 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-28 08:20:45.238  2177  2220 I Perf    : fps=5.9 worst=6 drawn=18/44 queue=0 build=35ms verts=44k cam=-1.2,1.6,0.8 yaw=2.64 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-28 08:20:47.370  2177  2220 I Perf    : fps=6.1 worst=7 drawn=18/44 queue=0 build=35ms verts=44k cam=-1.2,1.6,0.8 yaw=2.64 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
+ '[' -z 2177 ']'
+ grep -qa 'FATAL EXCEPTION' emulator-shots/logcat.txt
+ '[' 0 '!=' 0 ']'
+ echo 'OK: the app is alive, rendering, and the touch controls work'
OK: the app is alive, rendering, and the touch controls work
```
