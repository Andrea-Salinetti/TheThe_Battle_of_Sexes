package com.example.theproject_1;

public class Pop_Controller extends Thread{

    @Override
    public void run() {
        Population pop = null;
        try {
            pop = new Population();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pop.TheBigBang();
        try {
            pop.startSimulation();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}