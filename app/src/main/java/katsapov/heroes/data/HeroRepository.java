package katsapov.heroes.data;

import com.google.gson.Gson;

import java.util.List;

import katsapov.heroes.data.entity.Hero;
import katsapov.heroes.data.json.HeroParser;
import katsapov.heroes.data.network.NetworkManager;
import katsapov.heroes.data.network.RequestCallback;
import katsapov.heroes.domain.Constants;

public class HeroRepository {

    private HeroParser heroParser;
    private NetworkManager networkManager;

    public HeroRepository(NetworkManager networkManager) {
        heroParser = new HeroParser(new Gson());
        this.networkManager = networkManager;
    }

    public void getCharactersList(int page, RequestCallback<List<Hero>> callback) {
        String request = Constants.CHARACTERS_ENDPOINT
                .concat("?")
                .concat(Constants.CHARACTERS_PARAM_PAGE).concat(String.valueOf(page))
                .concat("&")
                .concat(Constants.CHARACTERS_PARAM_PAGE_SIZE).concat(String.valueOf(Constants.PAGE_SIZE));

        networkManager.makeRequest(NetworkManager.RequestMethod.GET, request, heroParser, callback);
    }
}
