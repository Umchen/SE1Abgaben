package org.hbrs.se1.ws26.exercises.uebung2;

import org.hbrs.se1.ws26.exercises.uebung3.persistence.PersistenceException;
import org.hbrs.se1.ws26.exercises.uebung3.persistence.PersistenceStrategyStream;

public class Main {
    public static void main(String[] args) {
        Container container = Container.getInstance();
        container.deleteAllMembers();

        try {
            PersistenceStrategyStream<Member> persistenceStrategy = new PersistenceStrategyStream<>();
            persistenceStrategy.setLocation("src/org/hbrs/se1/ws26/exercises/uebung2");

            container.setPersistenceStrategy(persistenceStrategy);

            Client client = new Client();

            client.run(container, 20);

            container.deleteAllMembers();
        } catch (PersistenceException  | ContainerException e) {
            throw new RuntimeException(e);
        }
    }
}
