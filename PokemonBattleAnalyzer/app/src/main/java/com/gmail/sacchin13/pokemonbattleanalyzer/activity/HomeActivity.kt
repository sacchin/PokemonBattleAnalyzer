package com.gmail.sacchin13.pokemonbattleanalyzer.activity

import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import android.widget.*
import com.gmail.sacchin13.pokemonbattleanalyzer.DatabaseHelper
import com.gmail.sacchin13.pokemonbattleanalyzer.GridAdapter
import com.gmail.sacchin13.pokemonbattleanalyzer.R
import com.gmail.sacchin13.pokemonbattleanalyzer.Util
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Party
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.PokemonMasterData
import com.gmail.sacchin13.pokemonbattleanalyzer.insert.*
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.content_home.*
import kotlinx.android.synthetic.main.app_bar_home.*

import kotlin.properties.Delegates

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    val SELECT_ACTIVITY_CODE = 0
    val AFFINITY_ACTIVITY_CODE = 1
    val EDIT_ACTIVITY_CODE = 2
    val GRAPH_ACTIVITY_CODE = 3

    var serviceStatePreferences: SharedPreferences by Delegates.notNull()

    var databaseHelper: DatabaseHelper by Delegates.notNull()

    var party: Party by Delegates.notNull()

    var util: Util by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        if (checkSelfPermission("android.permission.INTERNET") != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf("android.permission.INTERNET", "android.permission.ACCESS_NETWORK_STATE"), 1)
        }

        util = Util()

        fab!!.setOnClickListener { createOpponentParty() }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout!!.addDrawerListener(toggle)
        toggle.syncState()

        nav_view!!.setNavigationItemSelectedListener(this)

        serviceStatePreferences = getSharedPreferences("pokemon", MODE_PRIVATE)

        databaseHelper = DatabaseHelper(this)
        firstLaunch();

        party = Party(System.currentTimeMillis(), "opponent", "opponent")

