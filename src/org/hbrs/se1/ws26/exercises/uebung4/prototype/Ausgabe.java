package org.hbrs.se1.ws26.exercises.uebung4.prototype;

import java.util.List;

public class Ausgabe {
    public static void Dump(List<UserStory> stories) {

        System.out.println("ID | Titel | Projekt | Akzeptanzkriterien | Prio");
        stories.stream()
                .sorted((s1, s2) -> Double.compare(s2.getPrio(), s1.getPrio()))
                .forEach(s -> {
                    System.out.println(s.getId() + " | " +
                            s.getTitel() + " | " +
                            s.getProject() + " | " +
                            s.getAcceptCriteria() + " | " +
                            s.getPrio());
                    System.out.println("-----------------------");
                });
    }
}