package io.github.chhabra_dhiraj.poempre.network

import retrofit2.http.*

interface PoemService {

    @FormUrlEncoded
    @POST("poetries")
    suspend fun createPoem(
        @Field("title") title: String,
        @Field("genre") genre: String,
        @Field("body") body: String
    ): RegisterApiResponse

    @FormUrlEncoded
    @PUT("poetries/{id}")
    suspend fun updatePoem(
        @Path("id") poemId: Int,
        @Field("title") title: String,
        @Field("genre") genre: String,
        @Field("body") body: String
    ): RegisterApiResponse

    @DELETE("poetries/{id}")
    suspend fun deletePoem(
        @Path("id") poemId: Int
    ): RegisterApiResponse

}