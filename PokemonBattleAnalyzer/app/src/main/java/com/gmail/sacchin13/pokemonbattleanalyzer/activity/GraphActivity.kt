package com.gmail.sacchin13.pokemonbattleanalyzer.activity

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import android.widget.ArrayAdapter

import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BubbleData
import com.github.mikephil.charting.data.BubbleDataSet
import com.github.mikephil.charting.data.BubbleEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.IBubbleDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import com.gmail.sacchin13.pokemonbattleanalyzer.DatabaseHelper
import com.gmail.sacchin13.pokemonbattleanalyzer.R
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.*
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl.TrendForBattle
import com.gmail.sacchin13.pokemonbattleanalyzer.logic.BattleCalculator
import kotlinx.android.synthetic.main.activity_graph.*

import java.util.ArrayList
import kotlin.properties.Delegates

class GraphActivity : PGLActivity(), OnChartValueSelectedListener {

    var opponent: PartyInBattle by Delegates.notNull()
    var item: MutableList<String> by Delegates.notNull()
    var skill: MutableList<Skill?> by Delegates.notNull()

    init {
        opponent = PartyInBattle(PartyInBattle.OPPONENT_SIDE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)

        databaseHelper = DatabaseHelper(this)
        item = databaseHelper.selectAllItem()
        skill = databaseHelper.selectAllSkill()

        initView()

        val l = chart1.legend
        l.position = Legend.LegendPosition.RIGHT_OF_CHART

        val yl = chart1.axisLeft
        yl.spaceTop = 30f
        yl.spaceBottom = 30f
        yl.setDrawZeroLine(false)

        chart1.axisRight.isEnabled = false

        val xl = chart1.xAxis
        xl.position = XAxis.XAxisPosition.BOTTOM


        resetParty(true)

        onProgressChanged()
    }

    fun onProgressChanged() {

    }

    fun setData(){
        val index = arrayOf(4, 64, 128, 192, 252)

        val i = member1SkillSpinner.selectedItemPosition
        opponent.member[0].skill = opponent.member[0].trend.skillList[i].skill
        opponent.member[0].ability = member1AbiSpinner.selectedItem as String
        opponent.member[0].characteristic = member1CharSpinner.selectedItem as String
        opponent.member[0].item = member1ItemSpinner.selectedItem as String
        opponent.member[1].hpRatio = 100

        Log.v(opponent.member[0].individual.master.jname,  opponent.member[0].skill.jname + ", " + opponent.member[0].ability + ", " + opponent.member[0].characteristic + ", " + opponent.member[0].item)
        Log.v(opponent.member[1].individual.master.jname, "defense")
        //val dataSets = ArrayList<IBubbleDataSet>()

        val count = 10
        val range = 30
        val yVals1 = ArrayList<BubbleEntry>()
        val yVals2 = ArrayList<BubbleEntry>()
        val yVals3 = ArrayList<BubbleEntry>()
        val yVals4 = ArrayList<BubbleEntry>()
        val yVals5 = ArrayList<BubbleEntry>()
        val list = ArrayList<BubbleEntry>()
        list.add(BubbleEntry(0.toFloat(), 1.toFloat(), 1f))
        list.add(BubbleEntry(6.toFloat(), 1.toFloat(), 1f))

        for((y, h) in index.withIndex()){
            for((x, bd)in index.withIndex()) {
                val result = BattleCalculator.simulateTurn(opponent.member[0], opponent.member[1], BattleField(), h, bd)
                val pair = result.defeatTimes.filterValues { it -> 0.9 < it.minus(0.0)}.maxBy { it -> it.key }
                val rate = pair!!.value.toDouble().div(result.count().toDouble())

                when(pair.key) {
                    1 -> {
                        Log.v("data1", "x${x.plus(1).toFloat()}, y${y.plus(1).times(20).toFloat()}, k${pair.key}, r${rate.times(100).toFloat()}, ${pair.value.toDouble()}, ${result.count().toDouble()}")
                        yVals1.add(BubbleEntry(x.plus(1).toFloat(), y.plus(1).times(20).toFloat(), rate.times(100).toFloat()))
                    }
                    2 -> {
                        Log.v("data2", "x${x.plus(1).toFloat()}, y${y.plus(1).times(20).toFloat()}, k${pair.key}, r${rate.times(100).toFloat()}, ${pair.value.toDouble()}, ${result.count().toDouble()}")
                        yVals2.add(BubbleEntry(x.plus(1).toFloat(), y.plus(1).times(20).toFloat(), rate.times(100).toFloat()))
                    }
                    3 -> {
                        Log.v("data3", "x${x.plus(1).toFloat()}, y${y.plus(1).times(20).toFloat()}, k${pair.key}, r${rate.times(100).toFloat()}, ${pair.value.toDouble()}, ${result.count().toDouble()}")
                        yVals3.add(BubbleEntry(x.plus(1).toFloat(), y.plus(1).times(20).toFloat(), rate.times(100).toFloat()))
                    }
                    4 -> {
                        Log.v("data4", "x${x.plus(1).toFloat()}, y${y.plus(1).times(20).toFloat()}, k${pair.key}, r${rate.times(100).toFloat()}, ${pair.value.toDouble()}, ${result.count().toDouble()}")
                        yVals4.add(BubbleEntry(x.plus(1).toFloat(), y.plus(1).times(20).toFloat(), rate.times(100).toFloat()))
                    }
                    else -> {
                        Log.v("data5", "x${x.plus(1).toFloat()}, y${y.plus(1).times(20).toFloat()}, k${pair.key}, r${rate.times(100).toFloat()}, ${pair.value.toDouble()}, ${result.count().toDouble()}")
                        yVals5.add(BubbleEntry(x.plus(1).toFloat(), y.plus(1).times(20).toFloat(), rate.times(100).toFloat()))
                    }
                }
            }
        }

        val set1 = BubbleDataSet(yVals1, "DS 12")
        set1.setColor(Color.RED, 130)
        val set2 = BubbleDataSet(yVals2, "DS 23")
        set2.setColor(Color.YELLOW, 130)
        val set3 = BubbleDataSet(yVals3, "DS 34")
        set3.setColor(Color.GREEN, 130)
        val set4 = BubbleDataSet(yVals4, "DS 56")
        set4.setColor(Color.CYAN, 130)
        val set5 = BubbleDataSet(yVals5, "DS 78")
        set5.setColor(Color.BLUE, 130)
        val header = BubbleDataSet(list, "ev")
        header.setColor(Color.WHITE, 130)

        val dataSets = ArrayList<IBubbleDataSet>()
        dataSets.add(set1)
        dataSets.add(set2)
        dataSets.add(set3)
        dataSets.add(set4)
        dataSets.add(set5)
        dataSets.add(header)

        val data = BubbleData(dataSets)
        data.setDrawValues(false)
        data.setValueTextSize(8f)
        data.setValueTextColor(Color.WHITE)
        data.setHighlightCircleWidth(1.0f)

        chart1.setData(data)
        chart1.xAxis.setDrawLabels(false)
        chart1.getAxis(YAxis.AxisDependency.LEFT).setDrawLabels(false)
        chart1.invalidate()
    }

