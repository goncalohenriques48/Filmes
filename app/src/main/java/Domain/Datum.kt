package Domain

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Classe que representa os dados básicos de um filme
 * Utilizada para desserialização dos dados da API usando Gson
 */
class Datum {
    /**
     * Identificador único do filme
     */
    @SerializedName("id")
    @Expose
    var id: Int? = null

    /**
     * Título do filme
     */
    @SerializedName("title")
    @Expose
    var title: String? = null

    /**
     * URL do poster/imagem principal do filme
     */
    @SerializedName("poster")
    @Expose
    var poster: String? = null

    /**
     * Ano de lançamento do filme
     */
    @SerializedName("year")
    @Expose
    var year: String? = null

    /**
     * País de origem do filme
     */
    @SerializedName("country")
    @Expose
    var country: String? = null

    /**
     * Avaliação do filme no IMDB
     */
    @SerializedName("imdb_rating")
    @Expose
    var imdbRating: String? = null

    /**
     * Lista de géneros do filme
     */
    @SerializedName("genres")
    @Expose
    var genres: List<String>? = null

    /**
     * Lista de URLs das imagens adicionais do filme
     */
    @SerializedName("images")
    @Expose
    var images: List<String>? = null
}
