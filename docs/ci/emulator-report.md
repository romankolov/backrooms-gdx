# Emulator smoke test diagnostics

Commit: 2e1b302ffaf091cd959a48123a00a86c2db5c01a
Outcome: success

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 29 00:48 /dev/kvm
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
INFO         | The active JSON Web Key Sets can be found here: /run/user/1001/avd/running/2985/jwks/3aaf2872-a4d5-464c-8c9d-2cf45ab8d2c1/active.jwk
INFO         | Scanning /run/user/1001/avd/running/2985/jwks/3aaf2872-a4d5-464c-8c9d-2cf45ab8d2c1 for jwk keys.
INFO         | Started GRPC server at 127.0.0.1:8554, security: Local, auth: +token
INFO         | Advertising in: /run/user/1001/avd/running/pid_2985.ini
INFO         | Setting display: 0 configuration to: 1080x1920, dpi: 420x420 
INFO         | setDisplayActiveConfig: id:0, 1080x1920
INFO         | emulatorSetupEnvironment: Environment scene is not required
USER_INFO    | Emulator is performing a full startup. This may take upto two minutes, or more.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
INFO         | Boot completed in 26211 ms
INFO         | Increasing screen off timeout, logcat buffer size to 2M.
```
## Logcat (filtered)
```
10-29 00:52:36.004   503   543 W ActivityManager: Slow operation: 96ms so far, now at startProcess: returned from zygote!
10-29 00:52:36.062   503   543 I ActivityManager: Start proc 2342:com.android.traceur/u0a88 for broadcast {com.android.traceur/com.android.traceur.Receiver}
10-29 00:52:36.105   503  1741 I ActivityManager: Finished processing BOOT_COMPLETED for u0
10-29 00:52:36.106   503  1741 I ActivityManager: Killing 1774:com.android.externalstorage/u0a68 (adj 985): empty #17
10-29 00:52:50.969   503   541 I ActivityManager: Killing 1319:com.android.cellbroadcastreceiver/u0a65 (adj 985): empty #17
10-29 00:52:55.416  2402  2402 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 00:52:55.458  2402  2402 I AndroidRuntime: Using default boot image
10-29 00:52:55.458  2402  2402 I AndroidRuntime: Leaving lock profiling enabled
10-29 00:52:55.726   503   778 I ActivityManager: Killing 1756:android.process.acore/u0a64 (adj 985): empty #17
10-29 00:52:55.727   503   778 I ActivityManager: Killing 1979:com.android.quicksearchbox/u0a102 (adj 985): empty #18
10-29 00:52:55.746  2402  2402 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 00:52:55.770  2402  2402 D AndroidRuntime: Shutting down VM
10-29 00:52:56.495  2043  2103 I BackroomsGL: world shader compiled
10-29 00:52:58.072  2043  2103 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-29 00:52:58.072  2043  2103 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-29 00:52:58.753  2043  2103 I Perf    : fps=0.8 worst=1 drawn=0/2 queue=2 build=132ms verts=0k cam=0.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=0 noMesh=0 culled=0
10-29 00:52:59.192   503  2064 I ActivityManager: Killing 2125:com.android.calendar/u0a108 (adj 975): empty #17
10-29 00:53:00.706  2043  2103 I Perf    : fps=1.0 worst=3 drawn=4/4 queue=2 build=38ms verts=12k cam=0.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=4 noMesh=0 culled=0
10-29 00:53:02.914  2043  2103 I Perf    : fps=3.2 worst=3 drawn=11/18 queue=2 build=17ms verts=33k cam=0.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=11 noMesh=0 culled=5
10-29 00:53:05.073  2043  2103 I Perf    : fps=3.2 worst=3 drawn=14/32 queue=2 build=8ms verts=42k cam=0.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=16
10-29 00:53:07.372  2043  2103 I Perf    : fps=3.0 worst=3 drawn=14/37 queue=0 build=8ms verts=42k cam=0.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:53:09.375  2043  2103 I Perf    : fps=3.0 worst=3 drawn=14/37 queue=0 build=8ms verts=42k cam=0.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:53:11.679  2043  2103 I Perf    : fps=3.0 worst=3 drawn=14/37 queue=0 build=8ms verts=42k cam=0.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:53:13.958  2043  2103 I Perf    : fps=3.1 worst=3 drawn=14/37 queue=0 build=8ms verts=42k cam=0.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:53:16.252  2043  2103 I Perf    : fps=3.1 worst=3 drawn=14/37 queue=0 build=8ms verts=42k cam=0.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:53:18.391  2043  2103 I Perf    : fps=3.3 worst=3 drawn=14/37 queue=0 build=8ms verts=42k cam=0.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:53:20.660  2043  2103 I Perf    : fps=3.1 worst=3 drawn=14/37 queue=0 build=8ms verts=42k cam=0.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:53:22.722  2043  2103 I Perf    : fps=2.9 worst=3 drawn=14/37 queue=0 build=8ms verts=42k cam=0.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:53:24.759  2043  2103 I Perf    : fps=2.9 worst=3 drawn=14/37 queue=0 build=8ms verts=42k cam=0.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:53:26.797  2043  2103 I Perf    : fps=3.0 worst=3 drawn=14/37 queue=0 build=8ms verts=42k cam=0.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:53:28.148  2458  2458 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 00:53:28.159  2458  2458 I AndroidRuntime: Using default boot image
10-29 00:53:28.160  2458  2458 I AndroidRuntime: Leaving lock profiling enabled
10-29 00:53:28.262  2458  2458 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 00:53:28.942  2458  2458 D AndroidRuntime: Shutting down VM
10-29 00:53:29.121  2043  2103 I Perf    : fps=3.0 worst=3 drawn=13/37 queue=0 build=8ms verts=39k cam=0.5,1.6,3.5 yaw=2.42 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-29 00:53:31.381  2043  2103 I Perf    : fps=3.5 worst=3 drawn=14/37 queue=0 build=8ms verts=43k cam=0.5,1.6,3.5 yaw=2.42 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:53:33.552  2043  2103 I Perf    : fps=3.7 worst=4 drawn=14/37 queue=0 build=8ms verts=43k cam=0.5,1.6,3.5 yaw=2.42 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:53:34.271  2476  2476 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 00:53:34.279  2476  2476 I AndroidRuntime: Using default boot image
10-29 00:53:34.279  2476  2476 I AndroidRuntime: Leaving lock profiling enabled
10-29 00:53:34.529  2476  2476 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 00:53:34.535  2476  2476 D AndroidRuntime: Shutting down VM
10-29 00:53:35.575  2043  2103 I Perf    : fps=3.5 worst=3 drawn=14/37 queue=0 build=8ms verts=43k cam=0.5,1.6,3.5 yaw=2.42 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-29 00:53:37.798  2043  2103 I Perf    : fps=3.6 worst=3 drawn=14/37 queue=0 build=8ms verts=43k cam=0.5,1.6,3.5 yaw=2.42 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-29 00:53:37.818   503   543 I ActivityManager: Start proc 2490:android.process.acore/u0a64 for content provider {com.android.providers.contacts/com.android.providers.contacts.VoicemailContentProvider}
10-29 00:53:37.859  2490  2490 I ContactsPerf: VoicemailContentProvider.onCreate start
10-29 00:53:37.860  2490  2490 I ContactsPerf: VoicemailContentProvider.onCreate finish
10-29 00:53:39.248  2521  2521 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 00:53:39.250  2521  2521 I AndroidRuntime: Using default boot image
10-29 00:53:39.250  2521  2521 I AndroidRuntime: Leaving lock profiling enabled
10-29 00:53:39.434  2521  2521 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 00:53:40.061  2043  2103 I Perf    : fps=3.5 worst=3 drawn=14/37 queue=0 build=8ms verts=43k cam=0.5,1.6,3.5 yaw=2.42 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-29 00:53:40.940  2521  2521 D AndroidRuntime: Shutting down VM
10-29 00:53:42.229  2043  2103 I Perf    : fps=3.2 worst=3 drawn=15/43 queue=1 build=15ms verts=46k cam=-0.0,1.6,4.1 yaw=2.42 pitch=0.00 torch=0 ready=true drawn=15 noMesh=0 culled=26
10-29 00:53:44.439  2043  2103 I Perf    : fps=3.2 worst=3 drawn=18/44 queue=0 build=8ms verts=56k cam=-0.0,1.6,4.1 yaw=2.42 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-29 00:53:46.463  2043  2103 I Perf    : fps=3.0 worst=3 drawn=18/44 queue=0 build=8ms verts=56k cam=-0.0,1.6,4.1 yaw=2.42 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-29 00:53:48.468  2043  2103 I Perf    : fps=3.0 worst=3 drawn=18/44 queue=0 build=8ms verts=56k cam=-0.0,1.6,4.1 yaw=2.42 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-29 00:53:50.749  2043  2103 I Perf    : fps=3.1 worst=3 drawn=18/44 queue=0 build=8ms verts=56k cam=-0.0,1.6,4.1 yaw=2.42 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-29 00:53:52.959  2043  2103 I Perf    : fps=3.2 worst=3 drawn=18/44 queue=0 build=8ms verts=56k cam=-0.0,1.6,4.1 yaw=2.42 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
```
## Step log
```
rf    : fps=1.0 worst=3 drawn=4/4 queue=2 build=38ms verts=12k cam=0.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=4 noMesh=0 culled=0
10-29 00:53:02.914  2043  2103 I Perf    : fps=3.2 worst=3 drawn=11/18 queue=2 build=17ms verts=33k cam=0.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=11 noMesh=0 culled=5
10-29 00:53:05.073  2043  2103 I Perf    : fps=3.2 worst=3 drawn=14/32 queue=2 build=8ms verts=42k cam=0.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=16
10-29 00:53:07.372  2043  2103 I Perf    : fps=3.0 worst=3 drawn=14/37 queue=0 build=8ms verts=42k cam=0.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:53:09.375  2043  2103 I Perf    : fps=3.0 worst=3 drawn=14/37 queue=0 build=8ms verts=42k cam=0.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:53:11.679  2043  2103 I Perf    : fps=3.0 worst=3 drawn=14/37 queue=0 build=8ms verts=42k cam=0.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:53:13.958  2043  2103 I Perf    : fps=3.1 worst=3 drawn=14/37 queue=0 build=8ms verts=42k cam=0.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:53:16.252  2043  2103 I Perf    : fps=3.1 worst=3 drawn=14/37 queue=0 build=8ms verts=42k cam=0.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:53:18.391  2043  2103 I Perf    : fps=3.3 worst=3 drawn=14/37 queue=0 build=8ms verts=42k cam=0.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:53:20.660  2043  2103 I Perf    : fps=3.1 worst=3 drawn=14/37 queue=0 build=8ms verts=42k cam=0.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:53:22.363  2434  2434 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-29 00:53:22.722  2043  2103 I Perf    : fps=2.9 worst=3 drawn=14/37 queue=0 build=8ms verts=42k cam=0.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:53:24.759  2043  2103 I Perf    : fps=2.9 worst=3 drawn=14/37 queue=0 build=8ms verts=42k cam=0.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:53:26.797  2043  2103 I Perf    : fps=3.0 worst=3 drawn=14/37 queue=0 build=8ms verts=42k cam=0.5,1.6,3.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:53:28.148  2458  2458 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 00:53:28.159  2458  2458 I AndroidRuntime: Using default boot image
10-29 00:53:28.160  2458  2458 I AndroidRuntime: Leaving lock profiling enabled
10-29 00:53:28.262  2458  2458 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 00:53:28.942  2458  2458 D AndroidRuntime: Shutting down VM
10-29 00:53:29.121  2043  2103 I Perf    : fps=3.0 worst=3 drawn=13/37 queue=0 build=8ms verts=39k cam=0.5,1.6,3.5 yaw=2.42 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-29 00:53:31.381  2043  2103 I Perf    : fps=3.5 worst=3 drawn=14/37 queue=0 build=8ms verts=43k cam=0.5,1.6,3.5 yaw=2.42 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:53:33.552  2043  2103 I Perf    : fps=3.7 worst=4 drawn=14/37 queue=0 build=8ms verts=43k cam=0.5,1.6,3.5 yaw=2.42 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 00:53:34.271  2476  2476 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 00:53:34.279  2476  2476 I AndroidRuntime: Using default boot image
10-29 00:53:34.279  2476  2476 I AndroidRuntime: Leaving lock profiling enabled
10-29 00:53:34.529  2476  2476 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 00:53:34.535  2476  2476 D AndroidRuntime: Shutting down VM
10-29 00:53:35.575  2043  2103 I Perf    : fps=3.5 worst=3 drawn=14/37 queue=0 build=8ms verts=43k cam=0.5,1.6,3.5 yaw=2.42 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-29 00:53:37.798  2043  2103 I Perf    : fps=3.6 worst=3 drawn=14/37 queue=0 build=8ms verts=43k cam=0.5,1.6,3.5 yaw=2.42 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-29 00:53:37.859  2490  2490 I ContactsPerf: VoicemailContentProvider.onCreate start
10-29 00:53:37.860  2490  2490 I ContactsPerf: VoicemailContentProvider.onCreate finish
10-29 00:53:39.248  2521  2521 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 00:53:39.250  2521  2521 I AndroidRuntime: Using default boot image
10-29 00:53:39.250  2521  2521 I AndroidRuntime: Leaving lock profiling enabled
10-29 00:53:39.434  2521  2521 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 00:53:40.061  2043  2103 I Perf    : fps=3.5 worst=3 drawn=14/37 queue=0 build=8ms verts=43k cam=0.5,1.6,3.5 yaw=2.42 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-29 00:53:40.940  2521  2521 D AndroidRuntime: Shutting down VM
10-29 00:53:42.229  2043  2103 I Perf    : fps=3.2 worst=3 drawn=15/43 queue=1 build=15ms verts=46k cam=-0.0,1.6,4.1 yaw=2.42 pitch=0.00 torch=0 ready=true drawn=15 noMesh=0 culled=26
10-29 00:53:44.439  2043  2103 I Perf    : fps=3.2 worst=3 drawn=18/44 queue=0 build=8ms verts=56k cam=-0.0,1.6,4.1 yaw=2.42 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-29 00:53:46.383  2540  2540 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-29 00:53:46.463  2043  2103 I Perf    : fps=3.0 worst=3 drawn=18/44 queue=0 build=8ms verts=56k cam=-0.0,1.6,4.1 yaw=2.42 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-29 00:53:48.468  2043  2103 I Perf    : fps=3.0 worst=3 drawn=18/44 queue=0 build=8ms verts=56k cam=-0.0,1.6,4.1 yaw=2.42 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-29 00:53:50.749  2043  2103 I Perf    : fps=3.1 worst=3 drawn=18/44 queue=0 build=8ms verts=56k cam=-0.0,1.6,4.1 yaw=2.42 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
10-29 00:53:52.959  2043  2103 I Perf    : fps=3.2 worst=3 drawn=18/44 queue=0 build=8ms verts=56k cam=-0.0,1.6,4.1 yaw=2.42 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=26
+ '[' -z 2043 ']'
+ grep -qa 'FATAL EXCEPTION' emulator-shots/logcat.txt
+ '[' 0 '!=' 0 ']'
+ echo 'OK: the app is alive, rendering, and the touch controls work'
OK: the app is alive, rendering, and the touch controls work
```
