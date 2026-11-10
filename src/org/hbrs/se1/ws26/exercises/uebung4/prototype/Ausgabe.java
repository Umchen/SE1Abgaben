package org.hbrs.se1.ws26.exercises.uebung4.prototype;

import java.util.List;

public class Ausgabe {
    public static void Dump(List<UserStory> stories) {

        System.out.printf("%-5s %-20s %-20s %-25s %-10s%n",
                "ID", "Titel", "Projekt", "Akzeptanzkriterien", "Prio");

        stories.stream()
                .sorted((s1, s2) -> Double.compare(s2.getPrio(), s1.getPrio()))
                .forEach(s -> System.out.printf("%-5d %-20s %-20s %-25s %-10.2f%n",
                        s.getId(),
                        s.getTitel(),
                        s.getProject(),
                        s.getAcceptCriteria(),
                        s.getPrio()));
    }
}