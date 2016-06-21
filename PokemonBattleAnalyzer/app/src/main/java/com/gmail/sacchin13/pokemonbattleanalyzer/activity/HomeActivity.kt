package com.gmail.sacchin13.pokemonbattleanalyzer.activity

import android.content.Intent
import android.content.SharedPreferences
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
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.IndividualPBAPokemon
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.PBAPokemon
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Party
import com.gmail.sacchin13.pokemonbattleanalyzer.insert.*
import com.gmail.sacchin13.pokemonbattleanalyzer.interfaces.AddToListInterface
import com.gmail.sacchin13.pokemonbattleanalyzer.listener.OnClickFromParty
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.content_home.*
import kotlinx.android.synthetic.main.app_bar_home.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

import kotlin.properties.Delegates

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, AddToListInterface {
    val SELECT_ACTIVITY_CODE = 0
    val AFFINITY_ACTIVITY_CODE = 1
    val EDIT_ACTIVITY_CODE = 0

    var buttonEnable: Boolean = true
    var serviceStatePreferences: SharedPreferences by Delegates.notNull()

    val executorService: ExecutorService = Executors.newCachedThreadPool()

    //var databaseHelper: PartyDatabaseHelper? = null
    var databaseHelper: DatabaseHelper by Delegates.notNull()

    var party: Party by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        fab!!.setOnClickListener { createOpponentParty() }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout!!.addDrawerListener(toggle)
        toggle.syncState()

        nav_view!!.setNavigationItemSelectedListener(this)

        serviceStatePreferences = getSharedPreferences("pokemon", MODE_PRIVATE);

//        tool_bar.setTitle("Pokemon Battle Tool")
//        tool_bar.setTitleTextColor(Color.WHITE)
//        setSupportActionBar(tool_bar)
//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
//        supportActionBar!!.setHomeButtonEnabled(true)

        databaseHelper = DatabaseHelper(this)
//        executorService.execute(
//                PokemonRankingDownloader(databaseHelper));
        firstLaunch();
//        buttonEnable = serviceStatePreferences.getBoolean("enable", true);

        party = Party("", "")


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


    @SuppressWarnings("StatementWithEmptyBody")
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.nav_camera) {
        } else if (id == R.id.nav_gallery) {
            for(temp in databaseHelper!!.selectAllPBAPokemon()){
                Log.v("test", temp.masterRecord.no + "," + temp.masterRecord.jname)
            }

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        //        when (id) {
//            R.id.action_settings -> {
//                val serviceStatePreferences = getSharedPreferences("pokemon", MODE_PRIVATE)
//                val editor = serviceStatePreferences!!.edit()
//                editor.putBoolean("enable", !serviceStatePreferences.getBoolean("enable", true));
//                editor.apply();
//            }
//            R.id.action_modify -> startEditActivity()
//            android.R.id.home -> finish();
//            else -> result = super.onOptionsItemSelected(item)
//        }


        drawer_layout!!.closeDrawer(GravityCompat.START)
        return true
    }

    public override fun onResume() {
        super.onResume()
        //party.clear()
        //partyLayout.removeAllViews()
        createPokemonList()
    }

    fun showStatus(){
        Snackbar.make(fab, "OK", Snackbar.LENGTH_SHORT).show()
    }

    fun firstLaunch() {
        if(serviceStatePreferences!!.getBoolean("isFirst", true)){

            PokemonInsertHandler(databaseHelper).run()
            ItemInsertHandler(databaseHelper).run()
            SkillInsertHandler(databaseHelper).run()
            MegaPokemonInsertHandler(databaseHelper).run()

            val editor = serviceStatePreferences!!.edit()
            editor.putBoolean("isFirst", false);
            editor.apply();
            Log.i("This is First Time", "create table!");
        }
    }

    fun startEditActivity() {
//        val intent = Intent(MainActivity().javaClass, EditActivity().javaClass)
//        startActivityForResult(intent, EDIT_ACTIVITY_CODE);
    }

    fun startSelectActivity() {
        val intent = Intent(this, SelectActivity().javaClass)
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
        val list = databaseHelper.selectAllPBAPokemon()
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

    override fun removePokemonFromList(pokemon: PBAPokemon) {
        //throw UnsupportedOperationException()
    }

    override fun addPokemonToList(pokemon: PBAPokemon) {
        val ip = IndividualPBAPokemon(pokemon)
        val index = party.addMember(ip)
        if (index == -1) Snackbar.make(partyLayout, "すでに6体選択しています。", Snackbar.LENGTH_SHORT).show()
        else {
            val temp = Util.createImage(pokemon, 120f, resources)
            val localView = ImageView(this)
            localView.setImageBitmap(temp)
            localView.setOnClickListener(OnClickFromParty(this, ip))

            partyLayout.addView(localView)
        }
    }

    fun createOpponentParty() {
        if (party.member.size < 1) {
            Snackbar.make(partyLayout, "ポケモンが選択されていません。", Snackbar.LENGTH_SHORT).show()
            return
        }

        PartyInsertHandler(databaseHelper, party, false)
        startSelectActivity()
    }

    fun createMyParty() {
//        if (party.member == null || party.member!!.size < 1) {
//            Snackbar.make(partyLayout, "ポケモンが選択されていません。", Snackbar.LENGTH_SHORT).show()
//
//            return
//        }
//        party.time = Timestamp(System.currentTimeMillis())
//        party.userName = "mine"
//        executorService.execute(PartyInsertHandler(databaseHelper, party, false))
//        Snackbar.make(partyLayout, "登録しました。", Snackbar.LENGTH_SHORT).show()
    }

//    fun showAffinity() {
//        if (party.member == null || party.member!!.size < 1) {
//            Snackbar.make(partyLayout, "ポケモンが選択されていません。", Snackbar.LENGTH_SHORT).show()
//            return
//        }
//        (getActivity() as MainActivity).startAffinityActivity(party.member!!.get(0).master)
//    }


}
