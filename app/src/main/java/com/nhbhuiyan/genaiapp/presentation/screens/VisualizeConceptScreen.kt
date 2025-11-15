package com.nhbhuiyan.genaiapp.presentation.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nhbhuiyan.genaiapp.domain.model.EduSubject
import com.nhbhuiyan.genaiapp.presentation.viewmodels.MainViewModel

@Composable
fun VisualizeConceptScreen(
    viewModel: MainViewModel,
    navController: NavController
) {
    var concept by remember { mutableStateOf("") }
    var isGenerating by remember { mutableStateOf(false) }

    // Listen for navigation to result screen
    LaunchedEffect(viewModel.uiState.value) {
        val state = viewModel.uiState.value
        if (state.output.isNotBlank() && !state.isLoading && !state.isError) {
            navController.navigate("result_screen")
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF667EEA),
                        Color(0xFF764BA2),
                        Color(0xFF1A1A2E)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier
                        .size(40.dp)
                        .clickable { navController.popBackStack() }
                        .padding(8.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "🎨 Visualize Concept",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Concept Input Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(Color(0xFF4FACFE), Color(0xFF00F2FE))
                            ),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(20.dp)
                ) {
                    Text(
                        text = "💡 Describe Concept to Visualize",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    TextField(
                        value = concept,
                        onValueChange = { concept = it },
                        placeholder = { Text("e.g., Photosynthesis process, Human digestive system, African continent geography...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(120.dp),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.White.copy(alpha = 0.9f),
                            unfocusedContainerColor = Color.White.copy(alpha = 0.8f),
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            cursorColor = Color(0xFF4FACFE),
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Subject-based quick concepts
                    Text(
                        text = "Quick Visualizations for ${viewModel.selectedSubject.value.displayName}:",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.9f),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    val quickConcepts = when (viewModel.selectedSubject.value) {
                        EduSubject.MATH -> listOf(
                            "Algebraic equation solving steps",
                            "Geometric shapes and properties",
                            "Probability tree diagram",
                            "Calculus derivative concept"
                        )
                        EduSubject.SCIENCE -> listOf(
                            "Photosynthesis in African plants",
                            "Human circulatory system",
                            "Solar system planets alignment",
                            "Chemical reaction process"
                        )
                        EduSubject.HISTORY -> listOf(
                            "African independence movements timeline",
                            "Ancient Egyptian pyramid construction",
                            "Trans-Saharan trade routes",
                            "Colonial Africa map changes"
                        )
                        EduSubject.GEOGRAPHY -> listOf(
                            "African climate zones map",
                            "River Nile ecosystem",
                            "Sahara desert expansion",
                            "African tectonic plates"
                        )
                        EduSubject.LITERATURE -> listOf(
                            "Story character relationships",
                            "Plot structure diagram",
                            "Thematic symbolism",
                            "African folktale elements"
                        )
                        EduSubject.GENERAL -> listOf(
                            "Learning process flowchart",
                            "Study technique visualization",
                            "Knowledge connection map",
                            "Problem-solving steps"
                        )
                    }

                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(quickConcepts) { quickConcept ->
                            Text(
                                text = quickConcept,
                                color = Color.White,
                                fontSize = 12.sp,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Color.White.copy(alpha = 0.2f))
                                    .clickable { concept = quickConcept }
                                    .padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Examples Gallery
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(Color(0xFFF093FB), Color(0xFFF5576C))
                            ),
                            shape = RoundedCornerShape(20.dp)
                        )
                        .padding(20.dp)
                ) {
                    Text(
                        text = "📋 Example Visualizations",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    val examples = listOf(
                        "🔬 Science: 'Create a labeled diagram of photosynthesis showing sunlight, water, and carbon dioxide inputs'",
                        "🧮 Math: 'Visualize the Pythagorean theorem with a right-angled triangle and squares on each side'",
                        "🌍 Geography: 'Draw a map of Africa showing different climate zones with colors'",
                        "📜 History: 'Create a timeline of African independence movements from 1950-1970'"
                    )

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        examples.forEach { example ->
                            Text(
                                text = example,
                                fontSize = 12.sp,
                                color = Color.White.copy(alpha = 0.9f),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Generate Visualization Button
            Button(
                onClick = {
                    if (concept.isNotBlank()) {
                        isGenerating = true
                        viewModel.visualizeConcept(concept)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF43E97B)
                ),
                enabled = concept.isNotBlank() && !isGenerating
            ) {
                if (isGenerating) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = "Generating Visualization...",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                } else {
                    Text(
                        text = "🖼️ Generate Visualization",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            // Help text
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "💡 The AI will create a detailed visual description that you can use to draw or understand the concept better",
                fontSize = 12.sp,
                color = Color.White.copy(alpha = 0.7f),
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        // Loading overlay
        if (viewModel.uiState.value.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.7f)),
                contentAlignment = Alignment.Center
            ) {
                Card(
                    modifier = Modifier
                        .width(280.dp)
                        .height(140.dp),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(
                            color = Color(0xFF667EEA),
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Creating Visualization...",
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF667EEA)
                        )
                        Text(
                            text = "AI is designing your concept",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}