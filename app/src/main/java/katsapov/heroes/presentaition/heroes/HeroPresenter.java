package katsapov.heroes.presentaition.heroes;

import java.util.List;

import katsapov.heroes.data.HeroRepository;
import katsapov.heroes.data.NetworkManager;
import katsapov.heroes.domain.entity.ApiException;
import katsapov.heroes.domain.entity.Hero;
import katsapov.heroes.domain.interactor.HeroInteractor;
import katsapov.heroes.presentaition.base.BasePresenter;

public class HeroPresenter extends BasePresenter<HeroContract.View> implements
        HeroContract.Presenter,
        NetworkManager.RequestCallback<List<Hero>> {

    private HeroInteractor heroInteractor;

    HeroPresenter(NetworkManager networkManager) {
        heroInteractor = new HeroInteractor(new HeroRepository(networkManager));
    }

    @Override
    public void getDataOnAdapter(int page) {
        heroInteractor.getHeroesList(page, this);
    }

    @Override
    public void onFailure(ApiException exception) {
        mView.showError(exception.getMessage());
    }

    @Override
    public void onSuccess(List<Hero> response) {
        mView.updateList(response);
    }
}