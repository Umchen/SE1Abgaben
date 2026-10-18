package org.hbrs.se1.ws26.exercises.uebung2;

import java.io.Serializable;

public class ConcreteMember implements Member, Serializable {
    private final Integer id;

    public ConcreteMember(Integer id) {
        this.id = id;
    }

    @Override
    public Integer getID() {
        return id;
    }

    @Override
    public String toString() {
        return "Member (ID = " + id + ")";
    }
}
