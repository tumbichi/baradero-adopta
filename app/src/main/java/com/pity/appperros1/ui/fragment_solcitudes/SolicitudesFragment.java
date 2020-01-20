package com.pity.appperros1.ui.fragment_solcitudes;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.pity.appperros1.R;
import com.pity.appperros1.base.BaseFragment;
import com.pity.appperros1.ui.fragment_solcitudes.adapters.SolicitudesPagerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SolicitudesFragment extends BaseFragment<SolicitudesPresenter> implements ISolicitudesFragment{

    private Context context;
    private SolicitudesPagerAdapter adapter;

    @BindView(R.id.solicitudes_pager)
    ViewPager viewPager;

    @BindView(R.id.solicitudes_button_adopciones)
    Button btnAdopciones;
    @BindView(R.id.solicitudes_button_perdidos)
    Button btnPerdidos;

    private SolicitudesFragment(Context context){
        this.context = context;
    }

    public static SolicitudesFragment newInstance(Context context){
        SolicitudesFragment fragment = new SolicitudesFragment(context);
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public SolicitudesPresenter initializePresenter(Context context) {
        return new SolicitudesPresenter(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_solicitudes, container, false);
        ButterKnife.bind(this, view);
        viewPager.setAdapter(new SolicitudesPagerAdapter(context, getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, mPresenter));
        viewPager.setCurrentItem(0);
        setTabChangeListener();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnAdopciones = getView().findViewById(R.id.solicitudes_footer).findViewById(R.id.solicitudes_button_adopciones);
        btnPerdidos =  getView().findViewById(R.id.solicitudes_footer).findViewById(R.id.solicitudes_button_perdidos);
        initButton();
    }

    private void setTabChangeListener(){
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener(){

            @Override
            public void onPageScrollStateChanged(int position) {}
            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {}
            @Override
            public void onPageSelected(int position) {
                circlePageHandler(position);
            }

        });

    }

    private void circlePageHandler(int action){
        switch(action){
            case 0:
                setButtonDesing(btnAdopciones,R.drawable.pressed_circle);
                setButtonDesing(btnPerdidos, R.drawable.rounded_circle);
                break;
            case 1:
                setButtonDesing(btnPerdidos,R.drawable.pressed_circle);
                setButtonDesing(btnAdopciones,R.drawable.rounded_circle);
            break;
        }
    }

    private void initButton() {
        setButtonDesing(btnAdopciones, R.drawable.pressed_circle);
        setButtonDesing(btnPerdidos, R.drawable.rounded_circle);
    }

    private void setButtonDesing(Button btn, int background){
        btn.setBackgroundResource(background);
    }
}
