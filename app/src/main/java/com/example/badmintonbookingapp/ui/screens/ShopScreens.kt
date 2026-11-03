package com.example.badmintonbookingapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.badmintonbookingapp.data.BadmiData
import com.example.badmintonbookingapp.model.CartItem
import com.example.badmintonbookingapp.model.ProductItem
import com.example.badmintonbookingapp.ui.components.*
import com.example.badmintonbookingapp.ui.theme.*

@Composable
fun ShopScreen(
    onNavigateToProductDetail: (String) -> Unit,
    onNavigateToCart: () -> Unit
) {
    var selectedCategory by remember { mutableStateOf("Tất cả") }
    val categories = listOf("Tất cả", "Vợt cầu lông", "Quả cầu", "Giày thể thao", "Phụ kiện")

    Scaffold(
        topBar = {
            BadmiTopBar(
                title = "BadmiStore",
                subtitle = "Sẵn sàng cho trận hay",
                showBack = false,
                actions = {
                    IconButton(onClick = onNavigateToCart) {
                        BadgedBox(
                            badge = {
                                Badge(containerColor = BadmiPrimary) {
                                    Text("2", color = Color.White)
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.ShoppingCart,
                                contentDescription = "Giỏ hàng",
                                tint = BadmiTextPrimary
                            )
                        }
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
            // Category Chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                categories.forEach { cat ->
                    BadmiChip(
                        text = cat,
                        isSelected = selectedCategory == cat,
                        onClick = { selectedCategory = cat }
                    )
                }
            }

            // Products Grid / List
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                BadmiData.sampleProducts.forEach { product ->
                    ProductSummaryCard(
                        product = product,
                        onClick = { onNavigateToProductDetail(product.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun ProductSummaryCard(
    product: ProductItem,
    onClick: () -> Unit
) {
    BadmiCard(onClick = onClick) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(85.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(BadmiSurfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.SportsTennis,
                    contentDescription = null,
                    tint = BadmiPrimary,
                    modifier = Modifier.size(36.dp)
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                BadmiBadge(text = product.badge)
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = product.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = BadmiTextPrimary,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${product.weightGrip} • ${product.category}",
                    fontSize = 11.sp,
                    color = BadmiTextSecondary
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = formatCurrency(product.price),
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = BadmiPrimary
                        )
                        Text(
                            text = formatCurrency(product.originalPrice),
                            fontSize = 11.sp,
                            color = BadmiTextMuted,
                            textDecoration = TextDecoration.LineThrough
                        )
                    }

                    Text(
                        text = "Đã bán ${product.soldCount}",
                        fontSize = 11.sp,
                        color = BadmiTextSecondary
                    )
                }
            }
        }
    }
}

@Composable
fun ProductDetailScreen(
    productId: String,
    onBackClick: () -> Unit,
    onAddToCart: () -> Unit
) {
    val product = BadmiData.sampleProducts.find { it.id == productId } ?: BadmiData.sampleProducts[0]
    var selectedGrip by remember { mutableStateOf("4U/G5") }

    Scaffold(
        topBar = {
            BadmiTopBar(
                title = "Chi tiết sản phẩm",
                subtitle = product.category,
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
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    BadmiOutlinedButton(
                        text = "Thêm vào giỏ",
                        onClick = onAddToCart,
                        modifier = Modifier.weight(1f)
                    )
                    BadmiButton(
                        text = "Mua ngay",
                        onClick = onAddToCart,
                        modifier = Modifier.weight(1f)
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Product Image Graphic
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(BadmiSurfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.SportsTennis,
                    contentDescription = null,
                    tint = BadmiPrimary,
                    modifier = Modifier.size(80.dp)
                )
            }

            Text(
                text = product.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = BadmiTextPrimary
            )

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = formatCurrency(product.price),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = BadmiPrimary
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = formatCurrency(product.originalPrice),
                    fontSize = 14.sp,
                    color = BadmiTextMuted,
                    textDecoration = TextDecoration.LineThrough
                )
            }

            HorizontalDivider(color = BadmiBorderLight)

            Text("Chọn phiên bản trọng lượng", fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                listOf("4U/G5 (83g)", "3U/G5 (88g)").forEach { grip ->
                    BadmiChip(
                        text = grip,
                        isSelected = selectedGrip == grip,
                        onClick = { selectedGrip = grip }
                    )
                }
            }

            BadmiCard {
                Text("Thông số kỹ thuật", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(10.dp))
                InfoRow(label = "Độ cứng đũa", value = "Trung bình (Medium)")
                InfoRow(label = "Điểm cân bằng", value = product.balancePoint)
                InfoRow(label = "Mức căng tối đa", value = product.tension)
                InfoRow(label = "Chất liệu khung", value = "High Modulus Graphite")
            }

            BadmiCard {
                Text("Mô tả sản phẩm", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = product.description,
                    fontSize = 13.sp,
                    color = BadmiTextSecondary,
                    lineHeight = 18.sp
                )
            }
        }
    }
}

@Composable
fun CartScreen(
    onBackClick: () -> Unit,
    onCheckout: () -> Unit
) {
    var cartItems by remember { mutableStateOf(BadmiData.sampleCart) }
    val subtotal = cartItems.sumOf { it.product.price * it.quantity }
    val shipping = 30000
    val discount = 20000
    val total = subtotal + shipping - discount

    Scaffold(
        topBar = {
            BadmiTopBar(
                title = "Giỏ hàng của bạn",
                subtitle = "${cartItems.size} sản phẩm đã chọn",
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
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Tổng thanh toán", fontSize = 14.sp, color = BadmiTextSecondary)
                        Text(
                            text = formatCurrency(total),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = BadmiPrimary
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    BadmiButton(
                        text = "Tiến hành thanh toán (${formatCurrency(total)})",
                        onClick = onCheckout
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            cartItems.forEachIndexed { index, item ->
                BadmiCard {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(64.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(BadmiSurfaceVariant),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.SportsTennis, contentDescription = null, tint = BadmiPrimary)
                        }

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = item.product.name,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp,
                                color = BadmiTextPrimary,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(text = item.selectedWeight, fontSize = 12.sp, color = BadmiTextSecondary)
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = formatCurrency(item.product.price),
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                color = BadmiPrimary
                            )
                        }

                        // Quantity stepper
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(
                                onClick = {
                                    if (item.quantity > 1) {
                                        cartItems = cartItems.toMutableList().also {
                                            it[index] = item.copy(quantity = item.quantity - 1)
                                        }
                                    }
                                },
                                modifier = Modifier.size(28.dp)
                            ) {
                                Icon(Icons.Default.Remove, contentDescription = "Giảm", tint = BadmiTextSecondary)
                            }
                            Text(
                                text = "${item.quantity}",
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 6.dp)
                            )
                            IconButton(
                                onClick = {
                                    cartItems = cartItems.toMutableList().also {
                                        it[index] = item.copy(quantity = item.quantity + 1)
                                    }
                                },
                                modifier = Modifier.size(28.dp)
                            ) {
                                Icon(Icons.Default.Add, contentDescription = "Tăng", tint = BadmiPrimary)
                            }
                        }
                    }
                }
            }

            // Summary Card
            BadmiCard {
                Text("Tóm tắt chi phí", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Spacer(modifier = Modifier.height(8.dp))
                InfoRow(label = "Tạm tính hàng", value = formatCurrency(subtotal))
                InfoRow(label = "Phí giao hàng toàn quốc", value = formatCurrency(shipping))
                InfoRow(label = "Mã giảm giá BADMI20K", value = "-${formatCurrency(discount)}", valueColor = BadmiPrimary)
                HorizontalDivider(color = BadmiBorderLight, modifier = Modifier.padding(vertical = 6.dp))
                InfoRow(label = "Tổng cộng", value = formatCurrency(total), isBold = true, valueColor = BadmiPrimary)
            }
        }
    }
}
