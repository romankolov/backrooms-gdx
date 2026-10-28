# Emulator smoke test diagnostics

Commit: 2a122c21a0c20b3fd5b53037bd32f6d8759b36a6
Outcome: success

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 28 08:29 /dev/kvm
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
INFO         | The active JSON Web Key Sets can be found here: /run/user/1001/avd/running/2752/jwks/4f442254-fd20-413a-a4c8-9e3c234c029b/active.jwk
INFO         | Scanning /run/user/1001/avd/running/2752/jwks/4f442254-fd20-413a-a4c8-9e3c234c029b for jwk keys.
INFO         | Started GRPC server at 127.0.0.1:8554, security: Local, auth: +token
INFO         | Advertising in: /run/user/1001/avd/running/pid_2752.ini
INFO         | Setting display: 0 configuration to: 1080x1920, dpi: 420x420 
INFO         | setDisplayActiveConfig: id:0, 1080x1920
INFO         | emulatorSetupEnvironment: Environment scene is not required
USER_INFO    | Emulator is performing a full startup. This may take upto two minutes, or more.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
INFO         | Boot completed in 25238 ms
INFO         | Increasing screen off timeout, logcat buffer size to 2M.
```
## Logcat (filtered)
```
10-28 08:32:37.348  2197  2197 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:32:37.412  2197  2197 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:32:37.418  2197  2197 D AndroidRuntime: Shutting down VM
10-28 08:32:37.529   510  1075 I ActivityManager: Killing 1902:com.android.calendar/u0a108 (adj 985): empty #17
10-28 08:32:38.329   510   550 I ActivityManager: Start proc 2275:com.backrooms.gdx.debug/u0a130 for pre-top-activity {com.backrooms.gdx.debug/com.backrooms.gdx.android.AndroidLauncher}
10-28 08:32:52.422   510   550 I ActivityManager: Start proc 2359:com.android.cellbroadcastreceiver.module/u0a122 for broadcast {com.android.cellbroadcastreceiver.module/com.android.cellbroadcastreceiver.CellBroadcastReceiver}
10-28 08:32:52.505   510  1283 I ActivityManager: Killing 1926:com.android.camera2/u0a111 (adj 985): empty #17
10-28 08:32:53.227   510  1283 I ActivityManager: Killing 1948:com.android.contacts/u0a98 (adj 975): empty #17
10-28 08:33:00.107  2385  2385 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:33:00.117  2385  2385 I AndroidRuntime: Using default boot image
10-28 08:33:00.117  2385  2385 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:33:00.237  2385  2385 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:33:00.241  2385  2385 D AndroidRuntime: Shutting down VM
10-28 08:33:00.244  2385  2396 I AndroidRuntime: NOTE: attach of thread 'Binder:2385_1' failed
10-28 08:33:00.763  2275  2305 I BackroomsGL: world shader compiled
10-28 08:33:00.813  2275  2305 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 08:33:00.813  2275  2305 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 08:33:02.733  2275  2305 I Perf    : fps=7.4 worst=46 drawn=14/23 queue=0 build=12ms verts=35k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=7
10-28 08:33:04.829  2275  2305 I Perf    : fps=7.1 worst=7 drawn=18/37 queue=0 build=164ms verts=46k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:33:06.956  2275  2305 I Perf    : fps=7.1 worst=8 drawn=18/37 queue=0 build=164ms verts=46k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:33:09.067  2275  2305 I Perf    : fps=7.1 worst=9 drawn=18/37 queue=0 build=164ms verts=46k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:33:11.118  2275  2305 I Perf    : fps=7.3 worst=8 drawn=18/37 queue=0 build=164ms verts=46k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:33:13.140  2275  2305 I Perf    : fps=7.4 worst=8 drawn=18/37 queue=0 build=164ms verts=46k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:33:15.233  2275  2305 I Perf    : fps=7.2 worst=8 drawn=18/37 queue=0 build=164ms verts=46k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:33:17.337  2275  2305 I Perf    : fps=7.1 worst=8 drawn=18/37 queue=0 build=164ms verts=46k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:33:19.393  2275  2305 I Perf    : fps=7.3 worst=8 drawn=18/37 queue=0 build=164ms verts=46k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:33:21.460  2275  2305 I Perf    : fps=7.3 worst=8 drawn=18/37 queue=0 build=164ms verts=46k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:33:23.541  2275  2305 I Perf    : fps=7.2 worst=8 drawn=18/37 queue=0 build=164ms verts=46k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:33:25.576  2275  2305 I Perf    : fps=7.4 worst=8 drawn=18/37 queue=0 build=164ms verts=46k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:33:27.571  2275  2305 I Perf    : fps=7.0 worst=8 drawn=18/37 queue=0 build=164ms verts=46k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:33:29.634  2275  2305 I Perf    : fps=7.3 worst=9 drawn=18/37 queue=0 build=164ms verts=46k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:33:30.815  2412  2412 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:33:30.816  2412  2412 I AndroidRuntime: Using default boot image
10-28 08:33:30.816  2412  2412 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:33:30.913  2412  2412 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:33:31.528  2412  2412 D AndroidRuntime: Shutting down VM
10-28 08:33:31.711  2275  2305 I Perf    : fps=7.2 worst=8 drawn=14/37 queue=0 build=164ms verts=36k cam=-0.5,1.6,-0.5 yaw=2.85 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:33:33.759  2275  2305 I Perf    : fps=7.8 worst=9 drawn=14/37 queue=0 build=164ms verts=36k cam=-0.5,1.6,-0.5 yaw=2.85 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:33:35.801  2275  2305 I Perf    : fps=7.8 worst=8 drawn=14/37 queue=0 build=164ms verts=36k cam=-0.5,1.6,-0.5 yaw=2.85 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:33:36.477  2428  2428 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:33:36.494  2428  2428 I AndroidRuntime: Using default boot image
10-28 08:33:36.494  2428  2428 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:33:36.514   510   578 I ActivityManager: Killing 1969:com.android.dynsystem/1000 (adj 985): empty #17
10-28 08:33:36.514   510   578 I ActivityManager: Killing 1229:com.android.deskclock/u0a105 (adj 985): empty #18
10-28 08:33:36.514   510   578 I ActivityManager: Killing 1288:com.android.cellbroadcastreceiver/u0a64 (adj 995): empty #19
10-28 08:33:36.679  2428  2428 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:33:36.690  2428  2428 D AndroidRuntime: Shutting down VM
10-28 08:33:37.885  2275  2305 I Perf    : fps=7.2 worst=8 drawn=14/37 queue=0 build=164ms verts=36k cam=-0.5,1.6,-0.5 yaw=2.85 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-28 08:33:39.990  2275  2305 I Perf    : fps=7.6 worst=9 drawn=14/37 queue=0 build=164ms verts=36k cam=-0.5,1.6,-0.5 yaw=2.85 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-28 08:33:41.649  2464  2464 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:33:41.660  2464  2464 I AndroidRuntime: Using default boot image
10-28 08:33:41.660  2464  2464 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:33:41.743  2464  2464 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:33:42.009  2275  2305 I Perf    : fps=7.4 worst=8 drawn=14/37 queue=0 build=164ms verts=36k cam=-0.5,1.6,-0.5 yaw=2.85 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-28 08:33:43.294  2464  2464 D AndroidRuntime: Shutting down VM
10-28 08:33:44.083  2275  2305 I Perf    : fps=6.8 worst=8 drawn=18/44 queue=0 build=112ms verts=47k cam=-1.0,1.6,1.3 yaw=2.85 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-28 08:33:46.199  2275  2305 I Perf    : fps=6.6 worst=8 drawn=18/44 queue=0 build=112ms verts=47k cam=-1.0,1.6,1.3 yaw=2.85 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-28 08:33:48.268  2275  2305 I Perf    : fps=6.3 worst=7 drawn=18/44 queue=0 build=112ms verts=47k cam=-1.0,1.6,1.3 yaw=2.85 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-28 08:33:50.378  2275  2305 I Perf    : fps=6.2 worst=7 drawn=18/44 queue=0 build=112ms verts=47k cam=-1.0,1.6,1.3 yaw=2.85 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-28 08:33:52.523  2275  2305 I Perf    : fps=6.5 worst=7 drawn=18/44 queue=0 build=112ms verts=47k cam=-1.0,1.6,1.3 yaw=2.85 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
```
## Step log
```
0,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 08:33:02.733  2275  2305 I Perf    : fps=7.4 worst=46 drawn=14/23 queue=0 build=12ms verts=35k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=7
10-28 08:33:04.829  2275  2305 I Perf    : fps=7.1 worst=7 drawn=18/37 queue=0 build=164ms verts=46k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:33:06.956  2275  2305 I Perf    : fps=7.1 worst=8 drawn=18/37 queue=0 build=164ms verts=46k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:33:09.067  2275  2305 I Perf    : fps=7.1 worst=9 drawn=18/37 queue=0 build=164ms verts=46k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:33:11.118  2275  2305 I Perf    : fps=7.3 worst=8 drawn=18/37 queue=0 build=164ms verts=46k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:33:13.140  2275  2305 I Perf    : fps=7.4 worst=8 drawn=18/37 queue=0 build=164ms verts=46k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:33:15.233  2275  2305 I Perf    : fps=7.2 worst=8 drawn=18/37 queue=0 build=164ms verts=46k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:33:17.337  2275  2305 I Perf    : fps=7.1 worst=8 drawn=18/37 queue=0 build=164ms verts=46k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:33:19.393  2275  2305 I Perf    : fps=7.3 worst=8 drawn=18/37 queue=0 build=164ms verts=46k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:33:21.460  2275  2305 I Perf    : fps=7.3 worst=8 drawn=18/37 queue=0 build=164ms verts=46k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:33:23.541  2275  2305 I Perf    : fps=7.2 worst=8 drawn=18/37 queue=0 build=164ms verts=46k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:33:25.576  2275  2305 I Perf    : fps=7.4 worst=8 drawn=18/37 queue=0 build=164ms verts=46k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:33:26.070  2406  2406 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 08:33:27.571  2275  2305 I Perf    : fps=7.0 worst=8 drawn=18/37 queue=0 build=164ms verts=46k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:33:29.634  2275  2305 I Perf    : fps=7.3 worst=9 drawn=18/37 queue=0 build=164ms verts=46k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:33:30.815  2412  2412 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:33:30.816  2412  2412 I AndroidRuntime: Using default boot image
10-28 08:33:30.816  2412  2412 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:33:30.913  2412  2412 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:33:31.528  2412  2412 D AndroidRuntime: Shutting down VM
10-28 08:33:31.711  2275  2305 I Perf    : fps=7.2 worst=8 drawn=14/37 queue=0 build=164ms verts=36k cam=-0.5,1.6,-0.5 yaw=2.85 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:33:33.759  2275  2305 I Perf    : fps=7.8 worst=9 drawn=14/37 queue=0 build=164ms verts=36k cam=-0.5,1.6,-0.5 yaw=2.85 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:33:35.801  2275  2305 I Perf    : fps=7.8 worst=8 drawn=14/37 queue=0 build=164ms verts=36k cam=-0.5,1.6,-0.5 yaw=2.85 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:33:36.477  2428  2428 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:33:36.494  2428  2428 I AndroidRuntime: Using default boot image
10-28 08:33:36.494  2428  2428 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:33:36.679  2428  2428 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:33:36.690  2428  2428 D AndroidRuntime: Shutting down VM
10-28 08:33:37.885  2275  2305 I Perf    : fps=7.2 worst=8 drawn=14/37 queue=0 build=164ms verts=36k cam=-0.5,1.6,-0.5 yaw=2.85 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-28 08:33:39.990  2275  2305 I Perf    : fps=7.6 worst=9 drawn=14/37 queue=0 build=164ms verts=36k cam=-0.5,1.6,-0.5 yaw=2.85 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-28 08:33:41.649  2464  2464 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:33:41.660  2464  2464 I AndroidRuntime: Using default boot image
10-28 08:33:41.660  2464  2464 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:33:41.743  2464  2464 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:33:42.009  2275  2305 I Perf    : fps=7.4 worst=8 drawn=14/37 queue=0 build=164ms verts=36k cam=-0.5,1.6,-0.5 yaw=2.85 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-28 08:33:43.294  2464  2464 D AndroidRuntime: Shutting down VM
10-28 08:33:44.083  2275  2305 I Perf    : fps=6.8 worst=8 drawn=18/44 queue=0 build=112ms verts=47k cam=-1.0,1.6,1.3 yaw=2.85 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-28 08:33:46.199  2275  2305 I Perf    : fps=6.6 worst=8 drawn=18/44 queue=0 build=112ms verts=47k cam=-1.0,1.6,1.3 yaw=2.85 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-28 08:33:48.268  2275  2305 I Perf    : fps=6.3 worst=7 drawn=18/44 queue=0 build=112ms verts=47k cam=-1.0,1.6,1.3 yaw=2.85 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-28 08:33:48.413  2483  2483 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 08:33:50.378  2275  2305 I Perf    : fps=6.2 worst=7 drawn=18/44 queue=0 build=112ms verts=47k cam=-1.0,1.6,1.3 yaw=2.85 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-28 08:33:52.523  2275  2305 I Perf    : fps=6.5 worst=7 drawn=18/44 queue=0 build=112ms verts=47k cam=-1.0,1.6,1.3 yaw=2.85 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
+ '[' -z 2275 ']'
+ grep -qa 'FATAL EXCEPTION' emulator-shots/logcat.txt
+ '[' 0 '!=' 0 ']'
+ echo 'OK: the app is alive, rendering, and the touch controls work'
OK: the app is alive, rendering, and the touch controls work
```
