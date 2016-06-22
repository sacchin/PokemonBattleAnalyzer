package com.gmail.sacchin13.pokemonbattleanalyzer.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import com.gmail.sacchin13.pokemonbattleanalyzer.R

import com.gmail.sacchin13.pokemonbattleanalyzer.Util
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.IndividualPBAPokemon
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.PBAPokemon
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Party
import kotlinx.android.synthetic.main.activity_select.*
import kotlinx.android.synthetic.main.content_select.*

class SelectActivity : PGLActivity() {

    private var choices: MutableMap<Int, PBAPokemon> = mutableMapOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select)

        opponentParty = Party(System.currentTimeMillis(), "opponent", "opponent")
        opponentParty.member1 = intent.extras.getString("member1", "")
        opponentParty.member2 = intent.extras.getString("member2", "")
        opponentParty.member3 = intent.extras.getString("member3", "")
        opponentParty.member4 = intent.extras.getString("member4", "")
        opponentParty.member5 = intent.extras.getString("member5", "")
        opponentParty.member6 = intent.extras.getString("member6", "")

        myParty = Party(System.currentTimeMillis(), "mine", "mine")
        myParty.member1 = intent.extras.getString("member1", "")
        myParty.member2 = intent.extras.getString("member2", "")
        myParty.member3 = intent.extras.getString("member3", "")
        myParty.member4 = intent.extras.getString("member4", "")
        myParty.member5 = intent.extras.getString("member5", "")
        myParty.member6 = intent.extras.getString("member6", "")

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
        opponent_party1.setImageBitmap(Util.createImage(opponentParty.member[0] as PBAPokemon, 250f, resources))
        opponent_party2.setImageBitmap(Util.createImage(opponentParty.member[1] as PBAPokemon, 250f, resources))
        opponent_party3.setImageBitmap(Util.createImage(opponentParty.member[2] as PBAPokemon, 250f, resources))
        opponent_party4.setImageBitmap(Util.createImage(opponentParty.member[3] as PBAPokemon, 250f, resources))
        opponent_party5.setImageBitmap(Util.createImage(opponentParty.member[4] as PBAPokemon, 250f, resources))
        opponent_party6.setImageBitmap(Util.createImage(opponentParty.member[5] as PBAPokemon, 250f, resources))

        selected_party1.setOnClickListener{ removePokemonFromList(0) }
        selected_party2.setOnClickListener{ removePokemonFromList(1) }
        selected_party3.setOnClickListener{ removePokemonFromList(2) }
        selected_party4.setOnClickListener{ removePokemonFromList(3) }
        selected_party5.setOnClickListener{ removePokemonFromList(4) }
        selected_party6.setOnClickListener{ removePokemonFromList(5) }

        my_party1.setImageBitmap(Util.createImage(myParty.member[0] as PBAPokemon, 250f, resources))
        my_party1.setOnClickListener{ addPokemonToList(myParty.member[0] as PBAPokemon, 0) }
        my_party2.setImageBitmap(Util.createImage(myParty.member[1] as PBAPokemon, 250f, resources))
        my_party2.setOnClickListener{ addPokemonToList(myParty.member[1] as PBAPokemon, 1) }
        my_party3.setImageBitmap(Util.createImage(myParty.member[2] as PBAPokemon, 250f, resources))
        my_party3.setOnClickListener{ addPokemonToList(myParty.member[2] as PBAPokemon, 2) }
        my_party4.setImageBitmap(Util.createImage(myParty.member[3] as PBAPokemon, 250f, resources))
        my_party4.setOnClickListener{ addPokemonToList(myParty.member[3] as PBAPokemon, 3) }
        my_party5.setImageBitmap(Util.createImage(myParty.member[4] as PBAPokemon, 250f, resources))
        my_party5.setOnClickListener{ addPokemonToList(myParty.member[4] as PBAPokemon, 4) }
        my_party6.setImageBitmap(Util.createImage(myParty.member[5] as PBAPokemon, 250f, resources))
        my_party6.setOnClickListener{ addPokemonToList(myParty.member[5] as PBAPokemon, 5) }
    }

    private fun determineOpponent() {
        //estimate.removeAllViews()
        //val estimated = EstimateOpponentElection.createAllPattern(mine, party)
        //        for(IndividualPBAPokemon[] p : estimated){
        //            addPokemonToOpponentParty(p);
        //        }
        //        addPokemonToOpponentParty(estimated[0]);
    }

    fun addPokemonToList(pokemon: PBAPokemon, index: Int) {
        val ip = IndividualPBAPokemon(pokemon)
        if (choices.size == 3) {
            Snackbar.make(my_party1, "すでに3体選択しています。", Snackbar.LENGTH_SHORT).show()
            return
        }
        choices[index] = pokemon

        val temp = Util.createImage(R.drawable.select, 250f, resources)
        when(index){
            0 -> selected_party1.setImageBitmap(temp)
            1 -> selected_party2.setImageBitmap(temp)
            2 -> selected_party3.setImageBitmap(temp)
            3 -> selected_party4.setImageBitmap(temp)
            4 -> selected_party5.setImageBitmap(temp)
            5 -> selected_party6.setImageBitmap(temp)
        }
    }

    fun addPokemonToOpponentParty(pokemons: Array<PBAPokemon>) {
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
            val intent = Intent(this, ToolActivity().javaClass)
            intent.putExtra("member1", choices[0]!!.masterRecord.no)
            intent.putExtra("member2", choices[1]!!.masterRecord.no)
            intent.putExtra("member3", choices[2]!!.masterRecord.no)
            startActivityForResult(intent, 1)
        }
    }

//    override fun finishAllDownload() {
//        determineOpponent()
//    }
}
