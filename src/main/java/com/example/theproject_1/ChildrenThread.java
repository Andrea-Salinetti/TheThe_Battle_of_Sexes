package com.example.theproject_1;

import java.util.ArrayList;

public class ChildrenThread extends Thread{

    public static ArrayList<Object> Children_list;
    public static boolean done;
    public Population population;
    public static ArrayList<Man> boys_18 = new ArrayList<>();
    public static ArrayList<Woman> girls_18 = new ArrayList<>();

    public ChildrenThread(ArrayList<Object> list, Population population) {
        this.Children_list = list;
        this.population = population;
    }

    @Override
    public void run() {
        while(!Population.end){
            for(Object child: Children_list) {
                String type = child.getClass().getName();

                if (type == "com.example.theproject_1.Philanderer"){
                    Man NewChild = (Philanderer) child;
                    if (NewChild.amIAdult()){
                        boys_18.add(NewChild);
                    }
                } else if (type == "com.example.theproject_1.Faithful"){
                    Man NewChild = (Faithful) child;
                    if (NewChild.amIAdult()){
                        boys_18.add(NewChild);
                    }
                } else if ((type == "com.example.theproject_1.Fast")){
                    Woman NewChild = (Fast) child;
                    if (NewChild.amIAdult()){
                        girls_18.add(NewChild);
                    }
                } else if (type == "com.example.theproject_1.Coy"){
                    Woman NewChild = (Coy) child;
                    if (NewChild.amIAdult()){
                        girls_18.add(NewChild);
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
