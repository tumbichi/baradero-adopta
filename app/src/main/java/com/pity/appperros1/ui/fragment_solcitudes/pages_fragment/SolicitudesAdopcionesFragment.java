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
import com.pity.appperros1.data.repository.implementacion.AdopcionRepository;
import com.pity.appperros1.data.repository.implementacion.UserRepository;
import com.pity.appperros1.data.repository.interfaces.IAdopcionRepository;
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
    private SolicitudesListAdapter adapter;

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
            adapter = new SolicitudesListAdapter(getContext(), solicitudesAdopciones, R.layout.item_card_solicitudes, this);
            listView.setAdapter(adapter);
            listView.setVisibility(View.VISIBLE);
        }else{
            listView.setVisibility(View.GONE);
            textViewEmpty.setVisibility(View.VISIBLE);
        }

        return root;
    }

    @Override
    public void onClick(View v) {
        final int position = listView.getPositionForView(v);
        switch (v.getId()){
            case R.id.solicitudes_item_button_aceptar:
                Solicitud solicitudAceptada = adapter.getItem(position);
                break;
            case R.id.solicitudes_item_button_cancelar:
                Solicitud solicitudCancelada = adapter.getItem(position);
                Log.i(this.getClass().getName(), adapter.getItem(position).getDogName());
                AdopcionRepository.getInstance()
                        .deleteAdoption(solicitudCancelada.getIdSolicitud(), new IAdopcionRepository.CallbackAdoption() {
                            @Override
                            public void onSuccesAdoption() {
                                Log.i(this.getClass().getName(), UserRepository.getInstance().getLoggedUser().getDisplayName());
                                solicitudesAdopciones.remove(adapter.getItem(position));
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onFailedAdoption(Exception e) {

                            }
                        });
                break;
            default:
                Log.i(this.getTag(), Integer.toString(position));
        }
    }
}
