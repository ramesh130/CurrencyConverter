package hsv.rp.com.CurrencyConverter.Model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Converts the input currency to the desired one. Calls the service to fetch the conversion rates.
 *
 * @author ramesh130@gmail.com
 */
public class CurrencyConverter implements CurrencyConverters, CurrencyConverterAPI.CurrencyServiceCallback{
    private final String LOG_TAG = CurrencyConverter.class.getSimpleName();
    private JSONObject mRates;
    private CurrencyConverterAPI mCurrencyConverterService;
    DecimalFormat mDec = new DecimalFormat("0.00");

    public CurrencyConverter(CurrencyConverterAPI currencyConverter){
        mCurrencyConverterService = currencyConverter;

        // Call converter service to get the latest conversion rates
        mCurrencyConverterService.getCurrencyRates(this);
    }

    @Override
    public String convert(String currency, String type) {
        checkNotNull(mRates, "rates are Null.");

        try{
            double rate =  mRates.getDouble(type);

            String format = mDec.format(rate * Double.valueOf(currency));
            return String.valueOf(format);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onLoaded(String resp) {
        parseJSON(resp);
    }

    private void parseJSON(String jsonString){
        final String FIX_RATES = "rates";
        try{
            JSONObject json = new JSONObject(jsonString);
            mRates = json.getJSONObject(FIX_RATES);
        } catch (JSONException e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }
    }
}
