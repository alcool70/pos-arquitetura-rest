package rest

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlanetsResponse(
        val count: Int,
        val next: String?,
        val previous: String?,
        @SerialName("results")
        val planets: List<Planet>?,
)

@Serializable
data class Planet(
        val name: String,
        val rotation_period: Int,
        val orbital_period: Int,
        val diameter: Int,
        val climate: String,
        val gravity: String,
        val terrain: String,
        val surface_water: String?,
        val population: String?,
        val residents: List<String>,
        val films: List<String>,
        val created: String,
        val edited: String,
        val url: String,
)

@Serializable
data class People(
        val birth_year: String,
        val created: String,
        val edited: String,
        val eye_color: String,
        val films: List<String>,
        val gender: String,
        val hair_color: String,
        val height: String,
        val homeworld: String,
        val mass: String,
        val name: String,
        val skin_color: String,
        val species: List<String>?,
        val starships: List<String>,
        val url: String,
        val vehicles: List<String>,
)