package com.example.main_ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;

public class CalendarAdapter {
    public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder> {
        // {0 ~ 30/31} 까지의 날짜 리스트
        private ArrayList<Day> list;

        private Context context;
        private final LayoutInflater inflater;

        Calendar cal = Calendar.getInstance();
        ItemClickListener mClickListener;

        public CalendarAdapter(Context context, int year, int month, ArrayList<Day> list) {
            this.context = context;
            this.list = list;
            this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // 캘린더 세팅
            this.cal.set(Calendar.YEAR, year);
            this.cal.set(Calendar.MONTH, month);
        }

        @NonNull
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = inflater.inflate(R.layout.item_calendar, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Day day = list.get(position);
            if (day != null) holder.tv_item.setText(day.getDay());
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView tv_item;
            ImageView iv_item;
            boolean isInMonth = true;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                tv_item = itemView.findViewById(R.id.tv_date);
                iv_item = itemView.findViewById(R.id.iv_date);
                itemView.setOnClickListener(this);
            }

            public void onClick(View view) {
                if (mClickListener != null) mClickListener.onItemClick(view, (String) tv_item.getText(), isInMonth);
            }
        }

        // 클릭리스너 인터페이스
        public interface ItemClickListener {
            void onItemClick(View view, String day, boolean isInMonth);
        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.mClickListener = itemClickListener;
        }

    }
}
