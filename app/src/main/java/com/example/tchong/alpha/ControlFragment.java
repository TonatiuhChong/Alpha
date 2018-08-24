package com.example.tchong.alpha;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class ControlFragment extends Fragment {

    private EditText Child,Value,hab,sens;
    private String nino,nvalor;
    private TextView Resultado;
    private Button btn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View Rec= inflater.inflate(R.layout.fragment_control,container,false);
        //*******
        Value= Rec.findViewById(R.id.Valor);
        hab=  Rec.findViewById(R.id.Habitacion);
        sens=  Rec.findViewById(R.id.TSensor);
        Resultado=Rec.findViewById(R.id.Mirror);

        btn=Rec.findViewById(R.id.BtnActualizar);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizar();
            }
        });


        return Rec;
    }

    private void actualizar() {
        String Habitacion=hab.getText().toString();
        String TipoSensor=sens.getText().toString();

        String valor=Value.getText().toString();


        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Habitaciones").child(Habitacion);
        Map<String,Object> map= new HashMap<String, Object>();
        map.put(TipoSensor,valor);
        ref.updateChildren(map);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Resultado.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}