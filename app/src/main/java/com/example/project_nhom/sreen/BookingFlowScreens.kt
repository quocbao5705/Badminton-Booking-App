package com.example.project_nhom.sreen

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.SportsTennis
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

// Màu cho màn hình mới
private val LightGreenBg = Color(0xFFE8F5E9)

// ==========================================
// 1. MÀN HÌNH XÁC NHẬN ĐẶT SÂN
// ==========================================
@Composable
fun ConfirmBookingScreen(
    onBack: () -> Unit = {},
    onContinue: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize().background(Background).statusBarsPadding()
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back",
                modifier = Modifier.size(28.dp).clip(CircleShape).clickable { onBack() }, tint = TextDark
            )
            Spacer(Modifier.width(16.dp))
            Text("Xác nhận đặt sân", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextDark)
            Spacer(Modifier.weight(1f))
            Icon(Icons.Filled.MoreHoriz, contentDescription = "More", tint = TextGrey)
        }

        Column(
            modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = 20.dp)
        ) {
            // Thẻ thông tin sân
            Column(
                modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(Color.White).padding(16.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Box(Modifier.clip(RoundedCornerShape(50)).background(LightGreenBg).padding(horizontal = 10.dp, vertical = 4.dp)) {
                        Text("Đã xác nhận", fontSize = 11.sp, fontWeight = FontWeight.SemiBold, color = AccentGreen)
                    }
                    Text("BS191026", fontSize = 11.sp, color = TextGrey)
                }
                Spacer(Modifier.height(12.dp))
                Text("BadmiSpot Club", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextDark)
                Spacer(Modifier.height(4.dp))
                Text("Bình Thạnh · Sân 02", fontSize = 13.sp, color = TextGrey)
                Spacer(Modifier.height(12.dp))
                HorizontalDivider(color = BorderGrey, thickness = 1.dp)
                Spacer(Modifier.height(12.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                    Text("Thứ 2, 19/10/2026", fontSize = 14.sp, color = TextDark)
                    Text("18:00–20:00", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = DarkGreen)
                }
            }

            Spacer(Modifier.height(12.dp))
            Text("Thời lượng: 2 giờ · 120k đ / giờ", fontSize = 13.sp, color = TextGrey)

            Spacer(Modifier.height(20.dp))

            // Chi tiết giá
            Column(
                modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(Color.White).padding(16.dp)
            ) {
                Text("Chi tiết giá", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextDark)
                Spacer(Modifier.height(12.dp))
                PriceRow("Tiền sân", "240k đ")
                PriceRow("Phí dịch vụ", "5k đ")
                PriceRow("Mã giảm giá", "-20k đ")
                Spacer(Modifier.height(8.dp))
                HorizontalDivider(color = BorderGrey, thickness = 1.dp)
                Spacer(Modifier.height(8.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text("Tổng cộng", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextDark)
                    Text("225k đ", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = DarkGreen)
                }
            }

            Spacer(Modifier.height(20.dp))

            // Tuỳ chọn thêm
            Column(
                modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(Color.White)
            ) {
                Row(modifier = Modifier.fillMaxWidth().clickable { }.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.SportsTennis, contentDescription = null, tint = DarkGreen)
                    Spacer(Modifier.width(12.dp))
                    Text("Thêm vợt / ống cầu", fontSize = 14.sp, color = TextDark, modifier = Modifier.weight(1f))
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = TextGrey)
                }
                HorizontalDivider(color = BorderGrey, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
                Row(modifier = Modifier.fillMaxWidth().clickable { }.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.Security, contentDescription = null, tint = DarkGreen)
                    Spacer(Modifier.width(12.dp))
                    Text("Chính sách hủy sân", fontSize = 14.sp, color = TextDark, modifier = Modifier.weight(1f))
                    Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = TextGrey)
                }
            }
        }

        Button(
            onClick = onContinue,
            modifier = Modifier.fillMaxWidth().padding(20.dp).height(56.dp).navigationBarsPadding(),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DarkGreen)
        ) {
            Text("Tiếp tục thanh toán", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun PriceRow(label: String, price: String) {
    Row(modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, fontSize = 14.sp, color = TextGrey)
        Text(price, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = TextDark)
    }
}

// ==========================================
// 2. MÀN HÌNH PHƯƠNG THỨC THANH TOÁN
// ==========================================
@Composable
fun PaymentMethodScreen(
    onBack: () -> Unit = {},
    onContinue: () -> Unit = {}
) {
    var selectedMethod by remember { mutableStateOf("MoMo") }

    Column(
        modifier = Modifier.fillMaxSize().background(Background).statusBarsPadding()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", modifier = Modifier.size(28.dp).clip(CircleShape).clickable { onBack() }, tint = TextDark)
            Spacer(Modifier.width(16.dp))
            Text("Thanh toán", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextDark)
            Spacer(Modifier.weight(1f))
            Icon(Icons.Filled.MoreHoriz, contentDescription = "More", tint = TextGrey)
        }

        Column(
            modifier = Modifier.weight(1f).verticalScroll(rememberScrollState()).padding(horizontal = 20.dp)
        ) {
            Text("Thanh toán an tâm.", fontSize = 28.sp, fontWeight = FontWeight.ExtraBold, color = TextDark)
            Spacer(Modifier.height(20.dp))

            Column(modifier = Modifier.clip(RoundedCornerShape(16.dp)).background(Color.White)) {
                val methods = listOf("MoMo", "ZaloPay", "VNPay QR", "Chuyển khoản ngân hàng", "Thẻ đã lưu")
                methods.forEachIndexed { index, method ->
                    Row(
                        modifier = Modifier.fillMaxWidth().clickable { selectedMethod = method }.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Outlined.AccountBalanceWallet, contentDescription = null, tint = DarkGreen, modifier = Modifier.size(20.dp))
                        Spacer(Modifier.width(12.dp))
                        Text(method, fontSize = 15.sp, fontWeight = FontWeight.Medium, color = TextDark, modifier = Modifier.weight(1f))
                        RadioButton(
                            selected = selectedMethod == method,
                            onClick = { selectedMethod = method },
                            colors = RadioButtonDefaults.colors(selectedColor = AccentGreen)
                        )
                    }
                    if (index < methods.size - 1) HorizontalDivider(color = BorderGrey, thickness = 1.dp, modifier = Modifier.padding(horizontal = 16.dp))
                }
            }

            Spacer(Modifier.height(24.dp))
            Text("Nhập mã giảm giá", fontSize = 13.sp, color = TextDark, fontWeight = FontWeight.Medium)
            Spacer(Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                OutlinedTextField(
                    value = "BADMISPOT20",
                    onValueChange = {},
                    modifier = Modifier.weight(1f).height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    leadingIcon = { Icon(Icons.Outlined.SportsTennis, contentDescription = null, tint = AccentGreen, modifier = Modifier.size(18.dp)) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        focusedBorderColor = AccentGreen,
                        unfocusedBorderColor = BorderGrey
                    )
                )
                Spacer(Modifier.width(10.dp))
                Button(
                    onClick = {},
                    modifier = Modifier.height(52.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = LightGreenBg)
                ) {
                    Text("Áp dụng", color = AccentGreen, fontWeight = FontWeight.Bold)
                }
            }

            Spacer(Modifier.height(8.dp))
            Text("Đã áp dụng · Bạn tiết kiệm 20k đ", fontSize = 12.sp, color = AccentGreen)

            Spacer(Modifier.height(24.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Text("Tổng thanh toán", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = TextDark)
                Text("225k đ", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = DarkGreen)
            }
        }

        Button(
            onClick = onContinue,
            modifier = Modifier.fillMaxWidth().padding(20.dp).height(56.dp).navigationBarsPadding(),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DarkGreen)
        ) {
            Text("Thanh toán 225k đ", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

// ==========================================
// 3. MÀN HÌNH THANH TOÁN QR
// ==========================================
@Composable
fun PaymentQRScreen(
    onBack: () -> Unit = {},
    onSuccess: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize().background(Background).statusBarsPadding().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", modifier = Modifier.size(28.dp).clip(CircleShape).clickable { onBack() }, tint = TextDark)
            Spacer(Modifier.width(16.dp))
            Text("Thanh toán QR", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextDark)
            Spacer(Modifier.weight(1f))
            Icon(Icons.Filled.MoreHoriz, contentDescription = "More", tint = TextGrey)
        }

        Spacer(Modifier.height(32.dp))
        Text("Quét mã để thanh toán", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = TextDark, textAlign = TextAlign.Center)
        Spacer(Modifier.height(24.dp))

        Column(
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(20.dp)).background(Color.White).padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("225.000 đ", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = DarkGreen)
            Spacer(Modifier.height(20.dp))

            // Khung QR giả lập
            Box(modifier = Modifier.size(200.dp).border(1.dp, BorderGrey, RoundedCornerShape(12.dp)).padding(12.dp)) {
                // Vẽ một mã QR giả lập bằng các ô vuông (để demo)
                Icon(Icons.Filled.Check, contentDescription = null, tint = DarkGreen, modifier = Modifier.fillMaxSize())
            }

            Spacer(Modifier.height(20.dp))
            Text("BadmiSpot Club · Vietcombank", fontSize = 13.sp, color = TextGrey)
            Text("1026 1910 225", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextDark)
            Spacer(Modifier.height(12.dp))
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { }) {
                Icon(Icons.Filled.ContentCopy, contentDescription = null, tint = AccentGreen, modifier = Modifier.size(16.dp))
                Spacer(Modifier.width(6.dp))
                Text("Sao chép số tài khoản", fontSize = 13.sp, color = AccentGreen, fontWeight = FontWeight.Medium)
            }
        }

        Spacer(Modifier.height(24.dp))

        // Cảnh báo thời gian
        Row(
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp)).background(LightGreenBg).padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(Icons.Filled.Schedule, contentDescription = null, tint = AccentGreen, modifier = Modifier.size(16.dp))
            Spacer(Modifier.width(8.dp))
            Text("Hoàn tất trong 10:00", fontSize = 13.sp, fontWeight = FontWeight.Medium, color = AccentGreen)
        }
        Spacer(Modifier.height(8.dp))
        Text("Mã chỉ được hiển thị một lần", fontSize = 11.sp, color = TextGrey)

        Spacer(Modifier.weight(1f))

        Button(
            onClick = onSuccess,
            modifier = Modifier.fillMaxWidth().height(56.dp).navigationBarsPadding(),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DarkGreen)
        ) {
            Text("Tôi đã thanh toán", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

// ==========================================
// 4. MÀN HÌNH ĐẶT SÂN THÀNH CÔNG
// ==========================================
@Composable
fun BookingSuccessScreen(
    onBack: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize().background(Background).statusBarsPadding().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", modifier = Modifier.size(28.dp).clip(CircleShape).clickable { onBack() }, tint = TextDark)
            Spacer(Modifier.width(16.dp))
            Text("Lịch hẹn ra sân", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = TextDark)
            Spacer(Modifier.weight(1f))
            Icon(Icons.Filled.MoreHoriz, contentDescription = "More", tint = TextGrey)
        }

        Spacer(Modifier.height(24.dp))

        Box(modifier = Modifier.size(48.dp).clip(CircleShape).border(1.5.dp,
            AccentGreen, CircleShape), contentAlignment = Alignment.Center) {
            Icon(Icons.Filled.Check, contentDescription = null, tint = AccentGreen, modifier = Modifier.size(24.dp))
        }

        Spacer(Modifier.height(16.dp))
        Text("Đặt sân thành công!", fontSize = 24.sp, fontWeight = FontWeight.ExtraBold, color = TextDark)
        Spacer(Modifier.height(8.dp))
        Text("Hẹn bạn trên sân lúc 18:00 nhé.", fontSize = 14.sp, color = TextGrey)

        Spacer(Modifier.height(24.dp))

        Column(
            modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(16.dp)).background(Color.White).padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("BadmiSpot Club · Sân 02", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextDark)
            Spacer(Modifier.height(4.dp))
            Text("Thứ 2, 19/10/2026 · 18:00–20:00", fontSize = 13.sp, color = TextGrey)
            Spacer(Modifier.height(20.dp))

            Box(modifier = Modifier.size(140.dp)) {
                Icon(Icons.Filled.Check, contentDescription = null, tint = DarkGreen, modifier = Modifier.fillMaxSize())
            }

            Spacer(Modifier.height(16.dp))
            Text("Đưa mã này cho nhân viên sân", fontSize = 12.sp, color = TextGrey)
            Spacer(Modifier.height(16.dp))
            HorizontalDivider(color = BorderGrey, thickness = 1.dp)
            Spacer(Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text("Đã thanh toán", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = TextDark)
                Text("225k đ", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = DarkGreen)
            }
        }

        Spacer(Modifier.weight(1f))

        OutlinedButton(
            onClick = {},
            modifier = Modifier.fillMaxWidth().height(52.dp),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, BorderGrey)
        ) {
            Icon(Icons.Filled.CalendarMonth, contentDescription = null, tint = DarkGreen, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(8.dp))
            Text("Thêm vào lịch", color = DarkGreen, fontWeight = FontWeight.Bold)
        }
        Spacer(Modifier.height(12.dp))
        Button(
            onClick = onBack,
            modifier = Modifier.fillMaxWidth().height(56.dp).navigationBarsPadding(),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DarkGreen)
        ) {
            Text("Mời bạn cùng chơi", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
    }
}

// ==========================================
// PREVIEWS FOR ANDROID STUDIO
// ==========================================
@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun ConfirmBookingScreenPreview() {
    MaterialTheme { ConfirmBookingScreen() }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun PaymentMethodScreenPreview() {
    MaterialTheme { PaymentMethodScreen() }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun PaymentQRScreenPreview() {
    MaterialTheme { PaymentQRScreen() }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun BookingSuccessScreenPreview() {
    MaterialTheme { BookingSuccessScreen() }
}