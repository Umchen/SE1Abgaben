package se1.ws26.tests.uebung3;


import org.hbrs.se1.ws26.exercises.uebung2.ConcreteMember;
import org.hbrs.se1.ws26.exercises.uebung2.Container;
import org.hbrs.se1.ws26.exercises.uebung2.Member;
import org.hbrs.se1.ws26.exercises.uebung3.persistence.PersistenceException;
import org.hbrs.se1.ws26.exercises.uebung3.persistence.PersistenceStrategyMongoDB;
import org.hbrs.se1.ws26.exercises.uebung3.persistence.PersistenceStrategyStream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContainerTest {
    Container container;

    @BeforeEach
    void setUp() throws PersistenceException {
        container = Container.getInstance();
        container.setPersistenceStrategy(null);
        container.deleteAllMembers();
    }

    @Test
    void testSingleton() {
        Container testContainer = Container.getInstance();

        assertSame(container, testContainer);
    }

    @Test
    void testMongoDBNotImplementedSolution() {
        // Set a strategy, which has not been implemented
        // container.setPersistenceStrategie( new PersistenceStrategyMongoDB<Member>() );

        // Testing store
        // Hinweis: Beim Aufruf der Methoden store() oder load() muss eine Exception vom Typ
        // PersistenceException zurückgegeben werden. Der ExceptionType lautet ImplementationNotAvailable
        // Die Message (abrufbar mit der Methode e.getMessage() ) muss einen eindeutigen Text haben, z.B.:
        // assertEquals( e.getMessage() , "MongoDB is not implemented!"  );

        assertDoesNotThrow(() -> container.setPersistenceStrategy(new PersistenceStrategyMongoDB<>()));
        PersistenceException p = assertThrows(PersistenceException.class, () -> container.store());

        assertEquals(PersistenceException.ExceptionType.ImplementationNotAvailable, p.getExceptionTypeType());
        assertEquals("Not implemented!", p.getMessage());
    }

    @Test
    void testNoStrategeySet() {
        //
            // container.setPersistenceStrategie(null);
            // container.store();
        // } catch (PersistenceException e) {
            // Bitte auch hier die Message und den ExceptionType prüfen.
        // }
        assertDoesNotThrow(() -> container.setPersistenceStrategy(null));

        PersistenceException p = assertThrows(PersistenceException.class, () -> container.store());
        assertEquals(PersistenceException.ExceptionType.NoStrategyIsSet, p.getExceptionTypeType());
        assertEquals("Keine PersistenceStrategy vorhanden!", p.getMessage());

        p = assertThrows(PersistenceException.class, () -> container.load());
        assertEquals(PersistenceException.ExceptionType.NoStrategyIsSet, p.getExceptionTypeType());
        assertEquals("Keine PersistenceStrategy vorhanden!", p.getMessage());
    }

    @Test
    void testWrongLocationOfFile() {
        // try {
           //  PersistenceStrategyStream<Member> strat = new PersistenceStrategyStream<Member>();
            // FileStreams do not like directories, so try this out ;-)
            // strat.setLocation("/Users/saschaalda/tmp");
            // container.setPersistenceStrategie( strat );
            // container.store();

        // } catch (PersistenceException e) {
            // Auch hier diverse Assertions bringen, um die Nachricht und den ExceptionType zu testen.
        // }

//         try {
//          PersistenceStrategyStream<Member> strat = new PersistenceStrategyStream<>();
//         //FileStreams do not like directories, so try this out ;-)
//         strat.setLocation("/Users/saschaalda/tmp");
//         container.setPersistenceStrategy(strat);
//         container.store();
//
//         } catch (PersistenceException e) {
//             assertEquals();
//         }
        PersistenceStrategyStream<Member> strat = new PersistenceStrategyStream<>();
        strat.setLocation("test");

        assertDoesNotThrow(() -> container.setPersistenceStrategy(strat));

        PersistenceException p = assertThrows(PersistenceException.class, ()->container.store());
        assertEquals(PersistenceException.ExceptionType.ConnectionNotAvailable, p.getExceptionTypeType());
        assertEquals("File Location konnte nicht gefunden werden!", p.getMessage());

        p = assertThrows(PersistenceException.class, ()->container.load());
        assertEquals(PersistenceException.ExceptionType.ConnectionNotAvailable, p.getExceptionTypeType());
        assertEquals("File Location konnte nicht gefunden werden!", p.getMessage());
    }

    @Test
    void testStoreDeleteAndLoad() {
        // Testen Sie folgenden RoundTrip:
        // 1. Lösche alle Member-Objekte (Sicher ist sicher! Dazu die Methode deleteAllMember implementieren!)
        // 2. Setzen der Strategie
        // 3. Hinzufügen eines Member-Objekts
        // 4. Abspeichern
        // 5. Löschen des Member-Objekts
        // 6. Laden
        // Die Zustandsänderungen mittels der size() bitte stets testen!

        container.deleteAllMembers();
        assertEquals(0, container.size());

        PersistenceStrategyStream<Member> persistenceStrategyStream = new PersistenceStrategyStream<>();
        persistenceStrategyStream.setLocation("test/se1/ws26/tests/uebung3/persistentCache.ser");

        assertDoesNotThrow(() -> container.setPersistenceStrategy(persistenceStrategyStream));

        assertDoesNotThrow(() -> container.addMember((Member) new ConcreteMember(0)));
        assertEquals(1, container.size());

        assertDoesNotThrow(() -> container.store());

        assertEquals("Member-Objekt erfolgreich entfernt!", container.deleteMember(0));
        assertEquals(0, container.size());

        assertDoesNotThrow(() -> container.load());

        assertEquals(1, container.size());
    }

}