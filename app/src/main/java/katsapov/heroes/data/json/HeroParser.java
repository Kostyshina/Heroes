package katsapov.heroes.data.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import katsapov.heroes.data.entitiy.Constants;
import katsapov.heroes.data.entitiy.Hero;

public class HeroParser {

    private static JSONObject json;
    private static List<Hero> listHeroes;

    public HeroParser(List<Hero> listHeroes) {
        HeroParser.listHeroes = listHeroes;
    }

    public static void parseData(JSONArray array) {
        for (int i = 0; i < array.length(); i++) {
            Hero superHero = new Hero();
            json = null;
            try {
                json = array.getJSONObject(i);
                superHero.setName(json.getString(Constants.TAG_NAME));
                superHero.setGender(json.getString(Constants.TAG_GENDER));
                superHero.setCulture(json.getString(Constants.TAG_CULTURE));
                superHero.setBorn(json.getString(Constants.TAG_BORN));
                superHero.setDie(json.getString(Constants.TAG_DIE));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            listHeroes.add(superHero);
        }
    }
}