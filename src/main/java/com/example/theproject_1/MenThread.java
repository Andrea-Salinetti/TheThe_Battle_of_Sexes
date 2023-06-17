package com.example.theproject_1;

import java.util.ArrayList;

public class MenThread extends Thread{

    public ArrayList<Man> men_list;
    public static ArrayList<Man> graveyard = new ArrayList<>();
    public static ArrayList<Man> dating = new ArrayList<>();
    public static ArrayList<Man> backFree = new ArrayList<>();
    public static boolean done;
    public Population population;

    public MenThread(ArrayList<Man> list, Population Population){
        this.men_list = list;
        this.population = Population;
    }

    @Override
    public void run() {
        while(!Population.end){
            for (Man man : men_list){
                if (!man.amIAlive()){
                    graveyard.add(man);
                    if (man.Attribute== "Faithful"){Population.counter_F--;}
                    else{Population.counter_P--;}
                    Population.counter_deaths++;
                } else {
                    if (!man.amIAdult()){
                        continue;
                    } else {
                        if(man.partner == null){
                            if(man.Dating()){
                                dating.add(man);
                            }
                        }
                    }
                }
            }
        synchronized (population){
                done = true;
                try{population.wait();}
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
