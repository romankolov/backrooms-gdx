# Emulator smoke test diagnostics

Commit: 86d3250397032181394d9194ad6054fd24e2a2d0
Outcome: success

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 28 08:19 /dev/kvm
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
INFO         | The active JSON Web Key Sets can be found here: /run/user/1001/avd/running/2762/jwks/3182f6e2-1e34-4f0c-a22d-b6cad4905f0a/active.jwk
INFO         | Scanning /run/user/1001/avd/running/2762/jwks/3182f6e2-1e34-4f0c-a22d-b6cad4905f0a for jwk keys.
INFO         | Started GRPC server at 127.0.0.1:8554, security: Local, auth: +token
INFO         | Advertising in: /run/user/1001/avd/running/pid_2762.ini
INFO         | Setting display: 0 configuration to: 1080x1920, dpi: 420x420 
INFO         | setDisplayActiveConfig: id:0, 1080x1920
INFO         | emulatorSetupEnvironment: Environment scene is not required
USER_INFO    | Emulator is performing a full startup. This may take upto two minutes, or more.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
INFO         | Boot completed in 20482 ms
INFO         | Increasing screen off timeout, logcat buffer size to 2M.
```
## Logcat (filtered)
```
10-28 08:28:09.266   501  1496 I ActivityManager: Finished processing BOOT_COMPLETED for u0
10-28 08:28:09.267   501  1496 I ActivityManager: Killing 1926:com.android.quicksearchbox/u0a103 (adj 985): empty #17
10-28 08:28:10.178   501   501 W system_server: Long monitor contention with owner android.bg (539) at int com.android.server.am.ActivityManagerService.broadcastIntentWithFeature(android.app.IApplicationThread, java.lang.String, android.content.Intent, java.lang.String, android.content.IIntentReceiver, int, java.lang.String, android.os.Bundle, java.lang.String[], int, android.os.Bundle, boolean, boolean, int)(ActivityManagerService.java:16749) waiters=0 in boolean com.android.server.am.ActivityManagerService.isAppBad(android.content.pm.ApplicationInfo) for 124ms
10-28 08:28:10.183   501  2128 W system_server: Long monitor contention with owner android.bg (539) at int com.android.server.am.ActivityManagerService.broadcastIntentWithFeature(android.app.IApplicationThread, java.lang.String, android.content.Intent, java.lang.String, android.content.IIntentReceiver, int, java.lang.String, android.os.Bundle, java.lang.String[], int, android.os.Bundle, boolean, boolean, int)(ActivityManagerService.java:16749) waiters=1 in void com.android.server.am.ActivityManagerService.unregisterReceiver(android.content.IIntentReceiver) for 119ms
10-28 08:28:11.284   422  2056 I iorapd  : Perfetto TraceBuffer saved to file: /data/misc/iorapd/com.backrooms.gdx.debug/1/com.backrooms.gdx.android.AndroidLauncher/raw_traces/1793176091284659800.perfetto_trace.pb
10-28 08:28:23.716   501   539 I ActivityManager: Killing 1265:com.android.cellbroadcastreceiver/u0a65 (adj 985): empty #17
10-28 08:28:24.916   501   635 I ActivityManager: Killing 2013:com.android.localtransport/1000 (adj 975): empty #17
10-28 08:28:28.460  2391  2391 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:28:28.463  2391  2391 I AndroidRuntime: Using default boot image
10-28 08:28:28.463  2391  2391 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:28:28.525  2391  2391 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:28:28.538  2391  2391 D AndroidRuntime: Shutting down VM
10-28 08:28:28.543  2391  2402 I AndroidRuntime: NOTE: attach of thread 'Binder:2391_1' failed
10-28 08:28:29.076  2074  2131 I BackroomsGL: world shader compiled
10-28 08:28:29.129  2074  2131 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 08:28:29.129  2074  2131 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 08:28:31.030  2074  2131 I Perf    : fps=7.4 worst=77 drawn=10/17 queue=2 build=12ms verts=26k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=10 noMesh=0 culled=5
10-28 08:28:33.037  2074  2131 I Perf    : fps=6.9 worst=7 drawn=14/37 queue=0 build=219ms verts=36k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=22
10-28 08:28:35.067  2074  2131 I Perf    : fps=7.4 worst=8 drawn=14/37 queue=0 build=219ms verts=36k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:28:37.172  2074  2131 I Perf    : fps=7.1 worst=8 drawn=14/37 queue=0 build=219ms verts=36k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:28:39.298  2074  2131 I Perf    : fps=7.5 worst=8 drawn=14/37 queue=0 build=219ms verts=36k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:28:41.417  2074  2131 I Perf    : fps=7.1 worst=8 drawn=14/37 queue=0 build=219ms verts=36k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:28:43.478  2074  2131 I Perf    : fps=7.3 worst=8 drawn=14/37 queue=0 build=219ms verts=36k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:28:45.565  2074  2131 I Perf    : fps=7.2 worst=8 drawn=14/37 queue=0 build=219ms verts=36k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:28:47.602  2074  2131 I Perf    : fps=7.4 worst=8 drawn=14/37 queue=0 build=219ms verts=36k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:28:49.751  2074  2131 I Perf    : fps=7.0 worst=7 drawn=14/37 queue=0 build=219ms verts=36k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:28:51.822  2074  2131 I Perf    : fps=7.2 worst=8 drawn=14/37 queue=0 build=219ms verts=36k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:28:53.890  2074  2131 I Perf    : fps=7.3 worst=8 drawn=14/37 queue=0 build=219ms verts=36k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:28:55.965  2074  2131 I Perf    : fps=6.7 worst=7 drawn=14/37 queue=0 build=219ms verts=36k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:28:58.020  2074  2131 I Perf    : fps=7.3 worst=8 drawn=14/37 queue=0 build=219ms verts=36k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:28:58.174  2421  2421 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:28:58.186  2421  2421 I AndroidRuntime: Using default boot image
10-28 08:28:58.187  2421  2421 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:28:58.395  2421  2421 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:28:59.092  2421  2421 D AndroidRuntime: Shutting down VM
10-28 08:29:00.038  2074  2131 I Perf    : fps=7.4 worst=9 drawn=18/37 queue=0 build=219ms verts=48k cam=-0.5,1.6,1.5 yaw=2.63 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:29:01.087   501   501 I ActivityManager: Killing 2040:com.android.calendar/u0a107 (adj 985): empty #17
10-28 08:29:01.088   501   501 I ActivityManager: Killing 1743:android.process.acore/u0a63 (adj 985): empty #18
10-28 08:29:02.139  2074  2131 I Perf    : fps=7.6 worst=9 drawn=18/37 queue=0 build=219ms verts=48k cam=-0.5,1.6,1.5 yaw=2.63 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:29:03.637  2456  2456 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:29:03.649  2456  2456 I AndroidRuntime: Using default boot image
10-28 08:29:03.649  2456  2456 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:29:03.709  2456  2456 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:29:03.745  2456  2456 D AndroidRuntime: Shutting down VM
10-28 08:29:04.239  2074  2131 I Perf    : fps=7.6 worst=9 drawn=18/37 queue=0 build=219ms verts=48k cam=-0.5,1.6,1.5 yaw=2.63 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 08:29:06.364  2074  2131 I Perf    : fps=7.5 worst=8 drawn=18/37 queue=0 build=219ms verts=48k cam=-0.5,1.6,1.5 yaw=2.63 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 08:29:08.410  2074  2131 I Perf    : fps=7.8 worst=9 drawn=18/37 queue=0 build=219ms verts=48k cam=-0.5,1.6,1.5 yaw=2.63 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 08:29:08.586  2473  2473 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:29:08.597  2473  2473 I AndroidRuntime: Using default boot image
10-28 08:29:08.597  2473  2473 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:29:08.684  2473  2473 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:29:10.252  2473  2473 D AndroidRuntime: Shutting down VM
10-28 08:29:10.454  2074  2131 I Perf    : fps=7.3 worst=9 drawn=18/37 queue=0 build=219ms verts=48k cam=-1.6,1.6,3.4 yaw=2.63 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 08:29:10.766   501   541 I ActivityManager: Start proc 2488:android.process.acore/u0a63 for content provider {com.android.providers.contacts/com.android.providers.contacts.VoicemailContentProvider}
10-28 08:29:10.836  2488  2488 I ContactsPerf: VoicemailContentProvider.onCreate start
10-28 08:29:10.838  2488  2488 I ContactsPerf: VoicemailContentProvider.onCreate finish
10-28 08:29:12.470  2074  2131 I Perf    : fps=6.4 worst=7 drawn=18/37 queue=0 build=219ms verts=48k cam=-1.6,1.6,3.5 yaw=2.63 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 08:29:14.474  2074  2131 I Perf    : fps=6.5 worst=7 drawn=18/37 queue=0 build=219ms verts=48k cam=-1.6,1.6,3.5 yaw=2.63 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 08:29:16.505  2074  2131 I Perf    : fps=5.9 worst=6 drawn=18/37 queue=0 build=219ms verts=48k cam=-1.6,1.6,3.5 yaw=2.63 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 08:29:18.665  2074  2131 I Perf    : fps=6.5 worst=7 drawn=18/37 queue=0 build=219ms verts=48k cam=-1.6,1.6,3.5 yaw=2.63 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
```
## Step log
```
x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 08:28:31.030  2074  2131 I Perf    : fps=7.4 worst=77 drawn=10/17 queue=2 build=12ms verts=26k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=10 noMesh=0 culled=5
10-28 08:28:33.037  2074  2131 I Perf    : fps=6.9 worst=7 drawn=14/37 queue=0 build=219ms verts=36k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=22
10-28 08:28:35.067  2074  2131 I Perf    : fps=7.4 worst=8 drawn=14/37 queue=0 build=219ms verts=36k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:28:37.172  2074  2131 I Perf    : fps=7.1 worst=8 drawn=14/37 queue=0 build=219ms verts=36k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:28:39.298  2074  2131 I Perf    : fps=7.5 worst=8 drawn=14/37 queue=0 build=219ms verts=36k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:28:41.417  2074  2131 I Perf    : fps=7.1 worst=8 drawn=14/37 queue=0 build=219ms verts=36k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:28:43.478  2074  2131 I Perf    : fps=7.3 worst=8 drawn=14/37 queue=0 build=219ms verts=36k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:28:45.565  2074  2131 I Perf    : fps=7.2 worst=8 drawn=14/37 queue=0 build=219ms verts=36k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:28:47.602  2074  2131 I Perf    : fps=7.4 worst=8 drawn=14/37 queue=0 build=219ms verts=36k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:28:49.751  2074  2131 I Perf    : fps=7.0 worst=7 drawn=14/37 queue=0 build=219ms verts=36k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:28:51.822  2074  2131 I Perf    : fps=7.2 worst=8 drawn=14/37 queue=0 build=219ms verts=36k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:28:53.890  2074  2131 I Perf    : fps=7.3 worst=8 drawn=14/37 queue=0 build=219ms verts=36k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:28:54.374  2416  2416 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 08:28:55.965  2074  2131 I Perf    : fps=6.7 worst=7 drawn=14/37 queue=0 build=219ms verts=36k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:28:58.020  2074  2131 I Perf    : fps=7.3 worst=8 drawn=14/37 queue=0 build=219ms verts=36k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 08:28:58.174  2421  2421 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:28:58.186  2421  2421 I AndroidRuntime: Using default boot image
10-28 08:28:58.187  2421  2421 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:28:58.395  2421  2421 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:28:59.092  2421  2421 D AndroidRuntime: Shutting down VM
10-28 08:29:00.038  2074  2131 I Perf    : fps=7.4 worst=9 drawn=18/37 queue=0 build=219ms verts=48k cam=-0.5,1.6,1.5 yaw=2.63 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:29:02.139  2074  2131 I Perf    : fps=7.6 worst=9 drawn=18/37 queue=0 build=219ms verts=48k cam=-0.5,1.6,1.5 yaw=2.63 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 08:29:03.637  2456  2456 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:29:03.649  2456  2456 I AndroidRuntime: Using default boot image
10-28 08:29:03.649  2456  2456 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:29:03.709  2456  2456 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:29:03.745  2456  2456 D AndroidRuntime: Shutting down VM
10-28 08:29:04.239  2074  2131 I Perf    : fps=7.6 worst=9 drawn=18/37 queue=0 build=219ms verts=48k cam=-0.5,1.6,1.5 yaw=2.63 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 08:29:06.364  2074  2131 I Perf    : fps=7.5 worst=8 drawn=18/37 queue=0 build=219ms verts=48k cam=-0.5,1.6,1.5 yaw=2.63 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 08:29:08.410  2074  2131 I Perf    : fps=7.8 worst=9 drawn=18/37 queue=0 build=219ms verts=48k cam=-0.5,1.6,1.5 yaw=2.63 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 08:29:08.586  2473  2473 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 08:29:08.597  2473  2473 I AndroidRuntime: Using default boot image
10-28 08:29:08.597  2473  2473 I AndroidRuntime: Leaving lock profiling enabled
10-28 08:29:08.684  2473  2473 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 08:29:10.252  2473  2473 D AndroidRuntime: Shutting down VM
10-28 08:29:10.454  2074  2131 I Perf    : fps=7.3 worst=9 drawn=18/37 queue=0 build=219ms verts=48k cam=-1.6,1.6,3.4 yaw=2.63 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 08:29:10.836  2488  2488 I ContactsPerf: VoicemailContentProvider.onCreate start
10-28 08:29:10.838  2488  2488 I ContactsPerf: VoicemailContentProvider.onCreate finish
10-28 08:29:12.470  2074  2131 I Perf    : fps=6.4 worst=7 drawn=18/37 queue=0 build=219ms verts=48k cam=-1.6,1.6,3.5 yaw=2.63 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 08:29:14.474  2074  2131 I Perf    : fps=6.5 worst=7 drawn=18/37 queue=0 build=219ms verts=48k cam=-1.6,1.6,3.5 yaw=2.63 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 08:29:15.512  2522  2522 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 08:29:16.505  2074  2131 I Perf    : fps=5.9 worst=6 drawn=18/37 queue=0 build=219ms verts=48k cam=-1.6,1.6,3.5 yaw=2.63 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 08:29:18.665  2074  2131 I Perf    : fps=6.5 worst=7 drawn=18/37 queue=0 build=219ms verts=48k cam=-1.6,1.6,3.5 yaw=2.63 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
+ '[' -z 2074 ']'
+ grep -qa 'FATAL EXCEPTION' emulator-shots/logcat.txt
+ '[' 0 '!=' 0 ']'
+ echo 'OK: the app is alive, rendering, and the touch controls work'
OK: the app is alive, rendering, and the touch controls work
```
