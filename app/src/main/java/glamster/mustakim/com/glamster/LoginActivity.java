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

    private TextView tv_newUser;
    private LinearLayout linear;
    private ProgressDialog dialog;
    private EditText username,userpassword;
    private Button btn_login;
    public static ApiInterface apiInterface;
    public static PrefConfig prefConfig;
    private String uname,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        prefConfig = new PrefConfig(this);
        apiInterface = ApiClient.getApiCLient().create(ApiInterface.class);

        username = findViewById(R.id.ed_login_username);
        userpassword = findViewById(R.id.ed_login_password);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Please wait");
        dialog.setMessage("Logging you in...");
        dialog.setCancelable(true);

        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // dialog.show();
                performValidation();

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

    private void performValidation() {

        final String getUname = username.getText().toString().trim();
        final String getPass = userpassword.getText().toString().trim();

        if (getUname.equalsIgnoreCase("") && getUname.length() <= 5)
        {
            username.setError("Input Username");
            return;
        }
        else if (getPass.equalsIgnoreCase("") && getPass.length() <= 5)
        {
            userpassword.setError("Input password");
            return;
        }
        else{
            performLogin();
        }
    }


    private void performLogin() {

        uname = username.getText().toString().trim();
        pass = userpassword.getText().toString().trim();

        //Log.d("Login Activity", "userpassword "+

        dialog.show();
        Call<UserModelCLass> call = apiInterface.performLogin(uname,pass);

        call.enqueue(new Callback<UserModelCLass>() {
            @Override
            public void onResponse(Call<UserModelCLass> call, Response<UserModelCLass> response) {
                dialog.dismiss();
                if (response.body().getResponse() != null && response.body().getResponse().equals("ok"))
                {
                    prefConfig.displayToast("Login Successfull...!!!");

                   // dialog.dismiss();
                    Intent i = new Intent(getApplicationContext(),UserProfileActivity.class);
                    i.putExtra("username",uname);
                    startActivity(i);
                }
                else if (response.body().getResponse() != null && response.body().getResponse().equals("error"))
                {
                    //dialog.dismiss();
                    prefConfig.displayToast("Can't Login. Please try again later.");
                }
            }

            @Override
            public void onFailure(Call<UserModelCLass> call, Throwable t) {

                dialog.dismiss();
                prefConfig.displayToast("Server Error..!!!");

            }
        });
    }
}
