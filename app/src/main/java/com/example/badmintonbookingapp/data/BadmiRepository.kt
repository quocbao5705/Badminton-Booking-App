package com.example.badmintonbookingapp.data

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import com.example.badmintonbookingapp.model.BookingItem
import com.example.badmintonbookingapp.model.CartItem
import com.example.badmintonbookingapp.model.Court
import com.example.badmintonbookingapp.model.CourtFilterCriteria
import com.example.badmintonbookingapp.model.MatchFilterCriteria
import com.example.badmintonbookingapp.model.MatchItem
import com.example.badmintonbookingapp.model.MatchRating
import com.example.badmintonbookingapp.model.NotificationItem
import com.example.badmintonbookingapp.model.ProductItem
import com.example.badmintonbookingapp.model.TimeSlot
import com.example.badmintonbookingapp.model.UserProfile
import com.example.badmintonbookingapp.model.VoucherItem

object BadmiRepository {
    val userProfileState = mutableStateOf(BadmiSeedData.initialUser)
    val courtsState = mutableStateListOf<Court>().apply { addAll(BadmiSeedData.initialCourts) }
    val timeSlotsState = mutableStateListOf<TimeSlot>().apply { addAll(BadmiSeedData.initialTimeSlots) }
    val matchesState = mutableStateListOf<MatchItem>().apply { addAll(BadmiSeedData.initialMatches) }
    val productsState = mutableStateListOf<ProductItem>().apply { addAll(BadmiSeedData.initialProducts) }
    val cartItemsState = mutableStateListOf<CartItem>().apply { addAll(BadmiSeedData.initialCart) }
    val bookingsState = mutableStateListOf<BookingItem>().apply { addAll(BadmiSeedData.initialBookings) }
    val vouchersState = mutableStateListOf<VoucherItem>().apply { addAll(BadmiSeedData.initialVouchers) }
    val notificationsState = mutableStateListOf<NotificationItem>().apply { addAll(BadmiSeedData.initialNotifications) }
    val matchRatingsState = mutableStateListOf<MatchRating>()

    fun resetDataToSeed() {
        userProfileState.value = BadmiSeedData.initialUser
        courtsState.clear()
        courtsState.addAll(BadmiSeedData.initialCourts)
        timeSlotsState.clear()
        timeSlotsState.addAll(BadmiSeedData.initialTimeSlots)
        matchesState.clear()
        matchesState.addAll(BadmiSeedData.initialMatches)
        productsState.clear()
        productsState.addAll(BadmiSeedData.initialProducts)
        cartItemsState.clear()
        cartItemsState.addAll(BadmiSeedData.initialCart)
        bookingsState.clear()
        bookingsState.addAll(BadmiSeedData.initialBookings)
        vouchersState.clear()
        vouchersState.addAll(BadmiSeedData.initialVouchers)
        notificationsState.clear()
        notificationsState.addAll(BadmiSeedData.initialNotifications)
        matchRatingsState.clear()
    }

    // User Profile API
    fun getUserProfile(): UserProfile = userProfileState.value

    fun updateUserProfile(
        name: String = userProfileState.value.name,
        phone: String = userProfileState.value.phone,
        email: String = userProfileState.value.email,
        skillLevel: String = userProfileState.value.skillLevel,
        playStyle: String = userProfileState.value.playStyle,
        handedness: String = userProfileState.value.handedness,
    ): UserProfile {
        val updated = userProfileState.value.copy(
            name = name,
            phone = phone,
            email = email,
            skillLevel = skillLevel,
            playStyle = playStyle,
            handedness = handedness,
        )
        userProfileState.value = updated
        return updated
    }

    // Courts API
    fun getCourts(): List<Court> = courtsState

    fun getCourtById(id: String): Court? = courtsState.find { it.id == id }

    fun toggleFavorite(courtId: String): Boolean {
        val index = courtsState.indexOfFirst { it.id == courtId }
        if (index != -1) {
            val current = courtsState[index]
            val updated = current.copy(isFavorite = !current.isFavorite)
            courtsState[index] = updated
            return updated.isFavorite
        }
        return false
    }

