package katsapov.heroes.presentaition.ui;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;

import java.util.Collections;
import java.util.List;

import katsapov.heroes.R;
import katsapov.heroes.data.ApiException;
import katsapov.heroes.data.NetworkManager;
import katsapov.heroes.data.entitiy.Constants;
import katsapov.heroes.data.entitiy.Hero;
import katsapov.heroes.presentaition.adapter.HeroesRecyclerAdapter;
import katsapov.heroes.presentaition.adapter.HeroesRecyclerAdapter.OnHeroClickListener;
import katsapov.heroes.presentaition.adapter.PaginationListener;
import katsapov.heroes.presentaition.adapter.SpaceItemDecoration;
import katsapov.heroes.presentaition.mvp.HeroContract;
import katsapov.heroes.presentaition.mvp.HeroView;
import katsapov.heroes.presentaition.mvp.Presenter;


public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, NetworkManager.RequestCallback<List<Hero>> {

    HeroContract.Presenter mPresenter;
    HeroContract.HeroView mHeroView;

    private int currentPage = Constants.PAGE_START;
    private boolean isLoading = false;
    private boolean isLastPage = false;

    private HeroesRecyclerAdapter mAdapter;
    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPresenter = new Presenter();
        mHeroView = new HeroView();
        mPresenter.attachView(mHeroView);

        swipeRefresh = findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);

        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(0, getResources().getDimensionPixelSize(R.dimen.padding_half)));
        mRecyclerView.setHasFixedSize(true);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mPresenter.getDataOnAdapter(this, Constants.PAGE_START, this);
        mAdapter = new HeroesRecyclerAdapter(new OnHeroClickListener() {
            @Override
            public void onHeroClick(Hero hero) {
                mHeroView.showHeroDetails(hero, MainActivity.this);
            }
        });

        mRecyclerView.setAdapter(mAdapter);
        updateDataAfterRefresh();
        mPresenter.setList(Collections.<Hero>emptyList());

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
        boolean isHasInternetConnection = mPresenter.isOnline(this);
        if (isHasInternetConnection) {
            currentPage = Constants.PAGE_START;
            isLastPage = false;
            mAdapter.clear();
            updateDataAfterRefresh();
        } else {
            mAdapter.clear();
            mHeroView.showIsLoading(false, swipeRefresh);
            mHeroView.showError(this, R.string.error_connection);
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    public void setDataInList(List<Hero> list) {
        mPresenter.setList(list);
    }

    @Override
    public void onFailure(ApiException exc) {
        mAdapter.removeLoading();
        SwipeRefreshLayout swipeRefresh = findViewById(R.id.swipeRefresh);
        swipeRefresh.setRefreshing(false);
        Toast.makeText(this, exc.getResponse().getMessage(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onSuccess(List<Hero> response) {
        mAdapter.removeLoading();
        SwipeRefreshLayout swipeRefresh = findViewById(R.id.swipeRefresh);
        swipeRefresh.setRefreshing(false);
        int size = response.size();
        if (size < Constants.PAGE_SIZE) {
            Snackbar.make(findViewById(android.R.id.content), getString(R.string.home_message_list_size, size, currentPage), Snackbar.LENGTH_LONG).show();
        }
        mAdapter.addItems(response);
        isLoading = false;
    }

    private void updateDataAfterRefresh() {
        mPresenter.getDataOnAdapter(MainActivity.this, currentPage, this);
    }
}