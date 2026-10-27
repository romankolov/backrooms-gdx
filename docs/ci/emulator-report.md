# Emulator smoke test diagnostics

Commit: 1f6e1ec6ca2cda385b44f6ce676fb6e125b949e8
Outcome: success

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 27 23:53 /dev/kvm
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
INFO         | The active JSON Web Key Sets can be found here: /run/user/1001/avd/running/2969/jwks/46189358-4e1a-400a-a00b-a2652348eaf3/active.jwk
INFO         | Scanning /run/user/1001/avd/running/2969/jwks/46189358-4e1a-400a-a00b-a2652348eaf3 for jwk keys.
INFO         | Started GRPC server at 127.0.0.1:8554, security: Local, auth: +token
INFO         | Advertising in: /run/user/1001/avd/running/pid_2969.ini
INFO         | Setting display: 0 configuration to: 1080x1920, dpi: 420x420 
INFO         | setDisplayActiveConfig: id:0, 1080x1920
INFO         | emulatorSetupEnvironment: Environment scene is not required
USER_INFO    | Emulator is performing a full startup. This may take upto two minutes, or more.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
INFO         | Boot completed in 26257 ms
INFO         | Increasing screen off timeout, logcat buffer size to 2M.
```
## Logcat (filtered)
```
10-27 23:58:06.232   507   545 W system_server: Long monitor contention with owner PackageManager (563) at int com.android.server.am.ActivityManagerService$LocalService.broadcastIntent(android.content.Intent, android.content.IIntentReceiver, java.lang.String[], boolean, int, int[])(ActivityManagerService.java:19454) waiters=0 in int com.android.server.am.ActivityManagerService.broadcastIntentWithFeature(android.app.IApplicationThread, java.lang.String, android.content.Intent, java.lang.String, android.content.IIntentReceiver, int, java.lang.String, android.os.Bundle, java.lang.String[], int, android.os.Bundle, boolean, boolean, int) for 103ms
10-27 23:58:06.275   507  1646 I ActivityManager: Killing 1263:com.android.cellbroadcastreceiver.module/u0a125 (adj 985): empty #17
10-27 23:58:06.883   507   547 I ActivityManager: Start proc 2260:com.android.shell/2000 for broadcast {com.android.shell/com.android.shell.HeapDumpReceiver}
10-27 23:58:07.176   507  1249 I ActivityManager: Killing 1562:com.android.keychain/1000 (adj 985): empty #17
10-27 23:58:07.230   507   547 I ActivityManager: Start proc 2286:com.android.traceur/u0a91 for broadcast {com.android.traceur/com.android.traceur.Receiver}
10-27 23:58:07.386   507  1249 I ActivityManager: Finished processing BOOT_COMPLETED for u0
10-27 23:58:07.387   507  1249 I ActivityManager: Killing 1925:com.android.calendar/u0a108 (adj 985): empty #17
10-27 23:58:07.406   507   547 I ActivityManager: Start proc 2310:com.android.externalstorage/u0a68 for broadcast {com.android.externalstorage/com.android.externalstorage.MountReceiver}
10-27 23:58:07.621   507   625 I ActivityManager: Killing 1967:com.android.camera2/u0a112 (adj 985): empty #17
10-27 23:58:07.908   507   547 W ActivityManager: Slow operation: 63ms so far, now at startProcess: returned from zygote!
10-27 23:58:07.908   507   547 W ActivityManager: Slow operation: 64ms so far, now at startProcess: done updating battery stats
10-27 23:58:07.908   507   547 W ActivityManager: Slow operation: 64ms so far, now at startProcess: building log message
10-27 23:58:07.908   507   547 I ActivityManager: Start proc 2334:com.android.quicksearchbox/u0a101 for broadcast {com.android.quicksearchbox/com.android.quicksearchbox.SearchWidgetProvider}
10-27 23:58:07.908   507   547 W ActivityManager: Slow operation: 64ms so far, now at startProcess: starting to update pids map
10-27 23:58:07.908   507   547 W ActivityManager: Slow operation: 64ms so far, now at startProcess: done updating pids map
10-27 23:58:08.055   507   625 I ActivityManager: Killing 1864:com.android.printspooler/u0a89 (adj 985): empty #17
10-27 23:58:18.196   507   547 I ActivityManager: Start proc 2378:com.android.cellbroadcastreceiver.module/u0a125 for broadcast {com.android.cellbroadcastreceiver.module/com.android.cellbroadcastreceiver.CellBroadcastReceiver}
10-27 23:58:18.355   507   625 I ActivityManager: Killing 2018:com.android.contacts/u0a98 (adj 985): empty #17
10-27 23:58:21.279   507   545 I ActivityManager: Killing 1283:com.android.cellbroadcastreceiver/u0a64 (adj 985): empty #17
10-27 23:58:24.386   507   625 I ActivityManager: Killing 1593:android.process.acore/u0a65 (adj 985): empty #17
10-27 23:58:27.883  2407  2407 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-27 23:58:27.886  2407  2407 I AndroidRuntime: Using default boot image
10-27 23:58:27.886  2407  2407 I AndroidRuntime: Leaving lock profiling enabled
10-27 23:58:28.013  2407  2407 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-27 23:58:28.087  2407  2407 D AndroidRuntime: Shutting down VM
10-27 23:58:28.637  2145  2199 I BackroomsGL: world shader compiled
10-27 23:58:30.621  2145  2199 I Perf    : fps=6.8 worst=53 drawn=4/5 queue=2 build=280ms verts=12k cam=1.5,1.1,1.5 ready=true drawn=4 noMesh=0 culled=0
10-27 23:58:32.624  2145  2199 I Perf    : fps=3.0 worst=3 drawn=5/9 queue=2 build=1031ms verts=15k cam=1.5,1.1,1.5 ready=true drawn=5 noMesh=0 culled=3
10-27 23:58:32.710   507   537 I ActivityManager: Killing 1230:com.android.deskclock/u0a107 (adj 985): empty #17
10-27 23:58:32.715   507  2193 I ActivityManager: Killing 1813:com.android.provision/u0a116 (adj 975): empty #17
10-27 23:58:34.636  2145  2199 I Perf    : fps=3.0 worst=3 drawn=10/14 queue=2 build=108ms verts=30k cam=1.5,1.1,1.5 ready=true drawn=10 noMesh=0 culled=4
10-27 23:58:36.925  2145  2199 I Perf    : fps=3.1 worst=3 drawn=11/23 queue=2 build=105ms verts=33k cam=1.5,1.1,1.5 ready=true drawn=11 noMesh=0 culled=11
10-27 23:58:39.242  2145  2199 I Perf    : fps=3.0 worst=3 drawn=14/33 queue=2 build=97ms verts=41k cam=1.5,1.1,1.5 ready=true drawn=14 noMesh=0 culled=17
10-27 23:58:41.426  2145  2199 I Perf    : fps=3.2 worst=3 drawn=14/37 queue=0 build=83ms verts=41k cam=1.5,1.1,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:58:43.631  2145  2199 I Perf    : fps=3.2 worst=3 drawn=14/37 queue=0 build=83ms verts=41k cam=1.5,1.1,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:58:45.920  2145  2199 I Perf    : fps=3.1 worst=3 drawn=14/37 queue=0 build=83ms verts=41k cam=1.5,1.1,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:58:48.237  2145  2199 I Perf    : fps=3.0 worst=3 drawn=14/37 queue=0 build=83ms verts=41k cam=1.5,1.1,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:58:50.412  2145  2199 I Perf    : fps=3.2 worst=3 drawn=14/37 queue=0 build=83ms verts=41k cam=1.5,1.1,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:58:52.724  2145  2199 I Perf    : fps=3.0 worst=3 drawn=14/37 queue=0 build=83ms verts=41k cam=1.5,1.1,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:58:55.029  2145  2199 I Perf    : fps=3.0 worst=3 drawn=14/37 queue=0 build=83ms verts=41k cam=1.5,1.1,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:58:57.312  2145  2199 I Perf    : fps=3.1 worst=3 drawn=14/37 queue=0 build=83ms verts=41k cam=1.5,1.1,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:58:58.300  2445  2445 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-27 23:58:58.302  2445  2445 I AndroidRuntime: Using default boot image
10-27 23:58:58.302  2445  2445 I AndroidRuntime: Leaving lock profiling enabled
10-27 23:58:58.414  2445  2445 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-27 23:58:58.717  2445  2445 D AndroidRuntime: Shutting down VM
10-27 23:58:59.339  2145  2199 I Perf    : fps=3.0 worst=3 drawn=14/37 queue=0 build=83ms verts=41k cam=1.5,1.2,1.2 ready=true drawn=14 noMesh=0 culled=23
10-27 23:59:01.385  2145  2199 I Perf    : fps=2.9 worst=3 drawn=14/37 queue=0 build=83ms verts=41k cam=1.5,1.2,1.2 ready=true drawn=14 noMesh=0 culled=23
10-27 23:59:03.668  2145  2199 I Perf    : fps=3.1 worst=3 drawn=14/37 queue=0 build=83ms verts=41k cam=1.5,1.2,1.2 ready=true drawn=14 noMesh=0 culled=23
10-27 23:59:05.911  2145  2199 I Perf    : fps=3.1 worst=3 drawn=14/37 queue=0 build=83ms verts=41k cam=1.5,1.2,1.2 ready=true drawn=14 noMesh=0 culled=23
10-27 23:59:08.195  2145  2199 I Perf    : fps=3.1 worst=3 drawn=14/37 queue=0 build=83ms verts=41k cam=1.5,1.2,1.2 ready=true drawn=14 noMesh=0 culled=23
10-27 23:59:08.243   507   547 W ActivityManager: Slow operation: 221ms so far, now at startProcess: returned from zygote!
10-27 23:59:08.243   507   547 W ActivityManager: Slow operation: 221ms so far, now at startProcess: done updating battery stats
10-27 23:59:08.243   507   547 W ActivityManager: Slow operation: 221ms so far, now at startProcess: building log message
10-27 23:59:08.244   507   547 I ActivityManager: Start proc 2474:android.process.acore/u0a65 for content provider {com.android.providers.contacts/com.android.providers.contacts.VoicemailContentProvider}
10-27 23:59:08.244   507   547 W ActivityManager: Slow operation: 221ms so far, now at startProcess: starting to update pids map
10-27 23:59:08.244   507   547 W ActivityManager: Slow operation: 222ms so far, now at startProcess: done updating pids map
10-27 23:59:08.318  2474  2474 I ContactsPerf: VoicemailContentProvider.onCreate start
10-27 23:59:08.329  2474  2474 I ContactsPerf: VoicemailContentProvider.onCreate finish
10-27 23:59:10.482  2145  2199 I Perf    : fps=3.1 worst=3 drawn=14/37 queue=0 build=83ms verts=41k cam=1.5,1.2,1.2 ready=true drawn=14 noMesh=0 culled=23
```
## Step log
```
exture GL_OES_texture_half_float GL_OES_texture_half_float_linear GL_OES_packed_depth_stencil GL_OES_vertex_half_float GL_OES_standard_derivatives GL_OES_texture_npot GL_OES_rgb8_rgba8 GL_EXT_color_buffer_float GL_EXT_color_buffer_half_float GL_EXT_texture_format_BGRA8888 GL_APPLE_texture_format_BGRA8888 ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-27 23:58:27.101  2405  2405 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-27 23:58:27.883  2407  2407 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-27 23:58:27.886  2407  2407 I AndroidRuntime: Using default boot image
10-27 23:58:27.886  2407  2407 I AndroidRuntime: Leaving lock profiling enabled
10-27 23:58:28.013  2407  2407 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-27 23:58:28.087  2407  2407 D AndroidRuntime: Shutting down VM
10-27 23:58:28.637  2145  2199 I BackroomsGL: world shader compiled
10-27 23:58:30.621  2145  2199 I Perf    : fps=6.8 worst=53 drawn=4/5 queue=2 build=280ms verts=12k cam=1.5,1.1,1.5 ready=true drawn=4 noMesh=0 culled=0
10-27 23:58:32.624  2145  2199 I Perf    : fps=3.0 worst=3 drawn=5/9 queue=2 build=1031ms verts=15k cam=1.5,1.1,1.5 ready=true drawn=5 noMesh=0 culled=3
10-27 23:58:34.636  2145  2199 I Perf    : fps=3.0 worst=3 drawn=10/14 queue=2 build=108ms verts=30k cam=1.5,1.1,1.5 ready=true drawn=10 noMesh=0 culled=4
10-27 23:58:36.925  2145  2199 I Perf    : fps=3.1 worst=3 drawn=11/23 queue=2 build=105ms verts=33k cam=1.5,1.1,1.5 ready=true drawn=11 noMesh=0 culled=11
10-27 23:58:39.242  2145  2199 I Perf    : fps=3.0 worst=3 drawn=14/33 queue=2 build=97ms verts=41k cam=1.5,1.1,1.5 ready=true drawn=14 noMesh=0 culled=17
10-27 23:58:41.426  2145  2199 I Perf    : fps=3.2 worst=3 drawn=14/37 queue=0 build=83ms verts=41k cam=1.5,1.1,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:58:43.631  2145  2199 I Perf    : fps=3.2 worst=3 drawn=14/37 queue=0 build=83ms verts=41k cam=1.5,1.1,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:58:45.920  2145  2199 I Perf    : fps=3.1 worst=3 drawn=14/37 queue=0 build=83ms verts=41k cam=1.5,1.1,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:58:48.237  2145  2199 I Perf    : fps=3.0 worst=3 drawn=14/37 queue=0 build=83ms verts=41k cam=1.5,1.1,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:58:50.412  2145  2199 I Perf    : fps=3.2 worst=3 drawn=14/37 queue=0 build=83ms verts=41k cam=1.5,1.1,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:58:52.724  2145  2199 I Perf    : fps=3.0 worst=3 drawn=14/37 queue=0 build=83ms verts=41k cam=1.5,1.1,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:58:54.056  2435  2435 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-27 23:58:55.029  2145  2199 I Perf    : fps=3.0 worst=3 drawn=14/37 queue=0 build=83ms verts=41k cam=1.5,1.1,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:58:57.312  2145  2199 I Perf    : fps=3.1 worst=3 drawn=14/37 queue=0 build=83ms verts=41k cam=1.5,1.1,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:58:58.300  2445  2445 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-27 23:58:58.302  2445  2445 I AndroidRuntime: Using default boot image
10-27 23:58:58.302  2445  2445 I AndroidRuntime: Leaving lock profiling enabled
10-27 23:58:58.414  2445  2445 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-27 23:58:58.717  2445  2445 D AndroidRuntime: Shutting down VM
10-27 23:58:59.339  2145  2199 I Perf    : fps=3.0 worst=3 drawn=14/37 queue=0 build=83ms verts=41k cam=1.5,1.2,1.2 ready=true drawn=14 noMesh=0 culled=23
10-27 23:59:01.385  2145  2199 I Perf    : fps=2.9 worst=3 drawn=14/37 queue=0 build=83ms verts=41k cam=1.5,1.2,1.2 ready=true drawn=14 noMesh=0 culled=23
10-27 23:59:03.668  2145  2199 I Perf    : fps=3.1 worst=3 drawn=14/37 queue=0 build=83ms verts=41k cam=1.5,1.2,1.2 ready=true drawn=14 noMesh=0 culled=23
10-27 23:59:04.915  2472  2472 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-27 23:59:05.911  2145  2199 I Perf    : fps=3.1 worst=3 drawn=14/37 queue=0 build=83ms verts=41k cam=1.5,1.2,1.2 ready=true drawn=14 noMesh=0 culled=23
10-27 23:59:08.195  2145  2199 I Perf    : fps=3.1 worst=3 drawn=14/37 queue=0 build=83ms verts=41k cam=1.5,1.2,1.2 ready=true drawn=14 noMesh=0 culled=23
10-27 23:59:08.318  2474  2474 I ContactsPerf: VoicemailContentProvider.onCreate start
10-27 23:59:08.329  2474  2474 I ContactsPerf: VoicemailContentProvider.onCreate finish
10-27 23:59:10.482  2145  2199 I Perf    : fps=3.1 worst=3 drawn=14/37 queue=0 build=83ms verts=41k cam=1.5,1.2,1.2 ready=true drawn=14 noMesh=0 culled=23
+ '[' -z 2145 ']'
+ grep -qa 'FATAL EXCEPTION' emulator-shots/logcat.txt
+ echo 'OK: the app is alive and rendering'
OK: the app is alive and rendering
```
