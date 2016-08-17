package com.gmail.sacchin13.pokemonbattleanalyzer.listener;

import com.gmail.sacchin13.pokemonbattleanalyzer.activity.HomeActivity;

import android.view.View;
import android.view.View.OnClickListener;

public class OnClickCreateNewPartyButton implements OnClickListener{
	private HomeActivity activity = null;
	private boolean mine;

	public OnClickCreateNewPartyButton(HomeActivity activity, boolean mine){
		this.activity = activity;
		this.mine = mine;
	}

	@Override
	public void onClick(View v) {
		if(mine){
			activity.createMyParty();
		}else{
			activity.createOpponentParty();
		}
	}
}
