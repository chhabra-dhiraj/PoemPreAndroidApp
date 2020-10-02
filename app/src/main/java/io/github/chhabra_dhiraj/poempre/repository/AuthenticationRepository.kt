package io.github.chhabra_dhiraj.poempre.repository

import io.github.chhabra_dhiraj.poempre.network.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthenticationRepository {

    suspend fun checkEmailPresent(email: String) =
        Network.authenticationService!!.checkEmailPresentAsync(email)

    suspend fun login(email: String, password: String) =
        Network.authenticationService!!.login(email, password)

    suspend fun register(
        email: String,
        firstName: String,
        lastName: String,
        password: String
    ) = Network.authenticationService!!.register(
        email,
        firstName,
        lastName,
        "hhaskhhadsdsaahjbhdsbhjbsa",
        password
    )

    suspend fun forgot(email: String) = Network.authenticationService!!.forgot(email)

    suspend fun otpVerify(otp: String) = Network.authenticationService!!.otpVerify(otp)

    suspend fun changePass(email: String, newPass: String) =
        Network.authenticationService!!.changePass(email, newPass)

    suspend fun logout() = Network.authenticationService!!.logout()
}