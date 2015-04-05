/*
 * Copyright (C) 2015 Ihsan Isik
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tr.xip.errorview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Map;

/**
 * @author Ihsan Isik
 *         <p/>
 *         A custom view that displays an error image, a title, and a subtitle given an HTTP status
 *         code. It can be used for various other purposes like displaying other kinds of errors or
 *         just messages with images.
 * @see #setError(int)
 * <p/>
 */
public class ErrorView extends LinearLayout {

    private Context mContext;

    private ImageView mErrorImageView;
    private TextView mTitleTextView;
    private TextView mSubtitleTextView;
    private TextView mRetryButton;

    private RetryListener mListener;

    public ErrorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray a = mContext.getTheme().obtainStyledAttributes(attrs, R.styleable.ErrorView, 0, 0);

        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.error_view_layout, this, true);

        mErrorImageView = (ImageView) findViewById(R.id.error_image);
        mTitleTextView = (TextView) findViewById(R.id.error_title);
        mSubtitleTextView = (TextView) findViewById(R.id.error_subtitle);
        mRetryButton = (TextView) findViewById(R.id.error_retry);

        String mTitle;
        int mTitleColor;

        String mSubtitle;
        int mSubtitleColor;

        boolean mShowTitle;
        boolean mShowSubtitle;
        boolean mShowRetryButton;

        int mRetryButtonBackground;
        int mRetryButtonTextColor;

        int mErrorImageRes;

        try {
            mErrorImageRes = a.getResourceId(R.styleable.ErrorView_ev_errorImage, R.drawable.error_view_cloud);
            mTitle = a.getString(R.styleable.ErrorView_ev_title);
            mTitleColor = a.getColor(R.styleable.ErrorView_ev_titleColor,
                    getResources().getColor(R.color.error_view_text));
            mSubtitle = a.getString(R.styleable.ErrorView_ev_subtitle);
            mSubtitleColor = a.getColor(R.styleable.ErrorView_ev_subtitleColor,
                    getResources().getColor(R.color.error_view_text_light));
            mShowTitle = a.getBoolean(R.styleable.ErrorView_ev_showTitle, true);
            mShowSubtitle = a.getBoolean(R.styleable.ErrorView_ev_showSubtitle, true);
            mShowRetryButton = a.getBoolean(R.styleable.ErrorView_ev_showRetryButton, true);
            mRetryButtonBackground = a.getResourceId(R.styleable.ErrorView_ev_retryButtonBackground,
                    R.drawable.error_view_retry_button_background);
            mRetryButtonTextColor = a.getColor(R.styleable.ErrorView_ev_retryButtonTextColor,
                    getResources().getColor(R.color.error_view_text_dark));

            if (mErrorImageRes != 0)
                setImageResource(mErrorImageRes);

            if (mTitle != null)
                setTitle(mTitle);

            if (mSubtitle != null)
                setSubtitle(mSubtitle);

            if (!mShowTitle)
                mTitleTextView.setVisibility(GONE);

            if (!mShowSubtitle)
                mSubtitleTextView.setVisibility(GONE);

            if (!mShowRetryButton)
                mRetryButton.setVisibility(GONE);

            mTitleTextView.setTextColor(mTitleColor);
            mSubtitleTextView.setTextColor(mSubtitleColor);

            mRetryButton.setTextColor(mRetryButtonTextColor);
            mRetryButton.setBackgroundResource(mRetryButtonBackground);
        } finally {
            a.recycle();
        }

        mRetryButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) mListener.onRetry();
            }
        });
    }

    /**
     * Attaches a listener that to the view that reports retry events.
     *
     * @param listener {@link tr.xip.errorview.ErrorView.RetryListener} to be notified when a retry
     *                 event occurs.
     */
    public void setOnRetryListener(RetryListener listener) {
        this.mListener = listener;
    }

    /**
     * Sets error subtitle to the description of the given HTTP status code
     *
     * @param errorCode HTTP status code
     */
    public void setError(int errorCode) {
        Map<Integer, String> mCodes = HttpStatusCodes.getCodesMap();

        if (mCodes.containsKey(errorCode))
            setSubtitle(errorCode + " " + mCodes.get(errorCode));
    }

    /**
     * Sets error image to a given drawable resource
     *
     * @param res drawable resource.
     */
    public void setImageResource(int res) {
        mErrorImageView.setImageResource(res);
    }

    /**
     * Sets the error image to a given {@link android.graphics.drawable.Drawable}.
     *
     * @param drawable {@link android.graphics.drawable.Drawable} to use as error image.
     */
    public void setImageDrawable(Drawable drawable) {
        mErrorImageView.setImageDrawable(drawable);
    }

    /**
     * Sets the error image to a given {@link android.graphics.Bitmap}.
     *
     * @param bitmap {@link android.graphics.Bitmap} to use as error image.
     */
    public void setImageBitmap(Bitmap bitmap) {
        mErrorImageView.setImageBitmap(bitmap);
    }

    /**
     * Sets the error title to a given {@link java.lang.String}.
     *
     * @param text {@link java.lang.String} to use as error title.
     */
    public void setTitle(String text) {
        mTitleTextView.setText(text);
    }

    /**
     * Sets the error title to a given string resource.
     *
     * @param res string resource to use as error title.
     */
    public void setTitle(int res) {
        mTitleTextView.setText(res);
    }

    /**
     * Returns the current title string.
     */
    public String getTitle() {
        return mTitleTextView.getText().toString();
    }

    /**
     * Sets the error title text to a given color.
     *
     * @param res color resource to use for error title text.
     */
    public void setTitleColor(int res) {
        mTitleTextView.setTextColor(res);
    }

    /**
     * Returns the current title text color.
     */
    public int getTitleColor() {
        return mTitleTextView.getCurrentTextColor();
    }

    /**
     * Sets the error subtitle to a given {@link java.lang.String}.
     *
     * @param exception {@link java.lang.String} to use as error subtitle.
     */
    public void setSubtitle(String exception) {
        mSubtitleTextView.setText(exception);
    }

    /**
     * Sets the error subtitle to a given string resource.
     *
     * @param res string resource to use as error subtitle.
     */
    public void setSubtitle(int res) {
        mSubtitleTextView.setText(res);
    }

    /**
     * Returns the current subtitle.
     */
    public String getSubtitle() {
        return mSubtitleTextView.getText().toString();
    }

    /**
     * Sets the error subtitle text to a given color
     *
     * @param res color resource to use for error subtitle text.
     */
    public void setSubtitleColor(int res) {
        mSubtitleTextView.setTextColor(res);
    }

    /**
     * Returns the current subtitle text color.
     */
    public int getSubtitleColor() {
        return mSubtitleTextView.getCurrentTextColor();
    }

    /**
     * Sets the retry button's text to a given {@link java.lang.String}.
     *
     * @param text {@link java.lang.String} to use as retry button text.
     */
    public void setRetryButtonText(String text) {
        mRetryButton.setText(text);
    }

    /**
     * Sets the retry button's text to a given string resource.
     *
     * @param res string resource to be used as retry button text.
     */
    public void setRetryButtonText(int res) {
        mRetryButton.setText(res);
    }

    /**
     * Returns the current retry button text.
     */
    public String getRetryButtonText() {
        return mRetryButton.getText().toString();
    }

    /**
     * Shows or hides the error title
     */
    public void showTitle(boolean show) {
        mTitleTextView.setVisibility(show ? VISIBLE : GONE);
    }

    /**
     * Indicates whether the title is currently visible.
     */
    public boolean isTitleVisible() {
        return mTitleTextView.getVisibility() == VISIBLE;
    }

    /**
     * Shows or hides error subtitle.
     */
    public void showSubtitle(boolean show) {
        mSubtitleTextView.setVisibility(show ? VISIBLE : GONE);
    }

    /**
     * Indicates whether the subtitle is currently visible.
     */
    public boolean isSubtitleVisible() {
        return mSubtitleTextView.getVisibility() == VISIBLE;
    }

    /**
     * Shows or hides the retry button.
     */
    public void showRetryButton(boolean show) {
        mRetryButton.setVisibility(show ? VISIBLE : GONE);
    }

    /**
     * Indicates whether the retry button is visible.
     */
    public boolean isRetryButtonVisible() {
        return mRetryButton.getVisibility() == VISIBLE;
    }

    public interface RetryListener {
        public void onRetry();
    }
}