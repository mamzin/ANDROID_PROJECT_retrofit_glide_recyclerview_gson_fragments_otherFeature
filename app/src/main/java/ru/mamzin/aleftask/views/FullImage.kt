package ru.mamzin.aleftask.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.bumptech.glide.Glide
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
            .error(R.drawable.glide_err_foreground)
            .into(imageView)
    }
}