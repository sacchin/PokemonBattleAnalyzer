package com.gmail.sacchin13.pokemonbattleanalyzer.entity

import io.realm.RealmObject

public open class Skill(
        public open var no: Int = 0,
        public open var jname: String? = "",
        public open var ename: String? = "",
        public open var type: Int = 0,
        public open var power: Int = 0,
        public open var accuracy: Int = 0,
        public open var category: Int = 0,
        public open var pp: Int = 0,
        public open var contact: Boolean = false,
        public open var protectable: Boolean = false
):RealmObject() {
}

