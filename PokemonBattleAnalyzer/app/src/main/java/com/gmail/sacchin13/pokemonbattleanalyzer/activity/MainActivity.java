package com.gmail.sacchin13.pokemonbattleanalyzer.activity;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.gmail.sacchin13.pokemonbattleanalyzer.PartyDatabaseHelper;
import com.gmail.sacchin13.pokemonbattleanalyzer.R;
import com.gmail.sacchin13.pokemonbattleanalyzer.http.PokemonRankingDownloader;

public class MainActivity extends AppCompatActivity {
    private static final int SELECT_ACTIVITY_CODE = 0;
    private static final int AFFINITY_ACTIVITY_CODE = 1;
    private static final int EDIT_ACTIVITY_CODE = 0;

    public boolean buttonEnable = true;
    private SharedPreferences serviceStatePreferences;

    protected ExecutorService executorService = Executors.newCachedThreadPool();

    private PartyDatabaseHelper databaseHelper = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        serviceStatePreferences = getSharedPreferences("pokemon", MODE_PRIVATE);

        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        //transaction.add(R.id.FragmentContainer, MainFragment.newInstance(0));
        transaction.commit();

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle("Pokemon Battle Tool");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        databaseHelper = new PartyDatabaseHelper(this);
        executorService.execute(
                new PokemonRankingDownloader(databaseHelper));
        firstLaunch();
        buttonEnable = serviceStatePreferences.getBoolean("enable", true);
    }

    private void firstLaunch() {
        if(serviceStatePreferences.getBoolean("isFirst", true)){
//            executorService.execute(
//                    new PokemonInsertHandler(databaseHelper));
//            executorService.execute(
//                    new ItemInsertHandler(databaseHelper));
//            executorService.execute(
//                    new SkillInsertHandler(databaseHelper));
//                executorService.execute(new PartyInsertHandler(databaseHelper, null, true, this));
//                executorService.execute(new IndividualPokemonInsertHandler(databaseHelper, this));
//            executorService.execute(
//                    new MegaPokemonInsertHandler(databaseHelper));

            Editor editor = serviceStatePreferences.edit();
            editor.putBoolean("isFirst", false);
            editor.apply();
            Log.i("This is First Time", "create table!");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        boolean result = true;
        switch (id) {
            case R.id.action_settings:
                SharedPreferences serviceStatePreferences = getSharedPreferences("pokemon", MODE_PRIVATE);
                Editor editor = serviceStatePreferences.edit();
                editor.putBoolean("enable", !serviceStatePreferences.getBoolean("enable", true));
                editor.apply();
                break;
            case R.id.action_modify:
                startEditActivity();
                break;
            case android.R.id.home:
                finish();
                break;
            default:
                result = super.onOptionsItemSelected(item);
        }

        return result;
    }

    public void startEditActivity() {
        Intent intent = new Intent(MainActivity.this, EditActivity.class);
        startActivityForResult(intent, EDIT_ACTIVITY_CODE);
    }

    public void startSelectActivity() {
        Intent intent = new Intent(MainActivity.this, SelectActivity.class);
        startActivityForResult(intent, SELECT_ACTIVITY_CODE);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SELECT_ACTIVITY_CODE) {

//        }else if (requestCode == AFFINITY_ACTIVITY_CODE) {
//            partyLayout.removeAllViews();
//            party.clear();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
