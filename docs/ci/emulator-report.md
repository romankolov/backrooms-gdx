# Emulator smoke test diagnostics

Commit: ff00fa36ef46828b62411287fea7aded2c0aa8e6
Outcome: success

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 29 00:43 /dev/kvm
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
INFO         | The active JSON Web Key Sets can be found here: /run/user/1001/avd/running/2761/jwks/0a4d0f6a-3f98-421c-9aa4-2ae0284c0da5/active.jwk
INFO         | Scanning /run/user/1001/avd/running/2761/jwks/0a4d0f6a-3f98-421c-9aa4-2ae0284c0da5 for jwk keys.
INFO         | Started GRPC server at 127.0.0.1:8554, security: Local, auth: +token
INFO         | Advertising in: /run/user/1001/avd/running/pid_2761.ini
INFO         | Setting display: 0 configuration to: 1080x1920, dpi: 420x420 
INFO         | setDisplayActiveConfig: id:0, 1080x1920
INFO         | emulatorSetupEnvironment: Environment scene is not required
USER_INFO    | Emulator is performing a full startup. This may take upto two minutes, or more.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
INFO         | Boot completed in 26179 ms
INFO         | Increasing screen off timeout, logcat buffer size to 2M.
```
## Logcat (filtered)
```
10-29 00:46:44.583   504   546 W ActivityManager: Slow operation: 143ms so far, now at startProcess: done updating pids map
10-29 00:46:44.651   504  2213 I ActivityManager: Killing 1847:com.android.localtransport/1000 (adj 985): empty #17
10-29 00:46:44.780   504   546 I ActivityManager: Start proc 2323:com.android.traceur/u0a89 for broadcast {com.android.traceur/com.android.traceur.Receiver}
10-29 00:46:44.972   504  1336 I ActivityManager: Finished processing BOOT_COMPLETED for u0
10-29 00:46:44.973   504  1336 I ActivityManager: Killing 1772:com.android.externalstorage/u0a68 (adj 985): empty #17
10-29 00:46:48.032   434  2157 I iorapd  : Perfetto TraceBuffer saved to file: /data/misc/iorapd/com.backrooms.gdx.debug/1/com.backrooms.gdx.android.AndroidLauncher/raw_traces/1793234808031307298.perfetto_trace.pb
10-29 00:46:59.436   504   544 I ActivityManager: Killing 1278:com.android.cellbroadcastreceiver/u0a65 (adj 985): empty #17
10-29 00:47:05.108   504   536 I ActivityManager: Killing 2002:com.android.calendar/u0a109 (adj 985): empty #17
10-29 00:47:05.108   504   536 I ActivityManager: Killing 1928:com.android.quicksearchbox/u0a102 (adj 985): empty #18
10-29 00:47:05.541  2397  2397 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 00:47:05.564  2397  2397 I AndroidRuntime: Using default boot image
10-29 00:47:05.565  2397  2397 I AndroidRuntime: Leaving lock profiling enabled
10-29 00:47:05.685  2397  2397 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 00:47:05.698  2397  2397 D AndroidRuntime: Shutting down VM
10-29 00:47:06.212  2161  2194 I BackroomsGL: world shader compiled
10-29 00:47:06.848  2161  2194 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-29 00:47:06.848  2161  2194 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-29 00:47:08.326  2161  2194 I Perf    : fps=1.8 worst=4 drawn=4/6 queue=2 build=15ms verts=12k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=4 noMesh=0 culled=0
10-29 00:47:10.078   504  2071 I ActivityManager: Killing 2043:com.android.camera2/u0a111 (adj 975): empty #17
10-29 00:47:10.391  2161  2194 I Perf    : fps=4.3 worst=4 drawn=11/24 queue=2 build=83ms verts=33k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=11 noMesh=0 culled=11
10-29 00:47:12.571  2161  2194 I Perf    : fps=4.1 worst=4 drawn=14/37 queue=0 build=26ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:47:14.732  2161  2194 I Perf    : fps=4.2 worst=4 drawn=14/37 queue=0 build=26ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:47:16.943  2161  2194 I Perf    : fps=4.1 worst=4 drawn=14/37 queue=0 build=26ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:47:19.113  2161  2194 I Perf    : fps=4.1 worst=4 drawn=14/37 queue=0 build=26ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:47:21.254  2161  2194 I Perf    : fps=4.2 worst=4 drawn=14/37 queue=0 build=26ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:47:23.425  2161  2194 I Perf    : fps=4.1 worst=4 drawn=14/37 queue=0 build=26ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:47:25.586  2161  2194 I Perf    : fps=4.2 worst=4 drawn=14/37 queue=0 build=26ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:47:27.747  2161  2194 I Perf    : fps=4.2 worst=4 drawn=14/37 queue=0 build=26ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:47:29.866  2161  2194 I Perf    : fps=4.2 worst=4 drawn=14/37 queue=0 build=26ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:47:32.047  2161  2194 I Perf    : fps=4.1 worst=4 drawn=14/37 queue=0 build=26ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:47:34.080  2161  2194 I Perf    : fps=3.9 worst=4 drawn=14/37 queue=0 build=26ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:47:36.286  2161  2194 I Perf    : fps=4.1 worst=4 drawn=14/37 queue=0 build=26ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:47:38.493  2161  2194 I Perf    : fps=4.1 worst=4 drawn=14/37 queue=0 build=26ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:47:39.057  2450  2450 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 00:47:39.078  2450  2450 I AndroidRuntime: Using default boot image
10-29 00:47:39.078  2450  2450 I AndroidRuntime: Leaving lock profiling enabled
10-29 00:47:39.341  2450  2450 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 00:47:39.948  2450  2450 D AndroidRuntime: Shutting down VM
10-29 00:47:40.586  2161  2194 I Perf    : fps=4.3 worst=5 drawn=17/37 queue=0 build=26ms verts=49k cam=0.5,1.6,0.5 yaw=2.81 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-29 00:47:42.706  2161  2194 I Perf    : fps=4.7 worst=5 drawn=17/37 queue=0 build=26ms verts=49k cam=0.5,1.6,0.5 yaw=2.81 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-29 00:47:44.796  2467  2467 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 00:47:44.798  2467  2467 I AndroidRuntime: Using default boot image
10-29 00:47:44.798  2467  2467 I AndroidRuntime: Leaving lock profiling enabled
10-29 00:47:44.830  2161  2194 I Perf    : fps=4.7 worst=5 drawn=17/37 queue=0 build=26ms verts=49k cam=0.5,1.6,0.5 yaw=2.81 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-29 00:47:44.870  2467  2467 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 00:47:44.945  2467  2467 D AndroidRuntime: Shutting down VM
10-29 00:47:46.988  2161  2194 I Perf    : fps=4.6 worst=4 drawn=17/37 queue=0 build=26ms verts=49k cam=0.5,1.6,0.5 yaw=2.81 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-29 00:47:49.085  2161  2194 I Perf    : fps=4.8 worst=5 drawn=17/37 queue=0 build=26ms verts=49k cam=0.5,1.6,0.5 yaw=2.81 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-29 00:47:49.998  2484  2484 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 00:47:50.010  2484  2484 I AndroidRuntime: Using default boot image
10-29 00:47:50.010  2484  2484 I AndroidRuntime: Leaving lock profiling enabled
10-29 00:47:50.202  2484  2484 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 00:47:51.185  2161  2194 I Perf    : fps=4.7 worst=5 drawn=17/37 queue=0 build=26ms verts=49k cam=0.4,1.7,0.8 yaw=2.81 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-29 00:47:51.732  2484  2484 D AndroidRuntime: Shutting down VM
10-29 00:47:53.347  2161  2194 I Perf    : fps=4.6 worst=5 drawn=16/37 queue=0 build=26ms verts=46k cam=0.0,1.6,1.5 yaw=2.81 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-29 00:47:55.506  2161  2194 I Perf    : fps=4.6 worst=5 drawn=16/37 queue=0 build=26ms verts=46k cam=0.0,1.6,1.5 yaw=2.81 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-29 00:47:57.551  2161  2194 I Perf    : fps=4.4 worst=4 drawn=16/37 queue=0 build=26ms verts=46k cam=0.0,1.6,1.5 yaw=2.81 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-29 00:47:59.680  2161  2194 I Perf    : fps=4.2 worst=4 drawn=16/37 queue=0 build=26ms verts=46k cam=0.0,1.6,1.5 yaw=2.81 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-29 00:48:01.721  2161  2194 I Perf    : fps=4.4 worst=4 drawn=16/37 queue=0 build=26ms verts=46k cam=0.0,1.6,1.5 yaw=2.81 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-29 00:48:03.733  2161  2194 I Perf    : fps=4.5 worst=4 drawn=16/37 queue=0 build=26ms verts=46k cam=0.0,1.6,1.5 yaw=2.81 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
```
## Step log
```
=11/24 queue=2 build=83ms verts=33k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=11 noMesh=0 culled=11
10-29 00:47:12.571  2161  2194 I Perf    : fps=4.1 worst=4 drawn=14/37 queue=0 build=26ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:47:14.732  2161  2194 I Perf    : fps=4.2 worst=4 drawn=14/37 queue=0 build=26ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:47:16.943  2161  2194 I Perf    : fps=4.1 worst=4 drawn=14/37 queue=0 build=26ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:47:19.113  2161  2194 I Perf    : fps=4.1 worst=4 drawn=14/37 queue=0 build=26ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:47:21.254  2161  2194 I Perf    : fps=4.2 worst=4 drawn=14/37 queue=0 build=26ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:47:23.425  2161  2194 I Perf    : fps=4.1 worst=4 drawn=14/37 queue=0 build=26ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:47:25.586  2161  2194 I Perf    : fps=4.2 worst=4 drawn=14/37 queue=0 build=26ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:47:27.747  2161  2194 I Perf    : fps=4.2 worst=4 drawn=14/37 queue=0 build=26ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:47:29.866  2161  2194 I Perf    : fps=4.2 worst=4 drawn=14/37 queue=0 build=26ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:47:31.744  2425  2425 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-29 00:47:32.047  2161  2194 I Perf    : fps=4.1 worst=4 drawn=14/37 queue=0 build=26ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:47:34.080  2161  2194 I Perf    : fps=3.9 worst=4 drawn=14/37 queue=0 build=26ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:47:36.286  2161  2194 I Perf    : fps=4.1 worst=4 drawn=14/37 queue=0 build=26ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:47:38.493  2161  2194 I Perf    : fps=4.1 worst=4 drawn=14/37 queue=0 build=26ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:47:39.057  2450  2450 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 00:47:39.078  2450  2450 I AndroidRuntime: Using default boot image
10-29 00:47:39.078  2450  2450 I AndroidRuntime: Leaving lock profiling enabled
10-29 00:47:39.341  2450  2450 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 00:47:39.948  2450  2450 D AndroidRuntime: Shutting down VM
10-29 00:47:40.586  2161  2194 I Perf    : fps=4.3 worst=5 drawn=17/37 queue=0 build=26ms verts=49k cam=0.5,1.6,0.5 yaw=2.81 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-29 00:47:42.706  2161  2194 I Perf    : fps=4.7 worst=5 drawn=17/37 queue=0 build=26ms verts=49k cam=0.5,1.6,0.5 yaw=2.81 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-29 00:47:44.796  2467  2467 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 00:47:44.798  2467  2467 I AndroidRuntime: Using default boot image
10-29 00:47:44.798  2467  2467 I AndroidRuntime: Leaving lock profiling enabled
10-29 00:47:44.830  2161  2194 I Perf    : fps=4.7 worst=5 drawn=17/37 queue=0 build=26ms verts=49k cam=0.5,1.6,0.5 yaw=2.81 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-29 00:47:44.870  2467  2467 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 00:47:44.945  2467  2467 D AndroidRuntime: Shutting down VM
10-29 00:47:46.988  2161  2194 I Perf    : fps=4.6 worst=4 drawn=17/37 queue=0 build=26ms verts=49k cam=0.5,1.6,0.5 yaw=2.81 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-29 00:47:49.085  2161  2194 I Perf    : fps=4.8 worst=5 drawn=17/37 queue=0 build=26ms verts=49k cam=0.5,1.6,0.5 yaw=2.81 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-29 00:47:49.998  2484  2484 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 00:47:50.010  2484  2484 I AndroidRuntime: Using default boot image
10-29 00:47:50.010  2484  2484 I AndroidRuntime: Leaving lock profiling enabled
10-29 00:47:50.202  2484  2484 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 00:47:51.185  2161  2194 I Perf    : fps=4.7 worst=5 drawn=17/37 queue=0 build=26ms verts=49k cam=0.4,1.7,0.8 yaw=2.81 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-29 00:47:51.732  2484  2484 D AndroidRuntime: Shutting down VM
10-29 00:47:53.347  2161  2194 I Perf    : fps=4.6 worst=5 drawn=16/37 queue=0 build=26ms verts=46k cam=0.0,1.6,1.5 yaw=2.81 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-29 00:47:55.506  2161  2194 I Perf    : fps=4.6 worst=5 drawn=16/37 queue=0 build=26ms verts=46k cam=0.0,1.6,1.5 yaw=2.81 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-29 00:47:56.840  2502  2502 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-29 00:47:57.551  2161  2194 I Perf    : fps=4.4 worst=4 drawn=16/37 queue=0 build=26ms verts=46k cam=0.0,1.6,1.5 yaw=2.81 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-29 00:47:59.680  2161  2194 I Perf    : fps=4.2 worst=4 drawn=16/37 queue=0 build=26ms verts=46k cam=0.0,1.6,1.5 yaw=2.81 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-29 00:48:01.721  2161  2194 I Perf    : fps=4.4 worst=4 drawn=16/37 queue=0 build=26ms verts=46k cam=0.0,1.6,1.5 yaw=2.81 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-29 00:48:03.733  2161  2194 I Perf    : fps=4.5 worst=4 drawn=16/37 queue=0 build=26ms verts=46k cam=0.0,1.6,1.5 yaw=2.81 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
+ '[' -z 2161 ']'
+ grep -qa 'FATAL EXCEPTION' emulator-shots/logcat.txt
+ '[' 0 '!=' 0 ']'
+ echo 'OK: the app is alive, rendering, and the touch controls work'
OK: the app is alive, rendering, and the touch controls work
```
