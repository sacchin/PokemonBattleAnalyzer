package com.gmail.sacchin13.pokemonbattleanalyzer.activity

import android.app.ActivityOptions
import android.app.FragmentManager
import android.app.FragmentTransaction
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.transition.Explode
import android.util.Log
import android.view.*
import android.widget.*

import com.gmail.sacchin13.pokemonbattleanalyzer.R
import com.gmail.sacchin13.pokemonbattleanalyzer.Util
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.IndividualPBAPokemon
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Party
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.PokemonMasterData
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.pgl.RankingResponse
import com.gmail.sacchin13.pokemonbattleanalyzer.fragment.ToolFragment
import com.gmail.sacchin13.pokemonbattleanalyzer.listener.OnClickIndividualPokemon
import kotlinx.android.synthetic.main.content_tool.*
import java.io.IOException
import kotlin.properties.Delegates

class ToolActivity : PGLActivity() {

    var util: Util by Delegates.notNull()

    init{
        util = Util()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tool)

        opponentParty = databaseHelper.selectParty("opponent")

        myParty = Party(System.currentTimeMillis(), "mine", "mine")
//        myParty.member1 = intent.extras.getString("member1", "")
//        myParty.member2 = intent.extras.getString("member2", "")
//        myParty.member3 = intent.extras.getString("member3", "")

