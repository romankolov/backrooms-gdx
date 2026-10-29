# Emulator smoke test diagnostics

Commit: 72952af5c4af1d3524e572e78dcba54203b66a09
Outcome: success

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 29 05:10 /dev/kvm
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
INFO         | The active JSON Web Key Sets can be found here: /run/user/1001/avd/running/3216/jwks/4cd55633-9f39-42fa-82ba-9a01155d8248/active.jwk
INFO         | Scanning /run/user/1001/avd/running/3216/jwks/4cd55633-9f39-42fa-82ba-9a01155d8248 for jwk keys.
INFO         | Started GRPC server at 127.0.0.1:8554, security: Local, auth: +token
INFO         | Advertising in: /run/user/1001/avd/running/pid_3216.ini
INFO         | Setting display: 0 configuration to: 1080x1920, dpi: 420x420 
INFO         | setDisplayActiveConfig: id:0, 1080x1920
INFO         | emulatorSetupEnvironment: Environment scene is not required
USER_INFO    | Emulator is performing a full startup. This may take upto two minutes, or more.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
INFO         | Boot completed in 26579 ms
INFO         | Increasing screen off timeout, logcat buffer size to 2M.
```
## Logcat (filtered)
```
10-29 05:13:48.688   511   551 W ActivityManager: Slow operation: 53ms so far, now at startProcess: building log message
10-29 05:13:48.688   511   551 I ActivityManager: Start proc 2307:com.android.quicksearchbox/u0a105 for broadcast {com.android.quicksearchbox/com.android.quicksearchbox.SearchWidgetProvider}
10-29 05:13:48.688   511   551 W ActivityManager: Slow operation: 53ms so far, now at startProcess: starting to update pids map
10-29 05:13:48.688   511   551 W ActivityManager: Slow operation: 53ms so far, now at startProcess: done updating pids map
10-29 05:13:48.856   511  1019 I ActivityManager: Killing 1831:com.android.provision/u0a116 (adj 985): empty #17
10-29 05:13:50.757   422  2123 I iorapd  : Perfetto TraceBuffer saved to file: /data/misc/iorapd/com.backrooms.gdx.debug/1/com.backrooms.gdx.android.AndroidLauncher/raw_traces/1793250830756442620.perfetto_trace.pb
10-29 05:14:02.939   511   549 I ActivityManager: Killing 1291:com.android.cellbroadcastreceiver/u0a65 (adj 985): empty #17
10-29 05:14:07.013   511   541 I ActivityManager: Killing 2043:com.android.camera2/u0a111 (adj 985): empty #17
10-29 05:14:07.014   511   541 I ActivityManager: Killing 2021:com.android.calendar/u0a109 (adj 985): empty #18
10-29 05:14:09.292  2367  2367 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 05:14:09.308  2367  2367 I AndroidRuntime: Using default boot image
10-29 05:14:09.308  2367  2367 I AndroidRuntime: Leaving lock profiling enabled
10-29 05:14:09.427  2367  2367 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 05:14:09.433  2367  2367 D AndroidRuntime: Shutting down VM
10-29 05:14:10.036  2128  2174 I BackroomsGL: world shader compiled
10-29 05:14:10.101  2128  2174 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-29 05:14:10.101  2128  2174 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-29 05:14:12.125   511  1019 I ActivityManager: Killing 2069:com.android.contacts/u0a98 (adj 975): empty #17
10-29 05:14:12.127  2128  2174 I Perf    : fps=1.8 worst=4 drawn=4/6 queue=1 build=164ms verts=12k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=4 noMesh=0 culled=0
10-29 05:14:14.146  2128  2174 I Perf    : fps=3.9 worst=4 drawn=11/21 queue=2 build=16ms verts=34k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=11 noMesh=0 culled=8
10-29 05:14:16.225  2128  2174 I Perf    : fps=3.9 worst=4 drawn=14/37 queue=0 build=12ms verts=44k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=21
10-29 05:14:18.305  2128  2174 I Perf    : fps=3.8 worst=4 drawn=14/37 queue=0 build=12ms verts=44k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 05:14:20.456  2128  2174 I Perf    : fps=3.7 worst=3 drawn=14/37 queue=0 build=12ms verts=44k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 05:14:22.588  2128  2174 I Perf    : fps=3.8 worst=3 drawn=14/37 queue=0 build=12ms verts=44k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 05:14:24.757  2128  2174 I Perf    : fps=3.7 worst=4 drawn=14/37 queue=0 build=12ms verts=44k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 05:14:26.910  2128  2174 I Perf    : fps=3.7 worst=4 drawn=14/37 queue=0 build=12ms verts=44k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 05:14:28.959  2128  2174 I Perf    : fps=3.9 worst=4 drawn=14/37 queue=0 build=12ms verts=44k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 05:14:31.137  2128  2174 I Perf    : fps=3.7 worst=4 drawn=14/37 queue=0 build=12ms verts=44k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 05:14:33.269  2128  2174 I Perf    : fps=3.8 worst=4 drawn=14/37 queue=0 build=12ms verts=44k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 05:14:35.396  2128  2174 I Perf    : fps=3.8 worst=4 drawn=14/37 queue=0 build=12ms verts=44k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 05:14:37.555  2128  2174 I Perf    : fps=3.7 worst=4 drawn=14/37 queue=0 build=12ms verts=44k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 05:14:39.812  2128  2174 I Perf    : fps=3.5 worst=3 drawn=14/37 queue=0 build=12ms verts=44k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 05:14:42.063  2128  2174 I Perf    : fps=3.6 worst=3 drawn=14/37 queue=0 build=12ms verts=44k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 05:14:42.170  2413  2413 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 05:14:42.173  2413  2413 I AndroidRuntime: Using default boot image
10-29 05:14:42.173  2413  2413 I AndroidRuntime: Leaving lock profiling enabled
10-29 05:14:42.278  2413  2413 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 05:14:42.883  2413  2413 D AndroidRuntime: Shutting down VM
10-29 05:14:44.299  2128  2174 I Perf    : fps=4.0 worst=4 drawn=17/37 queue=0 build=12ms verts=56k cam=0.5,1.6,0.5 yaw=2.80 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-29 05:14:46.395  2128  2174 I Perf    : fps=4.3 worst=4 drawn=17/37 queue=0 build=12ms verts=56k cam=0.5,1.6,0.5 yaw=2.80 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-29 05:14:47.688  2429  2429 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 05:14:47.701  2429  2429 I AndroidRuntime: Using default boot image
10-29 05:14:47.702  2429  2429 I AndroidRuntime: Leaving lock profiling enabled
10-29 05:14:47.828  2429  2429 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 05:14:47.853  2429  2429 D AndroidRuntime: Shutting down VM
10-29 05:14:48.453  2128  2174 I Perf    : fps=4.4 worst=4 drawn=17/37 queue=0 build=12ms verts=56k cam=0.5,1.6,0.5 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-29 05:14:50.532  2128  2174 I Perf    : fps=4.3 worst=4 drawn=17/37 queue=0 build=12ms verts=56k cam=0.5,1.6,0.5 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-29 05:14:52.656  2128  2174 I Perf    : fps=4.2 worst=4 drawn=17/37 queue=0 build=12ms verts=56k cam=0.5,1.6,0.5 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-29 05:14:52.795  2445  2445 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 05:14:52.797  2445  2445 I AndroidRuntime: Using default boot image
10-29 05:14:52.797  2445  2445 I AndroidRuntime: Leaving lock profiling enabled
10-29 05:14:52.892  2445  2445 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 05:14:54.581  2445  2445 D AndroidRuntime: Shutting down VM
10-29 05:14:54.857  2128  2174 I Perf    : fps=4.1 worst=4 drawn=16/37 queue=0 build=12ms verts=52k cam=0.1,1.6,1.7 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-29 05:14:57.066  2128  2174 I Perf    : fps=4.1 worst=4 drawn=19/44 queue=0 build=32ms verts=63k cam=-0.0,1.6,1.9 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=19 noMesh=0 culled=25
10-29 05:14:59.140  2128  2174 I Perf    : fps=3.9 worst=4 drawn=19/44 queue=0 build=32ms verts=63k cam=-0.0,1.6,1.9 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=19 noMesh=0 culled=25
10-29 05:15:01.284  2128  2174 I Perf    : fps=3.7 worst=3 drawn=19/44 queue=0 build=32ms verts=63k cam=-0.0,1.6,1.9 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=19 noMesh=0 culled=25
10-29 05:15:03.370  2128  2174 I Perf    : fps=3.8 worst=4 drawn=19/44 queue=0 build=32ms verts=63k cam=-0.0,1.6,1.9 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=19 noMesh=0 culled=25
10-29 05:15:05.438  2128  2174 I Perf    : fps=3.9 worst=4 drawn=19/44 queue=0 build=32ms verts=63k cam=-0.0,1.6,1.9 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=19 noMesh=0 culled=25
10-29 05:15:07.701  2128  2174 I Perf    : fps=4.0 worst=4 drawn=19/44 queue=0 build=32ms verts=63k cam=-0.0,1.6,1.9 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=19 noMesh=0 culled=25
```
## Step log
```
1 queue=2 build=16ms verts=34k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=11 noMesh=0 culled=8
10-29 05:14:16.225  2128  2174 I Perf    : fps=3.9 worst=4 drawn=14/37 queue=0 build=12ms verts=44k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=21
10-29 05:14:18.305  2128  2174 I Perf    : fps=3.8 worst=4 drawn=14/37 queue=0 build=12ms verts=44k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 05:14:20.456  2128  2174 I Perf    : fps=3.7 worst=3 drawn=14/37 queue=0 build=12ms verts=44k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 05:14:22.588  2128  2174 I Perf    : fps=3.8 worst=3 drawn=14/37 queue=0 build=12ms verts=44k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 05:14:24.757  2128  2174 I Perf    : fps=3.7 worst=4 drawn=14/37 queue=0 build=12ms verts=44k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 05:14:26.910  2128  2174 I Perf    : fps=3.7 worst=4 drawn=14/37 queue=0 build=12ms verts=44k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 05:14:28.959  2128  2174 I Perf    : fps=3.9 worst=4 drawn=14/37 queue=0 build=12ms verts=44k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 05:14:31.137  2128  2174 I Perf    : fps=3.7 worst=4 drawn=14/37 queue=0 build=12ms verts=44k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 05:14:33.269  2128  2174 I Perf    : fps=3.8 worst=4 drawn=14/37 queue=0 build=12ms verts=44k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 05:14:35.396  2128  2174 I Perf    : fps=3.8 worst=4 drawn=14/37 queue=0 build=12ms verts=44k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 05:14:35.634  2389  2389 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-29 05:14:37.555  2128  2174 I Perf    : fps=3.7 worst=4 drawn=14/37 queue=0 build=12ms verts=44k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 05:14:39.812  2128  2174 I Perf    : fps=3.5 worst=3 drawn=14/37 queue=0 build=12ms verts=44k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 05:14:42.063  2128  2174 I Perf    : fps=3.6 worst=3 drawn=14/37 queue=0 build=12ms verts=44k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 05:14:42.170  2413  2413 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 05:14:42.173  2413  2413 I AndroidRuntime: Using default boot image
10-29 05:14:42.173  2413  2413 I AndroidRuntime: Leaving lock profiling enabled
10-29 05:14:42.278  2413  2413 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 05:14:42.883  2413  2413 D AndroidRuntime: Shutting down VM
10-29 05:14:44.299  2128  2174 I Perf    : fps=4.0 worst=4 drawn=17/37 queue=0 build=12ms verts=56k cam=0.5,1.6,0.5 yaw=2.80 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-29 05:14:46.395  2128  2174 I Perf    : fps=4.3 worst=4 drawn=17/37 queue=0 build=12ms verts=56k cam=0.5,1.6,0.5 yaw=2.80 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-29 05:14:47.688  2429  2429 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 05:14:47.701  2429  2429 I AndroidRuntime: Using default boot image
10-29 05:14:47.702  2429  2429 I AndroidRuntime: Leaving lock profiling enabled
10-29 05:14:47.828  2429  2429 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 05:14:47.853  2429  2429 D AndroidRuntime: Shutting down VM
10-29 05:14:48.453  2128  2174 I Perf    : fps=4.4 worst=4 drawn=17/37 queue=0 build=12ms verts=56k cam=0.5,1.6,0.5 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-29 05:14:50.532  2128  2174 I Perf    : fps=4.3 worst=4 drawn=17/37 queue=0 build=12ms verts=56k cam=0.5,1.6,0.5 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-29 05:14:52.656  2128  2174 I Perf    : fps=4.2 worst=4 drawn=17/37 queue=0 build=12ms verts=56k cam=0.5,1.6,0.5 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-29 05:14:52.795  2445  2445 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 05:14:52.797  2445  2445 I AndroidRuntime: Using default boot image
10-29 05:14:52.797  2445  2445 I AndroidRuntime: Leaving lock profiling enabled
10-29 05:14:52.892  2445  2445 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 05:14:54.581  2445  2445 D AndroidRuntime: Shutting down VM
10-29 05:14:54.857  2128  2174 I Perf    : fps=4.1 worst=4 drawn=16/37 queue=0 build=12ms verts=52k cam=0.1,1.6,1.7 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-29 05:14:57.066  2128  2174 I Perf    : fps=4.1 worst=4 drawn=19/44 queue=0 build=32ms verts=63k cam=-0.0,1.6,1.9 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=19 noMesh=0 culled=25
10-29 05:14:59.140  2128  2174 I Perf    : fps=3.9 worst=4 drawn=19/44 queue=0 build=32ms verts=63k cam=-0.0,1.6,1.9 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=19 noMesh=0 culled=25
10-29 05:15:00.248  2466  2466 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-29 05:15:01.284  2128  2174 I Perf    : fps=3.7 worst=3 drawn=19/44 queue=0 build=32ms verts=63k cam=-0.0,1.6,1.9 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=19 noMesh=0 culled=25
10-29 05:15:03.370  2128  2174 I Perf    : fps=3.8 worst=4 drawn=19/44 queue=0 build=32ms verts=63k cam=-0.0,1.6,1.9 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=19 noMesh=0 culled=25
10-29 05:15:05.438  2128  2174 I Perf    : fps=3.9 worst=4 drawn=19/44 queue=0 build=32ms verts=63k cam=-0.0,1.6,1.9 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=19 noMesh=0 culled=25
10-29 05:15:07.701  2128  2174 I Perf    : fps=4.0 worst=4 drawn=19/44 queue=0 build=32ms verts=63k cam=-0.0,1.6,1.9 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=19 noMesh=0 culled=25
+ '[' -z 2128 ']'
+ grep -qa 'FATAL EXCEPTION' emulator-shots/logcat.txt
+ '[' 0 '!=' 0 ']'
+ echo 'OK: the app is alive, rendering, and the touch controls work'
OK: the app is alive, rendering, and the touch controls work
```
