# Emulator smoke test diagnostics

Commit: 9a389fa266b93bf337496c8b37c6924b479e1543
Outcome: success

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 28 07:48 /dev/kvm
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
INFO         | The active JSON Web Key Sets can be found here: /run/user/1001/avd/running/2745/jwks/71395b41-421f-4a79-b082-7ce2d11ce4f8/active.jwk
INFO         | Scanning /run/user/1001/avd/running/2745/jwks/71395b41-421f-4a79-b082-7ce2d11ce4f8 for jwk keys.
INFO         | Started GRPC server at 127.0.0.1:8554, security: Local, auth: +token
INFO         | Advertising in: /run/user/1001/avd/running/pid_2745.ini
INFO         | Setting display: 0 configuration to: 1080x1920, dpi: 420x420 
INFO         | setDisplayActiveConfig: id:0, 1080x1920
INFO         | emulatorSetupEnvironment: Environment scene is not required
USER_INFO    | Emulator is performing a full startup. This may take upto two minutes, or more.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
INFO         | Boot completed in 25776 ms
INFO         | Increasing screen off timeout, logcat buffer size to 2M.
```
## Logcat (filtered)
```
10-28 07:53:40.780   504   544 W ActivityManager: Slow operation: 53ms so far, now at startProcess: done updating pids map
10-28 07:53:40.857   504   638 I ActivityManager: Killing 1758:com.android.provision/u0a117 (adj 985): empty #17
10-28 07:53:40.921   504   544 I ActivityManager: Start proc 2278:com.android.traceur/u0a90 for broadcast {com.android.traceur/com.android.traceur.Receiver}
10-28 07:53:41.196   504  1302 I ActivityManager: Finished processing BOOT_COMPLETED for u0
10-28 07:53:41.197   504  1302 I ActivityManager: Killing 1862:com.android.quicksearchbox/u0a102 (adj 985): empty #17
10-28 07:53:41.636  2231  2273 I OpenGLRenderer: Davey! duration=773ms; Flags=1, IntendedVsync=27174254915, Vsync=27240921579, OldestInputEvent=9223372036854775807, NewestInputEvent=0, HandleInputStart=27257372077, AnimationStart=27257374531, PerformTraversalsStart=27257434290, DrawStart=27726490532, SyncQueued=27726939731, SyncStart=27727402470, IssueDrawCommandsStart=27727507938, SwapBuffers=27807885914, FrameCompleted=27948585112, DequeueBufferDuration=27010099, QueueBufferDuration=9180197, GpuCompleted=12, 
10-28 07:53:41.681   711  1145 I OpenGLRenderer: Davey! duration=808ms; Flags=1, IntendedVsync=27174254915, Vsync=27757588225, OldestInputEvent=9223372036854775807, NewestInputEvent=0, HandleInputStart=27762208836, AnimationStart=27762209156, PerformTraversalsStart=27762215606, DrawStart=27959549912, SyncQueued=27960009897, SyncStart=27970690763, IssueDrawCommandsStart=27970753346, SwapBuffers=27991850755, FrameCompleted=27993852947, DequeueBufferDuration=577860, QueueBufferDuration=83615, GpuCompleted=0, 
10-28 07:53:45.530   428  2226 I iorapd  : Perfetto TraceBuffer saved to file: /data/misc/iorapd/com.backrooms.gdx.debug/1/com.backrooms.gdx.android.AndroidLauncher/raw_traces/1793174025530292216.perfetto_trace.pb
10-28 07:53:56.491   504   534 I ActivityManager: Killing 1283:com.android.cellbroadcastreceiver/u0a66 (adj 985): empty #17
10-28 07:53:56.492   504  1185 I ActivityManager: Killing 1892:com.android.calendar/u0a109 (adj 975): empty #17
10-28 07:54:02.916  2359  2359 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 07:54:02.929  2359  2359 I AndroidRuntime: Using default boot image
10-28 07:54:02.929  2359  2359 I AndroidRuntime: Leaving lock profiling enabled
10-28 07:54:03.054  2359  2359 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 07:54:03.062  2359  2359 D AndroidRuntime: Shutting down VM
10-28 07:54:03.583  2231  2276 I BackroomsGL: world shader compiled
10-28 07:54:03.636  2231  2276 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 07:54:03.636  2231  2276 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 07:54:05.553  2231  2276 I Perf    : fps=6.9 worst=57 drawn=10/15 queue=2 build=41ms verts=25k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=10 noMesh=0 culled=3
10-28 07:54:07.600  2231  2276 I Perf    : fps=7.3 worst=8 drawn=18/37 queue=0 build=38ms verts=45k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 07:54:09.715  2231  2276 I Perf    : fps=7.6 worst=8 drawn=18/37 queue=0 build=38ms verts=45k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 07:54:11.752  2231  2276 I Perf    : fps=7.4 worst=8 drawn=18/37 queue=0 build=38ms verts=45k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 07:54:13.864  2231  2276 I Perf    : fps=7.6 worst=8 drawn=18/37 queue=0 build=38ms verts=45k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 07:54:15.964  2231  2276 I Perf    : fps=7.6 worst=8 drawn=18/37 queue=0 build=38ms verts=45k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 07:54:18.022  2231  2276 I Perf    : fps=7.3 worst=9 drawn=18/37 queue=0 build=38ms verts=45k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 07:54:20.059  2231  2276 I Perf    : fps=7.4 worst=8 drawn=18/37 queue=0 build=38ms verts=45k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 07:54:22.154  2231  2276 I Perf    : fps=7.2 worst=8 drawn=18/37 queue=0 build=38ms verts=45k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 07:54:24.153  2231  2276 I Perf    : fps=7.5 worst=9 drawn=18/37 queue=0 build=38ms verts=45k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 07:54:26.273  2231  2276 I Perf    : fps=7.1 worst=8 drawn=18/37 queue=0 build=38ms verts=45k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 07:54:28.360  2231  2276 I Perf    : fps=7.2 worst=8 drawn=18/37 queue=0 build=38ms verts=45k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 07:54:30.401  2231  2276 I Perf    : fps=6.9 worst=7 drawn=18/37 queue=0 build=38ms verts=45k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 07:54:32.472  2231  2276 I Perf    : fps=7.2 worst=8 drawn=18/37 queue=0 build=38ms verts=45k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 07:54:34.571  2395  2395 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 07:54:34.573  2395  2395 I AndroidRuntime: Using default boot image
10-28 07:54:34.573  2395  2395 I AndroidRuntime: Leaving lock profiling enabled
10-28 07:54:34.584  2231  2276 I Perf    : fps=6.6 worst=7 drawn=18/37 queue=0 build=38ms verts=45k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 07:54:34.638  2395  2395 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 07:54:34.723   504   504 I ActivityManager: Killing 1943:com.android.camera2/u0a110 (adj 985): empty #17
10-28 07:54:34.723   504   504 I ActivityManager: Killing 1917:com.android.localtransport/1000 (adj 985): empty #18
10-28 07:54:35.360  2395  2395 D AndroidRuntime: Shutting down VM
10-28 07:54:36.719  2231  2276 I Perf    : fps=6.1 worst=7 drawn=14/37 queue=0 build=38ms verts=35k cam=-1.5,1.6,-1.5 yaw=2.57 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:54:38.771  2231  2276 I Perf    : fps=5.8 worst=7 drawn=14/37 queue=0 build=38ms verts=35k cam=-1.5,1.6,-1.5 yaw=2.57 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:54:40.223  2424  2424 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 07:54:40.236  2424  2424 I AndroidRuntime: Using default boot image
10-28 07:54:40.236  2424  2424 I AndroidRuntime: Leaving lock profiling enabled
10-28 07:54:40.356  2424  2424 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 07:54:40.407  2424  2424 D AndroidRuntime: Shutting down VM
10-28 07:54:40.837  2231  2276 I Perf    : fps=5.8 worst=7 drawn=14/37 queue=0 build=38ms verts=35k cam=-1.5,1.6,-1.5 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-28 07:54:42.992  2231  2276 I Perf    : fps=6.0 worst=6 drawn=14/37 queue=0 build=38ms verts=35k cam=-1.5,1.6,-1.5 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-28 07:54:45.003  2231  2276 I Perf    : fps=6.0 worst=7 drawn=14/37 queue=0 build=38ms verts=35k cam=-1.5,1.6,-1.5 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-28 07:54:45.157  2441  2441 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 07:54:45.170  2441  2441 I AndroidRuntime: Using default boot image
10-28 07:54:45.170  2441  2441 I AndroidRuntime: Leaving lock profiling enabled
10-28 07:54:45.369  2441  2441 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 07:54:46.889  2441  2441 D AndroidRuntime: Shutting down VM
10-28 07:54:47.065  2231  2276 I Perf    : fps=5.8 worst=7 drawn=13/37 queue=0 build=38ms verts=33k cam=-2.1,1.6,-0.5 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 07:54:49.086  2231  2276 I Perf    : fps=5.5 worst=6 drawn=13/37 queue=0 build=38ms verts=33k cam=-2.2,1.6,-0.4 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 07:54:51.227  2231  2276 I Perf    : fps=5.6 worst=6 drawn=13/37 queue=0 build=38ms verts=33k cam=-2.2,1.6,-0.4 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 07:54:53.238  2231  2276 I Perf    : fps=5.5 worst=5 drawn=13/37 queue=0 build=38ms verts=33k cam=-2.2,1.6,-0.4 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 07:54:55.326  2231  2276 I Perf    : fps=5.7 worst=6 drawn=13/37 queue=0 build=38ms verts=33k cam=-2.2,1.6,-0.4 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
```
## Step log
```
=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 07:54:05.553  2231  2276 I Perf    : fps=6.9 worst=57 drawn=10/15 queue=2 build=41ms verts=25k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=10 noMesh=0 culled=3
10-28 07:54:07.600  2231  2276 I Perf    : fps=7.3 worst=8 drawn=18/37 queue=0 build=38ms verts=45k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 07:54:09.715  2231  2276 I Perf    : fps=7.6 worst=8 drawn=18/37 queue=0 build=38ms verts=45k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 07:54:11.752  2231  2276 I Perf    : fps=7.4 worst=8 drawn=18/37 queue=0 build=38ms verts=45k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 07:54:13.864  2231  2276 I Perf    : fps=7.6 worst=8 drawn=18/37 queue=0 build=38ms verts=45k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 07:54:15.964  2231  2276 I Perf    : fps=7.6 worst=8 drawn=18/37 queue=0 build=38ms verts=45k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 07:54:18.022  2231  2276 I Perf    : fps=7.3 worst=9 drawn=18/37 queue=0 build=38ms verts=45k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 07:54:20.059  2231  2276 I Perf    : fps=7.4 worst=8 drawn=18/37 queue=0 build=38ms verts=45k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 07:54:22.154  2231  2276 I Perf    : fps=7.2 worst=8 drawn=18/37 queue=0 build=38ms verts=45k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 07:54:24.153  2231  2276 I Perf    : fps=7.5 worst=9 drawn=18/37 queue=0 build=38ms verts=45k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 07:54:26.273  2231  2276 I Perf    : fps=7.1 worst=8 drawn=18/37 queue=0 build=38ms verts=45k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 07:54:28.360  2231  2276 I Perf    : fps=7.2 worst=8 drawn=18/37 queue=0 build=38ms verts=45k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 07:54:29.271  2382  2382 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 07:54:30.401  2231  2276 I Perf    : fps=6.9 worst=7 drawn=18/37 queue=0 build=38ms verts=45k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 07:54:32.472  2231  2276 I Perf    : fps=7.2 worst=8 drawn=18/37 queue=0 build=38ms verts=45k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 07:54:34.571  2395  2395 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 07:54:34.573  2395  2395 I AndroidRuntime: Using default boot image
10-28 07:54:34.573  2395  2395 I AndroidRuntime: Leaving lock profiling enabled
10-28 07:54:34.584  2231  2276 I Perf    : fps=6.6 worst=7 drawn=18/37 queue=0 build=38ms verts=45k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 07:54:34.638  2395  2395 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 07:54:35.360  2395  2395 D AndroidRuntime: Shutting down VM
10-28 07:54:36.719  2231  2276 I Perf    : fps=6.1 worst=7 drawn=14/37 queue=0 build=38ms verts=35k cam=-1.5,1.6,-1.5 yaw=2.57 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:54:38.771  2231  2276 I Perf    : fps=5.8 worst=7 drawn=14/37 queue=0 build=38ms verts=35k cam=-1.5,1.6,-1.5 yaw=2.57 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 07:54:40.223  2424  2424 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 07:54:40.236  2424  2424 I AndroidRuntime: Using default boot image
10-28 07:54:40.236  2424  2424 I AndroidRuntime: Leaving lock profiling enabled
10-28 07:54:40.356  2424  2424 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 07:54:40.407  2424  2424 D AndroidRuntime: Shutting down VM
10-28 07:54:40.837  2231  2276 I Perf    : fps=5.8 worst=7 drawn=14/37 queue=0 build=38ms verts=35k cam=-1.5,1.6,-1.5 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-28 07:54:42.992  2231  2276 I Perf    : fps=6.0 worst=6 drawn=14/37 queue=0 build=38ms verts=35k cam=-1.5,1.6,-1.5 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-28 07:54:45.003  2231  2276 I Perf    : fps=6.0 worst=7 drawn=14/37 queue=0 build=38ms verts=35k cam=-1.5,1.6,-1.5 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-28 07:54:45.157  2441  2441 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 07:54:45.170  2441  2441 I AndroidRuntime: Using default boot image
10-28 07:54:45.170  2441  2441 I AndroidRuntime: Leaving lock profiling enabled
10-28 07:54:45.369  2441  2441 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 07:54:46.889  2441  2441 D AndroidRuntime: Shutting down VM
10-28 07:54:47.065  2231  2276 I Perf    : fps=5.8 worst=7 drawn=13/37 queue=0 build=38ms verts=33k cam=-2.1,1.6,-0.5 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 07:54:49.086  2231  2276 I Perf    : fps=5.5 worst=6 drawn=13/37 queue=0 build=38ms verts=33k cam=-2.2,1.6,-0.4 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 07:54:51.227  2231  2276 I Perf    : fps=5.6 worst=6 drawn=13/37 queue=0 build=38ms verts=33k cam=-2.2,1.6,-0.4 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 07:54:52.337  2459  2459 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 07:54:53.238  2231  2276 I Perf    : fps=5.5 worst=5 drawn=13/37 queue=0 build=38ms verts=33k cam=-2.2,1.6,-0.4 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 07:54:55.326  2231  2276 I Perf    : fps=5.7 worst=6 drawn=13/37 queue=0 build=38ms verts=33k cam=-2.2,1.6,-0.4 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
+ '[' -z 2231 ']'
+ grep -qa 'FATAL EXCEPTION' emulator-shots/logcat.txt
+ '[' 0 '!=' 0 ']'
+ echo 'OK: the app is alive, rendering, and the touch controls work'
OK: the app is alive, rendering, and the touch controls work
```
