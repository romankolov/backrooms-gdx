# Last screenshot run

Commit: 983e4f1

```
files:
ls: cannot access 'screenshots/title': No such file or directory
ls: cannot access 'screenshots/hole': No such file or directory
ls: cannot access 'screenshots/entity': No such file or directory
screenshots:
total 8
drwxr-xr-x  2 runner runner 4096 Oct 28 12:26 .
drwxr-xr-x 14 runner runner 4096 Oct 28 12:27 ..
performance:
  3 errors
> Check your code and dependencies to fix the compilation error(s)
/home/runner/work/backrooms-gdx/backrooms-gdx/core/src/main/java/com/backrooms/gdx/screens/PlayScreen.java:91: error: cannot find symbol
/home/runner/work/backrooms-gdx/backrooms-gdx/core/src/main/java/com/backrooms/gdx/screens/PlayScreen.java:329: error: cannot find symbol
/home/runner/work/backrooms-gdx/backrooms-gdx/core/src/main/java/com/backrooms/gdx/screens/PlayScreen.java:517: error: cannot find symbol
3 errors
  /home/runner/work/backrooms-gdx/backrooms-gdx/core/src/main/java/com/backrooms/gdx/screens/PlayScreen.java:91: error: cannot find symbol
  /home/runner/work/backrooms-gdx/backrooms-gdx/core/src/main/java/com/backrooms/gdx/screens/PlayScreen.java:329: error: cannot find symbol
  /home/runner/work/backrooms-gdx/backrooms-gdx/core/src/main/java/com/backrooms/gdx/screens/PlayScreen.java:517: error: cannot find symbol
  3 errors
> Check your code and dependencies to fix the compilation error(s)
/home/runner/work/backrooms-gdx/backrooms-gdx/core/src/main/java/com/backrooms/gdx/screens/PlayScreen.java:91: error: cannot find symbol
/home/runner/work/backrooms-gdx/backrooms-gdx/core/src/main/java/com/backrooms/gdx/screens/PlayScreen.java:329: error: cannot find symbol
/home/runner/work/backrooms-gdx/backrooms-gdx/core/src/main/java/com/backrooms/gdx/screens/PlayScreen.java:517: error: cannot find symbol
3 errors
  /home/runner/work/backrooms-gdx/backrooms-gdx/core/src/main/java/com/backrooms/gdx/screens/PlayScreen.java:91: error: cannot find symbol
  /home/runner/work/backrooms-gdx/backrooms-gdx/core/src/main/java/com/backrooms/gdx/screens/PlayScreen.java:329: error: cannot find symbol
  /home/runner/work/backrooms-gdx/backrooms-gdx/core/src/main/java/com/backrooms/gdx/screens/PlayScreen.java:517: error: cannot find symbol
  3 errors
> Check your code and dependencies to fix the compilation error(s)
render log tail:
te: Some input files use or override a deprecated API.
  /home/runner/work/backrooms-gdx/backrooms-gdx/core/src/main/java/com/backrooms/gdx/screens/PlayScreen.java:91: error: cannot find symbol
          entity.setEnabled(settings.entity);
                ^
    symbol:   method setEnabled(boolean)
    location: variable entity of type EntityController
  /home/runner/work/backrooms-gdx/backrooms-gdx/core/src/main/java/com/backrooms/gdx/screens/PlayScreen.java:329: error: cannot find symbol
              if (entity.isEnabled()) {
                        ^
    symbol:   method isEnabled()
    location: variable entity of type EntityController
  /home/runner/work/backrooms-gdx/backrooms-gdx/core/src/main/java/com/backrooms/gdx/screens/PlayScreen.java:517: error: cannot find symbol
                  entity.setEnabled(settings.entity);
                        ^
    symbol:   method setEnabled(boolean)
    location: variable entity of type EntityController
  3 errors
* Try:
> Check your code and dependencies to fix the compilation error(s)
> Run with --scan to get full insights.
BUILD FAILED in 7s
1 actionable task: 1 executed
To honour the JVM settings for this build a single-use Daemon process will be forked. For more on this, please refer to https://docs.gradle.org/8.11.1/userguide/gradle_daemon.html#sec:disabling_the_daemon in the Gradle documentation.
Daemon will be stopped at the end of the build 
> Task :desktop:processResources NO-SOURCE
> Task :core:compileJava FAILED
/home/runner/work/backrooms-gdx/backrooms-gdx/core/src/main/java/com/backrooms/gdx/screens/PlayScreen.java:91: error: cannot find symbol
        entity.setEnabled(settings.entity);
              ^
  symbol:   method setEnabled(boolean)
  location: variable entity of type EntityController
/home/runner/work/backrooms-gdx/backrooms-gdx/core/src/main/java/com/backrooms/gdx/screens/PlayScreen.java:329: error: cannot find symbol
            if (entity.isEnabled()) {
                      ^
  symbol:   method isEnabled()
  location: variable entity of type EntityController
/home/runner/work/backrooms-gdx/backrooms-gdx/core/src/main/java/com/backrooms/gdx/screens/PlayScreen.java:517: error: cannot find symbol
                entity.setEnabled(settings.entity);
                      ^
  symbol:   method setEnabled(boolean)
  location: variable entity of type EntityController
Note: Some input files use or override a deprecated API.
Note: Recompile with -Xlint:deprecation for details.
3 errors
[Incubating] Problems report is available at: file:///home/runner/work/backrooms-gdx/backrooms-gdx/build/reports/problems/problems-report.html
FAILURE: Build failed with an exception.
* What went wrong:
Execution failed for task ':core:compileJava'.
> Compilation failed; see the compiler output below.
  Note: Recompile with -Xlint:deprecation for details.
  Note: Some input files use or override a deprecated API.
  /home/runner/work/backrooms-gdx/backrooms-gdx/core/src/main/java/com/backrooms/gdx/screens/PlayScreen.java:91: error: cannot find symbol
          entity.setEnabled(settings.entity);
                ^
    symbol:   method setEnabled(boolean)
    location: variable entity of type EntityController
  /home/runner/work/backrooms-gdx/backrooms-gdx/core/src/main/java/com/backrooms/gdx/screens/PlayScreen.java:329: error: cannot find symbol
              if (entity.isEnabled()) {
                        ^
    symbol:   method isEnabled()
    location: variable entity of type EntityController
  /home/runner/work/backrooms-gdx/backrooms-gdx/core/src/main/java/com/backrooms/gdx/screens/PlayScreen.java:517: error: cannot find symbol
                  entity.setEnabled(settings.entity);
                        ^
    symbol:   method setEnabled(boolean)
    location: variable entity of type EntityController
  3 errors
* Try:
> Check your code and dependencies to fix the compilation error(s)
> Run with --scan to get full insights.
BUILD FAILED in 7s
1 actionable task: 1 executed
```
