package com.lucasdev.pagingexam.data

import com.google.gson.annotations.SerializedName

data class PokemonResponse(
    var species: Species,
    var sprites: Sprites
)

data class Species(var name: String)
data class Sprites(var frontDefault: String)