package com.dicoding.newsapp

import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dicoding.newsapp.ui.detail.DetailScreen
import com.dicoding.newsapp.ui.home.HomeScreen
import com.dicoding.newsapp.ui.home.navigation.Screen
import com.dicoding.newsapp.ui.theme.NewsAppTheme
import com.dicoding.newsapp.utils.FavoriteScreen

@Composable
fun NewsApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    Scaffold(
        modifier = modifier,
        topBar = { if(currentRoute==Screen.Home.route) AppBar(modifier = modifier, navController = navController) },
    //    floatingActionButton = { if (currentRoute == Screen.DetailNews.route) FavoriteFloatingActionButton() }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier.padding(vertical = innerPadding.calculateTopPadding()/2)
        ) {
            composable(Screen.Home.route) {
                HomeScreen {title ->
                    navController.navigate(Screen.DetailNews.createRoute(Uri.encode(title)))
                }
            }
            composable(Screen.Favorite.route) {
                FavoriteScreen(
                    navigateBack = {
                        navController.navigateUp()
                    },
                    navigateToDetail = { title ->
                        navController.navigate(Screen.DetailNews.createRoute(Uri.encode(title)))
                    }
                )
            }
            composable(
                route = Screen.DetailNews.route,
                arguments = listOf(navArgument("title") {type = NavType.StringType})
            ) {
                val title = it.arguments?.getString("title")
                Log.e("NewsApp", title.toString())
                DetailScreen(title = title.toString(), navigateBack = {
                    navController.navigateUp()
                })
            }
        }


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        modifier = modifier.fillMaxWidth(),
        title = {
            Text(text = stringResource(id = R.string.app_name))
        },
        actions = {
            IconButton(onClick = {  navController.navigate(Screen.Favorite.route) }) {
                Icon(imageVector = Icons.Default.Favorite, contentDescription = "favorite")
            }
        }
    )
}



@Preview (showBackground = true)
@Composable
private fun PreviewNewsApp() {
    NewsAppTheme {
        NewsApp()
    }
}