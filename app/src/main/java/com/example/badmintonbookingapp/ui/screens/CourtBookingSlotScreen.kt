package com.example.badmintonbookingapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
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
import com.example.badmintonbookingapp.model.Court
import com.example.badmintonbookingapp.ui.components.*
import com.example.badmintonbookingapp.ui.theme.*

@Composable
fun CourtBookingSlotScreen(
    courtId: String,
    onBackClick: () -> Unit,
    onProceedToSummary: (courtId: String, selectedSlotsCount: Int, totalPrice: Int) -> Unit
) {
    val court = BadmiData.sampleCourts.find { it.id == courtId } ?: BadmiData.sampleCourts[0]

    val days = listOf(
        Pair("T2", "20"),
        Pair("T3", "21"),
        Pair("T4", "22"),
        Pair("T5", "23"),
        Pair("T6", "24"),
        Pair("T7", "25"),
        Pair("CN", "26")
    )
    var selectedDayIndex by remember { mutableIntStateOf(2) } // T4 22

    val courtNumbers = listOf("Sân 01", "Sân 02", "Sân 03", "Sân 04")
    var selectedCourtNum by remember { mutableStateOf("Sân 02") }

    // Slot state
    var selectedSlots by remember {
        mutableStateOf(setOf("18:00 - 19:00", "19:00 - 20:00"))
    }

    val pricePerSlot = 120000
    val totalAmount = selectedSlots.size * pricePerSlot

    Scaffold(
        topBar = {
            BadmiTopBar(
                title = "Chọn giờ đặt sân",
                subtitle = court.name,
                onBackClick = onBackClick
            )
        },
        bottomBar = {
            Surface(
                color = BadmiSurface,
                shadowElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "${selectedSlots.size} giờ đã chọn",
                            fontSize = 12.sp,
                            color = BadmiTextSecondary
                        )
                        Text(
                            text = formatCurrency(totalAmount),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = BadmiPrimary
                        )
                    }

                    Button(
                        onClick = {
                            if (selectedSlots.isNotEmpty()) {
                                onProceedToSummary(court.id, selectedSlots.size, totalAmount)
                            }
                        },
                        enabled = selectedSlots.isNotEmpty(),
                        shape = RoundedCornerShape(25.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BadmiPrimary,
                            contentColor = BadmiTextWhite
                        ),
                        modifier = Modifier
                            .height(48.dp)
                            .padding(start = 16.dp)
                    ) {
                        Text(
                            text = "Tiếp tục đặt sân →",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
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
                .padding(20.dp)
        ) {
            // Court Mini Header Card
            BadmiCard {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    BadmintonRacketIconPlaceholder(modifier = Modifier.size(50.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = court.name,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = BadmiTextPrimary
                        )
                        Text(
                            text = court.address,
                            fontSize = 12.sp,
                            color = BadmiTextSecondary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Date Picker Row
            Text(
                text = "Chọn ngày ra sân",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = BadmiTextPrimary
            )
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                days.forEachIndexed { index, pair ->
                    val isSelected = selectedDayIndex == index
                    Column(
                        modifier = Modifier
                            .width(52.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (isSelected) BadmiPrimary else BadmiSurface)
                            .border(
                                1.dp,
                                if (isSelected) BadmiPrimary else BadmiBorder,
                                RoundedCornerShape(16.dp)
                            )
                            .clickable { selectedDayIndex = index }
                            .padding(vertical = 12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = pair.first,
                            fontSize = 12.sp,
                            color = if (isSelected) BadmiTextWhite.copy(alpha = 0.8f) else BadmiTextSecondary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = pair.second,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (isSelected) BadmiTextWhite else BadmiTextPrimary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Court Number Selector
            Text(
                text = "Chọn sân",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = BadmiTextPrimary
            )
            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                courtNumbers.forEach { cNum ->
                    BadmiChip(
                        text = cNum,
                        isSelected = selectedCourtNum == cNum,
                        onClick = { selectedCourtNum = cNum },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Legend Indicator
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                LegendItem(color = BadmiSurface, border = BadmiBorder, label = "Trống")
                LegendItem(color = BadmiPrimary, border = null, label = "Đang chọn")
                LegendItem(color = BadmiBorder, border = null, label = "Đã đặt")
            }

            Spacer(modifier = Modifier.height(18.dp))

            // Time Slots Grid
            Text(
                text = "Khung giờ trong ngày ($selectedCourtNum)",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = BadmiTextPrimary
            )
            Spacer(modifier = Modifier.height(12.dp))

            val allSlots = BadmiData.sampleTimeSlots

            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                allSlots.chunked(2).forEach { rowSlots ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        rowSlots.forEach { slot ->
                            val isSelected = selectedSlots.contains(slot.time)
                            val isBooked = slot.isBooked

                            val bg = when {
                                isBooked -> BadmiSurfaceVariant
                                isSelected -> BadmiPrimary
                                else -> BadmiSurface
                            }
                            val textColor = when {
                                isBooked -> BadmiTextMuted
                                isSelected -> BadmiTextWhite
                                else -> BadmiTextPrimary
                            }

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(bg)
                                    .border(
                                        1.dp,
                                        if (isSelected) BadmiPrimary else BadmiBorder,
                                        RoundedCornerShape(14.dp)
                                    )
                                    .clickable(enabled = !isBooked) {
                                        selectedSlots = if (isSelected) {
                                            selectedSlots - slot.time
                                        } else {
                                            selectedSlots + slot.time
                                        }
                                    }
                                    .padding(vertical = 12.dp, horizontal = 10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = slot.time,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = textColor
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = if (isBooked) "Đã kín" else formatCurrency(pricePerSlot),
                                        fontSize = 11.sp,
                                        color = if (isSelected) BadmiTextWhite.copy(alpha = 0.85f) else BadmiTextSecondary
                                    )
                                }
                            }
                        }
                        if (rowSlots.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LegendItem(color: Color, border: Color?, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(color, RoundedCornerShape(4.dp))
                .then(
                    if (border != null) Modifier.border(1.dp, border, RoundedCornerShape(4.dp))
                    else Modifier
                )
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = label,
            fontSize = 12.sp,
            color = BadmiTextSecondary
        )
    }
}

@Composable
fun BookingSummaryScreen(
    courtId: String,
    selectedHours: Int,
    rawAmount: Int,
    onBackClick: () -> Unit,
    onProceedToPayment: () -> Unit
) {
    val court = BadmiData.sampleCourts.find { it.id == courtId } ?: BadmiData.sampleCourts[0]
    val discount = 20000
    val finalTotal = (rawAmount - discount).coerceAtLeast(0)

    Scaffold(
        topBar = {
            BadmiTopBar(
                title = "Xác nhận đặt sân",
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Tổng thanh toán",
                            fontSize = 14.sp,
                            color = BadmiTextSecondary
                        )
                        Text(
                            text = formatCurrency(finalTotal),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = BadmiPrimary
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    BadmiButton(
                        text = "Chọn phương thức thanh toán →",
                        onClick = onProceedToPayment
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Booking Info Card
            BadmiCard {
                Text(
                    text = "Thông tin sân & lịch hẹn",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = BadmiTextPrimary
                )
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = BadmiBorderLight)
                Spacer(modifier = Modifier.height(12.dp))

                InfoRow(label = "Địa điểm", value = court.name)
                InfoRow(label = "Địa chỉ", value = court.address)
                InfoRow(label = "Sân đấu", value = "Sân 02 (Thảm tiêu chuẩn BWF)")
                InfoRow(label = "Thời gian", value = "18:00 - 20:00 ($selectedHours tiếng)")
                InfoRow(label = "Ngày chơi", value = "Thứ Tư, 22/10/2026")
            }

            // Customer Contact
            BadmiCard {
                Text(
                    text = "Thông tin người đặt",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = BadmiTextPrimary
                )
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = BadmiBorderLight)
                Spacer(modifier = Modifier.height(12.dp))

                InfoRow(label = "Họ tên", value = "Nguyễn Minh")
                InfoRow(label = "Số điện thoại", value = "0908 190 228")
                InfoRow(label = "Email", value = "minh.badmi@gmail.com")
            }

            // Voucher Card
            BadmiCard {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.ConfirmationNumber,
                            contentDescription = null,
                            tint = BadmiPrimary,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Mã giảm giá BADMI20K",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = BadmiTextPrimary
                        )
                    }
                    Text(
                        text = "-20.000 đ",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = BadmiPrimary
                    )
                }
            }

            // Price Details
            BadmiCard {
                Text(
                    text = "Chi tiết thanh toán",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = BadmiTextPrimary
                )
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = BadmiBorderLight)
                Spacer(modifier = Modifier.height(12.dp))

                InfoRow(label = "Tiền thuê sân ($selectedHours giờ)", value = formatCurrency(rawAmount))
                InfoRow(label = "Phí dịch vụ", value = "Miễn phí")
                InfoRow(label = "Khuyến mãi voucher", value = "-${formatCurrency(discount)}", valueColor = BadmiPrimary)
                Spacer(modifier = Modifier.height(4.dp))
                HorizontalDivider(color = BadmiBorderLight)
                Spacer(modifier = Modifier.height(8.dp))
                InfoRow(
                    label = "Tổng cộng",
                    value = formatCurrency(finalTotal),
                    isBold = true,
                    valueColor = BadmiPrimary
                )
            }
        }
    }
}

@Composable
fun InfoRow(
    label: String,
    value: String,
    isBold: Boolean = false,
    valueColor: Color = BadmiTextPrimary
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            color = BadmiTextSecondary
        )
        Text(
            text = value,
            fontSize = 13.sp,
            fontWeight = if (isBold) FontWeight.Bold else FontWeight.Medium,
            color = valueColor,
            textAlign = TextAlign.End,
            modifier = Modifier.fillMaxWidth(0.65f)
        )
    }
}
