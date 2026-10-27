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
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Màu bổ trợ cho màn hình
private val OrangeBg = Color(0xFFFFF3E0)
private val OrangeText = Color(0xFFF57C00)
private val LightGreenBg = Color(0xFFE8F5E9)

// ==========================================
// 1. MÀN HÌNH CHI TIẾT TRẬN
// ==========================================
@Composable
fun MatchDetailsScreen(
    onBack: () -> Unit = {},
    onJoinClick: () -> Unit = {} // Hàm mở Bottom Sheet
) {
    Column(
        modifier = Modifier.fillMaxSize().background(Background).statusBarsPadding()
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", modifier = Modifier.size(28.dp).clip(CircleShape).clickable { onBack() }, tint = TextDark)
            Spacer(Modifier.width(16.dp))
            Text("Chi tiết trận", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextDark)
            Spacer(Modifier.weight(1f))
            Icon(Icons.Filled.MoreHoriz, contentDescription = "More", tint = TextGrey)
        }

        Column(
            modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = 20.dp)
        ) {
            // Tag & Slots
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Box(Modifier.clip(RoundedCornerShape(50)).background(LightGreenBg).padding(horizontal = 12.dp, vertical = 4.dp)) {
                    Text("Trung bình", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = AccentGreen)
                }
                Text("Còn 2 chỗ", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = AccentGreen)
            }

            Spacer(Modifier.height(12.dp))
            Text("Đánh đôi sau giờ làm", fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, color = TextDark)

            Spacer(Modifier.height(16.dp))

            // Thẻ thông tin màu xanh đậm
            Column(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(DarkGreen).padding(16.dp)) {
                Text("BadmiSpot Club · Bình Thạnh", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Spacer(Modifier.height(4.dp))
                Text("Hôm nay, 18:00–20:00 · Chi tiết lịch", fontSize = 13.sp, color = Color.White.copy(alpha = 0.8f))
                Spacer(Modifier.height(12.dp))
                Text("60k đ / người", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.White)
            }

            Spacer(Modifier.height(24.dp))

            // Danh sách người chơi
            Text("Người chơi (4/6)", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextDark)
            Spacer(Modifier.height(12.dp))

            PlayerRow("Linh", "L", "Trung bình", LightGreenBg, AccentGreen)
            PlayerRow("Minh", "M", "Trung bình", LightGreenBg, AccentGreen)
            PlayerRow("An", "A", "Mới chơi", OrangeBg, OrangeText)
            PlayerRow("Huy", "H", "Trung bình", LightGreenBg, AccentGreen)

            // Ô trống
            Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                EmptyPlayerSlot(Modifier.weight(1f))
                EmptyPlayerSlot(Modifier.weight(1f))
            }

            Spacer(Modifier.height(16.dp))

            // Quy định
            Column(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(LightGreenBg).padding(16.dp)) {
                Text("Quy định", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = DarkGreen)
                Spacer(Modifier.height(6.dp))
                Text("• Đến đúng giờ, tự mang vợt. Bạn đến sớm 10 phút để khởi động nhé.", fontSize = 12.sp, color = AccentGreen)
            }
        }

        Button(
            onClick = onJoinClick,
            modifier = Modifier.fillMaxWidth().padding(20.dp).height(56.dp).navigationBarsPadding(),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DarkGreen)
        ) {
            Text("Tham gia trận", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun PlayerRow(name: String, initial: String, level: String, levelBg: Color, levelText: Color) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.size(32.dp).clip(CircleShape).background(ChipGreen), contentAlignment = Alignment.Center) {
            Text(initial, fontWeight = FontWeight.Bold, color = DarkGreen)
        }
        Spacer(Modifier.width(12.dp))
        Text(name, fontSize = 15.sp, fontWeight = FontWeight.SemiBold, color = TextDark, modifier = Modifier.weight(1f))
        Box(Modifier.clip(RoundedCornerShape(50)).background(levelBg).padding(horizontal = 10.dp, vertical = 4.dp)) {
            Text(level, fontSize = 11.sp, fontWeight = FontWeight.Medium, color = levelText)
        }
    }
}

@Composable
private fun EmptyPlayerSlot(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.height(40.dp).clip(RoundedCornerShape(8.dp)).border(1.dp, BorderGrey, RoundedCornerShape(8.dp)),
        contentAlignment = Alignment.Center
    ) {
        Text("+ Còn trống", fontSize = 12.sp, color = TextGrey)
    }
}

