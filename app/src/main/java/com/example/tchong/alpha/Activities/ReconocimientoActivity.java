package com.example.tchong.alpha.Activities;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.tchong.alpha.R;
import com.example.tchong.alpha.Singletons.Singleton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class ReconocimientoActivity extends AppCompatActivity implements  View.OnClickListener{

    private TextView NombreU,EmailU,PerfilU;
    private ImageView ImgUSer;
    private ListView lista;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> rooms= new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reconocimiento);

        lista=(ListView)findViewById(R.id.FRlista);
        NombreU=(TextView)findViewById(R.id.FRUSer);
        EmailU=(TextView)findViewById(R.id.FREmail);
        PerfilU=(TextView)findViewById(R.id.FRPerfil);
        ImgUSer=(ImageView)findViewById(R.id.FUserR);


        arrayAdapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,rooms);
        lista.setAdapter(arrayAdapter);

        NombreU.setText(Singleton.getInstance().getUser());
        EmailU.setText(Singleton.getInstance().getEmail());
        PerfilU.setText(Singleton.getInstance().getPassword());
        Glide.with(this).load(Singleton.getInstance().getFoto()).into(ImgUSer);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        conexion();

    }

    private void conexion() {

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Habitaciones");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Set<String> set = new HashSet<String>();
                Iterator i = dataSnapshot.getChildren().iterator();
                while (i.hasNext()){
                    set.add(((DataSnapshot)i.next()).getKey());
                }
                rooms.clear();
                rooms.addAll(set);

                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.BtnActualizar:

                break;

        }

    }



}