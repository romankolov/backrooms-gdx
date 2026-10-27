# Emulator smoke test diagnostics

Commit: 2e7a782577f1d945feab5cb40af2ae6e1cdb3467
Outcome: success

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 27 23:37 /dev/kvm
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
INFO         | The active JSON Web Key Sets can be found here: /run/user/1001/avd/running/3137/jwks/a0fd6db1-e73f-4d17-9226-ebb02c144ea1/active.jwk
INFO         | Scanning /run/user/1001/avd/running/3137/jwks/a0fd6db1-e73f-4d17-9226-ebb02c144ea1 for jwk keys.
INFO         | Started GRPC server at 127.0.0.1:8554, security: Local, auth: +token
INFO         | Advertising in: /run/user/1001/avd/running/pid_3137.ini
INFO         | Setting display: 0 configuration to: 1080x1920, dpi: 420x420 
INFO         | setDisplayActiveConfig: id:0, 1080x1920
INFO         | emulatorSetupEnvironment: Environment scene is not required
USER_INFO    | Emulator is performing a full startup. This may take upto two minutes, or more.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
INFO         | Boot completed in 26506 ms
INFO         | Increasing screen off timeout, logcat buffer size to 2M.
```
## Logcat (filtered)
```
10-27 23:40:33.703   507   547 W ActivityManager: Slow operation: 151ms so far, now at startProcess: building log message
10-27 23:40:33.703   507   547 I ActivityManager: Start proc 2165:com.android.deskclock/u0a107 for broadcast {com.android.deskclock/com.android.deskclock.AlarmInitReceiver}
10-27 23:40:33.703   507   547 W ActivityManager: Slow operation: 151ms so far, now at startProcess: starting to update pids map
10-27 23:40:33.703   507   547 W ActivityManager: Slow operation: 151ms so far, now at startProcess: done updating pids map
10-27 23:40:34.178   716  1140 I OpenGLRenderer: Davey! duration=752ms; Flags=1, IntendedVsync=30872089020, Vsync=31055422346, OldestInputEvent=9223372036854775807, NewestInputEvent=0, HandleInputStart=31070256144, AnimationStart=31070257106, PerformTraversalsStart=31070343036, DrawStart=31095491185, SyncQueued=31286579965, SyncStart=31289706345, IssueDrawCommandsStart=31289762800, SwapBuffers=31359878145, FrameCompleted=31628095984, DequeueBufferDuration=184951382, QueueBufferDuration=305970, GpuCompleted=0, 
10-27 23:40:34.249   507  2102 I ActivityManager: Killing 1557:com.android.documentsui/u0a76 (adj 985): empty #17
10-27 23:40:34.269   716  1140 I OpenGLRenderer: Davey! duration=846ms; Flags=1, IntendedVsync=30872089020, Vsync=31055422346, OldestInputEvent=9223372036854775807, NewestInputEvent=0, HandleInputStart=31070256144, AnimationStart=31070257106, PerformTraversalsStart=31070343036, DrawStart=31713141767, SyncQueued=31713679941, SyncStart=31714741670, IssueDrawCommandsStart=31714811049, SwapBuffers=31716592352, FrameCompleted=31719260110, DequeueBufferDuration=900480, QueueBufferDuration=365822, GpuCompleted=0, 
10-27 23:40:34.323   507   547 I ActivityManager: Start proc 2194:com.android.dynsystem/1000 for broadcast {com.android.dynsystem/com.android.dynsystem.BootCompletedReceiver}
10-27 23:40:34.363   507  2102 I ActivityManager: Killing 1642:com.android.music/u0a110 (adj 985): empty #17
10-27 23:40:34.364   507   547 I ActivityManager: Start proc 2213:com.android.gallery3d/u0a112 for broadcast {com.android.gallery3d/com.android.camera.DisableCameraReceiver}
10-27 23:40:34.467  2105  2174 I OpenGLRenderer: Davey! duration=760ms; Flags=1, IntendedVsync=31155409436, Vsync=31355409428, OldestInputEvent=9223372036854775807, NewestInputEvent=0, HandleInputStart=31369455354, AnimationStart=31369458049, PerformTraversalsStart=31369543248, DrawStart=31763617633, SyncQueued=31764074335, SyncStart=31764694282, IssueDrawCommandsStart=31764776595, SwapBuffers=31787561586, FrameCompleted=31916857014, DequeueBufferDuration=13993129, QueueBufferDuration=188863, GpuCompleted=12, 
10-27 23:40:34.492   716  1140 I OpenGLRenderer: Davey! duration=738ms; Flags=1, IntendedVsync=31072064344, Vsync=31755397650, OldestInputEvent=9223372036854775807, NewestInputEvent=0, HandleInputStart=31769553187, AnimationStart=31769554149, PerformTraversalsStart=31769562555, DrawStart=31790459983, SyncQueued=31790695623, SyncStart=31921724295, IssueDrawCommandsStart=31921783445, SwapBuffers=31923132442, FrameCompleted=31941938549, DequeueBufferDuration=15880739, QueueBufferDuration=153596, GpuCompleted=0, 
10-27 23:40:34.518   507  1129 I ActivityManager: Killing 1600:com.android.keychain/1000 (adj 985): empty #17
10-27 23:40:34.578   507   547 I ActivityManager: Start proc 2233:com.android.nfc/1027 for broadcast {com.android.nfc/com.android.nfc.NfcBootCompletedReceiver}
10-27 23:40:34.607   716  1140 I OpenGLRenderer: Davey! duration=968ms; Flags=1, IntendedVsync=31072064344, Vsync=31755397650, OldestInputEvent=9223372036854775807, NewestInputEvent=0, HandleInputStart=31769553187, AnimationStart=31769554149, PerformTraversalsStart=31769562555, DrawStart=31945884812, SyncQueued=31945966724, SyncStart=31961971795, IssueDrawCommandsStart=31965532549, SwapBuffers=31997658745, FrameCompleted=32056615904, DequeueBufferDuration=122478, QueueBufferDuration=56964893, GpuCompleted=0, 
10-27 23:40:34.666   507   562 I ActivityManager: Killing 2233:com.android.nfc/1027 (adj 0): change com.android.nfc
10-27 23:40:34.771   507   547 W ActivityManager: Slow operation: 100ms so far, now at startProcess: done updating battery stats
10-27 23:40:34.771   507   547 W ActivityManager: Slow operation: 100ms so far, now at startProcess: building log message
10-27 23:40:34.771   507   547 I ActivityManager: Start proc 2253:com.android.onetimeinitializer/u0a96 for broadcast {com.android.onetimeinitializer/com.android.onetimeinitializer.OneTimeInitializerReceiver}
10-27 23:40:34.771   507   547 W ActivityManager: Slow operation: 100ms so far, now at startProcess: starting to update pids map
10-27 23:40:34.772   507   547 W ActivityManager: Slow operation: 101ms so far, now at startProcess: done updating pids map
10-27 23:40:34.831   507  1188 I ActivityManager: Killing 1895:com.android.providers.calendar/u0a66 (adj 985): empty #17
10-27 23:40:34.842   507   547 I ActivityManager: Start proc 2277:com.android.packageinstaller/u0a69 for broadcast {com.android.packageinstaller/com.android.packageinstaller.TemporaryFileManager}
10-27 23:40:35.151   507   547 W ActivityManager: Slow operation: 320ms so far, now at startProcess: done updating pids map
10-27 23:40:35.287   507  1012 I ActivityManager: Killing 1707:com.android.printspooler/u0a87 (adj 985): empty #17
10-27 23:40:35.353   507   547 I ActivityManager: Start proc 2304:com.android.providers.calendar/u0a66 for broadcast {com.android.providers.calendar/com.android.providers.calendar.CalendarReceiver}
10-27 23:40:35.533   507   878 I ActivityManager: Killing 1924:com.android.localtransport/1000 (adj 985): empty #17
10-27 23:40:35.697   507   547 W ActivityManager: Slow operation: 56ms so far, now at startProcess: returned from zygote!
10-27 23:40:35.697   507   547 W ActivityManager: Slow operation: 56ms so far, now at startProcess: done updating battery stats
10-27 23:40:35.697   507   547 W ActivityManager: Slow operation: 57ms so far, now at startProcess: building log message
10-27 23:40:35.697   507   547 I ActivityManager: Start proc 2329:com.android.shell/2000 for broadcast {com.android.shell/com.android.shell.HeapDumpReceiver}
10-27 23:40:35.697   507   547 W ActivityManager: Slow operation: 57ms so far, now at startProcess: starting to update pids map
10-27 23:40:35.697   507   547 W ActivityManager: Slow operation: 57ms so far, now at startProcess: done updating pids map
10-27 23:40:35.866   507   878 I ActivityManager: Killing 1801:com.android.provision/u0a116 (adj 985): empty #17
10-27 23:40:35.942   507   547 I ActivityManager: Start proc 2353:com.android.traceur/u0a91 for broadcast {com.android.traceur/com.android.traceur.Receiver}
10-27 23:40:36.110   507  1188 I ActivityManager: Finished processing BOOT_COMPLETED for u0
10-27 23:40:36.112   507  1188 I ActivityManager: Killing 1728:com.android.externalstorage/u0a68 (adj 985): empty #17
10-27 23:40:50.605   507   545 I ActivityManager: Killing 1294:com.android.cellbroadcastreceiver/u0a65 (adj 985): empty #17
10-27 23:40:53.296   507   537 I ActivityManager: Killing 2008:com.android.quicksearchbox/u0a105 (adj 985): empty #17
10-27 23:40:56.362  2406  2406 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-27 23:40:56.371  2406  2406 I AndroidRuntime: Using default boot image
10-27 23:40:56.371  2406  2406 I AndroidRuntime: Leaving lock profiling enabled
10-27 23:40:56.550  2406  2406 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-27 23:40:56.567  2406  2406 D AndroidRuntime: Shutting down VM
10-27 23:40:57.124  2105  2186 I BackroomsGL: world shader compiled
10-27 23:40:58.361   507   537 I ActivityManager: Killing 1440:android.process.acore/u0a63 (adj 985): empty #17
10-27 23:40:58.362   507   534 I ActivityManager: Killing 2045:com.android.calendar/u0a111 (adj 975): empty #17
10-27 23:41:05.655  2105  2186 I Perf    : fps=19.0 worst=50 drawn=18/34 queue=2 build=57ms verts=53k cam=-0.5,1.9,-0.5 ready=true drawn=18 noMesh=0 culled=15
10-27 23:41:13.631  2105  2186 I Perf    : fps=16.7 worst=16 drawn=18/37 queue=0 build=106ms verts=53k cam=-0.5,1.9,-0.5 ready=true drawn=18 noMesh=0 culled=19
10-27 23:41:21.617  2105  2186 I Perf    : fps=16.7 worst=16 drawn=18/37 queue=0 build=106ms verts=53k cam=-0.5,1.9,-0.5 ready=true drawn=18 noMesh=0 culled=19
10-27 23:41:27.788  2450  2450 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-27 23:41:27.804  2450  2450 I AndroidRuntime: Using default boot image
10-27 23:41:27.806  2450  2450 I AndroidRuntime: Leaving lock profiling enabled
10-27 23:41:28.081  2450  2450 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-27 23:41:28.436  2450  2450 D AndroidRuntime: Shutting down VM
10-27 23:41:29.826  2105  2186 I Perf    : fps=16.7 worst=16 drawn=18/37 queue=0 build=106ms verts=53k cam=-0.5,1.9,-0.8 ready=true drawn=18 noMesh=0 culled=19
10-27 23:41:35.296   507   547 I ActivityManager: Start proc 2468:android.process.acore/u0a63 for content provider {com.android.providers.contacts/com.android.providers.contacts.VoicemailContentProvider}
10-27 23:41:35.357  2468  2468 I ContactsPerf: VoicemailContentProvider.onCreate start
10-27 23:41:35.368  2468  2468 I ContactsPerf: VoicemailContentProvider.onCreate finish
10-27 23:41:38.153  2105  2186 I Perf    : fps=16.7 worst=16 drawn=18/37 queue=0 build=106ms verts=53k cam=-0.5,1.9,-0.8 ready=true drawn=18 noMesh=0 culled=19
```
## Step log
```
t time)
10-27 23:40:34.467  2105  2174 I OpenGLRenderer: Davey! duration=760ms; Flags=1, IntendedVsync=31155409436, Vsync=31355409428, OldestInputEvent=9223372036854775807, NewestInputEvent=0, HandleInputStart=31369455354, AnimationStart=31369458049, PerformTraversalsStart=31369543248, DrawStart=31763617633, SyncQueued=31764074335, SyncStart=31764694282, IssueDrawCommandsStart=31764776595, SwapBuffers=31787561586, FrameCompleted=31916857014, DequeueBufferDuration=13993129, QueueBufferDuration=188863, GpuCompleted=12, 
10-27 23:40:34.492   716  1140 I OpenGLRenderer: Davey! duration=738ms; Flags=1, IntendedVsync=31072064344, Vsync=31755397650, OldestInputEvent=9223372036854775807, NewestInputEvent=0, HandleInputStart=31769553187, AnimationStart=31769554149, PerformTraversalsStart=31769562555, DrawStart=31790459983, SyncQueued=31790695623, SyncStart=31921724295, IssueDrawCommandsStart=31921783445, SwapBuffers=31923132442, FrameCompleted=31941938549, DequeueBufferDuration=15880739, QueueBufferDuration=153596, GpuCompleted=0, 
10-27 23:40:34.607   716  1140 I OpenGLRenderer: Davey! duration=968ms; Flags=1, IntendedVsync=31072064344, Vsync=31755397650, OldestInputEvent=9223372036854775807, NewestInputEvent=0, HandleInputStart=31769553187, AnimationStart=31769554149, PerformTraversalsStart=31769562555, DrawStart=31945884812, SyncQueued=31945966724, SyncStart=31961971795, IssueDrawCommandsStart=31965532549, SwapBuffers=31997658745, FrameCompleted=32056615904, DequeueBufferDuration=122478, QueueBufferDuration=56964893, GpuCompleted=0, 
10-27 23:40:34.639  2105  2186 I AndroidGraphics: OGL renderer: Android Emulator OpenGL ES Translator (Google SwiftShader)
10-27 23:40:34.640  2105  2186 I AndroidGraphics: OGL version: OpenGL ES 3.0 (OpenGL ES 3.0 SwiftShader 4.0.0.1)
10-27 23:40:34.640  2105  2186 I AndroidGraphics: OGL extensions: GL_EXT_debug_marker GL_EXT_robustness GL_OES_EGL_sync GL_OES_EGL_image GL_OES_EGL_image_external GL_OES_depth24 GL_OES_depth32 GL_OES_element_index_uint GL_OES_texture_float GL_OES_texture_float_linear GL_OES_compressed_paletted_texture GL_OES_compressed_ETC1_RGB8_texture GL_OES_depth_texture GL_OES_texture_half_float GL_OES_texture_half_float_linear GL_OES_packed_depth_stencil GL_OES_vertex_half_float GL_OES_standard_derivatives GL_OES_texture_npot GL_OES_rgb8_rgba8 GL_EXT_color_buffer_float GL_EXT_color_buffer_half_float GL_EXT_texture_format_BGRA8888 GL_APPLE_texture_format_BGRA8888 ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-27 23:40:55.654  2404  2404 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-27 23:40:56.362  2406  2406 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-27 23:40:56.371  2406  2406 I AndroidRuntime: Using default boot image
10-27 23:40:56.371  2406  2406 I AndroidRuntime: Leaving lock profiling enabled
10-27 23:40:56.550  2406  2406 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-27 23:40:56.567  2406  2406 D AndroidRuntime: Shutting down VM
10-27 23:40:57.124  2105  2186 I BackroomsGL: world shader compiled
10-27 23:41:05.655  2105  2186 I Perf    : fps=19.0 worst=50 drawn=18/34 queue=2 build=57ms verts=53k cam=-0.5,1.9,-0.5 ready=true drawn=18 noMesh=0 culled=15
10-27 23:41:13.631  2105  2186 I Perf    : fps=16.7 worst=16 drawn=18/37 queue=0 build=106ms verts=53k cam=-0.5,1.9,-0.5 ready=true drawn=18 noMesh=0 culled=19
10-27 23:41:21.617  2105  2186 I Perf    : fps=16.7 worst=16 drawn=18/37 queue=0 build=106ms verts=53k cam=-0.5,1.9,-0.5 ready=true drawn=18 noMesh=0 culled=19
10-27 23:41:22.532  2429  2429 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-27 23:41:27.788  2450  2450 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-27 23:41:27.804  2450  2450 I AndroidRuntime: Using default boot image
10-27 23:41:27.806  2450  2450 I AndroidRuntime: Leaving lock profiling enabled
10-27 23:41:28.081  2450  2450 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-27 23:41:28.436  2450  2450 D AndroidRuntime: Shutting down VM
10-27 23:41:29.826  2105  2186 I Perf    : fps=16.7 worst=16 drawn=18/37 queue=0 build=106ms verts=53k cam=-0.5,1.9,-0.8 ready=true drawn=18 noMesh=0 culled=19
10-27 23:41:34.504  2466  2466 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-27 23:41:35.357  2468  2468 I ContactsPerf: VoicemailContentProvider.onCreate start
10-27 23:41:35.368  2468  2468 I ContactsPerf: VoicemailContentProvider.onCreate finish
10-27 23:41:38.153  2105  2186 I Perf    : fps=16.7 worst=16 drawn=18/37 queue=0 build=106ms verts=53k cam=-0.5,1.9,-0.8 ready=true drawn=18 noMesh=0 culled=19
+ '[' -z 2105 ']'
+ grep -qa 'FATAL EXCEPTION' emulator-shots/logcat.txt
+ echo 'OK: the app is alive and rendering'
OK: the app is alive and rendering
```
