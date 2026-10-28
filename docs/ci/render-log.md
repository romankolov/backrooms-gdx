# Last screenshot run

Commit: ea39a20

```
files:
ls: cannot access 'screenshots/title': No such file or directory
ls: cannot access 'screenshots/hole': No such file or directory
ls: cannot access 'screenshots/entity': No such file or directory
ls: cannot access 'screenshots/entity-raw': No such file or directory
screenshots:
total 8
drwxr-xr-x  2 runner runner 4096 Oct 28 20:12 .
drwxr-xr-x 14 runner runner 4096 Oct 28 20:13 ..
performance:
/home/runner/work/backrooms-gdx/backrooms-gdx/core/src/main/java/com/backrooms/gdx/entity/EntityRenderer.java:352: error: incompatible types: possible lossy conversion from float to int
/home/runner/work/backrooms-gdx/backrooms-gdx/core/src/main/java/com/backrooms/gdx/entity/EntityRenderer.java:356: error: incompatible types: float cannot be converted to AnimationListener
4 errors
  /home/runner/work/backrooms-gdx/backrooms-gdx/core/src/main/java/com/backrooms/gdx/entity/EntityRenderer.java:130: error: incompatible types: possible lossy conversion from float to int
  /home/runner/work/backrooms-gdx/backrooms-gdx/core/src/main/java/com/backrooms/gdx/entity/EntityRenderer.java:235: error: incompatible types: float cannot be converted to AnimationListener
  /home/runner/work/backrooms-gdx/backrooms-gdx/core/src/main/java/com/backrooms/gdx/entity/EntityRenderer.java:352: error: incompatible types: possible lossy conversion from float to int
  /home/runner/work/backrooms-gdx/backrooms-gdx/core/src/main/java/com/backrooms/gdx/entity/EntityRenderer.java:356: error: incompatible types: float cannot be converted to AnimationListener
  4 errors
> Check your code and dependencies to fix the compilation error(s)
/home/runner/work/backrooms-gdx/backrooms-gdx/core/src/main/java/com/backrooms/gdx/entity/EntityRenderer.java:130: error: incompatible types: possible lossy conversion from float to int
/home/runner/work/backrooms-gdx/backrooms-gdx/core/src/main/java/com/backrooms/gdx/entity/EntityRenderer.java:235: error: incompatible types: float cannot be converted to AnimationListener
/home/runner/work/backrooms-gdx/backrooms-gdx/core/src/main/java/com/backrooms/gdx/entity/EntityRenderer.java:352: error: incompatible types: possible lossy conversion from float to int
/home/runner/work/backrooms-gdx/backrooms-gdx/core/src/main/java/com/backrooms/gdx/entity/EntityRenderer.java:356: error: incompatible types: float cannot be converted to AnimationListener
4 errors
  /home/runner/work/backrooms-gdx/backrooms-gdx/core/src/main/java/com/backrooms/gdx/entity/EntityRenderer.java:130: error: incompatible types: possible lossy conversion from float to int
  /home/runner/work/backrooms-gdx/backrooms-gdx/core/src/main/java/com/backrooms/gdx/entity/EntityRenderer.java:235: error: incompatible types: float cannot be converted to AnimationListener
  /home/runner/work/backrooms-gdx/backrooms-gdx/core/src/main/java/com/backrooms/gdx/entity/EntityRenderer.java:352: error: incompatible types: possible lossy conversion from float to int
  /home/runner/work/backrooms-gdx/backrooms-gdx/core/src/main/java/com/backrooms/gdx/entity/EntityRenderer.java:356: error: incompatible types: float cannot be converted to AnimationListener
  4 errors
> Check your code and dependencies to fix the compilation error(s)
render log tail:
Xlint:deprecation for details.
  Note: Some input files use or override a deprecated API.
  Note: Some messages have been simplified; recompile with -Xdiags:verbose to get full output
  4 errors
* Try:
> Check your code and dependencies to fix the compilation error(s)
> Run with --scan to get full insights.
BUILD FAILED in 9s
1 actionable task: 1 executed
To honour the JVM settings for this build a single-use Daemon process will be forked. For more on this, please refer to https://docs.gradle.org/8.11.1/userguide/gradle_daemon.html#sec:disabling_the_daemon in the Gradle documentation.
Daemon will be stopped at the end of the build 
> Task :desktop:processResources NO-SOURCE
> Task :core:compileJava FAILED
/home/runner/work/backrooms-gdx/backrooms-gdx/core/src/main/java/com/backrooms/gdx/entity/EntityRenderer.java:130: error: incompatible types: possible lossy conversion from float to int
            animation.animate(scareClip, 0.12f, null, 1f);
                                         ^
/home/runner/work/backrooms-gdx/backrooms-gdx/core/src/main/java/com/backrooms/gdx/entity/EntityRenderer.java:235: error: incompatible types: float cannot be converted to AnimationListener
                animation.setAnimation(idleClip, -1, 1f);
                                                     ^
/home/runner/work/backrooms-gdx/backrooms-gdx/core/src/main/java/com/backrooms/gdx/entity/EntityRenderer.java:352: error: incompatible types: possible lossy conversion from float to int
            animation.animate(wanted, 0.3f, null, wantedSpeed);
                                      ^
/home/runner/work/backrooms-gdx/backrooms-gdx/core/src/main/java/com/backrooms/gdx/entity/EntityRenderer.java:356: error: incompatible types: float cannot be converted to AnimationListener
            animation.setAnimation(wanted, -1, wantedSpeed);
                                               ^
Note: Some input files use or override a deprecated API.
Note: Recompile with -Xlint:deprecation for details.
Note: Some messages have been simplified; recompile with -Xdiags:verbose to get full output
4 errors
[Incubating] Problems report is available at: file:///home/runner/work/backrooms-gdx/backrooms-gdx/build/reports/problems/problems-report.html
FAILURE: Build failed with an exception.
* What went wrong:
Execution failed for task ':core:compileJava'.
> Compilation failed; see the compiler output below.
  /home/runner/work/backrooms-gdx/backrooms-gdx/core/src/main/java/com/backrooms/gdx/entity/EntityRenderer.java:130: error: incompatible types: possible lossy conversion from float to int
              animation.animate(scareClip, 0.12f, null, 1f);
                                           ^
  /home/runner/work/backrooms-gdx/backrooms-gdx/core/src/main/java/com/backrooms/gdx/entity/EntityRenderer.java:235: error: incompatible types: float cannot be converted to AnimationListener
                  animation.setAnimation(idleClip, -1, 1f);
                                                       ^
  /home/runner/work/backrooms-gdx/backrooms-gdx/core/src/main/java/com/backrooms/gdx/entity/EntityRenderer.java:352: error: incompatible types: possible lossy conversion from float to int
              animation.animate(wanted, 0.3f, null, wantedSpeed);
                                        ^
  /home/runner/work/backrooms-gdx/backrooms-gdx/core/src/main/java/com/backrooms/gdx/entity/EntityRenderer.java:356: error: incompatible types: float cannot be converted to AnimationListener
              animation.setAnimation(wanted, -1, wantedSpeed);
                                                 ^
  Note: Recompile with -Xlint:deprecation for details.
  Note: Some input files use or override a deprecated API.
  Note: Some messages have been simplified; recompile with -Xdiags:verbose to get full output
  4 errors
* Try:
> Check your code and dependencies to fix the compilation error(s)
> Run with --scan to get full insights.
BUILD FAILED in 9s
1 actionable task: 1 executed
```
