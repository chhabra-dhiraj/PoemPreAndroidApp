package io.github.chhabra_dhiraj.poempre.repository

import io.github.chhabra_dhiraj.poempre.network.Network

class UserRepository {

    suspend fun getUserAndPoems() = Network.userService!!.getUserAndPoems()

    suspend fun updateUser(
        email: String,
        firstName: String,
        lastName: String
    ) = Network.userService!!.updateUser(
        email,
        firstName,
        lastName,
        "sdhbahdbshjbadshbdshbhjsadshbzjh"
    )

    suspend fun deleteUser() = Network.userService!!.deleteUser()
}