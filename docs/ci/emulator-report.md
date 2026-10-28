# Emulator smoke test diagnostics

Commit: ea587ab5ed60c0e7cb820318bcac40b0d990a06b
Outcome: success

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 28 12:11 /dev/kvm
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
INFO         | The active JSON Web Key Sets can be found here: /run/user/1001/avd/running/2961/jwks/bc8df944-abef-483c-8c50-a0112a382617/active.jwk
INFO         | Scanning /run/user/1001/avd/running/2961/jwks/bc8df944-abef-483c-8c50-a0112a382617 for jwk keys.
INFO         | Started GRPC server at 127.0.0.1:8554, security: Local, auth: +token
INFO         | Advertising in: /run/user/1001/avd/running/pid_2961.ini
INFO         | Setting display: 0 configuration to: 1080x1920, dpi: 420x420 
INFO         | setDisplayActiveConfig: id:0, 1080x1920
INFO         | emulatorSetupEnvironment: Environment scene is not required
USER_INFO    | Emulator is performing a full startup. This may take upto two minutes, or more.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
INFO         | Boot completed in 26716 ms
INFO         | Increasing screen off timeout, logcat buffer size to 2M.
```
## Logcat (filtered)
```
10-28 12:14:39.757  2414  2414 I AndroidRuntime: Leaving lock profiling enabled
10-28 12:14:39.969  2414  2414 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 12:14:39.991  2414  2414 D AndroidRuntime: Shutting down VM
10-28 12:14:40.589  2050  2101 I BackroomsGL: world shader compiled
10-28 12:14:40.660  2050  2101 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 12:14:40.661  2050  2101 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 12:14:42.820  2050  2101 I Perf    : fps=1.3 worst=4 drawn=2/4 queue=2 build=220ms verts=5k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=2 noMesh=0 culled=0
10-28 12:14:43.907   501   531 I ActivityManager: Killing 1666:android.process.acore/u0a63 (adj 985): empty #17
10-28 12:14:43.944   501   825 I ActivityManager: Killing 2117:com.android.calendar/u0a110 (adj 975): empty #17
10-28 12:14:45.008  2050  2101 I Perf    : fps=4.6 worst=5 drawn=13/19 queue=2 build=19ms verts=32k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=4
10-28 12:14:47.127  2050  2101 I Perf    : fps=4.7 worst=4 drawn=18/37 queue=0 build=15ms verts=44k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:14:49.216  2050  2101 I Perf    : fps=4.8 worst=5 drawn=18/37 queue=0 build=15ms verts=44k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:14:51.288  2050  2101 I Perf    : fps=4.8 worst=5 drawn=18/37 queue=0 build=15ms verts=44k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:14:53.344  2050  2101 I Perf    : fps=4.9 worst=5 drawn=18/37 queue=0 build=15ms verts=44k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:14:55.469  2050  2101 I Perf    : fps=4.7 worst=5 drawn=18/37 queue=0 build=15ms verts=44k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:14:57.525  2050  2101 I Perf    : fps=4.9 worst=5 drawn=18/37 queue=0 build=15ms verts=44k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:14:59.672  2050  2101 I Perf    : fps=4.7 worst=5 drawn=18/37 queue=0 build=15ms verts=44k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:15:01.796  2050  2101 I Perf    : fps=4.7 worst=5 drawn=18/37 queue=0 build=15ms verts=44k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:15:03.891  2050  2101 I Perf    : fps=4.8 worst=5 drawn=18/37 queue=0 build=15ms verts=44k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:15:06.044  2050  2101 I Perf    : fps=4.6 worst=5 drawn=18/37 queue=0 build=15ms verts=44k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:15:08.175  2050  2101 I Perf    : fps=4.7 worst=5 drawn=18/37 queue=0 build=15ms verts=44k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:15:10.329  2050  2101 I Perf    : fps=4.6 worst=4 drawn=18/37 queue=0 build=15ms verts=44k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:15:12.349  2050  2101 I Perf    : fps=4.5 worst=4 drawn=18/37 queue=0 build=15ms verts=44k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:15:14.551  2050  2101 I Perf    : fps=4.5 worst=5 drawn=18/37 queue=0 build=15ms verts=44k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:15:14.664  2465  2465 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 12:15:14.673  2465  2465 I AndroidRuntime: Using default boot image
10-28 12:15:14.673  2465  2465 I AndroidRuntime: Leaving lock profiling enabled
10-28 12:15:14.915  2465  2465 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 12:15:15.519  2465  2465 D AndroidRuntime: Shutting down VM
10-28 12:15:16.646  2050  2101 I Perf    : fps=4.3 worst=4 drawn=13/37 queue=0 build=15ms verts=33k cam=-0.5,1.6,-0.5 yaw=2.67 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 12:15:18.680  2050  2101 I Perf    : fps=3.9 worst=4 drawn=13/37 queue=0 build=15ms verts=33k cam=-0.5,1.6,-0.5 yaw=2.67 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 12:15:20.608  2481  2481 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 12:15:20.622  2481  2481 I AndroidRuntime: Using default boot image
10-28 12:15:20.622  2481  2481 I AndroidRuntime: Leaving lock profiling enabled
10-28 12:15:20.688   501   541 W ActivityManager: Slow operation: 218ms so far, now at startProcess: returned from zygote!
10-28 12:15:20.688   501   541 W ActivityManager: Slow operation: 219ms so far, now at startProcess: done updating battery stats
10-28 12:15:20.688   501   541 W ActivityManager: Slow operation: 219ms so far, now at startProcess: building log message
10-28 12:15:20.688   501   541 I ActivityManager: Start proc 2483:android.process.acore/u0a63 for content provider {com.android.providers.contacts/com.android.providers.contacts.VoicemailContentProvider}
10-28 12:15:20.688   501   541 W ActivityManager: Slow operation: 219ms so far, now at startProcess: starting to update pids map
10-28 12:15:20.688   501   541 W ActivityManager: Slow operation: 219ms so far, now at startProcess: done updating pids map
10-28 12:15:20.691  2050  2101 I Perf    : fps=4.0 worst=4 drawn=13/37 queue=0 build=15ms verts=33k cam=-0.5,1.6,-0.5 yaw=2.67 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 12:15:20.778  2483  2483 I ContactsPerf: VoicemailContentProvider.onCreate start
10-28 12:15:20.783  2483  2483 I ContactsPerf: VoicemailContentProvider.onCreate finish
10-28 12:15:20.932  2481  2481 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 12:15:20.940  2481  2481 D AndroidRuntime: Shutting down VM
10-28 12:15:20.945  2481  2518 I AndroidRuntime: NOTE: attach of thread 'Binder:2481_1' failed
10-28 12:15:22.746  2050  2101 I Perf    : fps=3.9 worst=4 drawn=13/37 queue=0 build=15ms verts=33k cam=-0.5,1.6,-0.5 yaw=2.67 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 12:15:24.828  2050  2101 I Perf    : fps=4.3 worst=4 drawn=13/37 queue=0 build=15ms verts=33k cam=-0.5,1.6,-0.5 yaw=2.67 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 12:15:26.043  2526  2526 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 12:15:26.056  2526  2526 I AndroidRuntime: Using default boot image
10-28 12:15:26.056  2526  2526 I AndroidRuntime: Leaving lock profiling enabled
10-28 12:15:26.140  2526  2526 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 12:15:26.841  2050  2101 I Perf    : fps=4.0 worst=4 drawn=13/37 queue=0 build=15ms verts=33k cam=-0.5,1.6,-0.4 yaw=2.67 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 12:15:27.646  2526  2526 D AndroidRuntime: Shutting down VM
10-28 12:15:28.912  2050  2101 I Perf    : fps=4.4 worst=4 drawn=18/44 queue=0 build=148ms verts=46k cam=-1.0,1.6,0.6 yaw=2.67 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-28 12:15:31.013  2050  2101 I Perf    : fps=4.3 worst=4 drawn=18/44 queue=0 build=148ms verts=46k cam=-1.0,1.6,0.6 yaw=2.67 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-28 12:15:33.154  2050  2101 I Perf    : fps=4.2 worst=4 drawn=18/44 queue=0 build=148ms verts=46k cam=-1.0,1.6,0.6 yaw=2.67 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-28 12:15:35.282  2050  2101 I Perf    : fps=4.2 worst=4 drawn=18/44 queue=0 build=148ms verts=46k cam=-1.0,1.6,0.6 yaw=2.67 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-28 12:15:37.402  2050  2101 I Perf    : fps=4.2 worst=4 drawn=18/44 queue=0 build=148ms verts=46k cam=-1.0,1.6,0.6 yaw=2.67 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-28 12:15:39.465  2050  2101 I Perf    : fps=4.4 worst=4 drawn=18/44 queue=0 build=148ms verts=46k cam=-1.0,1.6,0.6 yaw=2.67 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
```
## Step log
```
88  2050  2101 I Perf    : fps=4.8 worst=5 drawn=18/37 queue=0 build=15ms verts=44k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:14:53.344  2050  2101 I Perf    : fps=4.9 worst=5 drawn=18/37 queue=0 build=15ms verts=44k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:14:55.469  2050  2101 I Perf    : fps=4.7 worst=5 drawn=18/37 queue=0 build=15ms verts=44k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:14:57.525  2050  2101 I Perf    : fps=4.9 worst=5 drawn=18/37 queue=0 build=15ms verts=44k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:14:59.672  2050  2101 I Perf    : fps=4.7 worst=5 drawn=18/37 queue=0 build=15ms verts=44k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:15:01.796  2050  2101 I Perf    : fps=4.7 worst=5 drawn=18/37 queue=0 build=15ms verts=44k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:15:03.891  2050  2101 I Perf    : fps=4.8 worst=5 drawn=18/37 queue=0 build=15ms verts=44k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:15:06.017  2440  2440 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 12:15:06.044  2050  2101 I Perf    : fps=4.6 worst=5 drawn=18/37 queue=0 build=15ms verts=44k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:15:08.175  2050  2101 I Perf    : fps=4.7 worst=5 drawn=18/37 queue=0 build=15ms verts=44k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:15:10.329  2050  2101 I Perf    : fps=4.6 worst=4 drawn=18/37 queue=0 build=15ms verts=44k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:15:12.349  2050  2101 I Perf    : fps=4.5 worst=4 drawn=18/37 queue=0 build=15ms verts=44k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:15:14.551  2050  2101 I Perf    : fps=4.5 worst=5 drawn=18/37 queue=0 build=15ms verts=44k cam=-0.5,1.6,-0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:15:14.664  2465  2465 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 12:15:14.673  2465  2465 I AndroidRuntime: Using default boot image
10-28 12:15:14.673  2465  2465 I AndroidRuntime: Leaving lock profiling enabled
10-28 12:15:14.915  2465  2465 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 12:15:15.519  2465  2465 D AndroidRuntime: Shutting down VM
10-28 12:15:16.646  2050  2101 I Perf    : fps=4.3 worst=4 drawn=13/37 queue=0 build=15ms verts=33k cam=-0.5,1.6,-0.5 yaw=2.67 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 12:15:18.680  2050  2101 I Perf    : fps=3.9 worst=4 drawn=13/37 queue=0 build=15ms verts=33k cam=-0.5,1.6,-0.5 yaw=2.67 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 12:15:20.608  2481  2481 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 12:15:20.622  2481  2481 I AndroidRuntime: Using default boot image
10-28 12:15:20.622  2481  2481 I AndroidRuntime: Leaving lock profiling enabled
10-28 12:15:20.691  2050  2101 I Perf    : fps=4.0 worst=4 drawn=13/37 queue=0 build=15ms verts=33k cam=-0.5,1.6,-0.5 yaw=2.67 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 12:15:20.778  2483  2483 I ContactsPerf: VoicemailContentProvider.onCreate start
10-28 12:15:20.783  2483  2483 I ContactsPerf: VoicemailContentProvider.onCreate finish
10-28 12:15:20.932  2481  2481 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 12:15:20.940  2481  2481 D AndroidRuntime: Shutting down VM
10-28 12:15:20.945  2481  2518 I AndroidRuntime: NOTE: attach of thread 'Binder:2481_1' failed
10-28 12:15:22.746  2050  2101 I Perf    : fps=3.9 worst=4 drawn=13/37 queue=0 build=15ms verts=33k cam=-0.5,1.6,-0.5 yaw=2.67 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 12:15:24.828  2050  2101 I Perf    : fps=4.3 worst=4 drawn=13/37 queue=0 build=15ms verts=33k cam=-0.5,1.6,-0.5 yaw=2.67 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 12:15:26.043  2526  2526 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 12:15:26.056  2526  2526 I AndroidRuntime: Using default boot image
10-28 12:15:26.056  2526  2526 I AndroidRuntime: Leaving lock profiling enabled
10-28 12:15:26.140  2526  2526 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 12:15:26.841  2050  2101 I Perf    : fps=4.0 worst=4 drawn=13/37 queue=0 build=15ms verts=33k cam=-0.5,1.6,-0.4 yaw=2.67 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 12:15:27.646  2526  2526 D AndroidRuntime: Shutting down VM
10-28 12:15:28.912  2050  2101 I Perf    : fps=4.4 worst=4 drawn=18/44 queue=0 build=148ms verts=46k cam=-1.0,1.6,0.6 yaw=2.67 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-28 12:15:31.013  2050  2101 I Perf    : fps=4.3 worst=4 drawn=18/44 queue=0 build=148ms verts=46k cam=-1.0,1.6,0.6 yaw=2.67 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-28 12:15:33.150  2544  2544 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 12:15:33.154  2050  2101 I Perf    : fps=4.2 worst=4 drawn=18/44 queue=0 build=148ms verts=46k cam=-1.0,1.6,0.6 yaw=2.67 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-28 12:15:35.282  2050  2101 I Perf    : fps=4.2 worst=4 drawn=18/44 queue=0 build=148ms verts=46k cam=-1.0,1.6,0.6 yaw=2.67 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-28 12:15:37.402  2050  2101 I Perf    : fps=4.2 worst=4 drawn=18/44 queue=0 build=148ms verts=46k cam=-1.0,1.6,0.6 yaw=2.67 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-28 12:15:39.465  2050  2101 I Perf    : fps=4.4 worst=4 drawn=18/44 queue=0 build=148ms verts=46k cam=-1.0,1.6,0.6 yaw=2.67 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
+ '[' -z 2050 ']'
+ grep -qa 'FATAL EXCEPTION' emulator-shots/logcat.txt
+ '[' 0 '!=' 0 ']'
+ echo 'OK: the app is alive, rendering, and the touch controls work'
OK: the app is alive, rendering, and the touch controls work
```
