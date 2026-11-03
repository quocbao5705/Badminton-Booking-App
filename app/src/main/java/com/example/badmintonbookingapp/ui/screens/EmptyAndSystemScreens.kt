package com.example.badmintonbookingapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.badmintonbookingapp.ui.components.*
import com.example.badmintonbookingapp.ui.theme.*

@Composable
fun EmptyScheduleScreen(onActionClick: () -> Unit) {
    EmptyStateTemplate(
        icon = Icons.Outlined.CalendarMonth,
        title = "Chưa có lịch hẹn ra sân",
        description = "Bạn chưa đặt khung giờ nào sắp tới. Hãy chọn sân gần bạn và đặt ngay nhé!",
        buttonText = "Tìm và đặt sân ngay",
        onButtonClick = onActionClick
    )
}

@Composable
fun EmptyMatchesScreen(onActionClick: () -> Unit) {
    EmptyStateTemplate(
        icon = Icons.Outlined.Groups,
        title = "Chưa có trận nào gần bạn",
        description = "Hiện tại chưa có kèo giao lưu nào quanh khu vực của bạn. Hãy là người đầu tiên tạo kèo!",
        buttonText = "Tạo kèo mới",
        onButtonClick = onActionClick
    )
}

@Composable
fun EmptyCartScreen(onActionClick: () -> Unit) {
    EmptyStateTemplate(
        icon = Icons.Outlined.RemoveShoppingCart,
        title = "Giỏ hàng đang trống",
        description = "Bạn chưa chọn sản phẩm hoặc dụng cụ cầu lông nào vào giỏ.",
        buttonText = "Khám phá cửa hàng",
        onButtonClick = onActionClick
    )
}

@Composable
fun NoInternetScreen(onRetry: () -> Unit) {
    EmptyStateTemplate(
        icon = Icons.Outlined.WifiOff,
        title = "Không có kết nối mạng",
        description = "Vui lòng kiểm tra lại Wifi hoặc dữ liệu di động (4G/5G) của bạn.",
        buttonText = "Thử lại kết nối",
        onButtonClick = onRetry
    )
}

@Composable
fun EmptyStateTemplate(
    icon: ImageVector,
    title: String,
    description: String,
    buttonText: String,
    onButtonClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BadmiBackground)
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(90.dp)
                .background(BadmiPrimaryLight, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = BadmiPrimary,
                modifier = Modifier.size(44.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = BadmiTextPrimary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = description,
            fontSize = 13.sp,
            color = BadmiTextSecondary,
            textAlign = TextAlign.Center,
            lineHeight = 18.sp
        )

        Spacer(modifier = Modifier.height(28.dp))

        BadmiButton(
            text = buttonText,
            onClick = onButtonClick,
            modifier = Modifier.fillMaxWidth(0.85f)
        )
    }
}

/**
 * Bảng tổng hợp UI · BadmiSpot (The Design System Showcase board directly matching the top left of Figma)
 */
@Composable
fun BadmiDesignSystemShowcaseScreen(
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            BadmiTopBar(
                title = "Hệ thống thiết kế · BadmiSpot",
                subtitle = "Bảng tổng hợp UI & Design Tokens",
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
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Brand Logo & Header
            BadmiCard {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .background(BadmiPrimary, RoundedCornerShape(14.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.SportsTennis,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(14.dp))
                    Column {
                        Text(
                            text = "BadmiSpot Design System",
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold,
                            color = BadmiPrimaryDark
                        )
                        Text(
                            text = "Tông màu ngọc bích & cấu trúc Figma tiêu chuẩn",
                            fontSize = 12.sp,
                            color = BadmiTextSecondary
                        )
                    }
                }
            }

            // Colors Palette
            Text(text = "1. Bảng màu thương hiệu (Brand Colors)", fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                ColorSwatch(color = BadmiPrimary, name = "Primary\n#0F6B4D", textColor = Color.White, modifier = Modifier.weight(1f))
                ColorSwatch(color = BadmiPrimaryDark, name = "Dark\n#094E37", textColor = Color.White, modifier = Modifier.weight(1f))
                ColorSwatch(color = BadmiMint, name = "Mint\n#2EC486", textColor = Color.White, modifier = Modifier.weight(1f))
                ColorSwatch(color = BadmiPrimaryLight, name = "Light\n#EAF5EF", textColor = BadmiPrimaryDark, modifier = Modifier.weight(1f))
            }

            // Buttons
            Text(text = "2. Nút bấm tương tác (Buttons)", fontWeight = FontWeight.Bold, fontSize = 15.sp)
            BadmiButton(text = "Nút chính (Primary CTA)", onClick = {})
            BadmiOutlinedButton(text = "Nút phụ (Outlined Button)", onClick = {})

            // Badges & Chips
            Text(text = "3. Thẻ trạng thái & Nhãn (Badges & Chips)", fontWeight = FontWeight.Bold, fontSize = 15.sp)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                BadmiBadge(text = "Sắp tới")
                BadmiBadge(text = "Đã hoàn thành", containerColor = BadmiMintSoft, contentColor = BadmiPrimaryDark)
                BadmiBadge(text = "Đã hủy", containerColor = BadmiDangerLight, contentColor = BadmiDanger)
                BadmiBadge(text = "4.9 ★", containerColor = BadmiOrangeLight, contentColor = BadmiOrange)
            }

            // Filter Chips
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                BadmiChip(text = "Đã chọn", isSelected = true, onClick = {})
                BadmiChip(text = "Chưa chọn", isSelected = false, onClick = {})
                BadmiChip(text = "Có lọc", isSelected = false, onClick = {}, icon = Icons.Default.FilterAlt)
            }

            // Typography Showcase
            Text(text = "4. Cấu trúc chữ (Typography Scale)", fontWeight = FontWeight.Bold, fontSize = 15.sp)
            BadmiCard {
                Text(text = "H1 Headline (24sp Bold)", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = BadmiTextPrimary)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "H2 Subtitle (18sp SemiBold)", fontSize = 18.sp, fontWeight = FontWeight.SemiBold, color = BadmiTextPrimary)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Body Text (14sp Regular) - Dễ đọc, độ tương phản cao", fontSize = 14.sp, color = BadmiTextSecondary)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = "Caption & Helper (11sp Medium)", fontSize = 11.sp, color = BadmiTextMuted)
            }
        }
    }
}

@Composable
fun ColorSwatch(
    color: Color,
    name: String,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(72.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(color)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = name,
            color = textColor,
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}
