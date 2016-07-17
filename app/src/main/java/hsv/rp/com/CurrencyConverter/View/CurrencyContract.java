package hsv.rp.com.CurrencyConverter.View;

import android.support.annotation.NonNull;

/**
 * Interface that defines the contract between the View and the Presenter.
 *
 * @author ramesh130@gmail.com
 */
public interface CurrencyContract {
    interface View {
        /**
         * Set the progress indicator on UI ON/OFF
         *
         * @param active status to be set
         */
        void setProgressIndicator(boolean active);
    }

    interface UserActionsListener {
        /**
         * When currency/type is changes, this method is called to compute the result
         *
         * @param currency Currency input values
         * @param type Type of currency
         * @return Converted currency
         */
        String currencyChanged(@NonNull String currency, String type);
    }
}
