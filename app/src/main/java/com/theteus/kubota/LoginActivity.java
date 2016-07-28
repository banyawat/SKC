package com.theteus.kubota;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.theteus.kubota.OrganizationDataService.AsyncResponse;
import com.theteus.kubota.OrganizationDataService.AuthenticateService;
import com.theteus.kubota.OrganizationDataService.Service;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    EditText username_edittext;
    EditText password_edittext;
    TextView warning_text;
    Button login_button;
    RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViewsById();
        layout = (RelativeLayout) findViewById(R.id.login_layout);
        username_edittext.setText(Reference.USERNAME);
        password_edittext.setText(Reference.PASSWORD);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = username_edittext.getText().toString();
                String password = password_edittext.getText().toString();
                boolean valid = true;
                if(username.length() == 0){
                    username_edittext.setError("Please enter username");
                    valid = false;
                }
                else
                    username_edittext.setError(null);

                if(password.length() == 0){
                    password_edittext.setError("Please enter password");
                    valid = false;
                }
                else
                    password_edittext.setError(null);

                if(!valid)
                    return;

                Service.setDefaultUsername(username);
                Service.setDefaultPassword(password);
                new AuthenticateService(LoginActivity.this, new AsyncResponse() {
                    @Override
                    public void onFinishTask(JSONObject result) {
                        if(result != null) {
                            startActivity(new Intent(LoginActivity.this, Home.class));
                            finish();
                        }
                        else {
                            warning_text.setVisibility(TextView.VISIBLE);
                        }
                    }
                }).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
            }
        });
    }

    @Override
    public void onBackPressed(){
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_OUTSIDE){
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        }
        return false;
    }

    private void findViewsById(){
        username_edittext = (EditText) findViewById(R.id.login_username);
        password_edittext = (EditText) findViewById(R.id.login_password);
        login_button = (Button) findViewById(R.id.login_button);
        warning_text = (TextView) findViewById(R.id.login_warning);
    }
}
