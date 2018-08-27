package glamster.mustakim.com.glamster;

import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.vansuita.gaussianblur.GaussianBlur;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserProfileActivity extends AppCompatActivity {

    private TextView name,email,age,eligibility,gender,city,height,education,
            skin,eyes,maritial_status,languages,training,passport,figure,biography,displayName;
    private ImageView imgCover;
    private de.hdodenhof.circleimageview.CircleImageView imgProfile;
    private String username;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        initView();

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        //blur background image
        GaussianBlur.with(this).size(300).radius(10).put(R.drawable.ic_default_user, imgCover);

        //check for intent value passed from login activity
        if (getIntent().getExtras() != null )
        {
            username = getIntent().getExtras().getString("username");
            Log.e("UserProfileActivity", "Intent Value: "+ username );
            fetchUserInformation(username);
        }


    }

    public void fetchUserInformation(String uname)
    {
        apiInterface = ApiClient.getApiCLient().create(ApiInterface.class);

        Call<UserModelCLass> call = apiInterface.getUserInformation(uname);

        call.enqueue(new Callback<UserModelCLass>() {
            @Override
            public void onResponse(Call<UserModelCLass> call, Response<UserModelCLass> response) {

                displayName.setText(response.body().getName());
                name.setText("Name : "+response.body().getName());
                email.setText("Email : "+response.body().getEmail());
                age.setText("Age : " + response.body().getAge());
                eligibility.setText("Eligibility : " + response.body().getEligibility());
                gender.setText("Gender : "+ response.body().getGender());
                city.setText("City : " + response.body().getCity());
                height.setText("Height : "+response.body().getHeight());
                education.setText("Education : "+response.body().getEducation());
                skin.setText("Skin Color : "+response.body().getSkinColor());
                eyes.setText("Eye Color : "+response.body().getEyeColor());
                maritial_status.setText("Maritial Status :" + response.body().getMaritialStatus());
                languages.setText("Languages : " + response.body().getLanguages());
                training.setText("Training : " + response.body().getTraining());
                passport.setText("Passport : " + response.body().getPassport());
                figure.setText("C/W/H : "+response.body().getFigure());
                biography.setText("Biography : " + response.body().getBiography());

            }

            @Override
            public void onFailure(Call<UserModelCLass> call, Throwable t) {

                Toast.makeText(UserProfileActivity.this, "Error", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void initView() {

        //TextViews init
        displayName = findViewById(R.id.tv_displayName);
        name = findViewById(R.id.tv_name);
        email = findViewById(R.id.tv_email);
        age = findViewById(R.id.tv_age);
        eligibility = findViewById(R.id.tv_eligibility);
        gender = findViewById(R.id.tv_gender);
        city = findViewById(R.id.tv_city);
        height = findViewById(R.id.tv_height);
        education = findViewById(R.id.tv_education);
        skin = findViewById(R.id.tv_skin);
        eyes = findViewById(R.id.tv_eye);
        maritial_status = findViewById(R.id.tv_maritialStatus);
        languages = findViewById(R.id.tv_languages);
        training = findViewById(R.id.tv_training);
        passport = findViewById(R.id.tv_passport);
        figure = findViewById(R.id.tv_figure);
        biography = findViewById(R.id.tv_biography);

        //ImageViews init
        imgCover = findViewById(R.id.imgView_cover);
        imgProfile = findViewById(R.id.imgView_profile);

    }
}
