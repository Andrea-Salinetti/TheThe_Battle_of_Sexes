package com.example.theproject_1;

import java.util.ArrayList;
import java.util.Random;

public class Faithful extends Man {

    public Faithful(int Current_year) {
        super(Current_year);
        this.Attribute = "Faithful";
        this.Sex = "M";
        this.maxChildren = (int) (2 + (Math.random() * 10));

        this.PhilGene = 50;
        this.FaithGene = 50;
    }

    @Override
    public boolean Dating(){
        ArrayList women_list = Population.women;
        Random choice = new Random();
        Woman woman = (Woman) women_list.get(choice.nextInt(Population.women.size()));
        if(choice.nextInt(10) < 7){
            if (woman.partner == null){
                woman.partner = this;
                this.partner = woman;
                this.start_rel = Population.Current_year;
            }
            return true;
        }
        return false;
    }
}