    fun searchCourts(filter: CourtFilterCriteria): List<Court> {
        val filtered = courtsState.filter { court ->
            val matchesQuery = filter.query.isBlank() ||
                court.name.contains(filter.query, ignoreCase = true) ||
                court.address.contains(filter.query, ignoreCase = true) ||
                court.district.contains(filter.query, ignoreCase = true)
            val matchesDistrict = filter.district == "Tất cả" || court.district.contains(filter.district, ignoreCase = true)
            val matchesPrice = court.pricePerHour <= filter.maxPrice
            val matchesFav = !filter.isFavoriteOnly || court.isFavorite
            val matchesSurface = filter.surfaceType == "Tất cả" || court.surfaceType.contains(filter.surfaceType, ignoreCase = true)
            matchesQuery && matchesDistrict && matchesPrice && matchesFav && matchesSurface
        }

        return when (filter.sortBy) {
            com.example.badmintonbookingapp.model.CourtSortOption.DISTANCE -> filtered.sortedBy { it.distanceKm }
            com.example.badmintonbookingapp.model.CourtSortOption.RATING -> filtered.sortedByDescending { it.rating }
            com.example.badmintonbookingapp.model.CourtSortOption.PRICE_LOW_TO_HIGH -> filtered.sortedBy { it.pricePerHour }
            com.example.badmintonbookingapp.model.CourtSortOption.PRICE_HIGH_TO_LOW -> filtered.sortedByDescending { it.pricePerHour }
            com.example.badmintonbookingapp.model.CourtSortOption.POPULARITY -> filtered.sortedByDescending { it.reviewCount }
        }
    }

    fun getSortedCourts(sortBy: com.example.badmintonbookingapp.model.CourtSortOption = com.example.badmintonbookingapp.model.CourtSortOption.DISTANCE): List<Court> {
        return when (sortBy) {
            com.example.badmintonbookingapp.model.CourtSortOption.DISTANCE -> courtsState.sortedBy { it.distanceKm }
            com.example.badmintonbookingapp.model.CourtSortOption.RATING -> courtsState.sortedByDescending { it.rating }
            com.example.badmintonbookingapp.model.CourtSortOption.PRICE_LOW_TO_HIGH -> courtsState.sortedBy { it.pricePerHour }
            com.example.badmintonbookingapp.model.CourtSortOption.PRICE_HIGH_TO_LOW -> courtsState.sortedByDescending { it.pricePerHour }
            com.example.badmintonbookingapp.model.CourtSortOption.POPULARITY -> courtsState.sortedByDescending { it.reviewCount }
        }
    }

    fun getNearbyCourts(maxDistanceKm: Double = 3.0): List<Court> {
        return courtsState.filter { it.distanceKm <= maxDistanceKm }.sortedBy { it.distanceKm }
    }

    fun getTopRatedCourts(minRating: Double = 4.8): List<Court> {
        return courtsState.filter { it.rating >= minRating }.sortedByDescending { it.rating }
    }

    // TimeSlot API
    fun getTimeSlots(): List<TimeSlot> = timeSlotsState

    fun updateSlotSelection(slotIndex: Int, isSelected: Boolean) {
        if (slotIndex in timeSlotsState.indices) {
            val slot = timeSlotsState[slotIndex]
            if (!slot.isBooked) {
                timeSlotsState[slotIndex] = slot.copy(isSelected = isSelected)
            }
        }
    }

    fun resetSlotSelections() {
        for (i in timeSlotsState.indices) {
            val slot = timeSlotsState[i]
            if (slot.isSelected) {
                timeSlotsState[i] = slot.copy(isSelected = false)
            }
        }
    }

    // Bookings API
    fun getBookings(): List<BookingItem> = bookingsState

    fun getBookingById(id: String): BookingItem? = bookingsState.find { it.id == id }

    fun addBooking(
        courtId: String,
        courtName: String = "",
        courtNumber: String = "Sân 02",
        address: String = "",
        date: String,
        timeSlot: String,
        durationHours: Int,
        totalPrice: Int,
    ): BookingItem {
        val court = getCourtById(courtId)
        val finalCourtName = courtName.ifBlank { court?.name ?: "BadmiSpot Club" }
        val finalAddress = address.ifBlank { court?.address ?: "Bình Thạnh, TP.HCM" }
        val newId = "bk_${System.currentTimeMillis().toString().takeLast(4)}"
        val codeNumber = (1000..9999).random()
        val booking = BookingItem(
            id = newId,
            bookingCode = "BMSP-$codeNumber",
            courtName = finalCourtName,
            courtNumber = courtNumber,
            address = finalAddress,
            date = date,
            timeSlot = timeSlot,
            durationHours = durationHours,
            totalPrice = totalPrice,
            status = "Sắp tới",
            qrCode = "BADMI-QR-$codeNumber",
        )
        bookingsState.add(0, booking)

        addNotification(
            title = "Đặt sân thành công!",
            message = "Lịch hẹn tại $finalCourtName ($courtNumber) ngày $date đã được xác nhận.",
            category = "Lịch đặt",
        )
        return booking
    }

    fun cancelBooking(bookingId: String): Boolean {
        val index = bookingsState.indexOfFirst { it.id == bookingId }
        if (index != -1) {
            val item = bookingsState[index]
            if (item.status != "Đã hủy") {
                bookingsState[index] = item.copy(status = "Đã hủy")
                addNotification(
                    title = "Lịch đặt đã hủy",
                    message = "Lịch hẹn ${item.bookingCode} tại ${item.courtName} đã được hủy thành công.",
                    category = "Lịch đặt",
                )
                return true
            }
        }
        return false
    }