//        val createMyParty = Button(getActivity())
//        createMyParty.text = "My Party"
//        createMyParty.textSize = 10f
//        createMyParty.setOnClickListener(OnClickCreateNewPartyButton(this, true))
//
//        val showAffinity = Button(getActivity())
//        showAffinity.text = "Show Affinity Complete"
//        showAffinity.textSize = 10f
//        showAffinity.setOnClickListener(OnClickCheckAffinityButton(this))
//
//        rootView.findViewById(R.id.button).addView(createMyParty)
//        rootView.findViewById(R.id.button).addView(showAffinity)

    }

    override fun onBackPressed() {
        if (drawer_layout!!.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (1 == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Snackbar.make(partyLayout, "OK", Snackbar.LENGTH_SHORT).show()
            } else {
                Snackbar.make(partyLayout, "NG。", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.nav_save) {
            createMyParty()
        } else if (id == R.id.nav_edit) {
            startEditActivity()
        } else if (id == R.id.nav_clear) {
            party.clear()
            partyLayout.removeAllViews()
            party.userName = "none"
        } else if (id == R.id.nav_graph) {
            startGraphActivity()
        }

        return true
    }

    public override fun onResume() {
        super.onResume()
        createPokemonList()
    }

    fun firstLaunch() {
        if(serviceStatePreferences.getBoolean("isFirst", true)){

            PokemonInsertHandler(databaseHelper).run()
            ItemInsertHandler(databaseHelper).run()
            SkillInsertHandler(databaseHelper).run()
            MegaPokemonInsertHandler(databaseHelper).run()

            val editor = serviceStatePreferences.edit()
            editor.putBoolean("isFirst", false)
            editor.apply()
            Log.i("This is First Time", "create table!")
        }
    }

    fun startEditActivity() {
        val intent = Intent(this, EditActivity().javaClass)
        startActivityForResult(intent, EDIT_ACTIVITY_CODE)
    }

    fun startGraphActivity() {
        if (party.member.size < 2) {
            Snackbar.make(partyLayout, "ポケモンが選択されていません。", Snackbar.LENGTH_SHORT).show()
            return
        }

        PartyInsertHandler(databaseHelper, party, false).run()

        val intent = Intent(this, GraphActivity().javaClass)
        startActivityForResult(intent, GRAPH_ACTIVITY_CODE)
    }

    fun startSelectActivity() {
        val intent = Intent(this, SelectActivity().javaClass)
        intent.putExtra("member1", party.member[0].no)
        intent.putExtra("member2", party.member[1].no)
        intent.putExtra("member3", party.member[2].no)
        intent.putExtra("member4", party.member[3].no)
        intent.putExtra("member5", party.member[4].no)
        intent.putExtra("member6", party.member[5].no)
        startActivityForResult(intent, SELECT_ACTIVITY_CODE)
    }


    //    public void startAffinityActivity(PBAPokemon selected) {
//        if(selected == null){
//            Toast.makeText(this, "エラーが発生しました。", Toast.LENGTH_SHORT).show();
//            return;
//        }
//        Intent intent = new Intent(MainActivity.this, AffinityComplementActivity.class);
//        intent.putExtra("selected",selected.getNo());
//        startActivityForResult(intent, AFFINITY_ACTIVITY_CODE);
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == SELECT_ACTIVITY_CODE) {

//        }else if (requestCode == AFFINITY_ACTIVITY_CODE) {
//            partyLayout.removeAllViews();
//            party.clear();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    fun createPokemonList() {
        val list = databaseHelper.selectAllPokemonMasterData()
        val adapter = GridAdapter(this, list)
        gridView.adapter = adapter
    }

//    fun createFrameLayout(p: PBAPokemon, countMap: Map<String, Int>): FrameLayout {
//        val fl = FrameLayout(this)
//        fl.setOnClickListener(OnClickFromList(this, p))
//
//        val pokemonImage = Util.createImage(p, 200f, resources)
//        val pokemonView = ImageView(this)
//        pokemonView.setImageBitmap(pokemonImage)
//        fl.addView(pokemonView)
//
//        val tv = TextView(this)
//        val c = countMap[p.rowId.toString()]
//        if (c != null) {
//            tv.text = c.toString()
//        } else {
//            tv.text = "0"
//        }
//        fl.addView(tv)
//        return fl
//    }

    fun removePokemonFromList(pokemon: PokemonMasterData) {
        //throw UnsupportedOperationException()
    }

    fun addPokemonToList(pokemon: PokemonMasterData, image: Bitmap) {
        val index = party.addMember(pokemon)
        if (index == -1) Snackbar.make(partyLayout, "すでに6体選択しています。", Snackbar.LENGTH_SHORT).show()
        else {
            val localView = ImageView(this)
            localView.setImageBitmap(image)
            localView.setOnClickListener{ removePokemonFromList(pokemon) }

            partyLayout.addView(localView)
        }
    }

    fun createOpponentParty() {
        if (party.member.size < 1) {
            Snackbar.make(partyLayout, "ポケモンが選択されていません。", Snackbar.LENGTH_SHORT).show()
            return
        }

        PartyInsertHandler(databaseHelper, party, false).run()
        startSelectActivity()
    }

    fun createMyParty() {
        if (party.member.size < 1) {
            Snackbar.make(partyLayout, "ポケモンが選択されていません。", Snackbar.LENGTH_SHORT).show()
            return
        }
        party.userName = "mine"

        PartyInsertHandler(databaseHelper, party, false).run()
        Snackbar.make(partyLayout, "登録しました。", Snackbar.LENGTH_SHORT).show()
    }

//    fun showAffinity() {
//        if (party.member == null || party.member!!.size < 1) {
//            Snackbar.make(partyLayout, "ポケモンが選択されていません。", Snackbar.LENGTH_SHORT).show()
//            return
//        }
//        (getActivity() as MainActivity).startAffinityActivity(party.member!!.get(0).master)
//    }


}
