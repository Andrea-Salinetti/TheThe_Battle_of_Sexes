package com.example.theproject_1;

import java.util.ArrayList;
import java.util.Random;

public class Philanderer extends Man {

    public Philanderer(int Current_year) {
        super(Current_year);
        this.Attribute = "Philanderer";
        this.Sex = "M";
        this.maxChildren = (int) (2 + (Math.random() * 10));

        this.PhilGene = 50;
        this.FaithGene = 50;
    }

    @Override
    public boolean Dating(){

        if (maxChildren <= 0){
            return false;
        } else {
            ArrayList women_list = Population.women;
            Random choice = new Random();
            Woman woman = (Woman) women_list.get(choice.nextInt(women_list.size()));

            switch (woman.Attribute) {
                case "Fast":
                    if (woman.partner == null){
                        if ((int) (Math.random())*10 < 5){
                            woman.Labour(this, woman);
                        }
                    } else {
                        if ((int) (Math.random())*10 < 3){
                            woman.partner.partner = null;
                            MenThread.backFree.add((woman.partner));
                            woman.partner = null;
                            woman.Labour(this, woman);
                            this.maxChildren -= 1;
                        }
                    }
                    break;
                case "Coy":
                    break;
            }
            return false;
        }
    }
}
