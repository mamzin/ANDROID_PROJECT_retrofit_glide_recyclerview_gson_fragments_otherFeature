package ru.mamzin.aleftask.views

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.load.engine.DiskCacheStrategy
import ru.mamzin.aleftask.R
import ru.mamzin.aleftask.utils.GlideApp

class FullImage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_image)


        var link = intent.getStringExtra("IMG_SRC")

        var imageView: ImageView = findViewById(R.id.fullimage)
        GlideApp.with(this)
            .load(link)
            .centerCrop()
            .error(R.drawable.glide_err_foreground)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(imageView)
    }
}