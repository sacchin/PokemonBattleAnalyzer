package com.gmail.sacchin13.pokemonbattleanalyzer.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar

import com.gmail.sacchin13.pokemonbattleanalyzer.R
import com.gmail.sacchin13.pokemonbattleanalyzer.Util
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.IndividualPBAPokemon
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.PartyInBattle
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl.RankingResponse
import kotlinx.android.synthetic.main.activity_expected.*
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlin.properties.Delegates

class ExpectedActivity : PGLActivity() {

    var util: Util by Delegates.notNull()
    var opponent: PartyInBattle by Delegates.notNull()
    var mine: PartyInBattle by Delegates.notNull()

    init {
        util = Util()
        opponent = PartyInBattle()
        mine = PartyInBattle()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expected)

        opponentHPBar.setOnSeekBarChangeListener(OnHPChangeListener(opponent))
        myHPBar.setOnSeekBarChangeListener(OnHPChangeListener(mine))

        resetParty(true)

        opponent.add(opponentParty.member1)
        opponent.add(opponentParty.member2)
        opponent.add(opponentParty.member3)
        opponent.add(opponentParty.member4)
        opponent.add(opponentParty.member5)
        opponent.add(opponentParty.member6)



        mine.add(get(intent.extras.getInt("member1", 0)))
        mine.add(get(intent.extras.getInt("member2", 0)))
        mine.add(get(intent.extras.getInt("member3", 0)))


        expected_fab!!.setOnClickListener { calc() }
    }

    fun calc (){
        opponent.apply()
        mine.apply()





    }


    fun get(index: Int): IndividualPBAPokemon{
        when(index){
            0 -> return myParty.member1
            1 -> return myParty.member2
            2 -> return myParty.member3
            3 -> return myParty.member4
            4 -> return myParty.member5
            5 -> return myParty.member6
            else -> return myParty.member1
        }
    }

    override fun setTrend(result: RankingResponse, index: Int){
        opponent.member[index].trend = result as RankingResponse
        Log.v("setTrend", result.statusCode)
        Log.v("setTrend", result.beforePokemonId)
        Log.v("setTrend", result.nextPokemonId)
    }

    override fun showParty() {
//        opponent.setMember(opponentParty.member)
//        mine.setMember(myParty.member)

        val statusAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item)
        statusAdapter.add("やけど")
        statusAdapter.add("こおり")
        statusAdapter.add("まひ")
        statusAdapter.add("どく")
        statusAdapter.add("もうどく")
        statusAdapter.add("ねむり")
        statusAdapter.add("ひんし")
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        myStatusSpinner.adapter = statusAdapter
        myStatusSpinner.onItemSelectedListener = OnStatusSelectedListener(mine)
        opponentStatusSpinner.adapter = statusAdapter
        opponentStatusSpinner.onItemSelectedListener = OnStatusSelectedListener(opponent)


        val opponentAdapter = ArrayAdapter<String>(this,android.R.layout.simple_spinner_item)
        for(temp in opponent.member){
            opponentAdapter.add(temp.master.jname)
        }
        opponentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        opponentPokemonSpinner.adapter = opponentAdapter
        opponentPokemonSpinner.onItemSelectedListener = OnPokemonSelectedListener(false)

        val myAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item)
        for(temp in mine.member){
            myAdapter.add(temp.master.jname)
        }
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        myPokemonSpinner.adapter = myAdapter
        myPokemonSpinner.onItemSelectedListener = OnPokemonSelectedListener(true)


        val rankAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item)
        rankAdapter.add("6")
        rankAdapter.add("5")
        rankAdapter.add("4")
        rankAdapter.add("3")
        rankAdapter.add("2")
        rankAdapter.add("1")
        rankAdapter.add("0")
        rankAdapter.add("-1")
        rankAdapter.add("-2")
        rankAdapter.add("-3")
        rankAdapter.add("-4")
        rankAdapter.add("-5")
        rankAdapter.add("-6")
        rankAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        myASpinner.adapter = rankAdapter
        myASpinner.setSelection(7)
        myASpinner.onItemSelectedListener = OnRankSelectedListener(mine, 0)
        myBSpinner.adapter = rankAdapter
        myBSpinner.setSelection(7)
        myBSpinner.onItemSelectedListener = OnRankSelectedListener(mine, 1)
        myCSpinner.adapter = rankAdapter
        myCSpinner.setSelection(7)
        myCSpinner.onItemSelectedListener = OnRankSelectedListener(mine, 2)
        myDSpinner.adapter = rankAdapter
        myDSpinner.setSelection(7)
        myDSpinner.onItemSelectedListener = OnRankSelectedListener(mine, 3)
        mySSpinner.adapter = rankAdapter
        mySSpinner.setSelection(7)
        mySSpinner.onItemSelectedListener = OnRankSelectedListener(mine, 4)
        opponentASpinner.adapter = rankAdapter
        opponentASpinner.setSelection(7)
        opponentASpinner.onItemSelectedListener = OnRankSelectedListener(mine, 0)
        opponentBSpinner.adapter = rankAdapter
        opponentBSpinner.setSelection(7)
        opponentBSpinner.onItemSelectedListener = OnRankSelectedListener(mine, 1)
        opponentCSpinner.adapter = rankAdapter
        opponentCSpinner.setSelection(7)
        opponentCSpinner.onItemSelectedListener = OnRankSelectedListener(mine, 2)
        opponentDSpinner.adapter = rankAdapter
        opponentDSpinner.setSelection(7)
        opponentDSpinner.onItemSelectedListener = OnRankSelectedListener(mine, 3)
        opponentSSpinner.adapter = rankAdapter
        opponentSSpinner.setSelection(7)
        opponentSSpinner.onItemSelectedListener = OnRankSelectedListener(mine, 4)

        expected_fab.setOnClickListener{}

    }

    inner class OnPokemonSelectedListener(val isMine: Boolean) : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) { }
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            if(isMine){
                mine.selected = position
                myHPBar.progress = 100
                myPokemonImage.setImageBitmap(util.createImage(myParty.member[position], 120f, resources))
                Log.v("myPokemonSpinner", "${position} click!")
            }else{
                opponent.selected = position
            }
        }
    }

    inner class OnStatusSelectedListener(val party: PartyInBattle) : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {}
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            party.setStatus(position)
        }
    }

    inner class OnRankSelectedListener(val party: PartyInBattle, val which: Int) : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {}
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            when(which){
                0 ->  party.setAttackRank(position)
                1 ->  party.setDefenseRank(position)
                2 ->  party.setSpecialAttackRank(position)
                3 ->  party.setSpecialDefenseRank(position)
                4 ->  party.setSpeedRank(position)
            }
        }
    }

    inner class OnHPChangeListener(val party: PartyInBattle) : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}
        override fun onStartTrackingTouch(seekBar: SeekBar?) {}
        override fun onStopTrackingTouch(seekBar: SeekBar?) {
            party.setHPRatio(seekBar!!.progress)
        }
    }


}