package timer;

import main.App;

import java.util.ArrayList;

public class Timer extends Thread {

    private ArrayList<TimerListener> listeners = new ArrayList<TimerListener>();
    private long tick;
    private int tickRate;
    private boolean started = false, stop = false, pause = false;

    public Timer(int tickRate) {
        this.tickRate = tickRate;
    }

    public void register(TimerListener listener) {
        listeners.add(listener);
    }

    public void stopTimer() {
        stop = true;
    }

    public boolean isStarted() {
        return started;
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public boolean isPaused() {
        return pause;
    }

    private void tick() {
        if(tick == Long.MAX_VALUE)
            tick = 0;
        tick++;
        for(TimerListener listener : listeners) {
            listener.onTick(tick);
        }
    }

    /*public int getTickRate() {
        return tickRate;
    }*/

    public void run() {
        started = true;
        long time, lastTime;
        int delay = Math.round(1000000000f / (float)tickRate); //nanoseconds
        int sleepTime;
        while(!stop) {
            lastTime = System.nanoTime();
            if(!pause) {
                tick();
            }
            time = System.nanoTime();
            sleepTime = (int)(delay - (time - lastTime));
            if(time - lastTime < delay) {
                try {
                    Thread.sleep(sleepTime/1000000, sleepTime%1000000);
                } catch (InterruptedException e) {
                    App.onError(e);
                }
            }
        }
    }
}
