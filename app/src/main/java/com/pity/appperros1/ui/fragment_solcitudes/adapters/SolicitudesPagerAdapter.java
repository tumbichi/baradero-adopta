package com.pity.appperros1.ui.fragment_solcitudes.adapters;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.pity.appperros1.ui.fragment_solcitudes.SolicitudesPresenter;
import com.pity.appperros1.ui.fragment_solcitudes.pages_fragment.SolicitudesAdopcionesFragment;
import com.pity.appperros1.ui.fragment_solcitudes.pages_fragment.SolicitudesPerdidosFragment;

public class SolicitudesPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    private SolicitudesPresenter solicitudesPresenter;
    public static int totalPage = 2;

    public SolicitudesPagerAdapter(Context context, @NonNull FragmentManager fm, int behavior, SolicitudesPresenter presenter) {
        super(fm, behavior);
        this.context = context;
        this.solicitudesPresenter = presenter;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new Fragment();
        switch (position) {
            case 0:
                fragment = SolicitudesAdopcionesFragment.newInstance(context, solicitudesPresenter);
                break;
            case 1:
                fragment = SolicitudesPerdidosFragment.newInstance(context, solicitudesPresenter);
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return totalPage;
    }


}
