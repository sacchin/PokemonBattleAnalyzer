package com.gmail.sacchin13.pokemonbattleanalyzer.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.*
import android.widget.*
import com.gmail.sacchin13.pokemonbattleanalyzer.DatabaseHelper

import com.gmail.sacchin13.pokemonbattleanalyzer.R
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.*
import kotlinx.android.synthetic.main.activity_edit.*
import kotlin.properties.Delegates

class EditActivity : AppCompatActivity() {
    var mine: Party by Delegates.notNull()
    var item: MutableList<String> by Delegates.notNull()
    var skill: MutableList<Skill?> by Delegates.notNull()
    var databaseHelper: DatabaseHelper by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        databaseHelper = DatabaseHelper(this)
        item = databaseHelper.selectAllItem()
        skill = databaseHelper.selectAllSkill()
    }

    public override fun onResume() {
        super.onResume()
        resetPartyList()
    }

    private fun resetPartyList() {
        mine = databaseHelper.selectParty("mine")

        addPokemonBlock(mine.member1, member1_text, member1CharSpinner, member1ItemSpinner,
                member1Skill1Spinner, member1Skill2Spinner, member1Skill3Spinner, member1Skill4Spinner,
                member1H, member1A, member1B, member1C, member1D, member1S)
        addPokemonBlock(mine.member2, member2_text, member2CharSpinner, member2ItemSpinner,
                member2Skill1Spinner, member2Skill2Spinner, member2Skill3Spinner, member2Skill4Spinner,
                member2H, member2A, member2B, member2C, member2D, member2S)
        addPokemonBlock(mine.member3, member3_text, member3CharSpinner, member3ItemSpinner,
                member3Skill1Spinner, member3Skill2Spinner, member3Skill3Spinner, member3Skill4Spinner,
                member3H, member3A, member3B, member3C, member3D, member3S)
        addPokemonBlock(mine.member4, member4_text, member4CharSpinner, member4ItemSpinner,
                member4Skill1Spinner, member4Skill2Spinner, member4Skill3Spinner, member4Skill4Spinner,
                member4H, member4A, member4B, member4C, member4D, member4S)
        addPokemonBlock(mine.member5, member5_text, member5CharSpinner, member5ItemSpinner,
                member5Skill1Spinner, member5Skill2Spinner, member5Skill3Spinner, member5Skill4Spinner,
                member5H, member5A, member5B, member5C, member5D, member5S)
        addPokemonBlock(mine.member6, member6_text, member6CharSpinner, member6ItemSpinner,
                member6Skill1Spinner, member6Skill2Spinner, member6Skill3Spinner, member6Skill4Spinner,
                member6H, member6A, member6B, member6C, member6D, member6S)

    }

    fun addPokemonBlock(pokemon: IndividualPBAPokemon, textView: TextView, charSpinner: Spinner, itemSpinner: Spinner,
                        skill1Spinner: Spinner, skill2Spinner: Spinner, skill3Spinner: Spinner, skill4Spinner: Spinner,
                        hEdit: EditText, aEdit: EditText, bEdit: EditText, cEdit: EditText, dEdit: EditText, sEdit: EditText) {
        val itemAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, item)
        itemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val skillAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, Skill.companion.skillNameList(skill))
        skillAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val charAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, PokemonCharacteristic.CHARACTERISTIC.toMutableList())
        charAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        textView.text = pokemon.master.jname
        charSpinner.adapter = charAdapter
        charSpinner.setSelection(charAdapter.getPosition(pokemon.characteristic))
        charSpinner.onItemSelectedListener = OnCharSelectedListener(pokemon)
        itemSpinner.adapter = itemAdapter
        itemSpinner.setSelection(itemAdapter.getPosition(pokemon.item))
        itemSpinner.onItemSelectedListener = OnItemSelectedListener(pokemon)
        skill1Spinner.adapter = skillAdapter
        skill1Spinner.setSelection(skillAdapter.getPosition(pokemon.skillNo1.jname))
        skill1Spinner.onItemSelectedListener = OnSkillSelectedListener(1, pokemon)
        skill2Spinner.adapter = skillAdapter
        skill2Spinner.setSelection(skillAdapter.getPosition(pokemon.skillNo2.jname))
        skill2Spinner.onItemSelectedListener = OnSkillSelectedListener(2, pokemon)
        skill3Spinner.adapter = skillAdapter
        skill3Spinner.setSelection(skillAdapter.getPosition(pokemon.skillNo3.jname))
        skill3Spinner.onItemSelectedListener = OnSkillSelectedListener(3, pokemon)
        skill4Spinner.adapter = skillAdapter
        skill4Spinner.setSelection(skillAdapter.getPosition(pokemon.skillNo4.jname))
        skill4Spinner.onItemSelectedListener = OnSkillSelectedListener(4, pokemon)
        hEdit.inputType = InputType.TYPE_CLASS_NUMBER
        if (isEffortValue(pokemon.hpEffortValue)) hEdit.setText(pokemon.hpEffortValue.toString())
        hEdit.addTextChangedListener(OnEditText(1, pokemon))
        aEdit.inputType = InputType.TYPE_CLASS_NUMBER
        if (isEffortValue(pokemon.attackEffortValue)) aEdit.setText(pokemon.attackEffortValue.toString())
        aEdit.addTextChangedListener(OnEditText(2, pokemon))
        bEdit.inputType = InputType.TYPE_CLASS_NUMBER
        if (isEffortValue(pokemon.defenseEffortValue)) bEdit.setText(pokemon.defenseEffortValue.toString())
        bEdit.addTextChangedListener(OnEditText(3, pokemon))
        cEdit.inputType = InputType.TYPE_CLASS_NUMBER
        if (isEffortValue(pokemon.specialAttackEffortValue)) cEdit.setText(pokemon.specialAttackEffortValue.toString())
        cEdit.addTextChangedListener(OnEditText(4, pokemon))
        dEdit.inputType = InputType.TYPE_CLASS_NUMBER
        if (isEffortValue(pokemon.specialDefenseEffortValue)) dEdit.setText(pokemon.specialDefenseEffortValue.toString())
        dEdit.addTextChangedListener(OnEditText(5, pokemon))
        sEdit.inputType = InputType.TYPE_CLASS_NUMBER
        if (isEffortValue(pokemon.speedEffortValue)) sEdit.setText(pokemon.speedEffortValue.toString())
        sEdit.addTextChangedListener(OnEditText(6, pokemon))
    }

    fun isEffortValue(value: Int): Boolean {
        return 0 <= value && value < 253
    }

    inner class OnItemSelectedListener(val pokemon: IndividualPBAPokemon) : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {}
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            databaseHelper.begin()
            pokemon.item = item[position]
            databaseHelper.commit()
        }
    }

    inner class OnCharSelectedListener(val pokemon: IndividualPBAPokemon) : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {}
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            databaseHelper.begin()
            pokemon.characteristic = PokemonCharacteristic.CHARACTERISTIC.toMutableList()[position]
            databaseHelper.commit()
        }
    }

    inner class OnSkillSelectedListener(val index: Int, val pokemon: IndividualPBAPokemon) : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {}
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            databaseHelper.begin()
            when(index){
                1 -> pokemon.skillNo1 = skill[position] as Skill
                2 -> pokemon.skillNo2 = skill[position] as Skill
                3 -> pokemon.skillNo3 = skill[position] as Skill
                4 -> pokemon.skillNo4 = skill[position] as Skill
            }
            databaseHelper.commit()
        }
    }

    inner class OnEditText(val type: Int , val pokemon: IndividualPBAPokemon): TextWatcher{
        override fun afterTextChanged(s: Editable?) {

            val ev = s.toString()
            if (ev.isEmpty()) {
                Snackbar.make(member1_text, "未入力が存在します", Snackbar.LENGTH_SHORT).show()
                return
            }
            try {
                val value = Integer.parseInt(ev)
                if (!isEffortValue(value)) {
                    Snackbar.make(member1_text, "努力値として不正な値です", Snackbar.LENGTH_SHORT).show()
                    return
                }
            } catch (e: NumberFormatException) {
                Snackbar.make(member1_text, "数値として不正な値です", Snackbar.LENGTH_SHORT).show()
                return
            }

            databaseHelper.begin()
            when(type){
                1 -> pokemon.hpEffortValue = s.toString().toInt()
                2 -> pokemon.attackEffortValue = s.toString().toInt()
                3 -> pokemon.defenseEffortValue = s.toString().toInt()
                4 -> pokemon.specialAttackEffortValue = s.toString().toInt()
                5 -> pokemon.specialDefenseEffortValue = s.toString().toInt()
                6 -> pokemon.speedEffortValue = s.toString().toInt()
            }
            databaseHelper.commit()
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
    }
}
