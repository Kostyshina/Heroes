package katsapov.heroes.presentaition.heroes;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import katsapov.heroes.R;
import katsapov.heroes.data.NetworkManager;
import katsapov.heroes.domain.Constants;
import katsapov.heroes.domain.entity.Hero;
import katsapov.heroes.presentaition.adapter.HeroesRecyclerAdapter;
import katsapov.heroes.presentaition.adapter.HeroesRecyclerAdapter.OnHeroClickListener;
import katsapov.heroes.presentaition.adapter.PaginationListener;
import katsapov.heroes.presentaition.adapter.SpaceItemDecoration;
import katsapov.heroes.presentaition.utils.NetworkUtils;


public class MainActivity extends AppCompatActivity implements
        HeroContract.View,
        SwipeRefreshLayout.OnRefreshListener{

    HeroPresenter mPresenter;

    private int currentPage = Constants.PAGE_START;
    private boolean isLoading = false;
    private boolean isLastPage = false;

    private HeroesRecyclerAdapter mAdapter;
    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPresenter = new HeroPresenter(new NetworkManager());
        mPresenter.attachView(this);

        swipeRefresh = findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);

        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new SpaceItemDecoration(0, getResources().getDimensionPixelSize(R.dimen.padding_half)));
        mRecyclerView.setHasFixedSize(true);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mPresenter.getDataOnAdapter(Constants.PAGE_START);
        mAdapter = new HeroesRecyclerAdapter(new OnHeroClickListener() {
            @Override
            public void onHeroClick(Hero hero) {
                showHeroDetails(hero);
            }
        });

        mRecyclerView.setAdapter(mAdapter);
        updateDataAfterRefresh();

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
        boolean isHasInternetConnection = NetworkUtils.isConnected(this);
        if (isHasInternetConnection) {
            currentPage = Constants.PAGE_START;
            isLastPage = false;
            mAdapter.clear();
            updateDataAfterRefresh();
        } else {
            mAdapter.clear();
            showError(getString(R.string.error_connection));
        }
    }

    @Override
    protected void onDestroy() {
        mPresenter.detachView();
        super.onDestroy();
    }

    @Override
    public void updateList(List<Hero> heroes) {
        mAdapter.removeLoading();
        swipeRefresh.setRefreshing(false);
        int size = heroes.size();
        if (size < Constants.PAGE_SIZE) {
            Snackbar.make(findViewById(android.R.id.content), getString(R.string.home_message_list_size, size, currentPage), Snackbar.LENGTH_LONG).show();
        }
        mAdapter.addItems(heroes);
        isLoading = false;
    }

    private void updateDataAfterRefresh() {
        mPresenter.getDataOnAdapter(currentPage);
    }

    @Override
    public void showLoader(boolean isLoading) {
        swipeRefresh.setProgressViewOffset(false, 1, 2);
    }

    @Override
    public void showHeroDetails(final Hero hero) {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_fragment);
        dialog.setTitle(R.string.details_dialog_label_about_character);

        TextView tvCulture = dialog.findViewById(R.id.tvCulture);
        tvCulture.setText(hero.getCulture());

        TextView tvGender = dialog.findViewById(R.id.tvGender);
        tvGender.setText(hero.getGender());

        TextView tvBorn = dialog.findViewById(R.id.tvBorn);
        tvBorn.setText(hero.getBorn());

        TextView tvDie = dialog.findViewById(R.id.tvDie);
        tvDie.setText(hero.getDie());

        TextView tvUrl = dialog.findViewById(R.id.tvUrl);
        tvUrl.setText(hero.getUrl());

        tvUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent("katsapov.Browser");
                intent.setData(Uri.parse(hero.getUrl()));
                startActivity(intent);
            }
        });

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


    @Override
    public void showError(String message) {
        mAdapter.removeLoading();
        swipeRefresh.setRefreshing(false);
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG).show();
    }
}