package com.example.tchong.alpha.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
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

import com.example.tchong.alpha.Activities.MenuActivity;
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

import static android.support.v4.content.ContextCompat.getSystemService;


public class ControlFragment extends Fragment{

    private ListView list;
    private Button btn;
    private ImageView sala,comedor, cocina1,cocina2,estudio,pasillo1,pasillo2,pasillo3,bano,servicio;
    private  EditText EditHab,EditSense,EditValue;
    private String[] rooms = {"Cocina", "Habitacion1", "Sala", "Estudio", "Entrada", "Comedor"};
    private String[] logicos = {"true", "false"};
    private String[] analogicos = {"Apagar", "Bajo", "Medio", "Alto", "Encendido Completo"};
    private String[] Sense = {"Presencia", "Iluminación", "Ambiental"};
    private String[] automatizacion = {"motor", "servo", "luz", "puerta", "ventana"};
    private String[] countries = {
            "Switch",
            "Presencia",
            "Ambiental",
            "Puerta",
            "Ventana",
            "Iluminación"
    };
    Integer[] flags = {
            R.drawable.corriente,
            //here you have to give image name which you already pasted it in /res/drawable-hdpi/
            R.drawable.presencia,
            R.drawable.ambiental,
            R.drawable.puerta,
            R.drawable.ventana,
            R.drawable.iluminacion
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
        EditHab.setText("cocina");
        EditSense.setText("notif");

        //**************
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizar();
            }
        });
        //DIALOGS EMERGENTES
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
        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
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
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://ideorreas.mx/inmotica-domotica/"));

            PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 0, intent, 0);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity());
            builder.setContentIntent(pendingIntent);

            builder.setSmallIcon(R.drawable.ambiental);
            builder.setAutoCancel(true);
            builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_notifications_black_24dp));
            builder.setContentTitle("Cambio de Valor");
            builder.setContentText("Se ha actualizado en " + DatosHabitacion.getInstance().getHabitacion() +" de la acción " +DatosHabitacion.getInstance().getTipo() +" con el valor de " +DatosHabitacion.getInstance().getValor());
            builder.setSubText("Presiona para abrir el mapa");

            NotificationManager notificationManager = (NotificationManager) getActivity().getSystemService(
                    getActivity().NOTIFICATION_SERVICE);
            notificationManager.notify(1, builder.build());


            ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Resultado.setText(dataSnapshot.getValue().toString());
                //nino=dataSnapshot.getValue().toString();
                //Toast.makeText(getActivity(), "Dato Actualizado", Toast.LENGTH_SHORT).show();
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