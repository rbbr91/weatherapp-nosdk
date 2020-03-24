package com.br.weatherapp;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.br.weatherapp.model.City;

import java.util.ArrayList;
import android.content.Intent;

import butterknife.BindView;
import butterknife.ButterKnife;

import com.br.weatherapp.view.ItemClickListener;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder> {

    private Activity mActivity;
    private ArrayList<City> mCities;
    private Context ctx;

    public CityAdapter(Context ctx, Activity activity, ArrayList<City> mCities) {
        this.mCities = mCities;
        this.ctx = ctx;
        mActivity = activity;
    }

    @Override
    public CityViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_city, viewGroup, false);
        return new CityViewHolder(v);
    }

    @Override
    public void onBindViewHolder(CityViewHolder holder, int position) {
        final City city = mCities.get(position);
        holder.tvName.setText(city.getName());

        holder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {

                //INTENT OBJ
                Intent i=new Intent(ctx, CityDetailActivity.class);

                //ADD DATA TO OUR INTENT
                i.putExtra("Name", city.getName());
                i.putExtra("Description", city.getWeatherDescription());
                i.putExtra("MinTemperature", city.getFormattedMinTemperature());
                i.putExtra("MaxTemperature", city.getFormattedMaxTemperature());

                //START DETAIL ACTIVITY
                ctx.startActivity(i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));

            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return mCities.size();
    }

    class CityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @BindView(R.id.cityName)
        TextView tvName;
        private ItemClickListener itemClickListener;

        CityViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            this.itemClickListener.onItemClick(v,getLayoutPosition());
        }

        public void setItemClickListener(ItemClickListener itemClickListener) {
            this.itemClickListener = itemClickListener;
        }

    }

}