// ==========================================
// 2. BOTTOM SHEET XÁC NHẬN THAM GIA
// ==========================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JoinConfirmationBottomSheet(
    onDismiss: () -> Unit = {},
    onConfirm: () -> Unit = {}
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color.White,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)) {
            Text("Tham gia trận này?", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = TextDark)
            Spacer(Modifier.height(8.dp))
            Text("Đánh đôi sau giờ làm. Hôm nay, 18:00–20:00 · Bình Thạnh", fontSize = 13.sp, color = TextGrey)

            Spacer(Modifier.height(24.dp))

            // Chi phí
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Chi phí tham gia", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = TextDark)
                Text("60k đ", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = DarkGreen)
            }

            Spacer(Modifier.height(12.dp))

            // Nút thanh toán
            Row(
                modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(LightGreenBg).clickable { }.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(Icons.Filled.Check, contentDescription = null, tint = AccentGreen, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text("Thanh toán bằng MoMo", fontSize = 14.sp, fontWeight = FontWeight.Medium, color = TextDark, modifier = Modifier.weight(1f))
                Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = TextDark)
            }

            Spacer(Modifier.height(16.dp))

            // Điều khoản
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Check, contentDescription = null, tint = AccentGreen, modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(8.dp))
                Text("Tôi đồng ý với quy định của trận", fontSize = 13.sp, color = TextDark)
            }

            Spacer(Modifier.height(24.dp))

            Button(
                onClick = onConfirm,
                modifier = Modifier.fillMaxWidth().height(56.dp).navigationBarsPadding(),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = DarkGreen)
            ) {
                Text("Xác nhận tham gia", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}

// ==========================================
// 3. MÀN HÌNH TẠO TRẬN MỚI
// ==========================================
@Composable
fun CreateMatchScreen(
    onBack: () -> Unit = {},
    onPost: () -> Unit = {}
) {
    var selectedLevel by remember { mutableStateOf("Trung bình") }
    var playerCount by remember { mutableIntStateOf(6) }

    Column(modifier = Modifier.fillMaxSize().background(Background).statusBarsPadding()) {
        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", modifier = Modifier.size(28.dp).clip(CircleShape).clickable { onBack() }, tint = TextDark)
            Spacer(Modifier.width(16.dp))
            Text("Tạo trận mới", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextDark)
            Spacer(Modifier.weight(1f))
            Icon(Icons.Filled.MoreHoriz, contentDescription = "More", tint = TextGrey)
        }

        Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = 20.dp)) {
            Text("Rủ bạn ra sân.", fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, color = TextDark)
            Spacer(Modifier.height(20.dp))

            // Form fields
            FormLabel("Sân / địa điểm")
            FormInputBox("BadmiSpot Club · Bình Thạnh", Icons.Outlined.LocationOn)

            FormLabel("Ngày và giờ")
            FormInputBox("Thứ 2, 19/10 · 18:00–20:00", Icons.Filled.DateRange)

            FormLabel("Trình độ")
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                LevelChip("Mới chơi", selectedLevel == "Mới chơi") { selectedLevel = "Mới chơi" }
                LevelChip("Trung bình", selectedLevel == "Trung bình") { selectedLevel = "Trung bình" }
                LevelChip("Nâng cao", selectedLevel == "Nâng cao") { selectedLevel = "Nâng cao" }
            }

            FormLabel("Số người chơi")
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.weight(1f))
                IconButton(onClick = { if (playerCount > 2) playerCount-- }, modifier = Modifier.clip(CircleShape).background(ChipGreen)) {
                    Icon(Icons.Filled.Remove, contentDescription = null, tint = DarkGreen)
                }
                Text("$playerCount", fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(horizontal = 16.dp), color = TextDark)
                IconButton(onClick = { if (playerCount < 10) playerCount++ }, modifier = Modifier.clip(CircleShape).background(ChipGreen)) {
                    Icon(Icons.Filled.Add, contentDescription = null, tint = DarkGreen)
                }
            }

            FormLabel("Giá mỗi người")
            FormInputBox("60k đ / người", null)
            Text("Tự tính từ tiền sân và cầu: 360k / 6 người. Bạn có thể đổi.", fontSize = 11.sp, color = TextGrey)

            FormLabel("Mô tả")
            OutlinedTextField(
                value = "Đánh đôi vui vẻ, cũng tiến bộ mỗi ngày!",
                onValueChange = {},
                modifier = Modifier.fillMaxWidth().height(100.dp),
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.White, unfocusedContainerColor = Color.White,
                    focusedIndicatorColor = AccentGreen, unfocusedIndicatorColor = BorderGrey
                )
            )
            Spacer(Modifier.height(20.dp))
        }

        Button(
            onClick = onPost,
            modifier = Modifier.fillMaxWidth().padding(20.dp).height(56.dp).navigationBarsPadding(),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DarkGreen)
        ) {
            Text("Đăng trận", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun FormLabel(text: String) {
    Text(text, fontSize = 13.sp, fontWeight = FontWeight.Medium, color = TextDark, modifier = Modifier.padding(top = 16.dp, bottom = 8.dp))
}

@Composable
private fun FormInputBox(text: String, icon: androidx.compose.ui.graphics.vector.ImageVector?) {
    Row(
        modifier = Modifier.fillMaxWidth().height(48.dp).clip(RoundedCornerShape(12.dp)).background(Color.White).border(1.dp, BorderGrey, RoundedCornerShape(12.dp)).padding(horizontal = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (icon != null) {
            Icon(icon, contentDescription = null, tint = DarkGreen, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(8.dp))
        }
        Text(text, fontSize = 14.sp, color = TextDark)
    }
}

@Composable
private fun LevelChip(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier.clip(RoundedCornerShape(50))
            .background(if (isSelected) ChipGreen else Color.White)
            .border(1.dp, if (isSelected) AccentGreen else BorderGrey, RoundedCornerShape(50))
            .clickable { onClick() }.padding(horizontal = 16.dp, vertical = 6.dp)
    ) {
        Text(text, fontSize = 13.sp, color = if (isSelected) AccentGreen else TextGrey, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal)
    }
}

// ==========================================
// 4. MÀN HÌNH PHÒNG CHỜ TRẬN
// ==========================================
@Composable
fun MatchLobbyScreen(
    onBack: () -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxSize().background(Background).statusBarsPadding()) {
        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", modifier = Modifier.size(28.dp).clip(CircleShape).clickable { onBack() }, tint = TextDark)
            Spacer(Modifier.width(16.dp))
            Text("Phòng chờ trận", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextDark)
            Spacer(Modifier.weight(1f))
            Icon(Icons.Filled.MoreHoriz, contentDescription = "More", tint = TextGrey)
        }

        Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = 20.dp)) {
            // Đồng hồ đếm ngược
            Column(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(DarkGreen).padding(16.dp)) {
                Text("Bắt đầu sau", fontSize = 13.sp, color = Color.White.copy(alpha = 0.8f))
                Text("02:15:30", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Spacer(Modifier.height(4.dp))
                Text("Đánh đôi sau giờ làm. 18:00–20:00. BadmiSpot Club · Bình Thạnh", fontSize = 12.sp, color = Color.White.copy(alpha = 0.8f))
            }

            Spacer(Modifier.height(20.dp))
            Text("Đội hình của bạn · 4/6", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextDark)
            Spacer(Modifier.height(12.dp))

            PlayerRow("Linh", "L", "Trung bình", LightGreenBg, AccentGreen)
            PlayerRow("Minh", "M", "Trung bình", LightGreenBg, AccentGreen)
            PlayerRow("An", "A", "Mới chơi", OrangeBg, OrangeText)
            PlayerRow("Huy", "H", "Trung bình", LightGreenBg, AccentGreen)

            Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                EmptyPlayerSlot(Modifier.weight(1f))
                EmptyPlayerSlot(Modifier.weight(1f))
            }

            Spacer(Modifier.height(16.dp))

            // Trò chuyện
            Row(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(12.dp)).background(Color.White).padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Box(modifier = Modifier.size(32.dp).clip(CircleShape).background(ChipGreen), contentAlignment = Alignment.Center) {
                    Text("L", fontWeight = FontWeight.Bold, color = DarkGreen)
                }
                Spacer(Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        Text("Linh · Chủ trì", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = TextDark)
                        Text("15:42", fontSize = 11.sp, color = TextGrey)
                    }
                    Text("Mình đã chuẩn bị cầu đủ. Hẹn mọi người ở sân 02 nhé!", fontSize = 13.sp, color = TextGrey)
                }
            }
            Text("Rời trận", fontSize = 13.sp, color = TextGrey, modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp), textAlign = TextAlign.Center)
        }

        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth().padding(20.dp).height(56.dp).navigationBarsPadding(),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DarkGreen)
        ) {
            Text("Mở trò chuyện", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

// ==========================================
// 5. MÀN HÌNH ĐÁNH GIÁ SAU TRẬN
// ==========================================
@Composable
fun MatchReviewScreen(
    onBack: () -> Unit = {},
    onSubmit: () -> Unit = {}
) {
    Column(modifier = Modifier.fillMaxSize().background(Background).statusBarsPadding()) {
        Row(modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 16.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", modifier = Modifier.size(28.dp).clip(CircleShape).clickable { onBack() }, tint = TextDark)
            Spacer(Modifier.width(16.dp))
            Text("Đánh giá sau trận", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextDark)
            Spacer(Modifier.weight(1f))
            Icon(Icons.Filled.MoreHoriz, contentDescription = "More", tint = TextGrey)
        }

        Column(modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = 20.dp)) {
            Text("Trận đấu thế nào?", fontSize = 26.sp, fontWeight = FontWeight.ExtraBold, color = TextDark)
            Text("Một lần nữa cảm ơn bạn đã giúp cộng đồng chơi vui hơn mỗi ngày.", fontSize = 13.sp, color = TextGrey)

            Spacer(Modifier.height(20.dp))

            // Đánh giá người chơi
            Column(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(Color.White).padding(16.dp)) {
                Text("Bạn chơi cùng", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextDark)
                Spacer(Modifier.height(12.dp))
                ReviewPlayerRow("Linh", "L")
                ReviewPlayerRow("Minh", "M")
                ReviewPlayerRow("An", "A")
            }

            Spacer(Modifier.height(16.dp))

            // Đánh giá sân
            Column(modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(Color.White).padding(16.dp)) {
                Text("Sân · BadmiSpot Club", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextDark)
                Spacer(Modifier.height(8.dp))
                RatingBar()
            }

            Spacer(Modifier.height(16.dp))

            // Tags
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                ReviewTag("Thân thiện")
                ReviewTag("Đúng giờ")
                ReviewTag("Trình độ tốt")
            }

            Spacer(Modifier.height(16.dp))

            // Text field
            OutlinedTextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("Chia sẻ thêm với mọi người...", fontSize = 13.sp, color = TextGrey) },
                modifier = Modifier.fillMaxWidth().height(100.dp),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = Color.White, unfocusedContainerColor = Color.White,
                    focusedBorderColor = AccentGreen, unfocusedBorderColor = BorderGrey
                )
            )
        }

        Button(
            onClick = onSubmit,
            modifier = Modifier.fillMaxWidth().padding(20.dp).height(56.dp).navigationBarsPadding(),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DarkGreen)
        ) {
            Text("Gửi đánh giá", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun ReviewPlayerRow(name: String, initial: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.size(32.dp).clip(CircleShape).background(ChipGreen), contentAlignment = Alignment.Center) {
            Text(initial, fontWeight = FontWeight.Bold, color = DarkGreen)
        }
        Spacer(Modifier.width(12.dp))
        Text(name, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = TextDark, modifier = Modifier.weight(1f))
        RatingBar(size = 16.dp)
    }
}

