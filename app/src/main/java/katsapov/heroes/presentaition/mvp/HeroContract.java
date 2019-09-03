package katsapov.heroes.presentaition.mvp;

import android.app.Activity;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import katsapov.heroes.data.entitiy.Hero;
import katsapov.heroes.presentaition.ui.MainActivity;

public interface HeroContract {

    interface HeroView {
        void showIsLoading(Boolean isLoading, SwipeRefreshLayout swipeRefreshLayout);
        void showHeroDetails(Hero hero);
        void showError(HeroView view, int string);
        //void doApiCall(Activity activity, List<Hero> list, HeroesRecyclerAdapter adapter);
    }

    interface Presenter {
        void attachView(HeroContract.HeroView view);
        void detachView();
        void setList(List<Hero> heroes);
        boolean isOnline(Activity activity);
        void getDataFromApi(MainActivity activity);
    }
}
