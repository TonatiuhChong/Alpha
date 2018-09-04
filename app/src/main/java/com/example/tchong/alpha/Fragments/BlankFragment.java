package com.example.tchong.alpha.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tchong.alpha.R;


public class BlankFragment extends Fragment {

    ImageView  sala,comedor, cocina1,cocina2,servicio,bano,pasillo1,pasillo2,pasillo3,estudio;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View Rec = inflater.inflate(R.layout.fragment_control, container, false);


//       // sala= Rec.findViewById(R.id.HabSala);
//        comedor=Rec.findViewById(R.id.HabComedor);
//        cocina1=Rec.findViewById(R.id.HabCocina1);
//        cocina2=Rec.findViewById(R.id.HabCocina2);
//        servicio=Rec.findViewById(R.id.HabServicio);
//        bano=Rec.findViewById(R.id.HabBano);
//        pasillo1=Rec.findViewById(R.id.HabPasillo1);
//        pasillo2=Rec.findViewById(R.id.HabPasillo2);
//        pasillo3=Rec.findViewById(R.id.HabPasillo3);
//        estudio=Rec.findViewById(R.id.HabEstudio);




//        sala.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getActivity(), "scsaijo", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//


       return Rec;
    }


   
}
