package com.example.tvapplication.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * Loads the Image from [link] into ImageView
 */
fun ImageView.loadImageFromLink(link: String?) {
    if (!link.isNullOrEmpty())
        Glide.with(context.applicationContext)
            .load(link)
            //.signature(StringSignature(link))
            //.dontAnimate()
            //.placeholder(R.drawable.ic_image)
            .into(this)
}