# Emulator smoke test diagnostics

Commit: f2a6be7230d7d0735e68bc1bcd80de5a9aa977e4
Outcome: success

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 29 00:21 /dev/kvm
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
INFO         | The active JSON Web Key Sets can be found here: /run/user/1001/avd/running/2752/jwks/1adb583b-22e6-4c75-9179-f3dc6866b94d/active.jwk
INFO         | Scanning /run/user/1001/avd/running/2752/jwks/1adb583b-22e6-4c75-9179-f3dc6866b94d for jwk keys.
INFO         | Started GRPC server at 127.0.0.1:8554, security: Local, auth: +token
INFO         | Advertising in: /run/user/1001/avd/running/pid_2752.ini
INFO         | Setting display: 0 configuration to: 1080x1920, dpi: 420x420 
INFO         | setDisplayActiveConfig: id:0, 1080x1920
INFO         | emulatorSetupEnvironment: Environment scene is not required
USER_INFO    | Emulator is performing a full startup. This may take upto two minutes, or more.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
INFO         | Boot completed in 26069 ms
INFO         | Increasing screen off timeout, logcat buffer size to 2M.
```
## Logcat (filtered)
```
10-29 00:25:10.728   502   544 I ActivityManager: Start proc 2349:com.android.quicksearchbox/u0a102 for broadcast {com.android.quicksearchbox/com.android.quicksearchbox.SearchWidgetProvider}
10-29 00:25:10.728   502   544 W ActivityManager: Slow operation: 98ms so far, now at startProcess: starting to update pids map
10-29 00:25:10.731   502   544 W ActivityManager: Slow operation: 101ms so far, now at startProcess: done updating pids map
10-29 00:25:11.099   502   787 I ActivityManager: Killing 1827:com.android.provision/u0a116 (adj 985): empty #17
10-29 00:25:13.751   434  2149 I iorapd  : Perfetto TraceBuffer saved to file: /data/misc/iorapd/com.backrooms.gdx.debug/1/com.backrooms.gdx.android.AndroidLauncher/raw_traces/1793233513735861460.perfetto_trace.pb
10-29 00:25:22.213   502   544 I ActivityManager: Start proc 2404:com.android.cellbroadcastreceiver.module/u0a123 for broadcast {com.android.cellbroadcastreceiver.module/com.android.cellbroadcastreceiver.CellBroadcastReceiver}
10-29 00:25:22.339   502  2023 I ActivityManager: Killing 1912:com.android.calendar/u0a107 (adj 985): empty #17
10-29 00:25:25.058   502   542 I ActivityManager: Killing 1281:com.android.cellbroadcastreceiver/u0a66 (adj 985): empty #17
10-29 00:25:30.649   502   534 I ActivityManager: Killing 1942:com.android.localtransport/1000 (adj 985): empty #17
10-29 00:25:30.677   502   531 I ActivityManager: Killing 1975:com.android.camera2/u0a110 (adj 975): empty #17
10-29 00:25:31.127  2443  2443 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 00:25:31.132  2443  2443 I AndroidRuntime: Using default boot image
10-29 00:25:31.132  2443  2443 I AndroidRuntime: Leaving lock profiling enabled
10-29 00:25:31.270  2443  2443 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 00:25:31.274  2443  2443 D AndroidRuntime: Shutting down VM
10-29 00:25:31.276  2443  2455 I AndroidRuntime: NOTE: attach of thread 'Binder:2443_2' failed
10-29 00:25:31.796  2157  2239 I BackroomsGL: world shader compiled
10-29 00:25:32.196  2157  2239 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-29 00:25:32.196  2157  2239 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-29 00:25:33.821  2157  2239 I Perf    : fps=2.4 worst=6 drawn=6/6 queue=2 build=36ms verts=19k cam=0.5,1.6,-3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=6 noMesh=0 culled=0
10-29 00:25:36.017  2157  2239 I Perf    : fps=4.6 worst=4 drawn=14/21 queue=2 build=22ms verts=44k cam=0.5,1.6,-3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=5
10-29 00:25:38.074  2157  2239 I Perf    : fps=4.4 worst=4 drawn=18/34 queue=2 build=26ms verts=57k cam=0.5,1.6,-3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=16
10-29 00:25:40.131  2157  2239 I Perf    : fps=4.4 worst=4 drawn=18/37 queue=0 build=40ms verts=57k cam=0.5,1.6,-3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 00:25:42.278  2157  2239 I Perf    : fps=4.2 worst=4 drawn=18/37 queue=0 build=40ms verts=57k cam=0.5,1.6,-3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 00:25:44.341  2157  2239 I Perf    : fps=4.4 worst=4 drawn=18/37 queue=0 build=40ms verts=57k cam=0.5,1.6,-3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 00:25:46.510  2157  2239 I Perf    : fps=4.1 worst=4 drawn=18/37 queue=0 build=40ms verts=57k cam=0.5,1.6,-3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 00:25:48.540  2157  2239 I Perf    : fps=4.4 worst=5 drawn=18/37 queue=0 build=40ms verts=57k cam=0.5,1.6,-3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 00:25:50.610  2157  2239 I Perf    : fps=4.3 worst=4 drawn=18/37 queue=0 build=40ms verts=57k cam=0.5,1.6,-3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 00:25:52.748  2157  2239 I Perf    : fps=4.2 worst=4 drawn=18/37 queue=0 build=40ms verts=57k cam=0.5,1.6,-3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 00:25:54.866  2157  2239 I Perf    : fps=4.2 worst=4 drawn=18/37 queue=0 build=40ms verts=57k cam=0.5,1.6,-3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 00:25:56.984  2157  2239 I Perf    : fps=4.3 worst=4 drawn=18/37 queue=0 build=40ms verts=57k cam=0.5,1.6,-3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 00:25:59.160  2157  2239 I Perf    : fps=4.1 worst=4 drawn=18/37 queue=0 build=40ms verts=57k cam=0.5,1.6,-3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 00:26:01.209  2157  2239 I Perf    : fps=4.4 worst=4 drawn=18/37 queue=0 build=40ms verts=57k cam=0.5,1.6,-3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 00:26:03.331  2157  2239 I Perf    : fps=4.2 worst=4 drawn=18/37 queue=0 build=40ms verts=57k cam=0.5,1.6,-3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 00:26:03.510  2483  2483 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 00:26:03.536  2483  2483 I AndroidRuntime: Using default boot image
10-29 00:26:03.536  2483  2483 I AndroidRuntime: Leaving lock profiling enabled
10-29 00:26:03.688  2483  2483 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 00:26:04.338  2483  2483 D AndroidRuntime: Shutting down VM
10-29 00:26:04.619   502   502 I ActivityManager: Killing 2017:com.android.contacts/u0a98 (adj 985): empty #17
10-29 00:26:05.336  2157  2239 I Perf    : fps=4.5 worst=5 drawn=13/37 queue=0 build=40ms verts=40k cam=0.5,1.6,-3.5 yaw=2.49 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-29 00:26:07.344  2157  2239 I Perf    : fps=5.0 worst=6 drawn=13/37 queue=0 build=40ms verts=40k cam=0.5,1.6,-3.5 yaw=2.49 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-29 00:26:09.091  2510  2510 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 00:26:09.111  2510  2510 I AndroidRuntime: Using default boot image
10-29 00:26:09.111  2510  2510 I AndroidRuntime: Leaving lock profiling enabled
10-29 00:26:09.180  2510  2510 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 00:26:09.182  2510  2510 D AndroidRuntime: Shutting down VM
10-29 00:26:09.433  2157  2239 I Perf    : fps=5.3 worst=6 drawn=13/37 queue=0 build=40ms verts=40k cam=0.5,1.6,-3.5 yaw=2.49 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-29 00:26:11.516  2157  2239 I Perf    : fps=5.3 worst=5 drawn=13/37 queue=0 build=40ms verts=40k cam=0.5,1.6,-3.5 yaw=2.49 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-29 00:26:13.696  2157  2239 I Perf    : fps=5.0 worst=5 drawn=13/37 queue=0 build=40ms verts=40k cam=0.5,1.6,-3.5 yaw=2.49 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-29 00:26:14.050  2527  2527 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 00:26:14.067  2527  2527 I AndroidRuntime: Using default boot image
10-29 00:26:14.067  2527  2527 I AndroidRuntime: Leaving lock profiling enabled
10-29 00:26:14.216  2527  2527 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 00:26:15.727  2527  2527 D AndroidRuntime: Shutting down VM
10-29 00:26:15.730  2157  2239 I Perf    : fps=4.9 worst=5 drawn=13/39 queue=2 build=23ms verts=40k cam=-0.1,1.6,-3.4 yaw=2.49 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-29 00:26:17.820  2157  2239 I Perf    : fps=5.2 worst=5 drawn=17/44 queue=0 build=71ms verts=52k cam=-0.4,1.6,-3.4 yaw=2.49 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=27
10-29 00:26:19.957  2157  2239 I Perf    : fps=5.1 worst=6 drawn=17/44 queue=0 build=71ms verts=52k cam=-0.4,1.6,-3.4 yaw=2.49 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=27
10-29 00:26:22.110  2157  2239 I Perf    : fps=5.1 worst=5 drawn=17/44 queue=0 build=71ms verts=52k cam=-0.4,1.6,-3.4 yaw=2.49 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=27
10-29 00:26:24.223  2157  2239 I Perf    : fps=5.2 worst=5 drawn=17/44 queue=0 build=71ms verts=52k cam=-0.4,1.6,-3.4 yaw=2.49 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=27
```
## Step log
```
t: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-29 00:25:33.821  2157  2239 I Perf    : fps=2.4 worst=6 drawn=6/6 queue=2 build=36ms verts=19k cam=0.5,1.6,-3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=6 noMesh=0 culled=0
10-29 00:25:36.017  2157  2239 I Perf    : fps=4.6 worst=4 drawn=14/21 queue=2 build=22ms verts=44k cam=0.5,1.6,-3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=5
10-29 00:25:38.074  2157  2239 I Perf    : fps=4.4 worst=4 drawn=18/34 queue=2 build=26ms verts=57k cam=0.5,1.6,-3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=16
10-29 00:25:40.131  2157  2239 I Perf    : fps=4.4 worst=4 drawn=18/37 queue=0 build=40ms verts=57k cam=0.5,1.6,-3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 00:25:42.278  2157  2239 I Perf    : fps=4.2 worst=4 drawn=18/37 queue=0 build=40ms verts=57k cam=0.5,1.6,-3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 00:25:44.341  2157  2239 I Perf    : fps=4.4 worst=4 drawn=18/37 queue=0 build=40ms verts=57k cam=0.5,1.6,-3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 00:25:46.510  2157  2239 I Perf    : fps=4.1 worst=4 drawn=18/37 queue=0 build=40ms verts=57k cam=0.5,1.6,-3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 00:25:48.540  2157  2239 I Perf    : fps=4.4 worst=5 drawn=18/37 queue=0 build=40ms verts=57k cam=0.5,1.6,-3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 00:25:50.610  2157  2239 I Perf    : fps=4.3 worst=4 drawn=18/37 queue=0 build=40ms verts=57k cam=0.5,1.6,-3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 00:25:52.748  2157  2239 I Perf    : fps=4.2 worst=4 drawn=18/37 queue=0 build=40ms verts=57k cam=0.5,1.6,-3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 00:25:54.866  2157  2239 I Perf    : fps=4.2 worst=4 drawn=18/37 queue=0 build=40ms verts=57k cam=0.5,1.6,-3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 00:25:56.984  2157  2239 I Perf    : fps=4.3 worst=4 drawn=18/37 queue=0 build=40ms verts=57k cam=0.5,1.6,-3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 00:25:57.366  2469  2469 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-29 00:25:59.160  2157  2239 I Perf    : fps=4.1 worst=4 drawn=18/37 queue=0 build=40ms verts=57k cam=0.5,1.6,-3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 00:26:01.209  2157  2239 I Perf    : fps=4.4 worst=4 drawn=18/37 queue=0 build=40ms verts=57k cam=0.5,1.6,-3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 00:26:03.331  2157  2239 I Perf    : fps=4.2 worst=4 drawn=18/37 queue=0 build=40ms verts=57k cam=0.5,1.6,-3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 00:26:03.510  2483  2483 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 00:26:03.536  2483  2483 I AndroidRuntime: Using default boot image
10-29 00:26:03.536  2483  2483 I AndroidRuntime: Leaving lock profiling enabled
10-29 00:26:03.688  2483  2483 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 00:26:04.338  2483  2483 D AndroidRuntime: Shutting down VM
10-29 00:26:05.336  2157  2239 I Perf    : fps=4.5 worst=5 drawn=13/37 queue=0 build=40ms verts=40k cam=0.5,1.6,-3.5 yaw=2.49 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-29 00:26:07.344  2157  2239 I Perf    : fps=5.0 worst=6 drawn=13/37 queue=0 build=40ms verts=40k cam=0.5,1.6,-3.5 yaw=2.49 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-29 00:26:09.091  2510  2510 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 00:26:09.111  2510  2510 I AndroidRuntime: Using default boot image
10-29 00:26:09.111  2510  2510 I AndroidRuntime: Leaving lock profiling enabled
10-29 00:26:09.180  2510  2510 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 00:26:09.182  2510  2510 D AndroidRuntime: Shutting down VM
10-29 00:26:09.433  2157  2239 I Perf    : fps=5.3 worst=6 drawn=13/37 queue=0 build=40ms verts=40k cam=0.5,1.6,-3.5 yaw=2.49 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-29 00:26:11.516  2157  2239 I Perf    : fps=5.3 worst=5 drawn=13/37 queue=0 build=40ms verts=40k cam=0.5,1.6,-3.5 yaw=2.49 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-29 00:26:13.696  2157  2239 I Perf    : fps=5.0 worst=5 drawn=13/37 queue=0 build=40ms verts=40k cam=0.5,1.6,-3.5 yaw=2.49 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-29 00:26:14.050  2527  2527 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 00:26:14.067  2527  2527 I AndroidRuntime: Using default boot image
10-29 00:26:14.067  2527  2527 I AndroidRuntime: Leaving lock profiling enabled
10-29 00:26:14.216  2527  2527 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 00:26:15.727  2527  2527 D AndroidRuntime: Shutting down VM
10-29 00:26:15.730  2157  2239 I Perf    : fps=4.9 worst=5 drawn=13/39 queue=2 build=23ms verts=40k cam=-0.1,1.6,-3.4 yaw=2.49 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-29 00:26:17.820  2157  2239 I Perf    : fps=5.2 worst=5 drawn=17/44 queue=0 build=71ms verts=52k cam=-0.4,1.6,-3.4 yaw=2.49 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=27
10-29 00:26:19.957  2157  2239 I Perf    : fps=5.1 worst=6 drawn=17/44 queue=0 build=71ms verts=52k cam=-0.4,1.6,-3.4 yaw=2.49 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=27
10-29 00:26:20.537  2545  2545 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-29 00:26:22.110  2157  2239 I Perf    : fps=5.1 worst=5 drawn=17/44 queue=0 build=71ms verts=52k cam=-0.4,1.6,-3.4 yaw=2.49 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=27
10-29 00:26:24.223  2157  2239 I Perf    : fps=5.2 worst=5 drawn=17/44 queue=0 build=71ms verts=52k cam=-0.4,1.6,-3.4 yaw=2.49 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=27
+ '[' -z 2157 ']'
+ grep -qa 'FATAL EXCEPTION' emulator-shots/logcat.txt
+ '[' 0 '!=' 0 ']'
+ echo 'OK: the app is alive, rendering, and the touch controls work'
OK: the app is alive, rendering, and the touch controls work
```
