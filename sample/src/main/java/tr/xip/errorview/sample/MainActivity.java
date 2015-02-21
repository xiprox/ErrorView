package tr.xip.errorview.sample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import tr.xip.errorview.ErrorView;
import tr.xip.errorview.ErrorViewStatusCodes;
import tr.xip.errorview.RetryListener;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ErrorView mErrorView = (ErrorView) findViewById(R.id.error_view);

        mErrorView.setOnRetryListener(new RetryListener() {
            @Override
            public void onRetry() {
                Toast.makeText(MainActivity.this, R.string.info_retrying, Toast.LENGTH_SHORT).show();

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                mErrorView.setError(ErrorViewStatusCodes.CODE_408);
                mErrorView.setErrorTitle(R.string.error_title_damn);
                mErrorView.setErrorTitleColor(getResources().getColor(android.R.color.holo_orange_dark));
                mErrorView.setErrorSubtitleColor(getResources().getColor(android.R.color.holo_green_dark));
            }
        });
    }
}
