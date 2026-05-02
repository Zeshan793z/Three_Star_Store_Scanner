package com.example.three_star_store_scanner.ui.billing;

import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.three_star_store_scanner.R;
import com.example.three_star_store_scanner.data.entity.Product;
import com.example.three_star_store_scanner.data.repository.ProductRepository;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;

import java.util.ArrayList;
import java.util.List;

public class BillingActivity extends AppCompatActivity {

    private DecoratedBarcodeView barcodeScannerView;
    private RecyclerView billRecyclerView;
    private BillAdapter billAdapter;
    private TextView totalPriceText;
    private Button reviewOrderButton;
    private List<BillItem> billItems = new ArrayList<>();
    private double totalPrice = 0.0;
    private ProductRepository repository;

    private String lastScanned = null;
    private long lastScanTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing);

        barcodeScannerView = findViewById(R.id.barcodeScannerView);
        billRecyclerView = findViewById(R.id.billRecyclerView);
        totalPriceText = findViewById(R.id.totalPriceText);
        reviewOrderButton = findViewById(R.id.reviewOrderButton);

        repository = new ProductRepository(this);

        // 🔑 Critical: set LayoutManager so RecyclerView can display items
        billRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        billAdapter = new BillAdapter(billItems, this::updateTotal);
        billRecyclerView.setAdapter(billAdapter);

        // Continuous scanning with debounce
        barcodeScannerView.decodeContinuous(new BarcodeCallback() {
            @Override
            public void barcodeResult(BarcodeResult result) {
                if (result.getText() != null) {
                    handleScan(result.getText());
                }
            }
        });

        reviewOrderButton.setOnClickListener(v -> {
            Toast.makeText(this, "Review Order clicked", Toast.LENGTH_SHORT).show();
            // TODO: open ReviewOrderActivity
        });
    }

    private void handleScan(String scannedBarcode) {
        long now = System.currentTimeMillis();

        // Ignore duplicates within 2 seconds
        if (scannedBarcode.equals(lastScanned) && (now - lastScanTime < 2000)) {
            return;
        }

        lastScanned = scannedBarcode;
        lastScanTime = now;

        Product product = repository.getProductByBarcode(scannedBarcode);
        if (product != null) {
            BillItem item = new BillItem(product.name, product.barcode, product.price);
            billItems.add(item);
            billAdapter.notifyDataSetChanged();

            updateTotal();
            playBeep();
        } else {
            Toast.makeText(this, "Product not found!", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateTotal() {
        totalPrice = 0.0;
        for (BillItem item : billItems) {
            totalPrice += item.getTotalPrice();
        }
        totalPriceText.setText("Total Price: ₹" + totalPrice);
    }

    private void playBeep() {
        ToneGenerator toneGen = new ToneGenerator(AudioManager.STREAM_MUSIC, 100);
        toneGen.startTone(ToneGenerator.TONE_CDMA_PIP, 150);
    }

    @Override
    protected void onResume() {
        super.onResume();
        barcodeScannerView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        barcodeScannerView.pause();
    }
}
