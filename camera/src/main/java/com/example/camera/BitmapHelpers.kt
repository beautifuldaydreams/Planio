package com.example.camera

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.ByteArrayOutputStream

object BitmapHelper {

    fun showBitmap(context: Context?, bitmap: Bitmap, imageView: ImageView?) {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos)
        val data = baos.toByteArray()
        Glide.with(context!!).load(data).into(imageView!!)
    }
}