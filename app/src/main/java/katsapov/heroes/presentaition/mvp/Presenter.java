package katsapov.heroes.presentaition.mvp;


import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.util.List;

import katsapov.heroes.data.NetworkManager;
import katsapov.heroes.data.entitiy.Hero;
import katsapov.heroes.presentaition.mvp.HeroContract.HeroView;
import katsapov.heroes.presentaition.ui.MainActivity;

public class Presenter implements HeroContract.Presenter {

    private HeroContract.HeroView mView;
    private List<Hero> heroList;


    @Override
    public void attachView(HeroView view) {
        mView = view;
    }

    @Override
    public void detachView() {
        mView = null;
    }

    @Override
    public void setList(List<Hero> list) {
        this.heroList = list;
    }

    @Override
    public boolean isOnline(Activity activity) {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void getDataFromApi(MainActivity activity) {
        new NetworkManager.getDataStringFromApi(activity).execute();
    }

}
