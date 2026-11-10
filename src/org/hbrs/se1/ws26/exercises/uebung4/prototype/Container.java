package org.hbrs.se1.ws26.exercises.uebung4.prototype;

import org.hbrs.se1.ws26.exercises.uebung3.persistence.PersistenceException;
import org.hbrs.se1.ws26.exercises.uebung3.persistence.PersistenceStrategy;
import org.hbrs.se1.ws26.exercises.uebung3.persistence.PersistenceStrategyStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * Klasse zum Management sowie zur Eingabe unnd Ausgabe von User-Stories.
 * Die Anwendung wird über dies Klasse auch gestartet (main-Methode hier vorhanden)
 *
 * erstellt von Julius P., H-BRS 2026, Version 1.3
 * modifiziert von Umar Jamil,
 *
 * Strategie für die Wiederverwendung (Reuse):
 * - Anlegen der Klasse UserStory
 * - Anpassen des Generic in der List-Klasse (VORHER: Member, NEU: UserStory)
 * - Anpassen der Methodennamen
 *
 * ToDo: Wie bewerten Sie diese Strategie? Was ist ihre Strategie zur Wiederverwendung? (F1)
 *
 * Entwurfsentscheidung: Die wichtigsten Zuständigkeiten (responsibilities)
 * sind in einer Klasse, d.h. Container?
 * ToDo: Wie bewerten Sie diese Entscheidung? Was wäre ein sinnvolle Aufteilung (F2, F6)
 * 
 */

public class Container {
	 
	// Interne ArrayList zur Abspeicherung der Objekte vom Type UserStory
	private List<UserStory> liste;
	
	// Statische Klassen-Variable, um die Referenz
	// auf das einzige Container-Objekt abzuspeichern
	// Diese Variante sei thread-safe, so hat Hr. P. es gehört... stimmt das?
	private static final Container instance = new Container();
	
	// URL der Datei, in der die Objekte gespeichert werden 
	final static String LOCATION = "allStories.ser";
	private final PersistenceStrategy<UserStory> persistenceStrategy =
			new PersistenceStrategyStream<>(LOCATION);

	/**
	 * Liefert ein Singleton zurück.
	 * @return
	 */
	public static Container getInstance() {
		return instance;
	}
	
	/**
	 * Vorschriftsmäßiges Ueberschreiben des Konstruktors (private) gemaess Singleton-Pattern (oder?)
	 *
	 */
	private Container(){
		liste = new ArrayList<>();
	}
	
