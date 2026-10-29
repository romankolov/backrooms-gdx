# Emulator smoke test diagnostics

Commit: f71a8b0de39fcbbd76bd6760fd8e98a1c843755f
Outcome: success

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 29 00:56 /dev/kvm
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
INFO         | The active JSON Web Key Sets can be found here: /run/user/1001/avd/running/2754/jwks/80567b3e-4b58-481a-b62a-7384971152cb/active.jwk
INFO         | Scanning /run/user/1001/avd/running/2754/jwks/80567b3e-4b58-481a-b62a-7384971152cb for jwk keys.
INFO         | Started GRPC server at 127.0.0.1:8554, security: Local, auth: +token
INFO         | Advertising in: /run/user/1001/avd/running/pid_2754.ini
INFO         | Setting display: 0 configuration to: 1080x1920, dpi: 420x420 
INFO         | setDisplayActiveConfig: id:0, 1080x1920
INFO         | emulatorSetupEnvironment: Environment scene is not required
USER_INFO    | Emulator is performing a full startup. This may take upto two minutes, or more.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
INFO         | Boot completed in 20625 ms
INFO         | Increasing screen off timeout, logcat buffer size to 2M.
```
## Logcat (filtered)
```
10-29 00:59:16.402   502   542 I ActivityManager: Start proc 2330:com.android.traceur/u0a87 for broadcast {com.android.traceur/com.android.traceur.Receiver}
10-29 00:59:16.595   502  2108 I ActivityManager: Finished processing BOOT_COMPLETED for u0
10-29 00:59:16.596   502  2108 I ActivityManager: Killing 1968:com.android.localtransport/1000 (adj 985): empty #17
10-29 00:59:31.161   502   540 I ActivityManager: Killing 1269:com.android.cellbroadcastreceiver/u0a66 (adj 985): empty #17
10-29 00:59:32.298   502  2056 I ActivityManager: Killing 1940:com.android.quicksearchbox/u0a104 (adj 975): empty #17
10-29 00:59:35.779  2401  2401 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 00:59:35.804  2401  2401 I AndroidRuntime: Using default boot image
10-29 00:59:35.804  2401  2401 I AndroidRuntime: Leaving lock profiling enabled
10-29 00:59:35.969  2401  2401 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 00:59:36.069  2401  2401 D AndroidRuntime: Shutting down VM
10-29 00:59:36.679  2032  2062 I BackroomsGL: world shader compiled
10-29 00:59:37.427  2032  2062 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-29 00:59:37.427  2032  2062 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-29 00:59:38.544  2032  2062 I Perf    : fps=2.4 worst=6 drawn=5/8 queue=2 build=51ms verts=15k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=5 noMesh=0 culled=1
10-29 00:59:40.602  2032  2062 I Perf    : fps=5.8 worst=6 drawn=14/31 queue=1 build=17ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=15
10-29 00:59:42.609  2032  2062 I Perf    : fps=5.9 worst=6 drawn=14/37 queue=0 build=102ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:59:44.615  2032  2062 I Perf    : fps=5.5 worst=6 drawn=14/37 queue=0 build=102ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:59:46.771  2032  2062 I Perf    : fps=5.6 worst=6 drawn=14/37 queue=0 build=102ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:59:48.923  2032  2062 I Perf    : fps=5.6 worst=6 drawn=14/37 queue=0 build=102ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:59:51.108  2032  2062 I Perf    : fps=5.5 worst=6 drawn=14/37 queue=0 build=102ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:59:53.136  2032  2062 I Perf    : fps=5.9 worst=6 drawn=14/37 queue=0 build=102ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:59:55.245  2032  2062 I Perf    : fps=5.7 worst=6 drawn=14/37 queue=0 build=102ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:59:57.278  2032  2062 I Perf    : fps=5.4 worst=6 drawn=14/37 queue=0 build=102ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:59:59.294  2032  2062 I Perf    : fps=5.9 worst=6 drawn=14/37 queue=0 build=102ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 01:00:01.389  2032  2062 I Perf    : fps=5.7 worst=6 drawn=14/37 queue=0 build=102ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 01:00:03.545  2032  2062 I Perf    : fps=5.6 worst=6 drawn=14/37 queue=0 build=102ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 01:00:05.554  2032  2062 I Perf    : fps=5.5 worst=6 drawn=14/37 queue=0 build=102ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 01:00:07.364  2447  2447 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 01:00:07.375  2447  2447 I AndroidRuntime: Using default boot image
10-29 01:00:07.375  2447  2447 I AndroidRuntime: Leaving lock profiling enabled
10-29 01:00:07.425  2447  2447 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 01:00:07.697  2032  2062 I Perf    : fps=5.6 worst=6 drawn=14/37 queue=0 build=102ms verts=43k cam=0.5,1.6,0.5 yaw=1.17 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 01:00:08.101  2447  2447 D AndroidRuntime: Shutting down VM
10-29 01:00:09.725  2032  2062 I Perf    : fps=5.9 worst=6 drawn=17/37 queue=0 build=102ms verts=51k cam=0.5,1.6,0.5 yaw=2.82 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-29 01:00:11.881  2032  2062 I Perf    : fps=6.0 worst=6 drawn=17/37 queue=0 build=102ms verts=51k cam=0.5,1.6,0.5 yaw=2.82 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-29 01:00:12.552   502  2142 I ActivityManager: Killing 2064:com.android.calendar/u0a106 (adj 985): empty #17
10-29 01:00:12.552   502  2142 I ActivityManager: Killing 1419:android.process.acore/u0a63 (adj 985): empty #18
10-29 01:00:13.320  2463  2463 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 01:00:13.330  2463  2463 I AndroidRuntime: Using default boot image
10-29 01:00:13.330  2463  2463 I AndroidRuntime: Leaving lock profiling enabled
10-29 01:00:13.391  2463  2463 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 01:00:13.394  2463  2463 D AndroidRuntime: Shutting down VM
10-29 01:00:13.395  2463  2474 I AndroidRuntime: NOTE: attach of thread 'Binder:2463_1' failed
10-29 01:00:13.954  2032  2062 I Perf    : fps=5.8 worst=6 drawn=17/37 queue=0 build=102ms verts=51k cam=0.5,1.6,0.5 yaw=2.82 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-29 01:00:15.992  2032  2062 I Perf    : fps=5.9 worst=6 drawn=17/37 queue=0 build=102ms verts=51k cam=0.5,1.6,0.5 yaw=2.82 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-29 01:00:17.596   502   542 I ActivityManager: Start proc 2497:android.process.acore/u0a63 for content provider {com.android.providers.contacts/com.android.providers.contacts.VoicemailContentProvider}
10-29 01:00:17.696  2497  2497 I ContactsPerf: VoicemailContentProvider.onCreate start
10-29 01:00:17.698  2497  2497 I ContactsPerf: VoicemailContentProvider.onCreate finish
10-29 01:00:18.043  2032  2062 I Perf    : fps=5.8 worst=6 drawn=17/37 queue=0 build=102ms verts=51k cam=0.5,1.6,0.5 yaw=2.82 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-29 01:00:18.475  2526  2526 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 01:00:18.488  2526  2526 I AndroidRuntime: Using default boot image
10-29 01:00:18.488  2526  2526 I AndroidRuntime: Leaving lock profiling enabled
10-29 01:00:18.597  2526  2526 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 01:00:20.175  2032  2062 I Perf    : fps=6.1 worst=6 drawn=17/37 queue=2 build=102ms verts=51k cam=0.0,1.6,1.6 yaw=2.82 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-29 01:00:20.181  2526  2526 D AndroidRuntime: Shutting down VM
10-29 01:00:22.346  2032  2062 I Perf    : fps=5.5 worst=6 drawn=20/44 queue=0 build=7ms verts=60k cam=-0.1,1.6,1.6 yaw=2.82 pitch=0.00 torch=0 ready=true drawn=20 noMesh=0 culled=24
10-29 01:00:24.401  2032  2062 I Perf    : fps=5.3 worst=5 drawn=20/44 queue=0 build=7ms verts=60k cam=-0.1,1.6,1.6 yaw=2.82 pitch=0.00 torch=0 ready=true drawn=20 noMesh=0 culled=24
10-29 01:00:26.566  2032  2062 I Perf    : fps=5.1 worst=5 drawn=20/44 queue=0 build=7ms verts=60k cam=-0.1,1.6,1.6 yaw=2.82 pitch=0.00 torch=0 ready=true drawn=20 noMesh=0 culled=24
10-29 01:00:28.655  2032  2062 I Perf    : fps=5.3 worst=5 drawn=20/44 queue=0 build=7ms verts=60k cam=-0.1,1.6,1.6 yaw=2.82 pitch=0.00 torch=0 ready=true drawn=20 noMesh=0 culled=24
10-29 01:00:30.665  2032  2062 I Perf    : fps=5.0 worst=5 drawn=20/44 queue=0 build=7ms verts=60k cam=-0.1,1.6,1.6 yaw=2.82 pitch=0.00 torch=0 ready=true drawn=20 noMesh=0 culled=24
```
## Step log
```
=0 culled=15
10-29 00:59:42.609  2032  2062 I Perf    : fps=5.9 worst=6 drawn=14/37 queue=0 build=102ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:59:44.615  2032  2062 I Perf    : fps=5.5 worst=6 drawn=14/37 queue=0 build=102ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:59:46.771  2032  2062 I Perf    : fps=5.6 worst=6 drawn=14/37 queue=0 build=102ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:59:48.923  2032  2062 I Perf    : fps=5.6 worst=6 drawn=14/37 queue=0 build=102ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:59:51.108  2032  2062 I Perf    : fps=5.5 worst=6 drawn=14/37 queue=0 build=102ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:59:53.136  2032  2062 I Perf    : fps=5.9 worst=6 drawn=14/37 queue=0 build=102ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:59:55.245  2032  2062 I Perf    : fps=5.7 worst=6 drawn=14/37 queue=0 build=102ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:59:57.278  2032  2062 I Perf    : fps=5.4 worst=6 drawn=14/37 queue=0 build=102ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:59:59.294  2032  2062 I Perf    : fps=5.9 worst=6 drawn=14/37 queue=0 build=102ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 01:00:01.389  2032  2062 I Perf    : fps=5.7 worst=6 drawn=14/37 queue=0 build=102ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 01:00:02.291  2441  2441 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-29 01:00:03.545  2032  2062 I Perf    : fps=5.6 worst=6 drawn=14/37 queue=0 build=102ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 01:00:05.554  2032  2062 I Perf    : fps=5.5 worst=6 drawn=14/37 queue=0 build=102ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 01:00:07.364  2447  2447 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 01:00:07.375  2447  2447 I AndroidRuntime: Using default boot image
10-29 01:00:07.375  2447  2447 I AndroidRuntime: Leaving lock profiling enabled
10-29 01:00:07.425  2447  2447 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 01:00:07.697  2032  2062 I Perf    : fps=5.6 worst=6 drawn=14/37 queue=0 build=102ms verts=43k cam=0.5,1.6,0.5 yaw=1.17 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 01:00:08.101  2447  2447 D AndroidRuntime: Shutting down VM
10-29 01:00:09.725  2032  2062 I Perf    : fps=5.9 worst=6 drawn=17/37 queue=0 build=102ms verts=51k cam=0.5,1.6,0.5 yaw=2.82 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-29 01:00:11.881  2032  2062 I Perf    : fps=6.0 worst=6 drawn=17/37 queue=0 build=102ms verts=51k cam=0.5,1.6,0.5 yaw=2.82 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-29 01:00:13.320  2463  2463 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 01:00:13.330  2463  2463 I AndroidRuntime: Using default boot image
10-29 01:00:13.330  2463  2463 I AndroidRuntime: Leaving lock profiling enabled
10-29 01:00:13.391  2463  2463 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 01:00:13.394  2463  2463 D AndroidRuntime: Shutting down VM
10-29 01:00:13.395  2463  2474 I AndroidRuntime: NOTE: attach of thread 'Binder:2463_1' failed
10-29 01:00:13.954  2032  2062 I Perf    : fps=5.8 worst=6 drawn=17/37 queue=0 build=102ms verts=51k cam=0.5,1.6,0.5 yaw=2.82 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-29 01:00:15.992  2032  2062 I Perf    : fps=5.9 worst=6 drawn=17/37 queue=0 build=102ms verts=51k cam=0.5,1.6,0.5 yaw=2.82 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-29 01:00:17.696  2497  2497 I ContactsPerf: VoicemailContentProvider.onCreate start
10-29 01:00:17.698  2497  2497 I ContactsPerf: VoicemailContentProvider.onCreate finish
10-29 01:00:18.043  2032  2062 I Perf    : fps=5.8 worst=6 drawn=17/37 queue=0 build=102ms verts=51k cam=0.5,1.6,0.5 yaw=2.82 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-29 01:00:18.475  2526  2526 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 01:00:18.488  2526  2526 I AndroidRuntime: Using default boot image
10-29 01:00:18.488  2526  2526 I AndroidRuntime: Leaving lock profiling enabled
10-29 01:00:18.597  2526  2526 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 01:00:20.175  2032  2062 I Perf    : fps=6.1 worst=6 drawn=17/37 queue=2 build=102ms verts=51k cam=0.0,1.6,1.6 yaw=2.82 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-29 01:00:20.181  2526  2526 D AndroidRuntime: Shutting down VM
10-29 01:00:22.346  2032  2062 I Perf    : fps=5.5 worst=6 drawn=20/44 queue=0 build=7ms verts=60k cam=-0.1,1.6,1.6 yaw=2.82 pitch=0.00 torch=0 ready=true drawn=20 noMesh=0 culled=24
10-29 01:00:24.401  2032  2062 I Perf    : fps=5.3 worst=5 drawn=20/44 queue=0 build=7ms verts=60k cam=-0.1,1.6,1.6 yaw=2.82 pitch=0.00 torch=0 ready=true drawn=20 noMesh=0 culled=24
10-29 01:00:25.244  2544  2544 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-29 01:00:26.566  2032  2062 I Perf    : fps=5.1 worst=5 drawn=20/44 queue=0 build=7ms verts=60k cam=-0.1,1.6,1.6 yaw=2.82 pitch=0.00 torch=0 ready=true drawn=20 noMesh=0 culled=24
10-29 01:00:28.655  2032  2062 I Perf    : fps=5.3 worst=5 drawn=20/44 queue=0 build=7ms verts=60k cam=-0.1,1.6,1.6 yaw=2.82 pitch=0.00 torch=0 ready=true drawn=20 noMesh=0 culled=24
10-29 01:00:30.665  2032  2062 I Perf    : fps=5.0 worst=5 drawn=20/44 queue=0 build=7ms verts=60k cam=-0.1,1.6,1.6 yaw=2.82 pitch=0.00 torch=0 ready=true drawn=20 noMesh=0 culled=24
+ '[' -z 2032 ']'
+ grep -qa 'FATAL EXCEPTION' emulator-shots/logcat.txt
+ '[' 0 '!=' 0 ']'
+ echo 'OK: the app is alive, rendering, and the touch controls work'
OK: the app is alive, rendering, and the touch controls work
```
