package com.example.gatheringapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Chalet_detiles extends AppCompatActivity {
    private ImageView imageView;
    private TextView nameTextView;
    private TextView priceTextView;
    private TextView descriptionTextView;
    private TextView addressTextView;
    private Button rentButton;
    DataBaseHelper db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chalet_detiles);
        db = new DataBaseHelper(this);
        String chaletId = getIntent().getStringExtra("chaletId");

        RentalPopup RentalPopup = new RentalPopup(this);

        imageView = findViewById(R.id.imageView);
        nameTextView = findViewById(R.id.nameTextView);
        priceTextView = findViewById(R.id.priceTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        addressTextView = findViewById(R.id.addressTextView);
        rentButton = findViewById(R.id.rentButton);
        if (chaletId != "-") {
            // Retrieve the Chalet object from the database
            Chalet chalet = db.getChaletById(chaletId);

            if (chalet != null) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(chalet.getImage(), 0, chalet.getImage().length);

                imageView.setImageBitmap(bitmap);

                nameTextView.setText(chalet.getChalet_name());
                priceTextView.setText("Price For Night " + String.valueOf(chalet.getChalet_price()) + " SAR");
                descriptionTextView.setText(chalet.getChalet_decription());
                addressTextView.setText(chalet.getChalet_address());
                if (chalet.rented_by == db.getCurrentUser()) {
                    String dateStr = chalet.rented_at;
                    LocalDate date = LocalDate.parse(dateStr, DateTimeFormatter.ISO_DATE);
                    LocalDate newDate = date.plusDays(chalet.duration);
                    String newDateStr = newDate.format(DateTimeFormatter.ISO_DATE);
                    rentButton.setText("Rented By You Until " + newDateStr );
                    rentButton.setEnabled(false);
                }
                rentButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RentalPopup.showPopupView(chalet.getChalet_price(), Integer.parseInt(chaletId));
                        RentalPopup.dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                finish();
                            }
                        });                    }
                });
            }

        }

    }
}