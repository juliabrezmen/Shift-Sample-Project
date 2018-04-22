package com.juliadanylyk.shift.ui.shiftlist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import com.juliadanylyk.shift.Dependencies.DEPENDENCIES
import com.juliadanylyk.shift.R
import com.juliadanylyk.shift.data.Shift
import com.juliadanylyk.shift.navigator.NavigatorImpl
import com.juliadanylyk.shift.views.ListDivider
import kotlinx.android.synthetic.main.activity_shift_list.*

class ShiftListActivity : AppCompatActivity(), ShiftListContract.View {

    private lateinit var presenter: ShiftListContract.Presenter
    private lateinit var shiftAdapter: ShiftAdapter

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
        if (!swipeRefresh.isRefreshing) {
            swipeRefresh.isRefreshing = true
        }
    }

    override fun hideLoading() {
        if (swipeRefresh.isRefreshing) {
            swipeRefresh.isRefreshing = false
        }
    }

    override fun showError() {
        Toast.makeText(this, R.string.common_something_wrong, Toast.LENGTH_SHORT).show()
    }

    private fun initView() {
        addShift.setOnClickListener { presenter.onAddShiftClicked() }
        swipeRefresh.setOnRefreshListener { presenter.onPullToRefresh() }

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
    }

    private fun initPresenter() {
        val presenter = ShiftListPresenter(this, DEPENDENCIES.shiftRepository, NavigatorImpl(this))
        lifecycle.addObserver(presenter)
        this.presenter = presenter
    }
}
