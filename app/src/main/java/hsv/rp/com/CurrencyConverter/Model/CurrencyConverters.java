package hsv.rp.com.CurrencyConverter.Model;

/**
 * Interface that defines Contract for interacting with the model/business logic.
 *
 * @author ramesh130@gmail.com
 */
public interface CurrencyConverters {
    /**
     * Convert the currency to a particular type
     *
     * @param currency Value of source currency
     * @param type Type to which source has to be converted
     * @return Converted currency
     */
    String convert(String currency, String type);
}
