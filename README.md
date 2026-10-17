# Quant Hour — Android starter app

A native Android starter project using Kotlin and Jetpack Compose.

## Included
- Separate Learner and Creator/Teacher entry flows
- Sign-in screen UI with role switching
- Learner dashboard for GRE, SAT and GAT
- Course detail screen with lessons, study plan and an interactive sample quant question
- Creator content form for lesson, quiz or study notes
- Profile screen and role switching
- Modern navy/blue visual design

## Open and run
1. Install Android Studio (current stable).
2. Open this folder as an existing project.
3. Allow Gradle sync to complete (requires internet for dependencies).
4. Run on an Android emulator or a device with USB debugging enabled.

## Important demo limitations
This is a front-end prototype. Sign-in is not real authentication, creator content is not persisted or uploaded, and course content is sample content. Before production, add:
- Firebase Authentication or a secure API for separate learner/creator accounts
- Database (e.g. Firebase Firestore / PostgreSQL API) and role-based authorization
- Video/PDF hosting, course enrollment and creator publishing workflow
- Quiz engine, saved progress, payments/subscriptions if required
- Privacy policy, terms, content moderation and secure backend rules

Package: `com.quanthour.app`

## Build an APK using GitHub Actions (without Android Studio on your PC)
1. Create a GitHub repository and upload the contents of this project.
2. Open the repository's **Actions** tab and select **Build Quant Hour APK**.
3. Select **Run workflow** and run it on your main branch.
4. When the workflow finishes successfully, open the workflow run and download the `QuantHour-debug-apk` artifact.
5. Extract the artifact ZIP to get `app-debug.apk`, then transfer it to your Android phone and install it. You may need to allow installation from that source.

This creates a **debug APK for testing**, not a signed release suitable for Play Store publication. The demo still does not have production authentication or cloud persistence.
