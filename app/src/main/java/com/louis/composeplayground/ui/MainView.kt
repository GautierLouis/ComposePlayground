package com.louis.composeplayground.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.louis.composeplayground.ui.theme.ComposePlaygroundTheme

@Composable
fun MainView() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            AddToFavoriteButton()
        }
        TabControl(firstTabContent = {
            Box(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth(), contentAlignment = Alignment.Center
            ) {
                Text(text = "First tab")
            }
        }, lastTabContent = {
            Box(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth(), contentAlignment = Alignment.Center
            ) {
                Text(text = "Last tab")
            }
        })
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposePlaygroundTheme {
        MainView()
    }
}