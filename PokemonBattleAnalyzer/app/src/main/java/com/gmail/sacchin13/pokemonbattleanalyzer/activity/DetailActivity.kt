package com.gmail.sacchin13.pokemonbattleanalyzer.activity

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.*
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*

import com.gmail.sacchin13.pokemonbattleanalyzer.R
import com.gmail.sacchin13.pokemonbattleanalyzer.Util
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.PokemonMasterData
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.TemporaryStatus
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl.TrendForBattle
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_expected.*
import kotlin.properties.Delegates

class DetailActivity : PGLActivity() {
    val NOT_MEGA = 0
    val MEGA_X = 1
    val MEGA_Y = 2

    val util = Util()
    var id: String = ""
    var temp: TemporaryStatus by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val toolbar = findViewById(R.id.tool_bar) as Toolbar?
        toolbar!!.title = "Pokemon Battle Tool"
        toolbar.setTitleTextColor(Color.WHITE)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeButtonEnabled(true)

        val intent = intent
        if (intent != null) {
            this.id = intent.getStringExtra("id")
            this.temp = intent.getParcelableExtra("status")
            val isMine = intent.getBooleanExtra("isMine", false)
            init(isMine)
            Log.e("DetailActivity", temp.toString())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_detail, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> {
                return true
            }
            android.R.id.home -> {
                intent.putExtra("edited", temp)
                setResult(RESULT_OK, intent)
                finish()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        resetParty(true)

//        for(p in myParty.member){
//            if(p.getId() == this.id){
//                iv.setImageResource(p.getMaster().getResourceId());
//                break;
//            }
//        }

    }

    override fun setTrend(result: TrendForBattle, index: Int) {
        if(result.itemInfo.isEmpty() && result.seikakuInfo.isEmpty() &&
                result.tokuseiInfo.isEmpty() && result.skillList.isEmpty()){
            Snackbar.make(statusview, "download failed at $index", Snackbar.LENGTH_SHORT).show()
        }
        if(finishCount == opponentParty.member.size){
            Snackbar.make(statusview, "download finish", Snackbar.LENGTH_SHORT).show()
        }

        skilltable.removeAllViews()
        characteristictable.removeAllViews()
        itemtable.removeAllViews()
        abilitytable.removeAllViews()

//        if (trend == null) {
            val nullText = arrayOf("-", "-")
            skilltable.addView(createTableRow(nullText, 0, Color.TRANSPARENT, Color.BLACK, 12))
//        } else {
//            val skill = result.createSkillMap()
//            for (key in skill.keys) {
//                val temp = skill[key]!!.clone()
//                temp[1] = temp[1] + "%"
//                skilltable.addView(createTableRow(temp, 0, Color.TRANSPARENT, Color.BLACK, 12))
//            }
//        }
//
//        if (trend == null) {
//            val nullText = arrayOf("-", "-")
            characteristictable.addView(createTableRow(nullText, 0, Color.TRANSPARENT, Color.BLACK, 12))
//        } else {
//            val characteristic = trend.createCharacteristicMap()
//            for (key in characteristic.keys) {
//                val temp = characteristic[key]!!.clone()
//                temp[1] = temp[1] + "%"
//                characteristictable.addView(createTableRow(temp, 0, Color.TRANSPARENT, Color.BLACK, 12))
//            }
//        }
//
//        if (trend == null) {
//            val nullText = arrayOf("-", "-")
            itemtable.addView(createTableRow(nullText, 0, Color.TRANSPARENT, Color.BLACK, 12))
//        } else {
//            val item = trend.createItemMap()
//            for (key in item.keys) {
//                val temp = item[key]!!.clone()
//                temp[1] = temp[1] + "%"
//                itemtable.addView(createTableRow(temp, 0, Color.TRANSPARENT, Color.BLACK, 12))
//            }
//        }
//
//        if (trend == null) {
//            val nullText = arrayOf("-", "-")
            abilitytable.addView(createTableRow(nullText, 0, Color.TRANSPARENT, Color.BLACK, 12))
//        } else {
//            val ability = trend.createAbilityMap()
//            for (key in ability.keys) {
//                val temp = ability[key]!!.clone()
//                temp[1] = temp[1] + "%"
//                abilitytable.addView(createTableRow(temp, 0, Color.TRANSPARENT, Color.BLACK, 12))
//            }
//        }
    }

    fun createTableRow(texts: Array<String>, layoutSpan: Int, bgColor: Int, txtColor: Int, txtSize: Int): TableRow {
        val row = TableRow(this)
        val p = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT)
        if (0 < layoutSpan) {
            p.span = layoutSpan
        }
        row.layoutParams = p
        for (temp in texts) {
            if (layoutSpan <= 0) {
                row.addView(createTextView(temp, bgColor, txtColor, txtSize))
            } else {
                row.addView(createTextView(temp, bgColor, txtColor, txtSize), p)
            }
        }
        return row
    }

