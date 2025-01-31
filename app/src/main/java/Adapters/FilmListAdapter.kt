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

/**
 * Adaptador responsável por controlar a lista de filmes no RecyclerView
 * @property context Contexto da aplicação
 * @property items Lista de filmes a serem mostrados
 */
class FilmListAdapter(var items: ListFilm) : RecyclerView.Adapter<FilmListAdapter.ViewHolder>() {
    var context: Context? = null

    /**
     * Cria uma nova ViewHolder quando necessário
     * @param parent ViewGroup pai que conterá a View
     * @param viewType Tipo da view a ser criada
     * @return ViewHolder configurada
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val inflate =
            LayoutInflater.from(parent.context).inflate(R.layout.viewholder_film, parent, false)
        return ViewHolder(inflate)
    }

    /**
     * Associa os dados de um filme à ViewHolder
     * @param holder ViewHolder a ser atualizada
     * @param position Posição do item na lista
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Define o título do filme
        holder.titleTxt.text = items.data?.get(position)?.title ?: ""
        // Carrega a imagem do filme usando o Glide
        var requestOptions = RequestOptions()
        requestOptions = requestOptions.transform(CenterCrop(), RoundedCorners(30))

        // Carregamento da imagem
        Glide.with(context!!)
            .load(items.data?.get(position)?.poster)
            .apply(requestOptions)
            .into(holder.pic)

        // Configura o clique no item para abrir os detalhes do filme
        holder.itemView.setOnClickListener { view: View? ->
            val intent = Intent(holder.itemView.context, DetailActivity::class.java)
            intent.putExtra("id", items.data?.get(position)?.id)
            context!!.startActivity(intent)
        }
    }

    /**
     * Retorna o número total de itens na lista
     * @return Quantidade de filmes na lista
     */
    override fun getItemCount(): Int {
        return items.data?.size ?: 0
    }

    /**
     * ViewHolder que contém as referências para as views de cada item
     * @property titleTxt TextView que mostra o título do filme
     * @property pic ImageView que mostra o poster do filme
     */
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleTxt: TextView = itemView.findViewById(R.id.titleTxt)
        var pic: ImageView = itemView.findViewById(R.id.pic)
    }
}
