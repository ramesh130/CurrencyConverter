package hsv.rp.com.CurrencyConverter.View;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.LinkedHashMap;

import hsv.rp.com.CurrencyConverter.Injection;
import hsv.rp.com.CurrencyConverter.Platform.Platform;
import hsv.rp.com.horizontalscrollview.R;

/**
 * Currency converter Activity
 *
 * @author ramesh130@gmail.com
 */
public class CurrencyConverterActivity extends AppCompatActivity implements CurrencyContract.View,
        CurrencyEditText.onEditTextChangeListener, CurrencyHorizontalScrollView.onScrollItemChangeListener {
    private final String LOG_TAG = CurrencyConverterActivity.class.getSimpleName();
    private final boolean HELVETICA = false;
    private CurrencyContract.UserActionsListener mUserActionListener;
    private CurrencyEditText mCurrencyEditText;
    private CurrencyHorizontalScrollView mCurrencyHorizontalScrollView;
    private TextView mConvertedCurrency;
    private LinkedHashMap<String, String> mCurrencyMap = new LinkedHashMap<String, String>();

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.currency_converter);

        // JPY currency symbol appears corrupted, so not using it
        if(HELVETICA) {
            final Typeface font = Typeface.createFromAsset(getAssets(), "fonts/Helvetica.ttf");
            // Set fonts of all Text/EditViews
            setFont(font, findViewById(R.id.currency_converter_layout_top));
        }

        mCurrencyEditText = (CurrencyEditText) findViewById(R.id.currency_converter_edittext_input);
        mCurrencyEditText.setOnEditTextChangeListener(this);

        mCurrencyHorizontalScrollView = (CurrencyHorizontalScrollView) findViewById(R.id.horizontalScrollView);
        mCurrencyHorizontalScrollView.setOnScrollItemChangeListener(this);

        mConvertedCurrency = (TextView) findViewById(R.id.currency_converter_textview_computed);

        // Context needed for tasks in other layers
        Platform.setContext(this);
        mUserActionListener = Injection.providePresenter(this);

        // Initialize the currency string to symbol mapping
        initLookup();
    }

    @Override
    public void setProgressIndicator(boolean active) {

    }

    @Override
    public void onEditTextChange() {
        updateCalculatedCurrency();
    }

    private void updateCalculatedCurrency() {
        String currency = mCurrencyHorizontalScrollView.getSelectedViewText();
        String num = mCurrencyEditText.getNumber();

        if (num != null && !num.trim().equals("")) {
            String val = mUserActionListener.currencyChanged(num, currency);
            mConvertedCurrency.setText(getSymbol(currency) + val);
        } else {
            mConvertedCurrency.setText(getSymbol(currency));
        }
    }

    @Override
    public void onScrollItemChange() {
        updateCalculatedCurrency();
    }

    private void initLookup() {
        String[] keys = this.getResources().getStringArray(R.array.currency_name);
        String[] values = this.getResources().getStringArray(R.array.currency_symbol);

        for (int i = 0; i < Math.min(keys.length, values.length); ++i) {
            mCurrencyMap.put(keys[i], values[i]);
        }
    }

    private String getSymbol(String currency) {
        return mCurrencyMap.get(currency);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        mCurrencyMap = null;
    }

    private void setFont(final Typeface tf, final View view) {
        if (view instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) view;
            for (int i = 0; i < vg.getChildCount(); i++) {
                View child = vg.getChildAt(i);
                setFont(tf, child);
            }
        } else if (view instanceof TextView) {
            ((TextView) view).setTypeface(tf);
        }
    }
}
