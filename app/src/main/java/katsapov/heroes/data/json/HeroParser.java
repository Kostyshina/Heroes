package katsapov.heroes.data.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import katsapov.heroes.data.entitiy.Hero;

public class HeroParser {
    public static List<Hero> parseData(String json) {
        Type listType = new TypeToken<List<Hero>>() {}.getType();
        List<Hero> listOfHeroes = new Gson().fromJson(json, listType);
        return listOfHeroes;
    }
}