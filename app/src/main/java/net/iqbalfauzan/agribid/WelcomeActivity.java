package net.iqbalfauzan.agribid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.butLanjut)
    Button butLanjut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        butLanjut.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.butLanjut:
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                break;
        }
    }
}
