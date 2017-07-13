package tr.xip.errorview.sample;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatDelegate;
import android.widget.Toast;

import tr.xip.errorview.ErrorView;

public class MainActivity extends ActionBarActivity {

    static {
        AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_AUTO);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ErrorView mErrorView = (ErrorView) findViewById(R.id.error_view);

        mErrorView.setRetryListener(new ErrorView.RetryListener() {
            @Override
            public void onRetry() {
                Toast.makeText(MainActivity.this, R.string.info_retrying, Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mErrorView.setTitle(R.string.error_title_damn)
                                .setTitleColor(getResources().getColor(R.color.apptheme_primary))
                                .setSubtitle(R.string.error_subtitle_failed_one_more_time)
                                .setRetryText(R.string.error_view_retry);

                    }
                }, 2000);
            }
        });
    }
}
