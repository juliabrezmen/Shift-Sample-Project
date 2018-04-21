package com.juliadanylyk.shift.ui.shiftlist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.juliadanylyk.shift.Dependencies.DEPENDENCIES
import com.juliadanylyk.shift.R
import com.juliadanylyk.shift.data.Shift
import com.juliadanylyk.shift.navigator.NavigatorImpl
import com.juliadanylyk.shift.ui.common.LoadingDialog
import com.juliadanylyk.shift.views.ListDivider
import kotlinx.android.synthetic.main.activity_shift_list.*

class ShiftListActivity : AppCompatActivity(), ShiftListContract.View {

    private lateinit var presenter: ShiftListContract.Presenter
    private lateinit var shiftAdapter: ShiftAdapter
    private var loadingDialog: LoadingDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shift_list)

        initView()
        initPresenter()
    }

    override fun updateShifts(shifts: List<Shift>) {
        shiftAdapter.submitList(shifts)
    }

    override fun showEmptyView() {
        emptyView.visibility = View.VISIBLE
    }

    override fun hideEmptyView() {
        emptyView.visibility = View.GONE
    }

    override fun showLoading() {
        loadingDialog = LoadingDialog(this)
        loadingDialog!!.show(getString(R.string.common_loading))
    }

    override fun hideLoading() {
        loadingDialog?.dismiss()
    }

    private fun initView() {
        shiftAdapter = ShiftAdapter(this)
        shiftAdapter.setListener(object : ShiftAdapter.Listener {
            override fun onShiftClicked(shift: Shift) {
                presenter.onShiftClicked(shift)
            }
        })
        with(shifts) {
            adapter = shiftAdapter
            layoutManager = LinearLayoutManager(this@ShiftListActivity)
            addItemDecoration(ListDivider.createDivider(this@ShiftListActivity))
        }
//        addShift.setOnClickListener { presenter.onShiftClicked() }
    }

    private fun initPresenter() {
        val presenter = ShiftListPresenter(this, DEPENDENCIES.shiftRepository, NavigatorImpl(this))
        lifecycle.addObserver(presenter)
        this.presenter = presenter
    }
}
