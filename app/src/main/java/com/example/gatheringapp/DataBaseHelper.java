package com.example.gatheringapp;
import java.text.SimpleDateFormat;
import java.util.Date;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.net.InetAddress;
import java.util.ArrayList;

public class DataBaseHelper extends SQLiteOpenHelper {
    private static String currentUser;
    private static final String DB_Name ="Renting_Chalet";
    private static final int DB_Version = 4;
    public static final String chalet_ID = " ID";
    public static final String chalet_name = " NAME";
    public static final String chalet_price = " PRICE";
    public static final String chalet_table = " CHALET";
    public static final String CHALET_DECRIPTION = " chalet_decription";
    public static final String CHALET_ADDRESS = " chalet_address";
    public static final String imageName = " imageName";

    public static final String image = " image";

   // public static final String DBNAME = "Login.db";
    public static final String TABLENAME = " users";
    public static final String name = " username";
    public static final String pass = " password";
    public static final String email = " emailAddress";
    public static final String phone = " phoneNumber";

    //
    public static final String SessionTableName = "session";
    public static final String userId = "user_name";
    private ByteArrayOutputStream objectOutputStream;
    byte[] imageBytes;
    Context context;

    public DataBaseHelper(@Nullable Context context) {
        super(context, DB_Name, null, DB_Version);
        this.context = context;
    }


