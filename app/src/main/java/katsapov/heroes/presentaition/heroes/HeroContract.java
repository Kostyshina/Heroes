package katsapov.heroes.presentaition.heroes;

import java.util.List;

import katsapov.heroes.domain.entity.Hero;
import katsapov.heroes.presentaition.base.BaseView;

public interface HeroContract {

    interface View extends BaseView {
        void showHeroDetails(Hero hero);
        void updateList(List<Hero> heroes);
    }

    interface Presenter {
        void getDataOnAdapter(int page);
    }
}
