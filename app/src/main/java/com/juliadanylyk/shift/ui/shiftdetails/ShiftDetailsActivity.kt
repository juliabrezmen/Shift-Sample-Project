package com.juliadanylyk.shift.ui.shiftdetails

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.annotation.StringRes
import android.view.View
import android.widget.Toast
import com.juliadanylyk.DispatcherImpl
import com.juliadanylyk.location.LocationManagerImpl
import com.juliadanylyk.shift.Dependencies
import com.juliadanylyk.shift.R
import com.juliadanylyk.shift.data.Shift
import com.juliadanylyk.shift.imageloader.ImageLoader
import com.juliadanylyk.shift.navigator.NavigatorImpl
import com.juliadanylyk.shift.ui.base.BaseActivity
import com.juliadanylyk.shift.ui.common.LOCATION_REQUEST_CODE
import com.juliadanylyk.shift.ui.common.LoadingDialog
import com.juliadanylyk.shift.utils.DateUtils
import kotlinx.android.synthetic.main.activity_shift_details.*

class ShiftDetailsActivity : BaseActivity(), ShiftDetailsContract.View {

    private lateinit var presenter: ShiftDetailsContract.Presenter
    private var loadingDialog: LoadingDialog? = null

    companion object {
        private const val KEY_SHIFT = "KEY_SHIFT"
        fun createIntent(context: Context, shift: Shift?) = Intent(context, ShiftDetailsActivity::class.java)
                .apply { putExtra(KEY_SHIFT, shift) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shift_details)

        initToolbar()
        initView()
        initPresenter()
    }

    override fun getShiftFromExtras(): Shift? = intent.getParcelableExtra(KEY_SHIFT)

    override fun initNewState() {
        status.text = getString(R.string.shift_details_new)
        startTime.text = getString(R.string.shift_details_start_time_now)
        startLocation.text = getString(R.string.shift_details_start_location_now)
        startShift.visibility = View.VISIBLE
    }

    override fun initInProgressState(startTime: Long, startLatitude: Double, startLongitude: Double, image: String) {
        status.text = getString(R.string.shift_details_in_progress)
        endShift.visibility = View.VISIBLE
        this.startTime.text = getString(R.string.shift_details_start_time, DateUtils.toDisplayableDate(startTime))
        this.startLocation.text = getString(R.string.shift_details_start_location, startLatitude, startLongitude)
        showShiftImage(image)
    }

    override fun initCompletedState(startTime: Long, startLatitude: Double, startLongitude: Double, endTime: Long,
                                    endLatitude: Double, endLongitude: Double, image: String) {
        status.text = getString(R.string.shift_details_completed)
        this.startTime.text = getString(R.string.shift_details_start_time, DateUtils.toDisplayableDate(startTime))
        this.startLocation.text = getString(R.string.shift_details_start_location, startLatitude, startLongitude)
        this.endTime.text = getString(R.string.shift_details_end_time, DateUtils.toDisplayableDate(endTime))
        this.endLocation.text = getString(R.string.shift_details_end_location, startLatitude, startLongitude)
        showShiftImage(image)
    }

    override fun showLoading() {
        loadingDialog = LoadingDialog(this)
        loadingDialog?.show(getString(R.string.common_loading))
    }

    override fun hideLoading() {
        loadingDialog?.dismiss()
    }

    override fun showToast(@StringRes message: Int) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun requestLocationPermission(callback: (granted: Boolean) -> Unit) {
        requestPermission(Manifest.permission.ACCESS_FINE_LOCATION, LOCATION_REQUEST_CODE, callback)
    }

    private fun showShiftImage(url: String) {
        Dependencies.imageLoader.load(ImageLoader.Params()
                .url(url)
                .placeHolder(R.drawable.bg_gray_rect)
                .view(shiftImage))
    }

    private fun initView() {
        startShift.setOnClickListener { presenter.onStartShiftClicked() }
        endShift.setOnClickListener { presenter.onEndShiftClicked() }
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener({ presenter.onBackClicked() })
    }

    private fun initPresenter() {
        val presenter = ShiftDetailsPresenter(this,
                Dependencies.shiftRepository,
                LocationManagerImpl(this),
                DispatcherImpl,
                NavigatorImpl(this))
        lifecycle.addObserver(presenter)
        this.presenter = presenter
    }
}