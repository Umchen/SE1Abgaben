package se1.ws26.tests.uebung3;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ContainerTest {

    @BeforeEach
    void setUp() {
        // Hier muss der Container einmalig als Singleton instanziiert werden.
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
    }

    @Test
    void testNoStrategeySet() {
        //
            // container.setPersistenceStrategie(null);
            // container.store();
        // } catch (PersistenceException e) {
            // Bitte auch hier die Message und den ExceptionType prüfen.
        // }
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
    }

}