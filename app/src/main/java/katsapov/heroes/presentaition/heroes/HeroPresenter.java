package katsapov.heroes.presentaition.heroes;

import java.util.List;

import katsapov.heroes.data.HeroRepository;
import katsapov.heroes.data.network.NetworkManager;
import katsapov.heroes.data.network.ApiException;
import katsapov.heroes.data.entity.Hero;
import katsapov.heroes.data.network.RequestCallback;
import katsapov.heroes.domain.Constants;
import katsapov.heroes.domain.interactor.HeroInteractor;
import katsapov.heroes.presentaition.base.BasePresenter;

public class HeroPresenter extends BasePresenter<HeroContract.View> implements
        HeroContract.Presenter {

    private HeroInteractor heroInteractor;
    private int currentPage = Constants.PAGE_START;
    private boolean isLastPage = false;

    HeroPresenter(NetworkManager networkManager) {
        heroInteractor = new HeroInteractor(new HeroRepository(networkManager));
    }

    @Override
    public void reloadHeroes() {
        currentPage = Constants.PAGE_START;
        isLastPage = false;
        loadNextHeroes();
    }

    @Override
    public void loadNextHeroes() {
        heroInteractor.getHeroesList(currentPage,
                new RequestCallback<List<Hero>>() {
                    @Override
                    public void onFailure(ApiException exception) {
                        mView.showError(exception.getMessage());
                    }

                    @Override
                    public void onSuccess(List<Hero> response) {
                        currentPage++;
                        int size = response.size();
                        if (size < Constants.PAGE_SIZE) {
                            isLastPage = true;
                        }
                        mView.updateList(response, currentPage, isLastPage);
                    }
                });
    }
}