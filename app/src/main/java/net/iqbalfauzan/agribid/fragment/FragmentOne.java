package net.iqbalfauzan.agribid.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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

import net.iqbalfauzan.agribid.R;
import net.iqbalfauzan.agribid.adapter.AdapterInvest;
import net.iqbalfauzan.agribid.adapter.AdapterLelang;
import net.iqbalfauzan.agribid.data.AgriBidClientBuilder;
import net.iqbalfauzan.agribid.data.Api;
import net.iqbalfauzan.agribid.data.DataHeader;
import net.iqbalfauzan.agribid.model.InvestModel;
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
    Api mApi = AgriBidClientBuilder.getAPIService();
    DataHeader dataHeader = new DataHeader();
    List<InvestModel> listInvests;
    List<LelangModel> listLelangs;
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



        getLelang();
        getInvest();
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
                    listLelangs.add(model);
                }
                AdapterLelang adapterLelang = new AdapterLelang(getActivity(), listLelangs);
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
                    listInvests.add(model);
                }
                AdapterInvest adapterInvest = new AdapterInvest(getActivity(), listInvests);
                listInvest.setAdapter(adapterInvest);
                adapterInvest.notifyDataSetChanged();
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
