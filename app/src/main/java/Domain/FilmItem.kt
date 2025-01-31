package Domain

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Classe que representa os detalhes completos de um filme
 * Utilizada para desserialização dos dados detalhados da API usando Gson
 */
class FilmItem {
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
     * Classificação indicativa do filme
     */
    @SerializedName("rated")
    @Expose
    var rated: String? = null

    /**
     * Data de lançamento do filme
     */
    @SerializedName("released")
    @Expose
    var released: String? = null

    /**
     * Duração do filme
     */
    @SerializedName("runtime")
    @Expose
    var runtime: String? = null

    /**
     * Nome do diretor do filme
     */
    @SerializedName("director")
    @Expose
    var director: String? = null

    /**
     * Nome do(s) escritor(s) do filme
     */
    @SerializedName("writer")
    @Expose
    var writer: String? = null

    /**
     * Lista dos principais atores do filme
     */
    @SerializedName("actors")
    @Expose
    var actors: String? = null

    /**
     * Sinopse do filme
     */
    @SerializedName("plot")
    @Expose
    var plot: String? = null

    /**
     * País de origem do filme
     */
    @SerializedName("country")
    @Expose
    var country: String? = null

    /**
     * Prémios recebidos pelo filme
     */
    @SerializedName("awards")
    @Expose
    var awards: String? = null

    /**
     * Pontuação Metascore do filme
     */
    @SerializedName("metascore")
    @Expose
    var metascore: String? = null

    /**
     * Avaliação do filme no IMDB
     */
    @SerializedName("imdb_rating")
    @Expose
    var imdbRating: String? = null

    /**
     * Número de votos no IMDB
     */
    @SerializedName("imdb_votes")
    @Expose
    var imdbVotes: String? = null

    /**
     * ID do filme no IMDB
     */
    @SerializedName("imdb_id")
    @Expose
    var imdbId: String? = null

    /**
     * Tipo do conteúdo (filme, série, etc)
     */
    @SerializedName("type")
    @Expose
    var type: String? = null

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