    fun getUpcomingBookings(): List<BookingItem> = bookingsState.filter { it.status == "Sắp tới" }
    fun getCompletedBookings(): List<BookingItem> = bookingsState.filter { it.status == "Đã hoàn thành" }
    fun getCanceledBookings(): List<BookingItem> = bookingsState.filter { it.status == "Đã hủy" }

    // Matches API
    fun getMatches(): List<MatchItem> = matchesState

    fun getMatchById(id: String): MatchItem? = matchesState.find { it.id == id }

    fun createMatch(
        title: String,
        courtName: String,
        address: String,
        date: String,
        time: String,
        format: String = "Đánh đôi",
        skillLevel: String = "Trung bình+",
        maxPlayers: Int = 4,
        feePerPlayer: Int = 45000,
        hostName: String = userProfileState.value.name,
        description: String = "",
    ): MatchItem {
        val newId = "match_${System.currentTimeMillis().toString().takeLast(4)}"
        val match = MatchItem(
            id = newId,
            title = title,
            courtName = courtName,
            address = address,
            date = date,
            time = time,
            format = format,
            skillLevel = skillLevel,
            currentPlayers = 1,
            maxPlayers = maxPlayers,
            feePerPlayer = feePerPlayer,
            hostName = hostName,
            description = description,
            status = "Đang tìm người",
        )
        matchesState.add(0, match)

        val user = userProfileState.value
        userProfileState.value = user.copy(matchesPlayed = user.matchesPlayed + 1)

        addNotification(
            title = "Tạo trận đấu thành công",
            message = "Trận '$title' tại $courtName đã được đăng lên hệ thống.",
            category = "Trận đấu",
        )
        return match
    }

    fun joinMatch(matchId: String): Boolean {
        val index = matchesState.indexOfFirst { it.id == matchId }
        if (index != -1) {
            val match = matchesState[index]
            if (match.currentPlayers < match.maxPlayers) {
                val updatedCount = match.currentPlayers + 1
                val newStatus = if (updatedCount >= match.maxPlayers) "Sắp diễn ra" else "Đang tìm người"
                matchesState[index] = match.copy(
                    currentPlayers = updatedCount,
                    status = newStatus,
                )

                val user = userProfileState.value
                userProfileState.value = user.copy(matchesPlayed = user.matchesPlayed + 1)

                addNotification(
                    title = "Tham gia trận đấu",
                    message = "Bạn đã tham gia trận '${match.title}' tại ${match.courtName}.",
                    category = "Trận đấu",
                )
                return true
            }
        }
        return false
    }

    fun leaveMatch(matchId: String): Boolean {
        val index = matchesState.indexOfFirst { it.id == matchId }
        if (index != -1) {
            val match = matchesState[index]
            if (match.currentPlayers > 1) {
                matchesState[index] = match.copy(
                    currentPlayers = match.currentPlayers - 1,
                    status = "Đang tìm người",
                )
                return true
            }
        }
        return false
    }

    fun rateMatch(matchId: String, rating: Double, comment: String): Boolean {
        matchRatingsState.add(MatchRating(matchId, rating, comment))
        val index = matchesState.indexOfFirst { it.id == matchId }
        if (index != -1) {
            matchesState[index] = matchesState[index].copy(status = "Đã hoàn tất")
        }
        addNotification(
            title = "Đánh giá trận đấu",
            message = "Cảm ơn bạn đã gửi đánh giá cho trận đấu!",
            category = "Trận đấu",
        )
        return true
    }

    fun filterMatches(filter: MatchFilterCriteria): List<MatchItem> {
        return matchesState.filter { match ->
            val matchesQuery = filter.query.isBlank() ||
                match.title.contains(filter.query, ignoreCase = true) ||
                match.courtName.contains(filter.query, ignoreCase = true)
            val matchesFormat = filter.format == "Tất cả" || match.format.contains(filter.format, ignoreCase = true)
            val matchesSkill = filter.skillLevel == "Tất cả" || match.skillLevel.contains(filter.skillLevel, ignoreCase = true)
            matchesQuery && matchesFormat && matchesSkill
        }
    }

    fun getAvailableMatches(): List<MatchItem> = matchesState.filter { !it.isFull }

    // Products & Cart API
    fun getProducts(): List<ProductItem> = productsState

    fun getProductsByCategory(category: String): List<ProductItem> {
        if (category == "Tất cả") return productsState
        return productsState.filter { it.category.equals(category, ignoreCase = true) }
    }

    fun getProductById(id: String): ProductItem? = productsState.find { it.id == id }

