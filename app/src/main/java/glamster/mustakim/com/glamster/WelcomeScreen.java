package glamster.mustakim.com.glamster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;

public class WelcomeScreen extends AppCompatActivity {

    private TextView tv_name;
    public static ApiInterface apiInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);

        apiInterface = ApiClient.getApiCLient().create(ApiInterface.class);

        tv_name = findViewById(R.id.tv_name);

        if (getIntent().getExtras() != null){

            final String username = getIntent().getExtras().getString("username");
            if (username != null)
            {
                tv_name.setText("Hello, "+username);
            }

        }



    }

    public void getDetails(){





    }
}
