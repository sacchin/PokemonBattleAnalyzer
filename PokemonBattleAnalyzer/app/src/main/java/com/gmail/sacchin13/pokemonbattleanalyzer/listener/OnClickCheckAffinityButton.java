package com.gmail.sacchin13.pokemonbattleanalyzer.listener;

import android.view.View;
import android.view.View.OnClickListener;

import com.gmail.sacchin13.pokemonbattleanalyzer.fragment.MainFragment;

public class OnClickCheckAffinityButton implements OnClickListener{
	private MainFragment fragment = null;

	public OnClickCheckAffinityButton(MainFragment fragment){
		this.fragment = fragment;
	}

	@Override
	public void onClick(View v) {
        fragment.showAffinity();
	}
}
