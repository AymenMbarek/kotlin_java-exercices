package com.myra.contact;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {
    private Context context;
    private Activity activity;
    private ArrayList id , name , email , phone_number , ville;
    int position ;
    private MyViewHolder holder;

    CustomAdapter(Context context , ArrayList id , ArrayList name , ArrayList email , ArrayList phone_number , ArrayList ville  ){

        this.context=context;
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone_number = phone_number;
        this.ville = ville;

    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row , parent, false);
        return  new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        holder.id.setText(String.valueOf(id.get(position)));
        holder.name.setText(String.valueOf(name.get(position)));
        holder.email.setText(String.valueOf(email.get(position)));
        holder.phone_number.setText(String.valueOf(phone_number.get(position)));
        holder.ville.setText(String.valueOf(ville.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(id.get(position)));
                intent.putExtra("name", String.valueOf(name.get(position)));
                intent.putExtra("email", String.valueOf(email.get(position)));
                intent.putExtra("phone_number", String.valueOf(phone_number.get(position)));
                intent.putExtra("ville", String.valueOf(ville.get(position)));
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView id , name , email , phone_number , ville;
        LinearLayout mainLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id= itemView.findViewById(R.id.id);
            name= itemView.findViewById(R.id.name);
            email= itemView.findViewById(R.id.email);
            phone_number= itemView.findViewById(R.id.phone_number);
            ville= itemView.findViewById(R.id.ville);
            mainLayout = itemView.findViewById(R.id.mainlayout);
        }
    }

}
