package com.example.alex.jocdelamoneda;

import android.content.DialogInterface;
import android.content.Intent;

import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    TextView explicacio, titleAlert;
    Button next;
    static ImageView title;
    Intent pantalla2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //This code means that the screen will be complete at the app
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Declaration of objects
        explicacio = (TextView) findViewById(R.id.stc_explicacio);
        next = (Button) findViewById(R.id.btn_comença);
        title = (ImageView) findViewById(R.id.titlemain);
        //tam();
        //Call of changeLanguage()
        changeLanguage();

        //It change the font family to another (external font family)
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/Amatic-Bold.ttf");
        explicacio.setTypeface(custom_font);
        next.setTypeface(custom_font);

        //Creates the intent. It go to Ruleta.class(Spinner)
        pantalla2 = new Intent(getApplicationContext(), Ruleta.class);

        //What to do after click on next button
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Open AlertDialog
                dialog();

            }
        });

    }

    private void dialog() {
        //Creates an AlertDialog and change of activity
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(R.string.dmaintxt)
                .setPositiveButton(R.string.dsi, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        pantalla2.putExtra("tips", true);
                        startActivity(pantalla2);
                    }
                }).setNegativeButton(R.string.dno, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        pantalla2.putExtra("tips", false);
                        startActivity(pantalla2);
                    }
                }).create();

        dialog.show();
    }

    public void onBackPressed() {
        //Detects if user click on back phone button. Because we are in the first activity,
        // if user click on back button, the app will be closed. Here we can control it.
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setMessage(R.string.quit)
                    .setPositiveButton(R.string.dsi, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            //Stop the activity
                            MainActivity.this.finish();
                        }

                    })
                    .setNegativeButton(R.string.dno, null)
                    .show();
    }

    public static void changeLanguage() {
        //Detects the current phone language and adapts the app language.
        String language = Locale.getDefault().getDisplayLanguage();
        Locale localizacion;
        Configuration config = new Configuration();
        if (language.startsWith("es")) {
            localizacion = new Locale("es", "ES");
            Locale.setDefault(localizacion);
            config.setLocale(localizacion);
            title.setBackgroundResource(R.drawable.titlemain_es);
        }
        else if (language.startsWith("ca")) {
            localizacion = new Locale("cat", "CAT");
            Locale.setDefault(localizacion);
            config.setLocale(localizacion);
            title.setBackgroundResource(R.drawable.title_main);

        }else{
            localizacion = new Locale("en", "EN");
            Locale.setDefault(localizacion);
            config.setLocale(localizacion);
            title.setBackgroundResource(R.drawable.titlemain_en);
        }
    }
    public void tam(){

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels; // ancho absoluto en pixels
        int height = metrics.heightPixels; // alto absoluto en pixels
        if(height>1300){
            Log.e("1","e");
            explicacio.setTextSize(explicacio.getTextSize()+1);
        }else if(height>1500){
            Log.e("2","e");
            explicacio.setTextSize(explicacio.getTextSize()+2);
        }else if (height>1700){
            Log.e("3","e");
            explicacio.setTextSize(explicacio.getTextSize()+3);
        }else if(height>1920){
            Log.e("4","e");
            explicacio.setTextSize(explicacio.getTextSize()+4);
        }

    }
}
