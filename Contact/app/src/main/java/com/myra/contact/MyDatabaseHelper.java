package com.myra.contact;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    private  Context context;
    private static final String DATABASE_NAME = "ContactDB.db";
    private static final int DATABASE_VERSION =  1 ;
    private static final String TABLE_NAME = "contact_tab";
    private static final String Table_Column_ID="id";

    private static final String Table_Column_1_Name="name";

    private static final String Table_Column_2_Email="email";

    public static final String Table_Column_3_PhoneNumber="phone_number";

    private static final String Table_Column_4_Ville="ville";
    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + " (" + Table_Column_ID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + Table_Column_1_Name+" VARCHAR, "+Table_Column_2_Email+" VARCHAR , "+Table_Column_3_PhoneNumber+" VARCHAR , "+Table_Column_4_Ville+" VARCHAR)";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    void  addContact(String name , String email , int phone_number , String ville){
        SQLiteDatabase db  = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(Table_Column_1_Name , name);
        cv.put(Table_Column_2_Email , email);
        cv.put(Table_Column_3_PhoneNumber , phone_number);
        cv.put(Table_Column_4_Ville , ville);
        long result = db.insert(TABLE_NAME , null , cv);
        if (result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
        }
    }
    Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null ;
        if (db != null){
            cursor =  db.rawQuery(query , null);
        }
        return cursor;
    }



    void updateData(String row_id, String name, String email, String phone_number , String ville ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(Table_Column_1_Name, name);
        cv.put(Table_Column_2_Email, email);
        cv.put(Table_Column_3_PhoneNumber, phone_number);
        cv.put(Table_Column_4_Ville, ville);


        long result = db.update(TABLE_NAME, cv, "id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!", Toast.LENGTH_SHORT).show();
        }

    }

    void deleteOneRow(String row_id){
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(TABLE_NAME, "id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    void deleteAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }

}
