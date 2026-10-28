# Emulator smoke test diagnostics

Commit: 079b2c7e3b31b5667c3b4db7fa8fd4001ddedcd9
Outcome: success

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 28 21:55 /dev/kvm
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
INFO         | The active JSON Web Key Sets can be found here: /run/user/1001/avd/running/3035/jwks/c2ebf6ab-72f2-4d3d-b18e-8f231f68b73e/active.jwk
INFO         | Scanning /run/user/1001/avd/running/3035/jwks/c2ebf6ab-72f2-4d3d-b18e-8f231f68b73e for jwk keys.
INFO         | Started GRPC server at 127.0.0.1:8554, security: Local, auth: +token
INFO         | Advertising in: /run/user/1001/avd/running/pid_3035.ini
INFO         | Setting display: 0 configuration to: 1080x1920, dpi: 420x420 
INFO         | setDisplayActiveConfig: id:0, 1080x1920
INFO         | emulatorSetupEnvironment: Environment scene is not required
USER_INFO    | Emulator is performing a full startup. This may take upto two minutes, or more.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
INFO         | Boot completed in 26661 ms
INFO         | Increasing screen off timeout, logcat buffer size to 2M.
```
## Logcat (filtered)
```
10-28 22:05:20.435   508   548 I ActivityManager: Start proc 2382:com.android.traceur/u0a89 for broadcast {com.android.traceur/com.android.traceur.Receiver}
10-28 22:05:20.623   508  1038 I ActivityManager: Finished processing BOOT_COMPLETED for u0
10-28 22:05:20.628   508  1038 I ActivityManager: Killing 1714:com.android.externalstorage/u0a68 (adj 985): empty #17
10-28 22:05:21.668   421  2072 I iorapd  : Perfetto TraceBuffer saved to file: /data/misc/iorapd/com.backrooms.gdx.debug/1/com.backrooms.gdx.android.AndroidLauncher/raw_traces/1793225121668204818.perfetto_trace.pb
10-28 22:05:35.152   508   546 I ActivityManager: Killing 1289:com.android.cellbroadcastreceiver/u0a66 (adj 985): empty #17
10-28 22:05:37.616   508   538 I ActivityManager: Killing 1683:android.process.acore/u0a64 (adj 985): empty #17
10-28 22:05:37.617   508   538 I ActivityManager: Killing 1936:com.android.quicksearchbox/u0a101 (adj 985): empty #18
10-28 22:05:40.223  2439  2439 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 22:05:40.231  2439  2439 I AndroidRuntime: Using default boot image
10-28 22:05:40.231  2439  2439 I AndroidRuntime: Leaving lock profiling enabled
10-28 22:05:40.444  2439  2439 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 22:05:40.534  2439  2439 D AndroidRuntime: Shutting down VM
10-28 22:05:41.111  2077  2135 I BackroomsGL: world shader compiled
10-28 22:05:41.579  2077  2135 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 22:05:41.579  2077  2135 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 22:05:42.651   508   535 I ActivityManager: Killing 2046:com.android.calendar/u0a109 (adj 975): empty #17
10-28 22:05:43.151  2077  2135 I Perf    : fps=1.4 worst=1 drawn=1/3 queue=2 build=28ms verts=2k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=1 noMesh=0 culled=0
10-28 22:05:45.194  2077  2135 I Perf    : fps=4.4 worst=5 drawn=14/21 queue=2 build=21ms verts=36k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=5
10-28 22:05:47.330  2077  2135 I Perf    : fps=4.2 worst=4 drawn=18/34 queue=2 build=31ms verts=46k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=14
10-28 22:05:49.502  2077  2135 I Perf    : fps=4.1 worst=4 drawn=18/37 queue=0 build=197ms verts=46k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 22:05:51.606  2077  2135 I Perf    : fps=4.3 worst=4 drawn=18/37 queue=0 build=197ms verts=46k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 22:05:53.772  2077  2135 I Perf    : fps=4.2 worst=4 drawn=18/37 queue=0 build=197ms verts=46k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 22:05:55.869  2077  2135 I Perf    : fps=4.3 worst=4 drawn=18/37 queue=0 build=197ms verts=46k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 22:05:57.974  2077  2135 I Perf    : fps=4.3 worst=4 drawn=18/37 queue=0 build=197ms verts=46k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 22:06:00.010  2077  2135 I Perf    : fps=4.4 worst=4 drawn=18/37 queue=0 build=197ms verts=46k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 22:06:02.149  2077  2135 I Perf    : fps=4.2 worst=4 drawn=18/37 queue=0 build=197ms verts=46k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 22:06:04.269  2077  2135 I Perf    : fps=4.2 worst=4 drawn=18/37 queue=0 build=197ms verts=46k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 22:06:06.352  2077  2135 I Perf    : fps=4.3 worst=4 drawn=18/37 queue=0 build=197ms verts=46k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 22:06:08.548  2077  2135 I Perf    : fps=4.1 worst=4 drawn=18/37 queue=0 build=197ms verts=46k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 22:06:10.549  2077  2135 I Perf    : fps=4.0 worst=4 drawn=18/37 queue=0 build=197ms verts=46k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 22:06:12.567  2491  2491 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 22:06:12.579  2491  2491 I AndroidRuntime: Using default boot image
10-28 22:06:12.579  2491  2491 I AndroidRuntime: Leaving lock profiling enabled
10-28 22:06:12.673  2491  2491 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 22:06:12.806  2077  2135 I Perf    : fps=4.0 worst=4 drawn=18/37 queue=0 build=197ms verts=46k cam=1.5,1.6,-0.5 yaw=0.55 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 22:06:13.373  2491  2491 D AndroidRuntime: Shutting down VM
10-28 22:06:14.814  2077  2135 I Perf    : fps=4.0 worst=4 drawn=13/37 queue=0 build=197ms verts=33k cam=1.5,1.6,-0.5 yaw=2.83 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 22:06:16.953  2077  2135 I Perf    : fps=4.2 worst=4 drawn=13/37 queue=0 build=197ms verts=33k cam=1.5,1.6,-0.5 yaw=2.83 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 22:06:18.178  2507  2507 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 22:06:18.198  2507  2507 I AndroidRuntime: Using default boot image
10-28 22:06:18.198  2507  2507 I AndroidRuntime: Leaving lock profiling enabled
10-28 22:06:18.479  2507  2507 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 22:06:18.486  2507  2507 D AndroidRuntime: Shutting down VM
10-28 22:06:18.496  2507  2518 I AndroidRuntime: NOTE: attach of thread 'Binder:2507_1' failed
10-28 22:06:19.021  2077  2135 I Perf    : fps=4.4 worst=4 drawn=13/37 queue=0 build=197ms verts=33k cam=1.5,1.6,-0.5 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 22:06:19.772   508   548 I ActivityManager: Start proc 2521:android.process.acore/u0a64 for content provider {com.android.providers.contacts/com.android.providers.contacts.VoicemailContentProvider}
10-28 22:06:19.808  2521  2521 I ContactsPerf: VoicemailContentProvider.onCreate start
10-28 22:06:19.809  2521  2521 I ContactsPerf: VoicemailContentProvider.onCreate finish
10-28 22:06:21.187  2077  2135 I Perf    : fps=4.2 worst=4 drawn=13/37 queue=0 build=197ms verts=33k cam=1.5,1.6,-0.5 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 22:06:23.152  2549  2549 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 22:06:23.155  2549  2549 I AndroidRuntime: Using default boot image
10-28 22:06:23.155  2549  2549 I AndroidRuntime: Leaving lock profiling enabled
10-28 22:06:23.304  2549  2549 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 22:06:23.344  2077  2135 I Perf    : fps=4.2 worst=4 drawn=13/37 queue=0 build=197ms verts=33k cam=1.5,1.6,-0.5 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 22:06:24.957  2549  2549 D AndroidRuntime: Shutting down VM
10-28 22:06:25.470  2077  2135 I Perf    : fps=4.2 worst=4 drawn=15/41 queue=2 build=22ms verts=38k cam=1.2,1.6,0.4 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=15 noMesh=0 culled=25
10-28 22:06:27.659  2077  2135 I Perf    : fps=4.1 worst=4 drawn=18/44 queue=0 build=23ms verts=46k cam=1.2,1.6,0.4 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-28 22:06:29.738  2077  2135 I Perf    : fps=4.3 worst=4 drawn=18/44 queue=0 build=23ms verts=46k cam=1.2,1.6,0.4 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-28 22:06:31.970  2077  2135 I Perf    : fps=4.0 worst=4 drawn=18/44 queue=0 build=23ms verts=46k cam=1.2,1.6,0.4 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-28 22:06:34.160  2077  2135 I Perf    : fps=4.1 worst=4 drawn=18/44 queue=0 build=23ms verts=46k cam=1.2,1.6,0.4 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
```
## Step log
```
28 22:05:45.194  2077  2135 I Perf    : fps=4.4 worst=5 drawn=14/21 queue=2 build=21ms verts=36k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=5
10-28 22:05:47.330  2077  2135 I Perf    : fps=4.2 worst=4 drawn=18/34 queue=2 build=31ms verts=46k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=14
10-28 22:05:49.502  2077  2135 I Perf    : fps=4.1 worst=4 drawn=18/37 queue=0 build=197ms verts=46k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 22:05:51.606  2077  2135 I Perf    : fps=4.3 worst=4 drawn=18/37 queue=0 build=197ms verts=46k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 22:05:53.772  2077  2135 I Perf    : fps=4.2 worst=4 drawn=18/37 queue=0 build=197ms verts=46k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 22:05:55.869  2077  2135 I Perf    : fps=4.3 worst=4 drawn=18/37 queue=0 build=197ms verts=46k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 22:05:57.974  2077  2135 I Perf    : fps=4.3 worst=4 drawn=18/37 queue=0 build=197ms verts=46k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 22:06:00.010  2077  2135 I Perf    : fps=4.4 worst=4 drawn=18/37 queue=0 build=197ms verts=46k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 22:06:02.149  2077  2135 I Perf    : fps=4.2 worst=4 drawn=18/37 queue=0 build=197ms verts=46k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 22:06:04.269  2077  2135 I Perf    : fps=4.2 worst=4 drawn=18/37 queue=0 build=197ms verts=46k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 22:06:06.352  2077  2135 I Perf    : fps=4.3 worst=4 drawn=18/37 queue=0 build=197ms verts=46k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 22:06:06.420  2467  2467 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 22:06:08.548  2077  2135 I Perf    : fps=4.1 worst=4 drawn=18/37 queue=0 build=197ms verts=46k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 22:06:10.549  2077  2135 I Perf    : fps=4.0 worst=4 drawn=18/37 queue=0 build=197ms verts=46k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 22:06:12.567  2491  2491 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 22:06:12.579  2491  2491 I AndroidRuntime: Using default boot image
10-28 22:06:12.579  2491  2491 I AndroidRuntime: Leaving lock profiling enabled
10-28 22:06:12.673  2491  2491 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 22:06:12.806  2077  2135 I Perf    : fps=4.0 worst=4 drawn=18/37 queue=0 build=197ms verts=46k cam=1.5,1.6,-0.5 yaw=0.55 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 22:06:13.373  2491  2491 D AndroidRuntime: Shutting down VM
10-28 22:06:14.814  2077  2135 I Perf    : fps=4.0 worst=4 drawn=13/37 queue=0 build=197ms verts=33k cam=1.5,1.6,-0.5 yaw=2.83 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 22:06:16.953  2077  2135 I Perf    : fps=4.2 worst=4 drawn=13/37 queue=0 build=197ms verts=33k cam=1.5,1.6,-0.5 yaw=2.83 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 22:06:18.178  2507  2507 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 22:06:18.198  2507  2507 I AndroidRuntime: Using default boot image
10-28 22:06:18.198  2507  2507 I AndroidRuntime: Leaving lock profiling enabled
10-28 22:06:18.479  2507  2507 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 22:06:18.486  2507  2507 D AndroidRuntime: Shutting down VM
10-28 22:06:18.496  2507  2518 I AndroidRuntime: NOTE: attach of thread 'Binder:2507_1' failed
10-28 22:06:19.021  2077  2135 I Perf    : fps=4.4 worst=4 drawn=13/37 queue=0 build=197ms verts=33k cam=1.5,1.6,-0.5 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 22:06:19.808  2521  2521 I ContactsPerf: VoicemailContentProvider.onCreate start
10-28 22:06:19.809  2521  2521 I ContactsPerf: VoicemailContentProvider.onCreate finish
10-28 22:06:21.187  2077  2135 I Perf    : fps=4.2 worst=4 drawn=13/37 queue=0 build=197ms verts=33k cam=1.5,1.6,-0.5 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 22:06:23.152  2549  2549 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 22:06:23.155  2549  2549 I AndroidRuntime: Using default boot image
10-28 22:06:23.155  2549  2549 I AndroidRuntime: Leaving lock profiling enabled
10-28 22:06:23.304  2549  2549 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 22:06:23.344  2077  2135 I Perf    : fps=4.2 worst=4 drawn=13/37 queue=0 build=197ms verts=33k cam=1.5,1.6,-0.5 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 22:06:24.957  2549  2549 D AndroidRuntime: Shutting down VM
10-28 22:06:25.470  2077  2135 I Perf    : fps=4.2 worst=4 drawn=15/41 queue=2 build=22ms verts=38k cam=1.2,1.6,0.4 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=15 noMesh=0 culled=25
10-28 22:06:27.659  2077  2135 I Perf    : fps=4.1 worst=4 drawn=18/44 queue=0 build=23ms verts=46k cam=1.2,1.6,0.4 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-28 22:06:29.738  2077  2135 I Perf    : fps=4.3 worst=4 drawn=18/44 queue=0 build=23ms verts=46k cam=1.2,1.6,0.4 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-28 22:06:29.753  2567  2567 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 22:06:31.970  2077  2135 I Perf    : fps=4.0 worst=4 drawn=18/44 queue=0 build=23ms verts=46k cam=1.2,1.6,0.4 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-28 22:06:34.160  2077  2135 I Perf    : fps=4.1 worst=4 drawn=18/44 queue=0 build=23ms verts=46k cam=1.2,1.6,0.4 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
+ '[' -z 2077 ']'
+ grep -qa 'FATAL EXCEPTION' emulator-shots/logcat.txt
+ '[' 0 '!=' 0 ']'
+ echo 'OK: the app is alive, rendering, and the touch controls work'
OK: the app is alive, rendering, and the touch controls work
```
