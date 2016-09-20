package com.gmail.sacchin13.pokemonbattleanalyzer.activity

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.gmail.sacchin13.pokemonbattleanalyzer.R
import com.gmail.sacchin13.pokemonbattleanalyzer.Util
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.*
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl.TrendForBattle
import com.gmail.sacchin13.pokemonbattleanalyzer.logic.BattleCalculator
import kotlinx.android.synthetic.main.activity_expected.*
import org.jetbrains.anko.backgroundColor
import kotlin.properties.Delegates

class ExpectedActivity : PGLActivity() {
    val DETAIL_CODE = 0

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

        selectedMine.skill = selectedMine.individual.skillNo1
        val caseOfSkill1 = BattleCalculator.getResultFirst(selectedMine, selectedOpponent, BattleField())

        coverRate.text = caseOfSkill1.coverRate()
        val (label, rate) = caseOfSkill1.orderResult(selectedMine, selectedOpponent)
        if (label.equals("UNKNOWN")) order.text = "必ず後手" else order.text = label + "(${util.percent(rate)})まで抜ける"
        if (caseOfSkill1.prioritySkills.isEmpty()) {
            priority.text = "なし"
        } else {
            for (temp in caseOfSkill1.prioritySkills) {
                priority.text = temp.key + " = ${util.percent(temp.value)}\n"
            }
        }

        //技1の場合
        skill1_name.text = selectedMine.individual.skillNo1.jname
        for (temp in caseOfSkill1.defeatedTimes) {
            for (pair in temp.value) {
                Log.v("defeatedTimes", "${pair}")
            }
        }

        //技2の場合
        selectedMine.skill = selectedMine.individual.skillNo2
        val caseOfSkill2 = BattleCalculator.getResult(selectedMine, selectedOpponent, BattleField())
        skill2_name.text = selectedMine.individual.skillNo2.jname
        for (temp in caseOfSkill2.defeatedTimes) {
            for (pair in temp.value) {
                Log.v("defeatedTimes", "${pair}")
            }
        }

        //技3の場合
        selectedMine.skill = selectedMine.individual.skillNo3
        val caseOfSkill3 = BattleCalculator.getResult(selectedMine, selectedOpponent, BattleField())
        skill3_name.text = selectedMine.individual.skillNo3.jname
        for (temp in caseOfSkill3.defeatedTimes) {
            for (pair in temp.value) {
                Log.v("defeatedTimes", "${pair}")
            }
        }

        //技4の場合
        selectedMine.skill = selectedMine.individual.skillNo4
        val caseOfSkill4 = BattleCalculator.getResult(selectedMine, selectedOpponent, BattleField())
        skill4_name.text = selectedMine.individual.skillNo4.jname
        for (temp in caseOfSkill4.defeatedTimes) {
            for (pair in temp.value) {
                Log.v("defeatedTimes", "${pair}")
            }
        }

        val yValues = mutableListOf<BarEntry>()
        yValues.add(BarEntry(2.toFloat(), caseOfSkill4.arrayForBarChart()))
        yValues.add(BarEntry(7.toFloat(), caseOfSkill3.arrayForBarChart()))
        yValues.add(BarEntry(12.toFloat(), caseOfSkill2.arrayForBarChart()))
        yValues.add(BarEntry(17.toFloat(), caseOfSkill1.arrayForBarChart()))
        setData(yValues)

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

