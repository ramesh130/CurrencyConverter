package hsv.rp.com.CurrencyConverter.Model;

import android.app.IntentService;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 *
 * @author ramesh130@gmail.com
 */
public class CurrencyService extends IntentService {
    private final String LOG_TAG = CurrencyService.class.getSimpleName();

    // Defines a custom Intent action
    public static final String BROADCAST_ACTION =
            "com.rp.currencyconverter.model.BROADCAST";

    // Defines the key for the status "extra" in an Intent
    public static final String EXTENDED_DATA_STATUS =
            "com.rp.currencyconverter.model.STATUS";

    public CurrencyService() {
        super("CurrencyService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String url = intent.getDataString();
        String resp = talkToServer(url);

        //Creates a new Intent containing a Uri object
        Intent localIntent =
                new Intent(BROADCAST_ACTION)
                        // Puts the status into the Intent
                        .putExtra(EXTENDED_DATA_STATUS, resp);
        // Broadcasts the Intent to receivers in this app.
        LocalBroadcastManager.getInstance(this).sendBroadcast(localIntent);
    }

    private String talkToServer(String baseUrl){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        // Will contain the raw JSON response as a string
        String responseStr = null;

        try {
            URL url = new URL(baseUrl);

            // Create the request to fixer.io, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // To ease debugging
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.
                return null;
            }
            return buffer.toString();
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        return null;
    }
}
