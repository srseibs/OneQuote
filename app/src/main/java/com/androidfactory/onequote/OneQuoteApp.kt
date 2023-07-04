package com.androidfactory.onequote

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.androidfactory.onequote.navigation.HeaderNavigation

@Composable
fun OneQuoteApp(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    viewModel: MainActivityViewModel = viewModel()
) {
    val appState by viewModel.appState.collectAsStateWithLifecycle()

    PerformOnLifecycle(
        lifecycleOwner = lifecycleOwner,
        onStart = {
            viewModel.fetchQuoteOfTheDay()
            viewModel.fetchAllQuotes()
        },
        onResume = { /* Do something here */ }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colors.background)
    ) {

        HeaderNavigation(
            navigation = appState.navigation,
            onClick = { viewModel.selectPage(it) }
        )

        // Page content
        val selectedPage = appState.navigation.selectedPage
        when (selectedPage.title) {
            "All quotes" -> {
                AllQuotesScreen(
                    allQuotesOperation = appState.allQuotes,
                    onFavoriteClicked = {
                        // todo handle onClick
                    }
                )
            }
            "Daily quote" -> DailyQuoteScreen(
                networkOperation = appState.quoteOfTheDay,
                onFavoriteClicked = {
                    // todo handle onClick
                },
                onRefresh = { viewModel.fetchQuoteOfTheDay() }
            )
            "Favorites" -> {
                TempContent(selectedPage.color)
                TempContent(selectedPage.color)
                TempContent(selectedPage.color)
            }
        }
    }
}

@Composable
private fun TempContent(itemColor: Color) {
    Box(
        modifier = Modifier
            .padding(all = 32.dp)
            .height(16.dp)
            .fillMaxWidth()
            .background(
                color = itemColor,
                shape = MaterialTheme.shapes.medium
            )
    )
}