package com.example.gatheringapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

public class RentedListActivity extends AppCompatActivity {
    ListView chaletList;
    DataBaseHelper db;
    ArrayList<Chalet> d = new ArrayList<>();
    @Override
    protected void onResume() {
        super.onResume();
        ArrayList<Chalet> chalets = db.getAllChalets();
        d = new ArrayList<>();
        for (Chalet c : chalets) {
            System.out.println(" -----------> " + c.rented_by + "   ---> " + db.getCurrentUser());
            if (c.rented_by.equals(db.getCurrentUser())) {
                d.add(c);
            }
        }
        System.out.println(d.size());
        ChaletAdaptor chaletAdaptor = new ChaletAdaptor(this, R.layout.item_chalet, d);
        chaletList.setAdapter(chaletAdaptor);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rented_list);
        chaletList =(ListView) findViewById(R.id.chaletList);
        db = new DataBaseHelper(this);

        chaletList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Chalet c = (Chalet) adapterView.getItemAtPosition(i);
                openConfirmationDialog(c.duration, c.getChalet_price(), c.getChalet_id());

            }
        });
        }
    private void openConfirmationDialog(int reservationDays, double price, int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation")
                .setMessage("Your reservation has " + reservationDays + " days.\nYou are going to get back " + (reservationDays * price) + ".")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Perform the confirmation action
                        //performConfirmationAction();
                        if (db.returnOutlet(id))
                            finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Cancel or dismiss the dialog
                        dialog.dismiss();

                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

}