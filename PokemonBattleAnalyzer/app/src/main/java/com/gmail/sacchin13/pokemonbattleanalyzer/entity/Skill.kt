package com.gmail.sacchin13.pokemonbattleanalyzer.entity

import io.realm.RealmObject
import io.realm.annotations.RealmClass
import java.util.*

@RealmClass
public open class Skill(
        public open var no: Int = -1,
        public open var jname: String = "unknown",
        public open var ename: String = "unknown",
        public open var type: Int = -1,
        public open var power: Int = -1,
        public open var accuracy: Int = -1,
        public open var category: Int = -1,
        public open var pp: Int = -1,
        public open var contact: Boolean = false,
        public open var protectable: Boolean = false
):RealmObject() {

    object companion{
        fun skillNameList(list: MutableList<Skill?>): MutableList<String>{
            val result = ArrayList<String>()
            for(temp in list){
                result.add(temp!!.jname)
            }
            return result
        }
    }

    override fun toString(): String{
        return "${no},${jname},${ename},${type},${power},${accuracy},${category},${pp},${contact},${protectable}"
    }
}

