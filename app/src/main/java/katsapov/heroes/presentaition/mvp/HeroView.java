package katsapov.heroes.presentaition.mvp;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import katsapov.heroes.data.entitiy.Constants;
import katsapov.heroes.data.entitiy.Hero;
import katsapov.heroes.presentaition.adapter.HeroesRecyclerAdapter;

public class HeroView   implements HeroContract.HeroView {

    private HeroContract.Presenter mPresenter;
    private HeroesRecyclerAdapter mAdapter;
    private SwipeRefreshLayout swipeRefresh;
    private List<Hero> listOfHeroes = new ArrayList<Hero>();
    private int currentPage = Constants.PAGE_START;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private HeroesRecyclerAdapter.OnHeroClickListener heroRecyclerLister;

    @Override
    public void showIsLoading(Boolean isLoading, SwipeRefreshLayout swipeRefresh1) {
        this.swipeRefresh = swipeRefresh1;
        if (false) {
            swipeRefresh1.setProgressViewOffset(false, 0, 0);
        } else
            swipeRefresh1.setProgressViewOffset(false, 1, 2);
    }

    @Override
    public void showHeroDetails(Hero hero) {

    }

    @Override
    public void showError(HeroContract.HeroView view, int string) {

    }

/*
    public void doApiCall(final Activity activity, final List<Hero> list, final HeroesRecyclerAdapter adapter) {
        this.listOfHeroes = list;
        mAdapter = new HeroesRecyclerAdapter(heroRecyclerLister);
        this.mAdapter = adapter;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentPage != Constants.PAGE_START) adapter.removeLoading();
                SwipeRefreshLayout swipeRefresh = activity.findViewById(R.id.swipeRefresh);
                swipeRefresh.setRefreshing(false);
                int i = list.size();
                int currentDataPage = i / Constants.TOTAL_PAGES;
                if (currentPage >= currentDataPage) {
                    Snackbar.make(activity.findViewById(android.R.id.content), activity.getString(R.string.data_from_api, i, currentDataPage), Snackbar.LENGTH_LONG).show();
                    adapter.addItems(listOfHeroes);
                }
                if (currentPage < currentDataPage) {
                    adapter.addLoading();
                } else {
                    isLastPage = true;
                }
                isLoading = false;
            }
        }, 1500);
    }*/

}
