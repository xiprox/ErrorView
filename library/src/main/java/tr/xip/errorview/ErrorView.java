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
    public void setImage(int res) {
        imageView.setImageResource(res);
    }

    /**
     * Sets the error image to a given {@link android.graphics.drawable.Drawable}.
     */
    public void setImage(Drawable drawable) {
        imageView.setImageDrawable(drawable);
    }

    /**
     * Sets the error image to a given {@link android.graphics.Bitmap}.
     */
    public void setImage(Bitmap bitmap) {
        imageView.setImageBitmap(bitmap);
    }

    /**
     * Tints the error image with given color.
     */
    public void setImageTint(int color) {
        imageView.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
    }

    /**
     * Shows or hides the error image.
     */
    public void setImageVisible(boolean visible) {
        imageView.setVisibility(visible ? VISIBLE : GONE);
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
    public void setTitle(String text) {
        setTitleVisible(text != null);
        titleView.setText(text);
    }

    /**
     * Sets the error title to a given string resource.
     */
    public void setTitle(int res) {
        // An exception will be thrown if the res isn't found anyways so it's safe to just go ahead
        // and make the title visible.
        setTitleVisible(true);
        titleView.setText(res);
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
    public void setTitleColor(int res) {
        titleView.setTextColor(res);
    }

    /**
     * Shows or hides the error title
     */
    public void setTitleVisible(boolean visible) {
        titleView.setVisibility(visible ? VISIBLE : GONE);
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
    public void setSubtitle(String subtitle) {
        setSubtitleVisible(subtitle != null);
        subtitleView.setText(subtitle);
    }

    /**
     * Sets the error subtitle to a given string resource.
     */
    public void setSubtitle(int res) {
        // An exception will be thrown if the res isn't found anyways so it's safe to just go ahead
        // and make the title visible.
        setSubtitleVisible(true);
        subtitleView.setText(res);
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
    public void setSubtitleColor(int res) {
        subtitleView.setTextColor(res);
    }

    /**
     * Shows or hides error subtitle.
     */
    public void setSubtitleVisible(boolean visible) {
        subtitleView.setVisibility(visible ? VISIBLE : GONE);
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
    public void setRetryText(String text) {
        retryView.setText(text);
    }

    /**
     * Sets the retry button text to a given string resource.
     */
    public void setRetryText(int res) {
        retryView.setText(res);
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
    public void setRetryColor(int color) {
        retryView.setTextColor(color);
    }

    /**
     * Shows or hides the retry button.
     */
    public void setRetryVisible(boolean visible) {
        retryView.setVisibility(visible ? VISIBLE : GONE);
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
    public void setRetryListener(RetryListener listener) {
        retryListener = listener;
    }

    public interface RetryListener {
        void onRetry();
    }
}