package com.gmail.sacchin13.pokemonbattleanalyzer.entity

class BattleField(
        var weather: Weather = BattleField.Weather.Unknown,
        var room: Room = BattleField.Room.Unknown,
        var terrain: Terrain = BattleField.Terrain.Unknown,
        var field: MutableList<Field> = mutableListOf()
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

    fun update(attackSide: PokemonForBattle, defenseSide: PokemonForBattle){
        if(attackSide.skill.jname.equals("あまごい")) weather = Weather.Rainy
        if(attackSide.skill.jname.equals("あられ")) weather = Weather.Hailstone
        if(attackSide.skill.jname.equals("にほんばれ")) weather = Weather.Sunny
        if(attackSide.skill.jname.equals("すなあらし")) weather = Weather.Sandstorm

        if(attackSide.skill.jname.equals("トリックルーム")) {
            if(room == Room.Trick) room = Room.Unknown else room = Room.Trick
        }
        if(attackSide.skill.jname.equals("マジックルーム")) {
            if(room == Room.Magic) room = Room.Unknown else room = Room.Magic
        }
        if(attackSide.skill.jname.equals("ワンダールーム")) {
            if(room == Room.Wander) room = Room.Unknown else room = Room.Wander
        }

        if(attackSide.skill.jname.equals("エレキフィールド")) terrain = Terrain.ElectricTerrain
        if(attackSide.skill.jname.equals("グラスフィールド")) terrain = Terrain.GrassyTerrain
        if(attackSide.skill.jname.equals("ミストフィールド")) terrain = Terrain.MistyTerrain

        if(attackSide.skill.jname.equals("どろあそび")) field.add(Field.MudSport)
        if(attackSide.skill.jname.equals("みずあそび")) field.add(Field.WaterSport)
        if(attackSide.skill.jname.equals("じゅうりょく")) field.add(Field.Gravity)

        if(attackSide.skill.jname.equals("まきびし")) defenseSide.field.add(BattleField.Field.Spikes)
        if(attackSide.skill.jname.equals("どくびし")) defenseSide.field.add(BattleField.Field.ToxicSpikes)
        if(attackSide.skill.jname.equals("ステルスロック")) defenseSide.field.add(BattleField.Field.StealthRock)
        if(attackSide.skill.jname.equals("ねばねばネット")) defenseSide.field.add(BattleField.Field.StickyWeb)
        if(attackSide.skill.jname.equals("ひかりのかべ")) attackSide.field.add(BattleField.Field.LightScreen)
        if(attackSide.skill.jname.equals("リフレクター")) attackSide.field.add(BattleField.Field.Reflect)
        if(attackSide.skill.jname.equals("おいかぜ")) attackSide.field.add(BattleField.Field.Tailwind)
        if(attackSide.skill.jname.equals("しんぴのまもり")) attackSide.field.add(BattleField.Field.LuckyChant)
        if(attackSide.skill.jname.equals("おまじない")) attackSide.field.add(BattleField.Field.Safeguard)
        if(attackSide.skill.jname.equals("しろいきり")) attackSide.field.add(BattleField.Field.Mist)
        if(attackSide.skill.jname.equals("たたみがえし")) attackSide.field.add(BattleField.Field.MatBlock)
    }
}