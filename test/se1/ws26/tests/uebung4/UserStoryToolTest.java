package se1.ws26.tests.uebung4;

import org.hbrs.se1.ws26.exercises.uebung3.persistence.PersistenceStrategyStream;
import org.hbrs.se1.ws26.exercises.uebung4.prototype.Container;
import org.hbrs.se1.ws26.exercises.uebung4.prototype.Prio;
import org.hbrs.se1.ws26.exercises.uebung4.prototype.UserStory;
import org.hbrs.se1.ws26.exercises.uebung4.prototype.ContainerException;
import org.hbrs.se1.ws26.exercises.uebung4.prototype.Ausgabe;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * JUnit-5-Tests fuer Aufgabe 4.2 (ausschliesslich uebung4.prototype).
 *
 * Testet einzelne Methoden und das Zusammenspiel von Container, Prio,
 * Ausgabe und PersistenceStrategyStream. Die manuellen End-to-End-Tests
 * der Kommandozeile sind separat durchzufuehren.
 */
class UserStoryToolTest {

    private Container container;

    @BeforeEach
    void setUp() {
        container = Container.getInstance();
        // Container ist ein Singleton: jeden Test mit leerer Liste beginnen.
        container.getCurrentList().clear();
    }

    @AfterEach
    void tearDown() {
        container.getCurrentList().clear();
    }

    @Test
    void singletonLiefertDieselbeInstanz() {
        assertSame(Container.getInstance(), Container.getInstance());
    }

    @Test
    void prioritaetWirdRichtigBerechnet() {
        // (Mehrwert + Strafe) / (Aufwand + Risiko) = (5 + 3) / (2 + 2)
        assertEquals(2.0, Prio.calculate(5, 3, 2, 2), 0.000001);
        assertEquals(1.0, Prio.calculate(1, 1, 1, 1), 0.000001);
        assertEquals(1.0, Prio.calculate(5, 5, 5, 5), 0.000001);
    }

    @Test
    void ungueltigeKennzahlenWerdenAbgelehnt() {
        // Untere Aequivalenzklassen: Zahlen < 1 werden abgelehnt.
        assertThrows(IllegalArgumentException.class,
                () -> Prio.calculate(0, 2, 2, 2));
        assertThrows(IllegalArgumentException.class,
                () -> Prio.calculate(2, 0, 2, 2));
        assertThrows(IllegalArgumentException.class,
                () -> Prio.calculate(2, 2, 0, 2));
        assertThrows(IllegalArgumentException.class,
                () -> Prio.calculate(2, 2, 2, -1));
        assertThrows(IllegalArgumentException.class,
                () -> Prio.calculate(2, 2, 2, 0));

        // Obere Aequivalenzklassen gemaess der aktuellen Implementierung.
        assertThrows(IllegalArgumentException.class,
                () -> Prio.calculate(6, 2, 2, 2));
        assertThrows(IllegalArgumentException.class,
                () -> Prio.calculate(2, 6, 2, 2));
        assertThrows(IllegalArgumentException.class,
                () -> Prio.calculate(2, 2, 6, 2));
    }

    @Test
    void userStorySpeichertIhreFelder() {
        UserStory story = story(7, "Login", 2.5);
        assertAll(
                () -> assertEquals(7, story.getId()),
                () -> assertEquals("Login", story.getTitel()),
                () -> assertEquals("ProjektA", story.getProject()),
                () -> assertEquals("Funktioniert", story.getAcceptCriteria()),
                () -> assertEquals(2.5, story.getPrio(), 0.000001)
        );
    }

    @Test
    void containerNimmtMehrereStoriesAuf() throws ContainerException {
        container.addUserStory(story(1, "Login", 2.0));
        container.addUserStory(story(2, "Logout", 1.0));

        assertEquals(2, container.size());
        assertEquals(List.of(1, 2), container.getCurrentList().stream()
                .map(UserStory::getId).toList());
    }

    @Test
    void doppelteIdsWerdenAbgelehnt() throws ContainerException {
        container.addUserStory(story(1, "Login", 2.0));

        assertThrows(ContainerException.class,
                () -> container.addUserStory(story(1, "AndereStory", 4.0)));
        assertEquals(1, container.size(), "Bei doppelter ID darf nichts hinzugefuegt werden");
    }

    @Test
    void dumpIstAbsteigendSortiertUndHatSpalten() {
        List<UserStory> stories = List.of(
                story(1, "Niedrig", 1.0),
                story(2, "Hoch", 4.0),
                story(3, "Mittel", 2.0)
        );

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        PrintStream vorher = System.out;
        try {
            System.setOut(new PrintStream(buffer, true, StandardCharsets.UTF_8));
            Ausgabe.Dump(stories);
        } finally {
            System.setOut(vorher);
        }

        String ausgabe = buffer.toString(StandardCharsets.UTF_8);
        assertAll(
                () -> assertTrue(ausgabe.contains("ID")),
                () -> assertTrue(ausgabe.contains("Titel")),
                () -> assertTrue(ausgabe.contains("Projekt")),
                () -> assertTrue(ausgabe.contains("Akzeptanzkriterien")),
                () -> assertTrue(ausgabe.contains("Prio")),
                () -> assertTrue(ausgabe.indexOf("Hoch") < ausgabe.indexOf("Mittel")),
                () -> assertTrue(ausgabe.indexOf("Mittel") < ausgabe.indexOf("Niedrig"))
        );
    }

    @Test
    void storeUndLoadStellenAlleStoriesWiederHer(@TempDir Path testOrdner)
            throws Exception {
        // Die bestehende Datei allStories.ser darf nicht ueberschrieben werden.
        // Darum wird die bereits vorhandene Test-Schnittstelle setLocation()
        // der PersistenceStrategyStream verwendet. Container stellt jedoch
        // keinen Getter fuer die Strategie bereit; daher hier Reflection.
        PersistenceStrategyStream<?> strategie = containerStrategie();
        String bisherigerPfad = strategie.getLocation();
        strategie.setLocation(testOrdner.resolve("testStories.ser").toString());

        try {
            container.addUserStory(story(1, "Login", 2.0));
            container.addUserStory(story(2, "Logout", 1.0));
            container.store();

            // Simuliert den leeren RAM nach einem Programmneustart.
            container.getCurrentList().clear();
            assertEquals(0, container.size());

            container.load();
            assertEquals(2, container.size());
            assertEquals(List.of(1, 2), container.getCurrentList().stream()
                    .map(UserStory::getId).toList());
            assertEquals("Login", container.getCurrentList().get(0).getTitel());
            assertEquals(2.0, container.getCurrentList().get(0).getPrio(), 0.000001);
        } finally {
            strategie.setLocation(bisherigerPfad);
        }
    }

    private static UserStory story(int id, String titel, double prio) {
        return new UserStory(id, titel, prio, "ProjektA", "Funktioniert");
    }

    private PersistenceStrategyStream<?> containerStrategie()
            throws ReflectiveOperationException {
        Field feld = Container.class.getDeclaredField("persistenceStrategy");
        feld.setAccessible(true);
        return (PersistenceStrategyStream<?>) feld.get(container);
    }
}
