package com.example.tchong.alpha.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tchong.alpha.R;
import com.example.tchong.alpha.Singletons.DatosHabitacion;
import com.example.tchong.alpha.Userdialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ControlFragment extends Fragment {

    private EditText Child, Value, sens;
    private String Hab, TSens, Valor, nino;
    private TextView Resultado;
    private Button btn;
    private ImageView sala,comedor, cocina1,cocina2,estudio,pasillo1,pasillo2,pasillo3,bano,servicio;
    private  EditText EditHab,EditSense,EditValue;
    private String[] rooms = {"Cocina", "Habitacion1", "Sala", "Estudio", "Entrada", "Comedor"};
    private String[] logicos = {"true", "false"};
    private String[] analogicos = {"Apagar", "Bajo", "Medio", "Alto", "Encendido Completo"};
    private String[] Sense = {"Presencia", "Iluminación", "Ambiental"};
    private String[] automatizacion = {"motor", "servo", "luz", "puerta", "ventana"};
    private String[] countries = new String[] {
            "Switch",
            "Presencia",
            "Ambiental",
            "Puerta",
            "Ventana",
            "Iluminación"
    };
    int[] flags = new int[]{
            R.drawable.corriente,
            //here you have to give image name which you already pasted it in /res/drawable-hdpi/
            R.drawable.presencia,
            R.drawable.ambiental,
            R.drawable.puerta,
            R.drawable.ventana,
            R.drawable.iluminacion
    };
    private String[] modos = {"motor", "sensor"};
    ArrayAdapter<String>  bb;
    ArrayAdapter<String>  cc;

    List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        for(int i=0;i<10;i++){
//            HashMap<String> hm = new HashMap<String>();
//            hm.put("txt", "Country : " + countries[i]);
//            hm.put("flag", Integer.toString(flags[i]) );
//            aList.add(hm);
//        }
//        String[] from = { "txt","flag" };
//        int[] to = { R.id.TextAcciones,R.id.ImgAcciones};
//        final SimpleAdapter adap =new SimpleAdapter(getActivity(),aList,R.layout.listviewacciones,from,to);


        View Rec = inflater.inflate(R.layout.fragment_control, container, false);
        //*******
//        Resultado = Rec.findViewById(R.id.Mirror);
        btn = Rec.findViewById(R.id.BtnActualizar);

        //********Habitaciones
        sala=Rec.findViewById(R.id.ImgHabSala);
        comedor=Rec.findViewById(R.id.ImgHabComedor);
        cocina1=Rec.findViewById(R.id.ImgHabCocina1);
        cocina2=Rec.findViewById(R.id.ImgHabCocina2);
        estudio=Rec.findViewById(R.id.ImgHabEstudio);
        pasillo1=Rec.findViewById(R.id.ImgHabPasillo1);
        pasillo2=Rec.findViewById(R.id.ImgHabPasillo2);
        pasillo3=Rec.findViewById(R.id.ImgHabPasillo3);
        bano=Rec.findViewById(R.id.ImgHabBano);
        servicio=Rec.findViewById(R.id.ImgHabServicio);

        //*******EditText
        EditHab=Rec.findViewById(R.id.EditHabitacion);
        EditSense=Rec.findViewById(R.id.EditSensor);
        EditValue=Rec.findViewById(R.id.EditValor);
        //**************
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizar();
            }
        });


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
                        break;
                    case 1:
                        DatosHabitacion.getInstance().setHabitacion(rooms[position]);
                        break;
                    case 2:
                        DatosHabitacion.getInstance().setHabitacion(rooms[position]);
                        break;
                    case 3:
                        DatosHabitacion.getInstance().setHabitacion(rooms[position]);
                        break;
                    case 4:
                        DatosHabitacion.getInstance().setHabitacion(rooms[position]);
                        break;
                    case 5:
                        DatosHabitacion.getInstance().setHabitacion(rooms[position]);
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

                      break;
                  case 1:
                      DatosHabitacion.getInstance().setModo(modos[position]);
                      bb = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, Sense);
                      bb.setDropDownViewResource(android.R.layout.simple_expandable_list_item_1);
                      spin2.setAdapter(bb);
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

                        break; }


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        sala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogos();

            }
        });

        comedor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogos();
            }
        });
        cocina1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogos();
            }
        });
        cocina2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogos();
            }
        });
        servicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogos();
            }
        });
        bano.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogos();
            }
        });
        pasillo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogos();
            }
        });
        estudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogos();
            }
        });
        pasillo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogos();
            }
        });
        pasillo3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogos();
            }
        });






        return Rec;
    }

    private void dialogos() {
        final Dialog dialog= new Dialog(getActivity());
        dialog.setContentView(R.layout.dialogcasa);
        dialog.setTitle("Title...");
        TextView text = (TextView) dialog.findViewById(R.id.text);
        text.setText("Android custom dialog example!");
//                ListView pp=(ListView)dialog.findViewById(R.id.listAcciones);
//
//                pp.setAdapter(adap);
        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }


    private void actualizar() {
        String Habitacion=EditHab.getText().toString();
        String TipoSensor=EditSense.getText().toString();
        String valor=EditValue.getText().toString();

        if (Habitacion.isEmpty()){
            EditHab.setError("Ingresa o selecciona una habitacion");
            EditHab.requestFocus();
        }
        if (TipoSensor.isEmpty()){
            EditSense.setError("Ingresa un tipo de sensor");
            EditSense.requestFocus();
        }
        if (valor.isEmpty()){
            EditValue.setError("Ingresa el valor del sensor");
            EditValue.requestFocus();
        }

        else{

            DatosHabitacion.getInstance().setHabitacion(Habitacion);
            DatosHabitacion.getInstance().setTipo(TipoSensor);
            DatosHabitacion.getInstance().setValor(valor);


        DatabaseReference ref= FirebaseDatabase.getInstance().getReference().child("Habitaciones").child(DatosHabitacion.getInstance().getHabitacion());
        Map<String,Object> map= new HashMap<String, Object>();
        map.put(TipoSensor,valor);
        ref.updateChildren(map);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Resultado.setText(dataSnapshot.getValue().toString());
                //nino=dataSnapshot.getValue().toString();
                Toast.makeText(getActivity(), "Dato Actualizado", Toast.LENGTH_SHORT).show();
                EditHab.getText().clear();
                EditSense.getText().clear();
                EditValue.getText().clear();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });}
    }



}