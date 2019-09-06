package katsapov.heroes.presentaition.heroes;

import java.util.List;

import katsapov.heroes.data.HeroRepository;
import katsapov.heroes.data.network.NetworkManager;
import katsapov.heroes.data.network.ApiException;
import katsapov.heroes.data.entity.Hero;
import katsapov.heroes.data.network.RequestCallback;
import katsapov.heroes.domain.interactor.HeroInteractor;
import katsapov.heroes.presentaition.base.BasePresenter;

public class HeroPresenter extends BasePresenter<HeroContract.View> implements
        HeroContract.Presenter {

    private HeroInteractor heroInteractor;

    HeroPresenter(NetworkManager networkManager) {
        heroInteractor = new HeroInteractor(new HeroRepository(networkManager));
    }

    @Override
    public void loadHeroes(int page) {
        heroInteractor.getHeroesList(page,
                new RequestCallback<List<Hero>>() {
                    @Override
                    public void onFailure(ApiException exception) {
                        mView.showError(exception.getMessage());
                    }

                    @Override
                    public void onSuccess(List<Hero> response) {
                        mView.updateList(response);
                    }
                });
    }
}