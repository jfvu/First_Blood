package com.example.jf.cardapp.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jf.cardapp.R;
import com.example.jf.cardapp.baseactivity.BaseActivity;
import com.example.jf.cardapp.entity.RefreshAggressivity;
import com.example.jf.cardapp.entity.RefreshCardType1;
import com.example.jf.cardapp.entity.RefreshConsumeresource;
import com.example.jf.cardapp.entity.RefreshCradColor;
import com.example.jf.cardapp.entity.RefreshCradSet;
import com.example.jf.cardapp.entity.RefreshCradType;
import com.example.jf.cardapp.entity.RefreshFristLetter;
import com.example.jf.cardapp.entity.RefreshToughness;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 筛选界面
 * Created by jf on 2017/2/20.
 */

public class RefreshActivity extends BaseActivity {
    @BindView(R.id.img_refresh_top_back)
    ImageView imgRefreshTopBack;
    @BindView(R.id.tv_refresh_top_title)
    TextView tvRefreshTopTitle;
    @BindView(R.id.tv_refresh_top_done)
    TextView tvRefreshTopDone;
    @BindView(R.id.img_refresh_top_refresh)
    ImageView imgRefreshTopRefresh;
    @BindView(R.id.a)
    CheckBox a;
    @BindView(R.id.b)
    CheckBox b;
    @BindView(R.id.c)
    CheckBox c;
    @BindView(R.id.d)
    CheckBox d;
    @BindView(R.id.e)
    CheckBox e;
    @BindView(R.id.f)
    CheckBox f;
    @BindView(R.id.g)
    CheckBox g;
    @BindView(R.id.h)
    CheckBox h;
    @BindView(R.id.i)
    CheckBox i;
    @BindView(R.id.j)
    CheckBox j;
    @BindView(R.id.k)
    CheckBox k;
    @BindView(R.id.l)
    CheckBox l;
    @BindView(R.id.m)
    CheckBox m;
    @BindView(R.id.n)
    CheckBox n;
    @BindView(R.id.o)
    CheckBox o;
    @BindView(R.id.p)
    CheckBox p;
    @BindView(R.id.q)
    CheckBox q;
    @BindView(R.id.r)
    CheckBox r;
    @BindView(R.id.s)
    CheckBox s;
    @BindView(R.id.t)
    CheckBox t;
    @BindView(R.id.u)
    CheckBox u;
    @BindView(R.id.v)
    CheckBox v;
    @BindView(R.id.w)
    CheckBox w;
    @BindView(R.id.x)
    CheckBox x;
    @BindView(R.id.y)
    CheckBox y;
    @BindView(R.id.z)
    CheckBox z;
    @BindView(R.id.but_red)
    CheckBox butRed;
    @BindView(R.id.but_green)
    CheckBox butGreen;
    @BindView(R.id.but_blue)
    CheckBox butBlue;
    @BindView(R.id.but_white)
    CheckBox butWhite;
    @BindView(R.id.but_black)
    CheckBox butBlack;
    @BindView(R.id.but_colourless)
    CheckBox butColourless;
    @BindView(R.id.but_multicolor)
    CheckBox butMulticolor;
    @BindView(R.id.but_other)
    CheckBox butOther;
    @BindView(R.id.but_artifact)
    CheckBox butArtifact;
    @BindView(R.id.but_creature)
    CheckBox butCreature;
    @BindView(R.id.but_enchantment)
    CheckBox butEnchantment;
    @BindView(R.id.but_instant)
    CheckBox butInstant;
    @BindView(R.id.but_land)
    CheckBox butLand;
    @BindView(R.id.but_sorcery)
    CheckBox butSorcery;
    @BindView(R.id.spinner_cardclass)
    Spinner spinnerCardclass;
    @BindView(R.id.tv_resource1)
    CheckBox tvResource1;
    @BindView(R.id.tv_resource2)
    CheckBox tvResource2;
    @BindView(R.id.tv_resource3)
    CheckBox tvResource3;
    @BindView(R.id.tv_resource4)
    CheckBox tvResource4;
    @BindView(R.id.tv_resource5)
    CheckBox tvResource5;
    @BindView(R.id.tv_resource6)
    CheckBox tvResource6;
    @BindView(R.id.tv_resource7)
    CheckBox tvResource7;
    @BindView(R.id.tv_resource8)
    CheckBox tvResource8;
    @BindView(R.id.tv_resource9)
    CheckBox tvResource9;
    @BindView(R.id.tv_resource10)
    CheckBox tvResource10;
    @BindView(R.id.tv_resource11)
    CheckBox tvResource11;
    @BindView(R.id.tv_resource12)
    CheckBox tvResource12;
    @BindView(R.id.tv_resource13)
    CheckBox tvResource13;
    @BindView(R.id.tv_resource14)
    CheckBox tvResource14;
    @BindView(R.id.tv_resource15)
    CheckBox tvResource15;
    @BindView(R.id.tv_resource16)
    CheckBox tvResource16;
    @BindView(R.id.tv_resource0)
    CheckBox tvResource0;
    @BindView(R.id.tv_aggressivity1)
    CheckBox tvAggressivity1;
    @BindView(R.id.tv_aggressivity2)
    CheckBox tvAggressivity2;
    @BindView(R.id.tv_aggressivity3)
    CheckBox tvAggressivity3;
    @BindView(R.id.tv_aggressivity4)
    CheckBox tvAggressivity4;
    @BindView(R.id.tv_aggressivity5)
    CheckBox tvAggressivity5;
    @BindView(R.id.tv_aggressivity6)
    CheckBox tvAggressivity6;
    @BindView(R.id.tv_aggressivity7)
    CheckBox tvAggressivity7;
    @BindView(R.id.tv_aggressivity8)
    CheckBox tvAggressivity8;
    @BindView(R.id.tv_aggressivity9)
    CheckBox tvAggressivity9;
    @BindView(R.id.tv_aggressivity10)
    CheckBox tvAggressivity10;
    @BindView(R.id.tv_aggressivity11)
    CheckBox tvAggressivity11;
    @BindView(R.id.tv_aggressivity12)
    CheckBox tvAggressivity12;
    @BindView(R.id.tv_aggressivity13)
    CheckBox tvAggressivity13;
    @BindView(R.id.tv_aggressivity14)
    CheckBox tvAggressivity14;
    @BindView(R.id.tv_aggressivity15)
    CheckBox tvAggressivity15;
    @BindView(R.id.tv_aggressivity0)
    CheckBox tvAggressivity0;
    @BindView(R.id.tv_toughness)
    TextView tvToughness;
    @BindView(R.id.tv_toughness1)
    CheckBox tvToughness1;
    @BindView(R.id.tv_toughness2)
    CheckBox tvToughness2;
    @BindView(R.id.tv_toughness3)
    CheckBox tvToughness3;
    @BindView(R.id.tv_toughness4)
    CheckBox tvToughness4;
    @BindView(R.id.tv_toughness5)
    CheckBox tvToughness5;
    @BindView(R.id.tv_toughness6)
    CheckBox tvToughness6;
    @BindView(R.id.tv_toughness7)
    CheckBox tvToughness7;
    @BindView(R.id.tv_toughness8)
    CheckBox tvToughness8;
    @BindView(R.id.tv_toughness9)
    CheckBox tvToughness9;
    @BindView(R.id.tv_toughness10)
    CheckBox tvToughness10;
    @BindView(R.id.tv_toughness11)
    CheckBox tvToughness11;
    @BindView(R.id.tv_toughness12)
    CheckBox tvToughness12;
    @BindView(R.id.tv_toughness13)
    CheckBox tvToughness13;
    @BindView(R.id.tv_toughness14)
    CheckBox tvToughness14;
    @BindView(R.id.tv_toughness15)
    CheckBox tvToughness15;
    @BindView(R.id.tv_toughness0)
    CheckBox tvToughness0;
    @BindView(R.id.but_mythicrare)
    CheckBox butMythicrare;
    @BindView(R.id.but_rare)
    CheckBox butRare;
    @BindView(R.id.but_uncommon)
    CheckBox butUncommon;
    @BindView(R.id.but_common)
    CheckBox butCommon;
    @BindView(R.id.but_special)
    CheckBox butSpecial;

