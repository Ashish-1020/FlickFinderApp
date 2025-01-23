
---

# FlickFinder App: Movie & TV Show Discovery

## Screenshots

![Untitled design](https://github.com/user-attachments/assets/140d5237-9d22-4b23-9ad9-c68532f2f792)

![Untitled design (1)](https://github.com/user-attachments/assets/263dc0dd-ed5f-4399-91ad-4b29863621ca)

### Download APK and watch the demo video 
You can download the APK for the **FlickFinder App** from [Google Drive](https://drive.google.com/drive/folders/1vqBu_kiWAyYSWLFoElVsrtIxkCOFOn-g).

## Overview
This project is an Android application built using **Jetpack Compose** that allows users to explore a wide range of movies and TV shows using the **Watchmode API**. The app focuses on providing a seamless and engaging user experience with modern design patterns and smooth animations.

## Features

### 1. Home Screen
- Displays a curated collection of movies and TV shows retrieved from the Watchmode API.
- Offers an intuitive **toggle button** or tabs to effortlessly switch between Movies and TV Shows.
- Incorporates a **loading shimmer effect** to maintain a polished and professional look while data is being fetched.

### 2. Details Screen
- Upon selecting a movie or TV show, users are navigated to a detailed screen that showcases:
  - **Title** of the movie/TV show
  - **Synopsis** providing a brief overview
  - **Release Date** to indicate when it premiered
  - **Poster Image** to visually represent the content (using **Coil** for image loading)
  - **Cast Details** listing the main actors and their roles
  - **Streaming Sources** with direct links to platforms where the content is available
- Includes a loading shimmer animation for a consistent experience during content retrieval.

### 3. API Integration
- Utilizes **Retrofit** for seamless and efficient communication with the Watchmode API.
- Simultaneously fetches data for both movies and TV shows using **Single.zip** from **RxKotlin**, optimizing the data retrieval process.

### 4. Error Handling
- Ensures a user-friendly experience by presenting **Snackbars** or **Toasts** to communicate errors, such as connectivity issues or API failures, in a non-intrusive manner.

### 5. Architecture
- Built on the **MVVM (Model-View-ViewModel)** architecture, promoting a clean separation of concerns and simplifying code maintenance.
- Employs **Hilt** for Dependency Injection, streamlining the management of dependencies and boosting scalability.

### 6. Image Loading (Coil)
- Uses **Coil** for loading and displaying images (such as movie posters) efficiently within the app. Coil is used to handle image caching and memory optimization.

## Assumptions
- The app prioritizes displaying recently released movies and TV shows based on the dataset provided by the Watchmode API.
- Content is arranged in a vertically scrollable list, ensuring smooth interaction and navigation.
- Default design elements from Jetpack Compose are utilized, with minor adjustments for customization.

## Challenges Faced

1. **Shimmer Effect Layout:**
   - Implementing shimmer animations within Jetpack Compose posed challenges due to its declarative nature.
   - Resolved by leveraging `Modifier.graphicsLayer()` and fine-tuning animation properties for placeholder components.

2. **Single.zip Implementation:**
   - Coordinating multiple API calls using Single.zip required an in-depth understanding of thread synchronization and RxKotlin operators.
   - Managed to integrate this feature successfully after exploring RxKotlin’s lifecycle management capabilities.

## Why Kotlin with Hilt is Better than Koin

- **Hilt** seamlessly integrates with Android lifecycle components, making it more efficient for managing dependencies in Android apps.
- It offers **better compatibility with Jetpack Compose**, reducing setup overhead compared to Koin.
- **Compile-time validation:** Hilt checks annotations during compilation, preventing runtime errors.
- **Performance:** Hilt’s generated code is more efficient than Koin’s reflection-based implementation, resulting in faster dependency resolution.

## Project Structure
The project adheres to **MVVM Architecture** principles:
- **Model:** Houses data classes representing API responses and a repository layer for managing data operations.
- **ViewModel:** Acts as a mediator between the View and Model, managing application logic and UI state.
- **View (Jetpack Compose):** Dynamically renders the UI and responds to changes in ViewModel state.

## Steps to Run the Project

1. Clone this repository:
   ```bash
   git clone https://github.com/Ashish-1020/FlickFinderApp.git
   ```

2. Open the project in Android Studio.

3. Build and run the project on an emulator or a physical device.

## Technologies Used
- **Kotlin**
- **Jetpack Compose**
- **RxKotlin**
- **Retrofit**
- **Hilt**
- **Coil** (for image loading)
- **MVVM Architecture**

## Future Enhancements
- Enable infinite scrolling through pagination on the home screen.
- Introduce a search bar to quickly locate specific movies or TV shows.
- Implement a toggle feature for light and dark mode themes.
- Add user authentication to provide personalized recommendations.

## Conclusion
This project was a valuable opportunity to delve into modern Android development practices. Overcoming challenges such as creating shimmer effects, implementing Single.zip, and integrating Coil for efficient image loading strengthened my understanding of Jetpack Compose, RxKotlin, Hilt, and Coil. The final application provides a smooth and interactive experience for users exploring movies and TV shows.

---


