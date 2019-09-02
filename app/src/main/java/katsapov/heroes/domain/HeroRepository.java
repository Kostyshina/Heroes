package katsapov.heroes.domain;

import java.util.List;

import katsapov.heroes.data.entitiy.Hero;

public class HeroRepository {

    public static List<Hero> listOfHeroes = null;
    public static String jsonResponse;

    public  static List<Hero> getHeroesList() {
       // listOfHeroes = (List<Hero>) new NetworkManager.getDataStringFromApi().execute();
        //HeroParser.parseData(jsonResponse);
        return listOfHeroes;
    }
}
