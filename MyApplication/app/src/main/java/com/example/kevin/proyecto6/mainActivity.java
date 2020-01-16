package com.example.kevin.proyecto6;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

public class mainActivity extends AppCompatActivity implements Runnable {

    private Button btnMasTNT , btnMasAlfa, btnMenosTNT, btnMenosAlfa, btnMasDist, btnMenosDist , btnAgain;
    private Integer actualTNT , actualAlfa, actualDist ;
    private TextView tvTNT, tvAlfa, tvNroDisp , tvNroAcier, tvDist;
    private Boolean isItOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        actualAlfa  = 45;
        actualDist =100;
        actualTNT =60;
        tvTNT= (TextView) findViewById(R.id.lblTNT);
        tvAlfa= (TextView) findViewById(R.id.lblAlfa);
        tvNroAcier= (TextView) findViewById(R.id.lblNroAcier);
        Datos.getInstance().setMainContext(this);
        isItOk = true;

        //Onclick Listener de MasTNT
        btnMasTNT = (Button) findViewById(R.id.masTNT);
        btnMasTNT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualTNT++;
                tvTNT.setText(actualTNT.toString()+ "kg TNT");
                Datos.getInstance().setTNT(actualTNT);
            }
        });
        //Onclick Listener de MenosTNT
        btnMenosTNT = (Button) findViewById(R.id.menosTNT);
        btnMenosTNT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(actualTNT>0)
                    actualTNT--;
                tvTNT.setText(actualTNT.toString()+"kg TNT");
                Datos.getInstance().setTNT(actualTNT);
            }
        });

        //Onclick Listener de MasAlfa
        btnMasAlfa = (Button) findViewById(R.id.masAlfa);
        btnMasAlfa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(actualAlfa<=90)
                    actualAlfa++;
                tvAlfa.setText(actualAlfa.toString()+" g");
                Datos.getInstance().setAlfa(actualAlfa);
            }
        });
        //Onclick Listener de MenosAlfa
        btnMenosAlfa = (Button) findViewById(R.id.menosAlfa);
        btnMenosAlfa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(actualAlfa>0)
                    actualAlfa--;
                tvAlfa.setText(actualAlfa.toString()+" g");
                Datos.getInstance().setAlfa(actualAlfa);
            }
        });
        //Onclick Listener MasDistancia
        btnMasDist = (Button) findViewById(R.id.btnMasDist);
        btnMasDist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualDist=actualDist+100;
                tvDist.setText(actualDist.toString()+"km");
                Datos.getInstance().setDistancia(actualDist);
            }
        });

        //Onclick Listener MenosDistancia
        btnMenosDist = (Button) findViewById(R.id.btnMenosDist);
        btnMenosDist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(actualDist>0)
                    actualDist=actualDist-100;
                tvDist.setText(actualDist.toString()+"km");
                Datos.getInstance().setDistancia(actualDist);
            }
        });

        //Programacion del Boton de Reinicio del Juego!
        btnAgain = (Button) findViewById(R.id.btnRestart);
        btnAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Datos.getInstance().setAttempts(0);
                Datos.getInstance().setScore(0);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Thread hilo = new Thread(this);
        hilo.start();
    }

    @Override
    public synchronized void run() { //actualiza los score e intentos cada 1/2 segundo
        tvNroDisp= (TextView) findViewById(R.id.lblNroDisp);
        tvDist = (TextView) findViewById(R.id.lblDist);
        while (isItOk)
        {
            try {
                wait(500);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tvNroDisp.setText("Disparos = "+Datos.getInstance().getAttempts());
                        tvNroAcier.setText("Aciertos = "+Datos.getInstance().getScore());
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
