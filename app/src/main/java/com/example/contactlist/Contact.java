package com.example.contactlist;

import androidx.annotation.Nullable;

public class Contact {
    private String name, phone;
    private int id;

    public Contact(){}
    public Contact(String name, String phone, int id) {
        this.name = name;
        this.phone = phone;
        this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this.phone.equals(((Contact)obj).phone)){
            return this.phone.equals(((Contact)obj).phone);
        }else{
            return false;
        }
    }

    public Contact(String name, String phone) {
        this.name = name;
        this.phone = phone;
        this.id = R.drawable.ic_menu_add;
    }

    public String getName() {
        return name;
    }
    public String getPhone() {
        return phone;
    }
    public int getId() {
        return id;
    }

    public Contact setName(String name) {
        this.name = name;
        return this;
    }
    public Contact setPhone(String phone) {
        this.phone = phone;
        return this;
    }
    public Contact setId(int id) {
        this.id = id;
        return this;
    }
}
