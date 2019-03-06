package jamesswinton.com.zebra.dotcodescanner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import jamesswinton.com.zebra.dotcodescanner.databinding.ActivityContinuousScanBinding;

public class ContinuousScanActivity extends AppCompatActivity {

    // Debugging
    private static final String TAG = "ContinuousScanActivity";

    /*
     * Constants
     */

    // Actions
    private static final String DATAWEDGE_ACTION = "com.symbol.datawedge.api.ACTION";
    private static final String NOTIFICATION_ACTION  = "com.symbol.datawedge.api.NOTIFICATION_ACTION";

    // Triggers
    private static final String DW_NOTIFICATION_TYPE = "com.symbol.datawedge.api.NOTIFICATION_TYPE";
    private static final String APPLICATION_NAME = "com.symbol.datawedge.api.APPLICATION_NAME";
    private static final String NOTIFICATION = "com.symbol.datawedge.api.NOTIFICATION";
    private static final String SOFT_SCAN_TRIGGER = "com.symbol.datawedge.api.SOFT_SCAN_TRIGGER";
    private static final String REGISTER_FOR_NOTIFICATION = "com.symbol.datawedge.api.REGISTER_FOR_NOTIFICATION";

    // Parameters
    private static final String STATUS = "STATUS";
    private static final String STOP_SCANNING = "STOP_SCANNING";
    private static final String START_SCANNING = "START_SCANNING";
    private static final String SCANNER_STATUS = "SCANNER_STATUS";
    private static final String TOGGLE_SCANNING = "TOGGLE_SCANNING";
    private static final String NOTIFICATION_TYPE = "NOTIFICATION_TYPE";

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
