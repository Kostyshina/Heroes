package katsapov.heroes.presentaition.heroes;

import java.util.List;

import katsapov.heroes.data.entity.Hero;
import katsapov.heroes.presentaition.base.BaseView;

public interface HeroContract {

    interface View extends BaseView {
        void showHeroDetails(Hero hero);
        void updateList(List<Hero> heroes, int currentPage, boolean isLastPage);
    }

    interface Presenter {
        void reloadHeroes();
        void loadNextHeroes();
    }
}
