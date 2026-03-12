# Kaka & Chui — Full Modernization Plan
### A Toddler's Swahili Learning Game — Rebuild & Enrichment Blueprint

---

## 1. Problem Statement

"Kaka and Chui" is a legacy Android educational game (circa 2013-2014) that teaches toddlers Swahili through flash cards and quizzes covering Vowels, Numbers, Shapes, and Colors. The project uses:

- **Ant-based build** (no Gradle)
- **AndEngine** (deprecated/unmaintained 2D game engine)
- **Java** (no Kotlin)
- **Min SDK 8** (Android 2.2 Froyo) / **Target SDK 19** (Android 4.4 KitKat)
- **800×480 fixed resolution** camera
- **Manual payment** wall (M-Pesa/Visa/PayPal instructions — no Play Store integration)
- **No analytics, no ads, no proper billing, no offline strategy, no adaptive learning**

### Current Content Inventory
| Module | Learn Screens | Quiz Items | Audio Files | Visuals |
|--------|:---:|:---:|:---:|:---:|
| **Vokali (Vowels)** | 5 (A, E, I, O, U) | 5 | 25+ (.aac) | Vowel shapes + words |
| **Tarakimu (Numbers)** | 10 (1-10 Swahili) | 10 | 30+ | Number visuals |
| **Maumbo (Shapes)** | 6 (Circle, Square, etc.) | 6 | 12+ | Shape icons |
| **Rangi (Colors)** | 6 (Green, Red, Blue, etc.) | 6 | 12+ | Color blocks |
| **Total** | **27** | **27** | **75+** | **156 graphics** |

### Current Architecture
```
src/com/game254studios/
├── MainActivity.java           # AndEngine BaseGameActivity (800x480 camera)
├── VideoActivity.java          # Video playback (splash + intro)
├── SceneManager.java           # Singleton scene manager (12 scenes)
├── Utils.java                  # Sprite animation utilities
├── MyTextureManager.java       # Texture loading/unloading
├── scenes/                     # 12 scene classes (Splash, Loading, MainMenu, 4x Soma*, 4x Zoezi*)
├── views/                      # 14 view classes (texture atlas loading, sprite creation)
├── models/                     # 14 model classes (data holders, SpriteSoundPair)
```

### Competitive Landscape (2025-2026)

Leading kids' Swahili/African-language apps offer features we currently lack:

| Feature | Mahlahle | PlayNative | Ambani Africa | Ningo Africa | **Kaka & Chui** |
|---------|:---:|:---:|:---:|:---:|:---:|
| AR Interactions | ✗ | ✗ | ✓ | ✗ | ✗ |
| AI-Adaptive Difficulty | ✗ | ✗ | ✗ | ✓ | ✗ |
| Native Speaker Audio | ✓ | ✓ | ✓ | ✓ | ✓ |
| Streaks/Badges/Coins | ✗ | ✓ | ✓ | ✓ | ✗ |
| Parent Dashboard | ✗ | ✓ | ✓ | ✓ | ✗ |
| COPPA-Compliant Ads | ✓ | ✓ | ✓ | ✓ | ✗ |
| In-App Subscriptions | ✓ | ✓ | ✓ | ✓ | ✗ |
| Offline Mode | ✓ | ✓ | ✗ | ✗ | ✗ |
| Multi-Profile | ✗ | ✗ | ✗ | ✗ | ✗ |
| Spaced Repetition | ✗ | ✗ | ✗ | ✓ | ✗ |
| Mini-Games | ✗ | ✓ | ✓ | ✓ | ✗ |

---

## 2. Proposed Approach

A **phased, incremental modernization** with **parallel agent orchestration**. Each phase delivers a shippable milestone. Specialized agents handle continuous validation, QA, and compliance review throughout.

---

## 3. Specialized Agents — Detailed Work Items

---

### 3.1 — 🔨 Build Validator Agent

**Purpose:** Continuously validates that the project compiles, installs, and launches on an Android emulator via ADB. Acts as the gatekeeper — no code is considered "done" until this agent gives a green signal.

---

#### Work Item BV-01: Environment Setup
**When:** Once at start, before any other BV work item
**Steps:**
1. Verify Android SDK is installed: `$ANDROID_HOME/cmdline-tools/latest/bin/sdkmanager --list`
2. Verify ADB is available: `adb version`
3. Check for running emulator: `adb devices` — expect at least one `emulator-XXXX device`
4. If no emulator running, create and start one:
   ```bash
   $ANDROID_HOME/cmdline-tools/latest/bin/avdmanager create avd \
     --name kaka_test --package "system-images;android-34;google_apis;x86_64" --device "pixel_6"
   $ANDROID_HOME/emulator/emulator -avd kaka_test -no-window -no-audio -gpu swiftshader_indirect &
   adb wait-for-device
   adb shell getprop sys.boot_completed  # wait until "1"
   ```
5. Verify emulator is responsive: `adb shell echo "hello"` → expect "hello"
6. Report environment status: SDK version, emulator device name, API level, screen resolution

**Pass criteria:** ADB connected to a booted emulator with API 34+
**Fail criteria:** No emulator, ADB not found, SDK missing

---

#### Work Item BV-02: Gradle Build Validation
**When:** After p1-gradle, p1-sdk, and every subsequent code change
**Steps:**
1. Navigate to project root: `cd /mnt/e/KAKA_AND_CHUI/KakaRemake`
2. Clean previous build: `./gradlew clean 2>&1 | tail -5`
3. Run debug build: `./gradlew assembleDebug 2>&1`
4. Capture exit code: `echo $?`
5. If build succeeds:
   - Verify APK exists: `ls -la app/build/outputs/apk/debug/app-debug.apk`
   - Record APK size: `du -h app/build/outputs/apk/debug/app-debug.apk`
   - Record build time from Gradle output
6. If build fails:
   - Capture full error output
   - Identify error type: compilation error, resource error, dependency error, manifest error
   - Extract the specific file:line causing the error
   - Report: error type, file, line number, error message, suggested fix

**Pass criteria:** `BUILD SUCCESSFUL` in output, APK file exists, exit code 0
**Fail criteria:** Any non-zero exit code, missing APK, compilation errors
**Output:** Build status (PASS/FAIL), APK size, build time, error details if failed

---

#### Work Item BV-03: APK Installation on Emulator
**When:** After every successful BV-02
**Steps:**
1. Uninstall previous version (ignore errors if not installed):
   ```bash
   adb uninstall com.game254studios.kakaandchui 2>/dev/null || true
   ```
2. Install debug APK:
   ```bash
   adb install -r -t app/build/outputs/apk/debug/app-debug.apk 2>&1
   ```
3. Verify installation:
   ```bash
   adb shell pm list packages | grep kakaandchui
   ```
4. If install fails, capture error:
   - `INSTALL_FAILED_OLDER_SDK` → minSdk mismatch
   - `INSTALL_FAILED_UPDATE_INCOMPATIBLE` → signature mismatch, need uninstall first
   - `INSTALL_FAILED_NO_MATCHING_ABIS` → wrong ABI for emulator

**Pass criteria:** Package `com.game254studios.kakaandchui` listed in installed packages
**Fail criteria:** Install error, package not found after install

---

#### Work Item BV-04: App Launch Verification
**When:** After every successful BV-03
**Steps:**
1. Clear logcat: `adb logcat -c`
2. Launch app:
   ```bash
   adb shell am start -n com.game254studios.kakaandchui/.MainActivity
   ```
3. Wait 5 seconds for app to initialize: `sleep 5`
4. Check if app is in foreground:
   ```bash
   adb shell dumpsys activity activities | grep "mResumedActivity" | grep kakaandchui
   ```
5. Check for crashes in logcat:
   ```bash
   adb logcat -d -s AndroidRuntime:E | grep -A 20 "FATAL EXCEPTION"
   ```
6. Check for ANR (Application Not Responding):
   ```bash
   adb logcat -d | grep "ANR in"
   ```
7. Take screenshot:
   ```bash
   adb exec-out screencap -p > /tmp/kaka_launch_screenshot.png
   ```
8. Verify no crash dialog visible:
   ```bash
   adb shell dumpsys window | grep "mCurrentFocus" | grep -v "has stopped"
   ```

**Pass criteria:** App in foreground, no FATAL EXCEPTION, no ANR, screenshot captured
**Fail criteria:** Crash in logcat, ANR detected, app not in foreground

---

#### Work Item BV-05: Screen Navigation Smoke Test
**When:** After BV-04 succeeds, run after Phase 2+ screens are implemented
**Steps:**
1. App should be on home screen (from BV-04)
2. Navigate to first module (Vokali):
   ```bash
   # Use UIAutomator to tap module button (coordinates depend on layout)
   adb shell input tap 200 240  # approximate center of Vokali card
   sleep 2
   adb exec-out screencap -p > /tmp/kaka_vokali_screen.png
   ```
