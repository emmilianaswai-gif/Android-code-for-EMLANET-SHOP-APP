package com.example.emlanetshopapp.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.TrendingDown
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.emlanetshopapp.ui.theme.DashboardBackground
import com.example.emlanetshopapp.ui.theme.DashboardCyan
import com.example.emlanetshopapp.ui.theme.DashboardDanger
import com.example.emlanetshopapp.ui.theme.DashboardIndigo
import com.example.emlanetshopapp.ui.theme.DashboardMuted
import com.example.emlanetshopapp.ui.theme.DashboardOnBackground
import com.example.emlanetshopapp.ui.theme.DashboardOnSurface
import com.example.emlanetshopapp.ui.theme.DashboardSuccess
import com.example.emlanetshopapp.ui.theme.DashboardSurface
import com.example.emlanetshopapp.ui.theme.DashboardSurfaceVariant
import com.example.emlanetshopapp.ui.theme.DashboardWarning
import com.example.emlanetshopapp.ui.theme.EMLANETSHOPAPPTheme

// ---------------------------------------------------------------------------
// Design tokens — single source of truth, no inline magic values in UI code
// ---------------------------------------------------------------------------

private val ScreenPadding = 20.dp
private val SectionSpacing = 20.dp
private val CardCorner = RoundedCornerShape(20.dp)
private val SmallCardCorner = RoundedCornerShape(16.dp)
private val SearchCorner = RoundedCornerShape(16.dp)

private val AccentGradient: Brush
    get() = Brush.linearGradient(
        colors = listOf(DashboardIndigo, DashboardCyan)
    )

private val PromoGradient: Brush
    get() = Brush.linearGradient(
        colors = listOf(Color(0xFF4F46E5), Color(0xFF7C3AED), Color(0xFF06B6D4))
    )

private val CardBorder = Color.White.copy(alpha = 0.08f)

// ---------------------------------------------------------------------------
// UI models
// ---------------------------------------------------------------------------

data class MetricUiModel(
    val title: String,
    val value: String,
    val delta: String,
    val isPositive: Boolean,
    val icon: ImageVector,
    val iconContainer: Color
)

data class QuickActionUiModel(
    val label: String,
    val icon: ImageVector
)

data class OrderUiModel(
    val id: String,
    val title: String,
    val subtitle: String,
    val amount: String,
    val status: String,
    val statusContainer: Color,
    val statusContent: Color
)

private fun sampleMetrics(): List<MetricUiModel> = listOf(
    MetricUiModel(
        title = "Revenue",
        value = "$24,580",
        delta = "+12.5%",
        isPositive = true,
        icon = Icons.Filled.Wallet,
        iconContainer = Color(0xFF4F46E5).copy(alpha = 0.18f)
    ),
    MetricUiModel(
        title = "Orders",
        value = "1,842",
        delta = "+8.2%",
        isPositive = true,
        icon = Icons.Filled.ShoppingCart,
        iconContainer = Color(0xFF06B6D4).copy(alpha = 0.18f)
    ),
    MetricUiModel(
        title = "Customers",
        value = "9,314",
        delta = "+4.6%",
        isPositive = true,
        icon = Icons.Filled.Person,
        iconContainer = Color(0xFF22C55E).copy(alpha = 0.16f)
    ),
    MetricUiModel(
        title = "Returns",
        value = "132",
        delta = "-1.9%",
        isPositive = false,
        icon = Icons.Filled.TrendingDown,
        iconContainer = Color(0xFFF43F5E).copy(alpha = 0.16f)
    )
)

private fun sampleQuickActions(): List<QuickActionUiModel> = listOf(
    QuickActionUiModel("Home", Icons.Filled.Home),
    QuickActionUiModel("Orders", Icons.Filled.ShoppingCart),
    QuickActionUiModel("Saved", Icons.Filled.Favorite),
    QuickActionUiModel("Account", Icons.Filled.Person)
)

