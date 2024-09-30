package com.capco.support.images

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.FileProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.igreenwood.loupe.Loupe
import java.io.File

data class Image(
        var uri: Uri? = null,
        var url: String? = null
)

fun Activity.openImage(imageView: ImageView, viewgroup: ViewGroup){
    Loupe.create(imageView,viewgroup) {

        // imageView is your ImageView
        onViewTranslateListener = object : Loupe.OnViewTranslateListener {

            override fun onStart(view: ImageView) {
                // called when the view starts moving
            }

            override fun onViewTranslate(view: ImageView, amount: Float) {
                // called whenever the view position changed
            }

            override fun onRestore(view: ImageView) {
                // called when the view drag gesture ended
            }

            override fun onDismiss(view: ImageView) {
                // called when the view drag gesture ended
                finish()
            }
        }
    }
}

fun ImageView.loadImage(image: Image, callback : (Exception?) -> Unit){

    if (image.uri != null){
        callback(null)
    } else if (!image.url.isNullOrEmpty()){
        try {

            Glide.with(this)
                    .load(image.url)
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                            callback(e)
                            return false
                        }
                        override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                            callback(null)
                            return false
                        }
                    })
                    .into(this)
        } catch (e: Exception){
            e.printStackTrace()
            callback(e)
        }
    } else {
    }
}

fun ImageView.loadImage(imageUrl : String, callback : (Exception?) -> Unit){
    try {
        Glide.with(this)
                .load(imageUrl)
                .listener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        callback(e)
                        return false
                    }
                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                        callback(null)
                        return false
                    }
                })
                .into(this)
    } catch (e: Exception){
        e.printStackTrace()
        callback(e)
    }
}

fun ImageView.loadImage(imageUrl: String, placeholder: Drawable?, callback: (Exception?) -> Unit){
    try {
        Glide.with(this)
            .load(imageUrl)
            .placeholder(placeholder)
            .listener(object : RequestListener<Drawable> {
                override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                    callback(e)
                    return false
                }
                override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                    callback(null)
                    return false
                }
            })
            .into(this)
    } catch (e: Exception){
        e.printStackTrace()
        callback(e)
    }
}

fun ImageView.loadImage(imageUrl : String){
    try {
        Glide.with(this)
            .load(imageUrl)
            .into(this)
    } catch (e: Exception){
        e.printStackTrace()
    }
}

fun ImageView.loadImageInCircle(imageUrl : String){
    try {
        Glide.with(this)
                .load(imageUrl)
                .circleCrop()
                .into(this)
    } catch (e: Exception){
        e.printStackTrace()
    }
}

fun ImageView.loadImage(imageUri : Uri){
    Glide.with(this)
        .load(imageUri)
        .into(this)
}

fun shareImage(context: Context, shareImage: File, shareText : String) {
    val intent = Intent()
    intent.action = Intent.ACTION_SEND
    intent.type = "image/*"
    val shareImageUri = FileProvider.getUriForFile(
        context,
        context.applicationContext.packageName.toString() + ".provider",
        shareImage
    )
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
    intent.putExtra(Intent.EXTRA_STREAM, shareImageUri)
    intent.putExtra(Intent.EXTRA_TEXT, shareText)
    context.startActivity(Intent.createChooser(intent, "Share Image"))
}