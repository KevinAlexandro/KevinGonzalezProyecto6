package com.example.kevin.proyecto6;

import android.content.Context;
import android.widget.TextView;

/**
 * Created by Kevin on 22-Apr-17.
 */

class Datos {
    private static final Datos ourInstance = new Datos();

    public static Datos getInstance() {
        return ourInstance;
    }

    private Integer Distancia, TNT, Alfa, Score, Attempts;
    private Context mainContext;

    private Datos() {
        Distancia = 100;
        TNT = 60;
        Alfa=45;
        mainContext= null;
        Score =0;
        Attempts = 0;
    }

    public void addAttempt()
    {
        Attempts++;
    }

    public void addScore()
    {
        Score++;
    }

    public Integer getScore() {
        return Score;
    }

    public Integer getAttempts() {
        return Attempts;
    }

    public void setScore(Integer score) {
        Score = score;
    }

    public void setAttempts(Integer attempts) {
        Attempts = attempts;
    }

    public void setMainContext(Context mainContext) {
        this.mainContext = mainContext;
    }

    public Context getMainContext() {

        return mainContext;
    }

    public Integer getDistancia() {
        return Distancia;
    }

    public Integer getTNT() {
        return TNT;
    }

    public Integer getAlfa() {
        return Alfa;
    }

    public void setDistancia(Integer distancia) {
        Distancia = distancia;
    }

    public void setTNT(Integer TNT) {
        this.TNT = TNT;
    }

    public void setAlfa(Integer alfa) {
        Alfa = alfa;
    }


}
