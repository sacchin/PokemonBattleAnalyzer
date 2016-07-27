package com.gmail.sacchin13.pokemonbattleanalyzer.entity

class BattleField(
        var weather: Weather = BattleField.Weather.Unknown,
        var room: Room = BattleField.Room.Unknown,
        var terrain: Terrain = BattleField.Terrain.Unknown,
        var field: MutableList<Field> = mutableListOf(),
        var myField: MutableList<Field> = mutableListOf(),
        var oppoField: MutableList<Field> = mutableListOf()
    ){

    enum class Weather {
        Sunny, Rainy, Sandstorm, Hailstone, Gimlet, Turbulence, DarkWeather, StrongSunny, StrongRainy, Unknown
    }

    enum class Room {
        Trick, Magic, Wander, Unknown
    }

    enum class Terrain {
        ElectricTerrain, GrassyTerrain, MistyTerrain, Unknown
    }

    enum class Field {
        MudSport, WaterSport, Gravity, Tailwind, LuckyChant, Mist, Safeguard, StealthRock, MatBlock, ToxicSpikes, StickyWeb, LightScreen, Spikes, Reflect, Unknown
    }

    fun add(item: BattleField.Field){
        field.add(item)
    }

    fun addToMine(item: BattleField.Field){
        myField.add(item)
    }

    fun addToOpponent(item: BattleField.Field){
        oppoField.add(item)
    }

}