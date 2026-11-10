package org.hbrs.se1.ws26.exercises.uebung4.prototype;

public class Prio {

    public static double calculate (int value, int penalty, int expenses, int risk){
        if((value>5 || value<1)||(penalty>5 || penalty<1)||(expenses<1 || expenses>5) ||(risk<1)){
            throw new IllegalArgumentException();
        }else
            return (double) (value + penalty) /(expenses+risk);
    }
}