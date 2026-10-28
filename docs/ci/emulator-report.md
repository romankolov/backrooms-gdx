# Emulator smoke test diagnostics

Commit: dd90c420a16e48b49f3df1676a15db04164b1b97
Outcome: success

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 28 21:27 /dev/kvm
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
INFO         | The active JSON Web Key Sets can be found here: /run/user/1001/avd/running/2901/jwks/071d5f8d-66e7-4271-a446-a121c5e1d15f/active.jwk
INFO         | Scanning /run/user/1001/avd/running/2901/jwks/071d5f8d-66e7-4271-a446-a121c5e1d15f for jwk keys.
INFO         | Started GRPC server at 127.0.0.1:8554, security: Local, auth: +token
INFO         | Advertising in: /run/user/1001/avd/running/pid_2901.ini
INFO         | Setting display: 0 configuration to: 1080x1920, dpi: 420x420 
INFO         | setDisplayActiveConfig: id:0, 1080x1920
INFO         | emulatorSetupEnvironment: Environment scene is not required
USER_INFO    | Emulator is performing a full startup. This may take upto two minutes, or more.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
INFO         | Boot completed in 34969 ms
INFO         | Increasing screen off timeout, logcat buffer size to 2M.
```
## Logcat (filtered)
```
10-28 21:30:13.693   506  2232 I ActivityManager: Killing 1711:com.android.provision/u0a116 (adj 985): empty #17
10-28 21:30:13.749   506   547 I ActivityManager: Start proc 2330:com.android.shell/2000 for broadcast {com.android.shell/com.android.shell.HeapDumpReceiver}
10-28 21:30:13.856   506   965 I ActivityManager: Killing 1869:com.android.localtransport/1000 (adj 985): empty #17
10-28 21:30:13.926   506   547 I ActivityManager: Start proc 2351:com.android.traceur/u0a89 for broadcast {com.android.traceur/com.android.traceur.Receiver}
10-28 21:30:14.014   506   965 I ActivityManager: Finished processing BOOT_COMPLETED for u0
10-28 21:30:14.016   506   965 I ActivityManager: Killing 1630:com.android.externalstorage/u0a68 (adj 985): empty #17
10-28 21:30:16.753   431  2204 I iorapd  : Perfetto TraceBuffer saved to file: /data/misc/iorapd/com.backrooms.gdx.debug/1/com.backrooms.gdx.android.AndroidLauncher/raw_traces/1793223016753526554.perfetto_trace.pb
10-28 21:30:28.759   506   545 I ActivityManager: Killing 1279:com.android.cellbroadcastreceiver/u0a65 (adj 985): empty #17
10-28 21:30:35.541  2434  2434 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 21:30:35.562  2434  2434 I AndroidRuntime: Using default boot image
10-28 21:30:35.562  2434  2434 I AndroidRuntime: Leaving lock profiling enabled
10-28 21:30:35.764  2434  2434 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 21:30:35.793  2434  2434 D AndroidRuntime: Shutting down VM
10-28 21:30:36.271   506   537 I ActivityManager: Killing 2016:com.android.calendar/u0a107 (adj 985): empty #17
10-28 21:30:36.272   506   537 I ActivityManager: Killing 1931:com.android.quicksearchbox/u0a103 (adj 985): empty #18
10-28 21:30:36.309   506  1180 I ActivityManager: Killing 2051:com.android.camera2/u0a110 (adj 975): empty #17
10-28 21:30:36.382  2209  2240 I BackroomsGL: world shader compiled
10-28 21:30:37.253  2209  2240 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 21:30:37.253  2209  2240 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 21:30:38.851  2209  2240 I Perf    : fps=1.2 worst=1 drawn=1/3 queue=2 build=29ms verts=2k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=1 noMesh=0 culled=0
10-28 21:30:41.017  2209  2240 I Perf    : fps=3.2 worst=3 drawn=8/14 queue=2 build=19ms verts=20k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=8 noMesh=0 culled=4
10-28 21:30:43.247  2209  2240 I Perf    : fps=3.1 worst=3 drawn=11/25 queue=2 build=41ms verts=28k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=11 noMesh=0 culled=12
10-28 21:30:45.473  2209  2240 I Perf    : fps=3.1 worst=3 drawn=14/37 queue=0 build=26ms verts=35k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:30:47.673  2209  2240 I Perf    : fps=3.2 worst=3 drawn=14/37 queue=0 build=26ms verts=35k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:30:49.945  2209  2240 I Perf    : fps=3.1 worst=3 drawn=14/37 queue=0 build=26ms verts=35k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:30:51.960  2209  2240 I Perf    : fps=3.0 worst=3 drawn=14/37 queue=0 build=26ms verts=35k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:30:54.143  2209  2240 I Perf    : fps=3.2 worst=3 drawn=14/37 queue=0 build=26ms verts=35k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:30:56.423  2209  2240 I Perf    : fps=3.1 worst=3 drawn=14/37 queue=0 build=26ms verts=35k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:30:58.621  2209  2240 I Perf    : fps=3.2 worst=3 drawn=14/37 queue=0 build=26ms verts=35k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:31:00.630  2209  2240 I Perf    : fps=3.0 worst=3 drawn=14/37 queue=0 build=26ms verts=35k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:31:02.949  2209  2240 I Perf    : fps=3.0 worst=3 drawn=14/37 queue=0 build=26ms verts=35k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:31:05.215  2209  2240 I Perf    : fps=3.1 worst=3 drawn=14/37 queue=0 build=26ms verts=35k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:31:07.239  2209  2240 I Perf    : fps=3.0 worst=3 drawn=14/37 queue=0 build=26ms verts=35k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:31:07.400  2484  2484 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 21:31:07.433  2484  2484 I AndroidRuntime: Using default boot image
10-28 21:31:07.433  2484  2484 I AndroidRuntime: Leaving lock profiling enabled
10-28 21:31:07.533  2484  2484 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 21:31:08.195  2484  2484 D AndroidRuntime: Shutting down VM
10-28 21:31:09.242  2209  2240 I Perf    : fps=3.0 worst=3 drawn=13/37 queue=0 build=26ms verts=33k cam=1.5,1.6,1.5 yaw=2.03 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 21:31:11.547  2209  2240 I Perf    : fps=3.0 worst=3 drawn=13/37 queue=0 build=26ms verts=33k cam=1.5,1.6,1.5 yaw=2.03 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 21:31:13.460  2500  2500 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 21:31:13.494  2500  2500 I AndroidRuntime: Using default boot image
10-28 21:31:13.494  2500  2500 I AndroidRuntime: Leaving lock profiling enabled
10-28 21:31:13.581  2500  2500 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 21:31:13.586  2500  2500 D AndroidRuntime: Shutting down VM
10-28 21:31:13.598  2500  2511 I AndroidRuntime: NOTE: attach of thread 'Binder:2500_1' failed
10-28 21:31:13.783  2209  2240 I Perf    : fps=3.1 worst=3 drawn=13/37 queue=0 build=26ms verts=33k cam=1.5,1.6,1.5 yaw=2.03 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 21:31:15.820  2209  2240 I Perf    : fps=2.9 worst=3 drawn=13/37 queue=0 build=26ms verts=33k cam=1.5,1.6,1.5 yaw=2.03 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 21:31:18.060  2209  2240 I Perf    : fps=3.1 worst=3 drawn=13/37 queue=0 build=26ms verts=33k cam=1.5,1.6,1.5 yaw=2.03 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 21:31:18.592  2516  2516 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 21:31:18.634  2516  2516 I AndroidRuntime: Using default boot image
10-28 21:31:18.634  2516  2516 I AndroidRuntime: Leaving lock profiling enabled
10-28 21:31:18.831  2516  2516 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 21:31:20.343  2516  2516 D AndroidRuntime: Shutting down VM
10-28 21:31:20.344  2209  2240 I Perf    : fps=3.1 worst=3 drawn=13/37 queue=0 build=26ms verts=33k cam=1.0,1.7,1.8 yaw=2.03 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 21:31:22.607  2209  2240 I Perf    : fps=3.1 worst=3 drawn=13/37 queue=0 build=26ms verts=33k cam=0.8,1.6,1.8 yaw=2.03 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 21:31:24.761  2209  2240 I Perf    : fps=3.2 worst=3 drawn=13/37 queue=0 build=26ms verts=33k cam=0.8,1.6,1.8 yaw=2.03 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 21:31:26.799  2209  2240 I Perf    : fps=3.0 worst=3 drawn=13/37 queue=0 build=26ms verts=33k cam=0.8,1.6,1.8 yaw=2.03 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 21:31:28.940  2209  2240 I Perf    : fps=3.3 worst=3 drawn=13/37 queue=0 build=26ms verts=33k cam=0.8,1.6,1.8 yaw=2.03 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 21:31:31.176  2209  2240 I Perf    : fps=3.1 worst=3 drawn=13/37 queue=0 build=26ms verts=33k cam=0.8,1.6,1.8 yaw=2.03 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
```
## Step log
```
940 jump=1609,937 stickZone=864
10-28 21:30:38.851  2209  2240 I Perf    : fps=1.2 worst=1 drawn=1/3 queue=2 build=29ms verts=2k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=1 noMesh=0 culled=0
10-28 21:30:41.017  2209  2240 I Perf    : fps=3.2 worst=3 drawn=8/14 queue=2 build=19ms verts=20k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=8 noMesh=0 culled=4
10-28 21:30:43.247  2209  2240 I Perf    : fps=3.1 worst=3 drawn=11/25 queue=2 build=41ms verts=28k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=11 noMesh=0 culled=12
10-28 21:30:45.473  2209  2240 I Perf    : fps=3.1 worst=3 drawn=14/37 queue=0 build=26ms verts=35k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:30:47.673  2209  2240 I Perf    : fps=3.2 worst=3 drawn=14/37 queue=0 build=26ms verts=35k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:30:49.945  2209  2240 I Perf    : fps=3.1 worst=3 drawn=14/37 queue=0 build=26ms verts=35k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:30:51.960  2209  2240 I Perf    : fps=3.0 worst=3 drawn=14/37 queue=0 build=26ms verts=35k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:30:54.143  2209  2240 I Perf    : fps=3.2 worst=3 drawn=14/37 queue=0 build=26ms verts=35k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:30:56.423  2209  2240 I Perf    : fps=3.1 worst=3 drawn=14/37 queue=0 build=26ms verts=35k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:30:58.621  2209  2240 I Perf    : fps=3.2 worst=3 drawn=14/37 queue=0 build=26ms verts=35k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:31:00.630  2209  2240 I Perf    : fps=3.0 worst=3 drawn=14/37 queue=0 build=26ms verts=35k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:31:01.917  2467  2467 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 21:31:02.949  2209  2240 I Perf    : fps=3.0 worst=3 drawn=14/37 queue=0 build=26ms verts=35k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:31:05.215  2209  2240 I Perf    : fps=3.1 worst=3 drawn=14/37 queue=0 build=26ms verts=35k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:31:07.239  2209  2240 I Perf    : fps=3.0 worst=3 drawn=14/37 queue=0 build=26ms verts=35k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:31:07.400  2484  2484 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 21:31:07.433  2484  2484 I AndroidRuntime: Using default boot image
10-28 21:31:07.433  2484  2484 I AndroidRuntime: Leaving lock profiling enabled
10-28 21:31:07.533  2484  2484 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 21:31:08.195  2484  2484 D AndroidRuntime: Shutting down VM
10-28 21:31:09.242  2209  2240 I Perf    : fps=3.0 worst=3 drawn=13/37 queue=0 build=26ms verts=33k cam=1.5,1.6,1.5 yaw=2.03 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 21:31:11.547  2209  2240 I Perf    : fps=3.0 worst=3 drawn=13/37 queue=0 build=26ms verts=33k cam=1.5,1.6,1.5 yaw=2.03 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 21:31:13.460  2500  2500 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 21:31:13.494  2500  2500 I AndroidRuntime: Using default boot image
10-28 21:31:13.494  2500  2500 I AndroidRuntime: Leaving lock profiling enabled
10-28 21:31:13.581  2500  2500 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 21:31:13.586  2500  2500 D AndroidRuntime: Shutting down VM
10-28 21:31:13.598  2500  2511 I AndroidRuntime: NOTE: attach of thread 'Binder:2500_1' failed
10-28 21:31:13.783  2209  2240 I Perf    : fps=3.1 worst=3 drawn=13/37 queue=0 build=26ms verts=33k cam=1.5,1.6,1.5 yaw=2.03 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 21:31:15.820  2209  2240 I Perf    : fps=2.9 worst=3 drawn=13/37 queue=0 build=26ms verts=33k cam=1.5,1.6,1.5 yaw=2.03 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 21:31:18.060  2209  2240 I Perf    : fps=3.1 worst=3 drawn=13/37 queue=0 build=26ms verts=33k cam=1.5,1.6,1.5 yaw=2.03 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 21:31:18.592  2516  2516 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 21:31:18.634  2516  2516 I AndroidRuntime: Using default boot image
10-28 21:31:18.634  2516  2516 I AndroidRuntime: Leaving lock profiling enabled
10-28 21:31:18.831  2516  2516 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 21:31:20.343  2516  2516 D AndroidRuntime: Shutting down VM
10-28 21:31:20.344  2209  2240 I Perf    : fps=3.1 worst=3 drawn=13/37 queue=0 build=26ms verts=33k cam=1.0,1.7,1.8 yaw=2.03 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 21:31:22.607  2209  2240 I Perf    : fps=3.1 worst=3 drawn=13/37 queue=0 build=26ms verts=33k cam=0.8,1.6,1.8 yaw=2.03 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 21:31:24.761  2209  2240 I Perf    : fps=3.2 worst=3 drawn=13/37 queue=0 build=26ms verts=33k cam=0.8,1.6,1.8 yaw=2.03 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 21:31:26.450  2534  2534 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 21:31:26.799  2209  2240 I Perf    : fps=3.0 worst=3 drawn=13/37 queue=0 build=26ms verts=33k cam=0.8,1.6,1.8 yaw=2.03 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 21:31:28.940  2209  2240 I Perf    : fps=3.3 worst=3 drawn=13/37 queue=0 build=26ms verts=33k cam=0.8,1.6,1.8 yaw=2.03 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 21:31:31.176  2209  2240 I Perf    : fps=3.1 worst=3 drawn=13/37 queue=0 build=26ms verts=33k cam=0.8,1.6,1.8 yaw=2.03 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
+ '[' -z 2209 ']'
+ grep -qa 'FATAL EXCEPTION' emulator-shots/logcat.txt
+ '[' 0 '!=' 0 ']'
+ echo 'OK: the app is alive, rendering, and the touch controls work'
OK: the app is alive, rendering, and the touch controls work
```
