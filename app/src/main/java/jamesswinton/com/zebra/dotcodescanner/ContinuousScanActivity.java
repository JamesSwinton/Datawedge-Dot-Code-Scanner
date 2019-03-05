package jamesswinton.com.zebra.dotcodescanner;

import android.content.Intent;
import android.content.IntentFilter;
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

    private IntentFilter mDataWedgeIntentFilter;
    private DataWedgeReceiver mDataWedgeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Init DataBinding
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_continuous_scan);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Create Intent Filter
        if (mDataWedgeIntentFilter == null) {
            mDataWedgeIntentFilter = new IntentFilter();
            mDataWedgeIntentFilter.addCategory(Intent.CATEGORY_DEFAULT);
            mDataWedgeIntentFilter.addAction(getResources().getString(R.string.scan_intent_action));
        }

        // Create Instance of Broadcast Receiver
        if (mDataWedgeReceiver == null) {
            mDataWedgeReceiver = new DataWedgeReceiver();
        }

        // Register Receiver
        registerReceiver(mDataWedgeReceiver, mDataWedgeIntentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Un-Register Receiver
        if (mDataWedgeReceiver != null) {
            unregisterReceiver(mDataWedgeReceiver);
        }
    }
}
