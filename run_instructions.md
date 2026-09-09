# Build and run

These steps build and install the debug app on a local device or emulator. Everything runs from the `KakaRemake/` folder, which is the Gradle project root.

## Set up

Install JDK 17 or newer. Android Studio ships one at `<Android Studio>/jbr`, and that JDK works.

Use Android Studio's SDK Manager to install SDK Platform 35, Android SDK Build-Tools and Android SDK Platform-Tools.

Gradle needs to know where the SDK is. Opening the project in Android Studio writes `KakaRemake/local.properties` for you. Otherwise set `ANDROID_HOME` yourself, or create `KakaRemake/local.properties` with a single `sdk.dir=` line pointing at the SDK. That file is deliberately untracked, so a fresh clone has neither until you do this.

```bash
export JAVA_HOME=/path/to/jdk17
export ANDROID_HOME=$HOME/Android/Sdk
export PATH="$JAVA_HOME/bin:$ANDROID_HOME/platform-tools:$PATH"
```

`KakaRemake/app/google-services.json` is committed, so Firebase needs no extra setup.

## Build

From `KakaRemake/` in Bash:

```bash
./gradlew :app:assembleDebug --console=plain
```

On Windows, replace `./gradlew` with `.\gradlew.bat`.

The first run downloads Gradle 8.11.1 and the dependencies, so expect several minutes. APK: `app/build/outputs/apk/debug/app-debug.apk` (roughly 36 MB).

## Run

Start an emulator or connect a phone with USB debugging enabled, then:

```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
adb shell monkey -p com.game254studios.kakaandchui -c android.intent.category.LAUNCHER 1
```

With more than one device attached, find the serial with `adb devices` and add `-s SERIAL` after `adb`.

Debug builds sign themselves with the local debug key, so no release keystore is needed.

## Tests and checks

Unit tests live in `app/src/test` and run on the JVM:

```bash
./gradlew :app:testDebugUnitTest --console=plain
```

The HTML report lands in `app/build/reports/tests/testDebugUnitTest/index.html`.

`./gradlew :app:lintDebug` currently fails while resolving `androidx.compose.ui:ui-test-junit4`. The Compose BOM is applied to `implementation` but not to `androidTestImplementation`, so that dependency has no version to resolve. The same gap affects instrumentation tests. Unit tests and `assembleDebug` are unaffected.

To start clean:

```bash
./gradlew clean --console=plain
```
