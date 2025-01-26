package Adapters

import Domain.ListFilm
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import pt.ipt.dam.movies.DetailActivity
import pt.ipt.dam.movies.R

class FilmListAdapter(var items: ListFilm) : RecyclerView.Adapter<FilmListAdapter.ViewHolder>() {
    var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val inflate =
            LayoutInflater.from(parent.context).inflate(R.layout.viewholder_film, parent, false)
        return ViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleTxt.text = items.data?.get(position)?.title ?: ""
        var requestOptions = RequestOptions()
        requestOptions = requestOptions.transform(CenterCrop(), RoundedCorners(30))

        Glide.with(context!!)
            .load(items.data?.get(position)?.poster)
            .apply(requestOptions)
            .into(holder.pic)

        holder.itemView.setOnClickListener { view: View? ->
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("id", items.data?.get(position)?.id)
            context!!.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return items.data?.size ?: 0
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleTxt: TextView = itemView.findViewById(R.id.titleTxt)
        var pic: ImageView = itemView.findViewById(R.id.pic)
    }
}
