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

    private BarcodeAdapter mBarcodeAdapter;
    private List<String> mBarcodes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Init DataBinding
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_continuous_scan);

        // Init Soft Scan Toggle Button
        mDataBinding.scanToggle.setOnCheckedChangeListener(this::toggleScan);

        // Init RecyclerView
        mDataBinding.scanDataRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mBarcodeAdapter = new BarcodeAdapter(this, mBarcodes);
        mDataBinding.scanDataRecyclerView.setAdapter(mBarcodeAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Create Intent Filter
        if (mDataWedgeIntentFilter == null) {
            mDataWedgeIntentFilter = new IntentFilter();
            mDataWedgeIntentFilter.addCategory(Intent.CATEGORY_DEFAULT);
            mDataWedgeIntentFilter.addAction("com.symbol.datawedge.api.NOTIFICATION_ACTION");
            mDataWedgeIntentFilter.addAction(getResources().getString(R.string.intent_action));
        }

        // Register for Notifications
        registerForNotifications();

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

    private void registerForNotifications() {
        Bundle registerNotificationsBundle = new Bundle();
        registerNotificationsBundle.putString(APPLICATION_NAME, getPackageName());
        registerNotificationsBundle.putString(DW_NOTIFICATION_TYPE, SCANNER_STATUS);
        Intent registerNotificationsIntent = new Intent();
        registerNotificationsIntent.setAction(DATAWEDGE_ACTION);
        registerNotificationsIntent.putExtra(REGISTER_FOR_NOTIFICATION, registerNotificationsBundle);
        this.sendBroadcast(registerNotificationsIntent);
    }

    private BroadcastReceiver dataWedgeBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get Action
            String intentAction = intent.getAction();

            // Log Receipt of Intent
            Log.i(TAG, "DataWedge Intent Received | Action: " + intentAction);

            // Exit if no Intent Action
            if (intentAction == null) {
                return;
            }

            if (intentAction.equals(NOTIFICATION_ACTION) && intent.hasExtra(NOTIFICATION)) {
                handleNotificationIntent(intent);
            } else {
                // Handle Intent
                processDataWedgeIntent(context, intent);
            }
        }
    };

    private void handleNotificationIntent(Intent notificationIntent) {
        // Get Type
        String type = notificationIntent.getBundleExtra(NOTIFICATION).getString(NOTIFICATION_TYPE);

        // Get Scanner Status
        if(type != null && type.equals(SCANNER_STATUS)) {

            // Get Status
            String scannerStatus = notificationIntent.getBundleExtra(NOTIFICATION).getString(STATUS);

            // Log Status
            Log.i(TAG, "Scanner Status: " + scannerStatus);

            // Handle Status
            if (scannerStatus != null) {
                switch (scannerStatus) {
                    case "WAITING":
                    case "IDLE":
                        if (mDataBinding.scanToggle.isChecked()) {
                            toggleScan(null, true);
                        }
                        break;
                    case "DISABLED":
                        // TODO: Re-enable scanner
                        break;
                }
            }
        }
    }

    private void toggleScan(View toggleButton, boolean scanOn) {
        // Start Or Stop Scan
        if (scanOn) {
            Intent i = new Intent();
            i.setAction(DATAWEDGE_ACTION);
            i.putExtra(SOFT_SCAN_TRIGGER, START_SCANNING);
            this.sendBroadcast(i);
        } else {
            Intent i = new Intent();
            i.setAction(DATAWEDGE_ACTION);
            i.putExtra(SOFT_SCAN_TRIGGER, STOP_SCANNING);
            this.sendBroadcast(i);
        }
    }

    private void processDataWedgeIntent(Context cx, Intent dataWedgeIntent) {
        // Get Barcode
        String barcode = dataWedgeIntent.getStringExtra(getResources()
                .getString(R.string.intent_data));

        // Add Barcode to Array
        mBarcodes.add(barcode);

        // Refresh Adapter
        mBarcodeAdapter.notifyDataSetChanged();
    }
}
