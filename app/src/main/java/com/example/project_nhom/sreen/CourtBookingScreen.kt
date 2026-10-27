package com.example.project_nhom.sreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CourtBookingScreen(onBack: () -> Unit = {}) {
    var selectedDate by remember { mutableStateOf("19") }
    var selectedTime by remember { mutableStateOf("18:00") }

    Box(modifier = Modifier.fillMaxSize().background(Background)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .statusBarsPadding()
                .padding(horizontal = 20.dp)
                .padding(top = 16.dp, bottom = 100.dp)
        ) {
            // --- Header ---
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back",
                    modifier = Modifier.size(28.dp).clip(CircleShape).clickable { onBack() }
                )
                Spacer(Modifier.width(16.dp))
                Text("Chọn sân & giờ", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextDark)
                Spacer(Modifier.weight(1f))
                Icon(Icons.Filled.MoreHoriz, contentDescription = "More", tint = TextGrey)
            }

            Spacer(Modifier.height(20.dp))

            // --- Ảnh Sân ---
            Box(
                modifier = Modifier.fillMaxWidth().height(180.dp).clip(RoundedCornerShape(16.dp))
                    .background(Brush.verticalGradient(listOf(Color(0xFF8CA899), Color(0xFF2E6B55))))
            )

            Spacer(Modifier.height(16.dp))

            // --- Thông tin sân ---
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.Top) {
                Column(Modifier.weight(1f)) {
                    Text("BadmiSpot Club · Bình Thạnh", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextDark)
                    Spacer(Modifier.height(4.dp))
                    Text("28 Đường D5, Phường 25, Bình Thạnh, TP.HCM", fontSize = 12.sp, color = TextGrey)
                    Spacer(Modifier.height(2.dp))
                    Text("6 sân · Có mái che · Bãi đỗ xe", fontSize = 12.sp, color = TextGrey)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Star, contentDescription = null, tint = AccentGreen, modifier = Modifier.size(16.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("4,9", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = AccentGreen)
                }
            }

            Spacer(Modifier.height(24.dp))

            // --- Chọn Ngày ---
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                val dates = listOf("T2" to "19", "T3" to "20", "T4" to "21", "T5" to "22", "T6" to "23")
                dates.forEach { (day, date) ->
                    val isSelected = date == selectedDate
                    Column(
                        modifier = Modifier
                            .width(56.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isSelected) DarkGreen else Color.White)
                            .clickable { selectedDate = date }
                            .padding(vertical = 10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(day, fontSize = 12.sp, color = if (isSelected) Color.White else TextDark)
                        Spacer(Modifier.height(4.dp))
                        Text(date, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = if (isSelected) Color.White else TextDark)
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            // --- Chọn giờ ---
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Chọn khung giờ", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextDark)
                Box(
                    modifier = Modifier.clip(RoundedCornerShape(50)).background(AccentGreen).padding(horizontal = 12.dp, vertical = 6.dp)
                ) {
                    Text("Sân 02 ⌄", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.Medium)
                }
            }

            Spacer(Modifier.height(12.dp))

            // Grid thời gian
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                    TimeSlot(modifier = Modifier.weight(1f), time = "16:00", isSelected = selectedTime == "16:00") { selectedTime = "16:00" }
                    TimeSlot(modifier = Modifier.weight(1f), time = "17:00", isSelected = false, isDisabled = true) { }
                    TimeSlot(modifier = Modifier.weight(1f), time = "18:00", isSelected = selectedTime == "18:00") { selectedTime = "18:00" }
                }
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.fillMaxWidth()) {
                    TimeSlot(modifier = Modifier.weight(1f), time = "19:00", isSelected = selectedTime == "19:00") { selectedTime = "19:00" }
                    TimeSlot(modifier = Modifier.weight(1f), time = "20:00", isSelected = selectedTime == "20:00") { selectedTime = "20:00" }
                    TimeSlot(modifier = Modifier.weight(1f), time = "21:00", isSelected = selectedTime == "21:00") { selectedTime = "21:00" }
                }
            }

            Spacer(Modifier.height(12.dp))

            // Legend (Chú thích)
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
                LegendItem("Còn trống", AccentGreen)
                LegendItem("Đã chọn", DarkGreen)
                LegendItem("Đã đặt", BorderGrey)
            }

            Spacer(Modifier.height(24.dp))

            // --- Thẻ tóm tắt ---
            Row(
                modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(
                    ChipGreen
                ).padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(Modifier.weight(1f)) {
                    Text("Thứ 2, 19/10 · 18:00–20:00", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = DarkGreen)
                    Spacer(Modifier.height(4.dp))
                    Text("2 giờ · Sân 02", fontSize = 13.sp, color = TextGrey)
                }
                Text("240k ₫", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = DarkGreen)
            }
        }

        // --- Nút Tiếp tục (Cố định dưới cùng) ---
        Button(
            onClick = { },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp)
                .height(56.dp)
                .navigationBarsPadding(),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DarkGreen)
        ) {
            Text("Tiếp tục đặt sân", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.White)
        }
    }
}

@Composable
private fun TimeSlot(
    modifier: Modifier = Modifier,
    time: String,
    isSelected: Boolean,
    isDisabled: Boolean = false,
    onClick: () -> Unit
) {
    val bgColor = when {
        isDisabled -> BorderGrey.copy(alpha = 0.3f)
        isSelected -> DarkGreen
        else -> Color.White
    }
    val textColor = when {
        isDisabled -> TextGrey
        isSelected -> Color.White
        else -> AccentGreen
    }
    val borderColor = if (isDisabled || isSelected) Color.Transparent else AccentGreen.copy(alpha = 0.5f)

    Box(
        modifier = modifier
            .height(44.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(bgColor)
            .border(1.dp, borderColor, RoundedCornerShape(10.dp))
            .clickable(enabled = !isDisabled, onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(time, color = textColor, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun LegendItem(text: String, dotColor: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(Modifier.size(8.dp).clip(CircleShape).background(dotColor))
        Spacer(Modifier.width(6.dp))
        Text(text, fontSize = 11.sp, color = TextGrey)
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun CourtBookingScreenPreview() {
    MaterialTheme { CourtBookingScreen() }
}
