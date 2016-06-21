package com.gmail.sacchin13.pokemonbattleanalyzer.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.gmail.sacchin13.pokemonbattleanalyzer.R;

public class ToolActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
		getWindow().setExitTransition(new Explode());

		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        //transaction.add(R.id.FragmentContainer, ToolFragment.newInstance(0));
        transaction.commit();

		Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
		toolbar.setTitle("Pokemon Battle Tool");
		toolbar.setTitleTextColor(Color.WHITE);
		setSupportActionBar(toolbar);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	protected void onStop() {
        super.onStop();
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
				Intent intent = new Intent(this, DetailActivity.class);
				startActivity(intent);
				break;
			case android.R.id.home:
				finish();
				break;
			default:
				result = super.onOptionsItemSelected(item);
		}

		return result;
	}
}
