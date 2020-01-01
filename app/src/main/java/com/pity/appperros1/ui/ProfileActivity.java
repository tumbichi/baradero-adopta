package com.pity.appperros1.ui;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pity.appperros1.R;
import com.pity.appperros1.data.repository.implementacion.UserRepository;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.profile_cerrar_sesion})
    public void onClickLogout(View view){
        UserRepository.getInstance().logoutUser();
    }

}
