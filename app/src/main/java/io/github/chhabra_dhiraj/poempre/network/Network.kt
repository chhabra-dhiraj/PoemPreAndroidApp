package io.github.chhabra_dhiraj.poempre.network

import io.github.chhabra_dhiraj.poempre.utils.SharedPreferencesManager
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object Network {
    private val httpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
        redactHeader("Authorization")
        redactHeader("Cookie")
    }

    private val okHttpClient = OkHttpClient.Builder().apply {
        connectTimeout(20, TimeUnit.SECONDS)
        addInterceptor(httpLoggingInterceptor)
        addInterceptor { chain ->

            val sessionId = SharedPreferencesManager.instance!!.sessionId

            val request: Request = if (sessionId != null) {
                chain.request()
                    .newBuilder()
                    .addHeader("Cookie: ", "connect.sid=$sessionId")
                    .build()
            } else {
                chain.request()
                    .newBuilder()
                    .build()
            }

            chain.proceed(request)
        }
    }.build()

    // Configure retrofit to parse JSON and use coroutines
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.0.11:5000/api/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val authenticationService: AuthenticationService? =
        retrofit.create(AuthenticationService::class.java)
    val userService: UserService? = retrofit.create(UserService::class.java)
    val poemService: PoemService? = retrofit.create(PoemService::class.java)
    val suggestionService: SuggestionService? = retrofit.create(SuggestionService::class.java)
}
