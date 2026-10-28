# Emulator smoke test diagnostics

Commit: f6cef2bf7b31150e7922339a1f50891bfcfbaef7
Outcome: success

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 28 20:35 /dev/kvm
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
INFO         | The active JSON Web Key Sets can be found here: /run/user/1001/avd/running/2877/jwks/f8a73152-4aaf-4010-a0d6-668a9f479a5c/active.jwk
INFO         | Scanning /run/user/1001/avd/running/2877/jwks/f8a73152-4aaf-4010-a0d6-668a9f479a5c for jwk keys.
INFO         | Started GRPC server at 127.0.0.1:8554, security: Local, auth: +token
INFO         | Advertising in: /run/user/1001/avd/running/pid_2877.ini
INFO         | Setting display: 0 configuration to: 1080x1920, dpi: 420x420 
INFO         | setDisplayActiveConfig: id:0, 1080x1920
INFO         | emulatorSetupEnvironment: Environment scene is not required
USER_INFO    | Emulator is performing a full startup. This may take upto two minutes, or more.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
INFO         | Boot completed in 26040 ms
INFO         | Increasing screen off timeout, logcat buffer size to 2M.
```
## Logcat (filtered)
```
10-28 20:40:47.203   506   548 W ActivityManager: Slow operation: 57ms so far, now at startProcess: building log message
10-28 20:40:47.203   506   548 I ActivityManager: Start proc 2378:com.android.cellbroadcastreceiver.module/u0a129 for broadcast {com.android.cellbroadcastreceiver.module/com.android.cellbroadcastreceiver.CellBroadcastReceiver}
10-28 20:40:47.203   506   548 W ActivityManager: Slow operation: 57ms so far, now at startProcess: starting to update pids map
10-28 20:40:47.203   506   548 W ActivityManager: Slow operation: 57ms so far, now at startProcess: done updating pids map
10-28 20:40:47.321   506  2091 I ActivityManager: Killing 1941:com.android.localtransport/1000 (adj 985): empty #17
10-28 20:40:49.595   506  1076 I ActivityManager: Killing 1962:com.android.calendar/u0a108 (adj 975): empty #17
10-28 20:40:50.714   506   596 I ActivityManager: Killing 1288:com.android.cellbroadcastreceiver/u0a65 (adj 985): empty #17
10-28 20:40:56.419  2421  2421 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 20:40:56.437  2421  2421 I AndroidRuntime: Using default boot image
10-28 20:40:56.438  2421  2421 I AndroidRuntime: Leaving lock profiling enabled
10-28 20:40:56.567  2421  2421 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 20:40:56.596  2421  2421 D AndroidRuntime: Shutting down VM
10-28 20:40:57.149  2301  2324 I BackroomsGL: world shader compiled
10-28 20:40:57.581  2301  2324 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 20:40:57.581  2301  2324 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 20:40:59.293  2301  2324 I Perf    : fps=1.8 worst=4 drawn=3/4 queue=2 build=220ms verts=7k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=3 noMesh=0 culled=0
10-28 20:41:01.414  2301  2324 I Perf    : fps=4.8 worst=5 drawn=11/21 queue=2 build=21ms verts=28k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=11 noMesh=0 culled=8
10-28 20:41:03.499  2301  2324 I Perf    : fps=4.8 worst=5 drawn=14/37 queue=0 build=100ms verts=36k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:41:05.656  2301  2324 I Perf    : fps=4.6 worst=5 drawn=14/37 queue=0 build=100ms verts=36k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:41:07.695  2301  2324 I Perf    : fps=4.9 worst=5 drawn=14/37 queue=0 build=100ms verts=36k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:41:09.802  2301  2324 I Perf    : fps=4.7 worst=5 drawn=14/37 queue=0 build=100ms verts=36k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:41:11.833  2301  2324 I Perf    : fps=4.9 worst=5 drawn=14/37 queue=0 build=100ms verts=36k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:41:13.960  2301  2324 I Perf    : fps=4.7 worst=5 drawn=14/37 queue=0 build=100ms verts=36k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:41:16.051  2301  2324 I Perf    : fps=4.8 worst=5 drawn=14/37 queue=0 build=100ms verts=36k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:41:18.136  2301  2324 I Perf    : fps=4.8 worst=5 drawn=14/37 queue=0 build=100ms verts=36k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:41:20.240  2301  2324 I Perf    : fps=4.8 worst=5 drawn=14/37 queue=0 build=100ms verts=36k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:41:22.426  2301  2324 I Perf    : fps=4.6 worst=5 drawn=14/37 queue=0 build=100ms verts=36k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:41:24.570  2301  2324 I Perf    : fps=4.7 worst=5 drawn=14/37 queue=0 build=100ms verts=36k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:41:26.702  2301  2324 I Perf    : fps=4.7 worst=5 drawn=14/37 queue=0 build=100ms verts=36k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:41:28.769  2455  2455 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 20:41:28.784  2455  2455 I AndroidRuntime: Using default boot image
10-28 20:41:28.784  2455  2455 I AndroidRuntime: Leaving lock profiling enabled
10-28 20:41:28.852  2455  2455 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 20:41:28.889  2301  2324 I Perf    : fps=4.6 worst=4 drawn=14/37 queue=0 build=100ms verts=36k cam=0.5,1.6,0.5 yaw=0.05 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:41:29.250   506   506 I ActivityManager: Killing 1999:com.android.camera2/u0a110 (adj 985): empty #17
10-28 20:41:29.250   506   506 I ActivityManager: Killing 1556:android.process.acore/u0a64 (adj 985): empty #18
10-28 20:41:29.457  2455  2455 D AndroidRuntime: Shutting down VM
10-28 20:41:31.044  2301  2324 I Perf    : fps=5.1 worst=5 drawn=16/37 queue=0 build=100ms verts=41k cam=0.5,1.6,0.5 yaw=2.73 pitch=0.00 torch=1 ready=true drawn=16 noMesh=0 culled=21
10-28 20:41:33.189  2301  2324 I Perf    : fps=5.1 worst=5 drawn=16/37 queue=0 build=100ms verts=41k cam=0.5,1.6,0.5 yaw=2.73 pitch=0.00 torch=1 ready=true drawn=16 noMesh=0 culled=21
10-28 20:41:34.488  2482  2482 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 20:41:34.501  2482  2482 I AndroidRuntime: Using default boot image
10-28 20:41:34.501  2482  2482 I AndroidRuntime: Leaving lock profiling enabled
10-28 20:41:34.596  2482  2482 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 20:41:34.611  2482  2482 D AndroidRuntime: Shutting down VM
10-28 20:41:35.277  2301  2324 I Perf    : fps=5.3 worst=5 drawn=16/37 queue=0 build=100ms verts=41k cam=0.5,1.6,0.5 yaw=2.73 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 20:41:35.894   506   548 I ActivityManager: Start proc 2496:android.process.acore/u0a64 for content provider {com.android.providers.contacts/com.android.providers.contacts.VoicemailContentProvider}
10-28 20:41:35.938  2496  2496 I ContactsPerf: VoicemailContentProvider.onCreate start
10-28 20:41:35.942  2496  2496 I ContactsPerf: VoicemailContentProvider.onCreate finish
10-28 20:41:37.383  2301  2324 I Perf    : fps=5.2 worst=5 drawn=16/37 queue=0 build=100ms verts=41k cam=0.5,1.6,0.5 yaw=2.73 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 20:41:39.535  2301  2324 I Perf    : fps=5.1 worst=5 drawn=16/37 queue=0 build=100ms verts=41k cam=0.5,1.6,0.5 yaw=2.73 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 20:41:39.567  2527  2527 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 20:41:39.579  2527  2527 I AndroidRuntime: Using default boot image
10-28 20:41:39.579  2527  2527 I AndroidRuntime: Leaving lock profiling enabled
10-28 20:41:39.694  2527  2527 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 20:41:41.199  2527  2527 D AndroidRuntime: Shutting down VM
10-28 20:41:41.577  2301  2324 I Perf    : fps=5.4 worst=5 drawn=16/37 queue=0 build=100ms verts=41k cam=0.1,1.6,1.5 yaw=2.73 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 20:41:43.717  2301  2324 I Perf    : fps=5.1 worst=5 drawn=16/37 queue=0 build=100ms verts=41k cam=0.1,1.6,1.5 yaw=2.73 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 20:41:45.770  2301  2324 I Perf    : fps=5.4 worst=5 drawn=16/37 queue=0 build=100ms verts=41k cam=0.1,1.6,1.5 yaw=2.73 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 20:41:47.958  2301  2324 I Perf    : fps=5.0 worst=5 drawn=16/37 queue=0 build=100ms verts=41k cam=0.1,1.6,1.5 yaw=2.73 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 20:41:50.099  2301  2324 I Perf    : fps=5.2 worst=5 drawn=16/37 queue=0 build=100ms verts=41k cam=0.1,1.6,1.5 yaw=2.73 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
```
## Step log
```
0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=11 noMesh=0 culled=8
10-28 20:41:03.499  2301  2324 I Perf    : fps=4.8 worst=5 drawn=14/37 queue=0 build=100ms verts=36k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:41:05.656  2301  2324 I Perf    : fps=4.6 worst=5 drawn=14/37 queue=0 build=100ms verts=36k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:41:07.695  2301  2324 I Perf    : fps=4.9 worst=5 drawn=14/37 queue=0 build=100ms verts=36k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:41:09.802  2301  2324 I Perf    : fps=4.7 worst=5 drawn=14/37 queue=0 build=100ms verts=36k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:41:11.833  2301  2324 I Perf    : fps=4.9 worst=5 drawn=14/37 queue=0 build=100ms verts=36k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:41:13.960  2301  2324 I Perf    : fps=4.7 worst=5 drawn=14/37 queue=0 build=100ms verts=36k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:41:16.051  2301  2324 I Perf    : fps=4.8 worst=5 drawn=14/37 queue=0 build=100ms verts=36k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:41:18.136  2301  2324 I Perf    : fps=4.8 worst=5 drawn=14/37 queue=0 build=100ms verts=36k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:41:20.240  2301  2324 I Perf    : fps=4.8 worst=5 drawn=14/37 queue=0 build=100ms verts=36k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:41:22.426  2301  2324 I Perf    : fps=4.6 worst=5 drawn=14/37 queue=0 build=100ms verts=36k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:41:22.816  2442  2442 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 20:41:24.570  2301  2324 I Perf    : fps=4.7 worst=5 drawn=14/37 queue=0 build=100ms verts=36k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:41:26.702  2301  2324 I Perf    : fps=4.7 worst=5 drawn=14/37 queue=0 build=100ms verts=36k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:41:28.213  2449  2449 W dex2oat32: Verification of void com.badlogic.gdx.graphics.g3d.particles.ParticleShader.setDefaultCullFace(int) took 144.490ms (34.60 bytecodes/s) (856B approximate peak alloc)
10-28 20:41:28.769  2455  2455 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 20:41:28.784  2455  2455 I AndroidRuntime: Using default boot image
10-28 20:41:28.784  2455  2455 I AndroidRuntime: Leaving lock profiling enabled
10-28 20:41:28.852  2455  2455 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 20:41:28.889  2301  2324 I Perf    : fps=4.6 worst=4 drawn=14/37 queue=0 build=100ms verts=36k cam=0.5,1.6,0.5 yaw=0.05 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:41:29.457  2455  2455 D AndroidRuntime: Shutting down VM
10-28 20:41:31.044  2301  2324 I Perf    : fps=5.1 worst=5 drawn=16/37 queue=0 build=100ms verts=41k cam=0.5,1.6,0.5 yaw=2.73 pitch=0.00 torch=1 ready=true drawn=16 noMesh=0 culled=21
10-28 20:41:33.189  2301  2324 I Perf    : fps=5.1 worst=5 drawn=16/37 queue=0 build=100ms verts=41k cam=0.5,1.6,0.5 yaw=2.73 pitch=0.00 torch=1 ready=true drawn=16 noMesh=0 culled=21
10-28 20:41:34.488  2482  2482 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 20:41:34.501  2482  2482 I AndroidRuntime: Using default boot image
10-28 20:41:34.501  2482  2482 I AndroidRuntime: Leaving lock profiling enabled
10-28 20:41:34.596  2482  2482 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 20:41:34.611  2482  2482 D AndroidRuntime: Shutting down VM
10-28 20:41:35.277  2301  2324 I Perf    : fps=5.3 worst=5 drawn=16/37 queue=0 build=100ms verts=41k cam=0.5,1.6,0.5 yaw=2.73 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 20:41:35.938  2496  2496 I ContactsPerf: VoicemailContentProvider.onCreate start
10-28 20:41:35.942  2496  2496 I ContactsPerf: VoicemailContentProvider.onCreate finish
10-28 20:41:37.383  2301  2324 I Perf    : fps=5.2 worst=5 drawn=16/37 queue=0 build=100ms verts=41k cam=0.5,1.6,0.5 yaw=2.73 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 20:41:39.535  2301  2324 I Perf    : fps=5.1 worst=5 drawn=16/37 queue=0 build=100ms verts=41k cam=0.5,1.6,0.5 yaw=2.73 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 20:41:39.567  2527  2527 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 20:41:39.579  2527  2527 I AndroidRuntime: Using default boot image
10-28 20:41:39.579  2527  2527 I AndroidRuntime: Leaving lock profiling enabled
10-28 20:41:39.694  2527  2527 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 20:41:41.199  2527  2527 D AndroidRuntime: Shutting down VM
10-28 20:41:41.577  2301  2324 I Perf    : fps=5.4 worst=5 drawn=16/37 queue=0 build=100ms verts=41k cam=0.1,1.6,1.5 yaw=2.73 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 20:41:43.717  2301  2324 I Perf    : fps=5.1 worst=5 drawn=16/37 queue=0 build=100ms verts=41k cam=0.1,1.6,1.5 yaw=2.73 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 20:41:45.770  2301  2324 I Perf    : fps=5.4 worst=5 drawn=16/37 queue=0 build=100ms verts=41k cam=0.1,1.6,1.5 yaw=2.73 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 20:41:46.572  2545  2545 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 20:41:47.958  2301  2324 I Perf    : fps=5.0 worst=5 drawn=16/37 queue=0 build=100ms verts=41k cam=0.1,1.6,1.5 yaw=2.73 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 20:41:50.099  2301  2324 I Perf    : fps=5.2 worst=5 drawn=16/37 queue=0 build=100ms verts=41k cam=0.1,1.6,1.5 yaw=2.73 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
+ '[' -z 2301 ']'
+ grep -qa 'FATAL EXCEPTION' emulator-shots/logcat.txt
+ '[' 0 '!=' 0 ']'
+ echo 'OK: the app is alive, rendering, and the touch controls work'
OK: the app is alive, rendering, and the touch controls work
```
