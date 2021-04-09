package com.example.tvapplication.ui.main.adapter

import android.content.Intent
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.tvapplication.R
import com.example.tvapplication.data.model.Movie
import com.example.tvapplication.ui.movie.view.MovieActivity
import com.example.tvapplication.utils.loadImageFromLink
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_list.view.*

class MainAdapter(
    private val movies: ArrayList<Movie>
) : RecyclerView.Adapter<MainAdapter.DataViewHolder>() {



    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var mImage: ImageView? = null
        private var mName: TextView? = null
        private var mValue: ImageButton? = null

        private var valueInt = 0

        init {
            mImage = itemView.findViewById(R.id.image_item)
            mName = itemView.findViewById(R.id.name_movie)
            mValue = itemView.findViewById(R.id.value_movie)
        }

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        fun bind(movie: Movie) {
            val url = "https://smarttv.orangetv.orange.es/stv/api/rtv/v1/images"
            mImage?.loadImageFromLink(url+ movie.attachments?.get(2)?.value)
            mName?.text = movie.shortName
            val context = mName?.context

            mValue?.setOnClickListener {
                valueInt = 1
                mValue?.background = context?.getDrawable(R.drawable.ic_baseline_star_24)
            }

            itemView.setOnClickListener {
                val intent = Intent(context, MovieActivity::class.java)
                intent.putExtra("id", movie.assetExternalId)
                intent.putExtra("val", valueInt)
                context?.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_list, parent,
                false
            )
        )

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: DataViewHolder, position: Int) =
        holder.bind(movies[position])

    override fun getItemCount(): Int = movies.size

    fun addData(list: ArrayList<Movie>) {
        movies.addAll(list)
    }
}