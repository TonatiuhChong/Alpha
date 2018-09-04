package com.example.tchong.alpha.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tchong.alpha.R;
import com.example.tchong.alpha.Singletons.DatosHabitacion;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;


public class ControlFragment extends Fragment {

    private EditText Child, Value, sens;
    private String Hab, TSens, Valor, nino;
    private TextView Resultado;
    private Button btn;

    private String[] rooms = {"Cocina", "Habitacion1", "Sala", "Estudio", "Entrada", "Comedor"};
    private String[] logicos = {"true", "false"};
    private String[] analogicos = {"Apagar", "Bajo", "Medio", "Alto", "Encendido Completo"};
    private String[] Sense = {"Presencia", "Iluminación", "Ambiental"};
        private String[] automatizacion = {"motor", "servo", "luz", "puerta", "ventana"};
    private String[] modos = {"motor", "sensor"};
    ArrayAdapter<String>  bb;
    ArrayAdapter<String>  cc;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View Rec = inflater.inflate(R.layout.fragment_control, container, false);
        //*******
        Resultado = Rec.findViewById(R.id.Mirror);
        btn = Rec.findViewById(R.id.BtnActualizar);

        //Deteccion
        final Spinner spin = Rec.findViewById(R.id.Habitaciones);
        final Spinner spin2 = Rec.findViewById(R.id.TSensor);
        final Spinner spin3 = Rec.findViewById(R.id.VSensor);
        final Spinner spin4 = Rec.findViewById(R.id.modo);


        //Adaptadores
        //tipo cambio
        ArrayAdapter<String> dd = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, modos);
        dd.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        spin4.setAdapter(dd);
        //habitacion
        ArrayAdapter<String> aa = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, rooms);
        aa.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
        spin.setAdapter(aa);


        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        DatosHabitacion.getInstance().setHabitacion(rooms[position]);
                        Toast.makeText(getActivity(), DatosHabitacion.getInstance().getHabitacion(), Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        DatosHabitacion.getInstance().setHabitacion(rooms[position]);
                        Toast.makeText(getActivity(), DatosHabitacion.getInstance().getHabitacion(), Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        DatosHabitacion.getInstance().setHabitacion(rooms[position]);
                        Toast.makeText(getActivity(), DatosHabitacion.getInstance().getHabitacion(), Toast.LENGTH_SHORT).show();//                      CAMBIA HABITACION
                        break;
                    case 3:
                        DatosHabitacion.getInstance().setHabitacion(rooms[position]);
                        Toast.makeText(getActivity(), DatosHabitacion.getInstance().getHabitacion(), Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        DatosHabitacion.getInstance().setHabitacion(rooms[position]);
                        Toast.makeText(getActivity(), DatosHabitacion.getInstance().getHabitacion(), Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                        DatosHabitacion.getInstance().setHabitacion(rooms[position]);
                        Toast.makeText(getActivity(), DatosHabitacion.getInstance().getHabitacion(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spin.setAnimation(new Animation() {
                    @Override
                    public void reset() {
                        super.reset();
                    }
                });

            }
        });


      spin4.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
          @Override
          public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              switch (position) {
                  case 0:
                      DatosHabitacion.getInstance().setModo(modos[position]);
                      bb = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, automatizacion);
                      bb.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
                      spin2.setAdapter(bb);
                      Toast.makeText(getActivity(), DatosHabitacion.getInstance().getModo(), Toast.LENGTH_SHORT).show();//                                                              CAMBIA MODO

                      break;
                  case 1:
                      DatosHabitacion.getInstance().setModo(modos[position]);
                      bb = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, Sense);
                      bb.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
                      spin2.setAdapter(bb);
                      Toast.makeText(getActivity(), DatosHabitacion.getInstance().getModo(), Toast.LENGTH_SHORT).show();
                      break; }


          }

          @Override
          public void onNothingSelected(AdapterView<?> parent) {

          }
      });

        spin2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            private String[] logicos = {"true", "false"};
//            private String[] analogicos = {"Apagar", "Bajo", "Medio", "Alto", "Encendido Completo"};
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        if (DatosHabitacion.getInstance().getModo()=="motor"){
                            DatosHabitacion.getInstance().setTipo("logicos");
                            cc = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, logicos);
                            cc.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
                            spin3.setAdapter(cc);

                        }else DatosHabitacion.getInstance().setTipo("analogicos");
                        break;
                    case 1:
                        DatosHabitacion.getInstance().setModo(modos[position]);
                        cc = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, Sense);
                        cc.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
                        spin3.setAdapter(cc);
                        Toast.makeText(getActivity(), DatosHabitacion.getInstance().getModo(), Toast.LENGTH_SHORT).show();
                        break; }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });




        return Rec;
    }


    private void actualizar() {
        String Habitacion=DatosHabitacion.getInstance().getHabitacion();
        String TipoSensor=DatosHabitacion.getInstance().getTipo();
        String valor=DatosHabitacion.getInstance().getValor();


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