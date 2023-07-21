package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    private Context context;
    Activity activity;
    private ArrayList cat_id, cat_description, cat_fact;
    private MyViewHolder holder;
    Animation translate_anim;

    CustomAdapter(Activity activity, Context context, ArrayList cat_id, ArrayList cat_description, ArrayList cat_fact){
        this.activity = activity;
        this.context = context;
        this.cat_id = cat_id;
        this.cat_description = cat_description;
        this.cat_fact = cat_fact;
    }

    @NonNull
    @Override
    public CustomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //ubacujemo linearnilayout sto smo pravili kao listitem da ubacimo u recyclerview
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        this.holder = holder;
        //Uzimamo vrednosti iz nasih Array listi
        holder.catIdTxt.setText(String.valueOf(cat_id.get(position)));
        holder.catDescriptionTxt.setText(String.valueOf(cat_description.get(position)));
        holder.catFactTxt.setText(String.valueOf(cat_fact.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("id", String.valueOf(cat_id.get(position)));
                intent.putExtra("description", String.valueOf(cat_description.get(position)));
                intent.putExtra("fact", String.valueOf(cat_fact.get(position)));
                //Navigacija iz main activity /1 to update activiti intent
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return cat_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView catIdTxt, catDescriptionTxt, catFactTxt;
        //mainLayout je nas root element linije u recyclerView
        LinearLayout mainLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            catIdTxt = itemView.findViewById(R.id.textViewCatIdTxt);
            catDescriptionTxt = itemView.findViewById(R.id.textViewCatDescriptionTxt);
            catFactTxt = itemView.findViewById(R.id.textViewCatFactTxt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
            //Animacija za nase elemente u recyclerView. Elementi dolaze odozdo na gore i dodaju se u prozor
            //tako sto popuju
            translate_anim = AnimationUtils.loadAnimation(context, R.anim.translate_anim);
            mainLayout.setAnimation(translate_anim);
        }
    }
}