        if (result.itemInfo.isEmpty() && result.seikakuInfo.isEmpty() &&
                result.tokuseiInfo.isEmpty() && result.skillList.isEmpty()) {
            Snackbar.make(inputTable.parent as View, "download failed at $index", Snackbar.LENGTH_SHORT).show()
        }
        if (finishCount == opponentParty.member.size) {
            Snackbar.make(inputTable.parent as View, "download finish", Snackbar.LENGTH_SHORT).show()
        }
    }

    fun initView(intent: Intent) {

        mine.add(get(intent.extras.getInt("member1", 0)))
        mine.add(get(intent.extras.getInt("member2", 0)))
        mine.add(get(intent.extras.getInt("member3", 0)))
        my_party1.setOnClickListener(OnPokemonSelectedListener(true, 0))
        my_party1.setImageBitmap(util.createImage(mine.member[0].individual.master, 180.0f, resources))
        my_party2.setOnClickListener(OnPokemonSelectedListener(true, 1))
        my_party2.setImageBitmap(util.createImage(mine.member[1].individual.master, 180.0f, resources))
        my_party3.setOnClickListener(OnPokemonSelectedListener(true, 2))
        my_party3.setImageBitmap(util.createImage(mine.member[2].individual.master, 180.0f, resources))

        chart1.setDrawGridBackground(false)
        chart1.setPinchZoom(false)
        chart1.setDrawBarShadow(false)
        chart1.setDrawValueAboveBar(true)
        chart1.isHighlightFullBarEnabled = false
        chart1.setDescription("")
        chart1.setNoDataText("相手を何回で倒せる？")
        chart1.setNoDataTextColor(Color.DKGRAY)
        chart1.xAxis.setAxisMinValue(0f)
        chart1.xAxis.setAxisMaxValue(19f)
        chart1.xAxis.setDrawAxisLine(true)
        chart1.xAxis.setDrawLabels(false)
        chart1.getAxis(YAxis.AxisDependency.LEFT).setDrawLabels(false)

        expected_fab.setOnClickListener {
//            showProgress(true)
            showBest()
//            showProgress(false)
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
        oppo_party1.setOnLongClickListener(OnPokemonLongTapListener(0, opponent.member[0]))
        oppo_party1.setImageBitmap(util.createImage(opponent.member[0].individual.master, 180.0f, resources))
        oppo_party2.setOnClickListener(OnPokemonSelectedListener(false, 1))
        oppo_party2.setOnLongClickListener(OnPokemonLongTapListener(1, opponent.member[1]))
        oppo_party2.setImageBitmap(util.createImage(opponent.member[1].individual.master, 180.0f, resources))
        oppo_party3.setOnClickListener(OnPokemonSelectedListener(false, 2))
        oppo_party3.setOnLongClickListener(OnPokemonLongTapListener(2, opponent.member[2]))
        oppo_party3.setImageBitmap(util.createImage(opponent.member[2].individual.master, 180.0f, resources))
        oppo_party4.setOnClickListener(OnPokemonSelectedListener(false, 3))
        oppo_party4.setOnLongClickListener(OnPokemonLongTapListener(3, opponent.member[3]))
        oppo_party4.setImageBitmap(util.createImage(opponent.member[3].individual.master, 180.0f, resources))
        oppo_party5.setOnClickListener(OnPokemonSelectedListener(false, 4))
        oppo_party5.setOnLongClickListener(OnPokemonLongTapListener(4, opponent.member[4]))
        oppo_party5.setImageBitmap(util.createImage(opponent.member[4].individual.master, 180.0f, resources))
        oppo_party6.setOnClickListener(OnPokemonSelectedListener(false, 5))
        oppo_party6.setOnLongClickListener(OnPokemonLongTapListener(5, opponent.member[5]))
        oppo_party6.setImageBitmap(util.createImage(opponent.member[5].individual.master, 180.0f, resources))
    }

    inner class OnPokemonSelectedListener(val isMine: Boolean, val position: Int) : View.OnClickListener {
        val temp = util.createImage(R.drawable.select, 180f, resources)
        override fun onClick(v: View?) {
            if (isMine) {
                selected_party1.setImageBitmap(null)
                selected_party2.setImageBitmap(null)
                selected_party3.setImageBitmap(null)
                when (position) {
                    0 -> selected_party1.setImageBitmap(temp)
                    1 -> selected_party2.setImageBitmap(temp)
                    2 -> selected_party3.setImageBitmap(temp)
                }
                mine.selected = position
            } else {
                selected_oppoParty1.setImageBitmap(null)
                selected_oppoParty2.setImageBitmap(null)
                selected_oppoParty3.setImageBitmap(null)
                selected_oppoParty4.setImageBitmap(null)
                selected_oppoParty5.setImageBitmap(null)
                selected_oppoParty6.setImageBitmap(null)
                when (position) {
                    0 -> selected_oppoParty1.setImageBitmap(temp)
                    1 -> selected_oppoParty2.setImageBitmap(temp)
                    2 -> selected_oppoParty3.setImageBitmap(temp)
                    3 -> selected_oppoParty4.setImageBitmap(temp)
                    4 -> selected_oppoParty5.setImageBitmap(temp)
                    5 -> selected_oppoParty6.setImageBitmap(temp)
                }
                opponent.selected = position
            }
            startDetailActivity(isMine)
        }
    }

    inner class OnPokemonLongTapListener(val index: Int, val pokemon: PokemonForBattle) : View.OnLongClickListener {
        override fun onLongClick(p0: View?): Boolean {
            val from = when (index) {
                0 -> oppo_party1
                1 -> oppo_party2
                2 -> oppo_party3
                3 -> oppo_party4
                4 -> oppo_party5
                5 -> oppo_party6
                else -> selected_party1
            }

            val added = opponent.add(pokemon)
            val white = Color.parseColor("#64FFFFFF")
            from.backgroundColor = if (added) white else Color.parseColor("#00000000")

            return true
        }
    }


//    fun showProgress(show: Boolean) {
//        val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime)
//        coverLayout.visibility = if (show) View.GONE else View.VISIBLE
//        coverLayout.animate().setDuration(shortAnimTime.toLong()).alpha(
//                (if (show) 0 else 1).toFloat()).setListener(object : AnimatorListenerAdapter() {
//            override fun onAnimationEnd(animation: Animator) {
//                coverLayout.visibility = if (show) View.GONE else View.VISIBLE
//            }
//        })
//
//        progress.visibility = if (show) View.VISIBLE else View.GONE
//        progress.animate().setDuration(shortAnimTime.toLong()).alpha(
//                (if (show) 1 else 0).toFloat()).setListener(object : AnimatorListenerAdapter() {
//            override fun onAnimationEnd(animation: Animator) {
//                progress.visibility = if (show) View.VISIBLE else View.GONE
//            }
//        })
//
//    }

    fun startDetailActivity(isMine: Boolean) {
        val party = if (isMine) mine else opponent
        party.load()
        val num = if (isMine) party.selected else party.selected + 4
        val from = when (num) {
            0 -> selected_party1
            1 -> selected_party2
            2 -> selected_party3
            3 -> selected_oppoParty1
            4 -> selected_oppoParty2
            5 -> selected_oppoParty3
            6 -> selected_oppoParty4
            7 -> selected_oppoParty5
            8 -> selected_oppoParty6
            else -> selected_party1
        }

        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("id", party.member[party.selected].individual.master.no)
        intent.putExtra("status", party.temp)
        intent.putExtra("isMine", isMine)
        startActivityForResult(intent, DETAIL_CODE,
                ActivityOptions.makeSceneTransitionAnimation(this, from, "image").toBundle())
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == DETAIL_CODE) {
            val party = if (data!!.getBooleanExtra("isMine", true)) mine else opponent
            party.temp = data.getParcelableExtra<TemporaryStatus>("edited")
            party.apply()
            Log.v("ExpectedActivity", party.temp.toString())
        }
    }

    fun getColors(): IntArray {
        return intArrayOf(
                Color.parseColor("#88F44336"),
                Color.parseColor("#88FF9800"),
                Color.parseColor("#884CAF50"),
                Color.parseColor("#88448AFF"),
                Color.parseColor("#883F51B5"))
    }

    fun setData(yValues: MutableList<BarEntry>) {
        val set = BarDataSet(yValues, "")
        set.setDrawValues(false)
        set.setColors(getColors())
        set.stackLabels = arrayOf("確1", "確2", "確3", "確4", "確5以上")

        val data = BarData(set)
        data.barWidth = 3f
        chart1.data = data

        chart1.animateY(2000)
    }
}
