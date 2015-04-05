package tr.xip.errorview.sample;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import tr.xip.errorview.ErrorView;
import tr.xip.errorview.HttpStatusCodes;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ErrorView mErrorView = (ErrorView) findViewById(R.id.error_view);

        mErrorView.setOnRetryListener(new ErrorView.RetryListener() {
            @Override
            public void onRetry() {
                Toast.makeText(MainActivity.this, R.string.info_retrying, Toast.LENGTH_SHORT).show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mErrorView.setError(HttpStatusCodes.CODE_408);
                        mErrorView.setTitle(R.string.error_title_damn);
                        mErrorView.setTitleColor(getResources().getColor(android.R.color.holo_orange_dark));
                        mErrorView.setSubtitleColor(getResources().getColor(android.R.color.holo_green_dark));
                    }
                }, 5000);
            }
        });
    }
}
