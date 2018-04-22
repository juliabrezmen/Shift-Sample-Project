package com.juliadanylyk.shift.ui.base

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    private var requestCode: Int = 0
    private var callback: ((granted: Boolean) -> Unit)? = null

    override fun onRequestPermissionsResult(currentRequestCode: Int, permissions: Array<String>, vararg grantResults: Int) {
        if (requestCode == currentRequestCode && permissionGranted(*grantResults)) {
            onPermissionEvent(true)
        } else {
            onPermissionEvent(false)
        }
    }

    protected fun requestPermission(permission: String, requestCode: Int, callback: (granted: Boolean) -> Unit) {
        this.callback = callback
        if (hasPermission(applicationContext, permission)) {
            onPermissionEvent(true)
        } else {
            this.requestCode = requestCode
            ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
        }
    }

    private fun onPermissionEvent(granted: Boolean) {
        callback?.invoke(granted)
    }

    private fun hasPermission(context: Context, permission: String): Boolean {
        return !isAtLeastMarshmallow() || ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }

    private fun isAtLeastMarshmallow(): Boolean {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
    }

    private fun permissionGranted(vararg grantResults: Int): Boolean {
        return grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED
    }

}