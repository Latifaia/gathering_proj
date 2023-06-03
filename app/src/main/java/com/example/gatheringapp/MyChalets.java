package com.example.gatheringapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MyChalets extends AppCompatActivity {
    ListView chaletList;
    DataBaseHelper db;
    ArrayList<Chalet> d = new ArrayList<>();
    public void refetch() {
        ArrayList<Chalet> chalets = db.getAllChalets();
        d = new ArrayList<>();
        for (Chalet c : chalets) {
            System.out.println(" -----------> " + c.rented_by + "   ---> " + db.getCurrentUser());
            if (c.getOwner().equals(db.getCurrentUser())) {
                d.add(c);
            }
        }
        ChaletAdaptor chaletAdaptor = new ChaletAdaptor(this, R.layout.item_chalet, d);
        chaletList.setAdapter(chaletAdaptor);
    }
    @Override
    protected void onResume() {
        super.onResume();
       refetch();
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
                openConfirmationDialog(c.getChalet_name());

            }
        });
        }
    private void openConfirmationDialog( String name) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation")
                .setMessage("It's going to be deleted " + name + " . ")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Perform the confirmation action
                        //performConfirmationAction();
                        db.DeleteOne(name);
                            refetch();


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