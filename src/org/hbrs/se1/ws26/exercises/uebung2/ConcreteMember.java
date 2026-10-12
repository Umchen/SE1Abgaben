package org.hbrs.se1.ws26.exercises.uebung2;

public class ConcreteMember implements Member, Comparable<Member>{
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
        return Integer.compare(member.getID() - id, 0);
    }
}
