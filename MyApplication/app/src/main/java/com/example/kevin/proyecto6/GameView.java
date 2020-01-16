package com.example.kevin.proyecto6;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by Kevin on 22-Apr-17.
 */

public class GameView extends SurfaceView implements Runnable, View.OnClickListener {

    private Thread hilo = null;
    private SurfaceHolder holder;
    private Boolean isItOk = false;
    private Bitmap cannon , target, egg, explosion =  null;
    private Canvas myCanvas;
    private Boolean Disparo, FirstTime, Acierto;
    private int cordX, cordY, anchoC, largoC,FirstAcierto , Score, Attempts;
    private Double tiempo;
    private MediaPlayer shotSound , yesSound, noSound;
    private Boolean IsOnTheAir;
    //private TextView tvScore, tvAttempts;

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        holder = getHolder();
        setOnClickListener(this);
        IsOnTheAir = false; //evita que se pueda disparar mientras la bala esta en el aire
        Disparo = false;
        cordX=0;
        cordY=0;
        tiempo=0.0;
        FirstTime = true;
        Acierto = false;
        FirstAcierto=0;
        //tvAttempts = (TextView) findViewById(R.id.lblNroDisp);
        //tvScore = (TextView) findViewById(R.id.lblNroAcier);

        resume();
    }

    @Override
    public void run() {
        while(isItOk)
        {
            //draw canvas
            if(!holder.getSurface().isValid())
            {
                continue;
            }

            if(FirstTime)
            {
                try {
                    cannon = BitmapFactory.decodeStream((InputStream)
                            new URL("http://findicons.com/files/icons/2711/free_icons_for_windows8_metro/128/cannon.png").getContent());
                    target = BitmapFactory.decodeStream((InputStream)
                            new URL("https://image.flaticon.com/icons/png/128/149/149231.png").getContent());
                    egg = BitmapFactory.decodeStream((InputStream)
                            new URL("https://vignette2.wikia.nocookie.net/dont-starve-game/images/5/53/Tallbird_Egg.png/revision/latest?cb=20121216155027").getContent());
                    explosion = BitmapFactory.decodeStream((InputStream)
                            new URL("http://images.clipartpanda.com/explosion-clipart-clipart-explosion-52fe.png").getContent());
                    //Para usar a Rafael Correa como objetivo descomentar las siguientes 2 lineas
                    //target = BitmapFactory.decodeStream((InputStream)
                    //new URL("https://s-media-cache-ak0.pinimg.com/236x/d0/9a/23/d09a23c621473d6ca80d8b831dd946a5.jpg").getContent());

                } catch (IOException e) {
                    e.printStackTrace();
                }
                FirstTime = false;
            }

            myCanvas = holder.lockCanvas();
            myCanvas.drawARGB(255,204,255,255);
            myCanvas.drawBitmap(cannon, 0, myCanvas.getHeight() - cannon.getHeight()  , null);
            largoC = myCanvas.getHeight();
            anchoC = myCanvas.getWidth();
            if(!Acierto)
            {
                myCanvas.drawBitmap(target , Datos.getInstance().getDistancia(),
                        myCanvas.getHeight() - target.getHeight(), null);
            }


            //Comportamiento Disparo
            if(Disparo)
            {
                tiempo = tiempo + 0.2;
                Double alfa = Datos.getInstance().getAlfa().doubleValue();
                alfa = (alfa*Math.PI)/180; //angulo elevacion
                int TNT = Datos.getInstance().getTNT(); //V0 m/s
                Double VoX = TNT*Math.cos(alfa);
                Double VoY= TNT*Math.sin(alfa);

                Double tempY = VoY*tiempo-(0.5*9.8*Math.pow(tiempo,2));
                cordY = tempY.intValue();
                int cordYenCanvas = myCanvas.getHeight()-cordY-target.getHeight();

                Double tempX = VoX*tiempo;
                cordX = tempX.intValue();

                myCanvas.drawBitmap(egg, cordX,cordYenCanvas,null);

                //Caso acierto
                if(cordYenCanvas>=(largoC-target.getHeight()) && cordYenCanvas<=largoC
                        && cordX>=Datos.getInstance().getDistancia()&&cordX<=(Datos.getInstance().getDistancia()+target.getWidth()))
                {
                    FirstAcierto++;
                    Acierto = true;
                    myCanvas.drawBitmap(explosion , Datos.getInstance().getDistancia(),
                            myCanvas.getHeight() - target.getHeight(), null);
                }

                if(FirstAcierto == 1) //solo se cuenta una vez el acierto
                {
                    FirstAcierto++; //se asegura que no vuelve a entrar
                    //play acierto sound
                    yesSound.start();
                    //sumar nro de aciertos
                    Datos.getInstance().addScore();
                }

                if(cordYenCanvas >largoC || cordX > anchoC) //si esta fuera de la pantalla se vuelve a graficar el target
                {
                    if(!Acierto)
                    {
                        //play error sound
                        noSound.start();
                    }
                    Acierto = false;
                    IsOnTheAir = false;
                    Disparo = false;
                    //pause();
                }
            }
            //Se dibuja
            holder.unlockCanvasAndPost(myCanvas);
        }
    }

    public void resume()
    {
        isItOk=true;
        hilo = new Thread(this);
        hilo.start();
    }

    @Override
    public void onClick(View v) {
        if(!IsOnTheAir)
        {
            if(Datos.getInstance().getAttempts()>0)
            {
                shotSound.reset();
                yesSound.reset();
                noSound.reset();
            }
            shotSound = MediaPlayer.create(Datos.getInstance().getMainContext(),R.raw.cannon_fire);
            yesSound = MediaPlayer.create(Datos.getInstance().getMainContext(),R.raw.target_hit);
            noSound = MediaPlayer.create(Datos.getInstance().getMainContext(),R.raw.blocker_hit);

            IsOnTheAir = true;
            shotSound.start();
            Disparo = true;
            tiempo=0.00;
            FirstAcierto=0;
            Datos.getInstance().addAttempt();
        }

    }
}