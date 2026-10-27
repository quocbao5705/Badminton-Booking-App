package com.example.project_nhom.sreen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ---------------------------------------------------------------------------
// Màu sắc theo thiết kế BadmiSpot
// ---------------------------------------------------------------------------
val DarkGreen = Color(0xFF0F4A2C)
val Green = Color(0xFF1F7A47)
val AccentGreen = Color(0xFF237A49)
val ChipGreen = Color(0xFFE2F0E5)
val Background = Color(0xFFF2F4F0)
val TextDark = Color(0xFF15201A)
val TextGrey = Color(0xFF7A837D)
val BorderGrey = Color(0xFFDCE1DB)

// ---------------------------------------------------------------------------
// Dữ liệu mẫu (sau này thay bằng dữ liệu lấy từ Firebase)
// ---------------------------------------------------------------------------
data class Court(
    val name: String,
    val rating: Double,
    val distanceKm: Double,
    val courtCount: Int,
    val availableFrom: String,
    val pricePerHour: Int,
)

data class OpenMatch(
    val level: String,
    val slotsLeft: Int,
    val title: String,
    val time: String,
    val area: String,
    val pricePerPerson: Int,
)

private val sampleCourt = Court(
    name = "BadmiSpot Club · Bình Thạnh",
    rating = 4.9,
    distanceKm = 1.2,
    courtCount = 6,
    availableFrom = "17:00",
    pricePerHour = 120_000,
)

private val sampleMatch = OpenMatch(
    level = "Trung bình",
    slotsLeft = 2,
    title = "Đánh đôi sau giờ làm",
    time = "18:00–20:00",
    area = "Bình Thạnh",
    pricePerPerson = 60_000,
)

private fun Double.vn(): String = toString().replace('.', ',')
private fun Int.toK(): String = "${this / 1000}k ₫"

// ---------------------------------------------------------------------------
// Màn hình Trang chủ
// ---------------------------------------------------------------------------
@Composable
fun HomeScreen(
    userName: String = "Minh",
    location: String = "Bình Thạnh, TP.HCM",
    onBookCourt: () -> Unit = {},
    onFindMatch: () -> Unit = {},
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    var query by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .statusBarsPadding()
                .padding(horizontal = 20.dp)
                .padding(top = 12.dp, bottom = 120.dp) // chừa chỗ cho thanh điều hướng
        ) {
            HeaderRow(location)

            Spacer(Modifier.height(14.dp))
            Text(
                text = "Chào $userName, hôm nay chơi gì?",
                fontSize = 30.sp,
                lineHeight = 38.sp,
                fontWeight = FontWeight.ExtraBold,
                color = TextDark,
            )

            Spacer(Modifier.height(18.dp))
            SearchBox(query = query, onQueryChange = { query = it })

            Spacer(Modifier.height(14.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ActionCard(
                    title = "Đặt sân",
                    background = DarkGreen,
                    onClick = onBookCourt,
                    modifier = Modifier.weight(1f),
                    icon = {
                        Icon(
                            Icons.Outlined.DateRange, contentDescription = null,
                            tint = Color.White, modifier = Modifier.size(24.dp)
                        )
                    },
                )
                ActionCard(
                    title = "Tìm trận",
                    background = Green,
                    onClick = onFindMatch,
                    modifier = Modifier.weight(1f),
                    icon = { PlayCircleIcon(color = Color.White, size = 24.dp) },
                )
            }

            Spacer(Modifier.height(26.dp))
            SectionHeader(title = "Sân quanh đây", action = "Xem tất cả")
            Spacer(Modifier.height(12.dp))
            CourtCard(sampleCourt)

            Spacer(Modifier.height(26.dp))
            SectionHeader(title = "Trận đang mở", action = "Khám phá")
            Spacer(Modifier.height(12.dp))
            MatchCard(sampleMatch)
        }

        BottomBar(
            selected = selectedTab,
            onSelect = { selectedTab = it },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .navigationBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        )
    }
}

// ---------------------------------------------------------------------------
// Các thành phần nhỏ
// ---------------------------------------------------------------------------
@Composable
private fun HeaderRow(location: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            Icons.Outlined.LocationOn, contentDescription = null,
            tint = AccentGreen, modifier = Modifier.size(18.dp)
        )
        Spacer(Modifier.width(4.dp))
        Text(
            text = location,
            color = AccentGreen,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.weight(1f),
        )
        Icon(
            Icons.Outlined.Notifications, contentDescription = "Thông báo",
            tint = AccentGreen,
            modifier = Modifier
                .size(26.dp)
                .clip(CircleShape)
                .clickable { }
        )
    }
}