private fun sampleOrders(): List<OrderUiModel> = listOf(
    OrderUiModel(
        id = "#8421",
        title = "Wireless Headphones",
        subtitle = "Today • 2 items",
        amount = "$249.00",
        status = "Delivered",
        statusContainer = Color(0xFF22C55E).copy(alpha = 0.15f),
        statusContent = Color(0xFF4ADE80)
    ),
    OrderUiModel(
        id = "#8420",
        title = "Smart Watch S2",
        subtitle = "Yesterday • 1 item",
        amount = "$329.00",
        status = "Shipped",
        statusContainer = Color(0xFF06B6D4).copy(alpha = 0.15f),
        statusContent = Color(0xFF67E8F9)
    ),
    OrderUiModel(
        id = "#8419",
        title = "Sneakers Air Max",
        subtitle = "2 days ago • 1 item",
        amount = "$179.00",
        status = "Pending",
        statusContainer = Color(0xFFF59E0B).copy(alpha = 0.15f),
        statusContent = Color(0xFFFCD34D)
    )
)

// ---------------------------------------------------------------------------
// Root screen
// ---------------------------------------------------------------------------

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    userName: String = "Emma Lane",
    userSubtitle: String = "Premium member",
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedTab by remember { mutableIntStateOf(0) }
    val metrics = remember { sampleMetrics() }
    val quickActions = remember { sampleQuickActions() }
    val orders = remember { sampleOrders() }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = DashboardBackground
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = ScreenPadding)
                    .padding(top = 16.dp, bottom = 12.dp),
                verticalArrangement = Arrangement.spacedBy(SectionSpacing)
            ) {
                WelcomeHeader(
                    userName = userName,
                    userSubtitle = userSubtitle,
                    unreadCount = 3,
                    onNotificationClick = {}
                )

                DashboardSearchBar(
                    query = searchQuery,
                    onQueryChange = { searchQuery = it },
                    onFilterClick = {}
                )

                PromoBannerCard(
                    onShopNowClick = {}
                )

                SectionHeader(
                    title = "Overview",
                    actionText = "See all",
                    onActionClick = {}
                )

                MetricsGrid(metrics = metrics)

                SectionHeader(
                    title = "Quick actions",
                    actionText = null,
                    onActionClick = null
                )

                QuickActionsRow(actions = quickActions)

                SectionHeader(
                    title = "Recent orders",
                    actionText = "View all",
                    onActionClick = {}
                )

                RecentOrdersList(orders = orders)
            }

            DashboardBottomBar(
                selectedIndex = selectedTab,
                onSelected = { selectedTab = it }
            )
        }
    }
}

// ---------------------------------------------------------------------------
// Header
// ---------------------------------------------------------------------------

@Composable
private fun WelcomeHeader(
    userName: String,
    userSubtitle: String,
    unreadCount: Int,
    onNotificationClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(AccentGradient),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = userName.firstOrNull()?.uppercase() ?: "E",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = "Welcome back 👋",
                    style = MaterialTheme.typography.labelMedium.copy(
                        color = DashboardMuted,
                        letterSpacing = 0.2.sp
                    )
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = userName,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = DashboardOnBackground
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = userSubtitle,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = DashboardMuted
                    )
                )
            }
        }

        BadgedBox(
            badge = {
                if (unreadCount > 0) {
                    Badge(
                        containerColor = DashboardDanger,
                        contentColor = Color.White
                    ) {
                        Text(text = unreadCount.toString(), fontSize = 10.sp)
                    }
                }
            }
        ) {
            IconButton(
                onClick = onNotificationClick,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(DashboardSurface)
            ) {
                Icon(
                    imageVector = Icons.Filled.Notifications,
                    contentDescription = "Notifications",
                    tint = DashboardOnSurface,
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Search bar (Material 3 styled OutlinedTextField)
// ---------------------------------------------------------------------------

@Composable
private fun DashboardSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onFilterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            modifier = Modifier.weight(1f),
            placeholder = {
                Text(
                    text = "Search products, orders…",
                    style = MaterialTheme.typography.bodyMedium.copy(color = DashboardMuted)
                )
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Search",
                    tint = DashboardMuted
                )
            },
            singleLine = true,
            shape = SearchCorner,
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = DashboardSurface,
                unfocusedContainerColor = DashboardSurface,
                disabledContainerColor = DashboardSurface,
                focusedBorderColor = DashboardCyan.copy(alpha = 0.6f),
                unfocusedBorderColor = CardBorder,
                focusedTextColor = DashboardOnSurface,
                unfocusedTextColor = DashboardOnSurface,
                cursorColor = DashboardCyan
            )
        )
        Box(
            modifier = Modifier
                .size(54.dp)
                .clip(SearchCorner)
                .background(AccentGradient)
                .clickable(onClick = onFilterClick),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.Settings,
                contentDescription = "Filters",
                tint = Color.White,
                modifier = Modifier.size(22.dp)
            )
        }
    }
}

