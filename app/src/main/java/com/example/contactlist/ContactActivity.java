package com.example.contactlist;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class ContactActivity extends AppCompatActivity implements View.OnClickListener {

    int thisContactId;
    private final int IDD_EXIT = 0;
    private final int Pick_image = 1;

    private Button edit, delete, cancel;
    private EditText name, phone;
    private ImageButton avatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        init();
    }
    private void init(){
        Bundle bundle = getIntent().getExtras();
        thisContactId = bundle.getInt("thisContact");
        edit = findViewById(R.id.editButton);
        edit.setOnClickListener(this);
        delete = findViewById(R.id.deleteButton);
        delete.setOnClickListener(this);
        cancel = findViewById(R.id.contactCancel);
        cancel.setOnClickListener(this);
        name = findViewById(R.id.nameLabel);
        phone = findViewById(R.id.phoneLabel);
        name.setText(ContactList.getOneContact(thisContactId).getName());
        name.setEnabled(false);
        phone.setText(ContactList.getOneContact(thisContactId).getPhone());
        phone.setEnabled(false);
        avatar = findViewById(R.id.imageButton);
        avatar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.editButton:{
                Intent intent = new Intent(this, EditContactActivity.class);
                intent.putExtra("ID", thisContactId);
                startActivity(intent);
                break;
            }
            case R.id.deleteButton:{
                showDialog(IDD_EXIT);
                break;
            }
            case R.id.contactCancel:{
                finish();
                break;
            }
            case R.id.imageButton:{
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                //Тип получаемых объектов - image:
                photoPickerIntent.setType("image/*");
                //Запускаем переход с ожиданием обратного результата в виде информации об изображении:
                startActivityForResult(photoPickerIntent, Pick_image);
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch (requestCode) {
            case Pick_image :
                if (resultCode == RESULT_OK) {
                    try {

                        //Получаем URI изображения, преобразуем его в Bitmap
                        //объект и отображаем в элементе ImageView нашего интерфейса:
                        final Uri imageUri = imageReturnedIntent.getData();
                        final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                        final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                        avatar.setImageBitmap(selectedImage);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
        }
    }
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case IDD_EXIT:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Are you sure you want to delete?");
            // Создаем кнопку "Yes" и обработчик события
                builder.setPositiveButton(
                        "Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                ContactList.remove(thisContactId);
                                finish();
                            }
                        });
                // Создаем кнопку "No" и обработчик события
                builder.setNegativeButton(
                        "No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
            builder.setCancelable(false);
            return builder.create();
            default:
                return null;
        }
    }
    }
