package katsapov.heroes.data;

import android.os.AsyncTask;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import katsapov.heroes.domain.Constants;
import katsapov.heroes.domain.entity.ApiException;
import katsapov.heroes.domain.entity.Response;
import katsapov.heroes.data.json.BaseParser;

public class NetworkManager {

    enum RequestMethod {
        GET("GET");

        public String value;

        RequestMethod(String value) {
            this.value = value;
        }
    }

    <Res> void makeRequest(RequestMethod requestMethod, String path, BaseParser<Res> parser, RequestCallback<Res> callback) {
        new Request<>(parser, callback).execute(requestMethod.value, path);
    }

    private static class Request<Res> extends AsyncTask<String, Void, Request.ResponsePair<Res>> {

        private RequestCallback<Res> callback;
        private BaseParser<Res> parser;

        Request(BaseParser<Res> parser, RequestCallback<Res> callback) {
            this.parser = parser;
            this.callback = callback;
        }

        @Override
        protected ResponsePair<Res> doInBackground(String... args) {
            HttpURLConnection connection = null;
            BufferedReader reader;
            String line = null;
            String responseJson;
            ApiException exception = null;
            URL uri;
            String request = Constants.BASE_URL.concat(args[1]);
            Log.d("request", request);
            try {
                uri = new URL(request);
                connection = (HttpURLConnection) uri.openConnection();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert connection != null;

            InputStream inResponse = null;
            try {
                connection.setRequestMethod(args[0]);
                connection.setConnectTimeout(8000);
                connection.setReadTimeout(8000);
                inResponse = connection.getInputStream();
            } catch (Exception exc) {
                exception = new ApiException(new Response(exc.getMessage()), exc);
            }

            if (inResponse == null) {
                connection.disconnect();
                return new ResponsePair<>(null, exception);
            }
            StringBuilder response = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(inResponse));
            while (true) {
                try {
                    if ((line = reader.readLine()) == null) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                response.append(line);
            }
            responseJson = response.toString();
            Res responseObject = parser.parseData(responseJson);
            Log.d("response", response.toString());
            connection.disconnect();
            try {
                reader.close();
                inResponse.close();
            } catch (IOException exc) {
                // TODO handle somehow
            }

            return new ResponsePair<>(responseObject, null);
        }

        @Override
        protected void onPostExecute(Request.ResponsePair<Res> res) {
            super.onPostExecute(res);
            if (res.exception != null) {
                callback.onFailure(res.exception);
            } else {
                callback.onSuccess(res.response);
            }
        }

        private static class ResponsePair<T> {
            private T response;
            private ApiException exception;

            ResponsePair(@Nullable T response, @Nullable ApiException exception) {
                this.response = response;
                this.exception = exception;
            }
        }
    }

    public interface RequestCallback<T> {
        void onFailure(ApiException exception);
        void onSuccess(T response);
    }
}
