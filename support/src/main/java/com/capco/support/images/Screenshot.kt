package com.capco.support.images

import android.graphics.Bitmap
import android.os.Environment
import android.text.format.DateFormat
import android.view.View
import com.bits.support.datetime.now
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

fun screenshot(view: View, filename: String): File? {
    val date = Date()

    // Here we are initialising the format of our image name
    val format: CharSequence = DateFormat.format("yyyy-MM-dd_hh:mm:ss", date)
    try {
        // Initialising the directory of storage
        val dirpath: String = Environment.getExternalStorageDirectory().toString() + "/Filmibeat/$filename/"
        val file = File(dirpath)
        if (!file.exists()) {
            val mkdir: Boolean = file.mkdir()
        }

        // File name
//        val path = "${dirpath}/Filmibeat/${filename}_${now("yyyyMMdd_hhmmss")}.jpeg"

        val path = "$dirpath/${filename}_${now("yyyyMMdd_hhmmss")}.jpeg"

        view.isDrawingCacheEnabled = true
        val bitmap: Bitmap = Bitmap.createBitmap(view.drawingCache)
        view.isDrawingCacheEnabled = false
        val imageFile = File(path)
        val outputStream = FileOutputStream(imageFile)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream)
        outputStream.flush()
        outputStream.close()
        return imageFile
    } catch (io: FileNotFoundException) {
        io.printStackTrace()
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return null
}

fun shareScreenshot(view: View, filename: String, message: String){
    val screenshotFile = screenshot(view,filename)
    screenshotFile?.let {
        view.context?.let {
            shareImage(it,screenshotFile,message)
        }
    }
}