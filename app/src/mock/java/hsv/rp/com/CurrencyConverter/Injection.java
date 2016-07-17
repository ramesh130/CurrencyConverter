package hsv.rp.com.CurrencyConverter;

import hsv.rp.com.CurrencyConverter.Model.CurrencyConverter;
import hsv.rp.com.CurrencyConverter.Model.CurrencyConverterAPIImpl;
import hsv.rp.com.CurrencyConverter.View.CurrencyContract;
import hsv.rp.com.CurrencyConverter.View.CurrencyPresenter;

/**
 * @author ramesh130@gmail.com
 */
public class Injection {
    private static CurrencyPresenter mPresenter;

    public static CurrencyPresenter providePresenter(CurrencyContract.View view){
        if(mPresenter == null) {
            mPresenter = new CurrencyPresenter(view, new CurrencyConverter(new CurrencyConverterAPIImpl()));
        }
        return mPresenter;
    }
}
