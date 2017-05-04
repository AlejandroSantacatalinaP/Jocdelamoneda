package com.example.alex.jocdelamoneda;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Random;

/**
 * Created by Alex on 28/04/2017.
 */

public class Moneda extends AppCompatActivity {
    ImageView moneda,fons;
    TextView pregunta;
    Button acabar;
    Random rm = new Random();
    Intent i;
    static ImageView title;
    private TextToSpeech TTS;
    boolean tips, cara=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moneda);

        //This code means that the screen will be complete at the app
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Declaration of objects
        moneda = (ImageView)findViewById(R.id.moneda);
        fons = (ImageView)findViewById(R.id.fons_moneda);
        pregunta=(TextView)findViewById(R.id.stc_pregunta2);
        title = (ImageView)findViewById(R.id.titlemoneda);
        acabar = (Button)findViewById(R.id.btn_acabar2);

        //Call of changeLanguage()
        changeLanguage();

        //It change the font family to another (external font family)
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Amatic-Bold.ttf");
        pregunta.setTypeface(custom_font);
        acabar.setTypeface(custom_font);

        //User wants tips???
        i = getIntent();
        tips=i.getBooleanExtra("tips",false);
        if (tips){
            dialog();
        }else{
            if (rm.nextInt(10)%2==0){

                moneda.setImageResource(R.drawable.cara);
                cara=true;

            }else{
                moneda.setImageResource(R.drawable.creu);
                cara=false;
            }
            moneda.setClickable(true);
        }
        moneda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cara && pregunta.getVisibility()==View.INVISIBLE){
                    moneda.setVisibility(View.INVISIBLE);
                    pregunta.setText(i.getStringExtra("pregunta"));
                    pregunta.setVisibility(View.VISIBLE);
                    StartSpeak(i.getStringExtra("pregunta"));
                }
                acabar.setVisibility(View.VISIBLE);
            }
        });
        acabar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }

    private void dialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.dtitlemoneda)
                .setMessage(R.string.dtxtmoneda)
                .setPositiveButton(R.string.dbtnmoneda, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (rm.nextInt(10)%2==0){

                            moneda.setImageResource(R.drawable.cara);
                            cara=true;

                        }else{
                            moneda.setImageResource(R.drawable.creu);
                            cara=false;
                        }
                        moneda.setClickable(true);
                        dialog.cancel();
                    }
                }).create();

        dialog.show();
    }

    private void StartSpeak(final String data) {

        TTS = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int initStatus) {
                if (initStatus == TextToSpeech.SUCCESS) {
                    if (TTS.isLanguageAvailable(Locale.getDefault()) == TextToSpeech.LANG_AVAILABLE)
                        TTS.setLanguage(Locale.getDefault());
                    TTS.setPitch(1.3f);
                    TTS.setSpeechRate(0.7f);
                    // start speak
                    speakWords(data);
                } else if (initStatus == TextToSpeech.ERROR) {
                    Toast.makeText(getApplicationContext(), "Sorry! Text To Speech failed...", Toast.LENGTH_LONG).show();
                }
            }


        });
    }
        private void speakWords ( String speech){
            TTS.speak(speech, TextToSpeech.QUEUE_FLUSH, null);
        }

    public static void changeLanguage() {
        //Detects the current phone language and adapts the app language.
        String language = Locale.getDefault().getDisplayLanguage();
        if (language.startsWith("es")) {
            title.setImageResource(R.drawable.hort_es);
        }
        else if (language.startsWith("ca")) {
            title.setImageResource(R.drawable.hort_ca);

        }else{
            title.setImageResource(R.drawable.hort);
        }
    }
}
