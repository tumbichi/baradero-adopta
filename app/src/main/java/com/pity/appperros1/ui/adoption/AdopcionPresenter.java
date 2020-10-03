package com.pity.appperros1.ui.adoption;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.pity.appperros1.R;
import com.pity.appperros1.ui.base.BasePresenter;
import com.pity.appperros1.data.interactor.implementation.AdopcionInteractor;
import com.pity.appperros1.data.modelos.Usuario;
import com.pity.appperros1.data.repository.interfaces.IAdopcionRepository;
import com.pity.appperros1.data.repository.interfaces.IUserRepository;
import com.pity.appperros1.utils.UserUtils;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class AdopcionPresenter extends BasePresenter<IAdopcionView> implements IAdopcionPresenter {

    private AdopcionInteractor mInteractor;

    private static final String TAG = "AdopcionPresenter";

    public AdopcionPresenter(Context context) {
        super(context);
        mInteractor = new AdopcionInteractor();
    }

    @Override
    public void requestDataFromDatabase(String dogID, String uploaderID, String adopterID) {
        mInteractor.attachRequestedData(dogID, uploaderID, adopterID, new CallbackResquest() {
            @Override
            public void onSuccessRequest() {
                if (mInteractor.getCurrentAdopcion().getUPLOADER() != null && mInteractor.getCurrentAdopcion().getDOG() != null && mInteractor.getCurrentAdopcion().getADOPTER() != null) {
                    Log.i(TAG, "Attach 'Adoption' success");
                    updateView(mInteractor.getCurrentAdopcion().getADOPTER());
                    view.hideProgressBar();
                }
            }

            @Override
            public void onFailureRequest(String msgError) {
                view.finishAdoption(false);
            }
        });
    }

    @SuppressLint("StaticFieldLeak")
    @Override
    public void onClickConfirmar(View view, String email, String phone) {
        if (!UserUtils.isEmailValid(email)) {
            Log.e(TAG, "Invalid email" + email);
            this.view.toast(view.getResources().getString(R.string.invalid_email));
            return;
        }
        if (!UserUtils.isPhoneNumberValid(phone)) {
            Log.e(TAG, "Invalid phone " + phone);
            this.view.toast(view.getResources().getString(R.string.invalid_number));
            return;
        }

        new AsyncTask<String, Void, Boolean>() {
            private CountDownLatch asyncSignal;

            @Override
            protected void onPreExecute() {
                AdopcionPresenter.this.view.showProgressBar();
                asyncSignal = new CountDownLatch(2);
            }

            @Override
            protected Boolean doInBackground(String... strings) {
                Boolean result = Boolean.FALSE;
                String mail = strings[0];
                String telefono = strings[1];
                String currentPhoneAdopter = mInteractor.getCurrentAdopcion().getADOPTER().getTelefono();

                if ((currentPhoneAdopter != null && !TextUtils.equals(telefono, currentPhoneAdopter))
                        || (currentPhoneAdopter == null || currentPhoneAdopter.isEmpty())) {

                    mInteractor.updatePhoneNumber(telefono, new IUserRepository.CallbackUserUpdate() {
                        @Override
                        public void onSuccessUpdateUser() {
                            Log.i(TAG, "onSuccesUpdateUser()");
                        }

                        @Override
                        public void onFailedUpdateUser(Exception e) {
                            Log.e(TAG, e.getMessage());
                        }
                    });

                }

                mInteractor.adoptionEvent(new IAdopcionRepository.CallbackAdoption() {
                    @Override
                    public void onSuccesAdoption() {
                        Log.i(TAG, "onSuccesAdopcion");
                        asyncSignal.countDown();
                        mInteractor.sendNotification(new IAdopcionRepository.CallbackAdoption() {
                            @Override
                            public void onSuccesAdoption() {
                                Log.i(TAG, "Notification sended");
                                asyncSignal.countDown();
                            }

                            @Override
                            public void onFailedAdoption(Exception e) {
                                Log.e(TAG, e.getMessage());
                            }
                        });
                    }

                    @Override
                    public void onFailedAdoption(Exception e) {
                        Log.e(TAG, e.getMessage());

                    }
                });

                try {
                    asyncSignal.await(30000, TimeUnit.MILLISECONDS);
                    if (asyncSignal.getCount() == 0) result = Boolean.TRUE;
                    asyncSignal = null;
                } catch (InterruptedException e) {
                    Log.e(TAG, e.getStackTrace().toString() + "\n Time out exeption?");

                }

                return result;
            }

            @Override
            protected void onPostExecute(Boolean result) {
                if (isViewAttached()) {
                    AdopcionPresenter.this.view.hideProgressBar();
                    AdopcionPresenter.this.view.finishAdoption(result);
                }
            }
        }.execute(email, phone);
    }

    private void updateView(Usuario adopter) {
        if (isViewAttached()){
            if (adopter.getTelefono() == null) {
                view.populateUI(adopter.getEmail(), "");
            } else if (adopter.getEmail() != null && adopter.getTelefono() != null) {
                view.populateUI(adopter.getEmail(), adopter.getTelefono().substring(3));
            }
        }
    }

}
