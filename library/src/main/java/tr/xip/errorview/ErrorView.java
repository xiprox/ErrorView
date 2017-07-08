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

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * @author Ihsan Isik
 *         <p/>
 *         A custom view that displays an error image, a title, and a subtitle given an HTTP status
 *         code. It can be used for various other purposes like displaying other kinds of errors or
 *         just messages with images.
 * <p/>
 */
public class ErrorView extends LinearLayout {
    private ImageView imageView;
    private TextView titleView;
    private TextView subtitleView;
    private TextView retryView;

    private RetryListener retryListener;

    public ErrorView(Context context) {
        this(context, null);
    }

    public ErrorView(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.ev_style);
    }

    public ErrorView(Context context, AttributeSet attrs, int defStyle) {
        this(context, attrs, defStyle, 0);
    }

    public ErrorView(Context context, AttributeSet attrs, int defStyle, int defStyleRes) {
        super(context, attrs);
        init(attrs, defStyle, defStyleRes);
    }

    private void init(AttributeSet attrs, int defStyle, int defStyleRes) {
        TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.ErrorView, defStyle, defStyleRes);

        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.error_view_layout, this, true);
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);

        /* Set android:animateLayoutChanges="true" programmatically */
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            setLayoutTransition(new LayoutTransition());
        }

        imageView = (ImageView) findViewById(R.id.error_image);
        titleView = (TextView) findViewById(R.id.error_title);
        subtitleView = (TextView) findViewById(R.id.error_subtitle);
        retryView = (TextView) findViewById(R.id.error_retry);

        int imageRes;

        String title;
        int titleColor;

        String subtitle;
        int subtitleColor;

        boolean showTitle;
        boolean showSubtitle;
        boolean showRetryButton;

        String retryButtonText;
        int retryButtonBackground;
        int retryButtonTextColor;

        try {
            imageRes = a.getResourceId(R.styleable.ErrorView_ev_errorImage, R.drawable.error_view_cloud);
            title = a.getString(R.styleable.ErrorView_ev_title);
            titleColor = a.getColor(R.styleable.ErrorView_ev_titleColor,
                    getResources().getColor(R.color.error_view_text));
            subtitle = a.getString(R.styleable.ErrorView_ev_subtitle);
            subtitleColor = a.getColor(R.styleable.ErrorView_ev_subtitleColor,
                    getResources().getColor(R.color.error_view_text_light));
            showTitle = a.getBoolean(R.styleable.ErrorView_ev_showTitle, true);
            showSubtitle = a.getBoolean(R.styleable.ErrorView_ev_showSubtitle, true);
            showRetryButton = a.getBoolean(R.styleable.ErrorView_ev_showRetryButton, true);
            retryButtonText = a.getString(R.styleable.ErrorView_ev_retryButtonText);
            retryButtonBackground = a.getResourceId(R.styleable.ErrorView_ev_retryButtonBackground,
                    R.drawable.error_view_retry_button_background);
            retryButtonTextColor = a.getColor(R.styleable.ErrorView_ev_retryButtonTextColor,
                    getResources().getColor(R.color.error_view_text_dark));

            if (imageRes != 0) {
                setImage(imageRes);
            }

            if (title != null) {
                setTitle(title);
            }

            if (subtitle != null) {
                setSubtitle(subtitle);
            }

            if (retryButtonText != null) {
                retryView.setText(retryButtonText);
            }

            if (!showTitle) {
                titleView.setVisibility(GONE);
            }

            if (!showSubtitle) {
                subtitleView.setVisibility(GONE);
            }

            if (!showRetryButton) {
                retryView.setVisibility(GONE);
            }

            titleView.setTextColor(titleColor);
            subtitleView.setTextColor(subtitleColor);

            retryView.setTextColor(retryButtonTextColor);
            retryView.setBackgroundResource(retryButtonBackground);
        } finally {
            a.recycle();
        }

        retryView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (retryListener != null) {
                    retryListener.onRetry();
                }
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
        retryListener = listener;
    }

    /**
     * Sets error image to a given drawable resource
     *
     * @param res drawable resource.
     */
    public void setImage(int res) {
        imageView.setImageResource(res);
    }

    /**
     * Sets the error image to a given {@link android.graphics.drawable.Drawable}.
     *
     * @param drawable {@link android.graphics.drawable.Drawable} to use as error image.
     */
    public void setImage(Drawable drawable) {
        imageView.setImageDrawable(drawable);
    }

    /**
     * Sets the error image to a given {@link android.graphics.Bitmap}.
     *
     * @param bitmap {@link android.graphics.Bitmap} to use as error image.
     */
    public void setImage(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }

    /**
     * Sets the error title to a given {@link java.lang.String}.
     *
     * @param text {@link java.lang.String} to use as error title.
     */
    public void setTitle(String text) {
        titleView.setText(text);
    }

    /**
     * Sets the error title to a given string resource.
     *
     * @param res string resource to use as error title.
     */
    public void setTitle(int res) {
        titleView.setText(res);
    }

    /**
     * Returns the current title string.
     */
    public String getTitle() {
        return titleView.getText().toString();
    }

    /**
     * Sets the error title text to a given color.
     *
     * @param res color resource to use for error title text.
     */
    public void setTitleColor(int res) {
        titleView.setTextColor(res);
    }

    /**
     * Sets the error subtitle to a given {@link java.lang.String}.
     *
     * @param exception {@link java.lang.String} to use as error subtitle.
     */
    public void setSubtitle(String exception) {
        subtitleView.setText(exception);
    }

    /**
     * Sets the error subtitle to a given string resource.
     *
     * @param res string resource to use as error subtitle.
     */
    public void setSubtitle(int res) {
        subtitleView.setText(res);
    }

    /**
     * Returns the current subtitle.
     */
    public String getSubtitle() {
        return subtitleView.getText().toString();
    }

    /**
     * Sets the error subtitle text to a given color
     *
     * @param res color resource to use for error subtitle text.
     */
    public void setSubtitleColor(int res) {
        subtitleView.setTextColor(res);
    }

    /**
     * Sets the retry button's text color to a given color.
     *
     * @param color int color to be used as text color.
     */
    public void setRetryButtonTextColor(int color) {
        retryView.setTextColor(color);
    }

    /**
     * Returns the current retry button text color.
     */
    public int getRetryButtonTextColor() {
        return retryView.getCurrentTextColor();
    }

    /**
     * Shows or hides the error title
     */
    public void showTitle(boolean show) {
        titleView.setVisibility(show ? VISIBLE : GONE);
    }

    /**
     * Indicates whether the title is currently visible.
     */
    public boolean isTitleVisible() {
        return titleView.getVisibility() == VISIBLE;
    }

    /**
     * Shows or hides error subtitle.
     */
    public void showSubtitle(boolean show) {
        subtitleView.setVisibility(show ? VISIBLE : GONE);
    }

    /**
     * Indicates whether the subtitle is currently visible.
     */
    public boolean isSubtitleVisible() {
        return subtitleView.getVisibility() == VISIBLE;
    }

    /**
     * Shows or hides the retry button.
     */
    public void showRetryButton(boolean show) {
        retryView.setVisibility(show ? VISIBLE : GONE);
    }

    /**
     * Indicates whether the retry button is visible.
     */
    public boolean isRetryButtonVisible() {
        return retryView.getVisibility() == VISIBLE;
    }

    public interface RetryListener {
        void onRetry();
    }
}