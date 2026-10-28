# Emulator smoke test diagnostics

Commit: 5b941b0bab42a8c03c6192e694751428f29e8e24
Outcome: success

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 28 12:21 /dev/kvm
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
INFO         | The active JSON Web Key Sets can be found here: /run/user/1001/avd/running/2747/jwks/4aafd303-73b7-4967-bff8-08b93e9cee33/active.jwk
INFO         | Scanning /run/user/1001/avd/running/2747/jwks/4aafd303-73b7-4967-bff8-08b93e9cee33 for jwk keys.
INFO         | Started GRPC server at 127.0.0.1:8554, security: Local, auth: +token
INFO         | Advertising in: /run/user/1001/avd/running/pid_2747.ini
INFO         | Setting display: 0 configuration to: 1080x1920, dpi: 420x420 
INFO         | setDisplayActiveConfig: id:0, 1080x1920
INFO         | emulatorSetupEnvironment: Environment scene is not required
USER_INFO    | Emulator is performing a full startup. This may take upto two minutes, or more.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
INFO         | Boot completed in 31705 ms
INFO         | Increasing screen off timeout, logcat buffer size to 2M.
```
## Logcat (filtered)
```
10-28 12:24:04.930   505   546 I ActivityManager: Start proc 2341:com.android.traceur/u0a90 for broadcast {com.android.traceur/com.android.traceur.Receiver}
10-28 12:24:05.176   505  1602 I ActivityManager: Finished processing BOOT_COMPLETED for u0
10-28 12:24:05.181   505  1602 I ActivityManager: Killing 1900:com.android.localtransport/1000 (adj 985): empty #17
10-28 12:24:07.224   420  2135 I iorapd  : Perfetto TraceBuffer saved to file: /data/misc/iorapd/com.backrooms.gdx.debug/1/com.backrooms.gdx.android.AndroidLauncher/raw_traces/1793190247223712968.perfetto_trace.pb
10-28 12:24:19.720   505   544 I ActivityManager: Killing 1285:com.android.cellbroadcastreceiver/u0a66 (adj 985): empty #17
10-28 12:24:23.170   505   536 I ActivityManager: Killing 1652:android.process.acore/u0a65 (adj 985): empty #17
10-28 12:24:23.171   505   536 I ActivityManager: Killing 1928:com.android.quicksearchbox/u0a103 (adj 985): empty #18
10-28 12:24:25.681  2394  2394 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 12:24:25.696  2394  2394 I AndroidRuntime: Using default boot image
10-28 12:24:25.697  2394  2394 I AndroidRuntime: Leaving lock profiling enabled
10-28 12:24:25.843  2394  2394 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 12:24:25.885  2394  2394 D AndroidRuntime: Shutting down VM
10-28 12:24:26.436  2139  2206 I BackroomsGL: world shader compiled
10-28 12:24:26.507  2139  2206 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 12:24:26.507  2139  2206 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 12:24:28.135   505   922 I ActivityManager: Killing 2003:com.android.calendar/u0a110 (adj 975): empty #17
10-28 12:24:28.579  2139  2206 I Perf    : fps=2.7 worst=11 drawn=4/7 queue=2 build=19ms verts=10k cam=-2.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=4 noMesh=0 culled=1
10-28 12:24:30.741  2139  2206 I Perf    : fps=4.2 worst=4 drawn=11/24 queue=2 build=12ms verts=29k cam=-2.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=11 noMesh=0 culled=11
10-28 12:24:32.938  2139  2206 I Perf    : fps=4.1 worst=4 drawn=14/37 queue=0 build=14ms verts=36k cam=-2.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:24:35.132  2139  2206 I Perf    : fps=4.1 worst=4 drawn=14/37 queue=0 build=14ms verts=36k cam=-2.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:24:37.295  2139  2206 I Perf    : fps=4.2 worst=4 drawn=14/37 queue=0 build=14ms verts=36k cam=-2.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:24:39.530  2139  2206 I Perf    : fps=4.0 worst=4 drawn=14/37 queue=0 build=14ms verts=36k cam=-2.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:24:41.561  2139  2206 I Perf    : fps=3.9 worst=4 drawn=14/37 queue=0 build=14ms verts=36k cam=-2.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:24:43.640  2139  2206 I Perf    : fps=4.3 worst=4 drawn=14/37 queue=0 build=14ms verts=36k cam=-2.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:24:45.742  2139  2206 I Perf    : fps=4.3 worst=4 drawn=14/37 queue=0 build=14ms verts=36k cam=-2.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:24:47.911  2139  2206 I Perf    : fps=4.1 worst=4 drawn=14/37 queue=0 build=14ms verts=36k cam=-2.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:24:50.083  2139  2206 I Perf    : fps=4.1 worst=4 drawn=14/37 queue=0 build=14ms verts=36k cam=-2.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:24:52.312  2139  2206 I Perf    : fps=4.0 worst=4 drawn=14/37 queue=0 build=14ms verts=36k cam=-2.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:24:54.317  2139  2206 I Perf    : fps=4.0 worst=4 drawn=14/37 queue=0 build=14ms verts=36k cam=-2.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:24:56.466  2139  2206 I Perf    : fps=3.7 worst=4 drawn=14/37 queue=0 build=14ms verts=36k cam=-2.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:24:58.618  2139  2206 I Perf    : fps=4.2 worst=4 drawn=14/37 queue=0 build=14ms verts=36k cam=-2.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:24:58.841  2444  2444 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 12:24:58.872  2444  2444 I AndroidRuntime: Using default boot image
10-28 12:24:58.872  2444  2444 I AndroidRuntime: Leaving lock profiling enabled
10-28 12:24:58.964  2444  2444 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 12:24:59.597  2444  2444 D AndroidRuntime: Shutting down VM
10-28 12:25:00.710  2139  2206 I Perf    : fps=4.3 worst=4 drawn=17/37 queue=0 build=14ms verts=44k cam=-2.5,1.6,3.5 yaw=2.18 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-28 12:25:02.848  2139  2206 I Perf    : fps=4.2 worst=5 drawn=17/37 queue=0 build=14ms verts=44k cam=-2.5,1.6,3.5 yaw=2.18 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-28 12:25:04.631  2460  2460 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 12:25:04.644  2460  2460 I AndroidRuntime: Using default boot image
10-28 12:25:04.644  2460  2460 I AndroidRuntime: Leaving lock profiling enabled
10-28 12:25:04.918  2460  2460 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 12:25:04.930  2460  2460 D AndroidRuntime: Shutting down VM
10-28 12:25:04.984  2139  2206 I Perf    : fps=4.2 worst=4 drawn=17/37 queue=0 build=14ms verts=44k cam=-2.5,1.6,3.5 yaw=2.18 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 12:25:05.876   505   546 I ActivityManager: Start proc 2474:android.process.acore/u0a65 for content provider {com.android.providers.contacts/com.android.providers.contacts.VoicemailContentProvider}
10-28 12:25:06.028  2474  2474 I ContactsPerf: VoicemailContentProvider.onCreate start
10-28 12:25:06.091  2474  2474 I ContactsPerf: VoicemailContentProvider.onCreate finish
10-28 12:25:07.127  2139  2206 I Perf    : fps=4.2 worst=4 drawn=17/37 queue=0 build=14ms verts=44k cam=-2.5,1.6,3.5 yaw=2.18 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 12:25:09.380  2139  2206 I Perf    : fps=4.0 worst=4 drawn=17/37 queue=0 build=14ms verts=44k cam=-2.5,1.6,3.5 yaw=2.18 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 12:25:10.259  2505  2505 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 12:25:10.263  2505  2505 I AndroidRuntime: Using default boot image
10-28 12:25:10.263  2505  2505 I AndroidRuntime: Leaving lock profiling enabled
10-28 12:25:10.451  2505  2505 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 12:25:11.543  2139  2206 I Perf    : fps=3.7 worst=4 drawn=17/37 queue=0 build=14ms verts=44k cam=-2.8,1.7,3.7 yaw=2.18 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 12:25:11.965  2505  2505 D AndroidRuntime: Shutting down VM
10-28 12:25:13.709  2139  2206 I Perf    : fps=3.7 worst=4 drawn=17/37 queue=0 build=14ms verts=44k cam=-3.4,1.6,4.1 yaw=2.18 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 12:25:15.847  2139  2206 I Perf    : fps=3.7 worst=4 drawn=17/37 queue=0 build=14ms verts=44k cam=-3.4,1.6,4.1 yaw=2.18 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 12:25:18.078  2139  2206 I Perf    : fps=3.6 worst=3 drawn=17/37 queue=0 build=14ms verts=44k cam=-3.4,1.6,4.1 yaw=2.18 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 12:25:20.346  2139  2206 I Perf    : fps=3.5 worst=3 drawn=17/37 queue=0 build=14ms verts=44k cam=-3.4,1.6,4.1 yaw=2.18 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 12:25:22.386  2139  2206 I Perf    : fps=3.4 worst=3 drawn=17/37 queue=0 build=14ms verts=44k cam=-3.4,1.6,4.1 yaw=2.18 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
```
## Step log
```
ild=12ms verts=29k cam=-2.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=11 noMesh=0 culled=11
10-28 12:24:32.938  2139  2206 I Perf    : fps=4.1 worst=4 drawn=14/37 queue=0 build=14ms verts=36k cam=-2.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:24:35.132  2139  2206 I Perf    : fps=4.1 worst=4 drawn=14/37 queue=0 build=14ms verts=36k cam=-2.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:24:37.295  2139  2206 I Perf    : fps=4.2 worst=4 drawn=14/37 queue=0 build=14ms verts=36k cam=-2.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:24:39.530  2139  2206 I Perf    : fps=4.0 worst=4 drawn=14/37 queue=0 build=14ms verts=36k cam=-2.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:24:41.561  2139  2206 I Perf    : fps=3.9 worst=4 drawn=14/37 queue=0 build=14ms verts=36k cam=-2.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:24:43.640  2139  2206 I Perf    : fps=4.3 worst=4 drawn=14/37 queue=0 build=14ms verts=36k cam=-2.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:24:45.742  2139  2206 I Perf    : fps=4.3 worst=4 drawn=14/37 queue=0 build=14ms verts=36k cam=-2.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:24:47.911  2139  2206 I Perf    : fps=4.1 worst=4 drawn=14/37 queue=0 build=14ms verts=36k cam=-2.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:24:50.083  2139  2206 I Perf    : fps=4.1 worst=4 drawn=14/37 queue=0 build=14ms verts=36k cam=-2.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:24:52.037  2420  2420 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 12:24:52.312  2139  2206 I Perf    : fps=4.0 worst=4 drawn=14/37 queue=0 build=14ms verts=36k cam=-2.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:24:54.317  2139  2206 I Perf    : fps=4.0 worst=4 drawn=14/37 queue=0 build=14ms verts=36k cam=-2.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:24:56.466  2139  2206 I Perf    : fps=3.7 worst=4 drawn=14/37 queue=0 build=14ms verts=36k cam=-2.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:24:58.618  2139  2206 I Perf    : fps=4.2 worst=4 drawn=14/37 queue=0 build=14ms verts=36k cam=-2.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:24:58.841  2444  2444 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 12:24:58.872  2444  2444 I AndroidRuntime: Using default boot image
10-28 12:24:58.872  2444  2444 I AndroidRuntime: Leaving lock profiling enabled
10-28 12:24:58.964  2444  2444 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 12:24:59.597  2444  2444 D AndroidRuntime: Shutting down VM
10-28 12:25:00.710  2139  2206 I Perf    : fps=4.3 worst=4 drawn=17/37 queue=0 build=14ms verts=44k cam=-2.5,1.6,3.5 yaw=2.18 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-28 12:25:02.848  2139  2206 I Perf    : fps=4.2 worst=5 drawn=17/37 queue=0 build=14ms verts=44k cam=-2.5,1.6,3.5 yaw=2.18 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-28 12:25:04.631  2460  2460 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 12:25:04.644  2460  2460 I AndroidRuntime: Using default boot image
10-28 12:25:04.644  2460  2460 I AndroidRuntime: Leaving lock profiling enabled
10-28 12:25:04.918  2460  2460 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 12:25:04.930  2460  2460 D AndroidRuntime: Shutting down VM
10-28 12:25:04.984  2139  2206 I Perf    : fps=4.2 worst=4 drawn=17/37 queue=0 build=14ms verts=44k cam=-2.5,1.6,3.5 yaw=2.18 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 12:25:06.028  2474  2474 I ContactsPerf: VoicemailContentProvider.onCreate start
10-28 12:25:06.091  2474  2474 I ContactsPerf: VoicemailContentProvider.onCreate finish
10-28 12:25:07.127  2139  2206 I Perf    : fps=4.2 worst=4 drawn=17/37 queue=0 build=14ms verts=44k cam=-2.5,1.6,3.5 yaw=2.18 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 12:25:09.380  2139  2206 I Perf    : fps=4.0 worst=4 drawn=17/37 queue=0 build=14ms verts=44k cam=-2.5,1.6,3.5 yaw=2.18 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 12:25:10.259  2505  2505 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 12:25:10.263  2505  2505 I AndroidRuntime: Using default boot image
10-28 12:25:10.263  2505  2505 I AndroidRuntime: Leaving lock profiling enabled
10-28 12:25:10.451  2505  2505 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 12:25:11.543  2139  2206 I Perf    : fps=3.7 worst=4 drawn=17/37 queue=0 build=14ms verts=44k cam=-2.8,1.7,3.7 yaw=2.18 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 12:25:11.965  2505  2505 D AndroidRuntime: Shutting down VM
10-28 12:25:13.709  2139  2206 I Perf    : fps=3.7 worst=4 drawn=17/37 queue=0 build=14ms verts=44k cam=-3.4,1.6,4.1 yaw=2.18 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 12:25:15.847  2139  2206 I Perf    : fps=3.7 worst=4 drawn=17/37 queue=0 build=14ms verts=44k cam=-3.4,1.6,4.1 yaw=2.18 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 12:25:17.446  2523  2523 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 12:25:18.078  2139  2206 I Perf    : fps=3.6 worst=3 drawn=17/37 queue=0 build=14ms verts=44k cam=-3.4,1.6,4.1 yaw=2.18 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 12:25:20.346  2139  2206 I Perf    : fps=3.5 worst=3 drawn=17/37 queue=0 build=14ms verts=44k cam=-3.4,1.6,4.1 yaw=2.18 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 12:25:22.386  2139  2206 I Perf    : fps=3.4 worst=3 drawn=17/37 queue=0 build=14ms verts=44k cam=-3.4,1.6,4.1 yaw=2.18 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
+ '[' -z 2139 ']'
+ grep -qa 'FATAL EXCEPTION' emulator-shots/logcat.txt
+ '[' 0 '!=' 0 ']'
+ echo 'OK: the app is alive, rendering, and the touch controls work'
OK: the app is alive, rendering, and the touch controls work
```
