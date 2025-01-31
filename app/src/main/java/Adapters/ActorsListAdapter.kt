package Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import pt.ipt.dam.movies.R

/**
 * Adaptador responsável por controlar a lista de atores de um filme
 * @property items Lista de atores a serem mostrados
 */
class ActorsListAdapter(var images: List<String>) :
    RecyclerView.Adapter<ActorsListAdapter.ViewHolder>() {
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
            LayoutInflater.from(parent.context).inflate(R.layout.viewholder_actors, parent, false)
        return ViewHolder(inflate)
    }

    /**
     * Associa os dados de um ator à ViewHolder
     * @param holder ViewHolder a ser atualizada
     * @param position Posição do item na lista
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context!!)
            .load(images[position]) // URL da imagem
            .into(holder.pic)       // ImageView destino
    }

    /**
     * Retorna o número total de atores
     * @return Quantidade de atores na lista
     */
    override fun getItemCount(): Int {
        return images.size
    }

    /**
     * ViewHolder que contém as referências para as views de cada item
     * @property pic ImageView que mostra a foto do ator
     */
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var pic: ImageView = itemView.findViewById(R.id.itemImages)
    }
}
