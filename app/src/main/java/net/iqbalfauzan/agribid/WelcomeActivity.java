package net.iqbalfauzan.agribid;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import net.iqbalfauzan.agribid.data.AgriBidClientBuilder;
import net.iqbalfauzan.agribid.data.Api;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.butLanjut)
    Button butLanjut;
    Api mApi = AgriBidClientBuilder.getAPIService();
    LoginStatus loginStatus = new LoginStatus();
    private String email, nama, noPhone="+62";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        butLanjut.setOnClickListener(this);
        email = loginStatus.getEmail();
        nama = loginStatus.getName();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.butLanjut:
                storeUser();
                break;
        }
    }
    private void storeUser(){
        String header = "application/x-www-form-urlencoded";
        Log.i("TAG", "storeUser: "+header);
        Log.i("TAG", "storeUser: "+email);
        Log.i("TAG", "storeUser: "+nama);
        Log.i("TAG", "storeUser: "+noPhone);
        mApi.storeUser(header,email, nama, noPhone).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()){
                    try {
                        String respon = response.body().string();
                        Log.d("TAG", "onResponse: "+respon);
                        showStoreUser(respon);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(WelcomeActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(WelcomeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void showStoreUser(String json){
        try {
            JSONObject jsonObject = new JSONObject(json);
            String token = jsonObject.getString("token");
            JSONObject object = jsonObject.getJSONObject("user");
            String id = object.getString("_id");
            String email = object.getString("email");
            String nama = object.getString("nama");
            String noTelp = object.getString("noTelp");
            Log.d("TAG", "showStoreUser: "+token);
            Log.d("TAG", "showStoreUser: "+id);
            Log.d("TAG", "showStoreUser: "+email);
            Log.d("TAG", "showStoreUser: "+nama);
            Log.d("TAG", "showStoreUser: "+noTelp);
            storeHeaderAuth(token, id);
            startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
            finish();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FirebaseAuth.getInstance().signOut();
        finish();
    }
    private void storeHeaderAuth(String headerAuth, String idUser) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.HEADER_AUTH, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Config.HEADER_AUTH, headerAuth);
        editor.apply();

        SharedPreferences prefUserUid = getApplicationContext().getSharedPreferences(Config.ID_USER, 0);
        SharedPreferences.Editor editorUid = prefUserUid.edit();
        editorUid.putString(Config.ID_USER, headerAuth);
        editorUid.apply();
    }
}
