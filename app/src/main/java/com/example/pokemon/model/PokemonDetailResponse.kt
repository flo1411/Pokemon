package com.example.pokemon.model

import com.google.gson.annotations.SerializedName

data class PokemonDetailResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("base_experience")
    val baseExperience: Int?,
    @SerializedName("height")
    val height: Int?,
    @SerializedName("weight")
    val weight: Int?,
    @SerializedName("order")
    val order: Int?,
    @SerializedName("is_default")
    val isDefault: Boolean?,
    @SerializedName("location_area_encounters")
    val locationAreaEncounters: String?,
    @SerializedName("abilities")
    val abilities: List<PokemonAbility>?,
    @SerializedName("cries")
    val cries: PokemonCries?,
    @SerializedName("forms")
    val forms: List<Pokemon>?,
    @SerializedName("game_indices")
    val gameIndices: List<PokemonGameIndex>?,
    @SerializedName("held_items")
    val heldItems: List<PokemonHeldItem>?,
    @SerializedName("moves")
    val moves: List<PokemonMove>?,
    @SerializedName("past_abilities")
    val pastAbilities: List<PokemonPastAbility>?,
    @SerializedName("past_stats")
    val pastStats: List<PokemonPastStat>?,
    @SerializedName("past_types")
    val pastTypes: List<PokemonPastType>?,
    @SerializedName("species")
    val species: Pokemon?,
    @SerializedName("sprites")
    val sprites: PokemonSprites?,
    @SerializedName("stats")
    val stats: List<PokemonStat>?,
    @SerializedName("types")
    val types: List<PokemonTypeSlot>?
)

data class PokemonAbility(
    @SerializedName("ability")
    val ability: Pokemon?,
    @SerializedName("is_hidden")
    val isHidden: Boolean?,
    @SerializedName("slot")
    val slot: Int?
)

data class PokemonCries(
    @SerializedName("latest")
    val latest: String?,
    @SerializedName("legacy")
    val legacy: String?
)

data class PokemonGameIndex(
    @SerializedName("game_index")
    val gameIndex: Int?,
    @SerializedName("version")
    val version: Pokemon?
)

data class PokemonHeldItem(
    @SerializedName("item")
    val item: Pokemon?,
    @SerializedName("version_details")
    val versionDetails: List<PokemonHeldItemVersion>?
)

data class PokemonHeldItemVersion(
    @SerializedName("rarity")
    val rarity: Int?,
    @SerializedName("version")
    val version: Pokemon?
)

data class PokemonMove(
    @SerializedName("move")
    val move: Pokemon?,
    @SerializedName("version_group_details")
    val versionGroupDetails: List<PokemonMoveVersionGroupDetail>?
)

data class PokemonMoveVersionGroupDetail(
    @SerializedName("level_learned_at")
    val levelLearnedAt: Int?,
    @SerializedName("move_learn_method")
    val moveLearnMethod: Pokemon?,
    @SerializedName("order")
    val order: Int?,
    @SerializedName("version_group")
    val versionGroup: Pokemon?
)

data class PokemonPastAbility(
    @SerializedName("abilities")
    val abilities: List<PokemonAbility>?,
    @SerializedName("generation")
    val generation: Pokemon?
)

data class PokemonPastStat(
    @SerializedName("generation")
    val generation: Pokemon?,
    @SerializedName("stats")
    val stats: List<PokemonStat>?
)

data class PokemonPastType(
    @SerializedName("generation")
    val generation: Pokemon?,
    @SerializedName("types")
    val types: List<PokemonTypeSlot>?
)

data class PokemonSprites(
    @SerializedName("back_default")
    val backDefault: String?,
    @SerializedName("back_female")
    val backFemale: String?,
    @SerializedName("back_shiny")
    val backShiny: String?,
    @SerializedName("back_shiny_female")
    val backShinyFemale: String?,
    @SerializedName("front_default")
    val frontDefault: String?,
    @SerializedName("front_female")
    val frontFemale: String?,
    @SerializedName("front_shiny")
    val frontShiny: String?,
    @SerializedName("front_shiny_female")
    val frontShinyFemale: String?,
    @SerializedName("other")
    val other: PokemonSpritesOther?
)

data class PokemonSpritesOther(
    @SerializedName("dream_world")
    val dreamWorld: PokemonSpriteImage?,
    @SerializedName("home")
    val home: PokemonSpriteImage?,
    @SerializedName("official-artwork")
    val officialArtwork: PokemonSpriteImage?,
    @SerializedName("showdown")
    val showdown: PokemonSpriteImage?
)

data class PokemonSpriteImage(
    @SerializedName("back_default")
    val backDefault: String?,
    @SerializedName("back_female")
    val backFemale: String?,
    @SerializedName("back_shiny")
    val backShiny: String?,
    @SerializedName("back_shiny_female")
    val backShinyFemale: String?,
    @SerializedName("front_default")
    val frontDefault: String?,
    @SerializedName("front_female")
    val frontFemale: String?,
    @SerializedName("front_shiny")
    val frontShiny: String?,
    @SerializedName("front_shiny_female")
    val frontShinyFemale: String?
)

data class PokemonStat(
    @SerializedName("base_stat")
    val baseStat: Int?,
    @SerializedName("effort")
    val effort: Int?,
    @SerializedName("stat")
    val stat: Pokemon?
)

data class PokemonTypeSlot(
    @SerializedName("slot")
    val slot: Int?,
    @SerializedName("type")
    val type: Pokemon?
)
