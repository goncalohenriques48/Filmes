package Domain

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ListFilm {
    @SerializedName("data")
    @Expose
    var data: List<Datum>? = null

    @SerializedName("metadata")
    @Expose
    var metadata: Metadata? = null
}
