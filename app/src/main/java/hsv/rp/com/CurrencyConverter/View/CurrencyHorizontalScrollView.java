package hsv.rp.com.CurrencyConverter.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import hsv.rp.com.horizontalscrollview.R;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Custom component that extends the {@link HorizontalScrollView} to provide following formatting features required by
 * the app-
 * 1. Change the text color of un-focused and focused elements
 * 2. Callback when the selection changes
 * 3. method to access the text of the selected view
 *
 * @author ramesh130@gmail.com
 */
public class CurrencyHorizontalScrollView extends HorizontalScrollView {
    private final String LOG_TAG = CurrencyHorizontalScrollView.class.getSimpleName();

    /**
     * Interface that defines the contract for listening to the scroll change event.
     */
    public interface onScrollItemChangeListener {
        /**
         * Callback when focused item changes
         */
        public void onScrollItemChange();
    }

    // Hold views for easy access
    class ViewHolder {
        public TextView[] mTextViews;
        public LinearLayout mContainer;
        public int mSelectedColor;
        public int mUnSelectedColor;
    }
    private ViewHolder mHolder;
    private onScrollItemChangeListener mOnScrollItemChangeListener;
    private TextView mLastFocusedView;
    private int mOrigin = -1;

    public CurrencyHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mHolder = new ViewHolder();
        mHolder.mSelectedColor = ContextCompat.getColor(context, R.color.colorText);
        mHolder.mUnSelectedColor = ContextCompat.getColor(context, R.color.colorTextShade);
    }

    /**
     * Set the scoll item change listener.
     *
     * @param listen To listen to scroll changes
     */
    public void setOnScrollItemChangeListener(@NonNull onScrollItemChangeListener listen) {
        mOnScrollItemChangeListener = listen;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // Get all the frequently used views in the holder
        holdViews();

        // Center the scrollview
        TextView tv = mHolder.mTextViews[mHolder.mTextViews.length / 2 - 1];
        scrollTo(tv.getLeft(), 0);
        if(mLastFocusedView == null){
            mLastFocusedView = tv;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for(TextView tv : mHolder.mTextViews){
            tv.setTextColor(mHolder.mUnSelectedColor);
        }

        mLastFocusedView.setTextColor(mHolder.mSelectedColor);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        TextView curView = getCurrentFocusedView();
        if (curView != null) {
            if (!mLastFocusedView.equals(curView)) {
                mLastFocusedView = curView;

                checkNotNull(mOnScrollItemChangeListener, "onScrollItemChangeListener cannot be Null.");
                mOnScrollItemChangeListener.onScrollItemChange();
            }
        }
    }

    /**
     * Get the view which is currently in focus (between the top and bottom markers)
     *
     * @return The TextView which is currently in focus.
     */
    private
    @Nullable
    TextView getCurrentFocusedView() {
        int xoff = getScrollX();
        if(mOrigin < 0 ){
            mOrigin = xoff;
        }

        Rect centerBound = new Rect();
        TextView sampletv = mHolder.mTextViews[0];

        LinearLayout view = mHolder.mContainer;
        view.getHitRect(centerBound);
        int c = centerBound.centerX();
        centerBound.left = c - sampletv.getWidth();
        centerBound.right = c + sampletv.getWidth();

        Rect childBounds = new Rect();
        for(TextView tv : mHolder.mTextViews){
            tv.getHitRect(childBounds);
            childBounds.left += (mOrigin - xoff);
            childBounds.right += (mOrigin - xoff);
            if (centerBound.contains(childBounds)) {
                return tv;
            }
        }
        return null;
    }

    private void holdViews() {
        if(mHolder.mTextViews == null) {
            LinearLayout view = (LinearLayout) findViewById(R.id.horizontal_scroll_linearlayout_container);
            mHolder.mContainer = view;

            if (view instanceof ViewGroup) {
                int childCount = ((ViewGroup) view).getChildCount();
                mHolder.mTextViews = new TextView[childCount];
                for (int i = 0; i < childCount; i++) {
                    View tv = ((ViewGroup) view).getChildAt(i);
                    if (tv instanceof TextView) {
                        mHolder.mTextViews[i] = (TextView) tv;
                    }
                }
            }
        }
    }

    /**
     * Get the text of the focused view.

     * @return Text from the selected view
     */
    public String getSelectedViewText() {
        checkNotNull(mLastFocusedView, "mLastFocusedView cannot be Null.");
        return mLastFocusedView.getText().toString();
    }
}
