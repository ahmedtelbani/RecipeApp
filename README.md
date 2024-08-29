![](https://raw.githubusercontent.com/ahmedtelbani/RecipeApp/dev/media/RecipeApp.jpg)

# Recipe App

## Overview
The Recipe App is an Android application designed to help users discover, save, and manage their favorite recipes. The app is built using the MVVM architecture, with Room for local data storage and Retrofit for network requests.

## Features
- **User Authentication**: Secure login and registration system.
- **Recipe Discovery**: Browse a list of recipes fetched from a remote API.
- **Favorite Recipes**: Save recipes to a favorites list for easy access.
- **Search Functionality**: Search for recipes by name or ingredients.
- **Recipe Details**: View detailed information about each recipe, including ingredients, instructions, and videos.
- **Offline Support**: Access saved recipes even when offline.
- **User-Friendly Interface**: Simple and intuitive UI.

## Tech Stack
- **Kotlin**: Programming language.
- **MVVM Architecture**: Ensures a clean separation of concerns.
- **Room**: Local database for storing user data and favorite recipes.
- **Retrofit**: For making network requests to fetch recipes.
- **Navigation Component**: For managing in-app navigation.
- **Lottie**: For adding animations to the splash screen.
- **GitHub**: Version control system used for collaboration.

## Project Setup
1. Clone the repository:
   ```bash
   git clone https://github.com/ahmedtelbani/RecipeApp.git
2. Open the project in Android Studio.
3. Sync the project with Gradle to install dependencies.
4. Create a local database using Room and configure the Retrofit instance.

## Folder Structure
* data: Contains data classes, DAOs, and database-related files.
* network: Contains Retrofit API interfaces and network-related files.
* ui: Contains fragments, activities, and view models for the app.
* util: Contains utility classes, including SharedPreferences management.
* res: Contains resources such as layouts, drawables, and animations.

## Contributors
* Ahmed El-Telbani
* Abdel-Rhman Ahmed Hamdy
* Mohammed Hisham
* Mustafa Abdel-Raheim
* Islam

## How to Use
1. Login/Register: Users must first log in or register to access the app.
2. Browse Recipes: The home screen displays a list of recipes fetched from the API.
3. Search: Use the search bar to find recipes by name or ingredients.
4. View Recipe Details: Click on a recipe to view detailed information.
5. Favorite Recipes: Save your favorite recipes for quick access later.

## License
This project is licensed under the MIT License

## Contact
For any inquiries or issues, please contact us.
