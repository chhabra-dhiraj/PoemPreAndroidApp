package io.github.chhabra_dhiraj.poempre.repository

import io.github.chhabra_dhiraj.poempre.network.Network

class SuggestionRepository {

    suspend fun getPoemSuggestions(word: String, genre: String) =
        Network.suggestionService!!.getPoemSuggestions(word, genre)

}