package org.hbrs.se1.ws26.exercises.uebung2;

import java.util.ArrayList;
import java.util.Objects;

public class Container {
    private ArrayList<Member> memberList;

    public Container() {
        memberList = new ArrayList<>();
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
        for (Member member : memberList) {
            System.out.println(member);
        }
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
}
