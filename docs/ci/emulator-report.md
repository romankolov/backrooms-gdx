# Emulator smoke test diagnostics

Commit: ebb364e7fe14e68ce76c53f5a7d947bf59fbbc37
Outcome: success

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 27 23:42 /dev/kvm
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
INFO         | The active JSON Web Key Sets can be found here: /run/user/1001/avd/running/3231/jwks/7b052703-3fb4-4a71-ae10-4a2da37a10ce/active.jwk
INFO         | Scanning /run/user/1001/avd/running/3231/jwks/7b052703-3fb4-4a71-ae10-4a2da37a10ce for jwk keys.
INFO         | Started GRPC server at 127.0.0.1:8554, security: Local, auth: +token
INFO         | Advertising in: /run/user/1001/avd/running/pid_3231.ini
INFO         | Setting display: 0 configuration to: 1080x1920, dpi: 420x420 
INFO         | setDisplayActiveConfig: id:0, 1080x1920
INFO         | emulatorSetupEnvironment: Environment scene is not required
USER_INFO    | Emulator is performing a full startup. This may take upto two minutes, or more.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
INFO         | Boot completed in 26743 ms
INFO         | Increasing screen off timeout, logcat buffer size to 2M.
```
## Logcat (filtered)
```
10-27 23:47:06.283   507   547 W ActivityManager: Slow operation: 70ms so far, now at startProcess: returned from zygote!
10-27 23:47:06.378   507  1210 I ActivityManager: Killing 1711:com.android.music/u0a111 (adj 985): empty #17
10-27 23:47:06.378   507   547 W ActivityManager: Slow operation: 165ms so far, now at startProcess: done updating battery stats
10-27 23:47:06.378   507   547 W ActivityManager: Slow operation: 165ms so far, now at startProcess: building log message
10-27 23:47:06.378   507   547 I ActivityManager: Start proc 2155:com.backrooms.gdx.debug/u0a130 for pre-top-activity {com.backrooms.gdx.debug/com.backrooms.gdx.android.AndroidLauncher}
10-27 23:47:06.378   507   547 W ActivityManager: Slow operation: 165ms so far, now at startProcess: starting to update pids map
10-27 23:47:06.378   507   547 W ActivityManager: Slow operation: 165ms so far, now at startProcess: done updating pids map
10-27 23:47:06.579   507   547 W ActivityManager: Slow operation: 206ms so far, now at startProcess: returned from zygote!
10-27 23:47:06.607   507   562 I ActivityManager: Killing 2127:com.android.nfc/1027 (adj 905): change com.android.nfc
10-27 23:47:06.616   507   547 W ActivityManager: Slow operation: 242ms so far, now at startProcess: done updating battery stats
10-27 23:47:06.616   507   547 W ActivityManager: Slow operation: 242ms so far, now at startProcess: building log message
10-27 23:47:06.616   507   547 I ActivityManager: Start proc 2179:com.android.onetimeinitializer/u0a96 for broadcast {com.android.onetimeinitializer/com.android.onetimeinitializer.OneTimeInitializerReceiver}
10-27 23:47:06.616   507   547 W ActivityManager: Slow operation: 243ms so far, now at startProcess: starting to update pids map
10-27 23:47:06.616   507   547 W ActivityManager: Slow operation: 243ms so far, now at startProcess: done updating pids map
10-27 23:47:07.286   507   547 I ActivityManager: Start proc 2210:com.android.packageinstaller/u0a70 for broadcast {com.android.packageinstaller/com.android.packageinstaller.TemporaryFileManager}
10-27 23:47:07.346   507   894 I ActivityManager: Killing 1617:com.android.printspooler/u0a89 (adj 985): empty #17
10-27 23:47:07.650  2155  2202 I OpenGLRenderer: Davey! duration=815ms; Flags=1, IntendedVsync=31281891016, Vsync=31815224328, OldestInputEvent=9223372036854775807, NewestInputEvent=0, HandleInputStart=31817230040, AnimationStart=31817232815, PerformTraversalsStart=31817295573, DrawStart=31942559738, SyncQueued=31942985584, SyncStart=31946630123, IssueDrawCommandsStart=31946763292, SwapBuffers=31962248549, FrameCompleted=32100548387, DequeueBufferDuration=18279599, QueueBufferDuration=3826097, GpuCompleted=0, 
10-27 23:47:07.665   705  1173 I OpenGLRenderer: Davey! duration=814ms; Flags=1, IntendedVsync=31298564378, Vsync=31881897688, OldestInputEvent=9223372036854775807, NewestInputEvent=0, HandleInputStart=31897135198, AnimationStart=31897136600, PerformTraversalsStart=31897144746, DrawStart=32108441543, SyncQueued=32108764216, SyncStart=32110761625, IssueDrawCommandsStart=32110804726, SwapBuffers=32112299217, FrameCompleted=32114686167, DequeueBufferDuration=769508, QueueBufferDuration=174306, GpuCompleted=0, 
10-27 23:47:07.974   507   547 I ActivityManager: Start proc 2237:com.android.shell/2000 for broadcast {com.android.shell/com.android.shell.HeapDumpReceiver}
10-27 23:47:08.097   507  1947 I ActivityManager: Killing 1647:com.android.externalstorage/u0a68 (adj 985): empty #17
10-27 23:47:08.125   507   547 I ActivityManager: Start proc 2260:com.android.traceur/u0a87 for broadcast {com.android.traceur/com.android.traceur.Receiver}
10-27 23:47:08.145   507  1947 I ActivityManager: Killing 1883:com.android.localtransport/1000 (adj 975): empty #17
10-27 23:47:08.205   507   534 I ActivityManager: Finished processing BOOT_COMPLETED for u0
10-27 23:47:08.206   507   534 I ActivityManager: Killing 1806:com.android.provision/u0a118 (adj 985): empty #17
10-27 23:47:11.396   421  2160 I iorapd  : Perfetto TraceBuffer saved to file: /data/misc/iorapd/com.backrooms.gdx.debug/1/com.backrooms.gdx.android.AndroidLauncher/raw_traces/1793144831395937853.perfetto_trace.pb
10-27 23:47:22.766   507   545 I ActivityManager: Killing 1288:com.android.cellbroadcastreceiver/u0a64 (adj 985): empty #17
10-27 23:47:28.474   507   537 I ActivityManager: Killing 1967:com.android.calendar/u0a109 (adj 985): empty #17
10-27 23:47:28.477   507   537 I ActivityManager: Killing 1925:com.android.quicksearchbox/u0a102 (adj 985): empty #18
10-27 23:47:29.401  2326  2326 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-27 23:47:29.415  2326  2326 I AndroidRuntime: Using default boot image
10-27 23:47:29.416  2326  2326 I AndroidRuntime: Leaving lock profiling enabled
10-27 23:47:29.489  2326  2326 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-27 23:47:29.496  2326  2326 D AndroidRuntime: Shutting down VM
10-27 23:47:29.508  2326  2337 I AndroidRuntime: NOTE: attach of thread 'Binder:2326_1' failed
10-27 23:47:30.078  2155  2209 I BackroomsGL: world shader compiled
10-27 23:47:32.139  2155  2209 I Perf    : fps=6.1 worst=45 drawn=5/7 queue=2 build=508ms verts=15k cam=-0.5,1.9,1.5 ready=true drawn=5 noMesh=0 culled=1
10-27 23:47:33.482   507   534 I ActivityManager: Killing 1996:com.android.camera2/u0a110 (adj 975): empty #17
10-27 23:47:34.205  2155  2209 I Perf    : fps=3.9 worst=4 drawn=11/18 queue=2 build=39ms verts=34k cam=-0.5,1.9,1.5 ready=true drawn=11 noMesh=0 culled=6
10-27 23:47:36.230  2155  2209 I Perf    : fps=4.0 worst=4 drawn=11/27 queue=2 build=60ms verts=34k cam=-0.5,1.9,1.5 ready=true drawn=11 noMesh=0 culled=14
10-27 23:47:38.436  2155  2209 I Perf    : fps=4.0 worst=4 drawn=14/37 queue=0 build=65ms verts=42k cam=-0.5,1.9,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:47:40.505  2155  2209 I Perf    : fps=3.9 worst=4 drawn=14/37 queue=0 build=65ms verts=42k cam=-0.5,1.9,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:47:42.710  2155  2209 I Perf    : fps=4.1 worst=4 drawn=14/37 queue=0 build=65ms verts=42k cam=-0.5,1.9,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:47:44.847  2155  2209 I Perf    : fps=4.2 worst=4 drawn=14/37 queue=0 build=65ms verts=42k cam=-0.5,1.9,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:47:46.961  2155  2209 I Perf    : fps=3.8 worst=4 drawn=14/37 queue=0 build=65ms verts=42k cam=-0.5,1.9,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:47:49.152  2155  2209 I Perf    : fps=4.1 worst=4 drawn=14/37 queue=0 build=65ms verts=42k cam=-0.5,1.9,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:47:51.364  2155  2209 I Perf    : fps=4.1 worst=4 drawn=14/37 queue=0 build=65ms verts=42k cam=-0.5,1.9,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:47:53.446  2155  2209 I Perf    : fps=4.3 worst=4 drawn=14/37 queue=0 build=65ms verts=42k cam=-0.5,1.9,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:47:55.471  2155  2209 I Perf    : fps=4.0 worst=4 drawn=14/37 queue=0 build=65ms verts=42k cam=-0.5,1.9,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:47:57.492  2155  2209 I Perf    : fps=4.0 worst=4 drawn=14/37 queue=0 build=65ms verts=42k cam=-0.5,1.9,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:47:59.664  2155  2209 I Perf    : fps=4.1 worst=4 drawn=14/37 queue=0 build=65ms verts=42k cam=-0.5,1.9,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:48:00.335  2369  2369 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-27 23:48:00.365  2369  2369 I AndroidRuntime: Using default boot image
10-27 23:48:00.365  2369  2369 I AndroidRuntime: Leaving lock profiling enabled
10-27 23:48:00.535  2369  2369 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-27 23:48:00.849  2369  2369 D AndroidRuntime: Shutting down VM
10-27 23:48:01.681  2155  2209 I Perf    : fps=4.0 worst=4 drawn=14/37 queue=0 build=65ms verts=42k cam=-0.5,1.9,1.2 ready=true drawn=14 noMesh=0 culled=23
10-27 23:48:03.733  2155  2209 I Perf    : fps=4.4 worst=4 drawn=14/37 queue=0 build=65ms verts=42k cam=-0.5,1.9,1.2 ready=true drawn=14 noMesh=0 culled=23
10-27 23:48:05.840  2155  2209 I Perf    : fps=4.3 worst=5 drawn=14/37 queue=0 build=65ms verts=42k cam=-0.5,1.9,1.2 ready=true drawn=14 noMesh=0 culled=23
10-27 23:48:08.034  2155  2209 I Perf    : fps=4.1 worst=4 drawn=14/37 queue=0 build=65ms verts=42k cam=-0.5,1.9,1.2 ready=true drawn=14 noMesh=0 culled=23
10-27 23:48:10.190  2155  2209 I Perf    : fps=4.2 worst=4 drawn=14/37 queue=0 build=65ms verts=42k cam=-0.5,1.9,1.2 ready=true drawn=14 noMesh=0 culled=23
```
## Step log
```
_texture_npot GL_OES_rgb8_rgba8 GL_EXT_color_buffer_float GL_EXT_color_buffer_half_float GL_EXT_texture_format_BGRA8888 GL_APPLE_texture_format_BGRA8888 ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-27 23:47:11.396   421  2160 I iorapd  : Perfetto TraceBuffer saved to file: /data/misc/iorapd/com.backrooms.gdx.debug/1/com.backrooms.gdx.android.AndroidLauncher/raw_traces/1793144831395937853.perfetto_trace.pb
10-27 23:47:28.817  2324  2324 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-27 23:47:29.401  2326  2326 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-27 23:47:29.415  2326  2326 I AndroidRuntime: Using default boot image
10-27 23:47:29.416  2326  2326 I AndroidRuntime: Leaving lock profiling enabled
10-27 23:47:29.489  2326  2326 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-27 23:47:29.496  2326  2326 D AndroidRuntime: Shutting down VM
10-27 23:47:29.508  2326  2337 I AndroidRuntime: NOTE: attach of thread 'Binder:2326_1' failed
10-27 23:47:30.078  2155  2209 I BackroomsGL: world shader compiled
10-27 23:47:32.139  2155  2209 I Perf    : fps=6.1 worst=45 drawn=5/7 queue=2 build=508ms verts=15k cam=-0.5,1.9,1.5 ready=true drawn=5 noMesh=0 culled=1
10-27 23:47:34.205  2155  2209 I Perf    : fps=3.9 worst=4 drawn=11/18 queue=2 build=39ms verts=34k cam=-0.5,1.9,1.5 ready=true drawn=11 noMesh=0 culled=6
10-27 23:47:36.230  2155  2209 I Perf    : fps=4.0 worst=4 drawn=11/27 queue=2 build=60ms verts=34k cam=-0.5,1.9,1.5 ready=true drawn=11 noMesh=0 culled=14
10-27 23:47:38.436  2155  2209 I Perf    : fps=4.0 worst=4 drawn=14/37 queue=0 build=65ms verts=42k cam=-0.5,1.9,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:47:40.505  2155  2209 I Perf    : fps=3.9 worst=4 drawn=14/37 queue=0 build=65ms verts=42k cam=-0.5,1.9,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:47:42.710  2155  2209 I Perf    : fps=4.1 worst=4 drawn=14/37 queue=0 build=65ms verts=42k cam=-0.5,1.9,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:47:44.847  2155  2209 I Perf    : fps=4.2 worst=4 drawn=14/37 queue=0 build=65ms verts=42k cam=-0.5,1.9,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:47:46.961  2155  2209 I Perf    : fps=3.8 worst=4 drawn=14/37 queue=0 build=65ms verts=42k cam=-0.5,1.9,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:47:49.152  2155  2209 I Perf    : fps=4.1 worst=4 drawn=14/37 queue=0 build=65ms verts=42k cam=-0.5,1.9,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:47:51.364  2155  2209 I Perf    : fps=4.1 worst=4 drawn=14/37 queue=0 build=65ms verts=42k cam=-0.5,1.9,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:47:53.446  2155  2209 I Perf    : fps=4.3 worst=4 drawn=14/37 queue=0 build=65ms verts=42k cam=-0.5,1.9,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:47:55.471  2155  2209 I Perf    : fps=4.0 worst=4 drawn=14/37 queue=0 build=65ms verts=42k cam=-0.5,1.9,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:47:55.720  2350  2350 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-27 23:47:57.492  2155  2209 I Perf    : fps=4.0 worst=4 drawn=14/37 queue=0 build=65ms verts=42k cam=-0.5,1.9,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:47:59.664  2155  2209 I Perf    : fps=4.1 worst=4 drawn=14/37 queue=0 build=65ms verts=42k cam=-0.5,1.9,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:48:00.335  2369  2369 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-27 23:48:00.365  2369  2369 I AndroidRuntime: Using default boot image
10-27 23:48:00.365  2369  2369 I AndroidRuntime: Leaving lock profiling enabled
10-27 23:48:00.535  2369  2369 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-27 23:48:00.849  2369  2369 D AndroidRuntime: Shutting down VM
10-27 23:48:01.681  2155  2209 I Perf    : fps=4.0 worst=4 drawn=14/37 queue=0 build=65ms verts=42k cam=-0.5,1.9,1.2 ready=true drawn=14 noMesh=0 culled=23
10-27 23:48:03.733  2155  2209 I Perf    : fps=4.4 worst=4 drawn=14/37 queue=0 build=65ms verts=42k cam=-0.5,1.9,1.2 ready=true drawn=14 noMesh=0 culled=23
10-27 23:48:05.840  2155  2209 I Perf    : fps=4.3 worst=5 drawn=14/37 queue=0 build=65ms verts=42k cam=-0.5,1.9,1.2 ready=true drawn=14 noMesh=0 culled=23
10-27 23:48:07.131  2386  2386 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-27 23:48:08.034  2155  2209 I Perf    : fps=4.1 worst=4 drawn=14/37 queue=0 build=65ms verts=42k cam=-0.5,1.9,1.2 ready=true drawn=14 noMesh=0 culled=23
10-27 23:48:10.190  2155  2209 I Perf    : fps=4.2 worst=4 drawn=14/37 queue=0 build=65ms verts=42k cam=-0.5,1.9,1.2 ready=true drawn=14 noMesh=0 culled=23
+ '[' -z 2155 ']'
+ grep -qa 'FATAL EXCEPTION' emulator-shots/logcat.txt
+ echo 'OK: the app is alive and rendering'
OK: the app is alive and rendering
```
