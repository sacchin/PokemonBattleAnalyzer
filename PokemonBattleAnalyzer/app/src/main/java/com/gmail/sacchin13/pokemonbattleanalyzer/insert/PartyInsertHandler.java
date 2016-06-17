package com.gmail.sacchin13.pokemonbattleanalyzer.insert;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gmail.sacchin13.pokemonbattleanalyzer.PartyDatabaseHelper;
import com.gmail.sacchin13.pokemonbattleanalyzer.entity.Party;

import android.database.SQLException;
import android.util.Log;

public class PartyInsertHandler implements Runnable {

	private PartyDatabaseHelper databaseHelper;
	private Party party;
	private boolean isInit = false;

	public PartyInsertHandler(PartyDatabaseHelper databaseHelper, Party party, boolean isInit) {
		this.databaseHelper = databaseHelper;
		this.party = party;
		this.isInit = isInit;
	}

    @Override
    public void run() {
        if(isInit){
            insertPastParty();
        }else{
            insertOneParty();
        }
    }

	public void insertPastParty(){
		JSONArray party = new JSONArray();
		try {
			party.put(new JSONObject("{\"member2\":1072,\"member3\":1073,\"member4\":1074,\"time\":1396098766986,\"member5\":1075,\"memo\":\"\",\"member6\":1076,\"_id\":185,\"userName\":\"\",\"member1\":1071}"));
			party.put(new JSONObject("{\"member2\":1066,\"member3\":1067,\"member4\":1068,\"time\":1396098144457,\"member5\":1069,\"memo\":\"\",\"member6\":1070,\"_id\":184,\"userName\":\"\",\"member1\":1065}"));
			party.put(new JSONObject("{\"member2\":1060,\"member3\":1061,\"member4\":1062,\"time\":1396097688652,\"member5\":1063,\"memo\":\"\",\"member6\":1064,\"_id\":183,\"userName\":\"\",\"member1\":1059}"));
			party.put(new JSONObject("{\"member2\":1054,\"member3\":1055,\"member4\":1056,\"time\":1396097009659,\"member5\":1057,\"memo\":\"\",\"member6\":1058,\"_id\":182,\"userName\":\"\",\"member1\":1053}"));
			party.put(new JSONObject("{\"member2\":1048,\"member3\":1049,\"member4\":1050,\"time\":1396096458643,\"member5\":1051,\"memo\":\"\",\"member6\":1052,\"_id\":181,\"userName\":\"\",\"member1\":1047}"));
			party.put(new JSONObject("{\"member2\":1042,\"member3\":1043,\"member4\":1044,\"time\":1396095803361,\"member5\":1045,\"memo\":\"\",\"member6\":1046,\"_id\":180,\"userName\":\"\",\"member1\":1041}"));
			party.put(new JSONObject("{\"member2\":1036,\"member3\":1037,\"member4\":1038,\"time\":1395933607345,\"member5\":1039,\"memo\":\"\",\"member6\":1040,\"_id\":179,\"userName\":\"\",\"member1\":1035}"));
			party.put(new JSONObject("{\"member2\":1030,\"member3\":1031,\"member4\":1032,\"time\":1395933070875,\"member5\":1033,\"memo\":\"\",\"member6\":1034,\"_id\":178,\"userName\":\"\",\"member1\":1029}"));
			party.put(new JSONObject("{\"member2\":1024,\"member3\":1025,\"member4\":1026,\"time\":1395932730763,\"member5\":1027,\"memo\":\"\",\"member6\":1028,\"_id\":177,\"userName\":\"\",\"member1\":1023}"));
			party.put(new JSONObject("{\"member2\":1018,\"member3\":1019,\"member4\":1020,\"time\":1395932580952,\"member5\":1021,\"memo\":\"\",\"member6\":1022,\"_id\":176,\"userName\":\"\",\"member1\":1017}"));
			party.put(new JSONObject("{\"member2\":1012,\"member3\":1013,\"member4\":1014,\"time\":1395932136048,\"member5\":1015,\"memo\":\"\",\"member6\":1016,\"_id\":175,\"userName\":\"\",\"member1\":1011}"));
			party.put(new JSONObject("{\"member2\":1006,\"member3\":1007,\"member4\":1008,\"time\":1395680037311,\"member5\":1009,\"memo\":\"\",\"member6\":1010,\"_id\":174,\"userName\":\"\",\"member1\":1005}"));
			party.put(new JSONObject("{\"member2\":1000,\"member3\":1001,\"member4\":1002,\"time\":1395679367998,\"member5\":1003,\"memo\":\"\",\"member6\":1004,\"_id\":173,\"userName\":\"ベルゼ\",\"member1\":999}"));
			party.put(new JSONObject("{\"member2\":994,\"member3\":995,\"member4\":996,\"time\":1395678861244,\"member5\":997,\"memo\":\"\",\"member6\":998,\"_id\":172,\"userName\":\"あやややや\",\"member1\":993}"));
			party.put(new JSONObject("{\"member2\":988,\"member3\":989,\"member4\":990,\"time\":1395678478293,\"member5\":991,\"memo\":\"\",\"member6\":992,\"_id\":171,\"userName\":\"\",\"member1\":987}"));
			party.put(new JSONObject("{\"member2\":982,\"member3\":983,\"member4\":984,\"time\":1395678113047,\"member5\":985,\"memo\":\"\",\"member6\":986,\"_id\":170,\"userName\":\"ADM\",\"member1\":981}"));
			party.put(new JSONObject("{\"member2\":976,\"member3\":977,\"member4\":978,\"time\":1395677368031,\"member5\":979,\"memo\":\"\",\"member6\":980,\"_id\":169,\"userName\":\"ちゃば\",\"member1\":975}"));
			party.put(new JSONObject("{\"member2\":970,\"member3\":971,\"member4\":972,\"time\":1395676866371,\"member5\":973,\"memo\":\"\",\"member6\":974,\"_id\":168,\"userName\":\"\",\"member1\":969}"));
			party.put(new JSONObject("{\"member2\":964,\"member3\":965,\"member4\":966,\"time\":1395676067005,\"member5\":967,\"memo\":\"\",\"member6\":968,\"_id\":167,\"userName\":\"\",\"member1\":963}"));
			party.put(new JSONObject("{\"member2\":958,\"member3\":959,\"member4\":960,\"time\":1395675844927,\"member5\":961,\"memo\":\"\",\"member6\":962,\"_id\":166,\"userName\":\"\",\"member1\":957}"));
			party.put(new JSONObject("{\"member2\":952,\"member3\":953,\"member4\":954,\"time\":1395675417590,\"member5\":955,\"memo\":\"\",\"member6\":956,\"_id\":165,\"userName\":\"キキ\",\"member1\":951}"));
			party.put(new JSONObject("{\"member2\":946,\"member3\":947,\"member4\":948,\"time\":1395674797412,\"member5\":949,\"memo\":\"\",\"member6\":950,\"_id\":164,\"userName\":\"カツヤ\",\"member1\":945}"));
			party.put(new JSONObject("{\"member2\":940,\"member3\":941,\"member4\":942,\"time\":1395589468526,\"member5\":943,\"memo\":\"\",\"member6\":944,\"_id\":163,\"userName\":\"かける\",\"member1\":939}"));
			party.put(new JSONObject("{\"member2\":934,\"member3\":935,\"member4\":936,\"time\":1395589172775,\"member5\":937,\"memo\":\"\",\"member6\":938,\"_id\":162,\"userName\":\"\",\"member1\":933}"));
			party.put(new JSONObject("{\"member2\":928,\"member3\":929,\"member4\":930,\"time\":1395588363729,\"member5\":931,\"memo\":\"\",\"member6\":932,\"_id\":161,\"userName\":\"マリア\",\"member1\":927}"));
			party.put(new JSONObject("{\"member2\":922,\"member3\":923,\"member4\":924,\"time\":1395587874128,\"member5\":925,\"memo\":\"\",\"member6\":926,\"_id\":160,\"userName\":\"なつかん\",\"member1\":921}"));
			party.put(new JSONObject("{\"member2\":916,\"member3\":917,\"member4\":918,\"time\":1395587288226,\"member5\":919,\"memo\":\"\",\"member6\":920,\"_id\":159,\"userName\":\"たうこ\",\"member1\":915}"));
			party.put(new JSONObject("{\"member2\":910,\"member3\":911,\"member4\":912,\"time\":1395586804070,\"member5\":913,\"memo\":\"\",\"member6\":914,\"_id\":158,\"userName\":\"nano\",\"member1\":909}"));
			party.put(new JSONObject("{\"member2\":904,\"member3\":905,\"member4\":906,\"time\":1395586448916,\"member5\":907,\"memo\":\"\",\"member6\":908,\"_id\":157,\"userName\":\"ルイ\",\"member1\":903}"));
			party.put(new JSONObject("{\"member2\":898,\"member3\":899,\"member4\":900,\"time\":1395585965403,\"member5\":901,\"memo\":\"\",\"member6\":902,\"_id\":156,\"userName\":\"みな\",\"member1\":897}"));
			party.put(new JSONObject("{\"member2\":892,\"member3\":893,\"member4\":894,\"time\":1395585330368,\"member5\":895,\"memo\":\"\",\"member6\":896,\"_id\":155,\"userName\":\"\",\"member1\":891}"));
			party.put(new JSONObject("{\"member2\":886,\"member3\":887,\"member4\":888,\"time\":1395584784119,\"member5\":889,\"memo\":\"\",\"member6\":890,\"_id\":154,\"userName\":\"マルカ\",\"member1\":885}"));
			party.put(new JSONObject("{\"member2\":880,\"member3\":881,\"member4\":882,\"time\":1394951951760,\"member5\":883,\"memo\":\"\",\"member6\":884,\"_id\":153,\"userName\":\"イリヤ\",\"member1\":879}"));
			party.put(new JSONObject("{\"member2\":874,\"member3\":875,\"member4\":876,\"time\":1394951523543,\"member5\":877,\"memo\":\"\",\"member6\":878,\"_id\":152,\"userName\":\"\",\"member1\":873}"));
			party.put(new JSONObject("{\"member2\":868,\"member3\":869,\"member4\":870,\"time\":1394950888173,\"member5\":871,\"memo\":\"\",\"member6\":872,\"_id\":151,\"userName\":\"キラマツモト\",\"member1\":867}"));
			party.put(new JSONObject("{\"member2\":862,\"member3\":863,\"member4\":864,\"time\":1394950401007,\"member5\":865,\"memo\":\"\",\"member6\":866,\"_id\":150,\"userName\":\"アッシュ\",\"member1\":861}"));
			party.put(new JSONObject("{\"member2\":856,\"member3\":857,\"member4\":858,\"time\":1394949840020,\"member5\":859,\"memo\":\"\",\"member6\":860,\"_id\":149,\"userName\":\"アウシュビ\",\"member1\":855}"));
			party.put(new JSONObject("{\"member2\":850,\"member3\":851,\"member4\":852,\"time\":1394949458251,\"member5\":853,\"memo\":\"\",\"member6\":854,\"_id\":148,\"userName\":\"\",\"member1\":849}"));
			party.put(new JSONObject("{\"member2\":844,\"member3\":845,\"member4\":846,\"time\":1394949074827,\"member5\":847,\"memo\":\"\",\"member6\":848,\"_id\":147,\"userName\":\"Mattew\",\"member1\":843}"));
			party.put(new JSONObject("{\"member2\":838,\"member3\":839,\"member4\":840,\"time\":1394948670421,\"member5\":841,\"memo\":\"\",\"member6\":842,\"_id\":146,\"userName\":\"ナナ\",\"member1\":837}"));
			party.put(new JSONObject("{\"member2\":832,\"member3\":833,\"member4\":834,\"time\":1394948220461,\"member5\":835,\"memo\":\"\",\"member6\":836,\"_id\":145,\"userName\":\"のーぶー\",\"member1\":831}"));
			party.put(new JSONObject("{\"member2\":826,\"member3\":827,\"member4\":828,\"time\":1394947358576,\"member5\":829,\"memo\":\"\",\"member6\":830,\"_id\":144,\"userName\":\"\",\"member1\":825}"));
			party.put(new JSONObject("{\"member2\":820,\"member3\":821,\"member4\":822,\"time\":1394946906722,\"member5\":823,\"memo\":\"\",\"member6\":824,\"_id\":143,\"userName\":\"シルバー\",\"member1\":819}"));
			party.put(new JSONObject("{\"member2\":814,\"member3\":815,\"member4\":816,\"time\":1394946142606,\"member5\":817,\"memo\":\"\",\"member6\":818,\"_id\":142,\"userName\":\"KON\",\"member1\":813}"));
			party.put(new JSONObject("{\"member2\":808,\"member3\":809,\"member4\":810,\"time\":1394945554796,\"member5\":811,\"memo\":\"\",\"member6\":812,\"_id\":141,\"userName\":\"くばしる\",\"member1\":807}"));
			party.put(new JSONObject("{\"member2\":802,\"member3\":803,\"member4\":804,\"time\":1394376884582,\"member5\":805,\"memo\":\"\",\"member6\":806,\"_id\":140,\"userName\":\"\",\"member1\":801}"));
			party.put(new JSONObject("{\"member2\":796,\"member3\":797,\"member4\":798,\"time\":1394375956906,\"member5\":799,\"memo\":\"\",\"member6\":800,\"_id\":139,\"userName\":\"かにみそ\",\"member1\":795}"));
			party.put(new JSONObject("{\"member2\":790,\"member3\":791,\"member4\":792,\"time\":1394374989676,\"member5\":793,\"memo\":\"\",\"member6\":794,\"_id\":138,\"userName\":\"\",\"member1\":789}"));
			party.put(new JSONObject("{\"member2\":784,\"member3\":785,\"member4\":786,\"time\":1394374308677,\"member5\":787,\"memo\":\"\",\"member6\":788,\"_id\":137,\"userName\":\"しん\",\"member1\":783}"));
			party.put(new JSONObject("{\"member2\":778,\"member3\":779,\"member4\":780,\"time\":1394373776778,\"member5\":781,\"memo\":\"\",\"member6\":782,\"_id\":136,\"userName\":\"みこと\",\"member1\":777}"));
			party.put(new JSONObject("{\"member2\":772,\"member3\":773,\"member4\":774,\"time\":1394373228798,\"member5\":775,\"memo\":\"\",\"member6\":776,\"_id\":135,\"userName\":\"\",\"member1\":771}"));
			party.put(new JSONObject("{\"member2\":766,\"member3\":767,\"member4\":768,\"time\":1394372571061,\"member5\":769,\"memo\":\"\",\"member6\":770,\"_id\":134,\"userName\":\"\",\"member1\":765}"));
			party.put(new JSONObject("{\"member2\":760,\"member3\":761,\"member4\":762,\"time\":1394309455988,\"member5\":763,\"memo\":\"\",\"member6\":764,\"_id\":133,\"userName\":\"ちゃげ\",\"member1\":759}"));
			party.put(new JSONObject("{\"member2\":754,\"member3\":755,\"member4\":756,\"time\":1394308993161,\"member5\":757,\"memo\":\"\",\"member6\":758,\"_id\":132,\"userName\":\"Blade\",\"member1\":753}"));
			party.put(new JSONObject("{\"member2\":748,\"member3\":749,\"member4\":750,\"time\":1394308343688,\"member5\":751,\"memo\":\"\",\"member6\":752,\"_id\":131,\"userName\":\"69ゆう\",\"member1\":747}"));
			party.put(new JSONObject("{\"member2\":742,\"member3\":743,\"member4\":744,\"time\":1394307474030,\"member5\":745,\"memo\":\"\",\"member6\":746,\"_id\":130,\"userName\":\"もっとん\",\"member1\":741}"));
			party.put(new JSONObject("{\"member2\":736,\"member3\":737,\"member4\":738,\"time\":1394306978043,\"member5\":739,\"memo\":\"\",\"member6\":740,\"_id\":129,\"userName\":\"ましゅー\",\"member1\":735}"));
			party.put(new JSONObject("{\"member2\":730,\"member3\":731,\"member4\":732,\"time\":1394306436528,\"member5\":733,\"memo\":\"\",\"member6\":734,\"_id\":128,\"userName\":\"King\",\"member1\":729}"));
			party.put(new JSONObject("{\"member2\":724,\"member3\":725,\"member4\":726,\"time\":1393855238451,\"member5\":727,\"memo\":\"\",\"member6\":728,\"_id\":127,\"userName\":\"みなとともか\",\"member1\":723}"));
			party.put(new JSONObject("{\"member2\":718,\"member3\":719,\"member4\":720,\"time\":1393854627841,\"member5\":721,\"memo\":\"\",\"member6\":722,\"_id\":126,\"userName\":\"\",\"member1\":717}"));
			party.put(new JSONObject("{\"member2\":712,\"member3\":713,\"member4\":714,\"time\":1393768819027,\"member5\":715,\"memo\":\"\",\"member6\":716,\"_id\":125,\"userName\":\"\",\"member1\":711}"));
			party.put(new JSONObject("{\"member2\":706,\"member3\":707,\"member4\":708,\"time\":1393768387383,\"member5\":709,\"memo\":\"\",\"member6\":710,\"_id\":124,\"userName\":\"\",\"member1\":705}"));
			party.put(new JSONObject("{\"member2\":700,\"member3\":701,\"member4\":702,\"time\":1393767730385,\"member5\":703,\"memo\":\"\",\"member6\":704,\"_id\":123,\"userName\":\"\",\"member1\":699}"));
			party.put(new JSONObject("{\"member2\":694,\"member3\":695,\"member4\":696,\"time\":1393767308733,\"member5\":697,\"memo\":\"\",\"member6\":698,\"_id\":122,\"userName\":\"\",\"member1\":693}"));
			party.put(new JSONObject("{\"member2\":688,\"member3\":689,\"member4\":690,\"time\":1393766857473,\"member5\":691,\"memo\":\"\",\"member6\":692,\"_id\":121,\"userName\":\"にょりーん\",\"member1\":687}"));
			party.put(new JSONObject("{\"member2\":682,\"member3\":683,\"member4\":684,\"time\":1393766335432,\"member5\":685,\"memo\":\"\",\"member6\":686,\"_id\":120,\"userName\":\"\",\"member1\":681}"));
			party.put(new JSONObject("{\"member2\":676,\"member3\":677,\"member4\":678,\"time\":1393694339386,\"member5\":679,\"memo\":\"\",\"member6\":680,\"_id\":119,\"userName\":\"ケンタ\",\"member1\":675}"));
			party.put(new JSONObject("{\"member2\":670,\"member3\":671,\"member4\":672,\"time\":1393693590782,\"member5\":673,\"memo\":\"\",\"member6\":674,\"_id\":118,\"userName\":\"\",\"member1\":669}"));
			party.put(new JSONObject("{\"member2\":665,\"member3\":666,\"member4\":667,\"time\":1393693113012,\"member5\":668,\"memo\":\"\",\"member6\":0,\"_id\":117,\"userName\":\"Mr.Jic\",\"member1\":664}"));
			party.put(new JSONObject("{\"member2\":659,\"member3\":660,\"member4\":661,\"time\":1393692639505,\"member5\":662,\"memo\":\"\",\"member6\":663,\"_id\":116,\"userName\":\"クロック\",\"member1\":658}"));
			party.put(new JSONObject("{\"member2\":653,\"member3\":654,\"member4\":655,\"time\":1393692121310,\"member5\":656,\"memo\":\"\",\"member6\":657,\"_id\":115,\"userName\":\"Super3210\",\"member1\":652}"));
			party.put(new JSONObject("{\"member2\":647,\"member3\":648,\"member4\":649,\"time\":1393691609072,\"member5\":650,\"memo\":\"\",\"member6\":651,\"_id\":114,\"userName\":\"ポパピ\",\"member1\":646}"));
			party.put(new JSONObject("{\"member2\":642,\"member3\":643,\"member4\":644,\"time\":1393691444421,\"member5\":645,\"memo\":\"\",\"member6\":639,\"_id\":113,\"userName\":\"ゆーた\",\"member1\":641}"));
			party.put(new JSONObject("{\"member2\":631,\"member3\":632,\"member4\":633,\"time\":1393690744498,\"member5\":634,\"memo\":\"\",\"member6\":635,\"_id\":111,\"userName\":\"オメガ\",\"member1\":630}"));
			party.put(new JSONObject("{\"member2\":625,\"member3\":626,\"member4\":627,\"time\":1393690074329,\"member5\":628,\"memo\":\"\",\"member6\":629,\"_id\":110,\"userName\":\"ベガ\",\"member1\":624}"));
			party.put(new JSONObject("{\"member2\":619,\"member3\":620,\"member4\":621,\"time\":1393689695615,\"member5\":622,\"memo\":\"\",\"member6\":623,\"_id\":109,\"userName\":\"ユキ\",\"member1\":618}"));
			party.put(new JSONObject("{\"member2\":613,\"member3\":614,\"member4\":615,\"time\":1393689043218,\"member5\":616,\"memo\":\"\",\"member6\":617,\"_id\":108,\"userName\":\"タバケイ\",\"member1\":612}"));
			party.put(new JSONObject("{\"member2\":607,\"member3\":608,\"member4\":609,\"time\":1393688515486,\"member5\":610,\"memo\":\"\",\"member6\":611,\"_id\":107,\"userName\":\"ジェット\",\"member1\":606}"));
			party.put(new JSONObject("{\"member2\":601,\"member3\":602,\"member4\":603,\"time\":1393517320699,\"member5\":604,\"memo\":\"\",\"member6\":605,\"_id\":106,\"userName\":\"ミヤ\",\"member1\":600}"));
			party.put(new JSONObject("{\"member2\":595,\"member3\":596,\"member4\":597,\"time\":1393516751927,\"member5\":598,\"memo\":\"\",\"member6\":599,\"_id\":105,\"userName\":\"なつれ\",\"member1\":594}"));
			party.put(new JSONObject("{\"member2\":589,\"member3\":590,\"member4\":591,\"time\":1393516381326,\"member5\":592,\"memo\":\"\",\"member6\":593,\"_id\":104,\"userName\":\"フロイド\",\"member1\":588}"));
			party.put(new JSONObject("{\"member2\":583,\"member3\":584,\"member4\":585,\"time\":1393515900599,\"member5\":586,\"memo\":\"\",\"member6\":587,\"_id\":103,\"userName\":\"かきのもと\",\"member1\":582}"));
			party.put(new JSONObject("{\"member2\":578,\"member3\":579,\"member4\":580,\"time\":1393253755511,\"member5\":581,\"memo\":\"\",\"member6\":0,\"_id\":102,\"userName\":\"\",\"member1\":577}"));
			party.put(new JSONObject("{\"member2\":572,\"member3\":573,\"member4\":574,\"time\":1393251867556,\"member5\":575,\"memo\":\"\",\"member6\":576,\"_id\":101,\"userName\":\"ハジメ\",\"member1\":571}"));
			party.put(new JSONObject("{\"member2\":566,\"member3\":567,\"member4\":568,\"time\":1393251292077,\"member5\":569,\"memo\":\"\",\"member6\":570,\"_id\":100,\"userName\":\"アカシックス\",\"member1\":565}"));
			party.put(new JSONObject("{\"member2\":560,\"member3\":561,\"member4\":562,\"time\":1393171689208,\"member5\":563,\"memo\":\"\",\"member6\":564,\"_id\":99,\"userName\":\"\",\"member1\":559}"));
			party.put(new JSONObject("{\"member2\":554,\"member3\":555,\"member4\":556,\"time\":1393170701987,\"member5\":557,\"memo\":\"\",\"member6\":558,\"_id\":98,\"userName\":\"\",\"member1\":553}"));
			party.put(new JSONObject("{\"member2\":548,\"member3\":549,\"member4\":550,\"time\":1393170163967,\"member5\":551,\"memo\":\"\",\"member6\":552,\"_id\":97,\"userName\":\"\",\"member1\":547}"));
			party.put(new JSONObject("{\"member2\":542,\"member3\":543,\"member4\":544,\"time\":1393089538149,\"member5\":545,\"memo\":\"\",\"member6\":546,\"_id\":96,\"userName\":\"\",\"member1\":541}"));
			party.put(new JSONObject("{\"member2\":537,\"member3\":538,\"member4\":539,\"time\":1393089087982,\"member5\":540,\"memo\":\"\",\"member6\":0,\"_id\":95,\"userName\":\"\",\"member1\":536}"));
			party.put(new JSONObject("{\"member2\":531,\"member3\":532,\"member4\":533,\"time\":1393088454646,\"member5\":534,\"memo\":\"\",\"member6\":535,\"_id\":94,\"userName\":\"\",\"member1\":530}"));
			party.put(new JSONObject("{\"member2\":525,\"member3\":526,\"member4\":527,\"time\":1393087381291,\"member5\":528,\"memo\":\"\",\"member6\":529,\"_id\":93,\"userName\":\"スシフ\",\"member1\":524}"));
			party.put(new JSONObject("{\"member2\":519,\"member3\":520,\"member4\":521,\"time\":1393087051537,\"member5\":522,\"memo\":\"\",\"member6\":523,\"_id\":92,\"userName\":\"\",\"member1\":518}"));
			party.put(new JSONObject("{\"member2\":513,\"member3\":514,\"member4\":515,\"time\":1392907983397,\"member5\":516,\"memo\":\"\",\"member6\":517,\"_id\":91,\"userName\":\"レンゲ\",\"member1\":512}"));
			party.put(new JSONObject("{\"member2\":508,\"member3\":509,\"member4\":510,\"time\":1392907626222,\"member5\":511,\"memo\":\"\",\"member6\":0,\"_id\":90,\"userName\":\"グラさん\",\"member1\":507}"));
			party.put(new JSONObject("{\"member2\":502,\"member3\":503,\"member4\":504,\"time\":1392730001971,\"member5\":505,\"memo\":\"\",\"member6\":506,\"_id\":89,\"userName\":\"メガーヌ\",\"member1\":501}"));
			party.put(new JSONObject("{\"member2\":496,\"member3\":497,\"member4\":498,\"time\":1392564456940,\"member5\":499,\"memo\":\"\",\"member6\":500,\"_id\":88,\"userName\":\"\",\"member1\":495}"));
			party.put(new JSONObject("{\"member2\":490,\"member3\":491,\"member4\":492,\"time\":1392562987100,\"member5\":493,\"memo\":\"\",\"member6\":494,\"_id\":87,\"userName\":\"\",\"member1\":489}"));
			party.put(new JSONObject("{\"member2\":484,\"member3\":485,\"member4\":486,\"time\":1392561992869,\"member5\":487,\"memo\":\"\",\"member6\":488,\"_id\":86,\"userName\":\"\",\"member1\":483}"));
			party.put(new JSONObject("{\"member2\":478,\"member3\":479,\"member4\":480,\"time\":1392535978262,\"member5\":481,\"memo\":\"\",\"member6\":482,\"_id\":85,\"userName\":\"\",\"member1\":477}"));
			party.put(new JSONObject("{\"member2\":472,\"member3\":473,\"member4\":474,\"time\":1392532211911,\"member5\":475,\"memo\":\"\",\"member6\":476,\"_id\":84,\"userName\":\"\",\"member1\":471}"));
			party.put(new JSONObject("{\"member2\":466,\"member3\":467,\"member4\":468,\"time\":1392530711975,\"member5\":469,\"memo\":\"\",\"member6\":470,\"_id\":83,\"userName\":\"セレナ\",\"member1\":465}"));
			party.put(new JSONObject("{\"member2\":460,\"member3\":461,\"member4\":462,\"time\":1392529726451,\"member5\":463,\"memo\":\"\",\"member6\":464,\"_id\":82,\"userName\":\"\",\"member1\":459}"));
			party.put(new JSONObject("{\"member2\":454,\"member3\":455,\"member4\":456,\"time\":1392389650771,\"member5\":457,\"memo\":\"\",\"member6\":458,\"_id\":81,\"userName\":\"ゴールド\",\"member1\":453}"));
			party.put(new JSONObject("{\"member2\":448,\"member3\":449,\"member4\":450,\"time\":1392389199858,\"member5\":451,\"memo\":\"\",\"member6\":452,\"_id\":80,\"userName\":\"Alperen\",\"member1\":447}"));
			party.put(new JSONObject("{\"member2\":442,\"member3\":443,\"member4\":444,\"time\":1392388684920,\"member5\":445,\"memo\":\"\",\"member6\":446,\"_id\":79,\"userName\":\"アウェン\",\"member1\":441}"));
			party.put(new JSONObject("{\"member2\":436,\"member3\":437,\"member4\":438,\"time\":1392387829109,\"member5\":439,\"memo\":\"\",\"member6\":440,\"_id\":78,\"userName\":\"ジン\",\"member1\":435}"));
			party.put(new JSONObject("{\"member2\":430,\"member3\":431,\"member4\":432,\"time\":1392387273978,\"member5\":433,\"memo\":\"\",\"member6\":434,\"_id\":77,\"userName\":\"ユウト\",\"member1\":429}"));
			party.put(new JSONObject("{\"member2\":424,\"member3\":425,\"member4\":426,\"time\":1392386854612,\"member5\":427,\"memo\":\"\",\"member6\":428,\"_id\":76,\"userName\":\"ギンジ\",\"member1\":423}"));
			party.put(new JSONObject("{\"member2\":418,\"member3\":419,\"member4\":420,\"time\":1392386056825,\"member5\":421,\"memo\":\"\",\"member6\":422,\"_id\":75,\"userName\":\"シゲマル\",\"member1\":417}"));
			party.put(new JSONObject("{\"member2\":412,\"member3\":413,\"member4\":414,\"time\":1392385498638,\"member5\":415,\"memo\":\"\",\"member6\":416,\"_id\":74,\"userName\":\"あきこ\",\"member1\":411}"));
			party.put(new JSONObject("{\"member2\":406,\"member3\":407,\"member4\":408,\"time\":1392384891505,\"member5\":409,\"memo\":\"\",\"member6\":410,\"_id\":73,\"userName\":\"ハヤテ\",\"member1\":405}"));
			party.put(new JSONObject("{\"member2\":400,\"member3\":401,\"member4\":402,\"time\":1392307159470,\"member5\":403,\"memo\":\"\",\"member6\":404,\"_id\":72,\"userName\":\"Beyonce\",\"member1\":399}"));
			party.put(new JSONObject("{\"member2\":394,\"member3\":395,\"member4\":396,\"time\":1392306104990,\"member5\":397,\"memo\":\"\",\"member6\":398,\"_id\":71,\"userName\":\"とおる\",\"member1\":393}"));
			party.put(new JSONObject("{\"member2\":388,\"member3\":389,\"member4\":390,\"time\":1392305422894,\"member5\":391,\"memo\":\"\",\"member6\":392,\"_id\":70,\"userName\":\"SALL\",\"member1\":387}"));
			party.put(new JSONObject("{\"member2\":383,\"member3\":384,\"member4\":385,\"time\":1392304782871,\"member5\":386,\"memo\":\"\",\"member6\":0,\"_id\":69,\"userName\":\"SHOGO\",\"member1\":382}"));
			party.put(new JSONObject("{\"member2\":377,\"member3\":378,\"member4\":379,\"time\":1392304058530,\"member5\":380,\"memo\":\"\",\"member6\":381,\"_id\":68,\"userName\":\"あかつき\",\"member1\":376}"));
			party.put(new JSONObject("{\"member2\":371,\"member3\":372,\"member4\":373,\"time\":1392303601981,\"member5\":374,\"memo\":\"\",\"member6\":375,\"_id\":67,\"userName\":\"てんのすけ\",\"member1\":370}"));
			party.put(new JSONObject("{\"member2\":365,\"member3\":366,\"member4\":367,\"time\":1391846451186,\"member5\":368,\"memo\":\"\",\"member6\":369,\"_id\":66,\"userName\":\"\",\"member1\":364}"));
			party.put(new JSONObject("{\"member2\":341,\"member3\":342,\"member4\":343,\"time\":1391845300335,\"member5\":344,\"memo\":\"\",\"member6\":345,\"_id\":62,\"userName\":\"\",\"member1\":340}"));
			party.put(new JSONObject("{\"member2\":290,\"member3\":291,\"member4\":292,\"time\":1391186453426,\"member5\":293,\"memo\":\"\",\"member6\":294,\"_id\":54,\"userName\":\"\",\"member1\":289}"));
			party.put(new JSONObject("{\"member2\":284,\"member3\":285,\"member4\":286,\"time\":1391185931893,\"member5\":287,\"memo\":\"\",\"member6\":288,\"_id\":53,\"userName\":\"サウス\",\"member1\":283}"));
			party.put(new JSONObject("{\"member2\":278,\"member3\":279,\"member4\":280,\"time\":1391185498271,\"member5\":281,\"memo\":\"\",\"member6\":282,\"_id\":52,\"userName\":\"Pon\",\"member1\":277}"));
			party.put(new JSONObject("{\"member2\":272,\"member3\":273,\"member4\":274,\"time\":1391184940380,\"member5\":275,\"memo\":\"\",\"member6\":276,\"_id\":51,\"userName\":\"koopa\",\"member1\":271}"));
			party.put(new JSONObject("{\"member2\":266,\"member3\":267,\"member4\":268,\"time\":1391184304184,\"member5\":269,\"memo\":\"\",\"member6\":270,\"_id\":50,\"userName\":\"オレンジ\",\"member1\":265}"));
			party.put(new JSONObject("{\"member2\":260,\"member3\":261,\"member4\":262,\"time\":1391183613836,\"member5\":263,\"memo\":\"\",\"member6\":264,\"_id\":49,\"userName\":\"ろろろ\",\"member1\":259}"));
			party.put(new JSONObject("{\"member2\":254,\"member3\":255,\"member4\":256,\"time\":1391182571293,\"member5\":257,\"memo\":\"\",\"member6\":258,\"_id\":48,\"userName\":\"\",\"member1\":253}"));
			party.put(new JSONObject("{\"member2\":248,\"member3\":249,\"member4\":250,\"time\":1391181955437,\"member5\":251,\"memo\":\"\",\"member6\":252,\"_id\":47,\"userName\":\"サハ\",\"member1\":247}"));
			party.put(new JSONObject("{\"member2\":242,\"member3\":243,\"member4\":244,\"time\":1391011459059,\"member5\":245,\"memo\":\"\",\"member6\":246,\"_id\":46,\"userName\":\"\",\"member1\":241}"));
			party.put(new JSONObject("{\"member2\":237,\"member3\":238,\"member4\":239,\"time\":1391011116515,\"member5\":240,\"memo\":\"\",\"member6\":0,\"_id\":45,\"userName\":\"ひゅうが\",\"member1\":236}"));
			party.put(new JSONObject("{\"member2\":231,\"member3\":232,\"member4\":233,\"time\":1391010387164,\"member5\":234,\"memo\":\"\",\"member6\":235,\"_id\":44,\"userName\":\"けん\",\"member1\":230}"));
			party.put(new JSONObject("{\"member2\":225,\"member3\":226,\"member4\":227,\"time\":1391009907091,\"member5\":228,\"memo\":\"\",\"member6\":229,\"_id\":43,\"userName\":\"\",\"member1\":224}"));
			party.put(new JSONObject("{\"member2\":219,\"member3\":220,\"member4\":221,\"time\":1391009112866,\"member5\":222,\"memo\":\"\",\"member6\":223,\"_id\":42,\"userName\":\"フィン\",\"member1\":218}"));
			party.put(new JSONObject("{\"member2\":213,\"member3\":214,\"member4\":215,\"time\":1391008676279,\"member5\":216,\"memo\":\"\",\"member6\":217,\"_id\":41,\"userName\":\"タイシ\",\"member1\":212}"));
			party.put(new JSONObject("{\"member2\":208,\"member3\":209,\"member4\":210,\"time\":1391008125107,\"member5\":211,\"memo\":\"\",\"member6\":0,\"_id\":40,\"userName\":\"Max\",\"member1\":207}"));
			party.put(new JSONObject("{\"member2\":202,\"member3\":203,\"member4\":204,\"time\":1391007672646,\"member5\":205,\"memo\":\"\",\"member6\":206,\"_id\":39,\"userName\":\"さとし\",\"member1\":201}"));
			party.put(new JSONObject("{\"member2\":196,\"member3\":197,\"member4\":198,\"time\":1391007058086,\"member5\":199,\"memo\":\"\",\"member6\":200,\"_id\":38,\"userName\":\"ミクラ\",\"member1\":195}"));
			party.put(new JSONObject("{\"member2\":190,\"member3\":191,\"member4\":192,\"time\":1390923220960,\"member5\":193,\"memo\":\"\",\"member6\":194,\"_id\":37,\"userName\":\"まいまい\",\"member1\":189}"));
			party.put(new JSONObject("{\"member2\":184,\"member3\":185,\"member4\":186,\"time\":1390922553688,\"member5\":187,\"memo\":\"\",\"member6\":188,\"_id\":36,\"userName\":\"きよえ\",\"member1\":183}"));
			party.put(new JSONObject("{\"member2\":178,\"member3\":179,\"member4\":180,\"time\":1390922367141,\"member5\":181,\"memo\":\"\",\"member6\":182,\"_id\":35,\"userName\":\"マツ\",\"member1\":177}"));
			party.put(new JSONObject("{\"member2\":172,\"member3\":173,\"member4\":174,\"time\":1390921972991,\"member5\":175,\"memo\":\"\",\"member6\":176,\"_id\":34,\"userName\":\"やさささく。\",\"member1\":171}"));
			party.put(new JSONObject("{\"member2\":166,\"member3\":167,\"member4\":168,\"time\":1390921363581,\"member5\":169,\"memo\":\"\",\"member6\":170,\"_id\":33,\"userName\":\"shun\",\"member1\":165}"));
			party.put(new JSONObject("{\"member2\":160,\"member3\":161,\"member4\":162,\"time\":1390920882693,\"member5\":163,\"memo\":\"\",\"member6\":164,\"_id\":32,\"userName\":\"さいか\",\"member1\":159}"));
			party.put(new JSONObject("{\"member2\":154,\"member3\":155,\"member4\":156,\"time\":1390920328234,\"member5\":157,\"memo\":\"\",\"member6\":158,\"_id\":31,\"userName\":\"ケイ\",\"member1\":153}"));
			party.put(new JSONObject("{\"member2\":148,\"member3\":149,\"member4\":150,\"time\":1390920022503,\"member5\":151,\"memo\":\"\",\"member6\":152,\"_id\":30,\"userName\":\"アイギス\",\"member1\":147}"));
			party.put(new JSONObject("{\"member2\":142,\"member3\":143,\"member4\":144,\"time\":1390837510917,\"member5\":145,\"memo\":\"\",\"member6\":146,\"_id\":29,\"userName\":\"らんだむ\",\"member1\":141}"));
			party.put(new JSONObject("{\"member2\":136,\"member3\":137,\"member4\":138,\"time\":1390837061674,\"member5\":139,\"memo\":\"\",\"member6\":140,\"_id\":28,\"userName\":\"シモテ\",\"member1\":135}"));
			party.put(new JSONObject("{\"member2\":130,\"member3\":131,\"member4\":132,\"time\":1390836600871,\"member5\":133,\"memo\":\"\",\"member6\":134,\"_id\":27,\"userName\":\"ジュウザー\",\"member1\":129}"));
			party.put(new JSONObject("{\"member2\":126,\"member3\":127,\"member4\":128,\"time\":1390836152652,\"member5\":0,\"memo\":\"\",\"member6\":0,\"_id\":26,\"userName\":\"\",\"member1\":125}"));
			party.put(new JSONObject("{\"member2\":121,\"member3\":122,\"member4\":123,\"time\":1390835776455,\"member5\":124,\"memo\":\"\",\"member6\":0,\"_id\":25,\"userName\":\"てしや\",\"member1\":120}"));
			party.put(new JSONObject("{\"member2\":115,\"member3\":116,\"member4\":117,\"time\":1390835313399,\"member5\":118,\"memo\":\"\",\"member6\":119,\"_id\":24,\"userName\":\"ロドリゴ\",\"member1\":114}"));
			party.put(new JSONObject("{\"member2\":110,\"member3\":111,\"member4\":112,\"time\":1390834830282,\"member5\":113,\"memo\":\"\",\"member6\":0,\"_id\":23,\"userName\":\"ゆうま\",\"member1\":109}"));
			party.put(new JSONObject("{\"member2\":104,\"member3\":105,\"member4\":106,\"time\":1390834086545,\"member5\":107,\"memo\":\"\",\"member6\":108,\"_id\":22,\"userName\":\"ドライザ\",\"member1\":103}"));
			party.put(new JSONObject("{\"member2\":98,\"member3\":99,\"member4\":100,\"time\":1390748037096,\"member5\":101,\"memo\":\"\",\"member6\":102,\"_id\":21,\"userName\":\"ユウスケ\",\"member1\":97}"));
			party.put(new JSONObject("{\"member2\":92,\"member3\":93,\"member4\":94,\"time\":1390747587953,\"member5\":95,\"memo\":\"\",\"member6\":96,\"_id\":20,\"userName\":\"ANGU\",\"member1\":91}"));
			party.put(new JSONObject("{\"member2\":86,\"member3\":87,\"member4\":88,\"time\":1390747030396,\"member5\":89,\"memo\":\"\",\"member6\":90,\"_id\":19,\"userName\":\"ブルータス\",\"member1\":85}"));
			party.put(new JSONObject("{\"member2\":80,\"member3\":81,\"member4\":82,\"time\":1390746468619,\"member5\":83,\"memo\":\"\",\"member6\":84,\"_id\":18,\"userName\":\"オンプ\",\"member1\":79}"));
			party.put(new JSONObject("{\"member2\":74,\"member3\":75,\"member4\":76,\"time\":1390746065457,\"member5\":77,\"memo\":\"\",\"member6\":78,\"_id\":17,\"userName\":\"すりーと\",\"member1\":73}"));
			party.put(new JSONObject("{\"member2\":68,\"member3\":69,\"member4\":70,\"time\":1390745598093,\"member5\":71,\"memo\":\"\",\"member6\":72,\"_id\":16,\"userName\":\"なな\",\"member1\":67}"));
			party.put(new JSONObject("{\"member2\":62,\"member3\":63,\"member4\":64,\"time\":1390745089275,\"member5\":65,\"memo\":\"\",\"member6\":66,\"_id\":15,\"userName\":\"K\",\"member1\":61}"));
			party.put(new JSONObject("{\"member2\":56,\"member3\":57,\"member4\":58,\"time\":1390744532506,\"member5\":59,\"memo\":\"\",\"member6\":60,\"_id\":14,\"userName\":\"ゆきあつ\",\"member1\":55}"));
			party.put(new JSONObject("{\"member2\":50,\"member3\":51,\"member4\":52,\"time\":1390743972394,\"member5\":53,\"memo\":\"\",\"member6\":54,\"_id\":13,\"userName\":\"やすきち\",\"member1\":49}"));
			party.put(new JSONObject("{\"member2\":44,\"member3\":45,\"member4\":46,\"time\":1390388154214,\"member5\":47,\"memo\":\"\",\"member6\":48,\"_id\":12,\"userName\":\"\",\"member1\":43}"));
			party.put(new JSONObject("{\"member2\":38,\"member3\":39,\"member4\":40,\"time\":1390064980568,\"member5\":41,\"memo\":\"\",\"member6\":42,\"_id\":11,\"userName\":\"チェ・グソン\",\"member1\":37}"));

			for(int i = 0 ; i < party.length() ; i++){
				databaseHelper.insertPartyData(party.getJSONObject(i));
			}
            Log.i("PartyInsertHandler","過去のパーティーデータOK!");
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    public void insertOneParty() {
        try {
            databaseHelper.insertPartyData(party);
            Log.i("PartyInsertHandler","パーティーを登録しました。");
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
