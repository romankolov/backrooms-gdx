# Emulator smoke test diagnostics

Commit: 13e4659dcd7e8cd24d0e6c1c36ccfdcc76a229f6
Outcome: failure

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 29 00:29 /dev/kvm
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
INFO         | The active JSON Web Key Sets can be found here: /run/user/1001/avd/running/3242/jwks/6f23d225-2c89-4cc1-b902-3b708d19f9cf/active.jwk
INFO         | Scanning /run/user/1001/avd/running/3242/jwks/6f23d225-2c89-4cc1-b902-3b708d19f9cf for jwk keys.
INFO         | Started GRPC server at 127.0.0.1:8554, security: Local, auth: +token
INFO         | Advertising in: /run/user/1001/avd/running/pid_3242.ini
INFO         | Setting display: 0 configuration to: 1080x1920, dpi: 420x420 
INFO         | setDisplayActiveConfig: id:0, 1080x1920
INFO         | emulatorSetupEnvironment: Environment scene is not required
USER_INFO    | Emulator is performing a full startup. This may take upto two minutes, or more.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
INFO         | Boot completed in 26487 ms
INFO         | Increasing screen off timeout, logcat buffer size to 2M.
```
## Logcat (filtered)
```
10-29 00:31:59.244   504   544 I ActivityManager: Start proc 2313:com.android.shell/2000 for broadcast {com.android.shell/com.android.shell.HeapDumpReceiver}
10-29 00:31:59.274   504  1806 I ActivityManager: Killing 1923:com.android.localtransport/1000 (adj 975): empty #17
10-29 00:31:59.310   504  1806 I ActivityManager: Killing 1837:com.android.printspooler/u0a87 (adj 985): empty #17
10-29 00:31:59.408   504   544 I ActivityManager: Start proc 2341:com.android.traceur/u0a94 for broadcast {com.android.traceur/com.android.traceur.Receiver}
10-29 00:31:59.580   504  1778 I ActivityManager: Finished processing BOOT_COMPLETED for u0
10-29 00:31:59.581   504  1778 I ActivityManager: Killing 1813:com.android.provision/u0a117 (adj 985): empty #17
10-29 00:32:01.600   435  2097 I iorapd  : Perfetto TraceBuffer saved to file: /data/misc/iorapd/com.backrooms.gdx.debug/1/com.backrooms.gdx.android.AndroidLauncher/raw_traces/1793233921600168679.perfetto_trace.pb
10-29 00:32:14.248   504   542 I ActivityManager: Killing 1293:com.android.cellbroadcastreceiver/u0a64 (adj 985): empty #17
10-29 00:32:19.657   504   534 I ActivityManager: Killing 2015:com.android.calendar/u0a109 (adj 985): empty #17
10-29 00:32:19.657   504   534 I ActivityManager: Killing 1974:com.android.quicksearchbox/u0a101 (adj 985): empty #18
10-29 00:32:20.100  2409  2409 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 00:32:20.110  2409  2409 I AndroidRuntime: Using default boot image
10-29 00:32:20.110  2409  2409 I AndroidRuntime: Leaving lock profiling enabled
10-29 00:32:20.319  2409  2409 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 00:32:20.362  2409  2409 D AndroidRuntime: Shutting down VM
10-29 00:32:21.131  2120  2203 I BackroomsGL: world shader compiled
10-29 00:32:22.900  2120  2203 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-29 00:32:22.916  2120  2203 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-29 00:32:22.946  2120  2203 I Perf    : fps=0.5 worst=0 drawn=0/0 queue=1 build=0ms verts=0k cam=0.0,0.0,0.0 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=0 noMesh=0 culled=0
10-29 00:32:24.720   504   990 I ActivityManager: Killing 2043:com.android.camera2/u0a112 (adj 975): empty #17
10-29 00:32:24.956  2120  2203 I Perf    : fps=7.9 worst=13 drawn=0/0 queue=2 build=0ms verts=0k cam=0.5,1.6,2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=0 noMesh=0 culled=0
10-29 00:32:27.047  2120  2203 I Perf    : fps=10.5 worst=12 drawn=0/0 queue=1 build=0ms verts=0k cam=0.5,1.6,2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=0 noMesh=0 culled=0
10-29 00:32:29.059  2120  2203 I Perf    : fps=10.9 worst=12 drawn=0/0 queue=0 build=0ms verts=0k cam=0.5,1.6,2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=0 noMesh=0 culled=0
10-29 00:32:31.151  2120  2203 I Perf    : fps=11.0 worst=12 drawn=0/0 queue=2 build=0ms verts=0k cam=0.5,1.6,2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=0 noMesh=0 culled=0
10-29 00:32:33.215  2120  2203 I Perf    : fps=11.2 worst=13 drawn=0/0 queue=0 build=0ms verts=0k cam=0.5,1.6,2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=0 noMesh=0 culled=0
10-29 00:32:35.273  2120  2203 I Perf    : fps=11.6 worst=14 drawn=0/0 queue=2 build=0ms verts=0k cam=0.5,1.6,2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=0 noMesh=0 culled=0
10-29 00:32:37.308  2120  2203 I Perf    : fps=11.8 worst=14 drawn=0/0 queue=2 build=0ms verts=0k cam=0.5,1.6,2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=0 noMesh=0 culled=0
10-29 00:32:39.312  2120  2203 I Perf    : fps=12.0 worst=13 drawn=0/0 queue=2 build=0ms verts=0k cam=0.5,1.6,2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=0 noMesh=0 culled=0
10-29 00:32:41.367  2120  2203 I Perf    : fps=11.7 worst=14 drawn=0/0 queue=2 build=0ms verts=0k cam=0.5,1.6,2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=0 noMesh=0 culled=0
10-29 00:32:43.412  2120  2203 I Perf    : fps=11.7 worst=13 drawn=0/0 queue=2 build=0ms verts=0k cam=0.5,1.6,2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=0 noMesh=0 culled=0
10-29 00:32:45.429  2120  2203 I Perf    : fps=11.9 worst=14 drawn=0/0 queue=2 build=0ms verts=0k cam=0.5,1.6,2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=0 noMesh=0 culled=0
10-29 00:32:47.469  2120  2203 I Perf    : fps=11.3 worst=12 drawn=0/0 queue=2 build=0ms verts=0k cam=0.5,1.6,2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=0 noMesh=0 culled=0
10-29 00:32:49.523  2120  2203 I Perf    : fps=11.2 worst=13 drawn=0/0 queue=2 build=0ms verts=0k cam=0.5,1.6,2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=0 noMesh=0 culled=0
10-29 00:32:50.253  2453  2453 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 00:32:50.320  2453  2453 I AndroidRuntime: Using default boot image
10-29 00:32:50.320  2453  2453 I AndroidRuntime: Leaving lock profiling enabled
10-29 00:32:50.639  2453  2453 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 00:32:51.247  2453  2453 D AndroidRuntime: Shutting down VM
10-29 00:32:51.575  2120  2203 I Perf    : fps=11.2 worst=12 drawn=0/0 queue=2 build=0ms verts=0k cam=0.5,1.6,2.5 yaw=2.81 pitch=0.00 torch=1 ready=true drawn=0 noMesh=0 culled=0
10-29 00:32:53.632  2120  2203 I Perf    : fps=11.7 worst=13 drawn=0/0 queue=2 build=0ms verts=0k cam=0.5,1.6,2.5 yaw=2.81 pitch=0.00 torch=1 ready=true drawn=0 noMesh=0 culled=0
10-29 00:32:55.653  2120  2203 I Perf    : fps=11.9 worst=14 drawn=0/0 queue=2 build=0ms verts=0k cam=0.5,1.6,2.5 yaw=2.81 pitch=0.00 torch=1 ready=true drawn=0 noMesh=0 culled=0
10-29 00:32:56.490  2482  2482 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 00:32:56.521  2482  2482 I AndroidRuntime: Using default boot image
10-29 00:32:56.521  2482  2482 I AndroidRuntime: Leaving lock profiling enabled
10-29 00:32:56.759  2482  2482 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 00:32:56.772  2482  2482 D AndroidRuntime: Shutting down VM
10-29 00:32:57.717  2120  2203 I Perf    : fps=11.6 worst=13 drawn=0/0 queue=2 build=0ms verts=0k cam=0.5,1.6,2.5 yaw=2.81 pitch=0.00 torch=0 ready=true drawn=0 noMesh=0 culled=0
10-29 00:32:59.751  2120  2203 I Perf    : fps=11.8 worst=14 drawn=0/0 queue=2 build=0ms verts=0k cam=0.5,1.6,2.5 yaw=2.81 pitch=0.00 torch=0 ready=true drawn=0 noMesh=0 culled=0
10-29 00:33:01.772  2120  2203 I Perf    : fps=11.4 worst=14 drawn=0/0 queue=2 build=0ms verts=0k cam=0.5,1.6,2.5 yaw=2.81 pitch=0.00 torch=0 ready=true drawn=0 noMesh=0 culled=0
10-29 00:33:01.916  2499  2499 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 00:33:01.934  2499  2499 I AndroidRuntime: Using default boot image
10-29 00:33:01.937  2499  2499 I AndroidRuntime: Leaving lock profiling enabled
10-29 00:33:02.233  2499  2499 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 00:33:03.776  2499  2499 D AndroidRuntime: Shutting down VM
10-29 00:33:03.790  2120  2203 I Perf    : fps=11.9 worst=14 drawn=0/0 queue=2 build=0ms verts=0k cam=0.4,1.6,5.5 yaw=2.81 pitch=0.00 torch=0 ready=true drawn=0 noMesh=0 culled=0
10-29 00:33:05.820  2120  2203 I Perf    : fps=11.8 worst=13 drawn=0/0 queue=2 build=0ms verts=0k cam=0.4,1.6,5.7 yaw=2.81 pitch=0.00 torch=0 ready=true drawn=0 noMesh=0 culled=0
10-29 00:33:07.848  2120  2203 I Perf    : fps=11.9 worst=13 drawn=0/0 queue=1 build=0ms verts=0k cam=0.4,1.6,5.7 yaw=2.81 pitch=0.00 torch=0 ready=true drawn=0 noMesh=0 culled=0
10-29 00:33:09.859  2120  2203 I Perf    : fps=11.4 worst=13 drawn=0/0 queue=2 build=0ms verts=0k cam=0.4,1.6,5.7 yaw=2.81 pitch=0.00 torch=0 ready=true drawn=0 noMesh=0 culled=0
10-29 00:33:11.944  2120  2203 I Perf    : fps=11.5 worst=13 drawn=0/0 queue=0 build=0ms verts=0k cam=0.4,1.6,5.7 yaw=2.81 pitch=0.00 torch=0 ready=true drawn=0 noMesh=0 culled=0
10-29 00:33:13.946  2120  2203 I Perf    : fps=11.0 worst=14 drawn=0/0 queue=2 build=0ms verts=0k cam=0.4,1.6,5.7 yaw=2.81 pitch=0.00 torch=0 ready=true drawn=0 noMesh=0 culled=0
```
## Step log
```
jump=1609,937 stickZone=864
10-29 00:32:22.946  2120  2203 I Perf    : fps=0.5 worst=0 drawn=0/0 queue=1 build=0ms verts=0k cam=0.0,0.0,0.0 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=0 noMesh=0 culled=0
10-29 00:32:24.956  2120  2203 I Perf    : fps=7.9 worst=13 drawn=0/0 queue=2 build=0ms verts=0k cam=0.5,1.6,2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=0 noMesh=0 culled=0
10-29 00:32:27.047  2120  2203 I Perf    : fps=10.5 worst=12 drawn=0/0 queue=1 build=0ms verts=0k cam=0.5,1.6,2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=0 noMesh=0 culled=0
10-29 00:32:29.059  2120  2203 I Perf    : fps=10.9 worst=12 drawn=0/0 queue=0 build=0ms verts=0k cam=0.5,1.6,2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=0 noMesh=0 culled=0
10-29 00:32:31.151  2120  2203 I Perf    : fps=11.0 worst=12 drawn=0/0 queue=2 build=0ms verts=0k cam=0.5,1.6,2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=0 noMesh=0 culled=0
10-29 00:32:33.215  2120  2203 I Perf    : fps=11.2 worst=13 drawn=0/0 queue=0 build=0ms verts=0k cam=0.5,1.6,2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=0 noMesh=0 culled=0
10-29 00:32:35.273  2120  2203 I Perf    : fps=11.6 worst=14 drawn=0/0 queue=2 build=0ms verts=0k cam=0.5,1.6,2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=0 noMesh=0 culled=0
10-29 00:32:37.308  2120  2203 I Perf    : fps=11.8 worst=14 drawn=0/0 queue=2 build=0ms verts=0k cam=0.5,1.6,2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=0 noMesh=0 culled=0
10-29 00:32:39.312  2120  2203 I Perf    : fps=12.0 worst=13 drawn=0/0 queue=2 build=0ms verts=0k cam=0.5,1.6,2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=0 noMesh=0 culled=0
10-29 00:32:41.367  2120  2203 I Perf    : fps=11.7 worst=14 drawn=0/0 queue=2 build=0ms verts=0k cam=0.5,1.6,2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=0 noMesh=0 culled=0
10-29 00:32:43.412  2120  2203 I Perf    : fps=11.7 worst=13 drawn=0/0 queue=2 build=0ms verts=0k cam=0.5,1.6,2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=0 noMesh=0 culled=0
10-29 00:32:45.429  2120  2203 I Perf    : fps=11.9 worst=14 drawn=0/0 queue=2 build=0ms verts=0k cam=0.5,1.6,2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=0 noMesh=0 culled=0
10-29 00:32:46.242  2440  2440 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-29 00:32:47.469  2120  2203 I Perf    : fps=11.3 worst=12 drawn=0/0 queue=2 build=0ms verts=0k cam=0.5,1.6,2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=0 noMesh=0 culled=0
10-29 00:32:49.523  2120  2203 I Perf    : fps=11.2 worst=13 drawn=0/0 queue=2 build=0ms verts=0k cam=0.5,1.6,2.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=0 noMesh=0 culled=0
10-29 00:32:50.253  2453  2453 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 00:32:50.320  2453  2453 I AndroidRuntime: Using default boot image
10-29 00:32:50.320  2453  2453 I AndroidRuntime: Leaving lock profiling enabled
10-29 00:32:50.639  2453  2453 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 00:32:51.247  2453  2453 D AndroidRuntime: Shutting down VM
10-29 00:32:51.575  2120  2203 I Perf    : fps=11.2 worst=12 drawn=0/0 queue=2 build=0ms verts=0k cam=0.5,1.6,2.5 yaw=2.81 pitch=0.00 torch=1 ready=true drawn=0 noMesh=0 culled=0
10-29 00:32:53.632  2120  2203 I Perf    : fps=11.7 worst=13 drawn=0/0 queue=2 build=0ms verts=0k cam=0.5,1.6,2.5 yaw=2.81 pitch=0.00 torch=1 ready=true drawn=0 noMesh=0 culled=0
10-29 00:32:55.653  2120  2203 I Perf    : fps=11.9 worst=14 drawn=0/0 queue=2 build=0ms verts=0k cam=0.5,1.6,2.5 yaw=2.81 pitch=0.00 torch=1 ready=true drawn=0 noMesh=0 culled=0
10-29 00:32:56.490  2482  2482 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 00:32:56.521  2482  2482 I AndroidRuntime: Using default boot image
10-29 00:32:56.521  2482  2482 I AndroidRuntime: Leaving lock profiling enabled
10-29 00:32:56.759  2482  2482 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 00:32:56.772  2482  2482 D AndroidRuntime: Shutting down VM
10-29 00:32:57.717  2120  2203 I Perf    : fps=11.6 worst=13 drawn=0/0 queue=2 build=0ms verts=0k cam=0.5,1.6,2.5 yaw=2.81 pitch=0.00 torch=0 ready=true drawn=0 noMesh=0 culled=0
10-29 00:32:59.751  2120  2203 I Perf    : fps=11.8 worst=14 drawn=0/0 queue=2 build=0ms verts=0k cam=0.5,1.6,2.5 yaw=2.81 pitch=0.00 torch=0 ready=true drawn=0 noMesh=0 culled=0
10-29 00:33:01.772  2120  2203 I Perf    : fps=11.4 worst=14 drawn=0/0 queue=2 build=0ms verts=0k cam=0.5,1.6,2.5 yaw=2.81 pitch=0.00 torch=0 ready=true drawn=0 noMesh=0 culled=0
10-29 00:33:01.916  2499  2499 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 00:33:01.934  2499  2499 I AndroidRuntime: Using default boot image
10-29 00:33:01.937  2499  2499 I AndroidRuntime: Leaving lock profiling enabled
10-29 00:33:02.233  2499  2499 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 00:33:03.776  2499  2499 D AndroidRuntime: Shutting down VM
10-29 00:33:03.790  2120  2203 I Perf    : fps=11.9 worst=14 drawn=0/0 queue=2 build=0ms verts=0k cam=0.4,1.6,5.5 yaw=2.81 pitch=0.00 torch=0 ready=true drawn=0 noMesh=0 culled=0
10-29 00:33:05.820  2120  2203 I Perf    : fps=11.8 worst=13 drawn=0/0 queue=2 build=0ms verts=0k cam=0.4,1.6,5.7 yaw=2.81 pitch=0.00 torch=0 ready=true drawn=0 noMesh=0 culled=0
10-29 00:33:07.848  2120  2203 I Perf    : fps=11.9 worst=13 drawn=0/0 queue=1 build=0ms verts=0k cam=0.4,1.6,5.7 yaw=2.81 pitch=0.00 torch=0 ready=true drawn=0 noMesh=0 culled=0
10-29 00:33:09.076  2522  2522 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-29 00:33:09.859  2120  2203 I Perf    : fps=11.4 worst=13 drawn=0/0 queue=2 build=0ms verts=0k cam=0.4,1.6,5.7 yaw=2.81 pitch=0.00 torch=0 ready=true drawn=0 noMesh=0 culled=0
10-29 00:33:11.944  2120  2203 I Perf    : fps=11.5 worst=13 drawn=0/0 queue=0 build=0ms verts=0k cam=0.4,1.6,5.7 yaw=2.81 pitch=0.00 torch=0 ready=true drawn=0 noMesh=0 culled=0
10-29 00:33:13.946  2120  2203 I Perf    : fps=11.0 worst=14 drawn=0/0 queue=2 build=0ms verts=0k cam=0.4,1.6,5.7 yaw=2.81 pitch=0.00 torch=0 ready=true drawn=0 noMesh=0 culled=0
+ '[' -z 2120 ']'
+ grep -qa 'FATAL EXCEPTION' emulator-shots/logcat.txt
+ '[' 1 '!=' 0 ']'
+ echo 'FATAL: the controls did not behave (see the [Perf] lines above)'
FATAL: the controls did not behave (see the [Perf] lines above)
+ exit 1
```
