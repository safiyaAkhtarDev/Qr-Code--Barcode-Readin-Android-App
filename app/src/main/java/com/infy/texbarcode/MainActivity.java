package com.infy.texbarcode;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

import info.androidhive.barcode.BarcodeReader;


public class MainActivity extends AppCompatActivity implements BarcodeReader.BarcodeReaderListener {
    BarcodeReader barcodeReader;
    RelativeLayout layScanner;
    LinearLayout layScan;
    String Str;
    private TextView contentTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button scanBtn = findViewById(R.id.scan_button);
        contentTxt = findViewById(R.id.scan_content);
        contentTxt.setText(".");
        layScanner = findViewById(R.id.layScanner);
        layScan = findViewById(R.id.layScan);

        // get the barcode reader instance
        barcodeReader = (BarcodeReader) getSupportFragmentManager().findFragmentById(R.id.barcode_scanner);
        scanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layScanner.setVisibility(View.VISIBLE);
            }
        });


    }

    @Override
    public void onScanned(Barcode barcode) {
        barcodeReader.playBeep();
        Str = contentTxt.getText().toString();
        Str += barcode.displayValue;

        String filename = "TexBarcode.txt";
        String Filepath = "Document/public";
        File file;
        FileOutputStream fileOutputStream;
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                file = new File("file");
//                if (file.createNewFile()) {
                MainActivity.this.layScanner.setVisibility(View.GONE);
                fileOutputStream = openFileOutput(filename, MODE_APPEND);
                fileOutputStream.flush();
                contentTxt.setText(getFilesDir().toString());
                fileOutputStream.close();
//                }
//                else{
//                    contentTxt.setText(" E");
//                }
            }
        } catch (Exception e) {
            contentTxt.setText(e.getMessage());
        }

    }

    @Override
    public void onScannedMultiple(List<Barcode> list) {
        // multiple barcodes scanned
    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {
        // barcode scanned from bitmap image
    }

    @Override
    public void onScanError(String s) {
        // scan error
        Toast.makeText(this, "Scaned: " + s, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onCameraPermissionDenied() {
        finish();
    }
}