// ---------------------------------------------------------------------------
// Promotional gradient banner
// ---------------------------------------------------------------------------

@Composable
private fun PromoBannerCard(
    onShopNowClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = CardCorner,
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(PromoGradient)
                .padding(20.dp)
        ) {
            // Decorative translucent circles
            Box(
                modifier = Modifier
                    .size(140.dp)
                    .align(Alignment.TopEnd)
                    .padding(top = 0.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.12f))
            )
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .align(Alignment.BottomEnd)
                    .clip(CircleShape)
                    .background(Color.Black.copy(alpha = 0.10f))
            )

            Column(modifier = Modifier.fillMaxWidth(0.72f)) {
                Surface(
                    color = Color.White.copy(alpha = 0.18f),
                    shape = RoundedCornerShape(999.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.LocalOffer,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(14.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "FLASH SALE • -30%",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.6.sp
                            )
                        )
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "Mega Tech Fest is live",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        lineHeight = 30.sp
                    )
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Up to 30% off headphones, watches and more. Ends Sunday.",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = Color.White.copy(alpha = 0.85f)
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(14.dp))
                Button(
                    onClick = onShopNowClick,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = DashboardIndigo
                    ),
                    shape = RoundedCornerShape(12.dp),
                    contentPadding = PaddingValues(horizontal = 20.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = "Shop now",
                        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        imageVector = Icons.Filled.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Section header
// ---------------------------------------------------------------------------

@Composable
private fun SectionHeader(
    title: String,
    actionText: String?,
    onActionClick: (() -> Unit)?,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = DashboardOnBackground
            )
        )
        if (actionText != null && onActionClick != null) {
            Text(
                text = actionText,
                style = MaterialTheme.typography.labelLarge.copy(
                    color = DashboardCyan,
                    fontWeight = FontWeight.SemiBold
                ),
                modifier = Modifier.clickable(onClick = onActionClick)
            )
        }
    }
}

// ---------------------------------------------------------------------------
// Metrics
// ---------------------------------------------------------------------------

@Composable
private fun MetricsGrid(
    metrics: List<MetricUiModel>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        metrics.chunked(2).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                rowItems.forEach { metric ->
                    MetricCard(
                        metric = metric,
                        modifier = Modifier.weight(1f)
                    )
                }
                // Keep grid balanced when odd count
                if (rowItems.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun MetricCard(
    metric: MetricUiModel,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = SmallCardCorner,
        colors = CardDefaults.cardColors(containerColor = DashboardSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(metric.iconContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = metric.icon,
                    contentDescription = metric.title,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = metric.value,
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.ExtraBold,
                    color = DashboardOnSurface
                ),
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = metric.title,
                style = MaterialTheme.typography.bodySmall.copy(color = DashboardMuted)
            )
            Spacer(modifier = Modifier.height(8.dp))
            DeltaPill(delta = metric.delta, isPositive = metric.isPositive)
        }
    }
}

