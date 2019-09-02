package katsapov.heroes.presentaition.mvp;


import java.util.ArrayList;
import java.util.List;

import katsapov.heroes.data.entitiy.Hero;
import katsapov.heroes.presentaition.mvp.HeroContract.HeroView;
import katsapov.heroes.presentaition.mvp.HeroRepository.HeroRequestCallbac;

public class Presenter implements HeroContract.Presenter {

    private HeroRepository mRepository;
    private HeroContract.HeroView mView;
    private List<Hero> mHeroes;
    private Integer currentPage;

    public Presenter() {
        currentPage = 0;
        mHeroes = new ArrayList<Hero>();
        mRepository = HeroRepository.getInstance();

    }

    @Override
    public void attachView(HeroView view) {
        mView = view;
    }


    @Override
    public void detachView() {
        mView = null;

    }

    @Override
    public void setList(List<Hero> list) {
        this.mHeroes = list;
    }

    @Override
    public void loadMore() {
        mView.showIsLoading(true);
        currentPage++;
        mRepository.getHeroes(currentPage, 10, new HeroRequestCallbac() {
            @Override
            public void onReqestFinished(List<Hero> heroes) {
                mHeroes.addAll(heroes);
                //  updateHeroes();
            }

            @Override
            public void onError(Exception e) {
                mView.showIsLoading(false);
                mView.showError(mView);
            }
        });
    }

}
