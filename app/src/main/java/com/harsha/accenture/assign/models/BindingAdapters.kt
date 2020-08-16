package com.harsha.accenture.assign.models

import android.app.Activity
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.ahmadrosid.svgloader.SvgLoader

object BindingAdapters {
    @BindingAdapter("app:imageThumb")
    @JvmStatic
    fun loadImage(imageView: ImageView, imageUrl: String) {
        SvgLoader.pluck()
            .with(imageView.context as Activity?)
            .setPlaceHolder(
                com.harsha.accenture.assign.R.mipmap.ic_launcher,
                com.harsha.accenture.assign.R.mipmap.ic_launcher
            )
            .load(imageUrl, imageView);
    }
}