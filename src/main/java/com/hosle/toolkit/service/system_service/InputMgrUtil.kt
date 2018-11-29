package com.hosle.toolkit.service.system_service

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * Created by tanjiahao on 2018/11/28
 * Original Project HoToolKit
 */

fun showSoftInput(context: Context, edit: EditText) {
    edit.isFocusable = true
    edit.isFocusableInTouchMode = true
    edit.requestFocus()
    val imm = context
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(edit, 0)
}

fun hideSoftInput(context: Context, edit: EditText) {

    val imm = context
            .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(edit.windowToken,0)
}