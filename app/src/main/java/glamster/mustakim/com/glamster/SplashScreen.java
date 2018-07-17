package glamster.mustakim.com.glamster;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {

    ImageView img_splash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        img_splash = findViewById(R.id.img_splashscreen);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in_anim);
        img_splash.startAnimation(animation);

        int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
            public void run() {

                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        }, secondsDelayed * 3000);
    }
}
