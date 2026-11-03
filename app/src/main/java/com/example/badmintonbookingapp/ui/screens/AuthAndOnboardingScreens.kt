package com.example.badmintonbookingapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.badmintonbookingapp.ui.components.*
import com.example.badmintonbookingapp.ui.theme.*

@Composable
fun WelcomeScreen(
    onGetStarted: () -> Unit,
    onLoginClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BadmiBackground)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        // Center Brand Identity
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Box(
                modifier = Modifier
                    .size(110.dp)
                    .background(BadmiPrimary, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.SportsTennis,
                    contentDescription = "BadmiSpot Logo",
                    tint = BadmiTextWhite,
                    modifier = Modifier.size(60.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "BadmiSpot",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = BadmiPrimaryDark
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Bắn cầu lông, bạn chơi, một chạm.",
                fontSize = 15.sp,
                color = BadmiTextSecondary,
                textAlign = TextAlign.Center
            )
        }

        // Action Buttons
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            BadmiButton(
                text = "Bắt đầu ngay →",
                onClick = onGetStarted
            )

            BadmiOutlinedButton(
                text = "Đã có tài khoản? Đăng nhập",
                onClick = onLoginClick
            )
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit,
    onBackClick: () -> Unit
) {
    var phoneOrEmail by remember { mutableStateOf("0908 190 228") }
    var password by remember { mutableStateOf("••••••••") }

    Scaffold(
        topBar = {
            BadmiTopBar(
                title = "Đăng nhập",
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BadmiBackground)
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Chào mừng trở lại! 👋",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = BadmiTextPrimary
            )
            Text(
                text = "Nhập thông tin để tiếp tục đặt sân và ghép kèo",
                fontSize = 13.sp,
                color = BadmiTextSecondary
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text("Số điện thoại hoặc Email", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            OutlinedTextField(
                value = phoneOrEmail,
                onValueChange = { phoneOrEmail = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Text("Mật khẩu", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Text(
                text = "Quên mật khẩu?",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = BadmiPrimary,
                modifier = Modifier.align(Alignment.End)
            )

            Spacer(modifier = Modifier.height(10.dp))

            BadmiButton(
                text = "Đăng nhập",
                onClick = onLoginSuccess
            )

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Chưa có tài khoản? ", fontSize = 13.sp, color = BadmiTextSecondary)
                Text(
                    text = "Đăng ký ngay",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = BadmiPrimary,
                    modifier = Modifier.clickable { onNavigateToRegister() }
                )
            }
        }
    }
}

@Composable
fun RegisterScreen(
    onNavigateToOtp: () -> Unit,
    onBackClick: () -> Unit
) {
    var fullName by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            BadmiTopBar(
                title = "Tạo tài khoản BadmiSpot",
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BadmiBackground)
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Tham gia cộng đồng cầu lông",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = BadmiTextPrimary
            )

            Text("Họ và tên", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                placeholder = { Text("Ví dụ: Nguyễn Minh") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Text("Số điện thoại", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                placeholder = { Text("Ví dụ: 0908 190 228") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Text("Mật khẩu", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Tối thiểu 6 ký tự") },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(14.dp))

            BadmiButton(
                text = "Tiếp tục nhận OTP →",
                onClick = onNavigateToOtp
            )
        }
    }
}

@Composable
fun OtpScreen(
    onVerifySuccess: () -> Unit,
    onBackClick: () -> Unit
) {
    val otpValues = remember { mutableStateListOf("8", "8", "2", "3") }

    Scaffold(
        topBar = {
            BadmiTopBar(
                title = "Xác nhận mã OTP",
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BadmiBackground)
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Nhập mã xác nhận",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = BadmiTextPrimary
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Mã gồm 4 số đã được gửi qua tin nhắn tới số điện thoại của bạn",
                fontSize = 13.sp,
                color = BadmiTextSecondary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(30.dp))

            // 4 OTP Boxes
            Row(horizontalArrangement = Arrangement.spacedBy(14.dp)) {
                otpValues.forEach { digit ->
                    Box(
                        modifier = Modifier
                            .size(56.dp)
                            .background(BadmiSurface, RoundedCornerShape(12.dp))
                            .border(1.5.dp, BadmiPrimary, RoundedCornerShape(12.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = digit,
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = BadmiPrimaryDark
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Gửi lại mã sau 45s",
                fontSize = 12.sp,
                color = BadmiTextMuted
            )

            Spacer(modifier = Modifier.height(30.dp))

            BadmiButton(
                text = "Xác nhận hoàn tất",
                onClick = onVerifySuccess
            )
        }
    }
}

@Composable
fun SkillAssessmentScreen(
    onComplete: () -> Unit
) {
    var selectedLevel by remember { mutableStateOf("Trung bình") }
    val levels = listOf(
        Pair("Mới làm quen", "Mới chơi dưới 6 tháng, đang rèn cầm vợt và đánh đều"),
        Pair("Trung bình", "Đã nắm rõ luật, phát cầu chuẩn, giao lưu thoải mái"),
        Pair("Khá (B)", "Đập cầu uy lực, bộ chân di chuyển linh hoạt, đánh chiến thuật"),
        Pair("Bán chuyên / Thi đấu", "Từng tham gia các giải phong trào, thể lực bền bỉ")
    )

    Scaffold(
        topBar = {
            BadmiTopBar(
                title = "Khảo sát trình độ",
                showBack = false
            )
        },
        bottomBar = {
            Surface(
                color = BadmiSurface,
                shadowElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    BadmiButton(
                        text = "Vào ứng dụng ngay 🏸",
                        onClick = onComplete
                    )
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BadmiBackground)
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                text = "Bạn tự đánh giá mình ở trình độ nào?",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = BadmiTextPrimary
            )
            Text(
                text = "Giúp BadmiSpot gợi ý kèo đấu và đối thủ vừa tầm nhất cho bạn.",
                fontSize = 13.sp,
                color = BadmiTextSecondary
            )

            Spacer(modifier = Modifier.height(8.dp))

            levels.forEach { (level, desc) ->
                val isSelected = selectedLevel == level
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .clickable { selectedLevel = level }
                        .border(
                            width = if (isSelected) 1.8.dp else 1.dp,
                            color = if (isSelected) BadmiPrimary else BadmiBorder,
                            shape = RoundedCornerShape(16.dp)
                        ),
                    color = if (isSelected) BadmiPrimaryLight.copy(alpha = 0.4f) else BadmiSurface
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = isSelected,
                            onClick = { selectedLevel = level },
                            colors = RadioButtonDefaults.colors(selectedColor = BadmiPrimary)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Text(
                                text = level,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = BadmiTextPrimary
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = desc,
                                fontSize = 12.sp,
                                color = BadmiTextSecondary
                            )
                        }
                    }
                }
            }
        }
    }
}
