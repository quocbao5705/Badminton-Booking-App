package com.example.badmintonbookingapp.data

import com.example.badmintonbookingapp.model.BookingItem
import com.example.badmintonbookingapp.model.CartItem
import com.example.badmintonbookingapp.model.Court
import com.example.badmintonbookingapp.model.MatchItem
import com.example.badmintonbookingapp.model.NotificationItem
import com.example.badmintonbookingapp.model.ProductItem
import com.example.badmintonbookingapp.model.TimeSlot
import com.example.badmintonbookingapp.model.UserProfile
import com.example.badmintonbookingapp.model.VoucherItem

object BadmiData {
    val sampleUser = UserProfile(
        name = "Minh",
        phone = "0908 190 228",
        email = "minh.badmi@gmail.com",
        skillLevel = "Trung bình khá (B+)",
        matchesPlayed = 24,
        hoursPlayed = 48,
        punctualityRate = 96,
        handedness = "Tay phải",
        playStyle = "Tấn công / Công thủ toàn diện"
    )

    val sampleCourts = listOf(
        Court(
            id = "court_1",
            name = "BadmiSpot Club Bình Thạnh",
            tag = "Sân nổi bật",
            address = "182/25 Bạch Đằng, P. 24, Bình Thạnh, TP.HCM",
            district = "Bình Thạnh",
            distanceKm = 1.2,
            rating = 4.9,
            reviewCount = 128,
            pricePerHour = 120000,
            openingHours = "06:00 - 23:00",
            totalCourts = 8,
            surfaceType = "Thảm tiêu chuẩn BWF",
            amenities = listOf("Bãi đỗ ô tô", "Máy lạnh", "Tắm nóng lạnh", "Căng vợt", "Wifi"),
            isFavorite = true
        ),
        Court(
            id = "court_2",
            name = "Sân Cầu Lông Viettel H2",
            tag = "Gần bạn nhất",
            address = "02 Hoàng Hoa Thám, P. 12, Tân Bình, TP.HCM",
            district = "Tân Bình",
            distanceKm = 2.5,
            rating = 4.8,
            reviewCount = 95,
            pricePerHour = 110000,
            openingHours = "05:30 - 23:30",
            totalCourts = 12,
            surfaceType = "Thảm cao su PVC chống trượt",
            amenities = listOf("Bãi đỗ ô tô", "Tắm nóng lạnh", "Căng vợt", "Nước giải khát"),
            isFavorite = true
        ),
        Court(
            id = "court_3",
            name = "Sân Cầu Lông Hải Yến Q7",
            tag = "Trần cao thoáng",
            address = "85 Huỳnh Tấn Phát, Tân Thuận Đông, Quận 7, TP.HCM",
            district = "Quận 7",
            distanceKm = 4.8,
            rating = 4.7,
            reviewCount = 76,
            pricePerHour = 130000,
            openingHours = "06:00 - 22:30",
            totalCourts = 6,
            surfaceType = "Thảm BWF Pro 5mm",
            amenities = listOf("Máy lạnh", "Phòng thay đồ", "Bãi xe máy rộng", "Wifi free"),
            isFavorite = false
        ),
        Court(
            id = "court_4",
            name = "BadmiArena Phú Nhuận",
            tag = "Đèn chuẩn thi đấu",
            address = "128 Phan Đăng Lưu, P. 3, Phú Nhuận, TP.HCM",
            district = "Phú Nhuận",
            distanceKm = 3.1,
            rating = 4.9,
            reviewCount = 142,
            pricePerHour = 125000,
            openingHours = "06:00 - 23:00",
            totalCourts = 10,
            surfaceType = "Thảm BWF Green Court",
            amenities = listOf("Bãi đỗ ô tô", "Máy lạnh", "Tắm nóng lạnh", "HLV hướng dẫn"),
            isFavorite = false
        )
    )

    val sampleTimeSlots = listOf(
        TimeSlot(time = "06:00 - 07:00", courtName = "Sân 02", isBooked = true, price = 90000),
        TimeSlot(time = "07:00 - 08:00", courtName = "Sân 02", isBooked = true, price = 90000),
        TimeSlot(time = "08:00 - 09:00", courtName = "Sân 02", isBooked = false, price = 100000),
        TimeSlot(time = "09:00 - 10:00", courtName = "Sân 02", isBooked = false, price = 100000),
        TimeSlot(time = "17:00 - 18:00", courtName = "Sân 02", isBooked = false, price = 120000),
        TimeSlot(time = "18:00 - 19:00", courtName = "Sân 02", isBooked = false, isSelected = true, price = 120000),
        TimeSlot(time = "19:00 - 20:00", courtName = "Sân 02", isBooked = false, isSelected = true, price = 120000),
        TimeSlot(time = "20:00 - 21:00", courtName = "Sân 02", isBooked = true, price = 120000),
        TimeSlot(time = "21:00 - 22:00", courtName = "Sân 02", isBooked = false, price = 110000)
    )

