package katsapov.heroes.presentaition.mvp;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;

import java.util.List;

import katsapov.heroes.R;
import katsapov.heroes.data.NetworkManager;
import katsapov.heroes.data.entitiy.Constants;
import katsapov.heroes.data.entitiy.Hero;
import katsapov.heroes.data.json.HeroParser;
import katsapov.heroes.presentaition.mvp.HeroContract.HeroView;

public class Presenter implements HeroContract.Presenter {

    private HeroView mView;
    private NetworkManager networkManager = new NetworkManager();
    private HeroParser heroParser = new HeroParser(new Gson());

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
    }

    @Override
    public boolean isOnline(Activity activity) {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        assert cm != null;
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    @Override
    public void getDataOnAdapter(Activity activity, int page, NetworkManager.RequestCallback<List<Hero>> callback) {
        boolean isHasInternetConnection = isOnline(activity);
        if (isHasInternetConnection) {
            String request = Constants.CHARACTERS_ENDPOINT
                    .concat("?")
                    .concat(Constants.CHARACTERS_PARAM_PAGE).concat(String.valueOf(page))
                    .concat("&")
                    .concat(Constants.CHARACTERS_PARAM_PAGE_SIZE).concat(String.valueOf(Constants.PAGE_SIZE));
            networkManager.makeRequest(NetworkManager.RequestMethod.GET, request, heroParser, callback);
        } else if (mView != null) {
            mView.showError(activity, R.string.error_connection);
        }
    }
}
