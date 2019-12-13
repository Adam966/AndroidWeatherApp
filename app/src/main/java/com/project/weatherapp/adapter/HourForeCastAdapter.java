package com.project.weatherapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.project.weatherapp.R;
import com.project.weatherapp.entitiy.ForeCast;
import com.project.weatherapp.other.SharedPref;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HourForeCastAdapter extends RecyclerView.Adapter<HourForeCastAdapter.ViewHolder> {

    private ArrayList<ForeCast> list;
    private Context context;

    public HourForeCastAdapter(ArrayList<ForeCast> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hour_forecast_adapter, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.temp.setText(String.valueOf(list.get(position).getMainWeather().getTemp()) + (SharedPref.getSharedPrefInstance(context).loadPrefUnits() == 2 ? "°C" : "°F"));
        Picasso.get().load("http://openweathermap.org/img/wn/" + list.get(position).getWeatherDscription().getIcon() + ".png").into(holder.icon);
        holder.time.setText(list.get(position).getDate().substring(10, list.get(position).getDate().length() - 3));
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView icon;
        TextView time;
        TextView temp;
        ConstraintLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.hourForeCastIcon);
            time = itemView.findViewById(R.id.hourForeCastTime);
            temp = itemView.findViewById(R.id.hourForeCastTemp);
            parentLayout = itemView.findViewById(R.id.hourForeCastLayout);
        }
    }
}