    val sampleMatches = listOf(
        MatchItem(
            id = "match_1",
            title = "Đánh đôi sau giờ làm",
            courtName = "BadmiSpot Club Bình Thạnh",
            address = "182/25 Bạch Đằng, Bình Thạnh",
            date = "Hôm nay, 22/10",
            time = "18:00 - 20:00",
            format = "Đánh đôi",
            skillLevel = "Trung bình+",
            currentPlayers = 3,
            maxPlayers = 4,
            feePerPlayer = 45000,
            hostName = "Minh Tuấn",
            description = "Kèo giao lưu vui vẻ, rèn thể lực sau giờ làm. Yêu cầu đánh đều tay, tính tình thoải mái, có tinh thần đồng đội.",
            status = "Đang tìm người"
        ),
        MatchItem(
            id = "match_2",
            title = "Giao lưu rèn tay cuối tuần",
            courtName = "Sân Cầu Lông Viettel H2",
            address = "02 Hoàng Hoa Thám, Tân Bình",
            date = "Thứ Bảy, 25/10",
            time = "08:00 - 10:00",
            format = "Đôi nam nữ",
            skillLevel = "Khá (B)",
            currentPlayers = 2,
            maxPlayers = 4,
            feePerPlayer = 50000,
            hostName = "Hoàng Long",
            description = "Trình độ B/B+, đập cầu tốt, đánh chiến thuật nhịp nhàng. Đã có sẵn cầu Ba Sao.",
            status = "Đang tìm người"
        ),
        MatchItem(
            id = "match_3",
            title = "Đánh đơn rèn sức bền",
            courtName = "BadmiArena Phú Nhuận",
            address = "128 Phan Đăng Lưu, Phú Nhuận",
            date = "Chủ Nhật, 26/10",
            time = "16:00 - 17:30",
            format = "Đơn nam",
            skillLevel = "Trung bình",
            currentPlayers = 1,
            maxPlayers = 2,
            feePerPlayer = 60000,
            hostName = "Quang Huy",
            description = "Cần bạn giao lưu đơn rèn thể lực, đánh cầu đều, vui là chính không áp lực điểm số.",
            status = "Đang tìm người"
        )
    )

    val sampleProducts = listOf(
        ProductItem(
            id = "prod_1",
            name = "Vợt Yonex Astrox 88D Play",
            category = "Vợt cầu lông",
            price = 790000,
            originalPrice = 950000,
            rating = 4.9,
            soldCount = 428,
            badge = "Bán chạy",
            weightGrip = "4U/G5",
            balancePoint = "Nặng đầu (Head Heavy)",
            tension = "20 - 28 lbs",
            description = "Phiên bản Play dễ tiếp cận của dòng vợt 88D danh tiếng, trợ lực smash cực thoát tay cho người chơi phong trào."
        ),
        ProductItem(
            id = "prod_2",
            name = "Vợt Lining Axforce 80 Chen Long",
            category = "Vợt cầu lông",
            price = 3450000,
            originalPrice = 3890000,
            rating = 5.0,
            soldCount = 89,
            badge = "Cao cấp",
            weightGrip = "4U/G5",
            balancePoint = "Nặng đầu 302mm",
            tension = "24 - 30 lbs",
            description = "Cây vợt huyền thoại đồng hành cùng Chen Long. Khung carbon siêu cứng đem lại uy lực tối đa."
        ),
        ProductItem(
            id = "prod_3",
            name = "Ống cầu lông Hải Yến S90",
            category = "Quả cầu lông",
            price = 245000,
            originalPrice = 270000,
            rating = 4.8,
            soldCount = 1240,
            badge = "Ưa chuộng",
            weightGrip = "Tốc độ 77",
            balancePoint = "Độ bền cao",
            tension = "12 quả / ống",
            description = "Cầu lông đạt chuẩn thi đấu toàn quốc, đường bay đầm, lông dẻo khó gãy."
        ),
        ProductItem(
            id = "prod_4",
            name = "Giày Yonex Power Cushion 65Z3",
            category = "Giày cầu lông",
            price = 2650000,
            originalPrice = 2990000,
            rating = 4.9,
            soldCount = 312,
            badge = "Chính hãng",
            weightGrip = "Size 40 - 44",
            balancePoint = "Đệm Power Cushion+",
            tension = "Bảo vệ khớp gối",
            description = "Mẫu giày được đa số tuyển thủ hàng đầu thế giới tin dùng với khả năng giảm chấn tuyệt hảo."
        )
    )

