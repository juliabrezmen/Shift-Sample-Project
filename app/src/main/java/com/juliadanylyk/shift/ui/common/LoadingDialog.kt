package com.juliadanylyk.shift.ui.common

import android.app.ProgressDialog
import android.content.Context

class LoadingDialog(private val context: Context) {
    private var dialog: ProgressDialog? = null

    fun show(message: String) {
        if (dialog == null) {
            dialog = ProgressDialog.show(context, null, message)
            dialog!!.setCancelable(false)
        }
    }

    fun dismiss() {
        dialog?.let {
            dialog!!.dismiss()
            dialog = null
        }
    }
}