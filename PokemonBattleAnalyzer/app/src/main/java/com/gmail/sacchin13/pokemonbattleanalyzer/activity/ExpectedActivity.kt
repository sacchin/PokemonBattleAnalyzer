package com.gmail.sacchin13.pokemonbattleanalyzer.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SeekBar
import com.gmail.sacchin13.pokemonbattleanalyzer.R
import com.gmail.sacchin13.pokemonbattleanalyzer.Util
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.BattleField
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.BattleStatus
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.IndividualPBAPokemon
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.PartyInBattle
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl.TrendForBattle
import com.gmail.sacchin13.pokemonbattleanalyzer.logic.BattleCalculator
import kotlinx.android.synthetic.main.activity_expected.*
import kotlin.properties.Delegates

class ExpectedActivity : PGLActivity() {

    var util: Util by Delegates.notNull()
    var opponent: PartyInBattle by Delegates.notNull()
    var mine: PartyInBattle by Delegates.notNull()

    init {
        util = Util()
        opponent = PartyInBattle(PartyInBattle.OPPONENT_SIDE)
        mine = PartyInBattle(PartyInBattle.MY_SIDE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expected)

        resetParty(true)
        initView(intent)
    }

    fun calc() {
        val selectedOpponent = opponent.apply()
        val selectedMine = mine.apply()

        //技1の場合
        selectedMine.skill = selectedMine.individual.skillNo1
        val caseOfSkill1 = BattleCalculator.companion.getResult(selectedMine, selectedOpponent, BattleField())
        Log.v(BattleStatus.name(BattleStatus.Code.WIN), caseOfSkill1.mayOccur[BattleStatus.Code.WIN].toString())
        Log.v(BattleStatus.name(BattleStatus.Code.DEFEAT), caseOfSkill1.mayOccur[BattleStatus.Code.DEFEAT].toString())
        Log.v(BattleStatus.name(BattleStatus.Code.REVERSE), caseOfSkill1.mayOccur[BattleStatus.Code.REVERSE].toString())
        Log.v(BattleStatus.name(BattleStatus.Code.OWN_HEAD), caseOfSkill1.mayOccur[BattleStatus.Code.OWN_HEAD].toString())

        //技2の場合
        //技3の場合
        //技4の場合
        //控え1に交換した場合
        //控え2に交換した場合

    }


    fun get(index: Int): IndividualPBAPokemon {
        when (index) {
            0 -> return myParty.member1
            1 -> return myParty.member2
            2 -> return myParty.member3
            3 -> return myParty.member4
            4 -> return myParty.member5
            5 -> return myParty.member6
            else -> return myParty.member1
        }
    }

    override fun setTrend(result: TrendForBattle, index: Int) {
        opponent.member[index].trend = result
    }

    fun initView(intent: Intent) {
        mine.add(get(intent.extras.getInt("member1", 0)))
        mine.add(get(intent.extras.getInt("member2", 0)))
        mine.add(get(intent.extras.getInt("member3", 0)))

        val myAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item)
        for (temp in mine.member) {
            myAdapter.add(temp.individual.master.jname)
        }
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        myPokemonSpinner.adapter = myAdapter
        myPokemonSpinner.onItemSelectedListener = OnPokemonSelectedListener(true)

        opponentHPBar.setOnSeekBarChangeListener(OnHPChangeListener(opponent))
        myHPBar.setOnSeekBarChangeListener(OnHPChangeListener(mine))

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
        myASpinner.setSelection(6)
        myASpinner.onItemSelectedListener = OnRankSelectedListener(mine, 0)
        myBSpinner.adapter = rankAdapter
        myBSpinner.setSelection(6)
        myBSpinner.onItemSelectedListener = OnRankSelectedListener(mine, 1)
        myCSpinner.adapter = rankAdapter
        myCSpinner.setSelection(6)
        myCSpinner.onItemSelectedListener = OnRankSelectedListener(mine, 2)
        myDSpinner.adapter = rankAdapter
        myDSpinner.setSelection(6)
        myDSpinner.onItemSelectedListener = OnRankSelectedListener(mine, 3)
        mySSpinner.adapter = rankAdapter
        mySSpinner.setSelection(6)
        mySSpinner.onItemSelectedListener = OnRankSelectedListener(mine, 4)
        opponentASpinner.adapter = rankAdapter
        opponentASpinner.setSelection(6)
        opponentASpinner.onItemSelectedListener = OnRankSelectedListener(mine, 0)
        opponentBSpinner.adapter = rankAdapter
        opponentBSpinner.setSelection(6)
        opponentBSpinner.onItemSelectedListener = OnRankSelectedListener(mine, 1)
        opponentCSpinner.adapter = rankAdapter
        opponentCSpinner.setSelection(6)
        opponentCSpinner.onItemSelectedListener = OnRankSelectedListener(mine, 2)
        opponentDSpinner.adapter = rankAdapter
        opponentDSpinner.setSelection(6)
        opponentDSpinner.onItemSelectedListener = OnRankSelectedListener(mine, 3)
        opponentSSpinner.adapter = rankAdapter
        opponentSSpinner.setSelection(6)
        opponentSSpinner.onItemSelectedListener = OnRankSelectedListener(mine, 4)

        expected_fab.setOnClickListener { calc() }
    }

    override fun showParty() {
        opponent.add(opponentParty.member1)
        opponent.add(opponentParty.member2)
        opponent.add(opponentParty.member3)
        opponent.add(opponentParty.member4)
        opponent.add(opponentParty.member5)
        opponent.add(opponentParty.member6)

        val opponentAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item)
        for (temp in opponent.member) {
            opponentAdapter.add(temp.individual.master.jname)
        }
        opponentAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        opponentPokemonSpinner.adapter = opponentAdapter
        opponentPokemonSpinner.onItemSelectedListener = OnPokemonSelectedListener(false)
    }

    inner class OnPokemonSelectedListener(val isMine: Boolean) : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            if (isMine) {
                mine.selected = position
                myHPBar.progress = 100
                myPokemonImage.setImageBitmap(util.createImage(myParty.member[position], 120f, resources))
                Log.v("myPokemonSpinner", "${position} click!")
            } else {
                opponent.selected = position
            }
        }
    }

    inner class OnStatusSelectedListener(val party: PartyInBattle) : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            party.setStatus(position)
        }
    }

    inner class OnRankSelectedListener(val party: PartyInBattle, val which: Int) : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            when (which) {
                0 -> party.setAttackRank(position)
                1 -> party.setDefenseRank(position)
                2 -> party.setSpecialAttackRank(position)
                3 -> party.setSpecialDefenseRank(position)
                4 -> party.setSpeedRank(position)
            }
        }
    }

    inner class OnHPChangeListener(val party: PartyInBattle) : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
            party.setHPRatio(seekBar!!.progress)
        }
    }
}