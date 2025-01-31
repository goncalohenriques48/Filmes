package Domain

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Classe que representa os metadados de uma lista paginada de filmes
 * Utilizada para desserialização das informações de paginação da API usando Gson
 */
class Metadata {
    /**
     * Número da página atual na paginação
     * Pode ser nulo caso não haja paginação
     */
    @SerializedName("current_page")
    @Expose
    var currentPage: String? = null

    /**
     * Quantidade de itens por página
     * Define quantos filmes são retornados em cada página
     * Pode ser nulo caso não haja paginação
     */
    @SerializedName("per_page")
    @Expose
    var perPage: Int? = null

    /**
     * Número total de páginas disponíveis
     * Calculado com base no total de itens e itens por página
     * Pode ser nulo caso não haja paginação
     */
    @SerializedName("page_count")
    @Expose
    var pageCount: Int? = null

    /**
     * Número total de itens disponíveis
     * Representa a quantidade total de filmes na base de dados
     * Pode ser nulo caso não seja possível determinar o total
     */
    @SerializedName("total_count")
    @Expose
    var totalCount: Int? = null
}
