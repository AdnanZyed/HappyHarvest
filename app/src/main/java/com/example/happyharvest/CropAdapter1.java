package com.example.happyharvest;

import android.content.Context;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.List;

public class CropAdapter1 extends ArrayAdapter<Crops> {

    public CropAdapter1(Context context, List<Crops> crops) {
        super(context, R.layout.item_crop, crops);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    private View createItemView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_crop, parent, false);
        }

        Crops crop = getItem(position);

        ImageView icon = convertView.findViewById(R.id.icon);
        TextView name = convertView.findViewById(R.id.name);

        icon.setImageResource(crop.getIconRes());
        name.setText(crop.getName());

        // تغيير لون الأيقونة حسب النوع
        int iconColor = getColorForType(crop.getType());
        icon.setColorFilter(iconColor, PorterDuff.Mode.SRC_IN);

        return convertView;
    }

    private int getColorForType(int type) {
        switch (type) {
            case 0: return ContextCompat.getColor(getContext(), R.color.green);
            case 1: return ContextCompat.getColor(getContext(), R.color.blue);
            case 2: return ContextCompat.getColor(getContext(), R.color.green);
            default: return ContextCompat.getColor(getContext(), R.color.black);
        }
    }
}