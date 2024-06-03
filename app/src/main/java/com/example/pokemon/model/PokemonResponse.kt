package com.example.pokemon.model

import com.google.gson.annotations.SerializedName

data class PokemonResponse (
    @SerializedName("count")
    val count: Int?,
    @SerializedName("results")
    val pokemon: List<Pokemon>?
)