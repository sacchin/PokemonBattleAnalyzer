package com.gmail.sacchin13.pokemonbattleanalyzer.listener

import com.gmail.sacchin13.pokemonbattleanalyzer.activity.ToolActivity

import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageView

class OnClickIndividualPokemon(
        val activity: ToolActivity,
        val index: Int,
        val from: ImageView) : OnClickListener {

    override fun onClick(v: View) {
        val p = activity.getIndividualPBAPokemon(index)
        activity.startDetailActivity(p!!, from)
        //        fragment.setMainView(p);
        //        fragment.setIndex(index);
        //        fragment.setTrend();
    }
}
