package katsapov.heroes.data;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import static katsapov.heroes.data.entitiy.Constants.DATA_URL;

public class NetworkManager {

    public static class getDataStringFromApi extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... arg0) {
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
            InputStream in = null;
            try {
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(8000);
                connection.setReadTimeout(8000);
                in = connection.getInputStream();
            } catch (ProtocolException e) {
                e.printStackTrace();
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
            return responeJson;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
