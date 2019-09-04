package katsapov.heroes.presentaition.mvp;

import android.app.Activity;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.List;

import katsapov.heroes.data.entitiy.Hero;
import katsapov.heroes.presentaition.ui.MainActivity;

public interface HeroContract {

    interface HeroView {
        void showIsLoading(Boolean isLoading, SwipeRefreshLayout swipeRefreshLayout);
        void showHeroDetails(Hero hero, Activity activity);
        void showError(Activity activity, int stringError);
    }

    interface Presenter {
        void attachView(HeroContract.HeroView view);
        void detachView();
        void setList(List<Hero> heroes);
        boolean isOnline(Activity activity);
        void getDataOnAdapter(Activity activity, MainActivity mainActivity);
    }
}