    fun getCartItems(): List<CartItem> = cartItemsState

    fun addToCart(product: ProductItem, quantity: Int = 1, selectedWeight: String = "4U/G5"): Boolean {
        val index = cartItemsState.indexOfFirst { it.product.id == product.id && it.selectedWeight == selectedWeight }
        if (index != -1) {
            val item = cartItemsState[index]
            cartItemsState[index] = item.copy(quantity = item.quantity + quantity)
        } else {
            cartItemsState.add(CartItem(product = product, quantity = quantity, selectedWeight = selectedWeight))
        }
        addNotification(
            title = "Đã thêm vào giỏ hàng",
            message = "${product.name} đã được thêm vào giỏ hàng của bạn.",
            category = "Khuyến mãi",
        )
        return true
    }

    fun removeFromCart(productId: String): Boolean {
        return cartItemsState.removeAll { it.product.id == productId }
    }

    fun updateCartQuantity(productId: String, newQuantity: Int): Boolean {
        if (newQuantity <= 0) {
            return removeFromCart(productId)
        }
        val index = cartItemsState.indexOfFirst { it.product.id == productId }
        if (index != -1) {
            cartItemsState[index] = cartItemsState[index].copy(quantity = newQuantity)
            return true
        }
        return false
    }

    fun clearCart() {
        cartItemsState.clear()
    }

    fun getCartTotal(): Int = cartItemsState.sumOf { it.itemTotalPrice }
    fun getCartItemCount(): Int = cartItemsState.sumOf { it.quantity }

    fun getActiveBookingsCount(): Int = bookingsState.count { it.status == "Sắp tới" }
    fun getTotalAmountSpent(): Int = bookingsState.filter { it.status != "Đã hủy" }.sumOf { it.totalPrice }

    fun getUserStatisticsSummary(): com.example.badmintonbookingapp.model.UserStatsSummary {
        val user = userProfileState.value
        return com.example.badmintonbookingapp.model.UserStatsSummary(
            totalMatchesPlayed = user.matchesPlayed,
            totalBookings = bookingsState.size,
            totalSpent = getTotalAmountSpent(),
            favoriteCourtsCount = courtsState.count { it.isFavorite },
            punctualityRate = user.punctualityRate,
        )
    }

    fun getFilteredBookings(status: String = "Tất cả", query: String = ""): List<BookingItem> {
        return bookingsState.filter { booking ->
            val matchesStatus = status == "Tất cả" || booking.status.equals(status, ignoreCase = true)
            val matchesQuery = query.isBlank() ||
                booking.courtName.contains(query, ignoreCase = true) ||
                booking.bookingCode.contains(query, ignoreCase = true) ||
                booking.courtNumber.contains(query, ignoreCase = true)
            matchesStatus && matchesQuery
        }
    }

    // Vouchers API
    fun getVouchers(): List<VoucherItem> = vouchersState

    fun selectVoucher(code: String): VoucherItem? {
        var selected: VoucherItem? = null
        for (i in vouchersState.indices) {
            val voucher = vouchersState[i]
            val isThis = voucher.code == code
            val updated = voucher.copy(isSelected = isThis && !voucher.isSelected)
            vouchersState[i] = updated
            if (updated.isSelected) {
                selected = updated
            }
        }
        return selected
    }

    fun getSelectedVoucher(): VoucherItem? = vouchersState.find { it.isSelected }

    fun getBestAvailableVoucher(amount: Int): VoucherItem? {
        return vouchersState
            .filter { it.isValidFor(amount) }
            .maxByOrNull { it.discountAmount }
    }

    fun calculateDiscount(code: String, amount: Int): Int {
        val voucher = vouchersState.find { it.code == code } ?: return 0
        return if (voucher.isValidFor(amount)) voucher.discountAmount else 0
    }

    // Notifications API
    fun getNotifications(): List<NotificationItem> = notificationsState

    fun markNotificationAsRead(id: String): Boolean {
        val index = notificationsState.indexOfFirst { it.id == id }
        if (index != -1) {
            notificationsState[index] = notificationsState[index].copy(isRead = true)
            return true
        }
        return false
    }

    fun markAllNotificationsAsRead() {
        for (i in notificationsState.indices) {
            if (!notificationsState[i].isRead) {
                notificationsState[i] = notificationsState[i].copy(isRead = true)
            }
        }
    }

    fun addNotification(title: String, message: String, category: String): NotificationItem {
        val notif = NotificationItem(
            id = "notif_${System.currentTimeMillis().toString().takeLast(4)}",
            title = title,
            message = message,
            timeAgo = "Vừa xong",
            category = category,
            isRead = false,
        )
        notificationsState.add(0, notif)
        return notif
    }

    fun getUnreadNotificationCount(): Int = notificationsState.count { !it.isRead }
}
