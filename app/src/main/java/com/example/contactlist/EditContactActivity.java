package com.example.contactlist;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EditContactActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText name, phone;
    private Button ok, cancel;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);
        Bundle bundle = getIntent().getExtras();
        id = bundle.getInt("ID");
        init();
        ok.setOnClickListener(this);
        cancel.setOnClickListener(this);
    }
    private void init(){
        name = findViewById(R.id.name1);
        name.setText(ContactList.getOneContact(id).getName());
        phone = findViewById(R.id.phone1);
        phone.setText(ContactList.getOneContact(id).getPhone());
        ok = findViewById(R.id.okButton);
        cancel = findViewById(R.id.cancelButton);
        setTitle("Edit contact");
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.okButton:{
                if (id < -1){
                    Toast.makeText(this, "Error!", Toast.LENGTH_SHORT);
                }else{
                    ContactList.getOneContact(id).setName(name.getText().toString())
                            .setPhone(phone.getText().toString());
                    finish();
                }
            }
            case R.id.cancelButton:{
                finish();
                break;
            }
        }
    }
}
