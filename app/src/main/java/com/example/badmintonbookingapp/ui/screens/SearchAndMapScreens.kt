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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.badmintonbookingapp.data.BadmiData
import com.example.badmintonbookingapp.model.Court
import com.example.badmintonbookingapp.ui.components.*
import com.example.badmintonbookingapp.ui.theme.*

@Composable
fun SearchFilterScreen(
    onBackClick: () -> Unit,
    onApplyFilter: () -> Unit
) {
    var distanceKm by remember { mutableFloatStateOf(5f) }
    var pricePerHour by remember { mutableFloatStateOf(150000f) }
    var selectedSurface by remember { mutableStateOf("Thảm BWF") }
    val amenities = listOf("Bãi đỗ ô tô", "Máy lạnh", "Tắm nóng lạnh", "Căng vợt", "Wifi")
    var selectedAmenities by remember { mutableStateOf(setOf("Bãi đỗ ô tô", "Máy lạnh")) }

    Scaffold(
        topBar = {
            BadmiTopBar(
                title = "Tìm kiếm & Bộ lọc sân",
                subtitle = "Tùy biến theo nhu cầu của bạn",
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
                        text = "Áp dụng bộ lọc (24 sân phù hợp)",
                        onClick = onApplyFilter
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
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            // Distance Slider
            BadmiCard {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Khoảng cách tối đa", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text("${distanceKm.toInt()} km", fontWeight = FontWeight.Bold, color = BadmiPrimary)
                }
                Slider(
                    value = distanceKm,
                    onValueChange = { distanceKm = it },
                    valueRange = 1f..25f,
                    colors = SliderDefaults.colors(
                        thumbColor = BadmiPrimary,
                        activeTrackColor = BadmiPrimary
                    )
                )
            }

            // Price Slider
            BadmiCard {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Mức giá tối đa", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Text("${formatCurrency(pricePerHour.toInt())}/h", fontWeight = FontWeight.Bold, color = BadmiPrimary)
                }
                Slider(
                    value = pricePerHour,
                    onValueChange = { pricePerHour = it },
                    valueRange = 60000f..300000f,
                    colors = SliderDefaults.colors(
                        thumbColor = BadmiPrimary,
                        activeTrackColor = BadmiPrimary
                    )
                )
            }

            // Surface type
            BadmiCard {
                Text("Loại mặt sân", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    listOf("Thảm BWF", "Sàn gỗ", "Thảm PVC").forEach { surface ->
                        BadmiChip(
                            text = surface,
                            isSelected = selectedSurface == surface,
                            onClick = { selectedSurface = surface },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // Amenities checkboxes
            BadmiCard {
                Text("Tiện ích kèm theo", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(10.dp))
                amenities.forEach { item ->
                    val isChecked = selectedAmenities.contains(item)
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                selectedAmenities = if (isChecked) selectedAmenities - item else selectedAmenities + item
                            }
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = isChecked,
                            onCheckedChange = { checked ->
                                selectedAmenities = if (checked) selectedAmenities + item else selectedAmenities - item
                            },
                            colors = CheckboxDefaults.colors(checkedColor = BadmiPrimary)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = item, fontSize = 13.sp, color = BadmiTextPrimary)
                    }
                }
            }
        }
    }
}

