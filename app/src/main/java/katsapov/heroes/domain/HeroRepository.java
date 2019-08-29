package katsapov.heroes.domain;

import java.util.List;

import katsapov.heroes.data.entitiy.Hero;
import katsapov.heroes.data.json.HeroParser;
import katsapov.heroes.data.network.NetworkManager;

public class HeroRepository {
    private HeroParser heroParser;
    private NetworkManager networkManager;
    private List<Hero> listOfHeroes;

    public List<Hero> getHeroes() {
        //String object  = NetworkManager.sendRequestWithHttpURLConnection(); //ВЕРНЕТ СТРОКУ
        // listOfHeroes = HeroParser.parseData(object); //сюда закинем эту строку и распарсим
        return listOfHeroes;
    }
}
