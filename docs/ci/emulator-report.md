# Emulator smoke test diagnostics

Commit: ba1e8d87d86f26b593f5e261940427a9c83a7b81
Outcome: success

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 28 07:27 /dev/kvm
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
INFO         | The active JSON Web Key Sets can be found here: /run/user/1001/avd/running/2770/jwks/7d0ae3cc-beed-428a-a16e-9468458396cb/active.jwk
INFO         | Scanning /run/user/1001/avd/running/2770/jwks/7d0ae3cc-beed-428a-a16e-9468458396cb for jwk keys.
INFO         | Started GRPC server at 127.0.0.1:8554, security: Local, auth: +token
INFO         | Advertising in: /run/user/1001/avd/running/pid_2770.ini
INFO         | Setting display: 0 configuration to: 1080x1920, dpi: 420x420 
INFO         | setDisplayActiveConfig: id:0, 1080x1920
INFO         | emulatorSetupEnvironment: Environment scene is not required
USER_INFO    | Emulator is performing a full startup. This may take upto two minutes, or more.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
INFO         | Boot completed in 26386 ms
INFO         | Increasing screen off timeout, logcat buffer size to 2M.
```
## Logcat (filtered)
```
10-28 07:33:11.363   505   549 W ActivityManager: Slow operation: 315ms so far, now at startProcess: starting to update pids map
10-28 07:33:11.369   505   549 W ActivityManager: Slow operation: 321ms so far, now at startProcess: done updating pids map
10-28 07:33:11.565   505  1674 I ActivityManager: Killing 1262:com.android.cellbroadcastreceiver.module/u0a125 (adj 985): empty #17
10-28 07:33:14.392   431  2154 I iorapd  : Perfetto TraceBuffer saved to file: /data/misc/iorapd/com.backrooms.gdx.debug/1/com.backrooms.gdx.android.AndroidLauncher/raw_traces/1793172794392605073.perfetto_trace.pb
10-28 07:33:23.541   505   549 I ActivityManager: Start proc 2376:com.android.cellbroadcastreceiver.module/u0a125 for broadcast {com.android.cellbroadcastreceiver.module/com.android.cellbroadcastreceiver.CellBroadcastReceiver}
10-28 07:33:23.654   505  1738 I ActivityManager: Killing 1945:com.android.calendar/u0a110 (adj 985): empty #17
10-28 07:33:26.563   505   539 I ActivityManager: Killing 1282:com.android.cellbroadcastreceiver/u0a66 (adj 985): empty #17
10-28 07:33:26.565   505  1736 I ActivityManager: Killing 1967:com.android.localtransport/1000 (adj 975): empty #17
10-28 07:33:32.357  2404  2404 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 07:33:32.367  2404  2404 I AndroidRuntime: Using default boot image
10-28 07:33:32.367  2404  2404 I AndroidRuntime: Leaving lock profiling enabled
10-28 07:33:32.558  2404  2404 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 07:33:32.573  2404  2404 D AndroidRuntime: Shutting down VM
10-28 07:33:33.103  2160  2232 I BackroomsGL: world shader compiled
10-28 07:33:33.155  2160  2232 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 07:33:33.155  2160  2232 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 07:33:35.264  2160  2232 I Perf    : fps=3.6 worst=38 drawn=5/7 queue=2 build=224ms verts=12k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=5 noMesh=0 culled=0
10-28 07:33:37.422  2160  2232 I Perf    : fps=5.1 worst=5 drawn=14/24 queue=2 build=168ms verts=35k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=10
10-28 07:33:39.455  2160  2232 I Perf    : fps=4.9 worst=5 drawn=18/37 queue=0 build=19ms verts=45k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 07:33:41.612  2160  2232 I Perf    : fps=4.7 worst=5 drawn=18/37 queue=0 build=19ms verts=45k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 07:33:43.784  2160  2232 I Perf    : fps=5.0 worst=5 drawn=18/37 queue=0 build=19ms verts=45k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 07:33:45.800  2160  2232 I Perf    : fps=5.0 worst=5 drawn=18/37 queue=0 build=19ms verts=45k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 07:33:47.994  2160  2232 I Perf    : fps=5.0 worst=5 drawn=18/37 queue=0 build=19ms verts=45k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 07:33:50.013  2160  2232 I Perf    : fps=5.0 worst=5 drawn=18/37 queue=0 build=19ms verts=45k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 07:33:52.031  2160  2232 I Perf    : fps=5.0 worst=5 drawn=18/37 queue=0 build=19ms verts=45k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 07:33:54.048  2160  2232 I Perf    : fps=5.0 worst=6 drawn=18/37 queue=0 build=19ms verts=45k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 07:33:56.116  2160  2232 I Perf    : fps=4.8 worst=5 drawn=18/37 queue=0 build=19ms verts=45k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 07:33:58.316  2160  2232 I Perf    : fps=5.0 worst=6 drawn=18/37 queue=0 build=19ms verts=45k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 07:34:00.399  2160  2232 I Perf    : fps=4.8 worst=5 drawn=18/37 queue=0 build=19ms verts=45k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 07:34:02.523  2160  2232 I Perf    : fps=4.7 worst=5 drawn=18/37 queue=0 build=19ms verts=45k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 07:34:03.622  2452  2452 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 07:34:03.631  2452  2452 I AndroidRuntime: Using default boot image
10-28 07:34:03.631  2452  2452 I AndroidRuntime: Leaving lock profiling enabled
10-28 07:34:03.844  2452  2452 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 07:34:04.354  2452  2452 D AndroidRuntime: Shutting down VM
10-28 07:34:04.687  2160  2232 I Perf    : fps=4.6 worst=5 drawn=13/37 queue=0 build=19ms verts=33k cam=1.5,1.6,-0.5 yaw=2.82 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 07:34:04.691   505   505 I ActivityManager: Killing 1989:com.android.camera2/u0a111 (adj 985): empty #17
10-28 07:34:04.691   505   505 I ActivityManager: Killing 1600:android.process.acore/u0a63 (adj 985): empty #18
10-28 07:34:06.695  2160  2232 I Perf    : fps=4.5 worst=4 drawn=13/37 queue=0 build=19ms verts=33k cam=1.5,1.6,-0.5 yaw=2.82 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 07:34:08.923  2160  2232 I Perf    : fps=4.5 worst=5 drawn=13/37 queue=0 build=19ms verts=33k cam=1.5,1.6,-0.5 yaw=2.82 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 07:34:09.266  2480  2480 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 07:34:09.277  2480  2480 I AndroidRuntime: Using default boot image
10-28 07:34:09.277  2480  2480 I AndroidRuntime: Leaving lock profiling enabled
10-28 07:34:09.339  2480  2480 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 07:34:09.358  2480  2480 D AndroidRuntime: Shutting down VM
10-28 07:34:11.126  2160  2232 I Perf    : fps=4.5 worst=5 drawn=13/37 queue=0 build=19ms verts=33k cam=1.5,1.6,-0.5 yaw=2.82 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 07:34:12.601   505   549 I ActivityManager: Start proc 2494:android.process.acore/u0a63 for content provider {com.android.providers.contacts/com.android.providers.contacts.VoicemailContentProvider}
10-28 07:34:12.661  2494  2494 I ContactsPerf: VoicemailContentProvider.onCreate start
10-28 07:34:12.665  2494  2494 I ContactsPerf: VoicemailContentProvider.onCreate finish
10-28 07:34:13.131  2160  2232 I Perf    : fps=4.5 worst=5 drawn=13/37 queue=0 build=19ms verts=33k cam=1.5,1.6,-0.5 yaw=2.82 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 07:34:13.950  2524  2524 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 07:34:13.960  2524  2524 I AndroidRuntime: Using default boot image
10-28 07:34:13.960  2524  2524 I AndroidRuntime: Leaving lock profiling enabled
10-28 07:34:14.052  2524  2524 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 07:34:15.256  2524  2524 D AndroidRuntime: Shutting down VM
10-28 07:34:15.315  2160  2232 I Perf    : fps=4.6 worst=5 drawn=14/41 queue=2 build=19ms verts=36k cam=1.2,1.6,0.4 yaw=2.82 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=25
10-28 07:34:17.495  2160  2232 I Perf    : fps=5.0 worst=5 drawn=17/44 queue=0 build=19ms verts=44k cam=1.1,1.6,0.6 yaw=2.82 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=27
10-28 07:34:19.714  2160  2232 I Perf    : fps=5.0 worst=5 drawn=17/44 queue=0 build=19ms verts=44k cam=1.1,1.6,0.6 yaw=2.82 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=27
10-28 07:34:21.784  2160  2232 I Perf    : fps=4.8 worst=5 drawn=17/44 queue=0 build=19ms verts=44k cam=1.1,1.6,0.6 yaw=2.82 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=27
10-28 07:34:23.864  2160  2232 I Perf    : fps=4.8 worst=5 drawn=17/44 queue=0 build=19ms verts=44k cam=1.1,1.6,0.6 yaw=2.82 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=27
```
## Step log
```
I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 07:33:35.264  2160  2232 I Perf    : fps=3.6 worst=38 drawn=5/7 queue=2 build=224ms verts=12k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=5 noMesh=0 culled=0
10-28 07:33:37.422  2160  2232 I Perf    : fps=5.1 worst=5 drawn=14/24 queue=2 build=168ms verts=35k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=10
10-28 07:33:39.455  2160  2232 I Perf    : fps=4.9 worst=5 drawn=18/37 queue=0 build=19ms verts=45k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 07:33:41.612  2160  2232 I Perf    : fps=4.7 worst=5 drawn=18/37 queue=0 build=19ms verts=45k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 07:33:43.784  2160  2232 I Perf    : fps=5.0 worst=5 drawn=18/37 queue=0 build=19ms verts=45k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 07:33:45.800  2160  2232 I Perf    : fps=5.0 worst=5 drawn=18/37 queue=0 build=19ms verts=45k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 07:33:47.994  2160  2232 I Perf    : fps=5.0 worst=5 drawn=18/37 queue=0 build=19ms verts=45k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 07:33:50.013  2160  2232 I Perf    : fps=5.0 worst=5 drawn=18/37 queue=0 build=19ms verts=45k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 07:33:52.031  2160  2232 I Perf    : fps=5.0 worst=5 drawn=18/37 queue=0 build=19ms verts=45k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 07:33:54.048  2160  2232 I Perf    : fps=5.0 worst=6 drawn=18/37 queue=0 build=19ms verts=45k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 07:33:56.116  2160  2232 I Perf    : fps=4.8 worst=5 drawn=18/37 queue=0 build=19ms verts=45k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 07:33:58.316  2160  2232 I Perf    : fps=5.0 worst=6 drawn=18/37 queue=0 build=19ms verts=45k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 07:33:58.749  2431  2431 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 07:34:00.399  2160  2232 I Perf    : fps=4.8 worst=5 drawn=18/37 queue=0 build=19ms verts=45k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 07:34:02.523  2160  2232 I Perf    : fps=4.7 worst=5 drawn=18/37 queue=0 build=19ms verts=45k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 07:34:03.622  2452  2452 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 07:34:03.631  2452  2452 I AndroidRuntime: Using default boot image
10-28 07:34:03.631  2452  2452 I AndroidRuntime: Leaving lock profiling enabled
10-28 07:34:03.844  2452  2452 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 07:34:04.354  2452  2452 D AndroidRuntime: Shutting down VM
10-28 07:34:04.687  2160  2232 I Perf    : fps=4.6 worst=5 drawn=13/37 queue=0 build=19ms verts=33k cam=1.5,1.6,-0.5 yaw=2.82 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 07:34:06.695  2160  2232 I Perf    : fps=4.5 worst=4 drawn=13/37 queue=0 build=19ms verts=33k cam=1.5,1.6,-0.5 yaw=2.82 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 07:34:08.923  2160  2232 I Perf    : fps=4.5 worst=5 drawn=13/37 queue=0 build=19ms verts=33k cam=1.5,1.6,-0.5 yaw=2.82 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 07:34:09.266  2480  2480 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 07:34:09.277  2480  2480 I AndroidRuntime: Using default boot image
10-28 07:34:09.277  2480  2480 I AndroidRuntime: Leaving lock profiling enabled
10-28 07:34:09.339  2480  2480 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 07:34:09.358  2480  2480 D AndroidRuntime: Shutting down VM
10-28 07:34:11.126  2160  2232 I Perf    : fps=4.5 worst=5 drawn=13/37 queue=0 build=19ms verts=33k cam=1.5,1.6,-0.5 yaw=2.82 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 07:34:12.661  2494  2494 I ContactsPerf: VoicemailContentProvider.onCreate start
10-28 07:34:12.665  2494  2494 I ContactsPerf: VoicemailContentProvider.onCreate finish
10-28 07:34:13.131  2160  2232 I Perf    : fps=4.5 worst=5 drawn=13/37 queue=0 build=19ms verts=33k cam=1.5,1.6,-0.5 yaw=2.82 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 07:34:13.950  2524  2524 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 07:34:13.960  2524  2524 I AndroidRuntime: Using default boot image
10-28 07:34:13.960  2524  2524 I AndroidRuntime: Leaving lock profiling enabled
10-28 07:34:14.052  2524  2524 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 07:34:15.256  2524  2524 D AndroidRuntime: Shutting down VM
10-28 07:34:15.315  2160  2232 I Perf    : fps=4.6 worst=5 drawn=14/41 queue=2 build=19ms verts=36k cam=1.2,1.6,0.4 yaw=2.82 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=25
10-28 07:34:17.495  2160  2232 I Perf    : fps=5.0 worst=5 drawn=17/44 queue=0 build=19ms verts=44k cam=1.1,1.6,0.6 yaw=2.82 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=27
10-28 07:34:19.714  2160  2232 I Perf    : fps=5.0 worst=5 drawn=17/44 queue=0 build=19ms verts=44k cam=1.1,1.6,0.6 yaw=2.82 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=27
10-28 07:34:20.551  2542  2542 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 07:34:21.784  2160  2232 I Perf    : fps=4.8 worst=5 drawn=17/44 queue=0 build=19ms verts=44k cam=1.1,1.6,0.6 yaw=2.82 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=27
10-28 07:34:23.864  2160  2232 I Perf    : fps=4.8 worst=5 drawn=17/44 queue=0 build=19ms verts=44k cam=1.1,1.6,0.6 yaw=2.82 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=27
+ '[' -z 2160 ']'
+ grep -qa 'FATAL EXCEPTION' emulator-shots/logcat.txt
+ '[' 0 '!=' 0 ']'
+ echo 'OK: the app is alive, rendering, and the touch controls work'
OK: the app is alive, rendering, and the touch controls work
```
