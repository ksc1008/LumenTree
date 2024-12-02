package com.example.lumentree.feature.device

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun LumenTreeBottomNavigation(
    modifier: Modifier,
    navController: NavController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDest = navBackStackEntry?.destination
    val items = listOf(
        BottomNavItem.Control,
        BottomNavItem.Colors,
        BottomNavItem.Settings
    )

    val containerColor = Color(0xFFFFF8F5)
    val contentColor = Color(0xFF51443B)

    AnimatedVisibility(
        visible = items.any { item ->
            currentDest?.hierarchy?.any {it.route == item.screenRoute::class.qualifiedName} == true
        }
    ) {
        NavigationBar(
            modifier = modifier,
            containerColor = containerColor,
            contentColor = contentColor,
        ) {
            items.forEach { item ->
                NavigationBarItem(
                    selected = currentDest?.hierarchy?.any {it. route == item.screenRoute::class.qualifiedName} == true,
                    label = {
                        Text(
                            text = stringResource(id = item.title),
                            style = TextStyle(
                                fontSize = 12.sp
                            )
                        )
                    },
                    icon = {
                        Icon(
                            painter = painterResource(id = item.icon),
                            contentDescription = stringResource(id = item.title)
                        )
                    },
                    onClick = {
                        navController.navigate(item.screenRoute) {
                            navController.graph.startDestinationRoute?.let {
                                popUpTo(it) { saveState = true }
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    },
                )
            }
        }
    }
}
