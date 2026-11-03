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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.badmintonbookingapp.ui.components.*
import com.example.badmintonbookingapp.ui.theme.*

@Composable
fun PaymentMethodScreen(
    totalAmount: Int = 220000,
    onBackClick: () -> Unit,
    onProceedToQr: () -> Unit
) {
    var selectedMethod by remember { mutableStateOf("qr_bank") }

    Scaffold(
        topBar = {
            BadmiTopBar(
                title = "Phương thức thanh toán",
                subtitle = "Thanh toán an toàn & bảo mật",
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
                            text = "Số tiền thanh toán",
                            fontSize = 14.sp,
                            color = BadmiTextSecondary
                        )
                        Text(
                            text = formatCurrency(totalAmount),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = BadmiPrimary
                        )
                    }
                    Spacer(modifier = Modifier.height(14.dp))
                    BadmiButton(
                        text = "Tiến hành thanh toán →",
                        onClick = onProceedToQr
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
                text = "Thanh toán điện tử (Khuyên dùng)",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = BadmiTextPrimary
            )

            PaymentOptionCard(
                title = "Chuyển khoản VietQR 24/7",
                subtitle = "Tự động kích hoạt vé sau 3 giây",
                icon = Icons.Default.QrCodeScanner,
                iconTint = BadmiPrimary,
                isSelected = selectedMethod == "qr_bank",
                onClick = { selectedMethod = "qr_bank" }
            )

            PaymentOptionCard(
                title = "Ví điện tử MoMo",
                subtitle = "Liên kết ví MoMo thanh toán 1 chạm",
                icon = Icons.Default.AccountBalanceWallet,
                iconTint = Color(0xFFA50064),
                isSelected = selectedMethod == "momo",
                onClick = { selectedMethod = "momo" }
            )

            PaymentOptionCard(
                title = "Cổng thanh toán VNPay",
                subtitle = "Hỗ trợ thẻ ATM & ứng dụng Mobile Banking",
                icon = Icons.Default.CreditCard,
                iconTint = BadmiBlue,
                isSelected = selectedMethod == "vnpay",
                onClick = { selectedMethod = "vnpay" }
            )

            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Thanh toán trực tiếp",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = BadmiTextPrimary
            )

            PaymentOptionCard(
                title = "Thanh toán tại quầy lễ tân sân",
                subtitle = "Giữ chỗ tối đa 30 phút trước giờ bắt đầu",
                icon = Icons.Default.Storefront,
                iconTint = BadmiOrange,
                isSelected = selectedMethod == "cash",
                onClick = { selectedMethod = "cash" }
            )
        }
    }
}

@Composable
fun PaymentOptionCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    iconTint: Color,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .border(
                width = if (isSelected) 1.8.dp else 1.dp,
                color = if (isSelected) BadmiPrimary else BadmiBorder,
                shape = RoundedCornerShape(16.dp)
            ),
        color = if (isSelected) BadmiPrimaryLight.copy(alpha = 0.3f) else BadmiSurface,
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .background(BadmiSurfaceVariant, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(24.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = BadmiTextPrimary
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    fontSize = 11.sp,
                    color = BadmiTextSecondary
                )
            }

            RadioButton(
                selected = isSelected,
                onClick = onClick,
                colors = RadioButtonDefaults.colors(
                    selectedColor = BadmiPrimary,
                    unselectedColor = BadmiTextMuted
                )
            )
        }
    }
}

