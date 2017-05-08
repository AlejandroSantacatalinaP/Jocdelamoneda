package com.example.alex.jocdelamoneda;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.INotificationSideChannel;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

/**
 * Created by Alex on 28/04/2017.
 */

public class Mostrar_Pregunta extends AppCompatActivity {
    TextView pregunta;
    Button vorela,moneda;
    boolean tips;
    static ImageView title;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mostrar_pregunta);

        //This code means that the screen will be complete at the app
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Declaration of objects
        title = (ImageView)findViewById(R.id.titlecontestacio);
        pregunta = (TextView) findViewById(R.id.stc_pregunta);
        vorela = (Button)findViewById(R.id.btn_vorela);
        moneda = (Button)findViewById(R.id.btn_moneda);

        //Call of changeLanguage()
        changeLanguage();

        //It change the font family to another (external font family)
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Amatic-Bold.ttf");
        pregunta.setTypeface(custom_font);
        vorela.setTypeface(custom_font);
        moneda.setTypeface(custom_font);

        //Users wants tips?
         i = getIntent();
        tips=i.getBooleanExtra("tips",false);
        if (tips){
            dialog();
        }

        //CLICKLISTENERS
        vorela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("answer",i.getStringExtra("pregunta"));
                pregunta.setText(i.getStringExtra("pregunta"));
                vorela.setVisibility(View.INVISIBLE);
                moneda.setVisibility(View.VISIBLE);
            }
        });
        moneda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogM();
            }
        });
    }

    private void dialog() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.dtitlecontestacio)
                .setMessage(R.string.dtxtcontestacio)
                .setPositiveButton(R.string.dbtnruleta, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        dialog2();
                    }
                }).create();

        dialog.show();
    }
    private void dialog2() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.dtitlecontestacio)
                .setMessage(R.string.dtxtcontestacio2)
                .setPositiveButton(R.string.dbtnruleta, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).create();

        dialog.show();
    }
    private void dialogM() {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.dtitlemoneda)
                .setMessage(R.string.moneda)
                .setPositiveButton(R.string.interna, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Intent moneda = new Intent(getApplicationContext(),Moneda.class);
                        moneda.putExtra("tips",tips);
                        moneda.putExtra("pregunta",i.getStringExtra("pregunta"));
                        startActivity(moneda);
                    }
                }).setNegativeButton(R.string.propia, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Intent monedaPropia = new Intent(getApplicationContext(),MonedaPropia.class);
                        monedaPropia.putExtra("tips",tips);
                        monedaPropia.putExtra("pregunta",i.getStringExtra("pregunta"));
                        startActivity(monedaPropia);
                    }
                }).create();

        dialog.show();
    }
    public static void changeLanguage() {
        //Detects the current phone language and adapts the app language.
        String language = Locale.getDefault().getDisplayLanguage();
        if (language.startsWith("es")) {
            title.setImageResource(R.drawable.titlecontestacion_es);
        }
        else if (language.startsWith("ca")) {
            title.setImageResource(R.drawable.contestaciotitle);

        }else{
            title.setImageResource(R.drawable.titlecontestacio_en);
        }
    }
}
