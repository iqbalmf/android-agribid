package net.iqbalfauzan.agribid.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import net.iqbalfauzan.agribid.R;
import net.iqbalfauzan.agribid.adapter.AdapterInvest;
import net.iqbalfauzan.agribid.adapter.AdapterLahan;
import net.iqbalfauzan.agribid.adapter.AdapterLelang;
import net.iqbalfauzan.agribid.adapter.ViewPagerAdapter;
import net.iqbalfauzan.agribid.data.AgriBidClientBuilder;
import net.iqbalfauzan.agribid.data.Api;
import net.iqbalfauzan.agribid.data.DataHeader;
import net.iqbalfauzan.agribid.model.InvestModel;
import net.iqbalfauzan.agribid.model.LahanModel;
import net.iqbalfauzan.agribid.model.LelangModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentOne extends Fragment implements View.OnClickListener {
    @BindView(R.id.list_invest) RecyclerView listInvest;
    @BindView(R.id.list_lelang) RecyclerView listLelang;
    @BindView(R.id.list_lahan) RecyclerView listLahan;
    Api mApi = AgriBidClientBuilder.getAPIService();
    DataHeader dataHeader = new DataHeader();
    List<InvestModel> listInvests;
    List<LelangModel> listLelangs;
    List<LahanModel> listLahans;

    private String[] images = {
            "https://2.bp.blogspot.com/-V6ykgJkirp4/V2Nfc__rzcI/AAAAAAAAFTc/8N9Euz_4VtkoXXz62zdJP6QUmkYrhGjbACLcB/s1600/WALLPAPER%2BBUAH-BUAHAN%2BSEGAR%2BHD%2B-%2BKARTUNLUCU.COM.jpg",
            "https://ae01.alicdn.com/kf/HTB1nlbeLpXXXXbmXVXXq6xXFXXXE/Buah-wallpaper-sayuran-organik-3D-foto-mural-untuk-ruang-tamu-yang-modern-restoran-toko-latar-belakang.jpg",
            "https://statik.tempo.co/data/2017/04/30/id_603178/603178_620.jpg"
    };
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    ViewPagerAdapter viewPagerAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        ButterKnife.bind(this, view);
        listInvest.setHasFixedSize(false);
        listInvest.setNestedScrollingEnabled(false);
        listInvest.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        listLelang.setHasFixedSize(false);
        listLelang.setNestedScrollingEnabled(false);
        listLelang.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        listLahan.setHasFixedSize(false);
        listLahan.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        listLahan.setNestedScrollingEnabled(false);
        viewPagerAdapter = new ViewPagerAdapter(getActivity(), images);
        viewPager.setAdapter(viewPagerAdapter);


        getLelang();
        getInvest();
        getLahan();
        return view;
    }
    private void getLelang(){
        mApi.getLelang(dataHeader.getAuthHeader(getActivity())).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        showLelang(response.body().string());
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
    private void showLelang(String json){
        listLelangs = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            if (jsonArray.length() > 0){
                for (int i=0; i <jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    LelangModel model = new LelangModel();
                    String id = jsonObject.getString("_id");
                    int hargaPasang = jsonObject.getInt("hargaPasang");
                    String idLahan = jsonObject.getString("lahan");
                    String idUser = jsonObject.getString("user");
                    model.setId(id);
                    model.setHargaPasang(hargaPasang);
                    model.setLahan(idLahan);
                    model.setUser(idUser);
                    model.setFoto("https://image.shutterstock.com/image-photo/vegetable-garden-260nw-353942729.jpg");
                    listLelangs.add(model);
                }
                AdapterLelang adapterLelang = new AdapterLelang(getActivity(), listLelangs, Glide.with(this));
                listLelang.setAdapter(adapterLelang);
                adapterLelang.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getInvest(){
        mApi.getInvest(dataHeader.getAuthHeader(getActivity())).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        showInvest(response.body().string());
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
    private void showInvest(String json){
        listInvests = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            if (jsonArray.length() > 0){
                for (int i =0; i< jsonArray.length(); i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    InvestModel model = new InvestModel();
                    String id = object.getString("_id");
                    int biayaInvest = object.getInt("biayaInvest");
                    int jumlahInvest = object.getInt("jumlahInvest");
                    boolean lunas = object.getBoolean("isLunas");
                    String idLahan = object.getString("lahan");
                    String idUser = object.getString("user");
                    model.set_id(id);
                    model.setBiayaInvest(biayaInvest);
                    model.setJumlahLahan(jumlahInvest);
                    model.setLunas(lunas);
                    model.setLahan(idLahan);
                    model.setUser(idUser);
                    model.setFoto("https://i1.wp.com/dolandolen.com/wp-content/uploads/2015/11/Kebun-Teh-Wonosari-Cover.jpg?fit=860%2C484&ssl=1");
                    listInvests.add(model);
                }
                AdapterInvest adapterInvest = new AdapterInvest(getActivity(), listInvests, Glide.with(this));
                listInvest.setAdapter(adapterInvest);
                adapterInvest.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void getLahan(){
        mApi.getLahan(dataHeader.getAuthHeader(getActivity())).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        showLahan(response.body().string());
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
    private void showLahan(String json){
        listLahans = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            if (jsonArray.length()>0){
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    LahanModel model = new LahanModel();
                    String id = jsonObject.getString("_id");
                    String luas = jsonObject.getString("luas");
                    String alamat = jsonObject.getString("alamat");
                    int hargaPerInvest = jsonObject.getInt("hargaPerInvest");
                    int hargaAwalLelang = jsonObject.getInt("hargaAwalLelang");
                    boolean isPanen = jsonObject.getBoolean("isPanen");
                    int maxInvest = jsonObject.getInt("maxInvest");
                    String tanaman = jsonObject.getString("tanaman");
                    String pemilik = jsonObject.getString("pemilik");
                    model.setId(id);
                    model.setLuas(luas);
                    model.setAlamat(alamat);
                    model.setHargaAwalLelang(hargaAwalLelang);
                    model.setHargaAwalLelang(hargaAwalLelang);
                    model.setPanen(isPanen);
                    model.setMaxInvest(maxInvest);
                    model.setTanaman(tanaman);
                    model.setFoto("https://apollo-singapore.akamaized.net/v1/files/7e2gueq7p1281-ID/image;s=644x461;olx-st/_1_.jpg");
                    model.setPemilik(pemilik);
                    listLahans.add(model);
                }
                AdapterLahan adapterLahan = new AdapterLahan(getActivity(), listLahans, Glide.with(this));
                listLahan.setAdapter(adapterLahan);
                adapterLahan.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.textMoreInvest:
                break;
            case R.id.textMoreLelang:
                break;
        }
    }
}
