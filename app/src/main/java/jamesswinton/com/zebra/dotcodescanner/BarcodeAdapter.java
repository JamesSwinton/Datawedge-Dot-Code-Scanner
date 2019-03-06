package jamesswinton.com.zebra.dotcodescanner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class BarcodeAdapter extends RecyclerView.Adapter<BarcodeAdapter.BarcodeViewHolder>  {

    // Debugging
    private static final String TAG = "BarcodeAdapter";

    // Constants


    // Variables
    private Context mContext;
    private static List<String> mBarcodes = new ArrayList<>();

    public BarcodeAdapter(Context context, List<String> barcodes) {
        mContext = context;
        mBarcodes = barcodes;
    }

    @NonNull
    @Override
    public BarcodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        return new BarcodeViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.layout_decoded_data_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull BarcodeViewHolder barcodeViewHolder, int position) {
        barcodeViewHolder.mBarcodeTextView.setText(mBarcodes.get(position));
    }

    @Override
    public int getItemCount() {
        return mBarcodes.size();
    }

    // View Holder
    class BarcodeViewHolder extends RecyclerView.ViewHolder {

        // UI Elements
        TextView mBarcodeTextView;

        // Get UI Elements
        BarcodeViewHolder(View itemView) {
            super(itemView);
            mBarcodeTextView = itemView.findViewById(R.id.barcode);
        }
    }
}
