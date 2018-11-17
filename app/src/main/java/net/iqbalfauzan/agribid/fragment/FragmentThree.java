package net.iqbalfauzan.agribid.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseAuth;

import net.iqbalfauzan.agribid.LoginActivity;
import net.iqbalfauzan.agribid.LoginStatus;
import net.iqbalfauzan.agribid.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FragmentThree extends Fragment implements View.OnClickListener {

    @BindView(R.id.butLogin)
    SignInButton butLogin;
    @BindView(R.id.layout_login)
    RelativeLayout layoutLogin;
    @BindView(R.id.layoutLogged)
    RelativeLayout layoutLogged;
    @BindView(R.id.butLogout)
    Button butLogout;
    @BindView(R.id.textStatus)
    TextView textStatus;
    LoginStatus loginStatus = new LoginStatus();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_three, container, false);
        ButterKnife.bind(this, view);
        butLogin.setOnClickListener(this);
        butLogout.setOnClickListener(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getStatus();
        Toast.makeText(getActivity(), "Silahkan Refresh", Toast.LENGTH_SHORT).show();
    }
    private void getStatus(){
        String email = loginStatus.getEmail();
        if (email == null){
            layoutLogin.setVisibility(View.VISIBLE);
        }else {
            layoutLogged.setVisibility(View.VISIBLE);
            textStatus.setText("Anda Login Sebagai : "+ email);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.butLogin:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.butLogout:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getActivity(), "Silahkan Refresh", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
