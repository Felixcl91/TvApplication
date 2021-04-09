package com.example.tvapplication.ui.movie.adapter

import android.content.Intent
import android.os.Build
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
import com.example.tvapplication.data.model.ResponseItem
import com.example.tvapplication.ui.movie.view.MovieActivity
import com.example.tvapplication.utils.loadImageFromLink

class RecommendationsAdapter(
        private val movies: ArrayList<ResponseItem?>?
) : RecyclerView.Adapter<RecommendationsAdapter.RecViewHolder>() {

    class RecViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var mImage: ImageView? = null
        private var mName: TextView? = null
        private var mValue: ImageButton? = null

        private var valueInt = 0

        init {
            mImage = itemView.findViewById(R.id.image_rec)
            mName = itemView.findViewById(R.id.name_rec)
            mValue = itemView.findViewById(R.id.value_rec)
        }

        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        fun bind(responseItem: ResponseItem) {
            val url = "https://smarttv.orangetv.orange.es/stv/api/rtv/v1/images"
            mImage?.loadImageFromLink(url + responseItem.images!![0]?.value)
            mName?.text = responseItem.name
            val context = mName?.context

            mValue?.setOnClickListener {
                valueInt = 1
                mValue?.background = context?.getDrawable(R.drawable.ic_baseline_star_24)
            }

            itemView.setOnClickListener {
                val intent = Intent(context, MovieActivity::class.java)
                intent.putExtra("id", responseItem.externalContentId)
                intent.putExtra("val", valueInt)
                context?.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            RecViewHolder(
                    LayoutInflater.from(parent.context).inflate(
                            R.layout.item_recommendations,
                            parent, false
                    )
            )

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: RecViewHolder, position: Int) =
            holder.bind(movies!![position]!!)

    override fun getItemCount(): Int = movies!!.size

    fun addData(list: ArrayList<ResponseItem?>?) {
        movies?.addAll(list!!)
    }

}