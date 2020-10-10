package com.lucasdev.pagingexam

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.api.load

object BindingAdapters {
    @BindingAdapter("imgUrl")
    @JvmStatic
    fun setImgSrc(imageView: ImageView, imgUrl: String?) {
        imageView.load(imgUrl)
    }
}
