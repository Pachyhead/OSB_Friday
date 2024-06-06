package com.example.main_ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MenuAdapter extends ArrayAdapter<MenuItem> {

    public MenuAdapter(Context context, List<MenuItem> menuItems) {
        super(context, 0, menuItems);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        com.example.main_ui.MenuItem menuItem = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.menu_list_item_layout, parent, false);
        }
        TextView itemTextView = convertView.findViewById(R.id.menu_item);
        TextView caloriesTextView = convertView.findViewById(R.id.menu_calories);

        itemTextView.setText(menuItem.getItem());
        caloriesTextView.setText(menuItem.getCalories() + " kcal");

        return convertView;
    }
}