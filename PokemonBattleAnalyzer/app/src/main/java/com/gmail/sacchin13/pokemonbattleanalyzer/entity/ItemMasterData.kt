package com.gmail.sacchin13.pokemonbattleanalyzer.entity

import io.realm.RealmObject

public open class ItemMasterData(
        public open var name: String = ""
) :RealmObject(){
}