@Composable
private fun SearchBox(query: String, onQueryChange: (String) -> Unit) {
    val shape = RoundedCornerShape(14.dp)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .clip(shape)
            .background(Color.White)
            .border(1.dp, BorderGrey, shape)
            .padding(horizontal = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            Icons.Outlined.Search, contentDescription = null,
            tint = DarkGreen, modifier = Modifier.size(22.dp)
        )
        Spacer(Modifier.width(10.dp))
        Box(Modifier.weight(1f)) {
            if (query.isEmpty()) {
                Text("Tìm sân hoặc khu vực", color = TextGrey, fontSize = 15.sp)
            }
            BasicTextField(
                value = query,
                onValueChange = onQueryChange,
                singleLine = true,
                textStyle = TextStyle(fontSize = 15.sp, color = TextDark),
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun ActionCard(
    title: String,
    background: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
) {
    Column(
        modifier = modifier
            .height(96.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(background)
            .clickable(onClick = onClick)
            .padding(14.dp),
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            icon()
            // Mũi tên chéo ↗ = mũi tên sang phải xoay -45 độ
            Icon(
                Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(18.dp)
                    .rotate(-45f)
            )
        }
        Text(title, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
private fun PlayCircleIcon(color: Color, size: Dp) {
    Box(
        modifier = Modifier
            .size(size)
            .border(1.7.dp, color, CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            Icons.Filled.PlayArrow, contentDescription = null,
            tint = color, modifier = Modifier.size(size * 0.62f)
        )
    }
}

@Composable
private fun SectionHeader(title: String, action: String, onAction: () -> Unit = {}) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark,
            modifier = Modifier.weight(1f),
        )
        Text(
            text = action,
            fontSize = 13.sp,
            fontWeight = FontWeight.SemiBold,
            color = AccentGreen,
            modifier = Modifier.clickable(onClick = onAction),
        )
    }
}

@Composable
private fun CourtCard(court: Court, onClick: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .clickable(onClick = onClick)
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Ảnh sân: tạm dùng khối màu. Khi có ảnh, thay bằng:
        // Image(painterResource(R.drawable.san_cau_long), null,
        //       contentScale = ContentScale.Crop, modifier = Modifier.size(82.dp).clip(RoundedCornerShape(14.dp)))
        Box(
            modifier = Modifier
                .size(82.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(
                    Brush.verticalGradient(listOf(Color(0xFF9AAFA4), Color(0xFF2E6B55)))
                ),
            contentAlignment = Alignment.Center,
        ) {
            Text("🏸", fontSize = 30.sp)
        }

        Spacer(Modifier.width(14.dp))

        Column(Modifier.weight(1f)) {
            Text(
                text = court.name,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Spacer(Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Filled.Star, contentDescription = null,
                    tint = TextGrey, modifier = Modifier.size(13.dp)
                )
                Spacer(Modifier.width(3.dp))
                Text(
                    text = "${court.rating.vn()} · ${court.distanceKm.vn()} km · ${court.courtCount} sân",
                    fontSize = 12.sp,
                    color = TextGrey,
                )
            }
            Spacer(Modifier.height(6.dp))
            Text(
                text = "Còn trống từ ${court.availableFrom}",
                fontSize = 12.sp,
                color = AccentGreen,
            )
            Spacer(Modifier.height(2.dp))
            Text(
                text = "${court.pricePerHour.toK()} / giờ",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = AccentGreen,
            )
        }
    }
}

@Composable
private fun MatchCard(match: OpenMatch, onClick: () -> Unit = {}) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(Color.White)
            .clickable(onClick = onClick)
            .padding(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = match.level,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = AccentGreen,
                modifier = Modifier
                    .clip(RoundedCornerShape(50))
                    .background(ChipGreen)
                    .padding(horizontal = 12.dp, vertical = 5.dp),
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = "Còn ${match.slotsLeft} chỗ",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = AccentGreen,
            )
        }

        Spacer(Modifier.height(10.dp))
        Text(
            text = match.title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark,
        )

        Spacer(Modifier.height(6.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "${match.time} · ${match.area}",
                fontSize = 12.sp,
                color = TextGrey,
                modifier = Modifier.weight(1f),
            )
            Text(
                text = "${match.pricePerPerson.toK()} / người",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = AccentGreen,
            )
        }
    }
}

// ---------------------------------------------------------------------------
// Thanh điều hướng dạng viên thuốc ở dưới cùng
// ---------------------------------------------------------------------------
@Composable
fun BottomBar(
    selected: Int,
    onSelect: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    val labels = listOf("Trang chủ", "Chơi", "Lịch đặt", "Hồ sơ")

    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(72.dp)
            .clip(RoundedCornerShape(36.dp))
            .background(DarkGreen)
            .padding(6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        labels.forEachIndexed { index, label ->
            val isSelected = index == selected
            val contentColor = if (isSelected) DarkGreen else Color.White

            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(30.dp))
                    .background(if (isSelected) Color.White else Color.Transparent)
                    .clickable { onSelect(index) },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                NavIcon(index, contentColor)
                Spacer(Modifier.height(3.dp))
                Text(
                    text = label,
                    color = contentColor,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
        }
    }
}

@Composable
private fun NavIcon(index: Int, color: Color) {
    val m = Modifier.size(22.dp)
    when (index) {
        0 -> Icon(Icons.Outlined.Home, contentDescription = null, tint = color, modifier = m)
        1 -> PlayCircleIcon(color = color, size = 21.dp)
        2 -> Icon(Icons.Outlined.DateRange, contentDescription = null, tint = color, modifier = m)
        else -> Icon(Icons.Outlined.Person, contentDescription = null, tint = color, modifier = m)
    }
}

// ---------------------------------------------------------------------------
// Xem trước trong Android Studio (khung Preview / Split)
// ---------------------------------------------------------------------------
@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun HomeScreenPreview() {
    MaterialTheme {
        HomeScreen()
    }
}
