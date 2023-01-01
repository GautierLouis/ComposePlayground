package com.louis.composeplayground.ui


import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.louis.composeplayground.ui.Tab.FIRST_TAB
import com.louis.composeplayground.ui.Tab.LAST_TAB
import com.louis.composeplayground.ui.theme.ComposePlaygroundTheme


enum class Tab {
    FIRST_TAB,
    LAST_TAB
}

@Composable
fun TabControl(
    firstTabTitle: String = "Tab1",
    lastTableTitle: String = "Tab2",
    selectedColor: Color = MaterialTheme.colorScheme.background,
    unselectedColor: Color = Color.LightGray,
    firstTabContent: @Composable () -> Unit = {},
    lastTabContent: @Composable () -> Unit = {},
    selectedTab: MutableState<Tab> = remember { mutableStateOf(FIRST_TAB) }
) {

    val radius = 10.dp
    val isFirstTabSelected = selectedTab.value == FIRST_TAB

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(unselectedColor, RoundedCornerShape(radius))
    ) {
        Column(Modifier.padding(8.dp)) {
            Row(horizontalArrangement = Arrangement.Center) {
                TabContainerHeader(
                    tab = FIRST_TAB,
                    tabTitle = firstTabTitle,
                    selectedTab = selectedTab,
                    unselectedColor = unselectedColor,
                    selectedColor = selectedColor,
                    radius = radius
                ) {
                    selectedTab.value = it
                }
                TabContainerHeader(
                    tab = LAST_TAB,
                    tabTitle = lastTableTitle,
                    selectedTab = selectedTab,
                    unselectedColor = unselectedColor,
                    selectedColor = selectedColor,
                    radius = radius
                ) {
                    selectedTab.value = it
                }
            }
            Box(
                Modifier
                    .fillMaxWidth()
                    .wrapContentWidth()
                    .background(
                        selectedColor,
                        RoundedCornerShape(
                            topStart = if (!isFirstTabSelected) radius else 0.dp,
                            topEnd = if (isFirstTabSelected) radius else 0.dp,
                            bottomStart = radius,
                            bottomEnd = radius
                        )
                    )
            ) {
                if (isFirstTabSelected) {
                    firstTabContent()
                } else lastTabContent()
            }
        }
    }
}

@Composable
fun RowScope.TabContainerHeader(
    tab: Tab,
    tabTitle: String,
    selectedTab: MutableState<Tab>,
    unselectedColor: Color,
    selectedColor: Color,
    radius: Dp,
    onTabClick: (Tab) -> Unit
) {

    val isSelected = selectedTab.value == tab
    TabHeaderBackgroundWrapper(
        isSelected = isSelected,
        tab = tab,
        radius = radius,
        selectedColor = selectedColor,
    ) {
        TabHeader(
            modifier = Modifier.selectable(selected = isSelected) { onTabClick(tab) },
            tabTitle = tabTitle,
            backgroundColor = if (isSelected) selectedColor else unselectedColor,
            radius = radius
        )
    }
}

@Composable
fun RowScope.TabHeaderBackgroundWrapper(
    isSelected: Boolean,
    tab: Tab,
    radius: Dp,
    selectedColor: Color,
    content: @Composable () -> Unit
) {

    Box(
        Modifier
            .weight(1f)
            .background(
                selectedColor,
                RoundedCornerShape(
                    topStart = radius,
                    topEnd = radius,
                    bottomStart = if (isSelected || tab == LAST_TAB) 0.dp else radius,
                    bottomEnd = if (isSelected || tab == FIRST_TAB) 0.dp else radius
                )
            )
    ) {
        content()
    }
}

@Composable
fun TabHeader(
    modifier: Modifier,
    tabTitle: String,
    backgroundColor: Color,
    radius: Dp
) {
    val shape = RoundedCornerShape(radius)
    Box(
        Modifier
            .fillMaxWidth()
            .background(backgroundColor, shape)
            .clip(shape)
            .then(modifier)
    ) {
        TableTitle(
            title = tabTitle,
            textColor = if (backgroundColor.luminance() < 0.5) Color.White else Color.Black,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
fun TableTitle(title: String, textColor: Color, modifier: Modifier) {
    Text(text = title, modifier = modifier.padding(16.dp), color = textColor)
}


class SelectedTabPreviewParameterProvider : PreviewParameterProvider<Tab> {
    override val values = sequenceOf(FIRST_TAB, LAST_TAB)
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(name = "Dark Mode", uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = false)
@Composable
fun TabControlPreview(
    @PreviewParameter(SelectedTabPreviewParameterProvider::class) selectedTab: Tab
) {
    ComposePlaygroundTheme() {
        TabControl(selectedTab = remember { mutableStateOf(selectedTab) })
    }
}