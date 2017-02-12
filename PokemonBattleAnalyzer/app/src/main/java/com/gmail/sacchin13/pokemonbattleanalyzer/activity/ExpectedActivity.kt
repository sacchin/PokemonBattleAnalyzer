package com.gmail.sacchin13.pokemonbattleanalyzer.activity

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.util.Log
import android.view.View
import android.view.animation.Animation
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CompoundButton
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.gmail.sacchin13.pokemonbattleanalyzer.R
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.*
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl.TrendForBattle
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.realm.IndividualPokemon
import com.gmail.sacchin13.pokemonbattleanalyzer.logic.BattleCalculator
import com.gmail.sacchin13.pokemonbattleanalyzer.logic.GeneralCalculator
import com.gmail.sacchin13.pokemonbattleanalyzer.util.Util
import kotlinx.android.synthetic.main.activity_expected.*
import org.jetbrains.anko.backgroundColor
import kotlin.properties.Delegates

class ExpectedActivity : PGLActivity() {
    val DETAIL_CODE = 0

    var util: Util by Delegates.notNull()
    var opponent: PartyInBattle by Delegates.notNull()
    var mine: PartyInBattle by Delegates.notNull()
    var allField = BattleField()

    init {
        util = Util()
        opponent = PartyInBattle(PartyInBattle.OPPONENT_SIDE)
        mine = PartyInBattle(PartyInBattle.MY_SIDE)
    }

    inner class GeneralCalculatorListener(
            val selectedMine: PokemonForBattle,
            val selectedOpponent: PokemonForBattle) : GeneralCalculator.EventListener {
        override fun onFinish(result: BattleResult) {
            showGeneralResult(selectedMine, selectedOpponent, result)
        }
    }

    inner class BattleCalculatorListener : BattleCalculator.EventListener {
        override fun onFinish(result: BattleResult) {
            Log.v("showBest", "start")
            for ((key, list) in result.defeatedTimes) {
                Log.v("showBest", "$key: ${summary(list)}")
            }
        }

        fun summary(list: MutableList<BattleResult.SufferDamage>): String {
            if (list.sumBy { it -> it.time } == 0) return "効果なし"

            list.forEach {
                Log.v("summary", "${it.damage}, ${it.time}, ${it.rate}")
            }

//            var result = "re: "
//            list.groupBy { it -> "${it.time}確" }.forEach {
//                result += "${}(${Util.percent(it.value.sum().toDouble())}), "
//            }

            return ""
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expected)

        resetParty(true)
        initView(intent)
    }

    fun showBest() {
        Log.v("showBest", "start")
        val selectedMine = mine.apply()
        val selectedOpponent = opponent.apply()
        allField.resetAttackSide(mine.field)
        allField.resetDefenseSide(opponent.field)

        GeneralCalculator(selectedMine, selectedOpponent, allField, GeneralCalculatorListener(selectedMine, selectedOpponent)).execute()
        BattleCalculator(50, selectedMine, selectedOpponent, allField, BattleCalculatorListener()).execute()


        //val suffer = calc.sufferDamage(selectedMine, selectedOpponent, allField)


        showDamage()


//        val caseOfSkill2 = calc.givenDamage(selectedMine, selectedOpponent, allField)
        //控え1に交換した場合
        //控え2に交換した場合
    }

    fun showGeneralResult(selectedMine: PokemonForBattle, selectedOpponent: PokemonForBattle, result: BattleResult) {
        coverRate.text = result.coverRate()
        order.text = result.orderResult(selectedMine, selectedOpponent, allField,
                mine.field.contains(BattleField.Field.Tailwind), opponent.field.contains(BattleField.Field.Tailwind))
        correctionRate.text = result.correctionRate()
        scarf.text = result.scarfRate()
        orderAbility.text = result.orderAbility()
        priority.text = result.prioritySkill()

//        for ((key, value) in result.defeatedTimes) {
//            for (pair in value) {
//                Log.v("defeatedTimes", "$pair")
//            }
//        }
    }


