# Emulator smoke test diagnostics

Commit: ec8162253dbc886a192aaba4a237f44deffe421b
Outcome: success

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 28 08:11 /dev/kvm
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
INFO         | The active JSON Web Key Sets can be found here: /run/user/1001/avd/running/2742/jwks/378e87e4-9ca0-4489-90fb-a0db0de34d9d/active.jwk
INFO         | Scanning /run/user/1001/avd/running/2742/jwks/378e87e4-9ca0-4489-90fb-a0db0de34d9d for jwk keys.
INFO         | Started GRPC server at 127.0.0.1:8554, security: Local, auth: +token
INFO         | Advertising in: /run/user/1001/avd/running/pid_2742.ini
INFO         | Setting display: 0 configuration to: 1080x1920, dpi: 420x420 
INFO         | setDisplayActiveConfig: id:0, 1080x1920
INFO         | emulatorSetupEnvironment: Environment scene is not required
USER_INFO    | Emulator is performing a full startup. This may take upto two minutes, or more.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
INFO         | Boot completed in 25789 ms
INFO         | Increasing screen off timeout, logcat buffer size to 2M.
```
## Logcat (filtered)
```
10-28 08:14:49.211   503   546 W ActivityManager: Slow operation: 62ms so far, now at startProcess: returned from zygote!
10-28 08:14:49.233   503   546 W ActivityManager: Slow operation: 64ms so far, now at startProcess: done updating battery stats
10-28 08:14:49.233   503   546 W ActivityManager: Slow operation: 84ms so far, now at startProcess: building log message
10-28 08:14:49.233   503   546 I ActivityManager: Start proc 2309:com.android.traceur/u0a88 for broadcast {com.android.traceur/com.android.traceur.Receiver}
10-28 08:14:49.233   503   546 W ActivityManager: Slow operation: 84ms so far, now at startProcess: starting to update pids map
10-28 08:14:49.233   503   546 W ActivityManager: Slow operation: 84ms so far, now at startProcess: done updating pids map
10-28 08:14:49.547   503   637 I ActivityManager: Finished processing BOOT_COMPLETED for u0
10-28 08:14:49.547   503   637 I ActivityManager: Killing 1746:com.android.externalstorage/u0a68 (adj 985): empty #17
10-28 08:15:03.666   503   544 I ActivityManager: Killing 1280:com.android.cellbroadcastreceiver/u0a63 (adj 985): empty #17
10-28 08:15:09.459   503   536 I ActivityManager: Killing 1982:com.android.calendar/u0a109 (adj 985): empty #17
10-28 08:15:09.469   503   536 I ActivityManager: Killing 1885:com.android.quicksearchbox/u0a101 (adj 985): empty #18
10-28 08:15:10.047  2368  2368 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:15:10.063  2368  2368 I AndroidRuntime: Using default boot image
10-28 08:15:10.064  2368  2368 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:15:10.137  2368  2368 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:15:10.173  2368  2368 D AndroidRuntime: Shutting down VM
10-28 08:15:10.741  2164  2218 I BackroomsGL: world shader compiled
10-28 08:15:10.791  2164  2218 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 08:15:10.791  2164  2218 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 08:15:12.785  2164  2218 I Perf    : fps=5.6 worst=64 drawn=7/13 queue=2 build=18ms verts=20k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=7 noMesh=0 culled=4
10-28 08:15:14.507   503   533 I ActivityManager: Killing 2029:com.android.camera2/u0a110 (adj 975): empty #17
10-28 08:15:14.935  2164  2218 I Perf    : fps=4.2 worst=4 drawn=11/27 queue=2 build=34ms verts=31k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=11 noMesh=0 culled=14
10-28 08:15:17.046  2164  2218 I Perf    : fps=4.3 worst=4 drawn=14/37 queue=0 build=199ms verts=38k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:15:19.130  2164  2218 I Perf    : fps=4.3 worst=4 drawn=14/37 queue=0 build=199ms verts=38k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:15:21.231  2164  2218 I Perf    : fps=4.3 worst=4 drawn=14/37 queue=0 build=199ms verts=38k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:15:23.342  2164  2218 I Perf    : fps=4.3 worst=4 drawn=14/37 queue=0 build=199ms verts=38k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:15:25.439  2164  2218 I Perf    : fps=4.3 worst=4 drawn=14/37 queue=0 build=199ms verts=38k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:15:27.642  2164  2218 I Perf    : fps=4.1 worst=4 drawn=14/37 queue=0 build=199ms verts=38k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:15:29.743  2164  2218 I Perf    : fps=4.3 worst=4 drawn=14/37 queue=0 build=199ms verts=38k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:15:31.879  2164  2218 I Perf    : fps=4.2 worst=4 drawn=14/37 queue=0 build=199ms verts=38k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:15:33.978  2164  2218 I Perf    : fps=4.3 worst=4 drawn=14/37 queue=0 build=199ms verts=38k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:15:36.139  2164  2218 I Perf    : fps=4.2 worst=4 drawn=14/37 queue=0 build=199ms verts=38k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:15:38.247  2164  2218 I Perf    : fps=4.3 worst=4 drawn=14/37 queue=0 build=199ms verts=38k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:15:40.429  2164  2218 I Perf    : fps=4.1 worst=4 drawn=14/37 queue=0 build=199ms verts=38k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:15:41.544  2404  2404 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:15:41.548  2404  2404 I AndroidRuntime: Using default boot image
10-28 08:15:41.548  2404  2404 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:15:41.660  2404  2404 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:15:42.362  2404  2404 D AndroidRuntime: Shutting down VM
10-28 08:15:42.577  2164  2218 I Perf    : fps=4.2 worst=5 drawn=15/37 queue=0 build=199ms verts=39k cam=0.5,1.6,0.5 yaw=2.61 pitch=0.00 torch=1 ready=true drawn=15 noMesh=0 culled=22
10-28 08:15:44.722  2164  2218 I Perf    : fps=5.6 worst=6 drawn=15/37 queue=0 build=199ms verts=39k cam=0.5,1.6,0.5 yaw=2.61 pitch=0.00 torch=1 ready=true drawn=15 noMesh=0 culled=22
10-28 08:15:46.836  2164  2218 I Perf    : fps=5.7 worst=6 drawn=15/37 queue=0 build=199ms verts=39k cam=0.5,1.6,0.5 yaw=2.61 pitch=0.00 torch=1 ready=true drawn=15 noMesh=0 culled=22
10-28 08:15:46.983  2431  2431 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:15:47.002  2431  2431 I AndroidRuntime: Using default boot image
10-28 08:15:47.002  2431  2431 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:15:47.157  2431  2431 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:15:47.215  2431  2431 D AndroidRuntime: Shutting down VM
10-28 08:15:48.949  2164  2218 I Perf    : fps=5.7 worst=6 drawn=15/37 queue=0 build=199ms verts=39k cam=0.5,1.6,0.5 yaw=2.61 pitch=0.00 torch=0 ready=true drawn=15 noMesh=0 culled=22
10-28 08:15:51.129  2164  2218 I Perf    : fps=5.5 worst=6 drawn=15/37 queue=0 build=199ms verts=39k cam=0.5,1.6,0.5 yaw=2.61 pitch=0.00 torch=0 ready=true drawn=15 noMesh=0 culled=22
10-28 08:15:51.970  2448  2448 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:15:51.982  2448  2448 I AndroidRuntime: Using default boot image
10-28 08:15:51.982  2448  2448 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:15:52.145  2448  2448 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:15:53.256  2164  2218 I Perf    : fps=5.6 worst=6 drawn=15/37 queue=0 build=199ms verts=39k cam=0.3,1.7,0.8 yaw=2.61 pitch=0.00 torch=0 ready=true drawn=15 noMesh=0 culled=22
10-28 08:15:53.787  2448  2448 D AndroidRuntime: Shutting down VM
10-28 08:15:55.432  2164  2218 I Perf    : fps=5.1 worst=6 drawn=19/44 queue=0 build=103ms verts=49k cam=-0.4,1.6,1.7 yaw=2.61 pitch=0.00 torch=0 ready=true drawn=19 noMesh=0 culled=25
10-28 08:15:57.559  2164  2218 I Perf    : fps=4.7 worst=5 drawn=19/44 queue=0 build=103ms verts=49k cam=-0.4,1.6,1.7 yaw=2.61 pitch=0.00 torch=0 ready=true drawn=19 noMesh=0 culled=25
10-28 08:15:59.648  2164  2218 I Perf    : fps=4.8 worst=5 drawn=19/44 queue=0 build=103ms verts=49k cam=-0.4,1.6,1.7 yaw=2.61 pitch=0.00 torch=0 ready=true drawn=19 noMesh=0 culled=25
10-28 08:16:01.735  2164  2218 I Perf    : fps=4.8 worst=5 drawn=19/44 queue=0 build=103ms verts=49k cam=-0.4,1.6,1.7 yaw=2.61 pitch=0.00 torch=0 ready=true drawn=19 noMesh=0 culled=25
10-28 08:16:03.915  2164  2218 I Perf    : fps=4.6 worst=5 drawn=19/44 queue=0 build=103ms verts=49k cam=-0.4,1.6,1.7 yaw=2.61 pitch=0.00 torch=0 ready=true drawn=19 noMesh=0 culled=25
```
## Step log
```
: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 08:15:12.785  2164  2218 I Perf    : fps=5.6 worst=64 drawn=7/13 queue=2 build=18ms verts=20k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=7 noMesh=0 culled=4
10-28 08:15:14.935  2164  2218 I Perf    : fps=4.2 worst=4 drawn=11/27 queue=2 build=34ms verts=31k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=11 noMesh=0 culled=14
10-28 08:15:17.046  2164  2218 I Perf    : fps=4.3 worst=4 drawn=14/37 queue=0 build=199ms verts=38k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:15:19.130  2164  2218 I Perf    : fps=4.3 worst=4 drawn=14/37 queue=0 build=199ms verts=38k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:15:21.231  2164  2218 I Perf    : fps=4.3 worst=4 drawn=14/37 queue=0 build=199ms verts=38k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:15:23.342  2164  2218 I Perf    : fps=4.3 worst=4 drawn=14/37 queue=0 build=199ms verts=38k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:15:25.439  2164  2218 I Perf    : fps=4.3 worst=4 drawn=14/37 queue=0 build=199ms verts=38k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:15:27.642  2164  2218 I Perf    : fps=4.1 worst=4 drawn=14/37 queue=0 build=199ms verts=38k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:15:29.743  2164  2218 I Perf    : fps=4.3 worst=4 drawn=14/37 queue=0 build=199ms verts=38k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:15:31.879  2164  2218 I Perf    : fps=4.2 worst=4 drawn=14/37 queue=0 build=199ms verts=38k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:15:33.978  2164  2218 I Perf    : fps=4.3 worst=4 drawn=14/37 queue=0 build=199ms verts=38k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:15:35.907  2390  2390 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 08:15:36.139  2164  2218 I Perf    : fps=4.2 worst=4 drawn=14/37 queue=0 build=199ms verts=38k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:15:38.247  2164  2218 I Perf    : fps=4.3 worst=4 drawn=14/37 queue=0 build=199ms verts=38k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:15:40.429  2164  2218 I Perf    : fps=4.1 worst=4 drawn=14/37 queue=0 build=199ms verts=38k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:15:41.544  2404  2404 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:15:41.548  2404  2404 I AndroidRuntime: Using default boot image
10-28 08:15:41.548  2404  2404 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:15:41.660  2404  2404 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:15:42.362  2404  2404 D AndroidRuntime: Shutting down VM
10-28 08:15:42.577  2164  2218 I Perf    : fps=4.2 worst=5 drawn=15/37 queue=0 build=199ms verts=39k cam=0.5,1.6,0.5 yaw=2.61 pitch=0.00 torch=1 ready=true drawn=15 noMesh=0 culled=22
10-28 08:15:44.722  2164  2218 I Perf    : fps=5.6 worst=6 drawn=15/37 queue=0 build=199ms verts=39k cam=0.5,1.6,0.5 yaw=2.61 pitch=0.00 torch=1 ready=true drawn=15 noMesh=0 culled=22
10-28 08:15:46.836  2164  2218 I Perf    : fps=5.7 worst=6 drawn=15/37 queue=0 build=199ms verts=39k cam=0.5,1.6,0.5 yaw=2.61 pitch=0.00 torch=1 ready=true drawn=15 noMesh=0 culled=22
10-28 08:15:46.983  2431  2431 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:15:47.002  2431  2431 I AndroidRuntime: Using default boot image
10-28 08:15:47.002  2431  2431 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:15:47.157  2431  2431 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:15:47.215  2431  2431 D AndroidRuntime: Shutting down VM
10-28 08:15:48.949  2164  2218 I Perf    : fps=5.7 worst=6 drawn=15/37 queue=0 build=199ms verts=39k cam=0.5,1.6,0.5 yaw=2.61 pitch=0.00 torch=0 ready=true drawn=15 noMesh=0 culled=22
10-28 08:15:51.129  2164  2218 I Perf    : fps=5.5 worst=6 drawn=15/37 queue=0 build=199ms verts=39k cam=0.5,1.6,0.5 yaw=2.61 pitch=0.00 torch=0 ready=true drawn=15 noMesh=0 culled=22
10-28 08:15:51.970  2448  2448 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:15:51.982  2448  2448 I AndroidRuntime: Using default boot image
10-28 08:15:51.982  2448  2448 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:15:52.145  2448  2448 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:15:53.256  2164  2218 I Perf    : fps=5.6 worst=6 drawn=15/37 queue=0 build=199ms verts=39k cam=0.3,1.7,0.8 yaw=2.61 pitch=0.00 torch=0 ready=true drawn=15 noMesh=0 culled=22
10-28 08:15:53.787  2448  2448 D AndroidRuntime: Shutting down VM
10-28 08:15:55.432  2164  2218 I Perf    : fps=5.1 worst=6 drawn=19/44 queue=0 build=103ms verts=49k cam=-0.4,1.6,1.7 yaw=2.61 pitch=0.00 torch=0 ready=true drawn=19 noMesh=0 culled=25
10-28 08:15:57.559  2164  2218 I Perf    : fps=4.7 worst=5 drawn=19/44 queue=0 build=103ms verts=49k cam=-0.4,1.6,1.7 yaw=2.61 pitch=0.00 torch=0 ready=true drawn=19 noMesh=0 culled=25
10-28 08:15:59.210  2468  2468 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 08:15:59.648  2164  2218 I Perf    : fps=4.8 worst=5 drawn=19/44 queue=0 build=103ms verts=49k cam=-0.4,1.6,1.7 yaw=2.61 pitch=0.00 torch=0 ready=true drawn=19 noMesh=0 culled=25
10-28 08:16:01.735  2164  2218 I Perf    : fps=4.8 worst=5 drawn=19/44 queue=0 build=103ms verts=49k cam=-0.4,1.6,1.7 yaw=2.61 pitch=0.00 torch=0 ready=true drawn=19 noMesh=0 culled=25
10-28 08:16:03.915  2164  2218 I Perf    : fps=4.6 worst=5 drawn=19/44 queue=0 build=103ms verts=49k cam=-0.4,1.6,1.7 yaw=2.61 pitch=0.00 torch=0 ready=true drawn=19 noMesh=0 culled=25
+ '[' -z 2164 ']'
+ grep -qa 'FATAL EXCEPTION' emulator-shots/logcat.txt
+ '[' 0 '!=' 0 ']'
+ echo 'OK: the app is alive, rendering, and the touch controls work'
OK: the app is alive, rendering, and the touch controls work
```
