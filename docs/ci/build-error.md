# Last build failure

Commit: 13e4659

```
Welcome to Gradle 8.11.1!
Here are the highlights of this release:
 - Parallel load and store for Configuration Cache
 - Java compilation errors at the end of the build output
 - Consolidated report for warnings and deprecations
For more details see https://docs.gradle.org/8.11.1/release-notes.html
To honour the JVM settings for this build a single-use Daemon process will be forked. For more on this, please refer to https://docs.gradle.org/8.11.1/userguide/gradle_daemon.html#sec:disabling_the_daemon in the Gradle documentation.
Daemon will be stopped at the end of the build 
> Task :android:copyAndroidNatives
Android natives: arm64-v8a/libgdx.so, armeabi-v7a/libgdx.so, x86_64/libgdx.so
> Task :android:preBuild
> Task :android:preDebugBuild
> Task :android:mergeDebugNativeDebugMetadata NO-SOURCE
> Task :android:javaPreCompileDebug FROM-CACHE
> Task :android:generateDebugResValues FROM-CACHE
> Task :android:checkDebugAarMetadata
> Task :android:mapDebugSourceSetPaths
> Task :android:generateDebugResources FROM-CACHE
> Task :android:mergeDebugResources FROM-CACHE
> Task :android:packageDebugResources FROM-CACHE
> Task :android:parseDebugLocalResources FROM-CACHE
> Task :android:createDebugCompatibleScreenManifests
> Task :android:extractDeepLinksDebug FROM-CACHE
> Task :android:processDebugMainManifest FROM-CACHE
> Task :android:processDebugManifest FROM-CACHE
> Task :android:processDebugManifestForPackage FROM-CACHE
> Task :android:processDebugResources FROM-CACHE
> Task :android:mergeDebugShaders
> Task :android:compileDebugShaders NO-SOURCE
> Task :android:generateDebugAssets UP-TO-DATE
> Task :android:mergeDebugAssets
> Task :android:desugarDebugFileDependencies FROM-CACHE
> Task :android:processDebugJavaRes NO-SOURCE
> Task :android:mergeDebugJniLibFolders
> Task :android:checkDebugDuplicateClasses
> Task :android:compressDebugAssets
> Task :core:compileJava
Note: Some input files use or override a deprecated API.
Note: Recompile with -Xlint:deprecation for details.
> Task :core:processResources NO-SOURCE
> Task :core:classes
> Task :core:jar
> Task :android:validateSigningDebug
> Task :core:compileTestJava
> Task :core:processTestResources NO-SOURCE
> Task :core:testClasses
> Task :android:compileDebugJavaWithJavac
Note: /home/runner/work/backrooms-gdx/backrooms-gdx/android/src/main/java/com/backrooms/gdx/android/AndroidLauncher.java uses or overrides a deprecated API.
Note: Recompile with -Xlint:deprecation for details.
> Task :android:dexBuilderDebug FROM-CACHE
> Task :android:mergeDebugJavaResource
> Task :core:test
ChunkMesherTest > holesAreCutIntoTheFloor FAILED
    java.lang.ArrayIndexOutOfBoundsException: Index 1980 out of bounds for length 1980
        at com.backrooms.gdx.light.LightBaker$Cells.<init>(LightBaker.java:238)
        at com.backrooms.gdx.light.LightBaker.bake(LightBaker.java:48)
        at com.backrooms.gdx.chunk.ChunkMesher.build(ChunkMesher.java:34)
        at com.backrooms.gdx.ChunkMesherTest.holesAreCutIntoTheFloor(ChunkMesherTest.java:170)
ChunkMesherTest > textureCoordinatesStillFollowTheWorldGrid FAILED
    java.lang.ArrayIndexOutOfBoundsException: Index 1980 out of bounds for length 1980
        at com.backrooms.gdx.light.LightBaker$Cells.<init>(LightBaker.java:238)
        at com.backrooms.gdx.light.LightBaker.bake(LightBaker.java:48)
        at com.backrooms.gdx.chunk.ChunkMesher.build(ChunkMesher.java:34)
        at com.backrooms.gdx.ChunkMesherTest.textureCoordinatesStillFollowTheWorldGrid(ChunkMesherTest.java:125)
ChunkMesherTest > geometryIsStoredRelativeToItsChunk FAILED
    java.lang.ArrayIndexOutOfBoundsException: Index 1980 out of bounds for length 1980
        at com.backrooms.gdx.light.LightBaker$Cells.<init>(LightBaker.java:238)
        at com.backrooms.gdx.light.LightBaker.bake(LightBaker.java:48)
        at com.backrooms.gdx.chunk.ChunkMesher.build(ChunkMesher.java:34)
        at com.backrooms.gdx.ChunkMesherTest.geometryIsStoredRelativeToItsChunk(ChunkMesherTest.java:100)
ChunkMesherTest > meshIsWellFormed FAILED
    java.lang.ArrayIndexOutOfBoundsException: Index 1980 out of bounds for length 1980
        at com.backrooms.gdx.light.LightBaker$Cells.<init>(LightBaker.java:238)
        at com.backrooms.gdx.light.LightBaker.bake(LightBaker.java:48)
        at com.backrooms.gdx.chunk.ChunkMesher.build(ChunkMesher.java:34)
        at com.backrooms.gdx.ChunkMesherTest.meshIsWellFormed(ChunkMesherTest.java:25)
ChunkMesherTest > everyKindOfRoomMeshesAtItsOwnHeight FAILED
    java.lang.ArrayIndexOutOfBoundsException: Index 1980 out of bounds for length 1980
        at com.backrooms.gdx.light.LightBaker$Cells.<init>(LightBaker.java:238)
        at com.backrooms.gdx.light.LightBaker.bake(LightBaker.java:48)
        at com.backrooms.gdx.chunk.ChunkMesher.build(ChunkMesher.java:34)
        at com.backrooms.gdx.ChunkMesherTest.everyKindOfRoomMeshesAtItsOwnHeight(ChunkMesherTest.java:231)
ChunkMesherTest > lightmapIsFullyPacked FAILED
    java.lang.ArrayIndexOutOfBoundsException: Index 1980 out of bounds for length 1980
        at com.backrooms.gdx.light.LightBaker$Cells.<init>(LightBaker.java:238)
        at com.backrooms.gdx.light.LightBaker.bake(LightBaker.java:48)
        at com.backrooms.gdx.chunk.ChunkMesher.build(ChunkMesher.java:34)
        at com.backrooms.gdx.ChunkMesherTest.lightmapIsFullyPacked(ChunkMesherTest.java:76)
ChunkMesherTest > boundsCoverTheChunk FAILED
    java.lang.ArrayIndexOutOfBoundsException: Index 1980 out of bounds for length 1980
        at com.backrooms.gdx.light.LightBaker$Cells.<init>(LightBaker.java:238)
        at com.backrooms.gdx.light.LightBaker.bake(LightBaker.java:48)
        at com.backrooms.gdx.chunk.ChunkMesher.build(ChunkMesher.java:34)
        at com.backrooms.gdx.ChunkMesherTest.boundsCoverTheChunk(ChunkMesherTest.java:61)
EntityControllerTest > itPassesByAndLeavesBehindAWall FAILED
    java.lang.AssertionError: it must not appear where the player can see it
        at org.junit.Assert.fail(Assert.java:89)
        at org.junit.Assert.assertTrue(Assert.java:42)
        at org.junit.Assert.assertFalse(Assert.java:65)
        at com.backrooms.gdx.EntityControllerTest.itPassesByAndLeavesBehindAWall(EntityControllerTest.java:98)
LightBakerTest > wallsBlockLight FAILED
    java.lang.ArrayIndexOutOfBoundsException: Index 1980 out of bounds for length 1980
        at com.backrooms.gdx.light.LightBaker$Cells.<init>(LightBaker.java:238)
        at com.backrooms.gdx.light.LightBaker.blocked(LightBaker.java:342)
        at com.backrooms.gdx.LightBakerTest.wallsBlockLight(LightBakerTest.java:36)
LightBakerTest > wallsDarkenTheFloorAroundThem FAILED
    java.lang.ArrayIndexOutOfBoundsException: Index 1980 out of bounds for length 1980
        at com.backrooms.gdx.light.LightBaker$Cells.<init>(LightBaker.java:238)
        at com.backrooms.gdx.light.LightBaker.bake(LightBaker.java:48)
        at com.backrooms.gdx.LightBakerTest.wallsDarkenTheFloorAroundThem(LightBakerTest.java:52)
> Task :android:mergeExtDexDebug FROM-CACHE
> Task :android:mergeProjectDexDebug FROM-CACHE
> Task :android:mergeDebugNativeLibs
> Task :android:stripDebugDebugSymbols
Unable to strip the following libraries, packaging them as they are: libgdx.so.
> Task :android:writeDebugAppMetadata
> Task :android:writeDebugSigningConfigVersions
> Task :android:preReleaseBuild
> Task :android:mergeReleaseJniLibFolders
> Task :android:mergeReleaseNativeLibs
> Task :android:stripReleaseDebugSymbols
Unable to strip the following libraries, packaging them as they are: libgdx.so.
> Task :android:extractReleaseNativeSymbolTables FROM-CACHE
> Task :android:mergeReleaseNativeDebugMetadata NO-SOURCE
> Task :android:checkReleaseDuplicateClasses
> Task :android:javaPreCompileRelease FROM-CACHE
> Task :android:checkReleaseAarMetadata
> Task :android:generateReleaseResValues FROM-CACHE
> Task :android:mapReleaseSourceSetPaths
> Task :android:generateReleaseResources FROM-CACHE
> Task :android:mergeReleaseResources FROM-CACHE
> Task :android:packageReleaseResources FROM-CACHE
> Task :android:parseReleaseLocalResources FROM-CACHE
> Task :android:createReleaseCompatibleScreenManifests
> Task :android:extractDeepLinksRelease FROM-CACHE
> Task :android:processReleaseMainManifest FROM-CACHE
> Task :android:processReleaseManifest FROM-CACHE
> Task :android:processReleaseManifestForPackage FROM-CACHE
> Task :android:processReleaseResources FROM-CACHE
> Task :android:compileReleaseJavaWithJavac FROM-CACHE
> Task :android:dexBuilderRelease FROM-CACHE
> Task :android:desugarReleaseFileDependencies FROM-CACHE
> Task :android:mergeReleaseStartupProfile
> Task :android:mergeExtDexRelease FROM-CACHE
> Task :android:mergeReleaseArtProfile
> Task :android:mergeReleaseShaders
> Task :android:compileReleaseShaders NO-SOURCE
> Task :android:generateReleaseAssets UP-TO-DATE
> Task :android:mergeReleaseAssets
> Task :android:mergeDebugGlobalSynthetics FROM-CACHE
> Task :android:mergeReleaseGlobalSynthetics FROM-CACHE
> Task :android:mergeLibDexDebug
> Task :android:compressReleaseAssets
> Task :android:extractReleaseVersionControlInfo
> Task :android:processReleaseJavaRes NO-SOURCE
> Task :android:packageDebug
> Task :android:createDebugApkListingFileRedirect
> Task :android:assembleDebug
> Task :android:optimizeReleaseResources FROM-CACHE
> Task :android:collectReleaseDependencies
> Task :android:mergeReleaseJavaResource
> Task :android:sdkReleaseDependencyData
> Task :android:writeReleaseAppMetadata
> Task :android:writeReleaseSigningConfigVersions
> Task :android:mergeDexRelease
> Task :android:compileReleaseArtProfile
> Task :android:packageRelease
> Task :android:createReleaseApkListingFileRedirect
> Task :android:assembleRelease
> Task :core:test
WorldStreamingTest > buildsAndUnloadsChunksAroundThePlayer FAILED
    java.lang.AssertionError: chunks should stream in
        at org.junit.Assert.fail(Assert.java:89)
        at org.junit.Assert.assertTrue(Assert.java:42)
        at com.backrooms.gdx.WorldStreamingTest.buildsAndUnloadsChunksAroundThePlayer(WorldStreamingTest.java:34)
51 tests completed, 11 failed
> Task :core:test FAILED
[Incubating] Problems report is available at: file:///home/runner/work/backrooms-gdx/backrooms-gdx/build/reports/problems/problems-report.html
FAILURE: Build failed with an exception.
* What went wrong:
Execution failed for task ':core:test'.
> There were failing tests. See the report at: file:///home/runner/work/backrooms-gdx/backrooms-gdx/core/build/reports/tests/test/index.html
* Try:
> Run with --scan to get full insights.
* Exception is:
org.gradle.api.tasks.TaskExecutionException: Execution failed for task ':core:test'.
	at org.gradle.api.internal.tasks.execution.ExecuteActionsTaskExecuter.lambda$executeIfValid$1(ExecuteActionsTaskExecuter.java:130)
	at org.gradle.internal.Try$Failure.ifSuccessfulOrElse(Try.java:293)
	at org.gradle.api.internal.tasks.execution.ExecuteActionsTaskExecuter.executeIfValid(ExecuteActionsTaskExecuter.java:128)
	at org.gradle.api.internal.tasks.execution.ExecuteActionsTaskExecuter.execute(ExecuteActionsTaskExecuter.java:116)
	at org.gradle.api.internal.tasks.execution.FinalizePropertiesTaskExecuter.execute(FinalizePropertiesTaskExecuter.java:46)
	at org.gradle.api.internal.tasks.execution.ResolveTaskExecutionModeExecuter.execute(ResolveTaskExecutionModeExecuter.java:51)
	at org.gradle.api.internal.tasks.execution.SkipTaskWithNoActionsExecuter.execute(SkipTaskWithNoActionsExecuter.java:57)
	at org.gradle.api.internal.tasks.execution.SkipOnlyIfTaskExecuter.execute(SkipOnlyIfTaskExecuter.java:74)
	at org.gradle.api.internal.tasks.execution.CatchExceptionTaskExecuter.execute(CatchExceptionTaskExecuter.java:36)
	at org.gradle.api.internal.tasks.execution.EventFiringTaskExecuter$1.executeTask(EventFiringTaskExecuter.java:77)
	at org.gradle.api.internal.tasks.execution.EventFiringTaskExecuter$1.call(EventFiringTaskExecuter.java:55)
	at org.gradle.api.internal.tasks.execution.EventFiringTaskExecuter$1.call(EventFiringTaskExecuter.java:52)
	at org.gradle.internal.operations.DefaultBuildOperationRunner$CallableBuildOperationWorker.execute(DefaultBuildOperationRunner.java:209)
	at org.gradle.internal.operations.DefaultBuildOperationRunner$CallableBuildOperationWorker.execute(DefaultBuildOperationRunner.java:204)
	at org.gradle.internal.operations.DefaultBuildOperationRunner$2.execute(DefaultBuildOperationRunner.java:66)
	at org.gradle.internal.operations.DefaultBuildOperationRunner$2.execute(DefaultBuildOperationRunner.java:59)
	at org.gradle.internal.operations.DefaultBuildOperationRunner.execute(DefaultBuildOperationRunner.java:166)
	at org.gradle.internal.operations.DefaultBuildOperationRunner.execute(DefaultBuildOperationRunner.java:59)
	at org.gradle.internal.operations.DefaultBuildOperationRunner.call(DefaultBuildOperationRunner.java:53)
	at org.gradle.api.internal.tasks.execution.EventFiringTaskExecuter.execute(EventFiringTaskExecuter.java:52)
	at org.gradle.execution.plan.LocalTaskNodeExecutor.execute(LocalTaskNodeExecutor.java:42)
	at org.gradle.execution.taskgraph.DefaultTaskExecutionGraph$InvokeNodeExecutorsAction.execute(DefaultTaskExecutionGraph.java:331)
	at org.gradle.execution.taskgraph.DefaultTaskExecutionGraph$InvokeNodeExecutorsAction.execute(DefaultTaskExecutionGraph.java:318)
	at org.gradle.execution.taskgraph.DefaultTaskExecutionGraph$BuildOperationAwareExecutionAction.lambda$execute$0(DefaultTaskExecutionGraph.java:314)
	at org.gradle.internal.operations.CurrentBuildOperationRef.with(CurrentBuildOperationRef.java:85)
	at org.gradle.execution.taskgraph.DefaultTaskExecutionGraph$BuildOperationAwareExecutionAction.execute(DefaultTaskExecutionGraph.java:314)
	at org.gradle.execution.taskgraph.DefaultTaskExecutionGraph$BuildOperationAwareExecutionAction.execute(DefaultTaskExecutionGraph.java:303)
	at org.gradle.execution.plan.DefaultPlanExecutor$ExecutorWorker.execute(DefaultPlanExecutor.java:459)
	at org.gradle.execution.plan.DefaultPlanExecutor$ExecutorWorker.run(DefaultPlanExecutor.java:376)
	at org.gradle.internal.concurrent.ExecutorPolicy$CatchAndRecordFailures.onExecute(ExecutorPolicy.java:64)
	at org.gradle.internal.concurrent.AbstractManagedExecutor$1.run(AbstractManagedExecutor.java:48)
Caused by: org.gradle.api.internal.exceptions.MarkedVerificationException: There were failing tests. See the report at: file:///home/runner/work/backrooms-gdx/backrooms-gdx/core/build/reports/tests/test/index.html
	at org.gradle.api.tasks.testing.AbstractTestTask.handleTestFailures(AbstractTestTask.java:707)
	at org.gradle.api.tasks.testing.AbstractTestTask.handleCollectedResults(AbstractTestTask.java:541)
	at org.gradle.api.tasks.testing.AbstractTestTask.executeTests(AbstractTestTask.java:536)
	at org.gradle.api.tasks.testing.Test.executeTests(Test.java:731)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at org.gradle.internal.reflect.JavaMethod.invoke(JavaMethod.java:125)
	at org.gradle.api.internal.project.taskfactory.StandardTaskAction.doExecute(StandardTaskAction.java:58)
	at org.gradle.api.internal.project.taskfactory.StandardTaskAction.execute(StandardTaskAction.java:51)
	at org.gradle.api.internal.project.taskfactory.StandardTaskAction.execute(StandardTaskAction.java:29)
	at org.gradle.api.internal.tasks.execution.TaskExecution$3.run(TaskExecution.java:244)
	at org.gradle.internal.operations.DefaultBuildOperationRunner$1.execute(DefaultBuildOperationRunner.java:29)
	at org.gradle.internal.operations.DefaultBuildOperationRunner$1.execute(DefaultBuildOperationRunner.java:26)
	at org.gradle.internal.operations.DefaultBuildOperationRunner$2.execute(DefaultBuildOperationRunner.java:66)
	at org.gradle.internal.operations.DefaultBuildOperationRunner$2.execute(DefaultBuildOperationRunner.java:59)
	at org.gradle.internal.operations.DefaultBuildOperationRunner.execute(DefaultBuildOperationRunner.java:166)
	at org.gradle.internal.operations.DefaultBuildOperationRunner.execute(DefaultBuildOperationRunner.java:59)
	at org.gradle.internal.operations.DefaultBuildOperationRunner.run(DefaultBuildOperationRunner.java:47)
	at org.gradle.api.internal.tasks.execution.TaskExecution.executeAction(TaskExecution.java:229)
	at org.gradle.api.internal.tasks.execution.TaskExecution.executeActions(TaskExecution.java:212)
	at org.gradle.api.internal.tasks.execution.TaskExecution.executeWithPreviousOutputFiles(TaskExecution.java:195)
	at org.gradle.api.internal.tasks.execution.TaskExecution.execute(TaskExecution.java:162)
	at org.gradle.internal.execution.steps.ExecuteStep.executeInternal(ExecuteStep.java:105)
	at org.gradle.internal.execution.steps.ExecuteStep.access$000(ExecuteStep.java:44)
	at org.gradle.internal.execution.steps.ExecuteStep$1.call(ExecuteStep.java:59)
	at org.gradle.internal.execution.steps.ExecuteStep$1.call(ExecuteStep.java:56)
	at org.gradle.internal.operations.DefaultBuildOperationRunner$CallableBuildOperationWorker.execute(DefaultBuildOperationRunner.java:209)
	at org.gradle.internal.operations.DefaultBuildOperationRunner$CallableBuildOperationWorker.execute(DefaultBuildOperationRunner.java:204)
	at org.gradle.internal.operations.DefaultBuildOperationRunner$2.execute(DefaultBuildOperationRunner.java:66)
	at org.gradle.internal.operations.DefaultBuildOperationRunner$2.execute(DefaultBuildOperationRunner.java:59)
	at org.gradle.internal.operations.DefaultBuildOperationRunner.execute(DefaultBuildOperationRunner.java:166)
	at org.gradle.internal.operations.DefaultBuildOperationRunner.execute(DefaultBuildOperationRunner.java:59)
	at org.gradle.internal.operations.DefaultBuildOperationRunner.call(DefaultBuildOperationRunner.java:53)
	at org.gradle.internal.execution.steps.ExecuteStep.execute(ExecuteStep.java:56)
	at org.gradle.internal.execution.steps.ExecuteStep.execute(ExecuteStep.java:44)
	at org.gradle.internal.execution.steps.CancelExecutionStep.execute(CancelExecutionStep.java:42)
	at org.gradle.internal.execution.steps.TimeoutStep.executeWithoutTimeout(TimeoutStep.java:75)
	at org.gradle.internal.execution.steps.TimeoutStep.execute(TimeoutStep.java:55)
	at org.gradle.internal.execution.steps.PreCreateOutputParentsStep.execute(PreCreateOutputParentsStep.java:50)
	at org.gradle.internal.execution.steps.PreCreateOutputParentsStep.execute(PreCreateOutputParentsStep.java:28)
	at org.gradle.internal.execution.steps.RemovePreviousOutputsStep.execute(RemovePreviousOutputsStep.java:67)
	at org.gradle.internal.execution.steps.RemovePreviousOutputsStep.execute(RemovePreviousOutputsStep.java:37)
	at org.gradle.internal.execution.steps.BroadcastChangingOutputsStep.execute(BroadcastChangingOutputsStep.java:61)
	at org.gradle.internal.execution.steps.BroadcastChangingOutputsStep.execute(BroadcastChangingOutputsStep.java:26)
	at org.gradle.internal.execution.steps.CaptureOutputsAfterExecutionStep.execute(CaptureOutputsAfterExecutionStep.java:69)
	at org.gradle.internal.execution.steps.CaptureOutputsAfterExecutionStep.execute(CaptureOutputsAfterExecutionStep.java:46)
	at org.gradle.internal.execution.steps.ResolveInputChangesStep.execute(ResolveInputChangesStep.java:40)
	at org.gradle.internal.execution.steps.ResolveInputChangesStep.execute(ResolveInputChangesStep.java:29)
	at org.gradle.internal.execution.steps.BuildCacheStep.executeWithoutCache(BuildCacheStep.java:189)
	at org.gradle.internal.execution.steps.BuildCacheStep.executeAndStoreInCache(BuildCacheStep.java:145)
	at org.gradle.internal.execution.steps.BuildCacheStep.lambda$executeWithCache$4(BuildCacheStep.java:101)
	at org.gradle.internal.execution.steps.BuildCacheStep.lambda$executeWithCache$5(BuildCacheStep.java:101)
	at org.gradle.internal.Try$Success.map(Try.java:175)
	at org.gradle.internal.execution.steps.BuildCacheStep.executeWithCache(BuildCacheStep.java:85)
	at org.gradle.internal.execution.steps.BuildCacheStep.lambda$execute$0(BuildCacheStep.java:74)
	at org.gradle.internal.Either$Left.fold(Either.java:115)
	at org.gradle.internal.execution.caching.CachingState.fold(CachingState.java:62)
	at org.gradle.internal.execution.steps.BuildCacheStep.execute(BuildCacheStep.java:73)
	at org.gradle.internal.execution.steps.BuildCacheStep.execute(BuildCacheStep.java:48)
	at org.gradle.internal.execution.steps.StoreExecutionStateStep.execute(StoreExecutionStateStep.java:46)
	at org.gradle.internal.execution.steps.StoreExecutionStateStep.execute(StoreExecutionStateStep.java:35)
	at org.gradle.internal.execution.steps.SkipUpToDateStep.executeBecause(SkipUpToDateStep.java:75)
	at org.gradle.internal.execution.steps.SkipUpToDateStep.lambda$execute$2(SkipUpToDateStep.java:53)
	at org.gradle.internal.execution.steps.SkipUpToDateStep.execute(SkipUpToDateStep.java:53)
	at org.gradle.internal.execution.steps.SkipUpToDateStep.execute(SkipUpToDateStep.java:35)
	at org.gradle.internal.execution.steps.legacy.MarkSnapshottingInputsFinishedStep.execute(MarkSnapshottingInputsFinishedStep.java:37)
	at org.gradle.internal.execution.steps.legacy.MarkSnapshottingInputsFinishedStep.execute(MarkSnapshottingInputsFinishedStep.java:27)
	at org.gradle.internal.execution.steps.ResolveIncrementalCachingStateStep.executeDelegate(ResolveIncrementalCachingStateStep.java:49)
	at org.gradle.internal.execution.steps.ResolveIncrementalCachingStateStep.executeDelegate(ResolveIncrementalCachingStateStep.java:27)
	at org.gradle.internal.execution.steps.AbstractResolveCachingStateStep.execute(AbstractResolveCachingStateStep.java:71)
	at org.gradle.internal.execution.steps.AbstractResolveCachingStateStep.execute(AbstractResolveCachingStateStep.java:39)
	at org.gradle.internal.execution.steps.ResolveChangesStep.execute(ResolveChangesStep.java:65)
	at org.gradle.internal.execution.steps.ResolveChangesStep.execute(ResolveChangesStep.java:36)
	at org.gradle.internal.execution.steps.ValidateStep.execute(ValidateStep.java:107)
	at org.gradle.internal.execution.steps.ValidateStep.execute(ValidateStep.java:56)
	at org.gradle.internal.execution.steps.AbstractCaptureStateBeforeExecutionStep.execute(AbstractCaptureStateBeforeExecutionStep.java:64)
	at org.gradle.internal.execution.steps.AbstractCaptureStateBeforeExecutionStep.execute(AbstractCaptureStateBeforeExecutionStep.java:43)
	at org.gradle.internal.execution.steps.AbstractSkipEmptyWorkStep.executeWithNonEmptySources(AbstractSkipEmptyWorkStep.java:125)
	at org.gradle.internal.execution.steps.AbstractSkipEmptyWorkStep.execute(AbstractSkipEmptyWorkStep.java:61)
	at org.gradle.internal.execution.steps.AbstractSkipEmptyWorkStep.execute(AbstractSkipEmptyWorkStep.java:36)
	at org.gradle.internal.execution.steps.legacy.MarkSnapshottingInputsStartedStep.execute(MarkSnapshottingInputsStartedStep.java:38)
	at org.gradle.internal.execution.steps.LoadPreviousExecutionStateStep.execute(LoadPreviousExecutionStateStep.java:36)
	at org.gradle.internal.execution.steps.LoadPreviousExecutionStateStep.execute(LoadPreviousExecutionStateStep.java:23)
	at org.gradle.internal.execution.steps.HandleStaleOutputsStep.execute(HandleStaleOutputsStep.java:75)
	at org.gradle.internal.execution.steps.HandleStaleOutputsStep.execute(HandleStaleOutputsStep.java:41)
	at org.gradle.internal.execution.steps.AssignMutableWorkspaceStep.lambda$execute$0(AssignMutableWorkspaceStep.java:35)
	at org.gradle.api.internal.tasks.execution.TaskExecution$4.withWorkspace(TaskExecution.java:289)
	at org.gradle.internal.execution.steps.AssignMutableWorkspaceStep.execute(AssignMutableWorkspaceStep.java:31)
	at org.gradle.internal.execution.steps.AssignMutableWorkspaceStep.execute(AssignMutableWorkspaceStep.java:22)
	at org.gradle.internal.execution.steps.ChoosePipelineStep.execute(ChoosePipelineStep.java:40)
	at org.gradle.internal.execution.steps.ChoosePipelineStep.execute(ChoosePipelineStep.java:23)
	at org.gradle.internal.execution.steps.ExecuteWorkBuildOperationFiringStep.lambda$execute$2(ExecuteWorkBuildOperationFiringStep.java:67)
	at org.gradle.internal.execution.steps.ExecuteWorkBuildOperationFiringStep.execute(ExecuteWorkBuildOperationFiringStep.java:67)
	at org.gradle.internal.execution.steps.ExecuteWorkBuildOperationFiringStep.execute(ExecuteWorkBuildOperationFiringStep.java:39)
	at org.gradle.internal.execution.steps.IdentityCacheStep.execute(IdentityCacheStep.java:46)
	at org.gradle.internal.execution.steps.IdentityCacheStep.execute(IdentityCacheStep.java:34)
	at org.gradle.internal.execution.steps.IdentifyStep.execute(IdentifyStep.java:48)
	at org.gradle.internal.execution.steps.IdentifyStep.execute(IdentifyStep.java:35)
	at org.gradle.internal.execution.impl.DefaultExecutionEngine$1.execute(DefaultExecutionEngine.java:61)
	at org.gradle.api.internal.tasks.execution.ExecuteActionsTaskExecuter.executeIfValid(ExecuteActionsTaskExecuter.java:127)
	at org.gradle.api.internal.tasks.execution.ExecuteActionsTaskExecuter.execute(ExecuteActionsTaskExecuter.java:116)
	at org.gradle.api.internal.tasks.execution.FinalizePropertiesTaskExecuter.execute(FinalizePropertiesTaskExecuter.java:46)
	at org.gradle.api.internal.tasks.execution.ResolveTaskExecutionModeExecuter.execute(ResolveTaskExecutionModeExecuter.java:51)
	at org.gradle.api.internal.tasks.execution.SkipTaskWithNoActionsExecuter.execute(SkipTaskWithNoActionsExecuter.java:57)
	at org.gradle.api.internal.tasks.execution.SkipOnlyIfTaskExecuter.execute(SkipOnlyIfTaskExecuter.java:74)
	at org.gradle.api.internal.tasks.execution.CatchExceptionTaskExecuter.execute(CatchExceptionTaskExecuter.java:36)
	at org.gradle.api.internal.tasks.execution.EventFiringTaskExecuter$1.executeTask(EventFiringTaskExecuter.java:77)
	at org.gradle.api.internal.tasks.execution.EventFiringTaskExecuter$1.call(EventFiringTaskExecuter.java:55)
	at org.gradle.api.internal.tasks.execution.EventFiringTaskExecuter$1.call(EventFiringTaskExecuter.java:52)
	at org.gradle.internal.operations.DefaultBuildOperationRunner$CallableBuildOperationWorker.execute(DefaultBuildOperationRunner.java:209)
	at org.gradle.internal.operations.DefaultBuildOperationRunner$CallableBuildOperationWorker.execute(DefaultBuildOperationRunner.java:204)
	at org.gradle.internal.operations.DefaultBuildOperationRunner$2.execute(DefaultBuildOperationRunner.java:66)
	at org.gradle.internal.operations.DefaultBuildOperationRunner$2.execute(DefaultBuildOperationRunner.java:59)
	at org.gradle.internal.operations.DefaultBuildOperationRunner.execute(DefaultBuildOperationRunner.java:166)
	at org.gradle.internal.operations.DefaultBuildOperationRunner.execute(DefaultBuildOperationRunner.java:59)
	at org.gradle.internal.operations.DefaultBuildOperationRunner.call(DefaultBuildOperationRunner.java:53)
	at org.gradle.api.internal.tasks.execution.EventFiringTaskExecuter.execute(EventFiringTaskExecuter.java:52)
	at org.gradle.execution.plan.LocalTaskNodeExecutor.execute(LocalTaskNodeExecutor.java:42)
	at org.gradle.execution.taskgraph.DefaultTaskExecutionGraph$InvokeNodeExecutorsAction.execute(DefaultTaskExecutionGraph.java:331)
	at org.gradle.execution.taskgraph.DefaultTaskExecutionGraph$InvokeNodeExecutorsAction.execute(DefaultTaskExecutionGraph.java:318)
	at org.gradle.execution.taskgraph.DefaultTaskExecutionGraph$BuildOperationAwareExecutionAction.lambda$execute$0(DefaultTaskExecutionGraph.java:314)
	at org.gradle.internal.operations.CurrentBuildOperationRef.with(CurrentBuildOperationRef.java:85)
	at org.gradle.execution.taskgraph.DefaultTaskExecutionGraph$BuildOperationAwareExecutionAction.execute(DefaultTaskExecutionGraph.java:314)
	at org.gradle.execution.taskgraph.DefaultTaskExecutionGraph$BuildOperationAwareExecutionAction.execute(DefaultTaskExecutionGraph.java:303)
	at org.gradle.execution.plan.DefaultPlanExecutor$ExecutorWorker.execute(DefaultPlanExecutor.java:459)
	at org.gradle.execution.plan.DefaultPlanExecutor$ExecutorWorker.run(DefaultPlanExecutor.java:376)
	at org.gradle.internal.concurrent.ExecutorPolicy$CatchAndRecordFailures.onExecute(ExecutorPolicy.java:64)
	at org.gradle.internal.concurrent.AbstractManagedExecutor$1.run(AbstractManagedExecutor.java:48)
BUILD FAILED in 20s
79 actionable tasks: 45 executed, 34 from cache
```
