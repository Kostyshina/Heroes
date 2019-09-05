package katsapov.heroes.data.json;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import katsapov.heroes.data.entitiy.Hero;

public class HeroParser implements BaseParser<List<Hero>> {

    private Gson gson;

    public HeroParser(Gson gson) {
        this.gson = gson;
    }

    @Override
    public List<Hero> parseData(String json) {
        Type listType = new TypeToken<List<Hero>>() {}.getType();
        return gson.fromJson(json, listType);
    }
}