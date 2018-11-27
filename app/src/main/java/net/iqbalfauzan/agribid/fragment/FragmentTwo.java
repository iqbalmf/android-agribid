package net.iqbalfauzan.agribid.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.iqbalfauzan.agribid.R;
import net.iqbalfauzan.agribid.adapter.AdapterTanaman;
import net.iqbalfauzan.agribid.data.AgriBidClientBuilder;
import net.iqbalfauzan.agribid.data.Api;
import net.iqbalfauzan.agribid.data.DataHeader;
import net.iqbalfauzan.agribid.model.TanamanModel;

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

public class FragmentTwo extends Fragment {
    @BindView(R.id.listTanaman)
    RecyclerView listTanamana;
    DataHeader dataHeader = new DataHeader();
    Api mApi = AgriBidClientBuilder.getAPIService();
    List<TanamanModel> tanamanModels;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        ButterKnife.bind(this, view);
        listTanamana.setHasFixedSize(true);
        listTanamana.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        getTanaman();
        return view;
    }
    private void getTanaman(){
        mApi.getTanaman(dataHeader.getAuthHeader(getActivity())).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        showTanaman(response.body().string());
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
    private void showTanaman(String json){
        tanamanModels = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(json);
            if (jsonArray.length()>0){
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    TanamanModel model = new TanamanModel();
                    String id = object.getString("_id");
                    String nama = object.getString("nama");
                    String namaLatin= object.getString("namaLatin");
                    int hargaPasar = object.getInt("hargaPasar");
                    model.setId(id);
                    model.setNama(nama);
                    model.setNamaLatin(namaLatin);
                    model.setHargaPasar(hargaPasar);
                    tanamanModels.add(model);
                }
                AdapterTanaman adapterTanaman = new AdapterTanaman(getActivity(), tanamanModels);
                listTanamana.setAdapter(adapterTanaman);
                adapterTanaman.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
