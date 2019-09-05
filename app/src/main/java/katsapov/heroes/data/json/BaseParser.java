package katsapov.heroes.data.json;

public interface BaseParser<T> {
    T parseData(String json);
}