    fun createTextView(text: String, bgColor: Int, txtColor: Int, txtSize: Int): TextView {
        val tv = TextView(this)
        tv.text = text
        tv.setBackgroundColor(bgColor)
        tv.setTextColor(txtColor)
        tv.textSize = txtSize.toFloat()
        val p = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT)
        tv.layoutParams = p
        return tv
    }

    override fun showParty() {
        var po = myParty.member[0]
        for(p in myParty.member) if(p.no.equals(id)) po = p
        for(p in opponentParty.member) if(p.no.equals(id)) po = p

        statusview.removeAllViews()
        createPBAPokemonStatus(po, NOT_MEGA)
        if(po.megax != null) createPBAPokemonStatus(po, MEGA_X)
        if(po.megay != null) createPBAPokemonStatus(po, MEGA_Y)
    }

    private fun createPBAPokemonStatus(p: PokemonMasterData, type:Int) {
        val sss = LinearLayout(this)
        sss.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        sss.orientation = LinearLayout.HORIZONTAL

        val temp = when(type){
            NOT_MEGA -> util.createImage(p, 150f, resources)
            MEGA_X -> util.createImage(p.no + "mx", 150f, resources)
            MEGA_Y -> util.createImage(p.no + "my", 150f, resources)
            else -> util.createImage(p, 150f, resources)
        }
        val imageView = ImageView(this)
        imageView.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT)
        imageView.setImageBitmap(temp)
        sss.addView(imageView)

        val status = TableLayout(this)
        status.layoutParams = TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT)
        status.isStretchAllColumns = true

        val headers = arrayOf("H", "A", "B", "C", "D", "S")
        status.addView(createTableRow(headers, 0, Color.TRANSPARENT, Color.GRAY, 12))

        val statuses = when(type){
            NOT_MEGA -> arrayOf(p.h.toString(), p.a.toString(), p.b.toString(), p.c.toString(), p.d.toString(), p.s.toString())
            MEGA_X -> arrayOf(p.megax!!.h.toString(), p.megax!!.a.toString(), p.megax!!.b.toString(), p.megax!!.c.toString(), p.megax!!.d.toString(), p.megax!!.s.toString())
            MEGA_Y -> arrayOf(p.megay!!.h.toString(), p.megay!!.a.toString(), p.megay!!.b.toString(), p.megay!!.c.toString(), p.megay!!.d.toString(), p.megay!!.s.toString())
            else -> arrayOf("-", "-", "-", "-", "-")
        }
        status.addView(createTableRow(statuses, 0, Color.TRANSPARENT, Color.BLACK, 18))

        val characteristics = when(type){
            NOT_MEGA -> arrayOf(p.ability1, p.ability2, p.abilityd)
            MEGA_X -> arrayOf(p.megax!!.ability, "-", "-")
            MEGA_Y -> arrayOf(p.megay!!.ability, "-", "-")
            else -> arrayOf("-", "-", "-")
        }
        status.addView(createTableRow(characteristics, 2, Color.TRANSPARENT, Color.BLACK, 12))

        sss.addView(status)
        statusview.addView(sss)
    }

    fun init(isMine: Boolean){
        my_mega_check.setOnCheckedChangeListener { compoundButton, b -> temp.tempMega = b }
        oppo_mega_check.setOnCheckedChangeListener { compoundButton, b -> temp.tempMega = b }
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
        myStatusSpinner.setSelection(temp.tempStatus)
        myStatusSpinner.onItemSelectedListener = OnStatusSelectedListener()

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
        myASpinner.setSelection(temp.tempAttack)
        myASpinner.onItemSelectedListener = OnRankSelectedListener(0)
        myBSpinner.adapter = rankAdapter
        myBSpinner.setSelection(temp.tempDefense)
        myBSpinner.onItemSelectedListener = OnRankSelectedListener(1)
        myCSpinner.adapter = rankAdapter
        myCSpinner.setSelection(temp.tempSpecialAttack)
        myCSpinner.onItemSelectedListener = OnRankSelectedListener(2)
        myDSpinner.adapter = rankAdapter
        myDSpinner.setSelection(temp.tempSpecialDefense)
        myDSpinner.onItemSelectedListener = OnRankSelectedListener(3)
        mySSpinner.adapter = rankAdapter
        mySSpinner.setSelection(temp.tempSpeed)
        mySSpinner.onItemSelectedListener = OnRankSelectedListener(4)
    }

    inner class OnStatusSelectedListener() : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {}
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            temp.tempStatus = position
        }
    }

    inner class OnRankSelectedListener(val which: Int) : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {}
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            when (which) {
                0 -> temp.setAttackRank(position)
                1 -> temp.setDefenseRank(position)
                2 -> temp.setSpecialAttackRank(position)
                3 -> temp.setSpecialDefenseRank(position)
                4 -> temp.setSpeedRank(position)
            }
        }
    }

    inner class OnHPChangeListener() : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {}
        override fun onStartTrackingTouch(seekBar: SeekBar?) {}
        override fun onStopTrackingTouch(seekBar: SeekBar?) {
            temp.tempHpRatio = seekBar!!.progress
        }
    }

    inner class OnHPEditListener() : TextView.OnEditorActionListener {
        override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
            if (actionId === EditorInfo.IME_ACTION_SEND) {
                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(v!!.windowToken, 0)
                temp.tempHpValue = Integer.parseInt(v.text.toString())
                return true
            }
            return false
        }
    }

}


