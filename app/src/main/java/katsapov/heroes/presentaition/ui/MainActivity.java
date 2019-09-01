package katsapov.heroes.presentaition.ui;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import katsapov.heroes.R;
import katsapov.heroes.data.entitiy.Hero;
import katsapov.heroes.presentaition.adapter.HeroesRecyclerAdapter;
import katsapov.heroes.presentaition.adapter.HeroesRecyclerAdapter.OnHeroClickListener;
import katsapov.heroes.presentaition.adapter.PaginationListener;
import katsapov.heroes.presentaition.mvp.HeroContract;

import static katsapov.heroes.presentaition.adapter.PaginationListener.PAGE_START;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener, HeroContract.HeroView{

    HeroContract.Presenter mPresenter;

    public RecyclerView mRecyclerView;

    private HeroesRecyclerAdapter mAdapter;
    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private int totalPage = 10;
    private boolean isLoading = false;
    int itemCount = 0;
    Dialog dialog;
    private List<Hero> list = new ArrayList<Hero>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // fireYourAsyncTask();
        //HeroRepository.getHeroesList();
        SwipeRefreshLayout swipeRefresh = findViewById(R.id.swipeRefresh);
        swipeRefresh.setOnRefreshListener(this);

        RecyclerView mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        mRecyclerView.setHasFixedSize(true);
        swipeRefresh.setOnRefreshListener(this);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(layoutManager);

        mAdapter = new HeroesRecyclerAdapter(new OnHeroClickListener() {
            @Override
            public void onHeroClick(int position) {
//                mPresenter.onHeroDetailsClicked(position);
                openDialog();
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
                mPresenter.loadMore();

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
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.detachView();
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

                if (currentPage != PAGE_START) mAdapter.removeLoading();
                mAdapter.addItems(list);
                int i =  list.size();
                SwipeRefreshLayout swipeRefresh = findViewById(R.id.swipeRefresh);
                swipeRefresh.setRefreshing(false);

                // check is last page or not
                if (currentPage < totalPage) {
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
        currentPage = PAGE_START;
        isLastPage = false;
        mAdapter.clear();
        doApiCall();
    }





    public void setList(List<Hero> list) {
        this.list = list;
    }




//    private void fireYourAsyncTask() {
//        new HeroRepository.NetworkManager().execute();
//    }


    @Override
    public void updateHeroesList(List<Hero> heroesList) {
        mAdapter.addItems(heroesList);

    }

    @Override
    public void showIsLoading(Boolean isLoading) {

    }

    @Override
    public void showHeroDetails(Hero hero) {
    }


   public void openDialog(){
       final Dialog dialog = new Dialog(this);
       dialog.setContentView(R.layout.dialog_fragment);
       dialog.setTitle("Title...");

       TextView text = dialog.findViewById(R.id.textView);
       text.setText("Name");

       TextView text2 = dialog.findViewById(R.id.textView2);
       text2.setText("Gender");

       TextView text3 = dialog.findViewById(R.id.textView3);
       text3.setText("Culture");

       Button dialogButton = dialog.findViewById(R.id.dialogButtonOK);
       dialogButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               dialog.dismiss();
           }
       });

       dialog.show();
   }

    //openDetailsActivity(){}
}
