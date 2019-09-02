package katsapov.heroes.presentaition.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import katsapov.heroes.R;
import katsapov.heroes.data.NetworkManager;
import katsapov.heroes.data.entitiy.Constants;
import katsapov.heroes.data.entitiy.Hero;
import katsapov.heroes.presentaition.adapter.HeroesRecyclerAdapter;
import katsapov.heroes.presentaition.adapter.HeroesRecyclerAdapter.OnHeroClickListener;
import katsapov.heroes.presentaition.adapter.PaginationListener;
import katsapov.heroes.presentaition.mvp.HeroContract;
import katsapov.heroes.presentaition.mvp.Presenter;


public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, HeroContract.HeroView {

    HeroContract.Presenter mPresenter;
    HeroContract.HeroView mView;

    private HeroesRecyclerAdapter mAdapter;
    private int currentPage = Constants.PAGE_START;
    private boolean isLoading = false;
    private boolean isLastPage = false;
    int itemCount = 0;
    private List<Hero> listOfHeroes = new ArrayList<Hero>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getDataFromApi();
        mPresenter = new Presenter();
        mPresenter.attachView(this);

        SwipeRefreshLayout swipeRefresh = findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);

        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        mRecyclerView.setHasFixedSize(true);
        swipeRefresh.setOnRefreshListener(this);
        mRecyclerView.setHasFixedSize(true);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);


        mAdapter = new HeroesRecyclerAdapter(new OnHeroClickListener() {
            @Override
            public void onHeroClick(int position) {
                getDataFromApi();
                if (position <= listOfHeroes.size() - 1) {
                    Hero hero = listOfHeroes.get(position);
                    showHeroDetails(hero);
                }
            }
        });


        mRecyclerView.setAdapter(mAdapter);
        doApiCall();

        mRecyclerView.addOnScrollListener(new PaginationListener(layoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage++;
                doApiCall();
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


    private void doApiCall() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //  Presenter.loadMore();
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


    @Override
    public void onRefresh() {
        itemCount = 0;
        currentPage = Constants.PAGE_START;
        isLastPage = false;
        mAdapter.clear();
        doApiCall();
    }

    @Override
    public void showError(HeroContract.HeroView view) {
        Snackbar.make(findViewById(android.R.id.content), R.string.error, Snackbar.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
    }

    public void setList(List<Hero> list) {
        this.listOfHeroes = list;
        mPresenter.setList(list);
    }

    private void getDataFromApi() {
        new NetworkManager.getDataStringFromApi(this).execute();
    }


    @Override
    public void showIsLoading(Boolean isLoading) {
        mView.showIsLoading(true);
    }


    @Override
    public void showHeroDetails(Hero hero) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_fragment);
        dialog.setTitle(R.string.about_person);

        TextView tvCulture = dialog.findViewById(R.id.tvCulture);
        tvCulture.setText(hero.getCulture());

        TextView tvGender = dialog.findViewById(R.id.tvGender);
        tvGender.setText(hero.getGender());

        TextView tvBorn = dialog.findViewById(R.id.tvBorn);
        tvBorn.setText(hero.getBorn());

        TextView tvDie = dialog.findViewById(R.id.tvDie);
        tvDie.setText(hero.getDie());

        TextView tvUrl = dialog.findViewById(R.id.tvUrl);
        tvUrl.setText(Html.fromHtml(hero.getUrl()));

        TextView tvFather = dialog.findViewById(R.id.tvFather);
        tvFather.setText(hero.getFather());

        TextView tvMother = dialog.findViewById(R.id.tvMother);
        tvMother.setText(hero.getMother());

        Button dialogButton = dialog.findViewById(R.id.dialogButtonOK);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}