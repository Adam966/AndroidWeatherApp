package com.project.weatherapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.project.weatherapp.R;

import java.util.List;

public class ChooseCityAdapter extends RecyclerView.Adapter<ChooseCityAdapter.ViewHolder> {
    private List<CarmenFeature> list;
    private onItemClick onItemClick;

    public ChooseCityAdapter(List<CarmenFeature> list, ChooseCityAdapter.onItemClick onItemClick) {
        this.list = list;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.choose_city_adapter, parent, false);
        return new ViewHolder(view, onItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.cityName.setText(list.get(position).placeName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        private TextView cityName;
        private onItemClick onItemClick;

        public ViewHolder(@NonNull View itemView, onItemClick itemClick) {
            super(itemView);

            this.onItemClick = itemClick;
            cityName = itemView.findViewById(R.id.cityName);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onItemClick.ItemClicked(list.get(getAdapterPosition()));
        }
    }

    public interface onItemClick {
        void ItemClicked(CarmenFeature feature);
    }
}