    //spinner选项
    private String cardType[] = {"请选择", "Aether Revolt", "Alara Reborn", "Alliances", "Antiquities", "Apocalypse",
            "Arabian Nights", "Archenemy", "Avacyn Restored", "Battle for Zendikar", "Battle Royale Box Set",
            "Beatdown BoX Set", "Betrayers of Kamigawa", "Born of the Gods", "Champions of Kamigawa",
            "Chronicles", "Classic Sixth Edition", "Coldsnap", "Commander 2013 Edition", "Commander 2014",
            "Commander 2015", "Commander 2016", "Commander's Arsenal", "Conflux", "Conspiracy: Take the Crown",
            "Dark Ascension", "Darksteel", "Dissension", "Dragon's Maze", "Dragons of Tarkir", "Duel Decks Anthology,Divine vs. Demonic",
            "Duel Decks Anthology, Elves vs.Goblins", "Duel Decks Anthology,Garruk vs.Liliana", "Duel Decks Anthology,Jace vs.Chandra",
            "Duel Decks:Ajani vs.Nicol Bolas", "Duel Decks:Blessed vs.Cursed", "Duel Decks:Divine vs.Demonic",
            "Duel Decks:Elspeth vs.Kiora", "Duel Decks:Elspeth vs.Tezzeret", "Duel Decks:Elves vs.Goblins",
            "Duel Decks:Garruk vs.Liliana", "Duel Decks:Heroes vs.Monsters", "Duel Decks:Lzzet vs.Golgari", "Duel Decks:Jace vs.Chandra",
            "Duel Decks:Jace vs.Vraska", "Duel Decks:Knights vs.Dragons", "Duel Decks:Nissa vs.Ob Nixilis", "Duel Decks:Phyrexia vs.the Coalition",
            "Duel Decks:Sorin vs.Tibalt", "Duel Decks:Speed vs.Cunning", "Duel Decks:Venser vs.Koth", "Duel Decks:Zendikar vs.Eldrazi",
            "Eighth Edition", "Eldritch Moon", "Eternal Masters", "Eventide", "Exodus", "Fallen Empires",
            "Fate Reforged", "Fifth Dawn", "Fifth Edition", "Fourth Edition", "From the Vault:Angels",
            "From the Vault:Annihilation(2014)", "From the Vault:Dragons", "From the Vault:Exiled", "From the Vault:Legends",
            "From the Vault:Lore", "From the Vault:Realms", "From the Vault:Relics", "From the Vault:Twenty", "Future Sight", "Gatecrash", "Guildpact",
            "Homelands", "Ice Age", "Innistrad", "Invasion", "Journey Into Nyx", "Judgment", "Kaladesh",
            "Khans of Tarkir", "Legends", "Legions", "Limited Edition Alpha", "Limited Edition Beta",
            "Lorwyn", "Magic 2010", "Magic 2011", "Magic 2012", "Magic 2013", "Magic 2014 Core Set", "Magic 2015 Core Set",
            "Magic Origins", "Magic:The Gathering-Commander", "Magic:The Gathering-Conspiracy", "Masterpiece Series:Kaladesh Inventions",
            "Masters Edition", "Masters Edition II", "Masters Edition III", "Masters Edition IV", "Mercadian Masques",
            "Mirage", "Mirrodin", "Mirrodin Besieged", "Modern Event Deck 2014", "Modern Masters", "Modern Masters 2015 Edition",
            "Morningtide", "Nemesis", "New Phyrexia", "Ninth Edition", "Oath of the Gatewatch", "Odyssey", "Onslaught",
            "Planar Chaos", "Planechase", "Planechase 2012 Edition", "Planechase Anthology", "Planeshift", "Portal",
            "Portal Second Age", "Portal Three Kingdoms", "Premium Deck Series Fire and Lightning",
            "Premium Deck Series Graveborn", "Premium Deck Series Slivers", "Promo Set For Gatherer", "Prophecy",
            "Ravnica:City of Guilds", "Return to Ravnica", "Revised Edition", "Rise of the Eldrazi", "Saviors of Kamigawa",
            "Scars of Mirrodin", "Scourge", "Seventh Edition", "Shadowmoor", "Shadows over Innistrad", "Shards of Alara",
            "Starter 1999", "Starter 2000", "Stronghold", "Tempest", "Tempest Remastered", "Tenth Edition",
            "The Dark", "Theros", "Time Spiral", "Timespiral Timeshifted", "Torment", "Ugins Fate Promos",
            "Unglued", "Unhinged", "Unhinged Edition", "Urzas Destiny", "Urzas Legacy", "Urzas Saga", "Vanguard", "Vintage Masters",
            "Visions", "Weatherlight", "Welcome Deck 2016", "Worldwake", "Zendikar", "Zendikar Expeditions"};

