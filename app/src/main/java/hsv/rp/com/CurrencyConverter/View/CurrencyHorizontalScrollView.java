package hsv.rp.com.CurrencyConverter.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
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
        public void onScrollItemChange();
    }

    private onScrollItemChangeListener mOnScrollItemChangeListener;
    private TextView mLastFocussedView;

    public CurrencyHorizontalScrollView(Context context) {
        super(context);
    }

    public CurrencyHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CurrencyHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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

        mLastFocussedView = (TextView) findViewById(R.id.horizontal_scroll_textview_eur);
        scrollTo(mLastFocussedView.getLeft(), 0);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int xoff = getScrollX();

        Rect centerBound = new Rect();
        TextView sampletv = (TextView) findViewById(R.id.horizontal_scroll_textview_gbp);

        LinearLayout view = (LinearLayout) findViewById(R.id.container_layout);
        view.getHitRect(centerBound);
        int c = centerBound.centerX();
        centerBound.left = c - sampletv.getWidth();
        centerBound.right = c + sampletv.getWidth();

        Rect childBounds = new Rect();
        if (view instanceof ViewGroup) {
            int chilCnt = ((ViewGroup) view).getChildCount();
            for (int i = 0; i < chilCnt; i++) {
                ((ViewGroup) view).getChildAt(i).getHitRect(childBounds);
                childBounds.left -= xoff - 2 * sampletv.getWidth();
                childBounds.right -= xoff - 2 * sampletv.getWidth();
                if (centerBound.contains(childBounds)) {
                    if (((ViewGroup) view).getChildAt(i) instanceof TextView) {
                        TextView cv = (TextView) ((ViewGroup) view).getChildAt(i);
                        cv.setTextColor(getResources().getColor(R.color.colorText));
                    }
                } else {
                    if (((ViewGroup) view).getChildAt(i) instanceof TextView) {
                        TextView cv = (TextView) ((ViewGroup) view).getChildAt(i);
                        cv.setTextColor(getResources().getColor(R.color.colorTextShade));
                    }
                }
            }
        }
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        TextView curView = getCurrentFocusedView();
        if (curView != null) {
            if (!mLastFocussedView.equals(curView)) {
                mLastFocussedView = curView;

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
    private @Nullable TextView getCurrentFocusedView() {
        int xoff = getScrollX();

        Rect centerBound = new Rect();
        TextView sampletv = (TextView) findViewById(R.id.horizontal_scroll_textview_gbp);

        LinearLayout view = (LinearLayout) findViewById(R.id.container_layout);
        view.getHitRect(centerBound);
        int c = centerBound.centerX();
        centerBound.left = c - sampletv.getWidth();
        centerBound.right = c + sampletv.getWidth();

        Rect childBounds = new Rect();
        if (view instanceof ViewGroup) {
            int chilCnt = ((ViewGroup) view).getChildCount();
            for (int i = 0; i < chilCnt; i++) {
                ((ViewGroup) view).getChildAt(i).getHitRect(childBounds);
                childBounds.left -= xoff - 2 * sampletv.getWidth();
                childBounds.right -= xoff - 2 * sampletv.getWidth();
                if (centerBound.contains(childBounds)) {
                    if (((ViewGroup) view).getChildAt(i) instanceof TextView) {
                        TextView cv = (TextView) ((ViewGroup) view).getChildAt(i);
                        return cv;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Get the text of the focussed view.
     *
     * @return Text from the selected view
     */
    public String getSelectedViewText() {
        checkNotNull(mLastFocussedView, "mLastFocussedView cannot be Null.");
        return mLastFocussedView.getText().toString();
    }
}
