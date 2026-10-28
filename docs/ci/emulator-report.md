# Emulator smoke test diagnostics

Commit: 16e3dce135d0a51f41aaab00e8b850f4dfbab684
Outcome: success

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 28 19:54 /dev/kvm
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
INFO         | The active JSON Web Key Sets can be found here: /run/user/1001/avd/running/3035/jwks/b245d588-12e2-4204-b328-9cdb8047b3d4/active.jwk
INFO         | Scanning /run/user/1001/avd/running/3035/jwks/b245d588-12e2-4204-b328-9cdb8047b3d4 for jwk keys.
INFO         | Started GRPC server at 127.0.0.1:8554, security: Local, auth: +token
INFO         | Advertising in: /run/user/1001/avd/running/pid_3035.ini
INFO         | Setting display: 0 configuration to: 1080x1920, dpi: 420x420 
INFO         | setDisplayActiveConfig: id:0, 1080x1920
INFO         | emulatorSetupEnvironment: Environment scene is not required
USER_INFO    | Emulator is performing a full startup. This may take upto two minutes, or more.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
INFO         | Boot completed in 26699 ms
INFO         | Increasing screen off timeout, logcat buffer size to 2M.
```
## Logcat (filtered)
```
10-28 20:00:20.182   505  2155 I ActivityManager: Killing 1610:com.android.keychain/1000 (adj 985): empty #17
10-28 20:00:20.229   505   546 I ActivityManager: Start proc 2344:com.android.traceur/u0a95 for broadcast {com.android.traceur/com.android.traceur.Receiver}
10-28 20:00:20.308   505  2155 I ActivityManager: Finished processing BOOT_COMPLETED for u0
10-28 20:00:20.308   505  2155 I ActivityManager: Killing 1928:com.android.localtransport/1000 (adj 985): empty #17
10-28 20:00:20.351   419  2013 I iorapd  : Perfetto TraceBuffer saved to file: /data/misc/iorapd/com.backrooms.gdx.debug/1/com.backrooms.gdx.android.AndroidLauncher/raw_traces/1793217620350689959.perfetto_trace.pb
10-28 20:00:21.263   505  2155 I ActivityManager: Killing 1902:com.android.printspooler/u0a88 (adj 985): empty #17
10-28 20:00:34.939   505   544 I ActivityManager: Killing 1286:com.android.cellbroadcastreceiver/u0a64 (adj 985): empty #17
10-28 20:00:39.052  2391  2391 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 20:00:39.053  2391  2391 I AndroidRuntime: Using default boot image
10-28 20:00:39.053  2391  2391 I AndroidRuntime: Leaving lock profiling enabled
10-28 20:00:39.234  2391  2391 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 20:00:39.324  2391  2391 D AndroidRuntime: Shutting down VM
10-28 20:00:39.497   505  2155 I ActivityManager: Killing 2080:com.android.calendar/u0a109 (adj 985): empty #17
10-28 20:00:39.497   505  2155 I ActivityManager: Killing 1949:com.android.quicksearchbox/u0a101 (adj 985): empty #18
10-28 20:00:39.831  2007  2064 I BackroomsGL: world shader compiled
10-28 20:00:39.905  2007  2064 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 20:00:39.905  2007  2064 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 20:00:41.922  2007  2064 I Perf    : fps=1.4 worst=5 drawn=2/4 queue=1 build=19ms verts=4k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=2 noMesh=0 culled=0
10-28 20:00:42.976   505  1737 I ActivityManager: Killing 2104:com.android.camera2/u0a107 (adj 975): empty #17
10-28 20:00:43.910  2007  2064 I Perf    : fps=4.0 worst=4 drawn=14/20 queue=2 build=159ms verts=34k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=4
10-28 20:00:46.008  2007  2064 I Perf    : fps=3.8 worst=4 drawn=18/33 queue=2 build=27ms verts=44k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=13
10-28 20:00:48.140  2007  2064 I Perf    : fps=3.7 worst=4 drawn=18/37 queue=0 build=33ms verts=44k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 20:00:50.398  2007  2064 I Perf    : fps=4.0 worst=4 drawn=18/37 queue=0 build=33ms verts=44k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 20:00:52.473  2007  2064 I Perf    : fps=3.9 worst=4 drawn=18/37 queue=0 build=33ms verts=44k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 20:00:54.519  2007  2064 I Perf    : fps=3.9 worst=4 drawn=18/37 queue=0 build=33ms verts=44k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 20:00:56.583  2007  2064 I Perf    : fps=3.9 worst=4 drawn=18/37 queue=0 build=33ms verts=44k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 20:00:58.716  2007  2064 I Perf    : fps=3.8 worst=4 drawn=18/37 queue=0 build=33ms verts=44k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 20:01:00.726  2007  2064 I Perf    : fps=4.0 worst=4 drawn=18/37 queue=0 build=33ms verts=44k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 20:01:02.803  2007  2064 I Perf    : fps=3.9 worst=4 drawn=18/37 queue=0 build=33ms verts=44k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 20:01:04.887  2007  2064 I Perf    : fps=3.8 worst=4 drawn=18/37 queue=0 build=33ms verts=44k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 20:01:06.983  2007  2064 I Perf    : fps=3.8 worst=4 drawn=18/37 queue=0 build=33ms verts=44k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 20:01:09.126  2007  2064 I Perf    : fps=3.7 worst=3 drawn=18/37 queue=0 build=33ms verts=44k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 20:01:11.202  2427  2427 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 20:01:11.230  2427  2427 I AndroidRuntime: Using default boot image
10-28 20:01:11.230  2427  2427 I AndroidRuntime: Leaving lock profiling enabled
10-28 20:01:11.256  2007  2064 I Perf    : fps=3.8 worst=3 drawn=18/37 queue=0 build=33ms verts=44k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 20:01:11.370  2427  2427 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 20:01:11.982  2427  2427 D AndroidRuntime: Shutting down VM
10-28 20:01:13.344  2007  2064 I Perf    : fps=3.8 worst=4 drawn=13/37 queue=0 build=33ms verts=32k cam=1.5,1.6,-0.5 yaw=2.83 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 20:01:15.484  2007  2064 I Perf    : fps=4.2 worst=4 drawn=13/37 queue=0 build=33ms verts=32k cam=1.5,1.6,-0.5 yaw=2.83 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 20:01:16.779  2455  2455 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 20:01:16.782  2455  2455 I AndroidRuntime: Using default boot image
10-28 20:01:16.782  2455  2455 I AndroidRuntime: Leaving lock profiling enabled
10-28 20:01:16.888  2455  2455 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 20:01:16.897  2455  2455 D AndroidRuntime: Shutting down VM
10-28 20:01:16.948  2455  2466 I AndroidRuntime: NOTE: attach of thread 'Binder:2455_1' failed
10-28 20:01:17.506  2007  2064 I Perf    : fps=4.0 worst=4 drawn=13/37 queue=0 build=33ms verts=32k cam=1.5,1.6,-0.5 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 20:01:19.649  2007  2064 I Perf    : fps=4.2 worst=4 drawn=13/37 queue=0 build=33ms verts=32k cam=1.5,1.6,-0.5 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 20:01:21.701  2007  2064 I Perf    : fps=3.9 worst=4 drawn=13/37 queue=0 build=33ms verts=32k cam=1.5,1.6,-0.5 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 20:01:21.875  2471  2471 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 20:01:21.887  2471  2471 I AndroidRuntime: Using default boot image
10-28 20:01:21.887  2471  2471 I AndroidRuntime: Leaving lock profiling enabled
10-28 20:01:22.011  2471  2471 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 20:01:23.517  2471  2471 D AndroidRuntime: Shutting down VM
10-28 20:01:23.717  2007  2064 I Perf    : fps=4.0 worst=4 drawn=13/41 queue=2 build=24ms verts=31k cam=1.2,1.6,0.5 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=26
10-28 20:01:25.729  2007  2064 I Perf    : fps=4.0 worst=4 drawn=17/44 queue=0 build=16ms verts=42k cam=1.1,1.6,0.7 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=27
10-28 20:01:27.760  2007  2064 I Perf    : fps=3.9 worst=4 drawn=17/44 queue=0 build=16ms verts=42k cam=1.1,1.6,0.7 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=27
10-28 20:01:29.791  2007  2064 I Perf    : fps=3.9 worst=4 drawn=17/44 queue=0 build=16ms verts=42k cam=1.1,1.6,0.7 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=27
10-28 20:01:31.987  2007  2064 I Perf    : fps=4.1 worst=4 drawn=17/44 queue=0 build=16ms verts=42k cam=1.1,1.6,0.7 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=27
10-28 20:01:33.993  2007  2064 I Perf    : fps=4.0 worst=4 drawn=17/44 queue=0 build=16ms verts=42k cam=1.1,1.6,0.7 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=27
```
## Step log
```
ed=0
10-28 20:00:43.910  2007  2064 I Perf    : fps=4.0 worst=4 drawn=14/20 queue=2 build=159ms verts=34k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=4
10-28 20:00:46.008  2007  2064 I Perf    : fps=3.8 worst=4 drawn=18/33 queue=2 build=27ms verts=44k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=13
10-28 20:00:48.140  2007  2064 I Perf    : fps=3.7 worst=4 drawn=18/37 queue=0 build=33ms verts=44k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 20:00:50.398  2007  2064 I Perf    : fps=4.0 worst=4 drawn=18/37 queue=0 build=33ms verts=44k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 20:00:52.473  2007  2064 I Perf    : fps=3.9 worst=4 drawn=18/37 queue=0 build=33ms verts=44k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 20:00:54.519  2007  2064 I Perf    : fps=3.9 worst=4 drawn=18/37 queue=0 build=33ms verts=44k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 20:00:56.583  2007  2064 I Perf    : fps=3.9 worst=4 drawn=18/37 queue=0 build=33ms verts=44k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 20:00:58.716  2007  2064 I Perf    : fps=3.8 worst=4 drawn=18/37 queue=0 build=33ms verts=44k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 20:01:00.726  2007  2064 I Perf    : fps=4.0 worst=4 drawn=18/37 queue=0 build=33ms verts=44k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 20:01:02.803  2007  2064 I Perf    : fps=3.9 worst=4 drawn=18/37 queue=0 build=33ms verts=44k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 20:01:04.887  2007  2064 I Perf    : fps=3.8 worst=4 drawn=18/37 queue=0 build=33ms verts=44k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 20:01:05.691  2414  2414 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 20:01:06.983  2007  2064 I Perf    : fps=3.8 worst=4 drawn=18/37 queue=0 build=33ms verts=44k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 20:01:09.126  2007  2064 I Perf    : fps=3.7 worst=3 drawn=18/37 queue=0 build=33ms verts=44k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 20:01:11.202  2427  2427 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 20:01:11.230  2427  2427 I AndroidRuntime: Using default boot image
10-28 20:01:11.230  2427  2427 I AndroidRuntime: Leaving lock profiling enabled
10-28 20:01:11.256  2007  2064 I Perf    : fps=3.8 worst=3 drawn=18/37 queue=0 build=33ms verts=44k cam=1.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 20:01:11.370  2427  2427 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 20:01:11.982  2427  2427 D AndroidRuntime: Shutting down VM
10-28 20:01:13.344  2007  2064 I Perf    : fps=3.8 worst=4 drawn=13/37 queue=0 build=33ms verts=32k cam=1.5,1.6,-0.5 yaw=2.83 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 20:01:15.484  2007  2064 I Perf    : fps=4.2 worst=4 drawn=13/37 queue=0 build=33ms verts=32k cam=1.5,1.6,-0.5 yaw=2.83 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 20:01:16.779  2455  2455 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 20:01:16.782  2455  2455 I AndroidRuntime: Using default boot image
10-28 20:01:16.782  2455  2455 I AndroidRuntime: Leaving lock profiling enabled
10-28 20:01:16.888  2455  2455 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 20:01:16.897  2455  2455 D AndroidRuntime: Shutting down VM
10-28 20:01:16.948  2455  2466 I AndroidRuntime: NOTE: attach of thread 'Binder:2455_1' failed
10-28 20:01:17.506  2007  2064 I Perf    : fps=4.0 worst=4 drawn=13/37 queue=0 build=33ms verts=32k cam=1.5,1.6,-0.5 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 20:01:19.649  2007  2064 I Perf    : fps=4.2 worst=4 drawn=13/37 queue=0 build=33ms verts=32k cam=1.5,1.6,-0.5 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 20:01:21.701  2007  2064 I Perf    : fps=3.9 worst=4 drawn=13/37 queue=0 build=33ms verts=32k cam=1.5,1.6,-0.5 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 20:01:21.875  2471  2471 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 20:01:21.887  2471  2471 I AndroidRuntime: Using default boot image
10-28 20:01:21.887  2471  2471 I AndroidRuntime: Leaving lock profiling enabled
10-28 20:01:22.011  2471  2471 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 20:01:23.517  2471  2471 D AndroidRuntime: Shutting down VM
10-28 20:01:23.717  2007  2064 I Perf    : fps=4.0 worst=4 drawn=13/41 queue=2 build=24ms verts=31k cam=1.2,1.6,0.5 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=26
10-28 20:01:25.729  2007  2064 I Perf    : fps=4.0 worst=4 drawn=17/44 queue=0 build=16ms verts=42k cam=1.1,1.6,0.7 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=27
10-28 20:01:27.760  2007  2064 I Perf    : fps=3.9 worst=4 drawn=17/44 queue=0 build=16ms verts=42k cam=1.1,1.6,0.7 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=27
10-28 20:01:29.077  2489  2489 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 20:01:29.791  2007  2064 I Perf    : fps=3.9 worst=4 drawn=17/44 queue=0 build=16ms verts=42k cam=1.1,1.6,0.7 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=27
10-28 20:01:31.987  2007  2064 I Perf    : fps=4.1 worst=4 drawn=17/44 queue=0 build=16ms verts=42k cam=1.1,1.6,0.7 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=27
10-28 20:01:33.993  2007  2064 I Perf    : fps=4.0 worst=4 drawn=17/44 queue=0 build=16ms verts=42k cam=1.1,1.6,0.7 yaw=2.83 pitch=0.00 torch=0 ready=true drawn=17 noMesh=0 culled=27
+ '[' -z 2007 ']'
+ grep -qa 'FATAL EXCEPTION' emulator-shots/logcat.txt
+ '[' 0 '!=' 0 ']'
+ echo 'OK: the app is alive, rendering, and the touch controls work'
OK: the app is alive, rendering, and the touch controls work
```
