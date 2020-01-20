package com.pity.appperros1.ui.fragment_solcitudes.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pity.appperros1.R;
import com.pity.appperros1.data.modelos.Solicitud;

import java.util.ArrayList;

public class SolicitudesListAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Solicitud> solicitudesList;
    private int layout;

    //private View.OnClickListener mOnClick;


    public SolicitudesListAdapter(Context context, ArrayList<Solicitud> list, int l /*, View.OnClickListener onClickListener*/) {
        this.context = context;
        this.solicitudesList = list;
        this.layout = l;
        //this.mOnClick = onClickListener;
    }

    @Override
    public int getCount() {
        return solicitudesList.size();
    }

    @Override
    public Solicitud getItem(int position) {
        return solicitudesList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SolicitudesListHolder holder;

        if (convertView == null){
            convertView = LayoutInflater.from(context).inflate(layout, null);

            holder = new SolicitudesListHolder();

            holder.dogImageView = convertView.findViewById(R.id.solicitudes_item_dog_photo);
            holder.titleTextView = convertView.findViewById(R.id.solicitudes_item_title);
            holder.phoneNumberTextView = convertView.findViewById(R.id.solicitudes_item_phone_number);
            holder.emailTextView = convertView.findViewById(R.id.solicitudes_item_email);
            holder.aceptarBtn = convertView.findViewById(R.id.solicitudes_item_button_aceptar);
            holder.cancelarBtn = convertView.findViewById(R.id.solicitudes_item_button_cancelar);

            convertView.setTag(holder);

        } else {
            holder = (SolicitudesListHolder) convertView.getTag();

        }
        Solicitud currentSolicitud = solicitudesList.get(position);
        String title = "El usuario " + currentSolicitud.getAdopterDispayName() + "¡Quiere adoptar a " + currentSolicitud.getDogName() + "!";
        holder.titleTextView.setText(title);

        Glide.with(context)
                .load(currentSolicitud.getDogUrlImage())
                .fitCenter()
                .centerCrop()
                .into(holder.dogImageView);

        holder.phoneNumberTextView.setText(currentSolicitud.getAdopterPhone());
        holder.emailTextView.setText(currentSolicitud.getAdopterEmail());

        return convertView;
    }

}


class SolicitudesListHolder implements View.OnClickListener {

    ImageView dogImageView;
    TextView titleTextView;
    TextView phoneNumberTextView;
    TextView emailTextView;
    Button aceptarBtn;
    ImageButton cancelarBtn;


    @Override
    public void onClick(View v) {
        String button = v.equals(aceptarBtn) ? "Aceptar" : "Cancelar";
        Log.e(SolicitudesListAdapter.class.getName(), "On click" + button);
    }
}
