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
import com.example.badmintonbookingapp.data.BadmiData
import com.example.badmintonbookingapp.model.BookingItem
import com.example.badmintonbookingapp.ui.components.*
import com.example.badmintonbookingapp.ui.theme.*

@Composable
fun MyBookingsScreen(
    onNavigateToDetail: (String) -> Unit,
    onNavigateToCancel: (String) -> Unit
) {
    val tabs = listOf("Sắp tới", "Đã hoàn thành", "Đã hủy")
    var selectedTabIndex by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BadmiBackground)
            .padding(bottom = 80.dp)
    ) {
        BadmiTopBar(
            title = "Lịch hẹn ra sân",
            subtitle = "Quản lý lượt đặt & vé check-in",
            showBack = false
        )

        // Tab Selector Row
        SecondaryTabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = BadmiSurface,
            contentColor = BadmiPrimary,
            indicator = {
                TabRowDefaults.SecondaryIndicator(
                    modifier = Modifier.tabIndicatorOffset(selectedTabIndex),
                    color = BadmiPrimary
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = {
                        Text(
                            text = title,
                            fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 13.sp
                        )
                    }
                )
            }
        }

        val filteredBookings = when (selectedTabIndex) {
            0 -> BadmiData.sampleBookings.filter { it.status == "Sắp tới" }
            1 -> BadmiData.sampleBookings.filter { it.status == "Đã hoàn thành" }
            else -> BadmiData.sampleBookings.filter { it.status == "Đã hủy" }
        }

        if (filteredBookings.isEmpty()) {
            EmptyStateView(
                title = "Chưa có lịch đặt nào",
                subtitle = "Bạn chưa có lịch hẹn ở mục này. Hãy tìm sân và đặt lịch ngay nhé!"
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                filteredBookings.forEach { booking ->
                    BookingItemCard(
                        booking = booking,
                        onViewDetail = { onNavigateToDetail(booking.id) },
                        onCancel = { onNavigateToCancel(booking.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun BookingItemCard(
    booking: BookingItem,
    onViewDetail: () -> Unit,
    onCancel: () -> Unit
) {
    val statusColor = when (booking.status) {
        "Sắp tới" -> BadmiPrimary
        "Đã hoàn thành" -> BadmiMint
        else -> BadmiDanger
    }
    val statusBg = when (booking.status) {
        "Sắp tới" -> BadmiPrimaryLight
        "Đã hoàn thành" -> BadmiMintSoft
        else -> BadmiDangerLight
    }

    BadmiCard(onClick = onViewDetail) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Mã: ${booking.bookingCode}",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = BadmiTextPrimary
            )
            BadmiBadge(
                text = booking.status,
                containerColor = statusBg,
                contentColor = statusColor
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = booking.courtName,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = BadmiTextPrimary
        )
        Text(
            text = "${booking.courtNumber} • ${booking.address}",
            fontSize = 12.sp,
            color = BadmiTextSecondary
        )

        Spacer(modifier = Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.Event,
                contentDescription = null,
                tint = BadmiPrimary,
                modifier = Modifier.size(15.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "${booking.date} (${booking.timeSlot})",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = BadmiTextPrimary
            )
        }

        Spacer(modifier = Modifier.height(12.dp))
        HorizontalDivider(color = BadmiBorderLight)
        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = formatCurrency(booking.totalPrice),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = BadmiPrimary
            )

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                if (booking.status == "Sắp tới") {
                    TextButton(
                        onClick = onCancel,
                        colors = ButtonDefaults.textButtonColors(contentColor = BadmiDanger)
                    ) {
                        Text("Hủy lịch", fontSize = 12.sp)
                    }

                    Button(
                        onClick = onViewDetail,
                        shape = RoundedCornerShape(16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BadmiPrimary,
                            contentColor = BadmiTextWhite
                        ),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.QrCode,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Check-in QR", fontSize = 12.sp)
                        }
                    }
                } else {
                    OutlinedButton(
                        onClick = onViewDetail,
                        shape = RoundedCornerShape(16.dp),
                        contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                    ) {
                        Text("Chi tiết vé", fontSize = 12.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun BookingDetailQrScreen(
    bookingId: String,
    onBackClick: () -> Unit,
    onCancelClick: () -> Unit
) {
    val booking = BadmiData.sampleBookings.find { it.id == bookingId } ?: BadmiData.sampleBookings[0]

    Scaffold(
        topBar = {
            BadmiTopBar(
                title = "Chi tiết lịch đặt",
                subtitle = "Vé vào sân điện tử",
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Main Ticket Pass
            BadmiCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BadmiBadge(
                        text = "VÉ ĐÃ THANH TOÁN TOÀN BỘ",
                        containerColor = BadmiPrimaryLight,
                        contentColor = BadmiPrimaryDark
                    )

                    Spacer(modifier = Modifier.height(14.dp))
                    Text(
                        text = booking.courtName,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = BadmiTextPrimary,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = booking.courtNumber,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = BadmiPrimary
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    // QR Code
                    Box(
                        modifier = Modifier
                            .size(190.dp)
                            .background(BadmiSurfaceVariant, RoundedCornerShape(16.dp))
                            .border(1.dp, BadmiBorder, RoundedCornerShape(16.dp))
                            .padding(14.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.QrCode2,
                            contentDescription = "QR Code Checkin",
                            tint = BadmiPrimaryDark,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = booking.qrCode,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = BadmiTextPrimary
                    )
                    Text(
                        text = "Quét tại quầy soát vé để nhận sân",
                        fontSize = 12.sp,
                        color = BadmiTextSecondary
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider(color = BadmiBorderLight)
                    Spacer(modifier = Modifier.height(14.dp))

                    InfoRow(label = "Thời gian", value = booking.timeSlot)
                    InfoRow(label = "Ngày", value = booking.date)
                    InfoRow(label = "Địa chỉ", value = booking.address)
                    InfoRow(label = "Tổng tiền", value = formatCurrency(booking.totalPrice), isBold = true, valueColor = BadmiPrimary)
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Action Buttons
            BadmiButton(
                text = "Xem chỉ đường tới sân",
                leadingIcon = Icons.Default.Navigation,
                onClick = {}
            )

            Spacer(modifier = Modifier.height(12.dp))

            if (booking.status == "Sắp tới") {
                TextButton(onClick = onCancelClick) {
                    Text(
                        text = "Bạn muốn hủy lịch hẹn này?",
                        color = BadmiDanger,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

@Composable
fun CancelBookingDialog(
    bookingId: String,
    onDismiss: () -> Unit,
    onConfirmCancel: () -> Unit
) {
    var selectedReason by remember { mutableStateOf("Bận việc đột xuất") }
    val reasons = listOf(
        "Bận việc đột xuất không sắp xếp được",
        "Thay đổi địa điểm chơi khác",
        "Đặt nhầm khung giờ",
        "Lý do thời tiết / sức khỏe",
        "Lý do khác"
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Hủy lịch hẹn ra sân",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = BadmiTextPrimary
            )
        },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text(
                    text = "Chính sách hoàn tiền: Hủy trước 4 tiếng được hoàn 100% vào số dư ví.",
                    fontSize = 12.sp,
                    color = BadmiTextSecondary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Vui lòng chọn lý do hủy:",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = BadmiTextPrimary
                )

                reasons.forEach { reason ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedReason = reason }
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = selectedReason == reason,
                            onClick = { selectedReason = reason },
                            colors = RadioButtonDefaults.colors(selectedColor = BadmiPrimary)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = reason,
                            fontSize = 12.sp,
                            color = BadmiTextPrimary
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = onConfirmCancel,
                colors = ButtonDefaults.buttonColors(containerColor = BadmiDanger)
            ) {
                Text("Xác nhận hủy lịch", color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Giữ lại lịch", color = BadmiTextSecondary)
            }
        }
    )
}

@Composable
fun EmptyStateView(title: String, subtitle: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(70.dp)
                .background(BadmiPrimaryLight, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.EventBusy,
                contentDescription = null,
                tint = BadmiPrimary,
                modifier = Modifier.size(36.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = BadmiTextPrimary
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = subtitle,
            fontSize = 13.sp,
            color = BadmiTextSecondary,
            textAlign = TextAlign.Center
        )
    }
}