@Composable
fun PaymentQrScreen(
    amount: Int = 220000,
    onBackClick: () -> Unit,
    onPaymentSuccess: () -> Unit
) {
    Scaffold(
        topBar = {
            BadmiTopBar(
                title = "Thanh toán QR",
                subtitle = "Quét mã để hoàn tất lịch hẹn",
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
                    BadmiButton(
                        text = "Tôi đã chuyển khoản thành công",
                        onClick = onPaymentSuccess
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    BadmiOutlinedButton(
                        text = "Hủy giao dịch",
                        onClick = onBackClick
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                color = BadmiOrangeLight,
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Timer,
                        contentDescription = null,
                        tint = BadmiOrange,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Mã QR hết hạn sau: 14:59",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = BadmiOrange
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // QR Code Box
            BadmiCard(modifier = Modifier.fillMaxWidth(0.92f)) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Số tiền thanh toán",
                        fontSize = 13.sp,
                        color = BadmiTextSecondary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = formatCurrency(amount),
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = BadmiPrimary
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // QR Graphic mockup
                    Box(
                        modifier = Modifier
                            .size(200.dp)
                            .background(BadmiSurfaceVariant, RoundedCornerShape(16.dp))
                            .border(2.dp, BadmiPrimaryLight, RoundedCornerShape(16.dp))
                            .padding(12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.QrCode,
                            contentDescription = "QR Code",
                            tint = BadmiPrimaryDark,
                            modifier = Modifier.fillMaxSize()
                        )
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .background(BadmiSurface, CircleShape)
                                .border(1.dp, BadmiPrimary, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.SportsTennis,
                                contentDescription = null,
                                tint = BadmiPrimary,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Ngân hàng thụ hưởng: MBBank",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = BadmiTextPrimary
                    )
                    Text(
                        text = "Số tài khoản: 1908 190 228",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = BadmiPrimary
                    )
                    Text(
                        text = "Tên chủ TK: BADMISPOT JSC",
                        fontSize = 12.sp,
                        color = BadmiTextSecondary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "Nội dung: BMSP8823 MINH",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Medium,
                        color = BadmiTextPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Hệ thống tự động kiểm tra và chuyển tiếp ngay khi tiền vào tài khoản.",
                fontSize = 12.sp,
                color = BadmiTextMuted,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 20.dp)
            )
        }
    }
}

@Composable
fun BookingSuccessScreen(
    onViewBookingDetail: () -> Unit,
    onBackToHome: () -> Unit
) {
    Scaffold(
        bottomBar = {
            Surface(
                color = BadmiSurface,
                shadowElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    BadmiButton(
                        text = "Xem chi tiết lịch đặt →",
                        onClick = onViewBookingDetail
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    BadmiOutlinedButton(
                        text = "Về trang chủ",
                        onClick = onBackToHome
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            // Success animated badge
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .background(BadmiPrimaryLight, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Thành công",
                    tint = BadmiPrimary,
                    modifier = Modifier.size(54.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Đặt sân thành công!",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = BadmiTextPrimary
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Lịch hẹn ra sân của bạn đã được lưu vào hệ thống",
                fontSize = 13.sp,
                color = BadmiTextSecondary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Ticket Presentation Card
            BadmiCard(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    BadmiBadge(
                        text = "MÃ ĐẶT SÂN: BMSP-8823",
                        containerColor = BadmiPrimaryLight,
                        contentColor = BadmiPrimaryDark
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = "BadmiSpot Club Bình Thạnh",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = BadmiTextPrimary
                    )
                    Text(
                        text = "Sân 02 (Thảm BWF) • 18:00 - 20:00",
                        fontSize = 13.sp,
                        color = BadmiTextSecondary
                    )
                    Text(
                        text = "Thứ Tư, 22/10/2026",
                        fontSize = 12.sp,
                        color = BadmiTextMuted
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // QR Code for check-in
                    Box(
                        modifier = Modifier
                            .size(140.dp)
                            .background(BadmiSurfaceVariant, RoundedCornerShape(12.dp))
                            .padding(8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.QrCode2,
                            contentDescription = "Check-in QR",
                            tint = BadmiPrimaryDark,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = "Đưa mã QR này cho lễ tân tại sân để check-in",
                        fontSize = 11.sp,
                        color = BadmiTextSecondary
                    )
                }
            }
        }
    }
}
