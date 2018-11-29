package com.hosle.toolkit.service.device

import android.content.Context

/**
 * Created by tanjiahao on 2018/11/28
 * Original Project HoToolKit
 */

fun getScreenWidth(context: Context): Int {
    return if (null != context.resources && null != context.resources.displayMetrics) {
        context.resources.displayMetrics.widthPixels
    } else 0
}

fun getScreenHeight(context: Context): Int {
    return if (null != context.resources && null != context.resources.displayMetrics) {
        context.resources.displayMetrics.heightPixels
    } else 0
}