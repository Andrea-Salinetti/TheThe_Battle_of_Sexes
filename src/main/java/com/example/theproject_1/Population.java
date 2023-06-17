package com.example.theproject_1;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Population {

    static ArrayList<Man> men = new ArrayList<>();
    static ArrayList<Woman> women = new ArrayList<>();
    static ArrayList<Object> children_list = new ArrayList<>();
    public static int Current_year = 0;
    static boolean end = false;

    static int a;
    static int b;
    static int c;

    static int counter_F;
    static int counter_P;
    static int counter_C;
    static int counter_S;
    static int counter_tot = counter_C + counter_F + counter_S + counter_P;
    static int counter_M;
    static int counter_W;
    static int counter_deaths;


    public Population() throws InterruptedException {
        a = 15;
        b = 20;
        c = 3;
    }


    public void TheBigBang(){
        for (int i = 0; i < 50; i++){
            Faithful faith = new Faithful(Current_year);
            Fast fast = new Fast(Current_year);
            Philanderer phil = new Philanderer(Current_year);
            Coy coy = new Coy(Current_year);

            men.add(faith); counter_F++; counter_M++;
            men.add(phil); counter_P++; counter_M++;
            women.add(fast); counter_S++; counter_W++;
            women.add(coy); counter_C++; counter_W++;
            counter_tot = counter_C + counter_F + counter_S + counter_P;
        }
    }


    public void startSimulation() throws InterruptedException {

        Stability stability = new Stability(a,b,c);
        MenThread ThreadOfMen = new MenThread(men, this);
        WomenThread ThreadOfWomen = new WomenThread(women, this);
        ChildrenThread children = new ChildrenThread(children_list, this);

        ThreadOfMen.start();
        ThreadOfWomen.start();
        children.start();

        while (!stability.checkStability(counter_C, counter_S, counter_F, counter_P)){
            wakeThreads();
        }

        end = true;
        System.out.println("STABILITY ACHIEVED");
        ThreadOfMen.interrupt();
        ThreadOfWomen.interrupt();
        children.interrupt();
    }

    public void wakeThreads() throws InterruptedException {

        if (WomenThread.done && MenThread.done && ChildrenThread.done){

            Current_year++;

            women.addAll(ChildrenThread.girls_18);   children_list.removeAll(ChildrenThread.girls_18);
            men.addAll(ChildrenThread.boys_18);   children_list.removeAll(ChildrenThread.boys_18);

            ChildrenThread.girls_18.clear();
            ChildrenThread.boys_18.clear();
            children_list.addAll(Woman.children_boys);
            children_list.addAll(Woman.children_girls);
            Woman.children_girls.clear();
            Woman.children_boys.clear();

            men.removeAll(MenThread.dating);
            MenThread.dating.clear();
            men.addAll(MenThread.backFree);
            MenThread.backFree.clear();

            men.removeAll(MenThread.graveyard);
            women.removeAll(WomenThread.graveyard);

            counter_tot = counter_tot -(MenThread.graveyard.size()) - (WomenThread.graveyard.size());
            counter_W = counter_W - (WomenThread.graveyard.size());
            counter_M = counter_M - (MenThread.graveyard.size());

            MenThread.graveyard.clear();
            WomenThread.graveyard.clear();

            TimeUnit.MILLISECONDS.sleep(2000);

            System.out.println("Current year: " + Current_year+"\n");
            System.out.println("Population:"+ counter_tot);
            System.out.println("Faithful: " + counter_F);
            System.out.println("Philanderers: " + counter_P);
            System.out.println("Coy: " + counter_P);
            System.out.println("fast: " + counter_S);
            System.out.println("Children: " + (children_list.size()));
            System.out.println();

            synchronized(this){
                WomenThread.done = false;
                MenThread.done = false;
                ChildrenThread.done = false;
                notifyAll();
            }

        }


    }
}
