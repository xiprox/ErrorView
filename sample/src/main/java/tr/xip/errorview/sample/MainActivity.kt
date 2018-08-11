package tr.xip.errorview.sample

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_AUTO)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        errorView.setRetryListener {
            Toast.makeText(this@MainActivity, R.string.info_retrying, Toast.LENGTH_SHORT).show()

            Handler().postDelayed({
                errorView.setTitle(R.string.error_title_damn)
                        .setTitleColor(resources.getColor(R.color.apptheme_primary))
                        .setSubtitle(R.string.error_subtitle_failed_one_more_time)
                        .setRetryText(R.string.error_view_retry)
            }, 2000)
        }
    }
}
