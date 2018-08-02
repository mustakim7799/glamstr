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
        dialog.setCancelable(false);

        btn_login = findViewById(R.id.btn_login);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                validate();
                dialog.show();
                performLogin();
            }

            private void validate() {

//                if (username.getText().toString().trim().length() <= 0){
//
//                    username.setError("Enter Username");
//                    return;
//                }
//
//                else if (userpassword.getText().toString().trim().length() <= 0){
//
//                    userpassword.setError("Enter Password");
//                    return;
//                }
                boolean errorOccured = false;
                if (username.getText().toString().trim().equals(""))
                {
                    username.setError("Enter Username");
                    errorOccured = true;
                }
                if (userpassword.getText().toString().trim().equals(""))
                {
                    userpassword.setError("Enter Password");
                    errorOccured = true;
                }
                if(errorOccured){return;}

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

        final String uname = username.getText().toString().trim();
        final String pass = userpassword.getText().toString().trim();

        //Log.d("Login Activity", "userpassword "+

        Call<UserModelCLass> call = apiInterface.performLogin(uname,pass);

        call.enqueue(new Callback<UserModelCLass>() {
            @Override
            public void onResponse(Call<UserModelCLass> call, Response<UserModelCLass> response) {

                if (response.body().getResponse() != null && response.body().getResponse().equals("ok"))
                {
                    prefConfig.displayToast("Login Successfull...!!!");

                    dialog.dismiss();
                    Intent i = new Intent(getApplicationContext(),UserProfileActivity.class);
                    i.putExtra("username",uname);
                    startActivity(i);
                }
                else if (response.body().getResponse() != null && response.body().getResponse().equals("error"))
                {
                    dialog.dismiss();
                    prefConfig.displayToast("Wrong Username or Password..!!!");
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
