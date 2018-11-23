package net.iqbalfauzan.agribid;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import net.iqbalfauzan.agribid.data.AgriBidClientBuilder;
import net.iqbalfauzan.agribid.data.Api;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    @BindView(R.id.butLogin)
    SignInButton butLogin;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    private GoogleApiClient googleApiClient;
    private FirebaseAuth mFirebaseAuth;
    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth.AuthStateListener authlistener;
    Api mApi = AgriBidClientBuilder.getAPIService();
    LoginStatus loginStatus = new LoginStatus();
    String userMail, userUid, userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        if (getSupportActionBar()!= null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Login");
        }
        mFirebaseAuth = FirebaseAuth.getInstance();
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(LoginActivity.this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();


        authlistener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    userMail = user.getEmail();
                    userName = user.getDisplayName();
                    userUid = user.getUid();
                    postSignUp();
                } else {
                    // User is signed out
                    Log.d("LoginActivity", "onAuthStateChanged:signed_out");
                }
            }
        };
        butLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        
    }
    @Override
    protected void onStart() {
        super.onStart();
        mFirebaseAuth.addAuthStateListener(authlistener);

    }
    @Override
    public void onStop() {
        super.onStop();
        if (authlistener != null) {
            mFirebaseAuth.removeAuthStateListener(authlistener);
        }
    }

    private void signIn(){
        Intent signIn = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signIn, RC_SIGN_IN);

        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()){
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            }else {
                progressBar.setVisibility(View.GONE);
            }
        }else {
            progressBar.setVisibility(View.GONE);
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mFirebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (!task.isSuccessful()){
                            Log.w("TAG","signInWithCredential",task.getException());
                            Toast.makeText(LoginActivity.this,"Authenticated Failed.\n"+task.getException(),Toast.LENGTH_LONG).show();
                        }else {
                            Log.d("TAG", "signInWithCredential:onComplete:" + task.isSuccessful());
                            googleApiClient.clearDefaultAccountAndReconnect();

                        }
                    }

                });

    }
    private void postSignUp(){
        final ProgressDialog load = ProgressDialog.show(LoginActivity.this, null,"Mohon Tunggu...",true, false);
        Log.i("TAG", "postSignUp: "+userMail+" "+userName);
        mApi.signUp("application/json",userMail, userName, "+62").enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                load.dismiss();
                if (response.isSuccessful()){
                    try {
                        Toast.makeText(LoginActivity.this, response.body().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    postSignIn();
                }else {
                    Toast.makeText(LoginActivity.this, "ERROR SIGNUP", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                load.dismiss();
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void postSignIn(){
        final ProgressDialog load = ProgressDialog.show(LoginActivity.this, null,"Logging in...",true, false);
        Log.i("TAG", "postSignIn: "+userMail+" "+userUid);
        mApi.signIn("application/json",userMail, userUid).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                load.dismiss();
                if (response.isSuccessful()){
                    try {
                        Toast.makeText(LoginActivity.this, response.body().string(), Toast.LENGTH_SHORT).show();
                        getUserSignIn(response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else {
                    Toast.makeText(LoginActivity.this, "ERRORSIGNIN", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                load.dismiss();
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getUserSignIn(String json){
        try {
            JSONObject object = new JSONObject(json);
            String token = object.getString("token");
            JSONObject object1 = object.getJSONObject("user");
            String user_uid = object1.getString("_id");
            Log.i("TAG", "getUserSignIn: "+user_uid);
            Log.i("TAG", "getUserSignIn: "+token);
            storeHeaderAuth(token, user_uid);
            finish();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(LoginActivity.this, "Silahkan ulangi beberapa saat lagi", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
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
