# Emulator smoke test diagnostics

Commit: 0dec3e0132dff33afbc4a9f0d27be6937da65bf4
Outcome: success

## Environment
```
SDK=/usr/local/lib/android/sdk
APK=android/build/outputs/apk/debug/android-debug.apk
--- kvm
crw-rw---- 1 root kvm 10, 232 Oct 28 20:13 /dev/kvm
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
INFO         | The active JSON Web Key Sets can be found here: /run/user/1001/avd/running/2988/jwks/8292b975-2f21-44a5-9d6e-c36048efa8b0/active.jwk
INFO         | Scanning /run/user/1001/avd/running/2988/jwks/8292b975-2f21-44a5-9d6e-c36048efa8b0 for jwk keys.
INFO         | Started GRPC server at 127.0.0.1:8554, security: Local, auth: +token
INFO         | Advertising in: /run/user/1001/avd/running/pid_2988.ini
INFO         | Setting display: 0 configuration to: 1080x1920, dpi: 420x420 
INFO         | setDisplayActiveConfig: id:0, 1080x1920
INFO         | emulatorSetupEnvironment: Environment scene is not required
USER_INFO    | Emulator is performing a full startup. This may take upto two minutes, or more.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
WARNING      | Failed to process .ini file /home/runner/.android/emu-update-last-check.ini for reading.
INFO         | Boot completed in 26567 ms
INFO         | Increasing screen off timeout, logcat buffer size to 2M.
```
## Logcat (filtered)
```
10-28 20:17:25.289   514  2127 I ActivityManager: Killing 1945:com.android.localtransport/1000 (adj 985): empty #17
10-28 20:17:25.295   514   554 I ActivityManager: Start proc 2302:com.android.quicksearchbox/u0a101 for broadcast {com.android.quicksearchbox/com.android.quicksearchbox.SearchWidgetProvider}
10-28 20:17:25.604   514  1542 I ActivityManager: Killing 1837:com.android.provision/u0a118 (adj 985): empty #17
10-28 20:17:44.423   514  1012 I ActivityManager: Killing 2012:com.android.calendar/u0a111 (adj 985): empty #17
10-28 20:17:44.423   514  1012 I ActivityManager: Killing 1621:android.process.acore/u0a63 (adj 985): empty #18
10-28 20:17:44.424   514  1012 I ActivityManager: Killing 1291:com.android.cellbroadcastreceiver/u0a65 (adj 995): empty #19
10-28 20:17:47.042  2384  2384 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 20:17:47.080  2384  2384 I AndroidRuntime: Using default boot image
10-28 20:17:47.081  2384  2384 I AndroidRuntime: Leaving lock profiling enabled
10-28 20:17:47.231  2384  2384 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 20:17:47.281  2384  2384 D AndroidRuntime: Shutting down VM
10-28 20:17:47.831  2160  2250 I BackroomsGL: world shader compiled
10-28 20:17:48.397  2160  2250 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 20:17:48.397  2160  2250 I BackroomsInput: screen=1920x1080 pause=1820,100 torch=1820,255 sprint=1805,940 jump=1609,937 stickZone=864
10-28 20:17:49.736  2160  2250 I Perf    : fps=1.5 worst=2 drawn=2/4 queue=2 build=426ms verts=5k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=2 noMesh=0 culled=0
10-28 20:17:50.545   514  1184 I ActivityManager: Killing 2037:com.android.camera2/u0a108 (adj 975): empty #17
10-28 20:17:51.792  2160  2250 I Perf    : fps=3.9 worst=4 drawn=11/19 queue=2 build=39ms verts=28k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=11 noMesh=0 culled=6
10-28 20:17:53.871  2160  2250 I Perf    : fps=3.8 worst=4 drawn=14/33 queue=2 build=15ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=17
10-28 20:17:56.003  2160  2250 I Perf    : fps=3.7 worst=3 drawn=14/37 queue=0 build=280ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:17:58.027  2160  2250 I Perf    : fps=4.0 worst=4 drawn=14/37 queue=0 build=280ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:18:00.110  2160  2250 I Perf    : fps=3.8 worst=4 drawn=14/37 queue=0 build=280ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:18:02.131  2160  2250 I Perf    : fps=4.0 worst=4 drawn=14/37 queue=0 build=280ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:18:04.226  2160  2250 I Perf    : fps=3.8 worst=4 drawn=14/37 queue=0 build=280ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:18:06.259  2160  2250 I Perf    : fps=3.9 worst=4 drawn=14/37 queue=0 build=280ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:18:08.328  2160  2250 I Perf    : fps=3.9 worst=4 drawn=14/37 queue=0 build=280ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:18:10.461  2160  2250 I Perf    : fps=3.8 worst=4 drawn=14/37 queue=0 build=280ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:18:12.469  2160  2250 I Perf    : fps=4.0 worst=4 drawn=14/37 queue=0 build=280ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:18:14.649  2160  2250 I Perf    : fps=3.7 worst=4 drawn=14/37 queue=0 build=280ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:18:16.825  2160  2250 I Perf    : fps=3.7 worst=4 drawn=14/37 queue=0 build=280ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:18:18.932  2160  2250 I Perf    : fps=3.8 worst=3 drawn=14/37 queue=0 build=280ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:18:19.542  2435  2435 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 20:18:19.544  2435  2435 I AndroidRuntime: Using default boot image
10-28 20:18:19.544  2435  2435 I AndroidRuntime: Leaving lock profiling enabled
10-28 20:18:19.616  2435  2435 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 20:18:20.220  2435  2435 D AndroidRuntime: Shutting down VM
10-28 20:18:21.008  2160  2250 I Perf    : fps=3.9 worst=4 drawn=18/37 queue=0 build=280ms verts=47k cam=-0.5,1.6,1.5 yaw=2.80 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 20:18:23.166  2160  2250 I Perf    : fps=3.7 worst=4 drawn=18/37 queue=0 build=280ms verts=47k cam=-0.5,1.6,1.5 yaw=2.80 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 20:18:25.194  2160  2250 I Perf    : fps=3.9 worst=4 drawn=18/37 queue=0 build=280ms verts=47k cam=-0.5,1.6,1.5 yaw=2.80 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 20:18:25.454  2452  2452 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 20:18:25.471  2452  2452 I AndroidRuntime: Using default boot image
10-28 20:18:25.472  2452  2452 I AndroidRuntime: Leaving lock profiling enabled
10-28 20:18:25.552  2452  2452 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 20:18:25.555  2452  2452 D AndroidRuntime: Shutting down VM
10-28 20:18:25.566  2452  2463 I AndroidRuntime: NOTE: attach of thread 'Binder:2452_1' failed
10-28 20:18:27.003   514   554 I ActivityManager: Start proc 2465:android.process.acore/u0a63 for content provider {com.android.providers.contacts/com.android.providers.contacts.VoicemailContentProvider}
10-28 20:18:27.289  2160  2250 I Perf    : fps=3.8 worst=4 drawn=18/37 queue=0 build=280ms verts=47k cam=-0.5,1.6,1.5 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 20:18:27.388  2465  2465 I ContactsPerf: VoicemailContentProvider.onCreate start
10-28 20:18:27.396  2465  2465 I ContactsPerf: VoicemailContentProvider.onCreate finish
10-28 20:18:29.332  2160  2250 I Perf    : fps=3.9 worst=4 drawn=18/37 queue=0 build=280ms verts=47k cam=-0.5,1.6,1.5 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 20:18:30.512  2495  2495 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 20:18:30.514  2495  2495 I AndroidRuntime: Using default boot image
10-28 20:18:30.514  2495  2495 I AndroidRuntime: Leaving lock profiling enabled
10-28 20:18:30.607  2495  2495 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 20:18:31.422  2160  2250 I Perf    : fps=3.8 worst=4 drawn=18/37 queue=0 build=280ms verts=47k cam=-0.5,1.6,1.6 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 20:18:32.121  2495  2495 D AndroidRuntime: Shutting down VM
10-28 20:18:33.571  2160  2250 I Perf    : fps=3.3 worst=3 drawn=18/37 queue=0 build=280ms verts=47k cam=-0.9,1.6,2.5 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 20:18:35.662  2160  2250 I Perf    : fps=3.3 worst=3 drawn=18/37 queue=0 build=280ms verts=47k cam=-0.9,1.6,2.5 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 20:18:37.727  2160  2250 I Perf    : fps=3.4 worst=3 drawn=18/37 queue=0 build=280ms verts=47k cam=-0.9,1.6,2.5 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 20:18:39.807  2160  2250 I Perf    : fps=3.4 worst=3 drawn=18/37 queue=0 build=280ms verts=47k cam=-0.9,1.6,2.5 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 20:18:41.937  2160  2250 I Perf    : fps=3.3 worst=3 drawn=18/37 queue=0 build=280ms verts=47k cam=-0.9,1.6,2.5 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
```
## Step log
```
871  2160  2250 I Perf    : fps=3.8 worst=4 drawn=14/33 queue=2 build=15ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=17
10-28 20:17:56.003  2160  2250 I Perf    : fps=3.7 worst=3 drawn=14/37 queue=0 build=280ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:17:58.027  2160  2250 I Perf    : fps=4.0 worst=4 drawn=14/37 queue=0 build=280ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:18:00.110  2160  2250 I Perf    : fps=3.8 worst=4 drawn=14/37 queue=0 build=280ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:18:02.131  2160  2250 I Perf    : fps=4.0 worst=4 drawn=14/37 queue=0 build=280ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:18:04.226  2160  2250 I Perf    : fps=3.8 worst=4 drawn=14/37 queue=0 build=280ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:18:06.259  2160  2250 I Perf    : fps=3.9 worst=4 drawn=14/37 queue=0 build=280ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:18:08.328  2160  2250 I Perf    : fps=3.9 worst=4 drawn=14/37 queue=0 build=280ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:18:10.461  2160  2250 I Perf    : fps=3.8 worst=4 drawn=14/37 queue=0 build=280ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:18:12.469  2160  2250 I Perf    : fps=4.0 worst=4 drawn=14/37 queue=0 build=280ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:18:13.295  2411  2411 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 20:18:14.649  2160  2250 I Perf    : fps=3.7 worst=4 drawn=14/37 queue=0 build=280ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:18:16.825  2160  2250 I Perf    : fps=3.7 worst=4 drawn=14/37 queue=0 build=280ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:18:18.932  2160  2250 I Perf    : fps=3.8 worst=3 drawn=14/37 queue=0 build=280ms verts=35k cam=-0.5,1.6,1.5 yaw=0.00 pitch=0.00 torch=1 ready=true drawn=14 noMesh=0 culled=23
10-28 20:18:19.542  2435  2435 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 20:18:19.544  2435  2435 I AndroidRuntime: Using default boot image
10-28 20:18:19.544  2435  2435 I AndroidRuntime: Leaving lock profiling enabled
10-28 20:18:19.616  2435  2435 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 20:18:20.220  2435  2435 D AndroidRuntime: Shutting down VM
10-28 20:18:21.008  2160  2250 I Perf    : fps=3.9 worst=4 drawn=18/37 queue=0 build=280ms verts=47k cam=-0.5,1.6,1.5 yaw=2.80 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 20:18:23.166  2160  2250 I Perf    : fps=3.7 worst=4 drawn=18/37 queue=0 build=280ms verts=47k cam=-0.5,1.6,1.5 yaw=2.80 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 20:18:25.194  2160  2250 I Perf    : fps=3.9 worst=4 drawn=18/37 queue=0 build=280ms verts=47k cam=-0.5,1.6,1.5 yaw=2.80 pitch=0.00 torch=1 ready=true drawn=18 noMesh=0 culled=19
10-28 20:18:25.454  2452  2452 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 20:18:25.471  2452  2452 I AndroidRuntime: Using default boot image
10-28 20:18:25.472  2452  2452 I AndroidRuntime: Leaving lock profiling enabled
10-28 20:18:25.552  2452  2452 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 20:18:25.555  2452  2452 D AndroidRuntime: Shutting down VM
10-28 20:18:25.566  2452  2463 I AndroidRuntime: NOTE: attach of thread 'Binder:2452_1' failed
10-28 20:18:27.289  2160  2250 I Perf    : fps=3.8 worst=4 drawn=18/37 queue=0 build=280ms verts=47k cam=-0.5,1.6,1.5 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 20:18:27.388  2465  2465 I ContactsPerf: VoicemailContentProvider.onCreate start
10-28 20:18:27.396  2465  2465 I ContactsPerf: VoicemailContentProvider.onCreate finish
10-28 20:18:29.332  2160  2250 I Perf    : fps=3.9 worst=4 drawn=18/37 queue=0 build=280ms verts=47k cam=-0.5,1.6,1.5 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 20:18:30.512  2495  2495 D AndroidRuntime: >>>>>> START com.android.internal.os.RuntimeInit uid 2000 <<<<<<
10-28 20:18:30.514  2495  2495 I AndroidRuntime: Using default boot image
10-28 20:18:30.514  2495  2495 I AndroidRuntime: Leaving lock profiling enabled
10-28 20:18:30.607  2495  2495 D AndroidRuntime: Calling main entry com.android.commands.input.Input
10-28 20:18:31.422  2160  2250 I Perf    : fps=3.8 worst=4 drawn=18/37 queue=0 build=280ms verts=47k cam=-0.5,1.6,1.6 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 20:18:32.121  2495  2495 D AndroidRuntime: Shutting down VM
10-28 20:18:33.571  2160  2250 I Perf    : fps=3.3 worst=3 drawn=18/37 queue=0 build=280ms verts=47k cam=-0.9,1.6,2.5 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 20:18:35.662  2160  2250 I Perf    : fps=3.3 worst=3 drawn=18/37 queue=0 build=280ms verts=47k cam=-0.9,1.6,2.5 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 20:18:37.727  2160  2250 I Perf    : fps=3.4 worst=3 drawn=18/37 queue=0 build=280ms verts=47k cam=-0.9,1.6,2.5 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 20:18:37.950  2513  2513 D HostConnection: HostComposition ext ANDROID_EMU_CHECKSUM_HELPER_v1 ANDROID_EMU_native_sync_v2 ANDROID_EMU_dma_v1 ANDROID_EMU_direct_mem ANDROID_EMU_host_composition_v1 ANDROID_EMU_host_composition_v2 ANDROID_EMU_vulkan ANDROID_EMU_deferred_vulkan_commands ANDROID_EMU_vulkan_null_optional_strings ANDROID_EMU_vulkan_create_resources_with_requirements ANDROID_EMU_YUV_Cache ANDROID_EMU_vulkan_ignored_handles ANDROID_EMU_has_shared_slots_host_memory_allocator ANDROID_EMU_vulkan_free_memory_sync ANDROID_EMU_vulkan_shader_float16_int8 ANDROID_EMU_vulkan_async_queue_submit ANDROID_EMU_sync_buffer_data ANDROID_EMU_vulkan_async_qsri ANDROID_EMU_read_color_buffer_dma ANDROID_EMU_hwc_color_transform GL_OES_EGL_image_external_essl3 GL_OES_vertex_array_object GL_KHR_texture_compression_astc_ldr ANDROID_EMU_host_side_tracing ANDROID_EMU_gles_max_version_3_0 
10-28 20:18:39.807  2160  2250 I Perf    : fps=3.4 worst=3 drawn=18/37 queue=0 build=280ms verts=47k cam=-0.9,1.6,2.5 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
10-28 20:18:41.937  2160  2250 I Perf    : fps=3.3 worst=3 drawn=18/37 queue=0 build=280ms verts=47k cam=-0.9,1.6,2.5 yaw=2.80 pitch=0.00 torch=0 ready=true drawn=18 noMesh=0 culled=19
+ '[' -z 2160 ']'
+ grep -qa 'FATAL EXCEPTION' emulator-shots/logcat.txt
+ '[' 0 '!=' 0 ']'
+ echo 'OK: the app is alive, rendering, and the touch controls work'
OK: the app is alive, rendering, and the touch controls work
```
