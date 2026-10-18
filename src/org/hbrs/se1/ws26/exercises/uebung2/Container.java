package org.hbrs.se1.ws26.exercises.uebung2;

import org.hbrs.se1.ws26.exercises.uebung3.persistence.PersistenceException;
import org.hbrs.se1.ws26.exercises.uebung3.persistence.PersistenceStrategy;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.hbrs.se1.ws26.exercises.uebung3.persistence.PersistenceException.ExceptionType.ImplementationNotAvailable;
import static org.hbrs.se1.ws26.exercises.uebung3.persistence.PersistenceException.ExceptionType.NoStrategyIsSet;

public class Container {
    private static final Container CONTAINER = new Container();
    private List<Member> memberList;
    private PersistenceStrategy<Member> persistenceStrategy;

    private Container() {
        memberList = new ArrayList<>();
    }

    public static Container getInstance() {
        return CONTAINER;
    }

    public List<Member> getCurrentList() {
        return memberList;
    }

    public void addMember(Member member) throws ContainerException {
        if (getPosition(member.getID(), 0, memberList.size() - 1) != -1) {
            throw new ContainerException(
                    "Das Member-Objekt mit der ID " + member.getID() + " ist be-\n" +
                    "reits vorhanden!");
        }

        memberList.add(member);
        memberList.sort(null);
    }

    public String deleteMember(Integer id) {
        int pos = getPosition(id, 0, memberList.size() - 1);
        if (pos == -1) {
            return "Member-Objekt mit der ID " + id + " existiert nicht!";
        }

        memberList.remove(pos);
        return "Member-Objekt erfolgreich entfernt!";
    }

    public void dump() {
        MemberView.dump(memberList);
    }

    public int size() {
        return memberList.size();
    }

    private int getPosition(Integer searchedId, int left, int right) {
        if (left > right) {
            return -1;
        }

        int pos = (left + right) / 2;
        Integer memberID = memberList.get(pos).getID();

        if (Objects.equals(memberID, searchedId)) {
            return pos;
        } else if (searchedId < memberID) {
            return getPosition(searchedId, left, pos - 1);
        }

        return getPosition(searchedId, pos + 1, right);
    }

    public void setPersistenceStrategy(PersistenceStrategy<Member> persistenceStrategy) throws PersistenceException {
        if (persistenceStrategy == null) {
            throw new PersistenceException(NoStrategyIsSet, "Keine gueltige Strategie");
        }
        this.persistenceStrategy = persistenceStrategy;
    }

    public void store() throws PersistenceException {
        if (persistenceStrategy == null) {
            throw new PersistenceException(NoStrategyIsSet, "Keine PersistenceStrategy vorhanden!");
        }

        try {
            persistenceStrategy.save(memberList);
        } catch (UnsupportedOperationException e) {
            throw new PersistenceException(ImplementationNotAvailable, e.getMessage());
        }
    }

    public void load() throws PersistenceException {
        if (persistenceStrategy == null) {
            throw new PersistenceException(NoStrategyIsSet, "Keine PersistenceStrategy vorhanden!");
        }
        try {
            memberList = persistenceStrategy.load();
        } catch (UnsupportedOperationException e) {
            throw new PersistenceException(ImplementationNotAvailable, e.getMessage());
        }
    }

    public void deleteAllMembers() {
        memberList.clear();
    }
}
