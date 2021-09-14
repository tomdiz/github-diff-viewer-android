package com.example.diffviewer.databinding

import android.widget.ImageView
import androidx.databinding.BindingAdapter

import com.example.diffviewer.R
import com.squareup.picasso.Picasso

object PicassoBindingAdapters {

    @JvmStatic
    @BindingAdapter("imageResource")
    fun setImageResource(view: ImageView, imageUrl: Int) {
        Picasso.get()
            .load(imageUrl)
            .placeholder(R.mipmap.no_image_placeholder)
            .into(view)
    }

    @JvmStatic
    @BindingAdapter("imageResource")
    fun setImageResource(view: ImageView, imageUrl: String?) {

        if (imageUrl != null && imageUrl.length > 5) {
            Picasso.get()
                .load(imageUrl)
                .placeholder(R.mipmap.no_image_placeholder)
                .into(view)
        } else {
            Picasso.get()
                .load(R.mipmap.no_image_placeholder)
                .placeholder(R.mipmap.no_image_placeholder)
                .into(view)
        }

    }

}
