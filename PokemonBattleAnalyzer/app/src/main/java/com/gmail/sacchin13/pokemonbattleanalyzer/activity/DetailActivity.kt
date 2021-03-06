package com.gmail.sacchin13.pokemonbattleanalyzer.activity

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.Toolbar
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import com.gmail.sacchin13.pokemonbattleanalyzer.R
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.TemporaryStatus
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl.TrendForBattle
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.realm.PokemonMasterData
import com.gmail.sacchin13.pokemonbattleanalyzer.util.Util
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.onCheckedChange
import org.jetbrains.anko.onClick
import kotlin.properties.Delegates

class DetailActivity : PGLActivity() {
    val NOT_MEGA = 0
    val MEGA_X = 1
    val MEGA_Y = 2

    val util = Util()
    var id: String = ""
    var isMine = false
    var temp: TemporaryStatus by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val toolbar = findViewById(R.id.tool_bar) as Toolbar?
        toolbar?.title = "Pokemon Battle Tool"
        toolbar?.setTitleTextColor(Color.WHITE)
        setSupportActionBar(toolbar)

        this.id = intent?.getStringExtra("id") ?: ""
        this.temp = intent?.getParcelableExtra("status") ?: TemporaryStatus()
        isMine = intent?.getBooleanExtra("isMine", false) ?: false
        init()

        val resourceId = util.pokemonImageResource[id.split("-")[0]]
        image.setImageResource(resourceId ?: R.drawable.noimage)

        detail_fab.onClick {
            intent.putExtra("edited", temp)
            setResult(RESULT_OK, intent)
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        resetParty(true)
        if (isMine) showMineStatus()
    }

    fun showMineStatus() {
        var p = myParty.member1
        if (myParty.member2.master.no.split("-")[0] == id) {
            p = myParty.member2
        }
        if (myParty.member3.master.no.split("-")[0] == id) {
            p = myParty.member3
        }

        skilltable.addView(createTableRow(arrayOf(p.skillNo1.jname, "-"), 0, Color.TRANSPARENT, Color.BLACK, 12))
        skilltable.addView(createTableRow(arrayOf(p.skillNo2.jname, "-"), 0, Color.TRANSPARENT, Color.BLACK, 12))
        skilltable.addView(createTableRow(arrayOf(p.skillNo3.jname, "-"), 0, Color.TRANSPARENT, Color.BLACK, 12))
        skilltable.addView(createTableRow(arrayOf(p.skillNo4.jname, "-"), 0, Color.TRANSPARENT, Color.BLACK, 12))
        characteristictable.addView(createTableRow(arrayOf(p.characteristic, "-"), 0, Color.TRANSPARENT, Color.BLACK, 12))
        itemtable.addView(createTableRow(arrayOf(p.item, "-"), 0, Color.TRANSPARENT, Color.BLACK, 12))
        abilitytable.addView(createTableRow(arrayOf(p.ability, "-"), 0, Color.TRANSPARENT, Color.BLACK, 12))
    }

    override fun setTrend(result: TrendForBattle, index: Int) {
        if (result.itemInfo.isEmpty() && result.seikakuInfo.isEmpty() &&
                result.tokuseiInfo.isEmpty() && result.skillList.isEmpty()) {
            Snackbar.make(base_layout, "download failed at $index", Snackbar.LENGTH_SHORT).show()
        }
        if (opponentParty.member[index].no.split("-")[0] == id) {
            Snackbar.make(base_layout, "download finish", Snackbar.LENGTH_SHORT).show()
            for (key in result.createSkillMap(util)) {
                skilltable.addView(createTableRow(arrayOf(key.first, key.second), 0, Color.TRANSPARENT, Color.BLACK, 12))
            }
            for (key in result.createCharacteristicMap(util)) {
                characteristictable.addView(createTableRow(arrayOf(key.first, key.second), 0, Color.TRANSPARENT, Color.BLACK, 12))
            }
            for (key in result.createItemMap(util)) {
                itemtable.addView(createTableRow(arrayOf(key.first, key.second), 0, Color.TRANSPARENT, Color.BLACK, 12))
            }
            for (key in result.createAbilityMap(util)) {
                abilitytable.addView(createTableRow(arrayOf(key.first, key.second), 0, Color.TRANSPARENT, Color.BLACK, 12))
            }
        }
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

    fun createTextView(t: String, bgColor: Int, txtColor: Int, txtSize: Int): TextView {
        return TextView(this).apply {
            text = t
            setBackgroundColor(bgColor)
            setTextColor(txtColor)
            textSize = txtSize.toFloat()
            val p = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT)
            layoutParams = p
        }
    }

