/*
 * Copyright (C) 2017 İhsan Işık
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
import android.graphics.PorterDuff;
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
 * A custom view that displays an image, a title, and a subtitle. It can be used for various
 * purposes like displaying errors, empty states, or just messages with images.
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

        imageView = (ImageView) findViewById(R.id.ev_image);
        titleView = (TextView) findViewById(R.id.ev_title);
        subtitleView = (TextView) findViewById(R.id.ev_subtitle);
        retryView = (TextView) findViewById(R.id.ev_retry);

        int imageRes;
        int imageTint;
        boolean imageVisible;

        String title;
        int titleColor;

        String subtitle;
        int subtitleColor;

        boolean titleVisible;
        boolean subtitleVisible;
        boolean retryVisible;

        String retryText;
        int retryBackground;
        int retryColor;

        try {
            imageRes = a.getResourceId(R.styleable.ErrorView_ev_image, R.drawable.error_view_cloud);
            imageTint = a.getColor(R.styleable.ErrorView_ev_imageTint, 0);
            imageVisible = a.getBoolean(R.styleable.ErrorView_ev_imageVisible, true);
            title = a.getString(R.styleable.ErrorView_ev_title);
            titleColor = a.getColor(R.styleable.ErrorView_ev_titleColor,
                    getResources().getColor(R.color.error_view_text));
            titleVisible = a.getBoolean(R.styleable.ErrorView_ev_titleVisible, true);
            subtitle = a.getString(R.styleable.ErrorView_ev_subtitle);
            subtitleColor = a.getColor(R.styleable.ErrorView_ev_subtitleColor,
                    getResources().getColor(R.color.error_view_text_light));
            subtitleVisible = a.getBoolean(R.styleable.ErrorView_ev_subtitleVisible, true);
            retryVisible = a.getBoolean(R.styleable.ErrorView_ev_retryVisible, true);
            retryText = a.getString(R.styleable.ErrorView_ev_retryText);
            retryBackground = a.getResourceId(R.styleable.ErrorView_ev_retryBackground,
                    R.drawable.error_view_retry_button_background);
            retryColor = a.getColor(R.styleable.ErrorView_ev_retryColor,
                    getResources().getColor(R.color.error_view_text_dark));

            if (imageRes != 0) {
                setImage(imageRes);
            }
            if (imageTint != 0) {
                setImageTint(imageTint);
            }
            setImageVisible(imageVisible);

            if (title != null) {
                setTitle(title);
            }

            if (subtitle != null) {
                setSubtitle(subtitle);
            }

            if (retryText != null) {
                retryView.setText(retryText);
            }

            if (!titleVisible) {
                titleView.setVisibility(GONE);
            }

            if (!subtitleVisible) {
                subtitleView.setVisibility(GONE);
            }

            if (!retryVisible) {
                retryView.setVisibility(GONE);
            }

            titleView.setTextColor(titleColor);
            subtitleView.setTextColor(subtitleColor);

            retryView.setTextColor(retryColor);
            retryView.setBackgroundResource(retryBackground);
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
     * Sets error image to a given drawable resource.
     */
    public ErrorView setImage(int res) {
        imageView.setImageResource(res);
        return this;
    }

    /**
     * Sets the error image to a given {@link android.graphics.drawable.Drawable}.
     */
    public ErrorView setImage(Drawable drawable) {
        imageView.setImageDrawable(drawable);
        return this;
    }

    /**
     * Sets the error image to a given {@link android.graphics.Bitmap}.
     */
    public ErrorView setImage(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
        return this;
    }

    /**
     * Tints the error image with given color.
     */
    public ErrorView setImageTint(int color) {
        imageView.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        return this;
    }

    /**
     * Shows or hides the error image.
     */
    public ErrorView setImageVisible(boolean visible) {
        imageView.setVisibility(visible ? VISIBLE : GONE);
        return this;
    }

    /**
     * Indicates whether the title is currently visible.
     */
    public boolean isImageVisible() {
        return imageView.getVisibility() == VISIBLE;
    }

    /**
     * Sets the error title to a given {@link java.lang.String}.
     */
    public ErrorView setTitle(String text) {
        setTitleVisible(text != null);
        titleView.setText(text);
        return this;
    }

    /**
     * Sets the error title to a given string resource.
     */
    public ErrorView setTitle(int res) {
        // An exception will be thrown if the res isn't found anyways so it's safe to just go ahead
        // and make the title visible.
        setTitleVisible(true);
        titleView.setText(res);
        return this;
    }

    /**
     * Returns the current title string.
     */
    public CharSequence getTitle() {
        return titleView.getText();
    }

    /**
     * Sets the error title text to a given color.
     */
    public ErrorView setTitleColor(int res) {
        titleView.setTextColor(res);
        return this;
    }

    /**
     * Shows or hides the error title
     */
    public ErrorView setTitleVisible(boolean visible) {
        titleView.setVisibility(visible ? VISIBLE : GONE);
        return this;
    }

    /**
     * Indicates whether the title is currently visible.
     */
    public boolean isTitleVisible() {
        return titleView.getVisibility() == VISIBLE;
    }

    /**
     * Sets the error subtitle to a given {@link java.lang.String}.
     */
    public ErrorView setSubtitle(String subtitle) {
        setSubtitleVisible(subtitle != null);
        subtitleView.setText(subtitle);
        return this;
    }

    /**
     * Sets the error subtitle to a given string resource.
     */
    public ErrorView setSubtitle(int res) {
        // An exception will be thrown if the res isn't found anyways so it's safe to just go ahead
        // and make the title visible.
        setSubtitleVisible(true);
        subtitleView.setText(res);
        return this;
    }

    /**
     * Returns the current subtitle.
     */
    public CharSequence getSubtitle() {
        return subtitleView.getText();
    }

    /**
     * Sets the error subtitle text to a given color
     */
    public ErrorView setSubtitleColor(int res) {
        subtitleView.setTextColor(res);
        return this;
    }

    /**
     * Shows or hides error subtitle.
     */
    public ErrorView setSubtitleVisible(boolean visible) {
        subtitleView.setVisibility(visible ? VISIBLE : GONE);
        return this;
    }

    /**
     * Indicates whether the subtitle is currently visible.
     */
    public boolean isSubtitleVisible() {
        return subtitleView.getVisibility() == VISIBLE;
    }

    /**
     * Sets the retry button text to a given string.
     */
    public ErrorView setRetryText(String text) {
        retryView.setText(text);
        return this;
    }

    /**
     * Sets the retry button text to a given string resource.
     */
    public ErrorView setRetryText(int res) {
        retryView.setText(res);
        return this;
    }

    /**
     * Returns the retry button text.
     */
    public CharSequence getRetryText() {
        return retryView.getText();
    }

    /**
     * Sets the retry button's text color to a given color.
     */
    public ErrorView setRetryColor(int color) {
        retryView.setTextColor(color);
        return this;
    }

    /**
     * Shows or hides the retry button.
     */
    public ErrorView setRetryVisible(boolean visible) {
        retryView.setVisibility(visible ? VISIBLE : GONE);
        return this;
    }

    /**
     * Indicates whether the retry button is visible.
     */
    public boolean isRetryVisible() {
        return retryView.getVisibility() == VISIBLE;
    }

    /**
     * Attaches a listener to the view which will be notified when retry events occur.
     */
    public ErrorView setRetryListener(RetryListener listener) {
        retryListener = listener;
        return this;
    }

    public interface RetryListener {
        void onRetry();
    }
}