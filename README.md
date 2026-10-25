# Volume Camera (private Android utility)

This standalone app arms a visible foreground service. When its explicitly
enabled Accessibility service detects 3 or 5 Volume Up presses within five
seconds, it opens a visible capture activity over the lock screen, turns the
screen on when Android delivers the key sequence, and takes rear-camera JPEGs at the
configured interval with flash disabled. The capture screen contains only a
green/red Start/Stop button and the session picture count. Once started, a
camera foreground service continues while you press Home or use another app;
Android's camera privacy indicator and explicit foreground notification remain
visible. Trigger again or press Stop to end.
Images are written to `DCIM/VolumeCamera`, which appears in the camera roll.

An optional Google account is connected in-app through OAuth using the narrow
`drive.file` scope. The app creates or reuses its configured folder and queues
each photo for immediate Drive API upload on Wi-Fi or mobile data. The UI shows
pictures taken, synced, pending, and failed attempts. WorkManager retries
network failures; camera-roll originals are never deleted.

Before Drive authorization can succeed, enable Google Drive API in Google Cloud,
configure the OAuth consent screen with `drive.file`, and create an Android
OAuth client for package `info.deltaindices.volumecamera` using the signing
certificate SHA-1 shown by `./gradlew signingReport`. Add your Google account as
a test user while the consent screen is in Testing mode.

The checked-in debug APK is signed with:

```text
Package: info.deltaindices.volumecamera
SHA-1: 32:82:6F:7B:A6:D9:84:36:05:18:C1:0F:49:0B:27:41:FA:56:59:BA
```

Client-side Google access tokens expire. A `401`/`403` leaves every photo in
the durable pending queue and changes status to **Reconnect required**; press
Connect again to refresh authorization and resume. Continuous unattended token
refresh would require a server-side OAuth refresh-token service and is not
implemented in this private client-only build.

## Build

From the repository root:

```sh
./gradlew :app:assembleDebug
```

Install `app/build/outputs/apk/debug/app-debug.apk`, or download the checked-in
APK from `releases/volume-camera-v1.0.1-debug.apk`.
Open it once, grant Camera and Notifications, choose the trigger settings,
enable **Volume Camera** under Android Accessibility settings, then arm it.

Android 14+ forbids starting camera access invisibly from an ordinary
background app. Therefore the capture activity must remain visible while the
camera runs, and Android's camera privacy indicator is shown. The persistent
armed notification and capture notification are intentional. Some OEM battery
managers may require excluding the app from battery optimization after reboot.
Android/OEM lock screens decide whether volume-key events reach accessibility;
the app cannot bypass a secure keyguard or an OEM that reserves those keys.
In particular, if the OEM does not route volume events while the display is
fully asleep, the user must wake the display first. A normal application cannot
intercept the hardware power button or override that system behavior.
