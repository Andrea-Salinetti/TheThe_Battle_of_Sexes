package com.example.theproject_1;

import java.util.Random;

public class Fast extends Woman {

    public Fast(int Current_year) {
        super(Current_year);
        Random chose = new Random();
        Court_years = chose.nextInt(2);
        this.Attribute = "Fast";
        this.Sex = "F";
        this.maxChildren = (int) (2 + (Math.random() * 10));

        this.CoyGene = 50;
        this.FastGene = 50;
    }


}
