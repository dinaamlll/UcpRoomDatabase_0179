package com.example.ucp2.ui.view.home

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ucp2.R

@Composable
fun HomeView(
    onDosenClick: () -> Unit,
    onMataKuliahClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // State for animation control
    var isVisible by remember { mutableStateOf(false) }

    // Trigger animation when the view is loaded
    LaunchedEffect(Unit) {
        isVisible = true
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2))
            .padding(16.dp) // Added padding for better layout spacing
    ) {
        // Main content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp), // Add top padding for better spacing
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            // Logo and welcome message with label inside a box
            TampilanHeader()
            Spacer(modifier = Modifier.height(40.dp)) // Increased space between box and buttons

            AnimatedButton(
                text = "Dosen",
                icon = Icons.Filled.Person,
                onClick = onDosenClick,
                buttonColor = Color(0xFF81D4FA)
            )
            Spacer(modifier = Modifier.height(20.dp))

            AnimatedButton(
                text = "Matakuliah",
                icon = Icons.Filled.Edit,
                onClick = onMataKuliahClick,
                buttonColor = Color(0xFF81D4FA)
            )
        }
        @Composable
        fun TampilanHeader() {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF2196F3))
                    .padding(20.dp)
                    .shadow(4.dp, RoundedCornerShape(12.dp)) // Added shadow for elevation effect
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start
                ) {
                    // Logo Box
                    Box(
                        contentAlignment = Alignment.BottomEnd,
                        modifier = Modifier.weight(1f)
                    ) {
                        Image(
                            painter = painterResource(R.drawable.logo),
                            modifier = Modifier.size(100.dp),
                            contentDescription = null
                        )
                    }
                    // Column for text
                    Column(
                        modifier = Modifier
                            .padding(16.dp)
                            .weight(3f)
                    ) {
                        Text(
                            text = "Teknologi Informasi",
                            color = Color.White,
                            fontSize = 28.sp, // Increased font size for better readability
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.padding(3.dp))
                        Text(
                            text = "Universitas Muhammadiyah Yogyakarta",
                            color = Color.White,
                            fontSize = 18.sp
                        )
                    }
                }
            }
        }
        @Composable
        fun AnimatedButton(
            text: String,
            icon: ImageVector,
            onClick: () -> Unit,
            buttonColor: Color
        ) {
            var isPressed by remember { mutableStateOf(false) }

            Button(
                onClick = onClick,
                colors = ButtonDefaults.buttonColors(
                    containerColor = buttonColor,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(50.dp), // Rounded corners for buttons
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 12.dp)
                    .height(90.dp)
                    .fillMaxWidth(0.9f)
                    .clickable { isPressed = !isPressed }
                    .shadow(
                        8.dp,
                        RoundedCornerShape(50.dp)
                    ) // Added shadow for better visual appeal
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 8.dp)
                            .size(50.dp),
                        tint = Color.White
                    )
                    Text(
                        text = text,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.animateScale(isPressed) // Button scale effect on press
                    )
                }
            }
        }

        @Composable
        fun Modifier.animateScale(isPressed: Boolean): Modifier {
            val scale by animateFloatAsState(
                targetValue = if (isPressed) 1.1f else 1.0f,
                animationSpec = tween(durationMillis = 300)
            )
            return this.then(Modifier.scale(scale))
        }
    }
}


