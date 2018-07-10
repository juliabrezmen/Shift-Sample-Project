package com.juliadanylyk.shift.ui.shiftlist

import android.arch.paging.PagedListAdapter
import android.content.Context
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.juliadanylyk.shift.R
import com.juliadanylyk.shift.data.Shift
import com.juliadanylyk.shift.imageloader.ImageLoader
import com.juliadanylyk.shift.utils.DateUtils
import kotlinx.android.synthetic.main.item_shift.view.*

class ShiftAdapter(private val context: Context, val imageLoader: ImageLoader) : PagedListAdapter<Shift, ShiftAdapter.ShiftViewHolder>(ShiftDiffCallback()) {

    private lateinit var onItemClickListener: Listener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ShiftViewHolder(LayoutInflater
            .from(context)
            .inflate(R.layout.item_shift, parent, false))

    override fun onBindViewHolder(holder: ShiftViewHolder, position: Int) = holder.bind(getItem(position))

    fun setListener(onItemClickListener: Listener) {
        this.onItemClickListener = onItemClickListener
    }

    inner class ShiftViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(shift: Shift?) {
            shift?.let {
                itemView.setOnClickListener { onItemClickListener.onShiftClicked(shift) }
                itemView.startTime.text = DateUtils.toDisplayableDate(shift.startTime)

                shift.endTime?.let {
                    itemView.endTime.text = DateUtils.toDisplayableDate(it)
                }

                // if there is no end time it means the shift is still in progress
                if (shift.endTime == null) {
                    itemView.inProgress.visibility = View.VISIBLE
                } else {
                    itemView.inProgress.visibility = View.GONE
                }

                shift.image.let {
                    imageLoader.load(ImageLoader.Params()
                            .url(it)
                            .placeHolder(R.drawable.bg_gray_oval)
                            .view(itemView.shiftImage))
                }
            }
        }
    }

    interface Listener {
        fun onShiftClicked(shift: Shift)
    }
}

class ShiftDiffCallback : DiffUtil.ItemCallback<Shift>() {
    override fun areItemsTheSame(oldItem: Shift?, newItem: Shift?) = oldItem?.id == newItem?.id

    override fun areContentsTheSame(oldItem: Shift?, newItem: Shift?) = oldItem == newItem
}