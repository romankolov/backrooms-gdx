# Emulator smoke test diagnostics

Commit: 135d17fb619ceb57b3285829614701651e7c420c
Outcome: success

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 28 20:44 /dev/kvm
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
INFO         | The active JSON Web Key Sets can be found here: /run/user/1001/avd/running/3233/jwks/c15783f7-9453-44b2-97ae-8cbdb5ffb0c2/active.jwk
INFO         | Scanning /run/user/1001/avd/running/3233/jwks/c15783f7-9453-44b2-97ae-8cbdb5ffb0c2 for jwk keys.
INFO         | Started GRPC server at 127.0.0.1:8554, security: Local, auth: +token
INFO         | Advertising in: /run/user/1001/avd/running/pid_3233.ini
INFO         | Setting display: 0 configuration to: 1080x1920, dpi: 420x420 
INFO         | setDisplayActiveConfig: id:0, 1080x1920
INFO         | emulatorSetupEnvironment: Environment scene is not required
USER_INFO    | Emulator is performing a full startup. This may take upto two minutes, or more.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
INFO         | Boot completed in 26564 ms
INFO         | Increasing screen off timeout, logcat buffer size to 2M.
```
## Logcat (filtered)
```
10-28 20:47:52.141   509   648 I ActivityManager: Killing 1939:com.android.localtransport/1000 (adj 985): empty #17
10-28 20:47:52.271   509   549 I ActivityManager: Start proc 2370:com.android.traceur/u0a91 for broadcast {com.android.traceur/com.android.traceur.Receiver}
10-28 20:47:52.434   509   648 I ActivityManager: Finished processing BOOT_COMPLETED for u0
10-28 20:47:52.436   509   648 I ActivityManager: Killing 1727:com.android.provision/u0a116 (adj 985): empty #17
10-28 20:47:53.081   420  2060 I iorapd  : Perfetto TraceBuffer saved to file: /data/misc/iorapd/com.backrooms.gdx.debug/1/com.backrooms.gdx.android.AndroidLauncher/raw_traces/1793220473081608222.perfetto_trace.pb
10-28 20:48:07.013   509   547 I ActivityManager: Killing 1292:com.android.cellbroadcastreceiver/u0a64 (adj 985): empty #17
10-28 20:48:10.106   509   539 I ActivityManager: Killing 1684:android.process.acore/u0a65 (adj 985): empty #17
10-28 20:48:10.106   509   539 I ActivityManager: Killing 1991:com.android.quicksearchbox/u0a101 (adj 985): empty #18
10-28 20:48:12.261  2432  2432 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 20:48:12.274  2432  2432 I AndroidRuntime: Using default boot image
10-28 20:48:12.274  2432  2432 I AndroidRuntime: Leaving lock profiling enabled
10-28 20:48:12.417  2432  2432 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 20:48:12.430  2432  2432 D AndroidRuntime: Shutting down VM
10-28 20:48:13.083  2067  2141 I BackroomsGL: world shader compiled
10-28 20:48:13.617  2067  2141 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 20:48:13.617  2067  2141 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 20:48:14.950   509   648 I ActivityManager: Killing 2092:com.android.calendar/u0a108 (adj 975): empty #17
10-28 20:48:15.353  2067  2141 I Perf    : fps=2.1 worst=10 drawn=2/4 queue=2 build=31ms verts=5k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=2 noMesh=0 culled=0
10-28 20:48:17.612  2067  2141 I Perf    : fps=3.1 worst=3 drawn=8/14 queue=2 build=21ms verts=21k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=8 noMesh=0 culled=4
10-28 20:48:19.901  2067  2141 I Perf    : fps=3.1 worst=3 drawn=11/26 queue=2 build=16ms verts=29k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=11 noMesh=0 culled=14
10-28 20:48:21.941  2067  2141 I Perf    : fps=3.0 worst=3 drawn=14/37 queue=0 build=17ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=22
10-28 20:48:24.233  2067  2141 I Perf    : fps=3.0 worst=3 drawn=14/37 queue=0 build=17ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:48:26.539  2067  2141 I Perf    : fps=3.0 worst=3 drawn=14/37 queue=0 build=17ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:48:28.671  2067  2141 I Perf    : fps=2.8 worst=3 drawn=14/37 queue=0 build=17ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:48:30.961  2067  2141 I Perf    : fps=3.1 worst=3 drawn=14/37 queue=0 build=17ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:48:33.248  2067  2141 I Perf    : fps=3.1 worst=3 drawn=14/37 queue=0 build=17ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:48:35.282  2067  2141 I Perf    : fps=2.9 worst=3 drawn=14/37 queue=0 build=17ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:48:37.548  2067  2141 I Perf    : fps=3.1 worst=3 drawn=14/37 queue=0 build=17ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:48:39.853  2067  2141 I Perf    : fps=3.0 worst=3 drawn=14/37 queue=0 build=17ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:48:42.157  2067  2141 I Perf    : fps=3.0 worst=3 drawn=14/37 queue=0 build=17ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:48:44.114  2486  2486 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 20:48:44.125  2486  2486 I AndroidRuntime: Using default boot image
10-28 20:48:44.125  2486  2486 I AndroidRuntime: Leaving lock profiling enabled
10-28 20:48:44.169  2067  2141 I Perf    : fps=3.0 worst=3 drawn=14/37 queue=0 build=17ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:48:44.198  2486  2486 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 20:48:44.815  2486  2486 D AndroidRuntime: Shutting down VM
10-28 20:48:46.325  2067  2141 I Perf    : fps=3.2 worst=3 drawn=14/37 queue=0 build=17ms verts=36k cam=1.5,1.6,1.5 yaw=2.15 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:48:48.530  2067  2141 I Perf    : fps=3.6 worst=3 drawn=14/37 queue=0 build=17ms verts=36k cam=1.5,1.6,1.5 yaw=2.15 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:48:49.761  2502  2502 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 20:48:49.764  2502  2502 I AndroidRuntime: Using default boot image
10-28 20:48:49.764  2502  2502 I AndroidRuntime: Leaving lock profiling enabled
10-28 20:48:49.828  2502  2502 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 20:48:49.929  2502  2502 D AndroidRuntime: Shutting down VM
10-28 20:48:50.604  2067  2141 I Perf    : fps=3.4 worst=3 drawn=14/37 queue=0 build=17ms verts=36k cam=1.5,1.6,1.5 yaw=2.15 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-28 20:48:52.646   509   549 I ActivityManager: Start proc 2515:android.process.acore/u0a65 for content provider {com.android.providers.contacts/com.android.providers.contacts.VoicemailContentProvider}
10-28 20:48:52.647  2067  2141 I Perf    : fps=3.4 worst=3 drawn=14/37 queue=0 build=17ms verts=36k cam=1.5,1.6,1.5 yaw=2.15 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-28 20:48:52.695  2515  2515 I ContactsPerf: VoicemailContentProvider.onCreate start
10-28 20:48:52.754  2515  2515 I ContactsPerf: VoicemailContentProvider.onCreate finish
10-28 20:48:54.716  2067  2141 I Perf    : fps=3.4 worst=3 drawn=14/37 queue=0 build=17ms verts=36k cam=1.5,1.6,1.5 yaw=2.15 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-28 20:48:54.906  2546  2546 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 20:48:54.915  2546  2546 I AndroidRuntime: Using default boot image
10-28 20:48:54.915  2546  2546 I AndroidRuntime: Leaving lock profiling enabled
10-28 20:48:55.117  2546  2546 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 20:48:56.723  2067  2141 I Perf    : fps=3.5 worst=4 drawn=14/37 queue=0 build=17ms verts=36k cam=1.1,1.7,1.8 yaw=2.15 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-28 20:48:57.028  2546  2546 D AndroidRuntime: Shutting down VM
10-28 20:48:58.939  2067  2141 I Perf    : fps=3.2 worst=3 drawn=14/37 queue=0 build=17ms verts=36k cam=0.7,1.6,2.0 yaw=2.15 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-28 20:49:01.107  2067  2141 I Perf    : fps=3.2 worst=3 drawn=14/37 queue=0 build=17ms verts=36k cam=0.7,1.6,2.0 yaw=2.15 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-28 20:49:03.229  2067  2141 I Perf    : fps=3.3 worst=3 drawn=14/37 queue=0 build=17ms verts=36k cam=0.7,1.6,2.0 yaw=2.15 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-28 20:49:05.355  2067  2141 I Perf    : fps=3.3 worst=3 drawn=14/37 queue=0 build=17ms verts=36k cam=0.7,1.6,2.0 yaw=2.15 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-28 20:49:07.517  2067  2141 I Perf    : fps=3.2 worst=3 drawn=14/37 queue=0 build=17ms verts=36k cam=0.7,1.6,2.0 yaw=2.15 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
```
## Step log
```
1 worst=10 drawn=2/4 queue=2 build=31ms verts=5k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=2 noMesh=0 culled=0
10-28 20:48:17.612  2067  2141 I Perf    : fps=3.1 worst=3 drawn=8/14 queue=2 build=21ms verts=21k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=8 noMesh=0 culled=4
10-28 20:48:19.901  2067  2141 I Perf    : fps=3.1 worst=3 drawn=11/26 queue=2 build=16ms verts=29k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=11 noMesh=0 culled=14
10-28 20:48:21.941  2067  2141 I Perf    : fps=3.0 worst=3 drawn=14/37 queue=0 build=17ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=22
10-28 20:48:24.233  2067  2141 I Perf    : fps=3.0 worst=3 drawn=14/37 queue=0 build=17ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:48:26.539  2067  2141 I Perf    : fps=3.0 worst=3 drawn=14/37 queue=0 build=17ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:48:28.671  2067  2141 I Perf    : fps=2.8 worst=3 drawn=14/37 queue=0 build=17ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:48:30.961  2067  2141 I Perf    : fps=3.1 worst=3 drawn=14/37 queue=0 build=17ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:48:33.248  2067  2141 I Perf    : fps=3.1 worst=3 drawn=14/37 queue=0 build=17ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:48:35.282  2067  2141 I Perf    : fps=2.9 worst=3 drawn=14/37 queue=0 build=17ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:48:37.548  2067  2141 I Perf    : fps=3.1 worst=3 drawn=14/37 queue=0 build=17ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:48:38.520  2462  2462 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 20:48:39.853  2067  2141 I Perf    : fps=3.0 worst=3 drawn=14/37 queue=0 build=17ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:48:42.157  2067  2141 I Perf    : fps=3.0 worst=3 drawn=14/37 queue=0 build=17ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:48:44.114  2486  2486 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 20:48:44.125  2486  2486 I AndroidRuntime: Using default boot image
10-28 20:48:44.125  2486  2486 I AndroidRuntime: Leaving lock profiling enabled
10-28 20:48:44.169  2067  2141 I Perf    : fps=3.0 worst=3 drawn=14/37 queue=0 build=17ms verts=36k cam=1.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:48:44.198  2486  2486 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 20:48:44.815  2486  2486 D AndroidRuntime: Shutting down VM
10-28 20:48:46.325  2067  2141 I Perf    : fps=3.2 worst=3 drawn=14/37 queue=0 build=17ms verts=36k cam=1.5,1.6,1.5 yaw=2.15 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:48:48.530  2067  2141 I Perf    : fps=3.6 worst=3 drawn=14/37 queue=0 build=17ms verts=36k cam=1.5,1.6,1.5 yaw=2.15 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:48:49.761  2502  2502 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 20:48:49.764  2502  2502 I AndroidRuntime: Using default boot image
10-28 20:48:49.764  2502  2502 I AndroidRuntime: Leaving lock profiling enabled
10-28 20:48:49.828  2502  2502 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 20:48:49.929  2502  2502 D AndroidRuntime: Shutting down VM
10-28 20:48:50.604  2067  2141 I Perf    : fps=3.4 worst=3 drawn=14/37 queue=0 build=17ms verts=36k cam=1.5,1.6,1.5 yaw=2.15 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-28 20:48:52.647  2067  2141 I Perf    : fps=3.4 worst=3 drawn=14/37 queue=0 build=17ms verts=36k cam=1.5,1.6,1.5 yaw=2.15 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-28 20:48:52.695  2515  2515 I ContactsPerf: VoicemailContentProvider.onCreate start
10-28 20:48:52.754  2515  2515 I ContactsPerf: VoicemailContentProvider.onCreate finish
10-28 20:48:54.716  2067  2141 I Perf    : fps=3.4 worst=3 drawn=14/37 queue=0 build=17ms verts=36k cam=1.5,1.6,1.5 yaw=2.15 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-28 20:48:54.906  2546  2546 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 20:48:54.915  2546  2546 I AndroidRuntime: Using default boot image
10-28 20:48:54.915  2546  2546 I AndroidRuntime: Leaving lock profiling enabled
10-28 20:48:55.117  2546  2546 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 20:48:56.723  2067  2141 I Perf    : fps=3.5 worst=4 drawn=14/37 queue=0 build=17ms verts=36k cam=1.1,1.7,1.8 yaw=2.15 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-28 20:48:57.028  2546  2546 D AndroidRuntime: Shutting down VM
10-28 20:48:58.939  2067  2141 I Perf    : fps=3.2 worst=3 drawn=14/37 queue=0 build=17ms verts=36k cam=0.7,1.6,2.0 yaw=2.15 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-28 20:49:01.107  2067  2141 I Perf    : fps=3.2 worst=3 drawn=14/37 queue=0 build=17ms verts=36k cam=0.7,1.6,2.0 yaw=2.15 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-28 20:49:02.630  2564  2564 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 20:49:03.229  2067  2141 I Perf    : fps=3.3 worst=3 drawn=14/37 queue=0 build=17ms verts=36k cam=0.7,1.6,2.0 yaw=2.15 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-28 20:49:05.355  2067  2141 I Perf    : fps=3.3 worst=3 drawn=14/37 queue=0 build=17ms verts=36k cam=0.7,1.6,2.0 yaw=2.15 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
10-28 20:49:07.517  2067  2141 I Perf    : fps=3.2 worst=3 drawn=14/37 queue=0 build=17ms verts=36k cam=0.7,1.6,2.0 yaw=2.15 pitch=0.00 torch=0 ready=true drawn=14 noMesh=0 culled=23
+ '[' -z 2067 ']'
+ grep -qa 'FATAL EXCEPTION' emulator-shots/logcat.txt
+ '[' 0 '!=' 0 ']'
+ echo 'OK: the app is alive, rendering, and the touch controls work'
OK: the app is alive, rendering, and the touch controls work
```
