package io.github.chhabra_dhiraj.poempre.network

import retrofit2.http.*

interface UserService {

    @GET("users")
    suspend fun getUserAndPoems(): GetUserAndPoemApiResponse

    @PATCH("users")
    suspend fun updateUser(
        @Field("email") email: String,
        @Field("firstname") firstName: String,
        @Field("lastname") lastName: String,
        @Field("imageUrl") imageUrl: String
    ): UpdateUserApiResponse

    @DELETE("users")
    suspend fun deleteUser(): RegisterApiResponse
}