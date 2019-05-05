package com.isma.dell.tvssolntask;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.isma.dell.tvssolntask.Network.ApiInterface;
import com.isma.dell.tvssolntask.Network.RetrofitClient;
import com.isma.dell.tvssolntask.PojoClass.LoginInfoModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    ApiInterface apiInterface;
    TextInputEditText textInputEditTextEmail,textInputEditTextPassword;
    AppCompatButton appCompatButtonLogin;

    SharedPreferences sharedPreferences;
    String myPreferencess="mypref";
    Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;
        findViews();

        apiInterface= RetrofitClient.getClient().create(ApiInterface.class);
        sharedPreferences=getSharedPreferences(myPreferencess, Context.MODE_PRIVATE);



        appCompatButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userEmail=textInputEditTextEmail.getText().toString().trim();
                String userPassword=textInputEditTextPassword.getText().toString().trim();
                LoginInfoModel loginInfoModel=new LoginInfoModel(userEmail,userPassword);
                if(!userEmail.trim().isEmpty()){
                    if(!userPassword.trim().isEmpty()){
                if(isNetworkAvailable(context)) {
                    toVerifyInfo(loginInfoModel);
                }else {
                    Toast.makeText(context, "Please connect to internet.", Toast.LENGTH_SHORT).show();
                }
                }else {
                        Toast.makeText(context, "Password field cannot be empty", Toast.LENGTH_SHORT).show();
                }
                }else {
                    Toast.makeText(context, "Email field cannot be empty", Toast.LENGTH_SHORT).show();
                }




            }
        });

    }

    public boolean isNetworkAvailable(final Context context) {
        final ConnectivityManager connectivityManager = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null && connectivityManager.getActiveNetworkInfo().isConnected();
    }

    private void toVerifyInfo(LoginInfoModel loginInfoModel) {
        apiInterface.getTableData(loginInfoModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResult,this::handleError);
    }

    private void findViews() {
        textInputEditTextEmail=findViewById(R.id.textInputEditTextEmail);
        textInputEditTextPassword=findViewById(R.id.textInputEditTextPassword);
        appCompatButtonLogin=findViewById(R.id.appCompatButtonLogin);
    }

    private void handleError(Throwable throwable) {
        Toast.makeText(this, "Incorrect Email/Password", Toast.LENGTH_SHORT).show();
        Log.d("TAGerror", "handleError: "+throwable.getLocalizedMessage());
    }

    private void handleResult(String s) {
        if(s!=null &&!s.isEmpty()) {

                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putString("TableData",s);
                editor.apply();

                Intent intent=new Intent(MainActivity.this,HomeActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.enter, R.anim.exit);
                finish();




            Log.d("StrResp", s);
            Toast.makeText(this, "Logged In", Toast.LENGTH_SHORT).show();
        }
    }
}
