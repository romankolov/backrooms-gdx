# Emulator smoke test diagnostics

Commit: 6244b2d0ba493c3cebb3202c7a39cb485561a524
Outcome: success

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 28 08:21 /dev/kvm
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
INFO         | The active JSON Web Key Sets can be found here: /run/user/1001/avd/running/2730/jwks/17e0f5d4-d8af-4ec1-8651-fc7e72ead029/active.jwk
INFO         | Scanning /run/user/1001/avd/running/2730/jwks/17e0f5d4-d8af-4ec1-8651-fc7e72ead029 for jwk keys.
INFO         | Started GRPC server at 127.0.0.1:8554, security: Local, auth: +token
INFO         | Advertising in: /run/user/1001/avd/running/pid_2730.ini
INFO         | Setting display: 0 configuration to: 1080x1920, dpi: 420x420 
INFO         | setDisplayActiveConfig: id:0, 1080x1920
INFO         | emulatorSetupEnvironment: Environment scene is not required
USER_INFO    | Emulator is performing a full startup. This may take upto two minutes, or more.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
INFO         | Boot completed in 25414 ms
INFO         | Increasing screen off timeout, logcat buffer size to 2M.
```
## Logcat (filtered)
```
10-28 08:23:15.537   502   543 W ActivityManager: Slow operation: 54ms so far, now at startProcess: returned from zygote!
10-28 08:23:15.559   502   543 W ActivityManager: Slow operation: 77ms so far, now at startProcess: done updating battery stats
10-28 08:23:15.560   502   543 W ActivityManager: Slow operation: 77ms so far, now at startProcess: building log message
10-28 08:23:15.560   502   543 I ActivityManager: Start proc 2304:com.android.traceur/u0a91 for broadcast {com.android.traceur/com.android.traceur.Receiver}
10-28 08:23:15.560   502   543 W ActivityManager: Slow operation: 77ms so far, now at startProcess: starting to update pids map
10-28 08:23:15.560   502   543 W ActivityManager: Slow operation: 78ms so far, now at startProcess: done updating pids map
10-28 08:23:15.620   502   995 I ActivityManager: Finished processing BOOT_COMPLETED for u0
10-28 08:23:15.621   502   995 I ActivityManager: Killing 1738:com.android.externalstorage/u0a68 (adj 985): empty #17
10-28 08:23:35.544  2354  2354 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:23:35.546  2354  2354 I AndroidRuntime: Using default boot image
10-28 08:23:35.546  2354  2354 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:23:35.662  2354  2354 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:23:35.726  2354  2354 D AndroidRuntime: Shutting down VM
10-28 08:23:35.837   502   533 I ActivityManager: Killing 1977:com.android.calendar/u0a108 (adj 985): empty #17
10-28 08:23:35.837   502   533 I ActivityManager: Killing 1840:com.android.quicksearchbox/u0a101 (adj 985): empty #18
10-28 08:23:35.837   502   533 I ActivityManager: Killing 1279:com.android.cellbroadcastreceiver/u0a63 (adj 995): empty #19
10-28 08:23:36.179  2156  2202 I BackroomsGL: world shader compiled
10-28 08:23:36.231  2156  2202 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 08:23:36.231  2156  2202 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 08:23:38.279  2156  2202 I Perf    : fps=4.6 worst=52 drawn=5/8 queue=2 build=234ms verts=12k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=5 noMesh=0 culled=3
10-28 08:23:40.329  2156  2202 I Perf    : fps=5.4 worst=5 drawn=13/29 queue=2 build=24ms verts=32k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=14
10-28 08:23:40.849   502  1119 I ActivityManager: Killing 2017:com.android.camera2/u0a110 (adj 975): empty #17
10-28 08:23:42.378  2156  2202 I Perf    : fps=5.3 worst=5 drawn=14/37 queue=0 build=14ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:23:44.426  2156  2202 I Perf    : fps=5.4 worst=6 drawn=14/37 queue=0 build=14ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:23:46.446  2156  2202 I Perf    : fps=5.4 worst=6 drawn=14/37 queue=0 build=14ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:23:48.609  2156  2202 I Perf    : fps=5.5 worst=6 drawn=14/37 queue=0 build=14ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:23:50.692  2156  2202 I Perf    : fps=5.3 worst=6 drawn=14/37 queue=0 build=14ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:23:52.767  2156  2202 I Perf    : fps=5.3 worst=5 drawn=14/37 queue=0 build=14ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:23:54.879  2156  2202 I Perf    : fps=5.7 worst=6 drawn=14/37 queue=0 build=14ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:23:56.896  2156  2202 I Perf    : fps=5.5 worst=6 drawn=14/37 queue=0 build=14ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:23:58.940  2156  2202 I Perf    : fps=5.4 worst=6 drawn=14/37 queue=0 build=14ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:24:01.100  2156  2202 I Perf    : fps=5.1 worst=5 drawn=14/37 queue=0 build=14ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:24:03.136  2156  2202 I Perf    : fps=5.4 worst=6 drawn=14/37 queue=0 build=14ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:24:05.140  2156  2202 I Perf    : fps=5.5 worst=5 drawn=14/37 queue=0 build=14ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:24:06.027  2393  2393 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:24:06.038  2393  2393 I AndroidRuntime: Using default boot image
10-28 08:24:06.038  2393  2393 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:24:06.179  2393  2393 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:24:06.908  2393  2393 D AndroidRuntime: Shutting down VM
10-28 08:24:07.185  2156  2202 I Perf    : fps=5.4 worst=7 drawn=18/37 queue=0 build=14ms verts=45k cam=-0.5,1.6,1.5 yaw=2.58 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:24:09.326  2156  2202 I Perf    : fps=7.0 worst=8 drawn=18/37 queue=0 build=14ms verts=45k cam=-0.5,1.6,1.5 yaw=2.58 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:24:11.328  2156  2202 I Perf    : fps=7.5 worst=8 drawn=18/37 queue=0 build=14ms verts=45k cam=-0.5,1.6,1.5 yaw=2.58 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:24:11.883  2422  2422 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:24:11.893  2422  2422 I AndroidRuntime: Using default boot image
10-28 08:24:11.893  2422  2422 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:24:11.996  2422  2422 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:24:12.028  2422  2422 D AndroidRuntime: Shutting down VM
10-28 08:24:13.449  2156  2202 I Perf    : fps=7.5 worst=8 drawn=18/37 queue=0 build=14ms verts=45k cam=-0.5,1.6,1.5 yaw=2.58 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 08:24:15.570  2156  2202 I Perf    : fps=7.1 worst=8 drawn=18/37 queue=0 build=14ms verts=45k cam=-0.5,1.6,1.5 yaw=2.58 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 08:24:16.771  2439  2439 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:24:16.781  2439  2439 I AndroidRuntime: Using default boot image
10-28 08:24:16.781  2439  2439 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:24:16.853  2439  2439 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:24:17.643  2156  2202 I Perf    : fps=7.2 worst=8 drawn=18/37 queue=0 build=14ms verts=45k cam=-0.6,1.6,1.7 yaw=2.58 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 08:24:18.355  2439  2439 D AndroidRuntime: Shutting down VM
10-28 08:24:19.680  2156  2202 I Perf    : fps=6.9 worst=8 drawn=18/37 queue=0 build=14ms verts=45k cam=-1.3,1.6,2.8 yaw=2.58 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 08:24:21.731  2156  2202 I Perf    : fps=6.8 worst=8 drawn=18/37 queue=0 build=14ms verts=45k cam=-1.3,1.6,2.8 yaw=2.58 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 08:24:23.787  2156  2202 I Perf    : fps=6.3 worst=7 drawn=18/37 queue=0 build=14ms verts=45k cam=-1.3,1.6,2.8 yaw=2.58 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 08:24:25.824  2156  2202 I Perf    : fps=6.4 worst=7 drawn=18/37 queue=0 build=14ms verts=45k cam=-1.3,1.6,2.8 yaw=2.58 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 08:24:27.941  2156  2202 I Perf    : fps=6.6 worst=7 drawn=18/37 queue=0 build=14ms verts=45k cam=-1.3,1.6,2.8 yaw=2.58 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
```
## Step log
```
put: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 08:23:38.279  2156  2202 I Perf    : fps=4.6 worst=52 drawn=5/8 queue=2 build=234ms verts=12k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=5 noMesh=0 culled=3
10-28 08:23:40.329  2156  2202 I Perf    : fps=5.4 worst=5 drawn=13/29 queue=2 build=24ms verts=32k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=14
10-28 08:23:42.378  2156  2202 I Perf    : fps=5.3 worst=5 drawn=14/37 queue=0 build=14ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:23:44.426  2156  2202 I Perf    : fps=5.4 worst=6 drawn=14/37 queue=0 build=14ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:23:46.446  2156  2202 I Perf    : fps=5.4 worst=6 drawn=14/37 queue=0 build=14ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:23:48.609  2156  2202 I Perf    : fps=5.5 worst=6 drawn=14/37 queue=0 build=14ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:23:50.692  2156  2202 I Perf    : fps=5.3 worst=6 drawn=14/37 queue=0 build=14ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:23:52.767  2156  2202 I Perf    : fps=5.3 worst=5 drawn=14/37 queue=0 build=14ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:23:54.879  2156  2202 I Perf    : fps=5.7 worst=6 drawn=14/37 queue=0 build=14ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:23:56.896  2156  2202 I Perf    : fps=5.5 worst=6 drawn=14/37 queue=0 build=14ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:23:58.940  2156  2202 I Perf    : fps=5.4 worst=6 drawn=14/37 queue=0 build=14ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:24:01.100  2156  2202 I Perf    : fps=5.1 worst=5 drawn=14/37 queue=0 build=14ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:24:01.875  2381  2381 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 08:24:03.136  2156  2202 I Perf    : fps=5.4 worst=6 drawn=14/37 queue=0 build=14ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:24:05.140  2156  2202 I Perf    : fps=5.5 worst=5 drawn=14/37 queue=0 build=14ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:24:06.027  2393  2393 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:24:06.038  2393  2393 I AndroidRuntime: Using default boot image
10-28 08:24:06.038  2393  2393 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:24:06.179  2393  2393 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:24:06.908  2393  2393 D AndroidRuntime: Shutting down VM
10-28 08:24:07.185  2156  2202 I Perf    : fps=5.4 worst=7 drawn=18/37 queue=0 build=14ms verts=45k cam=-0.5,1.6,1.5 yaw=2.58 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:24:09.326  2156  2202 I Perf    : fps=7.0 worst=8 drawn=18/37 queue=0 build=14ms verts=45k cam=-0.5,1.6,1.5 yaw=2.58 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:24:11.328  2156  2202 I Perf    : fps=7.5 worst=8 drawn=18/37 queue=0 build=14ms verts=45k cam=-0.5,1.6,1.5 yaw=2.58 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:24:11.883  2422  2422 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:24:11.893  2422  2422 I AndroidRuntime: Using default boot image
10-28 08:24:11.893  2422  2422 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:24:11.996  2422  2422 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:24:12.028  2422  2422 D AndroidRuntime: Shutting down VM
10-28 08:24:13.449  2156  2202 I Perf    : fps=7.5 worst=8 drawn=18/37 queue=0 build=14ms verts=45k cam=-0.5,1.6,1.5 yaw=2.58 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 08:24:15.570  2156  2202 I Perf    : fps=7.1 worst=8 drawn=18/37 queue=0 build=14ms verts=45k cam=-0.5,1.6,1.5 yaw=2.58 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 08:24:16.771  2439  2439 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:24:16.781  2439  2439 I AndroidRuntime: Using default boot image
10-28 08:24:16.781  2439  2439 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:24:16.853  2439  2439 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:24:17.643  2156  2202 I Perf    : fps=7.2 worst=8 drawn=18/37 queue=0 build=14ms verts=45k cam=-0.6,1.6,1.7 yaw=2.58 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 08:24:18.355  2439  2439 D AndroidRuntime: Shutting down VM
10-28 08:24:19.680  2156  2202 I Perf    : fps=6.9 worst=8 drawn=18/37 queue=0 build=14ms verts=45k cam=-1.3,1.6,2.8 yaw=2.58 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 08:24:21.731  2156  2202 I Perf    : fps=6.8 worst=8 drawn=18/37 queue=0 build=14ms verts=45k cam=-1.3,1.6,2.8 yaw=2.58 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 08:24:23.780  2457  2457 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 08:24:23.787  2156  2202 I Perf    : fps=6.3 worst=7 drawn=18/37 queue=0 build=14ms verts=45k cam=-1.3,1.6,2.8 yaw=2.58 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 08:24:25.824  2156  2202 I Perf    : fps=6.4 worst=7 drawn=18/37 queue=0 build=14ms verts=45k cam=-1.3,1.6,2.8 yaw=2.58 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 08:24:27.941  2156  2202 I Perf    : fps=6.6 worst=7 drawn=18/37 queue=0 build=14ms verts=45k cam=-1.3,1.6,2.8 yaw=2.58 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
+ '[' -z 2156 ']'
+ grep -qa 'FATAL EXCEPTION' emulator-shots/logcat.txt
+ '[' 0 '!=' 0 ']'
+ echo 'OK: the app is alive, rendering, and the touch controls work'
OK: the app is alive, rendering, and the touch controls work
```
