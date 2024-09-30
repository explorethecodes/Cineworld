package com.capco.widgets.image

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capco.support.images.loadImage
import com.capco.support.images.openImage
import com.capco.support.views.hide
import com.capco.support.views.show
import com.capco.widgets.databinding.ActivityImageBinding

class ImageActivity: AppCompatActivity() {

    lateinit var binding : ActivityImageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        create()
    }

    private fun create() {
        val imageUrl = intent.getStringExtra("image_url").toString()

        imageProgress(true)
        binding.idImage.loadImage(imageUrl){
            imageProgress(false)
            openImage(binding.idImage,binding.idRoot)
        }

        binding.idBack.setOnClickListener {
            finish()
        }
    }

    private fun imageProgress(isLoading : Boolean){
        if (isLoading){
            binding.progress.show()
        } else {
            binding.progress.hide()
        }
    }
}