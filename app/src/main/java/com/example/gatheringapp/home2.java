package com.example.gatheringapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;


public class home2 extends AppCompatActivity {
    private Button b_add;
    private Button LogOut;
    private Button viewButton;
    private Button returnButton;
    private Button myChaletsButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home2);

        b_add = (Button) findViewById(R.id.b_add);
        LogOut = (Button) findViewById(R.id.buttonLogOut);
        viewButton = (Button)findViewById(R.id.b_view);
        returnButton = findViewById(R.id.b_return);
        myChaletsButton = findViewById(R.id.b_my_chalets);

        b_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAdd();
            }
        });
        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openRentList();
            }
        });

        myChaletsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMyChalets();
            }
        });

        LogOut.setOnClickListener( new View.OnClickListener() {public void onClick(View view) {
            openHome();
        }});

        viewButton.setOnClickListener(new View.OnClickListener()  {public void onClick(View view){

            openView();


         }

         }


        );




    }

    public void openAdd(){
        Intent intent = new Intent(this, AddChalet.class);
        startActivity(intent);
    }

    public void openRentList () {
        Intent i = new Intent(this, RentedListActivity.class);
        startActivity(i);
    }
    public void openHome(){
        Intent intent = new Intent(this, home.class);
        startActivity(intent);
    }

    public void openView(){
        Intent intent = new Intent(this, ChaletsList.class);
        startActivity(intent);
    }

    public  void openMyChalets() {
        Intent i = new Intent(this, MyChalets.class);
        startActivity(i);
    }




}