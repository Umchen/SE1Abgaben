package org.hbrs.se1.ws26.solutions.uebung3.enumSingleton;

public class EnumMain {
    public static void main(String[] args) {
        MySingleton singleton = MySingleton.INSTANCE;
        System.out.println(singleton.getValue());
        singleton.setValue(2);
        System.out.println(singleton.getValue());
    }
}
