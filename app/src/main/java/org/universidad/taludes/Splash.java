package org.universidad.taludes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Splash extends AppCompatActivity {


    private ImageView imgBg;
    private ProgressBar prgB;
    private TextView entName;
    private CountDownTimer myTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        connectElements();
        manageAnimations();
        final MediaPlayer introSound = MediaPlayer.create(this, R.raw.intorcut);
        introSound.start();
    }
    private void connectElements()
    {
      imgBg = findViewById(R.id.imgBg);
    }
    private void manageAnimations()
    {
//        entName.animate().setDuration(1000).setStartDelay(1000).alpha(1);
        imgBg.animate().setDuration(1000).setStartDelay(500).alpha(1);
        myTimer = new CountDownTimer(5000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(Splash.this, iniciar.class);
                startActivity(intent);
                Splash.this.finish();
            }
        }.start();
    }

}
