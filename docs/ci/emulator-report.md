# Emulator smoke test diagnostics

Commit: 929f1ea189b1c7c37b24c926b916ae20ee65ac26
Outcome: success

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 29 00:33 /dev/kvm
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
INFO         | The active JSON Web Key Sets can be found here: /run/user/1001/avd/running/2764/jwks/107047f7-7437-460d-b565-e8076ba782ec/active.jwk
INFO         | Scanning /run/user/1001/avd/running/2764/jwks/107047f7-7437-460d-b565-e8076ba782ec for jwk keys.
INFO         | Started GRPC server at 127.0.0.1:8554, security: Local, auth: +token
INFO         | Advertising in: /run/user/1001/avd/running/pid_2764.ini
INFO         | Setting display: 0 configuration to: 1080x1920, dpi: 420x420 
INFO         | setDisplayActiveConfig: id:0, 1080x1920
INFO         | emulatorSetupEnvironment: Environment scene is not required
USER_INFO    | Emulator is performing a full startup. This may take upto two minutes, or more.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
INFO         | Boot completed in 26183 ms
INFO         | Increasing screen off timeout, logcat buffer size to 2M.
```
## Logcat (filtered)
```
10-29 00:36:41.010   505   603 I ActivityManager: Killing 1898:com.android.quicksearchbox/u0a101 (adj 975): empty #17
10-29 00:36:46.496  2415  2415 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 00:36:46.512  2415  2415 I AndroidRuntime: Using default boot image
10-29 00:36:46.512  2415  2415 I AndroidRuntime: Leaving lock profiling enabled
10-29 00:36:46.667  2415  2415 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 00:36:46.679  2415  2415 D AndroidRuntime: Shutting down VM
10-29 00:36:47.235  2241  2308 I BackroomsGL: world shader compiled
10-29 00:36:47.869  2241  2308 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-29 00:36:47.869  2241  2308 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-29 00:36:49.293  2241  2308 I Perf    : fps=1.9 worst=4 drawn=4/6 queue=1 build=9ms verts=13k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=4 noMesh=0 culled=0
10-29 00:36:51.339  2241  2308 I Perf    : fps=4.4 worst=4 drawn=11/24 queue=2 build=7ms verts=33k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=11 noMesh=0 culled=11
10-29 00:36:53.473  2241  2308 I Perf    : fps=4.2 worst=4 drawn=14/37 queue=0 build=9ms verts=42k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:36:55.601  2241  2308 I Perf    : fps=4.2 worst=4 drawn=14/37 queue=0 build=9ms verts=42k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:36:57.731  2241  2308 I Perf    : fps=4.2 worst=4 drawn=14/37 queue=0 build=9ms verts=42k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:36:59.851  2241  2308 I Perf    : fps=4.2 worst=4 drawn=14/37 queue=0 build=9ms verts=42k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:37:02.056  2241  2308 I Perf    : fps=4.1 worst=4 drawn=14/37 queue=0 build=9ms verts=42k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:37:04.127  2241  2308 I Perf    : fps=4.4 worst=4 drawn=14/37 queue=0 build=9ms verts=42k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:37:06.251  2241  2308 I Perf    : fps=4.2 worst=4 drawn=14/37 queue=0 build=9ms verts=42k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:37:08.328  2241  2308 I Perf    : fps=4.3 worst=4 drawn=14/37 queue=0 build=9ms verts=42k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:37:10.354  2241  2308 I Perf    : fps=4.4 worst=4 drawn=14/37 queue=0 build=9ms verts=42k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:37:12.493  2241  2308 I Perf    : fps=4.2 worst=4 drawn=14/37 queue=0 build=9ms verts=42k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:37:14.698  2241  2308 I Perf    : fps=4.1 worst=4 drawn=14/37 queue=0 build=9ms verts=42k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:37:16.925  2241  2308 I Perf    : fps=4.0 worst=4 drawn=14/37 queue=0 build=9ms verts=42k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:37:17.891  2450  2450 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 00:37:17.908  2450  2450 I AndroidRuntime: Using default boot image
10-29 00:37:17.909  2450  2450 I AndroidRuntime: Leaving lock profiling enabled
10-29 00:37:18.020  2450  2450 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 00:37:18.631  2450  2450 D AndroidRuntime: Shutting down VM
10-29 00:37:19.009  2241  2308 I Perf    : fps=3.8 worst=4 drawn=17/37 queue=0 build=9ms verts=53k cam=0.5,1.6,0.5 yaw=2.83 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-29 00:37:19.482   505   505 I ActivityManager: Killing 1978:com.android.calendar/u0a109 (adj 985): empty #17
10-29 00:37:19.482   505   505 I ActivityManager: Killing 1605:android.process.acore/u0a64 (adj 985): empty #18
10-29 00:37:21.209  2241  2308 I Perf    : fps=4.6 worst=5 drawn=17/37 queue=0 build=9ms verts=53k cam=0.5,1.6,0.5 yaw=2.83 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-29 00:37:23.309  2241  2308 I Perf    : fps=4.7 worst=5 drawn=17/37 queue=0 build=9ms verts=53k cam=0.5,1.6,0.5 yaw=2.83 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-29 00:37:23.388  2479  2479 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 00:37:23.390  2479  2479 I AndroidRuntime: Using default boot image
10-29 00:37:23.390  2479  2479 I AndroidRuntime: Leaving lock profiling enabled
10-29 00:37:23.607  2479  2479 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 00:37:23.618  2479  2479 D AndroidRuntime: Shutting down VM
10-29 00:37:25.432  2241  2308 I Perf    : fps=4.7 worst=5 drawn=17/37 queue=0 build=9ms verts=53k cam=0.5,1.6,0.5 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-29 00:37:26.896   505   545 W ActivityManager: Slow operation: 59ms so far, now at startProcess: returned from zygote!
10-29 00:37:26.897   505   545 W ActivityManager: Slow operation: 59ms so far, now at startProcess: done updating battery stats
10-29 00:37:26.897   505   545 W ActivityManager: Slow operation: 59ms so far, now at startProcess: building log message
10-29 00:37:26.897   505   545 I ActivityManager: Start proc 2492:android.process.acore/u0a64 for content provider {com.android.providers.contacts/com.android.providers.contacts.VoicemailContentProvider}
10-29 00:37:26.897   505   545 W ActivityManager: Slow operation: 59ms so far, now at startProcess: starting to update pids map
10-29 00:37:26.897   505   545 W ActivityManager: Slow operation: 60ms so far, now at startProcess: done updating pids map
10-29 00:37:27.078  2492  2492 I ContactsPerf: VoicemailContentProvider.onCreate start
10-29 00:37:27.080  2492  2492 I ContactsPerf: VoicemailContentProvider.onCreate finish
10-29 00:37:27.560  2241  2308 I Perf    : fps=4.7 worst=5 drawn=17/37 queue=0 build=9ms verts=53k cam=0.5,1.6,0.5 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-29 00:37:28.612  2523  2523 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 00:37:28.614  2523  2523 I AndroidRuntime: Using default boot image
10-29 00:37:28.615  2523  2523 I AndroidRuntime: Leaving lock profiling enabled
10-29 00:37:28.869  2523  2523 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 00:37:29.699  2241  2308 I Perf    : fps=4.7 worst=5 drawn=17/37 queue=0 build=9ms verts=53k cam=0.5,1.6,0.6 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-29 00:37:30.433  2523  2523 D AndroidRuntime: Shutting down VM
10-29 00:37:31.743  2241  2308 I Perf    : fps=4.9 worst=5 drawn=17/37 queue=0 build=9ms verts=53k cam=0.1,1.6,1.6 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-29 00:37:33.909  2241  2308 I Perf    : fps=4.6 worst=5 drawn=17/37 queue=0 build=9ms verts=53k cam=0.1,1.6,1.6 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-29 00:37:36.078  2241  2308 I Perf    : fps=4.6 worst=5 drawn=17/37 queue=0 build=9ms verts=53k cam=0.1,1.6,1.6 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-29 00:37:38.127  2241  2308 I Perf    : fps=4.4 worst=5 drawn=17/37 queue=0 build=9ms verts=53k cam=0.1,1.6,1.6 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-29 00:37:40.277  2241  2308 I Perf    : fps=4.6 worst=5 drawn=17/37 queue=0 build=9ms verts=53k cam=0.1,1.6,1.6 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-29 00:37:42.321  2241  2308 I Perf    : fps=4.4 worst=4 drawn=17/37 queue=0 build=9ms verts=53k cam=0.1,1.6,1.6 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
```
## Step log
```
I Perf    : fps=4.4 worst=4 drawn=11/24 queue=2 build=7ms verts=33k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=11 noMesh=0 culled=11
10-29 00:36:53.473  2241  2308 I Perf    : fps=4.2 worst=4 drawn=14/37 queue=0 build=9ms verts=42k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:36:55.601  2241  2308 I Perf    : fps=4.2 worst=4 drawn=14/37 queue=0 build=9ms verts=42k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:36:57.731  2241  2308 I Perf    : fps=4.2 worst=4 drawn=14/37 queue=0 build=9ms verts=42k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:36:59.851  2241  2308 I Perf    : fps=4.2 worst=4 drawn=14/37 queue=0 build=9ms verts=42k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:37:02.056  2241  2308 I Perf    : fps=4.1 worst=4 drawn=14/37 queue=0 build=9ms verts=42k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:37:04.127  2241  2308 I Perf    : fps=4.4 worst=4 drawn=14/37 queue=0 build=9ms verts=42k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:37:06.251  2241  2308 I Perf    : fps=4.2 worst=4 drawn=14/37 queue=0 build=9ms verts=42k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:37:08.328  2241  2308 I Perf    : fps=4.3 worst=4 drawn=14/37 queue=0 build=9ms verts=42k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:37:10.354  2241  2308 I Perf    : fps=4.4 worst=4 drawn=14/37 queue=0 build=9ms verts=42k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:37:12.461  2437  2437 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-29 00:37:12.493  2241  2308 I Perf    : fps=4.2 worst=4 drawn=14/37 queue=0 build=9ms verts=42k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:37:14.698  2241  2308 I Perf    : fps=4.1 worst=4 drawn=14/37 queue=0 build=9ms verts=42k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:37:16.925  2241  2308 I Perf    : fps=4.0 worst=4 drawn=14/37 queue=0 build=9ms verts=42k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:37:17.891  2450  2450 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 00:37:17.908  2450  2450 I AndroidRuntime: Using default boot image
10-29 00:37:17.909  2450  2450 I AndroidRuntime: Leaving lock profiling enabled
10-29 00:37:18.020  2450  2450 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 00:37:18.631  2450  2450 D AndroidRuntime: Shutting down VM
10-29 00:37:19.009  2241  2308 I Perf    : fps=3.8 worst=4 drawn=17/37 queue=0 build=9ms verts=53k cam=0.5,1.6,0.5 yaw=2.83 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-29 00:37:21.209  2241  2308 I Perf    : fps=4.6 worst=5 drawn=17/37 queue=0 build=9ms verts=53k cam=0.5,1.6,0.5 yaw=2.83 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-29 00:37:23.309  2241  2308 I Perf    : fps=4.7 worst=5 drawn=17/37 queue=0 build=9ms verts=53k cam=0.5,1.6,0.5 yaw=2.83 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-29 00:37:23.388  2479  2479 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 00:37:23.390  2479  2479 I AndroidRuntime: Using default boot image
10-29 00:37:23.390  2479  2479 I AndroidRuntime: Leaving lock profiling enabled
10-29 00:37:23.607  2479  2479 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 00:37:23.618  2479  2479 D AndroidRuntime: Shutting down VM
10-29 00:37:25.432  2241  2308 I Perf    : fps=4.7 worst=5 drawn=17/37 queue=0 build=9ms verts=53k cam=0.5,1.6,0.5 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-29 00:37:27.078  2492  2492 I ContactsPerf: VoicemailContentProvider.onCreate start
10-29 00:37:27.080  2492  2492 I ContactsPerf: VoicemailContentProvider.onCreate finish
10-29 00:37:27.560  2241  2308 I Perf    : fps=4.7 worst=5 drawn=17/37 queue=0 build=9ms verts=53k cam=0.5,1.6,0.5 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-29 00:37:28.612  2523  2523 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 00:37:28.614  2523  2523 I AndroidRuntime: Using default boot image
10-29 00:37:28.615  2523  2523 I AndroidRuntime: Leaving lock profiling enabled
10-29 00:37:28.869  2523  2523 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 00:37:29.699  2241  2308 I Perf    : fps=4.7 worst=5 drawn=17/37 queue=0 build=9ms verts=53k cam=0.5,1.6,0.6 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-29 00:37:30.433  2523  2523 D AndroidRuntime: Shutting down VM
10-29 00:37:31.743  2241  2308 I Perf    : fps=4.9 worst=5 drawn=17/37 queue=0 build=9ms verts=53k cam=0.1,1.6,1.6 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-29 00:37:33.909  2241  2308 I Perf    : fps=4.6 worst=5 drawn=17/37 queue=0 build=9ms verts=53k cam=0.1,1.6,1.6 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-29 00:37:35.637  2542  2542 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-29 00:37:36.078  2241  2308 I Perf    : fps=4.6 worst=5 drawn=17/37 queue=0 build=9ms verts=53k cam=0.1,1.6,1.6 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-29 00:37:38.127  2241  2308 I Perf    : fps=4.4 worst=5 drawn=17/37 queue=0 build=9ms verts=53k cam=0.1,1.6,1.6 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-29 00:37:40.277  2241  2308 I Perf    : fps=4.6 worst=5 drawn=17/37 queue=0 build=9ms verts=53k cam=0.1,1.6,1.6 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-29 00:37:42.321  2241  2308 I Perf    : fps=4.4 worst=4 drawn=17/37 queue=0 build=9ms verts=53k cam=0.1,1.6,1.6 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
+ '[' -z 2241 ']'
+ grep -qa 'FATAL EXCEPTION' emulator-shots/logcat.txt
+ '[' 0 '!=' 0 ']'
+ echo 'OK: the app is alive, rendering, and the touch controls work'
OK: the app is alive, rendering, and the touch controls work
```
