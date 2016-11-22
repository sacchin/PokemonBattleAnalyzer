package com.gmail.sacchin13.pokemonbattleanalyzer.entity

import io.realm.RealmObject
import io.realm.annotations.RealmClass

@RealmClass
open class ZSkill (
        open var no: Int = -1,
        open var skillNumber: Int = -1,
        open var jname: String = "unknown",
        open var type: Int = -1,
        open var power: Int = -1,
        open var category: Int = -1,
        open var rank: Int = -1
) : RealmObject() {

}