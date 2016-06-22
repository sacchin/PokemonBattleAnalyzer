package com.gmail.sacchin13.pokemonbattleanalyzer.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import android.widget.ImageView
import com.gmail.sacchin13.pokemonbattleanalyzer.R

import com.gmail.sacchin13.pokemonbattleanalyzer.Util
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.IndividualPBAPokemon
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.PBAPokemon
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Party
import com.gmail.sacchin13.pokemonbattleanalyzer.interfaces.AddToListInterface
import kotlinx.android.synthetic.main.content_select.*

class SelectActivity : PGLActivity(), AddToListInterface {

//    private var databaseHelper: PartyDatabaseHelper? = null

    private var choices: Party = Party(System.currentTimeMillis(), "choice", "choice")

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

        resetParty(true)
//        first_fab.setOnClickListener(View.OnClickListener {
//            if (choicedPokemon == null || choicedPokemon.getMember().size() !== 3) {
//                Snackbar.make(estimate, "3体選択して下さい。", Snackbar.LENGTH_SHORT).show()
//            } else {
//                choicedPokemon.setTime(Timestamp(System.currentTimeMillis()))
//                executorService.execute(PartyInsertHandler(databaseHelper, choicedPokemon, false))
//                (getActivity() as SelectActivity).startToolActivity()
//            }
//        })

    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
    }

    fun startToolActivity() {
        val intent = Intent(this, ToolActivity::class.java)
        startActivityForResult(intent, 1)
    }

    private fun createPartyList() {
        opponent_party1.setImageBitmap(Util.createImage(opponentParty.member[0] as PBAPokemon, 250f, resources))
        opponent_party2.setImageBitmap(Util.createImage(opponentParty.member[1] as PBAPokemon, 250f, resources))
        opponent_party3.setImageBitmap(Util.createImage(opponentParty.member[2] as PBAPokemon, 250f, resources))
        opponent_party4.setImageBitmap(Util.createImage(opponentParty.member[3] as PBAPokemon, 250f, resources))
        opponent_party5.setImageBitmap(Util.createImage(opponentParty.member[4] as PBAPokemon, 250f, resources))
        opponent_party6.setImageBitmap(Util.createImage(opponentParty.member[5] as PBAPokemon, 250f, resources))

        my_party1.setImageBitmap(Util.createImage(myParty.member[0] as PBAPokemon, 250f, resources))
        my_party1.setOnClickListener{ addPokemonToList(myParty.member[0] as PBAPokemon) }
        my_party2.setImageBitmap(Util.createImage(myParty.member[1] as PBAPokemon, 250f, resources))
        my_party2.setOnClickListener{ addPokemonToList(myParty.member[1] as PBAPokemon) }
        my_party3.setImageBitmap(Util.createImage(myParty.member[2] as PBAPokemon, 250f, resources))
        my_party3.setOnClickListener{ addPokemonToList(myParty.member[2] as PBAPokemon) }
        my_party4.setImageBitmap(Util.createImage(myParty.member[3] as PBAPokemon, 250f, resources))
        my_party4.setOnClickListener{ addPokemonToList(myParty.member[3] as PBAPokemon) }
        my_party5.setImageBitmap(Util.createImage(myParty.member[4] as PBAPokemon, 250f, resources))
        my_party5.setOnClickListener{ addPokemonToList(myParty.member[4] as PBAPokemon) }
        my_party6.setImageBitmap(Util.createImage(myParty.member[5] as PBAPokemon, 250f, resources))
        my_party6.setOnClickListener{ addPokemonToList(myParty.member[5] as PBAPokemon) }
    }


    private fun determineOpponent() {
        //estimate.removeAllViews()
        //val estimated = EstimateOpponentElection.createAllPattern(mine, party)
        //        for(IndividualPBAPokemon[] p : estimated){
        //            addPokemonToOpponentParty(p);
        //        }
        //        addPokemonToOpponentParty(estimated[0]);
    }

    override fun addPokemonToList(pokemon: PBAPokemon) {
        val ip = IndividualPBAPokemon(pokemon)
        if (choices.member.size === 3) {
            Snackbar.make(my_party1, "すでに3体選択しています。", Snackbar.LENGTH_SHORT).show()
            return
        }
        choices.member.add(pokemon)

        val temp = Util.createImage(pokemon, 120f, resources)
        val localView = ImageView(this)
        localView.setImageBitmap(temp)
        selected.addView(localView)
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

    override fun removePokemonFromList(pokemon: PBAPokemon) {

    }

    override fun setTrend() {
        createPartyList()
    }
//
//    override fun finishAllDownload() {
//        determineOpponent()
//    }
}
