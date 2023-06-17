package com.example.theproject_1;

import java.util.Random;

public class Man implements IPerson {

    public String Sex;
    protected String Attribute;
    public int Birth_year;
    public int Death_year;
    public Woman partner;

    int FaithGene;
    int PhilGene;

    protected int maxChildren;
    int start_rel;


    public Man(int Current_year){
        this.Birth_year = Current_year;
        life_calc();
    }

    public void life_calc(){
        Random choice = new Random();
        this.Death_year = choice.nextInt(0, 100) + Population.Current_year;

    }

    @Override
    public boolean amIAdult() {
        return (Population.Current_year - Birth_year) >= 18;
    }

    @Override
    public boolean amIAlive() {
        if (this.Death_year == Population.Current_year){
            if (this.partner != null){
                this.partner.partner = null;}
            this.partner = null;
            return false;
        }
        return true;
    }

    public boolean Dating(){
        return true;
    }

    public static int parentsPayoff(Woman wife, Man husband) {
        int payoff = 0;
        if (husband.Attribute == "Faithful") {
            if (wife.Attribute == "Coy") {
                payoff = (Population.a - Population.b / 2 - Population.c);
            } else {
                payoff = (Population.a - Population.b / 2);
            }
        } else {
            if (wife.Attribute == "Fast") {
                payoff = (Population.a);
            }
        }
        return payoff;
    }
}