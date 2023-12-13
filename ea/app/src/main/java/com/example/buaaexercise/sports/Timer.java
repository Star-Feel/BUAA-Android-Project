package com.example.buaaexercise.sports;

import android.os.SystemClock;
import android.util.Log;
import android.widget.Chronometer;

public class Timer {
    private Chronometer timer;

    private static int elaspedTime = 0;

    private boolean started;

    public Timer(Chronometer timer) {
        this.timer = timer;
        timer.stop();
        elaspedTime = 0;
        started = false;
    }

    public int getSeconds() {
        return elaspedTime/1000;
    }

    void start() {
        if (!started) {
            started = true;
            elaspedTime = 0;
            timer.setBase(SystemClock.elapsedRealtime());
            int hour = (int) ((SystemClock.elapsedRealtime() - timer.getBase()) / 1000 / 3600);

            timer.setFormat("0"+ hour +":%s");
            Log.d("start:",String.valueOf(SystemClock.elapsedRealtime() - timer.getBase()));
            timer.start();

        } else {
            timer.setBase(SystemClock.elapsedRealtime()-elaspedTime);
            int hour = (int) ((SystemClock.elapsedRealtime() - timer.getBase()) / 1000 / 3600);
            timer.setFormat("0"+ hour +":%s");
            Log.d("restart:",String.valueOf(SystemClock.elapsedRealtime() - timer.getBase()));
            timer.start();
        }
    }

    void pause() {
        timer.stop();
        Log.d("pause:",String.valueOf(SystemClock.elapsedRealtime() - timer.getBase()));
        //保存暂停时间，方便后续继续开始，时间不会错
        elaspedTime = (int)(SystemClock.elapsedRealtime()-timer.getBase());
    }

    void stop() {
        timer.stop();
        elaspedTime = (int)(SystemClock.elapsedRealtime()-timer.getBase());
        timer.setBase(SystemClock.elapsedRealtime());//计时器清零
        timer.setFormat("00:%s");
        started = false;
    }
}
