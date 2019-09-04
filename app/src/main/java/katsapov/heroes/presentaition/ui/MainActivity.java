package katsapov.heroes.presentaition.ui;

import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import katsapov.heroes.R;
import katsapov.heroes.data.entitiy.Constants;
import katsapov.heroes.data.entitiy.Hero;
import katsapov.heroes.presentaition.adapter.HeroesRecyclerAdapter;
import katsapov.heroes.presentaition.adapter.HeroesRecyclerAdapter.OnHeroClickListener;
import katsapov.heroes.presentaition.adapter.PaginationListener;
import katsapov.heroes.presentaition.mvp.HeroContract;
import katsapov.heroes.presentaition.mvp.HeroView;
import katsapov.heroes.presentaition.mvp.Presenter;


public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    HeroContract.Presenter mPresenter;
    HeroContract.HeroView mHeroView;

    private int currentPage = Constants.PAGE_START;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    private boolean isHasInternetConnection = false;
    private List<Hero> listOfHeroes = new ArrayList<>();

    private HeroesRecyclerAdapter mAdapter;
    private SwipeRefreshLayout swipeRefresh;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPresenter = new Presenter();
        mPresenter.attachView(mHeroView);

        swipeRefresh = findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        mRecyclerView.setHasFixedSize(true);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mPresenter = new Presenter();
        mPresenter.getDataOnAdapter(this, this);
        mAdapter = new HeroesRecyclerAdapter(new OnHeroClickListener() {
            @Override
            public void onHeroClick(int position) {
                mPresenter = new Presenter();
                mPresenter.getDataOnAdapter(MainActivity.this, MainActivity.this);
                if (position <= listOfHeroes.size() - 1) {
                    Hero hero = listOfHeroes.get(position);
                    mHeroView = new HeroView();
                    mHeroView.showHeroDetails(hero, MainActivity.this);
                }
            }
        });

        mRecyclerView.setAdapter(mAdapter);
        updateDataAfterRefresh();
        mPresenter.setList(this.listOfHeroes);

        mRecyclerView.addOnScrollListener(new PaginationListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage++;
                updateDataAfterRefresh();
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return isLoading;
            }
        });
    }


    @Override
    public void onRefresh() {
        mPresenter = new Presenter();
        isHasInternetConnection = mPresenter.isOnline(this);
        if (isHasInternetConnection) {
            currentPage = Constants.PAGE_START;
            isLastPage = false;
            mAdapter.clear();
            updateDataAfterRefresh();
        } else {
            mAdapter.clear();
            mHeroView = new HeroView();
            mHeroView.showIsLoading(false, swipeRefresh);
            mHeroView.showError(this, R.string.error_connection);
            if (isHasInternetConnection) {
                onRefresh();
            }
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    public void setList(List<Hero> list) {
        this.listOfHeroes = list;
        mPresenter = new Presenter();
        mPresenter.setList(list);
    }


    private void updateDataAfterRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (currentPage != Constants.PAGE_START) mAdapter.removeLoading();
                SwipeRefreshLayout swipeRefresh = findViewById(R.id.swipeRefresh);
                swipeRefresh.setRefreshing(false);
                int i = listOfHeroes.size();
                int currentDataPage = i / Constants.TOTAL_PAGES;
                if (currentPage >= currentDataPage) {
                    Snackbar.make(findViewById(android.R.id.content), getString(R.string.data_from_api, i, currentDataPage), Snackbar.LENGTH_LONG).show();
                    mAdapter.addItems(listOfHeroes);
                }
                if (currentPage < currentDataPage) {
                    mAdapter.addLoading();
                } else {
                    isLastPage = true;
                }
                isLoading = false;
            }
        }, 1500);
    }
}