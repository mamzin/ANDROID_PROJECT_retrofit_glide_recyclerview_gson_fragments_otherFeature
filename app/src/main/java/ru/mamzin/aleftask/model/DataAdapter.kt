package ru.mamzin.aleftask.model

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ru.mamzin.aleftask.R

class DataAdapter(
    private var dataList: List<String>,
    private val context: Context,
    private val cellClickListener: CellClickListener
) :
    RecyclerView.Adapter<DataAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return dataList.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context)
            .load(dataList[position])
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