package com.louis.composeplayground.ui

enum class ViewState {
    COLLAPSED, EXPANDED;

    fun opposite() =
        if (this == COLLAPSED) EXPANDED
        else COLLAPSED

}