3. Navigate back:
   ```bash
   adb shell input keyevent KEYCODE_BACK
   sleep 1
   ```
4. Check app still alive (not crashed):
   ```bash
   adb shell dumpsys activity activities | grep "mResumedActivity" | grep kakaandchui
   adb logcat -d -s AndroidRuntime:E | tail -5
   ```
5. Repeat for each implemented module
6. Stop app:
   ```bash
   adb shell am force-stop com.game254studios.kakaandchui
   ```

**Pass criteria:** All screens render without crash, back navigation works, app stays alive
**Fail criteria:** Crash on any navigation, black screen, frozen UI

---

#### Work Item BV-06: Resource & Asset Integrity Check
**When:** After Phase 8 assets are integrated, or when new assets are added
**Steps:**
1. Build APK (BV-02)
2. Extract and list resources:
   ```bash
   $ANDROID_HOME/build-tools/34.0.0/aapt2 dump resources app/build/outputs/apk/debug/app-debug.apk | head -100
   ```
3. Verify no missing resource references:
   ```bash
   ./gradlew lintDebug 2>&1 | grep -i "missing\|not found\|unresolved"
   ```
4. Check APK for all expected asset directories:
   ```bash
   unzip -l app/build/outputs/apk/debug/app-debug.apk | grep "assets/" | wc -l
   ```
5. Compare count against expected asset count (should match source assets/ directory)
6. Verify no oversized assets (>2MB individual files that should be compressed):
   ```bash
   unzip -l app/build/outputs/apk/debug/app-debug.apk | awk '$1 > 2000000 {print}'
   ```

**Pass criteria:** No missing resources, all assets bundled, no lint errors related to resources
**Fail criteria:** Missing resources, asset count mismatch, oversized uncompressed assets

---

#### Work Item BV-07: Dependency & SDK Compatibility Check
**When:** After p1-sdk, after adding any new dependency
**Steps:**
1. Check for dependency conflicts:
   ```bash
   ./gradlew app:dependencies --configuration debugRuntimeClasspath 2>&1 | grep "FAILED\|CONFLICT"
   ```
2. Verify no deprecated API usage warnings:
   ```bash
   ./gradlew assembleDebug 2>&1 | grep -i "deprecated\|warning:" | head -20
   ```
3. Run lint for API compatibility:
   ```bash
   ./gradlew lintDebug 2>&1 | grep -i "NewApi\|InlinedApi\|ObsoleteSdkInt"
   ```
4. Verify all SDK versions align:
   ```bash
   grep -r "compileSdk\|targetSdk\|minSdk" app/build.gradle.kts
   ```

**Pass criteria:** No conflicts, no critical deprecation warnings, lint clean for API issues
**Fail criteria:** Dependency conflict, API used above minSdk without check

---

#### Build Validator — Trigger Schedule

| Trigger Event | Work Items to Run | Phase |
|---------------|-------------------|-------|
| Gradle migration complete | BV-01, BV-02 | P1 |
| SDK & dependencies updated | BV-02, BV-07 | P1 |
| Kotlin migration (per batch of files) | BV-02 | P1 |
| AndEngine replaced with Compose | BV-02, BV-03, BV-04 | P1 |
| Each UI screen migrated | BV-02, BV-03, BV-04, BV-05 | P2 |
| New module added | BV-02, BV-03, BV-04, BV-05, BV-06 | P3 |
| Free assets integrated | BV-02, BV-06 | P8 |
| AdMob SDK added | BV-02, BV-03, BV-04, BV-07 | P5 |
| Play Billing SDK added | BV-02, BV-03, BV-04, BV-07 | P5 |
| Firebase SDKs added | BV-02, BV-07 | P6 |
| Before any git commit | BV-02 (minimum) | All |
| Pre-release candidate | BV-01 through BV-07 (full suite) | P7 |

---

### 3.2 — 🧪 QA Testing Agent

**Purpose:** Thoroughly tests every feature for functionality, usability, and edge cases. This agent writes and runs automated tests AND performs manual-equivalent checks via ADB/UIAutomator. Critical for a toddler app where broken UX can't be "figured out" by the user.

---

#### Work Item QA-01: Navigation Flow Testing
**When:** After Phase 2 navigation is implemented, re-run after each new screen
**Precondition:** App installed on emulator (BV-03 passed)
**Steps:**
1. Launch app: `adb shell am start -n com.game254studios.kakaandchui/.MainActivity`
2. Wait for splash to finish: `sleep 6`
3. Verify home screen is displayed:
   ```bash
   adb exec-out screencap -p > /tmp/qa_home.png
   adb logcat -d | grep -i "HomeScreen\|MainMenu" | tail -3
   ```
4. For EACH module (Vokali, Tarakimu, Maumbo, Rangi):
   a. Tap module card (use content description or coordinates)
   b. Wait 2s, screenshot → verify learn screen rendered
   c. Swipe right to advance through all learn items
   d. Verify final item reached (progress dots show last position)
   e. Tap "Quiz" or navigate to quiz
   f. Wait 2s, screenshot → verify quiz screen rendered
   g. Tap back → verify return to module or home
5. Navigate to Settings → verify settings dialog opens
6. Navigate to Parent Zone → verify parental gate appears
7. Enter correct parental gate answer → verify parent zone opens
8. Tap back repeatedly → verify we return to home (never stuck)
9. Check logcat for any errors throughout: `adb logcat -d -s AndroidRuntime:E`

**Pass criteria:** Every screen reachable, every back navigation works, no crashes, no stuck states
**Fail criteria:** Any screen unreachable, crash, infinite loop, wrong screen displayed

**Test Matrix:**
| Route | Expected Screens | Back Behavior |
|-------|-----------------|---------------|
| Home → Vokali → Learn → Quiz → Home | 4 screens | Stack pop |
| Home → Tarakimu → Learn → Quiz → Home | 4 screens | Stack pop |
| Home → Maumbo → Learn → Quiz → Home | 4 screens | Stack pop |
| Home → Rangi → Learn → Quiz → Home | 4 screens | Stack pop |
| Home → Settings → Home | 2 screens | Dismiss dialog |
| Home → Parent Zone (gate) → Dashboard → Home | 3 screens | Stack pop |

---

#### Work Item QA-02: Quiz Logic Verification
**When:** After Phase 2 quiz screens are implemented
**Precondition:** App on quiz screen
**Steps:**
1. Navigate to Vokali quiz
2. Listen for audio prompt (verify via logcat that audio played):
   ```bash
   adb logcat -d | grep -i "MediaPlayer\|audio\|sound" | tail -5
   ```
3. **Test correct answer:**
   a. Identify correct answer button (from audio prompt)
   b. Tap correct answer
   c. Verify: green checkmark/confetti animation appears
   d. Verify: positive sound plays (applause/chime)
   e. Verify: quiz advances to next question
   f. Screenshot after each correct answer
4. **Test wrong answer:**
   a. Tap an incorrect answer
   b. Verify: red X / shake animation appears
   c. Verify: sad/wrong sound plays
   d. Verify: quiz does NOT advance (same question remains)
   e. Verify: child can try again
5. **Test answer randomization:**
   a. Play through 10 quiz rounds
   b. Log the positions of correct answers
   c. Verify correct answer position is NOT always the same slot
   d. Verify the same correct answer doesn't appear consecutively (unless only 1 item left)
6. **Test quiz completion:**
   a. Answer all questions correctly
   b. Verify end-of-quiz summary screen appears
   c. Verify star rating displayed (1-3 stars based on accuracy)
   d. Verify "play again" and "back to home" buttons present
   e. Screenshot summary screen
7. **Test scoring accuracy:**
   a. Complete quiz with 0 mistakes → verify 3 stars
   b. Complete quiz with 1-2 mistakes → verify 2 stars
   c. Complete quiz with 3+ mistakes → verify 1 star
8. Repeat for ALL module quizzes (Tarakimu, Maumbo, Rangi)

**Pass criteria:** Correct/wrong detection accurate, animations fire, scoring correct, randomization works
**Fail criteria:** Wrong answer accepted as correct, no feedback, scoring miscalculation, crash

---

#### Work Item QA-03: Audio Playback Testing
**When:** After learn/quiz screens are implemented
**Precondition:** App installed, device volume up
**Steps:**
1. **Learn screen audio:**
   For EACH module, for EACH learn item:
   a. Navigate to item
   b. Verify audio auto-plays on item appear:
      ```bash
      adb logcat -d | grep -i "play\|media" | tail -3
      ```
   c. Tap the item → verify audio replays ("tap to hear again")
   d. Verify no audio overlap (previous audio stops before new one starts)
