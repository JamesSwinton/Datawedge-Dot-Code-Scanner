package jamesswinton.com.zebra.dotcodescanner;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import jamesswinton.com.zebra.dotcodescanner.databinding.ActivityContinuousScanBinding;

public class ContinuousScanActivity extends AppCompatActivity {

    // Debugging
    private static final String TAG = "ContinuousScanActivity";

    // Constants


    // Variables
    private ActivityContinuousScanBinding mDataBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Init DataBinding
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_continuous_scan);
    }
}
