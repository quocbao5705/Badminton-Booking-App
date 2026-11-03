package com.example.badmintonbookingapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.badmintonbookingapp.data.BadmiData
import com.example.badmintonbookingapp.model.UserProfile
import com.example.badmintonbookingapp.ui.components.*
import com.example.badmintonbookingapp.ui.theme.*

@Composable
fun ProfileScreen(
    onNavigateToEditProfile: () -> Unit,
    onNavigateToFavorites: () -> Unit,
    onNavigateToVouchers: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToNotifications: () -> Unit,
    onNavigateToCourtDetail: (String) -> Unit
) {
    val user = BadmiData.sampleUser

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BadmiBackground)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 90.dp)
    ) {
        BadmiTopBar(
            title = "Hồ sơ cá nhân",
            showBack = false,
            actions = {
                IconButton(onClick = onNavigateToSettings) {
                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = "Cài đặt",
                        tint = BadmiTextPrimary
                    )
                }
            }
        )

        // Profile Header Card
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(BadmiPrimaryContainer, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "M",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = BadmiPrimaryDark
                )
            }

            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = user.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = BadmiTextPrimary
            )
            Text(
                text = user.phone,
                fontSize = 12.sp,
                color = BadmiTextSecondary
            )

            Spacer(modifier = Modifier.height(6.dp))
            BadmiBadge(text = user.skillLevel)

            Spacer(modifier = Modifier.height(18.dp))

            // 3 Stats Boxes (24 Trận | 48 Giờ chơi | 96% Tỉ lệ đúng hẹn)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(BadmiSurface, RoundedCornerShape(16.dp))
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                StatColumn(number = "${user.matchesPlayed}", label = "Trận đã đấu")
                VerticalDivider(modifier = Modifier.height(36.dp), color = BadmiBorderLight)
                StatColumn(number = "${user.hoursPlayed}h", label = "Giờ ra sân")
                VerticalDivider(modifier = Modifier.height(36.dp), color = BadmiBorderLight)
                StatColumn(number = "${user.punctualityRate}%", label = "Đúng hẹn")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Profile Menu Items
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            ProfileMenuItem(
                icon = Icons.Outlined.Person,
                title = "Chỉnh sửa thông tin",
                subtitle = "Tên, số điện thoại, phong cách chơi",
                onClick = onNavigateToEditProfile
            )
            ProfileMenuItem(
                icon = Icons.Outlined.FavoriteBorder,
                title = "Sân đấu yêu thích",
                subtitle = "Xem lại các sân thường xuyên ghé",
                onClick = onNavigateToFavorites
            )
            ProfileMenuItem(
                icon = Icons.Outlined.ConfirmationNumber,
                title = "Ví mã giảm giá & Khuyến mãi",
                subtitle = "3 mã đang khả dụng",
                onClick = onNavigateToVouchers
            )
            ProfileMenuItem(
                icon = Icons.Outlined.Notifications,
                title = "Thông báo hệ thống",
                subtitle = "Cập nhật trận đấu & lịch nhắc",
                onClick = onNavigateToNotifications
            )
            ProfileMenuItem(
                icon = Icons.Outlined.Settings,
                title = "Cài đặt & Điều khoản",
                subtitle = "Ngôn ngữ, hỗ trợ kỹ thuật",
                onClick = onNavigateToSettings
            )
        }
    }
}

@Composable
fun StatColumn(number: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = number,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = BadmiPrimary
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            fontSize = 11.sp,
            color = BadmiTextSecondary
        )
    }
}

@Composable
fun ProfileMenuItem(
    icon: ImageVector,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    BadmiCard(onClick = onClick) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(BadmiPrimaryLight, RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = BadmiPrimary,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = BadmiTextPrimary
                )
                Text(
                    text = subtitle,
                    fontSize = 11.sp,
                    color = BadmiTextSecondary
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForwardIos,
                contentDescription = null,
                tint = BadmiTextMuted,
                modifier = Modifier.size(14.dp)
            )
        }
    }
}

