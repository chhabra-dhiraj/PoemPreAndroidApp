package io.github.chhabra_dhiraj.poempre.network

import retrofit2.http.*

interface SuggestionService {

    @GET("poems")
    suspend fun getPoemSuggestions(
        @Query("word") word: String,
        @Query("genre") genre: String
    ): SuggestionApiResponse

}