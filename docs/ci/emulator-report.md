# Emulator smoke test diagnostics

Commit: 2bcff4a0363c8cf90486124593f8fcbfd948ca32
Outcome: success

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 28 11:57 /dev/kvm
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
INFO         | The active JSON Web Key Sets can be found here: /run/user/1001/avd/running/2726/jwks/21a7aa85-3f7b-4b90-a436-8abd8cfc7320/active.jwk
INFO         | Scanning /run/user/1001/avd/running/2726/jwks/21a7aa85-3f7b-4b90-a436-8abd8cfc7320 for jwk keys.
INFO         | Started GRPC server at 127.0.0.1:8554, security: Local, auth: +token
INFO         | Advertising in: /run/user/1001/avd/running/pid_2726.ini
INFO         | Setting display: 0 configuration to: 1080x1920, dpi: 420x420 
INFO         | setDisplayActiveConfig: id:0, 1080x1920
INFO         | emulatorSetupEnvironment: Environment scene is not required
USER_INFO    | Emulator is performing a full startup. This may take upto two minutes, or more.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
INFO         | Boot completed in 25376 ms
INFO         | Increasing screen off timeout, logcat buffer size to 2M.
```
## Logcat (filtered)
```
10-28 11:59:54.326   507  1157 I ActivityManager: Start proc 2286:com.android.quicksearchbox/u0a104 for broadcast {com.android.quicksearchbox/com.android.quicksearchbox.SearchWidgetProvider}
10-28 11:59:54.326   507  1157 W ActivityManager: Slow operation: 58ms so far, now at startProcess: starting to update pids map
10-28 11:59:54.327   507  1157 W ActivityManager: Slow operation: 58ms so far, now at startProcess: done updating pids map
10-28 11:59:54.370   507   548 W ActivityManager: Slow operation: 102ms so far, now at startProcess: returned from zygote!
10-28 11:59:54.439   507  1157 I ActivityManager: Killing 1933:com.android.calendar/u0a108 (adj 985): empty #17
10-28 11:59:54.486   507   548 I ActivityManager: Start proc 2306:com.android.externalstorage/u0a68 for broadcast {com.android.externalstorage/com.android.externalstorage.MountReceiver}
10-28 11:59:54.643   507   831 I ActivityManager: Killing 1984:com.android.camera2/u0a111 (adj 985): empty #17
10-28 12:00:06.460   507   548 I ActivityManager: Start proc 2362:com.android.cellbroadcastreceiver.module/u0a129 for broadcast {com.android.cellbroadcastreceiver.module/com.android.cellbroadcastreceiver.CellBroadcastReceiver}
10-28 12:00:06.532   507   642 I ActivityManager: Killing 1885:com.android.printspooler/u0a87 (adj 985): empty #17
10-28 12:00:08.691   507   546 I ActivityManager: Killing 1266:com.android.cellbroadcastreceiver/u0a64 (adj 985): empty #17
10-28 12:00:09.950   507  2116 I ActivityManager: Killing 2011:com.android.contacts/u0a98 (adj 975): empty #17
10-28 12:00:13.887  2390  2390 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 12:00:13.889  2390  2390 I AndroidRuntime: Using default boot image
10-28 12:00:13.889  2390  2390 I AndroidRuntime: Leaving lock profiling enabled
10-28 12:00:13.990  2390  2390 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 12:00:13.995  2390  2390 D AndroidRuntime: Shutting down VM
10-28 12:00:14.512  1961  2050 I BackroomsGL: world shader compiled
10-28 12:00:14.534  1961  2050 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 12:00:14.534  1961  2050 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 12:00:16.526  1961  2050 I Perf    : fps=3.8 worst=9 drawn=6/12 queue=2 build=64ms verts=15k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=6 noMesh=0 culled=4
10-28 12:00:18.697  1961  2050 I Perf    : fps=5.1 worst=5 drawn=14/33 queue=2 build=127ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=17
10-28 12:00:20.779  1961  2050 I Perf    : fps=5.2 worst=5 drawn=14/37 queue=0 build=10ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:00:22.838  1961  2050 I Perf    : fps=5.3 worst=6 drawn=14/37 queue=0 build=10ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:00:24.939  1961  2050 I Perf    : fps=5.2 worst=5 drawn=14/37 queue=0 build=10ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:00:27.009  1961  2050 I Perf    : fps=5.3 worst=5 drawn=14/37 queue=0 build=10ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:00:29.162  1961  2050 I Perf    : fps=5.1 worst=5 drawn=14/37 queue=0 build=10ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:00:31.250  1961  2050 I Perf    : fps=5.3 worst=5 drawn=14/37 queue=0 build=10ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:00:33.329  1961  2050 I Perf    : fps=5.3 worst=5 drawn=14/37 queue=0 build=10ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:00:35.438  1961  2050 I Perf    : fps=5.2 worst=6 drawn=14/37 queue=0 build=10ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:00:37.575  1961  2050 I Perf    : fps=5.1 worst=6 drawn=14/37 queue=0 build=10ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:00:39.775  1961  2050 I Perf    : fps=5.0 worst=6 drawn=14/37 queue=0 build=10ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:00:41.921  1961  2050 I Perf    : fps=5.1 worst=5 drawn=14/37 queue=0 build=10ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:00:43.932  1961  2050 I Perf    : fps=5.0 worst=5 drawn=14/37 queue=0 build=10ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:00:44.696  2417  2417 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 12:00:44.697  2417  2417 I AndroidRuntime: Using default boot image
10-28 12:00:44.697  2417  2417 I AndroidRuntime: Leaving lock profiling enabled
10-28 12:00:44.751  2417  2417 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 12:00:45.399  2417  2417 D AndroidRuntime: Shutting down VM
10-28 12:00:46.078  1961  2050 I Perf    : fps=5.1 worst=5 drawn=18/37 queue=0 build=10ms verts=45k cam=-0.5,1.6,1.5 yaw=2.57 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:00:46.274   507   507 I ActivityManager: Killing 2030:com.android.localtransport/1000 (adj 985): empty #17
10-28 12:00:46.274   507   507 I ActivityManager: Killing 1218:com.android.deskclock/u0a110 (adj 985): empty #18
10-28 12:00:48.137  1961  2050 I Perf    : fps=5.8 worst=6 drawn=18/37 queue=0 build=10ms verts=45k cam=-0.5,1.6,1.5 yaw=2.57 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:00:50.043  2452  2452 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 12:00:50.044  2452  2452 I AndroidRuntime: Using default boot image
10-28 12:00:50.044  2452  2452 I AndroidRuntime: Leaving lock profiling enabled
10-28 12:00:50.098  2452  2452 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 12:00:50.103  2452  2452 D AndroidRuntime: Shutting down VM
10-28 12:00:50.270  1961  2050 I Perf    : fps=5.6 worst=6 drawn=18/37 queue=0 build=10ms verts=45k cam=-0.5,1.6,1.5 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 12:00:52.346  1961  2050 I Perf    : fps=5.8 worst=7 drawn=18/37 queue=0 build=10ms verts=45k cam=-0.5,1.6,1.5 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 12:00:54.414  1961  2050 I Perf    : fps=5.8 worst=6 drawn=18/37 queue=0 build=10ms verts=45k cam=-0.5,1.6,1.5 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 12:00:55.075  2469  2469 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 12:00:55.085  2469  2469 I AndroidRuntime: Using default boot image
10-28 12:00:55.085  2469  2469 I AndroidRuntime: Leaving lock profiling enabled
10-28 12:00:55.245  2469  2469 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 12:00:56.443  1961  2050 I Perf    : fps=5.4 worst=6 drawn=18/37 queue=0 build=10ms verts=45k cam=-0.9,1.6,2.1 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 12:00:56.832  2469  2469 D AndroidRuntime: Shutting down VM
10-28 12:00:58.540  1961  2050 I Perf    : fps=5.2 worst=5 drawn=18/37 queue=0 build=10ms verts=45k cam=-1.3,1.6,2.8 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 12:01:00.635  1961  2050 I Perf    : fps=5.3 worst=6 drawn=18/37 queue=0 build=10ms verts=45k cam=-1.3,1.6,2.8 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 12:01:02.751  1961  2050 I Perf    : fps=5.2 worst=5 drawn=18/37 queue=0 build=10ms verts=45k cam=-1.3,1.6,2.8 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 12:01:04.850  1961  2050 I Perf    : fps=5.2 worst=5 drawn=18/37 queue=0 build=10ms verts=45k cam=-1.3,1.6,2.8 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
```
## Step log
```
10-28 12:00:14.534  1961  2050 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 12:00:14.534  1961  2050 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 12:00:16.526  1961  2050 I Perf    : fps=3.8 worst=9 drawn=6/12 queue=2 build=64ms verts=15k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=6 noMesh=0 culled=4
10-28 12:00:18.697  1961  2050 I Perf    : fps=5.1 worst=5 drawn=14/33 queue=2 build=127ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=17
10-28 12:00:20.779  1961  2050 I Perf    : fps=5.2 worst=5 drawn=14/37 queue=0 build=10ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:00:22.838  1961  2050 I Perf    : fps=5.3 worst=6 drawn=14/37 queue=0 build=10ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:00:24.939  1961  2050 I Perf    : fps=5.2 worst=5 drawn=14/37 queue=0 build=10ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:00:27.009  1961  2050 I Perf    : fps=5.3 worst=5 drawn=14/37 queue=0 build=10ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:00:29.162  1961  2050 I Perf    : fps=5.1 worst=5 drawn=14/37 queue=0 build=10ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:00:31.250  1961  2050 I Perf    : fps=5.3 worst=5 drawn=14/37 queue=0 build=10ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:00:33.329  1961  2050 I Perf    : fps=5.3 worst=5 drawn=14/37 queue=0 build=10ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:00:35.438  1961  2050 I Perf    : fps=5.2 worst=6 drawn=14/37 queue=0 build=10ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:00:37.575  1961  2050 I Perf    : fps=5.1 worst=6 drawn=14/37 queue=0 build=10ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:00:39.771  2412  2412 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 12:00:39.775  1961  2050 I Perf    : fps=5.0 worst=6 drawn=14/37 queue=0 build=10ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:00:41.921  1961  2050 I Perf    : fps=5.1 worst=5 drawn=14/37 queue=0 build=10ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:00:43.932  1961  2050 I Perf    : fps=5.0 worst=5 drawn=14/37 queue=0 build=10ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 12:00:44.696  2417  2417 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 12:00:44.697  2417  2417 I AndroidRuntime: Using default boot image
10-28 12:00:44.697  2417  2417 I AndroidRuntime: Leaving lock profiling enabled
10-28 12:00:44.751  2417  2417 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 12:00:45.399  2417  2417 D AndroidRuntime: Shutting down VM
10-28 12:00:46.078  1961  2050 I Perf    : fps=5.1 worst=5 drawn=18/37 queue=0 build=10ms verts=45k cam=-0.5,1.6,1.5 yaw=2.57 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:00:48.137  1961  2050 I Perf    : fps=5.8 worst=6 drawn=18/37 queue=0 build=10ms verts=45k cam=-0.5,1.6,1.5 yaw=2.57 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:00:50.043  2452  2452 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 12:00:50.044  2452  2452 I AndroidRuntime: Using default boot image
10-28 12:00:50.044  2452  2452 I AndroidRuntime: Leaving lock profiling enabled
10-28 12:00:50.098  2452  2452 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 12:00:50.103  2452  2452 D AndroidRuntime: Shutting down VM
10-28 12:00:50.270  1961  2050 I Perf    : fps=5.6 worst=6 drawn=18/37 queue=0 build=10ms verts=45k cam=-0.5,1.6,1.5 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 12:00:52.346  1961  2050 I Perf    : fps=5.8 worst=7 drawn=18/37 queue=0 build=10ms verts=45k cam=-0.5,1.6,1.5 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 12:00:54.414  1961  2050 I Perf    : fps=5.8 worst=6 drawn=18/37 queue=0 build=10ms verts=45k cam=-0.5,1.6,1.5 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 12:00:55.075  2469  2469 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 12:00:55.085  2469  2469 I AndroidRuntime: Using default boot image
10-28 12:00:55.085  2469  2469 I AndroidRuntime: Leaving lock profiling enabled
10-28 12:00:55.245  2469  2469 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 12:00:56.443  1961  2050 I Perf    : fps=5.4 worst=6 drawn=18/37 queue=0 build=10ms verts=45k cam=-0.9,1.6,2.1 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 12:00:56.832  2469  2469 D AndroidRuntime: Shutting down VM
10-28 12:00:58.540  1961  2050 I Perf    : fps=5.2 worst=5 drawn=18/37 queue=0 build=10ms verts=45k cam=-1.3,1.6,2.8 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 12:01:00.635  1961  2050 I Perf    : fps=5.3 worst=6 drawn=18/37 queue=0 build=10ms verts=45k cam=-1.3,1.6,2.8 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 12:01:01.949  2487  2487 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 12:01:02.751  1961  2050 I Perf    : fps=5.2 worst=5 drawn=18/37 queue=0 build=10ms verts=45k cam=-1.3,1.6,2.8 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 12:01:04.850  1961  2050 I Perf    : fps=5.2 worst=5 drawn=18/37 queue=0 build=10ms verts=45k cam=-1.3,1.6,2.8 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
+ '[' -z 1961 ']'
+ grep -qa 'FATAL EXCEPTION' emulator-shots/logcat.txt
+ '[' 0 '!=' 0 ']'
+ echo 'OK: the app is alive, rendering, and the touch controls work'
OK: the app is alive, rendering, and the touch controls work
```
