package com.gmail.sacchin13.pokemonbattleanalyzer.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import com.gmail.sacchin13.pokemonbattleanalyzer.R

import com.gmail.sacchin13.pokemonbattleanalyzer.Util
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.IndividualPBAPokemon
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.PokemonMasterData
import kotlinx.android.synthetic.main.activity_select.*
import kotlinx.android.synthetic.main.content_select.*
import java.util.*
import kotlin.properties.Delegates

class SelectActivity : PGLActivity() {

    private var choices: MutableMap<Int, IndividualPBAPokemon> = mutableMapOf()
    var opponentList: MutableList<PokemonMasterData> by Delegates.notNull()

    var util: Util by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)

        util = Util()

        opponentList = ArrayList<PokemonMasterData>()
        opponentList.add(databaseHelper.selectPokemonMasterData(intent.extras.getString("member1", "")))
        opponentList.add(databaseHelper.selectPokemonMasterData(intent.extras.getString("member2", "")))
        opponentList.add(databaseHelper.selectPokemonMasterData(intent.extras.getString("member3", "")))
        opponentList.add(databaseHelper.selectPokemonMasterData(intent.extras.getString("member4", "")))
        opponentList.add(databaseHelper.selectPokemonMasterData(intent.extras.getString("member5", "")))
        opponentList.add(databaseHelper.selectPokemonMasterData(intent.extras.getString("member6", "")))

        resetParty(false)

        first_fab.setOnClickListener { startBattle() }
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
    }

    private fun createPartyList() {
        opponent_party1.setImageBitmap(util.createImage(opponentList[0], 250f, resources))
        opponent_party2.setImageBitmap(util.createImage(opponentList[1], 250f, resources))
        opponent_party3.setImageBitmap(util.createImage(opponentList[2], 250f, resources))
        opponent_party4.setImageBitmap(util.createImage(opponentList[3], 250f, resources))
        opponent_party5.setImageBitmap(util.createImage(opponentList[4], 250f, resources))
        opponent_party6.setImageBitmap(util.createImage(opponentList[5], 250f, resources))

        selected_party1.setOnClickListener{ removePokemonFromList(0) }
        selected_party2.setOnClickListener{ removePokemonFromList(1) }
        selected_party3.setOnClickListener{ removePokemonFromList(2) }
        selected_party4.setOnClickListener{ removePokemonFromList(3) }
        selected_party5.setOnClickListener{ removePokemonFromList(4) }
        selected_party6.setOnClickListener{ removePokemonFromList(5) }

        my_party1.setImageBitmap(util.createImage(myParty.member1.master, 250f, resources))
        my_party1.setOnClickListener{ addPokemonToList(myParty.member1, 0) }
        my_party2.setImageBitmap(util.createImage(myParty.member2.master,  250f, resources))
        my_party2.setOnClickListener{ addPokemonToList(myParty.member2, 1) }
        my_party3.setImageBitmap(util.createImage(myParty.member3.master, 250f, resources))
        my_party3.setOnClickListener{ addPokemonToList(myParty.member3, 2) }
        my_party4.setImageBitmap(util.createImage(myParty.member4.master, 250f, resources))
        my_party4.setOnClickListener{ addPokemonToList(myParty.member4, 3) }
        my_party5.setImageBitmap(util.createImage(myParty.member5.master, 250f, resources))
        my_party5.setOnClickListener{ addPokemonToList(myParty.member5, 4) }
        my_party6.setImageBitmap(util.createImage(myParty.member6.master, 250f, resources))
        my_party6.setOnClickListener{ addPokemonToList(myParty.member6, 5) }
    }

    private fun determineOpponent() {
        //estimate.removeAllViews()
        //val estimated = EstimateOpponentElection.createAllPattern(mine, party)
        //        for(IndividualPBAPokemon[] p : estimated){
        //            addPokemonToOpponentParty(p);
        //        }
        //        addPokemonToOpponentParty(estimated[0]);
    }

    fun addPokemonToList(pokemon: IndividualPBAPokemon, index: Int) {
        if (choices.size == 3) {
            Snackbar.make(my_party1, "すでに3体選択しています。", Snackbar.LENGTH_SHORT).show()
            return
        }
        choices[index] = pokemon

        val temp = util.createImage(R.drawable.select, 250f, resources)
        when(index){
            0 -> selected_party1.setImageBitmap(temp)
            1 -> selected_party2.setImageBitmap(temp)
            2 -> selected_party3.setImageBitmap(temp)
            3 -> selected_party4.setImageBitmap(temp)
            4 -> selected_party5.setImageBitmap(temp)
            5 -> selected_party6.setImageBitmap(temp)
        }
    }

    fun addPokemonToOpponentParty(pokemons: Array<PokemonMasterData>) {
//        val l = LinearLayout(getActivity())
//        l.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//        l.orientation = LinearLayout.HORIZONTAL
        //        l.setOnClickListener(new View.OnClickListener() {
        //            @Override
        //            public void onClick(View view) {
        //                Snackbar.make(view, pokemons[2].getJname(), Snackbar.LENGTH_SHORT).show();
        //            }
        //        });

//        for (p in pokemons) {
//            val ip = IndividualPBAPokemon(p)//, 0, new Timestamp(System.currentTimeMillis()), "", "", "", "", "", "", "");
//
//            val temp = Util.createImage(p, 120f, resources)
//            val localView = ImageView(getActivity())
//            localView.setImageBitmap(temp)
//
//            l.addView(localView)
//        }
//        estimate.addView(l)
    }

    fun removePokemonFromList(index: Int) {
        choices.remove(index)
        when(index) {
            0 -> selected_party1.setImageBitmap(null)
            1 -> selected_party2.setImageBitmap(null)
            2 -> selected_party3.setImageBitmap(null)
            3 -> selected_party4.setImageBitmap(null)
            4 -> selected_party5.setImageBitmap(null)
            5 -> selected_party6.setImageBitmap(null)
        }
    }

    override fun showParty() {
        createPartyList()
    }

    fun startBattle(){
        if (choices.size < 3) {
            Snackbar.make(my_party1, "3体選択して下さい。", Snackbar.LENGTH_SHORT).show()
        } else {
            val intent = Intent(this, ExpectedActivity().javaClass)
            val keys = choices.keys.toList()
            intent.putExtra("member1", keys[0])
            intent.putExtra("member2", keys[1])
            intent.putExtra("member3", keys[2])
            startActivityForResult(intent, 1)
        }
    }

//    override fun finishAllDownload() {
//        determineOpponent()
//    }
}
