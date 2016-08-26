package com.gmail.sacchin13.pokemonbattleanalyzer.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.gmail.sacchin13.pokemonbattleanalyzer.R
import com.gmail.sacchin13.pokemonbattleanalyzer.Util
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.BattleField
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

    fun showBest() {
        val selectedOpponent = opponent.apply()
        val selectedMine = mine.apply()

        //技1の場合
        selectedMine.skill = selectedMine.individual.skillNo1
        val caseOfSkill1 = BattleCalculator.getResult(selectedMine, selectedOpponent, BattleField())
        skill1_name.text = selectedMine.individual.skillNo1.jname
        skill1_alive.text = caseOfSkill1.winRate()
        skill1_dead.text = caseOfSkill1.loseRate()

        //技2の場合
        selectedMine.skill = selectedMine.individual.skillNo2
        val caseOfSkill2 = BattleCalculator.getResult(selectedMine, selectedOpponent, BattleField())
        skill2_name.text = selectedMine.individual.skillNo2.jname
        skill2_alive.text = caseOfSkill2.winRate()
        skill2_dead.text = caseOfSkill2.loseRate()

        //技3の場合
        selectedMine.skill = selectedMine.individual.skillNo3
        val caseOfSkill3 = BattleCalculator.getResult(selectedMine, selectedOpponent, BattleField())
        skill3_name.text = selectedMine.individual.skillNo3.jname
        skill3_alive.text = caseOfSkill3.winRate()
        skill3_dead.text = caseOfSkill3.loseRate()

        //技4の場合
        selectedMine.skill = selectedMine.individual.skillNo4
        val caseOfSkill4 = BattleCalculator.getResult(selectedMine, selectedOpponent, BattleField())
        skill4_name.text = selectedMine.individual.skillNo4.jname
        skill4_alive.text = caseOfSkill4.winRate()
        skill4_dead.text = caseOfSkill4.loseRate()

        coverRate.text = caseOfSkill1.coverRate()

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
        finishCount++

        result.updateSkills(databaseHelper)
        opponent.member[index].trend = result

        if(result.itemInfo.isEmpty() && result.seikakuInfo.isEmpty() &&
            result.tokuseiInfo.isEmpty() && result.skillList.isEmpty()){
            Snackbar.make(inputTable, "download failed at $index", Snackbar.LENGTH_SHORT).show()
        }
        if(finishCount == opponentParty.member.size){
            Snackbar.make(inputTable, "download finish", Snackbar.LENGTH_SHORT).show()
        }
    }

    fun initView(intent: Intent) {
        my_mega_check.setOnCheckedChangeListener { compoundButton, b -> mine.tempMega = b }
        oppo_mega_check.setOnCheckedChangeListener { compoundButton, b -> opponent.tempMega = b }

        mine.add(get(intent.extras.getInt("member1", 0)))
        mine.add(get(intent.extras.getInt("member2", 0)))
        mine.add(get(intent.extras.getInt("member3", 0)))

        my_party1.setOnClickListener(OnPokemonSelectedListener(true, 0))
        my_party1.setImageBitmap(util.createImage(mine.member[0].individual.master, 150.0f, resources))

        my_party2.setOnClickListener(OnPokemonSelectedListener(true, 1))
        my_party2.setImageBitmap(util.createImage(mine.member[1].individual.master, 150.0f, resources))

        my_party3.setOnClickListener(OnPokemonSelectedListener(true, 2))
        my_party3.setImageBitmap(util.createImage(mine.member[2].individual.master, 150.0f, resources))

        opponentHPBar.setOnSeekBarChangeListener(OnHPChangeListener())
        myHPBar.setOnEditorActionListener(OnHPEditListener())

        val statusAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item)
        statusAdapter.add("状態異常")
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

        expected_fab.setOnClickListener {
            showProgress(true)
            showBest()
            showProgress(false)
        }
    }

    override fun showParty() {
        opponent.add(opponentParty.member1)
        opponent.add(opponentParty.member2)
        opponent.add(opponentParty.member3)
        opponent.add(opponentParty.member4)
        opponent.add(opponentParty.member5)
        opponent.add(opponentParty.member6)

        oppo_party1.setOnClickListener(OnPokemonSelectedListener(false, 0))
        oppo_party1.setImageBitmap(util.createImage(opponent.member[0].individual.master, 150.0f, resources))

        oppo_party2.setOnClickListener(OnPokemonSelectedListener(false, 1))
        oppo_party2.setImageBitmap(util.createImage(opponent.member[1].individual.master, 150.0f, resources))

        oppo_party3.setOnClickListener(OnPokemonSelectedListener(false, 2))
        oppo_party3.setImageBitmap(util.createImage(opponent.member[2].individual.master, 150.0f, resources))

        oppo_party4.setOnClickListener(OnPokemonSelectedListener(false, 3))
        oppo_party4.setImageBitmap(util.createImage(opponent.member[3].individual.master, 150.0f, resources))

        oppo_party5.setOnClickListener(OnPokemonSelectedListener(false, 4))
        oppo_party5.setImageBitmap(util.createImage(opponent.member[4].individual.master, 150.0f, resources))

        oppo_party6.setOnClickListener(OnPokemonSelectedListener(false, 5))
        oppo_party6.setImageBitmap(util.createImage(opponent.member[5].individual.master, 150.0f, resources))
    }

    inner class OnPokemonSelectedListener(val isMine: Boolean, val position: Int) : View.OnClickListener {
        val temp = util.createImage(R.drawable.select, 150f, resources)
        override fun onClick(v: View?) {
            if (isMine) {
                selected_party1.setImageBitmap(null)
                selected_party2.setImageBitmap(null)
                selected_party3.setImageBitmap(null)
                when(position){
                    0 -> selected_party1.setImageBitmap(temp)
                    1 -> selected_party2.setImageBitmap(temp)
                    2 -> selected_party3.setImageBitmap(temp)
                }
            } else {
                selected_oppoParty1.setImageBitmap(null)
                selected_oppoParty2.setImageBitmap(null)
                selected_oppoParty3.setImageBitmap(null)
                selected_oppoParty4.setImageBitmap(null)
                selected_oppoParty5.setImageBitmap(null)
                selected_oppoParty6.setImageBitmap(null)
                when(position){
                    0 -> selected_oppoParty1.setImageBitmap(temp)
                    1 -> selected_oppoParty2.setImageBitmap(temp)
                    2 -> selected_oppoParty3.setImageBitmap(temp)
                    3 -> selected_oppoParty4.setImageBitmap(temp)
                    4 -> selected_oppoParty5.setImageBitmap(temp)
                    5 -> selected_oppoParty6.setImageBitmap(temp)
                }
            }
        }
    }

    inner class OnStatusSelectedListener(val party: PartyInBattle) : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {}
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            party.tempStatus = position
        }
    }

    inner class OnRankSelectedListener(val party: PartyInBattle, val which: Int) : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {}
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

    inner class OnHPChangeListener() : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}
        override fun onStartTrackingTouch(seekBar: SeekBar?) {}
        override fun onStopTrackingTouch(seekBar: SeekBar?) {
            opponent.tempHpRatio = seekBar!!.progress
        }
    }

    inner class OnHPEditListener() : TextView.OnEditorActionListener {
        override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
            if (actionId === EditorInfo.IME_ACTION_SEND) {
                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(v!!.windowToken, 0)
                mine.tempHpValue = Integer.parseInt(v.text.toString())
                return true
            }
            return false
        }
    }

    fun showProgress(show: Boolean) {
        val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime)
        coverLayout.visibility = if (show) View.GONE else View.VISIBLE
        coverLayout.animate().setDuration(shortAnimTime.toLong()).alpha(
                (if (show) 0 else 1).toFloat()).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                coverLayout.visibility = if (show) View.GONE else View.VISIBLE
            }
        })

        progress.visibility = if (show) View.VISIBLE else View.GONE
        progress.animate().setDuration(shortAnimTime.toLong()).alpha(
                (if (show) 1 else 0).toFloat()).setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                progress.visibility = if (show) View.VISIBLE else View.GONE
            }
        })

    }
}