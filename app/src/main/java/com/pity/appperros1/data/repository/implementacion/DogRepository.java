package com.pity.appperros1.data.repository.implementacion;

import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pity.appperros1.data.modelos.Perro;
import com.pity.appperros1.data.modelos.Usuario;
import com.pity.appperros1.data.repository.interfaces.IDogRepository;
import com.pity.appperros1.utils.DogUtils;
import com.pity.appperros1.utils.UserUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutionException;

public class DogRepository implements IDogRepository {


    private static DogRepository mRepository;
    private FirebaseDatabase database;
    private StorageReference storageReference;

    private final static String TAG = "DogRepository";
    private DogRepository() {
        database = FirebaseDatabase.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    public static DogRepository getInstance() {
        if (mRepository == null) {
            mRepository = new DogRepository();
        }
        return DogRepository.mRepository;
    }

    @Override
    public void uploadPhoto(Uri path, CallbackUploadPhoto callback) {

        StorageReference mRefImage = storageReference.child("images/").child("foto_perro/" + path.getLastPathSegment());


        mRefImage.putFile(path).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return mRefImage.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {

                if (task.isSuccessful()) {
                    String downloadUrlFoto = task.getResult().toString();
                    String fechaActual = new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss", Locale.getDefault()).format(new Date());
                    callback.onSuccessUploadPhoto(downloadUrlFoto, fechaActual);
                } else {
                    callback.onFailureUploadPhoto(task.getException().getMessage());
                }
            }

        });
    }

    @Override
    public void uploadPerro(Perro perro, Usuario currentUser, CallbackUploadDog callback) {

        perro.setUid(currentUser.getUid());

        DatabaseReference mRef = database.getReference().child("Perros").push();
        String didPerro = mRef.getKey();
        perro.setDid(didPerro);

        Map<String, Object> nuevoPerro = new HashMap<>();
        nuevoPerro.put("did", perro.getDid());

        nuevoPerro.put("nombre", perro.getNombre());
        nuevoPerro.put("descripcion", perro.getDescripcion());

        nuevoPerro.put("urlFoto", perro.getUrlFoto());
        nuevoPerro.put("pathFoto", perro.getPathFoto());

        nuevoPerro.put("genero", perro.getGenero());
        nuevoPerro.put("edad", perro.getEdad());
        nuevoPerro.put("tamanio", perro.getTamanio());
        nuevoPerro.put("esterilizado", perro.getEsterilizado());
        nuevoPerro.put("vacunado", perro.getVacunado());

        nuevoPerro.put(DogUtils.IS_AVAILABLE_KEY, true);
        nuevoPerro.put("timestamp", perro.getTimestamp());
        nuevoPerro.put("uid", perro.getUid());

        nuevoPerro.put("etiquetas", perro.getEtiquetas());

        mRef.updateChildren(nuevoPerro).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    DatabaseReference mRef = database.getReference().child("Usuarios").child(currentUser.getUid());

                    ArrayList<String> publicados = new ArrayList<>();
                    if (currentUser.getPerrosPublicados() == null || currentUser.getPerrosPublicados().size() == 0){
                        publicados.add((String) nuevoPerro.get("did"));
                        UserRepository.getInstance().getCurrentUser().setPerrosPublicados(publicados);
                    }else{
                        publicados = currentUser.getPerrosPublicados();
                        publicados.add((String) nuevoPerro.get("did"));
                        UserRepository.getInstance().getCurrentUser().setPerrosPublicados(publicados);
                    }

                    HashMap<String, Object> uploadedDogs = new HashMap<>();

                    uploadedDogs.put(UserUtils.DOGS_UPLOADS_KEY, publicados);

                    mRef.updateChildren(uploadedDogs).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                callback.onSuccessUploadDog();
                            }else{
                                callback.onFailureUploadDog(task.getException().getMessage());
                            }
                        }
                    });
                } else {
                    callback.onFailureUploadDog(task.getException().getMessage());
                }
            }
        });

    }



    @Override
    public void getDogListP(CallbackDogList callbackDogList) {
        DatabaseReference mRef = database.getReference().child("Perros");
        ArrayList<Perro> mDogList = new ArrayList<>();
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Perro currentDog = snapshot.getValue(Perro.class);
                    //currentDog.setNombre(currentDog.getNombre());
                    //currentDog.setDescripcion(currentDog.getDescripcion());
                    //currentDog.setUrlFoto(currentDog.getUrlFoto());
                    //currentDog.setUid(currentDog.getUid());
                    mDogList.add(currentDog);
                }
                //ArrayList<Perro> tempList = new ArrayList<>(mDogList);
                Collections.reverse(mDogList);
                callbackDogList.onSuccesGetDogList(mDogList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callbackDogList.onFailureGetDogList(databaseError.toString());
            }
        });

    }

    @Override
    public void getDogList(CallbackDogList callbackDogList) {
        DatabaseReference mRef = database.getReference().child("Perros");
        ArrayList<Perro> perdidos = new ArrayList<>();
        //Query qe = mRef.child("etiquetas").orderByChild("2").equalTo(true);
        //Query qe = mRef.orderByChild("etiquetas/1").equalTo(true);
        Query qe = mRef.getRef().orderByChild(DogUtils.IS_AVAILABLE_KEY).equalTo(true);
        qe.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Perro currentDog;
                perdidos.clear();

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    currentDog = snapshot.getValue(Perro.class);
                    perdidos.add(currentDog);
                }
                Collections.reverse(perdidos);
                callbackDogList.onSuccesGetDogList(perdidos);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callbackDogList.onFailureGetDogList(databaseError.getMessage());
            }
        });
    }

    @Override
    public void queryDogBy(String Id, CallbackQueryDog callbackQueryDog) {
        DatabaseReference mReference = database.getReference().child("Perros");
        final Perro[] currentDog = {null};

        // Query query = mReference.orderByChild("id").equalTo(Id).limitToFirst(1);
        // Query qe = mReference.child("etiquetas").orderByChild("1").equalTo(true);

        ValueEventListener queryListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    currentDog[0] = dataSnapshot.getChildren().iterator().next().getValue(Perro.class);
                }
                callbackQueryDog.onSucessQueryDog(currentDog[0]);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                callbackQueryDog.onFailureQueryDog(databaseError.getMessage() + "dogId: " + Id);
            }
        };

        Query q = mReference.orderByKey().equalTo(Id).limitToFirst(1);
        q.addListenerForSingleValueEvent(queryListener);
    }

    @Override
    public void updateDog(Perro perro, CallbackUploadDog callbackUpdateDog) {
        DatabaseReference mRef = database.getReference().child(DogUtils.DOG_DB_REFERENCE);
        mRef.child(perro.getDid())
                .updateChildren(DogUtils.dogToMap(perro)).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            callbackUpdateDog.onSuccessUploadDog();
                        }else callbackUpdateDog.onFailureUploadDog(task.getException().getMessage());
                    }
                });
    }

    public Perro queryDogBy(String id){
        final TaskCompletionSource<List<Perro>> tcs = new TaskCompletionSource<>();
        DatabaseReference mReference = database.getReference().child("Perros");
        final List<Perro> currentDog = new ArrayList<>();
        // Query query = mReference.orderByChild("id").equalTo(Id).limitToFirst(1);
        // Query qe = mReference.child("etiquetas").orderByChild("1").equalTo(true);
        Query q = mReference.orderByKey().equalTo(id).limitToFirst(1);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentDog.add(dataSnapshot.getChildren().iterator().next().getValue(Perro.class));
                tcs.setResult(currentDog);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, databaseError.getMessage());
            }
        });

        Task<List<Perro>> taskResult = tcs.getTask();

        try {
            Tasks.await(taskResult);
        }catch (ExecutionException | InterruptedException e){
            taskResult = Tasks.forException(e);
        }

        if (taskResult.isSuccessful()) {
            return taskResult.getResult().get(0);
        }else return null;


    }



}
