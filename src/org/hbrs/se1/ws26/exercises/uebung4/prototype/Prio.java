package org.hbrs.se1.ws26.exercises.uebung4.prototype;

public class Prio {

    public static double calculate (int value, int penalty, int expenses, int risk){
        if((value>5 || value<0)||(penalty>5 || penalty<0)||(expenses<0 || expenses>5) ||(risk<0)){
            throw new IllegalArgumentException();
        }else
            return (double) (value + penalty) /(expenses+risk);
    }
}