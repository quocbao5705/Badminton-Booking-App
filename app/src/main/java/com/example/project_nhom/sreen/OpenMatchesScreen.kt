package com.example.project_nhom.sreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Màu cho màn hình này
private val LevelOrangeBg = Color(0xFFFFF0D4)
private val LevelOrangeText = Color(0xFFD97706)

// Data class mở rộng cho màn hình này
data class MatchItem(
    val level: String,
    val levelBg: Color,
    val levelTextColor: Color,
    val slotsLeft: String,
    val slotsTextColor: Color,
    val title: String,
    val location: String,
    val time: String,
    val price: Int,
    val joinedPlayers: Int,
    val maxPlayers: Int,
    val players: List<String>
)

@Composable
fun OpenMatchesScreen(
    onHomeTab: () -> Unit = {},
    onProfileTab: () -> Unit = {}
) {
    val matches = listOf(
        MatchItem(
            level = "Trung bình", levelBg = ChipGreen, levelTextColor = AccentGreen,
            slotsLeft = "Còn 2 chỗ", slotsTextColor = AccentGreen,
            title = "Đánh đôi sau giờ làm", location = "BadmiSpot Club · Bình Thạnh",
            time = "Hôm nay, 18:00–20:00", price = 60000, joinedPlayers = 4, maxPlayers = 6,
            players = listOf("L", "M", "A")
        ),
        MatchItem(
            level = "Nâng cao", levelBg = LevelOrangeBg, levelTextColor = LevelOrangeText,
            slotsLeft = "Chỗ cuối", slotsTextColor = LevelOrangeText,
            title = "Cầu lông hết mình", location = "Sunrise Badminton · Phú Nhuận",
            time = "Hôm nay, 18:00–20:00", price = 60000, joinedPlayers = 5, maxPlayers = 6,
            players = listOf("L", "M", "A")
        ),
        MatchItem(
            level = "Mới chơi", levelBg = ChipGreen, levelTextColor = AccentGreen,
            slotsLeft = "Còn 2 chỗ", slotsTextColor = AccentGreen,
            title = "Làm quen cùng bạn mới", location = "Thủ Đức Arena",
            time = "Hôm nay, 18:00–20:00", price = 60000, joinedPlayers = 4, maxPlayers = 6,
            players = listOf("L", "M", "A")
        )
    )

    Scaffold(
        containerColor = Background,
        topBar = {
            Column(modifier = Modifier.statusBarsPadding().padding(horizontal = 20.dp, vertical = 16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                    Text("Tìm bạn chơi cùng", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = TextDark)
                    Spacer(Modifier.weight(1f))
                    Icon(Icons.Filled.MoreHoriz, contentDescription = "More", tint = TextGrey)
                }
                Spacer(Modifier.height(16.dp))
                // Bộ lọc
                LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    item {
                        Box(Modifier.clip(RoundedCornerShape(50)).background(DarkGreen).padding(horizontal = 16.dp, vertical = 8.dp)) {
                            Text("Hôm nay, 19/10", color = Color.White, fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
                        }
                    }
                    item {
                        Box(Modifier.clip(RoundedCornerShape(50)).background(Color.White).padding(horizontal = 16.dp, vertical = 8.dp)) {
                            Text("Mọi trình độ", color = TextGrey, fontSize = 13.sp)
                        }
                    }
                    item {
                        Box(Modifier.clip(RoundedCornerShape(50)).background(Color.White).padding(horizontal = 16.dp, vertical = 8.dp)) {
                            Text("Gần tôi", color = TextGrey, fontSize = 13.sp)
                        }
                    }
                }
            }
        },
        bottomBar = {
            // Tái sử dụng BottomBar từ HomeScreen.kt
            BottomBar(selected = 1, onSelect = {
                if (it == 0) onHomeTab()
                if (it == 3) onProfileTab()
            })
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(matches) { match ->
                MatchCardLarge(match)
            }
            item { Spacer(Modifier.height(20.dp)) }
        }
    }
}

@Composable
private fun MatchCardLarge(match: MatchItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .padding(16.dp)
    ) {
        // Hàng đầu: Level & Slots
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
            Box(Modifier.clip(RoundedCornerShape(50)).background(match.levelBg).padding(horizontal = 12.dp, vertical = 4.dp)) {
                Text(match.level, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = match.levelTextColor)
            }
            Text(match.slotsLeft, fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = match.slotsTextColor)
        }

        Spacer(Modifier.height(12.dp))
        Text(match.title, fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextDark)

        Spacer(Modifier.height(6.dp))
        Text(match.location, fontSize = 13.sp, color = TextGrey)
        Text(match.time, fontSize = 13.sp, color = TextGrey)

        Spacer(Modifier.height(16.dp))

        // Hàng dưới: Avatars, Giá, Nút Tham gia
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            // Avatars
            Row(verticalAlignment = Alignment.CenterVertically) {
                match.players.forEach { player ->
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(CircleShape)
                            .background(ChipGreen),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(player, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = AccentGreen)
                    }
                    Spacer(Modifier.width(4.dp))
                }
                // Dấu chấm cho các slot trống
                repeat(match.maxPlayers - match.players.size) {
                    Box(
                        modifier = Modifier.size(28.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(Modifier.size(6.dp).clip(CircleShape).background(BorderGrey))
                    }
                }
            }

            Spacer(Modifier.width(8.dp))
            Text("${match.joinedPlayers}/${match.maxPlayers} đã tham gia", fontSize = 11.sp, color = TextGrey)

            Spacer(Modifier.weight(1f))

            // Giá và Nút Tham Gia
            Column(horizontalAlignment = Alignment.End) {
                Text("${match.price / 1000}k ₫ / người", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = AccentGreen)
                Spacer(Modifier.height(6.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(10.dp))
                        .background(ChipGreen)
                        .clickable { }
                        .padding(horizontal = 16.dp, vertical = 6.dp)
                ) {
                    Text("Tham gia", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = AccentGreen)
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun OpenMatchesScreenPreview() {
    MaterialTheme { OpenMatchesScreen() }
}