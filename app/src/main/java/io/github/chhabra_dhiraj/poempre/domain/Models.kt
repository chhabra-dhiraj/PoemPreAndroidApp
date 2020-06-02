package io.github.chhabra_dhiraj.poempre.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class Sentence(
    val author: String,
    val poemName: String,
    val age: String,
    val sentence: String
)

data class User(
    val userId: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val imageUrl: String
)

@Parcelize
data class Poem(
    val poetryId: Int,
    val title: String,
    val genre: String,
    val body: String
) : Parcelable