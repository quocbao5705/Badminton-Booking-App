# BadmiSpot - Badminton Booking App 🏸

Ứng dụng di động đặt sân cầu lông, kết nối bạn chơi và mua sắm dụng cụ thể thao thông minh, được xây dựng hoàn toàn bằng **Kotlin** và **Jetpack Compose** theo hệ thống thiết kế trên Figma.

---

## 📱 Giới thiệu dự án

**BadmiSpot** giải quyết bài toán tìm kiếm sân cầu lông giờ cao điểm, kết nối cộng đồng người chơi phong trào và quản lý lịch đặt sân dễ dàng chỉ với vài chạm.

- **Nền tảng:** Android (Hỗ trợ Android 7.0 / API 24 trở lên)
- **Ngôn ngữ:** Kotlin 2.x
- **UI Framework:** Jetpack Compose (Material 3)
- **Kiến trúc & Điều hướng:** Navigation Compose, Single Activity Architecture
- **Màu sắc chủ đạo:** BadmiSpot Emerald & Mint (`#0F6B4D`, `#2EC486`)

---

## ✨ Các tính năng đã hoàn thiện theo Figma (18+ màn hình)

### 1. Đặt sân & Thanh toán (Core Booking)
- **Trang chủ (`HomeScreen`):** Banner ưu đãi giờ vàng, phím tắt tiện ích nhanh, danh sách sân nổi bật gần bạn.
- **Tìm kiếm & Bộ lọc (`SearchFilterScreen` / `SearchResultsScreen`):** Lọc sân theo khoảng cách (km), mức giá/giờ, loại thảm thi đấu chuẩn BWF, tiện ích đi kèm (máy lạnh, bãi đỗ ô tô, tắm nóng lạnh).
- **Bản đồ trực quan (`CourtMapScreen`):** Định vị vị trí sân cầu lông quanh khu vực.
- **Chọn khung giờ (`CourtBookingSlotScreen`):** Chọn ngày chơi, chọn số sân (Sân 01, Sân 02...), chọn các slot giờ chơi vàng.
- **Hóa đơn & Thanh toán (`BookingSummaryScreen` / `PaymentMethodScreen` / `PaymentQrScreen`):** Tự động tính tiền, áp mã giảm giá 20K, thanh toán qua QR chuyển khoản hoặc MoMo.
- **Vé điện tử (`BookingSuccessScreen` / `BookingDetailQrScreen`):** Hiển thị mã QR check-in tiện lợi tại quầy lễ tân sân.

### 2. Ghép trận giao lưu (Community & Matchmaking)
- **Danh sách kèo đấu (`MatchListScreen`):** Hiển thị số người hiện tại (ví dụ 3/4 người), trình độ yêu cầu (Mới chơi, Trung bình+, Khá, Bán chuyên).
- **Tạo kèo mới (`CreateMatchScreen`):** Tùy chọn đánh đơn, đánh đôi, chia sẻ chi phí sân theo đầu người, để lại ghi chú cho đồng đội.
- **Phòng chờ trận đấu (`MatchWaitingRoomScreen`):** Đếm ngược thời gian sẵn sàng ra sân.

### 3. Cửa hàng phụ kiện thể thao (BadmiStore)
- **Danh mục sản phẩm (`ShopScreen`):** Vợt cầu lông (Yonex 88D, Lining Axforce...), quả cầu Hải Yến, giày thi đấu.
- **Chi tiết sản phẩm (`ProductDetailScreen`):** Xem thông số chi tiết (4U/G5, điểm cân bằng, mức căng cước lbs).
- **Giỏ hàng (`CartScreen`):** Tăng/giảm số lượng và tính tổng giá trị đơn hàng.

### 4. Đăng nhập & Khởi tạo (Onboarding & Auth)
- Màn hình Chào mừng (`WelcomeScreen`), Đăng nhập (`LoginScreen`), Đăng ký (`RegisterScreen`).
- Xác nhận mã OTP 4 số (`OtpScreen`) và Khảo sát đánh giá trình độ ban đầu (`SkillAssessmentScreen`).

### 5. Hồ sơ & Tiện ích cá nhân (Profile & Utilities)
- **Hồ sơ cá nhân (`ProfileScreen`):** Quản lý thông số tay vợt (số trận, số giờ chơi, tỷ lệ đúng giờ).
- Quản lý lịch hẹn ra sân (`MyBookingsScreen`), Sân yêu thích, Kho voucher ưu đãi, Cài đặt thông báo.

---

## 🛠️ Cấu trúc thư mục mã nguồn

```
app/src/main/java/com/example/badmintonbookingapp/
├── data/
│   └── BadmiData.kt                    # Dữ liệu mẫu (Courts, Matches, Products, Bookings)
├── model/
│   └── BadmiModels.kt                  # Data class mô hình dữ liệu app
├── ui/
│   ├── components/
│   │   └── BadmiComponents.kt          # BadmiTopBar, BadmiButton, BadmiSearchBar, BadmiChip...
│   ├── navigation/
│   │   └── BadmiNavGraph.kt            # Hệ thống điều hướng NavHost & FigmaScreensExplorer
│   ├── screens/
│   │   ├── AuthAndOnboardingScreens.kt # Welcome, Login, Register, OTP, Skill
│   │   ├── BookingsScreens.kt          # MyBookingsScreen, BookingDetail
│   │   ├── CourtBookingSlotScreen.kt   # CourtDetail, BookingSlot, Summary
│   │   ├── EmptyAndSystemScreens.kt    # Empty states & Design System
│   │   ├── HomeScreen.kt               # Màn hình chính
│   │   ├── MatchScreens.kt             # MatchList, MatchDetail, CreateMatch
│   │   ├── PaymentAndSuccessScreens.kt # PaymentMethod, QR, Success
│   │   ├── ProfileScreens.kt           # Profile, Settings, Vouchers
│   │   ├── SearchAndMapScreens.kt      # Search, Filters, Map
│   │   └── ShopScreens.kt              # Shop, ProductDetail, Cart
│   └── theme/
│       ├── Color.kt                    # Bảng màu chuẩn Figma
│       ├── Theme.kt                    # BadmintonBookingAppTheme
│       └── Type.kt                     # Cấu hình Typography
└── MainActivity.kt                     # Entry point khởi chạy BadmiAppScaffold
```

---

## 🚀 Hướng dẫn cài đặt và chạy ứng dụng

1. **Yêu cầu môi trường:**
   - Android Studio Koala / Ladybug hoặc mới hơn.
   - JDK 17 trở lên.
   - Android SDK Platform API 34+ (Hỗ trợ máy ảo hoặc thiết bị thật).

2. **Các bước khởi chạy:**
   - Clone hoặc pull dự án về máy:
     ```bash
     git clone https://github.com/quocbao5705/Badminton-Booking-App.git
     ```
   - Mở dự án trong Android Studio.
   - Bấm **Sync Project with Gradle Files** (để tải các thư viện Compose & Navigation).
   - Chọn thiết bị máy ảo (Emulator) hoặc điện thoại thật và bấm **Run (Shift + F10)**.

---

## 👥 Thành viên thực hiện
- Dự án bài tập / đồ án phát triển ứng dụng di động đặt sân cầu lông **BadmiSpot**.
