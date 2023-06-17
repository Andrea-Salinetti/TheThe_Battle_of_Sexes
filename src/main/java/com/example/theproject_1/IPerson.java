package com.example.theproject_1;

public interface IPerson {

    default boolean amIAdult(){
        return false;
    }

    default boolean amIAlive(){
        return false;
    }

}
