package net.iqbalfauzan.agribid.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseAuth;

import net.iqbalfauzan.agribid.Config;
import net.iqbalfauzan.agribid.LoginActivity;
import net.iqbalfauzan.agribid.LoginStatus;
import net.iqbalfauzan.agribid.R;
import net.iqbalfauzan.agribid.adapter.AdapterListMenu;
import net.iqbalfauzan.agribid.data.AgriBidClientBuilder;
import net.iqbalfauzan.agribid.data.Api;
import net.iqbalfauzan.agribid.data.DataHeader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentThree extends Fragment implements View.OnClickListener {

    @BindView(R.id.butLogin)
    SignInButton butLogin;
    @BindView(R.id.layout_login)
    RelativeLayout layoutLogin;
    @BindView(R.id.layoutLogged)
    RelativeLayout layoutLogged;
    @BindView(R.id.listMenu)
    ListView listMenu;
    @BindView(R.id.textNama) TextView textNama;
    @BindView(R.id.textEmail) TextView textEmail;
    @BindView(R.id.textEdit) TextView textEdit;
    @BindView(R.id.imageProfil)
    ImageView imageProfil;
    List<String> titles = new ArrayList<>();
    List<Integer> gambars = new ArrayList<>();
    LoginStatus loginStatus = new LoginStatus();
    Api mApi = AgriBidClientBuilder.getAPIService();
    DataHeader dataHeader = new DataHeader();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_three, container, false);
        ButterKnife.bind(this, view);
        butLogin.setOnClickListener(this);
        textEdit.setOnClickListener(this);
        titles.add("Bagikan Aplikasi");
        titles.add("Beri Rating");
        titles.add("Bantuan & Saran");
        titles.add("Tentang Aplikasi");
        titles.add("Keluar");
        gambars.add(R.drawable.ic_share_black_24dp);
        gambars.add(R.drawable.ic_star_black_24dp);
        gambars.add(R.drawable.ic_help_black_24dp);
        gambars.add(R.drawable.ic_info_outline_black_24dp);
        gambars.add(R.drawable.ic_open_in_new_black_24dp);
        AdapterListMenu adapter = new AdapterListMenu(getActivity(), titles, gambars);
        listMenu.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        listMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    case 4:
                        SharedPreferences pref1 = getActivity().getSharedPreferences(Config.HEADER_AUTH, 0);
                        SharedPreferences pref2 = getActivity().getSharedPreferences(Config.ID_USER,0);
                        pref1.edit().clear().apply();
                        pref2.edit().clear().apply();
                        FirebaseAuth.getInstance().signOut();
                        onResume();
                        break;
                }
            }
        });
        getUsers();
        return view;
    }
    private void getUsers(){
        mApi.getUsers(dataHeader.getAuthHeader(getActivity())).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        Log.i("TAG", "onResponse: "+response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(getActivity(), "ERROR", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getStatus();
        Toast.makeText(getActivity(), "Silahkan Refresh", Toast.LENGTH_SHORT).show();
    }
    private void getStatus(){
        String email = loginStatus.getEmail();
        String namaUser = loginStatus.getName();
        if (email == null){
            layoutLogin.setVisibility(View.VISIBLE);
        }else {
            layoutLogged.setVisibility(View.VISIBLE);
            textEmail.setText(email);
            textNama.setText(namaUser);

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.butLogin:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.textEdit:
                break;
        }
    }
}
