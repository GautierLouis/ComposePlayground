package com.louis.composeplayground.ui

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.louis.composeplayground.ui.ViewState.COLLAPSED
import com.louis.composeplayground.ui.ViewState.EXPANDED


@Composable
fun SearchView(onResultPicked: (String) -> Unit = {}) {

    val searchState: MutableState<ViewState> = remember { mutableStateOf(COLLAPSED) }
    val searchQuery: MutableState<String> = remember { mutableStateOf("") }

    val configuration = LocalConfiguration.current
    val transition = updateTransition(searchState, label = "")
    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    val dynamicWidth = transition.animateDp(label = "") { state ->
        when (state.value) {
            COLLAPSED -> 300.dp
            EXPANDED -> screenWidth
        }
    }

    val dynamicHeight = transition.animateDp(label = "") { state ->
        when (state.value) {
            COLLAPSED -> 55.dp
            EXPANDED -> screenHeight + 300.dp // TODO
        }
    }

    val dynamicTopPadding = transition.animateDp(label = "") { state ->
        when (state.value) {
            COLLAPSED -> 32.dp
            EXPANDED -> 0.dp
        }
    }
    val dynamicHorizontalPadding = transition.animateDp(label = "") { state ->
        when (state.value) {
            COLLAPSED -> 32.dp
            EXPANDED -> 0.dp
        }
    }

    val dynamicTopContentPadding = transition.animateDp(label = "") { state ->
        when (state.value) {
            COLLAPSED -> 0.dp
            EXPANDED -> 32.dp
        }
    }

    val dynamicHorizontalContentPadding = transition.animateDp(label = "") { state ->
        when (state.value) {
            COLLAPSED -> 0.dp
            EXPANDED -> 32.dp
        }
    }


    val dynamicCorner = transition.animateDp(label = "") { state ->
        when (state.value) {
            COLLAPSED -> 10.dp
            EXPANDED -> 0.dp
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = dynamicTopPadding.value,
                start = dynamicHorizontalPadding.value,
                end = dynamicHorizontalPadding.value
            )
    ) {
        Card(
            modifier = Modifier
                .width(dynamicWidth.value)
                .height(dynamicHeight.value),
            shape = RoundedCornerShape(dynamicCorner.value)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = dynamicTopContentPadding.value,
                        start = dynamicHorizontalContentPadding.value,
                        end = dynamicHorizontalContentPadding.value
                    )
            ) {
                SearchBar(searchQuery, searchState)
                LazyColumn(modifier = Modifier.padding(top = 16.dp)) {
                    items(10, key = { it }, itemContent = { index ->
                        val word = "Item $index"
                        Text(
                            text = word,
                            Modifier.clickable {
                                searchState.value = COLLAPSED
                                onResultPicked(word)
                            }
                        )
                    })
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    searchQuery: MutableState<String>,
    searchState: MutableState<ViewState>
) {
    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current

    if (searchState.value == COLLAPSED) {
        focusManager.clearFocus()
    }

    OutlinedTextField(
        value = searchQuery.value,
        onValueChange = { searchQuery.value = it },
        modifier = Modifier
            .fillMaxWidth()
            .focusRequester(focusRequester)
            .onFocusChanged {
                searchState.value = if (it.hasFocus || it.isFocused) {
                    EXPANDED
                } else COLLAPSED
            },
        placeholder = { Text(text = "Search") },
        leadingIcon = {
            SearchTrailingIcon(state = searchState, focusManager)
        },
        trailingIcon = {
            if (searchQuery.value.isNotEmpty()) {
                IconButton(onClick = { searchQuery.value = "" }) {
                    Icon(imageVector = Icons.Default.Clear, contentDescription = "Clear")
                }
            }
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            autoCorrect = false,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Search
        ),
        singleLine = true,
        colors = TextFieldDefaults.outlinedTextFieldColors(
            containerColor = MaterialTheme.colorScheme.background,
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = Color.LightGray,
            focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
            unfocusedLeadingIconColor = Color.LightGray,
            focusedTrailingIconColor = MaterialTheme.colorScheme.primary,
            placeholderColor = Color.LightGray,
        ),
        shape = RoundedCornerShape(10.dp)
    )
}

@Composable
fun SearchTrailingIcon(
    state: MutableState<ViewState>,
    focusManager: FocusManager
) {
    if (state.value == EXPANDED) {
        IconButton(onClick = {
            focusManager.clearFocus(true)
        }) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back"
            )
        }
    } else {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "Search"
        )
    }
}
