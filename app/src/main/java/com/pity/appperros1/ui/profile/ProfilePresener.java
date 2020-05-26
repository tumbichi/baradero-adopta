package com.pity.appperros1.ui.profile;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.pity.appperros1.base.BasePresenter;
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
import com.pity.appperros1.utils.DogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class ProfilePresener extends BasePresenter<IProfileView> implements IProfilePresenter {

    ProfilePresener(Context context) {
        super(context);
    }

    @Override
    public void initView() {
        new AsyncTask<Void, Void, Usuario>(){
            CountDownLatch asyncSignal;

            @Override
            protected void onPreExecute() {
                asyncSignal = new CountDownLatch(1);
            }

            @Override
            protected Usuario doInBackground(Void... voids) {
                final Usuario[] logedUser = new Usuario[1];

                UserRepository.getInstance().getLoggedUser(new IUserRepository.CallbackQueryUser() {
                    @Override
                    public void onSuccessUserQueryById(Usuario user) {
                        UserRepository.getInstance().setLoggedUser(user);
                        logedUser[0] = user;
                        asyncSignal.countDown();
                    }

                    @Override
                    public void onFailureUserQueryById(String msgError) {

                    }
                });

                try {
                    asyncSignal.await(30000, TimeUnit.MILLISECONDS);
                    asyncSignal = null;
                } catch (InterruptedException e) {
                    Log.e(ProfilePresener.class.getName(), e.getStackTrace().toString() + " \n Time out exeption?");
                }


                return logedUser[0];
            }

            @Override
            protected void onPostExecute(Usuario logedUser) {
                String cantAdoptados = Integer.toString(logedUser.getPerrosAdoptados().size());
                String cantPublicados = Integer.toString(logedUser.getPerrosPublicados().size());
                String cantEncontrados = Integer.toString(logedUser.getPerrosEncontrados().size());

                mView.populateView(logedUser.getDisplayName(), cantAdoptados, cantPublicados, cantEncontrados, logedUser.getUrlFotoPerfil());
            }
        }.execute();





        new AsyncTask<Void, Void, HashMap<String, ArrayList<Solicitud>>>() {
            private CountDownLatch asyncSignal;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mView.showProgressBar();
            }

            @Override
            protected HashMap<String, ArrayList<Solicitud>> doInBackground(Void... voids) {
                final ArrayList<SolicitudReference> solicitudes = new ArrayList<>();
                ArrayList<Solicitud> adopciones = new ArrayList<>();
                ArrayList<Solicitud> perdidos = new ArrayList<>();

                asyncSignal = new CountDownLatch(1);

                AdopcionRepository.getInstance().getAdoptions(new IAdopcionRepository.CallbackGetAdoptions() {
                    @Override
                    public void onSuccessGetAdoptions(ArrayList<SolicitudReference> adoptions) {
                        solicitudes.addAll(adoptions);
                        asyncSignal.countDown();
                    }

                    @Override
                    public void onFailureGetAdoptions(String msgError) {

                    }
                });


                try {
                    asyncSignal.await(30000, TimeUnit.MILLISECONDS);
                    asyncSignal = null;
                } catch (InterruptedException e) {
                    Log.e(ProfilePresener.class.getName(), e.getStackTrace().toString() + " \n Time out exeption?");
                }

                if (!solicitudes.isEmpty()) {

                    for (int i = 0; i < solicitudes.size(); i++) {
                        Solicitud solicitud = new Solicitud();
                        solicitud.setIdSolicitud(solicitudes.get(i).getAdoptionID());
                        asyncSignal = new CountDownLatch(2);

                        DogRepository.getInstance().queryDogBy(solicitudes.get(i).getDogID(), new IDogRepository.CallbackQueryDog() {
                            @Override
                            public void onSucessQueryDog(Perro currentDog) {
                                solicitud.setIdDog(currentDog.getDid());
                                solicitud.setDogName(currentDog.getNombre());
                                solicitud.setDogUrlImage(currentDog.getUrlFoto());

                                if (currentDog.getEtiquetas().get(DogUtils.ETIQUETA_ADOPCION_ID)) {
                                    solicitud.setType(DogUtils.ETIQUETA_ADOPCION_ID);
                                } else
                                    if (currentDog.getEtiquetas().get(DogUtils.ETIQUETA_PERDIDO_ID)) {
                                    solicitud.setType(DogUtils.ETIQUETA_PERDIDO_ID);
                                }
                                asyncSignal.countDown();
                            }

                            @Override
                            public void onFailureQueryDog(String msgError) {
                                Log.e(ProfilePresener.class.getName(), msgError);
                            }
                        });

                        UserRepository.getInstance().getUserById(solicitudes.get(i).getAdopterID(), new IUserRepository.CallbackQueryUser() {
                            @Override
                            public void onSuccessUserQueryById(Usuario user) {
                                solicitud.setIdUser(user.getUid());
                                solicitud.setAdopterDispayName(user.getDisplayName());
                                solicitud.setAdopterEmail(user.getEmail());
                                solicitud.setAdopterPhone(user.getTelefono());
                                asyncSignal.countDown();
                            }

                            @Override
                            public void onFailureUserQueryById(String msgError) {
                                Log.e(ProfilePresener.class.getName(), msgError);
                            }
                        });

                        try {
                            asyncSignal.await(30000, TimeUnit.MILLISECONDS);
                            asyncSignal = null;
                        } catch (InterruptedException e) {
                            Log.e(ProfilePresener.class.getName(), e.getStackTrace().toString() + " \n Time out exeption?");
                        }

                        if (solicitud.getType() == DogUtils.ETIQUETA_ADOPCION_ID){
                            adopciones.add(solicitud);
                        }else{
                            if (solicitud.getType() == DogUtils.ETIQUETA_PERDIDO_ID){
                                 perdidos.add(solicitud);
                            }
                        }
                    }
                }

                HashMap<String, ArrayList<Solicitud>> result = new HashMap<>();

                result.put(DogUtils.ETIQUETA_ADOPCION, adopciones);
                result.put(DogUtils.ETIQUETA_PERDIDO, perdidos);

                return result;
            }


            @Override
            protected void onPostExecute(HashMap<String, ArrayList<Solicitud>> result) {
                SolicitudesCache.setSolicitudes(result);
                if (isViewAttached()){
                    mView.stackFragmentSolicitudes();
                    mView.hideProgressBar();
                }
            }
        }.execute();
    }


}
