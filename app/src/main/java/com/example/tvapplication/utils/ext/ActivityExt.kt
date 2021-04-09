package com.example.tvapplication.utils

import android.app.Activity
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.tvapplication.R

/**
 * Creates a Progress Dialog from Activity
 * @return
 */
fun Activity.createProgressDialog(): Dialog {
    val progressDialog = Dialog(this)
    progressDialog.show()
    if (progressDialog.window != null) {
        progressDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
    progressDialog.setContentView(R.layout.progress_dialog)
    progressDialog.setCancelable(false)
    progressDialog.setCanceledOnTouchOutside(false)
    return progressDialog
}

/**
 * Extension function to hide the keyboard
 */
fun Activity.hideKeyboard() {
    val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    //Find the currently focused view, so we can grab the correct window token from it.
    var view = currentFocus
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    if (view == null) {
        view = View(this)
    }
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}