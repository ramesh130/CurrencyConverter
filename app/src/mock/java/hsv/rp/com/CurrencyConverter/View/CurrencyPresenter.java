package hsv.rp.com.CurrencyConverter.View;

import android.support.annotation.NonNull;

import hsv.rp.com.CurrencyConverter.Model.CurrencyConverters;

/**
 * @author ramesh130@gmail.com
 */
public class CurrencyPresenter implements CurrencyContract.UserActionsListener{

    public CurrencyPresenter(@NonNull CurrencyContract.View view, @NonNull CurrencyConverters converters){

    }

    @Override
    public String currencyChanged(@NonNull String currency, String type) {
        return "8423";
    }
}
