# Emulator smoke test diagnostics

Commit: 39bf1bfb48323978cb4a352b4117d1368dbebc96
Outcome: success

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 27 23:22 /dev/kvm
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
INFO         | The active JSON Web Key Sets can be found here: /run/user/1001/avd/running/3090/jwks/67ce067b-655c-4304-824c-a6ecba49ea12/active.jwk
INFO         | Scanning /run/user/1001/avd/running/3090/jwks/67ce067b-655c-4304-824c-a6ecba49ea12 for jwk keys.
INFO         | Started GRPC server at 127.0.0.1:8554, security: Local, auth: +token
INFO         | Advertising in: /run/user/1001/avd/running/pid_3090.ini
INFO         | Setting display: 0 configuration to: 1080x1920, dpi: 420x420 
INFO         | setDisplayActiveConfig: id:0, 1080x1920
INFO         | emulatorSetupEnvironment: Environment scene is not required
USER_INFO    | Emulator is performing a full startup. This may take upto two minutes, or more.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
INFO         | Boot completed in 31171 ms
INFO         | Increasing screen off timeout, logcat buffer size to 2M.
```
## Logcat (filtered)
```
10-27 23:26:42.842   511   551 I ActivityManager: Start proc 2192:com.backrooms.gdx.debug/u0a130 for pre-top-activity {com.backrooms.gdx.debug/com.backrooms.gdx.android.AndroidLauncher}
10-27 23:26:42.842   511   551 W ActivityManager: Slow operation: 219ms so far, now at startProcess: starting to update pids map
10-27 23:26:42.848   511   551 W ActivityManager: Slow operation: 224ms so far, now at startProcess: done updating pids map
10-27 23:26:42.848   511   551 W ActivityManager: Slow operation: 102ms so far, now at startProcess: asking zygote to start proc
10-27 23:26:42.959   511   551 W ActivityManager: Slow operation: 213ms so far, now at startProcess: returned from zygote!
10-27 23:26:43.045   511   551 W ActivityManager: Slow operation: 299ms so far, now at startProcess: done updating battery stats
10-27 23:26:43.045   511   551 W ActivityManager: Slow operation: 299ms so far, now at startProcess: building log message
10-27 23:26:43.045   511   551 I ActivityManager: Start proc 2214:com.android.packageinstaller/u0a72 for broadcast {com.android.packageinstaller/com.android.packageinstaller.TemporaryFileManager}
10-27 23:26:43.045   511   551 W ActivityManager: Slow operation: 299ms so far, now at startProcess: starting to update pids map
10-27 23:26:43.045   511   551 W ActivityManager: Slow operation: 299ms so far, now at startProcess: done updating pids map
10-27 23:26:43.189   511   539 I ActivityManager: Killing 1632:com.android.printspooler/u0a86 (adj 985): empty #17
10-27 23:26:43.365   511   551 I ActivityManager: Start proc 2242:com.android.shell/2000 for broadcast {com.android.shell/com.android.shell.HeapDumpReceiver}
10-27 23:26:43.468   511   538 I ActivityManager: Killing 1713:com.android.externalstorage/u0a68 (adj 985): empty #17
10-27 23:26:43.546   511   551 W ActivityManager: Slow operation: 63ms so far, now at startProcess: returned from zygote!
10-27 23:26:43.599   511   551 W ActivityManager: Slow operation: 87ms so far, now at startProcess: done updating battery stats
10-27 23:26:43.599   511   551 W ActivityManager: Slow operation: 116ms so far, now at startProcess: building log message
10-27 23:26:43.599   511   551 I ActivityManager: Start proc 2264:com.android.traceur/u0a95 for broadcast {com.android.traceur/com.android.traceur.Receiver}
10-27 23:26:43.599   511   551 W ActivityManager: Slow operation: 116ms so far, now at startProcess: starting to update pids map
10-27 23:26:43.599   511   551 W ActivityManager: Slow operation: 116ms so far, now at startProcess: done updating pids map
10-27 23:26:43.908   511  1662 W ActivityManager: Slow operation: 84ms so far, now at getContentProviderImpl: after updateOomAdj
10-27 23:26:43.908   511  1662 W ActivityManager: Slow operation: 84ms so far, now at getContentProviderImpl: done!
10-27 23:26:43.911   511   539 I ActivityManager: Finished processing BOOT_COMPLETED for u0
10-27 23:26:43.913   511   539 I ActivityManager: Killing 1772:com.android.provision/u0a118 (adj 985): empty #17
10-27 23:26:43.916   717  1094 I OpenGLRenderer: Davey! duration=845ms; Flags=1, IntendedVsync=33501476073, Vsync=33668142733, OldestInputEvent=9223372036854775807, NewestInputEvent=0, HandleInputStart=33683108513, AnimationStart=33683108883, PerformTraversalsStart=33683192788, DrawStart=33698516035, SyncQueued=34335719146, SyncStart=34347322216, IssueDrawCommandsStart=34347377458, SwapBuffers=34349797383, FrameCompleted=34358751577, DequeueBufferDuration=899136, QueueBufferDuration=197684, GpuCompleted=4121974732908142624, 
10-27 23:26:44.016   717  1094 I OpenGLRenderer: Davey! duration=955ms; Flags=1, IntendedVsync=33501476073, Vsync=33668142733, OldestInputEvent=9223372036854775807, NewestInputEvent=0, HandleInputStart=33683108513, AnimationStart=33683108883, PerformTraversalsStart=33683192788, DrawStart=34441031506, SyncQueued=34441356239, SyncStart=34443188931, IssueDrawCommandsStart=34444475132, SwapBuffers=34452805670, FrameCompleted=34458764573, DequeueBufferDuration=733871, QueueBufferDuration=103394, GpuCompleted=0, 
10-27 23:26:44.076   717  1094 I OpenGLRenderer: Davey! duration=824ms; Flags=1, IntendedVsync=33684807069, Vsync=34468140371, OldestInputEvent=9223372036854775807, NewestInputEvent=0, HandleInputStart=34481736695, AnimationStart=34481737125, PerformTraversalsStart=34481745448, DrawStart=34486146446, SyncQueued=34486355207, SyncStart=34496359416, IssueDrawCommandsStart=34496388930, SwapBuffers=34500279130, FrameCompleted=34518880387, DequeueBufferDuration=7395518, QueueBufferDuration=115502, GpuCompleted=2314885530818453548, 
10-27 23:26:44.107   717  1094 I OpenGLRenderer: Davey! duration=858ms; Flags=1, IntendedVsync=33684807069, Vsync=34468140371, OldestInputEvent=9223372036854775807, NewestInputEvent=0, HandleInputStart=34481736695, AnimationStart=34481737125, PerformTraversalsStart=34481745448, DrawStart=34526942966, SyncQueued=34526981824, SyncStart=34533891580, IssueDrawCommandsStart=34533928845, SwapBuffers=34545642319, FrameCompleted=34549935287, DequeueBufferDuration=2346276, QueueBufferDuration=256662, GpuCompleted=0, 
10-27 23:26:44.243  2192  2238 I OpenGLRenderer: Davey! duration=967ms; Flags=1, IntendedVsync=33718144872, Vsync=34101478190, OldestInputEvent=9223372036854775807, NewestInputEvent=0, HandleInputStart=34112744487, AnimationStart=34112748023, PerformTraversalsStart=34112855653, DrawStart=34426814092, SyncQueued=34427219254, SyncStart=34427700589, IssueDrawCommandsStart=34452273329, SwapBuffers=34551758496, FrameCompleted=34685756126, DequeueBufferDuration=1142848, QueueBufferDuration=201039, GpuCompleted=0, 
10-27 23:26:44.426   511  1193 I ActivityManager: Killing 1902:com.android.quicksearchbox/u0a101 (adj 975): empty #17
10-27 23:26:47.643   435  2185 I iorapd  : Perfetto TraceBuffer saved to file: /data/misc/iorapd/com.backrooms.gdx.debug/1/com.backrooms.gdx.android.AndroidLauncher/raw_traces/1793143607643299435.perfetto_trace.pb
10-27 23:26:58.373   511   549 I ActivityManager: Killing 1294:com.android.cellbroadcastreceiver/u0a65 (adj 985): empty #17
10-27 23:27:03.694   511   541 I ActivityManager: Killing 1920:com.android.localtransport/1000 (adj 985): empty #17
10-27 23:27:05.931  2336  2336 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-27 23:27:05.954  2336  2336 I AndroidRuntime: Using default boot image
10-27 23:27:05.954  2336  2336 I AndroidRuntime: Leaving lock profiling enabled
10-27 23:27:06.190  2336  2336 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-27 23:27:06.217  2336  2336 D AndroidRuntime: Shutting down VM
10-27 23:27:08.735  2192  2240 I Perf    : fps=29.5 worst=60 drawn=0/12 queue=2 build=117ms verts=0k
10-27 23:27:08.738   511   541 I ActivityManager: Killing 1961:com.android.calendar/u0a108 (adj 985): empty #17
10-27 23:27:08.777   511   806 I ActivityManager: Killing 1996:com.android.camera2/u0a111 (adj 975): empty #17
10-27 23:27:10.740  2192  2240 I Perf    : fps=33.9 worst=151 drawn=0/28 queue=2 build=148ms verts=0k
10-27 23:27:12.748  2192  2240 I Perf    : fps=36.8 worst=145 drawn=0/37 queue=0 build=92ms verts=0k
10-27 23:27:14.749  2192  2240 I Perf    : fps=38.5 worst=194 drawn=0/37 queue=0 build=92ms verts=0k
10-27 23:27:16.752  2192  2240 I Perf    : fps=37.9 worst=138 drawn=0/37 queue=0 build=92ms verts=0k
10-27 23:27:18.752  2192  2240 I Perf    : fps=38.0 worst=266 drawn=0/37 queue=0 build=92ms verts=0k
10-27 23:27:20.771  2192  2240 I Perf    : fps=38.1 worst=206 drawn=0/37 queue=0 build=92ms verts=0k
10-27 23:27:22.778  2192  2240 I Perf    : fps=38.9 worst=172 drawn=0/37 queue=0 build=92ms verts=0k
10-27 23:27:24.784  2192  2240 I Perf    : fps=37.4 worst=100 drawn=0/37 queue=0 build=92ms verts=0k
10-27 23:27:26.807  2192  2240 I Perf    : fps=38.1 worst=193 drawn=0/37 queue=0 build=92ms verts=0k
10-27 23:27:28.816  2192  2240 I Perf    : fps=38.8 worst=120 drawn=0/37 queue=0 build=92ms verts=0k
10-27 23:27:30.842  2192  2240 I Perf    : fps=38.5 worst=186 drawn=0/37 queue=0 build=92ms verts=0k
10-27 23:27:32.450  2365  2365 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-27 23:27:32.475  2365  2365 I AndroidRuntime: Using default boot image
10-27 23:27:32.476  2365  2365 I AndroidRuntime: Leaving lock profiling enabled
10-27 23:27:32.617  2365  2365 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-27 23:27:32.845  2192  2240 I Perf    : fps=36.0 worst=155 drawn=0/37 queue=0 build=92ms verts=0k
10-27 23:27:32.951  2365  2365 D AndroidRuntime: Shutting down VM
10-27 23:27:34.851  2192  2240 I Perf    : fps=35.4 worst=136 drawn=0/37 queue=0 build=92ms verts=0k
10-27 23:27:36.860  2192  2240 I Perf    : fps=38.8 worst=243 drawn=0/37 queue=0 build=92ms verts=0k
10-27 23:27:38.905  2192  2240 I Perf    : fps=37.5 worst=100 drawn=0/37 queue=0 build=92ms verts=0k
```
## Step log
```
094 I OpenGLRenderer: Davey! duration=858ms; Flags=1, IntendedVsync=33684807069, Vsync=34468140371, OldestInputEvent=9223372036854775807, NewestInputEvent=0, HandleInputStart=34481736695, AnimationStart=34481737125, PerformTraversalsStart=34481745448, DrawStart=34526942966, SyncQueued=34526981824, SyncStart=34533891580, IssueDrawCommandsStart=34533928845, SwapBuffers=34545642319, FrameCompleted=34549935287, DequeueBufferDuration=2346276, QueueBufferDuration=256662, GpuCompleted=0, 
10-27 23:26:44.243  2192  2238 I OpenGLRenderer: Davey! duration=967ms; Flags=1, IntendedVsync=33718144872, Vsync=34101478190, OldestInputEvent=9223372036854775807, NewestInputEvent=0, HandleInputStart=34112744487, AnimationStart=34112748023, PerformTraversalsStart=34112855653, DrawStart=34426814092, SyncQueued=34427219254, SyncStart=34427700589, IssueDrawCommandsStart=34452273329, SwapBuffers=34551758496, FrameCompleted=34685756126, DequeueBufferDuration=1142848, QueueBufferDuration=201039, GpuCompleted=0, 
10-27 23:26:44.253  2192  2240 I AndroidGraphics: OGL extensions: GL_EXT_debug_marker GL_EXT_robustness GL_OES_EGL_sync GL_OES_EGL_image GL_OES_EGL_image_external GL_OES_depth24 GL_OES_depth32 GL_OES_element_index_uint GL_OES_texture_float GL_OES_texture_float_linear GL_OES_compressed_paletted_texture GL_OES_compressed_ETC1_RGB8_texture GL_OES_depth_texture GL_OES_texture_half_float GL_OES_texture_half_float_linear GL_OES_packed_depth_stencil GL_OES_vertex_half_float GL_OES_standard_derivatives GL_OES_texture_npot GL_OES_rgb8_rgba8 GL_EXT_color_buffer_float GL_EXT_color_buffer_half_float GL_EXT_texture_format_BGRA8888 GL_APPLE_texture_format_BGRA8888 ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-27 23:26:47.643   435  2185 I iorapd  : Perfetto TraceBuffer saved to file: /data/misc/iorapd/com.backrooms.gdx.debug/1/com.backrooms.gdx.android.AndroidLauncher/raw_traces/1793143607643299435.perfetto_trace.pb
10-27 23:27:05.274  2334  2334 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-27 23:27:05.931  2336  2336 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-27 23:27:05.954  2336  2336 I AndroidRuntime: Using default boot image
10-27 23:27:05.954  2336  2336 I AndroidRuntime: Leaving lock profiling enabled
10-27 23:27:06.190  2336  2336 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-27 23:27:06.217  2336  2336 D AndroidRuntime: Shutting down VM
10-27 23:27:08.735  2192  2240 I Perf    : fps=29.5 worst=60 drawn=0/12 queue=2 build=117ms verts=0k
10-27 23:27:10.740  2192  2240 I Perf    : fps=33.9 worst=151 drawn=0/28 queue=2 build=148ms verts=0k
10-27 23:27:12.748  2192  2240 I Perf    : fps=36.8 worst=145 drawn=0/37 queue=0 build=92ms verts=0k
10-27 23:27:14.749  2192  2240 I Perf    : fps=38.5 worst=194 drawn=0/37 queue=0 build=92ms verts=0k
10-27 23:27:16.752  2192  2240 I Perf    : fps=37.9 worst=138 drawn=0/37 queue=0 build=92ms verts=0k
10-27 23:27:18.752  2192  2240 I Perf    : fps=38.0 worst=266 drawn=0/37 queue=0 build=92ms verts=0k
10-27 23:27:20.771  2192  2240 I Perf    : fps=38.1 worst=206 drawn=0/37 queue=0 build=92ms verts=0k
10-27 23:27:22.778  2192  2240 I Perf    : fps=38.9 worst=172 drawn=0/37 queue=0 build=92ms verts=0k
10-27 23:27:24.784  2192  2240 I Perf    : fps=37.4 worst=100 drawn=0/37 queue=0 build=92ms verts=0k
10-27 23:27:26.807  2192  2240 I Perf    : fps=38.1 worst=193 drawn=0/37 queue=0 build=92ms verts=0k
10-27 23:27:28.816  2192  2240 I Perf    : fps=38.8 worst=120 drawn=0/37 queue=0 build=92ms verts=0k
10-27 23:27:30.842  2192  2240 I Perf    : fps=38.5 worst=186 drawn=0/37 queue=0 build=92ms verts=0k
10-27 23:27:31.907  2358  2358 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-27 23:27:32.450  2365  2365 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-27 23:27:32.475  2365  2365 I AndroidRuntime: Using default boot image
10-27 23:27:32.476  2365  2365 I AndroidRuntime: Leaving lock profiling enabled
10-27 23:27:32.617  2365  2365 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-27 23:27:32.845  2192  2240 I Perf    : fps=36.0 worst=155 drawn=0/37 queue=0 build=92ms verts=0k
10-27 23:27:32.951  2365  2365 D AndroidRuntime: Shutting down VM
10-27 23:27:34.851  2192  2240 I Perf    : fps=35.4 worst=136 drawn=0/37 queue=0 build=92ms verts=0k
10-27 23:27:36.860  2192  2240 I Perf    : fps=38.8 worst=243 drawn=0/37 queue=0 build=92ms verts=0k
10-27 23:27:38.670  2394  2394 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-27 23:27:38.905  2192  2240 I Perf    : fps=37.5 worst=100 drawn=0/37 queue=0 build=92ms verts=0k
+ '[' -z 2192 ']'
+ grep -qa 'FATAL EXCEPTION' emulator-shots/logcat.txt
+ echo 'OK: the app is alive and rendering'
OK: the app is alive and rendering
```
