package com.example.badmintonbookingapp.ui.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.draw.clip
import androidx.navigation.navArgument
import com.example.badmintonbookingapp.ui.components.*
import com.example.badmintonbookingapp.ui.screens.*
import com.example.badmintonbookingapp.ui.theme.*

sealed class Screen(val route: String, val title: String, val icon: ImageVector? = null) {
    // Bottom Nav Tabs
    object Home : Screen("home", "Trang chủ", Icons.Default.Home)
    object Bookings : Screen("bookings", "Lịch hẹn", Icons.Default.CalendarMonth)
    object Matches : Screen("matches", "Ghép trận", Icons.Default.Groups)
    object Shop : Screen("shop", "Cửa hàng", Icons.Default.ShoppingBag)
    object Profile : Screen("profile", "Cá nhân", Icons.Default.Person)

    // Booking Flow
    object CourtDetail : Screen("court_detail/{courtId}", "Chi tiết sân") {
        fun createRoute(courtId: String) = "court_detail/$courtId"
    }
    object CourtBookingSlot : Screen("court_booking_slot/{courtId}", "Chọn giờ đặt sân") {
        fun createRoute(courtId: String) = "court_booking_slot/$courtId"
    }
    object BookingSummary : Screen("booking_summary/{courtId}/{hours}/{amount}", "Xác nhận đặt") {
        fun createRoute(courtId: String, hours: Int, amount: Int) = "booking_summary/$courtId/$hours/$amount"
    }
    object PaymentMethod : Screen("payment_method/{amount}", "Phương thức thanh toán") {
        fun createRoute(amount: Int) = "payment_method/$amount"
    }
    object PaymentQr : Screen("payment_qr/{amount}", "Thanh toán QR") {
        fun createRoute(amount: Int) = "payment_qr/$amount"
    }
    object BookingSuccess : Screen("booking_success", "Đặt sân thành công!")
    object BookingDetailQr : Screen("booking_detail_qr/{bookingId}", "Chi tiết lịch đặt QR") {
        fun createRoute(bookingId: String) = "booking_detail_qr/$bookingId"
    }

    // Match Flow
    object MatchDetail : Screen("match_detail/{matchId}", "Chi tiết trận đấu") {
        fun createRoute(matchId: String) = "match_detail/$matchId"
    }
    object CreateMatch : Screen("create_match", "Tạo trận đấu")
    object MatchWaitingRoom : Screen("match_waiting_room/{matchId}", "Phòng chờ trận") {
        fun createRoute(matchId: String) = "match_waiting_room/$matchId"
    }
    object MatchRating : Screen("match_rating/{matchId}", "Đánh giá trận đấu") {
        fun createRoute(matchId: String) = "match_rating/$matchId"
    }

    // Search & Map
    object SearchFilter : Screen("search_filter", "Tìm kiếm & Bộ lọc")
    object SearchResults : Screen("search_results", "Kết quả tìm kiếm")
    object CourtMap : Screen("court_map", "Bản đồ sân")

    // Shop Flow
    object ProductDetail : Screen("product_detail/{productId}", "Chi tiết sản phẩm") {
        fun createRoute(productId: String) = "product_detail/$productId"
    }
    object Cart : Screen("cart", "Giỏ hàng")

    // Profile & Extra
    object EditProfile : Screen("edit_profile", "Chỉnh sửa hồ sơ")
    object Favorites : Screen("favorites", "Sân yêu thích")
    object Vouchers : Screen("vouchers", "Mã ưu đãi")
    object Notifications : Screen("notifications", "Thông báo")
    object Settings : Screen("settings", "Cài đặt")

    // Auth & Onboarding
    object Welcome : Screen("welcome", "Chào mừng")
    object Login : Screen("login", "Đăng nhập")
    object Register : Screen("register", "Đăng ký")
    object Otp : Screen("otp", "Xác nhận OTP")
    object SkillAssessment : Screen("skill_assessment", "Khảo sát trình độ")

