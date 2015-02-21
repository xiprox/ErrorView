/*
 * Copyright (C) 2014 Ihsan Isik
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

    private Context context;
    private AttributeSet attrs;

    private ImageView mErrorImage;
    private TextView mErrorTitle;
    private TextView mErrorSubtitle;
    private TextView mRetryButton;

    private int errorImageRes;

    private String errorTitle;
    private int errorTitleColor;

    private String errorSubtitle;
    private int errorSubtitleColor;

    private boolean showTitle;
    private boolean showSubtitle;
    private boolean showRetryButton;

    private int retryButtonBackground;
    private int retryButtonTextColor;

    private RetryListener listener;

    public ErrorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.attrs = attrs;
        init();
    }

    private void init() {
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ErrorView, 0, 0);

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.error_view_layout, this, true);

        mErrorImage = (ImageView) findViewById(R.id.error_image);
        mErrorTitle = (TextView) findViewById(R.id.error_title);
        mErrorSubtitle = (TextView) findViewById(R.id.error_subtitle);
        mRetryButton = (TextView) findViewById(R.id.error_retry);

        try {
            errorImageRes = a.getResourceId(R.styleable.ErrorView_ev_errorImage, R.drawable.error_view_cloud);
            errorTitle = a.getString(R.styleable.ErrorView_ev_errorTitle);
            errorTitleColor = a.getColor(R.styleable.ErrorView_ev_errorTitleColor,
                    getResources().getColor(R.color.error_view_text));
            errorSubtitle = a.getString(R.styleable.ErrorView_ev_errorSubtitle);
            errorSubtitleColor = a.getColor(R.styleable.ErrorView_ev_errorSubtitleColor,
                    getResources().getColor(R.color.error_view_text_light));
            showTitle = a.getBoolean(R.styleable.ErrorView_ev_showTitle, true);
            showSubtitle = a.getBoolean(R.styleable.ErrorView_ev_showSubtitle, true);
            showRetryButton = a.getBoolean(R.styleable.ErrorView_ev_showRetryButton, true);
            retryButtonBackground = a.getResourceId(R.styleable.ErrorView_ev_retryButtonBackground,
                    R.drawable.error_view_retry_button_background);
            retryButtonTextColor = a.getColor(R.styleable.ErrorView_ev_retryButtonTextColor,
                    getResources().getColor(R.color.error_view_text_dark));

            if (errorImageRes != 0)
                setErrorImageResource(errorImageRes);

            if (errorTitle != null)
                setErrorTitle(errorTitle);

            if (errorSubtitle != null)
                setErrorSubtitle(errorSubtitle);

            if (!showTitle)
                mErrorTitle.setVisibility(GONE);

            if (!showSubtitle)
                mErrorSubtitle.setVisibility(GONE);

            if (!showRetryButton)
                mRetryButton.setVisibility(GONE);

            mErrorTitle.setTextColor(errorTitleColor);
            mErrorSubtitle.setTextColor(errorSubtitleColor);

            mRetryButton.setTextColor(retryButtonTextColor);
            mRetryButton.setBackgroundResource(retryButtonBackground);
        } finally {
            a.recycle();
        }

        mRetryButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null) listener.onRetry();
            }
        });
    }

    /**
     * Attaches a listener that to the view that reports retry events.
     *
     * @param listener {@link tr.xip.errorview.RetryListener} to be notified when retry events occur.
     */
    public void setOnRetryListener(RetryListener listener) {
        this.listener = listener;
    }

    /**
     * Sets error subtitle to the description of the given HTTP status code
     *
     * @param errorCode HTTP status code
     */
    public void setError(int errorCode) {
        Map<Integer, String> mCodes = ErrorViewStatusCodes.getCodesMap();

        if (mCodes.containsKey(errorCode))
            setErrorSubtitle(errorCode + " " + mCodes.get(errorCode));
    }

    /**
     * Sets error image to a given drawable resource
     *
     * @param res drawable resource.
     */
    public void setErrorImageResource(int res) {
        mErrorImage.setImageResource(res);
    }

    /**
     * Sets the error image to a given {@link android.graphics.drawable.Drawable}.
     *
     * @param drawable {@link android.graphics.drawable.Drawable} to use as error image.
     */
    public void setErrorImageDrawable(Drawable drawable) {
        mErrorImage.setImageDrawable(drawable);
    }

    /**
     * Sets the error image to a given {@link android.graphics.Bitmap}.
     *
     * @param bitmap {@link android.graphics.Bitmap} to use as error image.
     */
    public void setErrorImageBitmap(Bitmap bitmap) {
        mErrorImage.setImageBitmap(bitmap);
    }

    /**
     * Sets the error title to a given {@link java.lang.String}.
     *
     * @param text {@link java.lang.String} to use as error title.
     */
    public void setErrorTitle(String text) {
        mErrorTitle.setText(text);
    }

    /**
     * Sets the error title to a given string resource.
     *
     * @param res string resource to use as error title.
     */
    public void setErrorTitle(int res) {
        mErrorTitle.setText(res);
    }

    /**
     * Sets the error title text to a given color.
     *
     * @param res color resource to use for error title text.
     */
    public void setErrorTitleColor(int res){
        mErrorTitle.setTextColor(context.getResources().getColor(res));
    }

    /**
     * Sets the error subtitle to a given {@link java.lang.String}.
     *
     * @param exception {@link java.lang.String} to use as error subtitle.
     */
    public void setErrorSubtitle(String exception) {
        mErrorSubtitle.setText(exception);
    }

    /**
     * Sets the error subtitle to a given string resource.
     *
     * @param res string resource to use as error subtitle.
     */
    public void setErrorSubtitle(int res) {
        mErrorSubtitle.setText(res);
    }

    /**
     * Sets the error subtitle text to a given color
     * @param res color resource to use for error subtitle text.
     */
    public void setErrorSubtitleColor(int res){
        mErrorSubtitle.setTextColor(context.getResources().getColor(res));
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
     * Shows or hides the error title
     */
    public void showTitle(boolean show) {
        mErrorTitle.setVisibility(show ? VISIBLE : GONE);
    }

    /**
     * Shows or hides error subtitle.
     */
    public void showSubtitle(boolean show) {
        mErrorSubtitle.setVisibility(show ? VISIBLE : GONE);
    }

    /**
     * Shows or hides the retry button.
     */
    public void showRetryButton(boolean show) {
        mRetryButton.setVisibility(show ? VISIBLE : GONE);
    }
}