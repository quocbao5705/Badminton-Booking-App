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
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.badmintonbookingapp.data.BadmiData
import com.example.badmintonbookingapp.model.MatchItem
import com.example.badmintonbookingapp.ui.components.*
import com.example.badmintonbookingapp.ui.theme.*

@Composable
fun MatchListScreen(
    onNavigateToMatchDetail: (String) -> Unit,
    onNavigateToCreateMatch: () -> Unit
) {
    var selectedFilter by remember { mutableStateOf("Tất cả") }
    val filters = listOf("Tất cả", "Gần bạn", "Trình độ phù hợp", "Hôm nay")

    Scaffold(
        topBar = {
            BadmiTopBar(
                title = "Ghép trận & Giao lưu",
                subtitle = "Tìm đồng đội, bắt cặp so tài",
                showBack = false
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onNavigateToCreateMatch,
                containerColor = BadmiPrimary,
                contentColor = BadmiTextWhite,
                shape = RoundedCornerShape(25.dp),
                modifier = Modifier.padding(bottom = 60.dp)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(6.dp))
                Text("Tạo kèo mới", fontWeight = FontWeight.Bold)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BadmiBackground)
                .padding(innerPadding)
                .padding(bottom = 80.dp)
        ) {
            // Filter Chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                filters.forEach { filter ->
                    BadmiChip(
                        text = filter,
                        isSelected = selectedFilter == filter,
                        onClick = { selectedFilter = filter }
                    )
                }
            }

            // Matches List
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                BadmiData.sampleMatches.forEach { match ->
                    MatchSummaryCard(
                        match = match,
                        onClick = { onNavigateToMatchDetail(match.id) }
                    )
                }
                Spacer(modifier = Modifier.height(30.dp))
            }
        }
    }
}

@Composable
fun MatchDetailScreen(
    matchId: String,
    onBackClick: () -> Unit,
    onJoinClick: () -> Unit,
    onGoToWaitingRoom: () -> Unit,
    onRateMatch: () -> Unit
) {
    val match = BadmiData.sampleMatches.find { it.id == matchId } ?: BadmiData.sampleMatches[0]

    Scaffold(
        topBar = {
            BadmiTopBar(
                title = "Chi tiết trận đấu",
                subtitle = match.format,
                onBackClick = onBackClick
            )
        },
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
                        Text(
                            text = "Chi phí mỗi bạn",
                            fontSize = 12.sp,
                            color = BadmiTextSecondary
                        )
                        Text(
                            text = formatCurrency(match.feePerPlayer),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = BadmiPrimary
                        )
                    }

                    Button(
                        onClick = onJoinClick,
                        shape = RoundedCornerShape(25.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BadmiPrimary,
                            contentColor = BadmiTextWhite
                        ),
                        modifier = Modifier.height(48.dp)
                    ) {
                        Text(
                            text = "Tham gia trận này 🏸",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
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
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Match Header Card
            BadmiCard {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BadmiBadge(text = match.skillLevel)
                    BadmiBadge(
                        text = "Còn ${match.maxPlayers - match.currentPlayers} chỗ",
                        containerColor = BadmiMintSoft,
                        contentColor = BadmiPrimaryDark
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = match.title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = BadmiTextPrimary
                )

                Spacer(modifier = Modifier.height(8.dp))
                InfoRow(label = "Địa điểm", value = match.courtName)
                InfoRow(label = "Địa chỉ", value = match.address)
                InfoRow(label = "Thời gian", value = "${match.date} • ${match.time}")
                InfoRow(label = "Thể thức", value = match.format)
            }

            // Host Card
            BadmiCard {
                Text(
                    text = "Người tổ chức kèo",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = BadmiTextPrimary
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .background(BadmiPrimaryContainer, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("T", fontWeight = FontWeight.Bold, color = BadmiPrimaryDark)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            text = match.hostName,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = BadmiTextPrimary
                        )
                        Text(
                            text = "Trình độ: Trung bình khá • 98% uy tín",
                            fontSize = 12.sp,
                            color = BadmiTextSecondary
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "\"${match.description}\"",
                    fontSize = 13.sp,
                    color = BadmiTextSecondary,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(BadmiSurfaceVariant, RoundedCornerShape(10.dp))
                        .padding(10.dp)
                )
            }

            // Players Slots
            BadmiCard {
                Text(
                    text = "Thành viên tham gia (${match.currentPlayers}/${match.maxPlayers})",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = BadmiTextPrimary
                )
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceAround
                ) {
                    listOf("Tuấn (Chủ kèo)", "Duy Nam", "Hoàng Hải", "+ Trống").forEachIndexed { i, name ->
                        val isSlotFilled = i < match.currentPlayers
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Box(
                                modifier = Modifier
                                    .size(50.dp)
                                    .background(
                                        if (isSlotFilled) BadmiPrimaryLight else BadmiSurfaceVariant,
                                        CircleShape
                                    )
                                    .border(
                                        1.dp,
                                        if (isSlotFilled) BadmiPrimary else BadmiBorder,
                                        CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                if (isSlotFilled) {
                                    Icon(
                                        imageVector = Icons.Default.Person,
                                        contentDescription = null,
                                        tint = BadmiPrimary
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Default.Add,
                                        contentDescription = null,
                                        tint = BadmiTextMuted
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = name,
                                fontSize = 11.sp,
                                color = if (isSlotFilled) BadmiTextPrimary else BadmiTextMuted,
                                fontWeight = if (isSlotFilled) FontWeight.SemiBold else FontWeight.Normal
                            )
                        }
                    }
                }
            }

            // Quick actions to waiting room or rate
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = onGoToWaitingRoom,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text("Phòng chờ", fontSize = 12.sp)
                }
                OutlinedButton(
                    onClick = onRateMatch,
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(20.dp)
                ) {
                    Text("Đánh giá trận", fontSize = 12.sp)
                }
            }
        }
    }
}

