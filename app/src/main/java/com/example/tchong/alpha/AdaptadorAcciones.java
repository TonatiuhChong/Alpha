package com.example.tchong.alpha;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class AdaptadorAcciones extends RecyclerView.Adapter<AdaptadorAcciones.ViewHolder> {

    private List<ListitemAcciones> listItems;

    public AdaptadorAcciones(List<ListitemAcciones> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    private Context context;




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v= LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.accioneslist,viewGroup,false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        final ListitemAcciones listItem=listItems.get(i);

        viewHolder.nombre.setText(listItem.getDesc());
        viewHolder.imagen.setImageResource(listItem.getFoto());

        viewHolder.imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, listItem.getDesc(), Toast.LENGTH_SHORT).show();

            }
        });
    }


    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView nombre;
        public ImageView imagen;
        public LinearLayout layout;


        public ViewHolder(View itemView) {
            super(itemView);

            nombre=(TextView)itemView.findViewById(R.id.nombre);
            imagen=(ImageView)itemView.findViewById(R.id.imagen);
            layout=(LinearLayout)itemView.findViewById(R.id.LinearCartita);


        }
    }
}
