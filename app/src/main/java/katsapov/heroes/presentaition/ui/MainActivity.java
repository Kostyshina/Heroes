package katsapov.heroes.presentaition.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import katsapov.heroes.R;
import katsapov.heroes.data.network.NetworkManager;

public class MainActivity extends AppCompatActivity {

    String url = "https://anapioficeandfire.com/api/characters/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            NetworkManager.sendRequestWithHttpURLConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //String result = NetworkManager.showResponse()
    }
}
