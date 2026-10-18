package org.hbrs.se1.ws26.exercises.uebung2;

import java.io.Serializable;

public class ConcreteMember implements Member, Comparable<Member>, Serializable {
    private final Integer id;

    public ConcreteMember(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getID() {
        return id;
    }

    @Override
    public int compareTo(Member member) {
        return Integer.compare(this.getID(), member.getID());
    }

    @Override
    public String toString() {
        return "Member (ID = " + id + ")";
    }
}