    private void populateSeeds(SQLiteDatabase db) {
        if (context == null) {return;}

        insertData("Owner1", "123", "owner1@gmail.com", "123", db);
        insertData("Owner2", "123", "owner2@gmail.com", "123", db);
        insertData("Owner3", "123", "owner3@gmail.com", "123", db);
        insertData("Owner4", "123", "owner4@gmail.com", "123", db);

        int drawableId = context.getResources().getIdentifier("c1", "drawable", context.getPackageName());
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), drawableId);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] img = byteArrayOutputStream.toByteArray();

        Chalet c = new Chalet("Cozy Mountain Retreat");
        c.setChalet_price(1500);
        c.setChalet_decription( "Escape to this charming chalet nestled in the heart of the mountains.");
        c.setChalet_address("123 Mountain Lane, Alpine Town");
        c.setImage(img);
        c.setImageName("c1.jpeg");
        c.setOwner("Owner1");
        addChalet(c, db);

        drawableId = context.getResources().getIdentifier("c2", "drawable", context.getPackageName());
        bitmap = BitmapFactory.decodeResource(context.getResources(), drawableId);
        byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        img = byteArrayOutputStream.toByteArray();

        c = new Chalet("Ski Lover's Paradise");
        c.setChalet_price(200);

        c.setChalet_decription(  "Experience the ultimate ski getaway in this luxurious chalet with stunning mountain views.");

        c.setChalet_address("456 Ski Slope Road, Snowy Village");
        c.setImage(img);
        c.setOwner("Owner2");
        c.setImageName("c2.jpeg");
        addChalet(c, db);

        drawableId = context.getResources().getIdentifier("c3", "drawable", context.getPackageName());
         bitmap = BitmapFactory.decodeResource(context.getResources(), drawableId);
         byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
         img = byteArrayOutputStream.toByteArray();

         c = new Chalet("Rustic Mountain Haven");
        c.setChalet_price(2500);

        c.setChalet_decription(  "Discover the tranquility of this cozy chalet surrounded by nature's beauty.");

        c.setChalet_address("789 Pine Ridge, Serene Valley");
        c.setImage(img);
        c.setOwner("Owner3");
        c.setImageName("c3.jpeg");
        c.rented_by = "Owner4";
        c.rented_at = "2022-01-01";
        c.duration = 5;
        addChalet(c, db);

        drawableId = context.getResources().getIdentifier("c4", "drawable", context.getPackageName());
        bitmap = BitmapFactory.decodeResource(context.getResources(), drawableId);
        byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        img = byteArrayOutputStream.toByteArray();

        c = new Chalet("Mountain Luxury Retreat");
        c.setChalet_price(300);

        c.setChalet_decription(  "Indulge in the elegance and comfort of this high-end chalet with top-notch amenities.");

        c.setChalet_address("101 Luxury Lane, Majestic Peaks");
        c.setImage(img);
        c.setOwner("Owner4");
        c.setImageName("c4.jpeg");
        c.rented_at = "2023-05-28";
        c.rented_by = "Owner3";
        c.duration = 3;
        addChalet(c, db);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_table = "CREATE TABLE" + chalet_table + "(" + chalet_ID + " INTEGER PRIMARY KEY," + chalet_name +
                " Text ," + chalet_price + " INTEGER ," + CHALET_DECRIPTION +
                " Text ," + CHALET_ADDRESS + " Text ,"
                + imageName + " Text ,"  + image+ " blob ,"
                + "owner TEXT ,"
                + "rented_at TEXT ,"
                + "rented_by TEXT ,"
                + "rentalDuration INTEGER)";
        db.execSQL(create_table);

        db.execSQL("create Table " + TABLENAME + "(" + name + " TEXT primary key, " + pass + " TEXT , " + email + " TEXT ," + phone + " TEXT)");
        populateSeeds(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String delete_query = "drop table if exists "+chalet_table;
       db.execSQL(delete_query);
     onCreate(db);
        db.execSQL("drop Table if exists " + TABLENAME);

    }

    public String getCurrentUser() {
        return currentUser;
    }
    public Boolean insertData(String username, String password , String emailAddress , String phoneNumber, SQLiteDatabase db){
        SQLiteDatabase MyDB = db;
        ContentValues contentValues= new ContentValues();
        contentValues.put(name, username);
        contentValues.put(pass, password);
        contentValues.put(email, emailAddress);
        contentValues.put(phone, phoneNumber);
        long result = MyDB.insert(TABLENAME, null, contentValues);
        if(result==-1) return false;
        return true;
    }
    public Boolean insertData(String username, String password , String emailAddress , String phoneNumber ){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(name, username);
        contentValues.put(pass, password);
        contentValues.put(email, emailAddress);
        contentValues.put(phone, phoneNumber);

        long result = MyDB.insert(TABLENAME, null, contentValues);
        if(result==-1) return false;
        currentUser = username;
        return true;
    }


    public Boolean checkUsername(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from " + TABLENAME + " where " + name + " = ?", new String[]{username});
        if (cursor.getCount() > 0)
            return true;

        return false;
    }

    public Boolean checkUsernamePassword(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from " + TABLENAME + " where " + name + " = ? and " + pass + " = ?", new String[] {username,password});
        if(cursor.getCount()>0) {
            currentUser = username;
            return true;
        }

        return false;
    }




    public boolean addChalet(Chalet chalet, SQLiteDatabase db) {
        ContentValues values = new ContentValues();

        //    values.put(chalet_ID , chalet.getChalet_id());
        values.put(chalet_name , chalet.getChalet_name());
        values.put(CHALET_DECRIPTION , chalet.getChalet_decription());
        values.put(CHALET_ADDRESS , chalet.getChalet_address());
        values.put(chalet_price , chalet.getChalet_price());
        values.put(imageName , chalet.getImageName());
        values.put(image, chalet.getImage());
        values.put("owner", chalet.getOwner());
        if (chalet.rented_at != null)values.put("rented_at", chalet.rented_at);
        if (chalet.rented_by != null)values.put("rented_by", chalet.rented_by);
        if (chalet.duration != 0)values.put("rentalDuration", chalet.duration);
        System.out.println("  ==  ==  " + chalet.rented_at + "  - " + chalet.rented_by);
        long insert = db.insert(chalet_table , null , values);

        if (insert == -1)
            return false;
        else
            return true;
    }


    public boolean addChalet(Chalet chalet) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

    //    values.put(chalet_ID , chalet.getChalet_id());
        values.put(chalet_name , chalet.getChalet_name());
        values.put(CHALET_DECRIPTION , chalet.getChalet_decription());
        values.put(CHALET_ADDRESS , chalet.getChalet_address());
        values.put(chalet_price , chalet.getChalet_price());
        values.put(imageName , chalet.getImageName());
        values.put(image, chalet.getImage());
        values.put("owner", chalet.getOwner());


       long insert = db.insert(chalet_table , null , values);

       if (insert == -1)
           return false;
       else
           return true;
    }

    public boolean DeleteOne(String chalet) {
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = "Delete From" + chalet_table + " WHERE " + chalet_name + "= '"+chalet+"'";
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor != null && cursor.getCount() > 0) {
            return true;
        } else {
            // nothing happens. no one is added.
            return false;
        }
    }



        public boolean rentChalet(int id, int duration) {

            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            String q = "UPDATE " + chalet_table + " SET rented_at = date('now'), rented_by = ?, rentalDuration = ? WHERE " + chalet_ID + " = ?";

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String currentDate = dateFormat.format(new Date());
            values.put("rented_at",currentDate); // Set the current date and time
            values.put("rented_by", currentUser);
            values.put("rentalDuration", duration);
            String[] whereArgs = {Integer.toString(id) };

            int ra = db.update(chalet_table, values, chalet_ID + " = ?", whereArgs);
            db.close();
            boolean success = (ra > 0);
            return success;

        }

        @SuppressLint("Range")
        public ArrayList<Chalet> getAllChalets() {

        ArrayList<Chalet> chalets = new ArrayList<>();

        String select_query = "select *, COALESCE(date(rented_at, '+' || rentalDuration || ' day') < date('now'), 1) AS retned  from " + chalet_table ;
                //+ " WHERE owner != ?" ;

        SQLiteDatabase db = this.getReadableDatabase();
       Cursor cursor= db.rawQuery(select_query ,null);// new String[]{currentUser});


       if(cursor.moveToFirst()) {
           do{
/*

               int ChaletID = cursor.getColumnIndex(chalet_ID);
              int CID = Integer.parseInt(cursor.getString(ChaletID));

                   int indexName = cursor.getColumnIndex(chalet_name);
                  String name = cursor.getString(indexName);

               int indexPrice = cursor.getColumnIndex(chalet_price);
              int price = Integer.parseInt(cursor.getString(indexPrice));

              int indexDec = cursor.getColumnIndex(CHALET_DECRIPTION)   ;
              String dec = cursor.getString(indexDec) ;

             int indexAddress = cursor.getColumnIndex(CHALET_ADDRESS)  ;
             String address = cursor.getString(indexAddress) ;


               int indexImageName = cursor.getColumnIndex(imageName)  ;
               String imageName = cursor.getString(indexImageName) ;

*/

         //  int imagesIndex = cursor.getColumnIndex(image);
         //   byte[] images = cursor.getBlob(imagesIndex);

             int ChaletID = cursor.getInt(0);
              String name = cursor.getString(1);
              int price = cursor.getInt(2);
               @SuppressLint("Range") String dec = cursor.getInt(cursor.getColumnIndex("retned")) == 0 ? "Rented" : "Available";
               String address = cursor.getString(4);
         //     String imageName = cursor.getString(5);
               System.out.println(" HEL LL LL L " + cursor.getInt(11) + " =<??>= "  + dec + " "+ cursor.getString(8 ) + "   " + cursor.getString(9) + " " + cursor.getInt(10) + "D ");
               byte[] image = cursor.getBlob(6);


         //      Chalet chalet = new Chalet( name ,ChaletID,  dec,  address,price , images , imageName);

               Chalet chalet = new Chalet( name , image);

               chalet.setChalet_id(ChaletID);
               chalet.setChalet_decription(dec);
               chalet.rented_by = cursor.getString(cursor.getColumnIndex("rented_by")) ;
                if( chalet.rented_by == null) {
                    chalet.rented_by = "None";
                }
                chalet.duration = cursor.getInt(cursor.getColumnIndex("rentalDuration"));
                chalet.setChalet_price(price);
                chalet.rented_at = cursor.getString(cursor.getColumnIndex("rented_at"));
                chalet.setOwner(cursor.getString(cursor.getColumnIndex("owner")));
               chalets.add(chalet);


           }while (cursor.moveToNext());

       }

       cursor.close();
       db.close();


        /*
       while(cursor.moveToNext()){

           int indexName = cursor.getColumnIndex(chalet_name);
           String name = cursor.getString(indexName);

           int indexPrice = cursor.getColumnIndex(chalet_price);
           int price = Integer.parseInt(cursor.getString(indexPrice));

           int indexDec = cursor.getColumnIndex(CHALET_DECRIPTION)   ;
           String dec = cursor.getString(indexDec) ;

           int indexAddress = cursor.getColumnIndex(CHALET_ADDRESS)  ;
           String address = cursor.getString(indexAddress) ;



           Chalet chalet = new Chalet(name ,dec , address , price);

           chalets.add(chalet);
       }

*/

        return chalets;
    }

    @SuppressLint("Range")
    public Chalet getChaletById(String id) {

        System.out.println("PLEASE  + " + id);
        String select_query = "select * from " + chalet_table + " where " + chalet_ID +" = ? " ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor= db.rawQuery(select_query ,new String[] {id});
        Chalet c = null;
        if(cursor.moveToFirst()) {
            int ChaletID = cursor.getInt(0);
            String name = cursor.getString(1);
            int price = cursor.getInt(2);
            String dec = cursor.getString(3);
            String address = cursor.getString(4);
            byte[] image = cursor.getBlob(6);
            String owner = cursor.getString(7);
             c = new Chalet( name, ChaletID,   dec, address, price, image, "", owner);
        }
        cursor.close();
        db.close();
        return c;
    }


    public void  storeImage (ModelImage modelImage) {

        try{

            SQLiteDatabase db = this.getWritableDatabase();
            Bitmap bitmap = modelImage.getImage();
            objectOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG , 100 , objectOutputStream)       ;
            imageBytes = objectOutputStream.toByteArray();
            ContentValues contentValues = new ContentValues();
            contentValues.put(image , imageBytes);


            long checkIfQueryRun = db.insert(chalet_table , null , contentValues);
            if(checkIfQueryRun!=0)
                Toast.makeText(context, "image added " , Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(context, "Failed image added " , Toast.LENGTH_SHORT).show();


        }catch (Exception e){
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();

        }





    }


    public boolean returnOutlet(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("rented_at", (String) null);
        values.put("rentalDuration", 0);
        values.put("rented_by", (String)null);

        String[] whereArgs = {Integer.toString(id) };

        int ra = db.update(chalet_table, values, chalet_ID + " = ?", whereArgs);
        db.close();
        boolean success = (ra > 0);
        return success;
    }
}
