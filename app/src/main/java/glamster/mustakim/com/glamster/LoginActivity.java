package glamster.mustakim.com.glamster;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vansuita.gaussianblur.GaussianBlur;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextView tv_newUser,tv_test;
    private LinearLayout linear;
    private  ProgressBar bar;
    private EditText username,userpassword;
    private Button btn_login;
    public static ApiInterface apiInterface;
    public static PrefConfig prefConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prefConfig = new PrefConfig(this);
        apiInterface = ApiClient.getApiCLient().create(ApiInterface.class);

        username = findViewById(R.id.ed_login_username);
        userpassword = findViewById(R.id.ed_login_password);
        tv_test = findViewById(R.id.tv_test);
        linear = findViewById(R.id.linear1);

        Bitmap blur = GaussianBlur.with(getApplicationContext()).render(R.drawable.ic_bg);
        Drawable drawable = new BitmapDrawable(getResources(),blur);

        linear.setBackground(drawable);


        btn_login = findViewById(R.id.btn_login);
        bar = findViewById(R.id.progressBar);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bar.setVisibility(View.VISIBLE);
                performLogin();
            }
        });




        tv_newUser = findViewById(R.id.tv_newUser);

        tv_newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(),RegisterActivity.class);
                startActivity(i);


            }
        });
    }

    private void performLogin() {

        String uname = username.getText().toString().trim();
        String pass = userpassword.getText().toString().trim();

        Log.d("Login Activity", "userpassword "+pass);


        Call<UserModelCLass> call = apiInterface.performLogin(uname,pass);

        call.enqueue(new Callback<UserModelCLass>() {
            @Override
            public void onResponse(Call<UserModelCLass> call, Response<UserModelCLass> response) {

                if (response.body().getResponse().equals("ok"))
                {
                    prefConfig.displayToast("Login Successfull...!!!");

                    bar.setVisibility(View.GONE);
                    Intent i = new Intent(getApplicationContext(),WelcomeScreen.class);
                    startActivity(i);
                }
                else if (response.body().getResponse().equals("error"))
                {
                    bar.setVisibility(View.GONE);
                    prefConfig.displayToast("Wrong Username or Password..!!!");
                }
            }

            @Override
            public void onFailure(Call<UserModelCLass> call, Throwable t) {

                bar.setVisibility(View.GONE);
                prefConfig.displayToast("Server Error..!!!");

            }
        });
    }
}
