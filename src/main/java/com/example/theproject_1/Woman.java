package com.example.theproject_1;

import java.util.ArrayList;
import java.util.Random;

public class Woman implements IPerson {

    public String Sex;
    public String Attribute;
    public int Birth_year;
    public int Court_years;
    public int Death_year;
    public Man partner;

    int CoyGene;
    int FastGene;

    int maxChildren;

    public static ArrayList<Man> children_boys = new ArrayList<>();
    public static ArrayList<Woman> children_girls = new ArrayList<>();


    public Woman(int Current_year){
        this.Birth_year = Current_year;
        life_calc();
    }


    public void Labour(Man man, Woman woman){

        if (man.maxChildren == 0 || woman.maxChildren == 0){
            return;
        } else {
            man.maxChildren -= 1;
            woman.maxChildren -= 1;
        }

        Weights prob = new Weights();
        int randNum = (int)(Math.random()*2);
        if (randNum == 0){
            prob.addEntry("Coy", woman.CoyGene);
            prob.addEntry("Fast", woman.FastGene);
        } else {
            prob.addEntry("Faithful", man.FaithGene);
            prob.addEntry("Philanderer", man.PhilGene);
        }

        int malePayoff = Man.parentsPayoff(woman, man);
        int femalePayoff = parentsPayoff(woman, man);
        String s = prob.getRandom();
        if(s == "Coy") {
            Woman child = new Coy(Population.Current_year);
            child.CoyGene = woman.CoyGene = adjustPayoff(woman.CoyGene + femalePayoff);
            child.FastGene = woman.FastGene = adjustPayoff(woman.FastGene - femalePayoff);
            checkStrategy(child);
            Population.counter_C++;
            Population.counter_W++;
            children_girls.add(child);
        }
        else if (s == "Faithful"){
            Man child = new Faithful(Population.Current_year);
            child.FaithGene = man.FaithGene = adjustPayoff(man.FaithGene + malePayoff);
            child.PhilGene = man.PhilGene = adjustPayoff(man.PhilGene - malePayoff);
            checkStrategy(child);
            Population.counter_F++;
            Population.counter_M++;
            children_boys.add(child);
        }
        else if (s == "Fast"){
            Woman child = new Fast(Population.Current_year);
            child.FastGene = woman.FastGene = adjustPayoff(woman.FastGene + femalePayoff);
            child.CoyGene = woman.CoyGene = adjustPayoff(woman.CoyGene - femalePayoff);
            checkStrategy(child);
            Population.counter_S++;
            Population.counter_W ++;
            children_girls.add(child);
        }
        else{
            Man child = new Philanderer(Population.Current_year);
            child.PhilGene = man.PhilGene = adjustPayoff(man.PhilGene + malePayoff);
            child.FaithGene = man.FaithGene = adjustPayoff(man.FaithGene - malePayoff);
            checkStrategy(child);
            Population.counter_P++;
            Population.counter_M++;
            children_boys.add(child);
        }
        Population.counter_tot++;
    }

    public void life_calc(){
        Random choice = new Random();
        this.Death_year = choice.nextInt(0,100) + Population.Current_year;
    }

    @Override
    public boolean amIAdult() {
        return (Population.Current_year - Birth_year) >= 18 && (Population.Current_year - Birth_year) < 51;
    }

    @Override
    public boolean amIAlive() {
        if (this.Death_year == Population.Current_year){
            if (this.partner != null){
                this.partner.partner = null;
            }
            this.partner = null;
            return false;
        }
        return true;
    }

    public static int parentsPayoff(Woman wife, Man husband){
        int payoff = 0;
        if (wife.Attribute == "Coy") {
            if (husband.Attribute == "Faithful") {
                payoff = (Population.a - Population.b / 2 - Population.c);
            }
        } else {
            if (husband.Attribute == "Faithful") {
                payoff = (Population.a - Population.b / 2);
            } else {
                payoff = (Population.a - Population.b);
            }
        }
        return payoff;
    }

    public int adjustPayoff(int payoff){
        if (payoff < 0){
            return 0;
        } else if (payoff > 100){
            return 100;
        } else {
            return payoff;
        }
    }

    static void checkStrategy(Woman girl) {
        if ((girl.Attribute == "Coy" && girl.CoyGene < 10) || (girl.Attribute == "Fast" && girl.FastGene < 10)){
            if (girl.Attribute == "Coy") {
                girl.Attribute = "Fast";
                girl.CoyGene = girl.FastGene = 50;
            } else {
                girl.Attribute = "Coy";
                girl.CoyGene = girl.FastGene = 50;
            }
        }
    }

    static void checkStrategy(Man boy) {
        if ((boy.Attribute == "Faithful" && boy.FaithGene < 10) || (boy.Attribute == "Philanderer" && boy.PhilGene < 10)){
            if (boy.Attribute == "Faithful") {
                boy.Attribute = "Philanderer";
                boy.FaithGene = boy.PhilGene = 50;
            } else {
                boy.Attribute = "Faithful";
                boy.FaithGene = boy.PhilGene = 50;
            }
        }
    }
}


