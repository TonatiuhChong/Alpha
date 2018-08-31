package com.example.tchong.alpha.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tchong.alpha.R;
import com.example.tchong.alpha.Singletons.ValoresHab;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class ControlFragment extends Fragment {

    private EditText Child,Value,sens;
    private String Hab,TSens,Valor,nino;
    private TextView Resultado;
    private Button btn;
    private String[] rooms={"Cocina","Habitacion1","Sala","Estudio","Entrada","Comedor"};
    private String[] Sense={"Presencia","Iluminación","Ambiental"};
   // private String[] logicos={"true","false"};
    private String [] analogicos={"Apagar","Bajo","Medio","Alto","Encendido Completo"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View Rec= inflater.inflate(R.layout.fragment_control,container,false);
        //*******
        Value= Rec.findViewById(R.id.Valor);
        sens=  Rec.findViewById(R.id.TSensor);
        Resultado=Rec.findViewById(R.id.Mirror);
        btn=Rec.findViewById(R.id.BtnActualizar);

        //Deteccion
        Spinner spin=Rec.findViewById(R.id.Habitaciones);
        Spinner spin2=Rec.findViewById(R.id.TSSensor);
        Spinner spin3=Rec.findViewById(R.id.VSensor);
        //Adaptadores
        ArrayAdapter<String> aa = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,rooms);
        aa.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        ArrayAdapter<String> bb = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,Sense);
        bb.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        ArrayAdapter cc = new ArrayAdapter(getActivity(),android.R.layout.simple_spinner_item,rooms);
        cc.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Spinner Adaptacion al spinner
        HabitacionSpinnerClass hab1= new HabitacionSpinnerClass();
        TipoSensorSpinnerClass hab2= new TipoSensorSpinnerClass();
        VSensorSpinnerClass hab3= new VSensorSpinnerClass();
        spin.setOnItemSelectedListener(hab1);
        spin2.setOnItemSelectedListener(hab2);
        spin3.setOnItemSelectedListener(hab3);
        spin.setAdapter(aa);
        spin.setAdapter(bb);
        spin.setAdapter(cc);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizar();
            }
        });

        return Rec;
    }


    class HabitacionSpinnerClass implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                ValoresHab.getInstance().setThab(rooms[position]);
                break;
            case 1:
                ValoresHab.getInstance().setThab(rooms[position]);
                break;
            case 2:
                ValoresHab.getInstance().setThab(rooms[position]);
                break;
            case 3:
                ValoresHab.getInstance().setThab(rooms[position]);
                break;
            case 4:
                ValoresHab.getInstance().setThab(rooms[position]);
                break;
            case 5:
                ValoresHab.getInstance().setThab(rooms[position]);
                break;
            //Toast.makeText(getActivity(), Integer.toString(position), Toast.LENGTH_SHORT).show();
        }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }


    class TipoSensorSpinnerClass implements AdapterView.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                ValoresHab.getInstance().setTsensor(Sense[position]);
                break;
            case 1:
                ValoresHab.getInstance().setTsensor(Sense[position]);
                break;
            case 2:
                ValoresHab.getInstance().setTsensor(Sense[position]);
                break;
        }
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    class  VSensorSpinnerClass implements  AdapterView.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    private void actualizar() {
        String Habitacion=ValoresHab.getInstance().getThab();
        String TipoSensor=sens.getText().toString();
        String valor=Value.getText().toString();

        if (TipoSensor.isEmpty()){
            sens.setError("Ingresa un tipo de sensor");
            sens.requestFocus();
        }
        if (valor.isEmpty()){
            Value.setError("Ingresa el valor del sensor");
            Value.requestFocus();
        }
        else{
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Habitaciones").child(Habitacion);
        Map<String,Object> map= new HashMap<String, Object>();
        map.put(TipoSensor,valor);
        ref.updateChildren(map);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Resultado.setText(dataSnapshot.getValue().toString());
                nino=dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });}
    }


}