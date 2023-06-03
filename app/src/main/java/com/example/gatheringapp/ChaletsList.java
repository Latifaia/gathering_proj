package com.example.gatheringapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ChaletsList extends AppCompatActivity {


    ListView chaletList;
    Button AddButton1;
    DataBaseHelper db;
    ArrayList <Chalet> chalets;
    @Override
    protected void onResume() {
        super.onResume();
        ArrayList<Chalet> cs = db.getAllChalets();
        chalets = new ArrayList<>();
        for (Chalet c : cs) {
            System.out.println(" -> " + c.getOwner());
            if (!c.getOwner().equals(db.getCurrentUser())) {
                chalets.add(c);
            }
        }
        ChaletAdaptor chaletAdaptor = new ChaletAdaptor(this , R.layout.item_chalet , chalets);
        chaletList.setAdapter(chaletAdaptor);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chalets_list);


        chaletList =(ListView) findViewById(R.id.chaletList1);

        db = new DataBaseHelper(this);

       // AddButton1 = (Button) findViewById(R.id.AddButton);


        //ChaletAdaptor chaletAdaptor = new ChaletAdaptor(this , R.layout.item_chalet , chalets);

        /*
        chalets.add(new Chalet("chalet1"));
        chalets.add(new Chalet("chalet1"));
        chalets.add(new Chalet("chalet1"));
        chalets.add(new Chalet("chalet1"));
        chalets.add(new Chalet("chalet1"));
        chalets.add(new Chalet("chalet1"));
        chalets.add(new Chalet("chalet1"));
        chalets.add(new Chalet("chalet1"));
*/
        //chaletList.setAdapter(chaletAdaptor);






       chaletList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Chalet sel_ch = (Chalet) parent.getItemAtPosition(position);
              //boolean fail = db.DeleteOne(sel_ch.getChalet_name());
                if (sel_ch.getChalet_decription() == "Rented") {
                    Toast.makeText(ChaletsList.this, "This already rented", Toast.LENGTH_SHORT).show();
                    return;
                }
                Context context = view.getContext();
                Intent intent = new Intent(context, Chalet_detiles.class);
                intent.putExtra("chaletId", Integer.toString( sel_ch.getChalet_id() ));
                context.startActivity(intent);
             //   ArrayList<Chalet> chalets = db.getAllChalets();
                                    //chaletList.setAdapter(chaletAdaptor);

               // ArrayList<Chalet> chalets = db.getAllChalets();
                //ChaletAdaptor chaletAdaptor = new ChaletAdaptor(this , R.layout.item_chalet , chalets);

               //chaletList.setAdapter(chaletAdaptor);







            }
        });


    }




}