        resetParty(true)
    }

    override fun onResume() {
        super.onResume()
        resetParty(true)
        createPartyList()
    }

    override fun onStop() {
        super.onStop()
        if (status != null) {
            status!!.removeAllViews()
            //status = null
        }
    }

    private var index = 0

    private var choices: Party? = null


    private fun createPartyList() {
        if (party == null) {
            Log.e("createPartyList", "null!")
            return
        } else {
            party!!.removeAllViews()
        }

        for (i in 0..opponentParty.member.size) {
            val p = opponentParty.member[i]
            val frame = createFrameLayout(p, 210f)
            frame.setOnClickListener{ OnClickIndividualPokemon(this, i, frame.getChildAt(0) as ImageView) }
            party.addView(frame);
        }

        if (choiced == null) {
            Log.e("choicedLayout", "null!")
            return
        } else {
            choiced!!.removeAllViews()
        }

        //        if(choiced != null){
        //            for (int i = 0; i < choiced.getMember().size(); i++) {
        //                IndividualPBAPokemon p = choiced.getMember().get(i);
        //                FrameLayout frame = createFrameLayout(p.getMaster(), 280f);
        //                frame.setOnClickListener(new OnClickChoicedPokemon(this, i));
        //                choicedLayout.addView(frame);
        //            }
        //        }
    }

    fun createFrameLayout(p: PokemonMasterData, size: Float): FrameLayout {
        val fl = FrameLayout(this)

        val temp = util.createImage(p, size, resources)
        val localView = ImageView(this)
        localView.setImageBitmap(temp)
        localView.transitionName = "image"
        fl.addView(localView)

        return fl
    }

    fun getIndividualPBAPokemon(index: Int): IndividualPBAPokemon? {
        return null//party.getMember().get(index);
    }

    fun finishAllDownload() {

    }

    fun setIndex(index: Int) {
        this.index = index
    }

    override fun setTrend(result: RankingResponse, index: Int) {

    }

    fun setAlert(index: Int) {
        //        IndividualPBAPokemon tapped = choiced.getMember().get(index);
        //        if(party != null){
        //            for (int i = 0; i < party.getMember().size(); i++) {
        //                IndividualPBAPokemon p = party.getMember().get(i);
        //                FrameLayout frame = (FrameLayout)partyLayout.getChildAt(i);
        //                if(1 < frame.getChildCount()){
        //                    frame.removeViewAt(frame.getChildCount() - 1);
        //                }
        //                Bitmap over = null;
        //                ImageView overView = new ImageView(getActivity());
        //                switch (BattleCalculator.getAttackOrder(tapped, p)){
        //                    case SAFE:
        //                        //set no frame
        //                        break;
        //                    case LGTM:
        //                        over = Util.Companion.createImage(R.drawable.safe, 210f, getResources());
        //                        break;
        //                    case FATAL:
        //                        over = Util.Companion.createImage(R.drawable.caution, 210f, getResources());
        //                        break;
        //                    default:
        //                        over = Util.Companion.createImage(R.drawable.alert, 210f, getResources());
        //                        break;
        //                }
        //                if(over != null){
        //                    overView.setImageBitmap(over);
        //                }
        //                frame.addView(overView);
        //            }
        //        }
    }

    fun createTableRow(texts: Array<String>, layoutSpan: Int, bgColor: Int, txtColor: Int, txtSize: Int): TableRow {
        val row = TableRow(this)
        val p = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT)
        if (0 < layoutSpan) {
            p.span = layoutSpan
        }
        row.layoutParams = p
        for (temp in texts) {
            if (layoutSpan <= 0) {
                row.addView(createTextView(temp, bgColor, txtColor, txtSize))
            } else {
                row.addView(createTextView(temp, bgColor, txtColor, txtSize), p)
            }
        }
        return row
    }

    fun createTextView(text: String, bgColor: Int, txtColor: Int, txtSize: Int): TextView {
        val tv = TextView(this)
        tv.text = text
        tv.setBackgroundColor(bgColor)
        tv.setTextColor(txtColor)
        tv.textSize = txtSize.toFloat()
        val p = TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT)
        tv.layoutParams = p
        return tv
    }

    fun setMainView(index: Int) {
        //        IndividualPBAPokemon tapped = choiced.getMember().get(index);
        //        selected.setImageBitmap(Util.Companion.createImage(tapped.getMaster(), 210f, getResources()));
        //
        //        String headers[] = {"", "H", "A", "B", "C", "D", "S"};
        //        status.addView(createTableRow(headers, 0, Color.TRANSPARENT, Color.GRAY, 12));
        //
        //        String base[] = {"種族", String.valueOf(tapped.getMaster().getMasterRecord().getH()), String.valueOf(tapped.getMaster().getMasterRecord().getA()), String.valueOf(tapped.getMaster().getMasterRecord().getB()),
        //                String.valueOf(tapped.getMaster().getMasterRecord().getC()), String.valueOf(tapped.getMaster().getMasterRecord().getD()), String.valueOf(tapped.getMaster().getMasterRecord().getS())};
        //        status.addView(createTableRow(base, 0, Color.TRANSPARENT, Color.BLACK, 18));
        //
        //        String effort[] = {"努力", String.valueOf(tapped.getHpEffortValue()), String.valueOf(tapped.getAttackEffortValue()),
        //                String.valueOf(tapped.getDeffenceEffortValue()), String.valueOf(tapped.getSpecialAttackEffortValue()),
        //                String.valueOf(tapped.getSpecialDeffenceEffortValue()), String.valueOf(tapped.getSpeedEffortValue())};
        //        status.addView(createTableRow(effort, 0, Color.TRANSPARENT, Color.BLACK, 18));
        //
        //        String actual[] = {"実数", String.valueOf(tapped.getHpEffortValue()), String.valueOf(tapped.getAttackValue()), String.valueOf(tapped.getDeffenceValue()),
        //                String.valueOf(tapped.getSpecialAttackValue()), String.valueOf(tapped.getSpecialDeffenceValue()), String.valueOf(tapped.getSpeedValue())};
        //        status.addView(createTableRow(actual, 0, Color.TRANSPARENT, Color.BLACK, 18));


        //        if(party != null){
        //            for (int i = 0; i < party.getMember().size(); i++) {
        //                IndividualPBAPokemon p = party.getMember().get(i);
        //                FrameLayout frame = (FrameLayout)partyLayout.getChildAt(i);
        //                if(1 < frame.getChildCount()){
        //                    frame.removeViewAt(frame.getChildCount() - 1);
        //                }
        //                Bitmap over = null;
        //                ImageView overView = new ImageView(getActivity());
        //                switch (BattleCalculator.getAttackOrder(tapped, p)){
        //                    case SAFE:
        //                        //set no frame
        //                        break;
        //                    case LGTM:
        //                        over = Util.createImage(R.drawable.safe, 210f, getResources());
        //                        break;
        //                    case FATAL:
        //                        over = Util.createImage(R.drawable.caution, 210f, getResources());
        //                        break;
        //                    default:
        //                        over = Util.createImage(R.drawable.alert, 210f, getResources());
        //                        break;
        //                }
        //                if(over != null){
        //                    overView.setImageBitmap(over);
        //                }
        //                frame.addView(overView);
        //            }
        //        }
    }

    fun startDetailActivity(pokemon: IndividualPBAPokemon, from: ImageView) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("id", pokemon.id)
        startActivity(intent,
                ActivityOptions.makeSceneTransitionAnimation(this, from, "image").toBundle())
    }
}
