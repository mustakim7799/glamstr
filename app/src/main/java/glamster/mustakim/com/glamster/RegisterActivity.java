package glamster.mustakim.com.glamster;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private static final int REQUEST_CAMERA = 2;

    private TextView tv_alreadyRegistered;
    //private ImageButton imgBtn_profile;
    private ProgressDialog dialog;
    private RadioGroup user_gender;
    private EditText name, user_name, user_email, user_password, user_eligibility,
            user_age, user_city, user_height, user_education, user_skin_color,
            user_eyes_color, user_maritial_status, user_languages, user_training, user_passport, user_figure, user_biography;
    private Button bn_register;
    public static ApiInterface apiInterface;
    public static PrefConfig prefConfig;
    private Bitmap bitmap;
    private de.hdodenhof.circleimageview.CircleImageView imgBtn_profile;
    private String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_registration_ui);

        prefConfig = new PrefConfig(this);
        apiInterface = ApiClient.getApiCLient().create(ApiInterface.class);

        name = findViewById(R.id.ed_name);
        user_name = findViewById(R.id.ed_username);
        user_email = findViewById(R.id.ed_email);
        user_password = findViewById(R.id.ed_password);
        user_eligibility = findViewById(R.id.ed_eligibility);
        user_age = findViewById(R.id.ed_age);
        user_gender = findViewById(R.id.radioGrp_gender);
        user_city = findViewById(R.id.ed_city);
        user_height = findViewById(R.id.ed_height);
        user_education = findViewById(R.id.ed_education);
        user_skin_color = findViewById(R.id.ed_skin);
        user_eyes_color = findViewById(R.id.ed_eyes);
        user_maritial_status = findViewById(R.id.ed_maritial_status);
        user_languages = findViewById(R.id.ed_languages);
        user_training = findViewById(R.id.ed_training);
        user_passport = findViewById(R.id.ed_passport);
        user_figure = findViewById(R.id.ed_figure);
        user_biography = findViewById(R.id.ed_biography);
        imgBtn_profile = findViewById(R.id.user_profile_photo);


        dialog = new ProgressDialog(this);
        dialog.setTitle("Please wait");
        dialog.setMessage("Registering");
        dialog.setCancelable(false);

        bn_register = findViewById(R.id.btn_register);

        imgBtn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final CharSequence[] items = {"Choose From Gallery", "Cancel"};

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(RegisterActivity.this);
                alertDialog.setTitle("Select Photo");
                alertDialog.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        if (items[i].equals("Choose From Gallery")) {
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);//
                            startActivityForResult(intent, REQUEST_CAMERA);
                        } else if (items[i].equals("Cancel")) {
                            dialogInterface.dismiss();
                        }

                    }
                });
                alertDialog.show();
            }
        });

        bn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                performValidation();

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK && data != null) {

            Uri path = data.getData();

            try {

                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), path);
                imgBtn_profile.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void performValidation()
    {

        if(name.getText().toString().length() <= 0)
        {
            name.setError("Enter Your Full name");
            return;
        }   else if (user_name.getText().toString().length() <= 0){
            user_name.setError("Enter Username");
            return;
        }
        else
        {
            performRegister();
        }
    }

    public void performRegister() {

        String fullname = name.getText().toString().trim();
        String username = user_name.getText().toString().trim();
        String email = user_email.getText().toString().trim();
        String password = user_password.getText().toString().trim();
        String eligibility = user_eligibility.getText().toString().trim();
        String age = user_age.getText().toString().trim();
        String city = user_city.getText().toString().trim();
        String height = user_height.getText().toString().trim();
        String education = user_education.getText().toString().trim();
        String skin_color = user_skin_color.getText().toString().trim();
        String eye_color = user_eyes_color.getText().toString().trim();
        String maritial_status = user_maritial_status.getText().toString().trim();
        String languages = user_languages.getText().toString().trim();
        String training = user_training.getText().toString().trim();
        String passport = user_passport.getText().toString().trim();
        String figure = user_figure.getText().toString().trim();
        String biography = user_biography.getText().toString().trim();

        //RadioButton values
        int radioBtnID = user_gender.getCheckedRadioButtonId();
        RadioButton radioBtn = user_gender.findViewById(radioBtnID);
        String selectedGender = (String) radioBtn.getText();

        //Image values
        //image = ImageToString();

        Log.d("Image", "performRegister: " + image);


        dialog.show();
        Call<UserModelCLass> call = apiInterface.performRegistration(fullname, username, email, password, eligibility, age, selectedGender, city, height, education, skin_color, eye_color, maritial_status, languages, training, passport, figure, biography);

        call.enqueue(new Callback<UserModelCLass>() {
            @Override
            public void onResponse(Call<UserModelCLass> call, Response<UserModelCLass> response) {

                dialog.dismiss();
                if (response.body().getResponse().equals("ok")) {

                    //dialog.dismiss();
                    prefConfig.displayToast("User Registered Successfully..!!!");
                    Intent i = new Intent(getApplicationContext(),LoginActivity.class);
                    finish();
                } else if (response.body().getResponse().equals("exist")) {
                    //dialog.dismiss();
                    prefConfig.displayToast("User Already Registered. Please Login..!!!");
                } else if (response.body().getResponse().equals("error")) {
                    prefConfig.displayToast("Registration Unsuccessfull..!!!");
                }
            }

            @Override
            public void onFailure(Call<UserModelCLass> call, Throwable t) {

                dialog.dismiss();
                prefConfig.displayToast("Error.. No response from server...!!!!");
                Log.e("Photo", "onFailure: "+t );

            }
        });

    }

//    private String ImageToString() {
//        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
//        byte[] imgByte = byteArrayOutputStream.toByteArray();
//        return Base64.encodeToString(imgByte, Base64.DEFAULT);
//   }

}
