# Emulator smoke test diagnostics

Commit: a53295e9a3934faf6c3a41bbb917e52ca2602456
Outcome: success

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 29 04:33 /dev/kvm
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
INFO         | The active JSON Web Key Sets can be found here: /run/user/1001/avd/running/3022/jwks/c7bc1ae6-feb9-495a-b70d-3d60042368bf/active.jwk
INFO         | Scanning /run/user/1001/avd/running/3022/jwks/c7bc1ae6-feb9-495a-b70d-3d60042368bf for jwk keys.
INFO         | Started GRPC server at 127.0.0.1:8554, security: Local, auth: +token
INFO         | Advertising in: /run/user/1001/avd/running/pid_3022.ini
INFO         | Setting display: 0 configuration to: 1080x1920, dpi: 420x420 
INFO         | setDisplayActiveConfig: id:0, 1080x1920
INFO         | emulatorSetupEnvironment: Environment scene is not required
USER_INFO    | Emulator is performing a full startup. This may take upto two minutes, or more.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
INFO         | Boot completed in 26948 ms
INFO         | Increasing screen off timeout, logcat buffer size to 2M.
```
## Logcat (filtered)
```
10-29 04:38:02.150   507   547 W ActivityManager: Slow operation: 57ms so far, now at startProcess: starting to update pids map
10-29 04:38:02.150   507   547 W ActivityManager: Slow operation: 57ms so far, now at startProcess: done updating pids map
10-29 04:38:02.306   507   808 I ActivityManager: Finished processing BOOT_COMPLETED for u0
10-29 04:38:02.307   507   808 I ActivityManager: Killing 1949:com.android.localtransport/1000 (adj 985): empty #17
10-29 04:38:16.906   507   545 I ActivityManager: Killing 1289:com.android.cellbroadcastreceiver/u0a66 (adj 985): empty #17
10-29 04:38:20.588   507   537 I ActivityManager: Killing 2104:com.android.calendar/u0a110 (adj 985): empty #17
10-29 04:38:20.589   507   537 I ActivityManager: Killing 1988:com.android.quicksearchbox/u0a101 (adj 985): empty #18
10-29 04:38:22.615  2411  2411 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 04:38:22.645  2411  2411 I AndroidRuntime: Using default boot image
10-29 04:38:22.645  2411  2411 I AndroidRuntime: Leaving lock profiling enabled
10-29 04:38:22.822  2411  2411 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 04:38:22.883  2411  2411 D AndroidRuntime: Shutting down VM
10-29 04:38:23.786  2063  2102 I BackroomsGL: world shader compiled
10-29 04:38:25.633   507   978 I ActivityManager: Killing 1744:android.process.acore/u0a64 (adj 975): empty #17
10-29 04:38:25.776  2063  2102 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-29 04:38:25.776  2063  2102 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-29 04:38:25.829  2063  2102 I Perf    : fps=0.4 worst=0 drawn=0/0 queue=2 build=0ms verts=0k cam=0.0,0.0,0.0 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=0 noMesh=0 culled=0
10-29 04:38:28.068  2063  2102 I Perf    : fps=1.3 worst=2 drawn=3/5 queue=2 build=114ms verts=9k cam=0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=3 noMesh=0 culled=0
10-29 04:38:30.104  2063  2102 I Perf    : fps=3.0 worst=3 drawn=11/17 queue=1 build=9ms verts=34k cam=0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=11 noMesh=0 culled=4
10-29 04:38:32.125  2063  2102 I Perf    : fps=3.0 worst=3 drawn=13/29 queue=2 build=30ms verts=40k cam=0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=14
10-29 04:38:34.190  2063  2102 I Perf    : fps=2.9 worst=3 drawn=14/37 queue=0 build=72ms verts=43k cam=0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 04:38:36.344  2063  2102 I Perf    : fps=2.8 worst=3 drawn=14/37 queue=0 build=72ms verts=43k cam=0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 04:38:38.435  2063  2102 I Perf    : fps=2.9 worst=3 drawn=14/37 queue=0 build=72ms verts=43k cam=0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 04:38:40.454  2063  2102 I Perf    : fps=3.0 worst=3 drawn=14/37 queue=0 build=72ms verts=43k cam=0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 04:38:42.659  2063  2102 I Perf    : fps=2.7 worst=2 drawn=14/37 queue=0 build=72ms verts=43k cam=0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 04:38:44.800  2063  2102 I Perf    : fps=2.8 worst=2 drawn=14/37 queue=0 build=72ms verts=43k cam=0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 04:38:46.959  2063  2102 I Perf    : fps=2.8 worst=2 drawn=14/37 queue=0 build=72ms verts=43k cam=0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 04:38:49.143  2063  2102 I Perf    : fps=2.8 worst=3 drawn=14/37 queue=0 build=72ms verts=43k cam=0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 04:38:51.277  2063  2102 I Perf    : fps=2.8 worst=2 drawn=14/37 queue=0 build=72ms verts=43k cam=0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 04:38:53.442  2063  2102 I Perf    : fps=2.8 worst=2 drawn=14/37 queue=0 build=72ms verts=43k cam=0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 04:38:54.936  2468  2468 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 04:38:54.959  2468  2468 I AndroidRuntime: Using default boot image
10-29 04:38:54.960  2468  2468 I AndroidRuntime: Leaving lock profiling enabled
10-29 04:38:55.087  2468  2468 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 04:38:55.553  2063  2102 I Perf    : fps=2.8 worst=3 drawn=13/37 queue=0 build=72ms verts=39k cam=0.5,1.6,1.5 yaw=2.10 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-29 04:38:55.743  2468  2468 D AndroidRuntime: Shutting down VM
10-29 04:38:57.598  2063  2102 I Perf    : fps=3.4 worst=3 drawn=15/37 queue=0 build=72ms verts=45k cam=0.5,1.6,1.5 yaw=2.57 pitch=0.00 torch=1 ready=true drawn=15 noMesh=0 culled=22
10-29 04:38:59.768  2063  2102 I Perf    : fps=3.7 worst=4 drawn=15/37 queue=0 build=72ms verts=45k cam=0.5,1.6,1.5 yaw=2.57 pitch=0.00 torch=1 ready=true drawn=15 noMesh=0 culled=22
10-29 04:39:00.554  2487  2487 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 04:39:00.564  2487  2487 I AndroidRuntime: Using default boot image
10-29 04:39:00.565  2487  2487 I AndroidRuntime: Leaving lock profiling enabled
10-29 04:39:00.672  2487  2487 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 04:39:00.676  2487  2487 D AndroidRuntime: Shutting down VM
10-29 04:39:01.782  2063  2102 I Perf    : fps=3.5 worst=3 drawn=15/37 queue=0 build=72ms verts=45k cam=0.5,1.6,1.5 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=15 noMesh=0 culled=22
10-29 04:39:04.023  2063  2102 I Perf    : fps=3.6 worst=3 drawn=15/37 queue=0 build=72ms verts=45k cam=0.5,1.6,1.5 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=15 noMesh=0 culled=22
10-29 04:39:04.023   507   547 I ActivityManager: Start proc 2501:android.process.acore/u0a64 for content provider {com.android.providers.contacts/com.android.providers.contacts.VoicemailContentProvider}
10-29 04:39:04.064  2501  2501 I ContactsPerf: VoicemailContentProvider.onCreate start
10-29 04:39:04.065  2501  2501 I ContactsPerf: VoicemailContentProvider.onCreate finish
10-29 04:39:06.165  2532  2532 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 04:39:06.168  2532  2532 I AndroidRuntime: Using default boot image
10-29 04:39:06.168  2532  2532 I AndroidRuntime: Leaving lock profiling enabled
10-29 04:39:06.247  2532  2532 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 04:39:06.312  2063  2102 I Perf    : fps=3.5 worst=3 drawn=15/37 queue=0 build=72ms verts=45k cam=0.5,1.6,1.5 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=15 noMesh=0 culled=22
10-29 04:39:07.861  2532  2532 D AndroidRuntime: Shutting down VM
10-29 04:39:08.518  2063  2102 I Perf    : fps=3.6 worst=4 drawn=15/37 queue=0 build=72ms verts=45k cam=0.3,1.6,1.7 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=15 noMesh=0 culled=22
10-29 04:39:10.519  2063  2102 I Perf    : fps=3.5 worst=3 drawn=15/37 queue=0 build=72ms verts=45k cam=0.3,1.6,1.7 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=15 noMesh=0 culled=22
10-29 04:39:12.757  2063  2102 I Perf    : fps=3.6 worst=4 drawn=15/37 queue=0 build=72ms verts=45k cam=0.3,1.6,1.7 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=15 noMesh=0 culled=22
10-29 04:39:14.992  2063  2102 I Perf    : fps=3.6 worst=3 drawn=15/37 queue=0 build=72ms verts=45k cam=0.3,1.6,1.7 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=15 noMesh=0 culled=22
10-29 04:39:17.254  2063  2102 I Perf    : fps=3.5 worst=3 drawn=15/37 queue=0 build=72ms verts=45k cam=0.3,1.6,1.7 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=15 noMesh=0 culled=22
10-29 04:39:19.305  2063  2102 I Perf    : fps=3.4 worst=3 drawn=15/37 queue=0 build=72ms verts=45k cam=0.3,1.6,1.7 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=15 noMesh=0 culled=22
```
## Step log
```
 worst=2 drawn=3/5 queue=2 build=114ms verts=9k cam=0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=3 noMesh=0 culled=0
