package net.iqbalfauzan.agribid.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import net.iqbalfauzan.agribid.R;
import net.iqbalfauzan.agribid.data.AgriBidClientBuilder;
import net.iqbalfauzan.agribid.data.Api;
import net.iqbalfauzan.agribid.data.DataHeader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FragmentOne extends Fragment implements View.OnClickListener {
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.butAddTanaman)
    FloatingActionButton butAddTanaman;
    @BindView(R.id.listTanaman)
    RecyclerView listTanaman;
    Api mApi = AgriBidClientBuilder.getAPIService();
    DataHeader dataHeader = new DataHeader();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_one, container, false);
        ButterKnife.bind(this, view);
        listTanaman.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        listTanaman.setHasFixedSize(true);
        butAddTanaman.setOnClickListener(this);
        getLahan();
        return view;
    }
    private void getLahan(){
        mApi.getLahan("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiI1YmY0MmFlN2FkOTA1NTAwMTY4NDhiOWYiLCJpYXQiOjE1NDI3Mjg0ODI0MDN9.AkT8mQzy0zj_NBA8XV6S7OYXknSJ73-dA7bNkOL7R7k").enqueue(new Callback<ResponseBody>() {
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
        try {
            JSONArray jsonArray = new JSONArray(json);
            if (jsonArray.length() > 0){
                for (int i=0; i<jsonArray.length();i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    String alamat = object.getString("alamat");
                    Log.i("TAG", "showLahan: "+alamat);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.butAddTanaman:
                break;
        }
    }
}
