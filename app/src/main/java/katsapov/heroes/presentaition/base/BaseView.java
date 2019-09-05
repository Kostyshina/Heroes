package katsapov.heroes.presentaition.base;

public interface BaseView {
    void showLoader(boolean isLoading);
    void showError(String message);
}
