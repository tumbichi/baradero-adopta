package com.pity.baradopta.ui.inicio.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pity.baradopta.R;
import com.pity.baradopta.data.modelos.Perro;

import java.util.ArrayList;

public class InicioAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<Perro> dogsList;
    private int layout;

    private View.OnClickListener mOnClick;


    public InicioAdapter (Context context, ArrayList<Perro> list, int l, View.OnClickListener onClickListener) {
        this.mContext = context;
        this.dogsList = list;
        this.layout = l;
        this.mOnClick = onClickListener;
    }


    @Override
    public int getCount() {
        return dogsList.size();
    }

    @Override
    public Perro getItem(int position) {
        return dogsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return  position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        PostListHolder holder;

        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(layout, null);

            holder = new PostListHolder();

            holder.textViewNombre = convertView.findViewById(R.id.post_text_view_nombre_perro);
            holder.textViewDescripcion = convertView.findViewById(R.id.post_text_view_descripcion);
            holder.imageViewPerro = convertView.findViewById(R.id.post_image_view_foto_perro);
            holder.imageViewPerfil = convertView.findViewById(R.id.post_foto_perfil_usuario);
            holder.buttonVerMas = convertView.findViewById(R.id.post_button_ver_mas);
            holder.etiquetaAdopcion = convertView.findViewById(R.id.post_etiqueta_adopcion);
            holder.etiquetPerdido = convertView.findViewById(R.id.post_etiqueta_perdido);

            holder.buttonVerMas.setOnClickListener(mOnClick);

            convertView.setTag(holder);

        } else {
            holder = (PostListHolder) convertView.getTag();

        }

        Perro currentDog = dogsList.get(position);
        holder.buttonVerMas.setTag(position);
        if (currentDog != null){
            holder.textViewNombre.setText(currentDog.getNombre());
            holder.textViewDescripcion.setText(currentDog.getDescripcion());

            Glide.with(mContext)
                    .load(currentDog.getUrlFoto())
                    .fitCenter()
                    .centerCrop()
                    .into(holder.imageViewPerro);

            Glide.with(mContext)
                    .load(currentDog.getUrlFoto())
                    .fitCenter()
                    .centerCrop()
                    .into(holder.imageViewPerfil);


            if (currentDog.getEtiquetas().get(1)){
                holder.etiquetPerdido.setVisibility(View.GONE);
                holder.etiquetaAdopcion.setVisibility(View.VISIBLE);
            }else{
                if (currentDog.getEtiquetas().get(2)){
                    holder.etiquetaAdopcion.setVisibility(View.GONE);
                    holder.etiquetPerdido.setVisibility(View.VISIBLE);
                }else{
                    holder.etiquetaAdopcion.setVisibility(View.GONE);
                    holder.etiquetPerdido.setVisibility(View.GONE);
                }
            }



        }

        return convertView;
    }



    }


     class PostListHolder {
        TextView textViewNombre;
        TextView textViewDescripcion;

        ImageView imageViewPerro;
        ImageView imageViewPerfil;

        Button buttonVerMas;

        LinearLayout etiquetaAdopcion;
        LinearLayout etiquetPerdido;


    }

