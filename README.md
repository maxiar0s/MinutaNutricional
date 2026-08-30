# Minuta Nutricional

Minuta Nutricional is a beginner-friendly Android application that presents a local weekly nutrition menu. It uses clear text, large touch targets, and simple navigation for people with low computer skills.

## Covered requirements

- Registration, login, and local password-recovery verification flows with clear status messages.
- Local in-memory user registration, email and password validation, duplicate prevention, and a five-user limit.
- A weekly summary table that groups recipes by day and displays recipe counts and names.
- Material 3 inputs, buttons, text, exposed dropdown, radio buttons, checklist, adaptive list/grid, and a local help link.
- Local navigation between access, weekly menu, and recipe detail screens, with scrollable layouts and wider-screen padding.
- The app declares the Android `INTERNET` permission, but it does not use a backend, API, or remote connectivity.

## Architecture

The app uses a small local Compose architecture:

- `MainActivity` owns the current screen and one in-memory `UserRepository` instance.
- Access composables receive Kotlin function callbacks and display local form state.
- `UserRepository` stores up to five `RegisteredUser` records while the app process remains open.
- `WeeklyMenu.kt` uses Kotlin `groupBy`, `map`, `joinToString`, and collection sizes to create the weekly summary.
- Menu data remains static in `WeeklyMenu.kt`.

No backend, persistence layer, ViewModel, dependency injection framework, navigation library, or additional dependency is required for this educational scope.

## Run tests

From the project root on Windows:

```powershell
.\gradlew.bat :app:testDebugUnitTest
```

## Git evidence

The project is maintained in Git. Review the implementation history and local changes with:

```powershell
git log --oneline
git status
```
