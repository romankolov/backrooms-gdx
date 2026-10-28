# Emulator smoke test diagnostics

Commit: ffd7be8f6369b932940da1d7e865d39fb5800059
Outcome: success

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 28 07:42 /dev/kvm
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
INFO         | The active JSON Web Key Sets can be found here: /run/user/1001/avd/running/3146/jwks/80485324-ec62-411e-b02b-8ae9bc084c2e/active.jwk
INFO         | Scanning /run/user/1001/avd/running/3146/jwks/80485324-ec62-411e-b02b-8ae9bc084c2e for jwk keys.
INFO         | Started GRPC server at 127.0.0.1:8554, security: Local, auth: +token
INFO         | Advertising in: /run/user/1001/avd/running/pid_3146.ini
INFO         | Setting display: 0 configuration to: 1080x1920, dpi: 420x420 
INFO         | setDisplayActiveConfig: id:0, 1080x1920
INFO         | emulatorSetupEnvironment: Environment scene is not required
USER_INFO    | Emulator is performing a full startup. This may take upto two minutes, or more.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
INFO         | Boot completed in 26557 ms
INFO         | Increasing screen off timeout, logcat buffer size to 2M.
```
## Logcat (filtered)
```
10-28 07:48:54.444   505   545 W ActivityManager: Slow operation: 67ms so far, now at startProcess: starting to update pids map
10-28 07:48:54.445   505   545 W ActivityManager: Slow operation: 68ms so far, now at startProcess: done updating pids map
10-28 07:48:54.667   505  1082 I ActivityManager: Finished processing BOOT_COMPLETED for u0
10-28 07:48:54.668   505  1082 I ActivityManager: Killing 1809:com.android.provision/u0a116 (adj 985): empty #17
10-28 07:48:56.367   424  2141 I iorapd  : Perfetto TraceBuffer saved to file: /data/misc/iorapd/com.backrooms.gdx.debug/1/com.backrooms.gdx.android.AndroidLauncher/raw_traces/1793173736367573678.perfetto_trace.pb
10-28 07:49:09.117   505   543 I ActivityManager: Killing 1296:com.android.cellbroadcastreceiver/u0a64 (adj 985): empty #17
10-28 07:49:12.845   505   535 I ActivityManager: Killing 2035:com.android.calendar/u0a108 (adj 985): empty #17
10-28 07:49:12.845   505   535 I ActivityManager: Killing 1982:com.android.quicksearchbox/u0a104 (adj 985): empty #18
10-28 07:49:14.751  2391  2391 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 07:49:14.775  2391  2391 I AndroidRuntime: Using default boot image
10-28 07:49:14.775  2391  2391 I AndroidRuntime: Leaving lock profiling enabled
10-28 07:49:14.933  2391  2391 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 07:49:14.938  2391  2391 D AndroidRuntime: Shutting down VM
10-28 07:49:14.943  2391  2403 I AndroidRuntime: NOTE: attach of thread 'Binder:2391_2' failed
10-28 07:49:15.489  2146  2194 I BackroomsGL: world shader compiled
10-28 07:49:15.541  2146  2194 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 07:49:15.541  2146  2194 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 07:49:17.474  2146  2194 I Perf    : fps=4.8 worst=33 drawn=5/8 queue=2 build=34ms verts=13k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=5 noMesh=0 culled=2
10-28 07:49:18.008   505  1240 I ActivityManager: Killing 2058:com.android.camera2/u0a109 (adj 975): empty #17
10-28 07:49:19.584  2146  2194 I Perf    : fps=3.3 worst=3 drawn=11/20 queue=2 build=47ms verts=28k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=11 noMesh=0 culled=7
10-28 07:49:21.666  2146  2194 I Perf    : fps=3.4 worst=3 drawn=14/30 queue=2 build=19ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=16
10-28 07:49:23.801  2146  2194 I Perf    : fps=3.3 worst=3 drawn=14/37 queue=0 build=30ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:49:25.833  2146  2194 I Perf    : fps=3.4 worst=3 drawn=14/37 queue=0 build=30ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:49:27.897  2146  2194 I Perf    : fps=3.4 worst=3 drawn=14/37 queue=0 build=30ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:49:29.917  2146  2194 I Perf    : fps=3.5 worst=3 drawn=14/37 queue=0 build=30ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:49:32.015  2146  2194 I Perf    : fps=3.3 worst=3 drawn=14/37 queue=0 build=30ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:49:34.076  2146  2194 I Perf    : fps=3.4 worst=3 drawn=14/37 queue=0 build=30ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:49:36.172  2146  2194 I Perf    : fps=3.3 worst=3 drawn=14/37 queue=0 build=30ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:49:38.319  2146  2194 I Perf    : fps=3.3 worst=3 drawn=14/37 queue=0 build=30ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:49:40.447  2146  2194 I Perf    : fps=3.3 worst=3 drawn=14/37 queue=0 build=30ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:49:42.643  2146  2194 I Perf    : fps=3.2 worst=3 drawn=14/37 queue=0 build=30ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:49:44.791  2146  2194 I Perf    : fps=3.3 worst=3 drawn=14/37 queue=0 build=30ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:49:46.877  2438  2438 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 07:49:46.897  2438  2438 I AndroidRuntime: Using default boot image
10-28 07:49:46.897  2438  2438 I AndroidRuntime: Leaving lock profiling enabled
10-28 07:49:46.989  2146  2194 I Perf    : fps=3.2 worst=3 drawn=14/37 queue=0 build=30ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:49:47.024  2438  2438 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 07:49:47.751  2438  2438 D AndroidRuntime: Shutting down VM
10-28 07:49:49.168  2146  2194 I Perf    : fps=4.1 worst=5 drawn=16/37 queue=0 build=30ms verts=42k cam=1.5,1.6,1.5 yaw=2.68 pitch=0.00 torch=1 ready=true drawn=16 noMesh=0 culled=21
10-28 07:49:51.373  2146  2194 I Perf    : fps=4.5 worst=5 drawn=16/37 queue=0 build=30ms verts=42k cam=1.5,1.6,1.5 yaw=2.68 pitch=0.00 torch=1 ready=true drawn=16 noMesh=0 culled=21
10-28 07:49:52.425  2455  2455 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 07:49:52.427  2455  2455 I AndroidRuntime: Using default boot image
10-28 07:49:52.427  2455  2455 I AndroidRuntime: Leaving lock profiling enabled
10-28 07:49:52.496  2455  2455 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 07:49:52.594  2455  2455 D AndroidRuntime: Shutting down VM
10-28 07:49:52.607  2455  2467 I AndroidRuntime: NOTE: attach of thread 'Binder:2455_2' failed
10-28 07:49:53.391  2146  2194 I Perf    : fps=4.5 worst=4 drawn=16/37 queue=0 build=30ms verts=42k cam=1.5,1.6,1.5 yaw=2.68 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 07:49:55.558  2146  2194 I Perf    : fps=4.6 worst=5 drawn=16/37 queue=0 build=30ms verts=42k cam=1.5,1.6,1.5 yaw=2.68 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 07:49:57.754  2146  2194 I Perf    : fps=4.6 worst=4 drawn=16/37 queue=0 build=30ms verts=42k cam=1.5,1.6,1.5 yaw=2.68 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 07:49:57.767  2472  2472 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 07:49:57.805  2472  2472 I AndroidRuntime: Using default boot image
10-28 07:49:57.805  2472  2472 I AndroidRuntime: Leaving lock profiling enabled
10-28 07:49:57.875  2472  2472 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 07:49:59.656  2472  2472 D AndroidRuntime: Shutting down VM
10-28 07:49:59.789  2146  2194 I Perf    : fps=4.4 worst=4 drawn=15/37 queue=0 build=30ms verts=39k cam=0.9,1.6,2.7 yaw=2.68 pitch=0.00 torch=0 ready=true drawn=15 noMesh=0 culled=22
10-28 07:50:01.983  2146  2194 I Perf    : fps=4.6 worst=5 drawn=15/37 queue=0 build=30ms verts=39k cam=0.8,1.6,2.9 yaw=2.68 pitch=0.00 torch=0 ready=true drawn=15 noMesh=0 culled=22
10-28 07:50:03.995  2146  2194 I Perf    : fps=4.5 worst=4 drawn=15/37 queue=0 build=30ms verts=39k cam=0.8,1.6,2.9 yaw=2.68 pitch=0.00 torch=0 ready=true drawn=15 noMesh=0 culled=22
10-28 07:50:06.180  2146  2194 I Perf    : fps=4.6 worst=5 drawn=15/37 queue=0 build=30ms verts=39k cam=0.8,1.6,2.9 yaw=2.68 pitch=0.00 torch=0 ready=true drawn=15 noMesh=0 culled=22
10-28 07:50:08.185  2146  2194 I Perf    : fps=4.5 worst=4 drawn=15/37 queue=0 build=30ms verts=39k cam=0.8,1.6,2.9 yaw=2.68 pitch=0.00 torch=0 ready=true drawn=15 noMesh=0 culled=22
10-28 07:50:10.195  2146  2194 I Perf    : fps=4.5 worst=4 drawn=15/37 queue=0 build=30ms verts=39k cam=0.8,1.6,2.9 yaw=2.68 pitch=0.00 torch=0 ready=true drawn=15 noMesh=0 culled=22
```
## Step log
```
rawn=5 noMesh=0 culled=2
10-28 07:49:19.584  2146  2194 I Perf    : fps=3.3 worst=3 drawn=11/20 queue=2 build=47ms verts=28k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=11 noMesh=0 culled=7
10-28 07:49:21.666  2146  2194 I Perf    : fps=3.4 worst=3 drawn=14/30 queue=2 build=19ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=16
10-28 07:49:23.801  2146  2194 I Perf    : fps=3.3 worst=3 drawn=14/37 queue=0 build=30ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:49:25.833  2146  2194 I Perf    : fps=3.4 worst=3 drawn=14/37 queue=0 build=30ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:49:27.897  2146  2194 I Perf    : fps=3.4 worst=3 drawn=14/37 queue=0 build=30ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:49:29.917  2146  2194 I Perf    : fps=3.5 worst=3 drawn=14/37 queue=0 build=30ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:49:32.015  2146  2194 I Perf    : fps=3.3 worst=3 drawn=14/37 queue=0 build=30ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:49:34.076  2146  2194 I Perf    : fps=3.4 worst=3 drawn=14/37 queue=0 build=30ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:49:36.172  2146  2194 I Perf    : fps=3.3 worst=3 drawn=14/37 queue=0 build=30ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:49:38.319  2146  2194 I Perf    : fps=3.3 worst=3 drawn=14/37 queue=0 build=30ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:49:40.447  2146  2194 I Perf    : fps=3.3 worst=3 drawn=14/37 queue=0 build=30ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:49:41.051  2414  2414 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 07:49:42.643  2146  2194 I Perf    : fps=3.2 worst=3 drawn=14/37 queue=0 build=30ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:49:44.791  2146  2194 I Perf    : fps=3.3 worst=3 drawn=14/37 queue=0 build=30ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:49:46.877  2438  2438 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 07:49:46.897  2438  2438 I AndroidRuntime: Using default boot image
10-28 07:49:46.897  2438  2438 I AndroidRuntime: Leaving lock profiling enabled
10-28 07:49:46.989  2146  2194 I Perf    : fps=3.2 worst=3 drawn=14/37 queue=0 build=30ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:49:47.024  2438  2438 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 07:49:47.751  2438  2438 D AndroidRuntime: Shutting down VM
10-28 07:49:49.168  2146  2194 I Perf    : fps=4.1 worst=5 drawn=16/37 queue=0 build=30ms verts=42k cam=1.5,1.6,1.5 yaw=2.68 pitch=0.00 torch=1 ready=true drawn=16 noMesh=0 culled=21
10-28 07:49:51.373  2146  2194 I Perf    : fps=4.5 worst=5 drawn=16/37 queue=0 build=30ms verts=42k cam=1.5,1.6,1.5 yaw=2.68 pitch=0.00 torch=1 ready=true drawn=16 noMesh=0 culled=21
10-28 07:49:52.425  2455  2455 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 07:49:52.427  2455  2455 I AndroidRuntime: Using default boot image
10-28 07:49:52.427  2455  2455 I AndroidRuntime: Leaving lock profiling enabled
10-28 07:49:52.496  2455  2455 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 07:49:52.594  2455  2455 D AndroidRuntime: Shutting down VM
10-28 07:49:52.607  2455  2467 I AndroidRuntime: NOTE: attach of thread 'Binder:2455_2' failed
10-28 07:49:53.391  2146  2194 I Perf    : fps=4.5 worst=4 drawn=16/37 queue=0 build=30ms verts=42k cam=1.5,1.6,1.5 yaw=2.68 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 07:49:55.558  2146  2194 I Perf    : fps=4.6 worst=5 drawn=16/37 queue=0 build=30ms verts=42k cam=1.5,1.6,1.5 yaw=2.68 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 07:49:57.754  2146  2194 I Perf    : fps=4.6 worst=4 drawn=16/37 queue=0 build=30ms verts=42k cam=1.5,1.6,1.5 yaw=2.68 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 07:49:57.767  2472  2472 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 07:49:57.805  2472  2472 I AndroidRuntime: Using default boot image
10-28 07:49:57.805  2472  2472 I AndroidRuntime: Leaving lock profiling enabled
10-28 07:49:57.875  2472  2472 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 07:49:59.656  2472  2472 D AndroidRuntime: Shutting down VM
10-28 07:49:59.789  2146  2194 I Perf    : fps=4.4 worst=4 drawn=15/37 queue=0 build=30ms verts=39k cam=0.9,1.6,2.7 yaw=2.68 pitch=0.00 torch=0 ready=true drawn=15 noMesh=0 culled=22
10-28 07:50:01.983  2146  2194 I Perf    : fps=4.6 worst=5 drawn=15/37 queue=0 build=30ms verts=39k cam=0.8,1.6,2.9 yaw=2.68 pitch=0.00 torch=0 ready=true drawn=15 noMesh=0 culled=22
10-28 07:50:03.995  2146  2194 I Perf    : fps=4.5 worst=4 drawn=15/37 queue=0 build=30ms verts=39k cam=0.8,1.6,2.9 yaw=2.68 pitch=0.00 torch=0 ready=true drawn=15 noMesh=0 culled=22
10-28 07:50:05.296  2492  2492 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 07:50:06.180  2146  2194 I Perf    : fps=4.6 worst=5 drawn=15/37 queue=0 build=30ms verts=39k cam=0.8,1.6,2.9 yaw=2.68 pitch=0.00 torch=0 ready=true drawn=15 noMesh=0 culled=22
10-28 07:50:08.185  2146  2194 I Perf    : fps=4.5 worst=4 drawn=15/37 queue=0 build=30ms verts=39k cam=0.8,1.6,2.9 yaw=2.68 pitch=0.00 torch=0 ready=true drawn=15 noMesh=0 culled=22
10-28 07:50:10.195  2146  2194 I Perf    : fps=4.5 worst=4 drawn=15/37 queue=0 build=30ms verts=39k cam=0.8,1.6,2.9 yaw=2.68 pitch=0.00 torch=0 ready=true drawn=15 noMesh=0 culled=22
+ '[' -z 2146 ']'
+ grep -qa 'FATAL EXCEPTION' emulator-shots/logcat.txt
+ '[' 0 '!=' 0 ']'
+ echo 'OK: the app is alive, rendering, and the touch controls work'
OK: the app is alive, rendering, and the touch controls work
```