@Composable
fun SearchResultsScreen(
    onBackClick: () -> Unit,
    onNavigateToCourtDetail: (String) -> Unit,
    onNavigateToBookingSlot: (String) -> Unit,
    onNavigateToMap: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("Bình Thạnh") }

    Scaffold(
        topBar = {
            BadmiTopBar(
                title = "Kết quả tìm kiếm",
                subtitle = "Tìm thấy 24 sân quanh bạn",
                onBackClick = onBackClick,
                actions = {
                    IconButton(onClick = onNavigateToMap) {
                        Icon(
                            imageVector = Icons.Default.Map,
                            contentDescription = "Bản đồ",
                            tint = BadmiPrimary
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BadmiBackground)
                .padding(innerPadding)
                .padding(bottom = 80.dp)
        ) {
            Box(modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)) {
                BadmiSearchBar(
                    query = searchQuery,
                    onQueryChange = { searchQuery = it },
                    placeholder = "Tìm sân gần đây..."
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                BadmiData.sampleCourts.forEach { court ->
                    CourtSummaryCard(
                        court = court,
                        onClick = { onNavigateToCourtDetail(court.id) },
                        onBookClick = { onNavigateToBookingSlot(court.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun CourtMapScreen(
    onBackClick: () -> Unit,
    onNavigateToCourtDetail: (String) -> Unit
) {
    val selectedCourt = BadmiData.sampleCourts[0]

    Scaffold(
        topBar = {
            BadmiTopBar(
                title = "Bản đồ sân cầu lông",
                subtitle = "Vị trí trực quan gần bạn",
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE5EDE8)) // Map simulated background
                .padding(innerPadding)
        ) {
            // Simulated Map Grid with street lines & pins
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceAround
            ) {
                repeat(5) {
                    HorizontalDivider(color = Color(0xFFD4E2D8), thickness = 8.dp)
                }
            }

            // Map Pins
            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(x = (-30).dp, y = (-40).dp)
            ) {
                Surface(
                    color = BadmiPrimary,
                    shape = CircleShape,
                    shadowElevation = 6.dp,
                    modifier = Modifier.size(46.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.SportsTennis,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .align(Alignment.Center)
                    .offset(x = 80.dp, y = 50.dp)
            ) {
                Surface(
                    color = BadmiMint,
                    shape = CircleShape,
                    shadowElevation = 4.dp,
                    modifier = Modifier.size(38.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            // Bottom Selected Court Floating Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(20.dp)
            ) {
                BadmiCard(onClick = { onNavigateToCourtDetail(selectedCourt.id) }) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            BadmiBadge(text = "Đang chọn • 1.2 km")
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = selectedCourt.name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = BadmiTextPrimary
                            )
                            Text(
                                text = selectedCourt.address,
                                fontSize = 12.sp,
                                color = BadmiTextSecondary
                            )
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "${formatCurrency(selectedCourt.pricePerHour)}/giờ",
                                fontWeight = FontWeight.Bold,
                                color = BadmiPrimary,
                                fontSize = 14.sp
                            )
                        }

                        Button(
                            onClick = { onNavigateToCourtDetail(selectedCourt.id) },
                            shape = RoundedCornerShape(20.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = BadmiPrimary)
                        ) {
                            Text("Xem sân", fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CourtDetailScreen(
    courtId: String,
    onBackClick: () -> Unit,
    onBookSlotClick: () -> Unit
) {
    val court = BadmiData.sampleCourts.find { it.id == courtId } ?: BadmiData.sampleCourts[0]
    var isFavorite by remember { mutableStateOf(court.isFavorite) }

    Scaffold(
        bottomBar = {
            Surface(
                color = BadmiSurface,
                shadowElevation = 8.dp,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 14.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("Giá thuê sân", fontSize = 12.sp, color = BadmiTextSecondary)
                        Text(
                            text = "${formatCurrency(court.pricePerHour)}/h",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = BadmiPrimary
                        )
                    }

                    Button(
                        onClick = onBookSlotClick,
                        shape = RoundedCornerShape(25.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = BadmiPrimary),
                        modifier = Modifier.height(48.dp)
                    ) {
                        Text("Đặt sân ngay →", fontWeight = FontWeight.Bold)
                    }
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
        ) {
            // Court Big Hero Image Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(230.dp)
                    .background(BadmiPrimaryDark)
            ) {
                BadmintonRacketIconPlaceholder(
                    modifier = Modifier.fillMaxSize(),
                    tint = BadmiMintSoft
                )

                // Back and Favorite Buttons
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    IconButton(
                        onClick = onBackClick,
                        modifier = Modifier.background(Color.Black.copy(alpha = 0.35f), CircleShape)
                    ) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = Color.White)
                    }

                    IconButton(
                        onClick = { isFavorite = !isFavorite },
                        modifier = Modifier.background(Color.Black.copy(alpha = 0.35f), CircleShape)
                    ) {
                        Icon(
                            imageVector = if (isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = "Favorite",
                            tint = if (isFavorite) BadmiDanger else Color.White
                        )
                    }
                }
            }

            // Court Details Content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BadmiBadge(text = court.tag)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Star, contentDescription = null, tint = BadmiStar, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(4.dp))
                        Text("${court.rating} (${court.reviewCount} đánh giá)", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }

                Text(
                    text = court.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = BadmiTextPrimary
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.LocationOn, contentDescription = null, tint = BadmiPrimary, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = court.address, fontSize = 13.sp, color = BadmiTextSecondary)
                }

                HorizontalDivider(color = BadmiBorderLight)

                Text("Tiện ích tại sân", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    court.amenities.take(3).forEach { item ->
                        BadmiBadge(text = item, containerColor = BadmiSurfaceVariant, contentColor = BadmiTextPrimary)
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))
                BadmiCard {
                    Text("Thông số sân bãi", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    InfoRow(label = "Quy mô", value = "${court.totalCourts} sân tiêu chuẩn")
                    InfoRow(label = "Chất liệu mặt sân", value = court.surfaceType)
                    InfoRow(label = "Giờ mở cửa", value = court.openingHours)
                }
            }
        }
    }
}
