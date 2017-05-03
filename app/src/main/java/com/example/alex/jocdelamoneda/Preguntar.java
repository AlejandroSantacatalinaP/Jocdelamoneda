package com.example.alex.jocdelamoneda;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import java.util.Locale;
import java.util.Random;

/**
 * Created by Alex on 28/04/2017.
 */

public class Preguntar extends AppCompatActivity {
    EditText pregunta;
    Button enviar;
    boolean tips=true;
    static ImageView title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preguntar);

        //This code means that the screen will be complete at the app
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Declaration of objects
        title = (ImageView)findViewById(R.id.titlepreguntar);
        enviar = (Button) findViewById(R.id.btn_enviar);
        pregunta = (EditText) findViewById(R.id.etpregunta);

        //Call of changeLanguage()
        changeLanguage();

        //It change the font family to another (external font family)
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Amatic-Bold.ttf");
        pregunta.setTypeface(custom_font);
        enviar.setTypeface(custom_font);

        Intent i =getIntent();
        tips=i.getBooleanExtra("tips",false);
        if (tips){
            dialog();
        }

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!pregunta.getText().toString().isEmpty()){
                    Intent mostrarPregunta = new Intent(getApplicationContext(),Mostrar_Pregunta.class);
                    mostrarPregunta.putExtra("pregunta",pregunta.getText().toString());
                    mostrarPregunta.putExtra("tips",tips);
                    startActivity(mostrarPregunta);
                }
            }
        });
    }
    private void dialog() {

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.preg)
                .setMessage(R.string.dtxtpregunta)
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
            title.setImageResource(R.drawable.titolpregunta);
        }
        else if (language.startsWith("ca")) {
            title.setImageResource(R.drawable.titolpregunta);

        }else{
            title.setImageResource(R.drawable.titlepregunta_en);
        }
    }
}
