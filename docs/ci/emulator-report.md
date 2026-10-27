# Emulator smoke test diagnostics

Commit: 4d1334e4494232e5c0ab1e6b11522f4e7c1f8b1f
Outcome: success

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 27 23:50 /dev/kvm
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
INFO         | The active JSON Web Key Sets can be found here: /run/user/1001/avd/running/2971/jwks/6a8287a5-5f0d-48ef-93d3-e39057c4f13c/active.jwk
INFO         | Scanning /run/user/1001/avd/running/2971/jwks/6a8287a5-5f0d-48ef-93d3-e39057c4f13c for jwk keys.
INFO         | Started GRPC server at 127.0.0.1:8554, security: Local, auth: +token
INFO         | Advertising in: /run/user/1001/avd/running/pid_2971.ini
INFO         | Setting display: 0 configuration to: 1080x1920, dpi: 420x420 
INFO         | setDisplayActiveConfig: id:0, 1080x1920
INFO         | emulatorSetupEnvironment: Environment scene is not required
USER_INFO    | Emulator is performing a full startup. This may take upto two minutes, or more.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
INFO         | Boot completed in 31249 ms
INFO         | Increasing screen off timeout, logcat buffer size to 2M.
```
## Logcat (filtered)
```
10-27 23:53:42.819   506   547 W ActivityManager: Slow operation: 75ms so far, now at startProcess: building log message
10-27 23:53:42.819   506   547 I ActivityManager: Start proc 2270:com.android.onetimeinitializer/u0a96 for broadcast {com.android.onetimeinitializer/com.android.onetimeinitializer.OneTimeInitializerReceiver}
10-27 23:53:42.819   506   547 W ActivityManager: Slow operation: 75ms so far, now at startProcess: starting to update pids map
10-27 23:53:42.819   506   547 W ActivityManager: Slow operation: 75ms so far, now at startProcess: done updating pids map
10-27 23:53:42.827   715  1140 I OpenGLRenderer: Davey! duration=969ms; Flags=1, IntendedVsync=34310361037, Vsync=34427027699, OldestInputEvent=9223372036854775807, NewestInputEvent=0, HandleInputStart=34441112846, AnimationStart=34441113537, PerformTraversalsStart=34441250561, DrawStart=35265322512, SyncQueued=35265695328, SyncStart=35279727961, IssueDrawCommandsStart=35279823273, SwapBuffers=35291476962, FrameCompleted=35294006240, DequeueBufferDuration=1739861, QueueBufferDuration=115052, GpuCompleted=0, 
10-27 23:53:42.920   506  2210 I ActivityManager: Killing 1592:com.android.printspooler/u0a87 (adj 985): empty #17
10-27 23:53:42.940   715  1140 I OpenGLRenderer: Davey! duration=938ms; Flags=1, IntendedVsync=34443708750, Vsync=35343708714, OldestInputEvent=9223372036854775807, NewestInputEvent=0, HandleInputStart=35357202483, AnimationStart=35357203775, PerformTraversalsStart=35357213419, DrawStart=35359026570, SyncQueued=35359215321, SyncStart=35384084176, IssueDrawCommandsStart=35384450612, SwapBuffers=35385184405, FrameCompleted=35406712333, DequeueBufferDuration=7736614, QueueBufferDuration=11229458, GpuCompleted=0, 
10-27 23:53:42.942   506   547 I ActivityManager: Start proc 2293:com.android.packageinstaller/u0a70 for broadcast {com.android.packageinstaller/com.android.packageinstaller.TemporaryFileManager}
10-27 23:53:42.955   715  1140 I OpenGLRenderer: Davey! duration=977ms; Flags=1, IntendedVsync=34443708750, Vsync=35343708714, OldestInputEvent=9223372036854775807, NewestInputEvent=0, HandleInputStart=35357202483, AnimationStart=35357203775, PerformTraversalsStart=35357213419, DrawStart=35407052450, SyncQueued=35407155363, SyncStart=35407682829, IssueDrawCommandsStart=35407751762, SwapBuffers=35411319326, FrameCompleted=35421579009, DequeueBufferDuration=9453081, QueueBufferDuration=129814, GpuCompleted=0, 
10-27 23:53:43.039   506  2210 I ActivityManager: Killing 1681:com.android.provision/u0a118 (adj 985): empty #17
10-27 23:53:43.726   506   547 I ActivityManager: Start proc 2323:com.android.shell/2000 for broadcast {com.android.shell/com.android.shell.HeapDumpReceiver}
10-27 23:53:43.867   506   534 I ActivityManager: Killing 1702:com.android.externalstorage/u0a68 (adj 985): empty #17
10-27 23:53:43.918   506   534 I ActivityManager: Start proc 2348:com.android.traceur/u0a94 for broadcast {com.android.traceur/com.android.traceur.Receiver}
10-27 23:53:44.080   506   534 I ActivityManager: Finished processing BOOT_COMPLETED for u0
10-27 23:53:44.080   506   534 I ActivityManager: Killing 1942:com.android.localtransport/1000 (adj 985): empty #17
10-27 23:53:58.542   506   545 I ActivityManager: Killing 1284:com.android.cellbroadcastreceiver/u0a65 (adj 985): empty #17
10-27 23:54:01.377   506   537 I ActivityManager: Killing 1906:com.android.quicksearchbox/u0a102 (adj 985): empty #17
10-27 23:54:01.880   506  2210 I ActivityManager: Killing 1667:android.process.acore/u0a63 (adj 985): empty #17
10-27 23:54:04.799  2405  2405 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-27 23:54:04.813  2405  2405 I AndroidRuntime: Using default boot image
10-27 23:54:04.813  2405  2405 I AndroidRuntime: Leaving lock profiling enabled
10-27 23:54:04.964  2405  2405 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-27 23:54:04.979  2405  2405 D AndroidRuntime: Shutting down VM
10-27 23:54:04.993  2405  2416 I AndroidRuntime: NOTE: attach of thread 'Binder:2405_1' failed
10-27 23:54:05.554  2174  2226 I BackroomsGL: world shader compiled
10-27 23:54:06.257   506   808 I ActivityManager: Killing 2028:com.android.calendar/u0a107 (adj 975): empty #17
10-27 23:54:07.509  2174  2226 I Perf    : fps=5.8 worst=53 drawn=5/5 queue=2 build=126ms verts=15k cam=1.5,2.1,1.5 ready=true drawn=5 noMesh=0 culled=0
10-27 23:54:09.739  2174  2226 I Perf    : fps=3.6 worst=4 drawn=10/14 queue=2 build=115ms verts=30k cam=1.5,2.1,1.5 ready=true drawn=10 noMesh=0 culled=4
10-27 23:54:11.973  2174  2226 I Perf    : fps=3.6 worst=3 drawn=11/21 queue=2 build=80ms verts=33k cam=1.5,2.1,1.5 ready=true drawn=11 noMesh=0 culled=10
10-27 23:54:14.176  2174  2226 I Perf    : fps=3.6 worst=3 drawn=14/33 queue=2 build=84ms verts=42k cam=1.5,2.1,1.5 ready=true drawn=14 noMesh=0 culled=17
10-27 23:54:16.404  2174  2226 I Perf    : fps=3.6 worst=3 drawn=14/37 queue=0 build=205ms verts=42k cam=1.5,2.1,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:54:18.655  2174  2226 I Perf    : fps=3.6 worst=3 drawn=14/37 queue=0 build=205ms verts=42k cam=1.5,2.1,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:54:20.834  2174  2226 I Perf    : fps=3.7 worst=3 drawn=14/37 queue=0 build=205ms verts=42k cam=1.5,2.1,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:54:23.058  2174  2226 I Perf    : fps=3.6 worst=3 drawn=14/37 queue=0 build=205ms verts=42k cam=1.5,2.1,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:54:25.251  2174  2226 I Perf    : fps=3.6 worst=3 drawn=14/37 queue=0 build=205ms verts=42k cam=1.5,2.1,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:54:27.432  2174  2226 I Perf    : fps=3.7 worst=3 drawn=14/37 queue=0 build=205ms verts=42k cam=1.5,2.1,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:54:29.678  2174  2226 I Perf    : fps=3.6 worst=3 drawn=14/37 queue=0 build=205ms verts=42k cam=1.5,2.1,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:54:31.887  2174  2226 I Perf    : fps=3.6 worst=4 drawn=14/37 queue=0 build=205ms verts=42k cam=1.5,2.1,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:54:34.115  2174  2226 I Perf    : fps=3.6 worst=4 drawn=14/37 queue=0 build=205ms verts=42k cam=1.5,2.1,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:54:36.151  2174  2226 I Perf    : fps=3.4 worst=3 drawn=14/37 queue=0 build=205ms verts=42k cam=1.5,2.1,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:54:37.424  2448  2448 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-27 23:54:37.437  2448  2448 I AndroidRuntime: Using default boot image
10-27 23:54:37.437  2448  2448 I AndroidRuntime: Leaving lock profiling enabled
10-27 23:54:37.608  2448  2448 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-27 23:54:38.083  2448  2448 D AndroidRuntime: Shutting down VM
10-27 23:54:38.364  2174  2226 I Perf    : fps=3.6 worst=4 drawn=14/37 queue=0 build=205ms verts=42k cam=1.5,2.1,1.4 ready=true drawn=14 noMesh=0 culled=23
10-27 23:54:40.388  2174  2226 I Perf    : fps=3.5 worst=3 drawn=14/37 queue=0 build=205ms verts=42k cam=1.5,2.1,1.4 ready=true drawn=14 noMesh=0 culled=23
10-27 23:54:42.612  2174  2226 I Perf    : fps=3.6 worst=3 drawn=14/37 queue=0 build=205ms verts=42k cam=1.5,2.1,1.4 ready=true drawn=14 noMesh=0 culled=23
10-27 23:54:44.179   506   547 W ActivityManager: Slow operation: 116ms so far, now at startProcess: asking zygote to start proc
10-27 23:54:44.268   506   547 W ActivityManager: Slow operation: 205ms so far, now at startProcess: returned from zygote!
10-27 23:54:44.268   506   547 W ActivityManager: Slow operation: 205ms so far, now at startProcess: done updating battery stats
10-27 23:54:44.268   506   547 W ActivityManager: Slow operation: 206ms so far, now at startProcess: building log message
10-27 23:54:44.268   506   547 I ActivityManager: Start proc 2465:android.process.acore/u0a63 for content provider {com.android.providers.contacts/com.android.providers.contacts.VoicemailContentProvider}
10-27 23:54:44.268   506   547 W ActivityManager: Slow operation: 206ms so far, now at startProcess: starting to update pids map
10-27 23:54:44.269   506   547 W ActivityManager: Slow operation: 206ms so far, now at startProcess: done updating pids map
10-27 23:54:44.316  2465  2465 I ContactsPerf: VoicemailContentProvider.onCreate start
10-27 23:54:44.326  2465  2465 I ContactsPerf: VoicemailContentProvider.onCreate finish
10-27 23:54:44.841  2174  2226 I Perf    : fps=3.6 worst=3 drawn=14/37 queue=0 build=205ms verts=42k cam=1.5,2.1,1.4 ready=true drawn=14 noMesh=0 culled=23
10-27 23:54:46.872  2174  2226 I Perf    : fps=3.4 worst=3 drawn=14/37 queue=0 build=205ms verts=42k cam=1.5,2.1,1.4 ready=true drawn=14 noMesh=0 culled=23
10-27 23:54:49.125  2174  2226 I Perf    : fps=3.6 worst=3 drawn=14/37 queue=0 build=205ms verts=42k cam=1.5,2.1,1.4 ready=true drawn=14 noMesh=0 culled=23
```
## Step log
```
InputEvent=0, HandleInputStart=35357202483, AnimationStart=35357203775, PerformTraversalsStart=35357213419, DrawStart=35359026570, SyncQueued=35359215321, SyncStart=35384084176, IssueDrawCommandsStart=35384450612, SwapBuffers=35385184405, FrameCompleted=35406712333, DequeueBufferDuration=7736614, QueueBufferDuration=11229458, GpuCompleted=0, 
10-27 23:53:42.955   715  1140 I OpenGLRenderer: Davey! duration=977ms; Flags=1, IntendedVsync=34443708750, Vsync=35343708714, OldestInputEvent=9223372036854775807, NewestInputEvent=0, HandleInputStart=35357202483, AnimationStart=35357203775, PerformTraversalsStart=35357213419, DrawStart=35407052450, SyncQueued=35407155363, SyncStart=35407682829, IssueDrawCommandsStart=35407751762, SwapBuffers=35411319326, FrameCompleted=35421579009, DequeueBufferDuration=9453081, QueueBufferDuration=129814, GpuCompleted=0, 
10-27 23:54:04.183  2403  2403 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-27 23:54:04.799  2405  2405 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-27 23:54:04.813  2405  2405 I AndroidRuntime: Using default boot image
10-27 23:54:04.813  2405  2405 I AndroidRuntime: Leaving lock profiling enabled
10-27 23:54:04.964  2405  2405 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-27 23:54:04.979  2405  2405 D AndroidRuntime: Shutting down VM
10-27 23:54:04.993  2405  2416 I AndroidRuntime: NOTE: attach of thread 'Binder:2405_1' failed
10-27 23:54:05.554  2174  2226 I BackroomsGL: world shader compiled
10-27 23:54:07.509  2174  2226 I Perf    : fps=5.8 worst=53 drawn=5/5 queue=2 build=126ms verts=15k cam=1.5,2.1,1.5 ready=true drawn=5 noMesh=0 culled=0
10-27 23:54:09.739  2174  2226 I Perf    : fps=3.6 worst=4 drawn=10/14 queue=2 build=115ms verts=30k cam=1.5,2.1,1.5 ready=true drawn=10 noMesh=0 culled=4
10-27 23:54:11.973  2174  2226 I Perf    : fps=3.6 worst=3 drawn=11/21 queue=2 build=80ms verts=33k cam=1.5,2.1,1.5 ready=true drawn=11 noMesh=0 culled=10
10-27 23:54:14.176  2174  2226 I Perf    : fps=3.6 worst=3 drawn=14/33 queue=2 build=84ms verts=42k cam=1.5,2.1,1.5 ready=true drawn=14 noMesh=0 culled=17
10-27 23:54:16.404  2174  2226 I Perf    : fps=3.6 worst=3 drawn=14/37 queue=0 build=205ms verts=42k cam=1.5,2.1,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:54:18.655  2174  2226 I Perf    : fps=3.6 worst=3 drawn=14/37 queue=0 build=205ms verts=42k cam=1.5,2.1,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:54:20.834  2174  2226 I Perf    : fps=3.7 worst=3 drawn=14/37 queue=0 build=205ms verts=42k cam=1.5,2.1,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:54:23.058  2174  2226 I Perf    : fps=3.6 worst=3 drawn=14/37 queue=0 build=205ms verts=42k cam=1.5,2.1,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:54:25.251  2174  2226 I Perf    : fps=3.6 worst=3 drawn=14/37 queue=0 build=205ms verts=42k cam=1.5,2.1,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:54:27.432  2174  2226 I Perf    : fps=3.7 worst=3 drawn=14/37 queue=0 build=205ms verts=42k cam=1.5,2.1,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:54:29.678  2174  2226 I Perf    : fps=3.6 worst=3 drawn=14/37 queue=0 build=205ms verts=42k cam=1.5,2.1,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:54:31.037  2432  2432 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-27 23:54:31.887  2174  2226 I Perf    : fps=3.6 worst=4 drawn=14/37 queue=0 build=205ms verts=42k cam=1.5,2.1,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:54:34.115  2174  2226 I Perf    : fps=3.6 worst=4 drawn=14/37 queue=0 build=205ms verts=42k cam=1.5,2.1,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:54:36.151  2174  2226 I Perf    : fps=3.4 worst=3 drawn=14/37 queue=0 build=205ms verts=42k cam=1.5,2.1,1.5 ready=true drawn=14 noMesh=0 culled=23
10-27 23:54:37.424  2448  2448 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-27 23:54:37.437  2448  2448 I AndroidRuntime: Using default boot image
10-27 23:54:37.437  2448  2448 I AndroidRuntime: Leaving lock profiling enabled
10-27 23:54:37.608  2448  2448 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-27 23:54:38.083  2448  2448 D AndroidRuntime: Shutting down VM
10-27 23:54:38.364  2174  2226 I Perf    : fps=3.6 worst=4 drawn=14/37 queue=0 build=205ms verts=42k cam=1.5,2.1,1.4 ready=true drawn=14 noMesh=0 culled=23
10-27 23:54:40.388  2174  2226 I Perf    : fps=3.5 worst=3 drawn=14/37 queue=0 build=205ms verts=42k cam=1.5,2.1,1.4 ready=true drawn=14 noMesh=0 culled=23
10-27 23:54:42.612  2174  2226 I Perf    : fps=3.6 worst=3 drawn=14/37 queue=0 build=205ms verts=42k cam=1.5,2.1,1.4 ready=true drawn=14 noMesh=0 culled=23
10-27 23:54:44.304  2463  2463 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-27 23:54:44.316  2465  2465 I ContactsPerf: VoicemailContentProvider.onCreate start
10-27 23:54:44.326  2465  2465 I ContactsPerf: VoicemailContentProvider.onCreate finish
10-27 23:54:44.841  2174  2226 I Perf    : fps=3.6 worst=3 drawn=14/37 queue=0 build=205ms verts=42k cam=1.5,2.1,1.4 ready=true drawn=14 noMesh=0 culled=23
10-27 23:54:46.872  2174  2226 I Perf    : fps=3.4 worst=3 drawn=14/37 queue=0 build=205ms verts=42k cam=1.5,2.1,1.4 ready=true drawn=14 noMesh=0 culled=23
10-27 23:54:49.125  2174  2226 I Perf    : fps=3.6 worst=3 drawn=14/37 queue=0 build=205ms verts=42k cam=1.5,2.1,1.4 ready=true drawn=14 noMesh=0 culled=23
+ '[' -z 2174 ']'
+ grep -qa 'FATAL EXCEPTION' emulator-shots/logcat.txt
+ echo 'OK: the app is alive and rendering'
OK: the app is alive and rendering
```
