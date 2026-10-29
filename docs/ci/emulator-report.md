# Emulator smoke test diagnostics

Commit: 4a856dcb55aa6ec583ecca88062ac7c8e1e6494d
Outcome: success

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 29 04:18 /dev/kvm
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
INFO         | The active JSON Web Key Sets can be found here: /run/user/1001/avd/running/3096/jwks/0a7f3051-264e-4c06-ac6d-f8b3bfbaa555/active.jwk
INFO         | Scanning /run/user/1001/avd/running/3096/jwks/0a7f3051-264e-4c06-ac6d-f8b3bfbaa555 for jwk keys.
INFO         | Started GRPC server at 127.0.0.1:8554, security: Local, auth: +token
INFO         | Advertising in: /run/user/1001/avd/running/pid_3096.ini
INFO         | Setting display: 0 configuration to: 1080x1920, dpi: 420x420 
INFO         | setDisplayActiveConfig: id:0, 1080x1920
INFO         | emulatorSetupEnvironment: Environment scene is not required
USER_INFO    | Emulator is performing a full startup. This may take upto two minutes, or more.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
INFO         | Boot completed in 34449 ms
INFO         | Increasing screen off timeout, logcat buffer size to 2M.
```
## Logcat (filtered)
```
10-29 04:23:58.819   514   554 I ActivityManager: Start proc 2408:com.android.traceur/u0a91 for broadcast {com.android.traceur/com.android.traceur.Receiver}
10-29 04:23:59.056   514  2129 I ActivityManager: Finished processing BOOT_COMPLETED for u0
10-29 04:23:59.058   514  2129 I ActivityManager: Killing 1984:com.android.printspooler/u0a87 (adj 985): empty #17
10-29 04:24:01.220   440  2207 I iorapd  : Perfetto TraceBuffer saved to file: /data/misc/iorapd/com.backrooms.gdx.debug/1/com.backrooms.gdx.android.AndroidLauncher/raw_traces/1793247841219996955.perfetto_trace.pb
10-29 04:24:13.611   514   552 I ActivityManager: Killing 1298:com.android.cellbroadcastreceiver/u0a66 (adj 985): empty #17
10-29 04:24:19.445   514   544 I ActivityManager: Killing 2078:com.android.calendar/u0a108 (adj 985): empty #17
10-29 04:24:19.445   514   544 I ActivityManager: Killing 2009:com.android.quicksearchbox/u0a101 (adj 985): empty #18
10-29 04:24:19.452   514  1140 I ActivityManager: Killing 1462:android.process.acore/u0a64 (adj 975): empty #17
10-29 04:24:19.768  2484  2484 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 04:24:19.770  2484  2484 I AndroidRuntime: Using default boot image
10-29 04:24:19.770  2484  2484 I AndroidRuntime: Leaving lock profiling enabled
10-29 04:24:19.909  2484  2484 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 04:24:20.011  2484  2484 D AndroidRuntime: Shutting down VM
10-29 04:24:20.580  2213  2278 I BackroomsGL: world shader compiled
10-29 04:24:21.401  2213  2278 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-29 04:24:21.401  2213  2278 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-29 04:24:23.752  2213  2278 I Perf    : fps=1.5 worst=9 drawn=1/3 queue=2 build=11ms verts=3k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=1 noMesh=0 culled=0
10-29 04:24:25.888  2213  2278 I Perf    : fps=3.3 worst=3 drawn=11/17 queue=2 build=18ms verts=34k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=11 noMesh=0 culled=4
10-29 04:24:28.194  2213  2278 I Perf    : fps=3.0 worst=3 drawn=14/31 queue=2 build=6ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=15
10-29 04:24:30.225  2213  2278 I Perf    : fps=2.9 worst=3 drawn=14/37 queue=0 build=66ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 04:24:32.438  2213  2278 I Perf    : fps=3.2 worst=3 drawn=14/37 queue=0 build=66ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 04:24:34.489  2213  2278 I Perf    : fps=2.9 worst=3 drawn=14/37 queue=0 build=66ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 04:24:36.551  2213  2278 I Perf    : fps=2.9 worst=3 drawn=14/37 queue=0 build=66ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 04:24:38.899  2213  2278 I Perf    : fps=3.0 worst=3 drawn=14/37 queue=0 build=66ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 04:24:40.950  2213  2278 I Perf    : fps=2.9 worst=3 drawn=14/37 queue=0 build=66ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 04:24:43.198  2213  2278 I Perf    : fps=3.1 worst=3 drawn=14/37 queue=0 build=66ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 04:24:45.296  2213  2278 I Perf    : fps=2.9 worst=3 drawn=14/37 queue=0 build=66ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 04:24:47.331  2213  2278 I Perf    : fps=2.9 worst=3 drawn=14/37 queue=0 build=66ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 04:24:49.427  2213  2278 I Perf    : fps=2.9 worst=3 drawn=14/37 queue=0 build=66ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 04:24:51.482  2213  2278 I Perf    : fps=2.9 worst=3 drawn=14/37 queue=0 build=66ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 04:24:52.376  2535  2535 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 04:24:52.398  2535  2535 I AndroidRuntime: Using default boot image
10-29 04:24:52.401  2535  2535 I AndroidRuntime: Leaving lock profiling enabled
10-29 04:24:52.594  2535  2535 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 04:24:53.207  2535  2535 D AndroidRuntime: Shutting down VM
10-29 04:24:53.485  2213  2278 I Perf    : fps=3.0 worst=3 drawn=16/37 queue=0 build=66ms verts=48k cam=0.5,1.6,0.5 yaw=2.83 pitch=0.00 torch=1 ready=true drawn=16 noMesh=0 culled=21
10-29 04:24:55.592  2213  2278 I Perf    : fps=3.3 worst=3 drawn=17/37 queue=0 build=66ms verts=52k cam=0.5,1.6,0.5 yaw=2.83 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-29 04:24:57.740  2213  2278 I Perf    : fps=3.3 worst=3 drawn=17/37 queue=0 build=66ms verts=52k cam=0.5,1.6,0.5 yaw=2.83 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-29 04:24:57.821   514   554 I ActivityManager: Start proc 2549:android.process.acore/u0a64 for content provider {com.android.providers.contacts/com.android.providers.contacts.VoicemailContentProvider}
10-29 04:24:57.919  2549  2549 I ContactsPerf: VoicemailContentProvider.onCreate start
10-29 04:24:57.932  2549  2549 I ContactsPerf: VoicemailContentProvider.onCreate finish
10-29 04:24:58.468  2579  2579 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 04:24:58.475  2579  2579 I AndroidRuntime: Using default boot image
10-29 04:24:58.476  2579  2579 I AndroidRuntime: Leaving lock profiling enabled
10-29 04:24:58.909  2579  2579 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 04:24:58.918  2579  2579 D AndroidRuntime: Shutting down VM
10-29 04:24:59.967  2213  2278 I Perf    : fps=3.1 worst=3 drawn=17/37 queue=0 build=66ms verts=52k cam=0.5,1.6,0.5 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-29 04:25:02.041  2213  2278 I Perf    : fps=3.4 worst=3 drawn=17/37 queue=0 build=66ms verts=52k cam=0.5,1.6,0.5 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-29 04:25:03.768  2595  2595 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 04:25:03.782  2595  2595 I AndroidRuntime: Using default boot image
10-29 04:25:03.783  2595  2595 I AndroidRuntime: Leaving lock profiling enabled
10-29 04:25:03.888  2595  2595 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 04:25:04.221  2213  2278 I Perf    : fps=3.2 worst=3 drawn=17/37 queue=0 build=66ms verts=52k cam=0.5,1.6,0.5 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-29 04:25:05.390  2595  2595 D AndroidRuntime: Shutting down VM
10-29 04:25:06.402  2213  2278 I Perf    : fps=3.2 worst=3 drawn=17/37 queue=0 build=66ms verts=52k cam=0.3,1.6,1.3 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-29 04:25:08.614  2213  2278 I Perf    : fps=3.2 worst=3 drawn=17/37 queue=0 build=66ms verts=52k cam=0.2,1.6,1.3 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-29 04:25:10.735  2213  2278 I Perf    : fps=3.3 worst=3 drawn=17/37 queue=0 build=66ms verts=52k cam=0.2,1.6,1.3 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-29 04:25:12.890  2213  2278 I Perf    : fps=3.2 worst=3 drawn=17/37 queue=0 build=66ms verts=52k cam=0.2,1.6,1.3 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-29 04:25:15.080  2213  2278 I Perf    : fps=3.2 worst=3 drawn=17/37 queue=0 build=66ms verts=52k cam=0.2,1.6,1.3 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-29 04:25:17.316  2213  2278 I Perf    : fps=3.1 worst=3 drawn=17/37 queue=0 build=66ms verts=52k cam=0.2,1.6,1.3 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
```
## Step log
```
st=3 drawn=11/17 queue=2 build=18ms verts=34k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=11 noMesh=0 culled=4
10-29 04:24:28.194  2213  2278 I Perf    : fps=3.0 worst=3 drawn=14/31 queue=2 build=6ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=15
10-29 04:24:30.225  2213  2278 I Perf    : fps=2.9 worst=3 drawn=14/37 queue=0 build=66ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 04:24:32.438  2213  2278 I Perf    : fps=3.2 worst=3 drawn=14/37 queue=0 build=66ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 04:24:34.489  2213  2278 I Perf    : fps=2.9 worst=3 drawn=14/37 queue=0 build=66ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 04:24:36.551  2213  2278 I Perf    : fps=2.9 worst=3 drawn=14/37 queue=0 build=66ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 04:24:38.899  2213  2278 I Perf    : fps=3.0 worst=3 drawn=14/37 queue=0 build=66ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 04:24:40.950  2213  2278 I Perf    : fps=2.9 worst=3 drawn=14/37 queue=0 build=66ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 04:24:43.198  2213  2278 I Perf    : fps=3.1 worst=3 drawn=14/37 queue=0 build=66ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 04:24:45.296  2213  2278 I Perf    : fps=2.9 worst=3 drawn=14/37 queue=0 build=66ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 04:24:45.991  2518  2518 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-29 04:24:47.331  2213  2278 I Perf    : fps=2.9 worst=3 drawn=14/37 queue=0 build=66ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 04:24:49.427  2213  2278 I Perf    : fps=2.9 worst=3 drawn=14/37 queue=0 build=66ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 04:24:51.482  2213  2278 I Perf    : fps=2.9 worst=3 drawn=14/37 queue=0 build=66ms verts=43k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 04:24:52.376  2535  2535 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 04:24:52.398  2535  2535 I AndroidRuntime: Using default boot image
10-29 04:24:52.401  2535  2535 I AndroidRuntime: Leaving lock profiling enabled
10-29 04:24:52.594  2535  2535 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 04:24:53.207  2535  2535 D AndroidRuntime: Shutting down VM
10-29 04:24:53.485  2213  2278 I Perf    : fps=3.0 worst=3 drawn=16/37 queue=0 build=66ms verts=48k cam=0.5,1.6,0.5 yaw=2.83 pitch=0.00 torch=1 ready=true drawn=16 noMesh=0 culled=21
10-29 04:24:55.592  2213  2278 I Perf    : fps=3.3 worst=3 drawn=17/37 queue=0 build=66ms verts=52k cam=0.5,1.6,0.5 yaw=2.83 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-29 04:24:57.740  2213  2278 I Perf    : fps=3.3 worst=3 drawn=17/37 queue=0 build=66ms verts=52k cam=0.5,1.6,0.5 yaw=2.83 pitch=0.00 torch=1 ready=true drawn=17 noMesh=0 culled=20
10-29 04:24:57.919  2549  2549 I ContactsPerf: VoicemailContentProvider.onCreate start
10-29 04:24:57.932  2549  2549 I ContactsPerf: VoicemailContentProvider.onCreate finish
10-29 04:24:58.468  2579  2579 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 04:24:58.475  2579  2579 I AndroidRuntime: Using default boot image
10-29 04:24:58.476  2579  2579 I AndroidRuntime: Leaving lock profiling enabled
10-29 04:24:58.909  2579  2579 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 04:24:58.918  2579  2579 D AndroidRuntime: Shutting down VM
10-29 04:24:59.967  2213  2278 I Perf    : fps=3.1 worst=3 drawn=17/37 queue=0 build=66ms verts=52k cam=0.5,1.6,0.5 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-29 04:25:02.041  2213  2278 I Perf    : fps=3.4 worst=3 drawn=17/37 queue=0 build=66ms verts=52k cam=0.5,1.6,0.5 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-29 04:25:03.768  2595  2595 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 04:25:03.782  2595  2595 I AndroidRuntime: Using default boot image
10-29 04:25:03.783  2595  2595 I AndroidRuntime: Leaving lock profiling enabled
10-29 04:25:03.888  2595  2595 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 04:25:04.221  2213  2278 I Perf    : fps=3.2 worst=3 drawn=17/37 queue=0 build=66ms verts=52k cam=0.5,1.6,0.5 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-29 04:25:05.390  2595  2595 D AndroidRuntime: Shutting down VM
10-29 04:25:06.402  2213  2278 I Perf    : fps=3.2 worst=3 drawn=17/37 queue=0 build=66ms verts=52k cam=0.3,1.6,1.3 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-29 04:25:08.614  2213  2278 I Perf    : fps=3.2 worst=3 drawn=17/37 queue=0 build=66ms verts=52k cam=0.2,1.6,1.3 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-29 04:25:10.735  2213  2278 I Perf    : fps=3.3 worst=3 drawn=17/37 queue=0 build=66ms verts=52k cam=0.2,1.6,1.3 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-29 04:25:11.269  2613  2613 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-29 04:25:12.890  2213  2278 I Perf    : fps=3.2 worst=3 drawn=17/37 queue=0 build=66ms verts=52k cam=0.2,1.6,1.3 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-29 04:25:15.080  2213  2278 I Perf    : fps=3.2 worst=3 drawn=17/37 queue=0 build=66ms verts=52k cam=0.2,1.6,1.3 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
10-29 04:25:17.316  2213  2278 I Perf    : fps=3.1 worst=3 drawn=17/37 queue=0 build=66ms verts=52k cam=0.2,1.6,1.3 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=20
+ '[' -z 2213 ']'
+ grep -qa 'FATAL EXCEPTION' emulator-shots/logcat.txt
+ '[' 0 '!=' 0 ']'
+ echo 'OK: the app is alive, rendering, and the touch controls work'
OK: the app is alive, rendering, and the touch controls work
```
