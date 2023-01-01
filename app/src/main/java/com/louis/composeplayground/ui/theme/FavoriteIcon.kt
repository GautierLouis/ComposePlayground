package com.louis.composeplayground.ui.theme

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.louis.composeplayground.ui.ViewState
import com.louis.composeplayground.ui.ViewState.COLLAPSED
import com.louis.composeplayground.ui.ViewState.EXPANDED


@Composable
fun FavoriteIcon(
    cardState: MutableState<ViewState>,
    size: Dp = 24.dp,
    tint: Color = Color.Red
) {

    val endIcon = Icons.Default.FavoriteBorder
    val startIcon = Icons.Default.Favorite
    val transition = updateTransition(cardState.value, label = "")

    val favSize = transition.animateDp(
        label = "",
        transitionSpec = { spring(Spring.DampingRatioHighBouncy) }
    ) {
        when (it) {
            COLLAPSED -> size
            EXPANDED -> 0.dp
        }
    }
    val unfavSize = transition.animateDp(
        label = "",
        transitionSpec = { spring(Spring.DampingRatioHighBouncy) }
    ) {
        when (it) {
            COLLAPSED -> 0.dp
            EXPANDED -> size
        }
    }

    Row(modifier = Modifier) {
        Icon(
            imageVector = endIcon,
            contentDescription = "",
            tint = tint,
            modifier =
            Modifier
                .size(unfavSize.value)

        )
        Icon(
            imageVector = startIcon,
            contentDescription = "",
            tint = tint,
            modifier =
            Modifier
                .size(favSize.value)
        )
    }
}
