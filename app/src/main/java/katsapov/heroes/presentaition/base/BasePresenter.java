package katsapov.heroes.presentaition.base;

public class BasePresenter<T extends BaseView> {

    protected T mView;

    public void attachView(T view) {
        mView = view;
    }

    public void detachView() {
        mView = null;
    }
}
