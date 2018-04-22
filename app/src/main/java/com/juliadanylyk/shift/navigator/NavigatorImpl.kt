package com.juliadanylyk.shift.navigator

import android.app.Activity
import com.juliadanylyk.shift.data.Shift
import com.juliadanylyk.shift.ui.shiftdetails.ShiftDetailsActivity

class NavigatorImpl(private val activity: Activity) : Navigator {

    override fun exitCurrentScreen() {
        activity.finish()
    }

    override fun exitWithResultCodeOk() {
        activity.setResult(Activity.RESULT_OK)
        activity.finish()
    }

    override fun openShiftDetailsScreen(shift: Shift?, requestCode: Int) {
        activity.startActivityForResult(ShiftDetailsActivity.createIntent(activity, shift), requestCode)
    }
}