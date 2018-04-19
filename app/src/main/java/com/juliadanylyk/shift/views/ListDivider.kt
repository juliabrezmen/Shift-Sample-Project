package com.juliadanylyk.shift.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import com.juliadanylyk.shift.R

class ListDivider(context: Context, @DrawableRes resId: Int) : RecyclerView.ItemDecoration() {
    private var divider: Drawable? = ContextCompat.getDrawable(context, resId)

    companion object {
        fun createDivider(context: Context): ListDivider {
            return ListDivider(context, R.drawable.list_divider)
        }
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State?) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)

            val params = child.layoutParams as RecyclerView.LayoutParams

            val top = child.bottom + params.bottomMargin
            divider?.let {
                val bottom = top + it.intrinsicHeight
                it.setBounds(left, top, right, bottom)
                it.draw(c)
            }
        }
    }
}