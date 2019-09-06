package katsapov.heroes.domain.interactor;

import java.util.List;

import katsapov.heroes.data.HeroRepository;
import katsapov.heroes.data.entity.Hero;
import katsapov.heroes.data.network.RequestCallback;

public class HeroInteractor {

    private HeroRepository repository;

    public HeroInteractor(HeroRepository repository) {
        this.repository = repository;
    }

    public void getHeroesList(int page, RequestCallback<List<Hero>> callback) {
        repository.getCharactersList(page, callback);
    }
}
