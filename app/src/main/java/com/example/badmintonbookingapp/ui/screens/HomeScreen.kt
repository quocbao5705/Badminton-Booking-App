package com.example.badmintonbookingapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.badmintonbookingapp.data.BadmiData
import com.example.badmintonbookingapp.model.Court
import com.example.badmintonbookingapp.model.MatchItem
import com.example.badmintonbookingapp.ui.components.*
import com.example.badmintonbookingapp.ui.theme.*

@Composable
fun HomeScreen(
    onNavigateToCourtDetail: (String) -> Unit,
    onNavigateToBookingSlot: (String) -> Unit,
    onNavigateToMatchDetail: (String) -> Unit,
    onNavigateToCreateMatch: () -> Unit,
    onNavigateToNotifications: () -> Unit,
    onNavigateToSearch: () -> Unit,
    onNavigateToShop: () -> Unit,
    onNavigateToVouchers: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategoryIndex by remember { mutableIntStateOf(0) }
    val categories = listOf("Tất cả", "Gần bạn", "Sân thảm BWF", "Có HLV", "Mở 24/7", "Giá tốt")

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BadmiBackground)
            .verticalScroll(rememberScrollState())
            .padding(bottom = 90.dp)
    ) {
        // Top Header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .background(BadmiPrimaryContainer, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "M",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = BadmiPrimaryDark
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = "Chào Minh 👋",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = BadmiTextPrimary
                    )
                    Text(
                        text = "Hôm nay bạn muốn chơi ở đâu?",
                        fontSize = 12.sp,
                        color = BadmiTextSecondary
                    )
                }
            }

            IconButton(
                onClick = onNavigateToNotifications,
                modifier = Modifier
                    .size(42.dp)
                    .background(BadmiSurface, CircleShape)
                    .clip(CircleShape)
            ) {
                BadgedBox(
                    badge = {
                        Badge(containerColor = BadmiDanger) {
                            Text("2", color = Color.White, fontSize = 10.sp)
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Notifications,
                        contentDescription = "Thông báo",
                        tint = BadmiTextPrimary
                    )
                }
            }
        }

        // Search Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            BadmiSearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it },
                onFilterClick = onNavigateToSearch
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Hero Banner Promo
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .height(145.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(
                    Brush.horizontalGradient(
                        colors = listOf(BadmiPrimaryDark, BadmiPrimary, BadmiMint)
                    )
                )
                .clickable { onNavigateToCourtDetail("court_1") }
                .padding(18.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.68f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                BadmiBadge(
                    text = "Ưu đãi tuần này",
                    containerColor = BadmiSurface.copy(alpha = 0.25f),
                    contentColor = BadmiTextWhite
                )
                Column {
                    Text(
                        text = "Đặt sân nhanh chỉ 3 chạm",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = BadmiTextWhite
                    )
                    Text(
                        text = "Giảm ngay 20K cho khung giờ vàng",
                        fontSize = 12.sp,
                        color = BadmiTextWhite.copy(alpha = 0.85f)
                    )
                }
                Surface(
                    shape = RoundedCornerShape(15.dp),
                    color = BadmiSurface,
                    modifier = Modifier.padding(top = 4.dp)
                ) {
                    Text(
                        text = "Đặt sân ngay →",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Bold,
                        color = BadmiPrimaryDark,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                    )
                }
            }

            // Shuttlecock decoration graphic
            Icon(
                imageVector = Icons.Default.SportsTennis,
                contentDescription = null,
                tint = Color.White.copy(alpha = 0.25f),
                modifier = Modifier
                    .size(110.dp)
                    .align(Alignment.BottomEnd)
                    .offset(x = 10.dp, y = 10.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Quick Action Shortcuts (Đặt sân, Ghép trận, Cửa hàng, Voucher)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            QuickActionItem(
                icon = Icons.Default.SportsTennis,
                label = "Đặt sân",
                bgColor = BadmiPrimaryLight,
                iconColor = BadmiPrimary,
                onClick = onNavigateToSearch
            )
            QuickActionItem(
                icon = Icons.Default.Groups,
                label = "Ghép trận",
                bgColor = BadmiMintSoft,
                iconColor = BadmiPrimaryDark,
                onClick = onNavigateToCreateMatch
            )
            QuickActionItem(
                icon = Icons.Default.ShoppingBag,
                label = "Cửa hàng",
                bgColor = BadmiOrangeLight,
                iconColor = BadmiOrange,
                onClick = onNavigateToShop
            )
            QuickActionItem(
                icon = Icons.Default.ConfirmationNumber,
                label = "Mã ưu đãi",
                bgColor = BadmiBlueLight,
                iconColor = BadmiBlue,
                onClick = onNavigateToVouchers
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Category Filter Chips
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            categories.forEachIndexed { index, title ->
                BadmiChip(
                    text = title,
                    isSelected = selectedCategoryIndex == index,
                    onClick = { selectedCategoryIndex = index }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Section: Sân nổi bật gần bạn
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Sân nổi bật gần bạn",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = BadmiTextPrimary
            )
            Text(
                text = "Xem tất cả",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = BadmiPrimary,
                modifier = Modifier.clickable { onNavigateToSearch() }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Court Cards
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            BadmiData.sampleCourts.take(2).forEach { court ->
                CourtSummaryCard(
                    court = court,
                    onClick = { onNavigateToCourtDetail(court.id) },
                    onBookClick = { onNavigateToBookingSlot(court.id) }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Section: Trận đấu đang tìm người
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Trận đấu đang tìm người",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = BadmiTextPrimary
            )
            Text(
                text = "Tạo trận",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = BadmiPrimary,
                modifier = Modifier.clickable { onNavigateToCreateMatch() }
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Match Card Preview
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            BadmiData.sampleMatches.take(2).forEach { match ->
                MatchSummaryCard(
                    match = match,
                    onClick = { onNavigateToMatchDetail(match.id) }
                )
            }
        }
    }
}

@Composable
fun QuickActionItem(
    icon: ImageVector,
    label: String,
    bgColor: Color,
    iconColor: Color,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.clickable { onClick() }
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .background(bgColor, RoundedCornerShape(18.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = iconColor,
                modifier = Modifier.size(26.dp)
            )
        }
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = BadmiTextPrimary
        )
    }
}

@Composable
fun CourtSummaryCard(
    court: Court,
    onClick: () -> Unit,
    onBookClick: () -> Unit
) {
    BadmiCard(onClick = onClick) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            // Court Image Thumbnail Placeholder
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(BadmiPrimaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.SportsTennis,
                    contentDescription = null,
                    tint = BadmiPrimary,
                    modifier = Modifier.size(36.dp)
                )
                Surface(
                    color = BadmiPrimaryDark,
                    shape = RoundedCornerShape(bottomStart = 8.dp),
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    Text(
                        text = "${court.distanceKm} km",
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = court.name,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = BadmiTextPrimary,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f)
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            tint = BadmiStar,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = court.rating.toString(),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = BadmiTextPrimary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = court.address,
                    fontSize = 12.sp,
                    color = BadmiTextSecondary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${formatCurrency(court.pricePerHour)}/giờ",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = BadmiPrimary
                    )

                    Surface(
                        color = BadmiPrimaryLight,
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.clickable { onBookClick() }
                    ) {
                        Text(
                            text = "Đặt ngay",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = BadmiPrimary,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MatchSummaryCard(
    match: MatchItem,
    onClick: () -> Unit
) {
    BadmiCard(onClick = onClick) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BadmiBadge(text = match.skillLevel)
            Surface(
                color = BadmiMintSoft,
                shape = RoundedCornerShape(6.dp)
            ) {
                Text(
                    text = "${match.currentPlayers}/${match.maxPlayers} người",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = BadmiPrimaryDark,
                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = match.title,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = BadmiTextPrimary
        )

        Spacer(modifier = Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.LocationOn,
                contentDescription = null,
                tint = BadmiTextMuted,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = match.courtName,
                fontSize = 12.sp,
                color = BadmiTextSecondary
            )
        }

        Spacer(modifier = Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                imageVector = Icons.Default.AccessTime,
                contentDescription = null,
                tint = BadmiTextMuted,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "${match.date} • ${match.time}",
                fontSize = 12.sp,
                color = BadmiTextSecondary
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        HorizontalDivider(color = BadmiBorderLight)
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "${formatCurrency(match.feePerPlayer)} / người",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = BadmiPrimary
            )
            Text(
                text = "Xem chi tiết →",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                color = BadmiPrimary
            )
        }
    }
}