@Composable
private fun RatingBar(size: androidx.compose.ui.unit.Dp = 20.dp) {
    var rating by remember { mutableIntStateOf(5) }
    Row {
        for (i in 1..5) {
            Icon(
                imageVector = if (i <= rating) Icons.Filled.Star else Icons.Outlined.Star,
                contentDescription = null,
                tint = if (i <= rating) AccentGreen else TextGrey,
                modifier = Modifier.size(size).clickable { rating = i }.padding(end = 2.dp)
            )
        }
    }
}

@Composable
private fun ReviewTag(text: String) {
    Box(modifier = Modifier.clip(RoundedCornerShape(50)).background(ChipGreen).padding(horizontal = 12.dp, vertical = 6.dp)) {
        Text(text, fontSize = 12.sp, color = AccentGreen, fontWeight = FontWeight.Medium)
    }
}

// ==========================================
// PREVIEWS FOR ANDROID STUDIO
// ==========================================
@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun MatchDetailsScreenPreview() {
    MaterialTheme { MatchDetailsScreen() }
}

@Preview(showBackground = true)
@Composable
private fun JoinConfirmationBottomSheetPreview() {
    MaterialTheme { JoinConfirmationBottomSheet() }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun CreateMatchScreenPreview() {
    MaterialTheme { CreateMatchScreen() }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun MatchLobbyScreenPreview() {
    MaterialTheme { MatchLobbyScreen() }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun MatchReviewScreenPreview() {
    MaterialTheme { MatchReviewScreen() }
}