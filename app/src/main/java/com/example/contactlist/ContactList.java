package com.example.contactlist;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;


public class ContactList extends SQLiteOpenHelper {
    public static final String DB_CONTACTS = "contacts.db";
    public static final String TABLE_NAME = "contact";
    public static final String NAME = "first_name";
    public static final String PHONE = "phone";
    public static final String IMAGE = "image";

    private static final ArrayList<Contact> ALL_CONTACT = new ArrayList<>();

    public ContactList(@Nullable Context context) {
        super(context, DB_CONTACTS, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME
                + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NAME + " TEXT, " + PHONE + " TEXT, " + IMAGE + " INTEGER);");
        putToSQL(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void putToSQL(SQLiteDatabase db){
        ContentValues values = new ContentValues();
        for (Contact c: ALL_CONTACT) {
            values.put(NAME, c.getName());
            values.put(PHONE, c.getPhone());
            values.put(IMAGE, c.getId());
            db.insert(TABLE_NAME, NAME, values);
        }

    }

    public static void add(Contact ... contacts){
        for (Contact temp: contacts) {
            ALL_CONTACT.add(temp);
        }
    }
    public static void remove(int index){
        ALL_CONTACT.remove(index);
    }

    public static Contact getOneContact(int index){
        return ALL_CONTACT.get(index);
    }
    public static ArrayList<Contact> getAllContactsAList(){
        return ALL_CONTACT;
    }
    public static void removeLast(){
        ALL_CONTACT.remove(ALL_CONTACT.size()-1);
    }
}