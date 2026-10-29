# Emulator smoke test diagnostics

Commit: b428192799b9771c612c709671a0ddd42d5b2418
Outcome: success

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 29 05:06 /dev/kvm
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
INFO         | The active JSON Web Key Sets can be found here: /run/user/1001/avd/running/2744/jwks/00fc6d4c-bf26-41b4-a689-1f00cb926dd5/active.jwk
INFO         | Scanning /run/user/1001/avd/running/2744/jwks/00fc6d4c-bf26-41b4-a689-1f00cb926dd5 for jwk keys.
INFO         | Started GRPC server at 127.0.0.1:8554, security: Local, auth: +token
INFO         | Advertising in: /run/user/1001/avd/running/pid_2744.ini
INFO         | Setting display: 0 configuration to: 1080x1920, dpi: 420x420 
INFO         | setDisplayActiveConfig: id:0, 1080x1920
INFO         | emulatorSetupEnvironment: Environment scene is not required
USER_INFO    | Emulator is performing a full startup. This may take upto two minutes, or more.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
INFO         | Boot completed in 26658 ms
INFO         | Increasing screen off timeout, logcat buffer size to 2M.
```
## Logcat (filtered)
```
10-29 05:09:17.638   507   535 I ActivityManager: Finished processing BOOT_COMPLETED for u0
10-29 05:09:17.639   507   535 I ActivityManager: Killing 1926:com.android.localtransport/1000 (adj 985): empty #17
10-29 05:09:17.853   715  1153 I OpenGLRenderer: Davey! duration=718ms; Flags=1, IntendedVsync=27467770059, Vsync=27467770059, OldestInputEvent=9223372036854775807, NewestInputEvent=0, HandleInputStart=27469021321, AnimationStart=27469021562, PerformTraversalsStart=27469052027, DrawStart=27484138484, SyncQueued=28155655970, SyncStart=28174704366, IssueDrawCommandsStart=28174779147, SwapBuffers=28196404304, FrameCompleted=28204927534, DequeueBufferDuration=4873575, QueueBufferDuration=728192, GpuCompleted=3976168487460614713, 
10-29 05:09:17.920   715  1153 I OpenGLRenderer: Davey! duration=759ms; Flags=1, IntendedVsync=27509446139, Vsync=28226112777, OldestInputEvent=9223372036854775807, NewestInputEvent=0, HandleInputStart=28227195296, AnimationStart=28227195807, PerformTraversalsStart=28227203057, DrawStart=28262801967, SyncQueued=28263030787, SyncStart=28266569267, IssueDrawCommandsStart=28269561034, SwapBuffers=28270307584, FrameCompleted=28272579501, DequeueBufferDuration=685068, QueueBufferDuration=148741, GpuCompleted=4121974732908142624, 
10-29 05:09:18.035   715  1153 I OpenGLRenderer: Davey! duration=864ms; Flags=1, IntendedVsync=27509446139, Vsync=28226112777, OldestInputEvent=9223372036854775807, NewestInputEvent=0, HandleInputStart=28227195296, AnimationStart=28227195807, PerformTraversalsStart=28227203057, DrawStart=28340074876, SyncQueued=28340573317, SyncStart=28353571088, IssueDrawCommandsStart=28353778236, SwapBuffers=28355991897, FrameCompleted=28386720356, DequeueBufferDuration=13830609, QueueBufferDuration=5808334, GpuCompleted=0, 
10-29 05:09:21.892   433  2238 I iorapd  : Perfetto TraceBuffer saved to file: /data/misc/iorapd/com.backrooms.gdx.debug/1/com.backrooms.gdx.android.AndroidLauncher/raw_traces/1793250561885455842.perfetto_trace.pb
10-29 05:09:33.605   507  1220 I ActivityManager: Killing 1883:com.android.quicksearchbox/u0a101 (adj 975): empty #17
10-29 05:09:39.294  2384  2384 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 05:09:39.302  2384  2384 I AndroidRuntime: Using default boot image
10-29 05:09:39.302  2384  2384 I AndroidRuntime: Leaving lock profiling enabled
10-29 05:09:39.446  2384  2384 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 05:09:39.449  2384  2384 D AndroidRuntime: Shutting down VM
10-29 05:09:39.456  2384  2395 I AndroidRuntime: NOTE: attach of thread 'Binder:2384_1' failed
10-29 05:09:40.018  2240  2308 I BackroomsGL: world shader compiled
10-29 05:09:40.079  2240  2308 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-29 05:09:40.079  2240  2308 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-29 05:09:41.988  2240  2308 I Perf    : fps=2.9 worst=5 drawn=5/9 queue=1 build=12ms verts=16k cam=-4.5,1.6,5.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=5 noMesh=0 culled=2
10-29 05:09:44.008  2240  2308 I Perf    : fps=4.4 worst=4 drawn=12/27 queue=2 build=65ms verts=39k cam=-4.5,1.6,5.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=12 noMesh=0 culled=13
10-29 05:09:46.010  2240  2308 I Perf    : fps=4.5 worst=4 drawn=15/37 queue=0 build=101ms verts=48k cam=-4.5,1.6,5.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=15 noMesh=0 culled=22
10-29 05:09:48.034  2240  2308 I Perf    : fps=4.4 worst=4 drawn=15/37 queue=0 build=101ms verts=48k cam=-4.5,1.6,5.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=15 noMesh=0 culled=22
10-29 05:09:50.061  2240  2308 I Perf    : fps=4.4 worst=4 drawn=15/37 queue=0 build=101ms verts=48k cam=-4.5,1.6,5.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=15 noMesh=0 culled=22
10-29 05:09:52.217  2240  2308 I Perf    : fps=4.6 worst=5 drawn=15/37 queue=0 build=101ms verts=48k cam=-4.5,1.6,5.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=15 noMesh=0 culled=22
10-29 05:09:54.257  2240  2308 I Perf    : fps=4.4 worst=4 drawn=15/37 queue=0 build=101ms verts=48k cam=-4.5,1.6,5.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=15 noMesh=0 culled=22
10-29 05:09:56.454  2240  2308 I Perf    : fps=4.6 worst=4 drawn=15/37 queue=0 build=101ms verts=48k cam=-4.5,1.6,5.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=15 noMesh=0 culled=22
10-29 05:09:58.630  2240  2308 I Perf    : fps=4.6 worst=5 drawn=15/37 queue=0 build=101ms verts=48k cam=-4.5,1.6,5.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=15 noMesh=0 culled=22
10-29 05:10:00.658  2240  2308 I Perf    : fps=4.4 worst=5 drawn=15/37 queue=0 build=101ms verts=48k cam=-4.5,1.6,5.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=15 noMesh=0 culled=22
10-29 05:10:02.883  2240  2308 I Perf    : fps=4.5 worst=5 drawn=15/37 queue=0 build=101ms verts=48k cam=-4.5,1.6,5.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=15 noMesh=0 culled=22
10-29 05:10:05.074  2240  2308 I Perf    : fps=4.6 worst=5 drawn=15/37 queue=0 build=101ms verts=48k cam=-4.5,1.6,5.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=15 noMesh=0 culled=22
10-29 05:10:07.241  2240  2308 I Perf    : fps=4.2 worst=4 drawn=15/37 queue=0 build=101ms verts=48k cam=-4.5,1.6,5.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=15 noMesh=0 culled=22
10-29 05:10:09.439  2240  2308 I Perf    : fps=4.1 worst=4 drawn=15/37 queue=0 build=101ms verts=48k cam=-4.5,1.6,5.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=15 noMesh=0 culled=22
10-29 05:10:11.422   507   507 I ActivityManager: Killing 2005:com.android.camera2/u0a109 (adj 985): empty #17
10-29 05:10:11.427   507   507 I ActivityManager: Killing 1978:com.android.calendar/u0a110 (adj 985): empty #18
10-29 05:10:11.427   507   507 I ActivityManager: Killing 1289:com.android.cellbroadcastreceiver/u0a65 (adj 995): empty #19
10-29 05:10:11.596  2240  2308 I Perf    : fps=4.2 worst=4 drawn=15/37 queue=0 build=101ms verts=48k cam=-4.5,1.6,5.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=15 noMesh=0 culled=22
10-29 05:10:11.881  2430  2430 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 05:10:11.894  2430  2430 I AndroidRuntime: Using default boot image
10-29 05:10:11.894  2430  2430 I AndroidRuntime: Leaving lock profiling enabled
10-29 05:10:11.972  2430  2430 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 05:10:12.789  2430  2430 D AndroidRuntime: Shutting down VM
10-29 05:10:13.734  2240  2308 I Perf    : fps=4.2 worst=4 drawn=18/37 queue=0 build=101ms verts=57k cam=-4.5,1.6,5.5 yaw=2.71 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 05:10:15.907  2240  2308 I Perf    : fps=4.6 worst=4 drawn=18/37 queue=0 build=101ms verts=57k cam=-4.5,1.6,5.5 yaw=2.71 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 05:10:17.579  2446  2446 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 05:10:17.593  2446  2446 I AndroidRuntime: Using default boot image
10-29 05:10:17.594  2446  2446 I AndroidRuntime: Leaving lock profiling enabled
10-29 05:10:17.747  2446  2446 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 05:10:17.782  2446  2446 D AndroidRuntime: Shutting down VM
10-29 05:10:17.950  2240  2308 I Perf    : fps=4.4 worst=4 drawn=18/37 queue=0 build=101ms verts=57k cam=-4.5,1.6,5.5 yaw=2.71 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-29 05:10:20.039  2240  2308 I Perf    : fps=4.3 worst=4 drawn=18/37 queue=0 build=101ms verts=57k cam=-4.5,1.6,5.5 yaw=2.71 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-29 05:10:22.249  2240  2308 I Perf    : fps=4.5 worst=5 drawn=18/37 queue=0 build=101ms verts=57k cam=-4.5,1.6,5.5 yaw=2.71 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-29 05:10:22.684  2463  2463 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 05:10:22.693  2463  2463 I AndroidRuntime: Using default boot image
10-29 05:10:22.694  2463  2463 I AndroidRuntime: Leaving lock profiling enabled
10-29 05:10:22.826  2463  2463 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 05:10:24.362  2463  2463 D AndroidRuntime: Shutting down VM
10-29 05:10:24.428  2240  2308 I Perf    : fps=4.6 worst=5 drawn=18/37 queue=0 build=101ms verts=57k cam=-4.9,1.6,6.4 yaw=2.71 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-29 05:10:26.591  2240  2308 I Perf    : fps=4.6 worst=5 drawn=18/37 queue=0 build=101ms verts=57k cam=-5.0,1.6,6.6 yaw=2.71 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-29 05:10:28.677  2240  2308 I Perf    : fps=4.8 worst=5 drawn=18/37 queue=0 build=101ms verts=57k cam=-5.0,1.6,6.6 yaw=2.71 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-29 05:10:30.809  2240  2308 I Perf    : fps=4.7 worst=5 drawn=18/37 queue=0 build=101ms verts=57k cam=-5.0,1.6,6.6 yaw=2.71 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-29 05:10:33.026  2240  2308 I Perf    : fps=4.5 worst=4 drawn=18/37 queue=0 build=101ms verts=57k cam=-5.0,1.6,6.6 yaw=2.71 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-29 05:10:35.188  2240  2308 I Perf    : fps=4.7 worst=4 drawn=18/37 queue=0 build=101ms verts=57k cam=-5.0,1.6,6.6 yaw=2.71 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
```
## Step log
```
verts=48k cam=-4.5,1.6,5.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=15 noMesh=0 culled=22
10-29 05:09:48.034  2240  2308 I Perf    : fps=4.4 worst=4 drawn=15/37 queue=0 build=101ms verts=48k cam=-4.5,1.6,5.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=15 noMesh=0 culled=22
10-29 05:09:50.061  2240  2308 I Perf    : fps=4.4 worst=4 drawn=15/37 queue=0 build=101ms verts=48k cam=-4.5,1.6,5.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=15 noMesh=0 culled=22
10-29 05:09:52.217  2240  2308 I Perf    : fps=4.6 worst=5 drawn=15/37 queue=0 build=101ms verts=48k cam=-4.5,1.6,5.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=15 noMesh=0 culled=22
10-29 05:09:54.257  2240  2308 I Perf    : fps=4.4 worst=4 drawn=15/37 queue=0 build=101ms verts=48k cam=-4.5,1.6,5.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=15 noMesh=0 culled=22
10-29 05:09:56.454  2240  2308 I Perf    : fps=4.6 worst=4 drawn=15/37 queue=0 build=101ms verts=48k cam=-4.5,1.6,5.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=15 noMesh=0 culled=22
10-29 05:09:58.630  2240  2308 I Perf    : fps=4.6 worst=5 drawn=15/37 queue=0 build=101ms verts=48k cam=-4.5,1.6,5.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=15 noMesh=0 culled=22
10-29 05:10:00.658  2240  2308 I Perf    : fps=4.4 worst=5 drawn=15/37 queue=0 build=101ms verts=48k cam=-4.5,1.6,5.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=15 noMesh=0 culled=22
10-29 05:10:02.883  2240  2308 I Perf    : fps=4.5 worst=5 drawn=15/37 queue=0 build=101ms verts=48k cam=-4.5,1.6,5.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=15 noMesh=0 culled=22
10-29 05:10:05.074  2240  2308 I Perf    : fps=4.6 worst=5 drawn=15/37 queue=0 build=101ms verts=48k cam=-4.5,1.6,5.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=15 noMesh=0 culled=22
10-29 05:10:05.334  2406  2406 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-29 05:10:07.241  2240  2308 I Perf    : fps=4.2 worst=4 drawn=15/37 queue=0 build=101ms verts=48k cam=-4.5,1.6,5.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=15 noMesh=0 culled=22
10-29 05:10:09.439  2240  2308 I Perf    : fps=4.1 worst=4 drawn=15/37 queue=0 build=101ms verts=48k cam=-4.5,1.6,5.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=15 noMesh=0 culled=22
10-29 05:10:09.778  2413  2413 W dex2oat32: Verification of void com.badlogic.gdx.graphics.g3d.shaders.DefaultShader$Setters$ACubemap.set(com.badlogic.gdx.graphics.g3d.shaders.BaseShader, int, com.badlogic.gdx.graphics.g3d.Renderable, com.badlogic.gdx.graphics.g3d.Attributes) took 104.388ms (1733.91 bytecodes/s) (3664B approximate peak alloc)
10-29 05:10:11.596  2240  2308 I Perf    : fps=4.2 worst=4 drawn=15/37 queue=0 build=101ms verts=48k cam=-4.5,1.6,5.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=15 noMesh=0 culled=22
10-29 05:10:11.881  2430  2430 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 05:10:11.894  2430  2430 I AndroidRuntime: Using default boot image
10-29 05:10:11.894  2430  2430 I AndroidRuntime: Leaving lock profiling enabled
10-29 05:10:11.972  2430  2430 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 05:10:12.789  2430  2430 D AndroidRuntime: Shutting down VM
10-29 05:10:13.734  2240  2308 I Perf    : fps=4.2 worst=4 drawn=18/37 queue=0 build=101ms verts=57k cam=-4.5,1.6,5.5 yaw=2.71 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 05:10:15.907  2240  2308 I Perf    : fps=4.6 worst=4 drawn=18/37 queue=0 build=101ms verts=57k cam=-4.5,1.6,5.5 yaw=2.71 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-29 05:10:17.579  2446  2446 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 05:10:17.593  2446  2446 I AndroidRuntime: Using default boot image
10-29 05:10:17.594  2446  2446 I AndroidRuntime: Leaving lock profiling enabled
10-29 05:10:17.747  2446  2446 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 05:10:17.782  2446  2446 D AndroidRuntime: Shutting down VM
10-29 05:10:17.950  2240  2308 I Perf    : fps=4.4 worst=4 drawn=18/37 queue=0 build=101ms verts=57k cam=-4.5,1.6,5.5 yaw=2.71 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-29 05:10:20.039  2240  2308 I Perf    : fps=4.3 worst=4 drawn=18/37 queue=0 build=101ms verts=57k cam=-4.5,1.6,5.5 yaw=2.71 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-29 05:10:22.249  2240  2308 I Perf    : fps=4.5 worst=5 drawn=18/37 queue=0 build=101ms verts=57k cam=-4.5,1.6,5.5 yaw=2.71 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-29 05:10:22.684  2463  2463 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 05:10:22.693  2463  2463 I AndroidRuntime: Using default boot image
10-29 05:10:22.694  2463  2463 I AndroidRuntime: Leaving lock profiling enabled
10-29 05:10:22.826  2463  2463 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 05:10:24.362  2463  2463 D AndroidRuntime: Shutting down VM
10-29 05:10:24.428  2240  2308 I Perf    : fps=4.6 worst=5 drawn=18/37 queue=0 build=101ms verts=57k cam=-4.9,1.6,6.4 yaw=2.71 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-29 05:10:26.591  2240  2308 I Perf    : fps=4.6 worst=5 drawn=18/37 queue=0 build=101ms verts=57k cam=-5.0,1.6,6.6 yaw=2.71 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-29 05:10:28.677  2240  2308 I Perf    : fps=4.8 worst=5 drawn=18/37 queue=0 build=101ms verts=57k cam=-5.0,1.6,6.6 yaw=2.71 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-29 05:10:29.505  2481  2481 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-29 05:10:30.809  2240  2308 I Perf    : fps=4.7 worst=5 drawn=18/37 queue=0 build=101ms verts=57k cam=-5.0,1.6,6.6 yaw=2.71 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-29 05:10:33.026  2240  2308 I Perf    : fps=4.5 worst=4 drawn=18/37 queue=0 build=101ms verts=57k cam=-5.0,1.6,6.6 yaw=2.71 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-29 05:10:35.188  2240  2308 I Perf    : fps=4.7 worst=4 drawn=18/37 queue=0 build=101ms verts=57k cam=-5.0,1.6,6.6 yaw=2.71 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
+ '[' -z 2240 ']'
+ grep -qa 'FATAL EXCEPTION' emulator-shots/logcat.txt
+ '[' 0 '!=' 0 ']'
+ echo 'OK: the app is alive, rendering, and the touch controls work'
OK: the app is alive, rendering, and the touch controls work
```