    override fun showParty() {
        var po = myParty.member[0]
        for (p in myParty.member) if (p.no == id) po = p
        for (p in opponentParty.member) if (p.no == id) po = p

        statusview.removeAllViews()
        createPBAPokemonStatus(po, NOT_MEGA)
        if (po.megax != null) createPBAPokemonStatus(po, MEGA_X)
        if (po.megay != null) createPBAPokemonStatus(po, MEGA_Y)

        val forms = po.battling()
        val megaAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, forms)
        megaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val megaSpinner = if (isMine) my_mega_check else oppo_mega_check
        if (forms.size == 1) megaSpinner.visibility = View.GONE else megaSpinner.adapter = megaAdapter
    }

    private fun createPBAPokemonStatus(p: PokemonMasterData, type: Int) {
        val sss = LinearLayout(this).apply {
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            orientation = LinearLayout.HORIZONTAL
        }

        val temp = when (type) {
            NOT_MEGA -> util.createImage(p, 150f, resources)
            MEGA_X -> when (p.no) {
                "681" -> util.createImage(R.drawable.n681_1, 150f, resources)
                "555" -> util.createImage(R.drawable.n555_1, 150f, resources)
                else -> util.createImage(p.no + "mx", 150f, resources)
            }
            MEGA_Y -> util.createImage(p.no + "my", 150f, resources)
            else -> util.createImage(p, 150f, resources)
        }
        val imageView = ImageView(this).apply {
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT)
            setImageBitmap(temp)
        }
        sss.addView(imageView)

        val status = TableLayout(this).apply {
            layoutParams = TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT)
            isStretchAllColumns = true
        }

        val abilities = when (type) {
            NOT_MEGA -> arrayOf(p.ability1, p.ability2, p.abilityd)
            MEGA_X -> arrayOf(p.megax!!.ability, "-", "-")
            MEGA_Y -> arrayOf(p.megay!!.ability, "-", "-")
            else -> arrayOf("-", "-", "-")
        }
        status.addView(createTableRow(abilities, 2, Color.TRANSPARENT, Color.BLACK, 12))

        val headers = arrayOf("H", "A", "B", "C", "D", "S")
        status.addView(createTableRow(headers, 0, Color.TRANSPARENT, Color.GRAY, 12))

        val statuses = when (type) {
            NOT_MEGA -> arrayOf(p.h.toString(), p.a.toString(), p.b.toString(), p.c.toString(), p.d.toString(), p.s.toString())
            MEGA_X -> arrayOf(p.megax?.h.toString(), p.megax?.a.toString(), p.megax?.b.toString(), p.megax?.c.toString(), p.megax?.d.toString(), p.megax?.s.toString())
            MEGA_Y -> arrayOf(p.megay?.h.toString(), p.megay?.a.toString(), p.megay?.b.toString(), p.megay?.c.toString(), p.megay?.d.toString(), p.megay?.s.toString())
            else -> arrayOf("-", "-", "-", "-", "-")
        }
        status.addView(createTableRow(statuses, 0, Color.TRANSPARENT, Color.BLACK, 18))
        sss.addView(status)
        statusview.addView(sss)
    }

    fun init() {
        val s = arrayOf("状態異常", "やけど", "こおり", "まひ", "どく", "もうどく", "ねむり", "ひんし")
        val statusAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, s)
        statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        val r = arrayOf("6", "5", "4", "3", "2", "1", "0", "-1", "-2", "-3", "-4", "-5", "-6")
        val rankAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, r)

        rankAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        myASpinner.adapter = rankAdapter
        myASpinner.setSelection(rankToIndex(temp.tempAttack))
        myASpinner.onItemSelectedListener = OnRankSelectedListener(0)
        myBSpinner.adapter = rankAdapter
        myBSpinner.setSelection(rankToIndex(temp.tempDefense))
        myBSpinner.onItemSelectedListener = OnRankSelectedListener(1)
        myCSpinner.adapter = rankAdapter
        myCSpinner.setSelection(rankToIndex(temp.tempSpecialAttack))
        myCSpinner.onItemSelectedListener = OnRankSelectedListener(2)
        myDSpinner.adapter = rankAdapter
        myDSpinner.setSelection(rankToIndex(temp.tempSpecialDefense))
        myDSpinner.onItemSelectedListener = OnRankSelectedListener(3)
        mySSpinner.adapter = rankAdapter
        mySSpinner.setSelection(rankToIndex(temp.tempSpeed))
        mySSpinner.onItemSelectedListener = OnRankSelectedListener(4)
        myHSpinner.adapter = rankAdapter
        myHSpinner.setSelection(rankToIndex(temp.tempHitProbability))
        myHSpinner.onItemSelectedListener = OnRankSelectedListener(5)
        myAVSpinner.adapter = rankAdapter
        myAVSpinner.setSelection(rankToIndex(temp.tempAvoidance))
        myAVSpinner.onItemSelectedListener = OnRankSelectedListener(6)
        myCRSpinner.adapter = rankAdapter
        myCRSpinner.setSelection(rankToIndex(temp.tempCritical))
        myCRSpinner.onItemSelectedListener = OnRankSelectedListener(7)

        if (isMine) {
            oppo_header.visibility = View.GONE
            myStatusSpinner.adapter = statusAdapter
            myStatusSpinner.setSelection(temp.tempStatus)
            myStatusSpinner.onItemSelectedListener = OnStatusSelectedListener()
            my_mega_check.setSelection(temp.tempMega)
            my_mega_check.onItemSelectedListener = OnMegaSelectedListener()
            myHPBar.setText(temp.tempHpValue.toString())
            myHPBar.setOnEditorActionListener(OnHPEditListener())
            if (temp.tempMigawari == 1) myMigawari.isChecked = true
            myMigawari.onCheckedChange { compoundButton, b -> temp.tempMigawari = if (b) 1 else 0 }
            if (temp.tempItem == 1) myItemUsed.isChecked = true
            myItemUsed.onCheckedChange { compoundButton, b -> temp.tempItem = if (b) 1 else 0 }
        } else {
            my_header.visibility = View.GONE
            opponentStatusSpinner.adapter = statusAdapter
            opponentStatusSpinner.setSelection(temp.tempStatus)
            opponentStatusSpinner.onItemSelectedListener = OnStatusSelectedListener()
            oppo_mega_check.setSelection(temp.tempMega)
            oppo_mega_check.onItemSelectedListener = OnMegaSelectedListener()
            opponentHPBar.progress = temp.tempHpRatio
            opponentHPBar.setOnSeekBarChangeListener(OnHPChangeListener())
            if (temp.tempMigawari == 1) oppoMigawari.isChecked = true
            oppoMigawari.onCheckedChange { compoundButton, b -> temp.tempMigawari = if (b) 1 else 0 }
            if (temp.tempItem == 1) oppoItemUsed.isChecked = true
            oppoItemUsed.onCheckedChange { compoundButton, b -> temp.tempItem = if (b) 1 else 0 }
        }
    }

    inner class OnStatusSelectedListener() : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            temp.tempStatus = position
        }
    }

    inner class OnRankSelectedListener(val which: Int) : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            when (which) {
                0 -> temp.tempAttack = indexToRank(id.toInt())
                1 -> temp.tempDefense = indexToRank(id.toInt())
                2 -> temp.tempSpecialAttack = indexToRank(id.toInt())
                3 -> temp.tempSpecialDefense = indexToRank(id.toInt())
                4 -> temp.tempSpeed = indexToRank(id.toInt())
                5 -> temp.tempHitProbability = indexToRank(id.toInt())
                6 -> temp.tempAvoidance = indexToRank(id.toInt())
                7 -> temp.tempCritical = indexToRank(id.toInt())
            }
        }
    }

    inner class OnMegaSelectedListener() : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            temp.tempMega = id.toInt()
        }
    }

    inner class OnHPChangeListener() : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
            temp.tempHpRatio = seekBar?.progress ?: 100
        }
    }

    inner class OnHPEditListener() : TextView.OnEditorActionListener {
        override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
            if (actionId === EditorInfo.IME_ACTION_SEND) {
                val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(v!!.windowToken, 0)
                temp.tempHpValue = Integer.parseInt(v.text.toString())
                return true
            } else {
                myHPBar.setText("0")
            }
            return false
        }
    }

    fun rankToIndex(rank: Int): Int = rank + 6
    fun indexToRank(position: Int): Int = position - 6
}


