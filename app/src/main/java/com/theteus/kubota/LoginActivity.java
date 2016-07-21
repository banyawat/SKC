package com.theteus.kubota;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.theteus.kubota.OrganizationDataService.AsyncResponse;
import com.theteus.kubota.OrganizationDataService.AuthenticateService;
import com.theteus.kubota.OrganizationDataService.Service;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    EditText username_edittext;
    EditText password_edittext;
    Button login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewsById();
        username_edittext.setText("administrator");
        password_edittext.setText("pass@word1");
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = username_edittext.getText().toString();
                String password = password_edittext.getText().toString();
                Service.setDefaultUsername(username);
                Service.setDefaultPassword(password);
                new AuthenticateService(LoginActivity.this, new AsyncResponse() {
                    @Override
                    public void onFinishTask(JSONObject result) {
                        if(result != null) {
                            startActivity(new Intent(LoginActivity.this, Home.class));
                            finish();
                        }
                        else
                            Log.i("TAG", "NOPE");
                    }
                }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });
    }

    private void findViewsById(){
        username_edittext = (EditText) findViewById(R.id.login_username);
        password_edittext = (EditText) findViewById(R.id.login_password);
        login_button = (Button) findViewById(R.id.login_button);
    }
}
