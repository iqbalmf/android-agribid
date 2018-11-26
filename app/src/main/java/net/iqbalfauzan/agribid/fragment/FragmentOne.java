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
    List<String> listInvests = new ArrayList<>();
    List<String> listLelangs = new ArrayList<>();
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

        listInvests.add("satu");
        listInvests.add("dua");
        listInvests.add("tiga");
        listInvests.add("empat");
        listInvests.add("lima");

        listLelangs.add("satu");
        listLelangs.add("dua");
        listLelangs.add("tiga");
        listLelangs.add("empat");
        listLelangs.add("lima");

        AdapterInvest adapterInvest = new AdapterInvest(getActivity(), listInvests);
        listInvest.setAdapter(adapterInvest);
        adapterInvest.notifyDataSetChanged();

        AdapterLelang adapterLelang = new AdapterLelang(getActivity(), listLelangs);
        listLelang.setAdapter(adapterLelang);
        adapterLelang.notifyDataSetChanged();
        return view;
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
