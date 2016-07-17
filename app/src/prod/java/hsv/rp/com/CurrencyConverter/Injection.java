package hsv.rp.com.CurrencyConverter;

import hsv.rp.com.CurrencyConverter.Model.CurrencyConverter;
import hsv.rp.com.CurrencyConverter.Model.CurrencyConverterAPIImpl;
import hsv.rp.com.CurrencyConverter.View.CurrencyContract;
import hsv.rp.com.CurrencyConverter.View.CurrencyPresenter;

/**
 * Dependency injection class
 *
 * @author ramesh130@gmail.com
 */
public class Injection {
    private final String LOG_TAG = Injection.class.getSimpleName();
    private static CurrencyPresenter mPresenter;

    /**
     * Provide the dependencies
     *
     * @param view View contract implementation
     * @return UserActionsListener contract implementation
     */
    public static CurrencyPresenter providePresenter(CurrencyContract.View view){
        if(mPresenter == null) {
            mPresenter = new CurrencyPresenter(view, new CurrencyConverter(new CurrencyConverterAPIImpl()));
        }
        return mPresenter;
    }
}
