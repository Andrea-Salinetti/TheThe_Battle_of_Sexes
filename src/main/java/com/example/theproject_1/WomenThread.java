package com.example.theproject_1;

import java.util.ArrayList;

public class WomenThread extends Thread{

    public ArrayList<Woman> women_list;
    public static ArrayList<Woman> graveyard = new ArrayList<>();
    public static boolean done;
    public Population population;

    public WomenThread(ArrayList<Woman> list, Population Population) {
        this.women_list = list;
        this.population = Population;
    }

    @Override
    public void run() {
        while(!Population.end){
            for (Woman woman : women_list) {
                if (!woman.amIAlive()) {
                    graveyard.add(woman);
                    if (woman.Attribute == "Coy"){Population.counter_C--;}
                    else{Population.counter_S--;}
                    Population.counter_deaths++;
                } else {
                    if (!woman.amIAdult()) {
                        continue;
                    } else {
                        try {
                            if (woman.partner != null) {
                                if (woman.partner.start_rel + woman.Court_years >= Population.Current_year) {
                                    woman.Labour(woman.partner, woman);
                                }
                            }
                        } catch (Exception e) {

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
