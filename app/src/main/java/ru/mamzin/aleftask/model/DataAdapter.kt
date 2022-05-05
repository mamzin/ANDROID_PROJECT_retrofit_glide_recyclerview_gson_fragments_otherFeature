package ru.mamzin.aleftask.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.engine.DiskCacheStrategy
import ru.mamzin.aleftask.R
import ru.mamzin.aleftask.utils.GlideApp

class DataAdapter(private val cellClickListener: CellClickListener) :
    RecyclerView.Adapter<DataAdapter.ViewHolder>() {

    var dataList = mutableListOf<String>()

    fun setImageList(urls: List<String>) {
        this.dataList = urls.toMutableList()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        GlideApp.with(holder.itemView.context)
            .load(dataList[position])
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .override(270, 270)
            .centerCrop()
            .error(R.drawable.glide_err_foreground)
            .into(holder.imageView)

        val data = dataList[position]
        holder.itemView.setOnClickListener {
            cellClickListener.onCellClickListener(data)
        }
    }


    class ViewHolder(itemLayoutView: View) : RecyclerView.ViewHolder(itemLayoutView) {
        var imageView: ImageView = itemLayoutView.findViewById(R.id.imageView)
    }


    interface CellClickListener {
        fun onCellClickListener(datalink: String)
    }

}