2. **Quiz screen audio:**
   For EACH quiz:
   a. Verify question audio plays on quiz start
   b. Verify feedback sounds play (correct/wrong)
   c. Verify quiz audio doesn't loop infinitely
3. **Background music:**
   a. Navigate to home screen
   b. Verify background music is playing
   c. Go to Settings → toggle music OFF → verify music stops
   d. Toggle music ON → verify music resumes
   e. Navigate to learn screen → verify music behavior (should pause or lower volume)
   f. Return to home → verify music resumes
4. **Mute device test:**
   a. Set device to silent: `adb shell cmd media volume --set 0`
   b. Navigate through learn/quiz screens
   c. Verify app doesn't crash when audio can't play
   d. Restore volume: `adb shell cmd media volume --set 10`
5. **Audio file integrity:**
   For EACH audio file in assets/:
   a. Verify file is not 0 bytes
   b. Verify file plays without error (MediaPlayer doesn't throw exception)

**Pass criteria:** All audio plays at correct times, no overlaps, mute doesn't crash, toggle works
**Fail criteria:** Missing audio, wrong audio for item, crash on mute, audio overlap, toggle broken

---

#### Work Item QA-04: Toddler-Proofing Tests
**When:** After Phase 2 UI is complete
**Purpose:** Toddlers are aggressive, imprecise tappers. The app must survive chaotic input.
**Steps:**
1. **Rapid tap test:**
   ```bash
   # Simulate 50 rapid taps on quiz area in 5 seconds
   for i in $(seq 1 50); do
     adb shell input tap $((RANDOM % 400 + 200)) $((RANDOM % 300 + 100))
     sleep 0.1
   done
   adb logcat -d -s AndroidRuntime:E | tail -10
   ```
   Verify: no crash, no duplicate score counting, no navigation glitch

2. **Multi-touch test:**
   ```bash
   # Simulate two simultaneous touches
   adb shell input swipe 200 200 200 200 100 &
   adb shell input swipe 600 300 600 300 100 &
   wait
   ```
   Verify: app handles gracefully (ignores second touch or handles both)

3. **Button mashing on navigation:**
   ```bash
   # Rapidly tap back button 20 times
   for i in $(seq 1 20); do
     adb shell input keyevent KEYCODE_BACK
     sleep 0.2
   done
   ```
   Verify: app navigates back to home or shows exit dialog, never crashes

4. **Swipe during animation:**
   a. Trigger a correct answer animation
   b. Immediately swipe to next quiz
   c. Verify: animation cancels cleanly, no rendering artifacts

5. **Touch target size audit:**
   a. Extract all clickable composable dimensions from layout inspector or screenshot analysis
   b. Verify every interactive element is ≥64dp × 64dp
   c. Verify spacing between adjacent buttons is ≥16dp (prevent mis-taps)
   d. Document any elements below threshold

6. **Screen edge taps:**
   ```bash
   # Tap all four corners
   adb shell input tap 10 10
   adb shell input tap 790 10
   adb shell input tap 10 470
   adb shell input tap 790 470
   ```
   Verify: no hidden buttons, no accidental navigation, no crashes

**Pass criteria:** App survives all chaotic input, no crashes, no score corruption, touch targets ≥64dp
**Fail criteria:** Any crash, double-counting, navigation glitch, elements <64dp

---

#### Work Item QA-05: Offline & Connectivity Testing
**When:** After Phase 9.1 offline mode is implemented
**Steps:**
1. Enable airplane mode:
   ```bash
   adb shell settings put global airplane_mode_on 1
   adb shell am broadcast -a android.intent.action.AIRPLANE_MODE --ez state true
   ```
2. Launch app from cold start
3. Verify: splash screen loads, home screen renders
4. Navigate through ALL modules (learn + quiz)
5. Verify: all assets load (images, audio, animations)
6. Verify: no "network error" dialogs or blank screens
7. Verify: progress saves locally
8. Complete a quiz → verify score/XP/coins saved
9. Disable airplane mode:
   ```bash
   adb shell settings put global airplane_mode_on 0
   adb shell am broadcast -a android.intent.action.AIRPLANE_MODE --ez state false
   ```
10. Verify: queued analytics events sync (check Firebase logs)
11. Verify: subscription status re-validates if premium user

**Pass criteria:** Full app functionality offline, progress persists, data syncs on reconnect
**Fail criteria:** Any screen fails to load, progress lost, crash without network

---

#### Work Item QA-06: Profile & Data Persistence Testing
**When:** After Phase 6.1 (local data) and Phase 9.2 (multi-profile)
**Steps:**
1. **Fresh install test:**
   a. Uninstall app: `adb uninstall com.game254studios.kakaandchui`
   b. Install app: BV-03
   c. Verify onboarding flow triggers
   d. Create profile "Child 1"
   e. Complete 3 vowel lessons and 1 quiz
   f. Note XP, coins, streak count
2. **Kill and restore:**
   a. Force stop: `adb shell am force-stop com.game254studios.kakaandchui`
   b. Relaunch app
   c. Verify: profile "Child 1" auto-selected
   d. Verify: XP, coins, streak, progress match what was recorded
   e. Verify: lessons show as completed (not reset)
3. **Multi-profile isolation:**
   a. Create profile "Child 2"
   b. Verify: Child 2 has 0 XP, 0 coins, no progress
   c. Complete a lesson as Child 2
   d. Switch back to Child 1
   e. Verify: Child 1 progress unchanged
   f. Verify: Child 2 progress didn't bleed into Child 1
4. **Streak persistence across days:**
   a. Set device date to Day 1: `adb shell date -s "2026-03-12 10:00:00"`
   b. Complete daily challenge → streak = 1
   c. Set date to Day 2: `adb shell date -s "2026-03-13 10:00:00"`
   d. Complete daily challenge → streak = 2
   e. Set date to Day 4 (skip Day 3): `adb shell date -s "2026-03-15 10:00:00"`
   f. Verify: streak reset to 0 (unless streak freeze used)
5. **App update simulation:**
   a. Note current progress/coins/XP
   b. Install newer version over existing: `adb install -r new_app.apk`
   c. Verify: all progress preserved after update

**Pass criteria:** All data persists, profiles isolated, streaks track correctly, updates preserve data
**Fail criteria:** Data loss, cross-profile contamination, streak miscounting

---

#### Work Item QA-07: Gamification System Testing
**When:** After Phase 4 gamification is implemented
**Steps:**
1. **XP system:**
   a. Note starting XP = 0
   b. Complete a lesson → verify XP increases by expected amount
   c. Complete a quiz with 3 stars → verify bonus XP
   d. Verify XP bar on home screen updates in real-time
   e. Accumulate enough XP to level up → verify level-up animation plays
   f. Verify level name changes (Chick → Duckling → etc.)
2. **Coin system:**
   a. Note starting coins = 0
   b. Complete quiz → verify coins earned
   c. Navigate to rewards store → verify coin balance shown
   d. Purchase an item → verify coin balance decreases
   e. Verify purchased item is applied (e.g., avatar hat appears)
   f. Try purchasing with insufficient coins → verify denied gracefully
3. **Badge system:**
   a. Complete first lesson → verify "First Steps" badge earned
   b. Verify badge unlock animation plays
   c. Navigate to trophy case → verify badge displayed
   d. 3-star all vowel quizzes → verify "Vowel Master" badge
   e. Verify badges don't double-award on replay
4. **Streak system:**
   a. Complete daily challenge → verify streak = 1
   b. Verify streak fire animation appears
   c. Verify streak counter increments each consecutive day
5. **Daily challenge:**
   a. Verify one challenge appears per day
   b. Complete challenge → verify reward granted
   c. Verify challenge doesn't reset mid-day on app restart
   d. Verify new challenge appears next day

**Pass criteria:** All gamification mechanics work, correct awards, no double-counting, animations fire
**Fail criteria:** Wrong XP/coin amounts, missing badges, streak errors, duplicate rewards

---

#### Work Item QA-08: Multi-Device Layout Testing
**When:** After Phase 2 responsive layouts are done
**Steps:**
1. Test on emulators with different configurations:
   ```bash
   # Create multiple AVDs
   avdmanager create avd --name phone_small --device "Nexus 5" --package "system-images;android-34;google_apis;x86_64"
   avdmanager create avd --name tablet_7 --device "Nexus 7" --package "system-images;android-34;google_apis;x86_64"
   avdmanager create avd --name tablet_10 --device "pixel_tablet" --package "system-images;android-34;google_apis;x86_64"
   ```
2. For EACH device:
   a. Install and launch app
   b. Screenshot every screen (home, learn, quiz, settings, parent zone)
   c. Verify no content cut off, no overlapping elements
   d. Verify text is readable (not too small on phone, not too large on tablet)
   e. Verify buttons are tappable (not too small on any device)
   f. Test landscape AND portrait (if supported)
3. Compile screenshot comparison report

**Pass criteria:** All screens render correctly on all device sizes, no clipping, no overlap
**Fail criteria:** Content clipped, text unreadable, buttons unusable, layout broken

---

#### Work Item QA-09: Accessibility Testing
**When:** After Phase 2.7 accessibility features are implemented
**Steps:**
1. **TalkBack testing:**
   ```bash
   adb shell settings put secure enabled_accessibility_services com.google.android.marvin.talkback/com.google.android.marvin.talkback.TalkBackService
   adb shell settings put secure accessibility_enabled 1
   ```
   a. Navigate through all screens using swipe gestures (TalkBack mode)
   b. Verify every interactive element has a content description
   c. Verify content descriptions are meaningful ("Vowel A - tap to learn" not "button1")
   d. Verify quiz answers are announced clearly
2. **Font scaling:**
   ```bash
   adb shell settings put system font_scale 2.0  # Largest font
   ```
   a. Screenshot all screens → verify text doesn't overflow containers
   b. Verify all text remains readable
   c. Reset: `adb shell settings put system font_scale 1.0`
3. **High contrast:**
   a. Enable high contrast mode
   b. Screenshot all screens → verify content still visible
4. **Disable TalkBack when done:**
   ```bash
   adb shell settings put secure enabled_accessibility_services ""
   adb shell settings put secure accessibility_enabled 0
   ```

**Pass criteria:** All elements have descriptions, TalkBack navigation works, font scaling doesn't break layout
**Fail criteria:** Missing descriptions, elements unreachable, text overflow, crash with accessibility on

---

#### Work Item QA-10: Interruption & Lifecycle Testing
**When:** After Phase 2 is complete
**Steps:**
1. **Phone call interruption:**
   ```bash
   # While on quiz screen mid-question:
   adb shell am start -a android.intent.action.CALL -d tel:5551234567
   sleep 3
   adb shell input keyevent KEYCODE_ENDCALL
   ```
   Verify: app resumes, quiz state preserved, audio resumes if playing

2. **Notification overlay:**
   ```bash
   adb shell am start -a android.settings.SETTINGS
   sleep 2
   adb shell input keyevent KEYCODE_BACK
   ```
   Verify: app still in foreground, state preserved

3. **Home and return:**
   ```bash
   adb shell input keyevent KEYCODE_HOME
   sleep 5
   adb shell am start -n com.game254studios.kakaandchui/.MainActivity
   ```
   Verify: app resumes exactly where left off

4. **Screen rotation (if supported):**
   ```bash
   adb shell settings put system accelerometer_rotation 1
   adb shell content insert --uri content://settings/system --bind name:s:user_rotation --bind value:i:1
   sleep 2
   adb shell content insert --uri content://settings/system --bind name:s:user_rotation --bind value:i:0
   ```
   Verify: no crash, state preserved

5. **Low memory simulation:**
   ```bash
   adb shell am send-trim-memory com.game254studios.kakaandchui RUNNING_CRITICAL
   ```
   Verify: app doesn't crash, recovers gracefully

6. **Process death and restore:**
   ```bash
   # On quiz screen mid-question
   adb shell am kill com.game254studios.kakaandchui
   # Relaunch from recents
   adb shell am start -n com.game254studios.kakaandchui/.MainActivity
   ```
   Verify: app restores to same screen or gracefully returns to home

**Pass criteria:** App survives all interruptions, state preserved or gracefully recovered
**Fail criteria:** Crash, lost state, frozen UI, audio continues playing after app backgrounded

---

#### QA Testing — Trigger Schedule

| Trigger Event | Work Items to Run | Phase |
|---------------|-------------------|-------|
| Navigation implemented | QA-01 | P2 |
| Quiz screens ready | QA-01, QA-02, QA-03 | P2 |
| UI responsive layouts done | QA-08 | P2 |
| Accessibility features done | QA-09 | P2 |
| Each new module added | QA-01, QA-02, QA-03 | P3 |
| Gamification system done | QA-07 | P4 |
| Data layer done | QA-06 | P6 |
| Offline mode done | QA-05 | P9 |
| Free assets integrated | QA-03 (audio), QA-08 (visuals) | P8 |
| Pre-release candidate | QA-01 through QA-10 (full suite) | P7 |

---

### 3.3 — 📋 Compliance & Ad Review Agent

**Purpose:** Performs code-level and runtime audits to ensure the app passes Google Play Families Policy review, meets COPPA requirements, and has properly placed/configured ads. Failures here mean Play Store rejection — this agent is the final gate.

---

#### Work Item CR-01: AdMob SDK Configuration Audit
**When:** After Phase 5.2 AdMob integration
**Steps:**
1. **Code review — child-directed tagging:**
   ```bash
   # Search for ALL AdRequest builder usages in codebase
   grep -rn "AdRequest" app/src/ --include="*.kt"
   ```
   For EACH AdRequest found, verify it includes:
   ```kotlin
   .tagForChildDirectedTreatment(TagForChildDirectedTreatment.TAG_FOR_CHILD_DIRECTED_TREATMENT_TRUE)
   ```
   **FAIL if any AdRequest is missing this tag.**

2. **Code review — max ad content rating:**
   ```bash
   grep -rn "RequestConfiguration" app/src/ --include="*.kt"
   ```
   Verify global config includes:
   ```kotlin
   .setMaxAdContentRating(RequestConfiguration.MAX_AD_CONTENT_RATING_G)
   ```

3. **Code review — no personalized ads:**
   ```bash
   grep -rn "setTagForUnderAgeOfConsent\|PersonalizedAd\|NonPersonalizedAd" app/src/ --include="*.kt"
   ```
   Verify tag for under age of consent is set to TRUE.

4. **Manifest review:**
   ```bash
   grep -A5 "com.google.android.gms.ads" app/src/main/AndroidManifest.xml
   ```
   Verify `<meta-data>` for AdMob app ID is present.
   Verify no `AD_MANAGER_APP` flag (that's for AdManager, not AdMob family-safe).

5. **Build dependency review:**
   ```bash
   grep -r "play-services-ads\|com.google.android.gms:play-services-ads" app/build.gradle.kts
   ```
   Verify using Google Mobile Ads SDK (self-certified for families).

6. **Test ad serving on emulator:**
   a. Launch app, navigate to home screen
   b. Check if test ads appear (test mode should be active on emulator)
   c. Verify banner ad renders in correct position (bottom of home, not overlapping content)
   d. Wait 5+ minutes, trigger interstitial → verify it appears with close button

**Pass criteria:** All ad requests tagged child-directed, max rating G, family-safe SDK version, test ads render
**Fail criteria:** ANY untagged ad request, missing config, non-family-safe SDK

**Output:** Compliance report listing each ad request location, tagging status, and SDK version

---

#### Work Item CR-02: Ad Placement & UX Audit
**When:** After AdMob integration, re-run before launch
**Steps:**
1. **Banner ad placement:**
   a. Navigate to home screen → screenshot
   b. Measure banner position: must be clearly separated from interactive buttons
   c. Verify banner has "Ad" label or is clearly distinguishable
   d. Verify banner doesn't overlap module cards, mascot, or navigation
   e. Tap banner → verify it opens in browser/Play Store (not in-app WebView that traps user)
   f. Navigate to learn screen → verify NO banner ad present
   g. Navigate to quiz screen → verify NO banner ad present
   h. Navigate to parent zone → verify NO banner ad present

2. **Interstitial ad placement:**
   a. Complete a quiz → verify interstitial ONLY shows between quiz completion and results screen
   b. Complete another quiz within 5 minutes → verify NO interstitial (frequency cap)
   c. Wait 5+ minutes, complete quiz → verify interstitial appears
   d. Verify close button is visible and ≥48dp
   e. Verify close button appears within 5 seconds (no forced viewing)
   f. Tap close → verify return to app (not another ad)
   g. Verify NO interstitial during active learning/quiz gameplay (mid-question)

3. **Rewarded ad placement:**
   a. Navigate to rewards store
   b. Find "Watch ad for coins" button → tap it
   c. Verify PARENTAL GATE appears first (math problem or similar)
   d. Fail parental gate → verify ad does NOT play
   e. Pass parental gate → verify rewarded ad plays
   f. Complete rewarded ad → verify coins credited
   g. Skip/close rewarded ad → verify no coins credited (fair exchange)
   h. Verify rewarded ad is NEVER shown to child without parental gate

4. **Premium user ad-free experience:**
   a. Simulate premium subscription (set in DataStore)
   b. Navigate all screens → verify ZERO ads appear anywhere
   c. Verify "Watch ad for coins" button is hidden or replaced with direct purchase

**Pass criteria:** Ads only in approved positions, frequency caps work, parental gate on rewarded, no ads for premium
**Fail criteria:** Ad during gameplay, no close button, rewarded without gate, ads for premium users

---

#### Work Item CR-03: Parental Gate Audit
**When:** After Phase 4.6 parent dashboard and Phase 5 monetization
**Steps:**
1. **Inventory all parental gates:**
   ```bash
   grep -rn "ParentalGate\|parentalGate\|parental_gate\|ageGate\|age_gate" app/src/ --include="*.kt"
   ```
   Document every location where a parental gate is used.

2. **Verify gates protect ALL required actions:**
   | Action | Gate Required? | Gate Present? |
   |--------|:-:|:-:|
   | In-app purchase (any) | YES | |
   | Subscription management | YES | |
   | External link (website) | YES | |
   | Rewarded ad viewing | YES | |
   | Parent zone / settings | YES | |
   | Social sharing (if any) | YES | |
   | Account creation | YES | |
   | Changing child profile | YES | |
   | Uninstalling content | NO | |
   | Playing game content | NO | |

3. **Test parental gate strength:**
   a. Present gate → verify it requires adult-level knowledge (math problem, text reading)
   b. Verify gate is NOT solvable by a toddler:
      - Not a simple "press button" dismiss
      - Not a predictable sequence
      - Requires reading comprehension or arithmetic (e.g., "What is 14 + 23?")
   c. Enter wrong answer → verify gate blocks access
   d. Enter right answer → verify access granted
   e. Verify gate re-triggers on each access (doesn't stay "unlocked" permanently)
   f. Verify gate timeout: if answered correctly, re-lock after 5 minutes of inactivity

4. **External link audit:**
   ```bash
   grep -rn "Intent\|ACTION_VIEW\|openUrl\|Uri.parse\|https://" app/src/ --include="*.kt"
   ```
   For EACH external link/intent found:
   a. Verify it's behind a parental gate
   b. Verify it doesn't auto-open (requires explicit user action)

**Pass criteria:** All restricted actions gated, gate is adult-only difficulty, no ungated external links
**Fail criteria:** Any restricted action accessible without gate, gate solvable by toddler

---

#### Work Item CR-04: Privacy & Data Collection Audit
**When:** After Phase 5.3 and Phase 6, re-run before launch
**Steps:**
1. **Permission audit:**
   ```bash
   grep -A2 "uses-permission" app/src/main/AndroidManifest.xml
   ```
   Verify ONLY necessary permissions:
   - `INTERNET` — OK (for ads, analytics, billing)
   - `ACCESS_NETWORK_STATE` — OK (for connectivity check)
   - NO `READ_CONTACTS`, `CAMERA`, `MICROPHONE` (unless speech recognition in Phase 3.4 stretch goal)
   - NO `ACCESS_FINE_LOCATION`, `ACCESS_COARSE_LOCATION`
   - NO `READ_PHONE_STATE`, `READ_EXTERNAL_STORAGE`

2. **Data collection inventory:**
   ```bash
   # Search for analytics events
   grep -rn "logEvent\|setUserProperty\|Analytics" app/src/ --include="*.kt"
   ```
   For EACH analytics event, document:
   - What data is sent
   - Is it personal data? (name, email, device ID)
   - Is it necessary for app function?
   **FAIL if any personal data from child is collected without parental consent.**

3. **Third-party SDK data audit:**
   ```bash
   grep -r "implementation\|api(" app/build.gradle.kts | grep -v "test\|debug"
   ```
   For EACH SDK:
   - Does it collect data? What data?
   - Is it self-certified for COPPA compliance?
   - Is it on Google's "families self-certified ads SDKs" list?

4. **Privacy policy verification:**
   a. Locate privacy policy in app: Settings → Privacy Policy
   b. Verify it opens and is readable
   c. Verify it covers: what data is collected, why, how it's stored, how to delete it
   d. Verify parent contact info is provided
   e. Verify it mentions COPPA compliance
   f. Compare privacy policy text with actual data collection practices → must match

5. **Data deletion verification:**
   a. Navigate to Parent Zone → Settings → Delete Data
   b. Confirm deletion → verify all local data is erased
   c. Verify: progress, coins, badges all reset
   d. Verify: analytics events queued for deletion (if applicable)

**Pass criteria:** Minimal permissions, no child personal data collected, privacy policy accurate, deletion works
**Fail criteria:** Excessive permissions, personal data collected, misleading privacy policy, no deletion option

---

#### Work Item CR-05: Google Play Billing Compliance Audit
**When:** After Phase 5.1 billing integration
**Steps:**
1. **Code review — no external payment:**
   ```bash
   grep -rn "mpesa\|paypal\|visa\|mastercard\|credit.card\|PaymentMethod" app/src/ --include="*.kt"
   ```
   **FAIL if any remnants of the old M-Pesa/PayPal/Visa payment system exist.** Google Play policy requires ALL digital goods to use Play Billing.

2. **Code review — purchase acknowledgment:**
   ```bash
   grep -rn "acknowledgePurchase\|consumePurchase\|AcknowledgePurchaseParams" app/src/ --include="*.kt"
   ```
   Verify EVERY purchase path ends with acknowledgment. Unacknowledged purchases auto-refund after 3 days.

3. **Code review — BillingClient initialization:**
   ```bash
   grep -rn "BillingClient\|enablePendingPurchases" app/src/ --include="*.kt"
   ```
   Verify `enablePendingPurchases()` is called.

4. **Test subscription flow (with test account):**
   a. Navigate to subscription screen (behind parental gate)
   b. Verify all plan options displayed with correct prices
   c. Select monthly plan → verify Play Store purchase dialog appears
   d. Complete test purchase → verify premium features unlock
   e. Verify subscription status persists after app restart
   f. Cancel subscription in Play Store → verify app detects cancellation (may take a few hours)

5. **Test restore purchases:**
   a. Uninstall and reinstall app
   b. Navigate to subscription screen → tap "Restore Purchases"
   c. Verify previously purchased subscription is detected and premium unlocked

6. **Test coin purchase:**
   a. Navigate to rewards store → coin bundles
   b. Purchase coin bundle → verify coins added to balance
   c. Verify purchase is consumed (one-time, can buy again)

7. **Free tier viability check:**
   a. Use app WITHOUT purchasing anything
   b. Verify 2 free modules are fully playable (learn + quiz)
   c. Verify free tier is not just a "nag screen" — genuine educational value
   d. Verify locked modules show clear, non-manipulative "premium" label

**Pass criteria:** No external payments, all purchases acknowledged, restore works, free tier genuine
**Fail criteria:** Old payment code exists, unacknowledged purchases, restore broken, free tier unusable

---

#### Work Item CR-06: Families Policy Content Audit
**When:** Before Play Store submission
**Steps:**
1. **Content appropriateness:**
   a. Review ALL text in app (buttons, labels, dialogs, error messages)
   b. Verify no inappropriate language, violence references, or adult themes
   c. Review ALL images/illustrations → verify family-friendly
   d. Review ALL audio → verify appropriate for toddlers (no scary sounds)
   e. Review ALL Lottie animations → verify age-appropriate

2. **Metadata consistency:**
   a. Verify target audience declaration in Play Console matches app content
   b. Verify app description mentions it's for children/toddlers
   c. Verify screenshots show actual app screens (not misleading)
   d. Verify content rating (IARC) is accurate

3. **Interactive elements audit:**
   a. Verify no social features accessible to children (chat, messaging, user-generated content)
   b. Verify no dating/matchmaking features (obviously n/a, but checklist completeness)
   c. Verify no real-money gambling elements
   d. Verify no violent or competitive elements inappropriate for toddlers

4. **Store listing compliance:**
   a. Privacy policy URL in listing → verify link works and is accurate
   b. App category → verify "Education" + "Designed for Families"
   c. Contact email → verify present and responsive
   d. Family-appropriate screenshots and descriptions

**Pass criteria:** All content appropriate, metadata accurate, no policy violations
**Fail criteria:** Any inappropriate content, misleading metadata, missing privacy policy

---

#### Work Item CR-07: End-to-End Compliance Smoke Test
**When:** 48 hours before Play Store submission
**Steps:**
1. Fresh install on clean emulator (factory reset)
2. **First launch:**
   a. Verify age-gate appears first
   b. Verify onboarding explains app is for children
   c. Verify parental consent prompt appears before any data collection
3. **Free user journey (30 min):**
   a. Play through 2 free modules completely
   b. Verify ads appear only in approved locations
   c. Verify locked modules are clearly marked, not tricky
   d. Verify no nag screens or manipulative "buy now" popups
4. **Premium user journey:**
   a. Subscribe (test account)
   b. Verify all ads disappear
   c. Verify all modules unlock
   d. Play through premium content
5. **Parent journey:**
   a. Access parent zone → verify gate
   b. View progress dashboard
   c. Change settings
   d. Manage subscription
6. **Uninstall and verify:**
   a. Uninstall app
   b. Verify no orphaned data (check for lingering files)
7. **Generate final compliance report** with:
   - All CR work items status (pass/fail)
   - Screenshots of every gate, ad placement, purchase flow
   - List of all permissions and their justification
   - Data collection inventory
   - Privacy policy accuracy assessment

**Pass criteria:** Complete journeys without policy violations, comprehensive report generated
**Fail criteria:** Any violation detected at any point in the journey

---

#### Compliance Agent — Trigger Schedule

| Trigger Event | Work Items to Run | Phase |
|---------------|-------------------|-------|
| AdMob SDK integrated | CR-01, CR-02 | P5 |
| Play Billing integrated | CR-05 | P5 |
| Privacy policy written | CR-04 | P5 |
| Parental gates implemented | CR-03 | P4/P5 |
| All content finalized | CR-06 | P7 |
| Pre-submission (48h before) | CR-01 through CR-07 (full audit) | P7 |
| After any ad/billing code change | CR-01, CR-02, CR-05 | Any |

---

## 4. Implementation Phases

### Phase 1: Project Infrastructure Modernization

#### 1.1 — Migrate Build System (Ant → Gradle/AGP)
- Create `build.gradle.kts` (project + app module) with AGP 8.x+
- Create `settings.gradle.kts`
- Add `gradle.properties` with AndroidX migration flags
- Configure `local.properties`
- Remove legacy `.classpath`, `.project`, `.settings`, `project.properties`, `bin/`, `gen/`
- Restructure to standard Gradle directory layout (`app/src/main/java/`, `app/src/main/res/`, `app/src/main/assets/`)

#### 1.2 — Update SDK Targets & Dependencies
- **compileSdk**: 35 (Android 15)
- **targetSdk**: 35
- **minSdk**: 24 (Android 7.0 — covers 99%+ of devices)
- Replace `android-support-v4.jar` with AndroidX equivalents
- Replace AppCompat v7 library project reference with `androidx.appcompat:appcompat`
- Add core Jetpack dependencies: `lifecycle`, `navigation`, `viewmodel`, `datastore`

#### 1.3 — Migrate Java → Kotlin
- Configure Kotlin plugin (2.0+)
- Convert all 30+ Java source files to Kotlin incrementally
- Adopt Kotlin idioms: coroutines, sealed classes, extension functions, data classes

#### 1.4 — Replace AndEngine with Jetpack Compose
- Use Jetpack Compose for all UI screens (menus, settings, onboarding)
- Use Compose Canvas + Animations for interactive learning scenes
- Use `AnimatedVisibility`, `Lottie`, or `AnimatedImageVector` for sprite-like animations
- Benefits: Native Android, no 3rd-party engine dependency, modern, maintainable

#### 1.5 — Update AndroidManifest
- Update package name to: `com.game254studios.kakaandchui`
- Set proper `android:exported` attributes on activities
- Add COPPA/Family Policy metadata
- Update theme references to Material 3
- Remove legacy large-heap requirement (optimize memory instead)

---

### Phase 2: UI/UX Modernization

#### 2.1 — Design System & Theming
- Implement Material Design 3 theming with a bright, child-friendly color palette
- Create custom Compose theme: `KakaTheme` with warm orange/yellow primaries, playful green/blue accents
- Large, rounded shapes (24dp+ corner radius)
- Custom typography using existing fun fonts (`Cabold Comic`, `Cartoon_Regular`)
- Support dynamic sizing: phones, tablets, landscape/portrait

#### 2.2 — Responsive Layout
- Replace fixed 800×480 camera with responsive Compose layouts
- Use `WindowSizeClass` to adapt for phones, tablets, foldables
- Landscape-first design (primary) with portrait support (secondary)
- Adaptive grid layouts for quiz answers

#### 2.3 — Modernize Navigation
- Implement Compose Navigation with type-safe routes
- Navigation graph:
  ```
  SplashScreen → OnboardingFlow (first launch) → HomeScreen
  HomeScreen → ModuleScreen(category) → LearnScreen(category, item) → QuizScreen(category)
  HomeScreen → ParentZone (behind parental gate) → Settings / Progress / Subscription
  ```
- Smooth shared-element transitions between screens

#### 2.4 — Revamp Home Screen
- Animated Kaka & Chui mascots (Lottie animations)
- Module cards with progress indicators (e.g., "3/5 vowels mastered")
- Daily challenge banner
- Streak counter display
- Fun ambient background animations (floating stars, bubbles)

#### 2.5 — Revamp Learn Screens
- Full-screen immersive flash card experience
- Swipe-based navigation (swipe left/right for prev/next) plus arrow buttons
- Large, vibrant illustrations with subtle parallax effects
- Auto-play pronunciation on card appear
- "Tap to hear again" interaction
- Progress dots at bottom showing position in set
- Smooth flip/slide card transitions

#### 2.6 — Revamp Quiz Screens
- Fun, game-like quiz UI with bouncy animations
- Answer buttons with press-down spring animations
- Confetti particle effect on correct answer (replace static green tick)
- Shake + gentle red glow on wrong answer (replace static red X)
- Animated Kaka character reacting in real-time
- Progress bar showing quiz completion
- End-of-quiz summary screen with score, stars earned, and "play again" option

#### 2.7 — Accessibility & Inclusivity
- Large touch targets (min 48dp, recommend 64dp+ for toddlers)
- High-contrast mode support
- TalkBack compatibility
- Haptic feedback on interactions
- Support for one-handed play (toddler grip)

---

### Phase 3: New Content & Learning Modules

#### 3.1 — Expand Existing Modules
- **Vokali (Vowels)**: Add "Maneno" (Words) mode — teach common Swahili words starting with each vowel (assets already exist: Baba, Dawa, Embe, Gari, Jiko, etc.)
- **Tarakimu (Numbers)**: Extend to 1-20, add counting exercises with visual objects
- **Maumbo (Shapes)**: Add shape tracing (draw-on-screen) interaction
- **Rangi (Colors)**: Add color mixing mini-game

#### 3.2 — New Learning Modules
- **Wanyama (Animals)** — Learn animal names in Swahili with sounds & images
- **Matunda (Fruits)** — Learn fruit names with colorful illustrations
- **Mwili (Body Parts)** — Interactive body diagram, tap to hear names
- **Salamu (Greetings)** — Common Swahili greetings and phrases
- **Alfabeti (Alphabet)** — Full Swahili alphabet with phonics

#### 3.3 — Interactive Scenes / Mini-Games
- **Puzzle Mode**: Drag-and-drop jigsaw puzzles of animals/shapes
- **Matching Game**: Memory card flip — match Swahili word to image
- **Coloring Book**: Simple coloring scenes with Kaka & Chui characters
- **Story Mode**: Short interactive stories narrated in Swahili with tap-along elements
- **Tracing / Drawing**: Trace letters, numbers, shapes on screen (finger drawing)

#### 3.4 — Audio & Pronunciation
- Record high-quality native Swahili speaker audio for all new content
- Add slow-speed pronunciation option
- Add "repeat after me" mode with microphone input (speech recognition) — stretch goal

---

### Phase 4: Gamification System

#### 4.1 — XP & Level System
- Earn XP for completing lessons, quizzes, daily challenges
- Level progression: Chick → Duckling → Parrot → Eagle (with Kaka character evolving)
- XP bar visible on home screen
- Level-up celebration animations

#### 4.2 — Stars & Scoring
- 1-3 stars per quiz based on accuracy:
  - ⭐ = completed (any score)
  - ⭐⭐ = 70%+ accuracy
  - ⭐⭐⭐ = 100% accuracy, no mistakes
- Star count visible on module cards
- Total stars unlock rewards

#### 4.3 — Badges & Achievements
- Achievement badges for milestones:
  - "First Steps" — Complete first lesson
  - "Vowel Master" — 3 stars on all vowel quizzes
  - "Number Ninja" — 3 stars on all number quizzes
  - "Shape Shifter" — 3 stars on all shape quizzes
  - "Rainbow Warrior" — 3 stars on all color quizzes
  - "Week Warrior" — 7-day streak
  - "Consistent Learner" — 30-day streak
  - "Kaka's Best Friend" — Complete all modules
- Trophy case screen to display earned badges

#### 4.4 — Streaks & Daily Challenges
- Daily login streak tracker
- Daily challenge: 1 random quiz from any module
- Streak freeze: Earn/purchase "streak shields"
- Weekly challenge with special badge reward

#### 4.5 — Coins & Rewards Store
- Earn coins from quizzes, streaks, achievements
- Spend coins on: avatar customization, theme colors, sticker collections, celebration animations
- Coins can also be purchased (in-app purchase) or earned by watching rewarded ads

#### 4.6 — Progress Tracking & Parent Dashboard
- Per-module progress: lessons completed, quiz scores, time spent
- Overall mastery percentage
- Learning streak history chart
- **Parent Zone** (behind parental gate — simple math problem):
  - View child's progress reports
  - Set daily time limits
  - Control ad preferences
  - Manage subscription

---

### Phase 5: Monetization Integration

#### 5.1 — Google Play Billing (Library v7+)
- **Freemium model**:
  - **Free tier**: 2 modules (Vokali + Tarakimu) fully accessible
  - **Premium tier**: All modules + mini-games + no ads
- **Subscription plans**:
  - Monthly: ~KES 200/month ($1.49 USD)
  - Annual: ~KES 1,500/year ($11.99 USD) — saves 37%
  - Family plan: Up to 5 kids — KES 2,500/year ($19.99 USD)
- **One-time purchases**: Individual module unlock packs, coin bundles
- **Implementation**:
  - `BillingClient` initialization with `enablePendingPurchases()`
  - Server-side purchase verification (Firebase Functions)
  - Subscription status caching with DataStore
  - Parental gate before any purchase flow
  - Restore purchases functionality

#### 5.2 — AdMob Integration (COPPA-Compliant)
- **Ad types**:
  - **Rewarded ads** (parent-gated): Watch ad → earn coins (optional, never required)
  - **Banner ads**: Small banner on home screen only (free tier)
  - **Interstitial ads**: Between quiz completions (max 1 per 5 minutes, free tier only)
- **COPPA compliance**:
  - Tag all ad requests as child-directed (`tagForChildDirectedTreatment(true)`)
  - Disable personalized/behavioral targeting
  - Use only contextual, family-safe ad content
  - Parental gate for rewarded ad viewing
- **No ads for premium subscribers**

#### 5.3 — Privacy & Compliance
- COPPA-compliant privacy policy (in-app + Play Store listing)
- Google Play Families Policy compliance checklist
- Parental consent management
- Minimal data collection policy
- Data retention and deletion procedures
- Age-gate on app first launch
- Privacy policy accessible from settings and parent zone

---

### Phase 6: Data & Backend

#### 6.1 — Local Data Storage
- Replace SharedPreferences with Jetpack DataStore (Proto)
- Room database for: user progress, achievements, streaks, coins, settings
- **Multi-profile support**: Up to 4 child profiles per device with separate progress

#### 6.2 — Firebase Integration
- **Firebase Analytics**: Track learning engagement, module popularity, quiz completion rates
- **Firebase Crashlytics**: Crash reporting
- **Firebase Remote Config**: A/B test content, toggle features remotely
- **Firebase Cloud Functions**: Server-side billing verification
- **Firebase Auth** (anonymous): Tie progress to device, allow backup/restore

---

### Phase 7: Polish & Launch Preparation

#### 7.1 — Asset Refresh
- Commission updated, higher-resolution illustrations
- Create Lottie animations for Kaka & Chui characters
- Adaptive icon (foreground + background layers)
- Android 12+ Splash Screen API

#### 7.2 — Audio Improvements
- Re-record audio in higher quality
- Add ambient sounds and music variety (currently only 1 background track)
- Consistent audio levels across all clips
- Add sound effects for UI interactions

#### 7.3 — Performance Optimization
- Lazy loading of assets per module
- WebP format conversion
- R8 code shrinking and obfuscation
- Baseline profiles for faster startup
- Memory profiling to eliminate leaks

#### 7.4 — Testing
- Unit tests: JUnit 5 + MockK for ViewModels and game logic
- UI tests: Compose Testing framework
- Accessibility testing: TalkBack, Switch Access
- Device compatibility: phones, tablets, various API levels
- Billing flow tests: Play Billing Lab

#### 7.5 — Play Store Preparation
- App Store listing with screenshots, feature graphic, promo video
- Designed for Families program enrollment
- Content rating questionnaire (IARC)
- Privacy policy URL
- Teacher-approved badge application (if eligible)
- ASO: keywords, description in English + Swahili

---

### Phase 8: Free & Open-Source Asset Enrichment

#### 8.1 — Illustration & Graphic Assets (CC0 / Public Domain)

| Source | What to Get | License | URL |
|--------|-------------|---------|-----|
| **Kenney.nl** | Animal packs, shape packs, UI elements, game icons, number tiles | CC0 | https://kenney.nl/assets |
| **OpenGameArt.org** | Sprite sheets, cartoon animals, fruits, educational icons, backgrounds | CC0/Public Domain | https://opengameart.org |
| **Pixabay** | Cartoon animal vectors, fruit illustrations, educational scenes | Pixabay License (free) | https://pixabay.com/illustrations/ |
| **Itch.io** | Character sprites, animal packs, UI button sets, badge icons | CC0 (filter) | https://itch.io/game-assets/free |
| **unDraw** | SVG illustrations for onboarding, settings, parent dashboard | MIT | https://undraw.co |

**Recommended per module:**
- **Wanyama (Animals):** Kenney "Animal Pack" — lion, elephant, giraffe, zebra, hippo, monkey, bird, fish
- **Matunda (Fruits):** Pixabay cartoon fruit vectors — mango, banana, pineapple, coconut, papaya, orange
- **Mwili (Body Parts):** OpenGameArt character anatomy sprites
- **Alfabeti (Alphabet):** Kenney "Letter Tiles" — colorful A-Z letter blocks
- **UI Elements:** Kenney "UI Pack" — buttons, panels, progress bars, star ratings, coin icons
- **Backgrounds:** OpenGameArt seamless cartoon backgrounds — sky, jungle, savanna, ocean themes

#### 8.2 — Sound Effects (CC0 / Royalty-Free)

| Source | What to Get | License | URL |
|--------|-------------|---------|-----|
| **Freesound.org** | Correct/wrong dings, clicks, whoosh, level-up fanfares | CC0 (filter) | https://freesound.org |
| **Pixabay SFX** | Applause, celebration, confetti, chimes, coin collect | Pixabay License | https://pixabay.com/sound-effects/ |
| **Mixkit** | Children's laughter, cheerful reactions, achievement sounds | Free (commercial OK) | https://mixkit.co/free-sound-effects/children/ |
| **SoundDino** | Quiz beeps, timer ticking, score counting, star earned | Free MP3 | https://sounddino.com |
| **Uppbeat** | Group cheers, kids clapping, celebration cues | Free (no attribution) | https://uppbeat.io/sfx/category/children |

**SFX Library to build:**
- `sfx_correct.mp3` — bright chime for correct answers
- `sfx_wrong.mp3` — gentle buzz for wrong answers (not scary!)
- `sfx_button_tap.mp3` — soft pop for button presses
- `sfx_swipe.mp3` — whoosh for card transitions
- `sfx_coin_collect.mp3` — coin pickup jingle
- `sfx_badge_earned.mp3` — triumphant short fanfare
- `sfx_level_up.mp3` — ascending chime sequence
- `sfx_streak.mp3` — fire/sizzle for streak display
- `sfx_confetti.mp3` — party popper for celebrations
- `sfx_star_1/2/3.mp3` — star reveal sounds (ascending pitch)
- `sfx_timer_tick.mp3` — gentle ticking for timed challenges

#### 8.3 — Background Music (CC0 / Royalty-Free)

| Source | What to Get | License | URL |
|--------|-------------|---------|-----|
| **Pixabay Music** | Playful kids game loops, cheerful educational tracks | CC0 | https://pixabay.com/music/search/kids%20game/ |
| **Chosic** | Children's background music, upbeat learning tracks | CC0/CC-BY | https://chosic.com/free-music/children/ |
| **FiftySounds** | Magical, cheerful, inspiring kids' music loops | Royalty-free | https://fiftysounds.com/royalty-free-music/children.html |
| **EduGamery** | Music made for educational games (puzzles, counting) | Free for games | https://edugamery.com/free-background-music-for-kids-games/ |

**Tracks to source:**
- Main menu theme (cheerful, looping, ~2 min)
- Learning mode background (calm, gentle, piano/xylophone)
- Quiz mode background (upbeat, energetic, builds tension)
- Achievement/results screen (celebratory, triumphant)
- Parent zone background (calm, sophisticated)
- 3-4 module-specific ambient tracks for variety

#### 8.4 — Lottie Animations (Free)

| Source | What to Get | License | URL |
|--------|-------------|---------|-----|
| **LottieFiles** | Confetti, star explosion, trophy, badge unlock, loading, checkmark/X | Simple License (free) | https://lottiefiles.com/free-animations |
| **IconScout** | Party confetti, celebration, badge reactions | Free tier | https://iconscout.com/free-lottie-animations |

**Animations to download:**
- `confetti_burst.json` — correct answer / quiz completion
- `star_reveal.json` — star rating animation (1-3 stars)
- `badge_unlock.json` — badge earned celebration
- `coin_collect.json` — coin flying into wallet
- `streak_fire.json` — fire behind streak counter
- `level_up.json` — level-up glow/burst effect
- `loading_spinner.json` — fun loading animation
- `checkmark_green.json` — animated green checkmark
- `x_red.json` — animated red X
- `happy_character.json` — character happy dance
- `encourage.json` — character thumbs up ("try again!")

---

### Phase 9: Missing Features & Best Practices (Gap Analysis)

These features were identified from competitive analysis and industry best practices for toddler educational apps. They fill critical gaps in the current design.

#### 9.1 — Offline Mode (Full Offline-First Architecture)
- All learning content (images, audio, animations) bundled in the APK or downloaded on first launch
- App must be 100% functional without internet
- Internet only needed for: billing verification, analytics sync, subscription validation
- Cache subscription status locally with periodic re-validation
- Queue analytics events for batch upload when connectivity returns

#### 9.2 — Multi-Profile Support
- Support up to 4 child profiles per device
- Each profile has independent: progress, XP/levels, streaks, coins, badges
- Profile selection screen on app launch (with large, photo-based avatars)
- Parent can manage profiles from Parent Zone
- Useful for siblings, classrooms, or shared family tablets

#### 9.3 — Spaced Repetition System (SRS)
- Track mastery per learning item (each vowel, number, shape, color, word)
- Items the child gets wrong appear more frequently in quizzes
- Items mastered appear less frequently (expanding intervals)
- "Review" mode: automated review sessions of items due for repetition
- Based on SM-2 algorithm (simplified for toddlers)

#### 9.4 — Adaptive Difficulty
- Adjust quiz difficulty based on child's performance:
  - **Easy**: 2-3 choices, slower pace, more hints
  - **Medium**: 4 choices, standard pace (current behavior)
  - **Hard**: 5-6 choices, timed, distractor options
- Auto-adjust: if child gets 3 correct in a row → increase difficulty
- Auto-adjust: if child gets 2 wrong in a row → decrease difficulty
- Parent can override difficulty in settings

#### 9.5 — Onboarding Flow
- First-launch experience:
  1. Language selection (English / Swahili UI)
  2. Age-gate / parent verification
  3. Create child profile (name, avatar selection)
  4. Short interactive tutorial showing how to play
  5. Guided first lesson with extra hints and encouragement
- Skip option for returning users

#### 9.6 — Daily Time Limits & Screen Time
- Parent-configurable daily play time limits (15/30/45/60 min)
- Friendly "time's up!" screen with Kaka waving goodbye
- Countdown timer visible in parent zone (not shown to child)
- Bedtime mode: disable app during set hours

#### 9.7 — Haptic & Multi-Sensory Feedback
- Vibration patterns for: correct answer (short buzz), wrong answer (double buzz), badge earned (pattern)
- Sound + visual + haptic combined for maximum toddler engagement
- Configurable in settings (parent can disable haptics)

#### 9.8 — Localization & Language Support
- App UI available in: English, Swahili
- Future: French, Arabic (other major African languages)
- All strings externalized to `strings.xml` / Compose resources
- RTL layout support for Arabic

#### 9.9 — Content Update System
- Ability to add new learning modules via remote content packs (Firebase Remote Config + Cloud Storage)
- No app update required for new word sets, images, or audio
- Versioned content packs with delta downloads

#### 9.10 — Classroom / Teacher Mode (Stretch Goal)
- Teacher can create a class and add multiple student profiles
- View class-wide progress reports
- Assign specific modules or quizzes as homework
- Print/export progress reports

---

## 5. Technical Stack Summary

| Component | Current | Modernized |
|-----------|---------|------------|
| Language | Java | Kotlin 2.0+ |
| Build | Ant | Gradle 8.x + AGP 8.x (Kotlin DSL) |
| Min SDK | 8 (Froyo) | 24 (Nougat) |
| Target SDK | 19 (KitKat) | 35 (Android 15) |
| UI Framework | AndEngine + XML | Jetpack Compose + Material 3 |
| Architecture | Custom MVS | MVVM (ViewModel + UiState + Compose) |
| Navigation | Manual SceneManager | Compose Navigation |
| Storage | SharedPreferences | DataStore + Room |
| Animations | AndEngine sprites | Lottie + Compose Animation |
| Ads | None | AdMob (COPPA-compliant) |
| Billing | Manual (M-Pesa text) | Google Play Billing Library v7 |
| Analytics | None | Firebase Analytics |
| Crash Reporting | None | Firebase Crashlytics |
| DI | None | Hilt |
| Async | None | Kotlin Coroutines + Flow |
| Testing | None | JUnit 5 + Compose Testing + MockK |
| Profiles | None | Room-backed multi-profile |
| Offline | Partial | Full offline-first |
| Learning | Static | Spaced repetition + adaptive difficulty |

---

## 6. Parallel Execution Strategy

### Agent Orchestration

Implementation will be orchestrated across **parallel agent streams**. Specialized agents run continuously alongside:

```
┌─────────────────────────────────────────────────────────────────┐
│                    CONTINUOUS AGENTS                              │
│                                                                  │
│  🔨 Build Validator    → Validates build + ADB install after    │
│                          every significant change                │
│                                                                  │
│  🧪 QA Testing Agent   → Tests functionality, UX, edge cases   │
│                          after each phase milestone              │
│                                                                  │
│  📋 Compliance Agent   → Reviews ads, billing, COPPA, Families  │
│                          Policy after Phase 5 + before launch    │
└─────────────────────────────────────────────────────────────────┘
```

### Parallel Implementation Streams

```
STREAM A: Infrastructure (Sequential — Foundation)
  p1-gradle → p1-sdk → p1-manifest → p1-kotlin → p1-engine
  [🔨 Build Validator runs after each step]

STREAM B: Data Layer (Starts after p1-kotlin)
  p6-local → p6-firebase
  ├── p9-offline (offline-first architecture)
  └── p9-multi-profile (multi-profile support)

STREAM C: UI/UX (Starts after p1-engine, parallel internally)
  p2-theme ──→ p2-home ──→ p7-assets
  p2-responsive ─┘         p2-a11y
  p2-navigation → p2-learn → p3-expand → p9-srs (spaced repetition)
                  p2-quiz  → p3-new-modules → p9-adaptive
                             p3-minigames
  p9-onboarding (after p2-navigation)
  [🔨 Build Validator + 🧪 QA Agent run after each screen]

STREAM D: Gamification (After p6-local + p2-quiz)
  p4-xp → p4-badges
        → p4-streaks
        → p4-coins → p5-billing
  p4-stars
  p4-parent → p9-time-limits

STREAM E: Monetization & Compliance (After UI + Gamification)
  p5-privacy → p5-admob
  p5-billing → p7-store
  [📋 Compliance Agent runs full review]

STREAM F: Content & Assets (Parallel throughout)
  p3-audio → p7-audio-polish
  p8-illustrations (download & integrate free assets)
  p8-sounds (download & curate SFX library)
  p8-music (download & organize background tracks)
  p8-lottie (download & integrate animations)

STREAM G: Polish (Final)
  p7-perf → p7-testing → p7-store
  p9-localization
  [🔨 Build Validator + 🧪 QA Agent + 📋 Compliance Agent: FINAL PASS]
```

---

## 7. Risks & Considerations

1. **AndEngine migration is the biggest risk** — no direct code migration path; all scene/sprite code must be rewritten in Compose
2. **Asset quality** — current graphics are low-res (800×480 era); free assets from Kenney/OpenGameArt can bridge this
3. **Audio licensing** — verify all existing music/sound files are properly licensed (e.g., "Jeez-Mugeek" sounds like a stock track)
4. **Scope creep** — Phase 3 (new content) + Phase 9 (gap features) can grow unbounded; prioritize quality over quantity
5. **COPPA compliance** is legally critical for a kids' app — get legal review before launch
6. **Play Store Families Policy** has strict requirements that can cause rejection if not followed precisely
7. **Offline-first adds complexity** — subscription validation without internet requires careful caching strategy
8. **Spaced repetition for toddlers** — algorithm must be heavily simplified; toddlers don't use apps consistently enough for traditional SRS
9. **Multi-profile on single device** — data isolation must be thorough; one child shouldn't see another's progress
10. **Free asset style consistency** — assets from different sources may not match visually; may need custom adjustments
