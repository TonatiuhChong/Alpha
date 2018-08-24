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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    private TextView Resultado,hab;
    private Button btn;
    private String[] habitaciones={"Cocina","Habitacion1","Sala","Estudio","Entrada","Comedor"};
    private String[] Sense={"Presencia","Iluminación","Ambiental"};
    private String[] logicos={"true","false"};
    private String [] analogicos={"Apagar","Bajo","Medio","Alto","Encendido Completo"};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View Rec= inflater.inflate(R.layout.fragment_control,container,false);
        //*******
        Value= Rec.findViewById(R.id.Valor);
        hab=  Rec.findViewById(R.id.Habitacion);
        sens=  Rec.findViewById(R.id.TSensor);
        Resultado=Rec.findViewById(R.id.Mirror);
        //Spinner 1
        Spinner spin=Rec.findViewById(R.id.Habitaciones);
        spin.setOnItemSelectedListener(new HabitacionSpinnerClass());
        ArrayAdapter aa = new ArrayAdapter(Rec.getContext(),android.R.layout.simple_spinner_item,habitaciones);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
        //Spinner 2
        Spinner spin2=Rec.findViewById(R.id.TSSensor);
        spin2.setOnItemSelectedListener(new TipoSensorSpinnerClass());
        ArrayAdapter bb = new ArrayAdapter(Rec.getContext(),android.R.layout.simple_spinner_item,Sense);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(bb);
        //Spinner 3
        Spinner spin3=Rec.findViewById(R.id.VSensor);
        spin3.setOnItemSelectedListener(new VSensorSpinnerClass());
        ArrayAdapter cc = new ArrayAdapter(Rec.getContext(),android.R.layout.simple_spinner_item,logicos);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(cc);

        btn=Rec.findViewById(R.id.BtnActualizar);
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

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }


    class TipoSensorSpinnerClass implements AdapterView.OnItemSelectedListener{
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

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
                nino=dataSnapshot.getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}