    fun showDamage() {
//        //技1の場合
//        selectedMine.skill = selectedMine.individual.skillNo1
//        val caseOfSkill1 = calc.sufferDamage(selectedMine, selectedOpponent, allField)
//        skill1_name.text = selectedMine.individual.skillNo1.jname
//        skill1_name.textColor = if (Skill.migawariSkill(selectedMine.individual.skillNo1.jname)) Color.RED else Color.DKGRAY
//
//        //技2の場合
//        selectedMine.skill = selectedMine.individual.skillNo2
//        val caseOfSkill2 = calc.sufferDamage(selectedMine, selectedOpponent, allField)
//        skill2_name.text = selectedMine.individual.skillNo2.jname
//        skill2_name.textColor = if (Skill.migawariSkill(selectedMine.individual.skillNo2.jname)) Color.RED else Color.DKGRAY
//
//        //技3の場合
//        selectedMine.skill = selectedMine.individual.skillNo3
//        val caseOfSkill3 = calc.sufferDamage(selectedMine, selectedOpponent, allField)
//        skill3_name.text = selectedMine.individual.skillNo3.jname
//        skill3_name.textColor = if (Skill.migawariSkill(selectedMine.individual.skillNo3.jname)) Color.RED else Color.DKGRAY
//
//        //技4の場合
//        selectedMine.skill = selectedMine.individual.skillNo4
//        val caseOfSkill4 = calc.sufferDamage(selectedMine, selectedOpponent, allField)
//        skill4_name.text = selectedMine.individual.skillNo4.jname
//        skill4_name.textColor = if (Skill.migawariSkill(selectedMine.individual.skillNo4.jname)) Color.RED else Color.DKGRAY
//
//        val yValues = mutableListOf<BarEntry>()
//        yValues.add(BarEntry(2.toFloat(), caseOfSkill4.arrayForBarChart()))
//        yValues.add(BarEntry(7.toFloat(), caseOfSkill3.arrayForBarChart()))
//        yValues.add(BarEntry(12.toFloat(), caseOfSkill2.arrayForBarChart()))
//        yValues.add(BarEntry(17.toFloat(), caseOfSkill1.arrayForBarChart()))
//        setData(yValues)
    }


    fun get(index: Int): IndividualPokemon {
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
        opponent.member[index].ready()
        Log.v("setTrend", "${opponent.member[index].individualForUI.master.jname}($index) is downloaded($finishCount) !")

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
        mine.member[0].ready()
        my_party2.setOnClickListener(OnPokemonSelectedListener(true, 1))
        my_party2.setImageBitmap(util.createImage(mine.member[1].individual.master, 180.0f, resources))
        mine.member[1].ready()
        my_party3.setOnClickListener(OnPokemonSelectedListener(true, 2))
        my_party3.setImageBitmap(util.createImage(mine.member[2].individual.master, 180.0f, resources))
        mine.member[2].ready()

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

        val temp = util.createImage(R.drawable.select, 180f, resources)
        selected_party1.setImageBitmap(temp)
        selected_oppoParty1.setImageBitmap(temp)

        val weatherAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayOf("天候", "晴", "雨", "砂", "霰"))
        weatherAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        weather.adapter = weatherAdapter
        weather.onItemSelectedListener = OnWeatherSelectedListener()

