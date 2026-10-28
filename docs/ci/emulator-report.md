# Emulator smoke test diagnostics

Commit: fcc02c6036b94c313760221a37cc2d7211f416b8
Outcome: success

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 28 21:19 /dev/kvm
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
INFO         | The active JSON Web Key Sets can be found here: /run/user/1001/avd/running/2910/jwks/377b44a5-ed76-4c87-bca1-e1e2af660f1d/active.jwk
INFO         | Scanning /run/user/1001/avd/running/2910/jwks/377b44a5-ed76-4c87-bca1-e1e2af660f1d for jwk keys.
INFO         | Started GRPC server at 127.0.0.1:8554, security: Local, auth: +token
INFO         | Advertising in: /run/user/1001/avd/running/pid_2910.ini
INFO         | Setting display: 0 configuration to: 1080x1920, dpi: 420x420 
INFO         | setDisplayActiveConfig: id:0, 1080x1920
INFO         | emulatorSetupEnvironment: Environment scene is not required
USER_INFO    | Emulator is performing a full startup. This may take upto two minutes, or more.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
INFO         | Boot completed in 26092 ms
INFO         | Increasing screen off timeout, logcat buffer size to 2M.
```
## Logcat (filtered)
```
10-28 21:22:18.701   507   547 I ActivityManager: Start proc 2355:com.android.shell/2000 for broadcast {com.android.shell/com.android.shell.HeapDumpReceiver}
10-28 21:22:18.701   507   547 W ActivityManager: Slow operation: 56ms so far, now at startProcess: starting to update pids map
10-28 21:22:18.701   507   547 W ActivityManager: Slow operation: 57ms so far, now at startProcess: done updating pids map
10-28 21:22:18.787   507  1004 I ActivityManager: Killing 1652:com.android.externalstorage/u0a68 (adj 985): empty #17
10-28 21:22:18.930   507   547 I ActivityManager: Start proc 2379:com.android.traceur/u0a90 for broadcast {com.android.traceur/com.android.traceur.Receiver}
10-28 21:22:19.114   507  1004 I ActivityManager: Finished processing BOOT_COMPLETED for u0
10-28 21:22:19.119   507  1004 I ActivityManager: Killing 1918:com.android.localtransport/1000 (adj 985): empty #17
10-28 21:22:33.610   507   545 I ActivityManager: Killing 1284:com.android.cellbroadcastreceiver/u0a65 (adj 985): empty #17
10-28 21:22:36.959   507   537 I ActivityManager: Killing 2043:com.android.calendar/u0a108 (adj 985): empty #17
10-28 21:22:36.960   507   537 I ActivityManager: Killing 1957:com.android.quicksearchbox/u0a101 (adj 985): empty #18
10-28 21:22:39.452  2447  2447 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 21:22:39.460  2447  2447 I AndroidRuntime: Using default boot image
10-28 21:22:39.460  2447  2447 I AndroidRuntime: Leaving lock profiling enabled
10-28 21:22:39.629  2447  2447 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 21:22:39.645  2447  2447 D AndroidRuntime: Shutting down VM
10-28 21:22:40.249  2183  2251 I BackroomsGL: world shader compiled
10-28 21:22:40.879  2183  2251 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 21:22:40.879  2183  2251 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 21:22:42.153   507  1787 I ActivityManager: Killing 2075:com.android.camera2/u0a109 (adj 975): empty #17
10-28 21:22:42.888  2183  2251 I Perf    : fps=1.1 worst=2 drawn=2/4 queue=2 build=235ms verts=5k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=2 noMesh=0 culled=0
10-28 21:22:44.955  2183  2251 I Perf    : fps=3.8 worst=4 drawn=11/17 queue=2 build=32ms verts=28k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=11 noMesh=0 culled=4
10-28 21:22:47.053  2183  2251 I Perf    : fps=3.8 worst=3 drawn=13/28 queue=2 build=19ms verts=33k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=14
10-28 21:22:49.046  2183  2251 I Perf    : fps=4.0 worst=4 drawn=14/37 queue=0 build=34ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:22:51.078  2183  2251 I Perf    : fps=3.9 worst=4 drawn=14/37 queue=0 build=34ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:22:53.148  2183  2251 I Perf    : fps=3.9 worst=4 drawn=14/37 queue=0 build=34ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:22:55.164  2183  2251 I Perf    : fps=4.0 worst=4 drawn=14/37 queue=0 build=34ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:22:57.204  2183  2251 I Perf    : fps=3.9 worst=4 drawn=14/37 queue=0 build=34ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:22:59.220  2183  2251 I Perf    : fps=4.0 worst=4 drawn=14/37 queue=0 build=34ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:23:01.263  2183  2251 I Perf    : fps=3.9 worst=4 drawn=14/37 queue=0 build=34ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:23:03.329  2183  2251 I Perf    : fps=3.9 worst=4 drawn=14/37 queue=0 build=34ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:23:05.410  2183  2251 I Perf    : fps=3.8 worst=4 drawn=14/37 queue=0 build=34ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:23:07.576  2183  2251 I Perf    : fps=3.7 worst=3 drawn=14/37 queue=0 build=34ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:23:09.646  2183  2251 I Perf    : fps=3.9 worst=4 drawn=14/37 queue=0 build=34ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:23:11.745  2183  2251 I Perf    : fps=3.8 worst=4 drawn=14/37 queue=0 build=34ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:23:12.153  2488  2488 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 21:23:12.182  2488  2488 I AndroidRuntime: Using default boot image
10-28 21:23:12.182  2488  2488 I AndroidRuntime: Leaving lock profiling enabled
10-28 21:23:12.376  2488  2488 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 21:23:12.982  2488  2488 D AndroidRuntime: Shutting down VM
10-28 21:23:13.825  2183  2251 I Perf    : fps=4.3 worst=5 drawn=16/37 queue=0 build=34ms verts=40k cam=0.5,1.6,0.5 yaw=2.79 pitch=0.00 torch=1 ready=true drawn=16 noMesh=0 culled=21
10-28 21:23:15.917  2183  2251 I Perf    : fps=4.8 worst=5 drawn=16/37 queue=0 build=34ms verts=40k cam=0.5,1.6,0.5 yaw=2.79 pitch=0.00 torch=1 ready=true drawn=16 noMesh=0 culled=21
10-28 21:23:17.791  2514  2514 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 21:23:17.802  2514  2514 I AndroidRuntime: Using default boot image
10-28 21:23:17.802  2514  2514 I AndroidRuntime: Leaving lock profiling enabled
10-28 21:23:17.946  2514  2514 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 21:23:18.095  2514  2514 D AndroidRuntime: Shutting down VM
10-28 21:23:18.098  2183  2251 I Perf    : fps=4.6 worst=4 drawn=16/37 queue=0 build=34ms verts=40k cam=0.5,1.6,0.5 yaw=2.79 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 21:23:20.177  2183  2251 I Perf    : fps=4.8 worst=5 drawn=16/37 queue=0 build=34ms verts=40k cam=0.5,1.6,0.5 yaw=2.79 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 21:23:22.313  2183  2251 I Perf    : fps=4.7 worst=5 drawn=16/37 queue=0 build=34ms verts=40k cam=0.5,1.6,0.5 yaw=2.79 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 21:23:23.094  2531  2531 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 21:23:23.100  2531  2531 I AndroidRuntime: Using default boot image
10-28 21:23:23.100  2531  2531 I AndroidRuntime: Leaving lock profiling enabled
10-28 21:23:23.302  2531  2531 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 21:23:24.488  2183  2251 I Perf    : fps=4.6 worst=5 drawn=16/37 queue=0 build=34ms verts=40k cam=0.4,1.7,0.9 yaw=2.79 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 21:23:24.868  2531  2531 D AndroidRuntime: Shutting down VM
10-28 21:23:26.651  2183  2251 I Perf    : fps=4.6 worst=4 drawn=16/37 queue=0 build=34ms verts=40k cam=0.1,1.6,1.6 yaw=2.79 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 21:23:28.788  2183  2251 I Perf    : fps=4.7 worst=5 drawn=16/37 queue=0 build=34ms verts=40k cam=0.1,1.6,1.6 yaw=2.79 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 21:23:30.848  2183  2251 I Perf    : fps=4.4 worst=4 drawn=16/37 queue=0 build=34ms verts=40k cam=0.1,1.6,1.6 yaw=2.79 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 21:23:32.885  2183  2251 I Perf    : fps=4.4 worst=4 drawn=16/37 queue=0 build=34ms verts=40k cam=0.1,1.6,1.6 yaw=2.79 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 21:23:35.071  2183  2251 I Perf    : fps=4.6 worst=4 drawn=16/37 queue=0 build=34ms verts=40k cam=0.1,1.6,1.6 yaw=2.79 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
```
## Step log
```
drawn=2/4 queue=2 build=235ms verts=5k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=2 noMesh=0 culled=0
10-28 21:22:44.955  2183  2251 I Perf    : fps=3.8 worst=4 drawn=11/17 queue=2 build=32ms verts=28k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=11 noMesh=0 culled=4
10-28 21:22:47.053  2183  2251 I Perf    : fps=3.8 worst=3 drawn=13/28 queue=2 build=19ms verts=33k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=14
10-28 21:22:49.046  2183  2251 I Perf    : fps=4.0 worst=4 drawn=14/37 queue=0 build=34ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:22:51.078  2183  2251 I Perf    : fps=3.9 worst=4 drawn=14/37 queue=0 build=34ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:22:53.148  2183  2251 I Perf    : fps=3.9 worst=4 drawn=14/37 queue=0 build=34ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:22:55.164  2183  2251 I Perf    : fps=4.0 worst=4 drawn=14/37 queue=0 build=34ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:22:57.204  2183  2251 I Perf    : fps=3.9 worst=4 drawn=14/37 queue=0 build=34ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:22:59.220  2183  2251 I Perf    : fps=4.0 worst=4 drawn=14/37 queue=0 build=34ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:23:01.263  2183  2251 I Perf    : fps=3.9 worst=4 drawn=14/37 queue=0 build=34ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:23:03.329  2183  2251 I Perf    : fps=3.9 worst=4 drawn=14/37 queue=0 build=34ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:23:05.410  2183  2251 I Perf    : fps=3.8 worst=4 drawn=14/37 queue=0 build=34ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:23:05.935  2474  2474 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 21:23:07.576  2183  2251 I Perf    : fps=3.7 worst=3 drawn=14/37 queue=0 build=34ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:23:09.646  2183  2251 I Perf    : fps=3.9 worst=4 drawn=14/37 queue=0 build=34ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:23:11.745  2183  2251 I Perf    : fps=3.8 worst=4 drawn=14/37 queue=0 build=34ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 21:23:12.153  2488  2488 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 21:23:12.182  2488  2488 I AndroidRuntime: Using default boot image
10-28 21:23:12.182  2488  2488 I AndroidRuntime: Leaving lock profiling enabled
10-28 21:23:12.376  2488  2488 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 21:23:12.982  2488  2488 D AndroidRuntime: Shutting down VM
10-28 21:23:13.825  2183  2251 I Perf    : fps=4.3 worst=5 drawn=16/37 queue=0 build=34ms verts=40k cam=0.5,1.6,0.5 yaw=2.79 pitch=0.00 torch=1 ready=true drawn=16 noMesh=0 culled=21
10-28 21:23:15.917  2183  2251 I Perf    : fps=4.8 worst=5 drawn=16/37 queue=0 build=34ms verts=40k cam=0.5,1.6,0.5 yaw=2.79 pitch=0.00 torch=1 ready=true drawn=16 noMesh=0 culled=21
10-28 21:23:17.791  2514  2514 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 21:23:17.802  2514  2514 I AndroidRuntime: Using default boot image
10-28 21:23:17.802  2514  2514 I AndroidRuntime: Leaving lock profiling enabled
10-28 21:23:17.946  2514  2514 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 21:23:18.095  2514  2514 D AndroidRuntime: Shutting down VM
10-28 21:23:18.098  2183  2251 I Perf    : fps=4.6 worst=4 drawn=16/37 queue=0 build=34ms verts=40k cam=0.5,1.6,0.5 yaw=2.79 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 21:23:20.177  2183  2251 I Perf    : fps=4.8 worst=5 drawn=16/37 queue=0 build=34ms verts=40k cam=0.5,1.6,0.5 yaw=2.79 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 21:23:22.313  2183  2251 I Perf    : fps=4.7 worst=5 drawn=16/37 queue=0 build=34ms verts=40k cam=0.5,1.6,0.5 yaw=2.79 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 21:23:23.094  2531  2531 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 21:23:23.100  2531  2531 I AndroidRuntime: Using default boot image
10-28 21:23:23.100  2531  2531 I AndroidRuntime: Leaving lock profiling enabled
10-28 21:23:23.302  2531  2531 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 21:23:24.488  2183  2251 I Perf    : fps=4.6 worst=5 drawn=16/37 queue=0 build=34ms verts=40k cam=0.4,1.7,0.9 yaw=2.79 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 21:23:24.868  2531  2531 D AndroidRuntime: Shutting down VM
10-28 21:23:26.651  2183  2251 I Perf    : fps=4.6 worst=4 drawn=16/37 queue=0 build=34ms verts=40k cam=0.1,1.6,1.6 yaw=2.79 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 21:23:28.788  2183  2251 I Perf    : fps=4.7 worst=5 drawn=16/37 queue=0 build=34ms verts=40k cam=0.1,1.6,1.6 yaw=2.79 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 21:23:30.397  2549  2549 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 21:23:30.848  2183  2251 I Perf    : fps=4.4 worst=4 drawn=16/37 queue=0 build=34ms verts=40k cam=0.1,1.6,1.6 yaw=2.79 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 21:23:32.885  2183  2251 I Perf    : fps=4.4 worst=4 drawn=16/37 queue=0 build=34ms verts=40k cam=0.1,1.6,1.6 yaw=2.79 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 21:23:35.071  2183  2251 I Perf    : fps=4.6 worst=4 drawn=16/37 queue=0 build=34ms verts=40k cam=0.1,1.6,1.6 yaw=2.79 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
+ '[' -z 2183 ']'
+ grep -qa 'FATAL EXCEPTION' emulator-shots/logcat.txt
+ '[' 0 '!=' 0 ']'
+ echo 'OK: the app is alive, rendering, and the touch controls work'
OK: the app is alive, rendering, and the touch controls work
```
