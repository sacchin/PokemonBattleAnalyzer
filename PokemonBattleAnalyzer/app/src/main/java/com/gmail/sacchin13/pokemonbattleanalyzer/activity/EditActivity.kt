package com.gmail.sacchin13.pokemonbattleanalyzer.activity

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.*
import com.gmail.sacchin13.pokemonbattleanalyzer.DatabaseHelper
import com.gmail.sacchin13.pokemonbattleanalyzer.R
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.*
import kotlinx.android.synthetic.main.activity_edit.*
import kotlin.properties.Delegates

class EditActivity : AppCompatActivity() {
    var mine: Party by Delegates.notNull()
    var item: MutableList<String> by Delegates.notNull()
    var skill: MutableList<Skill> by Delegates.notNull()
    var databaseHelper: DatabaseHelper by Delegates.notNull()
    val iv = arrayOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",
            "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
            "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31").reversedArray()

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

        addPokemonBlock(mine.member1, member1_text, member1CharSpinner, member1ItemSpinner, member1AbiSpinner,
                member1Skill1Spinner, member1Skill2Spinner, member1Skill3Spinner, member1Skill4Spinner,
                member1HSpinner, member1ASpinner, member1BSpinner, member1CSpinner, member1DSpinner, member1SSpinner,
                member1H, member1A, member1B, member1C, member1D, member1S)
        addPokemonBlock(mine.member2, member2_text, member2CharSpinner, member2ItemSpinner, member2AbiSpinner,
                member2Skill1Spinner, member2Skill2Spinner, member2Skill3Spinner, member2Skill4Spinner,
                member2HSpinner, member2ASpinner, member2BSpinner, member2CSpinner, member2DSpinner, member2SSpinner,
                member2H, member2A, member2B, member2C, member2D, member2S)
        addPokemonBlock(mine.member3, member3_text, member3CharSpinner, member3ItemSpinner, member3AbiSpinner,
                member3Skill1Spinner, member3Skill2Spinner, member3Skill3Spinner, member3Skill4Spinner,
                member3HSpinner, member3ASpinner, member3BSpinner, member3CSpinner, member3DSpinner, member3SSpinner,
                member3H, member3A, member3B, member3C, member3D, member3S)
        addPokemonBlock(mine.member4, member4_text, member4CharSpinner, member4ItemSpinner, member4AbiSpinner,
                member4Skill1Spinner, member4Skill2Spinner, member4Skill3Spinner, member4Skill4Spinner,
                member4HSpinner, member4ASpinner, member4BSpinner, member4CSpinner, member4DSpinner, member4SSpinner,
                member4H, member4A, member4B, member4C, member4D, member4S)
        addPokemonBlock(mine.member5, member5_text, member5CharSpinner, member5ItemSpinner, member5AbiSpinner,
                member5Skill1Spinner, member5Skill2Spinner, member5Skill3Spinner, member5Skill4Spinner,
                member5HSpinner, member5ASpinner, member5BSpinner, member5CSpinner, member5DSpinner, member5SSpinner,
                member5H, member5A, member5B, member5C, member5D, member5S)
        addPokemonBlock(mine.member6, member6_text, member6CharSpinner, member6ItemSpinner, member6AbiSpinner,
                member6Skill1Spinner, member6Skill2Spinner, member6Skill3Spinner, member6Skill4Spinner,
                member6HSpinner, member6ASpinner, member6BSpinner, member6CSpinner, member6DSpinner, member6SSpinner,
                member6H, member6A, member6B, member6C, member6D, member6S)
    }

    fun addPokemonBlock(pokemon: IndividualPokemon, textView: TextView, charSpinner: Spinner, itemSpinner: Spinner, abiSpinner: Spinner,
                        skill1Spinner: Spinner, skill2Spinner: Spinner, skill3Spinner: Spinner, skill4Spinner: Spinner,
                        hSpinner: Spinner, aSpinner: Spinner, bSpinner: Spinner, cSpinner: Spinner, dSpinner: Spinner, sSpinner: Spinner,
                        hEdit: EditText, aEdit: EditText, bEdit: EditText, cEdit: EditText, dEdit: EditText, sEdit: EditText) {
        val itemAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, item)
        itemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val skillAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, Skill.skillNameList(skill))
        skillAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val charAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, PokemonCharacteristic.CHARACTERISTIC.toMutableList())
        charAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val abiAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, pokemon.abilities)
        abiAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        val ivAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, iv)
        ivAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        textView.text = pokemon.master.jname
        charSpinner.adapter = charAdapter
        charSpinner.setSelection(charAdapter.getPosition(pokemon.characteristic))
        charSpinner.onItemSelectedListener = OnCharSelectedListener(pokemon)
        itemSpinner.adapter = itemAdapter
        itemSpinner.setSelection(itemAdapter.getPosition(pokemon.item))
        itemSpinner.onItemSelectedListener = OnItemSelectedListener(pokemon)
        abiSpinner.adapter = abiAdapter
        abiSpinner.setSelection(abiAdapter.getPosition(pokemon.ability))
        abiSpinner.onItemSelectedListener = OnAbilitySelectedListener(pokemon)
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

        hSpinner.adapter = ivAdapter
        hSpinner.setSelection(ivAdapter.getPosition(pokemon.hpIv.toString()))
        hSpinner.onItemSelectedListener = OnIvSelectedListener(1, pokemon)
        aSpinner.adapter = ivAdapter
        aSpinner.setSelection(ivAdapter.getPosition(pokemon.attackIv.toString()))
        aSpinner.onItemSelectedListener = OnIvSelectedListener(2, pokemon)
        bSpinner.adapter = ivAdapter
        bSpinner.setSelection(ivAdapter.getPosition(pokemon.defenseIv.toString()))
        bSpinner.onItemSelectedListener = OnIvSelectedListener(3, pokemon)
        cSpinner.adapter = ivAdapter
        cSpinner.setSelection(ivAdapter.getPosition(pokemon.specialAttackIv.toString()))
        cSpinner.onItemSelectedListener = OnIvSelectedListener(4, pokemon)
        dSpinner.adapter = ivAdapter
        dSpinner.setSelection(ivAdapter.getPosition(pokemon.specialDefenseIv.toString()))
        dSpinner.onItemSelectedListener = OnIvSelectedListener(5, pokemon)
        sSpinner.adapter = ivAdapter
        sSpinner.setSelection(ivAdapter.getPosition(pokemon.speedIv.toString()))
        sSpinner.onItemSelectedListener = OnIvSelectedListener(6, pokemon)

        hEdit.inputType = InputType.TYPE_CLASS_NUMBER
        hEdit.setText(pokemon.hpEv.toString())
        hEdit.addTextChangedListener(OnEditText(1, pokemon))
        aEdit.inputType = InputType.TYPE_CLASS_NUMBER
        aEdit.setText(pokemon.attackEv.toString())
        aEdit.addTextChangedListener(OnEditText(2, pokemon))
        bEdit.inputType = InputType.TYPE_CLASS_NUMBER
        bEdit.setText(pokemon.defenseEv.toString())
        bEdit.addTextChangedListener(OnEditText(3, pokemon))
        cEdit.inputType = InputType.TYPE_CLASS_NUMBER
        cEdit.setText(pokemon.specialAttackEv.toString())
        cEdit.addTextChangedListener(OnEditText(4, pokemon))
        dEdit.inputType = InputType.TYPE_CLASS_NUMBER
        dEdit.setText(pokemon.specialDefenseEv.toString())
        dEdit.addTextChangedListener(OnEditText(5, pokemon))
        sEdit.inputType = InputType.TYPE_CLASS_NUMBER
        sEdit.setText(pokemon.speedEv.toString())
        sEdit.addTextChangedListener(OnEditText(6, pokemon))
    }

    inner class OnItemSelectedListener(val pokemon: IndividualPokemon) : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            databaseHelper.begin()
            pokemon.item = item[position]
            databaseHelper.commit()
        }
    }

    inner class OnAbilitySelectedListener(val pokemon: IndividualPokemon) : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            databaseHelper.begin()
            pokemon.ability = pokemon.abilities[position]
            databaseHelper.commit()
        }
    }

    inner class OnCharSelectedListener(val pokemon: IndividualPokemon) : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            databaseHelper.begin()
            pokemon.characteristic = PokemonCharacteristic.CHARACTERISTIC.toMutableList()[position]
            databaseHelper.commit()
        }
    }

    inner class OnSkillSelectedListener(val index: Int, val pokemon: IndividualPokemon) : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            databaseHelper.begin()
            when (index) {
                1 -> pokemon.skillNo1 = skill[position]
                2 -> pokemon.skillNo2 = skill[position]
                3 -> pokemon.skillNo3 = skill[position]
                4 -> pokemon.skillNo4 = skill[position]
            }
            databaseHelper.commit()
        }
    }

    inner class OnIvSelectedListener(val index: Int, val pokemon: IndividualPokemon) : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            databaseHelper.begin()
            when (index) {
                1 -> pokemon.hpIv = iv[position].toInt()
                2 -> pokemon.attackIv = iv[position].toInt()
                3 -> pokemon.defenseIv = iv[position].toInt()
                4 -> pokemon.specialAttackIv = iv[position].toInt()
                5 -> pokemon.specialDefenseIv = iv[position].toInt()
                6 -> pokemon.speedIv = iv[position].toInt()
            }
            Log.v(pokemon.master.jname, "${pokemon.calcHp(MegaPokemonMasterData.NOT_MEGA)}")
            databaseHelper.commit()
        }
    }

    inner class OnEditText(val type: Int, val pokemon: IndividualPokemon) : TextWatcher {
        override fun afterTextChanged(s: Editable?) {

            val ev = s.toString()
            if (ev.isEmpty()) {
                Snackbar.make(member1_text, "未入力が存在します", Snackbar.LENGTH_SHORT).show()
                return
            }
            try {
                val value = Integer.parseInt(ev)
                databaseHelper.begin()
                when (type) {
                    1 -> pokemon.hpEv = value
                    2 -> pokemon.attackEv = value
                    3 -> pokemon.defenseEv = value
                    4 -> pokemon.specialAttackEv = value
                    5 -> pokemon.specialDefenseEv = value
                    6 -> pokemon.speedEv = value
                }
                databaseHelper.commit()
                Log.v(pokemon.master.jname, "${pokemon.calcHp(MegaPokemonMasterData.NOT_MEGA)}")

            } catch (e: NumberFormatException) {
                Snackbar.make(member1_text, "数値として不正な値です", Snackbar.LENGTH_SHORT).show()
                return
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }
    }
}
