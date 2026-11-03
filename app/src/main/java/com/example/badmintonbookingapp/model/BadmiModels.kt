package com.example.badmintonbookingapp.model

data class Court(
    val id: String,
    val name: String,
    val tag: String,
    val address: String,
    val district: String,
    val distanceKm: Double,
    val rating: Double,
    val reviewCount: Int,
    val pricePerHour: Int,
    val openingHours: String = "06:00 - 23:00",
    val totalCourts: Int = 8,
    val surfaceType: String = "Thảm tiêu chuẩn BWF",
    val amenities: List<String> = listOf("Bãi đỗ ô tô", "Máy lạnh", "Tắm nóng lạnh", "Căng vợt", "Wifi"),
    val isFavorite: Boolean = false,
    val imageUrl: String = ""
)

data class TimeSlot(
    val time: String,
    val courtName: String = "Sân 01",
    val isBooked: Boolean = false,
    val isSelected: Boolean = false,
    val price: Int = 120000
)

data class MatchItem(
    val id: String,
    val title: String,
    val courtName: String,
    val address: String,
    val date: String,
    val time: String,
    val format: String = "Đánh đôi",
    val skillLevel: String = "Trung bình+",
    val currentPlayers: Int = 3,
    val maxPlayers: Int = 4,
    val feePerPlayer: Int = 45000,
    val hostName: String = "Minh Tuấn",
    val hostAvatar: String = "",
    val description: String = "Tìm 1 bạn đánh đôi trình trung bình giao lưu vui vẻ, sau giờ làm xả stress.",
    val status: String = "Đang tìm người" // Đang tìm người, Sắp diễn ra, Đã hoàn tất
)

data class ProductItem(
    val id: String,
    val name: String,
    val category: String,
    val price: Int,
    val originalPrice: Int,
    val rating: Double,
    val soldCount: Int,
    val badge: String = "Bán chạy",
    val weightGrip: String = "4U/G5",
    val balancePoint: String = "Nặng đầu (Head Heavy)",
    val tension: String = "20 - 28 lbs",
    val description: String = "Dòng vợt tấn công linh hoạt, trợ lực tốt cho các pha đập cầu nhanh và chuẩn xác."
)

data class CartItem(
    val product: ProductItem,
    val quantity: Int = 1,
    val selectedWeight: String = "4U/G5"
)

data class BookingItem(
    val id: String,
    val bookingCode: String,
    val courtName: String,
    val courtNumber: String,
    val address: String,
    val date: String,
    val timeSlot: String,
    val durationHours: Int,
    val totalPrice: Int,
    val status: String, // "Sắp tới", "Đã hoàn thành", "Đã hủy"
    val qrCode: String = "BADMI-QR-8823"
)

data class VoucherItem(
    val code: String,
    val title: String,
    val discountText: String,
    val discountAmount: Int,
    val minSpend: Int,
    val expiryDate: String,
    val isSelected: Boolean = false
)

data class NotificationItem(
    val id: String,
    val title: String,
    val message: String,
    val timeAgo: String,
    val category: String, // "Lịch đặt", "Trận đấu", "Khuyến mãi"
    val isRead: Boolean = false
)

data class UserProfile(
    val name: String = "Minh",
    val phone: String = "0908 190 228",
    val email: String = "minh.badmi@gmail.com",
    val skillLevel: String = "Trung bình khá (B+)",
    val matchesPlayed: Int = 24,
    val hoursPlayed: Int = 48,
    val punctualityRate: Int = 96,
    val handedness: String = "Tay phải",
    val playStyle: String = "Tấn công / Công thủ toàn diện"
)
