package hsv.rp.com.CurrencyConverter.View;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.EditText;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

import hsv.rp.com.horizontalscrollview.R;

/**
 * Custom component that extends the {@link EditText} to provide following formatting features required by the app-
 * 1. $ prefix
 * 2. Comma separation for currency text
 * 3. Limit the decimal places to 2
 * 4. Callback when the text changes
 * 5. method to retrieve the unformatted currency string
 *
 * @author ramesh130@gmail.com
 */
public class CurrencyEditText extends EditText{
    private final String LOG_TAG = CurrencyEditText.class.getSimpleName();

    /**
     * Interface that defines the contract to listen to the changes in the EditText view.
     */
    public interface onEditTextChangeListener{
        /**
         * Inform when the text changes
         */
        public void onEditTextChange();
    }

    private String mDefaultString;
    private onEditTextChangeListener mEditTextChangeListener;

    public CurrencyEditText(Context context, AttributeSet attrs) {
        super(context, attrs);

        // Add TextWatcher to listen to changes in the EditText view
        addTextWatcher();

        // Set the default string in the EditText view
        mDefaultString = context.getString(R.string.usd);
        setDefaultText();
    }

    public CurrencyEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CurrencyEditText(Context context) {
        super(context);
    }

    /**
     * Set the listener for the changes in EidtText
     *
     * @param listen To listen to the changes in EditText
     */
    public void setOnEditTextChangeListener(@NonNull onEditTextChangeListener listen){
        mEditTextChangeListener = listen;
    }

    private void addTextWatcher(){
        TextWatcher tw = new TextWatcher() {
            boolean mEditing = false;
            DecimalFormat mDec = new DecimalFormat("0.00");

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(getText().length() == 0) {
                    setDefaultText();
                }else{
                    if(!mEditing) {
                        // To filter out the multiple calls to this method
                        mEditing = true;

                        String digits = getNumber();
                        if(digits.length() > 0 && !digits.endsWith(".")) {
                            if(digits.contains(".")){
                                String[] split = digits.split("\\.");
                                if(split.length >= 2 && split[1].length() > 2){
                                    digits = digits.substring(0, digits.length() - 1);
                                }
                            }

                            // Must be able to handle big numbers
                            BigDecimal bd = new BigDecimal(digits);

                            //Currency formatting
                            NumberFormat nf = NumberFormat.getInstance(Locale.ENGLISH);
                            ((DecimalFormat) nf).applyPattern("###,###.##");

                            //Sanity check for $
                            if(!editable.toString().startsWith(mDefaultString)) {
                                setText(mDefaultString + nf.format(bd));
                                setSelection(mDefaultString.length());
                            }else {
                                editable.replace(1, editable.length(), nf.format(bd));
                            }
                        }

                        mEditing = false;
                    }
                }

                // Inform anyone listening to the text chnage event
                if(mEditTextChangeListener != null) {
                    mEditTextChangeListener.onEditTextChange();
                }
            }
        };

        addTextChangedListener(tw);
    }

    private void setDefaultText(){
        setText(mDefaultString);
        setSelection(mDefaultString.length());
    }

    /**
     * Get unformatted string
     *
     * @return Unformatted string
     */
    public String getNumber(){
        String digits = getText().toString().replace(mDefaultString, "");
        if(digits.contains(",")) {
            digits = digits.toString().replace(",", "");
        }
        return digits;
    }
}
