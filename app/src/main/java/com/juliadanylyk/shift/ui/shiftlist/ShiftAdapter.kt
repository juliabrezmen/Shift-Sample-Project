package com.juliadanylyk.shift.ui.shiftlist

import android.content.Context
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.juliadanylyk.shift.Dependencies.DEPENDENCIES
import com.juliadanylyk.shift.R
import com.juliadanylyk.shift.data.Shift
import com.juliadanylyk.shift.imageloader.ImageLoader
import com.juliadanylyk.shift.utils.DateUtils
import kotlinx.android.synthetic.main.item_shift.view.*

class ShiftAdapter(private val context: Context) : ListAdapter<Shift, ShiftAdapter.ShiftViewHolder>(ShiftDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ShiftViewHolder(LayoutInflater
            .from(context)
            .inflate(R.layout.item_shift, parent, false))

    override fun onBindViewHolder(holder: ShiftViewHolder, position: Int) = holder.bind(getItem(position))

    inner class ShiftViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(shift: Shift) {
            shift.startTime?.let {
                itemView.startTime.text = DateUtils.toDisplayableDate(it)
            }
            shift.endTime?.let {
                itemView.endTime.text = DateUtils.toDisplayableDate(it)
            }
            DEPENDENCIES.imageLoader.load(ImageLoader.Params()
                    .url(shift.image)
                    .placeHolder(R.drawable.bg_gray_oval)
                    .view(itemView.shiftImage))
        }
    }
}

class ShiftDiffCallback : DiffUtil.ItemCallback<Shift>() {
    override fun areItemsTheSame(oldItem: Shift?, newItem: Shift?) = oldItem?.id == newItem?.id

    override fun areContentsTheSame(oldItem: Shift?, newItem: Shift?) = oldItem == newItem
}