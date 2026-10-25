package com.example.pokemon.model

import com.google.gson.annotations.SerializedName

data class PokemonFormResponse(
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("order")
    val order: Int?,
    @SerializedName("form_order")
    val formOrder: Int?,
    @SerializedName("is_default")
    val isDefault: Boolean?,
    @SerializedName("is_battle_only")
    val isBattleOnly: Boolean?,
    @SerializedName("is_mega")
    val isMega: Boolean?,
    @SerializedName("form_name")
    val formName: String?,
    @SerializedName("pokemon")
    val pokemon: Pokemon?,
    @SerializedName("sprites")
    val sprites: PokemonSprites?,
    @SerializedName("version_group")
    val versionGroup: Pokemon?,
    @SerializedName("form_names")
    val formNames: List<PokemonFormName>?,
    @SerializedName("names")
    val names: List<PokemonFormName>?,
    @SerializedName("types")
    val types: List<PokemonTypeSlot>?,
    @SerializedName("trigger_conditions")
    val triggerConditions: List<PokemonFormTriggerCondition>?,
    @SerializedName("flavor_text_entries")
    val flavorTextEntries: List<PokemonFormFlavorText>?
)

data class PokemonFormName(
    @SerializedName("name")
    val name: String?,
    @SerializedName("language")
    val language: Pokemon?
)

data class PokemonFormTriggerCondition(
    @SerializedName("trigger")
    val trigger: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("url")
    val url: String?,
    @SerializedName("base_form")
    val baseForm: Pokemon?
)

data class PokemonFormFlavorText(
    @SerializedName("flavor_text")
    val flavorText: String?,
    @SerializedName("language")
    val language: Pokemon?,
    @SerializedName("version")
    val version: Pokemon?
)
