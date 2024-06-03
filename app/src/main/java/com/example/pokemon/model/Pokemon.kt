package com.example.pokemon.model

import com.google.gson.annotations.SerializedName

data class Pokemon(
    @SerializedName("name")
    val name: String?,
    @SerializedName("url")
    val url: String?
)