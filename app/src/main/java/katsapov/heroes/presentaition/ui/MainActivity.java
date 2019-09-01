package katsapov.heroes.presentaition.ui;

import android.os.Bundle;
import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import katsapov.heroes.R;
import katsapov.heroes.data.entitiy.Hero;
import katsapov.heroes.data.network.NetworkManager;
import katsapov.heroes.presentaition.adapter.HeroesRecyclerAdapter;
import katsapov.heroes.presentaition.adapter.PaginationListener;

import static katsapov.heroes.presentaition.adapter.PaginationListener.PAGE_START;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    public RecyclerView mRecyclerView;

    private HeroesRecyclerAdapter adapter;
    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private int totalPage = 10;
    private boolean isLoading = false;
    int itemCount = 0;
    private List<Hero> list = new ArrayList<Hero>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fireYourAsyncTask();

        SwipeRefreshLayout swipeRefresh = findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);

        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        mRecyclerView.setHasFixedSize(true);
        swipeRefresh.setOnRefreshListener(this);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        adapter = new HeroesRecyclerAdapter(new ArrayList<Hero>());
        mRecyclerView.setAdapter(adapter);
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
                for (int i = 0; i < 10; i++) {
                    itemCount++;
                    Hero postItem = new Hero();
                    postItem.setName(postItem.getCulture() + itemCount); //postItem.getName()
                    postItem.setGender(postItem.getUrl());
                    list.add(postItem);
                }

                if (currentPage != PAGE_START) adapter.removeLoading();
                adapter.addItems(list);
                int i =  list.size();
                SwipeRefreshLayout swipeRefresh = findViewById(R.id.swipeRefresh);
                swipeRefresh.setRefreshing(false);

                // check is last page or not
                if (currentPage < totalPage) {
                    adapter.addLoading();
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
        currentPage = PAGE_START;
        isLastPage = false;
        adapter.clear();
        doApiCall();
    }

    public void setList(List<Hero> list) {
        this.list = list;
    }

    private void fireYourAsyncTask() {
        new NetworkManager.LoadStringsAsync(this).execute();
    }
}
