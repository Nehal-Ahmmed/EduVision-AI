# 🎓 EduVision AI — Your Smart Study Companion

**EduVision AI** is a cutting-edge educational tool built with **Kotlin** and **Jetpack Compose**, designed to revolutionize the way students learn. By leveraging the power of **Generative AI (Gemini 1.5 Flash)**, the app transforms complex study materials into interactive, easy-to-digest content.

<p align="center">
  <img src="https://github.com/user-attachments/assets/41ca1c0f-be31-41b7-83f2-4389caca59b7" width="100%" alt="EduVision AI Banner">
</p>

---

## 📱 Screenshots

<p align="center">
  <img src="https://github.com/user-attachments/assets/7c99ba66-8f3e-496e-abdf-9ac3d3724370" width="23%" alt="Home Screen">
  <img src="https://github.com/user-attachments/assets/0f9362af-3bd1-4992-92f2-a2dfdacf2e74" width="23%" alt="Diagram Upload">
  <img src="https://github.com/user-attachments/assets/f77b26e8-5577-48ea-8274-00080604530b" width="23%" alt="Explain Diagram">
  <img src="https://github.com/user-attachments/assets/3eaa71bc-3531-4a84-8586-37b8e10a49d8" width="23%" alt="AI Response">
</p>

<p align="center">
  <img src="https://github.com/user-attachments/assets/a25f937b-453f-4286-af11-0f4e84653f34" width="23%" alt="Visualize Concept">
  <img src="https://github.com/user-attachments/assets/fb069100-73a4-48bc-b68f-e46620a4fd45" width="23%" alt="Quiz Generator">
  <img src="https://github.com/user-attachments/assets/5358e5b1-aec0-4ab8-90a9-d896a62a5dab" width="23%" alt="AI Quiz Response">
  <img src="https://github.com/user-attachments/assets/7e32b3a4-a0be-42d1-a49a-4c04a389c068" width="23%" alt="Study History">
</p>

---

## ✨ Key Features

* **📸 Explain Diagram:** Upload images of complex scientific diagrams, math problems, or historical maps, and get detailed AI-powered explanations instantly.
* **🧠 Visualize Concept:** Turn abstract text into visual learning descriptions. Perfect for understanding processes like Photosynthesis or the Pythagorean theorem.
* **📝 AI Quiz Generator:** Instantly generate custom quizzes on any topic to test your knowledge and prepare for exams.
* **📚 Subject Categories:** Organized learning paths for Mathematics, Science, History, Geography, and more.
* **🕒 Study History:** Keep track of your learning sessions and review past AI responses anytime, stored securely in a local database.
* **⚡ Gemini Flash Integration:** Optimized for speed and intelligence using the latest Gemini 1.5 Flash model for real-time AI assistance.

---

## 🧩 Technical Stack

* **Language:** Kotlin
* **UI Framework:** Jetpack Compose (Material 3)
* **Architecture:** MVVM (Model-View-ViewModel)
* **AI Engine:** Google Gemini 1.5 Flash (Generative AI)
* **Local Database:** Room DB (for caching Study History)
* **Navigation:** Jetpack Compose Navigation
* **UI Enhancements:** Gradient UI, Shimmer Effects, and Modern Component Styling

---

## 🧠 Core Architecture
The app follows the **MVVM architecture** pattern to maintain a clean separation of concerns. It interacts with the **Gemini AI API** through a dedicated repository layer. Data persistence for study history is managed via **Room Database**, ensuring a fast and reliable offline-first experience for students.

---

## 🚀 How to Run
1. Clone this repository.
2. Obtain your API Key from [Google AI Studio](https://aistudio.google.com/).
3. Insert your key into the `local.properties` file: `API_KEY=your_key_here`.
4. Sync the project with Gradle files and run it on an Android device or emulator.
