package com.nhbhuiyan.genaiapp.domain.model
data class UiState(
    val output : String = "",
    val isLoading : Boolean=false,
    val isError: Boolean=false,
    val error: String=""
){
    companion object{
        val Initial = UiState()
        val Loading = UiState(isLoading = true)

        fun Error(message: String) = UiState(isError = true, error = message)
        fun Success(output: String) = UiState(output=output)
    }
}
