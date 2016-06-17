package com.gmail.sacchin13.pokemonbattleanalyzer.listener;

import com.gmail.sacchin13.pokemonbattleanalyzer.fragment.MainFragment;

import android.view.View;
import android.view.View.OnClickListener;

public class OnClickCreateNewPartyButton implements OnClickListener{
	private MainFragment fragment = null;
	private boolean mine;

	public OnClickCreateNewPartyButton(MainFragment fragment, boolean mine){
		this.fragment = fragment;
		this.mine = mine;
	}

	@Override
	public void onClick(View v) {
		if(mine){
			fragment.createMyParty();
		}else{
			fragment.createOpponentParty();
		}
	}
}
