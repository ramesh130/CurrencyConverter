package hsv.rp.com.CurrencyConverter.Model;

/**
 * Interface that defines Contract for interacting with the web API layer.
 *
 * @author ramesh130@gmail.com
 */
public interface CurrencyConverterAPI {
    interface CurrencyServiceCallback{
        /**
         * Callback when the currency values are received from the API
         *
         * @param resp The string containing server response
         */
        void onLoaded(String resp);
    }

    /**
     * Get the currency rates from the server
     *
     * @param callback Callback when request completes
     */
    void getCurrencyRates(CurrencyServiceCallback callback);
}
