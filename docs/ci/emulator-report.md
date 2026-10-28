# Emulator smoke test diagnostics

Commit: c349f0575aa1cacd02ce789873f5338023ab69f9
Outcome: success

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 28 08:00 /dev/kvm
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
INFO         | The active JSON Web Key Sets can be found here: /run/user/1001/avd/running/2758/jwks/4522f220-8b89-405f-97d5-32d73e512f06/active.jwk
INFO         | Scanning /run/user/1001/avd/running/2758/jwks/4522f220-8b89-405f-97d5-32d73e512f06 for jwk keys.
INFO         | Started GRPC server at 127.0.0.1:8554, security: Local, auth: +token
INFO         | Advertising in: /run/user/1001/avd/running/pid_2758.ini
INFO         | Setting display: 0 configuration to: 1080x1920, dpi: 420x420 
INFO         | setDisplayActiveConfig: id:0, 1080x1920
INFO         | emulatorSetupEnvironment: Environment scene is not required
USER_INFO    | Emulator is performing a full startup. This may take upto two minutes, or more.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
INFO         | Boot completed in 26008 ms
INFO         | Increasing screen off timeout, logcat buffer size to 2M.
```
## Logcat (filtered)
```
10-28 08:04:40.376   510  1688 I ActivityManager: Killing 1829:com.android.printspooler/u0a87 (adj 985): empty #17
10-28 08:04:40.411   510   550 I ActivityManager: Start proc 2322:com.android.traceur/u0a89 for broadcast {com.android.traceur/com.android.traceur.Receiver}
10-28 08:04:40.818   510  1031 I ActivityManager: Finished processing BOOT_COMPLETED for u0
10-28 08:04:40.818   510  1031 I ActivityManager: Killing 1924:com.android.localtransport/1000 (adj 985): empty #17
10-28 08:04:43.999   433  2191 I iorapd  : Perfetto TraceBuffer saved to file: /data/misc/iorapd/com.backrooms.gdx.debug/1/com.backrooms.gdx.android.AndroidLauncher/raw_traces/1793174683998874615.perfetto_trace.pb
10-28 08:05:00.648   510   540 I ActivityManager: Killing 1420:android.process.acore/u0a63 (adj 985): empty #17
10-28 08:05:00.648   510   540 I ActivityManager: Killing 1865:com.android.quicksearchbox/u0a101 (adj 985): empty #18
10-28 08:05:00.649   510   540 I ActivityManager: Killing 1292:com.android.cellbroadcastreceiver/u0a65 (adj 995): empty #19
10-28 08:05:01.487  2394  2394 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:05:01.490  2394  2394 I AndroidRuntime: Using default boot image
10-28 08:05:01.490  2394  2394 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:05:01.609  2394  2394 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:05:01.636  2394  2394 D AndroidRuntime: Shutting down VM
10-28 08:05:02.149  2194  2271 I BackroomsGL: world shader compiled
10-28 08:05:02.202  2194  2271 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 08:05:02.202  2194  2271 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 08:05:04.167  2194  2271 I Perf    : fps=6.2 worst=50 drawn=14/21 queue=2 build=24ms verts=36k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=5
10-28 08:05:05.689   510  1140 I ActivityManager: Killing 1975:com.android.calendar/u0a108 (adj 975): empty #17
10-28 08:05:06.267  2194  2271 I Perf    : fps=7.1 worst=7 drawn=18/37 queue=0 build=14ms verts=47k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:05:08.270  2194  2271 I Perf    : fps=7.5 worst=8 drawn=18/37 queue=0 build=14ms verts=47k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:05:10.330  2194  2271 I Perf    : fps=7.3 worst=8 drawn=18/37 queue=0 build=14ms verts=47k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:05:12.443  2194  2271 I Perf    : fps=7.6 worst=8 drawn=18/37 queue=0 build=14ms verts=47k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:05:14.494  2194  2271 I Perf    : fps=7.3 worst=8 drawn=18/37 queue=0 build=14ms verts=47k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:05:16.554  2194  2271 I Perf    : fps=7.8 worst=8 drawn=18/37 queue=0 build=14ms verts=47k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:05:18.557  2194  2271 I Perf    : fps=7.5 worst=8 drawn=18/37 queue=0 build=14ms verts=47k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:05:20.670  2194  2271 I Perf    : fps=7.6 worst=8 drawn=18/37 queue=0 build=14ms verts=47k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:05:22.788  2194  2271 I Perf    : fps=7.6 worst=8 drawn=18/37 queue=0 build=14ms verts=47k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:05:24.890  2194  2271 I Perf    : fps=7.6 worst=8 drawn=18/37 queue=0 build=14ms verts=47k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:05:26.944  2194  2271 I Perf    : fps=7.3 worst=7 drawn=18/37 queue=0 build=14ms verts=47k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:05:29.049  2194  2271 I Perf    : fps=7.2 worst=7 drawn=18/37 queue=0 build=14ms verts=47k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:05:31.166  2194  2271 I Perf    : fps=7.1 worst=7 drawn=18/37 queue=0 build=14ms verts=47k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:05:31.755  2432  2432 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:05:31.766  2432  2432 I AndroidRuntime: Using default boot image
10-28 08:05:31.767  2432  2432 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:05:31.819  2432  2432 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:05:32.512  2432  2432 D AndroidRuntime: Shutting down VM
10-28 08:05:33.277  2194  2271 I Perf    : fps=7.1 worst=8 drawn=16/37 queue=0 build=14ms verts=41k cam=-0.5,1.6,-0.5 yaw=2.39 pitch=0.00 torch=1 ready=true drawn=16 noMesh=0 culled=21
10-28 08:05:35.353  2194  2271 I Perf    : fps=7.7 worst=8 drawn=16/37 queue=0 build=14ms verts=41k cam=-0.5,1.6,-0.5 yaw=2.39 pitch=0.00 torch=1 ready=true drawn=16 noMesh=0 culled=21
10-28 08:05:37.142  2459  2459 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:05:37.144  2459  2459 I AndroidRuntime: Using default boot image
10-28 08:05:37.144  2459  2459 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:05:37.309  2459  2459 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:05:37.394  2459  2459 D AndroidRuntime: Shutting down VM
10-28 08:05:37.395  2194  2271 I Perf    : fps=7.3 worst=8 drawn=16/37 queue=0 build=14ms verts=41k cam=-0.5,1.6,-0.5 yaw=2.39 pitch=0.00 torch=1 ready=true drawn=16 noMesh=0 culled=21
10-28 08:05:39.498  2194  2271 I Perf    : fps=7.6 worst=8 drawn=16/37 queue=0 build=14ms verts=41k cam=-0.5,1.6,-0.5 yaw=2.39 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 08:05:41.535  2194  2271 I Perf    : fps=7.4 worst=8 drawn=16/37 queue=0 build=14ms verts=41k cam=-0.5,1.6,-0.5 yaw=2.39 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 08:05:42.266  2476  2476 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:05:42.277  2476  2476 I AndroidRuntime: Using default boot image
10-28 08:05:42.277  2476  2476 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:05:42.327  2476  2476 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:05:42.456   510   550 I ActivityManager: Start proc 2489:android.process.acore/u0a63 for content provider {com.android.providers.contacts/com.android.providers.contacts.VoicemailContentProvider}
10-28 08:05:42.606  2489  2489 I ContactsPerf: VoicemailContentProvider.onCreate start
10-28 08:05:42.608  2489  2489 I ContactsPerf: VoicemailContentProvider.onCreate finish
10-28 08:05:43.562  2194  2271 I Perf    : fps=7.4 worst=8 drawn=16/39 queue=2 build=11ms verts=41k cam=-1.1,1.6,0.1 yaw=2.39 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 08:05:43.840  2476  2476 D AndroidRuntime: Shutting down VM
10-28 08:05:45.621  2194  2271 I Perf    : fps=6.3 worst=6 drawn=21/44 queue=0 build=9ms verts=54k cam=-1.6,1.6,0.7 yaw=2.39 pitch=0.00 torch=0 ready=true drawn=21 noMesh=0 culled=23
10-28 08:05:47.627  2194  2271 I Perf    : fps=6.5 worst=7 drawn=21/44 queue=0 build=9ms verts=54k cam=-1.6,1.6,0.7 yaw=2.39 pitch=0.00 torch=0 ready=true drawn=21 noMesh=0 culled=23
10-28 08:05:49.672  2194  2271 I Perf    : fps=6.4 worst=7 drawn=21/44 queue=0 build=9ms verts=54k cam=-1.6,1.6,0.7 yaw=2.39 pitch=0.00 torch=0 ready=true drawn=21 noMesh=0 culled=23
10-28 08:05:51.706  2194  2271 I Perf    : fps=6.4 worst=7 drawn=21/44 queue=0 build=9ms verts=54k cam=-1.6,1.6,0.7 yaw=2.39 pitch=0.00 torch=0 ready=true drawn=21 noMesh=0 culled=23
10-28 08:05:53.741  2194  2271 I Perf    : fps=6.4 worst=7 drawn=21/44 queue=0 build=9ms verts=54k cam=-1.6,1.6,0.7 yaw=2.39 pitch=0.00 torch=0 ready=true drawn=21 noMesh=0 culled=23
```
## Step log
```
s=36k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=5
10-28 08:05:06.267  2194  2271 I Perf    : fps=7.1 worst=7 drawn=18/37 queue=0 build=14ms verts=47k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:05:08.270  2194  2271 I Perf    : fps=7.5 worst=8 drawn=18/37 queue=0 build=14ms verts=47k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:05:10.330  2194  2271 I Perf    : fps=7.3 worst=8 drawn=18/37 queue=0 build=14ms verts=47k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:05:12.443  2194  2271 I Perf    : fps=7.6 worst=8 drawn=18/37 queue=0 build=14ms verts=47k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:05:14.494  2194  2271 I Perf    : fps=7.3 worst=8 drawn=18/37 queue=0 build=14ms verts=47k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:05:16.554  2194  2271 I Perf    : fps=7.8 worst=8 drawn=18/37 queue=0 build=14ms verts=47k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:05:18.557  2194  2271 I Perf    : fps=7.5 worst=8 drawn=18/37 queue=0 build=14ms verts=47k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:05:20.670  2194  2271 I Perf    : fps=7.6 worst=8 drawn=18/37 queue=0 build=14ms verts=47k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:05:22.788  2194  2271 I Perf    : fps=7.6 worst=8 drawn=18/37 queue=0 build=14ms verts=47k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:05:24.890  2194  2271 I Perf    : fps=7.6 worst=8 drawn=18/37 queue=0 build=14ms verts=47k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:05:26.944  2194  2271 I Perf    : fps=7.3 worst=7 drawn=18/37 queue=0 build=14ms verts=47k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:05:27.371  2419  2419 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 08:05:29.049  2194  2271 I Perf    : fps=7.2 worst=7 drawn=18/37 queue=0 build=14ms verts=47k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:05:31.166  2194  2271 I Perf    : fps=7.1 worst=7 drawn=18/37 queue=0 build=14ms verts=47k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:05:31.755  2432  2432 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:05:31.766  2432  2432 I AndroidRuntime: Using default boot image
10-28 08:05:31.767  2432  2432 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:05:31.819  2432  2432 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:05:32.512  2432  2432 D AndroidRuntime: Shutting down VM
10-28 08:05:33.277  2194  2271 I Perf    : fps=7.1 worst=8 drawn=16/37 queue=0 build=14ms verts=41k cam=-0.5,1.6,-0.5 yaw=2.39 pitch=0.00 torch=1 ready=true drawn=16 noMesh=0 culled=21
10-28 08:05:35.353  2194  2271 I Perf    : fps=7.7 worst=8 drawn=16/37 queue=0 build=14ms verts=41k cam=-0.5,1.6,-0.5 yaw=2.39 pitch=0.00 torch=1 ready=true drawn=16 noMesh=0 culled=21
10-28 08:05:37.142  2459  2459 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:05:37.144  2459  2459 I AndroidRuntime: Using default boot image
10-28 08:05:37.144  2459  2459 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:05:37.309  2459  2459 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:05:37.394  2459  2459 D AndroidRuntime: Shutting down VM
10-28 08:05:37.395  2194  2271 I Perf    : fps=7.3 worst=8 drawn=16/37 queue=0 build=14ms verts=41k cam=-0.5,1.6,-0.5 yaw=2.39 pitch=0.00 torch=1 ready=true drawn=16 noMesh=0 culled=21
10-28 08:05:39.498  2194  2271 I Perf    : fps=7.6 worst=8 drawn=16/37 queue=0 build=14ms verts=41k cam=-0.5,1.6,-0.5 yaw=2.39 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 08:05:41.535  2194  2271 I Perf    : fps=7.4 worst=8 drawn=16/37 queue=0 build=14ms verts=41k cam=-0.5,1.6,-0.5 yaw=2.39 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 08:05:42.266  2476  2476 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:05:42.277  2476  2476 I AndroidRuntime: Using default boot image
10-28 08:05:42.277  2476  2476 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:05:42.327  2476  2476 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:05:42.606  2489  2489 I ContactsPerf: VoicemailContentProvider.onCreate start
10-28 08:05:42.608  2489  2489 I ContactsPerf: VoicemailContentProvider.onCreate finish
10-28 08:05:43.562  2194  2271 I Perf    : fps=7.4 worst=8 drawn=16/39 queue=2 build=11ms verts=41k cam=-1.1,1.6,0.1 yaw=2.39 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 08:05:43.840  2476  2476 D AndroidRuntime: Shutting down VM
10-28 08:05:45.621  2194  2271 I Perf    : fps=6.3 worst=6 drawn=21/44 queue=0 build=9ms verts=54k cam=-1.6,1.6,0.7 yaw=2.39 pitch=0.00 torch=0 ready=true drawn=21 noMesh=0 culled=23
10-28 08:05:47.627  2194  2271 I Perf    : fps=6.5 worst=7 drawn=21/44 queue=0 build=9ms verts=54k cam=-1.6,1.6,0.7 yaw=2.39 pitch=0.00 torch=0 ready=true drawn=21 noMesh=0 culled=23
10-28 08:05:49.018  2524  2524 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 08:05:49.672  2194  2271 I Perf    : fps=6.4 worst=7 drawn=21/44 queue=0 build=9ms verts=54k cam=-1.6,1.6,0.7 yaw=2.39 pitch=0.00 torch=0 ready=true drawn=21 noMesh=0 culled=23
10-28 08:05:51.706  2194  2271 I Perf    : fps=6.4 worst=7 drawn=21/44 queue=0 build=9ms verts=54k cam=-1.6,1.6,0.7 yaw=2.39 pitch=0.00 torch=0 ready=true drawn=21 noMesh=0 culled=23
10-28 08:05:53.741  2194  2271 I Perf    : fps=6.4 worst=7 drawn=21/44 queue=0 build=9ms verts=54k cam=-1.6,1.6,0.7 yaw=2.39 pitch=0.00 torch=0 ready=true drawn=21 noMesh=0 culled=23
+ '[' -z 2194 ']'
+ grep -qa 'FATAL EXCEPTION' emulator-shots/logcat.txt
+ '[' 0 '!=' 0 ']'
+ echo 'OK: the app is alive, rendering, and the touch controls work'
OK: the app is alive, rendering, and the touch controls work
```
