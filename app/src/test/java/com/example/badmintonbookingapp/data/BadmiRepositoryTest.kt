package com.example.badmintonbookingapp.data

import com.example.badmintonbookingapp.model.CourtFilterCriteria
import com.example.badmintonbookingapp.model.CourtSortOption
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class BadmiRepositoryTest {

    @Before
    fun setUp() {
        BadmiData.resetDataToSeed()
    }

    @Test
    fun testInitialDataLoadedCorrectly() {
        val user = BadmiData.sampleUser
        assertEquals("Minh", user.name)
        assertTrue(BadmiData.sampleCourts.isNotEmpty())
        assertTrue(BadmiData.sampleMatches.isNotEmpty())
        assertTrue(BadmiData.sampleProducts.isNotEmpty())
    }

    @Test
    fun testUserProfileUpdate() {
        val updated = BadmiData.updateUserProfile(
            name = "Nguyễn Văn Minh",
            phone = "0987654321",
            email = "minh.new@gmail.com",
            skillLevel = "Khá (B)",
            playStyle = "Tấn công",
            handedness = "Tay trái"
        )
        assertEquals("Nguyễn Văn Minh", updated.name)
        assertEquals("0987654321", updated.phone)
        assertEquals("Tay trái", updated.handedness)
        assertEquals("Nguyễn Văn Minh", BadmiData.sampleUser.name)
    }

    @Test
    fun testCourtFavoriteToggle() {
        val courtId = "court_1"
        val initialFavorite = BadmiData.sampleCourts.find { it.id == courtId }?.isFavorite ?: false
        val newFavorite = BadmiData.toggleFavorite(courtId)
        assertEquals(newFavorite, !initialFavorite)
        val updatedCourt = BadmiData.sampleCourts.find { it.id == courtId }
        assertEquals(newFavorite, updatedCourt?.isFavorite)
    }

    @Test
    fun testCourtSearchAndSort() {
        val results = BadmiData.searchCourts(
            CourtFilterCriteria(
                query = "Bình Thạnh",
                district = "Tất cả",
                maxPrice = 200000,
                sortBy = CourtSortOption.DISTANCE
            )
        )
        assertTrue(results.isNotEmpty())
        assertTrue(results.all { it.district.contains("Bình Thạnh") || it.name.contains("Bình Thạnh") || it.address.contains("Bình Thạnh") })
    }

    @Test
    fun testBookingAddAndCancel() {
        val initialBookingCount = BadmiData.sampleBookings.size
        val booking = BadmiData.addBooking(
            courtId = "court_1",
            courtName = "Sân Cầu Lông Test",
            courtNumber = "Sân 01",
            address = "Test Address",
            date = "25/10/2026",
            timeSlot = "19:00 - 21:00",
            durationHours = 2,
            totalPrice = 240000
        )
        assertNotNull(booking)
        assertEquals(initialBookingCount + 1, BadmiData.sampleBookings.size)
        assertEquals("Sắp tới", booking.status)

        val cancelSuccess = BadmiData.cancelBooking(booking.id)
        assertTrue(cancelSuccess)
        val updatedBooking = BadmiData.sampleBookings.find { it.id == booking.id }
        assertEquals("Đã hủy", updatedBooking?.status)
    }

    @Test
    fun testMatchCreateJoinAndRate() {
        val initialMatchCount = BadmiData.sampleMatches.size
        val newMatch = BadmiData.createMatch(
            title = "Trận test đơn nam",
            courtName = "BadmiSpot Club",
            address = "182 Bạch Đằng",
            date = "Hôm nay",
            time = "18:00 - 19:30",
            format = "Đơn nam",
            skillLevel = "Trung bình",
            maxPlayers = 2,
            feePerPlayer = 50000,
            description = "Trận thử nghiệm unit test"
        )
        assertNotNull(newMatch)
        assertEquals(initialMatchCount + 1, BadmiData.sampleMatches.size)
        assertEquals(1, newMatch.currentPlayers)

        val joinSuccess = BadmiData.joinMatch(newMatch.id)
        assertTrue(joinSuccess)
        val updatedMatch = BadmiData.sampleMatches.find { it.id == newMatch.id }
        assertEquals(2, updatedMatch?.currentPlayers)

        val rateSuccess = BadmiData.rateMatch(newMatch.id, 5.0, "Trận đấu rất hay!")
        assertTrue(rateSuccess)
        val ratedMatch = BadmiData.sampleMatches.find { it.id == newMatch.id }
        assertEquals("Đã hoàn tất", ratedMatch?.status)
    }

    @Test
    fun testCartAddUpdateRemoveAndClear() {
        val product = BadmiData.sampleProducts[0]
        BadmiData.clearCart()
        assertEquals(0, BadmiData.sampleCart.size)

        BadmiData.addToCart(product, quantity = 2, selectedWeight = "4U/G5")
        assertEquals(1, BadmiData.sampleCart.size)
        assertEquals(2, BadmiData.sampleCart[0].quantity)

        BadmiData.updateCartQuantity(product.id, 3)
        assertEquals(3, BadmiData.sampleCart[0].quantity)

        BadmiData.removeFromCart(product.id)
        assertEquals(0, BadmiData.sampleCart.size)
    }

    @Test
    fun testVoucherSelectionAndDiscount() {
        val voucherCode = "GIOVANG50"
        val selectedVoucher = BadmiData.selectVoucher(voucherCode)
        assertNotNull(selectedVoucher)
        assertTrue(selectedVoucher!!.isSelected)

        val discountAmount = BadmiData.calculateDiscount("BADMI20K", 200000)
        assertEquals(20000, discountAmount)
    }

    @Test
    fun testNotificationsMarkRead() {
        val notif = BadmiData.addNotification("Tiêu đề test", "Nội dung thông báo test", "Hệ thống")
        assertFalse(notif.isRead)

        BadmiData.markNotificationAsRead(notif.id)
        val readNotif = BadmiData.sampleNotifications.find { it.id == notif.id }
        assertTrue(readNotif?.isRead ?: false)

        BadmiData.markAllNotificationsAsRead()
        assertTrue(BadmiData.sampleNotifications.all { it.isRead })
    }

    @Test
    fun testNearbyAndTopRatedCourts() {
        val nearby = BadmiData.getNearbyCourts(3.0)
        assertTrue(nearby.all { it.distanceKm <= 3.0 })

        val topRated = BadmiData.getTopRatedCourts(4.8)
        assertTrue(topRated.all { it.rating >= 4.8 })
    }

    @Test
    fun testProductsByCategory() {
        val rackets = BadmiData.getProductsByCategory("Vợt cầu lông")
        assertTrue(rackets.isNotEmpty())
        assertTrue(rackets.all { it.category == "Vợt cầu lông" })
    }

    @Test
    fun testUserStatisticsSummary() {
        val stats = BadmiData.getUserStatisticsSummary()
        assertNotNull(stats)
        assertTrue(stats.totalBookings >= 0)
        assertTrue(stats.favoriteCourtsCount >= 0)
    }

    @Test
    fun testFilteredBookings() {
        val filtered = BadmiData.getFilteredBookings(status = "Sắp tới")
        assertTrue(filtered.all { it.status == "Sắp tới" })
    }
}
