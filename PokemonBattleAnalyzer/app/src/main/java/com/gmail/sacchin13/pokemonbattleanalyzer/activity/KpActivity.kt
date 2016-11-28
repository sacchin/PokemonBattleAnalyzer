package com.gmail.sacchin13.pokemonbattleanalyzer.activity

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.gmail.sacchin13.pokemonbattleanalyzer.R
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Party
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.PokemonMasterData
import com.gmail.sacchin13.pokemonbattleanalyzer.util.Util
import kotlinx.android.synthetic.main.activity_kpt.*
import kotlin.properties.Delegates

class KpActivity : PGLActivity() {
    var util: Util by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kpt)

        util = Util()

        databaseHelper.selectAllParty(this)
    }

    fun onSelect(list: List<Party>){
        val result = mutableMapOf<PokemonMasterData, Int>()
        for(temp in list) {
            result[temp.member1.master] = (result[temp.member1.master] ?: 0) + 1
            result[temp.member2.master] = (result[temp.member2.master] ?: 0) + 1
            result[temp.member3.master] = (result[temp.member3.master] ?: 0) + 1
            result[temp.member4.master] = (result[temp.member4.master] ?: 0) + 1
            result[temp.member5.master] = (result[temp.member5.master] ?: 0) + 1
            result[temp.member6.master] = (result[temp.member6.master] ?: 0) + 1
        }

        val sorted = result.entries.sortedByDescending { s -> s.value }
        sorted.forEach { run {
            val row = LinearLayout(this)
            row.orientation = LinearLayout.HORIZONTAL

            val image = ImageView(this)
            image.setImageBitmap(util.createImage(it.key, 120f, resources))
            row.addView(image)
            val text = TextView(this)
            text.text = it.value.toString()
            row.addView(text)

            view.addView(row)
        } }


        kp_fab.setOnClickListener {
        }

    }
}
