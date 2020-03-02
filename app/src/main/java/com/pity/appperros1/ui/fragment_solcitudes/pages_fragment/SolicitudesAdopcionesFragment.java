package com.pity.appperros1.ui.fragment_solcitudes.pages_fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pity.appperros1.R;
import com.pity.appperros1.data.modelos.Solicitud;
import com.pity.appperros1.data.modelos.SolicitudesCache;
import com.pity.appperros1.ui.fragment_solcitudes.SolicitudesPresenter;
import com.pity.appperros1.ui.fragment_solcitudes.adapters.SolicitudesListAdapter;
import com.pity.appperros1.utils.DogUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SolicitudesAdopcionesFragment extends Fragment implements View.OnClickListener {

    private Context context;
    private SolicitudesPresenter parentPresenter;
    private ArrayList<Solicitud> solicitudesAdopciones;

    @BindView(R.id.solicitudes_adopciones_list_view)
    ListView listView;
    @BindView(R.id.solicitudes_adopciones_text_empty)
    TextView textViewEmpty;

    private SolicitudesAdopcionesFragment(Context context, SolicitudesPresenter presenter){
        this.context = context;
        this.parentPresenter = presenter;
    }

    public static Fragment newInstance(Context context, SolicitudesPresenter presenter){
        SolicitudesAdopcionesFragment fragment = new SolicitudesAdopcionesFragment(context, presenter);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_solicitudes_adopciones, null);
        ButterKnife.bind(this, root);

        solicitudesAdopciones = SolicitudesCache.SOLICITUDES.get(DogUtils.ETIQUETA_ADOPCION);

        if (!solicitudesAdopciones.isEmpty()){
            listView.setAdapter(new SolicitudesListAdapter(getContext(), solicitudesAdopciones, R.layout.item_card_solicitudes, this));
            listView.setVisibility(View.VISIBLE);
        }else{
            listView.setVisibility(View.GONE);
            textViewEmpty.setVisibility(View.VISIBLE);
        }

        Fragment parent = this.getParentFragment();

        if (parent != null){
            Log.e("SolicitudesAdopciones", "I have a parent \n" + parent.toString());
        }

        return root;
    }

    @Override
    public void onClick(View v) {

    }
}
