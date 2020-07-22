package com.example.contactlist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class NewContactActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText name, phone;
    private Button ok, cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        init();
    }

    private void init(){
        name = findViewById(R.id.name1);
        phone = findViewById(R.id.phone1);
        ok = findViewById(R.id.okButton);
        cancel = findViewById(R.id.cancelButton);
        ok.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
     switch(v.getId()){
         case R.id.okButton:{
             if (phone.getText().toString().equals("") || name.getText().toString().equals("")){
                 Toast.makeText(this, "Please, fill in all fields", Toast.LENGTH_SHORT);
             }else{
                 ContactList.removeLast();
                 ContactList.add(new Contact(name.getText().toString(), phone.getText().toString()));
                 finish();
                 break;
             }
         }
         case R.id.cancelButton:{
             ContactList.removeLast();
             finish();
             break;
         }
     }
    }
}
