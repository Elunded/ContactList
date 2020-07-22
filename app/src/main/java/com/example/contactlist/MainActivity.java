package com.example.contactlist;


import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  implements AdapterView.OnItemClickListener {
    private static final int IDM_ADD = 101;

    private ListView list;

    private static CustomAdapter customAdapter;

    private ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = findViewById(R.id.list);
        updateList();
    }

    public void updateList() {
        customAdapter = new CustomAdapter(this.getLayoutInflater());
        list.setAdapter(customAdapter);
        list.setOnItemClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(Menu.NONE, IDM_ADD, Menu.NONE, "Add")
                .setIcon(R.drawable.ic_menu_add)
                .setAlphabeticShortcut('a');
        return (super.onCreateOptionsMenu(menu));
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateList();
        SQLiteDatabase db = new ContactList(
                getApplicationContext()).getWritableDatabase();
        if (db == null) {
                    Toast.makeText(getApplicationContext(),
                            "Error create database!", Toast.LENGTH_LONG).show();
                }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case IDM_ADD: {
                ContactList.add(new Contact());
                Intent in = new Intent();
                in.setClass(this, NewContactActivity.class);
                startActivity(in);
                break;
            }
        }
        return (super.onOptionsItemSelected(item));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(this, ContactActivity.class);
        intent.putExtra("thisContact", position);
        startActivity(intent);
        updateList();
    }

    class CustomAdapter extends BaseAdapter {
        private ArrayList<Contact> all = ContactList.getAllContactsAList();
        private LayoutInflater li;

        class ViewHolder{
            TextView name, phone;
            ImageView img;
        }

        public CustomAdapter(LayoutInflater li)
        {
            this.li = li;
        }

        @Override
        public int getCount() {
            return all.size();
        }

        @Override
        public Object getItem(int index) {
            return all.get(index);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            Contact c = (Contact)getItem(position);
            ViewHolder viewHolder = null;
            if (view == null) {
                view = li.inflate(R.layout.contact, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.name = view.findViewById(R.id.name);
                viewHolder.img = view.findViewById(R.id.image);
                viewHolder.phone = view.findViewById(R.id.phone);
                view.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder)view.getTag();
            }
            viewHolder.name.setText(c.getName());
            viewHolder.phone.setText(c.getPhone() + "");
            viewHolder.img.setImageResource(R.drawable.ic_menu_add);
            return view;
        }
    }
    }