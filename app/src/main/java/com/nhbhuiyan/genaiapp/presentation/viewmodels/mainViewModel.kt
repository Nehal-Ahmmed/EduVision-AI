package com.nhbhuiyan.genaiapp.presentation.viewmodels

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.nhbhuiyan.genaiapp.domain.model.EduSubject
import com.nhbhuiyan.genaiapp.domain.model.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    

    private val _uiState = MutableStateFlow<UiState>(UiState.Initial)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _selectedSubject = MutableStateFlow(EduSubject.GENERAL)
    val selectedSubject: StateFlow<EduSubject> = _selectedSubject.asStateFlow()

    private val TAG = "MainViewModel"

    // Education-specific prompt templates
    private val subjectPrompts = mapOf(
        EduSubject.MATH to "Explain this mathematical concept or problem in simple terms suitable for a student. Break it down step by step and provide examples.",
        EduSubject.SCIENCE to "Analyze this scientific concept or diagram. Explain it clearly for educational purposes, focusing on key principles and real-world applications.",
        EduSubject.HISTORY to "Explain this historical concept, event, or artifact. Provide context, significance, and make it engaging for students.",
        EduSubject.LITERATURE to "Analyze this literary concept or text. Explain themes, context, and provide insights that would help students understand better.",
        EduSubject.GEOGRAPHY to "Explain this geographical concept, map, or diagram. Focus on spatial relationships, processes, and real-world significance.",
        EduSubject.GENERAL to "Explain this educational content clearly and simply for students."
    )

    private fun getGenerativeModel(hasImage: Boolean): GenerativeModel {
        val modelName = "gemini-2.5-flash"
        Log.d(TAG, "Using model: $modelName")

        return try {
            GenerativeModel(
                modelName = modelName,
                apiKey = com.nhbhuiyan.genaiapp.BuildConfig.Api_Key
            )
        } catch (e: Exception) {
            Log.e(TAG, "Error creating GenerativeModel: ${e.message}")
            throw e
        }
    }

    fun setSubject(subject: EduSubject) {
        _selectedSubject.value = subject
    }

    // In MainViewModel.kt - make sure explainDiagram accepts bitmap
    fun explainDiagram(
        bitmap: Bitmap? = null,  // Make sure this parameter exists
        userQuestion: String = ""
    ) {
        val subjectPrompt = subjectPrompts[selectedSubject.value] ?: ""
        val fullPrompt = """
        $subjectPrompt
        
        ${if (userQuestion.isNotBlank()) "Specific question: $userQuestion" else "Please explain this educational diagram."}
        
        Provide the explanation in clear, simple language suitable for African students.
        If it's a complex concept, break it down into steps.
        Use examples relevant to African context when possible.
    """.trimIndent()

        sendPrompt(bitmap, fullPrompt)  // This should pass the bitmap to sendPrompt
    }

    fun generateQuiz(concept: String) {
        val prompt = """
            Based on the concept: "$concept"
            
            Generate a short quiz with 3 multiple-choice questions to test understanding.
            Format each question as:
            Q1: [Question text]
            A) Option A
            B) Option B
            C) Option C
            D) Option D
            Correct: [Correct letter]
            
            Make the questions educational and relevant to the concept.
        """.trimIndent()

        sendPrompt(null, prompt)
    }

    // In MainViewModel.kt - Update the visualizeConcept function
    fun visualizeConcept(concept: String) {
        val subjectContext = when (selectedSubject.value) {
            EduSubject.MATH -> "mathematical concepts and diagrams"
            EduSubject.SCIENCE -> "scientific processes and biological systems"
            EduSubject.HISTORY -> "historical timelines and events"
            EduSubject.GEOGRAPHY -> "geographical maps and spatial relationships"
            EduSubject.LITERATURE -> "literary structures and character relationships"
            EduSubject.GENERAL -> "educational concepts and learning frameworks"
        }

        val prompt = """
        Create a detailed, vivid visual description for the educational concept: "$concept"
        
        Context: This is for $subjectContext, specifically for African students.
        
        Please provide a comprehensive visual description that includes:
        
        1. **Overall Layout**: How should the visualization be arranged?
        2. **Colors**: Specific color schemes and their meanings
        3. **Labels**: What should be labeled and how?
        4. **Elements**: Key components and their relationships
        5. **Flow**: Direction or sequence if it's a process
        6. **African Context**: How to make it relevant to African students
        
        Format your response as:
        
        🎨 VISUALIZATION GUIDE: [Concept Name]
        
        📐 LAYOUT: [Describe the arrangement]
        
        🎨 COLORS: [Color scheme with meanings]
        
        🏷️ LABELS: [What to label and how]
        
        🔗 KEY ELEMENTS: [Main components and connections]
        
        ➡️ FLOW/SEQUENCE: [Step-by-step if applicable]
        
        🌍 AFRICAN CONTEXT: [How to make it locally relevant]
        
        💡 STUDY TIPS: [How students can use this visualization]
        
        Make it detailed enough that a student could draw this accurately, but keep the language simple and educational.
    """.trimIndent()

        sendPrompt(null, prompt)
    }

    fun sendPrompt(
        bitmap: Bitmap? = null,
        prompt: String
    ) {
        Log.d(TAG, "sendPrompt called with prompt: '$prompt', hasImage: ${bitmap != null}")

        if (prompt.isBlank() && bitmap == null) {
            _uiState.value = UiState.Error("Please enter a prompt or select an image")
            return
        }

        _uiState.value = UiState.Loading

        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d(TAG, "Starting API call...")

                val model = getGenerativeModel(bitmap != null)
                val content = content {
                    bitmap?.let {
                        Log.d(TAG, "Adding image to content")
                        image(it)
                    }
                    text(prompt)
                }

                Log.d(TAG, "Generating content...")
                val response = model.generateContent(content)
                Log.d(TAG, "Response received: ${response.text?.length ?: 0} characters")

                response.text?.let { outputContent ->
                    Log.d(TAG, "Success: $outputContent")
                    _uiState.value = UiState.Success(outputContent)
                } ?: run {
                    Log.e(TAG, "No output content received")
                    _uiState.value = UiState.Error("No output received from AI")
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error in sendPrompt: ${e.message}", e)
                _uiState.value = UiState.Error(
                    when {
                        e.message?.contains("API key", ignoreCase = true) == true ->
                            "Invalid API key. Please check your configuration."
                        e.message?.contains("quota", ignoreCase = true) == true ->
                            "API quota exceeded. Please try again later."
                        e.message?.contains("model", ignoreCase = true) == true ->
                            "Model not available: ${e.message}"
                        e.message?.contains("network", ignoreCase = true) == true ->
                            "Network error. Please check your internet connection."
                        else -> "Error: ${e.message ?: "Unknown error occurred"}"
                    }
                )
            }
        }
    }

    fun clearError() {
        _uiState.value = UiState.Initial
    }
}