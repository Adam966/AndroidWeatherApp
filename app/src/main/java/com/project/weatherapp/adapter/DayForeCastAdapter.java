package com.project.weatherapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.weatherapp.R;
import com.project.weatherapp.entitiy.ForeCast;
import com.project.weatherapp.other.SharedPref;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DayForeCastAdapter extends RecyclerView.Adapter<DayForeCastAdapter.ViewHolder> {
    private ArrayList<ForeCast> list;
    private Context context;
    private OnDayItemClick onDayItemClick;

    public DayForeCastAdapter(ArrayList<ForeCast> list, Context context, OnDayItemClick onDayItemClick) {
        this.list = list;
        this.context = context;
        this.onDayItemClick = onDayItemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_forecast_adapter, parent, false);
        return new ViewHolder(view, onDayItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.temp.setText(String.valueOf(list.get(position).getMainWeather().getTemp()) + (SharedPref.getSharedPrefInstance(context).loadPrefUnits() == 2 ? "°C" : "°F"));
        Picasso.get().load("http://openweathermap.org/img/wn/" + list.get(position).getWeatherDscription().getIcon() + ".png").into(holder.icon);
        try {
            holder.day.setText(new SimpleDateFormat("yyyy-MM-dd").parse(list.get(position).getDate()).toString().substring(0, 11));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView icon;
        TextView temp;
        TextView day;
        OnDayItemClick onDayItemClick;

        public ViewHolder(@NonNull View itemView, OnDayItemClick onDayItemClick) {
            super(itemView);

            icon = itemView.findViewById(R.id.iconDayForeCast);
            temp = itemView.findViewById(R.id.tempDayForeCast);
            day = itemView.findViewById(R.id.dayDayForeCast);

            this.onDayItemClick = onDayItemClick;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onDayItemClick.onDayItemClick(list.get(getAdapterPosition()));
        }
    }

    public interface OnDayItemClick {
        void onDayItemClick(ForeCast foreCast);
    }
}
