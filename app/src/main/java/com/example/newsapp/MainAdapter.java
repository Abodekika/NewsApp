package com.example.newsapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.Model.Articles;
import com.example.newsapp.Model.HeadLine;
import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;
import org.ocpsoft.prettytime.format.SimpleTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    private Context context;
    private List<Articles> ArticlesNews;

    public MainAdapter(Context context, List<Articles> articlesNews) {
        this.context = context;
        ArticlesNews = articlesNews;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new ViewHolder(view) ;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Articles a= ArticlesNews.get(position);

        holder.tvTitle.setText(a.getTitle());
        holder.tvDate.setText(a.getPublishedAt());
        holder.tvSource.setText(a.getSource().getName());

        Picasso.get().load(a.getUrlImage()).into(holder.imageView);


        holder.newsCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,Detailed.class);

                intent.putExtra("title",a.getTitle());
                intent.putExtra("source",a.getSource().getName());
                intent.putExtra("time",dateTime(a.getPublishedAt()));
                intent.putExtra("desc",a.getDescription());
                intent.putExtra("imageUrl",a.getUrlImage());
                intent.putExtra("url",a.getUrl());

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return ArticlesNews.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView tvTitle,tvSource,tvDate;
        CardView newsCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle =itemView.findViewById(R.id.tv_title);
            tvSource =itemView.findViewById(R.id.tv_source);
            tvDate =itemView.findViewById(R.id.tv_data);
            imageView =itemView.findViewById(R.id.image);
            newsCard =itemView.findViewById(R.id.newsCard);
        }


    }

    public String dateTime(String t){
        PrettyTime prettyTime =new PrettyTime(new Locale(getCountry()));
        String time=null;
        try{
            SimpleDateFormat simpleDateFormat= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm",Locale.ENGLISH);
            Date data = simpleDateFormat.parse(t);
            time =prettyTime.format(data);

        } catch (Exception e){
            e.printStackTrace();
        }
        return time;
    }

    public String getCountry(){
        Locale locale =Locale.getDefault();
        String country =locale.getCountry();
        return country.toLowerCase();
    }
}
