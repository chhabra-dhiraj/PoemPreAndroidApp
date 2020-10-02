package io.github.chhabra_dhiraj.poempre.repository

import io.github.chhabra_dhiraj.poempre.network.Network

class PoemRepository {

    suspend fun createPoem(
        title: String,
        genre: String,
        body: String
    ) = Network.poemService!!.createPoem(title, genre, body)

    suspend fun updatePoem(
        poemId: Int,
        title: String,
        genre: String,
        body: String
    ) = Network.poemService!!.updatePoem(poemId, title, genre, body)

    suspend fun deletePoem(
        poemId: Int
    ) = Network.poemService!!.deletePoem(poemId)

}