@Composable
fun CreateMatchScreen(
    onBackClick: () -> Unit,
    onSubmit: () -> Unit
) {
    var matchTitle by remember { mutableStateOf("Đánh đôi sau giờ làm") }
    var selectedFormat by remember { mutableStateOf("Đánh đôi") }
    var selectedLevel by remember { mutableStateOf("Trung bình") }
    var maxPlayers by remember { mutableIntStateOf(4) }
    var feePerPlayer by remember { mutableStateOf("45000") }
    var note by remember { mutableStateOf("Tìm bạn đánh đều tay, rèn thể lực vui vẻ.") }

    Scaffold(
        topBar = {
            BadmiTopBar(
                title = "Tạo trận đấu mới",
                subtitle = "Rủ bạn ra sân giao lưu",
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
                        text = "Đăng tạo trận ngay 🏸",
                        onClick = onSubmit
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
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Text(
                text = "Tên trận đấu",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = BadmiTextPrimary
            )
            OutlinedTextField(
                value = matchTitle,
                onValueChange = { matchTitle = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Text(
                text = "Chọn sân tổ chức",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = BadmiTextPrimary
            )
            BadmiCard {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text("BadmiSpot Club Bình Thạnh", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                        Text("Sân 02 • 18:00 - 20:00 (Hôm nay)", fontSize = 12.sp, color = BadmiTextSecondary)
                    }
                    Text("Đổi sân", color = BadmiPrimary, fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }

            Text(
                text = "Trình độ yêu cầu",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = BadmiTextPrimary
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Mới chơi", "Trung bình", "Khá", "Bán chuyên").forEach { lvl ->
                    BadmiChip(
                        text = lvl,
                        isSelected = selectedLevel == lvl,
                        onClick = { selectedLevel = lvl },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Text(
                text = "Thể thức & Số người",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = BadmiTextPrimary
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Đánh đơn (2)", "Đánh đôi (4)", "Nhóm đông (6)").forEach { fmt ->
                    val isSelected = (fmt.contains("4") && maxPlayers == 4) ||
                            (fmt.contains("2") && maxPlayers == 2) ||
                            (fmt.contains("6") && maxPlayers == 6)
                    BadmiChip(
                        text = fmt,
                        isSelected = isSelected,
                        onClick = {
                            if (fmt.contains("2")) maxPlayers = 2
                            if (fmt.contains("4")) maxPlayers = 4
                            if (fmt.contains("6")) maxPlayers = 6
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Text(
                text = "Chi phí chia sẻ (đ/người)",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = BadmiTextPrimary
            )
            OutlinedTextField(
                value = feePerPlayer,
                onValueChange = { feePerPlayer = it },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Text(
                text = "Lời nhắn cho đồng đội",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = BadmiTextPrimary
            )
            OutlinedTextField(
                value = note,
                onValueChange = { note = it },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3,
                shape = RoundedCornerShape(12.dp)
            )
        }
    }
}

@Composable
fun MatchWaitingRoomScreen(
    matchId: String,
    onBackClick: () -> Unit
) {
    Scaffold(
        topBar = {
            BadmiTopBar(
                title = "Phòng chờ trận đấu",
                subtitle = "Sẵn sàng ra sân",
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(BadmiBackground)
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Countdown Timer Banner (matching Figma: 02:15:30)
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                color = BadmiPrimaryDark
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "TRẬN ĐẤU BẮT ĐẦU SAU",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold,
                        color = BadmiMint
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "02:15:30",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = "BadmiSpot Club Bình Thạnh • Sân 02",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.8f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Players in Room
            BadmiCard {
                Text(
                    text = "Danh sách người chơi (3/4)",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold,
                    color = BadmiTextPrimary
                )
                Spacer(modifier = Modifier.height(14.dp))

                listOf(
                    Pair("Minh Tuấn (Chủ phòng)", "Đã sẵn sàng"),
                    Pair("Duy Nam", "Đã sẵn sàng"),
                    Pair("Bạn (Minh)", "Đã sẵn sàng"),
                    Pair("Vị trí số 4", "Đang đợi người tham gia...")
                ).forEachIndexed { i, player ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .background(
                                        if (i < 3) BadmiPrimaryLight else BadmiSurfaceVariant,
                                        CircleShape
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = if (i < 3) player.first.first().toString() else "?",
                                    fontWeight = FontWeight.Bold,
                                    color = if (i < 3) BadmiPrimaryDark else BadmiTextMuted
                                )
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = player.first,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = BadmiTextPrimary
                            )
                        }
                        BadmiBadge(
                            text = player.second,
                            containerColor = if (i < 3) BadmiMintSoft else BadmiSurfaceVariant,
                            contentColor = if (i < 3) BadmiPrimaryDark else BadmiTextMuted
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            BadmiButton(
                text = "Gửi tin nhắn trong phòng chờ",
                leadingIcon = Icons.AutoMirrored.Filled.Chat,
                onClick = {}
            )
        }
    }
}

@Composable
fun MatchRatingScreen(
    matchId: String,
    onBackClick: () -> Unit,
    onSubmitRating: () -> Unit
) {
    var rating by remember { mutableIntStateOf(5) }
    var reviewText by remember { mutableStateOf("Trận đấu rất vui vẻ, đối thủ và đồng đội đánh đều tay, tính tình thân thiện!") }
    val tags = listOf("Đúng giờ", "Chơi hay", "Thân thiện", "Sân đẹp", "Thể lực tốt")
    var selectedTags by remember { mutableStateOf(setOf("Đúng giờ", "Thân thiện")) }

    Scaffold(
        topBar = {
            BadmiTopBar(
                title = "Đánh giá trận đấu",
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
                        text = "Gửi đánh giá",
                        onClick = onSubmitRating
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
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Trận đấu thế nào?",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = BadmiTextPrimary
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Hãy chia sẻ cảm nhận về trận đấu và bạn chơi cùng nhé",
                fontSize = 12.sp,
                color = BadmiTextSecondary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(20.dp))

            // 5 Stars selector
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                (1..5).forEach { starIndex ->
                    IconButton(onClick = { rating = starIndex }) {
                        Icon(
                            imageVector = if (starIndex <= rating) Icons.Filled.Star else Icons.Outlined.StarBorder,
                            contentDescription = null,
                            tint = if (starIndex <= rating) BadmiStar else BadmiTextMuted,
                            modifier = Modifier.size(38.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Rating tags
            Text(
                text = "Điểm nổi bật",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = BadmiTextPrimary,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                tags.take(3).forEach { tag ->
                    val isSelected = selectedTags.contains(tag)
                    BadmiChip(
                        text = tag,
                        isSelected = isSelected,
                        onClick = {
                            selectedTags = if (isSelected) selectedTags - tag else selectedTags + tag
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                tags.drop(3).forEach { tag ->
                    val isSelected = selectedTags.contains(tag)
                    BadmiChip(
                        text = tag,
                        isSelected = isSelected,
                        onClick = {
                            selectedTags = if (isSelected) selectedTags - tag else selectedTags + tag
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Nhận xét chi tiết",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = BadmiTextPrimary,
                modifier = Modifier.align(Alignment.Start)
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = reviewText,
                onValueChange = { reviewText = it },
                modifier = Modifier.fillMaxWidth(),
                minLines = 4,
                shape = RoundedCornerShape(14.dp)
            )
        }
    }
}
