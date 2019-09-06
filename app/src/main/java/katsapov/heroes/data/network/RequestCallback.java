package katsapov.heroes.data.network;

public interface RequestCallback<T> {
    void onFailure(ApiException exception);
    void onSuccess(T response);
}
