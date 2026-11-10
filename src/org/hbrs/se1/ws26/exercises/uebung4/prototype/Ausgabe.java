package org.hbrs.se1.ws26.exercises.uebung4.prototype;

import java.util.List;

public class Ausgabe {
    public static void Dump(List<UserStory> Stories){
        Stories.stream().sorted((s2,s1)->Double.compare(s1.getPrio(),s2.getPrio()))
                .forEach(s -> {
                    System.out.println("ID: " + s.getId());
                    System.out.println("Titel: " + s.getTitel());
                    System.out.println("Projekt: " + s.getProject());
                    System.out.println("Akzeptanzkriterien: " + s.getAcceptCriteria());
                    System.out.println("Prio: " + s.getPrio());
                    System.out.println("-------------------");
                } );
    }
}