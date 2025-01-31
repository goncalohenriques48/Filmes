package Domain

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Classe que representa uma lista de filmes que é retornada pela API
 * Utilizada para desserialização da resposta JSON da API usando Gson
 */
class ListFilm {
    /**
     * Lista de filmes retornados pela API
     * Contém os dados básicos de cada filme
     * Pode ser nulo caso não haja filmes ou ocorra erro na resposta
     */
    @SerializedName("data")
    @Expose
    var data: List<Datum>? = null

    /**
     * Metadados da lista de filmes
     * Contém informações adicionais sobre a resposta da API
     * Pode ser nulo caso não haja metadados disponíveis
     */
    @SerializedName("metadata")
    @Expose
    var metadata: Metadata? = null
}