    private RefreshAggressivity aggressivity;//攻击力
    private RefreshConsumeresource consumeresource;//资源
    private RefreshCradColor cradColor;//卡牌颜色
    private RefreshCradSet cradSet;//卡牌系列
    private RefreshCradType cradType;//卡牌类型
    private RefreshFristLetter fristLetter;//卡牌首字母
    private RefreshToughness toughness;//防御值
    private RefreshCardType1 cardType1;

    private ArrayList<String> selectedF = new ArrayList<>();//已选首字母
    private ArrayList<String> selectedC = new ArrayList<>();//已选颜色
    private ArrayList<String> selectedT = new ArrayList<>();//已选类型
    private ArrayList<String> selectedS = new ArrayList<>();//已选系列
    private ArrayList<String> selectedCo = new ArrayList<>();//已选资源
    private ArrayList<String> selectedA = new ArrayList<>();//已选攻击
    private ArrayList<String> selectedTo = new ArrayList<>();//已选防御
    private ArrayList<String> selectedTy = new ArrayList<>();//已选类型2

    @Override
    protected int getLayoutId() {
        return R.layout.activity_refresh;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
        spinnerCardclass.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cardType));
        spinnerCardclass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                selectedS.add(cardType[position]);
                Log.e("seshi", selectedS.toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @OnClick({R.id.img_refresh_top_back, R.id.tv_refresh_top_done, R.id.img_refresh_top_refresh, R.id.a, R.id.b, R.id.c, R.id.d, R.id.e, R.id.f, R.id.g, R.id.h, R.id.i, R.id.j, R.id.k, R.id.l, R.id.m, R.id.n, R.id.o, R.id.p, R.id.q, R.id.r, R.id.s, R.id.t, R.id.u, R.id.v, R.id.w, R.id.x, R.id.y, R.id.z, R.id.but_red, R.id.but_green, R.id.but_blue, R.id.but_white, R.id.but_black, R.id.but_colourless, R.id.but_multicolor, R.id.but_other, R.id.but_artifact, R.id.but_creature, R.id.but_enchantment, R.id.but_instant, R.id.but_land, R.id.but_sorcery, R.id.tv_resource1, R.id.tv_resource2, R.id.tv_resource3, R.id.tv_resource4, R.id.tv_resource5, R.id.tv_resource6, R.id.tv_resource7, R.id.tv_resource8, R.id.tv_resource9, R.id.tv_resource10, R.id.tv_resource11, R.id.tv_resource12, R.id.tv_resource13, R.id.tv_resource14, R.id.tv_resource15, R.id.tv_resource16, R.id.tv_resource0, R.id.tv_aggressivity1, R.id.tv_aggressivity2, R.id.tv_aggressivity3, R.id.tv_aggressivity4, R.id.tv_aggressivity5, R.id.tv_aggressivity6, R.id.tv_aggressivity7, R.id.tv_aggressivity8, R.id.tv_aggressivity9, R.id.tv_aggressivity10, R.id.tv_aggressivity11, R.id.tv_aggressivity12, R.id.tv_aggressivity13, R.id.tv_aggressivity14, R.id.tv_aggressivity15, R.id.tv_aggressivity0, R.id.tv_toughness, R.id.tv_toughness1, R.id.tv_toughness2, R.id.tv_toughness3, R.id.tv_toughness4, R.id.tv_toughness5, R.id.tv_toughness6, R.id.tv_toughness7, R.id.tv_toughness8, R.id.tv_toughness9, R.id.tv_toughness10, R.id.tv_toughness11, R.id.tv_toughness12, R.id.tv_toughness13, R.id.tv_toughness14, R.id.tv_toughness15, R.id.tv_toughness0,R.id.but_mythicrare, R.id.but_rare, R.id.but_uncommon, R.id.but_common, R.id.but_special})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_refresh_top_back:

                finish();
                break;
            case R.id.tv_refresh_top_done:
                SharedPreferences sharedPreferences = getSharedPreferences("refresh", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (selectedF.size() != 0) {
                    for (int i = 0; i < selectedF.size(); i++) {
                        editor.putString("selectedF" + i, selectedF.get(i));
                    }
                }
                if (selectedC.size() != 0) {
                    for (int i = 0; i < selectedC.size(); i++) {
                        editor.putString("selectedC" + i, selectedC.get(i));
                    }
                }
                if (selectedT.size() != 0) {
                    for (int i = 0; i < selectedT.size(); i++) {
                        editor.putString("selectedT" + i, selectedT.get(i));
                    }
                }

                if (selectedS.size() != 1) {
                    editor.putString("selectedS", selectedS.get(1));
                }


                if (selectedCo.size() != 0) {
                    for (int i = 0; i < selectedCo.size(); i++) {
                        editor.putString("selectedCo" + i, "资源" + selectedCo.get(i));
                    }
                }
                if (selectedA.size() != 0) {
                    for (int i = 0; i < selectedA.size(); i++) {
                        editor.putString("selectedA" + i, "攻击力" + selectedA.get(i));
                    }
                }
                if (selectedTo.size() != 0) {
                    for (int i = 0; i < selectedTo.size(); i++) {
                        editor.putString("selectedTo" + i, "防御" + selectedTo.get(i));
                    }
                }
                if(selectedTy.size() !=0){
                    for (int i = 0;i<selectedTy.size();i++){
                        editor.putString("selectedTy" +i,selectedTy.get(i));
                    }
                }
                editor.commit();
                Log.e("seshi", selectedF.toString());
                selectedF.clear();
                selectedC.clear();
                selectedT.clear();
                selectedS.clear();
                selectedCo.clear();
                selectedA.clear();
                selectedTo.clear();
                selectedTy.clear();
                finish();
                break;
            case R.id.img_refresh_top_refresh:
                a.setChecked(false);
                b.setChecked(false);
                c.setChecked(false);
                d.setChecked(false);
                e.setChecked(false);
                f.setChecked(false);
                g.setChecked(false);
                h.setChecked(false);
                i.setChecked(false);
                j.setChecked(false);
                k.setChecked(false);
                l.setChecked(false);
                m.setChecked(false);
                n.setChecked(false);
                o.setChecked(false);
                p.setChecked(false);
                q.setChecked(false);
                r.setChecked(false);
                s.setChecked(false);
                t.setChecked(false);
                u.setChecked(false);
                v.setChecked(false);
                w.setChecked(false);
                x.setChecked(false);
                y.setChecked(false);
                z.setChecked(false);


                butRed.setChecked(false);
                butGreen.setChecked(false);
                butBlue.setChecked(false);
                butWhite.setChecked(false);
                butBlack.setChecked(false);
                butColourless.setChecked(false);
                butMulticolor.setChecked(false);
                butOther.setChecked(false);

                butMythicrare.setChecked(false);
                butRare.setChecked(false);
                butUncommon.setChecked(false);
                butCommon.setChecked(false);
                butSpecial.setChecked(false);

                butArtifact.setChecked(false);
                butCreature.setChecked(false);
                butEnchantment.setChecked(false);
                butInstant.setChecked(false);
                butLand.setChecked(false);
                butSorcery.setChecked(false);

                spinnerCardclass.setSelection(0);

                tvResource0.setChecked(false);
                tvResource1.setChecked(false);
                tvResource2.setChecked(false);
                tvResource3.setChecked(false);
                tvResource4.setChecked(false);
                tvResource6.setChecked(false);
                tvResource5.setChecked(false);
                tvResource7.setChecked(false);
                tvResource8.setChecked(false);
                tvResource9.setChecked(false);
                tvResource10.setChecked(false);
                tvResource11.setChecked(false);
                tvResource12.setChecked(false);
                tvResource13.setChecked(false);
                tvResource14.setChecked(false);
                tvResource15.setChecked(false);
                tvResource16.setChecked(false);

                tvAggressivity0.setChecked(false);
                tvAggressivity1.setChecked(false);
                tvAggressivity2.setChecked(false);
                tvAggressivity3.setChecked(false);
                tvAggressivity4.setChecked(false);
                tvAggressivity5.setChecked(false);
                tvAggressivity6.setChecked(false);
                tvAggressivity7.setChecked(false);
                tvAggressivity8.setChecked(false);
                tvAggressivity9.setChecked(false);
                tvAggressivity10.setChecked(false);
                tvAggressivity11.setChecked(false);
                tvAggressivity12.setChecked(false);
                tvAggressivity13.setChecked(false);
                tvAggressivity14.setChecked(false);
                tvAggressivity15.setChecked(false);

                tvToughness0.setChecked(false);
                tvToughness1.setChecked(false);
                tvToughness2.setChecked(false);
                tvToughness3.setChecked(false);
                tvToughness4.setChecked(false);
                tvToughness5.setChecked(false);
                tvToughness6.setChecked(false);
                tvToughness7.setChecked(false);
                tvToughness8.setChecked(false);
                tvToughness9.setChecked(false);
                tvToughness10.setChecked(false);
                tvToughness11.setChecked(false);
                tvToughness12.setChecked(false);
                tvToughness13.setChecked(false);
                tvToughness14.setChecked(false);
                tvToughness15.setChecked(false);

                selectedT.clear();
                selectedC.clear();
                selectedT.clear();
                selectedS.clear();
                selectedCo.clear();
                selectedA.clear();
                selectedTo.clear();

                break;
            case R.id.a:
                addSelectedF(a, "A", "fristLetter");
                break;
            case R.id.b:
                addSelectedF(b, "B", "fristLetter");
                break;
            case R.id.c:
                addSelectedF(c, "C", "fristLetter");
                break;
            case R.id.d:
                addSelectedF(d, "D", "fristLetter");
                break;
            case R.id.e:
                addSelectedF(e, "E", "fristLetter");
                break;
            case R.id.f:
                addSelectedF(f, "F", "fristLetter");
                break;
            case R.id.g:
                addSelectedF(g, "G", "fristLetter");
                break;
            case R.id.h:
                addSelectedF(h, "H", "fristLetter");
                break;
            case R.id.i:
                addSelectedF(i, "I", "fristLetter");
                break;
            case R.id.j:
                addSelectedF(j, "J", "fristLetter");
                break;
            case R.id.k:
                addSelectedF(k, "K", "fristLetter");
                break;
            case R.id.l:
                addSelectedF(l, "L", "fristLetter");
                break;
            case R.id.m:
                addSelectedF(m, "M", "fristLetter");
                break;
            case R.id.n:
                addSelectedF(n, "N", "fristLetter");
                break;
            case R.id.o:
                addSelectedF(o, "O", "fristLetter");
                break;
            case R.id.p:
                addSelectedF(p, "P", "fristLetter");
                break;
            case R.id.q:
                addSelectedF(q, "Q", "fristLetter");
                break;
            case R.id.r:
                addSelectedF(r, "R", "fristLetter");
                break;
            case R.id.s:
                addSelectedF(s, "S", "fristLetter");
                break;
            case R.id.t:
                addSelectedF(t, "T", "fristLetter");
                break;
            case R.id.u:
                addSelectedF(u, "U", "fristLetter");
                break;
            case R.id.v:
                addSelectedF(v, "V", "fristLetter");
                break;
            case R.id.w:
                addSelectedF(w, "W", "fristLetter");
                break;
            case R.id.x:
                addSelectedF(x, "X", "fristLetter");
                break;
            case R.id.y:
                addSelectedF(y, "Y", "fristLetter");
                break;
            case R.id.z:
                addSelectedF(z, "Z", "fristLetter");
                break;
            case R.id.but_red:
                addSelectedC(butRed, "红", "cradcolor");
                break;
            case R.id.but_green:
                addSelectedC(butGreen, "绿", "cradcolor");
                break;
            case R.id.but_blue:
                addSelectedC(butBlue, "蓝", "cradcolor");
                break;
            case R.id.but_white:
                addSelectedC(butWhite, "白", "cradcolor");
                break;
            case R.id.but_black:
                addSelectedC(butBlack, "黑", "cradcolor");
                break;
            case R.id.but_colourless:
                addSelectedC(butColourless, "无色", "cradcolor");
                break;
            case R.id.but_multicolor:
                addSelectedC(butMulticolor, "多色", "cradcolor");
                break;
            case R.id.but_other:
                addSelectedC(butOther, "其他", "cradcolor");
                break;
            case R.id.but_artifact:
                addSelectedT(butArtifact, "神器", "cardtype");
                break;
            case R.id.but_creature:
                addSelectedT(butCreature, "生物", "cardtype");
                break;
            case R.id.but_enchantment:
                addSelectedT(butEnchantment, "魔法", "cardtype");
                break;
            case R.id.but_instant:
                addSelectedT(butInstant, "即时", "cardtype");
                break;
            case R.id.but_land:
                addSelectedT(butLand, "土地", "cardtype");
                break;
            case R.id.but_sorcery:
                addSelectedT(butSorcery, "巫术", "cardtype");
                break;
            case R.id.tv_resource1:
                addSelectedCo(tvResource1, "1", "consumeresource");
                break;
            case R.id.tv_resource2:
                addSelectedCo(tvResource2, "2", "consumeresource");
                break;
            case R.id.tv_resource3:
                addSelectedCo(tvResource3, "3", "consumeresource");
                break;
            case R.id.tv_resource4:
                addSelectedCo(tvResource4, "4", "consumeresource");
                break;
            case R.id.tv_resource5:
                addSelectedCo(tvResource5, "5", "consumeresource");
                break;
            case R.id.tv_resource6:
                addSelectedCo(tvResource6, "6", "consumeresource");
                break;
            case R.id.tv_resource7:
                addSelectedCo(tvResource7, "7", "consumeresource");
                break;
            case R.id.tv_resource8:
                addSelectedCo(tvResource8, "8", "consumeresource");
                break;
            case R.id.tv_resource9:
                addSelectedCo(tvResource9, "9", "consumeresource");
                break;
            case R.id.tv_resource10:
                addSelectedCo(tvResource10, "10", "consumeresource");
                break;
            case R.id.tv_resource11:
                addSelectedCo(tvResource11, "11", "consumeresource");
                break;
            case R.id.tv_resource12:
                addSelectedCo(tvResource12, "12", "consumeresource");
                break;
            case R.id.tv_resource13:
                addSelectedCo(tvResource13, "13", "consumeresource");
                break;
            case R.id.tv_resource14:
                addSelectedCo(tvResource14, "14", "consumeresource");
                break;
            case R.id.tv_resource15:
                addSelectedCo(tvResource15, "15", "consumeresource");
                break;
            case R.id.tv_resource16:
                addSelectedCo(tvResource16, "16", "consumeresource");
                break;
            case R.id.tv_resource0:
                addSelectedCo(tvResource0, "0", "consumeresource");
                break;
            case R.id.tv_aggressivity1:
                addSelectedA(tvAggressivity1, "1", "aggressivity");
                break;
            case R.id.tv_aggressivity2:
                addSelectedA(tvAggressivity2, "2", "aggressivity");
                break;
            case R.id.tv_aggressivity3:
                addSelectedA(tvAggressivity3, "3", "aggressivity");
                break;
            case R.id.tv_aggressivity4:
                addSelectedA(tvAggressivity4, "4", "aggressivity");
                break;
            case R.id.tv_aggressivity5:
                addSelectedA(tvAggressivity5, "5", "aggressivity");
                break;
            case R.id.tv_aggressivity6:
                addSelectedA(tvAggressivity6, "6", "aggressivity");
                break;
            case R.id.tv_aggressivity7:
                addSelectedA(tvAggressivity7, "7", "aggressivity");
                break;
            case R.id.tv_aggressivity8:
                addSelectedA(tvAggressivity8, "8", "aggressivity");
                break;
            case R.id.tv_aggressivity9:
                addSelectedA(tvAggressivity9, "9", "aggressivity");
                break;
            case R.id.tv_aggressivity10:
                addSelectedA(tvAggressivity10, "10", "aggressivity");
                break;
            case R.id.tv_aggressivity11:
                addSelectedA(tvAggressivity11, "11", "aggressivity");
                break;
            case R.id.tv_aggressivity12:
                addSelectedA(tvAggressivity12, "12", "aggressivity");
                break;
            case R.id.tv_aggressivity13:
                addSelectedA(tvAggressivity13, "13", "aggressivity");
                break;
            case R.id.tv_aggressivity14:
                addSelectedA(tvAggressivity14, "14", "aggressivity");
                break;
            case R.id.tv_aggressivity15:
                addSelectedA(tvAggressivity15, "15", "aggressivity");
                break;
            case R.id.tv_aggressivity0:
                addSelectedA(tvAggressivity0, "0", "aggressivity");
                break;
            case R.id.tv_toughness1:
                addSelectedTo(tvToughness1, "1", "toughness");
                break;
            case R.id.tv_toughness2:
                addSelectedTo(tvToughness2, "2", "toughness");
                break;
            case R.id.tv_toughness3:
                addSelectedTo(tvToughness3, "3", "toughness");
                break;
            case R.id.tv_toughness4:
                addSelectedTo(tvToughness4, "4", "toughness");
                break;
            case R.id.tv_toughness5:
                addSelectedTo(tvToughness5, "5", "toughness");
                break;
            case R.id.tv_toughness6:
                addSelectedTo(tvToughness6, "6", "toughness");
                break;
            case R.id.tv_toughness7:
                addSelectedTo(tvToughness7, "7", "toughness");
                break;
            case R.id.tv_toughness8:
                addSelectedTo(tvToughness8, "8", "toughness");
                break;
            case R.id.tv_toughness9:
                addSelectedTo(tvToughness9, "9", "toughness");
                break;
            case R.id.tv_toughness10:
                addSelectedTo(tvToughness10, "10", "toughness");
                break;
            case R.id.tv_toughness11:
                addSelectedTo(tvToughness11, "11", "toughness");
                break;
            case R.id.tv_toughness12:
                addSelectedTo(tvToughness12, "12", "toughness");
                break;
            case R.id.tv_toughness13:
                addSelectedTo(tvToughness13, "13", "toughness");
                break;
            case R.id.tv_toughness14:
                addSelectedTo(tvToughness14, "14", "toughness");
                break;
            case R.id.tv_toughness15:
                addSelectedTo(tvToughness15, "15", "toughness");
                break;
            case R.id.tv_toughness0:
                addSelectedTo(tvToughness0, "0", "toughness");
                break;
            case R.id.but_mythicrare:
                addSelectedTy(butMythicrare,"神话","cardtype1");
                break;
            case R.id.but_rare:
                addSelectedTy(butRare,"稀有","cardtype1");
                break;
            case R.id.but_uncommon:
                addSelectedTy(butUncommon,"罕见","cardtype1");
                break;
            case R.id.but_common:
                addSelectedTy(butCommon,"通用","cardtype1");
                break;
            case R.id.but_special:
                addSelectedTy(butSpecial,"特殊","cardtype1");
                break;
        }
    }

    //根据checkbox是否选中，添加到arraylist中
    private void addSelectedF(CheckBox id, String name, String type) {
        if (id.isChecked()) {
            fristLetter = new RefreshFristLetter(name, true, type);
            selectedF.add(fristLetter.getName());
        } else {
            for (int i = 0; i < selectedF.size(); i++) {
                if (selectedF.get(i).equals(name)) {
                    selectedF.remove(i);
                }
            }
        }
    }

    private void addSelectedC(CheckBox id, String name, String type) {
        if (id.isChecked()) {
            cradColor = new RefreshCradColor(name, true, type);
            selectedC.add(cradColor.getName());
        } else {
            for (int i = 0; i < selectedC.size(); i++) {
                if (selectedC.get(i).equals(name)) {
                    selectedC.remove(i);
                }
            }
        }
    }

    private void addSelectedT(CheckBox id, String name, String type) {
        if (id.isChecked()) {
            cradType = new RefreshCradType(name, true, type);
            selectedT.add(cradType.getName());
        } else {
            for (int i = 0; i < selectedT.size(); i++) {
                if (selectedT.get(i).equals(name)) {
                    selectedT.remove(i);
                }
            }
        }
    }

    private void addSelectedCo(CheckBox id, String name, String type) {
        if (id.isChecked()) {
            consumeresource = new RefreshConsumeresource(name, true, type);
            selectedCo.add(consumeresource.getName());
        } else {
            for (int i = 0; i < selectedCo.size(); i++) {
                if (selectedCo.get(i).equals(name)) {
                    selectedCo.remove(i);
                }
            }
        }
    }

    private void addSelectedA(CheckBox id, String name, String type) {
        if (id.isChecked()) {
            aggressivity = new RefreshAggressivity(name, true, type);
            selectedA.add(aggressivity.getName());
        } else {
            for (int i = 0; i < selectedA.size(); i++) {
                if (selectedA.get(i).equals(name)) {
                    selectedA.remove(i);
                }
            }
        }
    }

    private void addSelectedTo(CheckBox id, String name, String type) {
        if (id.isChecked()) {
            toughness = new RefreshToughness(name, true, type);
            selectedTo.add(toughness.getName());
        } else {
            for (int i = 0; i < selectedTo.size(); i++) {
                if (selectedTo.get(i).equals(name)) {
                    selectedTo.remove(i);
                }
            }
        }
    }
    private void addSelectedTy(CheckBox id, String name, String type) {
        if (id.isChecked()) {
            cardType1 = new RefreshCardType1(name, true, type);
            selectedTy.add(cardType1.getName());
        } else {
            for (int i = 0; i < selectedTy.size(); i++) {
                if (selectedTy.get(i).equals(name)) {
                    selectedTy.remove(i);
                }
            }
        }
    }



}