10-29 04:38:30.104  2063  2102 I Perf    : fps=3.0 worst=3 drawn=11/17 queue=1 build=9ms verts=34k cam=0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=11 noMesh=0 culled=4
10-29 04:38:32.125  2063  2102 I Perf    : fps=3.0 worst=3 drawn=13/29 queue=2 build=30ms verts=40k cam=0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=14
10-29 04:38:34.190  2063  2102 I Perf    : fps=2.9 worst=3 drawn=14/37 queue=0 build=72ms verts=43k cam=0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 04:38:36.344  2063  2102 I Perf    : fps=2.8 worst=3 drawn=14/37 queue=0 build=72ms verts=43k cam=0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 04:38:38.435  2063  2102 I Perf    : fps=2.9 worst=3 drawn=14/37 queue=0 build=72ms verts=43k cam=0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 04:38:40.454  2063  2102 I Perf    : fps=3.0 worst=3 drawn=14/37 queue=0 build=72ms verts=43k cam=0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 04:38:42.659  2063  2102 I Perf    : fps=2.7 worst=2 drawn=14/37 queue=0 build=72ms verts=43k cam=0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 04:38:44.800  2063  2102 I Perf    : fps=2.8 worst=2 drawn=14/37 queue=0 build=72ms verts=43k cam=0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 04:38:46.959  2063  2102 I Perf    : fps=2.8 worst=2 drawn=14/37 queue=0 build=72ms verts=43k cam=0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 04:38:49.142  2447  2447 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-29 04:38:49.143  2063  2102 I Perf    : fps=2.8 worst=3 drawn=14/37 queue=0 build=72ms verts=43k cam=0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 04:38:51.277  2063  2102 I Perf    : fps=2.8 worst=2 drawn=14/37 queue=0 build=72ms verts=43k cam=0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 04:38:53.442  2063  2102 I Perf    : fps=2.8 worst=2 drawn=14/37 queue=0 build=72ms verts=43k cam=0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-29 04:38:54.936  2468  2468 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 04:38:54.959  2468  2468 I AndroidRuntime: Using default boot image
10-29 04:38:54.960  2468  2468 I AndroidRuntime: Leaving lock profiling enabled
10-29 04:38:55.087  2468  2468 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 04:38:55.553  2063  2102 I Perf    : fps=2.8 worst=3 drawn=13/37 queue=0 build=72ms verts=39k cam=0.5,1.6,1.5 yaw=2.10 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-29 04:38:55.743  2468  2468 D AndroidRuntime: Shutting down VM
10-29 04:38:57.598  2063  2102 I Perf    : fps=3.4 worst=3 drawn=15/37 queue=0 build=72ms verts=45k cam=0.5,1.6,1.5 yaw=2.57 pitch=0.00 torch=1 ready=true drawn=15 noMesh=0 culled=22
10-29 04:38:59.768  2063  2102 I Perf    : fps=3.7 worst=4 drawn=15/37 queue=0 build=72ms verts=45k cam=0.5,1.6,1.5 yaw=2.57 pitch=0.00 torch=1 ready=true drawn=15 noMesh=0 culled=22
10-29 04:39:00.554  2487  2487 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 04:39:00.564  2487  2487 I AndroidRuntime: Using default boot image
10-29 04:39:00.565  2487  2487 I AndroidRuntime: Leaving lock profiling enabled
10-29 04:39:00.672  2487  2487 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 04:39:00.676  2487  2487 D AndroidRuntime: Shutting down VM
10-29 04:39:01.782  2063  2102 I Perf    : fps=3.5 worst=3 drawn=15/37 queue=0 build=72ms verts=45k cam=0.5,1.6,1.5 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=15 noMesh=0 culled=22
10-29 04:39:04.023  2063  2102 I Perf    : fps=3.6 worst=3 drawn=15/37 queue=0 build=72ms verts=45k cam=0.5,1.6,1.5 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=15 noMesh=0 culled=22
10-29 04:39:04.064  2501  2501 I ContactsPerf: VoicemailContentProvider.onCreate start
10-29 04:39:04.065  2501  2501 I ContactsPerf: VoicemailContentProvider.onCreate finish
10-29 04:39:06.165  2532  2532 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-29 04:39:06.168  2532  2532 I AndroidRuntime: Using default boot image
10-29 04:39:06.168  2532  2532 I AndroidRuntime: Leaving lock profiling enabled
10-29 04:39:06.247  2532  2532 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-29 04:39:06.312  2063  2102 I Perf    : fps=3.5 worst=3 drawn=15/37 queue=0 build=72ms verts=45k cam=0.5,1.6,1.5 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=15 noMesh=0 culled=22
10-29 04:39:07.861  2532  2532 D AndroidRuntime: Shutting down VM
10-29 04:39:08.518  2063  2102 I Perf    : fps=3.6 worst=4 drawn=15/37 queue=0 build=72ms verts=45k cam=0.3,1.6,1.7 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=15 noMesh=0 culled=22
10-29 04:39:10.519  2063  2102 I Perf    : fps=3.5 worst=3 drawn=15/37 queue=0 build=72ms verts=45k cam=0.3,1.6,1.7 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=15 noMesh=0 culled=22
10-29 04:39:12.757  2063  2102 I Perf    : fps=3.6 worst=4 drawn=15/37 queue=0 build=72ms verts=45k cam=0.3,1.6,1.7 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=15 noMesh=0 culled=22
10-29 04:39:13.043  2550  2550 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-29 04:39:14.992  2063  2102 I Perf    : fps=3.6 worst=3 drawn=15/37 queue=0 build=72ms verts=45k cam=0.3,1.6,1.7 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=15 noMesh=0 culled=22
10-29 04:39:17.254  2063  2102 I Perf    : fps=3.5 worst=3 drawn=15/37 queue=0 build=72ms verts=45k cam=0.3,1.6,1.7 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=15 noMesh=0 culled=22
10-29 04:39:19.305  2063  2102 I Perf    : fps=3.4 worst=3 drawn=15/37 queue=0 build=72ms verts=45k cam=0.3,1.6,1.7 yaw=2.57 pitch=0.00 torch=0 ready=true drawn=15 noMesh=0 culled=22
+ '[' -z 2063 ']'
+ grep -qa 'FATAL EXCEPTION' emulator-shots/logcat.txt
+ '[' 0 '!=' 0 ']'
+ echo 'OK: the app is alive, rendering, and the touch controls work'
OK: the app is alive, rendering, and the touch controls work
```
