# Emulator smoke test diagnostics

Commit: 85052ec7722a310d7fd4f3e3094112eaf118887b
Outcome: success

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 28 20:52 /dev/kvm
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
INFO         | The active JSON Web Key Sets can be found here: /run/user/1001/avd/running/2977/jwks/be822526-c260-4a15-a3e9-881c2ab4ba3e/active.jwk
INFO         | Scanning /run/user/1001/avd/running/2977/jwks/be822526-c260-4a15-a3e9-881c2ab4ba3e for jwk keys.
INFO         | Started GRPC server at 127.0.0.1:8554, security: Local, auth: +token
INFO         | Advertising in: /run/user/1001/avd/running/pid_2977.ini
INFO         | Setting display: 0 configuration to: 1080x1920, dpi: 420x420 
INFO         | setDisplayActiveConfig: id:0, 1080x1920
INFO         | emulatorSetupEnvironment: Environment scene is not required
USER_INFO    | Emulator is performing a full startup. This may take upto two minutes, or more.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
INFO         | Boot completed in 26916 ms
INFO         | Increasing screen off timeout, logcat buffer size to 2M.
```
## Logcat (filtered)
```
10-28 20:56:17.988   507   547 W ActivityManager: Slow operation: 60ms so far, now at startProcess: starting to update pids map
10-28 20:56:17.988   507   547 W ActivityManager: Slow operation: 60ms so far, now at startProcess: done updating pids map
10-28 20:56:18.134   507   534 I ActivityManager: Finished processing BOOT_COMPLETED for u0
10-28 20:56:18.135   507   534 I ActivityManager: Killing 1786:com.android.provision/u0a116 (adj 985): empty #17
10-28 20:56:19.732   433  2089 I iorapd  : Perfetto TraceBuffer saved to file: /data/misc/iorapd/com.backrooms.gdx.debug/1/com.backrooms.gdx.android.AndroidLauncher/raw_traces/1793220979732194730.perfetto_trace.pb
10-28 20:56:32.742   507   545 I ActivityManager: Killing 1288:com.android.cellbroadcastreceiver/u0a66 (adj 985): empty #17
10-28 20:56:38.083  2421  2421 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 20:56:38.095  2421  2421 I AndroidRuntime: Using default boot image
10-28 20:56:38.095  2421  2421 I AndroidRuntime: Leaving lock profiling enabled
10-28 20:56:38.208  2421  2421 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 20:56:38.215  2421  2421 D AndroidRuntime: Shutting down VM
10-28 20:56:38.834  2083  2138 I BackroomsGL: world shader compiled
10-28 20:56:39.314  2083  2138 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 20:56:39.314  2083  2138 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 20:56:39.626   507   537 I ActivityManager: Killing 2108:com.android.calendar/u0a108 (adj 985): empty #17
10-28 20:56:39.626   507   537 I ActivityManager: Killing 1992:com.android.quicksearchbox/u0a101 (adj 985): empty #18
10-28 20:56:39.761   507   805 I ActivityManager: Killing 1597:android.process.acore/u0a64 (adj 975): empty #17
10-28 20:56:41.792  2083  2138 I Perf    : fps=1.0 worst=1 drawn=2/4 queue=2 build=144ms verts=5k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=2 noMesh=0 culled=0
10-28 20:56:43.969  2083  2138 I Perf    : fps=4.6 worst=4 drawn=14/23 queue=2 build=31ms verts=35k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=7
10-28 20:56:46.016  2083  2138 I Perf    : fps=4.4 worst=4 drawn=18/37 queue=0 build=43ms verts=45k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=18
10-28 20:56:48.192  2083  2138 I Perf    : fps=4.6 worst=5 drawn=18/37 queue=0 build=43ms verts=45k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 20:56:50.378  2083  2138 I Perf    : fps=4.6 worst=5 drawn=18/37 queue=0 build=43ms verts=45k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 20:56:52.588  2083  2138 I Perf    : fps=4.5 worst=4 drawn=18/37 queue=0 build=43ms verts=45k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 20:56:54.796  2083  2138 I Perf    : fps=4.5 worst=5 drawn=18/37 queue=0 build=43ms verts=45k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 20:56:56.810  2083  2138 I Perf    : fps=4.5 worst=4 drawn=18/37 queue=0 build=43ms verts=45k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 20:56:58.994  2083  2138 I Perf    : fps=4.6 worst=5 drawn=18/37 queue=0 build=43ms verts=45k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 20:57:01.192  2083  2138 I Perf    : fps=4.6 worst=4 drawn=18/37 queue=0 build=43ms verts=45k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 20:57:03.208  2083  2138 I Perf    : fps=4.5 worst=4 drawn=18/37 queue=0 build=43ms verts=45k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 20:57:05.426  2083  2138 I Perf    : fps=4.5 worst=4 drawn=18/37 queue=0 build=43ms verts=45k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 20:57:07.515  2083  2138 I Perf    : fps=4.3 worst=4 drawn=18/37 queue=0 build=43ms verts=45k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 20:57:09.632  2083  2138 I Perf    : fps=4.3 worst=4 drawn=18/37 queue=0 build=43ms verts=45k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 20:57:10.934  2472  2472 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 20:57:10.936  2472  2472 I AndroidRuntime: Using default boot image
10-28 20:57:10.936  2472  2472 I AndroidRuntime: Leaving lock profiling enabled
10-28 20:57:11.131  2472  2472 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 20:57:11.723  2083  2138 I Perf    : fps=4.3 worst=4 drawn=16/37 queue=0 build=43ms verts=41k cam=-1.5,1.6,-1.5 yaw=2.69 pitch=0.00 torch=1 ready=true drawn=16 noMesh=0 culled=21
10-28 20:57:11.744  2472  2472 D AndroidRuntime: Shutting down VM
10-28 20:57:13.732  2083  2138 I Perf    : fps=5.5 worst=6 drawn=14/37 queue=0 build=43ms verts=35k cam=-1.5,1.6,-1.5 yaw=2.84 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:57:15.743  2083  2138 I Perf    : fps=5.5 worst=5 drawn=14/37 queue=0 build=43ms verts=35k cam=-1.5,1.6,-1.5 yaw=2.84 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:57:16.532  2488  2488 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 20:57:16.549  2488  2488 I AndroidRuntime: Using default boot image
10-28 20:57:16.549  2488  2488 I AndroidRuntime: Leaving lock profiling enabled
10-28 20:57:16.687  2488  2488 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 20:57:16.736  2488  2488 D AndroidRuntime: Shutting down VM
10-28 20:57:17.830  2083  2138 I Perf    : fps=5.3 worst=5 drawn=14/37 queue=0 build=43ms verts=35k cam=-1.5,1.6,-1.5 yaw=2.84 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-28 20:57:18.615   507   547 I ActivityManager: Start proc 2502:android.process.acore/u0a64 for content provider {com.android.providers.contacts/com.android.providers.contacts.VoicemailContentProvider}
10-28 20:57:18.778  2502  2502 I ContactsPerf: VoicemailContentProvider.onCreate start
10-28 20:57:18.780  2502  2502 I ContactsPerf: VoicemailContentProvider.onCreate finish
10-28 20:57:19.914  2083  2138 I Perf    : fps=5.3 worst=5 drawn=14/37 queue=0 build=43ms verts=35k cam=-1.5,1.6,-1.5 yaw=2.84 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-28 20:57:21.610  2533  2533 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 20:57:21.627  2533  2533 I AndroidRuntime: Using default boot image
10-28 20:57:21.627  2533  2533 I AndroidRuntime: Leaving lock profiling enabled
10-28 20:57:21.713  2533  2533 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 20:57:21.923  2083  2138 I Perf    : fps=5.5 worst=6 drawn=14/37 queue=0 build=43ms verts=35k cam=-1.5,1.6,-1.5 yaw=2.84 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-28 20:57:23.237  2533  2533 D AndroidRuntime: Shutting down VM
10-28 20:57:24.017  2083  2138 I Perf    : fps=5.7 worst=6 drawn=14/37 queue=0 build=43ms verts=35k cam=-2.0,1.6,-0.4 yaw=2.84 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-28 20:57:26.073  2083  2138 I Perf    : fps=5.8 worst=6 drawn=14/37 queue=0 build=43ms verts=35k cam=-2.0,1.6,-0.4 yaw=2.84 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-28 20:57:28.258  2083  2138 I Perf    : fps=5.5 worst=6 drawn=14/37 queue=0 build=43ms verts=35k cam=-2.0,1.6,-0.4 yaw=2.84 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-28 20:57:30.373  2083  2138 I Perf    : fps=5.6 worst=5 drawn=14/37 queue=0 build=43ms verts=35k cam=-2.0,1.6,-0.4 yaw=2.84 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-28 20:57:32.505  2083  2138 I Perf    : fps=5.6 worst=6 drawn=14/37 queue=0 build=43ms verts=35k cam=-2.0,1.6,-0.4 yaw=2.84 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
```
## Step log
```
=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=2 noMesh=0 culled=0
10-28 20:56:43.969  2083  2138 I Perf    : fps=4.6 worst=4 drawn=14/23 queue=2 build=31ms verts=35k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=7
10-28 20:56:46.016  2083  2138 I Perf    : fps=4.4 worst=4 drawn=18/37 queue=0 build=43ms verts=45k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=18
10-28 20:56:48.192  2083  2138 I Perf    : fps=4.6 worst=5 drawn=18/37 queue=0 build=43ms verts=45k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 20:56:50.378  2083  2138 I Perf    : fps=4.6 worst=5 drawn=18/37 queue=0 build=43ms verts=45k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 20:56:52.588  2083  2138 I Perf    : fps=4.5 worst=4 drawn=18/37 queue=0 build=43ms verts=45k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 20:56:54.796  2083  2138 I Perf    : fps=4.5 worst=5 drawn=18/37 queue=0 build=43ms verts=45k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 20:56:56.810  2083  2138 I Perf    : fps=4.5 worst=4 drawn=18/37 queue=0 build=43ms verts=45k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 20:56:58.994  2083  2138 I Perf    : fps=4.6 worst=5 drawn=18/37 queue=0 build=43ms verts=45k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 20:57:01.192  2083  2138 I Perf    : fps=4.6 worst=4 drawn=18/37 queue=0 build=43ms verts=45k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 20:57:03.208  2083  2138 I Perf    : fps=4.5 worst=4 drawn=18/37 queue=0 build=43ms verts=45k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 20:57:03.863  2448  2448 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 20:57:05.426  2083  2138 I Perf    : fps=4.5 worst=4 drawn=18/37 queue=0 build=43ms verts=45k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 20:57:07.515  2083  2138 I Perf    : fps=4.3 worst=4 drawn=18/37 queue=0 build=43ms verts=45k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 20:57:09.632  2083  2138 I Perf    : fps=4.3 worst=4 drawn=18/37 queue=0 build=43ms verts=45k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 20:57:10.934  2472  2472 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 20:57:10.936  2472  2472 I AndroidRuntime: Using default boot image
10-28 20:57:10.936  2472  2472 I AndroidRuntime: Leaving lock profiling enabled
10-28 20:57:11.131  2472  2472 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 20:57:11.723  2083  2138 I Perf    : fps=4.3 worst=4 drawn=16/37 queue=0 build=43ms verts=41k cam=-1.5,1.6,-1.5 yaw=2.69 pitch=0.00 torch=1 ready=true drawn=16 noMesh=0 culled=21
10-28 20:57:11.744  2472  2472 D AndroidRuntime: Shutting down VM
10-28 20:57:13.732  2083  2138 I Perf    : fps=5.5 worst=6 drawn=14/37 queue=0 build=43ms verts=35k cam=-1.5,1.6,-1.5 yaw=2.84 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:57:15.743  2083  2138 I Perf    : fps=5.5 worst=5 drawn=14/37 queue=0 build=43ms verts=35k cam=-1.5,1.6,-1.5 yaw=2.84 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:57:16.532  2488  2488 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 20:57:16.549  2488  2488 I AndroidRuntime: Using default boot image
10-28 20:57:16.549  2488  2488 I AndroidRuntime: Leaving lock profiling enabled
10-28 20:57:16.687  2488  2488 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 20:57:16.736  2488  2488 D AndroidRuntime: Shutting down VM
10-28 20:57:17.830  2083  2138 I Perf    : fps=5.3 worst=5 drawn=14/37 queue=0 build=43ms verts=35k cam=-1.5,1.6,-1.5 yaw=2.84 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-28 20:57:18.778  2502  2502 I ContactsPerf: VoicemailContentProvider.onCreate start
10-28 20:57:18.780  2502  2502 I ContactsPerf: VoicemailContentProvider.onCreate finish
10-28 20:57:19.914  2083  2138 I Perf    : fps=5.3 worst=5 drawn=14/37 queue=0 build=43ms verts=35k cam=-1.5,1.6,-1.5 yaw=2.84 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-28 20:57:21.610  2533  2533 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 20:57:21.627  2533  2533 I AndroidRuntime: Using default boot image
10-28 20:57:21.627  2533  2533 I AndroidRuntime: Leaving lock profiling enabled
10-28 20:57:21.713  2533  2533 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 20:57:21.923  2083  2138 I Perf    : fps=5.5 worst=6 drawn=14/37 queue=0 build=43ms verts=35k cam=-1.5,1.6,-1.5 yaw=2.84 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-28 20:57:23.237  2533  2533 D AndroidRuntime: Shutting down VM
10-28 20:57:24.017  2083  2138 I Perf    : fps=5.7 worst=6 drawn=14/37 queue=0 build=43ms verts=35k cam=-2.0,1.6,-0.4 yaw=2.84 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-28 20:57:26.073  2083  2138 I Perf    : fps=5.8 worst=6 drawn=14/37 queue=0 build=43ms verts=35k cam=-2.0,1.6,-0.4 yaw=2.84 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-28 20:57:28.257  2551  2551 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 20:57:28.258  2083  2138 I Perf    : fps=5.5 worst=6 drawn=14/37 queue=0 build=43ms verts=35k cam=-2.0,1.6,-0.4 yaw=2.84 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-28 20:57:30.373  2083  2138 I Perf    : fps=5.6 worst=5 drawn=14/37 queue=0 build=43ms verts=35k cam=-2.0,1.6,-0.4 yaw=2.84 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-28 20:57:32.505  2083  2138 I Perf    : fps=5.6 worst=6 drawn=14/37 queue=0 build=43ms verts=35k cam=-2.0,1.6,-0.4 yaw=2.84 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
+ '[' -z 2083 ']'
+ grep -qa 'FATAL EXCEPTION' emulator-shots/logcat.txt
+ '[' 0 '!=' 0 ']'
+ echo 'OK: the app is alive, rendering, and the touch controls work'
OK: the app is alive, rendering, and the touch controls work
```
