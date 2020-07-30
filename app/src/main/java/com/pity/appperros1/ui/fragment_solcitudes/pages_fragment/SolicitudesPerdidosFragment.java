package com.pity.appperros1.ui.fragment_solcitudes.pages_fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
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
import com.pity.appperros1.data.modelos.Perro;
import com.pity.appperros1.data.modelos.Solicitud;
import com.pity.appperros1.data.modelos.SolicitudReference;
import com.pity.appperros1.data.modelos.SolicitudesCache;
import com.pity.appperros1.data.modelos.Usuario;
import com.pity.appperros1.data.repository.implementacion.AdopcionRepository;
import com.pity.appperros1.data.repository.implementacion.DogRepository;
import com.pity.appperros1.data.repository.implementacion.UserRepository;
import com.pity.appperros1.data.repository.interfaces.IAdopcionRepository;
import com.pity.appperros1.data.repository.interfaces.IDogRepository;
import com.pity.appperros1.data.repository.interfaces.IUserRepository;
import com.pity.appperros1.ui.fragment_solcitudes.SolicitudesPresenter;
import com.pity.appperros1.ui.fragment_solcitudes.adapters.SolicitudesListAdapter;
import com.pity.appperros1.utils.DogUtils;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SolicitudesPerdidosFragment extends Fragment implements View.OnClickListener {

    private Context context;
    private SolicitudesPresenter parentPresenter;
    private ArrayList<Solicitud> solicitudesPerdidos;
    private SolicitudesListAdapter adapter;

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

        solicitudesPerdidos = SolicitudesCache.SOLICITUDES.get(DogUtils.ETIQUETA_PERDIDO);

        if (!solicitudesPerdidos.isEmpty()) {
            adapter = new SolicitudesListAdapter(context, solicitudesPerdidos, R.layout.item_card_solicitudes, this);
            listView.setAdapter(adapter);
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
        final int position = listView.getPositionForView(v);
        switch (v.getId()){
            case R.id.solicitudes_item_button_aceptar:
                Solicitud solicitudAceptada = adapter.getItem(position);
                new AsyncTask<String, Void, ArrayList<SolicitudReference>>(){
                    private AlertDialog progressDialog;
                    private CountDownLatch asyncSignal;
                    private int i, j;
                    @Override
                    protected void onPreExecute() {
                        progressDialog = new ProgressDialog(context);
                        progressDialog.setTitle("Adopcion en proceso");
                        progressDialog.setMessage("Espere unos segundos");
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                    }

                    @Override
                    protected ArrayList<SolicitudReference> doInBackground(String... strings) {
                        String idDog = strings[0];
                        String idFinder = strings[1];

                        asyncSignal = new CountDownLatch(3);

                        UserRepository.getInstance().getUserById(idFinder, new IUserRepository.CallbackQueryUser() {
                            @Override
                            public void onSuccessUserQueryById(Usuario user) {
                                user.getPerrosEncontrados().add(idDog);
                                UserRepository.getInstance().updateUser(user, new IUserRepository.CallbackUserUpdate() {
                                    @Override
                                    public void onSuccessUpdateUser() {
                                        asyncSignal.countDown();
                                    }

                                    @Override
                                    public void onFailedUpdateUser(Exception e) {

                                    }
                                });
                            }

                            @Override
                            public void onFailureUserQueryById(String msgError) {

                            }
                        });

                        DogRepository.getInstance().queryDogBy(idDog, new IDogRepository.CallbackQueryDog() {
                            @Override
                            public void onSucessQueryDog(Perro currentDog) {
                                currentDog.setAvailable(false);
                                DogRepository.getInstance().updateDog(currentDog, new IDogRepository.CallbackUploadDog() {
                                    @Override
                                    public void onSuccessUploadDog() {
                                        asyncSignal.countDown();
                                    }

                                    @Override
                                    public void onFailureUploadDog(String messasgeError) {

                                    }
                                });
                            }

                            @Override
                            public void onFailureQueryDog(String msgError) {

                            }
                        });

                        ArrayList<SolicitudReference> result = new ArrayList<>();

                        AdopcionRepository.getInstance().getAdoptionsOfDog(idDog, new IAdopcionRepository.CallbackGetAdoptions() {
                            @Override
                            public void onSuccessGetAdoptions(ArrayList<SolicitudReference> adoptions) {
                                if (result.isEmpty()) result.addAll(adoptions);
                                j = adoptions.size();
                                for (i = 0 ; i < adoptions.size(); i++){
                                    AdopcionRepository.getInstance().deleteAdoption(adoptions.get(i).getAdoptionID(), new IAdopcionRepository.CallbackAdoption() {
                                        @Override
                                        public void onSuccesAdoption() {
                                            j--;
                                            if (j == 0) asyncSignal.countDown();

                                        }

                                        @Override
                                        public void onFailedAdoption(Exception e) {

                                        }
                                    });


                                }

                            }

                            @Override
                            public void onFailureGetAdoptions(String msgError) {

                            }
                        });

                        try {
                            asyncSignal.await(30000, TimeUnit.MILLISECONDS);
                            asyncSignal = null;
                        }catch (Exception e){
                            e.getStackTrace();
                        }

                        return result;
                    }

                    @Override
                    protected void onPostExecute(ArrayList<SolicitudReference> deleteSolicitudes) {
                        ArrayList<Solicitud> forDelete = new ArrayList<>();
                        for (SolicitudReference solicitud : deleteSolicitudes){
                            for (Solicitud adopcion : solicitudesPerdidos){
                                if (solicitud.getAdoptionID().equals(adopcion.getIdSolicitud())){
                                    forDelete.add(adopcion);
                                }
                            }
                        }
                        solicitudesPerdidos.removeAll(forDelete);
                        adapter.notifyDataSetChanged();
                        progressDialog.dismiss();
                    }
                }.execute(solicitudAceptada.getIdDog(), solicitudAceptada.getIdUser());


                break;
            case R.id.solicitudes_item_button_cancelar:
                Solicitud solicitudCancelada = adapter.getItem(position);
                Log.i(this.getClass().getName(), adapter.getItem(position).getDogName());
                AdopcionRepository.getInstance()
                        .deleteAdoption(solicitudCancelada.getIdSolicitud(), new IAdopcionRepository.CallbackAdoption() {
                            @Override
                            public void onSuccesAdoption() {
                                Log.i(this.getClass().getName(), UserRepository.getInstance().getCurrentUser().getDisplayName());
                                solicitudesPerdidos.remove(adapter.getItem(position));
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
