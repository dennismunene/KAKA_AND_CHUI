# Build and run

These steps build and install the debug app on a local device or emulator. Everything runs from the `KakaRemake/` folder, which is the Gradle project root.

## Set up

Install JDK 17 or newer. Android Studio ships one at `<Android Studio>/jbr`, and that JDK works.

Use Android Studio's SDK Manager to install SDK Platform 35, Android SDK Build-Tools and Android SDK Platform-Tools.

Gradle needs to know where the SDK is. Opening the project once in Android Studio writes `KakaRemake/local.properties` for you; otherwise set `ANDROID_HOME`, or create `KakaRemake/local.properties` yourself with a single `sdk.dir=` line pointing at the SDK. `local.properties` is untracked, so a fresh clone has no SDK path until you do one of these.

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

### Windows PowerShell

PowerShell does not read the `export` lines above, so set the variables its own way in the same session you build from:

```powershell
$env:JAVA_HOME = "C:\Program Files\Android\Android Studio\jbr"
$env:ANDROID_HOME = "$env:LOCALAPPDATA\Android\Sdk"
$env:PATH = "$env:JAVA_HOME\bin;$env:ANDROID_HOME\platform-tools;$env:PATH"
.\gradlew.bat :app:assembleDebug --console=plain
```

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

## Troubleshooting

**"SDK location not found ... define a valid SDK location with an ANDROID_HOME environment variable or by setting the sdk.dir path"** — `local.properties` is untracked and is missing on a fresh clone. Set `ANDROID_HOME`, or open the project once in Android Studio, then build again.

**Gradle picks the wrong JDK** — `gradle.properties` sets `org.gradle.java.installations.auto-detect=false`, so Gradle uses whichever JVM starts it. Point `JAVA_HOME` at JDK 17 or newer before running the wrapper. AGP 8.7.3 rejects older JDKs, and the app compiles against Java 17 source and target compatibility.

**`assembleRelease` produces an unsigned APK** — the `release` signing config in `app/build.gradle.kts` is an empty placeholder and is never attached to the release build type, so there is no local keystore fallback. Release builds also enable minification and resource shrinking. These instructions cover debug builds only.

## Good to know

The app ships Google's AdMob test application ID in `app/src/main/AndroidManifest.xml`, so local debug builds request test ads rather than live inventory. Analytics and Crashlytics still report to the real Firebase project in `app/google-services.json` when the device is online.

`KakaRemake/src`, `KakaRemake/res`, `KakaRemake/assets` and `KakaRemake/AndroidManifest.xml` are the original pre-Gradle app (package `com.game254studios`, min SDK 8). `settings.gradle.kts` includes only `:app`, so nothing there is compiled. Leave those files alone unless you are porting assets.

