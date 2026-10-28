# Emulator smoke test diagnostics

Commit: faf8f3874e3badaf2d72b4022fc4e8b868ead871
Outcome: success

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 28 20:26 /dev/kvm
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
INFO         | The active JSON Web Key Sets can be found here: /run/user/1001/avd/running/2779/jwks/9a6da3aa-5039-4599-be5e-01970f01ce12/active.jwk
INFO         | Scanning /run/user/1001/avd/running/2779/jwks/9a6da3aa-5039-4599-be5e-01970f01ce12 for jwk keys.
INFO         | Started GRPC server at 127.0.0.1:8554, security: Local, auth: +token
INFO         | Advertising in: /run/user/1001/avd/running/pid_2779.ini
INFO         | Setting display: 0 configuration to: 1080x1920, dpi: 420x420 
INFO         | setDisplayActiveConfig: id:0, 1080x1920
INFO         | emulatorSetupEnvironment: Environment scene is not required
USER_INFO    | Emulator is performing a full startup. This may take upto two minutes, or more.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
INFO         | Boot completed in 26053 ms
INFO         | Increasing screen off timeout, logcat buffer size to 2M.
```
## Logcat (filtered)
```
10-28 20:30:42.433   504  2122 I ActivityManager: Killing 1955:com.android.contacts/u0a98 (adj 985): empty #17
10-28 20:30:42.433   504  2122 I ActivityManager: Killing 1273:com.android.cellbroadcastreceiver/u0a63 (adj 985): empty #18
10-28 20:30:46.663  2397  2397 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 20:30:46.689  2397  2397 I AndroidRuntime: Using default boot image
10-28 20:30:46.689  2397  2397 I AndroidRuntime: Leaving lock profiling enabled
10-28 20:30:47.045  2397  2397 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 20:30:47.113  2397  2397 D AndroidRuntime: Shutting down VM
10-28 20:30:47.685  2262  2300 I BackroomsGL: world shader compiled
10-28 20:30:48.244  2262  2300 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 20:30:48.244  2262  2300 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 20:30:49.571  2262  2300 I Perf    : fps=1.5 worst=3 drawn=1/3 queue=2 build=212ms verts=2k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=1 noMesh=0 culled=0
10-28 20:30:51.635  2262  2300 I Perf    : fps=4.9 worst=5 drawn=11/22 queue=2 build=117ms verts=27k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=11 noMesh=0 culled=9
10-28 20:30:53.643  2262  2300 I Perf    : fps=4.9 worst=5 drawn=14/37 queue=0 build=20ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:30:55.655  2262  2300 I Perf    : fps=5.0 worst=5 drawn=14/37 queue=0 build=20ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:30:57.706  2262  2300 I Perf    : fps=4.9 worst=5 drawn=14/37 queue=0 build=20ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:30:59.725  2262  2300 I Perf    : fps=5.0 worst=5 drawn=14/37 queue=0 build=20ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:31:01.739  2262  2300 I Perf    : fps=5.0 worst=5 drawn=14/37 queue=0 build=20ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:31:03.899  2262  2300 I Perf    : fps=5.1 worst=5 drawn=14/37 queue=0 build=20ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:31:06.053  2262  2300 I Perf    : fps=5.1 worst=5 drawn=14/37 queue=0 build=20ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:31:08.232  2262  2300 I Perf    : fps=5.0 worst=5 drawn=14/37 queue=0 build=20ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:31:10.253  2262  2300 I Perf    : fps=4.9 worst=5 drawn=14/37 queue=0 build=20ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:31:12.274  2262  2300 I Perf    : fps=4.9 worst=5 drawn=14/37 queue=0 build=20ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:31:14.398  2262  2300 I Perf    : fps=4.7 worst=5 drawn=14/37 queue=0 build=20ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:31:16.487  2262  2300 I Perf    : fps=4.8 worst=5 drawn=14/37 queue=0 build=20ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:31:18.563  2262  2300 I Perf    : fps=4.8 worst=5 drawn=14/37 queue=0 build=20ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:31:18.643  2435  2435 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 20:31:18.653  2435  2435 I AndroidRuntime: Using default boot image
10-28 20:31:18.654  2435  2435 I AndroidRuntime: Leaving lock profiling enabled
10-28 20:31:18.713  2435  2435 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 20:31:18.778   504   504 I ActivityManager: Killing 1592:android.process.acore/u0a66 (adj 985): empty #17
10-28 20:31:19.359  2435  2435 D AndroidRuntime: Shutting down VM
10-28 20:31:20.609  2262  2300 I Perf    : fps=5.4 worst=5 drawn=16/37 queue=0 build=20ms verts=41k cam=0.5,1.6,0.5 yaw=2.23 pitch=0.00 torch=1 ready=true drawn=16 noMesh=0 culled=21
10-28 20:31:22.615  2262  2300 I Perf    : fps=5.5 worst=6 drawn=16/37 queue=0 build=20ms verts=41k cam=0.5,1.6,0.5 yaw=2.23 pitch=0.00 torch=1 ready=true drawn=16 noMesh=0 culled=21
10-28 20:31:24.139  2462  2462 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 20:31:24.140  2462  2462 I AndroidRuntime: Using default boot image
10-28 20:31:24.140  2462  2462 I AndroidRuntime: Leaving lock profiling enabled
10-28 20:31:24.454  2462  2462 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 20:31:24.457  2462  2462 D AndroidRuntime: Shutting down VM
10-28 20:31:24.664  2262  2300 I Perf    : fps=5.4 worst=6 drawn=16/37 queue=0 build=20ms verts=41k cam=0.5,1.6,0.5 yaw=2.23 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 20:31:26.011   504   546 W ActivityManager: Slow operation: 245ms so far, now at startProcess: returned from zygote!
10-28 20:31:26.012   504   546 W ActivityManager: Slow operation: 246ms so far, now at startProcess: done updating battery stats
10-28 20:31:26.012   504   546 W ActivityManager: Slow operation: 246ms so far, now at startProcess: building log message
10-28 20:31:26.012   504   546 I ActivityManager: Start proc 2475:android.process.acore/u0a66 for content provider {com.android.providers.contacts/com.android.providers.contacts.VoicemailContentProvider}
10-28 20:31:26.012   504   546 W ActivityManager: Slow operation: 246ms so far, now at startProcess: starting to update pids map
10-28 20:31:26.012   504   546 W ActivityManager: Slow operation: 246ms so far, now at startProcess: done updating pids map
10-28 20:31:26.115  2475  2475 I ContactsPerf: VoicemailContentProvider.onCreate start
10-28 20:31:26.116  2475  2475 I ContactsPerf: VoicemailContentProvider.onCreate finish
10-28 20:31:26.675  2262  2300 I Perf    : fps=5.5 worst=6 drawn=16/37 queue=0 build=20ms verts=41k cam=0.5,1.6,0.5 yaw=2.23 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 20:31:28.717  2262  2300 I Perf    : fps=5.4 worst=5 drawn=16/37 queue=0 build=20ms verts=41k cam=0.5,1.6,0.5 yaw=2.23 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 20:31:29.186  2507  2507 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 20:31:29.195  2507  2507 I AndroidRuntime: Using default boot image
10-28 20:31:29.196  2507  2507 I AndroidRuntime: Leaving lock profiling enabled
10-28 20:31:29.423  2507  2507 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 20:31:30.781  2262  2300 I Perf    : fps=5.3 worst=5 drawn=16/39 queue=2 build=76ms verts=41k cam=-0.2,1.6,1.1 yaw=2.23 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 20:31:30.931  2507  2507 D AndroidRuntime: Shutting down VM
10-28 20:31:32.788  2262  2300 I Perf    : fps=5.0 worst=5 drawn=19/44 queue=0 build=20ms verts=49k cam=-0.6,1.6,1.4 yaw=2.23 pitch=0.00 torch=0 ready=true drawn=19 noMesh=0 culled=25
10-28 20:31:34.814  2262  2300 I Perf    : fps=4.9 worst=5 drawn=19/44 queue=0 build=20ms verts=49k cam=-0.6,1.6,1.4 yaw=2.23 pitch=0.00 torch=0 ready=true drawn=19 noMesh=0 culled=25
10-28 20:31:36.883  2262  2300 I Perf    : fps=4.8 worst=5 drawn=19/44 queue=0 build=20ms verts=49k cam=-0.6,1.6,1.4 yaw=2.23 pitch=0.00 torch=0 ready=true drawn=19 noMesh=0 culled=25
10-28 20:31:38.965  2262  2300 I Perf    : fps=4.8 worst=5 drawn=19/44 queue=0 build=20ms verts=49k cam=-0.6,1.6,1.4 yaw=2.23 pitch=0.00 torch=0 ready=true drawn=19 noMesh=0 culled=25
10-28 20:31:40.975  2262  2300 I Perf    : fps=5.0 worst=5 drawn=19/44 queue=0 build=20ms verts=49k cam=-0.6,1.6,1.4 yaw=2.23 pitch=0.00 torch=0 ready=true drawn=19 noMesh=0 culled=25
```
## Step log
```
wn=11/22 queue=2 build=117ms verts=27k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=11 noMesh=0 culled=9
10-28 20:30:53.643  2262  2300 I Perf    : fps=4.9 worst=5 drawn=14/37 queue=0 build=20ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:30:55.655  2262  2300 I Perf    : fps=5.0 worst=5 drawn=14/37 queue=0 build=20ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:30:57.706  2262  2300 I Perf    : fps=4.9 worst=5 drawn=14/37 queue=0 build=20ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:30:59.725  2262  2300 I Perf    : fps=5.0 worst=5 drawn=14/37 queue=0 build=20ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:31:01.739  2262  2300 I Perf    : fps=5.0 worst=5 drawn=14/37 queue=0 build=20ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:31:03.899  2262  2300 I Perf    : fps=5.1 worst=5 drawn=14/37 queue=0 build=20ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:31:06.053  2262  2300 I Perf    : fps=5.1 worst=5 drawn=14/37 queue=0 build=20ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:31:08.232  2262  2300 I Perf    : fps=5.0 worst=5 drawn=14/37 queue=0 build=20ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:31:10.253  2262  2300 I Perf    : fps=4.9 worst=5 drawn=14/37 queue=0 build=20ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:31:12.274  2262  2300 I Perf    : fps=4.9 worst=5 drawn=14/37 queue=0 build=20ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:31:13.120  2422  2422 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 20:31:14.398  2262  2300 I Perf    : fps=4.7 worst=5 drawn=14/37 queue=0 build=20ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:31:16.487  2262  2300 I Perf    : fps=4.8 worst=5 drawn=14/37 queue=0 build=20ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:31:18.563  2262  2300 I Perf    : fps=4.8 worst=5 drawn=14/37 queue=0 build=20ms verts=35k cam=0.5,1.6,0.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:31:18.643  2435  2435 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 20:31:18.653  2435  2435 I AndroidRuntime: Using default boot image
10-28 20:31:18.654  2435  2435 I AndroidRuntime: Leaving lock profiling enabled
10-28 20:31:18.713  2435  2435 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 20:31:19.359  2435  2435 D AndroidRuntime: Shutting down VM
10-28 20:31:20.609  2262  2300 I Perf    : fps=5.4 worst=5 drawn=16/37 queue=0 build=20ms verts=41k cam=0.5,1.6,0.5 yaw=2.23 pitch=0.00 torch=1 ready=true drawn=16 noMesh=0 culled=21
10-28 20:31:22.615  2262  2300 I Perf    : fps=5.5 worst=6 drawn=16/37 queue=0 build=20ms verts=41k cam=0.5,1.6,0.5 yaw=2.23 pitch=0.00 torch=1 ready=true drawn=16 noMesh=0 culled=21
10-28 20:31:24.139  2462  2462 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 20:31:24.140  2462  2462 I AndroidRuntime: Using default boot image
10-28 20:31:24.140  2462  2462 I AndroidRuntime: Leaving lock profiling enabled
10-28 20:31:24.454  2462  2462 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 20:31:24.457  2462  2462 D AndroidRuntime: Shutting down VM
10-28 20:31:24.664  2262  2300 I Perf    : fps=5.4 worst=6 drawn=16/37 queue=0 build=20ms verts=41k cam=0.5,1.6,0.5 yaw=2.23 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 20:31:26.115  2475  2475 I ContactsPerf: VoicemailContentProvider.onCreate start
10-28 20:31:26.116  2475  2475 I ContactsPerf: VoicemailContentProvider.onCreate finish
10-28 20:31:26.675  2262  2300 I Perf    : fps=5.5 worst=6 drawn=16/37 queue=0 build=20ms verts=41k cam=0.5,1.6,0.5 yaw=2.23 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 20:31:28.717  2262  2300 I Perf    : fps=5.4 worst=5 drawn=16/37 queue=0 build=20ms verts=41k cam=0.5,1.6,0.5 yaw=2.23 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 20:31:29.186  2507  2507 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 20:31:29.195  2507  2507 I AndroidRuntime: Using default boot image
10-28 20:31:29.196  2507  2507 I AndroidRuntime: Leaving lock profiling enabled
10-28 20:31:29.423  2507  2507 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 20:31:30.781  2262  2300 I Perf    : fps=5.3 worst=5 drawn=16/39 queue=2 build=76ms verts=41k cam=-0.2,1.6,1.1 yaw=2.23 pitch=0.00 torch=0 ready=true drawn=16 noMesh=0 culled=21
10-28 20:31:30.931  2507  2507 D AndroidRuntime: Shutting down VM
10-28 20:31:32.788  2262  2300 I Perf    : fps=5.0 worst=5 drawn=19/44 queue=0 build=20ms verts=49k cam=-0.6,1.6,1.4 yaw=2.23 pitch=0.00 torch=0 ready=true drawn=19 noMesh=0 culled=25
10-28 20:31:34.814  2262  2300 I Perf    : fps=4.9 worst=5 drawn=19/44 queue=0 build=20ms verts=49k cam=-0.6,1.6,1.4 yaw=2.23 pitch=0.00 torch=0 ready=true drawn=19 noMesh=0 culled=25
10-28 20:31:36.063  2525  2525 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 20:31:36.883  2262  2300 I Perf    : fps=4.8 worst=5 drawn=19/44 queue=0 build=20ms verts=49k cam=-0.6,1.6,1.4 yaw=2.23 pitch=0.00 torch=0 ready=true drawn=19 noMesh=0 culled=25
10-28 20:31:38.965  2262  2300 I Perf    : fps=4.8 worst=5 drawn=19/44 queue=0 build=20ms verts=49k cam=-0.6,1.6,1.4 yaw=2.23 pitch=0.00 torch=0 ready=true drawn=19 noMesh=0 culled=25
10-28 20:31:40.975  2262  2300 I Perf    : fps=5.0 worst=5 drawn=19/44 queue=0 build=20ms verts=49k cam=-0.6,1.6,1.4 yaw=2.23 pitch=0.00 torch=0 ready=true drawn=19 noMesh=0 culled=25
+ '[' -z 2262 ']'
+ grep -qa 'FATAL EXCEPTION' emulator-shots/logcat.txt
+ '[' 0 '!=' 0 ']'
+ echo 'OK: the app is alive, rendering, and the touch controls work'
OK: the app is alive, rendering, and the touch controls work
```
