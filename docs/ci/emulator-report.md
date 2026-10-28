# Emulator smoke test diagnostics

Commit: 02a6e96a99be3df49df388d73d156c4a58a436c2
Outcome: success

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 28 11:51 /dev/kvm
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
INFO         | The active JSON Web Key Sets can be found here: /run/user/1001/avd/running/2738/jwks/77197abd-9dff-45c4-9966-4306fc72e40b/active.jwk
INFO         | Scanning /run/user/1001/avd/running/2738/jwks/77197abd-9dff-45c4-9966-4306fc72e40b for jwk keys.
INFO         | Started GRPC server at 127.0.0.1:8554, security: Local, auth: +token
INFO         | Advertising in: /run/user/1001/avd/running/pid_2738.ini
INFO         | Setting display: 0 configuration to: 1080x1920, dpi: 420x420 
INFO         | setDisplayActiveConfig: id:0, 1080x1920
INFO         | emulatorSetupEnvironment: Environment scene is not required
USER_INFO    | Emulator is performing a full startup. This may take upto two minutes, or more.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
INFO         | Boot completed in 25378 ms
INFO         | Increasing screen off timeout, logcat buffer size to 2M.
```
## Logcat (filtered)
```
10-28 11:55:25.460   505  1008 I ActivityManager: Finished processing BOOT_COMPLETED for u0
10-28 11:55:25.507   505   546 I ActivityManager: Start proc 2299:com.android.quicksearchbox/u0a101 for broadcast {com.android.quicksearchbox/com.android.quicksearchbox.SearchWidgetProvider}
10-28 11:55:25.700   505  1008 I ActivityManager: Killing 1719:com.android.provision/u0a116 (adj 985): empty #17
10-28 11:55:25.781   505   546 I ActivityManager: Start proc 2324:com.android.externalstorage/u0a68 for broadcast {com.android.externalstorage/com.android.externalstorage.MountReceiver}
10-28 11:55:25.830   505  1008 I ActivityManager: Killing 1896:com.android.calendar/u0a108 (adj 985): empty #17
10-28 11:55:27.964   421  2060 I iorapd  : Perfetto TraceBuffer saved to file: /data/misc/iorapd/com.backrooms.gdx.debug/1/com.backrooms.gdx.android.AndroidLauncher/raw_traces/1793188527963820857.perfetto_trace.pb
10-28 11:55:38.372   505   546 I ActivityManager: Start proc 2387:com.android.cellbroadcastreceiver.module/u0a124 for broadcast {com.android.cellbroadcastreceiver.module/com.android.cellbroadcastreceiver.CellBroadcastReceiver}
10-28 11:55:38.496   505   635 I ActivityManager: Killing 1940:com.android.camera2/u0a111 (adj 985): empty #17
10-28 11:55:41.798   505   536 I ActivityManager: Killing 1276:com.android.cellbroadcastreceiver/u0a65 (adj 985): empty #17
10-28 11:55:41.844   505  1008 I ActivityManager: Killing 1976:com.android.contacts/u0a98 (adj 975): empty #17
10-28 11:55:45.343  2413  2413 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 11:55:45.347  2413  2413 I AndroidRuntime: Using default boot image
10-28 11:55:45.347  2413  2413 I AndroidRuntime: Leaving lock profiling enabled
10-28 11:55:45.499  2413  2413 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 11:55:45.504  2413  2413 D AndroidRuntime: Shutting down VM
10-28 11:55:45.505  2413  2424 I AndroidRuntime: NOTE: attach of thread 'Binder:2413_1' failed
10-28 11:55:46.028  2065  2117 I BackroomsGL: world shader compiled
10-28 11:55:46.086  2065  2117 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 11:55:46.086  2065  2117 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 11:55:48.167  2065  2117 I Perf    : fps=4.1 worst=7 drawn=10/16 queue=2 build=210ms verts=26k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=10 noMesh=0 culled=4
10-28 11:55:50.180  2065  2117 I Perf    : fps=4.9 worst=5 drawn=14/36 queue=1 build=156ms verts=37k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=20
10-28 11:55:52.323  2065  2117 I Perf    : fps=4.7 worst=5 drawn=14/37 queue=0 build=81ms verts=37k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 11:55:54.371  2065  2117 I Perf    : fps=4.9 worst=5 drawn=14/37 queue=0 build=81ms verts=37k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 11:55:56.373  2065  2117 I Perf    : fps=5.0 worst=5 drawn=14/37 queue=0 build=81ms verts=37k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 11:55:58.395  2065  2117 I Perf    : fps=4.9 worst=5 drawn=14/37 queue=0 build=81ms verts=37k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 11:56:00.453  2065  2117 I Perf    : fps=4.9 worst=5 drawn=14/37 queue=0 build=81ms verts=37k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 11:56:02.481  2065  2117 I Perf    : fps=4.9 worst=5 drawn=14/37 queue=0 build=81ms verts=37k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 11:56:04.546  2065  2117 I Perf    : fps=4.8 worst=5 drawn=14/37 queue=0 build=81ms verts=37k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 11:56:06.640  2065  2117 I Perf    : fps=4.8 worst=5 drawn=14/37 queue=0 build=81ms verts=37k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 11:56:08.766  2065  2117 I Perf    : fps=4.7 worst=5 drawn=14/37 queue=0 build=81ms verts=37k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 11:56:10.871  2065  2117 I Perf    : fps=4.8 worst=5 drawn=14/37 queue=0 build=81ms verts=37k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 11:56:12.984  2065  2117 I Perf    : fps=4.7 worst=5 drawn=14/37 queue=0 build=81ms verts=37k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 11:56:14.984  2440  2440 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 11:56:14.995  2440  2440 I AndroidRuntime: Using default boot image
10-28 11:56:14.995  2440  2440 I AndroidRuntime: Leaving lock profiling enabled
10-28 11:56:15.038  2065  2117 I Perf    : fps=4.9 worst=5 drawn=14/37 queue=0 build=81ms verts=37k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 11:56:15.079  2440  2440 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 11:56:15.686  2440  2440 D AndroidRuntime: Shutting down VM
10-28 11:56:17.097  2065  2117 I Perf    : fps=4.9 worst=5 drawn=17/37 queue=0 build=81ms verts=43k cam=1.5,1.6,1.5 yaw=2.81 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-28 11:56:17.658   505   505 I ActivityManager: Killing 2029:com.android.dynsystem/1000 (adj 985): empty #17
10-28 11:56:17.659   505   505 I ActivityManager: Killing 1220:com.android.deskclock/u0a106 (adj 985): empty #18
10-28 11:56:19.111  2065  2117 I Perf    : fps=5.5 worst=6 drawn=17/37 queue=0 build=81ms verts=43k cam=1.5,1.6,1.5 yaw=2.81 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-28 11:56:20.783  2476  2476 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 11:56:20.785  2476  2476 I AndroidRuntime: Using default boot image
10-28 11:56:20.785  2476  2476 I AndroidRuntime: Leaving lock profiling enabled
10-28 11:56:20.858  2476  2476 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 11:56:20.881  2476  2476 D AndroidRuntime: Shutting down VM
10-28 11:56:21.243  2065  2117 I Perf    : fps=5.2 worst=5 drawn=17/37 queue=0 build=81ms verts=43k cam=1.5,1.6,1.5 yaw=2.81 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 11:56:23.374  2065  2117 I Perf    : fps=5.6 worst=6 drawn=17/37 queue=0 build=81ms verts=43k cam=1.5,1.6,1.5 yaw=2.81 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 11:56:25.404  2065  2117 I Perf    : fps=5.4 worst=6 drawn=17/37 queue=0 build=81ms verts=43k cam=1.5,1.6,1.5 yaw=2.81 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 11:56:25.654  2493  2493 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 11:56:25.655  2493  2493 I AndroidRuntime: Using default boot image
10-28 11:56:25.655  2493  2493 I AndroidRuntime: Leaving lock profiling enabled
10-28 11:56:25.760  2493  2493 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 11:56:27.333  2493  2493 D AndroidRuntime: Shutting down VM
10-28 11:56:27.541  2065  2117 I Perf    : fps=5.6 worst=6 drawn=17/37 queue=0 build=81ms verts=43k cam=1.1,1.6,2.8 yaw=2.81 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 11:56:29.629  2065  2117 I Perf    : fps=5.7 worst=6 drawn=17/37 queue=0 build=81ms verts=43k cam=1.1,1.6,2.8 yaw=2.81 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 11:56:31.692  2065  2117 I Perf    : fps=5.8 worst=6 drawn=17/37 queue=0 build=81ms verts=43k cam=1.1,1.6,2.8 yaw=2.81 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 11:56:33.838  2065  2117 I Perf    : fps=5.6 worst=5 drawn=17/37 queue=0 build=81ms verts=43k cam=1.1,1.6,2.8 yaw=2.81 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 11:56:35.982  2065  2117 I Perf    : fps=5.6 worst=6 drawn=17/37 queue=0 build=81ms verts=43k cam=1.1,1.6,2.8 yaw=2.81 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
```
## Step log
```
orld shader compiled
10-28 11:55:46.086  2065  2117 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 11:55:46.086  2065  2117 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 11:55:48.167  2065  2117 I Perf    : fps=4.1 worst=7 drawn=10/16 queue=2 build=210ms verts=26k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=10 noMesh=0 culled=4
10-28 11:55:50.180  2065  2117 I Perf    : fps=4.9 worst=5 drawn=14/36 queue=1 build=156ms verts=37k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=20
10-28 11:55:52.323  2065  2117 I Perf    : fps=4.7 worst=5 drawn=14/37 queue=0 build=81ms verts=37k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 11:55:54.371  2065  2117 I Perf    : fps=4.9 worst=5 drawn=14/37 queue=0 build=81ms verts=37k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 11:55:56.373  2065  2117 I Perf    : fps=5.0 worst=5 drawn=14/37 queue=0 build=81ms verts=37k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 11:55:58.395  2065  2117 I Perf    : fps=4.9 worst=5 drawn=14/37 queue=0 build=81ms verts=37k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 11:56:00.453  2065  2117 I Perf    : fps=4.9 worst=5 drawn=14/37 queue=0 build=81ms verts=37k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 11:56:02.481  2065  2117 I Perf    : fps=4.9 worst=5 drawn=14/37 queue=0 build=81ms verts=37k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 11:56:04.546  2065  2117 I Perf    : fps=4.8 worst=5 drawn=14/37 queue=0 build=81ms verts=37k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 11:56:06.640  2065  2117 I Perf    : fps=4.8 worst=5 drawn=14/37 queue=0 build=81ms verts=37k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 11:56:08.766  2065  2117 I Perf    : fps=4.7 worst=5 drawn=14/37 queue=0 build=81ms verts=37k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 11:56:10.871  2065  2117 I Perf    : fps=4.8 worst=5 drawn=14/37 queue=0 build=81ms verts=37k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 11:56:11.320  2435  2435 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 11:56:12.984  2065  2117 I Perf    : fps=4.7 worst=5 drawn=14/37 queue=0 build=81ms verts=37k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 11:56:14.984  2440  2440 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 11:56:14.995  2440  2440 I AndroidRuntime: Using default boot image
10-28 11:56:14.995  2440  2440 I AndroidRuntime: Leaving lock profiling enabled
10-28 11:56:15.038  2065  2117 I Perf    : fps=4.9 worst=5 drawn=14/37 queue=0 build=81ms verts=37k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 11:56:15.079  2440  2440 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 11:56:15.686  2440  2440 D AndroidRuntime: Shutting down VM
10-28 11:56:17.097  2065  2117 I Perf    : fps=4.9 worst=5 drawn=17/37 queue=0 build=81ms verts=43k cam=1.5,1.6,1.5 yaw=2.81 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-28 11:56:19.111  2065  2117 I Perf    : fps=5.5 worst=6 drawn=17/37 queue=0 build=81ms verts=43k cam=1.5,1.6,1.5 yaw=2.81 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-28 11:56:20.783  2476  2476 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 11:56:20.785  2476  2476 I AndroidRuntime: Using default boot image
10-28 11:56:20.785  2476  2476 I AndroidRuntime: Leaving lock profiling enabled
10-28 11:56:20.858  2476  2476 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 11:56:20.881  2476  2476 D AndroidRuntime: Shutting down VM
10-28 11:56:21.243  2065  2117 I Perf    : fps=5.2 worst=5 drawn=17/37 queue=0 build=81ms verts=43k cam=1.5,1.6,1.5 yaw=2.81 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 11:56:23.374  2065  2117 I Perf    : fps=5.6 worst=6 drawn=17/37 queue=0 build=81ms verts=43k cam=1.5,1.6,1.5 yaw=2.81 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 11:56:25.404  2065  2117 I Perf    : fps=5.4 worst=6 drawn=17/37 queue=0 build=81ms verts=43k cam=1.5,1.6,1.5 yaw=2.81 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 11:56:25.654  2493  2493 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 11:56:25.655  2493  2493 I AndroidRuntime: Using default boot image
10-28 11:56:25.655  2493  2493 I AndroidRuntime: Leaving lock profiling enabled
10-28 11:56:25.760  2493  2493 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 11:56:27.333  2493  2493 D AndroidRuntime: Shutting down VM
10-28 11:56:27.541  2065  2117 I Perf    : fps=5.6 worst=6 drawn=17/37 queue=0 build=81ms verts=43k cam=1.1,1.6,2.8 yaw=2.81 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 11:56:29.629  2065  2117 I Perf    : fps=5.7 worst=6 drawn=17/37 queue=0 build=81ms verts=43k cam=1.1,1.6,2.8 yaw=2.81 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 11:56:31.692  2065  2117 I Perf    : fps=5.8 worst=6 drawn=17/37 queue=0 build=81ms verts=43k cam=1.1,1.6,2.8 yaw=2.81 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 11:56:32.469  2512  2512 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 11:56:33.838  2065  2117 I Perf    : fps=5.6 worst=5 drawn=17/37 queue=0 build=81ms verts=43k cam=1.1,1.6,2.8 yaw=2.81 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-28 11:56:35.982  2065  2117 I Perf    : fps=5.6 worst=6 drawn=17/37 queue=0 build=81ms verts=43k cam=1.1,1.6,2.8 yaw=2.81 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
+ '[' -z 2065 ']'
+ grep -qa 'FATAL EXCEPTION' emulator-shots/logcat.txt
+ '[' 0 '!=' 0 ']'
+ echo 'OK: the app is alive, rendering, and the touch controls work'
OK: the app is alive, rendering, and the touch controls work
```
