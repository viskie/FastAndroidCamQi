# Volume Camera (private Android utility)

This standalone app arms a visible foreground service. When its explicitly
enabled Accessibility service detects 3 or 5 Volume Up presses within five
seconds, it opens a visible capture activity and takes rear-camera JPEGs at the
configured interval with flash disabled. Trigger again or press Stop to end.
Images are written to `DCIM/VolumeCamera`, which appears in the camera roll.

An optional folder selected through Android's system folder picker receives a
copy after each successful capture. Choose the Google Drive provider in that
picker to mirror into Drive. Availability and upload timing are controlled by
the installed Drive app; failed cloud copies never delete the camera-roll file.

## Build

From the repository root:

```sh
android/gradlew -p camera-trigger-android :app:assembleDebug
```

Install `camera-trigger-android/app/build/outputs/apk/debug/app-debug.apk`.
Open it once, grant Camera and Notifications, choose the trigger settings,
enable **Volume Camera** under Android Accessibility settings, then arm it.

Android 14+ forbids starting camera access invisibly from an ordinary
background app. Therefore the capture activity must remain visible while the
camera runs, and Android's camera privacy indicator is shown. The persistent
armed notification and capture notification are intentional. Some OEM battery
managers may require excluding the app from battery optimization after reboot.
