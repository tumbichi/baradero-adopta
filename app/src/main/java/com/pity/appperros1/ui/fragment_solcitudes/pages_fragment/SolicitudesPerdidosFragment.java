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
import com.pity.appperros1.data.modelos.SolicitudesContainer;
import com.pity.appperros1.ui.fragment_solcitudes.SolicitudesPresenter;
import com.pity.appperros1.ui.fragment_solcitudes.adapters.SolicitudesListAdapter;
import com.pity.appperros1.utils.DogUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SolicitudesPerdidosFragment extends Fragment {

    private Context context;
    private SolicitudesPresenter parentPresenter;
    private ArrayList<Solicitud> solicitudesPerdidos;

    @BindView(R.id.solicitudes_perdidos_list_view)
    ListView listView;
    @BindView(R.id.solicitudes_perdidos_text_empty)
    TextView textViewEmpty;

    private SolicitudesPerdidosFragment(Context context, SolicitudesPresenter presenter){
        this.context = context;
        this.parentPresenter = presenter;
    }

    public static Fragment newInstance(Context context, SolicitudesPresenter presenter){
        SolicitudesPerdidosFragment fragment = new SolicitudesPerdidosFragment(context, presenter);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_solicitudes_perdidos, null);
        ButterKnife.bind(this, root);

        solicitudesPerdidos = SolicitudesContainer.SOLICITUDES.get(DogUtils.ETIQUETA_PERDIDO);

        if (!solicitudesPerdidos.isEmpty()) {
            listView.setAdapter(new SolicitudesListAdapter(context, solicitudesPerdidos, R.layout.item_card_solicitudes));
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
}
