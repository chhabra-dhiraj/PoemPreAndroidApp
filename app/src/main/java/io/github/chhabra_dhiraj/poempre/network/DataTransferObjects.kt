package io.github.chhabra_dhiraj.poempre.network

import io.github.chhabra_dhiraj.poempre.domain.Poem
import io.github.chhabra_dhiraj.poempre.domain.Sentence
import io.github.chhabra_dhiraj.poempre.domain.User

data class LoginApiResponse(
    val message: String,
    val sessionId: String,
    val user: User
)

data class RegisterApiResponse(
    val isSuccessfull: Boolean,
    val isLoginSuccessfull: Boolean,
    val message: String,
    val sessionId: String,
    val user: User
)

data class ForgotApiResponse(
    val isSuccessfull: Boolean,
    val message: String
)

data class OtpApiResponse(
    val isVerificationSuccessfull: Boolean,
    val message: String
)

data class ChangePassApiResponse(
    val isSuccessfull: Boolean,
    val message: String
)

data class GetUserAndPoemApiResponse(
    val isSuccessfull: Boolean,
    val message: String,
    val user: User,
    val poems: List<Poem>
)

data class UpdateUserApiResponse(
    val isSuccessfull: Boolean,
    val message: String,
    val user: User
)

data class SuggestionApiResponse(
    val isSuccessfull: Boolean,
    val message: String,
    val sentencesList: List<Sentence>?
)

data class LogoutApiResponse(
    val message: String
)