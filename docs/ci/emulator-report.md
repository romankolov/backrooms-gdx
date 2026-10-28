# Emulator smoke test diagnostics

Commit: 0df96bb8e510cb8c652516d9572759dc7c1a378b
Outcome: success

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 28 11:46 /dev/kvm
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
INFO         | The active JSON Web Key Sets can be found here: /run/user/1001/avd/running/2969/jwks/e4a4a5e5-67ca-4098-b2d0-f09a2ee7c92e/active.jwk
INFO         | Scanning /run/user/1001/avd/running/2969/jwks/e4a4a5e5-67ca-4098-b2d0-f09a2ee7c92e for jwk keys.
INFO         | Started GRPC server at 127.0.0.1:8554, security: Local, auth: +token
INFO         | Advertising in: /run/user/1001/avd/running/pid_2969.ini
INFO         | Setting display: 0 configuration to: 1080x1920, dpi: 420x420 
INFO         | setDisplayActiveConfig: id:0, 1080x1920
INFO         | emulatorSetupEnvironment: Environment scene is not required
USER_INFO    | Emulator is performing a full startup. This may take upto two minutes, or more.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
INFO         | Boot completed in 26425 ms
INFO         | Increasing screen off timeout, logcat buffer size to 2M.
```
## Logcat (filtered)
```
10-28 11:49:14.820   504   638 I ActivityManager: Finished processing BOOT_COMPLETED for u0
10-28 11:49:14.820   504   638 I ActivityManager: Killing 1908:com.android.printspooler/u0a87 (adj 985): empty #17
10-28 11:49:14.857   504   544 I ActivityManager: Start proc 2333:com.android.quicksearchbox/u0a101 for broadcast {com.android.quicksearchbox/com.android.quicksearchbox.SearchWidgetProvider}
10-28 11:49:15.057   504   638 I ActivityManager: Killing 1257:com.android.cellbroadcastreceiver.module/u0a122 (adj 985): empty #17
10-28 11:49:16.930   429  2113 I iorapd  : Perfetto TraceBuffer saved to file: /data/misc/iorapd/com.backrooms.gdx.debug/1/com.backrooms.gdx.android.AndroidLauncher/raw_traces/1793188156930020538.perfetto_trace.pb
10-28 11:49:24.663   504   544 I ActivityManager: Start proc 2378:com.android.cellbroadcastreceiver.module/u0a122 for broadcast {com.android.cellbroadcastreceiver.module/com.android.cellbroadcastreceiver.CellBroadcastReceiver}
10-28 11:49:24.848   504   638 I ActivityManager: Killing 2006:com.android.calendar/u0a108 (adj 985): empty #17
10-28 11:49:29.157   504   542 I ActivityManager: Killing 1277:com.android.cellbroadcastreceiver/u0a65 (adj 985): empty #17
10-28 11:49:33.119   504   532 I ActivityManager: Killing 2058:com.android.contacts/u0a98 (adj 985): empty #17
10-28 11:49:33.127   504   532 I ActivityManager: Killing 2033:com.android.camera2/u0a110 (adj 985): empty #18
10-28 11:49:34.999  2411  2411 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 11:49:35.018  2411  2411 I AndroidRuntime: Using default boot image
10-28 11:49:35.018  2411  2411 I AndroidRuntime: Leaving lock profiling enabled
10-28 11:49:35.183  2411  2411 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 11:49:35.197  2411  2411 D AndroidRuntime: Shutting down VM
10-28 11:49:35.222  2411  2422 I AndroidRuntime: NOTE: attach of thread 'Binder:2411_1' failed
10-28 11:49:35.751  2131  2182 I BackroomsGL: world shader compiled
10-28 11:49:35.804  2131  2182 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 11:49:35.804  2131  2182 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 11:49:37.771  2131  2182 I Perf    : fps=5.2 worst=45 drawn=6/10 queue=2 build=28ms verts=15k cam=2.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=6 noMesh=0 culled=2
10-28 11:49:40.006  2131  2182 I Perf    : fps=4.5 worst=4 drawn=14/25 queue=2 build=187ms verts=36k cam=2.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=10
10-28 11:49:40.130   504   831 I ActivityManager: Killing 1822:com.android.provision/u0a116 (adj 975): empty #17
10-28 11:49:42.048  2131  2182 I Perf    : fps=4.4 worst=4 drawn=18/37 queue=0 build=31ms verts=46k cam=2.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 11:49:44.236  2131  2182 I Perf    : fps=4.6 worst=5 drawn=18/37 queue=0 build=31ms verts=46k cam=2.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 11:49:46.439  2131  2182 I Perf    : fps=4.5 worst=4 drawn=18/37 queue=0 build=31ms verts=46k cam=2.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 11:49:48.580  2131  2182 I Perf    : fps=4.7 worst=5 drawn=18/37 queue=0 build=31ms verts=46k cam=2.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 11:49:50.757  2131  2182 I Perf    : fps=4.6 worst=4 drawn=18/37 queue=0 build=31ms verts=46k cam=2.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 11:49:52.908  2131  2182 I Perf    : fps=4.7 worst=5 drawn=18/37 queue=0 build=31ms verts=46k cam=2.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 11:49:54.919  2131  2182 I Perf    : fps=4.5 worst=4 drawn=18/37 queue=0 build=31ms verts=46k cam=2.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 11:49:56.962  2131  2182 I Perf    : fps=4.4 worst=5 drawn=18/37 queue=0 build=31ms verts=46k cam=2.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 11:49:58.997  2131  2182 I Perf    : fps=4.4 worst=4 drawn=18/37 queue=0 build=31ms verts=46k cam=2.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 11:50:01.085  2131  2182 I Perf    : fps=4.3 worst=4 drawn=18/37 queue=0 build=31ms verts=46k cam=2.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 11:50:03.135  2131  2182 I Perf    : fps=4.4 worst=4 drawn=18/37 queue=0 build=31ms verts=46k cam=2.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 11:50:05.394  2131  2182 I Perf    : fps=4.4 worst=4 drawn=18/37 queue=0 build=31ms verts=46k cam=2.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 11:50:07.244  2448  2448 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 11:50:07.268  2448  2448 I AndroidRuntime: Using default boot image
10-28 11:50:07.269  2448  2448 I AndroidRuntime: Leaving lock profiling enabled
10-28 11:50:07.479  2448  2448 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 11:50:07.492  2131  2182 I Perf    : fps=4.3 worst=4 drawn=18/37 queue=0 build=31ms verts=46k cam=2.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 11:50:08.093  2448  2448 D AndroidRuntime: Shutting down VM
10-28 11:50:09.705  2131  2182 I Perf    : fps=4.1 worst=4 drawn=13/37 queue=0 build=31ms verts=33k cam=2.5,1.6,-1.5 yaw=2.84 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 11:50:11.880  2131  2182 I Perf    : fps=4.1 worst=4 drawn=13/37 queue=0 build=31ms verts=33k cam=2.5,1.6,-1.5 yaw=2.84 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 11:50:12.808  2475  2475 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 11:50:12.826  2475  2475 I AndroidRuntime: Using default boot image
10-28 11:50:12.826  2475  2475 I AndroidRuntime: Leaving lock profiling enabled
10-28 11:50:12.910  2475  2475 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 11:50:12.952  2475  2475 D AndroidRuntime: Shutting down VM
10-28 11:50:14.111  2131  2182 I Perf    : fps=4.0 worst=4 drawn=13/37 queue=0 build=31ms verts=33k cam=2.5,1.6,-1.5 yaw=2.84 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 11:50:16.283  2131  2182 I Perf    : fps=4.1 worst=4 drawn=13/37 queue=0 build=31ms verts=33k cam=2.5,1.6,-1.5 yaw=2.84 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 11:50:18.179  2492  2492 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 11:50:18.211  2492  2492 I AndroidRuntime: Using default boot image
10-28 11:50:18.211  2492  2492 I AndroidRuntime: Leaving lock profiling enabled
10-28 11:50:18.302  2492  2492 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 11:50:18.462  2131  2182 I Perf    : fps=4.1 worst=4 drawn=13/37 queue=0 build=31ms verts=33k cam=2.5,1.6,-1.5 yaw=2.84 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 11:50:19.894  2492  2492 D AndroidRuntime: Shutting down VM
10-28 11:50:20.574  2131  2182 I Perf    : fps=4.3 worst=5 drawn=13/37 queue=0 build=31ms verts=33k cam=2.2,1.6,-0.6 yaw=2.84 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 11:50:22.630  2131  2182 I Perf    : fps=4.4 worst=4 drawn=13/37 queue=0 build=31ms verts=33k cam=2.2,1.6,-0.6 yaw=2.84 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 11:50:24.677  2131  2182 I Perf    : fps=4.4 worst=4 drawn=13/37 queue=0 build=31ms verts=33k cam=2.2,1.6,-0.6 yaw=2.84 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 11:50:26.749  2131  2182 I Perf    : fps=4.3 worst=4 drawn=13/37 queue=0 build=31ms verts=33k cam=2.2,1.6,-0.6 yaw=2.84 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 11:50:28.846  2131  2182 I Perf    : fps=4.3 worst=4 drawn=13/37 queue=0 build=31ms verts=33k cam=2.2,1.6,-0.6 yaw=2.84 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
```
## Step log
```
ut: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 11:49:37.771  2131  2182 I Perf    : fps=5.2 worst=45 drawn=6/10 queue=2 build=28ms verts=15k cam=2.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=6 noMesh=0 culled=2
10-28 11:49:40.006  2131  2182 I Perf    : fps=4.5 worst=4 drawn=14/25 queue=2 build=187ms verts=36k cam=2.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=10
10-28 11:49:42.048  2131  2182 I Perf    : fps=4.4 worst=4 drawn=18/37 queue=0 build=31ms verts=46k cam=2.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 11:49:44.236  2131  2182 I Perf    : fps=4.6 worst=5 drawn=18/37 queue=0 build=31ms verts=46k cam=2.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 11:49:46.439  2131  2182 I Perf    : fps=4.5 worst=4 drawn=18/37 queue=0 build=31ms verts=46k cam=2.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 11:49:48.580  2131  2182 I Perf    : fps=4.7 worst=5 drawn=18/37 queue=0 build=31ms verts=46k cam=2.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 11:49:50.757  2131  2182 I Perf    : fps=4.6 worst=4 drawn=18/37 queue=0 build=31ms verts=46k cam=2.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 11:49:52.908  2131  2182 I Perf    : fps=4.7 worst=5 drawn=18/37 queue=0 build=31ms verts=46k cam=2.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 11:49:54.919  2131  2182 I Perf    : fps=4.5 worst=4 drawn=18/37 queue=0 build=31ms verts=46k cam=2.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 11:49:56.962  2131  2182 I Perf    : fps=4.4 worst=5 drawn=18/37 queue=0 build=31ms verts=46k cam=2.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 11:49:58.997  2131  2182 I Perf    : fps=4.4 worst=4 drawn=18/37 queue=0 build=31ms verts=46k cam=2.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 11:50:01.055  2434  2434 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 11:50:01.085  2131  2182 I Perf    : fps=4.3 worst=4 drawn=18/37 queue=0 build=31ms verts=46k cam=2.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 11:50:03.135  2131  2182 I Perf    : fps=4.4 worst=4 drawn=18/37 queue=0 build=31ms verts=46k cam=2.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 11:50:05.394  2131  2182 I Perf    : fps=4.4 worst=4 drawn=18/37 queue=0 build=31ms verts=46k cam=2.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 11:50:07.244  2448  2448 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 11:50:07.268  2448  2448 I AndroidRuntime: Using default boot image
10-28 11:50:07.269  2448  2448 I AndroidRuntime: Leaving lock profiling enabled
10-28 11:50:07.479  2448  2448 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 11:50:07.492  2131  2182 I Perf    : fps=4.3 worst=4 drawn=18/37 queue=0 build=31ms verts=46k cam=2.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 11:50:08.093  2448  2448 D AndroidRuntime: Shutting down VM
10-28 11:50:09.705  2131  2182 I Perf    : fps=4.1 worst=4 drawn=13/37 queue=0 build=31ms verts=33k cam=2.5,1.6,-1.5 yaw=2.84 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 11:50:11.880  2131  2182 I Perf    : fps=4.1 worst=4 drawn=13/37 queue=0 build=31ms verts=33k cam=2.5,1.6,-1.5 yaw=2.84 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 11:50:12.808  2475  2475 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 11:50:12.826  2475  2475 I AndroidRuntime: Using default boot image
10-28 11:50:12.826  2475  2475 I AndroidRuntime: Leaving lock profiling enabled
10-28 11:50:12.910  2475  2475 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 11:50:12.952  2475  2475 D AndroidRuntime: Shutting down VM
10-28 11:50:14.111  2131  2182 I Perf    : fps=4.0 worst=4 drawn=13/37 queue=0 build=31ms verts=33k cam=2.5,1.6,-1.5 yaw=2.84 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 11:50:16.283  2131  2182 I Perf    : fps=4.1 worst=4 drawn=13/37 queue=0 build=31ms verts=33k cam=2.5,1.6,-1.5 yaw=2.84 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 11:50:18.179  2492  2492 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 11:50:18.211  2492  2492 I AndroidRuntime: Using default boot image
10-28 11:50:18.211  2492  2492 I AndroidRuntime: Leaving lock profiling enabled
10-28 11:50:18.302  2492  2492 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 11:50:18.462  2131  2182 I Perf    : fps=4.1 worst=4 drawn=13/37 queue=0 build=31ms verts=33k cam=2.5,1.6,-1.5 yaw=2.84 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 11:50:19.894  2492  2492 D AndroidRuntime: Shutting down VM
10-28 11:50:20.574  2131  2182 I Perf    : fps=4.3 worst=5 drawn=13/37 queue=0 build=31ms verts=33k cam=2.2,1.6,-0.6 yaw=2.84 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 11:50:22.630  2131  2182 I Perf    : fps=4.4 worst=4 drawn=13/37 queue=0 build=31ms verts=33k cam=2.2,1.6,-0.6 yaw=2.84 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 11:50:24.677  2131  2182 I Perf    : fps=4.4 worst=4 drawn=13/37 queue=0 build=31ms verts=33k cam=2.2,1.6,-0.6 yaw=2.84 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 11:50:24.708  2509  2509 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 11:50:26.749  2131  2182 I Perf    : fps=4.3 worst=4 drawn=13/37 queue=0 build=31ms verts=33k cam=2.2,1.6,-0.6 yaw=2.84 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 11:50:28.846  2131  2182 I Perf    : fps=4.3 worst=4 drawn=13/37 queue=0 build=31ms verts=33k cam=2.2,1.6,-0.6 yaw=2.84 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
+ '[' -z 2131 ']'
+ grep -qa 'FATAL EXCEPTION' emulator-shots/logcat.txt
+ '[' 0 '!=' 0 ']'
+ echo 'OK: the app is alive, rendering, and the touch controls work'
OK: the app is alive, rendering, and the touch controls work
```
