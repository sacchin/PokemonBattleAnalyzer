package com.gmail.sacchin13.pokemonbattleanalyzer.entity

class BattleField(
        var weather: Weather = BattleField.Weather.Unknown,
        var room: Room = BattleField.Room.Unknown,
        var terrain: Terrain = BattleField.Terrain.Unknown,
        var field: MutableList<Field> = mutableListOf(),
        var attackSide: MutableList<Field> = mutableListOf(),
        var defenseSide: MutableList<Field> = mutableListOf()
) {

    enum class Weather {
        Sunny, Rainy, Sandstorm, Hailstone, Gimlet, Turbulence, DarkWeather, StrongSunny, StrongRainy, Unknown
    }

    enum class Room {
        Trick, Magic, Wander, Unknown
    }

    enum class Terrain {
        ElectricTerrain, GrassyTerrain, MistyTerrain, PsycoTerrain, Unknown
    }

    enum class Field {
        MudSport, WaterSport, Gravity, Tailwind, LuckyChant, Mist, Safeguard, StealthRock, MatBlock, ToxicSpikes, StickyWeb, LightScreen, Spikes, Reflect, Unknown
    }

    fun resetAttackSide(side: MutableSet<Field>) {
        attackSide.clear()
        attackSide.addAll(side)
    }

    fun resetDefenseSide(side: MutableSet<Field>) {
        defenseSide.clear()
        defenseSide.addAll(side)
    }
}