@Composable
fun EditProfileScreen(
    onBackClick: () -> Unit,
    onSaveSuccess: () -> Unit
) {
    var name by remember { mutableStateOf("Nguyễn Minh") }
    var phone by remember { mutableStateOf("0908 190 228") }
    var email by remember { mutableStateOf("minh.badmi@gmail.com") }
    var handedness by remember { mutableStateOf("Tay phải") }
    var style by remember { mutableStateOf("Tấn công") }

    Scaffold(
        topBar = {
            BadmiTopBar(
                title = "Chỉnh sửa hồ sơ",
                onBackClick = onBackClick
            )
        },
        bottomBar = {
            Surface(
                color = BadmiSurface,
                shadowElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    BadmiButton(text = "Lưu thay đổi", onClick = onSaveSuccess)
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
            Text("Họ và tên", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Text("Số điện thoại", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            OutlinedTextField(
                value = phone,
                onValueChange = { phone = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Text("Email", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Text("Tay thuận", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                listOf("Tay phải", "Tay trái").forEach { h ->
                    BadmiChip(text = h, isSelected = handedness == h, onClick = { handedness = h })
                }
            }

            Text("Lối chơi sở trường", fontWeight = FontWeight.Bold, fontSize = 13.sp)
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                listOf("Tấn công", "Phòng thủ phản tạt", "Toàn diện").forEach { s ->
                    BadmiChip(text = s, isSelected = style == s, onClick = { style = s })
                }
            }
        }
    }
}

@Composable
fun FavoritesScreen(
    onBackClick: () -> Unit,
    onNavigateToCourtDetail: (String) -> Unit
) {
    Scaffold(
        topBar = {
            BadmiTopBar(
                title = "Sân đấu yêu thích",
                subtitle = "Các sân bạn đã đánh dấu",
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
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            BadmiData.sampleCourts.filter { it.isFavorite }.forEach { court ->
                CourtSummaryCard(
                    court = court,
                    onClick = { onNavigateToCourtDetail(court.id) },
                    onBookClick = { onNavigateToCourtDetail(court.id) }
                )
            }
        }
    }
}

@Composable
fun VouchersScreen(onBackClick: () -> Unit) {
    Scaffold(
        topBar = {
            BadmiTopBar(
                title = "Mã ưu đãi của bạn",
                subtitle = "Áp dụng giảm tiền sân và thiết bị",
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
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            BadmiData.sampleVouchers.forEach { voucher ->
                BadmiCard {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            BadmiBadge(text = voucher.discountText)
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = voucher.title,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = BadmiTextPrimary
                            )
                            Text(
                                text = "Đơn tối thiểu: ${formatCurrency(voucher.minSpend)} • HSD: ${voucher.expiryDate}",
                                fontSize = 11.sp,
                                color = BadmiTextSecondary
                            )
                        }
                        Button(
                            onClick = {},
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = BadmiPrimary)
                        ) {
                            Text("Dùng ngay", fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun NotificationsScreen(onBackClick: () -> Unit) {
    Scaffold(
        topBar = {
            BadmiTopBar(
                title = "Thông báo",
                subtitle = "Có gì mới hôm nay?",
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
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            BadmiData.sampleNotifications.forEach { notif ->
                BadmiCard {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        BadmiBadge(text = notif.category)
                        Text(text = notif.timeAgo, fontSize = 11.sp, color = BadmiTextMuted)
                    }
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = notif.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = BadmiTextPrimary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = notif.message,
                        fontSize = 12.sp,
                        color = BadmiTextSecondary
                    )
                }
            }
        }
    }
}

@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
    onLogout: () -> Unit
) {
    var notifEnabled by remember { mutableStateOf(true) }
    var soundEnabled by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            BadmiTopBar(
                title = "Cài đặt & Hỗ trợ",
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
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            BadmiCard {
                Text("Thông báo & Âm thanh", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Thông báo nhắc giờ ra sân", fontSize = 13.sp)
                    Switch(
                        checked = notifEnabled,
                        onCheckedChange = { notifEnabled = it },
                        colors = SwitchDefaults.colors(checkedThumbColor = BadmiPrimary)
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Âm thanh hiệu ứng trong app", fontSize = 13.sp)
                    Switch(
                        checked = soundEnabled,
                        onCheckedChange = { soundEnabled = it },
                        colors = SwitchDefaults.colors(checkedThumbColor = BadmiPrimary)
                    )
                }
            }

            BadmiCard {
                Text("Hỗ trợ & Thông tin", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                InfoRow(label = "Hotline CSKH", value = "1900 1910 228")
                InfoRow(label = "Phiên bản ứng dụng", value = "BadmiSpot v2.4.0")
                InfoRow(label = "Điều khoản dịch vụ", value = "Xem điều khoản")
            }

            Spacer(modifier = Modifier.height(10.dp))
            BadmiOutlinedButton(
                text = "Đăng xuất tài khoản",
                borderColor = BadmiDanger,
                contentColor = BadmiDanger,
                onClick = onLogout
            )
        }
    }
}