    val sampleCart = listOf(
        CartItem(
            product = sampleProducts[0],
            quantity = 1,
            selectedWeight = "4U/G5"
        ),
        CartItem(
            product = sampleProducts[2],
            quantity = 1,
            selectedWeight = "Tốc độ 77"
        )
    )

    val sampleBookings = listOf(
        BookingItem(
            id = "bk_1",
            bookingCode = "BMSP-8823",
            courtName = "BadmiSpot Club Bình Thạnh",
            courtNumber = "Sân 02",
            address = "182/25 Bạch Đằng, P. 24, Bình Thạnh",
            date = "Thứ Tư, 22/10/2026",
            timeSlot = "18:00 - 20:00 (2 tiếng)",
            durationHours = 2,
            totalPrice = 220000,
            status = "Sắp tới",
            qrCode = "BADMI-QR-8823"
        ),
        BookingItem(
            id = "bk_2",
            bookingCode = "BMSP-7102",
            courtName = "Sân Cầu Lông Viettel H2",
            courtNumber = "Sân 05",
            address = "02 Hoàng Hoa Thám, P. 12, Tân Bình",
            date = "Chủ Nhật, 18/10/2026",
            timeSlot = "07:00 - 09:00 (2 tiếng)",
            durationHours = 2,
            totalPrice = 200000,
            status = "Đã hoàn thành",
            qrCode = "BADMI-QR-7102"
        ),
        BookingItem(
            id = "bk_3",
            bookingCode = "BMSP-6430",
            courtName = "BadmiArena Phú Nhuận",
            courtNumber = "Sân 01",
            address = "128 Phan Đăng Lưu, P. 3, Phú Nhuận",
            date = "Thứ Sáu, 10/10/2026",
            timeSlot = "19:00 - 21:00 (2 tiếng)",
            durationHours = 2,
            totalPrice = 240000,
            status = "Đã hủy",
            qrCode = "BADMI-QR-6430"
        )
    )

    val sampleVouchers = listOf(
        VoucherItem(
            code = "BADMI20K",
            title = "Giảm trực tiếp 20.000 đ",
            discountText = "Giảm 20K",
            discountAmount = 20000,
            minSpend = 150000,
            expiryDate = "31/10/2026",
            isSelected = true
        ),
        VoucherItem(
            code = "GIOVANG50",
            title = "Ưu đãi 50% giờ sáng sớm (06h - 09h)",
            discountText = "Giảm 50%",
            discountAmount = 50000,
            minSpend = 100000,
            expiryDate = "15/11/2026",
            isSelected = false
        ),
        VoucherItem(
            code = "FREESHIPSP",
            title = "Miễn phí vận chuyển dụng cụ phụ kiện",
            discountText = "Freeship",
            discountAmount = 30000,
            minSpend = 200000,
            expiryDate = "30/11/2026",
            isSelected = false
        )
    )

    val sampleNotifications = listOf(
        NotificationItem(
            id = "notif_1",
            title = "Nhắc nhở lịch đặt sân hôm nay!",
            message = "Bạn có lịch đặt Sân 02 tại BadmiSpot Bình Thạnh lúc 18:00. Hãy chuẩn bị ra sân nhé!",
            timeAgo = "15 phút trước",
            category = "Lịch đặt",
            isRead = false
        ),
        NotificationItem(
            id = "notif_2",
            title = "Có người vừa tham gia trận của bạn",
            message = "Thành viên Tuấn Trần đã tham gia 'Đánh đôi sau giờ làm'. Phòng còn 1 vị trí trống.",
            timeAgo = "1 giờ trước",
            category = "Trận đấu",
            isRead = false
        ),
        NotificationItem(
            id = "notif_3",
            title = "Mã ưu đãi mới cho bạn",
            message = "Tặng bạn mã BADMI20K giảm 20.000 đ cho lượt đặt sân trong tuần này.",
            timeAgo = "1 ngày trước",
            category = "Khuyến mãi",
            isRead = true
        )
    )
}
