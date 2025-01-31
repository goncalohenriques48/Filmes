package Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pt.ipt.dam.movies.R

/**
 * Adaptador responsável por controlar a lista de categorias de um filme específico
 * @property items Lista de strings com os nomes das categorias do filme
 */
class CategoryEachFilmListAdapter(var items: List<String>) :
    RecyclerView.Adapter<CategoryEachFilmListAdapter.ViewHolder>() {
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
            LayoutInflater.from(parent.context).inflate(R.layout.viewholder_category, parent, false)
        return ViewHolder(inflate)
    }

    /**
     * Associar os dados de uma categoria à ViewHolder
     * @param holder ViewHolder a ser atualizada
     * @param position Posição do item na lista
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.titleTxt.text = items[position]
    }

    /**
     * Retorna o número total de categorias
     * @return Quantidade de categorias na lista
     */
    override fun getItemCount(): Int {
        return items.size
    }

    /**
     * ViewHolder que contém a referência para o TextView da categoria
     * @property titleTxt TextView que mostra o nome da categoria
     */
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleTxt: TextView = itemView.findViewById(R.id.TitleTxt)
    }
}
