# Emulator smoke test diagnostics

Commit: 6254b4eb225030487f556fb76571c48622516b90
Outcome: success

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 28 12:03 /dev/kvm
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
INFO         | The active JSON Web Key Sets can be found here: /run/user/1001/avd/running/2761/jwks/b60aedae-e12e-4004-a58d-37d538e4f80f/active.jwk
INFO         | Scanning /run/user/1001/avd/running/2761/jwks/b60aedae-e12e-4004-a58d-37d538e4f80f for jwk keys.
INFO         | Started GRPC server at 127.0.0.1:8554, security: Local, auth: +token
INFO         | Advertising in: /run/user/1001/avd/running/pid_2761.ini
INFO         | Setting display: 0 configuration to: 1080x1920, dpi: 420x420 
INFO         | setDisplayActiveConfig: id:0, 1080x1920
INFO         | emulatorSetupEnvironment: Environment scene is not required
USER_INFO    | Emulator is performing a full startup. This may take upto two minutes, or more.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
INFO         | Boot completed in 26711 ms
INFO         | Increasing screen off timeout, logcat buffer size to 2M.
```
## Logcat (filtered)
```
10-28 12:07:21.125   507   547 W ActivityManager: Slow operation: 75ms so far, now at startProcess: done updating pids map
10-28 12:07:21.209   435  2034 I iorapd  : Perfetto TraceBuffer saved to file: /data/misc/iorapd/com.backrooms.gdx.debug/1/com.backrooms.gdx.android.AndroidLauncher/raw_traces/1793189241208670729.perfetto_trace.pb
10-28 12:07:21.253   507  1725 I ActivityManager: Finished processing BOOT_COMPLETED for u0
10-28 12:07:21.255   507  1725 I ActivityManager: Killing 1635:com.android.printspooler/u0a87 (adj 985): empty #17
10-28 12:07:34.401   507   535 I ActivityManager: Killing 1319:com.android.cellbroadcastreceiver/u0a65 (adj 985): empty #17
10-28 12:07:38.996   507   537 I ActivityManager: Killing 1656:android.process.acore/u0a63 (adj 985): empty #17
10-28 12:07:38.998   507   537 I ActivityManager: Killing 1974:com.android.quicksearchbox/u0a101 (adj 985): empty #18
10-28 12:07:39.184  2380  2380 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 12:07:39.190  2380  2380 I AndroidRuntime: Using default boot image
10-28 12:07:39.190  2380  2380 I AndroidRuntime: Leaving lock profiling enabled
10-28 12:07:39.346  2380  2380 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 12:07:39.393  2380  2380 D AndroidRuntime: Shutting down VM
10-28 12:07:39.930  2038  2094 I BackroomsGL: world shader compiled
10-28 12:07:40.003  2038  2094 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 12:07:40.003  2038  2094 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 12:07:41.846  2038  2094 I Perf    : fps=2.5 worst=9 drawn=4/6 queue=2 build=40ms verts=10k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=4 noMesh=0 culled=0
10-28 12:07:43.883  2038  2094 I Perf    : fps=5.4 worst=5 drawn=14/26 queue=2 build=24ms verts=36k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=10
10-28 12:07:43.894   507   993 I ActivityManager: Killing 2109:com.android.calendar/u0a109 (adj 975): empty #17
10-28 12:07:45.919  2038  2094 I Perf    : fps=5.4 worst=6 drawn=18/37 queue=0 build=184ms verts=47k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:07:48.070  2038  2094 I Perf    : fps=5.6 worst=5 drawn=18/37 queue=0 build=184ms verts=47k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:07:50.172  2038  2094 I Perf    : fps=5.7 worst=6 drawn=18/37 queue=0 build=184ms verts=47k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:07:52.330  2038  2094 I Perf    : fps=5.6 worst=6 drawn=18/37 queue=0 build=184ms verts=47k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:07:54.476  2038  2094 I Perf    : fps=5.6 worst=6 drawn=18/37 queue=0 build=184ms verts=47k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:07:56.627  2038  2094 I Perf    : fps=5.6 worst=5 drawn=18/37 queue=0 build=184ms verts=47k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:07:58.775  2038  2094 I Perf    : fps=5.6 worst=5 drawn=18/37 queue=0 build=184ms verts=47k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:08:00.957  2038  2094 I Perf    : fps=5.5 worst=6 drawn=18/37 queue=0 build=184ms verts=47k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:08:03.109  2038  2094 I Perf    : fps=5.6 worst=6 drawn=18/37 queue=0 build=184ms verts=47k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:08:05.242  2038  2094 I Perf    : fps=5.6 worst=6 drawn=18/37 queue=0 build=184ms verts=47k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:08:07.307  2038  2094 I Perf    : fps=5.3 worst=5 drawn=18/37 queue=0 build=184ms verts=47k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:08:09.460  2038  2094 I Perf    : fps=5.1 worst=5 drawn=18/37 queue=0 build=184ms verts=47k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:08:11.567  2038  2094 I Perf    : fps=5.2 worst=5 drawn=18/37 queue=0 build=184ms verts=47k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:08:11.976  2421  2421 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 12:08:11.989  2421  2421 I AndroidRuntime: Using default boot image
10-28 12:08:11.989  2421  2421 I AndroidRuntime: Leaving lock profiling enabled
10-28 12:08:12.181  2421  2421 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 12:08:12.811  2421  2421 D AndroidRuntime: Shutting down VM
10-28 12:08:13.640  2038  2094 I Perf    : fps=5.3 worst=6 drawn=13/37 queue=0 build=184ms verts=33k cam=-1.5,1.6,-1.5 yaw=2.62 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 12:08:15.664  2038  2094 I Perf    : fps=5.4 worst=5 drawn=13/37 queue=0 build=184ms verts=33k cam=-1.5,1.6,-1.5 yaw=2.62 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 12:08:17.694  2038  2094 I Perf    : fps=5.4 worst=6 drawn=13/37 queue=0 build=184ms verts=33k cam=-1.5,1.6,-1.5 yaw=2.62 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 12:08:17.776  2448  2448 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 12:08:17.785  2448  2448 I AndroidRuntime: Using default boot image
10-28 12:08:17.785  2448  2448 I AndroidRuntime: Leaving lock profiling enabled
10-28 12:08:17.942  2448  2448 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 12:08:18.097  2448  2448 D AndroidRuntime: Shutting down VM
10-28 12:08:19.770  2038  2094 I Perf    : fps=5.3 worst=5 drawn=13/37 queue=0 build=184ms verts=33k cam=-1.5,1.6,-1.5 yaw=2.62 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 12:08:21.811  2038  2094 I Perf    : fps=5.4 worst=5 drawn=13/37 queue=0 build=184ms verts=33k cam=-1.5,1.6,-1.5 yaw=2.62 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 12:08:22.383   507   547 I ActivityManager: Start proc 2462:android.process.acore/u0a63 for content provider {com.android.providers.contacts/com.android.providers.contacts.VoicemailContentProvider}
10-28 12:08:22.573  2462  2462 I ContactsPerf: VoicemailContentProvider.onCreate start
10-28 12:08:22.575  2462  2462 I ContactsPerf: VoicemailContentProvider.onCreate finish
10-28 12:08:23.107  2492  2492 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 12:08:23.109  2492  2492 I AndroidRuntime: Using default boot image
10-28 12:08:23.110  2492  2492 I AndroidRuntime: Leaving lock profiling enabled
10-28 12:08:23.221  2492  2492 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 12:08:23.960  2038  2094 I Perf    : fps=5.1 worst=5 drawn=13/37 queue=0 build=184ms verts=33k cam=-1.6,1.6,-1.4 yaw=2.62 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 12:08:24.805  2492  2492 D AndroidRuntime: Shutting down VM
10-28 12:08:26.134  2038  2094 I Perf    : fps=5.5 worst=6 drawn=13/37 queue=0 build=184ms verts=33k cam=-2.3,1.6,-0.1 yaw=2.62 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 12:08:28.161  2038  2094 I Perf    : fps=5.4 worst=5 drawn=13/37 queue=0 build=184ms verts=33k cam=-2.3,1.6,-0.1 yaw=2.62 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 12:08:30.285  2038  2094 I Perf    : fps=5.2 worst=5 drawn=13/37 queue=0 build=184ms verts=33k cam=-2.3,1.6,-0.1 yaw=2.62 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 12:08:32.303  2038  2094 I Perf    : fps=5.0 worst=5 drawn=13/37 queue=0 build=184ms verts=33k cam=-2.3,1.6,-0.1 yaw=2.62 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 12:08:34.317  2038  2094 I Perf    : fps=5.0 worst=5 drawn=13/37 queue=0 build=184ms verts=33k cam=-2.3,1.6,-0.1 yaw=2.62 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
```
## Step log
```
ch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=10
10-28 12:07:45.919  2038  2094 I Perf    : fps=5.4 worst=6 drawn=18/37 queue=0 build=184ms verts=47k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:07:48.070  2038  2094 I Perf    : fps=5.6 worst=5 drawn=18/37 queue=0 build=184ms verts=47k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:07:50.172  2038  2094 I Perf    : fps=5.7 worst=6 drawn=18/37 queue=0 build=184ms verts=47k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:07:52.330  2038  2094 I Perf    : fps=5.6 worst=6 drawn=18/37 queue=0 build=184ms verts=47k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:07:54.476  2038  2094 I Perf    : fps=5.6 worst=6 drawn=18/37 queue=0 build=184ms verts=47k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:07:56.627  2038  2094 I Perf    : fps=5.6 worst=5 drawn=18/37 queue=0 build=184ms verts=47k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:07:58.775  2038  2094 I Perf    : fps=5.6 worst=5 drawn=18/37 queue=0 build=184ms verts=47k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:08:00.957  2038  2094 I Perf    : fps=5.5 worst=6 drawn=18/37 queue=0 build=184ms verts=47k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:08:03.109  2038  2094 I Perf    : fps=5.6 worst=6 drawn=18/37 queue=0 build=184ms verts=47k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:08:05.242  2038  2094 I Perf    : fps=5.6 worst=6 drawn=18/37 queue=0 build=184ms verts=47k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:08:05.428  2407  2407 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 12:08:07.307  2038  2094 I Perf    : fps=5.3 worst=5 drawn=18/37 queue=0 build=184ms verts=47k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:08:09.460  2038  2094 I Perf    : fps=5.1 worst=5 drawn=18/37 queue=0 build=184ms verts=47k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:08:11.567  2038  2094 I Perf    : fps=5.2 worst=5 drawn=18/37 queue=0 build=184ms verts=47k cam=-1.5,1.6,-1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 12:08:11.976  2421  2421 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 12:08:11.989  2421  2421 I AndroidRuntime: Using default boot image
10-28 12:08:11.989  2421  2421 I AndroidRuntime: Leaving lock profiling enabled
10-28 12:08:12.181  2421  2421 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 12:08:12.811  2421  2421 D AndroidRuntime: Shutting down VM
10-28 12:08:13.640  2038  2094 I Perf    : fps=5.3 worst=6 drawn=13/37 queue=0 build=184ms verts=33k cam=-1.5,1.6,-1.5 yaw=2.62 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 12:08:15.664  2038  2094 I Perf    : fps=5.4 worst=5 drawn=13/37 queue=0 build=184ms verts=33k cam=-1.5,1.6,-1.5 yaw=2.62 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 12:08:17.694  2038  2094 I Perf    : fps=5.4 worst=6 drawn=13/37 queue=0 build=184ms verts=33k cam=-1.5,1.6,-1.5 yaw=2.62 pitch=0.00 torch=1 ready=true drawn=13 noMesh=0 culled=24
10-28 12:08:17.776  2448  2448 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 12:08:17.785  2448  2448 I AndroidRuntime: Using default boot image
10-28 12:08:17.785  2448  2448 I AndroidRuntime: Leaving lock profiling enabled
10-28 12:08:17.942  2448  2448 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 12:08:18.097  2448  2448 D AndroidRuntime: Shutting down VM
10-28 12:08:19.770  2038  2094 I Perf    : fps=5.3 worst=5 drawn=13/37 queue=0 build=184ms verts=33k cam=-1.5,1.6,-1.5 yaw=2.62 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 12:08:21.811  2038  2094 I Perf    : fps=5.4 worst=5 drawn=13/37 queue=0 build=184ms verts=33k cam=-1.5,1.6,-1.5 yaw=2.62 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 12:08:22.573  2462  2462 I ContactsPerf: VoicemailContentProvider.onCreate start
10-28 12:08:22.575  2462  2462 I ContactsPerf: VoicemailContentProvider.onCreate finish
10-28 12:08:23.107  2492  2492 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 12:08:23.109  2492  2492 I AndroidRuntime: Using default boot image
10-28 12:08:23.110  2492  2492 I AndroidRuntime: Leaving lock profiling enabled
10-28 12:08:23.221  2492  2492 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 12:08:23.960  2038  2094 I Perf    : fps=5.1 worst=5 drawn=13/37 queue=0 build=184ms verts=33k cam=-1.6,1.6,-1.4 yaw=2.62 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 12:08:24.805  2492  2492 D AndroidRuntime: Shutting down VM
10-28 12:08:26.134  2038  2094 I Perf    : fps=5.5 worst=6 drawn=13/37 queue=0 build=184ms verts=33k cam=-2.3,1.6,-0.1 yaw=2.62 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 12:08:28.161  2038  2094 I Perf    : fps=5.4 worst=5 drawn=13/37 queue=0 build=184ms verts=33k cam=-2.3,1.6,-0.1 yaw=2.62 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 12:08:30.209  2512  2512 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 12:08:30.285  2038  2094 I Perf    : fps=5.2 worst=5 drawn=13/37 queue=0 build=184ms verts=33k cam=-2.3,1.6,-0.1 yaw=2.62 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 12:08:32.303  2038  2094 I Perf    : fps=5.0 worst=5 drawn=13/37 queue=0 build=184ms verts=33k cam=-2.3,1.6,-0.1 yaw=2.62 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
10-28 12:08:34.317  2038  2094 I Perf    : fps=5.0 worst=5 drawn=13/37 queue=0 build=184ms verts=33k cam=-2.3,1.6,-0.1 yaw=2.62 pitch=0.00 torch=0 ready=true drawn=13 noMesh=0 culled=24
+ '[' -z 2038 ']'
+ grep -qa 'FATAL EXCEPTION' emulator-shots/logcat.txt
+ '[' 0 '!=' 0 ']'
+ echo 'OK: the app is alive, rendering, and the touch controls work'
OK: the app is alive, rendering, and the touch controls work
```
