package com.gmail.sacchin13.pokemonbattleanalyzer

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.gmail.sacchin13.pokemonbattleanalyzer.insert.ItemInsertHandler
import com.gmail.sacchin13.pokemonbattleanalyzer.insert.MegaPokemonInsertHandler
import com.gmail.sacchin13.pokemonbattleanalyzer.insert.PokemonInsertHandler
import com.gmail.sacchin13.pokemonbattleanalyzer.insert.SkillInsertHandler
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_home.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

import io.realm.Realm;
import io.realm.RealmConfiguration

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    val SELECT_ACTIVITY_CODE = 0
    val AFFINITY_ACTIVITY_CODE = 1
    val EDIT_ACTIVITY_CODE = 0

    var buttonEnable: Boolean = true
    var serviceStatePreferences: SharedPreferences? = null

    val executorService: ExecutorService = Executors.newCachedThreadPool()

    //var databaseHelper: PartyDatabaseHelper? = null
    var databaseHelper: DatabaseHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)

        fab!!.setOnClickListener { view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show() }

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
        // Handle navigation view item clicks here.
        val id = item.itemId

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {
            for(temp in databaseHelper!!.selectAllPBAPokemon()){
                Log.v("test", temp.masterRecord.no + "," + temp.masterRecord.jname)
            }

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

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


    fun firstLaunch() {
        if(serviceStatePreferences!!.getBoolean("isFirst", true)){
            val temp = PokemonInsertHandler(databaseHelper)
            temp.run()

//            executorService.execute(
//                    ItemInsertHandler(databaseHelper));
//            executorService.execute(
//                    SkillInsertHandler(databaseHelper));
//            executorService.execute(
//                    MegaPokemonInsertHandler(databaseHelper));

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
//        val intent = Intent(MainActivity().javaClass, SelectActivity.class)
//        startActivityForResult(intent, SELECT_ACTIVITY_CODE)
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
}
