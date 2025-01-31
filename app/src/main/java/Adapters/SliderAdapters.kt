package Adapters

import Adapters.SliderAdapters.SliderViewHolder
import Domain.SliderItems
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import pt.ipt.dam.movies.R

/**
 * Adaptador responsável por controlar o slider de imagens na aplicação
 * @property sliderItems Lista de itens a serem exibidos no slider
 * @property viewPager2 ViewPager2 que mostrará os itens do slider
 */
class SliderAdapters(sliderItems: MutableList<SliderItems>, private val viewPager2: ViewPager2) :
    RecyclerView.Adapter<SliderViewHolder?>() {
    private val sliderItems: List<SliderItems> = sliderItems
    private var context: Context? = null

    /**
     * Cria uma nova ViewHolder para o slider
     * @param parent ViewGroup pai que conterá a View
     * @param viewType Tipo da view a ser criada
     * @return SliderViewHolder configurada
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        context = parent.context
        return SliderViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.slide_item_container, parent, false
            )
        )
    }

    /**
     * Associa os dados de um item do slider à ViewHolder
     * @param holder ViewHolder a ser atualizada
     * @param position Posição do item na lista
     */
    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.setImage(sliderItems[position])
        // Verifica se precisa de adicionar mais itens ao slider
        if (position == sliderItems.size - 2) {
            viewPager2.post(runnable)
        }
    }

    /**
     * Retorna o número total de itens no slider
     * @return Quantidade de itens no slider
     */
    override fun getItemCount(): Int {
        return sliderItems.size
    }

    /**
     * ViewHolder que contém a referência para a ImageView do slider
     * @property imageView ImageView que mostra a imagem do slider
     */
    inner class SliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.imageSlide)
        /**
         * Configura a imagem no ImageView usando Glide
         * @param sliderItems Item do slider que contém a imagem a ser mostrada
         */
        fun setImage(sliderItems: SliderItems) {
            var requestOptions = RequestOptions()
            requestOptions = requestOptions.transforms(CenterCrop(), RoundedCorners(60))

            Glide.with(context!!)
                .load(sliderItems.image)
                .apply(requestOptions)
                .into(imageView)
        }
    }

    /**
     * Runnable que adiciona mais itens ao slider quando necessário
     * para criar um efeito de loop infinito
     */
    private val runnable = Runnable {
        sliderItems.addAll(sliderItems)
        notifyDataSetChanged()
    }
}
