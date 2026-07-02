# Shopping App(Android)
A modern Android application demonstrating **Clean Architecture**, **Jetpack Compose**, and robust networking patterns.

## Features
- **Product Search:** Real-time search functionality with dynamic result handling.
- **Modern UI:** Built entirely with Jetpack Compose, featuring Material 3 theming.
- **Image Loading:** Efficient, cached image rendering using Coil.

## Architecture
The project follows **Clean Architecture** principles to ensure the code is testable, scalable, and maintainable:

*   **Presentation Layer:** MVVM pattern using Jetpack Compose for reactive UI.
*   **Domain Layer:** Pure Kotlin business logic, containing Models, Repository interfaces.
*   **Data Layer:** Implementation of repositories, Retrofit API services, and DTO mapping.

## Tech Stack
- **Language:** [Kotlin](https://kotlinlang.org/)
- **UI:** [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material 3)
- **Dependency Injection:** [Hilt](https://dagger.dev/hilt/)
- **Networking:** [Retrofit](https://square.github.io/retrofit/) & [OkHttp](https://square.github.io/okhttp/)
- **Image Loading:** [Coil](https://coil-kt.github.io/coil/)
- **JSON Parsing:** [Gson](https://github.com/google/gson)

## Best Practices Included
- **Theming:** Centralized `Spacing` and `Dimens` objects to ensure UI consistency across all screens.
- **State Management:** Type-safe `UiState` handling using Sealed Interfaces.

## Project Structure

com.mahdi.assignment.shoppingapp
├── core                # Common UI components, Theme, and Utilities
├── di                  # Dependency Injection modules (Hilt)
└── feature_search      # Search & Product feature
    ├── data            # API models, DTOs, and Repository implementations
    ├── domain          # Domain models, and Repository interfaces
    └── presentation    # Composables and ViewModels


## Setup
1. Clone the repository.
2. Open in Android Studio.
3. Sync Gradle and run on an emulator or device (API 24+).

