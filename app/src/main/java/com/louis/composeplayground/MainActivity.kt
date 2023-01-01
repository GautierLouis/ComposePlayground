package com.louis.composeplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.louis.composeplayground.ViewState.COLLAPSED
import com.louis.composeplayground.ViewState.EXPANDED
import com.louis.composeplayground.ui.theme.ComposePlaygroundTheme

enum class ViewState {
    COLLAPSED, EXPANDED;

    fun opposite() =
        if (this == COLLAPSED) EXPANDED
        else COLLAPSED

}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposePlaygroundTheme {
                MainView()
            }
        }
    }
}

@Composable
fun MainView() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        AnimatedButton2()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimatedButton2(
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
            AnimatedFavIcon(cardState)
            AnimatedVisibility(visible = cardState.value == EXPANDED) {
                androidx.compose.material.Text(
                    modifier = Modifier.padding(start = 8.dp),
                    text = "Add to favorites !",
                    color = Color.Red
                )
            }
        }
    }

}


@Composable
fun AnimatedFavIcon(
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    ComposePlaygroundTheme {
        MainView()
    }
}