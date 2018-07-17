package glamster.mustakim.com.glamster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    TextView tv_alreadyRegistered;
    private EditText name,user_name,user_email,user_password;
    private Button bn_register;
    public static ApiInterface apiInterface;
    public static PrefConfig prefConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        prefConfig = new PrefConfig(this);
        apiInterface = ApiClient.getApiCLient().create(ApiInterface.class);

        name = findViewById(R.id.ed_name);
        user_name = findViewById(R.id.ed_username);
        user_email = findViewById(R.id.ed_email);
        user_password =  findViewById(R.id.ed_password);

        bn_register = findViewById(R.id.btn_register);

        bn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                performRegister();

            }
        });


        tv_alreadyRegistered = findViewById(R.id.tv_alreadyRegistered);

        tv_alreadyRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);

            }
        });
    }

    public void performRegister()
    {
        String Name = name.getText().toString().trim();
        String username = user_name.getText().toString().trim();
        String email = user_email.getText().toString().trim();
        String password = user_password.getText().toString().trim();

        Call<UserModelCLass> call = apiInterface.performRegistration(Name,username,email,password);

        call.enqueue(new Callback<UserModelCLass>() {
            @Override
            public void onResponse(Call<UserModelCLass> call, Response<UserModelCLass> response) {

                if (response.body().getResponse().equals("ok")){
                    prefConfig.displayToast("User Registered Successfully..!!!");
                }
                else if(response.body().getResponse().equals("exits")){
                    prefConfig.displayToast("User Already Registered. Please Login..!!!");
                }
                else if(response.body().getResponse().equals("error")){
                    prefConfig.displayToast("Registration Unsuccessfull..!!!");
                }
            }

            @Override
            public void onFailure(Call<UserModelCLass> call, Throwable t) {

                prefConfig.displayToast("Error.. No response from server...!!!!");


            }
        });

        name.setText("");
        user_name.setText("");
        user_email.setText("");
        user_password.setText("");

    }
}
