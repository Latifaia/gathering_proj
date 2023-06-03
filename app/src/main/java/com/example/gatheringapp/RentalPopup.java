package com.example.gatheringapp;

import android.app.AlertDialog;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RentalPopup {
    private Context context;
    public AlertDialog dialog;
    private EditText durationInput;
    private TextView totalText;
    DataBaseHelper db;

    public RentalPopup(Context context) {
        this.context = context;
        db = new DataBaseHelper(context);
    }

    public void showPopupView(int price, int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.confirm_popup, null);
        builder.setView(view);

        durationInput = view.findViewById(R.id.duration_input);
        totalText = view.findViewById(R.id.total_text);
        Button confirmButton = view.findViewById(R.id.confirm_button);
        totalText.setText(Integer.toString(price));
        durationInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

                if (!editable.toString().trim().isEmpty()) {
                    int d = Integer.parseInt(editable.toString());
                    totalText.setText(d * price + " SAR ");
                }
            }
        });
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String duration = durationInput.getText().toString();
                System.out.println("Duration: " + duration + " -> id = " + id);
                boolean x = db.rentChalet(id, Integer.parseInt(duration));
                System.out.println(" ->>>> "+ x);
                dialog.dismiss();
            }
        });

        dialog = builder.create();
        dialog.show();
    }
}
