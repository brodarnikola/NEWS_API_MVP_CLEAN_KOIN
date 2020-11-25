package com.vjezba.data.model


import com.google.gson.annotations.SerializedName
import com.vjezba.domain.entities.MainResponse
import com.vjezba.domain.entities.UserRepo

data class MainResponseDAO(

    @SerializedName("total_count")
    val total_count: Int,
    @SerializedName("incomplete_results")
    val incomplete_results: Boolean,
    val items: List<UserRepo>
)


fun MainResponseDAO.mapToMainReponse(): MainResponse {
    return MainResponse(total_count, incomplete_results, items)
}