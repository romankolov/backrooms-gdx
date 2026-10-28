# Emulator smoke test diagnostics

Commit: 9cb852be1e3fc33185e33b00298298d37e0c6152
Outcome: success

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 28 12:38 /dev/kvm
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
INFO         | The active JSON Web Key Sets can be found here: /run/user/1001/avd/running/3055/jwks/b7a29cf6-c647-4156-bab8-deb196e1b1bf/active.jwk
INFO         | Scanning /run/user/1001/avd/running/3055/jwks/b7a29cf6-c647-4156-bab8-deb196e1b1bf for jwk keys.
INFO         | Started GRPC server at 127.0.0.1:8554, security: Local, auth: +token
INFO         | Advertising in: /run/user/1001/avd/running/pid_3055.ini
INFO         | Setting display: 0 configuration to: 1080x1920, dpi: 420x420 
INFO         | setDisplayActiveConfig: id:0, 1080x1920
INFO         | emulatorSetupEnvironment: Environment scene is not required
USER_INFO    | Emulator is performing a full startup. This may take upto two minutes, or more.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
INFO         | Boot completed in 26250 ms
INFO         | Increasing screen off timeout, logcat buffer size to 2M.
```
## Logcat (filtered)
```
10-28 12:40:59.978   506   547 W ActivityManager: Slow operation: 61ms so far, now at startProcess: starting to update pids map
10-28 12:40:59.978   506   547 W ActivityManager: Slow operation: 61ms so far, now at startProcess: done updating pids map
10-28 12:41:00.181   506   798 I ActivityManager: Finished processing BOOT_COMPLETED for u0
10-28 12:41:00.182   506   798 I ActivityManager: Killing 1996:com.android.printspooler/u0a87 (adj 985): empty #17
10-28 12:41:14.527   506   545 I ActivityManager: Killing 1293:com.android.cellbroadcastreceiver/u0a64 (adj 985): empty #17
10-28 12:41:17.661   506   537 I ActivityManager: Killing 1971:com.android.quicksearchbox/u0a102 (adj 985): empty #17
10-28 12:41:19.033  2421  2421 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 12:41:19.039  2421  2421 I AndroidRuntime: Using default boot image
10-28 12:41:19.039  2421  2421 I AndroidRuntime: Leaving lock profiling enabled
10-28 12:41:19.193  2421  2421 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 12:41:19.278  2421  2421 D AndroidRuntime: Shutting down VM
10-28 12:41:19.843  2073  2111 I BackroomsGL: world shader compiled
10-28 12:41:19.918  2073  2111 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 12:41:19.918  2073  2111 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 12:41:21.870  2073  2111 I Perf    : fps=1.9 worst=5 drawn=4/4 queue=2 build=27ms verts=10k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=4 noMesh=0 culled=0
10-28 12:41:22.633   506   537 I ActivityManager: Killing 2116:com.android.calendar/u0a107 (adj 985): empty #17
10-28 12:41:22.655   506  1255 I ActivityManager: Killing 1614:android.process.acore/u0a63 (adj 975): empty #17
10-28 12:41:24.048  2073  2111 I Perf    : fps=4.6 worst=5 drawn=11/23 queue=2 build=25ms verts=27k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=11 noMesh=0 culled=10
10-28 12:41:26.194  2073  2111 I Perf    : fps=4.7 worst=5 drawn=14/37 queue=0 build=51ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:41:28.376  2073  2111 I Perf    : fps=4.6 worst=4 drawn=14/37 queue=0 build=51ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:41:30.566  2073  2111 I Perf    : fps=4.6 worst=4 drawn=14/37 queue=0 build=51ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:41:32.697  2073  2111 I Perf    : fps=4.7 worst=4 drawn=14/37 queue=0 build=51ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:41:34.835  2073  2111 I Perf    : fps=4.7 worst=5 drawn=14/37 queue=0 build=51ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:41:36.953  2073  2111 I Perf    : fps=4.7 worst=4 drawn=14/37 queue=0 build=51ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:41:39.036  2073  2111 I Perf    : fps=4.8 worst=5 drawn=14/37 queue=0 build=51ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:41:41.207  2073  2111 I Perf    : fps=4.6 worst=5 drawn=14/37 queue=0 build=51ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:41:43.300  2073  2111 I Perf    : fps=4.3 worst=4 drawn=14/37 queue=0 build=51ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:41:45.458  2073  2111 I Perf    : fps=4.6 worst=5 drawn=14/37 queue=0 build=51ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:41:47.470  2073  2111 I Perf    : fps=4.5 worst=5 drawn=14/37 queue=0 build=51ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:41:49.528  2073  2111 I Perf    : fps=4.4 worst=4 drawn=14/37 queue=0 build=51ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:41:51.754  2470  2470 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 12:41:51.756  2470  2470 I AndroidRuntime: Using default boot image
10-28 12:41:51.756  2470  2470 I AndroidRuntime: Leaving lock profiling enabled
10-28 12:41:51.779  2073  2111 I Perf    : fps=4.4 worst=5 drawn=14/37 queue=0 build=51ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:41:52.095  2470  2470 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 12:41:52.701  2470  2470 D AndroidRuntime: Shutting down VM
10-28 12:41:53.999  2073  2111 I Perf    : fps=4.5 worst=4 drawn=16/37 queue=0 build=51ms verts=40k cam=0.5,1.6,0.5 yaw=2.78 pitch=0.00 torch=1 ready=true drawn=16 noMesh=0 culled=21
10-28 12:41:56.069  2073  2111 I Perf    : fps=4.3 worst=4 drawn=16/37 queue=0 build=51ms verts=40k cam=0.5,1.6,0.5 yaw=2.78 pitch=0.00 torch=1 ready=true drawn=16 noMesh=0 culled=21
10-28 12:41:58.057  2486  2486 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 12:41:58.074  2486  2486 I AndroidRuntime: Using default boot image
10-28 12:41:58.074  2486  2486 I AndroidRuntime: Leaving lock profiling enabled
10-28 12:41:58.189  2073  2111 I Perf    : fps=4.2 worst=4 drawn=16/37 queue=0 build=51ms verts=40k cam=0.5,1.6,0.5 yaw=2.78 pitch=0.00 torch=1 ready=true drawn=16 noMesh=0 culled=21
10-28 12:41:58.267  2486  2486 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 12:41:58.283  2486  2486 D AndroidRuntime: Shutting down VM
10-28 12:41:59.522   506   547 I ActivityManager: Start proc 2500:android.process.acore/u0a63 for content provider {com.android.providers.contacts/com.android.providers.contacts.VoicemailContentProvider}
10-28 12:41:59.572  2500  2500 I ContactsPerf: VoicemailContentProvider.onCreate start
10-28 12:41:59.574  2500  2500 I ContactsPerf: VoicemailContentProvider.onCreate finish
10-28 12:42:00.435  2073  2111 I Perf    : fps=4.5 worst=4 drawn=16/37 queue=0 build=51ms verts=40k cam=0.5,1.6,0.5 yaw=2.78 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 12:42:02.505  2073  2111 I Perf    : fps=4.3 worst=4 drawn=16/37 queue=0 build=51ms verts=40k cam=0.5,1.6,0.5 yaw=2.78 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 12:42:03.145  2531  2531 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 12:42:03.175  2531  2531 I AndroidRuntime: Using default boot image
10-28 12:42:03.176  2531  2531 I AndroidRuntime: Leaving lock profiling enabled
10-28 12:42:03.395  2531  2531 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 12:42:04.620  2073  2111 I Perf    : fps=4.3 worst=4 drawn=16/37 queue=0 build=51ms verts=40k cam=0.4,1.7,0.9 yaw=2.78 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 12:42:04.920  2531  2531 D AndroidRuntime: Shutting down VM
10-28 12:42:06.767  2073  2111 I Perf    : fps=4.7 worst=5 drawn=16/37 queue=0 build=51ms verts=40k cam=0.1,1.6,1.5 yaw=2.78 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 12:42:08.892  2073  2111 I Perf    : fps=4.7 worst=5 drawn=16/37 queue=0 build=51ms verts=40k cam=0.1,1.6,1.5 yaw=2.78 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 12:42:10.949  2073  2111 I Perf    : fps=4.4 worst=4 drawn=16/37 queue=0 build=51ms verts=40k cam=0.1,1.6,1.5 yaw=2.78 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 12:42:13.119  2073  2111 I Perf    : fps=4.6 worst=4 drawn=16/37 queue=0 build=51ms verts=40k cam=0.1,1.6,1.5 yaw=2.78 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 12:42:15.341  2073  2111 I Perf    : fps=4.5 worst=4 drawn=16/37 queue=0 build=51ms verts=40k cam=0.1,1.6,1.5 yaw=2.78 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
```
## Step log
```
=5 drawn=11/23 queue=2 build=25ms verts=27k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=11 noMesh=0 culled=10
10-28 12:41:26.194  2073  2111 I Perf    : fps=4.7 worst=5 drawn=14/37 queue=0 build=51ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:41:28.376  2073  2111 I Perf    : fps=4.6 worst=4 drawn=14/37 queue=0 build=51ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:41:30.566  2073  2111 I Perf    : fps=4.6 worst=4 drawn=14/37 queue=0 build=51ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:41:32.697  2073  2111 I Perf    : fps=4.7 worst=4 drawn=14/37 queue=0 build=51ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:41:34.835  2073  2111 I Perf    : fps=4.7 worst=5 drawn=14/37 queue=0 build=51ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:41:36.953  2073  2111 I Perf    : fps=4.7 worst=4 drawn=14/37 queue=0 build=51ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:41:39.036  2073  2111 I Perf    : fps=4.8 worst=5 drawn=14/37 queue=0 build=51ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:41:41.207  2073  2111 I Perf    : fps=4.6 worst=5 drawn=14/37 queue=0 build=51ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:41:43.300  2073  2111 I Perf    : fps=4.3 worst=4 drawn=14/37 queue=0 build=51ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:41:45.231  2446  2446 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 12:41:45.458  2073  2111 I Perf    : fps=4.6 worst=5 drawn=14/37 queue=0 build=51ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:41:47.470  2073  2111 I Perf    : fps=4.5 worst=5 drawn=14/37 queue=0 build=51ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:41:49.528  2073  2111 I Perf    : fps=4.4 worst=4 drawn=14/37 queue=0 build=51ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:41:51.754  2470  2470 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 12:41:51.756  2470  2470 I AndroidRuntime: Using default boot image
10-28 12:41:51.756  2470  2470 I AndroidRuntime: Leaving lock profiling enabled
10-28 12:41:51.779  2073  2111 I Perf    : fps=4.4 worst=5 drawn=14/37 queue=0 build=51ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:41:52.095  2470  2470 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 12:41:52.701  2470  2470 D AndroidRuntime: Shutting down VM
10-28 12:41:53.999  2073  2111 I Perf    : fps=4.5 worst=4 drawn=16/37 queue=0 build=51ms verts=40k cam=0.5,1.6,0.5 yaw=2.78 pitch=0.00 torch=1 ready=true drawn=16 noMesh=0 culled=21
10-28 12:41:56.069  2073  2111 I Perf    : fps=4.3 worst=4 drawn=16/37 queue=0 build=51ms verts=40k cam=0.5,1.6,0.5 yaw=2.78 pitch=0.00 torch=1 ready=true drawn=16 noMesh=0 culled=21
10-28 12:41:58.057  2486  2486 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 12:41:58.074  2486  2486 I AndroidRuntime: Using default boot image
10-28 12:41:58.074  2486  2486 I AndroidRuntime: Leaving lock profiling enabled
10-28 12:41:58.189  2073  2111 I Perf    : fps=4.2 worst=4 drawn=16/37 queue=0 build=51ms verts=40k cam=0.5,1.6,0.5 yaw=2.78 pitch=0.00 torch=1 ready=true drawn=16 noMesh=0 culled=21
10-28 12:41:58.267  2486  2486 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 12:41:58.283  2486  2486 D AndroidRuntime: Shutting down VM
10-28 12:41:59.572  2500  2500 I ContactsPerf: VoicemailContentProvider.onCreate start
10-28 12:41:59.574  2500  2500 I ContactsPerf: VoicemailContentProvider.onCreate finish
10-28 12:42:00.435  2073  2111 I Perf    : fps=4.5 worst=4 drawn=16/37 queue=0 build=51ms verts=40k cam=0.5,1.6,0.5 yaw=2.78 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 12:42:02.505  2073  2111 I Perf    : fps=4.3 worst=4 drawn=16/37 queue=0 build=51ms verts=40k cam=0.5,1.6,0.5 yaw=2.78 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 12:42:03.145  2531  2531 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 12:42:03.175  2531  2531 I AndroidRuntime: Using default boot image
10-28 12:42:03.176  2531  2531 I AndroidRuntime: Leaving lock profiling enabled
10-28 12:42:03.395  2531  2531 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 12:42:04.620  2073  2111 I Perf    : fps=4.3 worst=4 drawn=16/37 queue=0 build=51ms verts=40k cam=0.4,1.7,0.9 yaw=2.78 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 12:42:04.920  2531  2531 D AndroidRuntime: Shutting down VM
10-28 12:42:06.767  2073  2111 I Perf    : fps=4.7 worst=5 drawn=16/37 queue=0 build=51ms verts=40k cam=0.1,1.6,1.5 yaw=2.78 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 12:42:08.892  2073  2111 I Perf    : fps=4.7 worst=5 drawn=16/37 queue=0 build=51ms verts=40k cam=0.1,1.6,1.5 yaw=2.78 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 12:42:10.070  2549  2549 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 12:42:10.949  2073  2111 I Perf    : fps=4.4 worst=4 drawn=16/37 queue=0 build=51ms verts=40k cam=0.1,1.6,1.5 yaw=2.78 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 12:42:13.119  2073  2111 I Perf    : fps=4.6 worst=4 drawn=16/37 queue=0 build=51ms verts=40k cam=0.1,1.6,1.5 yaw=2.78 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 12:42:15.341  2073  2111 I Perf    : fps=4.5 worst=4 drawn=16/37 queue=0 build=51ms verts=40k cam=0.1,1.6,1.5 yaw=2.78 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
+ '[' -z 2073 ']'
+ grep -qa 'FATAL EXCEPTION' emulator-shots/logcat.txt
+ '[' 0 '!=' 0 ']'
+ echo 'OK: the app is alive, rendering, and the touch controls work'
OK: the app is alive, rendering, and the touch controls work
```
