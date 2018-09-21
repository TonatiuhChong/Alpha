package com.example.tchong.alpha.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tchong.alpha.R;

import java.util.List;

public class AdaptadorListaSensores extends RecyclerView.Adapter<AdaptadorListaSensores.ViewHolder> {

    private List<LisitemSensores> listItems;
    private Context context;

    public AdaptadorListaSensores(List<LisitemSensores> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.accioneslist,viewGroup,false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
      final LisitemSensores listSensores=listItems.get(i);

    }


    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView TipoSensor,ValorSensor,Tipomotor,ValorMotor,HabitacionListview;
        public LinearLayout layout;


        public ViewHolder(View itemView) {
            super(itemView);

            TipoSensor=(TextView)itemView.findViewById(R.id.nombre);

            layout=(LinearLayout)itemView.findViewById(R.id.LinearCartita);


        }
    }
}
