package org.hbrs.se1.ws26.exercises.uebung2;

import java.util.ArrayList;
import java.util.Objects;

public class Container {
    private ArrayList<Member> memberList;

    public void addMember(Member member) throws ContainerException {
        if (containsId(member.getID(), memberList.size())) {
            throw new ContainerException(
                    "Das Member-Objekt mit der ID " + member.getID() + " ist be-\n" +
                    "reits vorhanden!");
        }

        memberList.add(member);
        memberList.sort(null);
    }

    public String deleteMember(Integer id) {
        if (!containsId(id, memberList.size())) {
            return "Member-Objekt mit der ID " + id + " existiert nicht!";
        }

        memberList.remove(containsIdGetPos(id, memberList.size()) / 2);
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

    private boolean containsId(Integer searchedId, int pos) {
        Integer memberID = memberList.get(pos).getID();
        if (Objects.equals(memberID, searchedId)) {
            return true;
        } else if (searchedId < memberID) {
            return containsId(searchedId, (pos-1) / 2);
        }
        return containsId(searchedId, (memberList.size() - searchedId + 1) / 2);
    }

    private int containsIdGetPos(Integer searchedId, int pos) {
        Integer memberID = memberList.get(pos).getID();
        if (Objects.equals(memberID, searchedId)) {
            return pos;
        } else if (searchedId < memberID) {
            return containsIdGetPos(searchedId, (pos-1) / 2);
        }
        return containsIdGetPos(searchedId, (memberList.size() - searchedId + 1) / 2);
    }
}
