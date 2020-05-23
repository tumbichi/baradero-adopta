package com.pity.appperros1.ui.inicio.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.pity.appperros1.R;
import com.pity.appperros1.data.interactor.interfaces.IInicioInteractor;
import com.pity.appperros1.data.modelos.Perro;
import com.pity.appperros1.ui.inicio.IInicioPresentador;
import com.pity.appperros1.ui.inicio.IInicioView;
import com.pity.appperros1.ui.inicio.InicioActivity;
import com.pity.appperros1.ui.inicio.adapters.InicioAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class DogsPostFragment extends Fragment implements IDogsPostFragment, View.OnClickListener {

    @BindView(R.id.inicio_list_view)
    ListView postListView;

    private InicioAdapter mAdapter;
    private IInicioView viewParent;
    private IInicioInteractor interactor;

    public DogsPostFragment(){

    }

    private DogsPostFragment(IInicioInteractor interactor){
        this.interactor = interactor;
    }

    public static DogsPostFragment newInstance(IInicioInteractor interactor){
        DogsPostFragment postFragment = new DogsPostFragment(interactor);
        Bundle bundle = new Bundle();
        postFragment.setArguments(bundle);
        return postFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_posts, container, false);
        ButterKnife.bind(this, view);
        //postListView = view.findViewById(R.id.inicio_list_view);
        viewParent = (InicioActivity) getActivity();
        updateDogList();
        return view;
    }

    private void updateDogList(){
        interactor.bringDogList(new IInicioInteractor.CallbackGetDogList() {
            @Override
            public void onSuccesGetDogList() {
                if (viewParent != null) {
                    viewParent.hideProgressDialog();
                    setListViewAdapter(interactor.getListPost());
                }
            }

            @Override
            public void onFailureDogList(String error) {
                viewParent.toast(error);
            }
        });
    }

    @Override
    public void setListViewAdapter(ArrayList<Perro> postList) {
        mAdapter = new InicioAdapter(getActivity(), postList, R.layout.item_post_list, this);
        postListView.setAdapter(mAdapter);
        postListView.setVisibility(View.VISIBLE);
    }

    @Override
    public void notifyDataChangeForListView() {
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public InicioAdapter getListViewAdapter() {
        if (mAdapter != null){
            return mAdapter;
        }else
            return null;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.post_button_ver_mas) {
            final int position = postListView.getPositionForView(v);
            onItemClickVerMas(position, getListViewAdapter());
        }
    }

    private void onItemClickVerMas(int position, InicioAdapter adapter) {
        Perro perroModel = adapter.getItem(position);
        if (viewParent != null) viewParent.navigateToInformacionOf(perroModel);
    }

    @OnItemClick(R.id.inicio_list_view)
    public void onItemClickListener(AdapterView<?> parent, int position) {
        Log.e("InicioView" , "onItemClickListener(" + parent.getAdapter().getItem(position) + ")");
    }


}
