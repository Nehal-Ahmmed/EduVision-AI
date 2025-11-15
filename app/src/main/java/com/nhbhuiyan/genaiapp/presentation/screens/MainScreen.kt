package com.nhbhuiyan.genaiapp.presentation.screens

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nhbhuiyan.genaiapp.presentation.viewmodels.MainViewModel
import com.nhbhuiyan.genaiapp.domain.model.EduSubject
import com.nhbhuiyan.genaiapp.domain.model.UiState
import kotlinx.coroutines.delay

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    navController: NavController
) {
    var selectedTab by remember { mutableStateOf(0) }
    val subjects = remember { EduSubject.entries.toTypedArray() }
    var selectedSubject by remember { mutableStateOf(EduSubject.GENERAL) }

    // Animated background gradient
    val infiniteTransition = rememberInfiniteTransition()
    val animatedColor by infiniteTransition.animateColor(
        initialValue = Color(0xFF667EEA),
        targetValue = Color(0xFF764BA2),
        animationSpec = infiniteRepeatable(
            animation = tween(3000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        animatedColor,
                        Color(0xFFF093FB),
                        Color(0xFF1A1A2E)
                    )
                )
            )
    ) {

        // Floating particles background
        FloatingParticles()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "🎓 EduVision AI",
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Text(
                        text = "Your Smart Study Companion",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }

                // Profile icon
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White.copy(alpha = 0.2f))
                        .border(1.dp, Color.White.copy(alpha = 0.3f), RoundedCornerShape(12.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile",
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Subject Selection Cards
            Text(
                text = "📚 Choose Your Subject",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(subjects.size) { index ->
                    val subject = subjects[index]
                    SubjectCard(
                        subject = subject,
                        isSelected = selectedSubject == subject,
                        onClick = { selectedSubject = subject }
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    FeatureCard(
                        icon = Icons.Default.PhotoCamera,
                        title = "Explain Diagram",
                        subtitle = "Upload & understand complex diagrams",
                        gradientColors = listOf(Color(0xFF667EEA), Color(0xFF764BA2)),
                        onClick = {
                            navController.navigate("diagram_upload")
                        }
                    )
                }

                item {
                    FeatureCard(
                        icon = Icons.Default.Quiz,
                        title = "Generate Quiz",
                        subtitle = "Test your knowledge with AI quizzes",
                        gradientColors = listOf(Color(0xFFF093FB), Color(0xFFF5576C)),
                        onClick = {
                            navController.navigate("quiz_generator")
                        }
                    )
                }

                item {
                    FeatureCard(
                        icon = Icons.Default.Visibility,
                        title = "Visualize Concept",
                        subtitle = "Create visual learning aids",
                        gradientColors = listOf(Color(0xFF4FACFE), Color(0xFF00F2FE)),
                        onClick = {
                            navController.navigate("visualize_concept")
                        }
                    )
                }

                item {
                    FeatureCard(
                        icon = Icons.Default.History,
                        title = "Study History",
                        subtitle = "Review your learning journey",
                        gradientColors = listOf(Color(0xFF43E97B), Color(0xFF38F9D7)),
                        onClick = {
                            navController.navigate("study_history")
                        }
                    )
                }
            }

            // Add result handling
            LaunchedEffect(viewModel.uiState.value) {
                when {
                    viewModel.uiState.value.isError -> {
                        // Jodi error handle korte chao
                    }
                    viewModel.uiState.value.output.isNotBlank() -> {
                        navController.navigate("result_screen")
                    }
                }
            }


            // Bottom Navigation
            BottomNavigationBar(selectedTab) { selectedTab = it }
        }
    }
}

@Composable
fun SubjectCard(
    subject: EduSubject,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val cardColor = if (isSelected) {
        Brush.horizontalGradient(listOf(Color(0xFF667EEA), Color(0xFF764BA2)))
    } else {
        Brush.horizontalGradient(listOf(Color(0x26FFFFFF), Color(0x0DFFFFFF)))
    }

    Card(
        modifier = Modifier
            .width(120.dp)
            .height(80.dp)
            .shadow(
                elevation = if (isSelected) 16.dp else 8.dp,
                shape = RoundedCornerShape(16.dp)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(cardColor)
                .border(
                    width = if (isSelected) 2.dp else 1.dp,
                    color = if (isSelected) Color.White else Color.White.copy(alpha = 0.3f),
                    shape = RoundedCornerShape(16.dp)
                )
                .clickable { onClick() },
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = when(subject) {
                        EduSubject.MATH -> "🧮"
                        EduSubject.SCIENCE -> "🔬"
                        EduSubject.HISTORY -> "📜"
                        EduSubject.LITERATURE -> "📖"
                        EduSubject.GEOGRAPHY -> "🌍"
                        EduSubject.GENERAL -> "🎓"
                    },
                    fontSize = 20.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = subject.displayName,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun FeatureCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    subtitle: String,
    gradientColors: List<Color>,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .shadow(16.dp, shape = RoundedCornerShape(20.dp)),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(colors = gradientColors),
                    shape = RoundedCornerShape(20.dp)
                )
                .clickable { onClick() }
                .padding(16.dp)
        ) {
            Column {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = Color.White,
                    modifier = Modifier.size(32.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = subtitle,
                    fontSize = 12.sp,
                    color = Color.White.copy(alpha = 0.8f),
                    maxLines = 2
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(selectedTab: Int, onTabSelected: (Int) -> Unit) {
    val tabs = listOf("Home", "Learn", "Quiz", "Profile")
    val icons = listOf(
        Icons.Default.Home,
        Icons.Default.School,
        Icons.Default.Quiz,
        Icons.Default.Person
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White.copy(alpha = 0.1f)
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            tabs.forEachIndexed { index, tab ->
                val isSelected = selectedTab == index
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(16.dp))
                        .background(
                            if (isSelected) Color.White.copy(alpha = 0.2f) else Color.Transparent
                        )
                        .clickable { onTabSelected(index) }
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = icons[index],
                            contentDescription = tab,
                            tint = if (isSelected) Color.White else Color.White.copy(alpha = 0.7f)
                        )
                        if (isSelected) {
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = tab,
                                color = Color.White,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
    }
}

// Floating particles for background animation
@Composable
fun FloatingParticles() {
    Box(modifier = Modifier.fillMaxSize()) {
        repeat(15) {
            FloatingParticle()
        }
    }
}

@Composable
fun FloatingParticle() {
    var offset by remember { mutableStateOf(0.dp) }

    LaunchedEffect(Unit) {
        while (true) {
            offset = (0..1000).random().dp
            delay((1000..3000).random().toLong())
        }
    }

    Box(
        modifier = Modifier
            .offset(x = (0..400).random().dp, y = offset)
            .size(4.dp)
            .background(
                color = Color.White.copy(alpha = 0.3f),
                shape = RoundedCornerShape(2.dp)
            )
    )
}