package com.louis.composeplayground.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.louis.composeplayground.ui.ViewState.COLLAPSED
import com.louis.composeplayground.ui.ViewState.EXPANDED
import com.louis.composeplayground.ui.theme.FavoriteIcon


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddToFavoriteButton(
    cardState: MutableState<ViewState> = remember { mutableStateOf(COLLAPSED) }
) {
    val configuration = LocalConfiguration.current

    val transition = updateTransition(cardState, label = "")
    val screenWidth = configuration.screenWidthDp.dp
    val dynamicWidth = transition.animateDp(label = "") { state ->
        when (state.value) {
            COLLAPSED -> 60.dp
            EXPANDED -> screenWidth
        }
    }
    val dynamicBorder = transition.animateDp(label = "") { state ->
        when (state.value) {
            COLLAPSED -> 0.dp
            EXPANDED -> 1.dp
        }
    }

    val dynamicCorner = transition.animateDp(label = "") { state ->
        when (state.value) {
            COLLAPSED -> 100.dp
            EXPANDED -> 5.dp
        }
    }

    val dynamicBackgroundColor = transition.animateColor(label = "") { state ->
        when (state.value) {
            COLLAPSED -> androidx.compose.material.MaterialTheme.colors.background
            EXPANDED -> Color.Transparent
        }
    }

    val shape = RoundedCornerShape(dynamicCorner.value)

    Card(
        modifier = Modifier
            .width(dynamicWidth.value)
            .height(60.dp)
            .padding(8.dp),
        border = BorderStroke(dynamicBorder.value, Color.Red),
        shape = shape,
        onClick = { cardState.value = cardState.value.opposite() },
        colors = CardDefaults.cardColors(
            containerColor = dynamicBackgroundColor.value
        )
    ) {
        Row(
            Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FavoriteIcon(cardState)
            AnimatedVisibility(visible = cardState.value == EXPANDED) {
                Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = "Add to favorites !",
                    color = Color.Red
                )
            }
        }
    }
}
