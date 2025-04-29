# Book Summary Project

## Overview

The Book Summary Project is an Android application that leverages the power of the Gemini API to generate concise summaries of books. 
Users can input a book title, optionally provide additional prompts (like "without spoilers"), select a desired language and summary length, and then receive a generated summary. 
The app also allows users to save their generated summaries to a Firestore database for later access.

## Key Features

*   **Book Summarization:** Uses the Gemini API to create summaries of books based on user input.
*   **Customizable Summaries:**
    *   Users can specify the desired length of the summary (Short, Medium, Long).
    *   Users can choose the language of the summary from a variety of options (English, French, Korean, Japanese, Chinese, Spanish, German).
    *   Users can add optional prompts to guide the summarization process (e.g., "focus on the main themes").
*   **Save Summaries:** Users can save generated summaries to a Firestore database, allowing them to keep track of their summarized books.
*   **User Authentication:** The app utilizes Firebase Authentication to associate saved summaries with individual users.
*   **Modern Android Development:** Built using Jetpack Compose for a modern and reactive UI.

## Technologies Used

*   **Kotlin:** The primary programming language for the project.
*   **Jetpack Compose:** For building the user interface.
*   **Gemini API:** For generating book summaries.
*   **Firebase Firestore:** For storing saved summaries.
*   **Firebase Authentication:** For user management and associating summaries with users.
*   **Android SDK:** For building the Android application.
*   **Material 3:** For the UI components.
*   **Navigation Compose:** For navigation between screens.

## Installation and Setup

1.  **Prerequisites:**
    *   Android Studio installed on your machine.
    *   An Android device or emulator.
    *   A Firebase project set up with Firestore and Authentication enabled.
    *   A Gemini API key.
2.  **Clone the Repository:**
    * actual URL of project repository.
3.  **Open in Android Studio:**
    *   Open Android Studio and select "Open an Existing Project."
    *   Navigate to the cloned repository and select the project directory.
4.  **Configure Firebase:**
    *   In your Firebase project, download the `google-services.json` file.
    *   Place the `google-services.json` file in the `app` directory of your Android project.
5.  **Add Gemini API Key:**
    *   You will need to add your Gemini API key to the project.
    *   The code uses the `GeminiApiService` class to interact with the API. You will need to add your API key to this class.
6.  **Build and Run:**
    *   Connect your Android device or start an emulator.
    *   Click the "Run" button in Android Studio to build and run the app.

## Usage

1.  **Enter Book Name:** Type the title of the book you want to summarize in the "Enter Book Name" field.
2.  **Optional Prompt:** If you have specific instructions for the summary (e.g., "without spoilers," "focus on the main characters"), enter them in the "Enter Optional Prompt" field.
3.  **Select Language:** Choose the desired language for the summary from the "Language" dropdown.
4.  **Select Length:** Choose the desired length of the summary (Short, Medium, Long) from the "Length" dropdown.
5.  **Generate Summary:** Click the "Summary" button. The app will use the Gemini API to generate a summary based on your input.
6.  **Save Summary:** If you like the generated summary, click the "Save" button to store it in your Firestore database.

## Contributing

Contributions to the Book Summary Project are welcome! If you'd like to contribute, please follow these steps:

1.  Fork the repository.
2.  Create a new branch for your feature or bug fix.
3.  Make your changes and commit them with clear messages.
4.  Push your changes to your forked repository.
5.  Submit a pull request to the main repository.

Please ensure your code follows the existing style and conventions.

## License

This project is licensed under the GNU Lesser General Public License - see the [LICENSE.txt](LICENSE.txt) file for details.

## Contact

If you have any questions or feedback, feel free to reach out to Kurtis Park at kurtispark91@gmail.com. Thank you.

## Acknowledgements

*   The Gemini API team for providing the summarization capabilities.
*   The Firebase team for the Firestore and Authentication services.
*   The Android and Jetpack Compose teams for the development tools.