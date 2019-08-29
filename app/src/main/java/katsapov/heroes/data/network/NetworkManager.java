package katsapov.heroes.data.network;


import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import static katsapov.heroes.data.entitiy.Constants.DATA_URL;

public class NetworkManager {

    //MAKE return string (Future*)
    public static void sendRequestWithHttpURLConnection() {
        new Thread(new Runnable() {
            String line;
            String responeJson;

            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;

                try {
                    URL uri = new URL(DATA_URL);
                    connection = (HttpURLConnection) uri.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();

                    Scanner scanner = new Scanner(in);
                    String line = scanner.next();

                    StringBuilder response = new StringBuilder();
                    reader = new BufferedReader(new InputStreamReader(in));
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }

                    //string with data
                    responeJson = response.toString();


                    Log.d("finish", response.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }
}