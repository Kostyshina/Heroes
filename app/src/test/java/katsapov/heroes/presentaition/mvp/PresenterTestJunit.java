package katsapov.heroes.presentaition.mvp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.Shadows;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowConnectivityManager;
import org.robolectric.shadows.ShadowNetworkInfo;

import katsapov.heroes.BuildConfig;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;


@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, manifest = Config.NONE)
public class PresenterTestJunit {

    private ConnectivityManager connectivityManager;
    private ShadowConnectivityManager shadowConnectivityManager;

    @Before
    public void setUp() {
        connectivityManager = getConnectivityManager();
        shadowConnectivityManager = Shadows.shadowOf(connectivityManager);
    }

    @Test
    public void isOnlineWithWIFI_test() {
        NetworkInfo networkInfo = ShadowNetworkInfo.newInstance(NetworkInfo.DetailedState.CONNECTED, ConnectivityManager.TYPE_WIFI, 0, true, true);
        shadowConnectivityManager.setActiveNetworkInfo(networkInfo);
        NetworkInfo activeInfo = connectivityManager.getActiveNetworkInfo();
        assertTrue(activeInfo != null && activeInfo.isConnected());
        assertEquals(ConnectivityManager.TYPE_WIFI, activeInfo.getType());
    }

    private ConnectivityManager getConnectivityManager() {
        return (ConnectivityManager) RuntimeEnvironment.application.getSystemService(Context.CONNECTIVITY_SERVICE);
    }
}
