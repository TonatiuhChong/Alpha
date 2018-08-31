package com.example.tchong.alpha.Singletons;

import android.net.Uri;
import android.widget.ImageView;

public class CUser  {
    public String name, email, password;
    public Uri foto;

    public CUser(String user, String email, String password, ImageView foto){

    }

    public CUser(String name, String email, String phone, Uri foto) {
        this.name = name;
        this.email = email;
        this.password = phone;
        this.foto=foto;
    }
}