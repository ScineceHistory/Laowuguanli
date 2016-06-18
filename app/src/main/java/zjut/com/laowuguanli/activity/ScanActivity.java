package zjut.com.laowuguanli.activity;

import android.view.WindowManager;

import com.journeyapps.barcodescanner.CaptureActivity;
import com.journeyapps.barcodescanner.CompoundBarcodeView;

import zjut.com.laowuguanli.R;

public class ScanActivity extends CaptureActivity {


    @Override
    protected CompoundBarcodeView initializeContent() {

        setContentView(R.layout.activity_scan);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return (CompoundBarcodeView)findViewById(R.id.zxing_barcode_scanner);
    }
}
