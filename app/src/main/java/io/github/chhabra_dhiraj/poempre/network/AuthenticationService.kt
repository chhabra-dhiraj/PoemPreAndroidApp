package io.github.chhabra_dhiraj.poempre.network

import retrofit2.Response
import retrofit2.http.*

interface AuthenticationService {

    @FormUrlEncoded
    @POST("email")
    suspend fun checkEmailPresentAsync(@Field("email") email: String): Boolean

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<LoginApiResponse>

    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("email") email: String,
        @Field("firstname") firstName: String,
        @Field("lastname") lastName: String,
        @Field("imageUrl") imageUrl: String,
        @Field("password") password: String
    ): Response<RegisterApiResponse>

    @FormUrlEncoded
    @POST("forgot")
    suspend fun forgot(@Field("email") email: String): ForgotApiResponse

    @FormUrlEncoded
    @POST("otp")
    suspend fun otpVerify(@Field("otp") otp: String): OtpApiResponse

    @FormUrlEncoded
    @POST("changepass")
    suspend fun changePass(
        @Field("email") email: String,
        @Field("newPass") newPass: String
    ): ChangePassApiResponse

    @GET("logout")
    suspend fun logout(): LogoutApiResponse
}