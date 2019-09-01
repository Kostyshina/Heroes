package katsapov.heroes.data.network;


import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;

import katsapov.heroes.data.entitiy.Hero;
import katsapov.heroes.presentaition.ui.MainActivity;

import static katsapov.heroes.data.entitiy.Constants.DATA_URL;

public class NetworkManager {

    public static class LoadStringsAsync extends AsyncTask<Void, Void, NetworkResponse> {
//    public static class LoadStringsAsync extends AsyncTask<Void, Void, List<Hero>> {

        //@SuppressLint("StaticFieldLeak")
        private WeakReference<MainActivity> mainActivityWeakReference;

        public LoadStringsAsync(MainActivity activity) {
            this.mainActivityWeakReference = new WeakReference(activity);
        }

        @Override
        protected NetworkResponse doInBackground(Void... arg0) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;
            String line = null;
            String responeJson;

            URL uri = null;
            try {
                uri = new URL(DATA_URL);
                connection = (HttpURLConnection) uri.openConnection();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
//            try {
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            try {
                connection.setRequestMethod("GET");
            } catch (ProtocolException e) {
                e.printStackTrace();
            }
            connection.setConnectTimeout(8000);
            connection.setReadTimeout(8000);
            InputStream in = null;
            try {
                in = connection.getInputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            StringBuilder response = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(in));
            while (true) {
                try {
                    if (!((line = reader.readLine()) != null)) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                response.append(line);
            }

            responeJson = response.toString();
            Log.d("response", response.toString());

            Type listType = new TypeToken<List<Hero>>() {}.getType();

            List<Hero> listOfHeroes = new Gson().fromJson(responeJson, listType);

            return  new NetworkResponse();
//            return listOfHeroes;
        }

//        @Override
//        protected void onPostExecute(List<Hero> str) {
//            super.onPostExecute(str);
//          // activity.setList(str);
//        }
    }
}