@Composable
private fun DeltaPill(
    delta: String,
    isPositive: Boolean,
    modifier: Modifier = Modifier
) {
    val container = if (isPositive) {
        DashboardSuccess.copy(alpha = 0.14f)
    } else {
        DashboardDanger.copy(alpha = 0.14f)
    }
    val content = if (isPositive) Color(0xFF4ADE80) else Color(0xFFFB7185)
    val icon = if (isPositive) Icons.Filled.TrendingUp else Icons.Filled.TrendingDown

    Surface(
        modifier = modifier,
        color = container,
        shape = RoundedCornerShape(999.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = null, tint = content, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = delta,
                style = MaterialTheme.typography.labelSmall.copy(
                    color = content,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

// ---------------------------------------------------------------------------
// Quick actions
// ---------------------------------------------------------------------------

@Composable
private fun QuickActionsRow(
    actions: List<QuickActionUiModel>,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(vertical = 2.dp)
    ) {
        items(actions) { action ->
            QuickActionItem(action = action)
        }
        item {
            QuickActionItem(
                action = QuickActionUiModel("Top rated", Icons.Filled.Star),
                highlight = true
            )
        }
    }
}

@Composable
private fun QuickActionItem(
    action: QuickActionUiModel,
    highlight: Boolean = false,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.width(72.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(60.dp)
                .clip(SmallCardCorner)
                .then(
                    if (highlight) Modifier.background(AccentGradient)
                    else Modifier.background(DashboardSurface)
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = action.icon,
                contentDescription = action.label,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = action.label,
            style = MaterialTheme.typography.labelSmall.copy(
                color = DashboardMuted,
                fontWeight = FontWeight.Medium
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

// ---------------------------------------------------------------------------
// Recent orders
// ---------------------------------------------------------------------------

@Composable
private fun RecentOrdersList(
    orders: List<OrderUiModel>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        orders.forEach { order ->
            OrderCard(order = order)
        }
    }
}

@Composable
private fun OrderCard(
    order: OrderUiModel,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = SmallCardCorner,
        colors = CardDefaults.cardColors(containerColor = DashboardSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, CardBorder)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(DashboardSurfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = order.id.takeLast(2),
                    style = MaterialTheme.typography.labelLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = DashboardOnSurface
                    )
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = order.title,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.SemiBold,
                        color = DashboardOnSurface
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "${order.id} • ${order.subtitle}",
                    style = MaterialTheme.typography.bodySmall.copy(color = DashboardMuted),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = order.amount,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontWeight = FontWeight.Bold,
                        color = DashboardOnSurface
                    )
                )
                Spacer(modifier = Modifier.height(6.dp))
                Surface(
                    color = order.statusContainer,
                    shape = RoundedCornerShape(999.dp)
                ) {
                    Text(
                        text = order.status,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = order.statusContent,
                            fontWeight = FontWeight.SemiBold
                        ),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }
            }
        }
    }
}

// ---------------------------------------------------------------------------
// Bottom bar
// ---------------------------------------------------------------------------

@Composable
private fun DashboardBottomBar(
    selectedIndex: Int,
    onSelected: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    val items = listOf("Home", "Orders", "Saved", "Profile")
    val icons = listOf(
        Icons.Filled.Home,
        Icons.Filled.ShoppingCart,
        Icons.Filled.Favorite,
        Icons.Filled.Person
    )

    NavigationBar(
        modifier = modifier,
        containerColor = DashboardSurface,
        tonalElevation = 0.dp
    ) {
        items.forEachIndexed { index, label ->
            NavigationBarItem(
                selected = selectedIndex == index,
                onClick = { onSelected(index) },
                icon = {
                    Icon(imageVector = icons[index], contentDescription = label)
                },
                label = {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelSmall.copy(
                            fontWeight = if (selectedIndex == index) FontWeight.Bold else FontWeight.Medium
                        )
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    selectedTextColor = Color.White,
                    unselectedIconColor = DashboardMuted,
                    unselectedTextColor = DashboardMuted,
                    indicatorColor = DashboardIndigo
                )
            )
        }
    }
}

// ---------------------------------------------------------------------------
// Preview
// ---------------------------------------------------------------------------

@Preview(showBackground = true, backgroundColor = 0xFF0F172A)
@Composable
private fun DashboardScreenPreview() {
    EMLANETSHOPAPPTheme(darkTheme = true, dynamicColor = false) {
        DashboardScreen()
    }
}

@Preview(showBackground = true, backgroundColor = 0xFF0F172A, name = "Promo + Metric")
@Composable
private fun DashboardComponentsPreview() {
    EMLANETSHOPAPPTheme(darkTheme = true, dynamicColor = false) {
        Surface(color = DashboardBackground) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                PromoBannerCard(onShopNowClick = {})
                MetricsGrid(metrics = sampleMetrics().take(2))
                // Reference unused palette entries to keep theme cohesive
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(DashboardWarning)
                    )
                    Box(
                        modifier = Modifier
                            .size(24.dp)
                            .clip(CircleShape)
                            .background(DashboardSuccess)
                    )
                }
            }
        }
    }
}
