package katsapov.heroes.domain;

import java.util.List;

import katsapov.heroes.data.entitiy.Hero;
import katsapov.heroes.data.json.HeroParser;
import katsapov.heroes.data.network.NetworkManager;

public class HeroRepository {
    private HeroParser heroParser;
    private NetworkManager networkManager;
    private List<Hero> listOfHeroes;

    public List<Hero> getHeroes(int page) {
//        AsyncTask<Void, Void, JSONArray> sss =  new NetworkManager.LoadStringsAsync().execute();
//        listOfHeroes = HeroParser.parseData(sss); //сюда закинем эту строку и распарсим
        return listOfHeroes;
    }
}
