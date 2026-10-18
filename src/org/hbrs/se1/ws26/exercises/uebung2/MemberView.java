package org.hbrs.se1.ws26.exercises.uebung2;

import java.util.List;

public class MemberView {
    public static void dump(List<Member> liste) {
        liste.forEach(System.out::println);
    }
}
