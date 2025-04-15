package com.rovaniemi.main.view

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.rovaniemi.main.model.NavItem

@Composable
internal fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    onClick: (screenRoute: String) -> Unit,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Row(
        modifier = modifier
            .fillMaxWidth()
            .shadow(
                elevation = 6.dp,
            )
            .background(
                color = Color.White,
            ),
    ) {
        listOf(
            NavItem.Search,
            NavItem.Bookmarks,
        ).forEach { navItem ->
            val isSelected = currentRoute == navItem.screenRoute
            val selectedColor by animateColorAsState(
                targetValue = if (isSelected) {
                    Color.Black
                } else {
                    Color.LightGray
                },
                label = "",
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null,
                        onClick = {
                            onClick(navItem.screenRoute)
                        },
                    )
                    .padding(
                        top = 12.dp,
                        bottom = 8.dp,
                    ),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Icon(
                    modifier = Modifier
                        .size(20.dp),
                    painter = painterResource(id = navItem.icon),
                    tint = selectedColor,
                    contentDescription = stringResource(id = navItem.title),
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = stringResource(id = navItem.title),
                    color = selectedColor,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewBottomNavigationBar(){
    BottomNavigationBar(
        navController = NavHostController(LocalContext.current),
        onClick = {},
    )
}