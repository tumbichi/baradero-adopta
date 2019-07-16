package com.pity.appperros1.ui.fragment_agregar_perro.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.pity.appperros1.R;
import com.pity.appperros1.base.BaseItem;

import java.util.ArrayList;

public class AdapterItemSpinner extends ArrayAdapter<BaseItem>  {

    private int itemLayout;
    private static final int NO_ICON = -1;

    public AdapterItemSpinner(Context mContext, ArrayList<BaseItem> edades, int layout){
        super(mContext, 0, edades);
        this.itemLayout = layout;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }


    private View initView(int position, View convertView, ViewGroup parent){
        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(
                    itemLayout, parent, false

            );

        }
        ImageView imageViewIcono = convertView.findViewById(R.id.spinner_image_view_edad);
        TextView textViewValor = convertView.findViewById(R.id.spinner_text_view_edad);

        BaseItem currentItem = getItem(position);


        if (currentItem != null) {
            if (currentItem.getIconoAtPosition(position) == NO_ICON) { // si devolver icono por posicion(getIconAtPos..) devuelve NO_ICON
                imageViewIcono.setVisibility(View.GONE);// Dejo de mostrar el icono
            }else{ // sino muestro el icono y le cargo el icono que necesite
                imageViewIcono.setVisibility(View.VISIBLE);
                imageViewIcono.setImageResource(currentItem.getIcono());
            }
            textViewValor.setText(currentItem.getValor());
        }

        return convertView;

    }


}
