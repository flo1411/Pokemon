package com.example.pokemon.network

import com.example.pokemon.model.PokemonDetailResponse
import com.example.pokemon.model.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {
    @GET("pokemon")
    suspend fun getPokemon(@Query("limit") limit: Int? = null,
                           @Query("offset") offset: Int? = null): PokemonResponse

    @GET
    suspend fun getPokemonDetails(@Url url: String?): PokemonDetailResponse
}