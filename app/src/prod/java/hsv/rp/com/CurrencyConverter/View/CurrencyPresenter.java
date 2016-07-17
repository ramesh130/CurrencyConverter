package hsv.rp.com.CurrencyConverter.View;

import android.support.annotation.NonNull;

import hsv.rp.com.CurrencyConverter.Model.CurrencyConverters;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Presenter layer to initiate the conversion when UI changes
 *
 * @author ramesh130@gmail.com
 */
public class CurrencyPresenter implements CurrencyContract.UserActionsListener {
    private final String LOG_TAG = CurrencyPresenter.class.getSimpleName();
    private final CurrencyContract.View mView;
    private final CurrencyConverters mCurrencyConverters;

    /**
     * Constructor for CurrencyPresenter
     *
     * @param view View contract implementation
     * @param converters CurrencyConverters contract implementation
     */
    public CurrencyPresenter(@NonNull CurrencyContract.View view, @NonNull CurrencyConverters converters){
        mView = checkNotNull(view, "View cannot be Null.");
        mCurrencyConverters = checkNotNull(converters, "Converter cannot be Null.");
    }

    @Override
    public String currencyChanged(@NonNull String currency, String type) {
        checkNotNull(currency, "Currency cannot be Null.");
        return mCurrencyConverters.convert(currency, type);
    }
}
