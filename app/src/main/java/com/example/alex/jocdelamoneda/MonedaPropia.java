package com.example.alex.jocdelamoneda;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.INotificationSideChannel;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.util.Locale;
import java.util.Random;


/**
 * Created by Alex on 04/05/2017.
 */

public class MonedaPropia extends AppCompatActivity {
    TextView titleMP, pregunta;
    static ImageView titlemonedapr;
    ImageView fons;
    Button cara, creu, acabar,acabarcreu;
    Intent i;
    Random rm = new Random();
    boolean tips, creuz=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moneda_propia);

        //Declaration of objects
        titlemonedapr= (ImageView)findViewById(R.id.titlemonedapr);
        //titleMP = (TextView)findViewById(R.id.title_mp);
        pregunta = (TextView)findViewById(R.id.stc_cara);
        cara = (Button)findViewById(R.id.btn_cara2);
        creu = (Button)findViewById(R.id.btn_creu2);
        fons = (ImageView)findViewById(R.id.fons_preguntac);
        acabar = (Button)findViewById(R.id.btn_acabar);
        acabarcreu = (Button)findViewById(R.id.bacabar_creu);

        //Call of changeLanguage()
        changeLanguage();

        //It change the font family to another (external font family)
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Amatic-Bold.ttf");
        pregunta.setTypeface(custom_font);
        //titleMP.setTypeface(custom_font);
        cara.setTypeface(custom_font);
        creu.setTypeface(custom_font);
        acabar.setTypeface(custom_font);
        acabarcreu.setTypeface(custom_font);

        //User wants tips?
        i = getIntent();
        pregunta.setText(i.getStringExtra("pregunta"));
        tips=i.getBooleanExtra("tips",false);
        if (tips){
            dialog();
        }

        //CLICKLISTENERS
        cara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cara.setVisibility(View.INVISIBLE);
                fons.setVisibility(View.VISIBLE);
                pregunta.setVisibility(View.VISIBLE);
                creu.setVisibility(View.INVISIBLE);
                acabar.setVisibility(View.VISIBLE);
            }
        });

        creu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cara.setVisibility(View.INVISIBLE);
                creu.setVisibility(View.INVISIBLE);
                acabarcreu.setVisibility(View.VISIBLE);
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
        acabarcreu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
    }
    public static void changeLanguage() {
        //Detects the current phone language and adapts the app language.
        String language = Locale.getDefault().getDisplayLanguage();
        if (language.startsWith("es")) {
            titlemonedapr.setImageResource(R.drawable.hort_es);
        }
        else if (language.startsWith("ca")) {
            titlemonedapr.setImageResource(R.drawable.hort_ca);

        }else{
            titlemonedapr.setImageResource(R.drawable.hort);
        }
    }

    private void dialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.dtitlemoneda)
                .setMessage(R.string.dtxtmonedapropia)
                .setPositiveButton(R.string.dbtnacabar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                }).create();

        dialog.show();
    }

}
