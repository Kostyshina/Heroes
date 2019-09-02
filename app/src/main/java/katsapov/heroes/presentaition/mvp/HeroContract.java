package katsapov.heroes.presentaition.mvp;

import java.util.List;

import katsapov.heroes.data.entitiy.Hero;

public interface HeroContract {

    interface HeroView {
        void showIsLoading(Boolean isLoading);
        void showHeroDetails(Hero hero);
        void showError(HeroView view);
    }

    interface Presenter {
        void attachView(HeroContract.HeroView view);
        void detachView();
        void loadMore();
        void setList(List<Hero> heroes);
    }
}
