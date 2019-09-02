package katsapov.heroes.presentaition.mvp;

import java.util.List;

import katsapov.heroes.data.entitiy.Hero;

public interface HeroContract {

  interface HeroView {
    void updateHeroesList(List<Hero> heroesList);
    void showIsLoading(Boolean isLoading);
    void showHeroDetails(Hero hero);
  }

  interface Presenter {
    void attachView(HeroContract.HeroView view);
    void detachView();
    void loadMore();
   // void updateHeroes();
    void onHeroDetailsClicked(int position);
  }
}
