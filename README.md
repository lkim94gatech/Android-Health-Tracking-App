# Health Tracking App: Android Studio Project

## Project Overview
This health-tracking app was developed for Android devices as part of a team project. The app helps users monitor their health by tracking daily caloric intake, storing personal health data, and managing ingredients and recipes. It was implemented in four sprints, each focusing on distinct functionalities and improvements. The project utilized Android Studio for development and Firebase Realtime Database for backend support.  
This project was collaboratively developed as a team using GitHub for version control, with regular pull requests, merges, and code reviews to ensure seamless integration of contributions.

---

## Team Members
- **Leandro Alan Kim**
- Colin Shaw
- David Folger
- Gabe St. George
- Santiago Tovar
- Aidan Givens

---

## Features

### Sprint 1: Foundations
- **User Authentication**: Secure login and account creation using Firebase.
- **Navigation System**: Splash page, start screen, and navigation bar for seamless user flow.
- **Placeholder Screens**: Developed initial screens for input meal, recipe, ingredient, and shopping list features.
- **Firebase Integration**: Connected Android Studio with Firebase for backend functionality.

### Sprint 2: Personalization
- **Personal Information Page**: Users input height, weight, and gender, stored in the Firebase Realtime Database.
- **Caloric Goal Calculation**: Daily caloric goals are dynamically calculated based on user data.
- **Meal Input**: Users input meal details (name, calories, time), which are stored in the database.
- **Data Visualization**: Display daily caloric intake against historical data and goals.

### Sprint 3: Ingredients and Recipes
- **Ingredient Management**: Users can add, update, and view pantry ingredients.
- **Recipe Management**: Add and organize recipes in a global cookbook.
- **Ingredient Match**: Check if pantry items match a recipe’s requirements.

### Sprint 4: Design Patterns and Advanced Features
- **Observer Pattern**: Implemented to keep the UI updated with data changes in real time.
- **Factory Pattern**: Used for creating meal objects dynamically based on user input.
- **Advanced UI Enhancements**: Improved animations and user feedback mechanisms.

---

## Technical Details
- **Platform**: Android Studio for app development.
- **Backend**: Firebase Realtime Database for user and meal data storage.
- **Programming Language**: Java (with XML for UI design).
- **Version Control**: Collaboration managed through GitHub with pull requests, merges, and reviews.

---

## How to Run
1. Clone the repository to your local machine.
2. Open the project in Android Studio.
3. Sync the project with Gradle to resolve dependencies.
4. Configure Firebase by adding your own `google-services.json` file.
5. Run the app on an Android device or emulator.