        val roomAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayOf("ルーム", "トリック", "マジック", "ワンダー"))
        roomAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        room.adapter = roomAdapter
        room.onItemSelectedListener = OnRoomSelectedListener()

        val terrainAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayOf("フィールド", "エレキ", "グラス", "ミスト", "サイコ"))
        terrainAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        terrain.adapter = terrainAdapter
        terrain.onItemSelectedListener = OnTerrainSelectedListener()

        my_lightScreen.setOnCheckedChangeListener(OnFieldCheckListener(1, true))
        oppo_lightScreen.setOnCheckedChangeListener(OnFieldCheckListener(1, false))
        my_reflect.setOnCheckedChangeListener(OnFieldCheckListener(2, true))
        oppo_reflect.setOnCheckedChangeListener(OnFieldCheckListener(2, false))
        my_tail.setOnCheckedChangeListener(OnFieldCheckListener(3, true))
        oppo_tail.setOnCheckedChangeListener(OnFieldCheckListener(3, false))
        my_stealth.setOnCheckedChangeListener(OnFieldCheckListener(4, true))
        oppo_stealth.setOnCheckedChangeListener(OnFieldCheckListener(4, false))
        my_safe.setOnCheckedChangeListener(OnFieldCheckListener(5, true))
        oppo_safe.setOnCheckedChangeListener(OnFieldCheckListener(5, false))
        my_gravity.setOnCheckedChangeListener(OnFieldCheckListener(6, true))
        oppo_gravity.setOnCheckedChangeListener(OnFieldCheckListener(6, false))
        my_spike.setOnCheckedChangeListener(OnFieldCheckListener(7, true))
        oppo_spike.setOnCheckedChangeListener(OnFieldCheckListener(7, false))
        my_toxic.setOnCheckedChangeListener(OnFieldCheckListener(8, true))
        oppo_toxic.setOnCheckedChangeListener(OnFieldCheckListener(8, false))
        my_mat.setOnCheckedChangeListener(OnFieldCheckListener(9, true))
        oppo_mat.setOnCheckedChangeListener(OnFieldCheckListener(9, false))
        my_stickyWeb.setOnCheckedChangeListener(OnFieldCheckListener(10, true))
        oppo_stickyWeb.setOnCheckedChangeListener(OnFieldCheckListener(10, false))
        my_mud.setOnCheckedChangeListener(OnFieldCheckListener(11, true))
        oppo_mud.setOnCheckedChangeListener(OnFieldCheckListener(11, false))
        my_water.setOnCheckedChangeListener(OnFieldCheckListener(12, true))
        oppo_water.setOnCheckedChangeListener(OnFieldCheckListener(12, false))
        my_luckyChant.setOnCheckedChangeListener(OnFieldCheckListener(13, true))
        oppo_luckyChant.setOnCheckedChangeListener(OnFieldCheckListener(13, false))
        my_mist.setOnCheckedChangeListener(OnFieldCheckListener(14, true))
        oppo_mist.setOnCheckedChangeListener(OnFieldCheckListener(14, false))

        open.setImageBitmap(BitmapFactory.decodeResource(resources, android.R.drawable.ic_input_add))
        open.setOnClickListener {
            //val inAnimation = AnimationUtils.loadAnimation(this, R.anim.in_animation)
            //val outAnimation = AnimationUtils.loadAnimation(this, R.anim.out_animation)
            if (field_container.visibility == View.GONE) {
                //inAnimation.setAnimationListener(OnAnimationListener(true))
                //field_container.startAnimation(inAnimation)
                field_container.visibility = View.VISIBLE
            } else {
                field_container.visibility = View.GONE
                //outAnimation.setAnimationListener(OnAnimationListener(false))
                //field_container.startAnimation(outAnimation)
            }
        }

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
            val white = Color.parseColor("#64DDDDDD")
            from.backgroundColor = if (added) white else Color.parseColor("#00000000")

            return true
        }
    }

    inner class OnFieldCheckListener(val index: Int, val isMine: Boolean) : CompoundButton.OnCheckedChangeListener {
        override fun onCheckedChanged(p0: CompoundButton?, p1: Boolean) {
            val party = if (isMine) mine else opponent
            when (index) {
                1 -> if (p1) party.add(BattleField.Field.LightScreen) else party.remove(BattleField.Field.LightScreen)
                2 -> if (p1) party.add(BattleField.Field.Reflect) else party.remove(BattleField.Field.Reflect)
                3 -> if (p1) party.add(BattleField.Field.Tailwind) else party.remove(BattleField.Field.Tailwind)
                4 -> if (p1) party.add(BattleField.Field.StealthRock) else party.remove(BattleField.Field.StealthRock)
                5 -> if (p1) party.add(BattleField.Field.Safeguard) else party.remove(BattleField.Field.Safeguard)
                6 -> if (p1) party.add(BattleField.Field.Gravity) else party.remove(BattleField.Field.Gravity)
                7 -> if (p1) party.add(BattleField.Field.Spikes) else party.remove(BattleField.Field.Spikes)
                8 -> if (p1) party.add(BattleField.Field.ToxicSpikes) else party.remove(BattleField.Field.ToxicSpikes)
                9 -> if (p1) party.add(BattleField.Field.MatBlock) else party.remove(BattleField.Field.MatBlock)
                10 -> if (p1) party.add(BattleField.Field.StickyWeb) else party.remove(BattleField.Field.StickyWeb)
                11 -> if (p1) party.add(BattleField.Field.MudSport) else party.remove(BattleField.Field.MudSport)
                12 -> if (p1) party.add(BattleField.Field.WaterSport) else party.remove(BattleField.Field.WaterSport)
                13 -> if (p1) party.add(BattleField.Field.LuckyChant) else party.remove(BattleField.Field.LuckyChant)
                14 -> if (p1) party.add(BattleField.Field.Mist) else party.remove(BattleField.Field.Mist)
                else -> Log.e("OnFieldCheckListener", "UNKNOWN")
            }
        }
    }

    inner class OnWeatherSelectedListener() : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            when (position) {
                1 -> allField.weather = BattleField.Weather.Sunny
                2 -> allField.weather = BattleField.Weather.Rainy
                3 -> allField.weather = BattleField.Weather.Sandstorm
                4 -> allField.weather = BattleField.Weather.Hailstone
                else -> allField.weather = BattleField.Weather.Unknown
            }
        }
    }

    inner class OnRoomSelectedListener() : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            when (position) {
                1 -> allField.room = BattleField.Room.Trick
                2 -> allField.room = BattleField.Room.Magic
                3 -> allField.room = BattleField.Room.Wander
                else -> allField.room = BattleField.Room.Unknown
            }
        }
    }

    inner class OnTerrainSelectedListener() : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            when (position) {
                1 -> allField.terrain = BattleField.Terrain.ElectricTerrain
                2 -> allField.terrain = BattleField.Terrain.GrassyTerrain
                3 -> allField.terrain = BattleField.Terrain.MistyTerrain
                4 -> allField.terrain = BattleField.Terrain.PsycoTerrain
                else -> allField.terrain = BattleField.Terrain.Unknown
            }
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
        if (requestCode == DETAIL_CODE && data != null) {
            val party = if (data.getBooleanExtra("isMine", true)) mine else opponent
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


    inner class OnAnimationListener(val isOpen: Boolean) : Animation.AnimationListener {
        override fun onAnimationRepeat(p0: Animation?) {
        }

        override fun onAnimationEnd(p0: Animation?) {
            if (isOpen) {
                field_container.visibility = View.VISIBLE
                open.setImageBitmap(BitmapFactory.decodeResource(resources, android.R.drawable.ic_input_delete))
            } else {
                field_container.visibility = View.GONE
                open.setImageBitmap(BitmapFactory.decodeResource(resources, android.R.drawable.ic_input_add))
            }
        }

        override fun onAnimationStart(p0: Animation?) {
        }
    }


}
