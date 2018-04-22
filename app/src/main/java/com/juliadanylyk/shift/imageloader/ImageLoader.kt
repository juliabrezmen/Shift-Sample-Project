package com.juliadanylyk.shift.imageloader

import android.content.Context
import android.support.annotation.DrawableRes
import android.widget.ImageView
import com.squareup.picasso.Picasso

class ImageLoader(private val context: Context) {

    fun load(params: Params) {
        Picasso.with(context)
                .load(params.url)
                .placeholder(params.placeHolder)
                .into(params.imageView)
    }

    class Params {

        @DrawableRes
        var placeHolder: Int = 0
        var imageView: ImageView? = null
        var url: String? = null

        fun url(url: String): Params {
            this.url = url
            return this
        }

        fun view(imageView: ImageView): Params {
            this.imageView = imageView
            return this
        }

        fun placeHolder(@DrawableRes placeHolder: Int): Params {
            this.placeHolder = placeHolder
            return this
        }
    }
}