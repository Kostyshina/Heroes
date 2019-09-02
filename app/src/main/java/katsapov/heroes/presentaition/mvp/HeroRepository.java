package katsapov.heroes.presentaition.mvp;

import android.os.Handler;

import java.util.ArrayList;
import java.util.List;

import katsapov.heroes.data.entitiy.Hero;

public class HeroRepository {

    private static HeroRepository INSTANCE = null;

    static HeroRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new HeroRepository();
        }
        return INSTANCE;
    }


    void getHeroes(int page, int pagePerRequest, final HeroRequestCallbac callbac) {
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                callbac.onReqestFinished(new ArrayList<Hero>());
            }
        });


    }


    public interface HeroRequestCallbac {
        void onReqestFinished(List<Hero> heroes);
        void onError(Exception e);
    }
}


