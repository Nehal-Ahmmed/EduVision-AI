// ModelConfig.kt (new file)
package com.nhbhuiyan.genaiapp.utils

object ModelConfig {
    // List of available models for different use cases
    const val GEMINI_PRO = "gemini-pro"
    const val GEMINI_PRO_VISION = "gemini-pro-vision"
    const val GEMINI_1_5_PRO = "gemini-1.5-pro"

    fun getModelForTask(hasImage: Boolean): String {
        return if (hasImage) {
            GEMINI_PRO_VISION  // Use vision model for image tasks
        } else {
            GEMINI_PRO  // Use standard model for text-only tasks
        }
    }
}