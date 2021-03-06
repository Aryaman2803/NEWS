package com.example.news;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news.Model.Articles;
import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * AFTER CREATING ApiClient->ApiInterface.
 * NOW WE ARE CREATING AN ADAPTER TO SHOW Item Layout ( i.e. CardView) in RecyclerView
 **/

/**
 * SIMPLY IMPLEMENT ALL PRE-DEFINED METHODS
 **/
public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    /**
     * (2) Create a Context for inflating the Articles into CardView
     **/
    Context context;
    /**
     * (3) Calling our Article and storing it in a list, since there will be multiple articles
     **/
    List<Articles> articles;

    public Adapter(Context context, List<Articles> articles) {
        this.context = context;
        this.articles = articles;
    }

    /**
     * (4) Now simply create its constructor by using "generate"
     **/


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        /**(5) Now we will inflate in layout**/
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        /**(6) Now start binding the Articles on its position and set its position*/
        Articles a = articles.get(position);
        holder.tvTitle.setText(a.getTitle());
        holder.tvDate.setText("\u2022" + dateTime(a.getPublishedAt()));
        holder.tvSource.setText(a.getSource().getName());

        String imageUrl = a.getUrlToImage();
        // Picasso.get().load(imageUrl).into(holder.imageView);
        Picasso.Builder builder = new Picasso.Builder(context);

        builder.listener(new Picasso.Listener() {
            @Override
            public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                holder.cardView.setVisibility(View.GONE);
            }
        });
        Picasso.get().load(imageUrl).resize(1080, 720).onlyScaleDown().into(holder.imageView);
//
//        if (TextUtils.isEmpty(imageUrl)) {
//
//            holder.imageView.setImageResource(R.drawable.ic_baseline_image_24);
//        } else {
//            Picasso.get().load(imageUrl).resize(1080, 720).onlyScaleDown().into(holder.imageView);
//        }


//
//        if(TextUtils.isEmpty(imageUrl)){
//            holder.imageView.setImageResource(R.drawable.ic_baseline_image_24);
//        }else {
//            Glide.with(context).load(imageUrl).diskCacheStrategy(DiskCacheStrategy.NONE).into(holder.imageView);
//        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Detailed.class);
                intent.putExtra("url", a.getUrl());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        /**
         * (1) Started finding Views and creating its objects
         **/

        TextView tvTitle, tvSource, tvDate;
        ImageView imageView;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle = itemView.findViewById(R.id.tvIdCardView);
            tvSource = itemView.findViewById(R.id.tvSource);
            tvDate = itemView.findViewById(R.id.tvDate);
            imageView = itemView.findViewById(R.id.imageCardView);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }

    /**__(1)__
     * Updated Dependencies then commented below**/
//
//    public String dateTime(String t) {
//        PrettyTime prettyTime = new PrettyTime(new Locale(getCountry()));
//        String time = null;
//        try {
//            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
//            Date date = simpleDateFormat.parse(t);
//            time = prettyTime.format(date);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return time;
//    }

    /**
     * __(2)__
     * Using SimpleDateFormat
     **/

    public String dateTime(String t) {
        String time = null;
        PrettyTime p = new PrettyTime();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.forLanguageTag("es"));
        try {
            Date date = simpleDateFormat.parse(t);
            time = p.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return time;
    }

    public String getCountry() {
        Locale locale = Locale.getDefault();
        String country = locale.getCountry();
        return country.toLowerCase();
    }

}
