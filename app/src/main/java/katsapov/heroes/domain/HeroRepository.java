package katsapov.heroes.domain;

import java.util.List;

import katsapov.heroes.data.NetworkManager;
import katsapov.heroes.data.entitiy.Hero;
import katsapov.heroes.data.json.HeroParser;

public class HeroRepository {

    public static List<Hero> listOfHeroes = null;
    public static String jsonResponse;

    public  static List<Hero> getHeroesList() {
        jsonResponse = new NetworkManager.getDataStringFromApi().execute().toString();
        HeroParser.parseData(jsonResponse);
        return listOfHeroes;
    }
}
