package hsv.rp.com.CurrencyConverter.Model;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;

import hsv.rp.com.CurrencyConverter.Platform.Platform;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Implement the web API layer. Manages {@link android.app.IntentService} for background network call.
 *
 * @author ramesh130@gmail.com
 */
public class CurrencyConverterAPIImpl extends BroadcastReceiver implements CurrencyConverterAPI{
    private final String LOG_TAG = CurrencyConverterAPIImpl.class.getSimpleName();
    private final String SERVER_URL = "http://api.fixer.io/latest?base=AUD";
    private CurrencyServiceCallback mCallback;

    @Override
    public void getCurrencyRates(CurrencyServiceCallback callback) {
        mCallback = callback;

        Context ctx = Platform.getContext();
        checkNotNull(ctx, "Context cannot be Null.");

        Intent intent = new Intent(ctx, CurrencyService.class);
        intent.setData(Uri.parse(SERVER_URL));
        ctx.startService(intent);

        // The filter's action is BROADCAST_ACTION
        IntentFilter intentFilter = new IntentFilter(
                CurrencyService.BROADCAST_ACTION);
        LocalBroadcastManager.getInstance(ctx).registerReceiver(
                this,
                intentFilter);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String resp = intent.getStringExtra(CurrencyService.EXTENDED_DATA_STATUS);
        mCallback.onLoaded(resp);
    }
}
