package com.example.alex.jocdelamoneda;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;
import java.util.Random;
import java.util.Timer;

import static android.support.v7.appcompat.R.id.time;

/**
 * Created by Alex on 28/04/2017.
 */

public class Ruleta extends AppCompatActivity {
    ImageView roda, fletxa;
    Button gira, pregunta,para;
    Boolean aux2;
    Random r = new Random();
    boolean tips=true;
    static ImageView title;
    int aux = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ruleta);

        //This code means that the screen will be complete at the app
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Declaration of objects
        title = (ImageView)findViewById(R.id.titleruleta);
        fletxa = (ImageView) findViewById(R.id.fletxa);
        gira = (Button) findViewById(R.id.gira);
        pregunta = (Button) findViewById(R.id.pregunta);
        para = (Button) findViewById(R.id.para);

        //Call of changeLanguage()
        changeLanguage();

        //It change the font family to another (external font family)
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Amatic-Bold.ttf");
        gira.setTypeface(custom_font);
        pregunta.setTypeface(custom_font);
        para.setTypeface(custom_font);

        //Here we check if user wants tips about how to play
        Intent i =getIntent();
        tips=i.getBooleanExtra("tips",false);
        if (tips){
            //YES
            //Show tips
            dialog();
        }

        //Make spin the spinner
        gira.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aux++;
                Rotate(fletxa);
                gira.setVisibility(View.INVISIBLE);
                para.setVisibility(View.VISIBLE);

            }
        });

        //Make stop the spinnter
        para.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aux++;
                para.setVisibility(View.INVISIBLE);
                gira.setVisibility(View.VISIBLE);
                //Stop the spinner
                fletxa.clearAnimation();
                //Let the arrow to a random position
                fletxa.setRotation(r.nextInt(360 - 0));

                if (aux>3){
                    //If we make spin and stop for two times the spinner,
                    // show the button for the next activity
                    pregunta.setVisibility(View.VISIBLE);
                    //and initializes the variable that controll it
                    aux=1;
                }
            }
        });

        //When we click the button for change the activity.. Go to Preguntar.class(Question).
        // We save if user want to see tips
        pregunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent preguntar = new Intent(getApplicationContext(), Preguntar.class);
                preguntar.putExtra("tips",tips);
                startActivity(preguntar);
            }
        });
    }

    public void Rotate (ImageView img){
        //This method make spin the spinner. We have to indicate the speed.
        float ROTATE_FROM = 0.0f;
        final float ROTATE_TO = -10.0f * 360.0f;
        RotateAnimation r = new RotateAnimation(ROTATE_FROM, ROTATE_TO,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        r.setDuration(2000);
        r.setRepeatCount(Animation.INFINITE);
        r.setRepeatMode(Animation.RESTART);
        r.setInterpolator(getApplicationContext(), android.R.anim.linear_interpolator);
        img.startAnimation(r);
    }
    private void dialog() {
        //Tip dialog.
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.dtitleruleta)
                .setMessage(R.string.dtxtruleta)
                .setPositiveButton(R.string.dbtnruleta, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).create();

        dialog.show();
    }
    public static void changeLanguage() {
        //Detects the current phone language and adapts the app language.
        String language = Locale.getDefault().getDisplayLanguage();
        if (language.startsWith("es")) {
            title.setImageResource(R.drawable.titolruleta);
        }
        else if (language.startsWith("ca")) {
            title.setImageResource(R.drawable.titolruleta);

        }else{
            title.setImageResource(R.drawable.titleruleta_en);
        }
    }

}
