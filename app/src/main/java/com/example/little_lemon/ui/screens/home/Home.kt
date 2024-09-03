package com.example.little_lemon.ui.screens.home


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.little_lemon.R
import com.example.little_lemon.data.MenuEntity
import com.example.little_lemon.nav.Destinations
import com.example.little_lemon.MenuViewModel
import com.example.little_lemon.ui.screens.UIUtil.Logo.LemonLogo
import com.example.little_lemon.ui.theme.Custom_Light_Green


@Composable
fun Home(
    navHostController: NavHostController
) {

    val menuViewModel: MenuViewModel = viewModel()
    val menuStateValue = menuViewModel.menuItems.observeAsState().value
    var menuList by remember(key1 = menuStateValue) {
        mutableStateOf(menuStateValue)
    }

    Column {
        Header(navHostController = navHostController)

        Card(
            colors = CardDefaults.cardColors(
                containerColor = Custom_Light_Green,
            )
        ) {
            CardContents()
            SearchBar {
                run {
                    menuList = search(it, { m-> m?.title}, menuStateValue)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        val categories = arrayOf("starters", "desserts", "mains", "drinks")
        Row {
            categories.forEach { category ->
                CategoryButton(categoryName = category) {
                    run {
                        menuList = search(category, { m-> m?.category}, menuStateValue)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn(modifier = Modifier.fillMaxHeight()) {
            if (menuList != null) {
                items(menuList!!.toList()) {
                    it?.let { it1 -> MealCard(menuEntity = it1) }
                }
            }
        }

    }
}

fun search(
    searchPhrase: String,
    attribute: (MenuEntity?) -> String?,
    menuItems: List<MenuEntity?>?
): List<MenuEntity?>? {
    return menuItems?.filter {
        attribute(it)?.contains(searchPhrase, ignoreCase = true) ?: true
    }
}


@Composable
fun Header(navHostController: NavHostController) {
    Row(
        modifier = Modifier.padding(0.dp, 20.dp)
    ) {
        LemonLogo(
            modifier = Modifier
                .weight(1f, true)
                .height(90.dp)
                .padding(top = 20.dp, bottom = 10.dp)
        )

        Button(
            onClick = { navHostController.navigate(Destinations.Profile.path) },
            content = {
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                )
            },
            elevation = null,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
        )
    }
}






