package com.pity.appperros1.data.repository.implementacion;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pity.appperros1.data.modelos.PerroModel;
import com.pity.appperros1.data.repository.interfaces.IDogRepository;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class DogRepository implements IDogRepository {


    private static DogRepository mRepository;
    private DatabaseReference mDatabase;
    private StorageReference mStorage;


    private DogRepository(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mStorage = FirebaseStorage.getInstance("gs://fir-appautentificacion.appspot.com").getReference();
    }

    public static DogRepository getInstance(){
        if (mRepository == null){
            mRepository = new DogRepository();
        }
        return mRepository;
    }

    @Override
    public void uploadPhoto(Uri path, CallbackUploadPhoto callback) {

        StorageReference mRefImage = mStorage.child("images/").child("foto_perro/"+path.getLastPathSegment());


        mRefImage.putFile(path).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()){
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
    public void uploadPerro(PerroModel perro, FirebaseUser currentUser, CallbackUploadDog callback) {

        perro.setIdUser(currentUser.getUid());

        DatabaseReference mRef = mDatabase.child("Perros").push();
        String uidPerro = mRef.getKey();
        perro.setId(uidPerro);

        Map<String, Object> nuevoPerro = new HashMap<>();
        nuevoPerro.put("id", perro.getId());
        nuevoPerro.put("nombre", perro.getNombre());
        nuevoPerro.put("descripcion" , perro.getDescripcion());
        nuevoPerro.put("urlFoto", perro.getUrlFoto());
        nuevoPerro.put("pathFoto", perro.getPathFoto());
        nuevoPerro.put("genero", perro.getGenero());
        nuevoPerro.put("edad", perro.getEdad());
        nuevoPerro.put("tamanio", perro.getTamanio());
        nuevoPerro.put("esterilizado", perro.getEsterilizado());
        nuevoPerro.put("vacunado", perro.getVacunado());
        nuevoPerro.put("fechaPublicacion", perro.getFechaPublicacion());
        nuevoPerro.put("idUser", perro.getIdUser());
        nuevoPerro.put("etiquetas", perro.getEtiquetas());

        mRef.updateChildren(nuevoPerro).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    callback.onSuccessUploadDog();
                }else{
                    callback.onFailureUploadDog(task.getException().getMessage());
                }
            }
        });

    }

    @Override
    public void getDogList(CallbackDogList callbackDogList) {
        DatabaseReference mRef = mDatabase.child("Perros");
        ArrayList<PerroModel>  mDogList = new ArrayList<>();
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    PerroModel currentDog = snapshot.getValue(PerroModel.class);

                    currentDog.setNombre(currentDog.getNombre());
                    currentDog.setDescripcion(currentDog.getDescripcion());
                    currentDog.setUrlFoto(currentDog.getUrlFoto());

                    mDogList.add(currentDog);
                }
                //ArrayList<PerroModel> tempList = new ArrayList<>(mDogList);
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
    public void queryDogBy(String Id, CallbackQueryDog callbackQueryDog){
        DatabaseReference mReference = mDatabase.child("Perros");
        final PerroModel[] currentDog = {null};


        Query query = mReference.orderByChild("id").equalTo(Id).limitToFirst(1);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                PerroModel currentDog = null;
                if (dataSnapshot != null){

                    currentDog = dataSnapshot.getChildren().iterator().next().getValue(PerroModel.class);


                }

                callbackQueryDog.onSucessQueryDog(currentDog);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callbackQueryDog.onFailureQueryDog(databaseError.getMessage());
            }
        });
    }


}
