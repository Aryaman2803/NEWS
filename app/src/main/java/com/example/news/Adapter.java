package com.example.news;

import android.content.Context;
import android.text.TextUtils;
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
        holder.tvDate.setText(dateTime(a.getPublishedAt()));
        holder.tvSource.setText(a.getSource().getName());

        String imageUrl = a.getUrlToImage();
        // Picasso.get().load(imageUrl).into(holder.imageView);

        if (TextUtils.isEmpty(imageUrl)) {
            holder.imageView.setImageResource(R.drawable.ic_baseline_image_24);
        } else {
            Picasso.get().load(imageUrl).into(holder.imageView);
        }

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

    public String dateTime(String s) {
        PrettyTime prettyTime = new PrettyTime(new Locale(getCountry()));
        String time = null;
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:", Locale.ENGLISH);
            Date date = simpleDateFormat.parse(s);
            time = prettyTime.format(date);
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
