package com.lang98.floatingactionmenu.FloatingActionMenu

import android.support.annotation.ColorRes
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes

data class FloatingActionModel(
    @DrawableRes val iconResId: Int,
    @ColorRes val iconBgColorResId: Int,
    @StringRes val labelResId: Int
);