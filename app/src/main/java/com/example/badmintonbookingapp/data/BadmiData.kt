package com.example.badmintonbookingapp.data

import com.example.badmintonbookingapp.model.BookingItem
import com.example.badmintonbookingapp.model.CartItem
import com.example.badmintonbookingapp.model.Court
import com.example.badmintonbookingapp.model.CourtFilterCriteria
import com.example.badmintonbookingapp.model.MatchFilterCriteria
import com.example.badmintonbookingapp.model.MatchItem
import com.example.badmintonbookingapp.model.NotificationItem
import com.example.badmintonbookingapp.model.ProductItem
import com.example.badmintonbookingapp.model.TimeSlot
import com.example.badmintonbookingapp.model.UserProfile
import com.example.badmintonbookingapp.model.VoucherItem

/**
 * Facade object for accessing badminton app data reactively.
 * Delegates all state operations to [BadmiRepository].
 */
object BadmiData {
    val repository: BadmiRepository get() = BadmiRepository

    // Dynamic Reactive Getters
    val sampleUser: UserProfile get() = BadmiRepository.userProfileState.value
    val sampleCourts: List<Court> get() = BadmiRepository.courtsState
    val sampleTimeSlots: List<TimeSlot> get() = BadmiRepository.timeSlotsState
    val sampleMatches: List<MatchItem> get() = BadmiRepository.matchesState
    val sampleProducts: List<ProductItem> get() = BadmiRepository.productsState
    val sampleCart: List<CartItem> get() = BadmiRepository.cartItemsState
    val sampleBookings: List<BookingItem> get() = BadmiRepository.bookingsState
    val sampleVouchers: List<VoucherItem> get() = BadmiRepository.vouchersState
    val sampleNotifications: List<NotificationItem> get() = BadmiRepository.notificationsState

    fun resetDataToSeed() = BadmiRepository.resetDataToSeed()

    // User Profile API
    fun updateUserProfile(
        name: String = sampleUser.name,
        phone: String = sampleUser.phone,
        email: String = sampleUser.email,
        skillLevel: String = sampleUser.skillLevel,
        playStyle: String = sampleUser.playStyle,
        handedness: String = sampleUser.handedness,
    ): UserProfile = BadmiRepository.updateUserProfile(name, phone, email, skillLevel, playStyle, handedness)

    // Court & Slot API
    fun toggleFavorite(courtId: String): Boolean = BadmiRepository.toggleFavorite(courtId)
    fun searchCourts(filter: CourtFilterCriteria): List<Court> = BadmiRepository.searchCourts(filter)
    fun getSortedCourts(sortBy: com.example.badmintonbookingapp.model.CourtSortOption = com.example.badmintonbookingapp.model.CourtSortOption.DISTANCE): List<Court> = BadmiRepository.getSortedCourts(sortBy)
    fun getNearbyCourts(maxDistanceKm: Double = 3.0): List<Court> = BadmiRepository.getNearbyCourts(maxDistanceKm)
    fun getTopRatedCourts(minRating: Double = 4.8): List<Court> = BadmiRepository.getTopRatedCourts(minRating)
    fun updateSlotSelection(slotIndex: Int, isSelected: Boolean) = BadmiRepository.updateSlotSelection(slotIndex, isSelected)
    fun resetSlotSelections() = BadmiRepository.resetSlotSelections()

    // Booking API
    fun addBooking(
        courtId: String,
        courtName: String = "",
        courtNumber: String = "Sân 02",
        address: String = "",
        date: String,
        timeSlot: String,
        durationHours: Int,
        totalPrice: Int,
    ): BookingItem = BadmiRepository.addBooking(courtId, courtName, courtNumber, address, date, timeSlot, durationHours, totalPrice)

    fun cancelBooking(bookingId: String): Boolean = BadmiRepository.cancelBooking(bookingId)
    fun getActiveBookingsCount(): Int = BadmiRepository.getActiveBookingsCount()
    fun getTotalAmountSpent(): Int = BadmiRepository.getTotalAmountSpent()
    fun getUserStatisticsSummary(): com.example.badmintonbookingapp.model.UserStatsSummary = BadmiRepository.getUserStatisticsSummary()
    fun getFilteredBookings(status: String = "Tất cả", query: String = ""): List<BookingItem> = BadmiRepository.getFilteredBookings(status, query)

    // Match API
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
        hostName: String = sampleUser.name,
        description: String = "",
    ): MatchItem = BadmiRepository.createMatch(title, courtName, address, date, time, format, skillLevel, maxPlayers, feePerPlayer, hostName, description)

    fun joinMatch(matchId: String): Boolean = BadmiRepository.joinMatch(matchId)
    fun leaveMatch(matchId: String): Boolean = BadmiRepository.leaveMatch(matchId)
    fun rateMatch(matchId: String, rating: Double, comment: String): Boolean = BadmiRepository.rateMatch(matchId, rating, comment)
    fun filterMatches(filter: MatchFilterCriteria): List<MatchItem> = BadmiRepository.filterMatches(filter)
    fun getAvailableMatches(): List<MatchItem> = BadmiRepository.getAvailableMatches()

    // Shop & Cart API
    fun addToCart(product: ProductItem, quantity: Int = 1, selectedWeight: String = "4U/G5"): Boolean = BadmiRepository.addToCart(product, quantity, selectedWeight)
    fun removeFromCart(productId: String): Boolean = BadmiRepository.removeFromCart(productId)
    fun updateCartQuantity(productId: String, newQuantity: Int): Boolean = BadmiRepository.updateCartQuantity(productId, newQuantity)
    fun clearCart() = BadmiRepository.clearCart()
    fun getCartTotal(): Int = BadmiRepository.getCartTotal()
    fun getCartItemCount(): Int = BadmiRepository.getCartItemCount()
    fun getProductsByCategory(category: String): List<ProductItem> = BadmiRepository.getProductsByCategory(category)

    // Voucher API
    fun selectVoucher(code: String): VoucherItem? = BadmiRepository.selectVoucher(code)
    fun getSelectedVoucher(): VoucherItem? = BadmiRepository.getSelectedVoucher()
    fun getBestAvailableVoucher(amount: Int): VoucherItem? = BadmiRepository.getBestAvailableVoucher(amount)
    fun calculateDiscount(code: String, amount: Int): Int = BadmiRepository.calculateDiscount(code, amount)

    // Notification API
    fun markNotificationAsRead(id: String): Boolean = BadmiRepository.markNotificationAsRead(id)
    fun markAllNotificationsAsRead() = BadmiRepository.markAllNotificationsAsRead()
    fun addNotification(title: String, message: String, category: String): NotificationItem = BadmiRepository.addNotification(title, message, category)
    fun getUnreadNotificationCount(): Int = BadmiRepository.getUnreadNotificationCount()
}
