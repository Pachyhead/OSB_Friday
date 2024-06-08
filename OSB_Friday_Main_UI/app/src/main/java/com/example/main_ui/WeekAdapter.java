package com.example.main_ui;

import android.content.Context;

import android.view.LayoutInflater;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import androidx.annotation.NonNull;

public class WeekAdapter extends RecyclerView.Adapter<WeekAdapter.ViewHolder> {

    private ArrayList<String> list;
    private Context context;
    private final LayoutInflater inflater;

    /**
     * 생성자
     *
     * @param context
     * @param list
     */
    public WeekAdapter(Context context, ArrayList<String> list) {
        this.context = context;
        this.list = list;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    public WeekAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_calendar_day_of_week, parent, false);
        return new WeekAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeekAdapter.ViewHolder holder, int position) {
        holder.tv_date_of_week.setText("" + list.get(position));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_date_of_week;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_date_of_week = itemView.findViewById(R.id.tv_day_of_week);
        }
    }
}