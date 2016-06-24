package com.gmail.sacchin13.pokemonbattleanalyzer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.gmail.sacchin13.pokemonbattleanalyzer.activity.HomeActivity
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.PokemonMasterData
import org.jetbrains.anko.onClick
import kotlin.properties.Delegates

class GridAdapter(context: HomeActivity, list: MutableList<PokemonMasterData>): BaseAdapter() {

    var mContext: HomeActivity by Delegates.notNull()
    var mLayoutInflater: LayoutInflater by Delegates.notNull()
    var pokemonArray: MutableList<PokemonMasterData> by Delegates.notNull()

    private class ViewHolder {
        var hueImageView: ImageView by Delegates.notNull()
        var hueTextView: TextView by Delegates.notNull()
    }

    init{
        mContext = context
        mLayoutInflater = LayoutInflater.from(context)
        pokemonArray = list
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        if (convertView == null) {
            val newConvertView = mLayoutInflater.inflate(R.layout.grid_content, null);
            val holder = ViewHolder()
            holder.hueImageView = newConvertView.findViewById(R.id.imageView) as ImageView
            holder.hueTextView = newConvertView.findViewById(R.id.textView) as TextView
            newConvertView.tag = holder
            val pokemonImage = Util.Companion.createImage(pokemonArray[position], 200f, mContext.resources)
            holder.hueImageView.setImageBitmap(pokemonImage)
            holder.hueTextView.text = "0"//pokemonArray[position].masterRecord.jname

            newConvertView.onClick {
                mContext.addPokemonToList(pokemonArray[position])
            }

            return newConvertView
        } else {
            val holder = convertView.tag as ViewHolder
            val pokemonImage = Util.Companion.createImage(pokemonArray[position], 200f, mContext.resources)
            holder.hueImageView.setImageBitmap(pokemonImage)
            holder.hueTextView.text = "0"//pokemonArray[position].masterRecord.jname

            convertView.onClick {
                mContext.addPokemonToList(pokemonArray[position])
            }

            return convertView
        }
    }

    override fun getItem(position: Int): Any? {
        return pokemonArray[position];
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return pokemonArray.size
    }
}