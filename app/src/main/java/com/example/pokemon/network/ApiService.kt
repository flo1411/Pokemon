package com.example.pokemon.network

import com.example.pokemon.model.Pokemon
import retrofit2.http.GET

interface ApiService {
    @GET("pokemon")
    suspend fun getPokemon(): List<Pokemon>
}