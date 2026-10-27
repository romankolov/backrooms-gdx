# Emulator smoke test diagnostics

Commit: aed194d33a08674078c0825298d1c2751958bfda
Outcome: success

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 27 23:23 /dev/kvm
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
INFO         | The active JSON Web Key Sets can be found here: /run/user/1001/avd/running/3077/jwks/3d5e2e6a-0291-43fc-bca7-7551a242b525/active.jwk
INFO         | Scanning /run/user/1001/avd/running/3077/jwks/3d5e2e6a-0291-43fc-bca7-7551a242b525 for jwk keys.
INFO         | Started GRPC server at 127.0.0.1:8554, security: Local, auth: +token
INFO         | Advertising in: /run/user/1001/avd/running/pid_3077.ini
INFO         | Setting display: 0 configuration to: 1080x1920, dpi: 420x420 
INFO         | setDisplayActiveConfig: id:0, 1080x1920
INFO         | emulatorSetupEnvironment: Environment scene is not required
USER_INFO    | Emulator is performing a full startup. This may take upto two minutes, or more.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
INFO         | Boot completed in 26369 ms
INFO         | Increasing screen off timeout, logcat buffer size to 2M.
```
## Logcat (filtered)
```
10-27 23:31:58.528   505  1612 W ActivityManager: Slow operation: 68ms so far, now at startProcess: done creating new process record
10-27 23:31:58.528   505  1612 W ActivityManager: Slow operation: 68ms so far, now at startProcess: stepping in to startProcess
10-27 23:31:58.528   505  1612 W ActivityManager: Slow operation: 69ms so far, now at startProcess: done starting proc!
10-27 23:31:58.540   505  1612 I ActivityManager: Killing 1391:com.android.carrierconfig/u0a114 (adj 985): empty #17
10-27 23:31:58.599   505   545 W ActivityManager: Slow operation: 72ms so far, now at startProcess: done updating battery stats
10-27 23:31:58.599   505   545 W ActivityManager: Slow operation: 72ms so far, now at startProcess: building log message
10-27 23:31:58.599   505   545 I ActivityManager: Start proc 2194:com.android.gallery3d/u0a111 for broadcast {com.android.gallery3d/com.android.camera.DisableCameraReceiver}
10-27 23:31:58.599   505   545 W ActivityManager: Slow operation: 72ms so far, now at startProcess: starting to update pids map
10-27 23:31:58.600   505   545 W ActivityManager: Slow operation: 72ms so far, now at startProcess: done updating pids map
10-27 23:31:58.666   505  1580 I ActivityManager: Killing 1596:com.android.keychain/1000 (adj 985): empty #17
10-27 23:31:58.987   505   545 I ActivityManager: Start proc 2213:com.android.nfc/1027 for broadcast {com.android.nfc/com.android.nfc.NfcBootCompletedReceiver}
10-27 23:31:59.288   505   561 I ActivityManager: Killing 2213:com.android.nfc/1027 (adj 0): change com.android.nfc
10-27 23:31:59.319   505   545 I ActivityManager: Start proc 2233:com.android.onetimeinitializer/u0a96 for broadcast {com.android.onetimeinitializer/com.android.onetimeinitializer.OneTimeInitializerReceiver}
10-27 23:31:59.508   505  1580 I ActivityManager: Killing 1820:com.android.providers.calendar/u0a67 (adj 985): empty #17
10-27 23:31:59.508   505   545 I ActivityManager: Start proc 2259:com.android.packageinstaller/u0a70 for broadcast {com.android.packageinstaller/com.android.packageinstaller.TemporaryFileManager}
10-27 23:31:59.656   505  1580 I ActivityManager: Killing 1635:com.android.printspooler/u0a87 (adj 985): empty #17
10-27 23:31:59.709   505   545 I ActivityManager: Start proc 2280:com.android.providers.calendar/u0a67 for broadcast {com.android.providers.calendar/com.android.providers.calendar.CalendarReceiver}
10-27 23:31:59.947   505  1580 I ActivityManager: Killing 1765:com.android.externalstorage/u0a68 (adj 985): empty #17
10-27 23:32:00.081   505   545 I ActivityManager: Start proc 2307:com.android.shell/2000 for broadcast {com.android.shell/com.android.shell.HeapDumpReceiver}
10-27 23:32:00.203   505  1077 I ActivityManager: Killing 1924:com.android.quicksearchbox/u0a102 (adj 985): empty #17
10-27 23:32:00.334   505   545 W ActivityManager: Slow operation: 70ms so far, now at startProcess: returned from zygote!
10-27 23:32:00.334   505   545 W ActivityManager: Slow operation: 71ms so far, now at startProcess: done updating battery stats
10-27 23:32:00.334   505   545 W ActivityManager: Slow operation: 71ms so far, now at startProcess: building log message
10-27 23:32:00.334   505   545 I ActivityManager: Start proc 2328:com.android.traceur/u0a90 for broadcast {com.android.traceur/com.android.traceur.Receiver}
10-27 23:32:00.334   505   545 W ActivityManager: Slow operation: 71ms so far, now at startProcess: starting to update pids map
10-27 23:32:00.334   505   545 W ActivityManager: Slow operation: 71ms so far, now at startProcess: done updating pids map
10-27 23:32:00.523   505  1077 I ActivityManager: Finished processing BOOT_COMPLETED for u0
10-27 23:32:00.523   505  1077 I ActivityManager: Killing 1952:com.android.localtransport/1000 (adj 985): empty #17
10-27 23:32:01.499   419  2069 I iorapd  : Perfetto TraceBuffer saved to file: /data/misc/iorapd/com.backrooms.gdx.debug/1/com.backrooms.gdx.android.AndroidLauncher/raw_traces/1793143921442063905.perfetto_trace.pb
10-27 23:32:15.008   505   543 I ActivityManager: Killing 1288:com.android.cellbroadcastreceiver/u0a66 (adj 985): empty #17
10-27 23:32:17.359   505   535 I ActivityManager: Killing 1741:com.android.provision/u0a115 (adj 985): empty #17
10-27 23:32:19.938  2384  2384 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-27 23:32:19.943  2384  2384 I AndroidRuntime: Using default boot image
10-27 23:32:19.943  2384  2384 I AndroidRuntime: Leaving lock profiling enabled
10-27 23:32:20.076  2384  2384 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-27 23:32:20.115  2384  2384 D AndroidRuntime: Shutting down VM
10-27 23:32:20.705  2074  2105 E BackroomsGL: SHADER FAILED: Precisions for uniform u_time do not match between the vertex and fragment shader|
10-27 23:32:22.450   505   535 I ActivityManager: Killing 1520:android.process.acore/u0a64 (adj 985): empty #17
10-27 23:32:22.455   505  2127 I ActivityManager: Killing 2012:com.android.calendar/u0a107 (adj 975): empty #17
10-27 23:32:22.746  2074  2105 I Perf    : fps=27.6 worst=50 drawn=0/11 queue=2 build=218ms verts=0k cam=1.5,1.4,1.5 ready=false drawn=0 noMesh=0 culled=0 shaderLog=Precisions for uniform u_time do not match between the vertex and fragment shader|
10-27 23:32:24.740  2074  2105 I Perf    : fps=33.3 worst=98 drawn=0/27 queue=2 build=130ms verts=0k cam=1.5,1.4,1.5 ready=false drawn=0 noMesh=0 culled=0 shaderLog=Precisions for uniform u_time do not match between the vertex and fragment shader|
10-27 23:32:26.745  2074  2105 I Perf    : fps=35.9 worst=112 drawn=0/37 queue=0 build=172ms verts=0k cam=1.5,1.4,1.5 ready=false drawn=0 noMesh=0 culled=0 shaderLog=Precisions for uniform u_time do not match between the vertex and fragment shader|
10-27 23:32:28.768  2074  2105 I Perf    : fps=38.1 worst=219 drawn=0/37 queue=0 build=172ms verts=0k cam=1.5,1.4,1.5 ready=false drawn=0 noMesh=0 culled=0 shaderLog=Precisions for uniform u_time do not match between the vertex and fragment shader|
10-27 23:32:30.775  2074  2105 I Perf    : fps=37.9 worst=210 drawn=0/37 queue=0 build=172ms verts=0k cam=1.5,1.4,1.5 ready=false drawn=0 noMesh=0 culled=0 shaderLog=Precisions for uniform u_time do not match between the vertex and fragment shader|
10-27 23:32:32.777  2074  2105 I Perf    : fps=37.0 worst=111 drawn=0/37 queue=0 build=172ms verts=0k cam=1.5,1.4,1.5 ready=false drawn=0 noMesh=0 culled=0 shaderLog=Precisions for uniform u_time do not match between the vertex and fragment shader|
10-27 23:32:34.791  2074  2105 I Perf    : fps=37.2 worst=121 drawn=0/37 queue=0 build=172ms verts=0k cam=1.5,1.4,1.5 ready=false drawn=0 noMesh=0 culled=0 shaderLog=Precisions for uniform u_time do not match between the vertex and fragment shader|
10-27 23:32:36.808  2074  2105 I Perf    : fps=37.7 worst=57 drawn=0/37 queue=0 build=172ms verts=0k cam=1.5,1.4,1.5 ready=false drawn=0 noMesh=0 culled=0 shaderLog=Precisions for uniform u_time do not match between the vertex and fragment shader|
10-27 23:32:38.811  2074  2105 I Perf    : fps=37.9 worst=159 drawn=0/37 queue=0 build=172ms verts=0k cam=1.5,1.4,1.5 ready=false drawn=0 noMesh=0 culled=0 shaderLog=Precisions for uniform u_time do not match between the vertex and fragment shader|
10-27 23:32:40.843  2074  2105 I Perf    : fps=37.4 worst=58 drawn=0/37 queue=0 build=172ms verts=0k cam=1.5,1.4,1.5 ready=false drawn=0 noMesh=0 culled=0 shaderLog=Precisions for uniform u_time do not match between the vertex and fragment shader|
10-27 23:32:42.855  2074  2105 I Perf    : fps=37.3 worst=74 drawn=0/37 queue=0 build=172ms verts=0k cam=1.5,1.4,1.5 ready=false drawn=0 noMesh=0 culled=0 shaderLog=Precisions for uniform u_time do not match between the vertex and fragment shader|
10-27 23:32:44.857  2074  2105 I Perf    : fps=37.5 worst=91 drawn=0/37 queue=0 build=172ms verts=0k cam=1.5,1.4,1.5 ready=false drawn=0 noMesh=0 culled=0 shaderLog=Precisions for uniform u_time do not match between the vertex and fragment shader|
10-27 23:32:46.219  2409  2409 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-27 23:32:46.232  2409  2409 I AndroidRuntime: Using default boot image
10-27 23:32:46.232  2409  2409 I AndroidRuntime: Leaving lock profiling enabled
10-27 23:32:46.397  2409  2409 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-27 23:32:46.710  2409  2409 D AndroidRuntime: Shutting down VM
10-27 23:32:46.884  2074  2105 I Perf    : fps=35.5 worst=142 drawn=0/37 queue=0 build=172ms verts=0k cam=1.5,1.4,0.4 ready=false drawn=0 noMesh=0 culled=0 shaderLog=Precisions for uniform u_time do not match between the vertex and fragment shader|
10-27 23:32:48.910  2074  2105 I Perf    : fps=34.6 worst=307 drawn=0/37 queue=0 build=172ms verts=0k cam=1.5,1.4,0.4 ready=false drawn=0 noMesh=0 culled=0 shaderLog=Precisions for uniform u_time do not match between the vertex and fragment shader|
10-27 23:32:50.911  2074  2105 I Perf    : fps=38.0 worst=59 drawn=0/37 queue=0 build=172ms verts=0k cam=1.5,1.4,0.4 ready=false drawn=0 noMesh=0 culled=0 shaderLog=Precisions for uniform u_time do not match between the vertex and fragment shader|
10-27 23:32:52.915  2074  2105 I Perf    : fps=35.9 worst=91 drawn=0/37 queue=0 build=172ms verts=0k cam=1.5,1.4,0.4 ready=false drawn=0 noMesh=0 culled=0 shaderLog=Precisions for uniform u_time do not match between the vertex and fragment shader|
```
## Step log
```
9  2069 I iorapd  : Perfetto TraceBuffer saved to file: /data/misc/iorapd/com.backrooms.gdx.debug/1/com.backrooms.gdx.android.AndroidLauncher/raw_traces/1793143921442063905.perfetto_trace.pb
10-27 23:32:19.132  2382  2382 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-27 23:32:19.938  2384  2384 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-27 23:32:19.943  2384  2384 I AndroidRuntime: Using default boot image
10-27 23:32:19.943  2384  2384 I AndroidRuntime: Leaving lock profiling enabled
10-27 23:32:20.076  2384  2384 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-27 23:32:20.115  2384  2384 D AndroidRuntime: Shutting down VM
10-27 23:32:20.705  2074  2105 E BackroomsGL: SHADER FAILED: Precisions for uniform u_time do not match between the vertex and fragment shader|
10-27 23:32:22.746  2074  2105 I Perf    : fps=27.6 worst=50 drawn=0/11 queue=2 build=218ms verts=0k cam=1.5,1.4,1.5 ready=false drawn=0 noMesh=0 culled=0 shaderLog=Precisions for uniform u_time do not match between the vertex and fragment shader|
10-27 23:32:24.740  2074  2105 I Perf    : fps=33.3 worst=98 drawn=0/27 queue=2 build=130ms verts=0k cam=1.5,1.4,1.5 ready=false drawn=0 noMesh=0 culled=0 shaderLog=Precisions for uniform u_time do not match between the vertex and fragment shader|
10-27 23:32:26.745  2074  2105 I Perf    : fps=35.9 worst=112 drawn=0/37 queue=0 build=172ms verts=0k cam=1.5,1.4,1.5 ready=false drawn=0 noMesh=0 culled=0 shaderLog=Precisions for uniform u_time do not match between the vertex and fragment shader|
10-27 23:32:28.768  2074  2105 I Perf    : fps=38.1 worst=219 drawn=0/37 queue=0 build=172ms verts=0k cam=1.5,1.4,1.5 ready=false drawn=0 noMesh=0 culled=0 shaderLog=Precisions for uniform u_time do not match between the vertex and fragment shader|
10-27 23:32:30.775  2074  2105 I Perf    : fps=37.9 worst=210 drawn=0/37 queue=0 build=172ms verts=0k cam=1.5,1.4,1.5 ready=false drawn=0 noMesh=0 culled=0 shaderLog=Precisions for uniform u_time do not match between the vertex and fragment shader|
10-27 23:32:32.777  2074  2105 I Perf    : fps=37.0 worst=111 drawn=0/37 queue=0 build=172ms verts=0k cam=1.5,1.4,1.5 ready=false drawn=0 noMesh=0 culled=0 shaderLog=Precisions for uniform u_time do not match between the vertex and fragment shader|
10-27 23:32:34.791  2074  2105 I Perf    : fps=37.2 worst=121 drawn=0/37 queue=0 build=172ms verts=0k cam=1.5,1.4,1.5 ready=false drawn=0 noMesh=0 culled=0 shaderLog=Precisions for uniform u_time do not match between the vertex and fragment shader|
10-27 23:32:36.808  2074  2105 I Perf    : fps=37.7 worst=57 drawn=0/37 queue=0 build=172ms verts=0k cam=1.5,1.4,1.5 ready=false drawn=0 noMesh=0 culled=0 shaderLog=Precisions for uniform u_time do not match between the vertex and fragment shader|
10-27 23:32:38.811  2074  2105 I Perf    : fps=37.9 worst=159 drawn=0/37 queue=0 build=172ms verts=0k cam=1.5,1.4,1.5 ready=false drawn=0 noMesh=0 culled=0 shaderLog=Precisions for uniform u_time do not match between the vertex and fragment shader|
10-27 23:32:40.843  2074  2105 I Perf    : fps=37.4 worst=58 drawn=0/37 queue=0 build=172ms verts=0k cam=1.5,1.4,1.5 ready=false drawn=0 noMesh=0 culled=0 shaderLog=Precisions for uniform u_time do not match between the vertex and fragment shader|
10-27 23:32:42.855  2074  2105 I Perf    : fps=37.3 worst=74 drawn=0/37 queue=0 build=172ms verts=0k cam=1.5,1.4,1.5 ready=false drawn=0 noMesh=0 culled=0 shaderLog=Precisions for uniform u_time do not match between the vertex and fragment shader|
10-27 23:32:44.857  2074  2105 I Perf    : fps=37.5 worst=91 drawn=0/37 queue=0 build=172ms verts=0k cam=1.5,1.4,1.5 ready=false drawn=0 noMesh=0 culled=0 shaderLog=Precisions for uniform u_time do not match between the vertex and fragment shader|
10-27 23:32:45.778  2407  2407 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-27 23:32:46.219  2409  2409 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-27 23:32:46.232  2409  2409 I AndroidRuntime: Using default boot image
10-27 23:32:46.232  2409  2409 I AndroidRuntime: Leaving lock profiling enabled
10-27 23:32:46.397  2409  2409 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-27 23:32:46.710  2409  2409 D AndroidRuntime: Shutting down VM
10-27 23:32:46.884  2074  2105 I Perf    : fps=35.5 worst=142 drawn=0/37 queue=0 build=172ms verts=0k cam=1.5,1.4,0.4 ready=false drawn=0 noMesh=0 culled=0 shaderLog=Precisions for uniform u_time do not match between the vertex and fragment shader|
10-27 23:32:48.910  2074  2105 I Perf    : fps=34.6 worst=307 drawn=0/37 queue=0 build=172ms verts=0k cam=1.5,1.4,0.4 ready=false drawn=0 noMesh=0 culled=0 shaderLog=Precisions for uniform u_time do not match between the vertex and fragment shader|
10-27 23:32:50.911  2074  2105 I Perf    : fps=38.0 worst=59 drawn=0/37 queue=0 build=172ms verts=0k cam=1.5,1.4,0.4 ready=false drawn=0 noMesh=0 culled=0 shaderLog=Precisions for uniform u_time do not match between the vertex and fragment shader|
10-27 23:32:52.432  2443  2443 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-27 23:32:52.915  2074  2105 I Perf    : fps=35.9 worst=91 drawn=0/37 queue=0 build=172ms verts=0k cam=1.5,1.4,0.4 ready=false drawn=0 noMesh=0 culled=0 shaderLog=Precisions for uniform u_time do not match between the vertex and fragment shader|
+ '[' -z 2074 ']'
+ grep -qa 'FATAL EXCEPTION' emulator-shots/logcat.txt
+ echo 'OK: the app is alive and rendering'
OK: the app is alive and rendering
```
