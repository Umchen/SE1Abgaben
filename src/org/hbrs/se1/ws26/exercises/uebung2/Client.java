package org.hbrs.se1.ws26.exercises.uebung2;

import java.util.ArrayList;
import java.util.List;

public class Client {
    public void run(Container container, int memberCount) throws ContainerException {
        List<Member> members = new ArrayList<>();
        for (int i = 0; i < memberCount; i++) {
            members.add(new ConcreteMember(i));
        }

        for (Member member : members) {
            container.addMember(member);
        }

        MemberView.dump(container.getCurrentList());
    }
}
