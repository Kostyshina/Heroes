package katsapov.heroes.domain.interactor;

import java.util.List;

import katsapov.heroes.data.HeroRepository;
import katsapov.heroes.data.NetworkManager;
import katsapov.heroes.domain.entity.Hero;

public class HeroInteractor {

    private HeroRepository repository;

    public HeroInteractor(HeroRepository repository) {
        this.repository = repository;
    }

    public void getHeroesList(int page, NetworkManager.RequestCallback<List<Hero>> callback) {
        repository.getCharactersList(page, callback);
    }
}