    override fun onValueSelected(e: Entry, h: Highlight) {
        Log.i("VAL SELECTED",
                "Value: " + e.y + ", xIndex: " + e.x
                        + ", DataSet index: " + h.dataSetIndex)
    }

    override fun onNothingSelected() { }

    override fun setTrend(result: TrendForBattle, index: Int) {
        finishCount++

        result.updateSkills(databaseHelper)
        opponent.member[index].trend = result

        if(result.itemInfo.isEmpty() && result.seikakuInfo.isEmpty() &&
                result.tokuseiInfo.isEmpty() && result.skillList.isEmpty()){
            Snackbar.make(chart1, "download failed at $index", Snackbar.LENGTH_SHORT).show()
        }
        if(finishCount == opponentParty.member.size){
            Snackbar.make(chart1, "download finish", Snackbar.LENGTH_SHORT).show()

            val skillAdapter = ArrayAdapter(this, R.layout.my_spinner_item, opponent.member[0].trend.skillNames())
            skillAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            member1SkillSpinner.adapter = skillAdapter

            val abiAdapter = ArrayAdapter(this, R.layout.my_spinner_item, opponent.member[0].individual.abilities)
            abiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            member1AbiSpinner.adapter = abiAdapter

            member1_text.text = opponent.member[0].individual.master.jname
            member2_text.text = opponent.member[1].individual.master.jname
        }
    }

    override fun showParty() {
        opponent.add(opponentParty.member1)
        opponent.add(opponentParty.member2)
        opponent.add(opponentParty.member3)
        opponent.add(opponentParty.member4)
        opponent.add(opponentParty.member5)
        opponent.add(opponentParty.member6)
    }

    fun initView() {
        chart1.setDescription("")
        chart1.setOnChartValueSelectedListener(this)
        chart1.setDrawGridBackground(false)
        chart1.setTouchEnabled(true)
        chart1.isDragEnabled = true
        chart1.setScaleEnabled(true)
        chart1.setMaxVisibleValueCount(200)
        chart1.setPinchZoom(true)

        val itemAdapter = ArrayAdapter(this, R.layout.my_spinner_item, item)
        itemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val charAdapter = ArrayAdapter(this, R.layout.my_spinner_item, PokemonCharacteristic.CHARACTERISTIC.toMutableList())
        charAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        member1CharSpinner.adapter = charAdapter
        member1ItemSpinner.adapter = itemAdapter

        graph_fab.setOnClickListener {
            setData()
        }

    }
}
