package com.example.camera

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.widget.ImageView
import com.bumptech.glide.Glide
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStreamWriter

object BitmapHelper {
    fun decodeByteFromBitmap(bitmap: Bitmap): ByteArray {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos)
        return baos.toByteArray()
    }

    fun showBitmap(context: Context?, bitmap: Bitmap, imageView: ImageView?) {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos)
        val data = baos.toByteArray()
        Glide.with(context!!).load(data).into(imageView!!)
    }

    @Throws(Exception::class)
    fun readBitmapFromPath(context: Context, path: Uri?): Bitmap {
        val stream = context.contentResolver.openInputStream(path!!)
        val bitmap = BitmapFactory.decodeStream(stream)
        stream?.close()
        return bitmap
    }

    @Throws(Exception::class)
    fun writeToPublicDirectory(
        filename: String?,
        data: ByteArray?,
        directory: String?,
        environmentDirectory: String?
    ) {
        val publicDirectory =
            File(Environment.getExternalStoragePublicDirectory(environmentDirectory), directory)
        val result = publicDirectory.mkdirs()
        val targetFile = File(publicDirectory, filename)
        val fileOutputStream = FileOutputStream(targetFile)
        fileOutputStream.write(data)
        fileOutputStream.close()
    }

    @Throws(Exception::class)
    fun writeToPublicDirectory(
        filename: String?,
        string: String?,
        directory: String?,
        environmentDirectory: String?
    ) {
        val publicDirectory =
            File(Environment.getExternalStoragePublicDirectory(environmentDirectory), directory)
        val result = publicDirectory.mkdirs()
        val file = File(publicDirectory, filename)
        val fileOutputStream = FileOutputStream(file)
        val outputStreamWriter = OutputStreamWriter(fileOutputStream)
        outputStreamWriter.write(string)
        outputStreamWriter.close()
    }
}