    // Empty & Design System
    object EmptySchedule : Screen("empty_schedule", "Lịch trống")
    object EmptyMatches : Screen("empty_matches", "Trận trống")
    object EmptyCart : Screen("empty_cart", "Giỏ trống")
    object NoInternet : Screen("no_internet", "Mất kết nối")
    object DesignSystem : Screen("design_system", "Hệ thống thiết kế · BadmiSpot")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BadmiAppScaffold() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    var showCancelDialog by remember { mutableStateOf(false) }
    var cancelTargetBookingId by remember { mutableStateOf("") }
    var showScreenExplorerSheet by remember { mutableStateOf(false) }

    val bottomNavItems = listOf(
        Screen.Home,
        Screen.Bookings,
        Screen.Matches,
        Screen.Shop,
        Screen.Profile
    )

    val isBottomBarVisible = bottomNavItems.any { it.route == currentRoute }

    Scaffold(
        bottomBar = {
            if (isBottomBarVisible) {
                NavigationBar(
                    containerColor = BadmiSurface,
                    tonalElevation = 8.dp
                ) {
                    bottomNavItems.forEach { screen ->
                        val selected = currentRoute == screen.route
                        NavigationBarItem(
                            selected = selected,
                            onClick = {
                                if (currentRoute != screen.route) {
                                    navController.navigate(screen.route) {
                                        popUpTo(Screen.Home.route) { saveState = true }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            },
                            icon = {
                                screen.icon?.let {
                                    Icon(
                                        imageVector = it,
                                        contentDescription = screen.title,
                                        tint = if (selected) BadmiPrimary else BadmiTextMuted
                                    )
                                }
                            },
                            label = {
                                Text(
                                    text = screen.title,
                                    color = if (selected) BadmiPrimary else BadmiTextMuted,
                                    fontSize = 11.sp,
                                    fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium
                                )
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = BadmiPrimaryLight
                            )
                        )
                    }
                }
            }
        },
        floatingActionButton = {
            // Quick Figma Screen Switcher FAB
            FloatingActionButton(
                onClick = { showScreenExplorerSheet = true },
                containerColor = BadmiPrimaryDark,
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier
                    .size(48.dp)
                    .padding(bottom = if (isBottomBarVisible) 60.dp else 0.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.DashboardCustomize,
                    contentDescription = "Figma Screens Menu",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            BadmiNavHost(
                navController = navController,
                onTriggerCancelDialog = { bookingId ->
                    cancelTargetBookingId = bookingId
                    showCancelDialog = true
                }
            )

            if (showCancelDialog) {
                CancelBookingDialog(
                    bookingId = cancelTargetBookingId,
                    onDismiss = { showCancelDialog = false },
                    onConfirmCancel = {
                        showCancelDialog = false
                        navController.navigate(Screen.Bookings.route)
                    }
                )
            }

            if (showScreenExplorerSheet) {
                ModalBottomSheet(
                    onDismissRequest = { showScreenExplorerSheet = false },
                    containerColor = BadmiSurface,
                    shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
                ) {
                    FigmaScreensExplorer(
                        onSelectScreen = { route ->
                            showScreenExplorerSheet = false
                            navController.navigate(route)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun BadmiNavHost(
    navController: NavHostController,
    onTriggerCancelDialog: (String) -> Unit
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        // Tab 1: Home
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToCourtDetail = { id -> navController.navigate(Screen.CourtDetail.createRoute(id)) },
                onNavigateToBookingSlot = { id -> navController.navigate(Screen.CourtBookingSlot.createRoute(id)) },
                onNavigateToMatchDetail = { id -> navController.navigate(Screen.MatchDetail.createRoute(id)) },
                onNavigateToCreateMatch = { navController.navigate(Screen.CreateMatch.route) },
                onNavigateToNotifications = { navController.navigate(Screen.Notifications.route) },
                onNavigateToSearch = { navController.navigate(Screen.SearchFilter.route) },
                onNavigateToShop = { navController.navigate(Screen.Shop.route) },
                onNavigateToVouchers = { navController.navigate(Screen.Vouchers.route) }
            )
        }

        // Tab 2: Bookings
        composable(Screen.Bookings.route) {
            MyBookingsScreen(
                onNavigateToDetail = { id -> navController.navigate(Screen.BookingDetailQr.createRoute(id)) },
                onNavigateToCancel = { id -> onTriggerCancelDialog(id) }
            )
        }

        // Tab 3: Matches
        composable(Screen.Matches.route) {
            MatchListScreen(
                onNavigateToMatchDetail = { id -> navController.navigate(Screen.MatchDetail.createRoute(id)) },
                onNavigateToCreateMatch = { navController.navigate(Screen.CreateMatch.route) }
            )
        }

        // Tab 4: Shop
        composable(Screen.Shop.route) {
            ShopScreen(
                onNavigateToProductDetail = { id -> navController.navigate(Screen.ProductDetail.createRoute(id)) },
                onNavigateToCart = { navController.navigate(Screen.Cart.route) }
            )
        }

        // Tab 5: Profile
        composable(Screen.Profile.route) {
            ProfileScreen(
                onNavigateToEditProfile = { navController.navigate(Screen.EditProfile.route) },
                onNavigateToFavorites = { navController.navigate(Screen.Favorites.route) },
                onNavigateToVouchers = { navController.navigate(Screen.Vouchers.route) },
                onNavigateToSettings = { navController.navigate(Screen.Settings.route) },
                onNavigateToNotifications = { navController.navigate(Screen.Notifications.route) },
                onNavigateToCourtDetail = { id -> navController.navigate(Screen.CourtDetail.createRoute(id)) }
            )
        }

        // Court Booking Flow
        composable(
            route = Screen.CourtDetail.route,
            arguments = listOf(navArgument("courtId") { type = NavType.StringType })
        ) { backStackEntry ->
            val courtId = backStackEntry.arguments?.getString("courtId") ?: "court_1"
            CourtDetailScreen(
                courtId = courtId,
                onBackClick = { navController.popBackStack() },
                onBookSlotClick = { navController.navigate(Screen.CourtBookingSlot.createRoute(courtId)) }
            )
        }

        composable(
            route = Screen.CourtBookingSlot.route,
            arguments = listOf(navArgument("courtId") { type = NavType.StringType })
        ) { backStackEntry ->
            val courtId = backStackEntry.arguments?.getString("courtId") ?: "court_1"
            CourtBookingSlotScreen(
                courtId = courtId,
                onBackClick = { navController.popBackStack() },
                onProceedToSummary = { cId, hours, amount ->
                    navController.navigate(Screen.BookingSummary.createRoute(cId, hours, amount))
                }
            )
        }

        composable(
            route = Screen.BookingSummary.route,
            arguments = listOf(
                navArgument("courtId") { type = NavType.StringType },
                navArgument("hours") { type = NavType.IntType },
                navArgument("amount") { type = NavType.IntType }
            )
        ) { backStackEntry ->
            val courtId = backStackEntry.arguments?.getString("courtId") ?: "court_1"
            val hours = backStackEntry.arguments?.getInt("hours") ?: 2
            val amount = backStackEntry.arguments?.getInt("amount") ?: 240000
            BookingSummaryScreen(
                courtId = courtId,
                selectedHours = hours,
                rawAmount = amount,
                onBackClick = { navController.popBackStack() },
                onProceedToPayment = {
                    val finalAmount = (amount - 20000).coerceAtLeast(0)
                    navController.navigate(Screen.PaymentMethod.createRoute(finalAmount))
                }
            )
        }

        composable(
            route = Screen.PaymentMethod.route,
            arguments = listOf(navArgument("amount") { type = NavType.IntType })
        ) { backStackEntry ->
            val amount = backStackEntry.arguments?.getInt("amount") ?: 220000
            PaymentMethodScreen(
                totalAmount = amount,
                onBackClick = { navController.popBackStack() },
                onProceedToQr = { navController.navigate(Screen.PaymentQr.createRoute(amount)) }
            )
        }

        composable(
            route = Screen.PaymentQr.route,
            arguments = listOf(navArgument("amount") { type = NavType.IntType })
        ) { backStackEntry ->
            val amount = backStackEntry.arguments?.getInt("amount") ?: 220000
            PaymentQrScreen(
                amount = amount,
                onBackClick = { navController.popBackStack() },
                onPaymentSuccess = { navController.navigate(Screen.BookingSuccess.route) }
            )
        }

        composable(Screen.BookingSuccess.route) {
            BookingSuccessScreen(
                onViewBookingDetail = {
                    navController.navigate(Screen.BookingDetailQr.createRoute("bk_1")) {
                        popUpTo(Screen.Home.route)
                    }
                },
                onBackToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                }
            )
        }

        composable(
            route = Screen.BookingDetailQr.route,
            arguments = listOf(navArgument("bookingId") { type = NavType.StringType })
        ) { backStackEntry ->
            val bookingId = backStackEntry.arguments?.getString("bookingId") ?: "bk_1"
            BookingDetailQrScreen(
                bookingId = bookingId,
                onBackClick = { navController.popBackStack() },
                onCancelClick = { onTriggerCancelDialog(bookingId) }
            )
        }

        // Match Detail & Actions
        composable(
            route = Screen.MatchDetail.route,
            arguments = listOf(navArgument("matchId") { type = NavType.StringType })
        ) { backStackEntry ->
            val matchId = backStackEntry.arguments?.getString("matchId") ?: "match_1"
            MatchDetailScreen(
                matchId = matchId,
                onBackClick = { navController.popBackStack() },
                onJoinClick = { navController.navigate(Screen.MatchWaitingRoom.createRoute(matchId)) },
                onGoToWaitingRoom = { navController.navigate(Screen.MatchWaitingRoom.createRoute(matchId)) },
                onRateMatch = { navController.navigate(Screen.MatchRating.createRoute(matchId)) }
            )
        }

        composable(Screen.CreateMatch.route) {
            CreateMatchScreen(
                onBackClick = { navController.popBackStack() },
                onSubmit = { navController.navigate(Screen.Matches.route) }
            )
        }

        composable(
            route = Screen.MatchWaitingRoom.route,
            arguments = listOf(navArgument("matchId") { type = NavType.StringType })
        ) { backStackEntry ->
            val matchId = backStackEntry.arguments?.getString("matchId") ?: "match_1"
            MatchWaitingRoomScreen(
                matchId = matchId,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(
            route = Screen.MatchRating.route,
            arguments = listOf(navArgument("matchId") { type = NavType.StringType })
        ) { backStackEntry ->
            val matchId = backStackEntry.arguments?.getString("matchId") ?: "match_1"
            MatchRatingScreen(
                matchId = matchId,
                onBackClick = { navController.popBackStack() },
                onSubmitRating = { navController.popBackStack() }
            )
        }

        // Search & Maps
        composable(Screen.SearchFilter.route) {
            SearchFilterScreen(
                onBackClick = { navController.popBackStack() },
                onApplyFilter = { navController.navigate(Screen.SearchResults.route) }
            )
        }

        composable(Screen.SearchResults.route) {
            SearchResultsScreen(
                onBackClick = { navController.popBackStack() },
                onNavigateToCourtDetail = { id -> navController.navigate(Screen.CourtDetail.createRoute(id)) },
                onNavigateToBookingSlot = { id -> navController.navigate(Screen.CourtBookingSlot.createRoute(id)) },
                onNavigateToMap = { navController.navigate(Screen.CourtMap.route) }
            )
        }

        composable(Screen.CourtMap.route) {
            CourtMapScreen(
                onBackClick = { navController.popBackStack() },
                onNavigateToCourtDetail = { id -> navController.navigate(Screen.CourtDetail.createRoute(id)) }
            )
        }

        // Shop Sub-screens
        composable(
            route = Screen.ProductDetail.route,
            arguments = listOf(navArgument("productId") { type = NavType.StringType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId") ?: "prod_1"
            ProductDetailScreen(
                productId = productId,
                onBackClick = { navController.popBackStack() },
                onAddToCart = { navController.navigate(Screen.Cart.route) }
            )
        }

        composable(Screen.Cart.route) {
            CartScreen(
                onBackClick = { navController.popBackStack() },
                onCheckout = { navController.navigate(Screen.PaymentMethod.createRoute(835000)) }
            )
        }

        // Profile Sub-screens
        composable(Screen.EditProfile.route) {
            EditProfileScreen(
                onBackClick = { navController.popBackStack() },
                onSaveSuccess = { navController.popBackStack() }
            )
        }

        composable(Screen.Favorites.route) {
            FavoritesScreen(
                onBackClick = { navController.popBackStack() },
                onNavigateToCourtDetail = { id -> navController.navigate(Screen.CourtDetail.createRoute(id)) }
            )
        }

        composable(Screen.Vouchers.route) {
            VouchersScreen(onBackClick = { navController.popBackStack() })
        }

        composable(Screen.Notifications.route) {
            NotificationsScreen(onBackClick = { navController.popBackStack() })
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                onBackClick = { navController.popBackStack() },
                onLogout = { navController.navigate(Screen.Welcome.route) }
            )
        }

        // Auth & Onboarding Flow
        composable(Screen.Welcome.route) {
            WelcomeScreen(
                onGetStarted = { navController.navigate(Screen.SkillAssessment.route) },
                onLoginClick = { navController.navigate(Screen.Login.route) }
            )
        }

        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = { navController.navigate(Screen.Home.route) },
                onNavigateToRegister = { navController.navigate(Screen.Register.route) },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                onNavigateToOtp = { navController.navigate(Screen.Otp.route) },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.Otp.route) {
            OtpScreen(
                onVerifySuccess = { navController.navigate(Screen.SkillAssessment.route) },
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(Screen.SkillAssessment.route) {
            SkillAssessmentScreen(
                onComplete = { navController.navigate(Screen.Home.route) }
            )
        }

        // System & Empty States
        composable(Screen.EmptySchedule.route) {
            EmptyScheduleScreen(onActionClick = { navController.navigate(Screen.SearchFilter.route) })
        }
        composable(Screen.EmptyMatches.route) {
            EmptyMatchesScreen(onActionClick = { navController.navigate(Screen.CreateMatch.route) })
        }
        composable(Screen.EmptyCart.route) {
            EmptyCartScreen(onActionClick = { navController.navigate(Screen.Shop.route) })
        }
        composable(Screen.NoInternet.route) {
            NoInternetScreen(onRetry = { navController.navigate(Screen.Home.route) })
        }
        composable(Screen.DesignSystem.route) {
            BadmiDesignSystemShowcaseScreen(onBackClick = { navController.popBackStack() })
        }
    }
}

/**
 * Quick drawer to jump to any screen shown in Figma
 */
@Composable
fun FigmaScreensExplorer(
    onSelectScreen: (String) -> Unit
) {
    val figmaScreenCategories = listOf(
        Pair("1. Hệ thống thiết kế", listOf(
            Pair("Bảng tổng hợp UI · BadmiSpot", Screen.DesignSystem.route)
        )),
        Pair("2. Đặt sân & Thanh toán", listOf(
            Pair("Trang chủ", Screen.Home.route),
            Pair("Chi tiết sân", Screen.CourtDetail.createRoute("court_1")),
            Pair("Chọn giờ & Khung giờ đặt", Screen.CourtBookingSlot.createRoute("court_1")),
            Pair("Xác nhận đặt sân", Screen.BookingSummary.createRoute("court_1", 2, 240000)),
            Pair("Phương thức thanh toán", Screen.PaymentMethod.createRoute(220000)),
            Pair("Thanh toán QR", Screen.PaymentQr.createRoute(220000)),
            Pair("Đặt sân thành công!", Screen.BookingSuccess.route),
            Pair("Lịch hẹn ra sân", Screen.Bookings.route),
            Pair("Chi tiết lịch đặt (QR Check-in)", Screen.BookingDetailQr.createRoute("bk_1"))
        )),
        Pair("3. Ghép trận & Cộng đồng", listOf(
            Pair("Danh sách ghép trận", Screen.Matches.route),
            Pair("Chi tiết trận đấu", Screen.MatchDetail.createRoute("match_1")),
            Pair("Tạo trận đấu", Screen.CreateMatch.route),
            Pair("Phòng chờ trận đấu (02:15:30)", Screen.MatchWaitingRoom.createRoute("match_1")),
            Pair("Đánh giá trận đấu", Screen.MatchRating.createRoute("match_1"))
        )),
        Pair("4. Tìm kiếm & Bản đồ", listOf(
            Pair("Tìm kiếm & Lọc", Screen.SearchFilter.route),
            Pair("Kết quả tìm kiếm (24 sân)", Screen.SearchResults.route),
            Pair("Bản đồ sân trực quan", Screen.CourtMap.route)
        )),
        Pair("5. Cửa hàng BadmiStore", listOf(
            Pair("Cửa hàng dụng cụ", Screen.Shop.route),
            Pair("Chi tiết sản phẩm (Vợt 88D)", Screen.ProductDetail.createRoute("prod_1")),
            Pair("Giỏ hàng", Screen.Cart.route)
        )),
        Pair("6. Hồ sơ & Tiện ích", listOf(
            Pair("Hồ sơ cá nhân (Minh)", Screen.Profile.route),
            Pair("Chỉnh sửa hồ sơ", Screen.EditProfile.route),
            Pair("Sân yêu thích", Screen.Favorites.route),
            Pair("Mã giảm giá", Screen.Vouchers.route),
            Pair("Thông báo", Screen.Notifications.route),
            Pair("Cài đặt", Screen.Settings.route)
        )),
        Pair("7. Đăng nhập & Khởi tạo", listOf(
            Pair("Chào mừng / Onboarding", Screen.Welcome.route),
            Pair("Đăng nhập", Screen.Login.route),
            Pair("Đăng ký", Screen.Register.route),
            Pair("Xác nhận OTP", Screen.Otp.route),
            Pair("Khảo sát trình độ", Screen.SkillAssessment.route)
        )),
        Pair("8. Trạng thái rỗng & Hệ thống", listOf(
            Pair("Lịch hẹn trống", Screen.EmptySchedule.route),
            Pair("Trận đấu trống", Screen.EmptyMatches.route),
            Pair("Giỏ hàng trống", Screen.EmptyCart.route),
            Pair("Mất kết nối mạng", Screen.NoInternet.route)
        ))
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .heightIn(max = 520.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "Bản đồ 30+ Màn hình Figma",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = BadmiTextPrimary
                )
                Text(
                    text = "Bấm vào màn hình bất kỳ để mở xem ngay lập tức",
                    fontSize = 12.sp,
                    color = BadmiTextSecondary
                )
            }
            BadmiBadge(text = "BadmiSpot UI")
        }

        Spacer(modifier = Modifier.height(14.dp))
        HorizontalDivider(color = BadmiBorderLight)
        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            figmaScreenCategories.forEach { (categoryName, screens) ->
                item {
                    Text(
                        text = categoryName,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = BadmiPrimary
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        screens.forEach { (name, route) ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .clickable { onSelectScreen(route) }
                                    .padding(vertical = 8.dp, horizontal = 10.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "• $name",
                                    fontSize = 13.sp,
                                    color = BadmiTextPrimary
                                )
                                Icon(
                                    imageVector = Icons.Default.ChevronRight,
                                    contentDescription = null,
                                    tint = BadmiTextMuted,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