	/**
	 * Start-Methoden zum Starten des Programms 
	 * (hier koennen ggf. weitere Initialisierungsarbeiten gemacht werden spaeter)
	 */
	public static void main (String[] args) {
		Container con = Container.getInstance();

		try {
			con.startEingabe();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Diese Methode realisiert eine Eingabe ueber einen Scanner
	 * Alle Exceptions werden an den aufrufenden Context (hier: main) weitergegeben (throws)
	 * Das entlastet den Entwickler zur Entwicklungszeit und den Endanwender zur Laufzeit
	 */
	public void startEingabe() throws Exception {
		String strInput;
		
		// Initialisierung des Eingabe-View
		Scanner scanner = new Scanner( System.in );
		System.out.println("Tool fuer Eingabe von User Stories V 1.4");

		while (true) {
			System.out.print( "> "  );
			strInput = scanner.nextLine();
		
			// Extrahiert ein Array aus der Eingabe
			String[] strings = strInput.split(" ");

			// 	Falls 'help' eingegeben wurde, werden alle Befehle ausgedruckt
			if ( strings[0].equals("help") ) {
				System.out.println("enter: Eingabe von User Story mit folgendem Pattern:");
				System.out.println("Titel(String) ID(int) Mehrwert(int) Strafe(int) " +
						"Aufwand(int) Risiko(int) Projektname(String) Akzeptanzkriterium(Abfolge von Strings)");
				System.out.println("Beispiel 12345 1 4 3 6 Beispielprojekt " +
						"Die User Story ist fertig, wenn sie fertig ist!\n");
				System.out.println("store: Persistieren von User Stories");
				System.out.println("load: Laden von User Stories aus einer .ser Datei");
				System.out.println("dump: Gibt eine sortierte Tabelle von User Stories aus");
				System.out.println("exit: Terminiert das Programm");
				System.out.println("help: Zeigt diese Ausgabe an :)");
			}

			// Auswahl der bisher implementierten Befehle:
			else if ( strings[0].equalsIgnoreCase("dump") ) {
				Ausgabe.Dump(List.copyOf(liste));
			}

			else if (strings[0].equalsIgnoreCase("exit")) return;

			else if ( strings[0].equalsIgnoreCase("enter") ) {
				try {
					String titel = strings[1];
					int id = Integer.parseInt(strings[2]);
					int value = Integer.parseInt(strings[3]);
					int penalty = Integer.parseInt(strings[4]);
					int expenses = Integer.parseInt(strings[5]);
					int risk = Integer.parseInt(strings[6]);
					String project = strings[7];
					String acceptCriteria = "";
					for (int i = 8; i < strings.length; i++) {
						acceptCriteria += strings[i] + " ";
					}
					double prio = Prio.calculate(value, penalty, expenses, risk);
					UserStory userStory = new UserStory(id, titel,prio,project,acceptCriteria);
					this.addUserStory(userStory);
					System.out.println("User Story wurde gespeichert.");
				} catch (Exception e) {
					System.out.println("Eingabe konnte nicht verarbeitet werden!");
				}
			}
								
			else if (strings[0].equalsIgnoreCase("store")  ) {
				this.store();
            }

			else if (strings[0].equalsIgnoreCase("load")) {
				this.load();
			}
			else {
				System.out.println("Befehl wurde nicht erkannt, rufe help auf, um eine Liste der Befehle zu erhalten");
			}
		}
	}

	/*
	 * Methode zum Speichern der Liste. Es wird die komplette Liste
	 * inklusive ihrer gespeicherten UserStory-Objekte gespeichert.
	 * 
	 */
	public void store() throws ContainerException {
		if (liste.isEmpty()) {
			System.out.println("Keine User Stories vorhanden zum Speichern!");
			return;
		}
		try {
			persistenceStrategy.save(liste);
			System.out.println(this.size() + " User Stories wurden erfolgreich gespeichert!");
		} catch (PersistenceException e) {
			throw new ContainerException("Fehler beim Abspeichern!");
		}
	}

	/*
	 * Methode zum Laden der Liste. Es wird die komplette Liste
	 * inklusive ihrer gespeicherten UserStory-Objekte geladen.
	 * 
	 */
	public void load() {
		try {
			liste = persistenceStrategy.load();
			System.out.println("Daten konnten erfolgreich geladen werden!");
		} catch (PersistenceException e) {
			System.out.println("Liste konnte nicht geladen werden!");
		}
	}

	/**
	 * Methode zum Hinzufügen eines Mitarbeiters unter Wahrung der Schlüsseleigenschaft
	 * @param userStory
	 * @throws ContainerException
	 */
	public void addUserStory ( UserStory userStory ) throws ContainerException {
		if (contains(userStory)) {
            throw new ContainerException("ID bereits vorhanden!");
		}
		liste.add(userStory);
	}

	/**
	 * Prüft, ob eine UserStory bereits vorhanden ist
	 * @param userStory
	 * @return
	 */
	private boolean contains( UserStory userStory) {
		int ID = userStory.getId();
		for ( UserStory userStory1 : liste) {
			if ( userStory1.getId() == ID ) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Ermittlung der Anzahl von internen UserStory
	 * -Objekten
	 * @return
	 */
	public int size() {
		return liste.size();
	}

	/**
	 * Methode zur Rückgabe der aktuellen Liste mit Stories
	 * Findet aktuell keine Anwendung bei Hr. P.
	 * @return
	 */
	public List<UserStory> getCurrentList() {
		return this.liste;
	}

	/**
	 * Liefert eine bestimmte UserStory zurück
	 * @param id
	 * @return
	 */
	private UserStory getUserStory(int id) {
		for ( UserStory userStory : liste) {
			if (id == userStory.getId() ){
				return userStory;
			}
		}
		return null;
	}
}
