package com.gmail.sacchin13.pokemonbattleanalyzer.listener;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.gmail.sacchin13.pokemonbattleanalyzer.activity.EditActivityFragment;
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.IndividualPBAPokemon;
import com.gmail.sacchin13.pokemonbattleanalyzer.fragment.ToolFragment;

public class OnClickSaveMyParty implements OnClickListener{
	private EditActivityFragment fragment = null;

	public OnClickSaveMyParty(EditActivityFragment fragment){
		this.fragment = fragment;
	}

	@Override
	public void onClick(View v) {
		fragment.parseAlInput();
	}
}
