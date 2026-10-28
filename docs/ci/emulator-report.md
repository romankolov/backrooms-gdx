# Emulator smoke test diagnostics

Commit: 7a3eadc3fbf2441f50464784c9acb7af85d35f13
Outcome: success

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 28 21:00 /dev/kvm
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
INFO         | The active JSON Web Key Sets can be found here: /run/user/1001/avd/running/2996/jwks/b892e208-c5c1-4753-bc6b-18e3f6d2828e/active.jwk
INFO         | Scanning /run/user/1001/avd/running/2996/jwks/b892e208-c5c1-4753-bc6b-18e3f6d2828e for jwk keys.
INFO         | Started GRPC server at 127.0.0.1:8554, security: Local, auth: +token
INFO         | Advertising in: /run/user/1001/avd/running/pid_2996.ini
INFO         | Setting display: 0 configuration to: 1080x1920, dpi: 420x420 
INFO         | setDisplayActiveConfig: id:0, 1080x1920
INFO         | emulatorSetupEnvironment: Environment scene is not required
USER_INFO    | Emulator is performing a full startup. This may take upto two minutes, or more.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
INFO         | Boot completed in 26107 ms
INFO         | Increasing screen off timeout, logcat buffer size to 2M.
```
## Logcat (filtered)
```
10-28 21:03:46.826   420  2111 I iorapd  : Perfetto TraceBuffer saved to file: /data/misc/iorapd/com.backrooms.gdx.debug/1/com.backrooms.gdx.android.AndroidLauncher/raw_traces/1793221426825704004.perfetto_trace.pb
10-28 21:03:55.257   509   549 W ActivityManager: Slow operation: 95ms so far, now at startProcess: returned from zygote!
10-28 21:03:55.257   509   549 W ActivityManager: Slow operation: 95ms so far, now at startProcess: done updating battery stats
10-28 21:03:55.257   509   549 W ActivityManager: Slow operation: 96ms so far, now at startProcess: building log message
10-28 21:03:55.257   509   549 I ActivityManager: Start proc 2384:com.android.cellbroadcastreceiver.module/u0a125 for broadcast {com.android.cellbroadcastreceiver.module/com.android.cellbroadcastreceiver.CellBroadcastReceiver}
10-28 21:03:55.257   509   549 W ActivityManager: Slow operation: 96ms so far, now at startProcess: starting to update pids map
10-28 21:03:55.257   509   549 W ActivityManager: Slow operation: 96ms so far, now at startProcess: done updating pids map
10-28 21:03:59.459   509   539 I ActivityManager: Killing 1282:com.android.cellbroadcastreceiver/u0a65 (adj 985): empty #17
10-28 21:04:03.991   509   590 I ActivityManager: Killing 2028:com.android.camera2/u0a107 (adj 975): empty #17
10-28 21:04:04.929  2421  2421 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 21:04:04.957  2421  2421 I AndroidRuntime: Using default boot image
10-28 21:04:04.957  2421  2421 I AndroidRuntime: Leaving lock profiling enabled
10-28 21:04:05.321  2421  2421 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 21:04:05.404  2421  2421 D AndroidRuntime: Shutting down VM
10-28 21:04:05.980  2113  2180 I BackroomsGL: world shader compiled
10-28 21:04:06.017   509   590 I ActivityManager: Killing 2079:com.android.deskclock/u0a109 (adj 985): empty #17
10-28 21:04:06.017   509   590 I ActivityManager: Killing 2053:com.android.contacts/u0a98 (adj 985): empty #18
10-28 21:04:06.460  2113  2180 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 21:04:06.460  2113  2180 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 21:04:08.767  2113  2180 I Perf    : fps=1.0 worst=1 drawn=2/3 queue=2 build=381ms verts=5k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=2 noMesh=0 culled=0
10-28 21:04:10.790  2113  2180 I Perf    : fps=4.5 worst=5 drawn=12/17 queue=2 build=17ms verts=30k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=12 noMesh=0 culled=3
10-28 21:04:12.871  2113  2180 I Perf    : fps=4.3 worst=4 drawn=18/33 queue=2 build=43ms verts=46k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=13
10-28 21:04:14.921  2113  2180 I Perf    : fps=4.4 worst=4 drawn=18/37 queue=0 build=17ms verts=46k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 21:04:16.931  2113  2180 I Perf    : fps=4.5 worst=4 drawn=18/37 queue=0 build=17ms verts=46k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 21:04:18.935  2113  2180 I Perf    : fps=4.5 worst=4 drawn=18/37 queue=0 build=17ms verts=46k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 21:04:21.040  2113  2180 I Perf    : fps=4.3 worst=4 drawn=18/37 queue=0 build=17ms verts=46k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 21:04:23.063  2113  2180 I Perf    : fps=4.5 worst=4 drawn=18/37 queue=0 build=17ms verts=46k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 21:04:25.084  2113  2180 I Perf    : fps=4.4 worst=4 drawn=18/37 queue=0 build=17ms verts=46k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 21:04:27.105  2113  2180 I Perf    : fps=4.5 worst=4 drawn=18/37 queue=0 build=17ms verts=46k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 21:04:29.305  2113  2180 I Perf    : fps=4.5 worst=4 drawn=18/37 queue=0 build=17ms verts=46k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 21:04:31.331  2113  2180 I Perf    : fps=4.4 worst=4 drawn=18/37 queue=0 build=17ms verts=46k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 21:04:33.524  2113  2180 I Perf    : fps=4.1 worst=4 drawn=18/37 queue=0 build=17ms verts=46k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 21:04:35.628  2113  2180 I Perf    : fps=4.3 worst=4 drawn=18/37 queue=0 build=17ms verts=46k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 21:04:37.755  2113  2180 I Perf    : fps=4.3 worst=4 drawn=18/37 queue=0 build=17ms verts=46k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 21:04:37.938  2472  2472 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 21:04:37.955  2472  2472 I AndroidRuntime: Using default boot image
10-28 21:04:37.962  2472  2472 I AndroidRuntime: Leaving lock profiling enabled
10-28 21:04:38.074  2472  2472 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 21:04:38.679  2472  2472 D AndroidRuntime: Shutting down VM
10-28 21:04:39.926  2113  2180 I Perf    : fps=4.1 worst=4 drawn=13/37 queue=0 build=17ms verts=33k cam=1.5,1.6,-0.5 yaw=2.80 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 21:04:42.110  2113  2180 I Perf    : fps=4.1 worst=4 drawn=13/37 queue=0 build=17ms verts=33k cam=1.5,1.6,-0.5 yaw=2.80 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 21:04:43.824  2489  2489 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 21:04:43.853  2489  2489 I AndroidRuntime: Using default boot image
10-28 21:04:43.853  2489  2489 I AndroidRuntime: Leaving lock profiling enabled
10-28 21:04:43.976  2489  2489 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 21:04:43.993  2489  2489 D AndroidRuntime: Shutting down VM
10-28 21:04:44.161  2113  2180 I Perf    : fps=3.9 worst=4 drawn=13/37 queue=0 build=17ms verts=33k cam=1.5,1.6,-0.5 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 21:04:46.333  2113  2180 I Perf    : fps=4.1 worst=4 drawn=13/37 queue=0 build=17ms verts=33k cam=1.5,1.6,-0.5 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 21:04:48.448  2113  2180 I Perf    : fps=4.3 worst=4 drawn=13/37 queue=0 build=17ms verts=33k cam=1.5,1.6,-0.5 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 21:04:48.879  2506  2506 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 21:04:48.898  2506  2506 I AndroidRuntime: Using default boot image
10-28 21:04:48.899  2506  2506 I AndroidRuntime: Leaving lock profiling enabled
10-28 21:04:49.019  2506  2506 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 21:04:50.526  2506  2506 D AndroidRuntime: Shutting down VM
10-28 21:04:50.628  2113  2180 I Perf    : fps=4.1 worst=4 drawn=12/37 queue=2 build=17ms verts=30k cam=1.2,1.6,0.2 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=12 noMesh=0 culled=25
10-28 21:04:52.876  2113  2180 I Perf    : fps=4.0 worst=4 drawn=18/44 queue=0 build=141ms verts=46k cam=1.2,1.6,0.4 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-28 21:04:55.029  2113  2180 I Perf    : fps=4.2 worst=4 drawn=18/44 queue=0 build=141ms verts=46k cam=1.2,1.6,0.4 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-28 21:04:57.051  2113  2180 I Perf    : fps=4.0 worst=4 drawn=18/44 queue=0 build=141ms verts=46k cam=1.2,1.6,0.4 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-28 21:04:59.228  2113  2180 I Perf    : fps=4.1 worst=4 drawn=18/44 queue=0 build=141ms verts=46k cam=1.2,1.6,0.4 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-28 21:05:01.440  2113  2180 I Perf    : fps=4.1 worst=4 drawn=18/44 queue=0 build=141ms verts=46k cam=1.2,1.6,0.4 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
```
## Step log
```
81ms verts=5k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=2 noMesh=0 culled=0
10-28 21:04:10.790  2113  2180 I Perf    : fps=4.5 worst=5 drawn=12/17 queue=2 build=17ms verts=30k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=12 noMesh=0 culled=3
10-28 21:04:12.871  2113  2180 I Perf    : fps=4.3 worst=4 drawn=18/33 queue=2 build=43ms verts=46k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=13
10-28 21:04:14.921  2113  2180 I Perf    : fps=4.4 worst=4 drawn=18/37 queue=0 build=17ms verts=46k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 21:04:16.931  2113  2180 I Perf    : fps=4.5 worst=4 drawn=18/37 queue=0 build=17ms verts=46k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 21:04:18.935  2113  2180 I Perf    : fps=4.5 worst=4 drawn=18/37 queue=0 build=17ms verts=46k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 21:04:21.040  2113  2180 I Perf    : fps=4.3 worst=4 drawn=18/37 queue=0 build=17ms verts=46k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 21:04:23.063  2113  2180 I Perf    : fps=4.5 worst=4 drawn=18/37 queue=0 build=17ms verts=46k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 21:04:25.084  2113  2180 I Perf    : fps=4.4 worst=4 drawn=18/37 queue=0 build=17ms verts=46k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 21:04:27.105  2113  2180 I Perf    : fps=4.5 worst=4 drawn=18/37 queue=0 build=17ms verts=46k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 21:04:29.305  2113  2180 I Perf    : fps=4.5 worst=4 drawn=18/37 queue=0 build=17ms verts=46k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 21:04:31.331  2113  2180 I Perf    : fps=4.4 worst=4 drawn=18/37 queue=0 build=17ms verts=46k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 21:04:31.852  2449  2449 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 21:04:33.524  2113  2180 I Perf    : fps=4.1 worst=4 drawn=18/37 queue=0 build=17ms verts=46k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 21:04:35.628  2113  2180 I Perf    : fps=4.3 worst=4 drawn=18/37 queue=0 build=17ms verts=46k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 21:04:37.755  2113  2180 I Perf    : fps=4.3 worst=4 drawn=18/37 queue=0 build=17ms verts=46k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 21:04:37.938  2472  2472 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 21:04:37.955  2472  2472 I AndroidRuntime: Using default boot image
10-28 21:04:37.962  2472  2472 I AndroidRuntime: Leaving lock profiling enabled
10-28 21:04:38.074  2472  2472 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 21:04:38.679  2472  2472 D AndroidRuntime: Shutting down VM
10-28 21:04:39.926  2113  2180 I Perf    : fps=4.1 worst=4 drawn=13/37 queue=0 build=17ms verts=33k cam=1.5,1.6,-0.5 yaw=2.80 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 21:04:42.110  2113  2180 I Perf    : fps=4.1 worst=4 drawn=13/37 queue=0 build=17ms verts=33k cam=1.5,1.6,-0.5 yaw=2.80 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 21:04:43.824  2489  2489 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 21:04:43.853  2489  2489 I AndroidRuntime: Using default boot image
10-28 21:04:43.853  2489  2489 I AndroidRuntime: Leaving lock profiling enabled
10-28 21:04:43.976  2489  2489 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 21:04:43.993  2489  2489 D AndroidRuntime: Shutting down VM
10-28 21:04:44.161  2113  2180 I Perf    : fps=3.9 worst=4 drawn=13/37 queue=0 build=17ms verts=33k cam=1.5,1.6,-0.5 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 21:04:46.333  2113  2180 I Perf    : fps=4.1 worst=4 drawn=13/37 queue=0 build=17ms verts=33k cam=1.5,1.6,-0.5 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 21:04:48.448  2113  2180 I Perf    : fps=4.3 worst=4 drawn=13/37 queue=0 build=17ms verts=33k cam=1.5,1.6,-0.5 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 21:04:48.879  2506  2506 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 21:04:48.898  2506  2506 I AndroidRuntime: Using default boot image
10-28 21:04:48.899  2506  2506 I AndroidRuntime: Leaving lock profiling enabled
10-28 21:04:49.019  2506  2506 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 21:04:50.526  2506  2506 D AndroidRuntime: Shutting down VM
10-28 21:04:50.628  2113  2180 I Perf    : fps=4.1 worst=4 drawn=12/37 queue=2 build=17ms verts=30k cam=1.2,1.6,0.2 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=12 noMesh=0 culled=25
10-28 21:04:52.876  2113  2180 I Perf    : fps=4.0 worst=4 drawn=18/44 queue=0 build=141ms verts=46k cam=1.2,1.6,0.4 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-28 21:04:55.029  2113  2180 I Perf    : fps=4.2 worst=4 drawn=18/44 queue=0 build=141ms verts=46k cam=1.2,1.6,0.4 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-28 21:04:55.766  2524  2524 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 21:04:57.051  2113  2180 I Perf    : fps=4.0 worst=4 drawn=18/44 queue=0 build=141ms verts=46k cam=1.2,1.6,0.4 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-28 21:04:59.228  2113  2180 I Perf    : fps=4.1 worst=4 drawn=18/44 queue=0 build=141ms verts=46k cam=1.2,1.6,0.4 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-28 21:05:01.440  2113  2180 I Perf    : fps=4.1 worst=4 drawn=18/44 queue=0 build=141ms verts=46k cam=1.2,1.6,0.4 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
+ '[' -z 2113 ']'
+ grep -qa 'FATAL EXCEPTION' emulator-shots/logcat.txt
+ '[' 0 '!=' 0 ']'
+ echo 'OK: the app is alive, rendering, and the touch controls work'
OK: the app is alive, rendering, and the touch controls work
```
