package jamesswinton.com.zebra.dotcodescanner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import jamesswinton.com.zebra.dotcodescanner.databinding.ActivityContinuousScanBinding;

public class ContinuousScanActivity extends AppCompatActivity {

    // Debugging
    private static final String TAG = "ContinuousScanActivity";

    // Constants


    // Variables
    private ActivityContinuousScanBinding mDataBinding;

    private IntentFilter mDataWedgeIntentFilter;
    private BroadcastReceiver mDataWedgeReceiver;

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

        // Register Receiver
        registerReceiver(dataWedgeBroadcastReceiver, mDataWedgeIntentFilter);
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Un-Register Receiver
        if (dataWedgeBroadcastReceiver != null) {
            unregisterReceiver(dataWedgeBroadcastReceiver);
        }
    }

    private BroadcastReceiver dataWedgeBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Log Receipt of Intent
            Log.i(TAG, "DataWedge Intent Received");

            // Handle Intent
            processDataWedgeIntent(context, intent);
        }
    };

    private void processDataWedgeIntent(Context cx, Intent dataWedgeIntent) {

    }
}
